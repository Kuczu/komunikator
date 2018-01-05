package socketServerCommunication.requests;

public interface SocketRequestParser {
	void preParse();
	void setRawRequest(String rawRequest);
	String getRequestBody();
	ConnectionRequestStatus getConnectionRequestStatus();
}
