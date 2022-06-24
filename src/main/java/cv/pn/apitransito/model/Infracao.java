package cv.pn.apitransito.model;


import lombok.Data;

import javax.persistence.Entity;


@Entity
@Data
public class Infracao extends AbstractEntity{

    private String artigo;
    private String descricao;
    private String valor;
    private String previsto;
    private String cont_orden;
    private String creation;
    private String update;
    private String obs;

}
