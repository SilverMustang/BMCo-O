package smelldetector.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.ChildPropertyDescriptor;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;

import smelldetector.util.TreeTraverseUtil.DataNode;

public class TreeTraverseUtil {
	
	public static class DataNode{
		public ASTNode node; //所代表的的AST节点
		public int label; //编号
		public List<Integer> childrenLables = new ArrayList<>(); //直接的子节点的编号
		public List<ASTNode> childrenNodes = new ArrayList<>(); //直接的子节点
		public boolean isLeaf = false; //是否是叶子节点
		public String nodeType = "unknown";
	}
	
	Map<String, Integer> result = new HashMap<>();
	int depth = -1; //树的深度
	int childSize = 0; //最大子节点数
	int id = 0; //结点编号
	
	public Map<String, Integer> treeTraverse(ASTNode node){
		Map<Integer, DataNode> Nodes = new HashMap<Integer, DataNode>();
		List<List<ASTNode>> res = new ArrayList<>();
		int treeWidth = 0;
		int treeDepth = 0;
		int nodeSum = 0;
		int maxChildren = 0;
		result = getDirectChildren(node, 0, Nodes, maxChildren, 0);
		res = levelOrder(node);
		for (int i = 0; i < res.size(); i++) {
			if(res.get(i).size() > treeWidth) {
				treeWidth = Math.max(treeWidth,res.get(i).size());
			}
		}
		result.put("treeWidth", treeWidth);
		return result;
	}
	
	public Map<String, Integer> getDirectChildren(ASTNode node, int label, Map<Integer, DataNode> Nodes, int childSize, int curDepth) {
		id++;
		//判断是否为叶子节点
		if(isLeaf(node)) {
			depth = Math.max(depth, curDepth);
			result.put("treeDepth", depth);
		}
		DataNode myNode = new DataNode();
		Nodes.put(label, myNode);
		myNode.label = label;
		myNode.node = node;
		myNode.nodeType = node.getClass().toString();
		List listProperty = node.structuralPropertiesForType();
		boolean hasChildren = false;
		for(int i = 0; i < listProperty.size(); i++){
			StructuralPropertyDescriptor propertyDescriptor = (StructuralPropertyDescriptor) listProperty.get(i);
			if(propertyDescriptor instanceof ChildListPropertyDescriptor){//ASTNode列表
				ChildListPropertyDescriptor childListPropertyDescriptor = (ChildListPropertyDescriptor)propertyDescriptor;
				Object children = node.getStructuralProperty(childListPropertyDescriptor);
				List<ASTNode> childrenNodes = (List<ASTNode>)children;
				for(ASTNode childNode: childrenNodes){
					//获取所有节点
					if(childNode == null)
						continue;
					hasChildren = true;
					myNode.childrenNodes.add(childNode);
					myNode.childrenLables.add((++id));
					getDirectChildren(childNode, id, Nodes, childSize, curDepth+1);
				}

			}else if(propertyDescriptor instanceof ChildPropertyDescriptor){//一个ASTNode
				ChildPropertyDescriptor childPropertyDescriptor = (ChildPropertyDescriptor)propertyDescriptor;
				Object child = node.getStructuralProperty(childPropertyDescriptor);
				ASTNode childNode = (ASTNode)child;
				if(childNode == null)
					continue;
				hasChildren = true;
				//获取了这个节点
				myNode.childrenNodes.add(childNode);
				myNode.childrenLables.add((++id));
				getDirectChildren(childNode, id, Nodes, childSize, curDepth+1);
			}	
		}
//		System.out.println(ID);
		childSize = Math.max(childSize, myNode.childrenLables.size());
		id = Math.max(label, id);
		result.put("maxChildren", childSize);
		result.put("nodeSum", id);
		if(hasChildren){
			//进行递归子节点
			myNode.isLeaf = false;
		}
		else{
			//结束，是叶子结点
			myNode.isLeaf = true;
		}
		return result;
	}
	
	public static boolean isLeaf(ASTNode node) {
		boolean hasChildren = false;
		List listProperty = node.structuralPropertiesForType();
		for(int i = 0; i < listProperty.size(); i++) {
			StructuralPropertyDescriptor propertyDescriptor = (StructuralPropertyDescriptor) listProperty.get(i);
			if(propertyDescriptor instanceof ChildListPropertyDescriptor) {
				ChildListPropertyDescriptor childListPropertyDescriptor = (ChildListPropertyDescriptor)propertyDescriptor;
				Object children = node.getStructuralProperty(childListPropertyDescriptor);
				List<ASTNode> childrenNodes = (List<ASTNode>)children;
				for(ASTNode childNode: childrenNodes) {
					if(childNode == null)
						continue;
					hasChildren = true;
				}
			}else if(propertyDescriptor instanceof ChildPropertyDescriptor) {
				ChildPropertyDescriptor childPropertyDescriptor = (ChildPropertyDescriptor)propertyDescriptor;
				Object child = node.getStructuralProperty(childPropertyDescriptor);
				ASTNode childNode = (ASTNode)child;
				if(childNode == null)
					continue;
				hasChildren = true;
			}
		}
		if(hasChildren) {
			return false;
		}else {
			return true;
		}
	}
	
	public List<List<ASTNode>> levelOrder(ASTNode root){
		List<List<ASTNode>> res = new ArrayList<>();
		if (root == null) return res;
		helper(root, 0, res);
		return res;
	}
	
	private void helper(ASTNode root, int depth, List<List<ASTNode>> res) {
		if (root == null) return;
		if (depth + 1 > res.size()) {
			res.add(new ArrayList<>());
		}
		res.get(depth).add(root);
		
		List listProperty = root.structuralPropertiesForType();
		for(int i = 0; i < listProperty.size(); i++) {
			StructuralPropertyDescriptor propertyDescriptor = (StructuralPropertyDescriptor) listProperty.get(i);
			if(propertyDescriptor instanceof ChildListPropertyDescriptor) {
				ChildListPropertyDescriptor childListPropertyDescriptor = (ChildListPropertyDescriptor)propertyDescriptor;
				Object children = root.getStructuralProperty(childListPropertyDescriptor);
				List<ASTNode> childrenNodes = (List<ASTNode>)children;
				for(ASTNode childNode: childrenNodes) {
					if(childNode == null)
						continue;
					helper(childNode, depth + 1, res);
				}
			}else if(propertyDescriptor instanceof ChildPropertyDescriptor) {
				ChildPropertyDescriptor childPropertyDescriptor = (ChildPropertyDescriptor)propertyDescriptor;
				Object child = root.getStructuralProperty(childPropertyDescriptor);
				ASTNode childNode = (ASTNode)child;
				if(childNode == null)
					continue;
				helper(childNode, depth + 1, res);
			}
		}
	}

}
