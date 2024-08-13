package br.com.elo7.controlador.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

public class MoverSondaRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long planetaId;
	@NotNull
	private Integer posicaoX;
	@NotNull
	private Integer posicaoY;
	@NotNull
	private String comandos;

	public Long getPlanetaId() {
		return planetaId;
	}

	public void setPlanetaId(Long planetaId) {
		this.planetaId = planetaId;
	}

	public Integer getPosicaoX() {
		return posicaoX;
	}

	public void setPosicaoX(Integer posicaoX) {
		this.posicaoX = posicaoX;
	}

	public Integer getPosicaoY() {
		return posicaoY;
	}

	public void setPosicaoY(Integer posicaoY) {
		this.posicaoY = posicaoY;
	}

	public String getComandos() {
		return comandos.toUpperCase();
	}

	public void setComandos(String comandos) {
		this.comandos = comandos;
	}

}
