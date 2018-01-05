package socketServerCommunication.config.contexts;

import socketServerCommunication.requests.RequestProcessorStep;
import socketServerCommunication.responses.ResponseProcessorStep;
import socketServerCommunication.utils.ControllerHandler;

import java.util.List;

public interface FlowContext {
	List<RequestProcessorStep> getRequestProcessorStepList();
	List<ResponseProcessorStep> getResponseProcessorStepList();
	ControllerHandler getControllerHandler();
}
