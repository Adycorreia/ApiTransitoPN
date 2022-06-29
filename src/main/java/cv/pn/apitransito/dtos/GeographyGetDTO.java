package cv.pn.apitransito.dtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class GeographyGetDTO {

	private String id;

	private String name;

	private Integer level;

	private String SelfId;

}
