package cv.pn.apitransito.controller;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AgenteEndpoint {

    private int idNumeroAgente;
    private String nome;
    private String posto;
    private String funcção;
    private String morada;
    private String contacto;
    private String obs;
}
