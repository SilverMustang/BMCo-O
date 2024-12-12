package smelldetector.pojo;

public class LargeClass {
	
	private Integer id;
	private Integer col;//类代码行数
	private Integer nof;//字段数
	private Integer nom;//方法个数
	private Integer treeDepth;//树深度
	private Integer treeWidth;//树宽度
	private Integer nodeSum;//树节点数
	private Integer maxChildren;//最大度数
	private String projectName;//项目名
	private String className;//类名
	private double possibility;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCOL() {
		return col;
	}

	public void setCOL(Integer col) {
		this.col = col;
	}
	
	public Integer getNOF() {
		return nof;
	}

	public void setNOF(Integer nof) {
		this.nof = nof;
	}
	
	public Integer getNOM() {
		return nom;
	}

	public void setNOM(Integer nom) {
		this.nom = nom;
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
	
	public Double getPossibility() {
		return possibility;
	}

	public void setPossibility(Double possibility) {
		this.possibility = possibility;
	}

}
