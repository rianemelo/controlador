package br.com.elo7.controlador.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "planeta")
public class Planeta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	@NotNull
	private String nome;

	@JsonManagedReference
	@OneToMany(mappedBy = "planeta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Sonda> sondas;

	public Planeta() {
	}

	public Planeta(Long id, String nome) {
		this.id = id;
		this.nome = nome;
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

	public List<Sonda> getSondas() {
		return sondas;
	}

	public void setSondas(List<Sonda> sondas) {
		this.sondas = sondas;
	}

}
