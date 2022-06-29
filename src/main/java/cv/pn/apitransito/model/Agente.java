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

    private Constants.Sexo sexo;

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

    @Column(name = "estado_civil", nullable = true, columnDefinition = "character varying(40)")
    @Enumerated(value = EnumType.STRING )
    private Constants.EstadoCivil estado_civil;

    @Column(name = "ilha_id", nullable = true, columnDefinition = "character varying(40)")
    private String ilha_id;

    @Column(name = "concelho_id", nullable = true, columnDefinition = "character varying(40)")
    private String concelho_id;

    @Column(name = "freguesia_id", nullable = true, columnDefinition = "character varying(40)")
    private String freguesia_id;

    @Column(name = "localidade_id", nullable = true, columnDefinition = "character varying(40)")
    private String localidade_id;

    @Column(name = "zona_id", nullable = true, columnDefinition = "character varying(40)")
    private String zona_id;

    @Column(name = "nacionalidade_id", nullable = true, columnDefinition = "character varying(40)")
    private String nacionalidade_id;

    @Column(name = "cv_nacionalidade", nullable = true, columnDefinition = "boolean")
    private Boolean cv_nacionalidade;

    @Column(name = "nivel_instrucao", nullable = true, columnDefinition = "character varying(100)")
    private String nivel_instrucao;

    @Column(name = "local_nascimento", nullable = true, columnDefinition = "character varying(200)")
    private String local_nascimento;

    @Column(name = "naturalidade", nullable = true, columnDefinition = "character varying(40)")
    private String naturalidade;



}
