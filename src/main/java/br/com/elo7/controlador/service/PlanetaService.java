package br.com.elo7.controlador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.elo7.controlador.model.Planeta;
import br.com.elo7.controlador.repository.PlanetaRepository;

@Service
public class PlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;

	public List<Planeta> listarPlanetas() {
		return planetaRepository.findAll();
	}

	public Optional<Planeta> encontrarPlaneta(Long id) {
		return planetaRepository.findById(id);
	}

	public void salvarPlaneta(Planeta planeta) {
		if (planetaRepository.existsById(planeta.getId())) {
			throw new IllegalArgumentException("Planeta ja registrado no controlador, se quiser pode renomea-lo.");
		}
		planetaRepository.save(planeta);
	}

	public void detonarPlaneta(Long id) {
		if (!planetaRepository.existsById(id)) {
			throw new IllegalArgumentException("Detonacao abortada: planeta nao esta no controlador!");
		}
		planetaRepository.deleteById(id);
	}

}
