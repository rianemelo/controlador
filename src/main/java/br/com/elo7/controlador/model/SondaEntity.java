package br.com.elo7.controlador.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sondas")
public class SondaEntity {

    @Id
	private Long id;

	@Column(name = "posicaoX")
	@NotNull
	@Min(value = -5)
	@Max(value = 5)
	private Integer posicaoX;

	@Column(name = "posicaoY")
	@NotNull
	@Min(value = -5)
	@Max(value = 5)
	private Integer posicaoY;

	@Column(name = "angulo")
	@NotNull
	private Integer angulo;

	public SondaEntity(Long id, Integer posicaoX, Integer posicaoY, Integer angulo) {
		this.id = id;
		this.posicaoX = posicaoX;
		this.posicaoY = posicaoY;
		this.angulo = angulo;
	}

	public SondaEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getAngulo() {
		return angulo;
	}

	public void setAngulo(Integer angulo) {
		this.angulo = angulo;
	}

}
