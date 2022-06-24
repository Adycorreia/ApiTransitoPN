package cv.pn.apitransito.dtos;


import cv.pn.apitransito.model.Infracao;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class InfracaoResponseDTO {

    private Long  idinfracao;
    private String artigo;
    private String descricao;
    private String valor;
    private String previsto;
    private String Cont_orden;
    private String creation;
    private String update;
    private String obs;

}
