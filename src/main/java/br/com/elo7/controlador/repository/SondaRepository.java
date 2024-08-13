package br.com.elo7.controlador.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.elo7.controlador.model.Sonda;

@Repository
public interface SondaRepository extends JpaRepository<Sonda, Long> {

	List<Sonda> findByPlanetaId(@Param(value = "planetaId") Long planetaId);

	Sonda findByPlanetaIdAndId(@Param(value = "planetaId") Long planetaId, @Param(value = "sondaId") Long sondaId);

	Optional<Sonda> findByPlanetaIdAndPosicaoXAndPosicaoY(@Param(value = "planetaId") Long planetaId,
			@Param(value = "posicaoX") Integer posicaoX, @Param(value = "posicaoY") Integer posicaoY);

	boolean existsByPlanetaIdAndPosicaoXAndPosicaoY(@Param(value = "planetaId") Long planetaId,
			@Param(value = "posicaoX") Integer posicaoX, @Param(value = "posicaoY") Integer posicaoY);
}