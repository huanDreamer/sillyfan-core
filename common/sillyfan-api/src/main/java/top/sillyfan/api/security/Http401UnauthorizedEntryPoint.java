package top.sillyfan.api.security;


import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Returns a 401 error code (Unauthorized) to the client.
 */
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

	private final Logger log = LoggerFactory
			.getLogger(Http401UnauthorizedEntryPoint.class);

	/**
	 * Always returns a 401 error code to the client.
	 * 
	 * {ExceptionTranslationFilter}
	 * 
	 */
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException ex)
			throws IOException, ServletException {
		
		Optional<String> targetUrl = Optional.ofNullable(request.getSession(false)).flatMap(session -> {
			return Optional.ofNullable(String.valueOf(session.getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY")));			
		});
		if(targetUrl.isPresent()){
			log.debug("Pre-authenticated entry point called. Rejecting access to: {}",targetUrl.get());
		}else{
			log.debug("Pre-authenticated entry point called. Rejecting access");
		}		
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
	}

}
