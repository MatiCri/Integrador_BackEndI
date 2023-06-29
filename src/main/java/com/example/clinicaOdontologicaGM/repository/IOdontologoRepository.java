package com.example.clinicaOdontologicaGM.repository;

import com.example.clinicaOdontologicaGM.entity.Odontologo;
import com.example.clinicaOdontologicaGM.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {

    @Query("SELECT t FROM Turno t WHERE t.odontologo.id = ?1")
    List<Turno> buscarTurnosPorOdontologo(Long id);


}
