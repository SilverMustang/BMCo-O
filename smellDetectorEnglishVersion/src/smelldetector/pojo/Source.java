package smelldetector.pojo;

import java.util.Date;

public class Source {
	
	private static final long serialVersionUID = 1L;
	//属性名和数据可表的字段对应
	private int id;
	private String names;//包名加类名
	private String methods;//方法详细信息
	private String fields;//参数信息
	private Integer fieldSum;//字段个数
	private String methodNames;//方法名称
	private String importClassMap;//导入的包名
	private String superClassName;//父类名
	private String interfaceName;//接口名称
	private String projectName;//项目名称
	private Date dateCreateTime;//创建时间
	private Date dateChangeLastTime;//最近修改时间
	private Integer methodSum;//方法总数
	private Integer lineSum;
	private Integer NMNOPARAM;//类中无参数方法的个数
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMethods() {
		return methods;
	}
	public void setMethods(String methods) {
		this.methods = methods;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public Integer getFieldSum() {
		return fieldSum;
	}
	public void setFieldSum(Integer fieldSum) {
		this.fieldSum = fieldSum;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getMethodNames() {
		return methodNames;
	}
	public void setMethodNames(String methodNames) {
		this.methodNames = methodNames;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getDateCreateTime() {
		return dateCreateTime;
	}
	public void setDateCreateTime(Date dateCreateTime) {
		this.dateCreateTime = dateCreateTime;
	}
	public Date getDateChangeLastTime() {
		return dateChangeLastTime;
	}
	public void setDateChangeLastTime(Date dateChangeLastTime) {
		this.dateChangeLastTime = dateChangeLastTime;
	}
	public Integer getMethodSum() {
		return methodSum;
	}
	public void setMethodSum(Integer methodSum) {
		this.methodSum = methodSum;
	}
	public String getSuperClassName() {
		return superClassName;
	}
	public void setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
	}
	public String getImportClassMap() {
		return importClassMap;
	}
	public void setImportClassMap(String importClassMap) {
		this.importClassMap = importClassMap;
	}
	
	public Integer getLineSum() {
		return lineSum;
	}
	
	public void setLineSum(Integer lineSum) {
		this.lineSum = lineSum;
	}


}
