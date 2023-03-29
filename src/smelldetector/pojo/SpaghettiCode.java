package smelldetector.pojo;

public class SpaghettiCode {
	
	private Integer id; //唯一id
	
	private String projectName; //项目名
	
	private String className; //类名
	
	private Integer NMNOPARAM; //类内无参数方法个数
	
	private Integer LOC; //类代码行数
	
	private Integer NOGV; //方法内全局变量个数
	
	private double possibility; //可能性
	
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
	
	public Integer getNMNOPARAM() {
		return NMNOPARAM;
	}
	
	public void setNMNOPARAM(Integer NMNOPARAM) {
		this.NMNOPARAM= NMNOPARAM;
	}
	
	public Integer getLOC() {
		return LOC;
	}
	
	public void setLOC(Integer LOC) {
		this.LOC= LOC;
	}
	
	public Integer getNOGV() {
		return NOGV;
	}
	
	public void setNOGV(Integer NOGV) {
		this.NOGV= NOGV;
	}
	
	public Double getPossibility() {
		return possibility;
	}

	public void setPossibility(Double possibility) {
		this.possibility = possibility;
	}

}
