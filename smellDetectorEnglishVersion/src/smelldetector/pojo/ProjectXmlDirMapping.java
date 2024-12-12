package smelldetector.pojo;

import java.util.Date;

public class ProjectXmlDirMapping {
	
private Integer id;//编号
	
	private String projectName;//项目唯一标识名称
	
	private String xmlDir;//xml文件路径
	
	private String className;//类名
	
	private Date dateCreateTime;//上传时间
	
	private Integer type;//主要用于标记是在本地上传还是服务器

	private Integer fileType;//主要用于标记是在文件类型 1 为 xml 2 为zip
	
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

	public String getXmlDir() {
		return xmlDir;
	}

	public void setXmlDir(String xmlDir) {
		this.xmlDir = xmlDir;
	}

	public Date getDateCreateTime() {
		return dateCreateTime;
	}

	public void setDateCreateTime(Date dateCreateTime) {
		this.dateCreateTime = dateCreateTime;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((dateCreateTime == null) ? 0 : dateCreateTime.hashCode());
		result = prime * result + ((fileType == null) ? 0 : fileType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((xmlDir == null) ? 0 : xmlDir.hashCode());
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
		ProjectXmlDirMapping other = (ProjectXmlDirMapping) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (dateCreateTime == null) {
			if (other.dateCreateTime != null)
				return false;
		} else if (!dateCreateTime.equals(other.dateCreateTime))
			return false;
		if (fileType == null) {
			if (other.fileType != null)
				return false;
		} else if (!fileType.equals(other.fileType))
			return false;
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
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (xmlDir == null) {
			if (other.xmlDir != null)
				return false;
		} else if (!xmlDir.equals(other.xmlDir))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectXmlDirMapping [id=" + id + ", projectName=" + projectName + ", xmlDir=" + xmlDir + ", className="
				+ className + ", dateCreateTime=" + dateCreateTime + ", type=" + type + ", fileType=" + fileType + "]";
	}

}
