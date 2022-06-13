package cv.pn.apitransito.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class EfectivosResponseDTO {

    private Long  idagente;
    private String nome;
    private String função;
    private String morada;
    private String posto;
    private String contacto;
    private String email;
    private String creation;
    private String update;
    private String obs;


}
