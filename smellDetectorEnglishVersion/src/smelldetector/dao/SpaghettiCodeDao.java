package smelldetector.dao;

import java.io.Reader;

import javax.swing.JOptionPane;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import smelldetector.mapper.SpaghettiCodeMapper;
import smelldetector.pojo.SpaghettiCode;

public class SpaghettiCodeDao {
	
	public void insertSpaghettiCode(SpaghettiCode spaghettiCode) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			SpaghettiCodeMapper spaghettiCodeMapper = sqlSession.getMapper(SpaghettiCodeMapper.class);
			spaghettiCodeMapper.insertSpaghettiCode(spaghettiCode);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}

}
