package smelldetector.ast.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smelldetector.dao.MessageChainDao;
import smelldetector.dao.MethodLinkDao;
import smelldetector.dao.MethodNodeDao;
import smelldetector.dao.MethodTreeDao;
import smelldetector.pojo.MessageChain;
import smelldetector.pojo.MethodLink;
import smelldetector.pojo.MethodNode;
import smelldetector.pojo.MethodTree;
import smelldetector.util.CollectionUtil;

public class MessageChainDe {
	
	MethodLinkDao methodLinkDao = new MethodLinkDao();
	MethodTreeDao methodTreeDao = new MethodTreeDao();
	List<MethodLink> methodLinkList = new ArrayList<>();
	List<MethodTree> methodTreeList = new ArrayList<>();
	Map<String,Object> queryMap = new HashMap<>();
	double mcc_avg = 0;
	
	public void messageChainAnalysis(String projectName) {

		List<MethodTree> methodTreeTrueList = new ArrayList<>();
		List<MethodLink> messageChainSuspect = new ArrayList<>();
		queryMap.put("projectName", projectName);
		methodLinkList = methodLinkDao.findMethodLinkList(queryMap);
		methodTreeList = methodTreeDao.findMethodTreeList(queryMap);
		
//		System.out.println("methodLinkList Metrics...");
//		System.out.println(methodLinkList.size());
//		System.out.println(methodTreeList.size());

		messageChainSuspect = filterMessageChain(methodLinkList);
		methodTreeTrueList = messageChainDe(messageChainSuspect);
//		methodTreeTrueList = messageChain_Palomba(methodLinkList);//Palomba的检测方法
		for(MethodTree methodTree : methodTreeList) {
			methodTree.setIsMC(0);
			if(methodTreeTrueList.contains(methodTree)) {
				methodTree.setIsMC(1);
			}
			methodTreeDao.updateIsMC(methodTree);
		}
	}

	private List<MethodTree> messageChainDe(List<MethodLink> messageChainSuspect) {
		// TODO Auto-generated method stub
		List<MethodLink> messageChainInstance = new ArrayList<>();
		List<MethodTree> methodTreeTrueList = new ArrayList<>();
		MethodNodeDao methodNodeDao = new MethodNodeDao();
		if(CollectionUtil.isNotNullOrEmpty(messageChainSuspect)) {
			for(MethodLink methodLink : messageChainSuspect) {
				MessageChain messageChain = new MessageChain();
				MessageChainDao messageChainDao = new MessageChainDao();
				int mcc = methodLink.getMCC();
				double mcc_p = countPossibility(mcc, mcc_avg);
				BigDecimal df = new BigDecimal(mcc_p);
				double p = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				
				if(p>=1) {
					MethodNode methodNode = methodNodeDao.findMethodNodeById(methodLink.getSource());
					MethodTree methodTree = findMethodTree(methodNode, methodTreeList);
					methodTreeTrueList.add(methodTree);
					messageChain.setProjectName(methodTree.getProjectName());
					messageChain.setClassName(methodTree.getClassName());
					messageChain.setMethodName(methodTree.getMethodName());
					messageChain.setMCC(methodLink.getMCC());
					messageChain.setPossibility(p);
					messageChainDao.insertMessageChain(messageChain);
				}
			}
		}
		return methodTreeTrueList;
	}

	private double countPossibility(int metric, double average) {
		// TODO Auto-generated method stub
		if(average != 0) {
			double base = metric/average;
//			if(base > 2.0) 
			if(base > 4.5) 
			{
				return 1.0;
			}else if(base > 0.9) {
				return 0.9;  
			}else if(base > 0.8) {
				return 0.8;
			}else if(base > 0.7) {
				return 0.7;
			}else if(base > 0.6) {
				return 0.6;
			}else if(base > 0.5) {
				return 0.5;
			}else if(base > 0.4) {
				return 0.4;
			}else if(base > 0.3) {
				return 0.3;
			}else if(base > 0.2) {
				return 0.2;
			}else if(base > 0.1) {
				return 0.1;
			}else {
				return 0;
			}
		}
		return 0;
	}

	private List<MethodLink> filterMessageChain(List<MethodLink> methodLinkList) {
		// TODO Auto-generated method stub
		List<MethodLink> messageChainSuspect = new ArrayList<>();
		List<Integer> mccList = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(methodLinkList)) {
			for(MethodLink methodLink : methodLinkList) {
				mccList.add(methodLink.getMCC());
//				System.out.println("methodLink:");
//				System.out.println(methodLink);
			}
			mcc_avg = countAvg(mccList);
			
			for(MethodLink methodLink : methodLinkList) {
				 int mcc = methodLink.getMCC();
				if(mcc > mcc_avg) {
					messageChainSuspect.add(methodLink);
				}
			}
		}
		return messageChainSuspect;
	}

	private double countAvg(List<Integer> metricList) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=0; i<metricList.size(); i++) {
			sum += metricList.get(i);
		}
		double average = sum/metricList.size();
		return average;
	}
	
	private MethodTree findMethodTree(MethodNode methodNode, List<MethodTree> methodTreeList) {
		if(CollectionUtil.isNotNullOrEmpty(methodTreeList)) {
			for(MethodTree methodTree : methodTreeList) {
				if(methodTree.getClassName().equals(methodNode.getClassName()) && 
						methodTree.getMethodName().equals(methodNode.getMethodName()) && 
						methodTree.getNOP().equals(methodNode.getArguementCount()) && 
						methodTree.getMLOC().equals(methodNode.getMethodLine())) {
					return methodTree;
				}
			}
		}
		return null;
		
	}
	
	private List<MethodTree> messageChain_Palomba(List<MethodLink> methodLinkList) {
		List<MethodLink> messageChainInstance = new ArrayList<>();
		List<MethodTree> methodTreeTrueList = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(methodLinkList)) {
			MethodNodeDao methodNodeDao = new MethodNodeDao();
			for(MethodLink methodLink : methodLinkList) {
				MessageChain messageChain = new MessageChain();
				MessageChainDao messageChainDao = new MessageChainDao();
				if(methodLink.getMCC()>5) {
					MethodNode methodNode = methodNodeDao.findMethodNodeById(methodLink.getSource());
					MethodTree methodTree = findMethodTree(methodNode, methodTreeList);
					if(!methodTreeTrueList.contains(methodTree)) {
						methodTreeTrueList.add(methodTree);
						messageChain.setProjectName(methodTree.getProjectName());
						messageChain.setClassName(methodTree.getClassName());
						messageChain.setMethodName(methodTree.getMethodName());
						messageChain.setMCC(methodLink.getMCC());
						messageChainDao.insertMessageChain(messageChain);
					}else {
						continue;
					}
				}
			}
		}
		return methodTreeTrueList;
	}

}
