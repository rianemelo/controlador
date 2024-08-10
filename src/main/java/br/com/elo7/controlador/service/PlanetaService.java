package br.com.elo7.controlador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.elo7.controlador.model.PlanetaEntity;
import br.com.elo7.controlador.repository.PlanetaRepository;

@Service
public class PlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;

	public PlanetaEntity salvarPlaneta(PlanetaEntity planeta) {
		return planetaRepository.save(planeta);
	}

	public List<PlanetaEntity> listarPlanetas() {
		return planetaRepository.findAll();
	}

	public Optional<PlanetaEntity> encontrarPlaneta(Long id) {
		return planetaRepository.findById(id);
	}

	public boolean detonarPlaneta(Long id) {
		Optional<PlanetaEntity> planeta = encontrarPlaneta(id);
		boolean existePlaneta = !planeta.isEmpty() ? true : false;
		if (!existePlaneta) {
			planetaRepository.deleteById(id);
		}
		return existePlaneta;
	}

}
