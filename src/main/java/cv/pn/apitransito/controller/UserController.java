package cv.pn.apitransito.controller;


import cv.pn.apitransito.services.ArmamentoService;
import cv.pn.apitransito.services.UserService;
import cv.pn.apitransito.utilities.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<Object> users() {

        APIResponse response = userService.allUser();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
