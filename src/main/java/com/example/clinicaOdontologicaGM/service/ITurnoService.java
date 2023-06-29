package com.example.clinicaOdontologicaGM.service;

import com.example.clinicaOdontologicaGM.dto.TurnoDTO;
import com.example.clinicaOdontologicaGM.entity.Turno;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ITurnoService {

    TurnoDTO agregarTurno(Turno turno) throws ResourceNotFoundException, BadRequestException;
    TurnoDTO modificarTurno(Turno turno) throws ResourceNotFoundException;
    void eliminarTurno(Long id) throws ResourceNotFoundException;
    TurnoDTO buscarTurno(Long id) throws ResourceNotFoundException;
    List<TurnoDTO> listarTurnos();


}
