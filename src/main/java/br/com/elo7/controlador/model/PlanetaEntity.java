package br.com.elo7.controlador.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "planetas")
public class PlanetaEntity {

	@Id
	private Long id;

	@Column(name = "nome")
	@NotNull
	private String nome;

	public PlanetaEntity(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public PlanetaEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
