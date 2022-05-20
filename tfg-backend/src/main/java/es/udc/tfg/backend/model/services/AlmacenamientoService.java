package es.udc.tfg.backend.model.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import es.udc.tfg.backend.rest.controllers.common.PropiedadesFichero;

@Service
public class AlmacenamientoService {

	private final Path fileStorageLocation;

	@Autowired
	public AlmacenamientoService(PropiedadesFichero propiedadesFichero) {
		this.fileStorageLocation = Paths.get(propiedadesFichero.getArchivoSubido()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new AlmacenamientoException("No se ha podido crear el directorio de almacenamiento.", ex);
		}
	}

	public String storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new AlmacenamientoException("El fichero contiene una ruta incorrecta " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new AlmacenamientoException("No se pudo almacenar el fichero" + fileName, ex);
		}
	}

	public void destroyFile(String path) {

		try {
			// Check if the file's name contains invalid characters
			if (path.contains("..")) {
				throw new AlmacenamientoException("El fichero contiene una ruta incorrecta " + path);
			}

			Path targetLocation = this.fileStorageLocation.resolve(path);
			Files.delete(targetLocation);

		} catch (IOException ex) {
			throw new AlmacenamientoException("No se pudo borrar el fichero" + path, ex);
		}
	}

}