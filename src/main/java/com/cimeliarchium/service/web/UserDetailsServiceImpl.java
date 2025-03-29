package com.cimeliarchium.service.web;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cimeliarchium.repository.dao.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepo;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	/**
	 * Method used for authentication checks
	 * 
	 * @param username
	 *            the username
	 * @return UserDetails
	 * @throws UsernameNotFoundException
	 *             the exception
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Obtain User by username
		final com.cimeliarchium.model.dao.User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		// Add GrantedAuthorities
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("public"));
		if (user.getHasAdminRole()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
		}
		return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}
}