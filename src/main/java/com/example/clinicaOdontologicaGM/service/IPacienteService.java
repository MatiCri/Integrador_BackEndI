package com.example.clinicaOdontologicaGM.service;

import com.example.clinicaOdontologicaGM.dto.PacienteDTO;
import com.example.clinicaOdontologicaGM.dto.TurnoDTO;
import com.example.clinicaOdontologicaGM.entity.Paciente;
import com.example.clinicaOdontologicaGM.entity.Turno;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IPacienteService {

    PacienteDTO agregarPaciente(Paciente paciente) throws BadRequestException, MethodArgumentNotValidException;
    PacienteDTO modificarPaciente(Paciente paciente) throws ResourceNotFoundException;
    void eliminarPaciente(Long id) throws ResourceNotFoundException, BadRequestException;
    PacienteDTO buscarPaciente(Long id) throws ResourceNotFoundException;
    Set<PacienteDTO> listarPacientes();

    List<TurnoDTO> buscarTurnosPorPaciente(Long id);

}
