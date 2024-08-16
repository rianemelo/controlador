package br.com.elo7.controlador.service;

import br.com.elo7.controlador.model.Sonda;

public interface SondaMovimento {

	Sonda mover(Sonda sonda, String comando);

}
