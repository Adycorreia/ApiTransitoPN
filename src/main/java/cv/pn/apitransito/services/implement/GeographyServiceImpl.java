package cv.pn.apitransito.services.implement;


import cv.pn.apitransito.model.acessoschema.Geografia;
import cv.pn.apitransito.repository.acessoschema.GeografiaRepository;
import cv.pn.apitransito.services.GeographyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeographyServiceImpl implements GeographyService {

	@Autowired
	private GeografiaRepository geographyRepository;


	@Override
	public List<Geografia> getGeografia(Integer level, String selfId) {

		List<Geografia> geography = geographyRepository.findGeographyByNivelAndAndSelfId(level, selfId);

		return geography;
	}

	@Override
	public Optional<Geografia> getNameById(String id) {

		return geographyRepository.findById(id);
	}

}
