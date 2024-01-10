package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		// Testuje dodawanie i usuwanie zaplanowanych płatności na koncie testowym.

		testAccount.addTimedPayment("Rent", 30, 10, new Money(50000, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("Rent"));

		testAccount.removeTimedPayment("Rent");
		assertFalse(testAccount.timedPaymentExists("Rent"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Testuje realizację zaplanowanej płatności po określonej liczbie ticków.

		testAccount.addTimedPayment("Utility", 5, 5, new Money(200000, SEK), SweBank, "Alice");
		for (int i = 0; i < 5; i++) {
			testAccount.tick();
		}
		assertTrue(testAccount.getBalance().getAmount() < 10000000);
	}

	@Test
	public void testAddWithdraw() {
		// Testuje wpłatę i wypłatę środków z konta testowego oraz sprawdza aktualne saldo.

		testAccount.deposit(new Money(500000, SEK));
		assertEquals(Integer.valueOf(10500000), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(250000, SEK));
		assertEquals(Integer.valueOf(10250000), testAccount.getBalance().getAmount());
	}

	@Test
	public void testGetBalance() {
		// Testuje pobieranie aktualnego salda konta.

		assertEquals(Integer.valueOf(10000000), testAccount.getBalance().getAmount());
	}
}
