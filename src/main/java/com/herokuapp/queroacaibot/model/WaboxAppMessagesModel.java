package com.herokuapp.queroacaibot.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class WaboxAppMessagesModel {
	
	@Getter
	@Setter
	private String uid;
	
	@Getter
	@Setter
	private String contactUid;
	
	@Getter
	@Setter
	private String contactName;
	
	@Getter
	private Timestamp messageDtm;
	
	@Getter
	@Setter
	private String messageDir;
	
	@Getter
	@Setter
	private String messageType;
	
	@Getter
	@Setter
	private String messageBodyText;
	
	@Getter
	@Setter
	private String messageBodyCaption;
	
	public void setMessageDtm(Timestamp messageDtm) {
		this.messageDtm = messageDtm;
	}
	
	public void setMessageDtm(Long messageDtm) {
		try {
			this.messageDtm = new Timestamp(TimeUnit.MILLISECONDS.convert(messageDtm, TimeUnit.SECONDS));
		} catch(Exception e) {
			this.messageDtm = new Timestamp(Calendar.getInstance().getTimeInMillis());
		}
	}
	
	public String getMessage() {
		return (messageType.equals("chat") ? messageBodyText : (!messageBodyCaption.isEmpty() ? messageBodyCaption : messageType)).replaceAll("[\\r\\n\\t]", " ");
	}
	
	public String gotContactName() {
		return ((contactName.isEmpty() ? "" : (contactName + " ")) + contactUid);
	}
	
	public String gotContactUid() {
		return uid + contactUid;
	}
}
