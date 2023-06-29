package com.example.clinicaOdontologicaGM.service.impl;

import com.example.clinicaOdontologicaGM.dto.OdontologoDTO;
import com.example.clinicaOdontologicaGM.dto.PacienteDTO;
import com.example.clinicaOdontologicaGM.dto.TurnoDTO;
import com.example.clinicaOdontologicaGM.entity.Domicilio;
import com.example.clinicaOdontologicaGM.entity.Odontologo;
import com.example.clinicaOdontologicaGM.entity.Paciente;
import com.example.clinicaOdontologicaGM.entity.Turno;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServiceTest {

    @Autowired
    TurnoService turnoService;
    @Autowired
    OdontologoService odontologoService;
    @Autowired
    PacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaAgregarUnTurno() throws BadRequestException, MethodArgumentNotValidException, ResourceNotFoundException {
        Odontologo odontologoAAgregar = new Odontologo("MA-223321321321", "Roberto", "Tenerds");
        OdontologoDTO odontologoDTO = odontologoService.agregarOdontologo(odontologoAAgregar);

        Paciente pacienteAAgregar = new Paciente("Carlos", "Sánchez", "32234432", LocalDate.of(2023, 7, 1), new Domicilio("Calle aasdfasdfasdfasdf", 2032, "CABA", "Buenos Aires"));
        PacienteDTO pacienteDTO = pacienteService.agregarPaciente(pacienteAAgregar);

        Turno turnoAAgregar = new Turno(pacienteAAgregar, odontologoAAgregar, LocalDateTime.of(2040,8,2,12,0));

        TurnoDTO turnoDTO = turnoService.agregarTurno(turnoAAgregar);

        Assertions.assertNotNull(turnoDTO);
        Assertions.assertNotNull(turnoDTO.getId());
    }

    @Test
    @Order(2)
    void deberiaListarUnSoloTurno(){
        List<TurnoDTO> turnoDTOS = turnoService.listarTurnos();
        assertEquals(1, turnoDTOS.size());
    }

    @Test
    @Order(3)
    void deberiaEliminarElTurnoId1() throws ResourceNotFoundException, BadRequestException {
        turnoService.eliminarTurno(1L);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> turnoService. eliminarTurno(1L));
    }

}