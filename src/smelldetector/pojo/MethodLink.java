package smelldetector.pojo;

public class MethodLink {
	
	private Integer id;//关联id
	
	private Integer source;//起点
	
	private Integer target;//终点
	
	private String projectName;
	
	private Integer timeCount;
	
	private Integer linkType;//link的类型
	
	private LineStyle lineStyle;
	
	private Integer mcc; //方法调用链长度

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getTimeCount() {
		return timeCount;
	}

	public void setTimeCount(Integer timeCount) {
		this.timeCount = timeCount;
	}
		
	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}
		
	public LineStyle getLineStyle() {
		return lineStyle;
	}

	public void setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
	}
	
	public Integer getMCC() {
		return mcc;
	}
	
	public void setMCC(Integer mcc) {
		this.mcc = mcc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((timeCount == null) ? 0 : timeCount.hashCode());
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
		MethodLink other = (MethodLink) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (timeCount == null) {
			if (other.timeCount != null)
				return false;
		} else if (!timeCount.equals(other.timeCount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MethodLink [id=" + id + ", source=" + source + ", target=" + target + ", projectName=" + projectName
				+ ", timeCount=" + timeCount + "]";
	}

}
