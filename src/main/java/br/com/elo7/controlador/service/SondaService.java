package br.com.elo7.controlador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.elo7.controlador.model.Planeta;
import br.com.elo7.controlador.model.Sonda;
import br.com.elo7.controlador.model.SondaM;
import br.com.elo7.controlador.repository.PlanetaRepository;
import br.com.elo7.controlador.repository.SondaRepository;

@Service
public class SondaService {

	@Autowired
	private SondaRepository sondaRepository;

	@Autowired
	private PlanetaRepository planetaRepository;

	public List<Sonda> listarSondas(Long planetaId) {
		return sondaRepository.findByPlanetaId(planetaId);
	}

	public Sonda encontrarSonda(Long planetaId, Long sondaId) {
		return sondaRepository.findByPlanetaIdAndId(planetaId, sondaId);
	}

	private Sonda encontrarSondaPelaPosicao(Long planetaId, Integer x, Integer y) {
		return sondaRepository.findByPlanetaIdAndPosicaoXAndPosicaoY(planetaId, x, y);
	}

	public boolean aterrissarSonda(Long planetaId, Sonda sonda) {
		Optional<Planeta> planeta = planetaRepository.findById(planetaId);
		if (planeta.isEmpty()) {
			return false;
		}
		if (encontrarSondaPelaPosicao(planetaId, sonda.getPosicaoX(), sonda.getPosicaoY()) != null) {
			return false;
		}

		sonda.setPlaneta(planeta.get());
		sondaRepository.save(sonda);
		return true;
	}

	public Sonda moverSonda(Long planetaId, Integer x, Integer y, String comando) {
		Sonda sonda = encontrarSondaPelaPosicao(planetaId, x, y);

		String[] comandos = comando.toUpperCase().split("");

		SondaM sondaM = new SondaM(x, y, sonda.getAngulo());
		for (int i = 0; i < comandos.length; i++) {
			sondaM.mover(comandos[i]);
		}

		sonda.setAngulo(sondaM.getTheta());
		sonda.setPosicaoX(sondaM.getX());
		sonda.setPosicaoY(sondaM.getY());
		return sondaRepository.save(sonda);
	}

	public String validarMovimento(Long planetaId, Integer x, Integer y, String comando) {
		Sonda sonda = encontrarSondaPelaPosicao(planetaId, x, y);
		if (sonda == null) {
			return "Sonda nao encontrada. Verifique se o planeta e a sonda estao no controlador";
		}

		SondaM sondaM = new SondaM(x, y, sonda.getAngulo());

		String[] comandos = comando.toUpperCase().split("");
		StringBuilder comandoPrevia = new StringBuilder();

		for (int i = 0; i < comandos.length; i++) {
			String z = comandos[i];
			if (!z.equals("L") && !z.equals("R") && !z.equals("M")) {
				return String.format("Comando %s nao e permitido!", comandos[i]);
			}

			sondaM.mover(comandos[i]);
			comandoPrevia.append(comandos[i]);
			if (sondaM.getX() >= 6 || sondaM.getY() >= 6 || sondaM.getX() <= -6 || sondaM.getY() <= -6) {
				return String.format("Sequencia de comandos %s mandam a sonda para o espaco, planeta 5x5!", comandoPrevia.toString());
			}
		}
		
		if (sondaRepository.existsByPosicaoXAndPosicaoY(sondaM.getX(), sondaM.getY())) {
			return "Risco de colisao, posicao ja esta ocupada por outra sonda.";
		}
		
		return null;
	}

	public boolean detonarSonda(Long planetaId, Integer x, Integer y) {
		Sonda sonda = encontrarSondaPelaPosicao(planetaId, x, y);
		boolean posicaoLivre = sonda == null ? true : false;
		if (!posicaoLivre) {
			sondaRepository.delete(sonda);
		}
		return posicaoLivre;
	}

}
