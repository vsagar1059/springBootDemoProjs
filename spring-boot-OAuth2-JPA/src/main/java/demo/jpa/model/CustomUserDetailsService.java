package demo.jpa.model;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import static java.util.Arrays.asList;


public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserDao userDao; 
	
	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	public CustomUserDetailsService() {
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("Calling custom user details LoadByUserName..");
		User user = userDao.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Username " + username + " not"
					+"found");
		}
		
		
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
				getGrantedAuthorities(username));
	}
	
	
	private Collection<? extends GrantedAuthority> getGrantedAuthorities(String
			username) {
			Collection<? extends GrantedAuthority> authorities;
			if (username.equals("John")) {
			authorities = asList(() -> "ROLE_ADMIN", () -> "ROLE_BASIC");
			} else {
			authorities = asList(() -> "ROLE_BASIC");
			}
			return authorities;
	}
}
