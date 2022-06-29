package cv.pn.apitransito.model.acessoschema;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Organica {

    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    @Column(name = "nome")
    private String name;

    @Column(name = "codigo")
    private String code;

    @Column(name = "estado")
    private String state;

    @Column(name = "id_ent_igrp")
    private String idEntIgrp;

    @Column(name = "nif")
    private String nif;

    @Column(name = "recebedoria")
    private String recebedoria;

    @Column(name = "codigo_def")
    private String codDef;

    @Column(name = "cd_postal")
    private String cdPostal;

    @Column(name = "morada_l1")
    private String moradaL1;

    @Column(name = "morada_l2")
    private String moradaL2;

    @Column(name = "localidade")
    private String localidade;

    @Column(name = "pais")
    private String pais;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "ilha")
    private String ilha;

    @Column(name = "id_moeda")
    private String idMoeda;

    @Column(name = "cobranca_via_duc")
    private String cobrancaViaDuc;

}
