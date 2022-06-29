package cv.pn.apitransito.dtos;

import cv.pn.apitransito.utilities.Constants;
import lombok.Data;

import java.util.List;

@Data
public class DomainDTO {

	List<DomainRequest> domain;

	@Data
	public static class DomainRequest {

		private String dominio;

		private String valor;

		private String descricao;

		private int order;

		private Constants.DMEstado estado;

	}

}
