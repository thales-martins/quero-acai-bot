package com.herokuapp.queroacaibot.model;

import java.util.HashMap;

import com.ibm.watson.assistant.v2.Assistant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class WatsonAssistantModel {
	
	@Getter
	@Setter
	private Assistant instance;
	
	@Getter
	@Setter
	private HashMap<String, WatsonAssistantSessionModel> sessions = new HashMap<String, WatsonAssistantSessionModel>();
	
	public void setSession(String key, WatsonAssistantSessionModel session) {		
		sessions.put(key, session);		
	}
	
	public WatsonAssistantSessionModel getSession(String key) {
		return (sessions.get(key) == null ? new WatsonAssistantSessionModel() : sessions.get(key));		
	}
}
