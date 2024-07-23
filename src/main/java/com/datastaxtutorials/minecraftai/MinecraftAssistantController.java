package com.datastaxtutorials.minecraftai;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class MinecraftAssistantController {

	private RestTemplate restTemplate;
	private record Response(String answer) {};
	private record Request(String question) {};
	private static  final String FASTAPI_URL = "http://127.0.0.1:8000";
	
	public MinecraftAssistantController() {
		restTemplate = new RestTemplateBuilder().build();
	}
	
	//curl -s -XPOST http://127.0.0.1:8000/askAI \
	//	  -H 'Content-Type: application/json' \
	//	  -H 'Accept: application/json' \
	//	  -d '{"question":"What is the recipe for a gold sword?"}'
	
	public String askQuestion(String question) {
		
		Request req = new Request(question);
		
		Response resp = restTemplate.postForObject(
				FASTAPI_URL + "/askAI",
				req,
				Response.class);
		
		return resp.answer();
	}
	
}
