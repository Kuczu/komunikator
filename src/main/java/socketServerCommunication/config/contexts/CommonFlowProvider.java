package socketServerCommunication.config.contexts;

import com.google.common.collect.ImmutableList;
import socketServerCommunication.requests.RequestProcessorStep;
import socketServerCommunication.responses.ResponseProcessorStep;
import socketServerCommunication.utils.ControllerHandler;
import socketServerCommunication.utils.DataDecryptorEncryptor;
import socketServerCommunication.utils.JwtProcessor;

import java.util.List;

public class CommonFlowProvider implements FlowContext {
	private static CommonFlowProvider instance;
	
	private ImmutableList<RequestProcessorStep> requestProcessorStepList;
	private ControllerHandler controllerHandler;
	
	private ImmutableList<ResponseProcessorStep> responseProcessorStepList;
	
	private CommonFlowProvider() {
		this.requestProcessorStepList = ImmutableList.of(DataDecryptorEncryptor.getInstance(), JwtProcessor.getInstance()); // todo from context
		this.controllerHandler = new ControllerHandler();
		
		this.responseProcessorStepList = ImmutableList.of(JwtProcessor.getInstance(), DataDecryptorEncryptor.getInstance());
	}
	
	public static CommonFlowProvider getInstance() {
		if (instance == null) {
			instance = new CommonFlowProvider();
		}
		
		return instance;
	}
	
	@Override
	public List<RequestProcessorStep> getRequestProcessorStepList() {
		return requestProcessorStepList;
	}
	
	@Override
	public ControllerHandler getControllerHandler() {
		return controllerHandler;
	}
	
	@Override
	public List<ResponseProcessorStep> getResponseProcessorStepList() {
		return responseProcessorStepList;
	}
}
