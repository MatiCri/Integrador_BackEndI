package com.example.clinicaOdontologicaGM.controller;


import com.example.clinicaOdontologicaGM.dto.OdontologoDTO;
import com.example.clinicaOdontologicaGM.dto.PacienteDTO;
import com.example.clinicaOdontologicaGM.dto.TurnoDTO;
import com.example.clinicaOdontologicaGM.entity.Turno;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import com.example.clinicaOdontologicaGM.service.IOdontologoService;
import com.example.clinicaOdontologicaGM.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private final ITurnoService turnoService;

    @Autowired
    public TurnoController (ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> agregarTurno(@Valid  @RequestBody Turno turno) throws ResourceNotFoundException, BadRequestException {
        TurnoDTO turnoDto = turnoService.agregarTurno(turno);
        return new ResponseEntity<>(turnoDto, null, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<TurnoDTO> modificarTurno(@Valid @RequestBody Turno turno) throws ResourceNotFoundException {
        TurnoDTO turnoDtoActualizado = turnoService.modificarTurno(turno);
        return new ResponseEntity<>(turnoDtoActualizado, null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TurnoDTO> buscarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        ResponseEntity<TurnoDTO> respuesta;
        TurnoDTO turnoDTO = turnoService.buscarTurno(id);
        if(turnoDTO != null){
            respuesta = new ResponseEntity<>(turnoDTO, null, HttpStatus.OK);
        }else{
            respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return respuesta;
    }

    @GetMapping
    public Collection<TurnoDTO> listarTurno(){
        return turnoService.listarTurnos();
    }

}
