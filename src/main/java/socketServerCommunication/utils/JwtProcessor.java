package socketServerCommunication.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communicatorServer.config.AppMainConfig;
import communicatorServer.models.User.User;
import communicatorServer.models.User.UserService;
import org.bson.types.ObjectId;
import socketServerCommunication.requests.Request;
import socketServerCommunication.requests.RequestProcessorStep;
import socketServerCommunication.responses.Response;
import socketServerCommunication.responses.ResponseProcessorStep;

import java.io.UnsupportedEncodingException;

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
		
		String payload = decodedJWT
				.getPayload(); // TODO make user from payload
		
		JsonObject parsedPayload = jsonParser
				.parse(payload)
				.getAsJsonObject();
		
		ObjectId userId = new ObjectId(
				parsedPayload
						.getAsJsonPrimitive("id")
						.getAsString()
		);
		
		User socketUser = request
				.getUserSocketHandler()
				.getUser();
		
		if (socketUser != null && userId != socketUser.getId()) {
			throw new JWTVerificationException("CRITICAL!"); // TODO
		}
		
		User user = UserService.getUserById(userId);
		
		if (user == null) {
			throw new JWTVerificationException("CRITICAL!"); // TODO
		}
		
		request.setUser(user);
		
		if (socketUser == null) {
			request.getUserSocketHandler().setUserAndSubscribe(user);
		}
	}
	
	@Override
	public void proceed(Response response) {
		if (response.getUser() == null) {
			return;
		}
		
		response.setJWT(generateToken(response.getUser()));
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
