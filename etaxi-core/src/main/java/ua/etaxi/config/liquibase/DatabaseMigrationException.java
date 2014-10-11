package ua.etaxi.config.liquibase;

public class DatabaseMigrationException extends RuntimeException {

	public DatabaseMigrationException(String message, Throwable cause) {
		super(message, cause);
	}
}
