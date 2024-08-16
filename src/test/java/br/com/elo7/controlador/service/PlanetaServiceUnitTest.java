package br.com.elo7.controlador.service;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.com.elo7.controlador.repository.PlanetaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class PlanetaServiceUnitTest {

	@InjectMocks
	private PlanetaService planetaService;

	@Mock
	private PlanetaRepository planetaRepository;

	@Test
	public void testDetonarPlanetaSucesso() {
		Long planetaId = 1L;

		when(planetaRepository.existsById(planetaId)).thenReturn(true);
		planetaService.detonarPlaneta(planetaId);

		verify(planetaRepository, times(1)).deleteById(planetaId);
	}

	@Test
	public void testDetonarPlanetaForaDoControlador() {
		when(planetaRepository.existsById(any(Long.class))).thenReturn(false);
		
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			planetaService.detonarPlaneta(1L);
		}, "Detonacao abortada: planeta nao esta no controlador!");
	}
}
