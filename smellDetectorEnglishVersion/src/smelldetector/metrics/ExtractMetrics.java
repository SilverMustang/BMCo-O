package smelldetector.metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smelldetector.dao.MethodLinkDao;
import smelldetector.pojo.MethodLink;
import smelldetector.util.CollectionUtil;

public class ExtractMetrics {
	
	List<MethodLink> methodLinkList = new ArrayList<>();
	
	public void extractMetrics(String projectName) {
		MethodLinkDao methodLinkDao = new MethodLinkDao();
		Map<String,Object> queryMap = new HashMap<>();
		
		queryMap.put("projectName", projectName);
		methodLinkList = methodLinkDao.findMethodLinkList(queryMap);
//		System.out.println("mcc...");
//		System.out.println(methodLinkList.get(1));
		extractMCC(methodLinkList);
	}

	private void extractMCC(List<MethodLink> methodLinkList) {
		// TODO Auto-generated method stub
		List<List<Integer>> methodChainList = new ArrayList<>();
		MethodLinkDao methodLinkDao = new MethodLinkDao();
		if(CollectionUtil.isNotNullOrEmpty(methodLinkList)) {
			for(MethodLink methodLink : methodLinkList) {
				
				
				List<List<Integer>> chainList = new ArrayList<>();
				List<Integer> chain = new ArrayList<>();
				chain.add(methodLink.getSource());
			    methodChain(chainList, methodLink, chain);
			    if(chainList.size()<=1) {
			    	int mcc = chainList.get(0).size();
			    	methodLink.setMCC(mcc);
			    }else if(chainList.size()>1) {
			    	int mcc = 0;
			    	for(int i=0; i<chainList.size(); i++) {
			    		if(chainList.get(i).size()>mcc) {
			    			mcc = chainList.get(i).size();
			    		}
			    	}
			    	System.out.println("mcc");
			    	System.out.println(mcc);
			    	methodLink.setMCC(mcc);
			    }
			    methodLinkDao.updateMCC(methodLink);
		
			    System.out.println(chainList);
			}
			//System.out.println(methodChainList);
		}

	}

	private void methodChain(List<List<Integer>> chainList, MethodLink methodLink, List<Integer> chain) {
		// TODO Auto-generated method stub
		MethodLinkDao methodLinkDao = new MethodLinkDao();
		List<MethodLink> methodList = new ArrayList<>();
		methodList = findMethodLink(methodLink.getTarget(), methodLink.getProjectName());
		if(!CollectionUtil.isNotNullOrEmpty(methodList) || chain.contains(methodLink.getTarget())) {
			chain.add(methodLink.getTarget());
			chainList.add(chain);
			return;
		}
		
		for(MethodLink method : methodList) {
			List<Integer> chain_2 = new ArrayList<>();
			chain_2.addAll(chain);
			chain_2.add(method.getSource());
//			if(!chain_2.contains(method.getSource())) {
//				chain_2.add(method.getSource());
//			}else {
//				continue;
//			}
			methodChain(chainList, method, chain_2);
		}
		
	}
	
	public List<MethodLink> findMethodLink(int source, String projectName){
		List<MethodLink> methodList = new ArrayList<>();
		for(MethodLink methodLink : methodLinkList) {
			if(methodLink.getSource() == source && methodLink.getProjectName().equals(projectName)) {
				methodList.add(methodLink);
			}
		}	
		return methodList;
	}

}
