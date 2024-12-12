package smelldetector.ast.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smelldetector.dao.LongMethodDao;
import smelldetector.dao.MethodNodeDao;
import smelldetector.dao.MethodTreeDao;
import smelldetector.pojo.LongMethod;
import smelldetector.pojo.MethodNode;
import smelldetector.pojo.MethodTree;
import smelldetector.util.CollectionUtil;

public class LongMethodDe {
	
	float mloc_avg = 0;
	float nop_avg = 0;
	float nos_avg  = 0;
	float treeDepth_avg = 0;
	float treeWidth_avg = 0;
	float nodeSum_avg = 0;
	float maxChildren_avg = 0;
	
	
	public void longMethodAnalysis(String projectName) {
		MethodTreeDao methodTreeDao = new MethodTreeDao();
		MethodNodeDao methodNodeDao = new MethodNodeDao();
		List<MethodNode> methodNodeList = new ArrayList<>();
		List<MethodTree> methodTreeList = new ArrayList<>();
		List<MethodTree> longMethodSuspect = new ArrayList<>();
		List<MethodTree> longMethodInstance = new ArrayList<>();
		Map<String,Object> queryMap = new HashMap<>();
		
		queryMap.put("projectName", projectName);
		methodNodeList = methodNodeDao.findMethodNodeList(queryMap);
		methodTreeList = methodTreeDao.findMethodTreeList(queryMap);
		longMethodSuspect = filterLongMethod(methodTreeList);
		longMethodInstance = longMethodDe(longMethodSuspect);
		
		if(CollectionUtil.isNotNullOrEmpty(methodTreeList)) {
			for(MethodTree methodTree : methodTreeList) {
				methodTree.setIsLM(0);
				if(longMethodInstance.contains(methodTree)) {
					methodTree.setIsLM(1);
				}
				methodTreeDao.updateIsLM(methodTree);
			}
		}
	}

	private List<MethodTree> filterLongMethod(List<MethodTree> methodTreeList) {
		// TODO Auto-generated method stub
		List<MethodTree> longMethodSuspect = new ArrayList<>();
		List<Integer> mlocList = new ArrayList<>();//代码行数
		List<Integer> nopList = new ArrayList<>();//参数个数
		List<Integer> nosList = new ArrayList<>();//语句个数
		List<Integer> treeDepthList = new ArrayList<>();//树深度
		List<Integer> treeWidthList = new ArrayList<>();//树宽度
		List<Integer> nodeSumList = new ArrayList<>();//树节点总数
		List<Integer> maxChildrenList = new ArrayList<>();//树度数
		if(CollectionUtil.isNotNullOrEmpty(methodTreeList)) {
			for(MethodTree methodTree : methodTreeList) {
				mlocList.add(methodTree.getMLOC());
				nopList.add(methodTree.getNOP());
				nosList.add(methodTree.getNOS());
				treeDepthList.add(methodTree.getTreeDepth());
				treeWidthList.add(methodTree.getTreeWidth());
				nodeSumList.add(methodTree.getNodeSum());
				maxChildrenList.add(methodTree.getMaxChildren());
			}
			mloc_avg = countAvg(mlocList);
			nop_avg = countAvg(nopList);
			nos_avg = countAvg(nosList);
			treeDepth_avg = countAvg(treeDepthList);
			treeWidth_avg = countAvg(treeWidthList);
			nodeSum_avg = countAvg(nodeSumList);
			maxChildren_avg = countAvg(maxChildrenList);
			for(MethodTree methodTree : methodTreeList) {
				int mloc = methodTree.getMLOC();
				int nop = methodTree.getNOP();
				int nos = methodTree.getNOS();
				int treeDepth = methodTree.getTreeDepth();
				int treeWidth = methodTree.getTreeWidth();
				int nodeSum = methodTree.getNodeSum();
				int maxChildren = methodTree.getMaxChildren();
				if(mloc>mloc_avg || (nop>nop_avg && nos>nos_avg) || (treeDepth>treeDepth_avg && 
						treeWidth>treeWidth_avg  && maxChildren>nodeSum_avg) || nodeSum>maxChildren_avg) {
					longMethodSuspect.add(methodTree);
				}
			}
			
		}
		return longMethodSuspect;
	}

	private float countAvg(List<Integer> metricList) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=0; i<metricList.size(); i++) {
			sum += metricList.get(i);
		}
		float average = sum/metricList.size();
		return average;
	}

	//LongMethod检测
	private List<MethodTree> longMethodDe(List<MethodTree> longMethodSuspect) {
		// TODO Auto-generated method stub
		List<MethodTree> longMethodInstance = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(longMethodSuspect)) {
			LongMethodDao longMethodDao = new LongMethodDao();
			LongMethod longMethod = new LongMethod();
			for(MethodTree methodTree : longMethodSuspect) {
				int mloc = methodTree.getMLOC();//代码行数
				int nop = methodTree.getNOP();//参数个数
				int nos = methodTree.getNOS();//语句个数
				int treeDepth = methodTree.getTreeDepth();//树深度
				int treeWidth = methodTree.getTreeWidth();//树宽度
				int nodeSum = methodTree.getNodeSum();//树节点总数
				int maxChildren = methodTree.getMaxChildren();//树度数
				double mloc_p = countPossibility(mloc, mloc_avg);
				double nop_p = countPossibility(nop, nop_avg);
				double nos_p = countPossibility(nos, nos_avg);
				double treeDepth_p = countPossibility(treeDepth, treeDepth_avg);
				double treeWidth_p = countPossibility(treeWidth, treeWidth_avg);
				double nodeSum_p = countPossibility(nodeSum, nodeSum_avg);
				double maxChildren_p = countPossibility(maxChildren, maxChildren_avg);
				double base_p = 0;
				double true_p = mloc_p + nop_p + nos_p + treeDepth_p + treeWidth_p + nodeSum_p + maxChildren_p;
				BigDecimal df = new BigDecimal(true_p);
				double p = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				
				if(p > 1) {
					longMethod.setProjectName(methodTree.getProjectName());
					longMethod.setClassName(methodTree.getClassName());
					longMethod.setMethodName(methodTree.getMethodName());
					longMethod.setMLOC(methodTree.getMLOC());
					longMethod.setNOP(methodTree.getNOP());
					longMethod.setNOS(methodTree.getNOS());
					longMethod.setTreeDepth(methodTree.getTreeDepth());
					longMethod.setTreeWidth(methodTree.getTreeWidth());
					longMethod.setNodeSum(methodTree.getNodeSum());
					longMethod.setMaxChildren(methodTree.getMaxChildren());
					longMethod.setPossibility(p);
					longMethodDao.insertLongMethod(longMethod);
					longMethodInstance.add(methodTree);
					
				}
				
			}
		}
		
		return longMethodInstance;
	}

	private double countPossibility(int metric, float average) {
		// TODO Auto-generated method stub
		if(average !=0 ) {
			double base = metric / average;
			if(base > 30) {
				return 1.0;
			}else if(base > 27) {
				return 0.9;  
			}else if(base > 24) {
				return 0.8; 
			}else if(base > 21) {
				return 0.7;
			}else if(base > 18) {
				return 0.6;
			}else if(base > 15) {
				return 0.5;	
			}else if(base > 12) {
				return 0.4;
			}else if(base > 9) {
				return 0.3;
			}else if(base > 6) {
				return 0.2;
			}else if(base > 3) {
				return 0.1;
			}else {
				return 0;
			}
		}else {
			return 0;
		}

		
	}

}
