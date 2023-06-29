package com.example.clinicaOdontologicaGM.service;

import com.example.clinicaOdontologicaGM.dto.OdontologoDTO;
import com.example.clinicaOdontologicaGM.entity.Odontologo;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

public interface IOdontologoService {

    OdontologoDTO agregarOdontologo(Odontologo odontologo) throws MethodArgumentNotValidException;
    OdontologoDTO modificarOdontologo(Odontologo odontologo) throws ResourceNotFoundException;
    void eliminarOdontologo(Long id) throws ResourceNotFoundException;
    OdontologoDTO buscarOdonotologo(Long id) throws ResourceNotFoundException;
    Set<OdontologoDTO> listarOdontologos();

}
