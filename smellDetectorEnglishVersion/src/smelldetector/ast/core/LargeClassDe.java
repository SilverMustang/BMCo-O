package smelldetector.ast.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smelldetector.dao.ClassNodeDao;
import smelldetector.dao.ClassTreeDao;
import smelldetector.dao.LargeClassDao;
import smelldetector.dao.MethodTreeDao;
import smelldetector.dao.SourceDao;
import smelldetector.pojo.ClassNode;
import smelldetector.pojo.ClassTree;
import smelldetector.pojo.LargeClass;
import smelldetector.pojo.MethodTree;
import smelldetector.pojo.Source;
import smelldetector.util.CollectionUtil;

public class LargeClassDe {
	
	List<String> classNodeNameList = new ArrayList<>();
	List<MethodTree> methodTreeList = new ArrayList<>();
	Map<ClassNode, ClassTree> classMap = new HashMap<>();
	float loc_avg = 0;
	float nof_avg = 0;
	float nom_avg = 0;
	float treeDepth_avg = 0;
	float treeWidth_avg = 0;
	float nodeSum_avg = 0;
	float maxChildren_avg = 0;
	
	public void largeClassAnalysis(String projectName) {
		ClassTreeDao classTreeDao = new ClassTreeDao();
		ClassNodeDao classNodeDao = new ClassNodeDao();
		MethodTreeDao methodTreeDao = new MethodTreeDao();
		Map<String,Object> queryMap = new HashMap<>();
		List<ClassTree> classTreeList = new ArrayList<>();
		List<ClassNode> classNodeList = new ArrayList<>();
		List<ClassNode> largeClassSuspect = new ArrayList<>();
		List<String> largeClassInstance = new ArrayList<>();
		
		queryMap.put("projectName", projectName);
		classTreeList = classTreeDao.findClassTreeList(queryMap);
		classNodeList = classNodeDao.findClassNodeList(queryMap);
		methodTreeList = methodTreeDao.findMethodTreeList(queryMap);
		
		largeClassSuspect = filterLargeClass(classNodeList, classTreeList);
		largeClassInstance = largeClassDe(largeClassSuspect);
		if(CollectionUtil.isNotNullOrEmpty(classNodeList)) {
			for(ClassNode classNode : classNodeList) {
				classNode.setIsLC(0);
				if(largeClassInstance.contains(classNode.getClassName())) {
					classNode.setIsLC(1);
				}
				classNodeDao.updateIsLC(classNode);
			}
		}
	}

