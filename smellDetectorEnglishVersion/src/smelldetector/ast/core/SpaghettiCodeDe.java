package smelldetector.ast.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smelldetector.dao.ClassNodeDao;
import smelldetector.dao.MessageChainDao;
import smelldetector.dao.MethodTreeDao;
import smelldetector.dao.SpaghettiCodeDao;
import smelldetector.pojo.ClassNode;
import smelldetector.pojo.MessageChain;
import smelldetector.pojo.MethodTree;
import smelldetector.pojo.SpaghettiCode;
import smelldetector.util.CollectionUtil;

public class SpaghettiCodeDe {
	
	Map<String, Integer> maxNOGVMap = new HashMap<>();
	List<MessageChain> messageChainList = new ArrayList<>();
	int NMNOPARAM_avg = 0;
	int LOC_avg = 0;
	int NOGV_avg = 0;
	
	public void spaghettiCodeAnalysis(String projectName) {
		ClassNodeDao classNodeDao = new ClassNodeDao();
		MethodTreeDao methodTreeDao = new MethodTreeDao();
		MessageChainDao messageChainDao = new MessageChainDao();
		List<ClassNode> classNodeList = new ArrayList<>();
		List<ClassNode> spaghettiCodeSuspect = new ArrayList<>();
		List<ClassNode> spaghettiCodeInstance = new ArrayList<>();
		List<MethodTree> methodTreeList = new ArrayList<>();
		Map<String,Object> queryMap = new HashMap<>();
		
		queryMap.put("projectName", projectName);
		classNodeList = classNodeDao.findClassNodeList(queryMap);
		methodTreeList = methodTreeDao.findMethodTreeList(queryMap);
		messageChainList = messageChainDao.findAllMessageChain(queryMap);
		spaghettiCodeSuspect = filterSpaghttiCode(classNodeList, methodTreeList);
		spaghettiCodeInstance = spaghettiCodeDe(spaghettiCodeSuspect);
		
		for(ClassNode classNode : classNodeList) {
			classNode.setIsSC(0);
			if(spaghettiCodeInstance.contains(classNode)) {
				classNode.setIsSC(1);
			}
			classNodeDao.updateIsSC(classNode);
		}
	}

	private List<ClassNode> spaghettiCodeDe(List<ClassNode> spaghettiCodeSuspect) {
		// TODO Auto-generated method stub
		List<ClassNode> spaghettiCodeInstance = new ArrayList<>();
		List<String> messageChainClassName = new ArrayList<>();
		SpaghettiCodeDao spaghettiCodeDao = new SpaghettiCodeDao();
		for(MessageChain messageChain : messageChainList) {
			messageChainClassName.add(messageChain.getClassName());
		}
		if(CollectionUtil.isNotNullOrEmpty(spaghettiCodeSuspect)) {
			for(ClassNode classNode : spaghettiCodeSuspect) {
				SpaghettiCode spaghettiCode = new SpaghettiCode();
				int NMNOPARAM = classNode.getNMNOPARAM();
				int LOC = classNode.getLOC();
				int max_NOGV = maxNOGVMap.get(classNode.getClassName());
				double NMNOPARAM_p = countPossibility(NMNOPARAM, NMNOPARAM_avg);
				double LOC_p = countPossibility(LOC, LOC_avg);
				double NOGV_p = countPossibility_2(max_NOGV, NOGV_avg);
				double base_p = 0;
				if(messageChainClassName.contains(classNode.getClassName())) {
					base_p = 0.17;
				}
				double true_p = base_p + NMNOPARAM_p + LOC_p + NOGV_p;
				BigDecimal df = new BigDecimal(true_p);
				double p = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if(p >= 1) {
					spaghettiCode.setProjectName(classNode.getProjectName());
					spaghettiCode.setClassName(classNode.getClassName());
					spaghettiCode.setNMNOPARAM(NMNOPARAM);
					spaghettiCode.setLOC(LOC);
					spaghettiCode.setNOGV(max_NOGV);
					spaghettiCode.setPossibility(true_p);
					spaghettiCodeInstance.add(classNode);
					spaghettiCodeDao.insertSpaghettiCode(spaghettiCode);
				}
			}
		}
		return spaghettiCodeInstance;
	}
	
