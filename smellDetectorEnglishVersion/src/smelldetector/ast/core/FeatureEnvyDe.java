package smelldetector.ast.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smelldetector.dao.FeatureEnvyDao;
import smelldetector.dao.MethodTreeDao;
import smelldetector.pojo.FeatureEnvy;
import smelldetector.pojo.MethodTree;
import smelldetector.util.CollectionUtil;

public class FeatureEnvyDe {
	
	 float atfd_avg = 0;
	
	public void featureEnvyAnalysis(String projectName) {
		MethodTreeDao methodTreeDao = new MethodTreeDao();
		List<MethodTree> methodTreeList = new ArrayList<>();
		List<MethodTree> featureEnvySuspect = new ArrayList<>();
		List<MethodTree> featureEnvyInstance = new ArrayList<>();
		Map<String,Object> queryMap = new HashMap<>();
		
		queryMap.put("projectName", projectName);
		methodTreeList = methodTreeDao.findMethodTreeList(queryMap);
		featureEnvySuspect = filterFeatureEnvy(methodTreeList);
		featureEnvyInstance = featureEnvyDe(featureEnvySuspect);
		for(MethodTree methodTree : methodTreeList) {
			methodTree.setIsFE(0);
			if(featureEnvyInstance.contains(methodTree)) {
				methodTree.setIsFE(1);
			}
			methodTreeDao.updateIsFE(methodTree);
		}
	}

	private List<MethodTree> featureEnvyDe(List<MethodTree> featureEnvySuspect) {
		// TODO Auto-generated method stub
		List<MethodTree> featureEnvyInstance = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(featureEnvySuspect)) {
			for(MethodTree methodTree : featureEnvySuspect) {
				FeatureEnvy featureEnvy = new FeatureEnvy();
				FeatureEnvyDao featureEnvyDao = new FeatureEnvyDao();
				int atfd = methodTree.getATFD();
				double atfd_p = countPossibility(atfd, atfd_avg);
				BigDecimal df = new BigDecimal(atfd_p);
				double p = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if(p>=1) {
					featureEnvyInstance.add(methodTree);
					featureEnvy.setProjectName(methodTree.getProjectName());
					featureEnvy.setClassName(methodTree.getClassName());
					featureEnvy.setMethodName(methodTree.getMethodName());
					featureEnvy.setATFD(methodTree.getATFD());
					featureEnvy.setPossibility(p);
					featureEnvyDao.insertFeatureEnvy(featureEnvy);
				}
			}
		}
		
		return featureEnvyInstance;
	}

	private double countPossibility(int metric, float average) {
		// TODO Auto-generated method stub
		if(average != 0) {
			double base = metric/average;
			if(base > 25) {
				return 1.0;
			}else if(base > 9) {
				return 0.9;  
			}else if(base > 8) {
				return 0.8;
			}else if(base > 7) {
				return 0.7;
			}else if(base > 6) {
				return 0.6;
			}else if(base > 5) {
				return 0.5;
			}else if(base > 4) {
				return 0.4;
			}else if(base > 3) {
				return 0.3;
			}else if(base > 2) {
				return 0.2;
			}else if(base > 1) {
				return 0.1;
			}else {
				return 0;
			}
		}
		return 0;
	}

	private List<MethodTree> filterFeatureEnvy(List<MethodTree> methodTreeList) {
		// TODO Auto-generated method stub
		List<MethodTree> featureEnvySuspect = new ArrayList<>();
		List<Integer> atfd_list = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(methodTreeList)) {
			for(MethodTree methodTree : methodTreeList) {
				atfd_list.add(methodTree.getATFD());
			}
		}
		atfd_avg = count_avg(atfd_list);
		for(MethodTree methodTree : methodTreeList) {
			int atfd = methodTree.getATFD();
			if(atfd > atfd_avg) {
				featureEnvySuspect.add(methodTree);
			}
		}
		
		return featureEnvySuspect;
	}

	private float count_avg(List<Integer> metric_list) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=0; i<metric_list.size(); i++) {
			sum += metric_list.get(i);
		}
		float average = (float)sum/metric_list.size();
		return average;
	}

}
