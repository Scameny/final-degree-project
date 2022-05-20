package es.udc.tfg.backend.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

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
import es.udc.tfg.backend.model.services.ClasificadorService;
import es.udc.tfg.backend.model.services.ClasificadorServiceImpl;
import es.udc.tfg.backend.model.services.PermissionChecker;
import es.udc.tfg.backend.model.services.XmlParseException;

public class ClasificadorServiceUnitTest {

	private CuentaTwitterDao cuentaDao;
	private TweetDao tweetDao;
	private ConjuntoCuentasDao conjuntoDao;
	private PermissionChecker permissionChecker;
	private ClasificadorService clasificadorService;
	private ResultadoClasificadorDao resultadoDao;
	private CuentaTwitter cuenta;

	@BeforeEach
	void initUseCase() {
		cuentaDao = Mockito.mock(CuentaTwitterDao.class);
		tweetDao = Mockito.mock(TweetDao.class);
		conjuntoDao = Mockito.mock(ConjuntoCuentasDao.class);
		resultadoDao = Mockito.mock(ResultadoClasificadorDao.class);
		permissionChecker = Mockito.mock(PermissionChecker.class);
		clasificadorService = new ClasificadorServiceImpl(tweetDao, cuentaDao, conjuntoDao, permissionChecker,
				resultadoDao);
		cuenta = new CuentaTwitter();
	}

	// El fichero usado para el input de los test de unidad del clasificador tiene 2
	// usuarios con varios tweets
	@Test
	public void testClasificarConjuntoDeCuentasSinInteracciones() throws XmlParseException, InstanceNotFoundException {
		ArgumentCaptor<CuentaTwitter> argCuenta = ArgumentCaptor.forClass(CuentaTwitter.class);
		ArgumentCaptor<Tweet> argTweet = ArgumentCaptor.forClass(Tweet.class);
		cuenta.setNombreUsuario("cuentaTest");
		Optional<CuentaTwitter> opt = Optional.of(cuenta);
		/*
		 * Las operaciones de guardado en bd no devuelven ningún valor nuevo ya que el
		 * id tanto de las cuentas (el @) como el de los tweets (id) ya viene dado en el
		 * input
		 */
		when(permissionChecker.checkUser(any(Long.class))).thenReturn(new User());
		when(conjuntoDao.save(any(ConjuntoCuentas.class))).then(returnsFirstArg());
		when(cuentaDao.save(any(CuentaTwitter.class))).then(returnsFirstArg());
		when(resultadoDao.save(any(ResultadoClasificador.class))).then(returnsFirstArg());
		when(tweetDao.save(any(Tweet.class))).then(returnsFirstArg());
		when(cuentaDao.findById(any(String.class))).thenReturn(opt);

		clasificadorService.clasificarConjuntoDeCuentas("input_test_sinInt.xml", "set1", "test", 1L);
		/*
		 * Debido a que el método crea primero los usuarios gracias al input y luego
		 * actualiza sus porcentajes en función de lo obtenido en el clasificador. Por
		 * ello se realizan dos saves por usuario
		 */
		verify(tweetDao, times(4)).save(argTweet.capture());
		verify(cuentaDao, times(4)).save(argCuenta.capture());
		assertFalse(argCuenta.getAllValues().isEmpty());
		// Comprobamos que el primer usuario del .xml se ha procesado bien
		assertEquals(argCuenta.getAllValues().get(0).getNombreUsuario(), "madridagm");
		assertTrue(argCuenta.getAllValues().get(3).getResultadosClasificador().get(0).getPorcentajeBot() != null
				|| argCuenta.getAllValues().get(3).getResultadosClasificador().get(0).getPorcentajeHumano() != null);
		/*
		 * Comprobamos que el primer tweet del primer usuario se ha procesado bien y se
		 * han guardado bien todos sus datos
		 */
		Tweet tweet = argTweet.getAllValues().get(0);
		assertEquals((long) argTweet.getAllValues().get(0).getId(), Long.parseLong("1090380735364632576"));
		assertEquals(tweet.getEnlace(), "https://twitter.com/madridagm/status/1090380735364632576");
		assertEquals(tweet.getFechaHoraTweet(), LocalDateTime
				.ofInstant(Instant.ofEpochMilli(Long.parseLong("1548802000000")), TimeZone.getDefault().toZoneId()));
		assertEquals(tweet.getTexto(),
				"Pues pedir igualdad de horario pero no que yo tenga que llamarles con una hora antes de acabar la cena que no sé ni cuando me voy a ir! Que os va a salir el tiro por la culata");
		assertEquals(tweet.getUsuario(), argCuenta.getAllValues().get(0));
	}

	@Test
	public void testClasificarConjuntoDeCuentasConInteracciones() throws XmlParseException, InstanceNotFoundException {
		ArgumentCaptor<CuentaTwitter> argCuenta = ArgumentCaptor.forClass(CuentaTwitter.class);
		ArgumentCaptor<Tweet> argTweet = ArgumentCaptor.forClass(Tweet.class);
		cuenta.setNombreUsuario("cuentaTest");
		Optional<CuentaTwitter> opt = Optional.of(cuenta);
		Optional<CuentaTwitter> optEmp = Optional.empty();

		/*
		 * Las operaciones de guardado en bd no devuelven ningún valor nuevo ya que el
		 * id tanto de las cuentas (el @) como el de los tweets (id) ya viene dado en el
		 * input
		 */
		when(permissionChecker.checkUser(any(Long.class))).thenReturn(new User());
		when(conjuntoDao.save(any(ConjuntoCuentas.class))).then(returnsFirstArg());
		when(cuentaDao.save(any(CuentaTwitter.class))).then(returnsFirstArg());
		when(tweetDao.save(any(Tweet.class))).then(returnsFirstArg());
		when(cuentaDao.findById(any(String.class))).thenReturn(optEmp);
		when(cuentaDao.findById("madridagm")).thenReturn(opt);
		when(cuentaDao.findById("tostijulian")).thenReturn(opt);

		clasificadorService.clasificarConjuntoDeCuentas("input_test_conInt.xml", "set2", "test", 1L);

		/*
		 * En este caso de prueba cuentaDao llama al método save 8 veces, dos por cada
		 * usuario que se vaya a clasificar y una vez por cada usuario que ha dado me
		 * gusta o retweet a uno de los tweets del fichero de input y que no esté en
		 * este último fichero
		 */
		verify(tweetDao, times(2)).save(argTweet.capture());
		verify(cuentaDao, times(8)).save(argCuenta.capture());
		assertFalse(argCuenta.getAllValues().isEmpty());

		/*
		 * Comprobamos que el primer tweet tiene los me gusta que debería tener
		 */
		Tweet tweet = argTweet.getAllValues().get(0);
		assertEquals((long) argTweet.getAllValues().get(0).getId(), Long.parseLong("1090380735364632576"));
		assertEquals(tweet.getMeGusta().size(), 5);
	}

	/*
	 * Se prueba a parsear un archivo mal formado. En este caso, carente de una
	 * etiqueta.
	 */
	@Test
	public void testClasificarConjuntoDeCuentasXmlMalFormado() throws XmlParseException {
		Assertions.assertThrows(XmlParseException.class, () -> {
			clasificadorService.clasificarConjuntoDeCuentas("input_test_mal_formado.xml", "set3", "test", 1L);

		});
	}
}
