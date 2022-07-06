package cv.pn.apitransito.dtos;

import cv.pn.apitransito.model.Agente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArmamentoResponseDTO {

    private Long  idarma;
    private Number numero;
    private String marca;
    private String modelo;
    private String calibre;
    private Number n_carregador;
    private Number n_municoes;
    private String estado;
    private String fotografia;

    private Long id_agente;
    private String nome_efectivo;
    private String apelido_efectivo;

    private LocalDateTime creation;

    private String update;
    private String obs;


}
