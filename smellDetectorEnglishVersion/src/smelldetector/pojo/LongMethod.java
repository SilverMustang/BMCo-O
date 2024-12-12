package smelldetector.pojo;

public class LongMethod {
	
	private Integer id;//id
	private Integer mloc;//方法代码行数
	private Integer nop;//参数数量
	private Integer nos;//语句数量
	private Integer treeDepth;//数深度
	private Integer treeWidth;//数宽度
	private Integer nodeSum;//节点总数
	private Integer maxChildren;//树度数
	private String projectName;//项目名称
	private String className;//所属类名称
	private String methodName;//方法名称
	private Double possibility;//可能性
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getMLOC() {
		return mloc;
	}
	
	public void setMLOC(Integer mloc) {
		this.mloc = mloc;
	}
	
	public Integer getNOP() {
		return nop;
	}
	
	public void setNOP(Integer nop) {
		this.nop = nop;
	}
	
	public Integer getNOS() {
		return nos;
	}
	
	public void setNOS(Integer nos) {
		this.nos = nos;
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
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
