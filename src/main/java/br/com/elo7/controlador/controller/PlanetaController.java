package br.com.elo7.controlador.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.elo7.controlador.model.Planeta;
import br.com.elo7.controlador.model.Sonda;
import br.com.elo7.controlador.service.PlanetaService;
import br.com.elo7.controlador.service.SondaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class PlanetaController {

	@Autowired
	private PlanetaService planetaService;

	@Autowired
	private SondaService sondaService;

	@GetMapping("/planetas")
	public List<Planeta> listarPlanetas() {
		return planetaService.listarPlanetas();
	}

	@GetMapping("/planetas/{planetaId}")
	public Optional<Planeta> encontrarPlaneta(@PathVariable(value = "planetaId") Long planetaId) {
		return planetaService.encontrarPlaneta(planetaId);
	}

	@GetMapping("/planetas/{planetasId}/sondas")
	public List<Sonda> listarSondas(Long planetaId) {
		return sondaService.listarSondas(planetaId);
	}

	@GetMapping("/planetas/{planetaId}/sondas/{sondaId}")
	public Sonda encontrarSonda(@PathVariable(value = "planetaId") Long planetaId,
			@PathVariable(value = "sondaId") Long sondaId) {
		return sondaService.encontrarSonda(planetaId, sondaId);
	}

	@PostMapping("/planetas")
	public ResponseEntity<String> incluirPlaneta(@RequestBody @Valid Planeta planeta) {
		Optional<Planeta> planetaExistente = planetaService.encontrarPlaneta(planeta.getId());
		if (!planetaExistente.isEmpty()) {
			return new ResponseEntity<>("Planeta ja registrado no controlador, se quiser pode renomea-lo.",
					HttpStatus.BAD_REQUEST);
		}
		planetaService.salvarPlaneta(planeta);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("planetas/{planetaId}/sondas")
	public ResponseEntity<String> aterrissarSonda(@PathVariable(value = "planetaId") Long planetaId,
			@RequestBody @Validated Sonda sonda) {
		if (!sondaService.aterrissarSonda(planetaId, sonda)) {
			return new ResponseEntity<>("Posicao ja ocupada!", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/planetas/{planetaId}")
	public ResponseEntity<?> renomearPlaneta(@PathVariable(value = "planetaId") Long planetaId,
			@RequestParam(name = "nome") @NotNull String nome) {
		Optional<Planeta> planeta = planetaService.encontrarPlaneta(planetaId);
		if (planeta.isEmpty()) {
			return new ResponseEntity<>("Renomeacao nao concluida: planeta nao esta no controlador!", HttpStatus.BAD_REQUEST);
		}
		planeta.get().setNome(nome);
		planetaService.salvarPlaneta(planeta.get());
		return ResponseEntity.status(HttpStatus.OK).body(planeta.get());
	}

	@PutMapping("planetas/{planetaId}/sondas")
	public ResponseEntity<?> moverSonda(@PathVariable(value = "planetaId") Long planetaId,
			@RequestParam(value = "posicaoX") Integer x, @RequestParam(value = "posicaoY") Integer y,
			@RequestParam(value = "comando") String comando) {
		String erro = sondaService.validarMovimento(planetaId, x, y, comando);
		if (erro != null) {
			return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
		}

		Sonda sonda = sondaService.moverSonda(planetaId, x, y, comando);
		return ResponseEntity.status(HttpStatus.OK).body(sonda);
	}

	@DeleteMapping("planetas/{planetaId}")
	public ResponseEntity<String> detonarPlaneta(@PathVariable(value = "planetaId") Long planetaId) {
		if (!planetaService.detonarPlaneta(planetaId)) {
			return new ResponseEntity<>("Detonacao nao concluida: planeta nao esta no controlador!", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("planetas/{planetaId}/sondas")
	public ResponseEntity<String> detonarSonda(@PathVariable(value = "planetaId") Long planetaId, @RequestParam(value = "posicaoX") Integer x,
			@RequestParam(value = "posicaoY") Integer y) {
		if (!sondaService.detonarSonda(planetaId, x, y)) {
			return new ResponseEntity<>("0 sonda, espaco livre!", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}