package com.herokuapp.queroacaibot.model;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class BlockListInfosModel {
	
	@Getter
	@Setter
	private Date date = Calendar.getInstance().getTime();
	
	@Getter
	@Setter
	private int timeoutLimit = -1;
	
	public Boolean isBlocked() {
		long diffInMillies = Math.abs(Calendar.getInstance().getTime().getTime() - date.getTime());
		long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		return diff < timeoutLimit;		
	}
}
