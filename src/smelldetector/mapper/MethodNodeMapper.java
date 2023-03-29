package smelldetector.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import smelldetector.pojo.MethodNode;

public interface MethodNodeMapper {
	
	void insertMethodNode(MethodNode methodNode);
	
	List<MethodNode> findMethodNodeList(Map<String,Object> queryMap);
	
	List<MethodNode> findMethodNodeListByType(Map<String,Object> queryMap);
	
	List<MethodNode> findMethodNodeByProjectName(Map<String,Object> queryMap);
	
	MethodNode findMethodNodeById(Integer id);
	
	void deleteMethodLinkByClassNodeId(Map<String,Object> queryMap);

	List<MethodNode> findAllMethodNode() throws SQLException;

	List<MethodNode> fuzzyFindMethodNode(String projectName) throws SQLException;

}
