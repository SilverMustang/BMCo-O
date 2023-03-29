package smelldetector.dao;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import smelldetector.mapper.MethodLinkMapper;
import smelldetector.mapper.MethodTreeMapper;
import smelldetector.pojo.MethodLink;
import smelldetector.pojo.MethodTree;

public class MethodLinkDao {
	
	public List<MethodLink> methodLinkList = new ArrayList<>();
	
	public List<MethodLink> findMethodLinkList(Map<String, Object> queryMap){
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodLinkMapper methodLinkMapper = sqlSession.getMapper(MethodLinkMapper.class);
			methodLinkList = methodLinkMapper.findMethodLinkList(queryMap);
			sqlSession.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		return methodLinkList;
	}
	
	public List<MethodLink> findMethodLinkById(int targetId, String projectName) {
		List<MethodLink> methodLinkList = new ArrayList<>();
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodLinkMapper methodLinkMapper = sqlSession.getMapper(MethodLinkMapper.class);
			methodLinkList = methodLinkMapper.findMethodLinkByTargetId(targetId, projectName);
			sqlSession.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		return methodLinkList;
	}
	
	public void updateMCC(MethodLink methodLink) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodLinkMapper methodLinkMapper = sqlSession.getMapper(MethodLinkMapper.class);
			methodLinkMapper.updateMCC(methodLink);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}

}
