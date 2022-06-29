package cv.pn.apitransito.controller;

import cv.pn.apitransito.dtos.DomainDTO;
import cv.pn.apitransito.services.DomainService;
import cv.pn.apitransito.utilities.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/domain")
public class DomainController {

	@Autowired
	private DomainService domainService;


	@GetMapping(value = "/{dominio}")
    public ResponseEntity<Object> findDomain(@PathVariable("dominio") String dominio){

		APIResponse response = domainService.findDomain(dominio);

		return new ResponseEntity<Object>(response, HttpStatus.OK);

    }


	@PostMapping()
    public ResponseEntity<Object> insertDomain(@Valid @RequestBody DomainDTO dto){

		APIResponse response = domainService.insertDomain(dto);

		return new ResponseEntity<Object>(response, HttpStatus.CREATED);

    }


	@PutMapping(value = "/{idDominio}")
    public ResponseEntity<Object> updateDomain(@PathVariable("idDominio") String id, @Valid @RequestBody DomainDTO.DomainRequest dto){

		APIResponse response = domainService.updateDomain(dto, id);

		return new ResponseEntity<Object>(response, HttpStatus.OK);

    }

	@DeleteMapping(value = "/{idDominio}")
    public ResponseEntity<Object> swicthEstadoDomain(@PathVariable("idDominio") String idDominio){

		APIResponse response = domainService.swicthEstadoDomain(idDominio);

		return new ResponseEntity<Object>(response, HttpStatus.OK);

    }

}
