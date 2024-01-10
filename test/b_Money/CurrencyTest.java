package b_Money;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		// Testuje, czy metoda getName() zwraca poprawne nazwy walut.

		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}

	@Test
	public void testGetRate() {
		// Testuje, czy metoda getRate() zwraca poprawne kursy wymiany walut.

		assertEquals(Double.valueOf(0.15), SEK.getRate());
		assertEquals(Double.valueOf(0.20), DKK.getRate());
		assertEquals(Double.valueOf(1.5), EUR.getRate());
	}

	@Test
	public void testSetRate() {
		// Testuje ustawianie kursu wymiany waluty.

		SEK.setRate(0.20);
		assertEquals(Double.valueOf(0.20), SEK.getRate());

		SEK.setRate(0.15);
	}

	@Test
	public void testGlobalValue() {
		// Testuje obliczanie wartości uniwersalnej (globalnej) waluty.

		assertEquals(Integer.valueOf(1500), SEK.universalValue(10000));
		assertEquals(Integer.valueOf(3000), EUR.universalValue(2000));
	}

	@Test
	public void testValueInThisCurrency() {
		// Testuje obliczanie wartości w danej walucie.

		Integer amountInDKK = SEK.valueInThisCurrency(10000, DKK);
		Integer amountInEUR = DKK.valueInThisCurrency(5000, EUR);

		assertEquals(Integer.valueOf(7500), amountInDKK);
		assertEquals(Integer.valueOf(667), amountInEUR);
	}

}
