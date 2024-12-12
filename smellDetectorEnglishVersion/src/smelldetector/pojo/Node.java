package smelldetector.pojo;

public class Node {
	
private Integer id;//编号
	
	private Integer logicId;//逻辑Id
	
	private String name;//顶点名称
	
	private Double x;//横坐标
	
	private Double y;//纵坐标
	
	private Double symbolSize;//大小
	
	private Integer category;//类别
	
	private String packageName;//包名
	
	private String projectName;//项目名
	
	private Integer nodeType;//顶点类型:1代表类节点 2代表方法节点....
	
	private String methodName;//方法名称，方法节点才需要填充
	
	private Integer methodType;//1代表private,2代表public;0代表不是方法节点
	
	private Integer arguementCount;//参数个数,方法节点才需要填充
	
	private String parameter;//参数列表,方法节点才需要填充
	
	private String returnType;//返回值,方法节点才需要填充
	
	private Integer timeSum;//代表该节点被调用的次数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getSymbolSize() {
		return symbolSize;
	}

	public void setSymbolSize(Double symbolSize) {
		this.symbolSize = symbolSize;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Integer getMethodType() {
		return methodType;
	}

	public void setMethodType(Integer methodType) {
		this.methodType = methodType;
	}

	public Integer getArguementCount() {
		return arguementCount;
	}

	public void setArguementCount(Integer arguementCount) {
		this.arguementCount = arguementCount;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
		
	public Integer getLogicId() {
		return logicId;
	}

	public void setLogicId(Integer logicId) {
		this.logicId = logicId;
	}
		
	public Integer getTimeSum() {
		return timeSum;
	}

	public void setTimeSum(Integer timeSum) {
		this.timeSum = timeSum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguementCount == null) ? 0 : arguementCount.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + ((methodType == null) ? 0 : methodType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nodeType == null) ? 0 : nodeType.hashCode());
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
		result = prime * result + ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
		result = prime * result + ((symbolSize == null) ? 0 : symbolSize.hashCode());
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		Node other = (Node) obj;
		if (arguementCount == null) {
			if (other.arguementCount != null)
				return false;
		} else if (!arguementCount.equals(other.arguementCount))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		if (methodType == null) {
			if (other.methodType != null)
				return false;
		} else if (!methodType.equals(other.methodType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nodeType == null) {
			if (other.nodeType != null)
				return false;
		}
		else if (!nodeType.equals(other.nodeType))
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		if (symbolSize == null) {
			if (other.symbolSize != null)
				return false;
		} else if (!symbolSize.equals(other.symbolSize))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", name=" + name + ", x=" + x + ", y=" + y + ", symbolSize=" + symbolSize
				+ ", category=" + category + ", packageName=" + packageName
				+ ", projectName=" + projectName + ", nodeType=" + nodeType + ", methodName=" + methodName
				+ ", methodType=" + methodType + ", arguementCount=" + arguementCount + ", parameter=" + parameter
				+ ", returnType=" + returnType + "]";
	}
}
