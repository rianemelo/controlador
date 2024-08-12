package br.com.elo7.controlador.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sonda")
public class Sonda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "planeta_id")
	private Planeta planeta;

	public Sonda() {
	}

	public Sonda(Integer posicaoX, Integer posicaoY, Integer angulo) {
		this.posicaoX = posicaoX;
		this.posicaoY = posicaoY;
		this.angulo = angulo;
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

	public Planeta getPlaneta() {
		return planeta;
	}

	public void setPlaneta(Planeta planeta) {
		this.planeta = planeta;
	}

}
