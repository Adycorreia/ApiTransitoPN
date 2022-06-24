package cv.pn.apitransito.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import cv.pn.apitransito.dtos.DocumentsResponseDTO;
import cv.pn.apitransito.services.DocumentsService;

import cv.pn.apitransito.utilities.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class DocumentsEndpoint {


    @Autowired
    DocumentsService documentsService;

    @GetMapping(path = "/listipoDoc")
    public ResponseEntity<Object> ListDocuments(){

        APIResponse response = documentsService.documentsAll();
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/listipoDoc/{tipodoc}")
    public ResponseEntity<Object> findDoc(@PathVariable("tipodoc") String tripod){

        APIResponse response = documentsService.findTripod(tripod);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/listipoDoc/id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> listById(@PathVariable("id") Long id) {

        APIResponse response = documentsService.listById(id);
        return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);

    }

    @PostMapping(path = "/listipoDoc")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> insertDocuments(@Valid @RequestBody DocumentsResponseDTO documentsResponseDTO) {

        APIResponse response = documentsService.insertDocuments(documentsResponseDTO);
        return new ResponseEntity<Object>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/listipoDoc/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> deleteDoc(@PathVariable("id") Long id) {

        APIResponse response = documentsService.deletedoc(id);
        return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);

    }

    @PutMapping(path = "/listipoDoc/{id}")
    public  ResponseEntity<Object> updateDocId(@PathVariable("id") Long id, @Valid @RequestBody DocumentsResponseDTO dto)  {

        APIResponse response = documentsService.updateDocId(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

/*
@RequestMapping(method = RequestMethod.GET,path = "/list")
public List<Documents>  listAll(){
    return asList(
            new Documents(01,
                    "ST-06-NG",
                    "",
                    "",
                    "Adilson dos Santos Correia",
                    "Falta de Seguros",
                    "DGTR",
                    "Nº704/22",
                    2016-9-20,
                    2016-10-20,
                    "Titulo e Livrete",
                    "Sim",
                    ""),
            new Documents(02,
                    "ST-48-wr",
                    "",
                    "",
                    "Carlos dos Semedo Varela",
                    "Violação de Sinal Vertical",
                    "DGTR",
                    "Nº705/22",
                    2022-12-15,
                    2022-12-15, "Carta de Codução", "", ""),
           new Documents(03,
                "ST-55-TR",
                "",
                "",
                "Jose Manuel Semedo",
                "Falta de Seguros",
                "DGTR",
                "Nº706/22",
                2022-12-15,
                2022-12-15, "Titulo e Livrete", "Sim", ""),
            new Documents(04,
                        "ST-65-GR",
                        "",
                        "",
                        "Helmer Oliveira Correia",
                        "Violação de Sinal Vertical",
                        "DGTR",
                        "Nº707/22",
                        2022-12-15,
                        2022-12-15, "Carta de Codução", "Não", ""));
    }

    {
  "matricula": "ST-25-TR",
  "condutor": "aDILSON cORREIA",
  "n_carta": "STETS",
  "proprietario": "cORREIA",
  "motivo": "fALTA DE sEGURO",
  "n_oficio": "TEST",
  "destino": "dgtr",
  "data_apreensao": "tESTE",
  "data_entrega": "TESTE",
  "tipodoc": "CARTA",
  "v_apreendido": "SIM",
  "obs": "string"

}
*/


}
