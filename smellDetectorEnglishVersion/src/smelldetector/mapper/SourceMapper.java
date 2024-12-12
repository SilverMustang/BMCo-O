package smelldetector.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import smelldetector.pojo.Source;

public interface SourceMapper {
	
	void insertSource(Source source);
	
	List<Source> findSourceList(Map queryMap);
	
	Source findSourceByClassName(Map queryMap);
		
	List<Source> findSourceMessageList(Map queryMap);
	
	void insertSourceBackups(Source source);
	
	void deleteSourceById(Source source);
	
	void deleteSourceByProjectName(Map<String,Object> queryMap);
	
	List<Source> findAllSource() throws SQLException;
	
	List<Source> fuzzyFindSource(String projectName) throws SQLException;

}
