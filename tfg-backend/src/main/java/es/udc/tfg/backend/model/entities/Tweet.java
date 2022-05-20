package es.udc.tfg.backend.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Tweet")
public class Tweet {

	private Long id;
	private List<CuentaTwitter> meGusta = new ArrayList<CuentaTwitter>();
	private List<CuentaTwitter> retweet = new ArrayList<CuentaTwitter>();
	private String texto;
	private CuentaTwitter usuario;
	private LocalDateTime fechaHoraTweet;
	private String multimedia;
	private String enlace;
	private String plataformaUsada;

	public Tweet() {
		super();
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MeGusta", joinColumns = { @JoinColumn(name = "idTweet") }, inverseJoinColumns = {
			@JoinColumn(name = "nombreUsuario") })
	public List<CuentaTwitter> getMeGusta() {
		return meGusta;
	}

	public void setMeGusta(List<CuentaTwitter> meGusta) {
		this.meGusta = meGusta;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Retweet", joinColumns = { @JoinColumn(name = "idTweet") }, inverseJoinColumns = {
			@JoinColumn(name = "nombreUsuario") })
	public List<CuentaTwitter> getRetweet() {
		return retweet;
	}

	public void setRetweet(List<CuentaTwitter> retweet) {
		this.retweet = retweet;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getFechaHoraTweet() {
		return fechaHoraTweet;
	}

	public void setFechaHoraTweet(LocalDateTime fechaHoraTweet) {
		this.fechaHoraTweet = fechaHoraTweet;
	}

	public String getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(String multimedia) {
		this.multimedia = multimedia;
	}

	public String getEnlace() {
		return enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	public String getPlataformaUsada() {
		return plataformaUsada;
	}

	public void setPlataformaUsada(String plataformaUsada) {
		this.plataformaUsada = plataformaUsada;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "nombreUsuario")
	public CuentaTwitter getUsuario() {
		return usuario;
	}

	public void setUsuario(CuentaTwitter usuario) {
		this.usuario = usuario;
	}

}
