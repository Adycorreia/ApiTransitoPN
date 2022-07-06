package cv.pn.apitransito.model;

import cv.pn.apitransito.utilities.Constants;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Agente extends AbstractEntity{

    private Number id_pn;

    private String nome;

    private String apelido;

    private LocalDate data_nasc;

    private Constants.Sexo sexo;

    private String filiacao;

    private int idade;

    private String cni;

    private Number nif;

    private String funcao;

    private String morada;

    private String posto;

    private String contacto;

    private String email;

    @Column(name = "creation", nullable = true, columnDefinition = "TIMESTAMP DEFAULT now()")
    @CreationTimestamp
    private LocalDateTime creation;

    private String update;

    private String obs;

    private String fotografia;

    private String assinatura;

    @Column(name = "estado_civil", nullable = true, columnDefinition = "character varying(40)")
    @Enumerated(value = EnumType.STRING )
    private Constants.EstadoCivil estado_civil;

    private String ilha_id;

    private String concelho_id;

    private String freguesia_id;

    private String localidade_id;

    private String zona_id;

    private String nacionalidade_id;

    private Boolean cv_nacionalidade;

    private String nivel_instrucao;

    private String local_nascimento;

    private String naturalidade;

    private Number n_arma;

    private String marca;

    private String modelo;

    private String calibre;

    private Number n_carregador;

    private Number n_municoes;

    private String estado_arma;

    private LocalDate data_inspeArma;



}
