package socketServerCommunication.requests;

public class TCPRequestParserFactory implements SocketRequestParserFactory {
	@Override
	public SocketRequestParser getNewSocketRequestParser() {
		return new TCPRequestParser();
	}
}