	private List<ClassNode> filterLargeClass(List<ClassNode> classNodeList, List<ClassTree> classTreeList) {
		// TODO Auto-generated method stub
		List<ClassNode> largeClassSuspect = new ArrayList<>();
		List<Integer> locList = new ArrayList<>();
		List<Integer> nomList = new ArrayList<>();
		List<Integer> nofList = new ArrayList<>();
		List<Integer> treeDepthList = new ArrayList<>();//树深度
		List<Integer> treeWidthList = new ArrayList<>();//树宽度
		List<Integer> nodeSumList = new ArrayList<>();//树节点总数
		List<Integer> maxChildrenList = new ArrayList<>();//树度数
		
		
		if(CollectionUtil.isNotNullOrEmpty(classNodeList) & CollectionUtil.isNotNullOrEmpty(classTreeList)) {
			for(ClassNode classNode : classNodeList) {
				locList.add(classNode.getLOC());
				nomList.add(classNode.getNOM());
				nofList.add(classNode.getNOF());
				for(ClassTree classTree : classTreeList) {
					if(classTree.getClassName().equals(classNode.getClassName())) {
						classMap.put(classNode, classTree);
					}
				}
			}
			
			for(ClassTree classTree : classTreeList) {
				treeDepthList.add(classTree.getTreeDepth());
				treeWidthList.add(classTree.getTreeWidth());
				nodeSumList.add(classTree.getNodeSum());
				maxChildrenList.add(classTree.getMaxChildren());
			}

			loc_avg = countAvg(locList);
			nom_avg = countAvg(nomList);
			nof_avg = countAvg(nofList);
			treeDepth_avg = countAvg(treeDepthList);
			treeWidth_avg = countAvg(treeWidthList);
			nodeSum_avg = countAvg(nodeSumList);
			maxChildren_avg = countAvg(maxChildrenList);
			
			for(ClassNode classNode : classNodeList) {
				ClassTree classTree = classMap.get(classNode);
				int loc = classNode.getLOC();
				int nom = classNode.getNOM();
				int nof = classNode.getNOF();
				int treeDepth = classTree.getTreeDepth();
				int treeWidth = classTree.getTreeWidth();
				int nodeSum = classTree.getNodeSum();
				int maxChildren = classTree.getMaxChildren();
				if(loc>1000) {
					if(loc>loc_avg || (nom>nom_avg && nof>nof_avg) || nodeSum>nodeSum_avg || 
							(treeDepth>treeDepth_avg && treeWidth>treeWidth_avg && maxChildren>maxChildren_avg)) {
						largeClassSuspect.add(classNode);
					}
				}
			}
		}
		
		return largeClassSuspect;
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

	//LargeClass检测
	private List<String> largeClassDe(List<ClassNode> largeClassSuspect) {
		// TODO Auto-generated method stub
		List<String> largeClassInstance = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(largeClassSuspect)) {
			LargeClassDao largeClassDao = new LargeClassDao();
			LargeClass largeClass = new LargeClass();
			for(ClassNode classNode : largeClassSuspect) {
				ClassTree classTree = classMap.get(classNode);
				int loc = classNode.getLOC();
				int nom = classNode.getNOM();
				int nof = classNode.getNOF();
				int treeDepth = classTree.getTreeDepth();
				int treeWidth = classTree.getTreeWidth();
				int nodeSum = classTree.getNodeSum();
				int maxChildren = classTree.getMaxChildren();
				double loc_p = countPossibility(loc, loc_avg);
				double nom_p = countPossibility(nom, nom_avg);
				double nof_p = countPossibility(nof, nof_avg);
				double treeDepth_p = countPossibility(treeDepth, treeDepth_avg);
				double treeWidth_p = countPossibility(treeWidth, treeWidth_avg);
				double nodeSum_p = countPossibility(nodeSum, nodeSum_avg);
				double maxChildren_p = countPossibility(maxChildren, maxChildren_avg);
				double base_p = 0;
				for(MethodTree methodTree : methodTreeList) {
					if(classNode.getClassName().equals(methodTree.getClassName())) {
						if(methodTree.getIsMC() == 1) {
							base_p = 0.13;
							break;
						}
					}
				}
				
				double true_p = base_p + loc_p + nom_p + nof_p + treeDepth_p + treeWidth_p + nodeSum_p + maxChildren_p;
				BigDecimal df = new BigDecimal(true_p);
				double p = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if(p > 1) {
					largeClass.setProjectName(classNode.getProjectName());
					largeClass.setClassName(classNode.getClassName());
					largeClass.setCOL(loc);
					largeClass.setNOF(nof);
					largeClass.setNOM(nom);
					largeClass.setTreeDepth(treeDepth);
					largeClass.setTreeWidth(treeWidth);
					largeClass.setNodeSum(nodeSum);
					largeClass.setMaxChildren(maxChildren);
					largeClass.setPossibility(p);
					largeClassDao.insertLargeClass(largeClass);
					largeClassInstance.add(classNode.getClassName());
				}
			}
		}
		
		return largeClassInstance;
	}
	

	private double countPossibility(int metric, float average) {
		// TODO Auto-generated method stub
		if(average !=0 ) {
			double base = metric / average;
			if(base > 20) {
				return 1.0;
			}else if(base > 18) {
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
		}else {
			return 0;
		}

	}

}
