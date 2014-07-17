package br.ufpe.application;

import java.util.Properties;

import org.hibernate.cfg.Environment;
import org.hibernate.service.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

//Classe respons�vel por prover conex�o com o banco de dados utilizado a partir de configura��es e propriedades
public class ConnectionProviderBuilder {
	//Constantes utilizadas para configura��o da conex�o com a base de dados
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/cliente1";
	public static final String USER = "root";
	public static final String PASS = "BHU*nji9";
	
	//M�todo respons�vel por montar um arquivo de propriedades com os dados do banco utilizado
	public static Properties getConnectionProviderProperties(String dbName) {
		Properties props = new Properties(null);
		props.put(Environment.DRIVER, DRIVER);
		props.put(Environment.URL, String.format(URL, dbName));
		System.out.println(String.format(URL, dbName));
		props.put(Environment.USER, USER);
		props.put(Environment.PASS, PASS);
		return props;
	}

	//m�todo respons�vel por construir um provedor de conex�es que usa o DriverManager diretamente para abrir conex�es e oferece um pool de conex�o muito rudimentar.
	private static DriverManagerConnectionProviderImpl buildConnectionProvider(
			Properties props, final boolean allowAggressiveRelease) {
		DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl() {
			public boolean supportsAggressiveRelease() {
				return allowAggressiveRelease;
			}
		};
		connectionProvider.configure(props);
		return connectionProvider;
	}

	public static ConnectionProvider buildConnectionProvider(String dbName) {
		
		return buildConnectionProvider(getConnectionProviderProperties(dbName), true);
	}
	
	

}