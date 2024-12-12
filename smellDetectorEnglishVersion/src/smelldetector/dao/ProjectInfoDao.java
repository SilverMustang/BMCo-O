package smelldetector.dao;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import smelldetector.mapper.ProjectInfoMapper;
import smelldetector.pojo.ProjectInfo;

public class ProjectInfoDao {
	
	public List<ProjectInfo> projectInfoList = new ArrayList<>();
	
	public List<ProjectInfo> findAllProjectInfo(){
		try {
		Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession sqlSession = factory.openSession();
		ProjectInfoMapper projectInfoMapper = sqlSession.getMapper(ProjectInfoMapper.class);
		projectInfoList = projectInfoMapper.findAllProjectInfo();
		sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		
		return projectInfoList;
	}

}
