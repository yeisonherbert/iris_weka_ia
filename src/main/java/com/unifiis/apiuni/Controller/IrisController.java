package com.unifiis.apiuni.Controller;

import com.unifiis.apiuni.Beans.Iris;
import com.unifiis.apiuni.Service.IrisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Iris")
public class IrisController {
    private final IrisService apiService;
    @Autowired

    public IrisController(IrisService apiService) {
        this.apiService = apiService;
    }
    @RequestMapping(value = "/Consultar", method = RequestMethod.GET)
    public ResponseEntity<Object> Elemento(Iris iris) {
        String respuesta = apiService.consultar(iris);
        return new ResponseEntity<Object>(respuesta, HttpStatus.OK);
    }
}
