package com.herokuapp.queroacaibot.model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class BlockListModel {
	
	@Getter
	@Setter
	private Map<String, BlockListInfosModel> blockListInfosModel = new HashMap<String, BlockListInfosModel>();
	
	public void add(String key, int timeoutLimit) {
		BlockListInfosModel blockListInfoModel = new BlockListInfosModel(Calendar.getInstance().getTime(), timeoutLimit);
		
		blockListInfosModel.put(key, blockListInfoModel);
	}
	
	public Boolean isBlocked(String key) {
		BlockListInfosModel blockListInfoModel = blockListInfosModel.get(key);
		
		if(blockListInfoModel == null)
			return false;
		
		Boolean isBlocked = blockListInfoModel.isBlocked();		
		
		if(!isBlocked)
			blockListInfosModel.remove(key, blockListInfoModel);
		
		return isBlocked;
	}
}
