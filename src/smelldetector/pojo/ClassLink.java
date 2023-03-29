package smelldetector.pojo;

public class ClassLink {
	
private Integer id;
	
	private String linkName;
	
	private Integer source;
	
	private Integer target;
	
	private LineStyle lineStyle;
	
	private Integer linkType;
	
	private String projectName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return linkName;
	}

	public void setName(String name) {
		this.linkName = name;
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

	public LineStyle getLineStyle() {
		return lineStyle;
	}

	public void setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
		

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lineStyle == null) ? 0 : lineStyle.hashCode());
		result = prime * result + ((linkType == null) ? 0 : linkType.hashCode());
		result = prime * result + ((linkName == null) ? 0 : linkName.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
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
		ClassLink other = (ClassLink) obj;
		if (linkType == null) {
			if (other.linkType != null)
				return false;
		} else if (!linkType.equals(other.linkType))
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
		return true;
	}

	@Override
	public String toString() {
		return "Link [id=" + id + ", linkName=" + linkName + ", source=" + source + ", target=" + target + ", lineStyle="
				+ lineStyle + ", linkType=" + linkType + "]";
	}

}
