package smelldetector.dao;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import smelldetector.mapper.LongMethodMapper;
import smelldetector.pojo.LongMethod;

public class LongMethodDao {
	
	List<LongMethod> longMethodList = new ArrayList<>();
	
	public void insertLongMethod(LongMethod longMethod) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			LongMethodMapper longMethodMapper = sqlSession.getMapper(LongMethodMapper.class);
			longMethodMapper.insertLongMethod(longMethod);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}

}
