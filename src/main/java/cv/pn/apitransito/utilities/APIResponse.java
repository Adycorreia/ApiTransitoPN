package cv.pn.apitransito.utilities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Builder @Data
public class APIResponse {

	private Boolean status;

	private String statusText;

	private List<Object> details;

	@JsonIgnore
	private final LocalDateTime  timeStamp = LocalDateTime.now();


}
