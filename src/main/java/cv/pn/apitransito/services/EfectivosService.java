package cv.pn.apitransito.services;


import cv.pn.apitransito.dtos.DocumentsResponseDTO;
import cv.pn.apitransito.dtos.EfectivosResponseDTO;
import cv.pn.apitransito.utilities.APIResponse;

public interface EfectivosService {

    APIResponse efectivosAll();
    APIResponse insertEfectivos(EfectivosResponseDTO efectivosResponseDTO);
    APIResponse deleteefect(Long id);
    APIResponse listById(Long Id);
    APIResponse updateEfectId(Long id, EfectivosResponseDTO dto);

}
