package com.saba.sci.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.hibernate.annotations.Filter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component("corsFilter")
//@Order(298)
//@Filter(name = "corsFilter")
public class CORSFilter implements ContainerResponseFilter, javax.servlet.Filter {

//    @Override
//    public ContainerResponse filter(ContainerRequest creq, ContainerResponse cresp) throws IOException  {
//
//        cresp.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
//        cresp.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
//        cresp.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
//        cresp.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
//
//        return cresp;
//    }

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");

        return;
	}

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
	// TODO Auto-generated method stub
	
}
}
