/**
 * Ce fichier est la propriété de Thomas BROUSSARD Code application : Composant :
 */
package fr.epita.iam.services.identity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiPredicate;

import fr.epita.iam.datamodel.Identity;

public class FileIdentityDAO implements IdentityDAO {

	static {
		try {
			IdentityDAOFactoryDynamicRegistration.registeredDAOs.put("file", new FileIdentityDAO(new BiPredicate<Identity, Identity>() {

				@Override
				public boolean test(Identity identity1, Identity identity2) {
					return identity1.getEmail().startsWith(identity2.getEmail())
							|| identity1.getDisplayName().startsWith(identity2.getDisplayName());
				}
			}));
		} catch (final FileNotFoundException e) {
			// TODO log
		} catch (final IOException e) {
			// FIXME Use a logger to trace the following error
			// LOGGER.error("An error occured", ${exception_var})
		}
	}

	private final PrintWriter writer;
	private final Scanner scanner;

	private final BiPredicate<Identity, Identity> searchFilter;
	//
	// @Override
	// public boolean test(Identity currentIdentity, Identity criteria) {
	// return matchString(currentIdentity.getEmail(), criteria.getEmail())
	// || matchString(currentIdentity.getDisplayName(), criteria.getDisplayName());
	// }
	//
	//
	// };


	public FileIdentityDAO(BiPredicate<Identity, Identity> searchFilter) throws FileNotFoundException, IOException {
		final File file = new File("C:/tmp/2018s1/identities.txt");
		writer = initializePrintWriter(file);
		scanner = initializeScanner(file);
		this.searchFilter = searchFilter;
	}

	@Override
	public void create(Identity identity) {
		writer.println("---");
		writer.println(identity.getDisplayName());
		writer.println(identity.getEmail());
		writer.println(identity.getUid());
		writer.println("---");
		writer.flush();
	}

	@Override
	public List<Identity> search(Identity criteria) {
		final ArrayList<Identity> list = new ArrayList<>();
		while (scanner.hasNext()) {

			scanner.nextLine();
			final String displayName = scanner.nextLine();
			final String email = scanner.nextLine();
			final String uid = scanner.nextLine();
			scanner.nextLine();
			final Identity currentIdentity = new Identity(displayName, uid, email);

			if (searchFilter.test(currentIdentity, criteria)) {
				list.add(currentIdentity);
			}

		}

		return list;
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
	private boolean matchString(String current, String expected) {
		return current.contains(expected);
	}

	@Override
	public void update(Identity identity) {

	}

	@Override
	public void delete(Identity identity) {

	}

	private static PrintWriter initializePrintWriter(final File file) throws IOException, FileNotFoundException {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		final FileOutputStream fos = new FileOutputStream(file, true);
		final PrintWriter writer = new PrintWriter(fos);
		return writer;
	}

	private static Scanner initializeScanner(final File file) throws IOException, FileNotFoundException {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}

		return new Scanner(file);
	}

	public void releaseResources() {
		writer.close();
	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#getById(java.io.Serializable)
	 */
	@Override
	public Identity getById(Serializable id) {
		return new Identity();// TODO implement
	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.IdentityDAO#healthCheck()
	 */
	@Override
	public boolean healthCheck() {
		// TODO implements
		return true;
	}
}
