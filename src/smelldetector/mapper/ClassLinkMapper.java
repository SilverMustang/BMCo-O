package smelldetector.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import smelldetector.pojo.ClassLink;

public interface ClassLinkMapper {
	
	void insertLink(ClassLink link);
	
	List<ClassLink> findLinkList(Map<String,Object> queryMap);
		
	ClassLink findLink(Map<String,Object> queryMap);
		
	void insertLinkBackups(ClassLink link);
		
	void deleteLinkByProjectName(Map<String,Object> queryMap);
	
	List<ClassLink> findAllClassLink() throws SQLException;
	
	List<ClassLink> fuzzyFindClassLink(String projectName) throws SQLException;

}
