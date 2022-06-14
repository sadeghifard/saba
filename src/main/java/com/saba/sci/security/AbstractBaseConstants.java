package com.saba.sci.security;

public class AbstractBaseConstants {

	public static final String ROUTE_ROOT = "/sci/";
	public static final String ACCESS_DENIED_PAGE = ROUTE_ROOT + "accessDenied";
	public static final String SESSION_ERROR_INVALID_PAGE = ROUTE_ROOT + "session"+ROUTE_ROOT +"error"+ROUTE_ROOT +"invalid";
	public static final String SESSION_ERROR_EXPIRED_PAGE = ROUTE_ROOT + "session"+ROUTE_ROOT +"error"+ROUTE_ROOT +"expired";
	public static final String ROUTE_LOGIN_REST = ROUTE_ROOT + "login";
	public static final String ROUTE_LOGIN_PAGE = ROUTE_ROOT + "loginPage";
	public static final String ROUTE_LOGOUT = ROUTE_ROOT + "logout";
	public static final String ROUTE_DASHBOARD = ROUTE_ROOT + "dashboard";
}
