package com.herokuapp.queroacaibot.component;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.herokuapp.queroacaibot.model.WatsonAssistantInfosModel;
import com.herokuapp.queroacaibot.model.WatsonAssistantModel;
import com.herokuapp.queroacaibot.model.WatsonAssistantSessionModel;
import com.ibm.cloud.sdk.core.http.HttpConfigOptions;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageInputOptions;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

@Component
public class WatsonAssistantComponent {
	
	private WatsonAssistantModel assistant = null;
	
	private WatsonAssistantInfosModel watsonAssistantInfosModel = new WatsonAssistantInfosModel();
	
	private WatsonAssistantModel getAssistant() {
		
		if(assistant != null) {
			return assistant;
		} else {
			assistant = new WatsonAssistantModel();
			
			IamAuthenticator auth = new IamAuthenticator(watsonAssistantInfosModel.getApiKey());
			
			Assistant instance = new Assistant(watsonAssistantInfosModel.getVersion(), auth);
			
			instance.setServiceUrl(watsonAssistantInfosModel.getServiceUrl());
			
			HttpConfigOptions configOptions = new HttpConfigOptions.Builder()
					  .disableSslVerification(true)
					  .build();
			
			instance.configureClient(configOptions);
			
			assistant.setInstance(instance);
			
			return assistant;
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getSession(String userId) {
		
		WatsonAssistantModel assistant = getAssistant();
		
		WatsonAssistantSessionModel session = assistant.getSession(userId);
		
		session.setTimeoutLimit(watsonAssistantInfosModel.getTimeoutLimit());
		
		if(session.isValid()) {
			session.setDate(Calendar.getInstance().getTime());
			
			return session.getId();
		} else {								
			CreateSessionOptions options = new CreateSessionOptions.Builder(watsonAssistantInfosModel.getAssistantId()).build();
			
			SessionResponse response = assistant.getInstance().createSession(options).execute().getResult();
			
			session.setId(response.getSessionId());
			
			session.setDate(Calendar.getInstance().getTime());
			
			assistant.setSession(userId, session);
			
			return session.getId();
		}
	}
	
	public MessageResponse sendMessage(String message, 
			String userId) {	
		
		MessageInputOptions inputOptions = new MessageInputOptions.Builder()
				.returnContext(true)
				.build();		
		
		MessageInput input = new MessageInput.Builder()
				.messageType("text")
				.text(message)
				.options(inputOptions)
				.build();	
		
		MessageOptions options = new MessageOptions.Builder(watsonAssistantInfosModel.getAssistantId(), getSession(userId))
				.input(input)
				.build();
		
		MessageResponse response = getAssistant().getInstance().message(options).execute().getResult();
		
		return response;
	}
}

