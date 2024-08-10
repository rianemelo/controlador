package br.com.elo7.controlador.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.elo7.controlador.model.SondaEntity;
import br.com.elo7.controlador.service.SondaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class SondaController {

	@Autowired
	private SondaService sondaService;

	@PostMapping("/sondas")
	public ResponseEntity<String> aterrissarSonda(@RequestBody @Valid SondaEntity sonda) {
		if (!sondaService.aterrissarSonda(sonda)) {
			return new ResponseEntity<>("Posicao ja ocupada!", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/sondas")
	public List<SondaEntity> listarSondas() {
		return sondaService.listarSondas();
	}

	@GetMapping("/sondas/posicaoX/{x}/posicaoY/{y}")
	public SondaEntity encontrarSondaPelaPosicao(@PathVariable(value = "x") Integer x, @PathVariable(value = "y") Integer y) {
		return sondaService.encontrarSondaPelaPosicao(x, y);
	}

	@PutMapping("/sondas/posicaoX/{x}/posicaoY/{y}/comando/{comando}")
	public ResponseEntity<?> moverSonda(@PathVariable(value = "x") Integer x, @PathVariable(value = "y") Integer y,
			@PathVariable(value = "comando") String comando) {
		String erro = sondaService.validarMovimento(x, y, comando);
		if (erro != null) {
			return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
		}

		SondaEntity sonda = sondaService.moverSonda(x, y, comando);
		return ResponseEntity.status(HttpStatus.OK).body(sonda);
	}

	@DeleteMapping("sondas/posicaoX/{x}/posicaoY/{y}")
	public ResponseEntity<String> detonarSonda(@PathVariable(value = "x") Integer x,
			@PathVariable(value = "y") Integer y) {
		if (!sondaService.detonarSonda(x, y)) {
			return new ResponseEntity<>("0 sonda, espaco livre!", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}