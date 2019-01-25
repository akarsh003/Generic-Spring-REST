package com.ndg.springdemo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

	@SuppressWarnings("serial")
	Map<String, Entry<String, String[]>> usernames = new HashMap<String, Entry<String, String[]>>() {
		{
			put("manogna", new SimpleEntry<>("Manogna Lakshman", new String[] { "Leadership","QR" }));
			put("mythri", new SimpleEntry<>("Mythri MJ", new String[] { "QR","Processor" }));
			put("akarsha", new SimpleEntry<>("Akarsha KR", new String[] { "Processor","Leadership" }));
			put("ram", new SimpleEntry<>("Ramanathan", new String[] { "QR" }));
			put("then", new SimpleEntry<>("Thenmozhi", new String[] { "Processor" }));
			put("srikesh", new SimpleEntry<>("Srikesh Nagoji", new String[] { "Leadership" }));
		}
	};

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Entry<String, String[]> userinfo = usernames.get(username);
		String name = userinfo.getKey();
		List<GrantedAuthority> authorities = Arrays.stream(userinfo.getValue())
				.map(item -> new SimpleGrantedAuthority(item)).collect(Collectors.toList());
		UserDetails user = new User(name, "", true, true, true, true, authorities);

		return user;
	}

}