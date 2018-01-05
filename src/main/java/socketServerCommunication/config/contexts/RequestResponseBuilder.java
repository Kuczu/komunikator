package socketServerCommunication.config.contexts;

import socketServerCommunication.requests.RequestProcessor;
import socketServerCommunication.requests.RequestProcessorStep;
import socketServerCommunication.responses.ResponseProcessor;
import socketServerCommunication.responses.ResponseProcessorStep;

public class RequestResponseBuilder {
	public static RequestProcessor buildRequestProcessor(FlowContext flowContext) {
		RequestProcessor.Builder builder = new RequestProcessor.Builder();
		
		for (RequestProcessorStep step : flowContext.getRequestProcessorStepList()) {
			builder.addNextRequestBodyProcessorStep(step);
		}
		
		builder.setControllerHandler(flowContext.getControllerHandler());
		
		return builder.buildRequestProcessor();
	}
	
	public static ResponseProcessor buildResponseProcessor(FlowContext flowContext) {
		ResponseProcessor.Builder builder = new ResponseProcessor.Builder();
		
		for (ResponseProcessorStep step : flowContext.getResponseProcessorStepList()) {
			builder.addNextRequestBodyProcessorStep(step);
		}
		
		return builder.buildResponseProcessor();
	}
}
