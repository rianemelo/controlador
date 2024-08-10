package br.com.elo7.controlador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.elo7.controlador.model.Sonda;
import br.com.elo7.controlador.model.SondaEntity;
import br.com.elo7.controlador.repository.SondaRepository;

@Service
public class SondaService {

	@Autowired
	private SondaRepository sondaRepository;

	public boolean aterrissarSonda(SondaEntity sonda) {
		boolean posicaoLivre = encontrarSondaPelaPosicao(sonda.getPosicaoX(), sonda.getPosicaoY()) == null ? true : false;
		if (posicaoLivre) {
			sondaRepository.save(sonda);
		}
		return posicaoLivre;
	}

	public List<SondaEntity> listarSondas() {
		return sondaRepository.findAll();
	}

	public SondaEntity encontrarSondaPelaPosicao(Integer x, Integer y) {
		return sondaRepository.findByPosicaoXAndPosicaoY(x, y);
	}

	public SondaEntity moverSonda(Integer x, Integer y, String comando) {
		SondaEntity entity = encontrarSondaPelaPosicao(x, y);

		String[] comandos = comando.toUpperCase().split("");

		Sonda sonda = new Sonda(x, y, entity.getAngulo());
		for (int i = 0; i < comandos.length; i++) {
			sonda.mover(comandos[i]);
		}

		entity.setAngulo(sonda.getTheta());
		entity.setPosicaoX(sonda.getX());
		entity.setPosicaoY(sonda.getY());
		return sondaRepository.save(entity);
	}

	public String validarMovimento(Integer x, Integer y, String comando) {
		SondaEntity entity = encontrarSondaPelaPosicao(x, y);
		if (entity == null) {
			return String.format("Nao ha sondas na posicao (%s,%s)", x, y);
		}

		Sonda sonda = new Sonda(x, y, entity.getAngulo());

		String[] comandos = comando.toUpperCase().split("");
		StringBuilder comandoPrevia = new StringBuilder();

		for (int i = 0; i < comandos.length; i++) {
			String z = comandos[i];
			if (!z.equals("L") && !z.equals("R") && !z.equals("M")) {
				return String.format("Comando %s nao e permitido", comandos[i]);
			}
			
			sonda.mover(comandos[i]);
			comandoPrevia.append(comandos[i]);
			if (sonda.getX() >= 6 || sonda.getY() >= 6 || sonda.getX() <= -6 || sonda.getY() <= -6) {
				return String.format("Comando %s mandam a sonda para o espaco, planeta 5x5!", comandoPrevia.toString());
			}
		}
		return null;
	}

	public boolean detonarSonda(Integer x, Integer y) {
		SondaEntity sonda = encontrarSondaPelaPosicao(x, y);
		boolean posicaoLivre = sonda == null ? true : false;
		if (!posicaoLivre) {
			sondaRepository.delete(sonda);
		}
		return posicaoLivre;
	}

}
