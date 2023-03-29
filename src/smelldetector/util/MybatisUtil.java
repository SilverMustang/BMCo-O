package smelldetector.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtil {
	
	static SqlSessionFactory sqlSessionFactory = null;
	
	static {
		try {
			//加载mybatis配置文件，并创建SqlSessionFactory实例
			String resource = "sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			//这个build方法可以接受几种不同的参数，如Reader/InputStream等
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}catch (IOException e) {
			
		}
	}
	
	public static SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
	
	public static void closeSqlSession(SqlSession sqlSession) {
		if(sqlSession != null) {
			sqlSession.close();
		}
	}
}
