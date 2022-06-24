package cv.pn.apitransito.services;


import cv.pn.apitransito.dtos.DocumentsResponseDTO;
import cv.pn.apitransito.model.Documents;
import cv.pn.apitransito.utilities.APIResponse;

import java.util.List;

public interface DocumentsService {

    APIResponse documentsAll();
    APIResponse insertDocuments(DocumentsResponseDTO documentsResponseDTO);
    APIResponse findTripod(String tipodoc);
    APIResponse deletedoc(Long id);
    APIResponse listById(Long Id);
    APIResponse updateDocId(Long id, DocumentsResponseDTO dto);
}
