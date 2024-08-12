package br.com.elo7.controlador.model;

import br.com.elo7.controlador.service.SondaMovimento;

public class SondaM implements SondaMovimento {

	private final Integer NOVENTA_GRAUS = 90;

	private Integer x;
	private Integer y;
	private Integer theta;

	public SondaM(Integer x, Integer y, Integer theta) {
		this.x = x;
		this.y = y;
		this.theta = theta;
	}

	private void transladar(Integer angulo) {
		Integer omega = angulo / 90;
		x = x + (int) Math.cos(Math.PI*omega/2);
		y = y + (int) Math.sin(Math.PI*omega/2);
	}

	@Override
	public void mover(String comando) {
		switch (comando) {
		case "L":
			theta += NOVENTA_GRAUS;
			break;
		case "R":
			theta -= NOVENTA_GRAUS;
			break;
		case "M":
			transladar(theta);
			break;
		}
	}
	
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getTheta() {
		return theta;
	}

	public void setTheta(Integer theta) {
		this.theta = theta;
	}

}
