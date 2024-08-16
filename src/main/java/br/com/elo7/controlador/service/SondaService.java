package br.com.elo7.controlador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.elo7.controlador.dto.MoverSondaRequestDTO;
import br.com.elo7.controlador.model.Planeta;
import br.com.elo7.controlador.model.Sonda;
import br.com.elo7.controlador.repository.PlanetaRepository;
import br.com.elo7.controlador.repository.SondaRepository;

@Service
public class SondaService {

	@Autowired
	private SondaRepository sondaRepository;

	@Autowired
	private PlanetaRepository planetaRepository;

	@Autowired
	private SondaMovimento sondaMovimento;

	public List<Sonda> listarSondas(Long planetaId) {
		return sondaRepository.findByPlanetaId(planetaId);
	}

	public Sonda encontrarSonda(Long planetaId, Long sondaId) {
		return sondaRepository.findByPlanetaIdAndId(planetaId, sondaId);
	}

	private Sonda encontrarSondaPelaPosicao(Long planetaId, Integer x, Integer y) {
		return sondaRepository.findByPlanetaIdAndPosicaoXAndPosicaoY(planetaId, x, y)
				.orElseThrow(() -> new IllegalArgumentException(
						"Sonda nao encontrada. Verifique se o planeta e a sonda estao no controlador."));
	}

	public void aterrissarSonda(Long planetaId, Sonda sonda) {
		Planeta planeta = planetaRepository.findById(planetaId)
				.orElseThrow(() -> new IllegalArgumentException("Planeta nao esta no controlador!"));

		if (sondaRepository.existsByPlanetaIdAndPosicaoXAndPosicaoY(planetaId, sonda.getPosicaoX(),
				sonda.getPosicaoY())) {
			throw new IllegalArgumentException(
					"Aterrissagem abortada! Risco de colisao: posicao ja ocupada por outra sonda!");
		}

		sonda.setPlaneta(planeta);
		sondaRepository.save(sonda);
	}

	public Sonda moverSonda(MoverSondaRequestDTO request) {
		if (!planetaRepository.existsById(request.getPlanetaId())) {
			throw new IllegalArgumentException("Comando cancelado: planeta nao esta no controlador.");
		}

		Sonda sonda = encontrarSondaPelaPosicao(request.getPlanetaId(), request.getPosicaoX(), request.getPosicaoY());

		Sonda sondaMovida = sondaMovimento.mover(sonda, request.getComandos());

		if (sondaRepository.existsByPlanetaIdAndPosicaoXAndPosicaoY(request.getPlanetaId(), sondaMovida.getPosicaoX(),
				sondaMovida.getPosicaoY())) {
			throw new IllegalArgumentException(
					"Comando cancelado. Risco de colisao: posicao ja ocupada por outra sonda!");
		}

		return sondaRepository.save(sondaMovida);
	}

	public void detonarSonda(Long planetaId, Integer x, Integer y) {
		Sonda sonda = encontrarSondaPelaPosicao(planetaId, x, y);
		sondaRepository.delete(sonda);
	}

}
