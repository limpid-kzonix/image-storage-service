package com.omnie.module.filter;


import com.google.inject.Inject;
import play.filters.cors.CORSFilter;
import play.filters.hosts.AllowedHostsFilter;
import play.http.HttpFilters;
import play.mvc.EssentialFilter;


/**
 * Created by limpid on 5/4/17.
 */
public class Filters implements HttpFilters {


	private EssentialFilter[] filters;

	@Inject
	public Filters( LoggingFilter loggingFilter, CORSFilter corsFilter, AllowedHostsFilter allowedHostsFilter,
	                CORSCongiguration corsCongiguration ) {
		filters = new EssentialFilter[]{
				loggingFilter.asJava( ),
				corsFilter.asJava( ),
		        allowedHostsFilter.asJava(),
		        corsCongiguration.asJava()
		};
	}

	public EssentialFilter[] filters( ) {
		return filters;
	}
}
