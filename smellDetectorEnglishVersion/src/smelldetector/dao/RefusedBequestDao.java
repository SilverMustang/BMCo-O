package smelldetector.dao;

import java.io.Reader;

import javax.swing.JOptionPane;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import smelldetector.mapper.RefusedBequestMapper;
import smelldetector.pojo.RefusedBequest;

public class RefusedBequestDao {

	public void insertRefusedBequest(RefusedBequest refusedBequest) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			RefusedBequestMapper refusedBequestMapper = sqlSession.getMapper(RefusedBequestMapper.class);
			refusedBequestMapper.insertRefusedBequest(refusedBequest);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
}
