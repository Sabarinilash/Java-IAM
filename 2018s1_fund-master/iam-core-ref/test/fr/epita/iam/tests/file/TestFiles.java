/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.iam.tests.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.identity.FileIdentityDAO;
import fr.epita.iam.ui.ConsoleOperations;

public class TestFiles {

	public static void main(String[] args) throws Exception {
		// testWriteThenDisplayList();

		// given
		final FileIdentityDAO dao = new FileIdentityDAO();
		final Identity quentin = new Identity("quentin", "7893", "qdca@qdca.com");
		dao.create(quentin);

		final Identity criteria = new Identity("quen", null, "qdc");

		// when
		final List<Identity> resultList = dao.search(criteria);

		// then
		boolean found = false;
		for (final Identity identity : resultList) {
			if (identity.getUid().equals(quentin.getUid())) {
				found = true;
				break;
			}
		}

		if (!found) {
			throw new Exception("the search method did not work");
		}
		System.out.println("search successful!");

	}

	/**
	 * <h3>Description</h3>
	 * <p>This methods allows to ...</p>
	 *
	 * <h3>Usage</h3>
	 * <p>It should be used as follows :
	 *
	 * <pre><code> ${enclosing_type} sample;
	 *
	 * //...
	 *
	 * sample.${enclosing_method}();
	 *</code></pre>
	 * </p>
	 *
	 * @since $${version}
	 * @see Voir aussi $${link}
	 * @author ${user}
	 *
	 * ${tags}
	 */
	private static void testWriteThenDisplayList() throws FileNotFoundException, IOException {
		final ConsoleOperations console = new ConsoleOperations();

		final Identity identity = console.readIdentityFromConsole();

		final FileIdentityDAO dao = new FileIdentityDAO();
		dao.create(identity);
		final List<Identity> list = dao.search(new Identity());
		System.out.println(list);
		dao.releaseResources();
		console.releaseResources();
	}

	/**
	 * <h3>Description</h3>
	 * <p>
	 * This methods allows to ...
	 * </p>
	 *
	 * <h3>Usage</h3>
	 * <p>
	 * It should be used as follows :
	 *
	 * <pre>
	 * <code> ${enclosing_type} sample;
	 *
	 * //...
	 *
	 * sample.${enclosing_method}();
	 *</code>
	 * </pre>
	 * </p>
	 *
	 * @since $${version}
	 * @see Voir aussi $${link}
	 * @author ${user}
	 *
	 *         ${tags}
	 */
	private static Identity readIdentityFromConsole(final Scanner scanner) {
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
		return identity;
	}

	/**
	 * <h3>Description</h3>
	 * <p>This methods allows to ...</p>
	 *
	 * <h3>Usage</h3>
	 * <p>It should be used as follows :
	 *
	 * <pre><code> ${enclosing_type} sample;
	 *
	 * //...
	 *
	 * sample.${enclosing_method}();
	 *</code></pre>
	 * </p>
	 *
	 * @since $${version}
	 * @see Voir aussi $${link}
	 * @author ${user}
	 *
	 * ${tags}
	 */
	private static void writeIdentity(final Identity identity, final PrintWriter writer) {
		writer.println("---");
		writer.println(identity.getDisplayName());
		writer.println(identity.getEmail());
		writer.println(identity.getUid());
		writer.println("---");
	}

	/**
	 * <h3>Description</h3>
	 * <p>This methods allows to ...</p>
	 *
	 * <h3>Usage</h3>
	 * <p>It should be used as follows :
	 *
	 * <pre><code> ${enclosing_type} sample;
	 *
	 * //...
	 *
	 * sample.${enclosing_method}();
	 *</code></pre>
	 * </p>
	 *
	 * @since $${version}
	 * @see Voir aussi $${link}
	 * @author ${user}
	 *
	 * ${tags}
	 */
	private static PrintWriter initializePrintWriter(final File file) throws IOException, FileNotFoundException {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		final FileOutputStream fos = new FileOutputStream(file, true);
		final PrintWriter writer = new PrintWriter(fos);
		return writer;
	}

	/**
	 * <h3>Description</h3>
	 * <p>
	 * This methods allows to ...
	 * </p>
	 *
	 * <h3>Usage</h3>
	 * <p>
	 * It should be used as follows :
	 *
	 * <pre>
	 * <code> ${enclosing_type} sample;
	 *
	 * //...
	 *
	 * sample.${enclosing_method}();
	 *</code>
	 * </pre>
	 * </p>
	 *
	 * @since $${version}
	 * @see Voir aussi $${link}
	 * @author ${user}
	 *
	 *         ${tags}
	 */
	private static void basicFileTest() throws IOException, FileNotFoundException {
		final File directory = new File("C:\\tmp\\2018s1");
		if (directory.exists()) {
			System.out.println("the directory exists");
		} else {
			System.out.println("the directory does not exist, cannot continue");
			return;
		}
		final File file = new File("C:\\tmp2\\2018s1\\identities.txt");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		final FileOutputStream fos = new FileOutputStream(file, true);
		final PrintWriter writer = new PrintWriter(fos);
		writer.println("hello!");
		writer.flush();
		fos.close();
		writer.close();
	}
}
