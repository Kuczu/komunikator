package socketServerCommunication.responses;

import java.util.LinkedList;
import java.util.List;

public class ResponseProcessor {
	private final List<ResponseProcessorStep> requestProcessorStepList;
	
	private ResponseProcessor(List<ResponseProcessorStep> requestProcessorStepList) {
		this.requestProcessorStepList = requestProcessorStepList;
	}
	
	public void proceed(Response response) {
		for (ResponseProcessorStep step : requestProcessorStepList) {
			step.proceed(response);
		}
	}
	
	public static class Builder {
		private List<ResponseProcessorStep> processorStepList;
		
		public Builder() {
			this.processorStepList = new LinkedList<>();
		}
		
		public void addNextRequestBodyProcessorStep(ResponseProcessorStep requestProcessorStep) {
			if (requestProcessorStep != null) {
				processorStepList.add(requestProcessorStep);
			}
		}
		
		public ResponseProcessor buildResponseProcessor() {
			return new ResponseProcessor(processorStepList);
		}
	}
}
