package smelldetector.ast.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smelldetector.dao.ClassNodeDao;
import smelldetector.dao.ComplexClassDao;
import smelldetector.dao.MessageChainDao;
import smelldetector.pojo.ClassNode;
import smelldetector.pojo.ComplexClass;
import smelldetector.pojo.MessageChain;
import smelldetector.util.CollectionUtil;

public class ComplexClassDe {
	
	List<MessageChain> messageChainList = new ArrayList<>();
	int wmc_avg = 0;
	
	public void complexClassAnalysis(String projectName) {
		ClassNodeDao classNodeDao = new ClassNodeDao();
		MessageChainDao messageChainDao = new MessageChainDao();
		List<ClassNode> classNodeList = new ArrayList<>();
		List<ClassNode> complexClassSuspect = new ArrayList<>();
		List<ClassNode> complexClassInstance = new ArrayList<>();
		Map<String, Object> queryMap = new HashMap<>();
		
		queryMap.put("projectName", projectName);
		messageChainList = messageChainDao.findAllMessageChain(queryMap);
		classNodeList = classNodeDao.findClassNodeList(queryMap);
		complexClassSuspect = filterComplexClass(classNodeList);
		complexClassInstance = complexClassDe(complexClassSuspect);
		for(ClassNode classNode : classNodeList) {
			classNode.setIsCC(0);
			if(complexClassInstance.contains(classNode)) {
				classNode.setIsCC(1);
			}
			classNodeDao.updateIsCC(classNode);
		}
	}

	private List<ClassNode> complexClassDe(List<ClassNode> complexClassSuspect) {
		// TODO Auto-generated method stub
		List<ClassNode> complexClassInstance = new ArrayList<>();
		List<String> messageChainClassName = new ArrayList<>();
		for(MessageChain messageChain : messageChainList) {
			messageChainClassName.add(messageChain.getClassName());
		}
		if(CollectionUtil.isNotNullOrEmpty(complexClassSuspect)) {
			for(ClassNode classNode : complexClassSuspect) {
				ComplexClass complexClass = new ComplexClass();
				ComplexClassDao complexClassDao = new ComplexClassDao();
				int wmc = classNode.getWMC();
				double wmc_p = countPossibility(wmc, wmc_avg);
				double base_p = 0;
				if(messageChainClassName.contains(classNode.getClassName())) {
					base_p = 0.32;
				}
				double true_p = base_p + wmc_p;
				BigDecimal df = new BigDecimal(true_p);
				double p = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if(p>=1) {
					complexClassInstance.add(classNode);
					complexClass.setProjectName(classNode.getProjectName());
					complexClass.setClassName(classNode.getClassName());
					complexClass.setWMC(classNode.getWMC());
					complexClass.setPossibility(p);
					complexClassDao.insertComplexClass(complexClass);
				}
			}
		}
		return complexClassInstance;
	}
	
	private double countPossibility(int metric, int average) {
		// TODO Auto-generated method stub
		if(average != 0) {
			double base = metric/average;
			if(base > 16) {//12
				return 1.0;
			}else if(base > 14) {
				return 0.9;  
			}else if(base > 16) {
				return 0.8;
			}else if(base > 14) {
				return 0.7;
			}else if(base > 12) {
				return 0.6;
			}else if(base > 10) {
				return 0.5;
			}else if(base > 8) {
				return 0.4;
			}else if(base > 6) {
				return 0.3;
			}else if(base > 4) {
				return 0.2;
			}else if(base > 2) {
				return 0.1;
			}else {
				return 0;
			}
		}
		return 0;
	}

	private List<ClassNode> filterComplexClass(List<ClassNode> classNodeList) {
		// TODO Auto-generated method stub
		List<ClassNode> complexClassSuspect = new ArrayList<>();
		List<Integer> wmc_list = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(classNodeList)) {
			for(ClassNode classNode : classNodeList) {
				wmc_list.add(classNode.getWMC());
			}
			wmc_avg = countAvg(wmc_list);
			for(ClassNode classNode : classNodeList) {
				int wmc = classNode.getWMC();
				if(wmc > wmc_avg) {
					complexClassSuspect.add(classNode);
				}
			}
		}		
		return complexClassSuspect;
	}
	
	private int countAvg(List<Integer> metricList) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=0; i<metricList.size(); i++) {
			sum += metricList.get(i);
		}
		int average = sum/metricList.size();
		return average;
	}

}
