package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import smelldetector.util.TreeTraverseUtil;
import smelldetector.util.TreeTraverseUtil.DataNode;

public class TreeInfoVisit extends ASTVisitor{
	
	@Override
	public boolean visit(TypeDeclaration node) {
		TreeTraverseUtil treeUtil = new TreeTraverseUtil();
		Map<Integer, DataNode> Nodes = new HashMap<Integer, DataNode>();
		Map<String, Integer> result = new HashMap<>();
		List<List<ASTNode>> res = new ArrayList<>();
		int classTreeWidth = 0;
		int classTreeDepth = 0;
		int classNodeSum = 0;
		int classMaxChildren = 0;
		result = treeUtil.getDirectChildren(node, 0, Nodes, classMaxChildren, 0);
		classNodeSum = result.get("nodeSum");
		classTreeDepth = result.get("treeDepth");
		classMaxChildren = result.get("maxChildren");
		for (int i = 0; i < res.size(); i++) {
			if(res.get(i).size() > classTreeWidth) {
				classTreeWidth = Math.max(classTreeWidth,res.get(i).size());
			}
		}
		return true;
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		TreeTraverseUtil treeUtil = new TreeTraverseUtil();
		Map<Integer, DataNode> Nodes = new HashMap<Integer, DataNode>();
		Map<String, Integer> result = new HashMap<>();
		List<List<ASTNode>> res = new ArrayList<>();
		int methodTreeWidth = 0;
		int methodTreeDepth = 0;
		int methodNodeSum = 0;
		int methodMaxChildren = 0;
		result = treeUtil.getDirectChildren(node, 0, Nodes, methodMaxChildren, 0);
		methodNodeSum = result.get("nodeSum");
		methodTreeDepth = result.get("treeDepth");
		methodMaxChildren = result.get("maxChildren");
		for (int i = 0; i < res.size(); i++) {
			if(res.get(i).size() > methodTreeWidth) {
				methodTreeWidth = Math.max(methodTreeWidth,res.get(i).size());
			}
		}
		return true;
	}

}
