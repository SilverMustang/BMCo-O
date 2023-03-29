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

import smelldetector.mapper.ClassTreeMapper;
import smelldetector.pojo.ClassTree;

public class ClassTreeDao {
	
	public List<ClassTree> classTreeList = new ArrayList<>();
	
	public List<ClassTree> findClassTreeList(Map<String, Object> queryMap){
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			ClassTreeMapper classTreeMapper = sqlSession.getMapper(ClassTreeMapper.class);
			classTreeList = classTreeMapper.findClassTreeList(queryMap);
			sqlSession.commit();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		return classTreeList;
	}

}
