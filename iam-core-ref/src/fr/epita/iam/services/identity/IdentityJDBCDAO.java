package fr.epita.iam.services.identity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.EntityCreationException;
import fr.epita.iam.exceptions.EntityDeletionException;
import fr.epita.iam.exceptions.EntityUpdateException;
import fr.epita.iam.services.conf.ConfKey;
import fr.epita.iam.services.conf.ConfigurationService;
import fr.epita.iam.ui.ConsoleOperations;

public class IdentityJDBCDAO<PreparedStament> implements IdentityDAO {

	static {
		IdentityDAOFactoryDynamicRegistration.registeredDAOs.put(ConfKey.DB_BACKEND.getKey(), new IdentityJDBCDAO());

	}

	private static Connection getConnection() throws SQLException {
		// Given this context
		final String url = ConfigurationService.getProperty(ConfKey.DB_URL);
		Connection connection = null;

		// When I connect
		connection = DriverManager.getConnection(url, ConfigurationService.getProperty(ConfKey.DB_USER),

				ConfigurationService.getProperty(ConfKey.DB_PASSWORD));
		return connection;
		

	}

	@Override
	public void create(Identity identity) throws EntityCreationException {
		Connection connection = null;
		try {
			connection = getConnection();
			final PreparedStatement pstmt = connection
					.prepareStatement(ConfigurationService.getProperty(ConfKey.IDENTITY_INSERT_QUERY));
			pstmt.setString(1, identity.getDisplayName());
			pstmt.setString(2, identity.getEmail());
			pstmt.setString(3, identity.getUid());
			pstmt.execute();
			
			
			pstmt.close();
			
			connection.close();
		} catch (final SQLException e) {
			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e1) {
					System.out.println(e1.getMessage());
				}
			}
			final EntityCreationException exception = new EntityCreationException(identity, e);
			throw exception;
		}
	}

	@Override
	public void update(Identity identity) throws EntityUpdateException {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement stmts = connection.prepareStatement("SELECT * FROM IDENTITIES WHERE IDENTITY_UID=?");
			stmts.setString(1,identity.getUid());
			final ResultSet result = stmts.executeQuery();
		
			if(result.next()){
			PreparedStatement pstmt = connection.prepareStatement("UPDATE IDENTITIES SET IDENTITY_DISPLAYNAME=?, IDENTITY_EMAIL=? where identity_uid=?");

			pstmt.setString(1, identity.getDisplayName());
			pstmt.setString(2, identity.getEmail());
			pstmt.setString(3, identity.getUid());
			pstmt.executeUpdate();
			System.out.println("Updated " + identity.getDisplayName() + " Sucesfully ");
			
			connection.close();
			
			} else
			{
				System.out.println(identity.getUid() + "Not Found !!");
			} 
		}catch (final SQLException e) {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println(identity.getDisplayName() + "Failed" + e.toString());
			EntityUpdateException exception = new EntityUpdateException();
		}
	}

	@Override
	public void delete(Identity identity) throws EntityDeletionException {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("Select * from IDENTITIES where IDENTITY_DISPLAYNAME=?");
			stmt.setString(1, identity.getDisplayName());
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				stmt = connection.prepareStatement("Delete from IDENTITIES where IDENTITY_DISPLAYNAME=?");
				stmt.setString(1, identity.getDisplayName());
				stmt.executeUpdate();
				System.out.println(identity.getDisplayName() + " deleted from Identity table");
				connection.close();
			} else {
				System.out.println(identity.getDisplayName() + " not found !!");
			}
		} catch (final SQLException e) {
			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	@Override
	public Identity getById(Serializable id) {
		final Identity identity = new Identity();

		return identity;
	}

	@Override
	public List<Identity> search(Identity criteria) {
		final List<Identity> list = new ArrayList<>();

		Connection connection = null;
		try {
			connection = getConnection();
			final PreparedStatement pstmt = connection
					.prepareStatement("select IDENTITY_DISPLAYNAME, IDENTITY_EMAIL, IDENTITY_UID from IDENTITIES"
							+ " where IDENTITY_DISPLAYNAME like ? or IDENTITY_EMAIL like ?");
			pstmt.setString(1, "%" + criteria.getDisplayName() + "%");
			pstmt.setString(2, "%" + criteria.getEmail() + "%");
			final ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				final String displayName = rs.getString("IDENTITY_DISPLAYNAME");
				final String email = rs.getString("IDENTITY_EMAIL");
				final String uid = rs.getString("IDENTITY_UID");
				final Identity identity = new Identity(displayName, uid, email);
				list.add(identity);

			}

			pstmt.close();
			connection.close();
			
		} catch (final SQLException e) {
			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e2) {
					System.out.println(e2.getMessage());
				}
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.epita.iam.services.IdentityDAO#healthCheck()
	 */
	@Override
	public boolean healthCheck() {
		try {
			final Connection connection = getConnection();
			connection.close();
			return true;
		} catch (final SQLException sqle) {
			System.out.println(sqle.getMessage());

		}
		return false;

	}

}
