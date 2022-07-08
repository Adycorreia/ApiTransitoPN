package cv.pn.apitransito.services;

import cv.pn.apitransito.dtos.FeriasResponseDTO;
import cv.pn.apitransito.utilities.APIResponse;

public interface FeriasService {

    APIResponse feriasAll();
    APIResponse insertEfectivos(FeriasResponseDTO feriasResponseDTO);
    APIResponse deleteefect(Long id);
    APIResponse listById(Long Id);
    APIResponse updateEfectId(Long id, FeriasResponseDTO dto);
}
