/**
 * Ce fichier est la propriété de Thomas BROUSSARD Code application : Composant :
 */
package fr.epita.iam.services.identity;

import java.util.function.BiPredicate;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.conf.ConfKey;
import fr.epita.iam.services.conf.ConfigurationService;


public class IdentityDAOFactory {

	private static IdentityDAO currentInstance;

	private static boolean fallbackActivated;

	public static IdentityDAO getDAO() throws Exception {
		final String backendMode = ConfigurationService.getProperty(ConfKey.BACKEND_MODE);

		if (currentInstance == null) {

			currentInstance = getInstance(backendMode);
		}
		if (currentInstance != null && !currentInstance.healthCheck()) {
			fallbackActivated = true;
			final String fallbackMode = ConfigurationService.getProperty(ConfKey.FALLBACK_BACKEND_MODE);
			currentInstance = getInstance(fallbackMode);
		}

		return currentInstance;
	}


	private static IdentityDAO getInstance(final String backendMode) throws Exception {
		IdentityDAO instance = null;
		switch (backendMode) {
		case "db":
			instance = new IdentityJDBCDAO();
			break;
		case "file":
			try {
				instance = new FileIdentityDAO(new BiPredicate<Identity, Identity>() {

					@Override
					public boolean test(Identity identity1, Identity identity2) {
						return identity1.getEmail().startsWith(identity2.getEmail())
								|| identity1.getDisplayName().startsWith(identity2.getDisplayName());
					}
				});
			} catch (final Exception e) {
				// TODO log
			}
			break;
		case "xml":
			instance = new IdentityXMLDAO();
			break;
		default:
			throw new Exception("not implemented yet");
		}
		return instance;

	}

}
