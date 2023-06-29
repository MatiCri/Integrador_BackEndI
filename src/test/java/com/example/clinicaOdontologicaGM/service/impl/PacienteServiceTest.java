package com.example.clinicaOdontologicaGM.service.impl;

import com.example.clinicaOdontologicaGM.dto.PacienteDTO;
import com.example.clinicaOdontologicaGM.entity.Domicilio;
import com.example.clinicaOdontologicaGM.entity.Paciente;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacienteServiceTest {

    @Autowired
    PacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaAgregarUnPaciente() throws BadRequestException, MethodArgumentNotValidException {
        Domicilio domicilioAAgregar = new Domicilio("Calle aasdfasdfasdfasdf", 2032, "CABA", "Buenos Aires");
        LocalDate fechaAAgregar = LocalDate.of(2023, 7, 1);
        Paciente pacienteAAgregar = new Paciente("Carlos", "Sánchez", "32234432", fechaAAgregar, domicilioAAgregar);

        PacienteDTO pacienteDTO = pacienteService.agregarPaciente(pacienteAAgregar);

        Assertions.assertNotNull(pacienteDTO);
        Assertions.assertNotNull(pacienteDTO.getId());
    }

    @Test
    @Order(2)
    void cuandoNoSeCumpleElFormatoDeCaracteresNumericosDeDni_noDeberiaAgregarPaciente(){
        Domicilio domicilioAAgregar = new Domicilio("Calle aasdfasdfasdfasdf", 2032, "CABA", "Buenos Aires");
        LocalDate fechaAAgregar = LocalDate.of(2023, 7, 1);
        Paciente pacienteAAgregar = new Paciente("Carlos", "Sánchez", "32234432aa", fechaAAgregar, domicilioAAgregar);

        Assertions.assertThrows(ConstraintViolationException.class, () -> pacienteService.agregarPaciente(pacienteAAgregar));
    }

    @Test
    @Order(3)
    void deberiaListarUnSoloPaciente(){
        Set<PacienteDTO> pacienteDTOS = pacienteService.listarPacientes();
        assertEquals(1, pacienteDTOS.size());
    }

    @Test
    @Order(4)
    void deberiaEliminarElPacienteId1() throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(1L);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> pacienteService. eliminarPaciente(1L));
    }

}