/*
 * Copyright (c) 2022 Airtango.
 * All rights reserved.
 */

package org.vaadin.spring.tutorial.model;

/**
 *
 * @author fbahle
 */
public class ExampleDto {
	private String url;

	public ExampleDto(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
