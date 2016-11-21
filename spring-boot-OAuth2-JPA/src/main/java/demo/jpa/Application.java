package demo.jpa;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class Application {

	/*private static final String CREATE_OAUTH_ACCESS_TOKEN_SQL = "create table if not exists oauth_access_token ("+
            "token_id VARCHAR(256),"+
            "token LONGVARBINARY,"+
            "authentication_id VARCHAR(256),"+
            "user_name VARCHAR(256),"+
            "client_id VARCHAR(256),"+
            "authentication LONGVARBINARY,"+
            "refresh_token VARCHAR(256)"+
            ");";
	
	private static final String CREATE_OAUTH_REFRESH_TOKEN_SQL = "create table if not exists oauth_refresh_token ("+
            "token_id VARCHAR(256),"+
            "token LONGVARBINARY,"+
            "authentication_id VARCHAR(256),"+
            "user_name VARCHAR(256),"+
            "client_id VARCHAR(256),"+
            "authentication LONGVARBINARY,"+
            "refresh_token VARCHAR(256)"+
            ");";

    private static final String DELETE_TOKENS_SQL = "delete from oauth_access_token";
    private static final String DELETE_REF_TOKENS_SQL = "delete from oauth_refresh_token";

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void setUpTokenDatasource() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(CREATE_OAUTH_ACCESS_TOKEN_SQL);
        jdbcTemplate.execute(CREATE_OAUTH_REFRESH_TOKEN_SQL);
    }
    
    @PreDestroy
    public void cleanUpDatasource(){
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	jdbcTemplate.execute(DELETE_TOKENS_SQL);
    	jdbcTemplate.execute(DELETE_REF_TOKENS_SQL);
    }
	*/
	
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}