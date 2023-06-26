package com.example.clinicaOdontologicaGM.service;

import com.example.clinicaOdontologicaGM.dto.OdontologoDTO;
import com.example.clinicaOdontologicaGM.entity.Odontologo;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

public interface IOdontologoService {

    void agregarOdontologo(Odontologo odontologo) throws MethodArgumentNotValidException;
    void modificarOdontologo(Odontologo odontologo) throws ResourceNotFoundException;
    void eliminarOdontologo(Long id) throws ResourceNotFoundException;
    OdontologoDTO buscarOdonotologo(Long id) throws ResourceNotFoundException;
    Set<OdontologoDTO> listarOdontologos();

}
