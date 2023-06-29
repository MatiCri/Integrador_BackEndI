package com.example.clinicaOdontologicaGM.service.impl;

import com.example.clinicaOdontologicaGM.dto.DomicilioDTO;
import com.example.clinicaOdontologicaGM.dto.OdontologoDTO;
import com.example.clinicaOdontologicaGM.dto.PacienteDTO;
import com.example.clinicaOdontologicaGM.dto.TurnoDTO;
import com.example.clinicaOdontologicaGM.entity.Domicilio;
import com.example.clinicaOdontologicaGM.entity.Odontologo;
import com.example.clinicaOdontologicaGM.entity.Paciente;
import com.example.clinicaOdontologicaGM.entity.Turno;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import com.example.clinicaOdontologicaGM.repository.IDomicilioRepoitory;
import com.example.clinicaOdontologicaGM.repository.IOdontologoRepository;
import com.example.clinicaOdontologicaGM.repository.IPacienteRepository;
import com.example.clinicaOdontologicaGM.service.IPacienteService;
import com.example.clinicaOdontologicaGM.utils.JsonPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PacienteService implements IPacienteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final IPacienteRepository pacienteRepository;

    private final IDomicilioRepoitory domicilioRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PacienteService(IPacienteRepository pacienteRepository, IDomicilioRepoitory domicilioRepository, ObjectMapper objectMapper) {
        this.pacienteRepository = pacienteRepository;
        this.domicilioRepository = domicilioRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public PacienteDTO agregarPaciente(Paciente paciente) throws BadRequestException, MethodArgumentNotValidException {

            Paciente pacienteNuevo = pacienteRepository.save(paciente);
            DomicilioDTO domicilioDto = objectMapper.convertValue(pacienteNuevo.getDomicilio(), DomicilioDTO.class);
            PacienteDTO pacienteDtoNuevo = objectMapper.convertValue(pacienteNuevo, PacienteDTO.class);
            pacienteDtoNuevo.setDomicilioDTO(domicilioDto);

            if(pacienteDtoNuevo!=null){
                LOGGER.info("Nuevo paciente registrado con exito: {}", JsonPrinter.toString(pacienteDtoNuevo));
            }else{
                LOGGER.info("Nuevo paciente NO registrado con exito: {}", JsonPrinter.toString(pacienteDtoNuevo));
                throw new BadRequestException("No se pudo agregar el paciente, por favor revise");
            }

            return pacienteDtoNuevo;

    }

    @Override
    public PacienteDTO modificarPaciente(Paciente paciente) throws ResourceNotFoundException {
        Paciente pacienteAActualizar = pacienteRepository.findById(paciente.getId()).orElse(null);
        PacienteDTO pacienteDTOActualizado = null;

        if(pacienteAActualizar != null){
            pacienteAActualizar = paciente;
            pacienteRepository.save(pacienteAActualizar);
            pacienteDTOActualizado = objectMapper.convertValue(pacienteAActualizar, PacienteDTO.class);
            DomicilioDTO domicilioDto = objectMapper.convertValue(pacienteAActualizar.getDomicilio(), DomicilioDTO.class);
            pacienteDTOActualizado.setDomicilioDTO(domicilioDto);
            LOGGER.info("Paciente modificado con exito: {}", pacienteAActualizar);
        }else{
            LOGGER.info("No fue posible actualizar, ya que no existe el paciente");
            throw new ResourceNotFoundException("No fue posible actualizar.");
        }

        return pacienteDTOActualizado;

    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException{
        if(buscarPaciente(id) != null){
            pacienteRepository.deleteById(id);
            LOGGER.info("Pacientee eliminado con exito");
        }else{
            LOGGER.error("Pacientee NO eliminado con exito");
            throw new ResourceNotFoundException("No fue posible eliminar.");
        }

    }

    @Override
    public PacienteDTO buscarPaciente(Long id) throws ResourceNotFoundException {
        Paciente pacienteEncontrado = pacienteRepository.findById(id).orElse(null);
        PacienteDTO pacienteDTO = null;

        if(pacienteEncontrado != null){
            pacienteDTO = objectMapper.convertValue(pacienteEncontrado, PacienteDTO.class);
            Domicilio domicilio = pacienteEncontrado.getDomicilio();
            DomicilioDTO domicilioDTO = objectMapper.convertValue(domicilio, DomicilioDTO.class);
            pacienteDTO.setDomicilioDTO(domicilioDTO);
        }else {
            LOGGER.error("Paciente NO encontradp con exito");
            throw new ResourceNotFoundException("No fue posible encontrar al paciente");
        }

        return pacienteDTO;

    }

    @Override
    public Set<PacienteDTO> listarPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();

        Set<PacienteDTO> pacientesDTO = new HashSet<>();

        for(Paciente p : pacientes){
            PacienteDTO pacienteDTO = objectMapper.convertValue(p, PacienteDTO.class);
            DomicilioDTO domicilioDTO = objectMapper.convertValue(p.getDomicilio(), DomicilioDTO.class);
            pacienteDTO.setDomicilioDTO(domicilioDTO);
            pacientesDTO.add(pacienteDTO);
        }

        return pacientesDTO;
    }

    @Override
    public List<TurnoDTO> buscarTurnosPorPaciente(Long id) {

        List<Turno> turnos = pacienteRepository.buscarTurnosPorPaciente(id);
        List<TurnoDTO> turnosDTO = turnos.stream()
                .map(TurnoDTO::fromTurno)
                .toList();

        return turnosDTO;

    }

}
