////package communicatorServer;
//
//import communicatorServer.config.ConfigManager;
//import socketServerCommunication.TcpConnector;
//
//import java.util.logging.Logger;
//
//public class Main {
//	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
//
//	public static void main(String[] args) {
//		try {
//			ConfigManager.initializeConfigContext();
//
//			new TcpConnector().start(7777);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
