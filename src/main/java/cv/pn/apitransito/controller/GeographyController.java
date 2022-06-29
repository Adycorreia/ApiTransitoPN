package cv.pn.apitransito.controller;

import cv.pn.apitransito.model.acessoschema.Geografia;
import cv.pn.apitransito.services.GeographyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/geography")
public class GeographyController {

	@Autowired
	private GeographyService geographyServiceImpl;

	@GetMapping(value="/{nivel}/{self}")
    @ResponseStatus(code=HttpStatus.OK)
    public List<Geografia> getGeography(@PathVariable("nivel") Integer level, @PathVariable("self") String selfId) {

    	return geographyServiceImpl.getGeografia(level, selfId);
    }

}
