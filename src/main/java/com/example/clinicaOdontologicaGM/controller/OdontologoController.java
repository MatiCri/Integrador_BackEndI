package com.example.clinicaOdontologicaGM.controller;

import com.example.clinicaOdontologicaGM.dto.OdontologoDTO;
import com.example.clinicaOdontologicaGM.dto.PacienteDTO;
import com.example.clinicaOdontologicaGM.entity.Odontologo;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import com.example.clinicaOdontologicaGM.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private final IOdontologoService odontologoService;

    @Autowired
    public OdontologoController(IOdontologoService odontologoService) {

        this.odontologoService = odontologoService;

    }

    @PostMapping
    public ResponseEntity<?> agregarOdontologo(@Valid  @RequestBody Odontologo odontologo) throws MethodArgumentNotValidException {
        odontologoService.agregarOdontologo(odontologo);
        return ResponseEntity.ok("Odontologo guardado con Exito");
    }

    @PutMapping
    public ResponseEntity<?> modificarOdontologo(@Valid @RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        odontologoService.modificarOdontologo(odontologo);
        return ResponseEntity.ok("Odontologo encontrado con Exito");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("Odontologo eliminado con Exito");
    }

    @GetMapping("{id}")
    public ResponseEntity<OdontologoDTO> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        ResponseEntity<OdontologoDTO> respuesta;
        OdontologoDTO odontologoDTO = odontologoService.buscarOdonotologo(id);
        if (odontologoDTO != null) respuesta = new ResponseEntity<>(odontologoDTO, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }

    @GetMapping
    public Collection<OdontologoDTO> listarOdontologos(){
        return odontologoService.listarOdontologos();
    }


}
