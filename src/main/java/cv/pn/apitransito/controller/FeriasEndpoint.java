package cv.pn.apitransito.controller;


import cv.pn.apitransito.services.FeriasService;
import cv.pn.apitransito.utilities.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
