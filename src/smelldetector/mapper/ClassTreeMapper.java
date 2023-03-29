package smelldetector.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import smelldetector.pojo.ClassTree;

public interface ClassTreeMapper {
	
	void insertClassTree(ClassTree treeNode);
	
	List<ClassTree> findAllClassTree() throws SQLException;
	
	List<ClassTree> findClassTreeList(Map<String, Object> queryMap);

}
