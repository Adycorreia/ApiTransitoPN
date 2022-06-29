package cv.pn.apitransito.services;

import cv.pn.apitransito.model.acessoschema.Geografia;


import java.util.List;
import java.util.Optional;

public interface GeographyService {

	List<Geografia> getGeografia(Integer level, String selfId);
	Optional<Geografia> getNameById(String id);
	//Optional<Geography> findNomeCartaoById(String id);

}
