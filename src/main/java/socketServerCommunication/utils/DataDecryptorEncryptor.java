package socketServerCommunication.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import socketServerCommunication.requests.Request;
import socketServerCommunication.requests.RequestProcessorStep;
import socketServerCommunication.responses.Response;
import socketServerCommunication.responses.ResponseProcessorStep;

import java.util.Base64;

public class DataDecryptorEncryptor implements RequestProcessorStep, ResponseProcessorStep {
	private static DataDecryptorEncryptor instance;
	private final JsonParser jsonParser;
	
	private DataDecryptorEncryptor() {
		this.jsonParser = new JsonParser();
	}
	
	public static DataDecryptorEncryptor getInstance() {
		if (instance == null) {
			instance = new DataDecryptorEncryptor();
		}
		
		return instance;
	}
	
	@Override
	public void proceed(Request request) {
		request.setRawRequest(new String(Base64.getDecoder().decode(request.getRawRequest())));
		
		parse(request);
	}
	
	private void parse(Request request) {
		JsonObject jsonObject = jsonParser.parse(request.getRawRequest()).getAsJsonObject();
		
		request.setApiPath(jsonObject.get("apiPath").getAsString()); // TODO can produce NPE
		
		JsonElement jsonElement = jsonObject.get("JWT");
		if (jsonElement != null) {
			request.setJWT(jsonElement.getAsString());
		}
		
		jsonElement = jsonObject.get("data");
		if (jsonElement != null) {
			request.setBody(jsonElement.getAsString());
		}
	}
	
	@Override
	public void proceed(Response response) {
		JsonObject jsonObject = new JsonObject();
		
		if (response.getJWT() != null) {
			jsonObject.addProperty("JWT", response.getJWT());
		}
		
		if (response.getJsonBody() != null) {
			jsonObject.addProperty("data", response.getJsonBody());
		}
		
		response.setEncodedData(
				new String(Base64
						.getEncoder()
						.encode(jsonObject.toString().getBytes())
				)
		);
	}
}
