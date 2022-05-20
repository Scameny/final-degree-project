package es.udc.tfg.backend.rest.dtos;

public class InteraccionDto {

	private String source;
	private String target;
	private Long quantity;
	private String interacción;

	public InteraccionDto(String source, String target, Long quantity, String interacción) {
		super();
		this.source = source;
		this.target = target;
		this.quantity = quantity;
		this.interacción = interacción;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getInteracción() {
		return interacción;
	}

	public void setInteracción(String interacción) {
		this.interacción = interacción;
	}

}
