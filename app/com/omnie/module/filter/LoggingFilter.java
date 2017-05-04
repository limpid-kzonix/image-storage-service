package com.omnie.module.filter;

import akka.stream.Materializer;
import com.google.inject.Inject;
import play.Logger;
import play.mvc.Filter;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * Created by limpid on 5/4/17.
 */
public class LoggingFilter extends Filter {

	@Inject
	public LoggingFilter(Materializer mat ) {
		super(mat);
	}

	@Override
	public CompletionStage<Result > apply(
			Function<Http.RequestHeader, CompletionStage<Result>> nextFilter,
			Http.RequestHeader requestHeader ) {
		long startTime = System.currentTimeMillis();
		return nextFilter.apply(requestHeader).thenApply(result -> {
			long endTime = System.currentTimeMillis();
			long requestTime = endTime - startTime;

			Logger.info( "{} {} took {}ms and returned {}",
			             requestHeader.method(), requestHeader.uri(), requestTime, result.status() );

			return result.withHeader("Request-Time", "" + requestTime);
		});
	}
}
