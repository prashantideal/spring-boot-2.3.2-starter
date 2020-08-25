package com.group.module.common;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.group.module.exception.ApiException;
import com.group.module.exception.Codes;


/**
 * @author prashant.mishra1
 *
 */
public class CustomErrorAttributes extends DefaultErrorAttributes {
	
	private Logger logger = LoggerFactory.getLogger(CustomErrorAttributes.class);
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		return super.resolveException(request, response, handler, ex);
	}

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		Map<String, Object> errorAttributes = new LinkedHashMap<>();
		try {
			
			Error error = new Error();
			String code = getCode(webRequest);
			if(!"0".equalsIgnoreCase(code)) {
				error.setCode(getNumber(code));
			}
			addStatus(error, webRequest);
			addMessage(error, webRequest);
			errorAttributes.put("error", error);
			errorAttributes.put("success", false);
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			return super.getErrorAttributes(webRequest, options);
		}
		return errorAttributes;
	}
	
	private void addMessage(Error error, WebRequest webRequest) throws NoSuchMessageException, Exception {
		if(error.getCode() == 4444 || error.getCode() == 5555)
		{
			Throwable t = super.getError(webRequest);
			t.printStackTrace();
			String msg = t.getMessage();
			if(StringUtils.hasText(msg)){
				error.setMessage(msg);
			}
		}
		
		if(!StringUtils.hasText(error.getMessage())) {
			error.setMessage(messageSource.getMessage(String.valueOf(error.getCode()), getPlaceHolders(webRequest), Locale.getDefault()));
		}
			
	}
	
	private String getCode(WebRequest request) throws Exception {
		Throwable error = super.getError(request);
		logger.error("Error is "+error);
		if(error instanceof ResponseStatusException) {
			return ((ResponseStatusException) error).getReason();
		}
		else {
			Object message = getAttribute(request, "javax.servlet.error.message");
			if ((!StringUtils.isEmpty(message))
					&& !(error instanceof BindingResult)) {
				return (StringUtils.isEmpty(message) ? "No message available" : message.toString());
			}
		}
		return "0";
	}
	
	private int getNumber(String value) {
		try {
			return Integer.parseInt(value);
		}catch(NumberFormatException nfe) {
			return Integer.parseInt(Codes.GENERIC_500);
		}
	}
	
	private Object[] getPlaceHolders(WebRequest request) throws Exception {
		Throwable error = super.getError(request);
		if(error instanceof ApiException) {
			return ((ApiException) error).getPlaceHolderValues();
		}
		return null;
	}
	
	private void addStatus(Error error, RequestAttributes requestAttributes) {
		Integer status = getAttribute(requestAttributes, "javax.servlet.error.status_code");
		if (status == null) {
			error.setStatusCode(999);
			return;
		}
		error.setStatusCode(status);
		handleInternalExceptions(error);
	}
	
	private void handleInternalExceptions(Error error) {
		if(error.getCode() == 0) {
			if(HttpStatus.resolve(error.getStatusCode()) == HttpStatus.BAD_REQUEST)
				error.setCode(4444);
			else if(HttpStatus.resolve(error.getStatusCode()) == HttpStatus.INTERNAL_SERVER_ERROR)
				error.setCode(5555);
		}
	}

	
	@SuppressWarnings("unchecked")
	private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
		return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}
	
}
