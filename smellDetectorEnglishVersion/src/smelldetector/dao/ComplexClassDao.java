package smelldetector.dao;

import java.io.Reader;

import javax.swing.JOptionPane;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import smelldetector.mapper.ComplexClassMapper;
import smelldetector.pojo.ComplexClass;

public class ComplexClassDao {
	
	public void insertComplexClass(ComplexClass complexClass) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			ComplexClassMapper complexClassMapper = sqlSession.getMapper(ComplexClassMapper.class);
			complexClassMapper.insertComplexClass(complexClass);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}

}
