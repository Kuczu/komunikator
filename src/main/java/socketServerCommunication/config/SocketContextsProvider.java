package socketServerCommunication.config;

import socketServerCommunication.RequestResponseManager;
import socketServerCommunication.requests.SocketRequestParser;
import socketServerCommunication.requests.RequestProcessor;
import socketServerCommunication.requests.SocketRequestParserFactory;
import socketServerCommunication.requests.TCPRequestParserFactory;
import socketServerCommunication.responses.ResponseProcessor;
import socketServerCommunication.config.contexts.CommonFlowProvider;
import socketServerCommunication.config.contexts.FlowContext;
import socketServerCommunication.config.contexts.RequestResponseBuilder;

public class SocketContextsProvider {
	// TODO get instances for socket server config and build it
	public static final FlowContext FLOW_CONTEXT = CommonFlowProvider.getInstance();
	private static final RequestProcessor REQUEST_PROCESSOR = RequestResponseBuilder.buildRequestProcessor(FLOW_CONTEXT);
	private static final ResponseProcessor RESPONSE_PROCESSOR = RequestResponseBuilder.buildResponseProcessor(FLOW_CONTEXT);
	public static final RequestResponseManager REQUEST_RESPONSE_MANAGER = new RequestResponseManager(RESPONSE_PROCESSOR, REQUEST_PROCESSOR, FLOW_CONTEXT.getControllerHandler());
	private static final SocketRequestParserFactory SOCKET_REQUEST_PARSER_FACTORY = new TCPRequestParserFactory();
	
	public static SocketRequestParser getSocketRequestParser() {
		return SOCKET_REQUEST_PARSER_FACTORY.getNewSocketRequestParser();
	}
}
