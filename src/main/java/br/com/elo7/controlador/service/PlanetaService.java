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
		return planetaRepository.findAll(); //retorna [] se não houver registros
	}

	public Optional<Planeta> encontrarPlaneta(Long id) {
		return planetaRepository.findById(id); //retorna null se não houver registro com o dado id
	}

	public Planeta salvarPlaneta(Planeta planeta) {
		return planetaRepository.save(planeta);
	}

	public boolean detonarPlaneta(Long id) {
		if (!planetaRepository.existsById(id)) {
			return false;
		}
		planetaRepository.deleteById(id);
		return true;
	}

}
