package com.example.clinicaOdontologicaGM.repository;


import com.example.clinicaOdontologicaGM.entity.Domicilio;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDomicilioRepoitory extends JpaRepository<Domicilio, Long> {

}
