package com.camwrenn.rentaltool;

import com.camwrenn.rentaltool.commands.RentalCommands;
import com.camwrenn.rentaltool.repository.HardCodedToolRepository;
import com.camwrenn.rentaltool.repository.HardCodedToolTypeRepository;
import com.camwrenn.rentaltool.repository.ToolRepository;
import com.camwrenn.rentaltool.repository.ToolTypeRepository;
import com.camwrenn.rentaltool.service.ChargeableDaysService;
import com.camwrenn.rentaltool.service.RentalAgreementService;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class RentaltoolApplication {

	ToolRepository toolRepository;
	ToolTypeRepository toolTypeRepository;
	ChargeableDaysService chargeableDaysService;
	RentalAgreementService rentalAgreementService;
	RentalCommands rentalCommands;

	public static void main(String[] args) {
		boolean doubleHoliday = false;
		if (args.length > 0) {
			Optional<String> dh = Arrays.stream(args).filter(arg -> arg.startsWith("dh=")).findFirst();
			if (dh.isPresent() && dh.get().substring(3).equals("true")) {
				doubleHoliday = true;
				System.out.println("Double holidays enabled: Tools that do not charge for holidays will be discounted" +
						" on the actual holiday, and observed holiday.");
			}
		}

		System.out.println("Welcome to the Rental Tool Application");

		RentaltoolApplication app = new RentaltoolApplication(doubleHoliday);
		app.scan();
	}

	public RentaltoolApplication(boolean doubleHoliday) {
		this.toolTypeRepository = new HardCodedToolTypeRepository();
		this.toolRepository = new HardCodedToolRepository(toolTypeRepository);
		this.chargeableDaysService = new ChargeableDaysService(doubleHoliday);
		this.rentalAgreementService = new RentalAgreementService(toolRepository, chargeableDaysService);
		this.rentalCommands = new RentalCommands(rentalAgreementService, toolRepository);
	}

	private void scan() {

		help();

        try (Scanner scan = new Scanner(System.in)) {
            boolean done = false;
            while (!done) {

				System.out.print("> ");

                String request = scan.nextLine();

                String[] command = request.split(" ");
                if (command.length > 0 && !command[0].isBlank()) {

					switch (command[0].toLowerCase()) {
						case "checkout" -> {
							if (command.length == 5) {
								System.out.println(rentalCommands.checkout(command[1], command[2], command[3], command[4]));
							} else {
								System.out.println("""
                                    checkout requires 4 parameters separated by space:
                                    	String: Valid code for a tool
                                    	Integer: The length of the rental, greater than 0
                                    	Integer: The discount percent, between 1 and 100
                                    	String: The checkout date in the format MM/DD/YY
                                    
                                    	Example:
                                    	checkout JAKD 5 10 7/1/24
                                    """);
							}
						}
						case ("list") -> System.out.println(rentalCommands.list());
						case ("tool") -> {
							if (command.length == 2) {
								System.out.println(rentalCommands.tool(command[1]));
							} else {
								System.out.println("""
                                    checkout requires 1 parameter:
                                    	String: The tool code
                                    
                                    	Example:
                                    	tool JAKD
                                    """);
							}
						}
						case ("help") -> help();
						case ("exit") -> done = true;
						default -> System.out.printf("\"%s\" not recognized as a valid command \n", command[0]);
					}
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}

	private void help() {
		System.out.printf("""
				General commands:
				  help: Display this message
				  exit: Finish and close the application
				%s
				""", rentalCommands.help());
	}
}