package com.example.clinicaOdontologicaGM.repository;

import com.example.clinicaOdontologicaGM.entity.Paciente;
import com.example.clinicaOdontologicaGM.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT t FROM Turno t WHERE t.paciente.id = ?1")
    List<Turno> buscarTurnosPorPaciente(Long id);

}
