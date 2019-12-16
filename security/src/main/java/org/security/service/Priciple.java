package org.security.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Priciple implements UserDetails {
	private Long id;
	private String userName;
	private String password;
	private String role;
	private List<SimpleGrantedAuthority> list;

	public Priciple(Long id, String userName, String password, String role, List<SimpleGrantedAuthority> list) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.list = list;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static Priciple create(Details user) {
		return new Priciple(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getAuthorities()
				.stream().map(m -> new SimpleGrantedAuthority(m.toString())).collect(Collectors.toList()));
	}

	public List<SimpleGrantedAuthority> getList() {
		return list;
	}

	public void setList(List<SimpleGrantedAuthority> list) {
		this.list = list;
	}

}
