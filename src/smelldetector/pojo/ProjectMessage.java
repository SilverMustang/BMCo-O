package smelldetector.pojo;

import java.util.Date;

public class ProjectMessage {
	
private String projectName;//项目简单名称
	
	private String uniqueProjectName;//项目唯一标识名
	
	private Date dateCreateTime;//创建时间

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUniqueProjectName() {
		return uniqueProjectName;
	}

	public void setUniqueProjectName(String uniqueProjectName) {
		this.uniqueProjectName = uniqueProjectName;
	}

	public Date getDateCreateTime() {
		return dateCreateTime;
	}

	public void setDateCreateTime(Date dateCreateTime) {
		this.dateCreateTime = dateCreateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreateTime == null) ? 0 : dateCreateTime.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((uniqueProjectName == null) ? 0 : uniqueProjectName.hashCode());
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
		ProjectMessage other = (ProjectMessage) obj;
		if (dateCreateTime == null) {
			if (other.dateCreateTime != null)
				return false;
		} else if (!dateCreateTime.equals(other.dateCreateTime))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (uniqueProjectName == null) {
			if (other.uniqueProjectName != null)
				return false;
		} else if (!uniqueProjectName.equals(other.uniqueProjectName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GetProjectListResponseType [projectName=" + projectName + ", uniqueProjectName=" + uniqueProjectName
				+ ", dateCreateTime=" + dateCreateTime + "]";
	}

}
