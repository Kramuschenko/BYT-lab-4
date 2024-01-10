package b_Money;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		// Testuje, czy metoda getAmount() zwraca poprawne kwoty pieniężne.

		assertEquals(Integer.valueOf(10000), SEK100.getAmount());
		assertEquals(Integer.valueOf(1000), EUR10.getAmount());
	}

	@Test
	public void testGetCurrency() {
		// Testuje, czy metoda getCurrency() zwraca poprawne waluty.

		assertEquals( SEK, SEK100.getCurrency());
		assertEquals( EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		// Testuje, czy metoda toString() zwraca poprawne reprezentacje kwoty pieniężnej.

		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("10.0 EUR", EUR10.toString());
	}

	@Test
	public void testGlobalValue() {
		// Testuje obliczanie wartości uniwersalnej (globalnej) kwoty pieniężnej.

		assertEquals(Integer.valueOf(1500), SEK100.universalValue());
		assertEquals(Integer.valueOf(1500), EUR10.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		// Testuje porównywanie kwot pieniężnych.

		assertTrue(SEK100.equals(new Money(10000, SEK)));
		assertTrue(SEK100.equals(EUR10));
		assertFalse(SEK100.equals(EUR20));
	}

	@Test
	public void testAdd() {
		// Testuje dodawanie kwot pieniężnych.

		Money sum = SEK100.add(EUR10);
		assertTrue(sum.equals(new Money(20000, SEK)));
	}

	@Test
	public void testSub() {
		// Testuje odejmowanie kwot pieniężnych.

		Money difference = SEK200.sub(SEK100);
		assertTrue(difference.equals(new Money(10000, SEK)));
	}

	@Test
	public void testIsZero() {
		// Testuje, czy kwota pieniężna jest zerowa.

		assertTrue(SEK0.isZero());
		assertFalse(SEK100.isZero());
	}

	@Test
	public void testNegate() {
		// Testuje negację kwoty pieniężnej.

		assertEquals(Integer.valueOf(-10000), SEK100.negate().getAmount());
	}

	@Test
	public void testCompareTo() {
		// Testuje porównywanie kwot pieniężnych.

		assertEquals(0, SEK100.compareTo(new Money(10000, SEK)));
		assertTrue(0 > SEK100.compareTo(new Money(20000, SEK)));
		assertTrue(0 < SEK200.compareTo(SEK100));
	}
}
