package cv.pn.apitransito.services;


import cv.pn.apitransito.dtos.ArmamentoResponseDTO;
import cv.pn.apitransito.model.Agente;
import cv.pn.apitransito.utilities.APIResponse;

public interface ArmamentoService {

    APIResponse armamentoAll();
    APIResponse insertArmamentoAll(ArmamentoResponseDTO armamentoResponseDTO);
    APIResponse deleteArma(Long id);
    APIResponse listById(Long Id);
    APIResponse updateArmaId(Long id, ArmamentoResponseDTO dto);
    APIResponse listByIdAgentId(Long id);

}
