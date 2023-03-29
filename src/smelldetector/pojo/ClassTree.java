package smelldetector.pojo;

public class ClassTree {

private Integer id; //唯一ID
	
	private String projectName; //项目名称
	
	private String className; //类名称
	
	private Integer treeDepth; //树的深度
	
	private Integer treeWidth; //树的宽度
	
	private Integer nodeSum; //树节点总数
	
	private Integer maxChildren; //树的度数
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public Integer getTreeDepth() {
		return treeDepth;
	}

	public void setTreeDepth(Integer treeDepth) {
		this.treeDepth = treeDepth;
	}
	
	public Integer getTreeWidth() {
		return treeWidth;
	}

	public void setTreeWidth(Integer treeWidth) {
		this.treeWidth = treeWidth;
	}
	
	public Integer getNodeSum() {
		return nodeSum;
	}

	public void setNodeSum(Integer nodeSum) {
		this.nodeSum = nodeSum;
	}
	
	public Integer getMaxChildren() {
		return maxChildren;
	}

	public void setMaxChildren(Integer maxChildren) {
		this.maxChildren = maxChildren;
	}
}
