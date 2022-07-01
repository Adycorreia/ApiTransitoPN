package cv.pn.apitransito.controller;

import cv.pn.apitransito.dtos.ArmamentoResponseDTO;
import cv.pn.apitransito.dtos.EfectivosResponseDTO;
import cv.pn.apitransito.dtos.InfracaoResponseDTO;
import cv.pn.apitransito.services.ArmamentoService;
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
public class ArmamentoEndpoint {

    @Autowired
    ArmamentoService armamentoService;

    @GetMapping(path = "/listeArmament")
    public ResponseEntity<Object> ListArmamento(){

        APIResponse response = armamentoService.armamentoAll();
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/listeArmament/id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> listById(@PathVariable("id") Long id) {

        APIResponse response = armamentoService.listById(id);
        return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);

    }

    @PostMapping(path = "/listeArmament")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> insertArmamento(@Valid @RequestBody ArmamentoResponseDTO armamentoResponseDTO) {

        APIResponse response = armamentoService.insertArmamentoAll(armamentoResponseDTO);
        return new ResponseEntity<Object>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/listeArmament/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> deleteArm(@PathVariable("id") Long id) {

        APIResponse response = armamentoService.deleteArma(id);
        return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);

    }

    @PutMapping(path = "/listeArmament/{id}")
    public  ResponseEntity<Object> updateArmId(@PathVariable("id") Long id, @Valid @RequestBody ArmamentoResponseDTO dto)  {

        APIResponse response = armamentoService.updateArmaId(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
