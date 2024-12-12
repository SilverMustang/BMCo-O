package smelldetector.pojo;

public class RefusedBequest {
	
	private Integer id;//关联id
	
	private String projectName; //项目名称
	
	private String className; //类名称
	
	private Integer norm;//Number of Override Method
	
	private Double possibility;//可能性
	
	private Integer nprotm;//Number of Protected Members
	
	private float bur;
	
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
	
	public Integer getNORM() {
		return norm;
	}
	
	public void setNORM(Integer norm) {
		this.norm = norm;
	}
	
	public Integer getNPROTM() {
		return nprotm;
	}
	
	public void setNPROTM(Integer nprotm) {
		this.nprotm = nprotm;
	}
	
	public Double getPossibility() {
		return possibility;
	}

	public void setPossibility(Double possibility) {
		this.possibility = possibility;
	}
	
	public float getBUR() {
		return bur;
	}
	
	public void setBUR(float bur) {
		this.bur = bur;
	}

}
