package smelldetector.mapper;

import java.util.List;
import java.util.Map;

import smelldetector.pojo.MessageChain;

public interface MessageChainMapper {
	
	void insertMessageChain(MessageChain messageChain);
	
	List<MessageChain> findMessageChainList(Map<String, Object> queryMap);

	List<MessageChain> findMessageChain();

}
