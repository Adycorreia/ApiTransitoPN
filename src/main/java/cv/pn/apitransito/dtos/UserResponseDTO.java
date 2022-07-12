package cv.pn.apitransito.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {

    private String id_user;
    private String username;
    private String name;
    private String status;
    private String organica;
    private String idOrganica;

}
