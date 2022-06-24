package cv.pn.apitransito.controller;

import cv.pn.apitransito.dtos.EfectivosResponseDTO;
import cv.pn.apitransito.dtos.InfracaoResponseDTO;
import cv.pn.apitransito.services.EfectivosService;
import cv.pn.apitransito.services.InfracaoService;
import cv.pn.apitransito.utilities.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class InfracaoEndpoint {


    @Autowired
    InfracaoService infracaoService;

    @GetMapping(path = "/listInfrac")
    public ResponseEntity<Object> ListInfração(){

        APIResponse response = infracaoService.infracaoAll();
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/listInfrac")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> insertInfracao(@Valid @RequestBody InfracaoResponseDTO infracaoResponseDTO) {

        APIResponse response = infracaoService.insertInfracao(infracaoResponseDTO);
        return new ResponseEntity<Object>(response, HttpStatus.CREATED);
    }


}
