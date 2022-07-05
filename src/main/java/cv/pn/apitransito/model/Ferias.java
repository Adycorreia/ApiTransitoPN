package cv.pn.apitransito.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Data
public class Ferias extends AbstractEntity{

    private LocalDate data_inicio;
    private LocalDate data_fim;
    private String local_feria;
    private String entrega_arma;
    private String n_oficio;
    private String despacho;
    private String estado;
    private String pass_numero;
    private String user_despacho;

    @ManyToOne
    @JoinColumn(name = "id_agente",  referencedColumnName = "id",  updatable=true)
    @JsonBackReference
    private Agente agente;

    @Column(name = "creation", nullable = true, columnDefinition = "TIMESTAMP DEFAULT now()")
    @CreationTimestamp
    private LocalDateTime creation;

    private String update;
    private String obs;


}
