package com.example.clinicaOdontologicaGM.service.impl;

import com.example.clinicaOdontologicaGM.dto.OdontologoDTO;
import com.example.clinicaOdontologicaGM.entity.Odontologo;
import com.example.clinicaOdontologicaGM.exceptions.BadRequestException;
import com.example.clinicaOdontologicaGM.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoServiceTest {

    @Autowired
    OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaAgregarUnOdontologo() throws BadRequestException, MethodArgumentNotValidException {
        Odontologo odontologoAAgregar = new Odontologo("MA-223321321321", "Roberto", "Tenerds");

        OdontologoDTO odontologoDTO = odontologoService.agregarOdontologo(odontologoAAgregar);

        Assertions.assertNotNull(odontologoDTO);
        Assertions.assertNotNull(odontologoDTO.getId());
    }

    @Test
    @Order(2)
    void cuandoNoSeCumpleLaCantidadMaximaDeCaracteresDeMatricula_noDeberiaAgregarOdontologo(){
        Odontologo odontologoAAgregar = new Odontologo("MA-2233", "Roberto", "Tenerds");

        Assertions.assertThrows(ConstraintViolationException.class, () -> odontologoService.agregarOdontologo(odontologoAAgregar));
    }

    @Test
    @Order(3)
    void deberiaListarUnSoloOdontologo(){
        Set<OdontologoDTO> odontologoDTOS = odontologoService.listarOdontologos();
        assertEquals(1, odontologoDTOS.size());
    }

    @Test
    @Order(4)
    void deberiaEliminarElOdontologoId1() throws ResourceNotFoundException, BadRequestException {
        odontologoService.eliminarOdontologo(1L);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> odontologoService. eliminarOdontologo(1L));
    }

}