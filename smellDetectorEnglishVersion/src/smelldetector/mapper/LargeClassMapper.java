package smelldetector.mapper;

import java.util.List;
import java.util.Map;

import smelldetector.pojo.LargeClass;

public interface LargeClassMapper {
	
	void insertLargeClass(LargeClass largeClass);
	
	List<LargeClass> findLargeClassList(Map<String,Object> queryMap);
	
	List<LargeClass> findLargeClass();

}
