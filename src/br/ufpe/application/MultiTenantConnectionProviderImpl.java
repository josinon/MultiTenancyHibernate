package br.ufpe.application;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider;

public class MultiTenantConnectionProviderImpl implements
		MultiTenantConnectionProvider{ 

	private static final long serialVersionUID = 1L;
	private final ConnectionProvider connectionProvider = ConnectionProviderBuilder
			.buildConnectionProvider("cliente1");

	@Override
	public Connection getAnyConnection() throws SQLException {
		return connectionProvider.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connectionProvider.closeConnection(connection);
	}

	@Override
	public Connection getConnection(String tenantIdentifier)
			throws SQLException {
		final Connection connection = getAnyConnection();
		try {
			connection.createStatement().execute("USE " + tenantIdentifier);
		} catch (SQLException e) {
			throw new HibernateException(
					" Não pode alterar a conexão para o esquema especificado ["
							+ tenantIdentifier + "]", e);
		}
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection)
			throws SQLException {
		try {
			connection.createStatement().execute("USE " + tenantIdentifier);
		} catch (SQLException e) {
			throw new HibernateException(
					"Could not alter JDBC connection to specified schema ["
							+ tenantIdentifier + "]", e);
		}
		connectionProvider.closeConnection(connection);
	}

	@Override
	public boolean isUnwrappableAs(@SuppressWarnings("rawtypes") Class arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsAggressiveRelease() {
		// TODO Auto-generated method stub
		return false;
	}
}