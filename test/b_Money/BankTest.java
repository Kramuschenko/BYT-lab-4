package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		// Sprawdzenie, czy metoda getName() zwraca oczekiwane nazwy banków.

		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		// Sprawdzenie, czy metoda getCurrency() zwraca oczekiwane waluty banków.

		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException {
		try {
			// Próba otwarcia konta o istniejącej już nazwie (Ulrika) i sprawdzenie, czy rzucany jest wyjątek AccountExistsException.

			SweBank.openAccount("Ulrika");
			fail("Should throw AccountExistsException");
		} catch (AccountExistsException e) {
		}
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		// Wpłata 1000 SEK na konto "Ulrika" w SweBank i sprawdzenie, czy saldo wynosi teraz 1000.

		Money money = new Money(1000, SEK);
		SweBank.deposit("Ulrika", money);
		assertEquals(Integer.valueOf(1000), SweBank.getBalance("Ulrika"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		// Wypłata 500 SEK z konta "Bob" w SweBank i sprawdzenie, czy saldo wynosi teraz -500.

		Money money = new Money(500, SEK);
		SweBank.withdraw("Bob", money);
		assertEquals(Integer.valueOf(-500), SweBank.getBalance("Bob"));
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		// Sprawdzenie, czy saldo konta "Ulrika" w SweBank wynosi początkowo 0.

		assertEquals(Integer.valueOf(0), SweBank.getBalance("Ulrika"));
	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		// Przelew 500 SEK z konta "Ulrika" w SweBank na konto "Bob" w Nordea i sprawdzenie, czy saldo zostało zaktualizowane odpowiednio.

		Money money = new Money(500, SEK);
		SweBank.transfer("Ulrika", Nordea, "Bob", money);
		assertEquals(Integer.valueOf(-500), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(500), Nordea.getBalance("Bob"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Dodanie zaplanowanej płatności dla "Bob" w SweBank, która będzie się powtarzać co 10 ticków i sprawdzenie, czy została poprawnie dodana.
		// Wykonanie 10 ticków, aby sprawdzić, czy zaplanowane płatności są realizowane i saldo zostaje zaktualizowane odpowiednio.

		Money money = new Money(100, SEK);
		SweBank.addTimedPayment("Bob", "Rent", 10, 10, money, DanskeBank, "Gertrud");
		assertTrue(SweBank.getAccountlist().get("Bob").timedPaymentExists("Rent"));

		for (int i = 0; i < 10; i++) {
			SweBank.tick();
		}
		assertEquals(Integer.valueOf(-100), SweBank.getBalance("Bob"));
		assertEquals(Integer.valueOf(75), DanskeBank.getBalance("Gertrud"));
	}
}
