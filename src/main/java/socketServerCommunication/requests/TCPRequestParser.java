package socketServerCommunication.requests;

import java.util.Objects;

public class TCPRequestParser implements SocketRequestParser {
	private static final String MAGIC_START_REQUEST = "MAGIC_START_REQUEST:";
	private static final String MAGIC_STOP_REQUEST = "MAGIC_STOP.";

	private String rawRequest;
	private ConnectionRequestStatus connectionRequestStatus;
	private String requestBody;
	
	public TCPRequestParser() {
	}
	
	public TCPRequestParser(String rawRequest) {
		this.rawRequest = rawRequest;
	}
	
	@Override
	public void setRawRequest(String rawRequest) {
		this.rawRequest = rawRequest;
	}
	
	@Override
	public ConnectionRequestStatus getConnectionRequestStatus() {
		return connectionRequestStatus;
	}
	
	@Override
	public void preParse() {
		if (rawRequest == null) {
			connectionRequestStatus = ConnectionRequestStatus.INVALID;
			return;
		}
		
		String[] splitedRequest = rawRequest.split(" ");
		
		if (splitedRequest.length != 3) {
			connectionRequestStatus = ConnectionRequestStatus.INVALID;
			return;
		}
		
		if (!Objects.equals(splitedRequest[0], MAGIC_START_REQUEST) || !Objects.equals(splitedRequest[2], MAGIC_STOP_REQUEST)) {
			connectionRequestStatus = ConnectionRequestStatus.INVALID;
			return;
		}
		
		requestBody = splitedRequest[1];
		connectionRequestStatus = ConnectionRequestStatus.VALID;
	}
	
	@Override
	public String getRequestBody() {
		return requestBody;
	}
}