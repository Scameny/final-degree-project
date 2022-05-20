package es.udc.tfg.backend.model.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ResultadoClasificador {
	private Long id;
	private Double porcentajeBot;
	private Double porcentajeHumano;
	private ConjuntoCuentas conjunto;

	public ResultadoClasificador() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPorcentajeBot() {
		return porcentajeBot;
	}

	public void setPorcentajeBot(Double porcentajeBot) {
		this.porcentajeBot = porcentajeBot;
	}

	public Double getPorcentajeHumano() {
		return porcentajeHumano;
	}

	public void setPorcentajeHumano(Double porcentajeHumano) {
		this.porcentajeHumano = porcentajeHumano;
	}

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "idConjunto")
	public ConjuntoCuentas getConjunto() {
		return conjunto;
	}

	public void setConjunto(ConjuntoCuentas conjunto) {
		this.conjunto = conjunto;
	}

}
