package com.example.clinicaOdontologicaGM.dto;

import com.example.clinicaOdontologicaGM.entity.Domicilio;
import com.example.clinicaOdontologicaGM.entity.Turno;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private String dni;

    private LocalDate fechaIngreso;

    private DomicilioDTO domicilioDTO;


    public PacienteDTO() {
    }

    public PacienteDTO(Long id, String nombre, String apellido, String dni, LocalDate fechaIngreso, DomicilioDTO domicilioDTO) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilioDTO = domicilioDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public DomicilioDTO getDomicilioDTO() {
        return domicilioDTO;
    }

    public void setDomicilioDTO(DomicilioDTO domicilioDTO) {
        this.domicilioDTO = domicilioDTO;
    }


}
