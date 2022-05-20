package es.udc.tfg.backend.model.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.udc.tfg.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.tfg.backend.model.entities.ConjuntoCuentas;
import es.udc.tfg.backend.model.entities.CuentaTwitter;
import es.udc.tfg.backend.model.entities.ResultadoClasificador;
import es.udc.tfg.backend.model.entities.Tweet;
import es.udc.tfg.backend.model.entities.User;
import es.udc.tfg.backend.model.repositories.ConjuntoCuentasDao;
import es.udc.tfg.backend.model.repositories.CuentaTwitterDao;
import es.udc.tfg.backend.model.repositories.ResultadoClasificadorDao;
import es.udc.tfg.backend.model.repositories.TweetDao;

@Service
@Transactional
public class ClasificadorServiceImpl implements ClasificadorService {

	private final TweetDao tweetDao;
	private final ResultadoClasificadorDao resultadoDao;
	private final CuentaTwitterDao cuentaTwitterDao;
	private final ConjuntoCuentasDao conjuntoDao;
	private final PermissionChecker permissionChecker;

	public ClasificadorServiceImpl(TweetDao tweetDao, CuentaTwitterDao cuentaTwitterDao, ConjuntoCuentasDao conjuntoDao,
			PermissionChecker permissionChecker, ResultadoClasificadorDao resultadoDao) {
		super();
		this.tweetDao = tweetDao;
		this.cuentaTwitterDao = cuentaTwitterDao;
		this.conjuntoDao = conjuntoDao;
		this.permissionChecker = permissionChecker;
		this.resultadoDao = resultadoDao;
	}

	@Override
	public ConjuntoCuentas clasificarConjuntoDeCuentas(String filePath, String nombreConjunto, String descripcion,
			Long userId) throws XmlParseException, InstanceNotFoundException {
		File bitbucket;

		User user = permissionChecker.checkUser(userId);

		try {

			bitbucket = new File("/dev/null");
			// Llamamos al shell para correr el comando y volcar la salida del script en un
			// fichero
			ProcessBuilder builder = new ProcessBuilder("/bin/sh", "-c",
					"cat input/" + filePath + " |/usr/bin/perl scripts/classif.perl > output/result.txt")
							.redirectOutput(ProcessBuilder.Redirect.appendTo(bitbucket))
							.redirectError(ProcessBuilder.Redirect.appendTo(bitbucket));

			// Ponemos como directorio desde el que ejecutar el comando la carpeta donde
			// tenemos los inputs, el script y las salidas
			builder.directory(new File("classifier"));
			Process process = builder.start();
			process.waitFor();
			if (process.exitValue() == 0) {
				ConjuntoCuentas conjunto = persistenciaInput(filePath);
				conjunto.setAutor(user);
				conjunto.setDescripcion(descripcion);
				conjunto.setNombre(nombreConjunto);
				conjunto.setFechaCreacion(LocalDateTime.now());
				conjuntoDao.save(conjunto);
				persistenciaOutput(conjunto);
				return conjunto;
			} else {
				throw new CommandFailureException("Command have failed");
			}
		} catch (XmlParseException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Método usado para persistir las cuentas y tweets del fichero de input
	// enformato xml
	private ConjuntoCuentas persistenciaInput(String filePath)
			throws IOException, ParserConfigurationException, SAXException, XmlParseException {
		try {
			File inputFile = new File("classifier/input/" + filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			// Evitamos que el parser escriba los errores ya que estos son catcheados por
			// las excepciones
			// dBuilder.setErrorHandler(null);
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("user");
			// Recorremos todos los usuarios del archivo xml añadiendolo a la bd junto a sus
			// tweets
			ConjuntoCuentas conjunto = new ConjuntoCuentas();
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					CuentaTwitter cuenta;
					if (!cuentaTwitterDao.existsById(eElement.getAttribute("name"))) {
						cuenta = new CuentaTwitter();
						cuenta.setNombreUsuario(eElement.getAttribute("name"));
						cuentaTwitterDao.save(cuenta);
					} else
						cuenta = cuentaTwitterDao.findById(eElement.getAttribute("name")).get();
					// Añadimos la cuenta al conjunto
					conjunto.getCuentas().add(cuenta);
					NodeList tweetsList = eElement.getElementsByTagName("document");
					// Recorremos todos los tweets del usuario
					for (int temp2 = 0; temp2 < tweetsList.getLength(); temp2++) {
						Node nNode2 = tweetsList.item(temp2);
						if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement2 = (Element) nNode2;
							Tweet tweet = new Tweet();
							tweet.setId(Long
									.parseLong(eElement2.getElementsByTagName("id_tweet").item(0).getTextContent()));
							/*
							 * Para pasar del tipo TimeStamp a LocalDateTime es necesario primero extraer el
							 * valor del XML, pasarlo a long para luego inicializar el tipo LocalDateTime
							 * pasándole el timestamp y la zona horaria
							 */
							tweet.setFechaHoraTweet(LocalDateTime.ofInstant(
									Instant.ofEpochMilli(Long.parseLong(
											eElement2.getElementsByTagName("timestamp").item(0).getTextContent())),
									TimeZone.getDefault().toZoneId()));
							tweet.setEnlace(eElement2.getElementsByTagName("url").item(0).getTextContent());
							tweet.setTexto(eElement2.getElementsByTagName("body").item(0).getTextContent());
							tweet.setUsuario(cuenta);

							/*
							 * En caso de haber retweets o likes pasaríamos el string de las cuentas que
							 * hayan interaccionado al metodo para persistirlas
							 */
							int number;
							Element likesElement = (Element) eElement2.getElementsByTagName("likes").item(0);
							if ((number = Integer.valueOf(likesElement.getAttribute("number"))) > 0)
								persistenciaInteraccionesTweet(tweet.getMeGusta(), likesElement.getTextContent(),
										number);

							Element rtElement = (Element) eElement2.getElementsByTagName("retweets").item(0);
							if ((number = Integer.valueOf(rtElement.getAttribute("number"))) > 0)
								persistenciaInteraccionesTweet(tweet.getRetweet(), rtElement.getTextContent(), number);

							System.out.println("entró");

							tweetDao.save(tweet);
						}
					}
				}
			}
			return conjunto;
		} catch (SAXException e) {
			throw new XmlParseException(e.getMessage());
		}
	}

