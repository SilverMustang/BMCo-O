package smelldetector.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import smelldetector.pojo.MethodTree;

public interface MethodTreeMapper {
	
	void insertMethodTree(MethodTree treeNode);
	
	List<MethodTree> findAllMethodTree() throws SQLException;
	
	List<MethodTree> findMethodTreeList(Map<String, Object> queryMap);
	
	void updateIsLM(MethodTree treeNode);
	
	void updateIsMC(MethodTree treeNode);
	
	void updateIsFE(MethodTree treeNode);
	
	MethodTree findMethodTreeById(Integer id);

}
