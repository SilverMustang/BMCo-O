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

import smelldetector.mapper.ClassNodeMapper;
import smelldetector.pojo.ClassNode;

public class ClassNodeDao {
	
	public List<ClassNode> classNodeList = new ArrayList<>();
	
	public List<ClassNode> findClassNodeList(Map<String, Object> queryMap){
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			ClassNodeMapper classNodeMapper = sqlSession.getMapper(ClassNodeMapper.class);
			classNodeList = classNodeMapper.findClassNodeList(queryMap);
			sqlSession.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		return classNodeList;
	}
	
	public void updateIsLC(ClassNode classNode) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			ClassNodeMapper classNodeMapper = sqlSession.getMapper(ClassNodeMapper.class);
			classNodeMapper.updateIsLC(classNode);
			sqlSession.commit();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateIsSC(ClassNode classNode) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			ClassNodeMapper classNodeMapper = sqlSession.getMapper(ClassNodeMapper.class);
			classNodeMapper.updateIsSC(classNode);
			sqlSession.commit();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateIsCC(ClassNode classNode) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			ClassNodeMapper classNodeMapper = sqlSession.getMapper(ClassNodeMapper.class);
			classNodeMapper.updateIsCC(classNode);
			sqlSession.commit();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateIsRB(ClassNode classNode) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			ClassNodeMapper classNodeMapper = sqlSession.getMapper(ClassNodeMapper.class);
			classNodeMapper.updateIsRB(classNode);
			sqlSession.commit();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}

}
