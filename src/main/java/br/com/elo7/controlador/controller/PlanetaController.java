package br.com.elo7.controlador.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.elo7.controlador.model.PlanetaEntity;
import br.com.elo7.controlador.service.PlanetaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class PlanetaController {

	@Autowired
	private PlanetaService planetaService;

	@PostMapping("/planetas")
	public ResponseEntity<String> incluirPlaneta(@RequestBody @Valid PlanetaEntity planeta) {
		Optional<PlanetaEntity> planetaExistente = planetaService.encontrarPlaneta(planeta.getId());
		if (!planetaExistente.isEmpty()) {
			return new ResponseEntity<>("Planeta ja registrado no controlador, se quiser pode renomea-lo.",
					HttpStatus.BAD_REQUEST);
		}
		planetaService.salvarPlaneta(planeta);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/planetas")
	public List<PlanetaEntity> listarPlanetas() {
		return planetaService.listarPlanetas();
	}

	@GetMapping("/planetas/{id}")
	public Optional<PlanetaEntity> encontrarPlaneta(@PathVariable(value = "id") Long id) {
		return planetaService.encontrarPlaneta(id);
	}

	@PutMapping("/planetas/{id}")
	public ResponseEntity<?> renomearPlaneta(@PathVariable(value = "id") Long id,
			@RequestParam(name = "nome") @NotNull String nome) {
		Optional<PlanetaEntity> planeta = planetaService.encontrarPlaneta(id);
		if (planeta.isEmpty()) {
			return new ResponseEntity<>("Esse planeta nao existe!", HttpStatus.BAD_REQUEST);
		}
		planeta.get().setNome(nome);
		planetaService.salvarPlaneta(planeta.get());
		return ResponseEntity.status(HttpStatus.OK).body(planeta.get());
	}

	@DeleteMapping("planetas/{id}")
	public ResponseEntity<String> detonarPlaneta(@PathVariable(value = "id") Long id) {
		if (!planetaService.detonarPlaneta(id)) {
			return new ResponseEntity<>("Esse planeta nao existe!", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}