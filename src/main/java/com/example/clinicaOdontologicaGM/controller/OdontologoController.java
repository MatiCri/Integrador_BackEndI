package com.example.clinicaOdontologicaGM.controller;

import com.example.clinicaOdontologicaGM.dto.OdontologoDTO;
import com.example.clinicaOdontologicaGM.dto.PacienteDTO;
import com.example.clinicaOdontologicaGM.entity.Odontologo;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
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
    public ResponseEntity<OdontologoDTO> agregarOdontologo(@Valid  @RequestBody Odontologo odontologo) throws BadRequestException, MethodArgumentNotValidException {
        ResponseEntity<OdontologoDTO> respuesta;
        OdontologoDTO odontologoDTO = odontologoService.agregarOdontologo(odontologo);
        if(odontologoDTO!=null) respuesta = new ResponseEntity<>(odontologoDTO, null, HttpStatus.CREATED);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return respuesta;
    }

    @PutMapping
    public ResponseEntity<OdontologoDTO> modificarOdontologo(@Valid @RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        ResponseEntity<OdontologoDTO> respuesta;
        OdontologoDTO odontologoDTO = odontologoService.modificarOdontologo(odontologo);
        if(odontologoDTO!=null) respuesta = new ResponseEntity<>(odontologoDTO, null, HttpStatus.CREATED);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return respuesta;

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
