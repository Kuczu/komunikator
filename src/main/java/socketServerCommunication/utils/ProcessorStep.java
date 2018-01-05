package socketServerCommunication.utils;

import socketServerCommunication.requests.Request;
import socketServerCommunication.responses.Response;

public interface ProcessorStep {
	void proceed(Request request);
	void proceed(Response response);
}
