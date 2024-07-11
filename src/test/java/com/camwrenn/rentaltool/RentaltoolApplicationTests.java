package com.camwrenn.rentaltool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RentaltoolApplicationTests {

	RentaltoolApplication app = new RentaltoolApplication(false);

	@Test
	public void test1() {
		assertEquals("Discount percent must be between 0 and 100",
				app.rentalCommands.checkout("JAKR", "5", "101", "9/3/15"));
	}

	@Test
	public void test2() {
		String result = """
				Tool code: LADW
				Tool type: Ladder
				Tool brand: Werner
				Rental days: 3
				Check out date: 07/02/20
				Due date: 07/05/20
				Daily Rental Charge: $1.99
				Charge days: 2
				Pre-discount charge: $3.98
				Discount percent: 10%
				Discount amount: $0.40
				__________________________
				Final charge: $3.58
				""";

		assertEquals(result, app.rentalCommands.checkout("LADW", "3", "10", "7/2/20"));
	}

	@Test
	public void test3() {
		String result = """
				Tool code: CHNS
				Tool type: Chainsaw
				Tool brand: Stihl
				Rental days: 5
				Check out date: 07/02/15
				Due date: 07/07/15
				Daily Rental Charge: $1.49
				Charge days: 3
				Pre-discount charge: $4.47
				Discount percent: 25%
				Discount amount: $1.12
				__________________________
				Final charge: $3.35
				""";

		assertEquals(result, app.rentalCommands.checkout("CHNS", "5", "25", "7/2/15"));
	}

	@Test
	public void test4() {
		String result = """
				Tool code: JAKD
				Tool type: Jackhammer
				Tool brand: DeWalt
				Rental days: 6
				Check out date: 09/03/15
				Due date: 09/09/15
				Daily Rental Charge: $2.99
				Charge days: 3
				Pre-discount charge: $8.97
				Discount percent: 0%
				Discount amount: $0.00
				__________________________
				Final charge: $8.97
				""";

		assertEquals(result, app.rentalCommands.checkout("JAKD", "6", "0", "9/3/15"));
	}

	@Test
	public void test5() {
		String result = """
				Tool code: JAKR
				Tool type: Jackhammer
				Tool brand: Rigid
				Rental days: 9
				Check out date: 07/02/15
				Due date: 07/11/15
				Daily Rental Charge: $2.99
				Charge days: 6
				Pre-discount charge: $17.94
				Discount percent: 0%
				Discount amount: $0.00
				__________________________
				Final charge: $17.94
				""";

		assertEquals(result, app.rentalCommands.checkout("JAKR", "9", "0", "7/2/15"));
	}

	@Test
	public void test6() {
		String result = """
				Tool code: JAKR
				Tool type: Jackhammer
				Tool brand: Rigid
				Rental days: 4
				Check out date: 07/02/20
				Due date: 07/06/20
				Daily Rental Charge: $2.99
				Charge days: 1
				Pre-discount charge: $2.99
				Discount percent: 50%
				Discount amount: $1.50
				__________________________
				Final charge: $1.49
				""";

		assertEquals(result, app.rentalCommands.checkout("JAKR", "4", "50", "7/2/20"));
	}
}
