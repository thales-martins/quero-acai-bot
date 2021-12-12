package com.herokuapp.queroacaibot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {
	
	@Getter
	@Setter
	private int code;
	
	@Getter
	@Setter
	private String responseString;
}

