package co.itm.apifestivos.controllers;

import co.itm.apifestivos.services.FestivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/festivos")
@CrossOrigin(origins = "*")
public class FestivoController {
    
    @Autowired
    private FestivoService festivoService;
    
    @GetMapping("/verificar/{fecha}")
    public ResponseEntity<String> verificarFecha(@PathVariable String fecha) {
        String resultado = festivoService.validarFecha(fecha);
        return ResponseEntity.ok(resultado);
    }
}
