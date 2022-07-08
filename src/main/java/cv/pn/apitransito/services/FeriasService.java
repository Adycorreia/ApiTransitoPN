package cv.pn.apitransito.services;

import cv.pn.apitransito.dtos.FeriasResponseDTO;
import cv.pn.apitransito.utilities.APIResponse;

public interface FeriasService {

    APIResponse feriasAll();
    APIResponse insertFerias(FeriasResponseDTO feriasResponseDTO);
    APIResponse deleteFeria(Long id);
    APIResponse listById(Long Id);
    APIResponse updateFeriatId(Long id, FeriasResponseDTO dto);
}
