package br.com.elo7.controlador.model;

import br.com.elo7.controlador.service.SondaMovimento;

public class Sonda implements SondaMovimento{

	private Integer x;
	private Integer y;
	private Integer theta;
	private String referencial;
	
	public Sonda(Integer x, Integer y, Integer theta, String referencial) {
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.referencial = referencial;
	}
	
	@Override
	public void left(Integer omega) {
		theta += omega;	
	}
	
	@Override
	public void right(Integer omega) {
		theta -= omega;
	}
		
	@Override
	public void mover(Integer omega) {
		x = (int) (x + Math.cos(omega.doubleValue()));
		y = (int) (y + Math.sin(omega.doubleValue()));
	}
	
}
