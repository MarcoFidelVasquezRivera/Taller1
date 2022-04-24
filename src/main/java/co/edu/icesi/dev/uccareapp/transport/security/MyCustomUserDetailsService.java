package co.edu.icesi.dev.uccareapp.transport.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.icesi.dev.uccareapp.transport.model.UserApp;
import co.edu.icesi.dev.uccareapp.transport.repositories.UserRepository;

@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	private UserRepository ur;
	
	@Autowired
	public MyCustomUserDetailsService(UserRepository ur) {
		this.ur = ur;
	}
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		System.out.println("Username: "+name);
		
		UserApp userApp = ur.findByName(name).get();
		
		if (userApp != null) {
			
			User.UserBuilder builder = User.withUsername(name).password(userApp.getPassword()).roles(userApp.getType().toString());
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
}