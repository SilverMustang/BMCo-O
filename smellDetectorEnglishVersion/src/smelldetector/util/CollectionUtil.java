package smelldetector.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import smelldetector.pojo.Node;

public class CollectionUtil {
	
	public static <E> boolean isNotNullOrEmpty(Collection<E> collection){
		if(collection != null && collection.size() > 0) {
			return true;
		}
		return false;		
	}
	
	public static <E> boolean isNullOrEmpty(Collection<E> collection){
		if(collection == null || collection.size() == 0) {
			return true;
		}
		return false;		
	}
	
	//判断集合是否有相同的顶点
	public static boolean isContainNodeInList(List<Node> nodeList,Node node) {
		boolean flag = false;
		if(nodeList != null && nodeList.size() > 0) {
			//方法结点的判定规则
			if(node.getNodeType().equals(2)) {
				for(Node temp : nodeList) {
					if(temp.getNodeType().equals(2)) {
						boolean isEqual = true;
						//1 类名必须相同
						if(node.getName() != null) {
							if(!node.getName().equals(temp.getName())) {
								isEqual = false;
							}
						}else if(temp.getName() != null && node.getName() == null) {							
							isEqual = false;						
						}
						//2 方法名必须相同
						if(node.getMethodName() != null) {
							if(!node.getMethodName().equals(temp.getMethodName())) {
								isEqual = false;
							}
						}else if(node.getMethodName() == null && temp.getMethodName() != null) {
							isEqual = false;
						}
						//3 方法参数必须相同
						if(node.getArguementCount() != null) {
							if(!node.getArguementCount().equals(temp.getArguementCount())) {
								isEqual = false;
							}
						}else if(node.getArguementCount() == null && temp.getArguementCount() != null) {
							isEqual = false;
						}
						//4 返回值必须相同
						if(node.getReturnType() != null) {
							if(!node.getReturnType().equals(temp.getReturnType())) {
								isEqual = false;
							}
						}else if(node.getReturnType() == null && temp.getReturnType() != null) {
							isEqual = false;
						}
						//5 参数列表需要相同
						if(node.getParameter() != null) {
							if(!node.getParameter().equals(temp.getParameter())) {
								isEqual = false;
							}
						}else if(node.getParameter() == null && temp.getParameter() != null) {
							isEqual = false;
						}	
						//6 方法类别相同
						if(node.getMethodType() != null ){
							if(!node.getMethodType().equals(temp.getMethodType())) {
								isEqual = false;
							}
						}else if(node.getMethodType() == null && temp.getMethodType() != null) {
							isEqual = false;
						}
						if(isEqual) {							
							return true;
						}
					}
				}
			}
			//其他类结点的判定规则
			else if(node.getNodeType().equals(3)) {
				for(Node temp : nodeList) {
					if(temp.getNodeType().equals(3)) {
						boolean isEqual = true;
						//1 类名必须相同
						if(node.getName() != null) {
							if(!node.getName().equals(temp.getName())) {
								isEqual = false;
							}
						}else if(node.getName() == null && temp.getName() != null) {
							isEqual = false;
						}
						
						if(isEqual) {
							return true;
						}
					}
				}
			}
			else if(node.getNodeType().equals(4)) {
				for(Node temp : nodeList) {
					if(temp.getNodeType().equals(4) || temp.getNodeType().equals(2)) {
						boolean isEqual = true;
						//1 类名必须相同
						if(node.getName() != null) {
							if(!node.getName().equals(temp.getName())) {
								isEqual = false;
							}
						}else if(node.getName() == null && temp.getName() != null) {
							isEqual = false;
						}
						//2 方法名必须相同
						if(node.getMethodName() != null) {
							if(!node.getMethodName().equals(temp.getMethodName())) {
								isEqual = false;
							}
						}else if(node.getMethodName() == null && temp.getMethodName() != null) {
							isEqual = false;
						}
						//3 方法参数必须相同
						if(node.getArguementCount() != null) {
							if(!node.getArguementCount().equals(temp.getArguementCount())) {
								isEqual = false;
							}
						}else if(node.getArguementCount() == null && temp.getArguementCount() != null) {
							isEqual = false;
						}
						if(isEqual) {
							return true;
						}
					}
				}
			}
			
		}
		return flag;
	}
	
