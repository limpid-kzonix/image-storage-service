package com.omnie.module.filter;

import com.google.inject.Inject;
import play.http.HttpFilters;
import play.mvc.EssentialFilter;

/**
 * Created by limpid on 5/4/17.
 */
public class Filters implements HttpFilters {

	private EssentialFilter[] filters;

	@Inject
	public Filters(LoggingFilter loggingFilter) {
		filters = new EssentialFilter[] { loggingFilter.asJava() };
	}

	public EssentialFilter[] filters() {
		return filters;
	}
}