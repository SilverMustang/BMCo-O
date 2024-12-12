package smelldetector.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import smelldetector.pojo.MethodLink;

public interface MethodLinkMapper {
	
	void insertMethodLink(MethodLink methodLink);
	
	MethodLink findMethodLink(Map<String,Object> queryMap);
	
	List<MethodLink> findMethodLinkListBySource(Map<String,Object> queryMap);
	
	void deleteMethodLinkByProjectName(Map<String,Object> queryMap);
	
	List<MethodLink> findAllMethodLink() throws SQLException;
	
	List<MethodLink> fuzzyFindMethodLink(String projectName) throws SQLException;

	List<MethodLink> findMethodLinkList(Map<String, Object> queryMap);
	
	List<MethodLink> findMethodLinkByTargetId(@Param("targetId")int targetId, @Param("projectName")String projectName);
	
	void updateMCC(MethodLink methodLink);

}
