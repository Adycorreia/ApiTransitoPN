package cv.pn.apitransito.model;

import cv.pn.apitransito.utilities.Constants;
import lombok.Data;

import javax.persistence.*;

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

    private String fotografia;

    private String assinatura;




}
