package com.group.module.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.group.module.model.SessionInfo;
import com.group.module.repository.AuthTokenRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private AuthTokenRepository sessionTokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {


		try {
			String token = parseToken(request);
			log.info("In doFilterInternal: parseToken token in  header check is {}",  token);
			if (StringUtils.hasText(token)) {
				SessionInfo sessionInfo = sessionTokenRepository.findSession(token);
				if(sessionInfo !=null) {

					log.info("In doFilterInternal: validateToken succcess sessionInfo is {}", sessionInfo);

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							sessionInfo.getUser(), null, sessionInfo.getUser().getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					log.info("In doFilterInternal: validateToken succcess userDetails is success{}");	
				}	
			}
		} catch (RuntimeException e) {
			logger.error("Cannot set user authentication due to RuntimeException: {}", e);
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

	public static String parseToken(HttpServletRequest request) {
		log.info("In doFilterInternal: parseToken ");
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			log.info("In doFilterInternal: parseToken in header check");
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

}
