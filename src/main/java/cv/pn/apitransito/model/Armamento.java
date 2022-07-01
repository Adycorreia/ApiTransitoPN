package cv.pn.apitransito.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@Entity
@Data
public class Armamento extends AbstractEntity{

    private Number numero;
    private String marca;
    private String modelo;
    private String calibre;
    private Number n_carregador;
    private Number n_municoes;
    private String estado;
    private String fotografia;

    @ManyToOne
    @JoinColumn(name = "id_agente",  referencedColumnName = "id",  updatable=false)
    @JsonBackReference
    private Agente id_agente;

    @Column(name = "creation", nullable = true, columnDefinition = "TIMESTAMP DEFAULT now()")
    @CreationTimestamp
    private LocalDateTime creation;

    private String update;
    private String obs;


}