	private double countPossibility(int metric, int average) {
		// TODO Auto-generated method stub
		if(average !=0 ) {
			double base = metric / average;
			if(base > 20.5 ) {//11.5
				return 1.0;
			}else if(base > 18.5) {//10.5
				return 0.9;  
			}else if(base > 16.5) {//9.5
				return 0.8;
			}else if(base > 14.5) {//8.5
				return 0.7;
			}else if(base > 12.5) {//7.5
				return 0.6;
			}else if(base > 10.5) {//6.5
				return 0.5;
			}else if(base > 8.5) {//5.5
				return 0.4;
			}else if(base > 6.5) {//4.5
				return 0.3;
			}else if(base > 4.5) {//3.5
				return 0.2;
			}else if(base > 2.5) {//2.5
				return 0.1;
			}else {
				return 0;
			}
		}else {
			return 0;
		}
	
	}
	
	private double countPossibility_2(int metric, int average) {
		// TODO Auto-generated method stub
		if(average !=0 ) {
			double base = metric / average;
			if(base > 35) {
				return 1.0;
			}else if(base > 30) {
				return 0.9;  
			}else if(base > 25) {
				return 0.8;
			}else if(base > 20) {
				return 0.7;
			}else if(base > 15) {
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
		}else {
			return 0;
		}
	
	}

	private List<ClassNode> filterSpaghttiCode(List<ClassNode> classNodeList, List<MethodTree> methodTreeList) {
		// TODO Auto-generated method stub
		List<ClassNode> spaghettiCodeSuspect = new ArrayList<>();
		List<Integer> NMNOPARAM_list = new ArrayList<>(); //类内无参数方法个数的集合
		List<Integer> LOC_list = new ArrayList<>(); //类代码行数的集合
		List<Integer> NOGV_list = new ArrayList<>(); //方法内全局变量个数的集合
		List<ClassNode> classNodeSuspectList = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(classNodeList) && CollectionUtil.isNotNullOrEmpty(methodTreeList)) {
			for(ClassNode classNode : classNodeList) {
				NMNOPARAM_list.add(classNode.getNMNOPARAM());
				LOC_list.add(classNode.getLOC());
				if(classNode.getNMNOPARAM() != 0 && classNode.getNMNOPARAM() != null) {
					classNodeSuspectList.add(classNode);
					maxNOGVMap.put(classNode.getClassName(), 0);
				}
			}
			for(MethodTree methodTree : methodTreeList) {
				String className = methodTree.getClassName();
				NOGV_list.add(methodTree.getNOGV());
				if(maxNOGVMap.get(className)!=null && methodTree.getNOP()==0) {
					int max_nogv = maxNOGVMap.get(className);
					if(methodTree.getNOGV() > max_nogv) {
						maxNOGVMap.put(className, methodTree.getNOGV());
					}					
				}
			}
		}
		NMNOPARAM_avg = countAvg(NMNOPARAM_list);
		LOC_avg = countAvg(LOC_list);
		NOGV_avg = countAvg(NOGV_list);
		for(ClassNode classNodeSuspect : classNodeSuspectList) {
			String className = classNodeSuspect.getClassName();
			int NMNOPARAM = classNodeSuspect.getNMNOPARAM();
			int LOC = classNodeSuspect.getLOC();
			int max_NOGV = maxNOGVMap.get(className);
			if(NMNOPARAM>NMNOPARAM_avg && LOC>LOC_avg && max_NOGV>NOGV_avg) {
				spaghettiCodeSuspect.add(classNodeSuspect);
			}
		}
		return spaghettiCodeSuspect;
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
