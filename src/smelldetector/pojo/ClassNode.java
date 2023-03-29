package smelldetector.pojo;

import org.eclipse.jdt.core.dom.Javadoc;

public class ClassNode {
	
    private Integer id;//类节点Id
	
	private String className;//类名
	
	private String projectName;//唯一项目名
	
	private String packageName;//包名
	
	private Integer type;//类节点类别
	
	private String author;
	
	private String since;
	
	private String version;
	
	private String isJavadoc;//是否有文档注释
	
	private Javadoc javadoc;//注释内容
	
	private Integer isLC;//是否为Large Class
	
	private Integer loc;//类内代码行数
	
	private Integer NMNOPARAM;//类内无参数方法个数
	
	private Integer isSC; //是否为SpaghettiCode
	
	private Integer wmc;//Weighted Method Count
	
	private Integer isCC; //是否为ComplexClass
	
	private Integer norm;//Number of Override Method
	
	private Integer isRB;//是否为Refused Bequest
	
	private Integer superType;//是否有父类
	
	private Integer nprom;//Number of Protected Member
	
	private float bur;//Base Class Usage Ratio
	
	private Integer nom;
	
	private Integer nof;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getSince() {
		return since;
	}
	
	public void setSince(String since) {
		this.since = since;
	}

	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getIsJavadoc() {
		return isJavadoc;
	}
	
	public void setIsJavadoc(String isJavadoc) {
		this.isJavadoc = isJavadoc;
	}
	
	public Javadoc getJavadoc() {
		return javadoc;
	}
	
	public void setJavadoc(Javadoc javadoc) {
		this.javadoc = javadoc;
	}
	
	public Integer getIsLC() {
		return isLC;
	}
	
	public void setIsLC(Integer isLC) {
		this.isLC = isLC;
	}
	
	public Integer getLOC() {
		return loc;
	}
	
	public void setLOC(Integer loc) {
		this.loc = loc;
	}
	
	public Integer getNMNOPARAM() {
		return NMNOPARAM;
	}
	
	public void setNMNOPARAM(Integer NMNOPARAM) {
		this.NMNOPARAM = NMNOPARAM;
	}
	
	public Integer getIsSC() {
		return isSC;
	}
	
	public void setIsSC(Integer isSC) {
		this.isSC = isSC;
	}
	
	public Integer getWMC() {
		return wmc;
	}
	
	public void setWMC(Integer wmc) {
		this.wmc = wmc;
	}
	
	public Integer getIsCC() {
		return isCC;
	}
	
	public void setIsCC(Integer isCC) {
		this.isCC = isCC;
	}
	
	public Integer getNORM() {
		return norm;
	}
	
	public void setNORM(Integer norm) {
		this.norm = norm;
	}
	
	public Integer getIsRB() {
		return isRB;
	}
	
	public void setIsRB(Integer isRB) {
		this.isRB = isRB;
	}
	
	public Integer getSuperTyper() {
		return superType;
	}
	
	public void setSuperType(Integer superType) {
		this.superType = superType;
	}
	
	public Integer getNPROM() {
		return nprom;
	}
	
	public void setNPROM(Integer nprom) {
		this.nprom = nprom;
	}
	
	public float getBUR() {
		return bur;
	}
	
	public void setBUR(float bur) {
		this.bur = bur;
	}
	
	public Integer getNOM() {
		return nom;
	}
	
	public void setNOM(Integer nom) {
		this.nom = nom;
	}
	
	public Integer getNOF() {
		return nof;
	}
	
	public void setNOF(Integer nof) {
		this.nof = nof;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((since == null) ? 0 : since.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result + ((isJavadoc == null) ? 0 : isJavadoc.hashCode());
		result = prime * result + ((javadoc == null) ? 0 : javadoc.hashCode());
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
		ClassNode other = (ClassNode) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
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
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (since == null) {
			if (other.since != null)
				return false;
		} else if (!since.equals(other.since))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		if (isJavadoc == null) {
			if (other.isJavadoc != null)
				return false;
		} else if (!isJavadoc.equals(other.isJavadoc))
			return false;
		if (javadoc == null) {
			if (other.javadoc != null)
				return false;
		} else if (!javadoc.equals(other.javadoc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClassNode [id=" + id + ", className=" + className + ", projectName=" + projectName + ", packageName="
				+ packageName + ", type=" + type + ", author=" + author + ", since=" + since + ", version=" 
				+ version + ", isJavadoc=" + isJavadoc +  ", javadoc=" + javadoc + "]";
	}

}
