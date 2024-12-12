package smelldetector.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import smelldetector.pojo.Node;

public interface NodeMapper {
	
	void insertNode(Node node);
	
	void insertMethodNode(Node node);
	
	List<Node> findNodeList(Map<String,Object> queryMap);
	
	Node findNodeById(Map<String,Object> queryMap);
	
	List<Node> findNodeMessageList(Map<String,Object> queryMap);
	
	void insertNodeBackups(Node node);
	
	void deleteNodeByProjectName(Map<String,Object> queryMap);
	
	List<Node> findAllNode() throws SQLException; 
	
	List<Node> fuzzyFindNode(String projectName) throws SQLException;

}
