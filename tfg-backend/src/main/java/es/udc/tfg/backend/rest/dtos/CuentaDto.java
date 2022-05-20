package es.udc.tfg.backend.rest.dtos;

public class CuentaDto implements Comparable<CuentaDto> {
	private String nombreUsuario;
	private Double porcentajeBot;
	private Double porcentajeHumano;

	public CuentaDto(String nombreUsuario, Double porcentajeBot, Double porcentajeHumano) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.porcentajeBot = porcentajeBot;
		this.porcentajeHumano = porcentajeHumano;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
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

	@Override
	public int compareTo(CuentaDto c) {
		Double porcentaje;
		if (c.getPorcentajeBot() == null)
			porcentaje = c.getPorcentajeHumano();
		else
			porcentaje = (-c.getPorcentajeBot());
		if (this.getPorcentajeBot() == null)
			return Double.compare(this.porcentajeHumano, porcentaje);
		return Double.compare(-this.porcentajeBot, porcentaje);
	}

}
