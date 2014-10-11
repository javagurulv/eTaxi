package ua.etaxi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Viktor on 07/09/2014.
 */
@Configuration
@ComponentScan(basePackages = {"ua.etaxi"})
@Import({DataSourceConfig.class, HibernateConfig.class,
        TransactionConfig.class, AppPropertiesConfig.class,})
public class CoreConfig {


}
