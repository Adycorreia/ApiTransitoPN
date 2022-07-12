package cv.pn.apitransito.controller;


import cv.pn.apitransito.dtos.ArmamentoResponseDTO;
import cv.pn.apitransito.dtos.FeriasResponseDTO;
import cv.pn.apitransito.model.Ferias;
import cv.pn.apitransito.services.FeriasService;
import cv.pn.apitransito.utilities.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class FeriasEndpoint {

    @Autowired
    FeriasService feriasService;

    @GetMapping(path = "/listeFerias")
    public ResponseEntity<Object> ListFerias(){

        APIResponse response = feriasService.feriasAll();
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


    @GetMapping(path = "/listeFerias/id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> listByIdFeriaID(@PathVariable("id") Long id) {

        APIResponse response = feriasService.listById(id);
        return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);

    }

    @GetMapping(path = "/listeFerias/idferia/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> listByIdFeriaEfectivo(@PathVariable("id") Long id, FeriasResponseDTO feriasResponseDTO) {

        APIResponse response = feriasService.listByIdEfectivo(id, feriasResponseDTO);
        return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);

    }

    @PostMapping(path = "/listeFerias")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> insertFeria(@Valid @RequestBody FeriasResponseDTO feriasResponseDTO) {

        APIResponse response = feriasService.insertFerias(feriasResponseDTO);
        return new ResponseEntity<Object>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/listeFerias/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> deleteFeria(@PathVariable("id") Long id) {

        APIResponse response = feriasService.deleteFeria(id);
        return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);

    }

    @PutMapping(path = "/listeFerias/{id}")
    public  ResponseEntity<Object> updateFeria(@PathVariable("id") Long id, @Valid @RequestBody FeriasResponseDTO dto)  {

        APIResponse response = feriasService.updateFeriatId(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
