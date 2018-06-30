/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.iam.services.identity;

import java.util.LinkedHashMap;
import java.util.Map;

import fr.epita.iam.services.conf.ConfKey;
import fr.epita.iam.services.conf.ConfigurationService;

/**
 * <h3>Description</h3>
 * <p>This class allows to ...</p>
 *
 * <h3>Usage</h3>
 * <p>This class should be used as follows:
 *   <pre><code>${type_name} instance = new ${type_name}();</code></pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 * ${tags}
 */
public class IdentityDAOFactoryDynamicRegistration {

	static Map<String, IdentityDAO> registeredDAOs = new LinkedHashMap<>();

	public static IdentityDAO getDAO() throws Exception {
		return registeredDAOs.get(ConfigurationService.getProperty(ConfKey.BACKEND_MODE));
	}

}
