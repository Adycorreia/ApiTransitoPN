package cv.pn.apitransito.dtos;


import cv.pn.apitransito.utilities.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor

public class EfectivosResponseDTO {

    private Long  idagente;

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


}
