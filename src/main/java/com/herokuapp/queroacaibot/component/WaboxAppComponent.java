package com.herokuapp.queroacaibot.component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.herokuapp.queroacaibot.model.RequestModel;
import com.herokuapp.queroacaibot.model.WaboxAppInfosModel;

@Component
public class WaboxAppComponent {
	
	@Autowired
	private RequestComponent requestComponent;
	
	private WaboxAppInfosModel waboxAppInfosModel = new WaboxAppInfosModel();
	
	public void sendChat(String uid,
			String contactUid,
			String text) {
		
		String customUid = UUID.randomUUID().toString();
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("token", waboxAppInfosModel.getToken()));
		parameters.add(new BasicNameValuePair("uid", uid));
		parameters.add(new BasicNameValuePair("to", contactUid));
		parameters.add(new BasicNameValuePair("custom_uid", customUid));
		parameters.add(new BasicNameValuePair("text", text));		
		
		RequestModel requestModel = new RequestModel(waboxAppInfosModel.getScheme(), 
				waboxAppInfosModel.getHost(), 
				waboxAppInfosModel.getSendChatPath(),
				parameters);
		
		requestComponent.execute(requestModel);		
	}
	
	public void sendMedia(String uid,
			String contactUid,
			String url) {
		
		sendMedia(uid, contactUid, url, null, null, null);
	}
	
	public void sendMedia(String uid,
			String contactUid,
			String url,
			String caption,
			String description,
			String urlThumb) {
		
		String customUid = UUID.randomUUID().toString();
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("token", waboxAppInfosModel.getToken()));
		parameters.add(new BasicNameValuePair("uid", uid));
		parameters.add(new BasicNameValuePair("to", contactUid));
		parameters.add(new BasicNameValuePair("custom_uid", customUid));
		parameters.add(new BasicNameValuePair("url", url));	
		parameters.add(new BasicNameValuePair("caption", caption));	
		parameters.add(new BasicNameValuePair("description", description));	
		parameters.add(new BasicNameValuePair("url_thumb", urlThumb));	
		
		RequestModel requestModel = new RequestModel(waboxAppInfosModel.getScheme(), 
				waboxAppInfosModel.getHost(), 
				waboxAppInfosModel.getSendMediaPath(),
				parameters);
		
		requestComponent.execute(requestModel);		
	}
}
