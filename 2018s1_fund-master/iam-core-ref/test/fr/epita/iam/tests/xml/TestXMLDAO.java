/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.iam.tests.xml;

import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.identity.IdentityDAO;
import fr.epita.iam.services.identity.IdentityXMLDAO;

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
public class TestXMLDAO {

	public static void main(String[] args) throws Exception {
		// testWriteThenDisplayList();

		// given
		final IdentityDAO dao = new IdentityXMLDAO();
		final Identity quentin = new Identity("quentin", "7893", "qdc@qdc.com");
		dao.create(quentin);

		final Identity criteria = new Identity("quen", null, "qdc@qdc.com");

		// when
		final List<Identity> resultList = dao.search(criteria);


		// then
		if (resultList.size() != 1) {
			throw new Exception("the search method did not work");
		}
		System.out.println(resultList);
		System.out.println("search successful!");

	}

}
