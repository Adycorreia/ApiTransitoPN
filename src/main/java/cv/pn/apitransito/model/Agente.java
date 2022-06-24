package cv.pn.apitransito.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Agente extends AbstractEntity{

    private Number id_pn;
    private String nome;
    private String apelido;
    private String data_nasc;
    private String sexo;
    private String filiacao;
    private String idade;
    private String cni;
    private Number nif;
    private String funcao;
    private String morada;
    private String posto;
    private String contacto;
    private String email;
    private String creation;
    private String update;
    private String obs;
}
