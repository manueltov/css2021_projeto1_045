package dbutils;

import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseUtils {
	
	private DatabaseUtils(){}

	public static void clearTables() throws IOException, SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("domain-model-jpa");
		new RunSQLScript().runScript(emf, "dbScripts/clearTables.sql");
		emf.close();		
	}
	
	public static void populateTables() throws IOException, SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("domain-model-jpa");
		new RunSQLScript().runScript(emf, "dbScripts/populateTables.sql");
		emf.close();		
	}

	public static void dropTables() throws IOException, SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("domain-model-jpa");
		new RunSQLScript().runScript(emf, "dbScripts/dropTables.sql");
		emf.close();	
	}

	public static void runScript() throws IOException, SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("domain-model-jpa");
		new RunSQLScript().runScript(emf, "dbScripts/dropFK.sql");
		emf.close();	
	}
}
