package socketServerCommunication.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communicatorServer.config.AppMainConfig;
import communicatorServer.contexts.UserConnectionContext;
import communicatorServer.models.user.User;
import communicatorServer.models.user.UserService;
import org.bson.types.ObjectId;
import socketServerCommunication.requests.Request;
import socketServerCommunication.requests.RequestProcessorStep;
import socketServerCommunication.responses.Response;
import socketServerCommunication.responses.ResponseProcessorStep;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class JwtProcessor implements RequestProcessorStep, ResponseProcessorStep {
	private static JwtProcessor instance;
	
	private final JsonParser jsonParser;
	private Algorithm algorithmHS;
	
	private JwtProcessor() {
		this.jsonParser = new JsonParser();
		
		try {
			this.algorithmHS = Algorithm.HMAC512(AppMainConfig.HMAC_SECRET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static JwtProcessor getInstance() {
		if (instance == null) {
			instance = new JwtProcessor();
		}
		
		return instance;
	}
	
	@Override
	public void proceed(Request request) {
		if (request.getJWT() == null) {
			return;
		}
		
		DecodedJWT decodedJWT;

//		try {
		decodedJWT = verifyToken(request.getJWT());
//		} catch (JWTVerificationException e) {
		// TODO handle bad users data
//			return;
//		}
		
		String payload = new String(
				Base64.getDecoder()
						.decode(
								decodedJWT.getPayload()
						)
		);
		
		JsonObject parsedPayload = jsonParser
				.parse(payload)
				.getAsJsonObject();
		
		ObjectId userId = new ObjectId(
				parsedPayload
						.getAsJsonPrimitive("id")
						.getAsString()
		);
		
		ObjectId userSocketId = UserConnectionContext.getUserId(request.getClientWebSocket());
		
		if (userSocketId != null && userId.compareTo(userSocketId) != 0) {
			throw new JWTVerificationException("CRITICAL!"); // TODO
		}
		
		User user = UserService.getUserBy(userId);
		
		if (user == null) {
			throw new JWTVerificationException("CRITICAL!"); // TODO
		}
		
		request.setUserId(userId);
		
		if (userSocketId == null) {
			UserConnectionContext.addLoggedUser(userId, request.getClientWebSocket());
		}
	}
	
	@Override
	public void proceed(Response response) {
		if (response.getUser() == null || response.getJWT() != null) {
			return;
		}
		
		response.setJWT(generateToken(response.getUser()));
		
		ObjectId userSocketId = UserConnectionContext.getUserId(response.getRequest().getClientWebSocket());
		
		if (userSocketId == null) {
			UserConnectionContext.addLoggedUser(response.getUser().getId(), response.getRequest().getClientWebSocket());
		}
	}
	
	private String generateToken(User user) {
		return JWT.create()
				.withIssuer("Communicator")
				.withClaim("id", user.getId().toHexString())
				.withClaim("name", user.getNick())
				.sign(algorithmHS);
	}
	
	private DecodedJWT verifyToken(String token) {
		JWTVerifier verifier = JWT.require(algorithmHS)
				.withIssuer("Communicator")
				.build();
		
		return verifier.verify(token);
	}
}
