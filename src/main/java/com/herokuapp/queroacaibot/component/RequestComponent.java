package com.herokuapp.queroacaibot.component;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;

import com.herokuapp.queroacaibot.model.RequestModel;
import com.herokuapp.queroacaibot.model.ResponseModel;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class RequestComponent {
	
	public ResponseModel execute(RequestModel requestModel) {		
		try {
			URIBuilder uriBuilder = new URIBuilder()
					.setScheme(requestModel.getScheme())
					.setHost(requestModel.getHost())
					.setPath(requestModel.getPath());
			
			if(requestModel.getParameters() != null)
				uriBuilder = uriBuilder.setParameters(requestModel.getParameters());
			
			String url = uriBuilder.build().toString();
			
			OkHttpClient okHttpClient = new OkHttpClient();	
			
			Request request = new Request.Builder()
					.url(url)
					.build();
			
			if(requestModel.getRequestBody() != null) {
				request = request.newBuilder()
						.post(requestModel.getRequestBody())
						.build();
			}
			
			if(requestModel.getHeaders() != null) {
				request = request.newBuilder()
						.headers(requestModel.getHeaders())
						.build();
			}
			
			Response response = okHttpClient.newCall(request)
					.execute();
			
			ResponseModel responseModel = new ResponseModel(response.code(), response.body().string());
			
			response.body().close();
			
			return responseModel;
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ResponseModel();
		} 
	}
}
