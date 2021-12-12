package com.herokuapp.queroacaibot.model;

import java.util.List;

import org.apache.http.NameValuePair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhttp3.Headers;
import okhttp3.RequestBody;

@NoArgsConstructor
@AllArgsConstructor
public class RequestModel {
	
	@Getter
	@Setter
	private String scheme;
	
	@Getter
	@Setter
	private String host;
	
	@Getter
	@Setter
	private String path;
	
	@Getter
	@Setter
	private List<NameValuePair> parameters;
	
	@Getter
	@Setter
	private RequestBody requestBody;
	
	@Getter
	@Setter
	private Headers headers;

	public RequestModel(String scheme, String host, String path, List<NameValuePair> parameters) {
		super();
		this.scheme = scheme;
		this.host = host;
		this.path = path;
		this.parameters = parameters;
	}
}

