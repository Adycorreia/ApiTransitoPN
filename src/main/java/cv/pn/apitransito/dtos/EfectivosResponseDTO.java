package cv.pn.apitransito.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class EfectivosResponseDTO {

    private Long  idagente;
    private Number id_pn;
    private String nome;
    private String apelido;
    private String data_nasc;
    private String sexo;
    private String filiacao;
    private String idade;
    private String cni;
    private Number nif;
    private String função;
    private String morada;
    private String posto;
    private String contacto;
    private String email;
    private String creation;
    private String update;
    private String obs;


}
