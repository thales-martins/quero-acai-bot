package com.herokuapp.queroacaibot.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.herokuapp.queroacaibot.model.BlockListModel;
import com.herokuapp.queroacaibot.model.WaboxAppMessagesModel;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.RuntimeResponseGeneric;

@Component
public class CoreComponent {
	
	@Autowired
	private WatsonAssistantComponent watsonAssistantComponent;
	
	@Autowired
	private WaboxAppComponent waboxAppComponent;
	
	private BlockListModel blockListModel = new BlockListModel();
	
	private Map<String, Boolean> processMessage = new HashMap<String, Boolean>();
	
	public void processMessage(WaboxAppMessagesModel waboxAppMessagesModel) {
		
		Boolean isBlocked = blockListModel.isBlocked(waboxAppMessagesModel.gotContactUid());
		
		if(!isBlocked) {
			
			Boolean canProcessMessage = processMessage.get(waboxAppMessagesModel.gotContactUid());
			
			canProcessMessage = (canProcessMessage == null ? true : canProcessMessage);
			
			if(canProcessMessage) {
				
				canProcessMessage = false;
				
				processMessage.put(waboxAppMessagesModel.gotContactUid(), canProcessMessage);
		
				MessageResponse messageResponse = watsonAssistantComponent.sendMessage(waboxAppMessagesModel.getMessage(), waboxAppMessagesModel.gotContactUid());
				
				List<RuntimeResponseGeneric> generic = messageResponse.getOutput().getGeneric();
				
				for(RuntimeResponseGeneric responseGeneric: generic) {	
					
					if(responseGeneric.responseType().equals("text")) {
						
						String text = responseGeneric.text();
						
						switch(text) {
							case "&cardapio":
								waboxAppComponent.sendMedia(waboxAppMessagesModel.getUid(), 
										waboxAppMessagesModel.getContactUid(), 
										"https://queroacaibot.herokuapp.com/public/files/cardapio-queroacai-oficial.pdf", 
										"Cardápio Oficial do Quero Açaí", 
										"Clique aqui para ter acesso ao cardápio oficial do Quero Açaí.", 
										"https://queroacaibot.herokuapp.com/public/images/cardapio-queroacai-oficial.png");
								break;
							case "&pedido":		
								waboxAppComponent.sendChat(waboxAppMessagesModel.getUid(), waboxAppMessagesModel.getUid(), (waboxAppMessagesModel.gotContactName() + " - Realizou um *PEDIDO*, favor conferir."));
								break;
							case "&atendente":						
								blockListModel.add(waboxAppMessagesModel.gotContactUid(), 30);
								
								waboxAppComponent.sendChat(waboxAppMessagesModel.getUid(), waboxAppMessagesModel.getUid(), (waboxAppMessagesModel.gotContactName() + " - Gostaria de falar com um *ATENDENTE*, favor conferir."));
								break;
							default:
								waboxAppComponent.sendChat(waboxAppMessagesModel.getUid(), waboxAppMessagesModel.getContactUid(), text);
								break;
						}	
					}
				}
				
				canProcessMessage = true;
				
				processMessage.put(waboxAppMessagesModel.gotContactUid(), canProcessMessage);
			}		
		}
	}
}
