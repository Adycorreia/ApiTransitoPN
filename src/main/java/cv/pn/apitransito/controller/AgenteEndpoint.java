package cv.pn.apitransito.controller;

import cv.pn.apitransito.dtos.DocumentsResponseDTO;
import cv.pn.apitransito.dtos.EfectivosResponseDTO;
import cv.pn.apitransito.services.DocumentsService;
import cv.pn.apitransito.services.EfectivosService;
import cv.pn.apitransito.utilities.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class AgenteEndpoint {


    @Autowired
    EfectivosService efectivosService;

    @GetMapping(path = "/listefect")
    public ResponseEntity<Object> ListEfectivos(){

        APIResponse response = efectivosService.efectivosAll();
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/listefect/id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> listById(@PathVariable("id") Long id) {

        APIResponse response = efectivosService.listById(id);
        return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);

    }

    @PostMapping(path = "/listefect")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> insertEfectivos(@Valid @RequestBody EfectivosResponseDTO efectivosResponseDTO) {

        APIResponse response = efectivosService.insertEfectivos(efectivosResponseDTO);
        return new ResponseEntity<Object>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/listefect/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> deleteDoc(@PathVariable("id") Long id) {

        APIResponse response = efectivosService.deleteefect(id);
        return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);

    }

    @PutMapping(path = "/listefect/{id}")
    public  ResponseEntity<Object> updateDocId(@PathVariable("id") Long id, @Valid @RequestBody EfectivosResponseDTO dto)  {

        APIResponse response = efectivosService.updateEfectId(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
