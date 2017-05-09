package com.omnie.module.filter

import play.api.mvc.{EssentialAction, EssentialFilter, RequestHeader, Results}
import play.mvc.Http

/**
	* Created by limpid on 5/9/17.
	*/
class CORSConfiguration extends EssentialFilter {
	def apply( nextFilter: EssentialAction ) = new EssentialAction {
		def apply( requestHeader: RequestHeader ) = {
			nextFilter( requestHeader )
				.map { result =>
					if ( requestHeader.method.equals( "OPTIONS" ) ) {
						Results.Ok.withHeaders(
							Http.HeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
							Http.HeaderNames.ACCESS_CONTROL_ALLOW_HEADERS -> "X-Requested-With, Accept, Content-Type",
							Http.HeaderNames.ACCESS_CONTROL_ALLOW_METHODS -> "HEAD,GET,POST,PUT,PATCH,DELETE" )
					} else {
						result.withHeaders(
							Http.HeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
							Http.HeaderNames.ACCESS_CONTROL_ALLOW_HEADERS -> "X-Requested-With, Accept, Content-Type",
							Http.HeaderNames.ACCESS_CONTROL_ALLOW_METHODS -> "HEAD,GET,POST,PUT,PATCH,DELETE",
							Http.HeaderNames.ACCESS_CONTROL_EXPOSE_HEADERS -> "X-Custom-Header-To-Expose" )
					}
				}
		}
	}
}
