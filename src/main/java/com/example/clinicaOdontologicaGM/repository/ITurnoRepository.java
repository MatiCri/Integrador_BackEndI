package com.example.clinicaOdontologicaGM.repository;

import com.example.clinicaOdontologicaGM.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long> {
}
