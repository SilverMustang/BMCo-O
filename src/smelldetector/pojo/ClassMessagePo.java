package smelldetector.pojo;

import java.util.List;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassMessagePo {
	
    private Source source;
	
	private Node node;
	
	private ClassNode classNode;
	
	private List<MethodNode> methodNodes;
	
	private ProjectInfo projectInfo;
	
	private ClassTree classTree;
	
	private List<MethodTree> methodTrees;
	
	private TypeDeclaration type;

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public ClassNode getClassNode() {
		return classNode;
	}

	public void setClassNode(ClassNode classNode) {
		this.classNode = classNode;
	}

	public List<MethodNode> getMethodNodes() {
		return methodNodes;
	}

	public void setMethodNodes(List<MethodNode> methodNodes) {
		this.methodNodes = methodNodes;
	}
	
	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}
	
	public ClassTree getClassTree() {
		return classTree;
	}
	
	public void setClassTree(ClassTree classTree) {
		this.classTree = classTree;
	}
	
	public List<MethodTree> getMethodTrees() {
		return methodTrees;
	}
	
	public void setMethodTrees(List<MethodTree> methodTrees) {
		this.methodTrees = methodTrees;
	}
	
	public TypeDeclaration getType() {
		return type;
	}
	
	public void setType(TypeDeclaration type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classNode == null) ? 0 : classNode.hashCode());
		result = prime * result + ((methodNodes == null) ? 0 : methodNodes.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((source == null) ? 0 : projectInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassMessagePo other = (ClassMessagePo) obj;
		if (classNode == null) {
			if (other.classNode != null)
				return false;
		} else if (!classNode.equals(other.classNode))
			return false;
		if (methodNodes == null) {
			if (other.methodNodes != null)
				return false;
		} else if (!methodNodes.equals(other.methodNodes))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (projectInfo == null) {
			if (other.projectInfo != null)
				return false;
		} else if (!projectInfo.equals(other.projectInfo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClassMessagePo [source=" + source + ", node=" + node + ", classNode=" + classNode + ", methodNodes="
				+ methodNodes + ", projectInfo=" + projectInfo + "]";
	}

}
