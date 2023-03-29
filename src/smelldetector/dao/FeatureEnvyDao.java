package smelldetector.dao;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import smelldetector.mapper.FeatureEnvyMapper;
import smelldetector.pojo.FeatureEnvy;

public class FeatureEnvyDao {
	
	List<FeatureEnvy> featureEnvyDao = new ArrayList<>();
	
	public void insertFeatureEnvy(FeatureEnvy featureEnvy) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			FeatureEnvyMapper featureEnvyMapper = sqlSession.getMapper(FeatureEnvyMapper.class);
			featureEnvyMapper.insertFeatureEnvy(featureEnvy);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}

}
