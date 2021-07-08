package com.gk.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.gk.hotel.exception.DataFormatException;
import com.gk.hotel.exception.ResourceNotFoundException;
import com.gk.hotel.model.ErrorDetail;

import javax.servlet.http.HttpServletResponse;

public abstract class AbstractRestHandler implements ApplicationEventPublisherAware {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	protected ApplicationEventPublisher eventPublisher;

	protected static final String DEFAULT_PAGE_SIZE = "100";
	protected static final String DEFAULT_PAGE_NUM = "0";

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataFormatException.class)
	public @ResponseBody ErrorDetail handleDataStoreException(DataFormatException ex, WebRequest request,
			HttpServletResponse response) {
		log.info("Converting Data Store exception: " + ex.getMessage());

		return new ErrorDetail(ex, "Error occured");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public @ResponseBody ErrorDetail handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request,
			HttpServletResponse response) {
		log.info("ResourceNotFoundException handler:" + ex.getMessage());

		return new ErrorDetail(ex, "Couldn't find it.");
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;
	}

	public static <T> T checkResourceFound(final T resource) {
		if (resource == null) {
			throw new ResourceNotFoundException("Resource not found");
		}
		return resource;
	}

}