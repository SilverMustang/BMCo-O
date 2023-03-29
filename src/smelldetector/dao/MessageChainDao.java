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

import smelldetector.mapper.MessageChainMapper;
import smelldetector.pojo.MessageChain;

public class MessageChainDao {
	
	List<MessageChain> messageChainList = new ArrayList<>();
	
	public void insertMessageChain(MessageChain messageChain) {
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MessageChainMapper messageChainMapper = sqlSession.getMapper(MessageChainMapper.class);
			messageChainMapper.insertMessageChain(messageChain);
			sqlSession.commit();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public List<MessageChain> findAllMessageChain(Map<String, Object> queryMap){
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MessageChainMapper messageChainMapper = sqlSession.getMapper(MessageChainMapper.class);
			messageChainList = messageChainMapper.findMessageChainList(queryMap);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		
		return messageChainList;
	}
	
	public List<MessageChain> findMessageChain(){
		try {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MessageChainMapper messageChainMapper = sqlSession.getMapper(MessageChainMapper.class);
			messageChainList = messageChainMapper.findMessageChain();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		
		return messageChainList;
	}

}
