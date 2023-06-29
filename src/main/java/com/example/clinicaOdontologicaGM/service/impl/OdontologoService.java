package com.example.clinicaOdontologicaGM.service.impl;

import com.example.clinicaOdontologicaGM.dto.OdontologoDTO;
import com.example.clinicaOdontologicaGM.dto.TurnoDTO;
import com.example.clinicaOdontologicaGM.entity.Odontologo;
import com.example.clinicaOdontologicaGM.entity.Turno;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import com.example.clinicaOdontologicaGM.repository.IOdontologoRepository;
import com.example.clinicaOdontologicaGM.service.IOdontologoService;
import com.example.clinicaOdontologicaGM.utils.JsonPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OdontologoService implements IOdontologoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final IOdontologoRepository odontologoRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public OdontologoService(IOdontologoRepository odontologoRepository, ObjectMapper objectMapper) {
        this.odontologoRepository = odontologoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public OdontologoDTO agregarOdontologo(Odontologo odontologo)  {

        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
        OdontologoDTO odontologoDTO = objectMapper.convertValue(odontologoGuardado, OdontologoDTO.class);
        LOGGER.info("Nuevo odontologo registrado con exito: {}", JsonPrinter.toString(odontologoGuardado));

        return odontologoDTO;

    }

    @Override
    public OdontologoDTO modificarOdontologo(Odontologo odontologo) throws ResourceNotFoundException {
        Odontologo odontologoAActualizar = odontologoRepository.findById(odontologo.getId()).orElse(null);
        OdontologoDTO odontologoDTO = null;

        if(odontologoAActualizar != null){
            odontologoAActualizar = odontologo;
            odontologoRepository.save(odontologoAActualizar);
            odontologoDTO = objectMapper.convertValue(odontologoAActualizar, OdontologoDTO.class);
            LOGGER.info("Odontologo modificado con exito: {}", odontologoAActualizar);
        }else{
            LOGGER.info("No fue posible actualizar, ya que no existe el odontologo");
            throw new ResourceNotFoundException("No se ha encontrado el Odontologo");
        }

        return odontologoDTO;
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException, BadRequestException {
        if(!buscarTurnosPorOdontologo(id).isEmpty() ) {
            LOGGER.error("Pacientee NO eliminado con exito");
            throw new BadRequestException("No fue posible eliminar ya que tiene turnos asignados");
        }if(buscarOdonotologo(id) != null){
            odontologoRepository.deleteById(id);
            LOGGER.info("Odontologo eliminado con exito");
        }else{
            LOGGER.info("No se ha encontrado el Odontologo con id: " + id);
            throw new ResourceNotFoundException("No se ha encontrado el Odontologo");
        }
    }

    @Override
    public OdontologoDTO buscarOdonotologo(Long id) throws ResourceNotFoundException {
        Odontologo odontologoEncontrado = odontologoRepository.findById(id).orElse(null);
        OdontologoDTO odontologoDTO = null;

        if(odontologoEncontrado != null){
            odontologoDTO = objectMapper.convertValue(odontologoEncontrado, OdontologoDTO.class);
            LOGGER.info("Odontologo encontrado con exito {}", JsonPrinter.toString(odontologoEncontrado));
        }else{
            LOGGER.info("Odontologo no ha sido encontrado con exito");
            throw new ResourceNotFoundException("No se ha encontrado el Odontologo");
        }

        return odontologoDTO;
    }

    @Override
    public Set<OdontologoDTO> listarOdontologos() {
        List<Odontologo> odontologos = odontologoRepository.findAll();

        Set<OdontologoDTO> odontologosDTO = new HashSet<>();

        for(Odontologo o : odontologos){
            odontologosDTO.add(objectMapper.convertValue(o, OdontologoDTO.class));
        }

        return odontologosDTO;
    }

    @Override
    public List<TurnoDTO> buscarTurnosPorOdontologo(Long id) {

        List<Turno> turnos = odontologoRepository.buscarTurnosPorOdontologo(id);
        List<TurnoDTO> turnosDTO = turnos.stream()
                .map(TurnoDTO::fromTurno)
                .toList();

        return turnosDTO;

    }
}