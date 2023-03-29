package smelldetector.pojo;

public class MethodTree {
	
	private Integer id; //唯一ID
	
	private String projectName; //项目名称
	
	private String className; //类名称
	
	private String methodName; //方法名称
	
	private Integer treeDepth; //树的深度
	
	private Integer treeWidth; //树的宽度
	
	private Integer nodeSum; //树节点总数
	
	private Integer maxChildren; //树的度数
	
	private Integer nop;//参数个数
	
	private Integer nos;//语句数
	
	private Integer	mloc;//方法代码行数
	
	private Integer isLM;//是否为LongMethod
	
	private Integer isMC; //是否为消息链
	
	private Integer nogv;//使用的全局变量个数
	
	private Integer mcn;//McCabe’s Cyclomatic Number
	
	private Integer atfd;//Access to Foreign Data
	
	private Integer isFE;//是否为Feature Envy
	
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
	
	public Integer getNOS() {
		return nos;
	}
	
	public void setNOS(Integer nos) {
		this.nos = nos;
	}
	
	public Integer getNOP() {
		return nop;
	}
	
	public void setNOP(Integer nop) {
		this.nop = nop;
	}
	
	public Integer getMLOC() {
		return mloc;
	}
	
	public void setMLOC(Integer mloc) {
		this.mloc = mloc;
	}
	
	public Integer getIsLM() {
		return isLM;
	}
	
	public void setIsLM(Integer isLM) {
		this.isLM = isLM;
	}
	
	public Integer getIsMC() {
		return isMC;
	}
	
	public void setIsMC(Integer isMC) {
		this.isMC = isMC;
	}
	
	public Integer getNOGV() {
		return nogv;
	}
	
	public void setNOGV(Integer nogv) {
		this.nogv = nogv;
	}
	
	public Integer getMCN() {
		return mcn;
	}
	
	public void setMCN(Integer mcn) {
		this.mcn = mcn;
	}
	
	public Integer getATFD() {
		return atfd;
	}
	
	public void setATFD(Integer atfd) {
		this.atfd = atfd;
	}
	
	public Integer getIsFE() {
		return isFE;
	}
	
	public void setIsFE(Integer isFE) {
		this.isFE = isFE;
	}
}
