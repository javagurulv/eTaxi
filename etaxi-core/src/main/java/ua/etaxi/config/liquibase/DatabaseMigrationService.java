package ua.etaxi.config.liquibase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseMigrationService {

	private static final String MAIN_CHANGE_LOG_FILE = "liquibase/db.changelog-master.xml";


	public void update(DataSource dataSource, String contexts) {
		Connection connection = null;
		try {
			connection = tryUpdate(dataSource, contexts);
		} catch (Exception e) {
			throw new DatabaseMigrationException("Error while updating the ua.etaxi.core.database", e);
		} finally {
			tryClose(connection);
		}
	}

	private Connection tryUpdate(DataSource dataSource, String contexts) throws Exception {
		Connection connection = dataSource.getConnection();

		DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
		Database database = databaseFactory.findCorrectDatabaseImplementation(new JdbcConnection(connection));

		String fileName = MAIN_CHANGE_LOG_FILE;
		Liquibase liquibase = new Liquibase(fileName, new ClassLoaderResourceAccessor(), database);

		liquibase.update(contexts);
		return connection;
	}

	private void tryClose(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DatabaseMigrationException("Error while updating the ua.etaxi.core.database", e);
			}
		}
	}

}
