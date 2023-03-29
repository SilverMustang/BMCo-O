package smelldetector.mapper;

import java.util.List;
import java.util.Map;

import smelldetector.pojo.LongMethod;

public interface LongMethodMapper {
	
	void insertLongMethod(LongMethod longMethod);
	
	List<LongMethodMapper> findLongMethodList(Map<String, Object> queryMap);
}