	/*
	 * Método usado para buscar o crear las cuentas que han interactuado con el
	 * tweet para poder persistirlo en bd
	 */
	private void persistenciaInteraccionesTweet(List<CuentaTwitter> listaInt, String inter, int numero)
			throws IOException {

		String[] values = inter.split(",");
		for (int temp = 0; temp < values.length; temp++) {
			Optional<CuentaTwitter> cuentaOpt = cuentaTwitterDao.findById(values[temp]);
			CuentaTwitter cuenta;
			if (!cuentaOpt.isPresent()) {
				cuenta = new CuentaTwitter();
				cuenta.setNombreUsuario(values[temp]);
				cuentaTwitterDao.save(cuenta);
			} else
				cuenta = cuentaOpt.get();
			listaInt.add(cuenta);
		}
	}

	/*
	 * Método para la persistencia del fichero de Output en formato txt. Actualizará
	 * las cuentas ya persistidas con el resultado del clasificador
	 */
	private void persistenciaOutput(ConjuntoCuentas conjunto) throws IOException, InstanceNotFoundException {

		File file = new File("classifier/output/result.txt");
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(isr);

		String line;
		// Recorremos todas las líneas del fichero de salida para poder guardar los
		// resultados del clasificador en bd
		while ((line = br.readLine()) != null) {
			String[] values = line.split("\\s+");
			Optional<CuentaTwitter> cuentaOpt = cuentaTwitterDao.findById(values[1]);
			if (!cuentaOpt.isPresent()) {
				throw new InstanceNotFoundException("project.entities.user", values[1]);
			}
			CuentaTwitter cuenta = cuentaOpt.get();
			ResultadoClasificador resultado = new ResultadoClasificador();
			resultado.setConjunto(conjunto);
			if (values[2].equals("human"))
				resultado.setPorcentajeHumano(Double.parseDouble(values[0]));
			else
				resultado.setPorcentajeBot(Double.parseDouble(values[0]));
			cuenta.getResultadosClasificador().add(resultado);
			resultadoDao.save(resultado);
		}

	}

}
