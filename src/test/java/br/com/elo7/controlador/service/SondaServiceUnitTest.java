package br.com.elo7.controlador.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.com.elo7.controlador.dto.MoverSondaRequestDTO;
import br.com.elo7.controlador.model.Planeta;
import br.com.elo7.controlador.model.Sonda;
import br.com.elo7.controlador.repository.PlanetaRepository;
import br.com.elo7.controlador.repository.SondaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class SondaServiceUnitTest {

	@InjectMocks
	private SondaService sondaService;
	
	@Mock
	private PlanetaRepository planetaRepository;

	@Mock
	private SondaRepository sondaRepository;

	@Mock
	private SondaMovimento sondaMovimento;
	
	@Test
	public void testAterrissarSondaSemPlaneta() {
		Sonda mockSonda = new Sonda(0, 0, "N");
		Long mockPlanetaId = 9L;
		
		when(planetaRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));

		assertThrowsExactly(IllegalArgumentException.class, () -> {
			sondaService.aterrissarSonda(mockPlanetaId, mockSonda);
		}, "Planeta nao esta no controlador!");
	}

	@Test
	public void testRiscoColisaoNaAterrissagem() {
		Sonda mockSonda = new Sonda(0, 0, "N");
		Optional<Planeta> mockPlaneta = Optional.ofNullable(new Planeta(1L, "Planet Hemp"));
		mockSonda.setPlaneta(mockPlaneta.get());
		planetaRepository.save(mockPlaneta.get());

		when(sondaRepository.existsByPlanetaIdAndPosicaoXAndPosicaoY(any(Long.class), any(Integer.class),
				any(Integer.class))).thenReturn(true);

		assertThrowsExactly(IllegalArgumentException.class, () -> {
			sondaService.aterrissarSonda(mockPlaneta.get().getId(), mockSonda);
		}, "Aterrissagem abortada! Risco de colisao: posicao ja ocupada por outra sonda!");
	}

	@Test
	public void moverSondaSemPlaneta() {
		MoverSondaRequestDTO mockDto = new MoverSondaRequestDTO();
		mockDto.setPosicaoX(2);
		mockDto.setPosicaoY(5);
		mockDto.setComandos("PPPP");
		mockDto.setPlanetaId(9L);

		when(planetaRepository.existsById(any(Long.class))).thenReturn(false);

		assertThrowsExactly(IllegalArgumentException.class, () -> {
			sondaService.moverSonda(mockDto);
		}, "Comando cancelado: planeta nao esta no controlador.");
	}

	@Test
	public void moverSondaForaDeNenhumPlaneta() {
		MoverSondaRequestDTO mockDto = new MoverSondaRequestDTO();
		mockDto.setPosicaoX(2);
		mockDto.setPosicaoY(5);
		mockDto.setComandos("PPPP");
		mockDto.setPlanetaId(9L);

		when(sondaRepository.findByPlanetaIdAndPosicaoXAndPosicaoY(any(Long.class), any(Integer.class),
				any(Integer.class))).thenReturn(null);

		assertThrows(IllegalArgumentException.class, () -> {
			sondaService.moverSonda(mockDto);
		}, "Sonda nao encontrada. Verifique se o planeta e a sonda estao no controlador.");
	}

	@Test
	public void moverSondaRiscoColisao() {
		MoverSondaRequestDTO mockDto = new MoverSondaRequestDTO();
		mockDto.setPosicaoX(0);
		mockDto.setPosicaoY(2);
		mockDto.setComandos("RMRLMMM");
		Optional<Sonda> mockSonda = Optional.ofNullable(new Sonda(0, 2, "N"));

		when(planetaRepository.existsById(any(Long.class))).thenReturn(false);

		when(sondaRepository.findByPlanetaIdAndPosicaoXAndPosicaoY(any(Long.class), any(Integer.class),
				any(Integer.class))).thenReturn(mockSonda);

		when(sondaMovimento.mover(any(Sonda.class), any(String.class))).thenReturn(new Sonda(0, 2, "S"));

		assertThrowsExactly(IllegalArgumentException.class, () -> {
			sondaService.moverSonda(mockDto);
		}, "Comando cancelado. Risco de colisao: posicao ja ocupada por outra sonda!");
	}

	@Test
	public void moverSondaSucesso() {
		MoverSondaRequestDTO mockDto = new MoverSondaRequestDTO();
		mockDto.setPlanetaId(87L);
		mockDto.setPosicaoX(3);
		mockDto.setPosicaoY(3);
		mockDto.setComandos("mmrmmrmrrml");
		Optional<Sonda> mockSonda = Optional.ofNullable(new Sonda(3, 3, "E"));

		when(planetaRepository.existsById(any(Long.class))).thenReturn(true);

		when(sondaRepository.findByPlanetaIdAndPosicaoXAndPosicaoY(any(Long.class), any(Integer.class),
				any(Integer.class))).thenReturn(mockSonda);
		
		Sonda mockSondaMovida = new Sonda(5, 1, "N");
		when(sondaMovimento.mover(any(Sonda.class), any(String.class))).thenReturn(mockSondaMovida);

		when(sondaRepository.save(any(Sonda.class))).thenReturn(mockSondaMovida);
		
		Sonda sondaMovida = sondaService.moverSonda(mockDto);
		
		assertEquals(5, sondaMovida.getPosicaoX());
		assertEquals(1, sondaMovida.getPosicaoY());
		assertEquals("N", sondaMovida.getDirecao());
	}

}
