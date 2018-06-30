/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.iam.tests.db;

import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.identity.IdentityDAO;
import fr.epita.iam.services.identity.IdentityDAOFactory;

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
public class TestJDBCDAO {

	public static void main(String[] args) throws Exception {
		// testWriteThenDisplayList();

		// given
		final IdentityDAO dao = IdentityDAOFactory.getDAO();
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

}
