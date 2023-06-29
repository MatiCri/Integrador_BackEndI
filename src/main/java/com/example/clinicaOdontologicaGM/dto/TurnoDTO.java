package com.example.clinicaOdontologicaGM.dto;

import com.example.clinicaOdontologicaGM.entity.Odontologo;
import com.example.clinicaOdontologicaGM.entity.Paciente;
import com.example.clinicaOdontologicaGM.entity.Turno;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoDTO {

    private Long id;

    private String paciente;

    private String odontologo;

    private LocalDateTime fechaTurno;

    public TurnoDTO() {
    }

    public TurnoDTO(Long id, String paciente, String odontologo, LocalDateTime fechaTurno) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaTurno = fechaTurno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(String odontologo) {
        this.odontologo = odontologo;
    }

    public LocalDateTime getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(LocalDateTime fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public static TurnoDTO fromTurno(Turno turno) {
        String paciente = turno.getPaciente().getNombre() + turno.getPaciente().getApellido();
        String odontologo = turno.getOdontologo().getNombre() + turno.getOdontologo().getApellido();
        return new TurnoDTO(turno.getId(), paciente, odontologo, turno.getFechaTurno());
    }
}
