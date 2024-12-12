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

import smelldetector.mapper.MethodNodeMapper;
import smelldetector.pojo.MethodNode;

public class MethodNodeDao {
	
	public List<MethodNode> methodNodeList = new ArrayList<>();
	
	public List<MethodNode> findMethodNodeList(Map<String, Object> queryMap){
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodNodeMapper methodNodeMapper = sqlSession.getMapper(MethodNodeMapper.class);
			methodNodeList = methodNodeMapper.findMethodNodeList(queryMap);
			sqlSession.commit();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		return methodNodeList;
	}
	
	public MethodNode findMethodNodeById(Integer id){
		MethodNode methodNode = new MethodNode();
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodNodeMapper methodNodeMapper = sqlSession.getMapper(MethodNodeMapper.class);
			methodNode = methodNodeMapper.findMethodNodeById(id);
			sqlSession.commit();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		return methodNode;
	}

}
