package com.saba.sci.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

public class ProxyAuthenticationFilter extends AnonymousAuthenticationFilter {

	private static String key = "key";

	public ProxyAuthenticationFilter() {
		super(key);
	}

	public ProxyAuthenticationFilter(String key, Object principal,
			List<GrantedAuthority> authorities) {
		super(key, principal, authorities);
	}

	@Override
	protected Authentication createAuthentication(HttpServletRequest request) {

		// do increment and store somewhere

		return super.createAuthentication(request);
	}
}
