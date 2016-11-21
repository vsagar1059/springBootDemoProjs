package demo.jpa.oauth2;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    
    //private static final String RESOURCE_ID = "my_rest_api";
    
    private static final Logger log = LoggerFactory.getLogger(ResourceServerConfiguration.class);

    @Autowired
    private DataSource dataSource;
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //resources.resourceId(RESOURCE_ID).tokenStore(tokenStore());
    	TokenStore tokenStore = new JdbcTokenStore(dataSource);
    	resources.tokenStore(tokenStore);
        log.info("ResourceServerSecurityConfigurer...");
    }
 
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
        anonymous().disable()
        .requestMatchers().antMatchers("/users/**")
        .and().authorizeRequests()
        .antMatchers("/users/**").access("hasRole('ROLE_ADMIN')")
        .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        
        log.info("http config for /user ");
    }
    
    

}
