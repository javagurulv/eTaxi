package ua.etaxi.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.etaxi.config.liquibase.LiquibaseSchemaUpdate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by Viktor on 07/09/2014.
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    private LiquibaseSchemaUpdate liquibaseSchemaUpdate;


    @Bean(destroyMethod = "close")
    public DataSource dataSource(
            @Value("${ua.etaxi.core.database.driverClass}") String driver,
            @Value("${ua.etaxi.core.database.jdbcUrl}") String url,
            @Value("${ua.etaxi.core.database.userName}") String user,
            @Value("${ua.etaxi.core.database.password}") String password) throws PropertyVetoException {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDefaultAutoCommit(false);

        liquibaseSchemaUpdate.execute(dataSource);

        return dataSource;
    }

}
