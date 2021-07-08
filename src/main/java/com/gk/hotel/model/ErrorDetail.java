package com.gk.hotel.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorDetail {
	public final String detail;
	public final String message;

	public ErrorDetail(Exception ex, String detail) {
		this.message = ex.getLocalizedMessage();
		this.detail = detail;
	}
}
