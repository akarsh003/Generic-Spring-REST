package com.ndg.springdemo.utility;

import java.util.HashMap;
import java.util.Map;

public interface Util {
	@SuppressWarnings("serial")
	Map<String, String[]> usernames= new HashMap<String,String[]>() {{
		put("manogna",new String[] {"Manogna Lakshman","Leadership",});
		put("mythri",new String[] {"Mythri MJ","QR"});
		put("akarsha",new String[] {"Akarsha KR","Processor"});
		put("ram",new String[] {"Ramanathan","QR"});
	}};

}
