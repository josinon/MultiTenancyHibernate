package br.ufpe.application;

import java.io.IOException;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class JpaSchemaExport {
	public static void main(String[] args) throws IOException {
		execute();
	}

	public static void execute() {
		System.out.println("Starting schema export");
		Configuration configuration = new Configuration()
		.configure();
			
		SchemaExport schemaExport = new SchemaExport(configuration);
		schemaExport.create(true, true);
		System.out.println("Schema exported");
	}
}
