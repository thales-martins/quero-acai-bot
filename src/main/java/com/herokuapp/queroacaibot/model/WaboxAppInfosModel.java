package com.herokuapp.queroacaibot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class WaboxAppInfosModel {
	
	@Getter
	@Setter
	private String scheme = "https";
	
	@Getter
	@Setter
	private String host = "www.waboxapp.com";
	
	@Getter
	@Setter
	private String token = "TOKEN";
	
	@Getter
	@Setter
	private String sendChatPath = "/api/send/chat";
	
	@Getter
	@Setter
	private String sendMediaPath = "/api/send/media";
}
