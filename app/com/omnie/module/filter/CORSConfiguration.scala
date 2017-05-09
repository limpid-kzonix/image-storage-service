package com.omnie.module.filter

import akka.util.ByteString
import play.api.libs.streams.Accumulator
import play.api.mvc._
import play.mvc.Http

import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
	* Created by limpid on 5/9/17.
	*/
class CORSConfiguration extends EssentialFilter {
	override def apply( nextFilter: EssentialAction ) = new EssentialAction {

		override def apply( v1: RequestHeader ): Accumulator[ByteString, Result] = {
			nextFilter( v1 )
				.map { result =>
					if ( v1.method.equals( "OPTIONS" ) ) {
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