	//判断集合是否有相同的顶点
	public static Node getNodeInList(List<Node> nodeList,Node node) {
		Node flag = null;
		if(nodeList != null && nodeList.size() > 0) {
			//方法结点的判定规则
			if(node.getNodeType().equals(2)) {
				for(Node temp : nodeList) {
					if(temp.getNodeType().equals(2)) {
						boolean isEqual = true;
						//1 类名必须相同
						if(node.getName() != null) {
							if(!node.getName().equals(temp.getName())) {
								isEqual = false;
							}
						}else if(temp.getName() != null && node.getName() == null) {							
							isEqual = false;						
						}
						//2 方法名必须相同
						if(node.getMethodName() != null) {
							if(!node.getMethodName().equals(temp.getMethodName())) {
								isEqual = false;
							}
						}else if(node.getMethodName() == null && temp.getMethodName() != null) {
							isEqual = false;
						}
						//3 方法参数必须相同
						if(node.getArguementCount() != null) {
							if(!node.getArguementCount().equals(temp.getArguementCount())) {
								isEqual = false;
							}
						}else if(node.getArguementCount() == null && temp.getArguementCount() != null) {
							isEqual = false;
						}
						//4 返回值必须相同
						if(node.getReturnType() != null) {
							if(!node.getReturnType().equals(temp.getReturnType())) {
								isEqual = false;
							}
						}else if(node.getReturnType() == null && temp.getReturnType() != null) {
							isEqual = false;
						}
						//5 参数列表需要相同
						if(node.getParameter() != null) {
							if(!node.getParameter().equals(temp.getParameter())) {
								isEqual = false;
							}
						}else if(node.getParameter() == null && temp.getParameter() != null) {
							isEqual = false;
						}	
						//6 方法类别相同
						if(node.getMethodType() != null ){
							if(!node.getMethodType().equals(temp.getMethodType())) {
								isEqual = false;
							}
						}else if(node.getMethodType() == null && temp.getMethodType() != null) {
							isEqual = false;
						}
						if(isEqual) {							
							return temp;
						}
					}
				}
			}
			//其他类结点的判定规则
			else if(node.getNodeType().equals(3)) {
				for(Node temp : nodeList) {
					if(temp.getNodeType().equals(3)) {
						boolean isEqual = true;
						//1 类名必须相同
						if(node.getName() != null) {
							if(!node.getName().equals(temp.getName())) {
								isEqual = false;
							}
						}else if(node.getName() == null && temp.getName() != null) {
							isEqual = false;
						}
						
						if(isEqual) {
							return temp;
						}
					}
				}
			}
			else if(node.getNodeType().equals(4)) {
				for(Node temp : nodeList) {
					if(temp.getNodeType().equals(4) || temp.getNodeType().equals(2)) {
						boolean isEqual = true;
						//1 类名必须相同
						if(node.getName() != null) {
							if(!node.getName().equals(temp.getName())) {
								isEqual = false;
							}
						}else if(node.getName() == null && temp.getName() != null) {
							isEqual = false;
						}
						//2 方法名必须相同
						if(node.getMethodName() != null) {
							if(!node.getMethodName().equals(temp.getMethodName())) {
								isEqual = false;
							}
						}else if(node.getMethodName() == null && temp.getMethodName() != null) {
							isEqual = false;
						}
						//3 方法参数必须相同
						if(node.getArguementCount() != null) {
							if(!node.getArguementCount().equals(temp.getArguementCount())) {
								isEqual = false;
							}
						}else if(node.getArguementCount() == null && temp.getArguementCount() != null) {
							isEqual = false;
						}
						if(isEqual) {
							return temp;
						}
					}
				}
			}
			
		}
		return flag;
	}
	
	//比较两个List是否一样
	public static boolean compareTwoList(List<Object> mapValues,List<Object> compareMapValues) {
		Iterator<Object> tempValueIterator = compareMapValues.iterator();
		Boolean flag = true;
		while(tempValueIterator.hasNext()) {
			Object value = tempValueIterator.next();
			if(value instanceof String) {
				if(value.equals("Number") || value.equals("default") || value.equals("Method") || value.equals("Boolean")) {
					continue;
				}
				if(mapValues.contains(value)) {
					mapValues.remove(value);
					tempValueIterator.remove();
				}else {
					flag = false;
					break;
				}
			}					
		}		
		return flag;		
	}
}
