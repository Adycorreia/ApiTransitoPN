package cv.pn.apitransito.services;


import cv.pn.apitransito.dtos.InfracaoResponseDTO;
import cv.pn.apitransito.utilities.APIResponse;

public interface InfracaoService {

    APIResponse infracaoAll();
    APIResponse insertInfracao(InfracaoResponseDTO infracaoResponseDTO);

}
