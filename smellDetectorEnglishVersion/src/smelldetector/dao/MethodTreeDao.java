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

import smelldetector.mapper.MethodTreeMapper;
import smelldetector.pojo.MethodTree;

public class MethodTreeDao {
	
	public List<MethodTree> methodTreeList = new ArrayList<>();
	
	public List<MethodTree> findMethodTreeList(Map<String, Object> queryMap){
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodTreeMapper methodTreeMapper = sqlSession.getMapper(MethodTreeMapper.class);
			methodTreeList = methodTreeMapper.findMethodTreeList(queryMap);
			sqlSession.commit();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		return methodTreeList;
	}
	
	public void updateIsLM(MethodTree methodTree) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodTreeMapper methodTreeMapper = sqlSession.getMapper(MethodTreeMapper.class);
			methodTreeMapper.updateIsLM(methodTree);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public MethodTree findMethodTreeById(Integer id){
		MethodTree methodTree = new MethodTree();
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodTreeMapper methodTreeMapper = sqlSession.getMapper(MethodTreeMapper.class);
			methodTree = methodTreeMapper.findMethodTreeById(id);
			sqlSession.commit();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		return methodTree;
	}
	
	public void updateIsMC(MethodTree methodTree) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodTreeMapper methodTreeMapper = sqlSession.getMapper(MethodTreeMapper.class);
			methodTreeMapper.updateIsMC(methodTree);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateIsFE(MethodTree methodTree) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodTreeMapper methodTreeMapper = sqlSession.getMapper(MethodTreeMapper.class);
			methodTreeMapper.updateIsFE(methodTree);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}

}
