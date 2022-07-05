package cv.pn.apitransito.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cv.pn.apitransito.model.Agente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeriasResponseDTO {

    private Long  idferia;
    private LocalDate data_inicio;
    private LocalDate data_fim;
    private String local_feria;
    private String entrega_arma;
    private String n_oficio;
    private String despacho;
    private String estado;
    private String pass_numero;
    private String user_despacho;

    private Long id_agente;
    private String nome_efectivo;
    private String apelido_efectivo;

    private LocalDateTime creation;

    private String update;
    private String obs;


}
