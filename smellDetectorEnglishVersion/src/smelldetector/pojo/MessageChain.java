package smelldetector.pojo;

public class MessageChain {
	
	private Integer id;//关联id
	
	private String projectName; //项目名称
	
	private String className; //类名称
	
	private String methodName; //方法名称
	
	private Integer mcc;
	
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
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public Integer getMCC() {
		return mcc;
	}
	
	public void setMCC(Integer mcc) {
		this.mcc = mcc;
	}
	
	public Double getPossibility() {
		return possibility;
	}

	public void setPossibility(Double possibility) {
		this.possibility = possibility;
	}

}
