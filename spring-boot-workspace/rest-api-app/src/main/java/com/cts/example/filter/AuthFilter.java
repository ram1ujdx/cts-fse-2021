package com.cts.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.example.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

	@Autowired
	JwtTokenUtil tokenUtil;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("Request Intercepted");
		String authHeader=request.getHeader("Authorization");
		log.info("Header - "+authHeader);
		String username=null;
		if(authHeader !=null) {
			username=tokenUtil.getUsernameFromToken(authHeader);
		}
		
		if(username!=null) {
			UserDetails userDetails=userDetailsService.loadUserByUsername(username);
			if(tokenUtil.validateToken(authHeader, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken=
							new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
			
		}
		filterChain.doFilter(request, response);
		
	}

}
