package cv.pn.apitransito.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {
    private String id_user;
    private String username;
    private String name;
    private String status;
}
