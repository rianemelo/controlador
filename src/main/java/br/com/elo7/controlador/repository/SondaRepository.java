package br.com.elo7.controlador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.elo7.controlador.model.SondaEntity;

@Repository
public interface SondaRepository extends JpaRepository<SondaEntity, Long> {

	SondaEntity findByPosicaoXAndPosicaoY(Integer posicaoX, Integer posicaoY);
}
