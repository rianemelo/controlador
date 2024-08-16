package br.com.elo7.controlador.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.com.elo7.controlador.model.Sonda;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class SondaMovimentoImplUnitTest {

	@InjectMocks
	private SondaMovimentoImpl sondaSondaMovimentoImpl;

	@Test
	public void testMoverExemplo1() {
		Sonda sonda = new Sonda(1, 2, "N");
		String comando = "LMLMLMLMM";
		Sonda sondaMovida = sondaSondaMovimentoImpl.mover(sonda, comando);

		assertEquals(1, sondaMovida.getPosicaoX());
		assertEquals(3, sondaMovida.getPosicaoY());
		assertEquals("N", sondaMovida.getDirecao());
	}

	@Test
	public void testMoverExemplo2() {
		Sonda sonda = new Sonda(3, 3, "E");
		String comando = "mmrmmrmrrml";
		Sonda sondaMovida = sondaSondaMovimentoImpl.mover(sonda, comando);

		assertEquals(5, sondaMovida.getPosicaoX());
		assertEquals(1, sondaMovida.getPosicaoY());
		assertEquals("N", sondaMovida.getDirecao());
	}

	@Test
	public void testMandarSondaParaEspaco() {
		Sonda sonda = new Sonda(0, 0, "E");
		String comando = "mmmmmm";

		assertThrowsExactly(IllegalArgumentException.class, () -> {
			sondaSondaMovimentoImpl.mover(sonda, comando);
		}, "Comando cancelado: sequencia manda a sonda para o espaco. PLANETA 5x5!");
	}
	
	@Test
	public void testComandoNaoReconhecido() {
		Sonda sonda = new Sonda(0, 0, "E");
		String comando = "MMqmmP";

		assertThrowsExactly(IllegalArgumentException.class, () -> {
			sondaSondaMovimentoImpl.mover(sonda, comando);
		}, "Comando Q nao reconhecido.");
	}

}
