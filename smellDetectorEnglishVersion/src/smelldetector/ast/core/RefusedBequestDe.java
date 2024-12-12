package smelldetector.ast.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smelldetector.dao.ClassNodeDao;
import smelldetector.dao.MessageChainDao;
import smelldetector.dao.RefusedBequestDao;
import smelldetector.pojo.ClassNode;
import smelldetector.pojo.MessageChain;
import smelldetector.pojo.RefusedBequest;
import smelldetector.util.CollectionUtil;

public class RefusedBequestDe {
	
	List<MessageChain> messageChainList = new ArrayList<>();
	float norm_avg = 0;
	float bur_avg = 0;
	float nprotm_avg = 0;
	
	public void refusedBequestAnalysis(String projectName) {
		ClassNodeDao classNodeDao = new ClassNodeDao();
		MessageChainDao messageChainDao = new MessageChainDao();
		List<ClassNode> classNodeList = new ArrayList<>();
		List<ClassNode> refusedBequestSuspect = new ArrayList<>();
		List<ClassNode> refusedBequestInstance = new ArrayList<>();
		Map<String, Object> queryMap = new HashMap<>();
		
		queryMap.put("projectName", projectName);
		messageChainList = messageChainDao.findAllMessageChain(queryMap);
		classNodeList = classNodeDao.findClassNodeList(queryMap);
		refusedBequestSuspect = filterRefusedBequest(classNodeList);
		refusedBequestInstance = refusedBequestDe(refusedBequestSuspect);
		for(ClassNode classNode : classNodeList) {
			classNode.setIsRB(0);
			if(refusedBequestInstance.contains(classNode)) {
				classNode.setIsRB(1);
			}
			classNodeDao.updateIsRB(classNode);
		}
	}

	private List<ClassNode> refusedBequestDe(List<ClassNode> refusedBequestSuspect) {
		// TODO Auto-generated method stub
		List<ClassNode> refusedBequestInstance = new ArrayList<>();
		List<String> messageChainClassName = new ArrayList<>();
		for(MessageChain messageChain : messageChainList) {
			messageChainClassName.add(messageChain.getClassName());
		}
		if(CollectionUtil.isNotNullOrEmpty(refusedBequestSuspect)) {
			for(ClassNode classNode : refusedBequestSuspect) {
				RefusedBequest refusedBequest = new RefusedBequest();
				RefusedBequestDao refusedBequestDao = new RefusedBequestDao();
				int norm = classNode.getNORM();
				int nprotm = classNode.getNPROM();
				float bur = classNode.getBUR();
				double norm_p = countPossibility(norm, norm_avg);
				double nprotm_p = countPossibility(nprotm, nprotm_avg);
				double bur_p = countPossibility_2(bur, bur_avg);
				double base_p = 0;
				if(messageChainClassName.contains(classNode.getClassName())) {
					base_p = 0.35;
				}
				double true_p = base_p + norm_p + nprotm_p;
				
				BigDecimal df = new BigDecimal(true_p);
				double p = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if(p>=1) {
					refusedBequestInstance.add(classNode);
					refusedBequest.setProjectName(classNode.getProjectName());
					refusedBequest.setClassName(classNode.getClassName());
					refusedBequest.setNORM(classNode.getNORM());
					refusedBequest.setNPROTM(classNode.getNPROM());
					refusedBequest.setPossibility(p);
					refusedBequest.setBUR(bur);
					refusedBequestDao.insertRefusedBequest(refusedBequest);
				}
			}
		}
		
		return refusedBequestInstance;
	}

	private double countPossibility(int metric, float average) {
		// TODO Auto-generated method stub
		if(average != 0) {
			double base = metric/average;
			if(base > 15) {//6.5
				return 1.0;
			}else if(base > 14) {
				return 0.9;  
			}else if(base > 13) {
				return 0.8;
			}else if(base > 12) {
				return 0.7;
			}else if(base > 11) {
				return 0.6;
			}else if(base > 10) {
				return 0.5;
			}else if(base > 9) {
				return 0.4;
			}else if(base > 6){
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
	private double countPossibility_2(float metric, float average) {
		// TODO Auto-generated method stub
		if(average != 0) {
			double base = metric/average;
			if(base > 15) {//5
				return 1.0;
			}else if(base > 14) {
				return 0.9;  
			}else if(base > 13) {
				return 0.8;
			}else if(base > 12) {
				return 0.7;
			}else if(base > 11) {
				return 0.6;
			}else if(base > 10) {
				return 0.5;
			}else if(base > 9) {
				return 0.4;
			}else if(base > 6){
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

	private List<ClassNode> filterRefusedBequest(List<ClassNode> classNodeList) {
		// TODO Auto-generated method stub
		List<ClassNode> candidates = new ArrayList<>();
		List<ClassNode> refusedBequestSuspect = new ArrayList<>();
		List<Integer> norm_list = new ArrayList<>();
		List<Float> bur_list = new ArrayList<>();
		List<Integer> nprotm_list = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(classNodeList)) {
			for(ClassNode classNode : classNodeList) {
				if(classNode.getSuperTyper()!=0) {
					candidates.add(classNode);
				}
			}

		}
		if(CollectionUtil.isNotNullOrEmpty(candidates)) {
			for(ClassNode classNode : candidates) {
				if(classNode.getNORM()!=0) {
					norm_list.add(classNode.getNORM());
				}	
				bur_list.add(classNode.getBUR());
				nprotm_list.add(classNode.getNPROM());
			}
			norm_avg = countAvg(norm_list);
			nprotm_avg = countAvg(nprotm_list);
			bur_avg = countAvg_2(bur_list);
			for(ClassNode classNode : candidates) {
				int norm = classNode.getNORM();
				int nprotm = classNode.getNPROM();
				float bur = classNode.getBUR();
				if(norm > norm_avg || nprotm > nprotm_avg) {
					refusedBequestSuspect.add(classNode);
				}
			}
		}
		
		return refusedBequestSuspect;
	}

	private float countAvg(List<Integer> metric_list) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=0; i<metric_list.size(); i++) {
			sum += metric_list.get(i);
		}
		float average = (float)sum/metric_list.size();
		return average;
	}
	
	private float countAvg_2(List<Float> metric_list) {
		// TODO Auto-generated method stub
		float sum = 0;
		for(int i=0; i<metric_list.size(); i++) {
			sum += metric_list.get(i);
		}
		float average = sum/metric_list.size();
		return average;
	}

}
