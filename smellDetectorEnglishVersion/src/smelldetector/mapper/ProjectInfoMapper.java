package smelldetector.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import smelldetector.pojo.ProjectInfo;

public interface ProjectInfoMapper {
	
	void insertProjectInfo(ProjectInfo projectInfo);
	
	ProjectInfo findProjectInfo(Map queryMap);
	
	List<ProjectInfo> findProjectInfoList(Map queryMap);
	
	List<ProjectInfo> findAllProjectInfo() throws SQLException;
	
	List<ProjectInfo> fuzzyFindProjectInfo(String projectName) throws SQLException;

}
