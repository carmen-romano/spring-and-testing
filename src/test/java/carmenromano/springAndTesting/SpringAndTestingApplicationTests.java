package carmenromano.springAndTesting;

import carmenromano.springAndTesting.entities.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringAndTestingApplicationTests {
	private AnnotationConfigApplicationContext ctx;

	@BeforeEach
	void setupContext() {
		ctx = new AnnotationConfigApplicationContext(SpringComponentsApplication.class);
	}
	///*****************************TEST 1 *********************************
	@Test
	void costoCoperto() {
		Tavolo tavolo = new Tavolo(1.50, 4);
		Ordine ordine = new Ordine(tavolo);

		double expectedTotale = ordine.getImportoTotale();
		double result = 6;
		assertEquals(expectedTotale, result);
	}
	///*****************************TEST 2 *********************************

	@Test
	void costoTotaleOrdine() {
		Tavolo tavolo = new Tavolo(1.50, 4);
		Ordine ordine = new Ordine(tavolo);
		Menu m = (Menu) ctx.getBean("menu");
		ordine.aggiungiElementoOrdinato(m.getPizzaList().get(2));

		double expectedTotaleOrdine = ordine.getImportoTotale();
		double result = 11.98;
		assertEquals(expectedTotaleOrdine, result);
	}

	///*****************************TEST 3 *********************************
	@Test
	void testSvuotaOrdine() {
		Tavolo tavolo = new Tavolo(1.50, 4);
		Ordine ordine = new Ordine(tavolo);
		Menu m = (Menu) ctx.getBean("menu");

		ordine.aggiungiElementoOrdinato(m.getPizzaList().get(3));
		ordine.aggiungiElementoOrdinato(m.getPizzaList().get(2));
		ordine.aggiungiElementoOrdinato(m.getPizzaList().get(1));
		assertFalse(ordine.getElementiOrdinati().isEmpty());

		ordine.svuotaOrdine();
		assertTrue(ordine.getElementiOrdinati().isEmpty());
	}


	///*****************************TEST 4 *********************************
	@Test
	void testSvuotaElementoDaOrdine() {
		Tavolo tavolo = new Tavolo(1.50, 4);
		Ordine ordine = new Ordine(tavolo);
		Menu m = (Menu) ctx.getBean("menu");

		ordine.aggiungiElementoOrdinato(m.getPizzaList().get(3));
		assertEquals(1, ordine.getElementiOrdinati().size());

		ordine.rimuoviElementoOrdinato(m.getPizzaList().get(3));
		assertEquals(0, ordine.getElementiOrdinati().size());
	}

	///*****************************TEST 5 *********************************
	@ParameterizedTest
	@CsvSource({
			"1.50, 4, 0, 10.99",
			"2.00, 3, 1, 12.77",
			"1.00, 2, 2, 7.98"
	})
	void testCalcoloImportoTotale(double costoCoperto, int numeroPersone, int indiceMenu, double expectedResult) {
		Tavolo tavolo = new Tavolo(costoCoperto, numeroPersone);
		Ordine ordine = new Ordine(tavolo);
		Menu m = (Menu) ctx.getBean("menu");

		ordine.aggiungiElementoOrdinato(m.getPizzaList().get(indiceMenu));

		assertEquals(expectedResult, ordine.getImportoTotale());
	}
	}


