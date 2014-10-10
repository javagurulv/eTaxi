package ua.etaxi.liquibase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;

@Component
public class LiquibaseSchemaUpdate {

	private static final Logger log = LoggerFactory.getLogger(LiquibaseSchemaUpdate.class);
	
	@Autowired
	private DatabaseMigrationService databaseMigrationService;

	@Value("${liquibase.update:true}")
	private boolean enabled;

	@Value("${liquibase.context:main}")
	private String context;

	
	public void execute(DataSource dataSource) {
		if (enabled) {
			updateSchema(dataSource);
		}
	}

	private void updateSchema(DataSource dataSource) {
		StopWatch watch = watchTime();

		databaseMigrationService.update(dataSource, context);

		log.info("Database populated in {} ms", elapsedTimeInMillis(watch));
	}

	private StopWatch watchTime() {
		StopWatch watch = new StopWatch();
		watch.start();
		return watch;
	}

	private long elapsedTimeInMillis(StopWatch watch) {
		watch.stop();
		return watch.getTotalTimeMillis();
	}
	
}
