package com.herokuapp.queroacaibot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class WatsonAssistantInfosModel {
	
	@Getter
	@Setter
	private String version = "2020-04-01";
	
	@Getter
	@Setter
	private String serviceUrl = "https://api.us-south.assistant.watson.cloud.ibm.com";
	
	@Getter
	@Setter
	private Integer timeoutLimit = 1440;
	
	@Getter
	@Setter
	private String assistantId = "ASSISTANT_ID";
	
	@Getter
	@Setter
	private String apiKey = "API_KEY";
}
