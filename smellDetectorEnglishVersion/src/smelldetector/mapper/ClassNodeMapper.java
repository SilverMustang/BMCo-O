package smelldetector.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import smelldetector.pojo.ClassNode;

public interface ClassNodeMapper {
	
	void insertClassNode(ClassNode classNode);
	
	Integer findClassNodeIdByClassName(Map<String,Object> queryMap);
	
	Integer findClassNodeIdByClassNameAndType(Map<String,Object> queryMap);
	
	List<Integer> findClassNodeIds(Map<String,Object> queryMap);
	
	String findClassName(Map<String,Object> queryMap);
	
	List<ClassNode> findClassNodeList(Map<String,Object> queryMap);
	
	void deleteClassNodeByProjectName(Map<String,Object> queryMap);
	
	List<ClassNode> findAllClassNode() throws SQLException;
	
	List<ClassNode> fuzzyFindClassNode(String projectName) throws SQLException;
	
	void updateIsLC(ClassNode classNode);
	
	void updateIsSC(ClassNode classNode);
	
	void updateIsCC(ClassNode classNode);
	
	void updateIsRB(ClassNode classNode);

}
