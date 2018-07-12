package fr.epita.iam.launcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.EntityCreationException;
import fr.epita.iam.exceptions.EntityDeletionException;
import fr.epita.iam.exceptions.EntitySearchException;
import fr.epita.iam.exceptions.EntityUpdateException;
import fr.epita.iam.services.identity.IdentityDAO;
import fr.epita.iam.services.identity.IdentityDAOFactory;
import fr.epita.iam.services.identity.IdentityJDBCDAO;
import fr.epita.iam.ui.ConsoleOperations;

public class Launcher {

	
	public static void  main(String[] args) throws FileNotFoundException, IOException {
		// initialize resources
		IdentityDAO dao = null;
		
		
		try {
			dao = IdentityDAOFactory.getDAO();
			
		} catch (final Exception e) {
			System.out.println(e.getMessage());

			return;
		}
		final ConsoleOperations console = new ConsoleOperations();
		// Welcome
		System.out.println("Identity Access Management DataBase");

		// Authentication

		console.authenticatingUser();

		// Menu
		
		Scanner menu = new Scanner(System.in);
		String Dashboard = menu.nextLine();
		
		switch (Dashboard) 
		
		{
		// Create
		case "1":

			final Identity identity = console.readIdentityFromConsole();
			try {
				dao.create(identity);
				
				
			} catch (final EntityCreationException ece) {
				System.out.println(ece.getMessage());
				console.cont();
			}
			break;
		// Search?
		case "2":
			final Identity criteria = console.readCriteriaFromConsole();
			List<Identity> resultList;
			try {
				resultList = dao.search(criteria);
				console.displayIdentitiesInConsole(resultList);
				console.cont();
			} catch (final EntitySearchException e) {
				System.out.println(e.getMessage());
			}
			break;

		// Update
		case "3":
			try {
				final Identity userUpdate = console.readUserFromConsoletoUpdate();

				dao.update(userUpdate);
				console.cont();
			} catch (EntityUpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		// Delete
		case "4":
			
			try {
				final Identity userDelete = console.readFromConsoleToDelete();
				dao.delete(userDelete);
				console.cont();
			} catch (EntityDeletionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "5":
			
			console.releaseResources();
			System.out.println("Exited");
			
			
		default :
			System.out.println("");
			break;
		}
	}
}

