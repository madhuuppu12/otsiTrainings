package org.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDetailsRepo userRepo;
	@Autowired
	private PasswordEncoder paaswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Details> user1 = userRepo.findByUsername(userName);
		user1.orElseThrow(() -> new UsernameNotFoundException("user not found: " + userName));

		return Priciple.create(user1.get());
	}

	public void saveuser(UserVo user) {
		Details details = new Details();
		String password = paaswordEncoder.encode(user.getPassword());
		details.setUsername(user.getUsername());
		details.setRole(user.getRole());

		details.setPassword(password);
		userRepo.save(details);
	}

}
