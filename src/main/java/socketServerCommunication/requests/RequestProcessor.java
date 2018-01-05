package socketServerCommunication.requests;

import socketServerCommunication.utils.ControllerHandler;

import java.util.LinkedList;
import java.util.List;

public class RequestProcessor {
	private final List<RequestProcessorStep> requestProcessorStepList;
	private final ControllerHandler controllerHandler;
	
	private RequestProcessor(List<RequestProcessorStep> requestProcessorStepList, ControllerHandler controllerHandler) {
		this.requestProcessorStepList = requestProcessorStepList;
		this.controllerHandler = controllerHandler;
	}
	
	public Request proceed(Request request) {
		for (RequestProcessorStep step : requestProcessorStepList) {
			step.proceed(request);
		}
		
		return request;
	}
	
	public static class Builder {
		private List<RequestProcessorStep> processorStepList;
		private ControllerHandler controllerHandler;
		
		public Builder() {
			this.processorStepList = new LinkedList<>();
		}
		
		public void addNextRequestBodyProcessorStep(RequestProcessorStep requestProcessorStep) {
			if (requestProcessorStep != null) {
				processorStepList.add(requestProcessorStep);
			}
		}
		
		public void setControllerHandler(ControllerHandler controllerHandler) {
			this.controllerHandler = controllerHandler;
		}
		
		public RequestProcessor buildRequestProcessor() {
			return new RequestProcessor(processorStepList, controllerHandler);
		}
	}
}
