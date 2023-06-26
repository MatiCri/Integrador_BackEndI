package com.example.clinicaOdontologicaGM.service.impl;

import com.example.clinicaOdontologicaGM.dto.OdontologoDTO;
import com.example.clinicaOdontologicaGM.dto.PacienteDTO;
import com.example.clinicaOdontologicaGM.dto.TurnoDTO;
import com.example.clinicaOdontologicaGM.entity.Turno;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import com.example.clinicaOdontologicaGM.repository.ITurnoRepository;
import com.example.clinicaOdontologicaGM.service.ITurnoService;
import com.example.clinicaOdontologicaGM.utils.JsonPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final ITurnoRepository turnoRepository;

    private final OdontologoService odontologoService;

    private final PacienteService pacienteService;

    private final ObjectMapper objectMapper;

    @Autowired
    public TurnoService(ITurnoRepository turnoRepository, OdontologoService odontologoService, PacienteService pacienteService, ObjectMapper objectMapper) {
        this.turnoRepository = turnoRepository;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void agregarTurno(Turno turno) throws BadRequestException, ResourceNotFoundException {

        PacienteDTO paciente = pacienteService.buscarPaciente(turno.getPaciente().getId());
        OdontologoDTO odontologo = odontologoService.buscarOdonotologo(turno.getOdontologo().getId());

        turnoRepository.save(turno);
        LOGGER.info("Nuevo turno registrado con exito: {}", JsonPrinter.toString(turno));
    }

    @Override
    public void modificarTurno(Turno turno) throws ResourceNotFoundException{

        Turno turnoAActualizar = turnoRepository.findById(turno.getId()).orElse(null);

        if(turnoAActualizar != null){
            turnoAActualizar = turno;
            turnoRepository.save(turnoAActualizar);
            LOGGER.info("Turno modificado con exito: {}", turnoAActualizar);
        }else{
            LOGGER.info("No fue posible actualizar, ya que no existe el turno");
            throw new ResourceNotFoundException("No se ha encontrado el turno");
        }

    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if(buscarTurno(id) != null){
            turnoRepository.deleteById(id);
            LOGGER.info("Turno eliminado con exito");
        }else{
            LOGGER.error("No se ha eliminado el turno");
            throw new ResourceNotFoundException("No se ha encontrado el turno");
        }

    }

    @Override
    public TurnoDTO buscarTurno(Long id) throws ResourceNotFoundException{
        Turno turnoEncontrado = turnoRepository.findById(id).orElse(null);
        TurnoDTO turnoDTO = null;
        if(turnoEncontrado != null){
            turnoDTO = objectMapper.convertValue(turnoEncontrado, TurnoDTO.class);
            LOGGER.info("Turno encontrado con exito");
        }else{
            LOGGER.error("No se ha encontrado el turno");
            throw new ResourceNotFoundException("No se ha encontrado el turno");
        }


        return turnoDTO;

    }

    @Override
    public List<TurnoDTO> listarTurnos() {

        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDTO> turnosDTO = turnos.stream()
                .map(TurnoDTO::fromTurno)
                .toList();

        return turnosDTO;


    }
}
