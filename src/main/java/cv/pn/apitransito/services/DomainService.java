package cv.pn.apitransito.services;

import cv.pn.apitransito.dtos.DomainDTO;
import cv.pn.apitransito.utilities.APIResponse;


public interface DomainService {

	APIResponse findDomain(String dominio);
	APIResponse insertDomain(DomainDTO dto);
	APIResponse updateDomain(DomainDTO.DomainRequest dto, String id);
	APIResponse swicthEstadoDomain(String id);

}
