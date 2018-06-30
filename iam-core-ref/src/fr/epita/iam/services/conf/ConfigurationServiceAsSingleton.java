/**
 * Ce fichier est la propriété de Thomas BROUSSARD Code application : Composant :
 */
package fr.epita.iam.services.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * <h3>Description</h3>
 * <p>
 * This class allows to ...
 * </p>
 *
 * <h3>Usage</h3>
 * <p>
 * This class should be used as follows:
 *
 * <pre>
 * <code>${type_name} instance = new ${type_name}();</code>
 * </pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 *         ${tags}
 */
public class ConfigurationServiceAsSingleton {

	private final Properties properties;
	private static ConfigurationServiceAsSingleton instance;

	private ConfigurationServiceAsSingleton() throws FileNotFoundException, IOException {
		properties = new Properties();
		properties.load(new FileInputStream(new File(System.getProperty("conf.file.path"))));
	}

	public static ConfigurationServiceAsSingleton getInstance() {
		if (instance == null) {
			try {
				instance = new ConfigurationServiceAsSingleton();
			} catch (final FileNotFoundException e) {
				// FIXME Use a logger to trace the following error
				// LOGGER.error("An error occured", ${exception_var})
			} catch (final IOException e) {
				// FIXME Use a logger to trace the following error
				// LOGGER.error("An error occured", ${exception_var})
			}
		}
		return instance;
	}



	public String getProperty(String key) {
		return properties.getProperty(key);
	}

}
