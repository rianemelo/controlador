package br.com.elo7.controlador.model;

import br.com.elo7.controlador.model.parser.ReferencialParser;
import br.com.elo7.controlador.service.SondaMovimento;

public class Sonda implements SondaMovimento{

	private Integer x;
	private Integer y;
	private String referencial;
	
	public Sonda(Integer x, Integer y, String referencial) {
		this.x = x;
		this.y = y;
		this.referencial = referencial;
	}
	
	
	@Override
	public void left(String direcao) {		
		int nDirecao = ReferencialParser.get().numeroModulo(direcao);
		int nReferencial = ReferencialParser.get().numeroModulo(direcao);
		referencial = 
				ReferencialParser.get().referencial(nDirecao + nReferencial);
	
	}
	
	@Override
	public void right(String direcao) {
		int nDirecao = ReferencialParser.get().numeroModulo(direcao);
		int nReferencial = ReferencialParser.get().numeroModulo(direcao);
		referencial = 
				ReferencialParser.get().referencial(nDirecao - nReferencial);
	}
		
	@Override
	public void mover(String direcao) {
		switch(direcao) {
			case "S": y = y - 1;
				break;
			case "N": y = y + 1;
				break;
			case "E": x = x + 1;
				break;
			case "W": x = x - 1;
				break;
			default: //ainda não sei
				break;
		}
	}
}
