package smelldetector.pojo;

public class ComplexClass {
	
	private Integer id;//关联id
	
	private String projectName; //项目名称
	
	private String className; //类名称
	
	private Integer wmc;//Weighted Method Count
	
	private Double possibility;//可能性
	
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
	
	public Integer getWMC() {
		return wmc;
	}
	
	public void setWMC(Integer wmc) {
		this.wmc = wmc;
	}
	
	public Double getPossibility() {
		return possibility;
	}

	public void setPossibility(Double possibility) {
		this.possibility = possibility;
	}


}
