
package fr.epita.iam.ui;

import java.util.List;
import java.util.Scanner;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;

import fr.epita.iam.datamodel.Identity;

public class ConsoleOperations {

	private final Scanner scanner;

	public ConsoleOperations() {
		scanner = new Scanner(System.in);
	}

	public void menu() {

		System.out.println("\nSelect user database Operations: ");
		System.out.println("\n1 Create");
		System.out.println("2 Search");
		System.out.println("3 Update");
		System.out.println("4 Delete");
		System.out.println("5 Exit");
	}

	public void cont() {
		Scanner in = new Scanner(System.in);
		System.out.println("Would you like to continue (yes/no)?");

		String conti = in.nextLine();

		if (conti.equals("yes")) {
			;
		} else {
			System.out.println("Program is terminated");
		}
	}

	public void authenticatingUser() {
		System.out.println("\nEnter User Name: ");
		String userName = scanner.nextLine();
		System.out.println("Enter Password: ");
		String passWord = scanner.nextLine();
		if (userName.equals("admin") && passWord.equals("test")) {
			System.out.println("logged Sucessfully ");
			menu();
			

		} else {
			System.out.println("Login Failed !");

		}

	}

	// Create Operation
	public Identity readIdentityFromConsole() {
		final Identity identity = new Identity();
		System.out.println("please input the display name : ");
		String line = scanner.nextLine();
		identity.setDisplayName(line);
		System.out.println("please input the email");
		line = scanner.nextLine();
		identity.setEmail(line);
		System.out.println("please input uid");
		line = scanner.nextLine();
		identity.setUid(line);
		System.out.println("Created Sucessfully ");
		
		cont();
		return identity;
	}

	// Search Operation
	public Identity readCriteriaFromConsole() {
		System.out.println("Enter criteria");
		final Identity identity = new Identity();
		System.out.println("please input the criterion for display name : ");
		String line = scanner.nextLine();
		identity.setDisplayName(line);
		System.out.println("please input the criterion for email");
		line = scanner.nextLine();
		identity.setEmail(line);
		System.out.println("Result: ");
		
		return identity;
	}

	// Update Operation
	public Identity readUserFromConsoletoUpdate() {

		final Identity identity = new Identity();
		System.out.println("please input uid");
		String line = scanner.nextLine();
		identity.setUid(line);
		System.out.println("please input the new user name : ");
		line = scanner.nextLine();
		identity.setDisplayName(line);
		System.out.println("please input the new email");
		line = scanner.nextLine();
		identity.setEmail(line);
		
		cont();
		return identity;
	}

	// Delete Operation
	public Identity readFromConsoleToDelete() {
		final Identity identity = new Identity();
		System.out.println("select the user name: ");
		String userName = scanner.nextLine();
		identity.setDisplayName(userName);
		
		cont();
		return identity;
	}

	// Output of Identity from Database
	public void displayIdentitiesInConsole(List<Identity> identities) {
		int i = 1;
		for (final Identity identity : identities) {
			System.out.print(i++);
			System.out.println(" - " + identity);
			
		}
	}

	public void releaseResources() {
		scanner.close();
	}
}
