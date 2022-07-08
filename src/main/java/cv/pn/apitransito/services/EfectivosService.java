package cv.pn.apitransito.services;


import cv.pn.apitransito.dtos.DocumentsResponseDTO;
import cv.pn.apitransito.dtos.EfectivosResponseDTO;
import cv.pn.apitransito.utilities.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface EfectivosService {

    APIResponse efectivosAll();
    APIResponse insertEfectivos(EfectivosResponseDTO efectivosResponseDTO);
    APIResponse deleteefect(Long id);
    APIResponse listById(Long Id);
    APIResponse updateEfectId(Long id, EfectivosResponseDTO dto);
    APIResponse updateDocIdArma(Long id, EfectivosResponseDTO dto);


}
