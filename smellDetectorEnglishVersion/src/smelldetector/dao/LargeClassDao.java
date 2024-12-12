package smelldetector.dao;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import smelldetector.mapper.LargeClassMapper;
import smelldetector.mapper.MessageChainMapper;
import smelldetector.pojo.LargeClass;
import smelldetector.pojo.MessageChain;

public class LargeClassDao {
	
	List<LargeClass> largeClassList = new ArrayList<>();
	
	public void insertLargeClass(LargeClass largeClass) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			LargeClassMapper largeClassMapper = sqlSession.getMapper(LargeClassMapper.class);
			largeClassMapper.insertLargeClass(largeClass);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public List<LargeClass> findLargeClass(){
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			LargeClassMapper largeClassMapper = sqlSession.getMapper(LargeClassMapper.class);
			largeClassList = largeClassMapper.findLargeClass();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		
		return largeClassList;
	}

}
