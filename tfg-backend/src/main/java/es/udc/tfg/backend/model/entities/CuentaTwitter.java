package es.udc.tfg.backend.model.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Cuenta")
public class CuentaTwitter {

	private String nombre;
	private String nombreUsuario;
	private String biografia;
	private String ubicacion;
	private String sitioWeb;
	private String fotoPerfil;
	private String fotoEncabezado;
	private LocalDate fechaNacimiento;
	private LocalDate fechaCreacion;
	private List<ResultadoClasificador> resultadosClasificador = new ArrayList<ResultadoClasificador>();

	public CuentaTwitter() {
		super();
	}

	public CuentaTwitter(String nombre, String nombreUsuario, String biografia, String ubicacion, String sitioWeb,
			String fotoPerfil, String fotoEncabezado, LocalDate fechaNacimiento, LocalDate fechaCreacion,
			List<ResultadoClasificador> resultadoClasificador) {
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.biografia = biografia;
		this.ubicacion = ubicacion;
		this.sitioWeb = sitioWeb;
		this.fotoPerfil = fotoPerfil;
		this.fotoEncabezado = fotoEncabezado;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaCreacion = fechaCreacion;
		this.resultadosClasificador = resultadoClasificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Id
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public String getFotoEncabezado() {
		return fotoEncabezado;
	}

	public void setFotoEncabezado(String fotoEncabezado) {
		this.fotoEncabezado = fotoEncabezado;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "nombreUsuario")
	public List<ResultadoClasificador> getResultadosClasificador() {
		return resultadosClasificador;
	}

	public void setResultadosClasificador(List<ResultadoClasificador> resultadosClasificador) {
		this.resultadosClasificador = resultadosClasificador;
	}
}
