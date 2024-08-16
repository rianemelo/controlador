package br.com.elo7.controlador.service;

import org.springframework.stereotype.Component;

import br.com.elo7.controlador.model.Sonda;

@Component
public class SondaMovimentoImpl implements SondaMovimento {

	private final Integer NOVENTA_GRAUS = 90;

	@Override
	public Sonda mover(Sonda sonda, String comando) {
		String[] listaComandos = comando.toUpperCase().split("");

		for (int i = 0; i < listaComandos.length; i++) {
			moverComandoUnico(sonda, listaComandos[i]);

			if (sonda.getPosicaoX() >= 6 || sonda.getPosicaoY() >= 6 || sonda.getPosicaoX() <= -6
					|| sonda.getPosicaoY() <= -6) {
				throw new IllegalArgumentException(
						"Comando cancelado: sequencia manda a sonda para o espaco. PLANETA 5x5!");
			}
		}

		return sonda;
	}

	private Sonda moverComandoUnico(Sonda sonda, String comando) {
		switch (comando) {
		case "L":
			sonda.setDirecao(girarLeft(sonda.getDirecao()));
			break;
		case "R":
			sonda.setDirecao(girarRight(sonda.getDirecao()));
			break;
		case "M":
			transladar(sonda);
			break;
		default:
			throw new IllegalArgumentException(String.format("Comando %s nao reconhecido.", comando));
		}
		return sonda;
	}

	private String girarRight(String direcao) {
		Integer omega = retornarAngulo(direcao) - NOVENTA_GRAUS;
		Integer theta = ((omega / NOVENTA_GRAUS % 4) + 4) * NOVENTA_GRAUS;
		omega = omega >= 0 ? omega : theta; // omega sera um angulo positivo equivalente a rotacao right do angulo
		return retornarDirecao(omega);
	}

	private String girarLeft(String direcao) {
		return retornarDirecao(retornarAngulo(direcao) + NOVENTA_GRAUS);
	}

	private void transladar(Sonda sonda) {
		Integer angulo = retornarAngulo(sonda.getDirecao()) / 90;

		sonda.setPosicaoX(sonda.getPosicaoX() + (int) Math.cos(Math.PI * angulo / 2));
		sonda.setPosicaoY(sonda.getPosicaoY() + (int) Math.sin(Math.PI * angulo / 2));
	}

	private Integer retornarAngulo(String z) {
		switch (z) {
		case "S":
			return 270;
		case "N":
			return 90;
		case "E":
			return 0;
		case "W":
			return 180;
		default:
			return 0;
		}
	}

	private String retornarDirecao(Integer angulo) {
		switch ((angulo / NOVENTA_GRAUS) % 4) {
		case 3:
			return "S";
		case 1:
			return "N";
		case 0:
			return "E";
		case 2:
			return "W";
		default:
			return "E";
		}
	}

}
