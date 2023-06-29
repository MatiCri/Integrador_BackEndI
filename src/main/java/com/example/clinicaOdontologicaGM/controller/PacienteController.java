package com.example.clinicaOdontologicaGM.controller;

import com.example.clinicaOdontologicaGM.dto.PacienteDTO;
import com.example.clinicaOdontologicaGM.dto.TurnoDTO;
import com.example.clinicaOdontologicaGM.entity.Paciente;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import com.example.clinicaOdontologicaGM.service.IPacienteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final IPacienteService pacienteService;

    @Autowired
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> agregarPaciente(@Valid @RequestBody Paciente paciente) throws BadRequestException, MethodArgumentNotValidException {
        ResponseEntity<PacienteDTO> respuesta;
        PacienteDTO pacienteDTO = pacienteService.agregarPaciente(paciente);
        if(pacienteDTO!=null) respuesta = new ResponseEntity<>(pacienteDTO, null, HttpStatus.CREATED);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return respuesta;
    }

    @PutMapping
    public ResponseEntity<PacienteDTO> modificarPaciente(@Valid @RequestBody Paciente paciente) throws ResourceNotFoundException {
        ResponseEntity<PacienteDTO> respuesta;
        PacienteDTO pacienteDTO = pacienteService.modificarPaciente(paciente);
        if(pacienteDTO!=null) respuesta = new ResponseEntity<>(pacienteDTO, null, HttpStatus.CREATED);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return respuesta;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PacienteDTO> buscarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        ResponseEntity<PacienteDTO> respuesta;
        PacienteDTO pacienteDto = pacienteService.buscarPaciente(id);
        if (pacienteDto != null) respuesta = new ResponseEntity<>(pacienteDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }

    @GetMapping
    public Collection<PacienteDTO> listarPacientes(){
        return pacienteService.listarPacientes();
    }


    @GetMapping("/turnos/{id}")
    public List<TurnoDTO> listarTurnosPacientes(@PathVariable Long id){
        return pacienteService.buscarTurnosPorPaciente(id);
    }


}
