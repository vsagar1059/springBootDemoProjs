package demo.jpa.oauth2;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * 
 */
@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

    
    @Autowired
    private DataSource dataSource;
    
 
    @Autowired
    private UserApprovalHandler userApprovalHandler;
 
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static String REALM="MY_OAUTH_REALM";
    
 
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
 
        //clients.jdbc(dataSource)
    	clients.inMemory()
            .withClient("my-client")
            .authorizedGrantTypes("password", "refresh_token", "implicit")
            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
            .scopes("read", "write", "trust")
            .secret("secret")
            .accessTokenValiditySeconds(600).//Access token is only valid for 10 minutes.
            refreshTokenValiditySeconds(1200);//Refresh token is only valid for 20 minutes.
    }
 
    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)
            throws Exception {
        //security.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authorizationCodeServices(authorizationCodeServices())
                .authenticationManager(authenticationManager).tokenStore(tokenStore())
                .approvalStoreDisabled();
    }
    
    @Bean
    public TokenStore tokenStore() {
        //DataSource tokenDataSource = DataSourceBuilder.create().driverClassName("org.sqlite.JDBC").url(oauthDbJdbc).build();
        return new JdbcTokenStore(dataSource);
    }
    
    /*@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager);
        //endpoints.tokenStore(tokenStore);
    }
 
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.realm(REALM+"/client");
    }*/
    
}
