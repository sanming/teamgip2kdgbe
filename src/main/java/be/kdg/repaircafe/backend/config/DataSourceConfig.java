package be.kdg.repaircafe.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * Responsible for creating the H2 Datasource for a Server Mode In-Memory Database
 * using ;DB_CLOSE_DELAY=-1 options to keep data for life-time of JVM process.
 */
@Configuration
public class DataSourceConfig
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/datasource/

    @Value("classpath:/be/kdg/repaircafe/backend/datasources/address.sql")
    private Resource address;

    @Value("classpath:/be/kdg/repaircafe/backend/datasources/person.sql")
    private Resource person;

    @Value("classpath:/be/kdg/repaircafe/backend/datasources/user.sql")
    private Resource user;

    @Value("classpath:/be/kdg/repaircafe/backend/datasources/role.sql")
    private Resource role;

    @Value("classpath:/be/kdg/repaircafe/backend/datasources/item.sql")
    private Resource item;

    @Value("classpath:/be/kdg/repaircafe/backend/datasources/repairdetails.sql")
    private Resource repairdetails;

    @Value("classpath:/be/kdg/repaircafe/backend/datasources/repair.sql")
    private Resource repair;

    @Value("classpath:/be/kdg/repaircafe/backend/datasources/bid.sql")
    private Resource bid;

    // name of bean is superfluous in this case since method name is the same as bean name
    @Bean(name = "datasource")
    public DriverManagerDataSource datasource()
    {
        // http://baptiste-wicht.com/posts/2010/08/presentation-usage-h2-database-engine.html
        // http://www.h2database.com/html/features.html#in_memory_databases
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");

        // datasource in-memory
        dataSource.setUrl("jdbc:h2:mem:RepairCafe;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

        // datasource remote tcp server
        // in this case:also start H2 server from commandline with: java -cp h2*.jar org.h2.tools.Server
        // dataSource.setUrl("jdbc:h2:tcp://localhost/~RepairCafe;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

        // optional
        //dataSource.setUsername("sa");
        //dataSource.setPassword("sa");
        return dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource)
    {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        //populator.addScript(schema);
        populator.addScript(address);
        populator.addScript(person);
        populator.addScript(user);
        populator.addScript(role);
        populator.addScript(item);
        populator.addScript(repairdetails);
        populator.addScript(repair);
        populator.addScript(bid);

        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
