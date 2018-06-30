/**
 * Ce fichier est la propriété de Thomas BROUSSARD Code application : Composant :
 */
package fr.epita.iam.tests.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestDabases {

	public static void main(String[] args) throws Exception {
		// testConnection();
		final Connection connection = getConnection();
		final PreparedStatement pstmt = connection
				.prepareStatement("INSERT INTO IDENTITIES (IDENTITY_DISPLAYNAME, IDENTITY_EMAIL, IDENTITY_UID) VALUES (?, ?, ?)");
		pstmt.setString(1, "Clément Serr");
		pstmt.setString(2, "cserr@cserr.com");
		pstmt.setString(3, "9123");
		pstmt.execute();
		pstmt.close();
		connection.close();

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
	private static void testConnection() throws Exception {

		final String currentSchema = "";
		final Connection connection = getConnection();
		// Then I should get the "TEST" string in the currentSchema
		if (!currentSchema.equals("TEST")) {
			throw new Exception("problem: connection not operational");
		}
	}

	private static Connection getConnection() throws SQLException {
		// Given this context
		final String url = "jdbc:derby://localhost:1527/testInstance;create=true";
		Connection connection = null;

		// When I connect
		connection = DriverManager.getConnection(url, "test", "test");
		return connection;

	}

}
