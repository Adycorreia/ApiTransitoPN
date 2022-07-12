package cv.pn.apitransito.dtos;


import cv.pn.apitransito.utilities.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor

public class ArmaEdResponseDTO {

    private Number n_carregador;

    private Number n_municoes;

    private String estado_arma;

    private LocalDate data_inspeArma;


}
