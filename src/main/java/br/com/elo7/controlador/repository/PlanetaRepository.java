package br.com.elo7.controlador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.elo7.controlador.model.PlanetaEntity;

@Repository
public interface PlanetaRepository extends JpaRepository<PlanetaEntity, Long> {

}
