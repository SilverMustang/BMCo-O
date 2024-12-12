package smelldetector.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FastJsonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();	
	
	public static String objectToString(Object object) throws Exception{
		String str = "";
		if(object != null) {
			str = mapper.writeValueAsString(object);
		}
		return str;
	}
	
	public static Map jsonStrToMap(String json) throws Exception{
		Map map = new HashMap<>();
		map = (Map)mapper.readValue(json, Map.class);
		return map;
	}
	
	public static List<String> jsonStrToList(String json) throws Exception {
		List<String> list = new ArrayList<String>();
		list = mapper.readValue(json, ArrayList.class);
		return list;
	}

}
