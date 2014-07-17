package br.ufpe.application;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import br.ufpe.modelo.Pessoa;

//Classe de aplica��o
public class MultiTenancyEsquemaExclusivo {

	// F�brica de sess�es
	private static SessionFactory sessionFactory;

	public static void main(String[] args) {

		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		sessionFactory = configureSessionFactory();
		Session session = sessionFactory.withOptions()
				.tenantIdentifier("tenant4").openSession();
		List<Pessoa> result =  session.createCriteria(Pessoa.class).list();
		pessoas.addAll(result);
		
		
		session = sessionFactory.withOptions().tenantIdentifier("cliente1").openSession();
		result =  session.createCriteria(Pessoa.class).list();
		pessoas.addAll(result);
		
		session = sessionFactory.withOptions().tenantIdentifier("cliente2").openSession();
		result =  session.createCriteria(Pessoa.class).list();
		pessoas.addAll(result);
		
		
		session = sessionFactory.withOptions().tenantIdentifier("cliente3").openSession();
		result =  session.createCriteria(Pessoa.class).list();
		pessoas.addAll(result);
		
	}
	
	private static void inserts(){
		// Inser��o de um registro no esquema cliente1
		System.out.println("Grando dados no esquema [ cliente1 ]");
		Session session = sessionFactory.withOptions()
				.tenantIdentifier("cliente1").openSession();

		session.beginTransaction();
		Pessoa pessoa = new Pessoa();
		pessoa.setId(2L);
		pessoa.setNome("Mateus");
		session.save(pessoa);
		session.getTransaction().commit();
		session.close();

		// Inser��o de um registro no esquema cliente2
		System.out.println("Grando dados no esquema [ cliente2 ]");
		session = sessionFactory.withOptions().tenantIdentifier("cliente2")
				.openSession();
		session.beginTransaction();
		Pessoa pessoa2 = new Pessoa();
		pessoa2.setId(2L);
		pessoa2.setNome("Mateus");
		session.save(pessoa2);
		session.getTransaction().commit();
		session.close();
//		
		
		// Inser��o de um registro no esquema cliente2
		System.out.println("Grando dados no esquema [ cliente2 ]");
		session = sessionFactory.withOptions().tenantIdentifier("tenant4")
				.openSession();
		session.beginTransaction();
		Pessoa pessoa3 = new Pessoa();
		pessoa3.setId(2L);
		pessoa3.setNome("Josino");
		session.save(pessoa3);
		session.getTransaction().commit();
		session.close();
	}

	// M�todo que realiza as configura��es da f�brica de sess�es
	private static SessionFactory configureSessionFactory()
			throws HibernateException {

		Configuration configuration = new Configuration().configure();
		ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();
		serviceRegistryBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = serviceRegistryBuilder
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

}
