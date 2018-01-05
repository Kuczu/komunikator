//package socketServerCommunication.Utils;
//
//import socketServerCommunication.requests.Request;
//import socketServerCommunication.responses.Response;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.LinkedList;
//import java.util.List;
//
////TODO invert builder get up make interfaces for response/request and create from private classes
///*abstract*/ public class RequestResponseProcessor<E extends ProcessorStep> {
//	private final List<E> requestProcessorStepList;
//	private final ControllerHandler controllerHandler;
//
//	private RequestResponseProcessor(List<E> requestProcessorStepList, ControllerHandler controllerHandler) {
//		this.requestProcessorStepList = requestProcessorStepList;
//		this.controllerHandler = controllerHandler;
//	}
//
//	public void proceed(Request request) throws InvocationTargetException, IllegalAccessException { //TODO
//		for (E step : requestProcessorStepList) {
//			step.proceed(request);
//		}
//
//		controllerHandler.proceed(request);
//	}
//
//	public void proceed(Response request) { // TODO
//		for (E step : requestProcessorStepList) {
//			step.proceed(request);
//		}
//	}
//
//	public /*abstract*/ static class Builder<E extends ProcessorStep> {
//		private List<E> processorStepList;
//		private ControllerHandler controllerHandler;
//
//		public Builder() {
//			this.processorStepList = new LinkedList<>();
//		}
//
//		public void addNextRequestBodyProcessorStep(E requestProcessorStep) {
//			if (requestProcessorStep != null) {
//				processorStepList.add(requestProcessorStep);
//			}
//		}
//
//		public void setControllerHandler(ControllerHandler controllerHandler) {
//			this.controllerHandler = controllerHandler;
//		}
//
//		public RequestResponseProcessor buildRequest() {
//			return new RequestResponseProcessor<>(processorStepList, controllerHandler);
//		}
//
//		public RequestResponseProcessor buildResponse() {
//			return new RequestResponseProcessor<>(processorStepList, controllerHandler);
//		}
//	}
//}
