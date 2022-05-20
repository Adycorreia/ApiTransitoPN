package cv.pn.def.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import cv.pn.def.dtos.PhaseExecDTO;
import cv.pn.def.dtos.FinalJsonDTO;
import cv.pn.def.dtos.sniac.ShippingRequestSniacDTO;
import cv.pn.def.utilities.APIResponse;
import cv.pn.def.utilities.Constants;

public interface PhaseExecService {

    APIResponse processPhaseExecall(String id);
    APIResponse jsonInputPhaseExec(String id);
    APIResponse refusalProcess(FinalJsonDTO dto);
    APIResponse validateProcess(FinalJsonDTO dto);
    APIResponse updatePhaseExec(PhaseExecDTO dto, String id);
    APIResponse insertPhaseExec(PhaseExecDTO dto);
    APIResponse listProcessSendSniac();
    APIResponse detailsCard(String idPhaseExec);
    String xmlDataSniac(String incm);
    APIResponse shippingDataSniac(ShippingRequestSniacDTO dto) throws JsonProcessingException;
    APIResponse updateJson(String id, String idPedido, FinalJsonDTO dto);
    APIResponse validatePreAnalise(String idPhaseExec, FinalJsonDTO dto) throws JsonProcessingException;
}
