package cv.pn.apitransito.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DomainResponseDTO {

	private String idDomain;

	private String code;

	private String descricao;
}
