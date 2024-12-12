package smelldetector.pojo;

public class MethodNode {
	
private Integer id;//方法节点id
	
	private Integer classNodeId;//所属类节点的id
	
	private String methodName;//方法名称
	
	private String returnType;//方法返回值
	
	private Integer arguementCount;//参数个数
	
	private Integer nos;//语句数
	
	private Integer methodType;//方法属性
	
	private String parameter;//参数列表
	
	private String className;//类名
	
	private String projectName;//项目名
	
	private Integer type;//1代表项目下类的方法;2代表其他类
	
	private Double x;//横坐标
	
	private Double y;//纵坐标
	
	private Double symbolSize;//大小
	
	private ItemStyle itemStyle;//样式
	
	private Integer category;//类别
	
	private Integer recursionCount;//递归调用次数
	
	private Integer callNumber;//调用层级
	
	private String	isJavadoc;//是否有注释
	
	private Integer	methodLine;//方法代码行数
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClassNodeId() {
		return classNodeId;
	}

	public void setClassNodeId(Integer classNodeId) {
		this.classNodeId = classNodeId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Integer getArguementCount() {
		return arguementCount;
	}

	public void setArguementCount(Integer arguementCount) {
		this.arguementCount = arguementCount;
	}
	
	public Integer getNOS() {
		return nos;
	}
	
	public void setNOS(Integer nos) {
		this.nos = nos;
	}

	public Integer getMethodType() {
		return methodType;
	}

	public void setMethodType(Integer methodType) {
		this.methodType = methodType;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}	
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public ItemStyle getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(ItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}
		
	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}
		
	public Integer getRecursionCount() {
		return recursionCount;
	}

	public void setRecursionCount(Integer recursionCount) {
		this.recursionCount = recursionCount;
	}
		
	public Integer getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(Integer callNumber) {
		this.callNumber = callNumber;
	}
	
	public String getIsJavadoc() {
		return isJavadoc;
	}
	
	public void setIsJavadoc(String isJavadoc) {
		this.isJavadoc = isJavadoc;
	}

	public Integer getMethodLine() {
		return methodLine;
	}
	
	public void setMethodLine(Integer methodLine) {
		this.methodLine = methodLine;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguementCount == null) ? 0 : arguementCount.hashCode());
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((classNodeId == null) ? 0 : classNodeId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + ((methodType == null) ? 0 : methodType.hashCode());
		result = prime * result + ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((isJavadoc == null) ? 0 : isJavadoc.hashCode());
		result = prime * result + ((methodLine == null) ? 0 : methodLine.hashCode());
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
		MethodNode other = (MethodNode) obj;
		if (arguementCount == null) {
			if (other.arguementCount != null)
				return false;
		} else if (!arguementCount.equals(other.arguementCount))
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (classNodeId == null) {
			if (other.classNodeId != null)
				return false;
		} else if (!classNodeId.equals(other.classNodeId))
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
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (isJavadoc == null) {
			if (other.isJavadoc != null)
				return false;
		} else if (!isJavadoc.equals(other.isJavadoc))
			return false;
		if (methodLine == null) {
			if (other.methodLine != null)
				return false;
		} else if (!methodLine.equals(other.methodLine))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MethodNode [id=" + id + ", classNodeId=" + classNodeId + ", methodName=" + methodName + ", returnType="
				+ returnType + ", arguementCount=" + arguementCount + ", methodType=" + methodType + ", parameter="
				+ parameter + ", className=" + className + ", projectName=" + projectName + ", type=" + type
				+ ", isJavadoc=" + isJavadoc + ", methodLine=" + methodLine + "]";
	}

}
