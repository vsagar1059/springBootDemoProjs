package demo.jpa;

import java.util.Properties;

import javax.sql.DataSource;

import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Contains database configurations.
 */
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EntityScan
@EnableJpaRepositories
public class DatabaseConfig {


   @Value("classpath:schema.sql")
   private Resource schemaScript;

   private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
	
  /**
   * DataSource definition for database connection. Settings are read from
   * the application.properties file (using the env object).
   */
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("db.driver"));
    dataSource.setUrl(env.getProperty("db.url"));
    dataSource.setUsername(env.getProperty("db.username"));
    dataSource.setPassword(env.getProperty("db.password"));
    return (DataSource)dataSource;
  }

  /**
   * Declare the JPA entity manager factory.
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactory =
        new LocalContainerEntityManagerFactoryBean();
    
    entityManagerFactory.setDataSource(dataSource());
    
    // Classpath scanning of @Component, @Service, etc annotated class
        
    // Vendor adapter
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
    
    // Hibernate properties
    Properties additionalProperties = new Properties();
    additionalProperties.put(
        "hibernate.dialect", 
        env.getProperty("hibernate.dialect"));
    additionalProperties.put(
        "hibernate.show_sql", 
        env.getProperty("hibernate.show_sql"));
    additionalProperties.put(
        "hibernate.hbm2ddl.auto", 
        env.getProperty("hibernate.hbm2ddl.auto"));
    entityManagerFactory.setJpaProperties(additionalProperties);
    
    return entityManagerFactory;
  }

  /**
   * Declare the transaction manager.
   */
  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = 
        new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(
        entityManagerFactory().getObject());
    return transactionManager;
  }
  
   
  @Bean
  public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
      DataSourceInitializer initializer = new DataSourceInitializer();
      initializer.setDataSource(dataSource);
      initializer.setDatabasePopulator(databasePopulator());
      return initializer;
  }
   
  private DatabasePopulator databasePopulator() {
      ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
      log.info("Going to execute the schema...");
      populator.addScript(schemaScript);
      log.info("executed the schema...");
      return populator;
  }
   
 

/*  @PostConstruct
  @Autowired
  public void setUpTokenDatasource(DataSource dataSource) {
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
      jdbcTemplate.execute(CREATE_OAUTH_ACCESS_TOKEN_SQL);
      jdbcTemplate.execute(DELETE_TOKENS_SQL);
  }
  */
  
  
  /**
   * PersistenceExceptionTranslationPostProcessor is a bean post processor
   * which adds an advisor to any bean annotated with Repository so that any
   * platform-specific exceptions are caught and then rethrown as one
   * Spring's unchecked data access exceptions (i.e. a subclass of 
   * DataAccessException).
   */
  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  /**
   * H2 DB configuration to access h2 console url.
   * http://localhost:8080/console
   * @return
   */
  
  @Bean
	public ServletRegistrationBean h2servletRegistration() {
	    ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
	    registration.addUrlMappings("/console/*");
	    return registration;
	}
  
  
  @Autowired
  private Environment env;

  /*@Autowired
  private DataSource dataSource;

  @Autowired
  private LocalContainerEntityManagerFactoryBean entityManagerFactory;*/

  private static final String CREATE_OAUTH_ACCESS_TOKEN_SQL = "create table if not exists oauth_access_token ("+
          "token_id VARCHAR(256),"+
          "token LONGVARBINARY,"+
          "authentication_id VARCHAR(256),"+
          "user_name VARCHAR(256),"+
          "client_id VARCHAR(256),"+
          "authentication LONGVARBINARY,"+
          "refresh_token VARCHAR(256)"+
          ");";

  private static final String DELETE_TOKENS_SQL = "delete from oauth_access_token";



} 
