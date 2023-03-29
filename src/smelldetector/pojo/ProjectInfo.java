package smelldetector.pojo;

import java.util.Date;

public class ProjectInfo {
	
private Integer id; //唯一id
	
	private String projectName; //项目名称
	
	private Integer classSum;	//类的数量
	
	private Integer methodSum;	//方法数量
	
	private Integer lineSum;	//代码行数
	
	private Date createTime;	//创建时间
	
	private Integer authorSum; //author number
	
	private Integer methodJavadoc; //有注释的方法数量
	
	private Integer classJavadoc;//有注释的类数量
	
	private Integer classLinkSum;//类关联总数
	
	private String star; //项目star数量
	
	private String fork; //项目fork数量
	
	private String contributor; //项目贡献者数量
	
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
	
	public Integer getClassSum() {
		return classSum;
	}
	
	public void setClassSum(Integer classSum) {
		this.classSum = classSum;
	}
	
	public Integer getMethodSum() {
		return methodSum;
	}
	
	public void setMethodSum(Integer methodSum) {
		this.methodSum = methodSum;
	}
	
	public Integer getLineSum() {
		return lineSum;
	}
	
	public void setLineSum(Integer lineSum) {
		this.lineSum = lineSum;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getAuthorSum() {
		return authorSum;
	}
	
	public void setAuthorSum(Integer authorSum) {
		this.authorSum = authorSum;
	}
	
	public Integer getMethodJavadoc() {
		return methodJavadoc;
	}
	
	public void setMethodJavadoc(Integer methodJavadoc) {
		this.methodJavadoc = methodJavadoc;
	}
	
	public Integer getClassJavadoc() {
		return classJavadoc;
	}
	
	public void setClassJavadoc(Integer classJavadoc) {
		this.classJavadoc = classJavadoc;
	}
	
	public Integer getClassLinkSum() {
		return classLinkSum;
	}
	
	public void setClassLinkSum(Integer classLinkSum) {
		this.classLinkSum = classLinkSum;
	}
	
	public String getStar() {
		return star;
	}
	
	public void setStar(String star) {
		this.star = star;
	}
	
	public String getFork() {
		return fork;
	}
	
	public void setFork(String fork) {
		this.fork = fork;
	}
	
	public String getContributor() {
		return contributor;
	}
	
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classSum == null) ? 0 : classSum.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((methodSum == null) ? 0 : methodSum.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((lineSum == null) ? 0 : lineSum.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((methodJavadoc == null) ? 0 : methodJavadoc.hashCode());
		result = prime * result + ((classJavadoc == null) ? 0 : classJavadoc.hashCode());
		result = prime * result + ((classLinkSum == null) ? 0 : classLinkSum.hashCode());
		result = prime * result + ((star == null) ? 0 : star.hashCode());
		result = prime * result + ((fork == null) ? 0 : fork.hashCode());
		result = prime * result + ((contributor == null) ? 0 : contributor.hashCode());
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
		ProjectInfo other = (ProjectInfo) obj;
		if (classSum == null) {
			if (other.classSum != null)
				return false;
		} else if (!classSum.equals(other.classSum))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (methodSum == null) {
			if (other.methodSum != null)
				return false;
		} else if (!methodSum.equals(other.methodSum))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (lineSum == null) {
			if (other.lineSum != null)
				return false;
		} else if (!lineSum.equals(other.lineSum))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (methodJavadoc == null) {
			if (other.methodJavadoc != null)
				return false;
		} else if (!methodJavadoc.equals(other.methodJavadoc))
			return false;
		if (classJavadoc == null) {
			if (other.classJavadoc != null)
				return false;
		} else if (!classJavadoc.equals(other.classJavadoc))
			return false;
		if (classLinkSum == null) {
			if (other.classLinkSum != null)
				return false;
		} else if (!classLinkSum.equals(other.classLinkSum))
			return false;
		if (star == null) {
			if (other.star != null)
				return false;
		} else if (!star.equals(other.star))
			return false;
		if (fork == null) {
			if (other.fork != null)
				return false;
		} else if (!fork.equals(other.fork))
			return false;
		if (contributor == null) {
			if (other.contributor != null)
				return false;
		} else if (!contributor.equals(other.contributor))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ProjectInfo [id=" + id + ", classSum=" + classSum + ", projectName=" + projectName + ", methodSum="
				+ methodSum + ", lineSum=" + lineSum + ", createTime=" + createTime + ", methodJavadoc=" + methodJavadoc+
				", classJavadoc=" + classJavadoc + ", star=" + star + ", fork=" + fork 
				+ ", contributor=" + contributor + ", classLinkSum=" + classLinkSum+ "]";
		}

}
