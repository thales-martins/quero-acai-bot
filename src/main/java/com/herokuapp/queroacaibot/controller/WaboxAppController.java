package com.herokuapp.queroacaibot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.herokuapp.queroacaibot.component.CoreComponent;
import com.herokuapp.queroacaibot.model.WaboxAppInfosModel;
import com.herokuapp.queroacaibot.model.WaboxAppMessagesModel;

@RestController
@RequestMapping("/waboxapp")
public class WaboxAppController {
	
	@Autowired
	private CoreComponent coreComponent;
	
	private WaboxAppInfosModel waboxAppInfosModel = new WaboxAppInfosModel();
	
	@PostMapping("/message/received")
	public void messageReceived(@RequestParam(defaultValue = "") String token, 
			@RequestParam(defaultValue = "") String event,
			@RequestParam(defaultValue = "") String uid,
			@RequestParam(name = "contact[uid]", defaultValue = "") String contactUid,
			@RequestParam(name = "contact[name]", defaultValue = "") String contactName,
			@RequestParam(name = "contact[type]", defaultValue = "") String contactType,
			@RequestParam(name = "message[dtm]", required = false) Long messageDtm,
			@RequestParam(name = "message[dir]", defaultValue = "") String messageDir,
			@RequestParam(name = "message[type]", defaultValue = "") String messageType,
			@RequestParam(name = "message[body][text]", defaultValue = "") String messageBodyText,
			@RequestParam(name = "message[body][caption]", defaultValue = "") String messageBodyCaption) {
		
		if(!token.equals(waboxAppInfosModel.getToken()))
			return;		
		
		if(event.equals("message") && contactType.equals("user")) {
			WaboxAppMessagesModel waboxAppMessagesModel = new WaboxAppMessagesModel(uid,
					contactUid,
					contactName, 
					null, 
					messageDir, 
					messageType, 
					messageBodyText, 
					messageBodyCaption);
			waboxAppMessagesModel.setMessageDtm(messageDtm);
			
			if(waboxAppMessagesModel.getMessageDir().equals("i") && !waboxAppMessagesModel.getUid().equals(waboxAppMessagesModel.getContactUid()))
				coreComponent.processMessage(waboxAppMessagesModel);
		}
	}
}
