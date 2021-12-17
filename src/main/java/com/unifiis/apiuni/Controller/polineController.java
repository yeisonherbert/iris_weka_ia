package com.unifiis.apiuni.Controller;

import com.unifiis.apiuni.Beans.Iris;
import com.unifiis.apiuni.Beans.Polineuropatia;
import com.unifiis.apiuni.Beans.Resultado;
import com.unifiis.apiuni.Service.IrisService;
import com.unifiis.apiuni.Service.polineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Polineuropatia")
public class polineController {
    private final polineService apiService;
    @Autowired

    public polineController(polineService apiService) {
        this.apiService = apiService;
    }
    @RequestMapping(value = "/Consultar", method = RequestMethod.POST)
    public ResponseEntity<Object> Elemento(@RequestBody Polineuropatia param) {
        Resultado rpta = new Resultado();
        String respuesta = apiService.consultar(param);
        rpta.setEstado(200);
        rpta.setValor(respuesta);
        return new ResponseEntity<Object>(rpta, HttpStatus.OK);
    }
}
