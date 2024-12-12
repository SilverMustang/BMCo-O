package smelldetector.ast.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.fasterxml.jackson.databind.ObjectMapper;

import smelldetector.util.ASTUtil;
import smelldetector.ast.visitor.FieldInfoVisit;
import smelldetector.ast.visitor.HeadInfoVisit;
import smelldetector.ast.visitor.MethodInfoVisit;
import smelldetector.ast.visitor.StatementVisitToXML;
import smelldetector.util.CountLength;
import smelldetector.util.ASTUtil;
import smelldetector.ast.visitor.ExpressVisitToDB;
import smelldetector.ast.visitor.FieldInfoVisitToDB;
import smelldetector.ast.visitor.HeadInfoVisitToDB;
import smelldetector.ast.visitor.MethodInfoVisitToDB;
import smelldetector.ast.visitor.MethodVariableVisit;
import smelldetector.ast.visitor.ProjectInfoVisit;
import smelldetector.ast.visitor.StatementsVisitToDB;
import smelldetector.ast.visitor.TreeInfoVisit;
import smelldetector.ast.visitor.TypeDeclarationVisit;
import smelldetector.ast.visitor.TypeInfoVisitToDB;
import smelldetector.util.CollectionUtil;
import smelldetector.util.FastJsonUtil;
import smelldetector.util.TreeTraverseUtil;
import smelldetector.util.ZipToFile;
import smelldetector.mapper.ClassLinkMapper;
import smelldetector.mapper.ClassNodeMapper;
import smelldetector.mapper.ClassTreeMapper;
import smelldetector.mapper.MethodLinkMapper;
import smelldetector.mapper.MethodNodeMapper;
import smelldetector.mapper.MethodTreeMapper;
import smelldetector.mapper.NodeMapper;
import smelldetector.mapper.ProjectInfoMapper;
import smelldetector.mapper.SourceMapper;
import smelldetector.metrics.AccessToForeignData;
import smelldetector.metrics.BaseClassUsageRatio;
import smelldetector.metrics.McCabe;
import smelldetector.metrics.NumOfSuperClass;
import smelldetector.metrics.NumOverrideMethod;
import smelldetector.metrics.NumOverrideMethods;
import smelldetector.metrics.NumProtMembersInParent;
import smelldetector.pojo.ClassMessagePo;
import smelldetector.pojo.ClassNode;
import smelldetector.pojo.ClassTree;
import smelldetector.pojo.ClassLink;
import smelldetector.pojo.MethodLink;
import smelldetector.pojo.MethodNode;
import smelldetector.pojo.Node;
import smelldetector.pojo.ProjectInfo;
import smelldetector.pojo.Source;
import smelldetector.pojo.MethodTree;
import smelldetector.pojo.ProjectXmlDirMapping;

public class ASTAnalysis {
	
	private static String filePath = "D:\\ZKH\\uploadFiles\\";
	private List<Source> sources = new ArrayList<>();
	private List<Node> nodeList = new ArrayList<>();//保存顶点信息
	private List<ProjectXmlDirMapping> projectXmlDirMappings = new ArrayList<>();//保存xml文件路径映射
	private List<ClassNode> classNodeList = new ArrayList<>();
	private List<MethodNode> methodNodeList = new ArrayList<>();
	private List<ClassTree> classTreeList = new ArrayList<>();
	private List<MethodTree> methodTreeList = new ArrayList<>();
	private List<TypeDeclaration> typeList = new ArrayList<>();
	private String projectName;
	
	public List<Source> getSources() {
		return sources;
	}

	public void setSources(List<Source> sources) {
		this.sources = sources;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
	
	public List<ProjectXmlDirMapping> getProjectXmlDirMappings() {
		return projectXmlDirMappings;
	}

	public void setProjectXmlDirMappings(List<ProjectXmlDirMapping> projectXmlDirMappings) {
		this.projectXmlDirMappings = projectXmlDirMappings;
	}

	
	public List<ClassNode> getClassNodeList() {
		return classNodeList;
	}

	public void setClassNodeList(List<ClassNode> classNodeList) {
		this.classNodeList = classNodeList;
	}

	public List<MethodNode> getMethodNodeList() {
		return methodNodeList;
	}

	public void setMethodNodeList(List<MethodNode> methodNodeList) {
		this.methodNodeList = methodNodeList;
	}
	
	public List<ClassTree> getClassTreeList(){
		return classTreeList;
	}
	
	public void setClassTreeList(List<ClassTree> classTreeList) {
		this.classTreeList = classTreeList;
	}
	
	public List<MethodTree> getMethodTreeList(){
		return methodTreeList;
	}
	
	public void setMethodTreeList(List<MethodTree> methodTreeList) {
		this.methodTreeList = methodTreeList;
	}
	
	public List<TypeDeclaration> getTypeList() {
		return typeList;
	}
	
	public void setTypeList(List<TypeDeclaration> typeList) {
		this.typeList = typeList;
	}
	
	public void ast(String projectName, String filePath) throws Exception 
	{
		
		//System.out.println("A");
		//getXMLInfo("src\\test\\Test.java");
		//xml会生成在工程根目录下
		ASTAnalysis astAnalysis = new ASTAnalysis();
		List<Source> sources = new ArrayList<>();
		List<Node> nodeList = new ArrayList<>();//保存顶点信息
		List<ClassNode> classNodeList = new ArrayList<>();//类节点信息
		List<MethodNode> methodNodeList = new ArrayList<>();
		List<ClassTree> classTreeList = new ArrayList<>();
		List<MethodTree> methodTreeList = new ArrayList<>();
		List<TypeDeclaration> typeList = new ArrayList<>();
		astAnalysis.setSources(sources);
		astAnalysis.setNodeList(nodeList);
		astAnalysis.setClassTreeList(classTreeList);
		astAnalysis.setMethodTreeList(methodTreeList);
		String saveZipPath = filePath;
		String saveProjectPath = saveZipPath.substring(0, saveZipPath.lastIndexOf("."));
//		String fileProjectName = saveZipPath.substring(saveZipPath.lastIndexOf("\\")+1, saveZipPath.indexOf(".zip"));
		astAnalysis.setProjectName(projectName);
		astAnalysis.setTypeList(typeList);
		try {
			ZipToFile.zipToFile(saveZipPath, saveProjectPath);
		}catch (Exception e) {
			e.printStackTrace();
		}
		String rootPath = saveZipPath.substring(0, saveZipPath.lastIndexOf("."));
		File file = new File(rootPath);
		if(file.isDirectory()) {
			//如果输入的是文件夹，递归解析源码成xml文件			
			astAnalysis.scannerFolder(file,rootPath,sources);
			
		}else {
			//如果是文件则单个解析文件
			//getDBInfo(file.getPath());
			//getXMLInfo(file.getPath());
			Source source = new Source();
			
			//ClassMessagePo classMessagePo = new ClassMessagePo();
			//String projectName = "";
			/*
			classMessagePo = getDBInfoToSource(file.getPath(),projectName);
			if(classMessagePo != null) {
				if(classMessagePo.getSource() != null) {
					source = classMessagePo.getSource();
					sources.add(source);
				}
				if(classMessagePo.getNode() != null) {
					nodeList.add(classMessagePo.getNode());
				}
			
			}
			//getBOXmlInfo(file.getPath());
			 * 
			 */
			List<ClassMessagePo> classMessagePos = new ArrayList<>();
			classMessagePos = getDBInfoToSourceList(file.getPath(),projectName);
			if(CollectionUtil.isNotNullOrEmpty(classMessagePos)) {
				for(ClassMessagePo classMessagePo : classMessagePos) {
					if(classMessagePo != null) {
						if(classMessagePo.getSource() != null) {
							source = classMessagePo.getSource();
							sources.add(source);
						}
						if(classMessagePo.getNode() != null) {
							nodeList.add(classMessagePo.getNode());
						}
						if(classMessagePo.getClassNode() != null) {
							classNodeList.add(classMessagePo.getClassNode());
						}
						if(CollectionUtil.isNotNullOrEmpty(classMessagePo.getMethodNodes())) {
							methodNodeList.addAll(classMessagePo.getMethodNodes());
						}
						if(classMessagePo.getClassTree() != null) {
							classTreeList.add(classMessagePo.getClassTree());
						}
						if(CollectionUtil.isNotNullOrEmpty(classMessagePo.getMethodTrees())) {
							methodTreeList.addAll(classMessagePo.getMethodTrees());
						}
						if(classMessagePo.getType() != null) {
							typeList.add(classMessagePo.getType());
						}
					}								
				}
			}
		}
		
		//插入全量信息
		if(CollectionUtil.isNotNullOrEmpty(astAnalysis.getSources())) {
			for(Source source : astAnalysis.getSources()){
				Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
				SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
				SqlSession sqlSession = factory.openSession();
				SourceMapper sourceMapper = sqlSession.getMapper(SourceMapper.class);
				projectName = source.getProjectName();
				source.setProjectName(projectName);
				sourceMapper.insertSource(source);
				sqlSession.commit();
			}
		}
		//插入顶点信息
		if(CollectionUtil.isNotNullOrEmpty(astAnalysis.getNodeList())) {
			for(Node node : astAnalysis.getNodeList()){
				Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
				SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
				SqlSession sqlSession = factory.openSession();
				NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
				projectName = node.getProjectName();
				node.setProjectName(projectName);
				nodeMapper.insertNode(node);
				sqlSession.commit();
			}
		}
		
		//插入类节点
		if(CollectionUtil.isNotNullOrEmpty(astAnalysis.getClassNodeList()) && CollectionUtil.isNotNullOrEmpty(astAnalysis.getTypeList())) {
			List<TagElement> tagElementList = new ArrayList();
			for(ClassNode classNode : astAnalysis.getClassNodeList()){
				String author = "";
				String since = "";
				String version = "";
				Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
				SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
				SqlSession sqlSession = factory.openSession();
				ClassNodeMapper classNodeMapper = sqlSession.getMapper(ClassNodeMapper.class);
				Javadoc javadoc = classNode.getJavadoc();//获取javadoc注释信息
				if(javadoc != null) {
					tagElementList.addAll(javadoc.tags());
					for(TagElement tag : tagElementList) {
						if(tag.getTagName() != null) {
							switch(tag.getTagName()) {
							case "@author":
								author =  tag.fragments().toString().substring((tag.fragments().toString().indexOf("["))+1, (tag.fragments().toString().indexOf("]")));
								break;
							case "@since":
								since = tag.fragments().toString().substring((tag.fragments().toString().indexOf("["))+1, (tag.fragments().toString().indexOf("]")));
								break;
							case "@version":
								version = tag.fragments().toString().substring((tag.fragments().toString().indexOf("["))+1, (tag.fragments().toString().indexOf("]")));
								break;
							}
						}else {
							author = "anonymous";
							since = "null";
							version = "null";
						}
					}
				}else {
					author = "anonymous";
					since = "null";
					version = "null";
				}
				
				//是否有父类
				int superType = 0;
				int nOvM = 0;//类内重写方法个数
				int protMembers = 0;//Number of Protected Members			
				float bur = 0;//Base Class Usage Ratio				
				for(TypeDeclaration type : astAnalysis.getTypeList()) {
					if(type.resolveBinding().getQualifiedName().equals(classNode.getClassName())) {
						NumOfSuperClass nsc = new NumOfSuperClass(astAnalysis.getTypeList());
						NumOverrideMethods norm = new NumOverrideMethods(astAnalysis.getTypeList());
						NumProtMembersInParent nProt = new NumProtMembersInParent(astAnalysis.getTypeList());
						BaseClassUsageRatio bcur = new BaseClassUsageRatio(astAnalysis.getTypeList(),type);
						superType = nsc.calculate_NSC(type);
						nOvM = norm.calculate_NORM(type);
						protMembers = nProt.calculate_nprom(type);
						if(type.getMethods().length!=0) {
							bur = (float) nOvM/type.getMethods().length;
						}					
					}
				}
				classNode.setAuthor(author.trim());
				classNode.setSince(since);
				classNode.setVersion(version);
				classNode.setProjectName(projectName);
				classNode.setSuperType(superType);
				classNode.setNORM(nOvM);
				classNode.setNPROM(protMembers);
				classNode.setBUR(bur);
				classNodeMapper.insertClassNode(classNode);
				sqlSession.commit();
			}
		}
		//插入方法节点
		if(CollectionUtil.isNotNullOrEmpty(astAnalysis.getMethodNodeList())) {
			Map<String,Integer> classIdMap = new HashMap<>();
			for(MethodNode methodNode : astAnalysis.getMethodNodeList()){
				Integer id = 0;
				Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
				SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
				SqlSession sqlSession = factory.openSession();
				ClassNodeMapper classNodeMapper = sqlSession.getMapper(ClassNodeMapper.class);
				MethodNodeMapper methodNodeMapper = sqlSession.getMapper(MethodNodeMapper.class);
				methodNode.setProjectName(projectName);
				if(classIdMap.containsKey(methodNode.getClassName()+methodNode.getProjectName())) {
					id = classIdMap.get(methodNode.getClassName()+methodNode.getProjectName());
					methodNode.setClassNodeId(id);
					methodNodeMapper.insertMethodNode(methodNode);
				}else{
					for(ClassNode node : astAnalysis.getClassNodeList()) {
						if(node.getClassName().equals(methodNode.getClassName()) && node.getProjectName().equals(methodNode.getProjectName())) {
							id = node.getId() == null ? 0 : node.getId();						
							break;
						}
					}
					if(id == 0) {
						Map<String,Object> queryMap = new HashMap<>();
						queryMap.put("className", methodNode.getClassName());
						queryMap.put("projectName", methodNode.getProjectName());
						id = classNodeMapper.findClassNodeIdByClassName(queryMap);
						
					}
					if(id != 0) {
						methodNode.setClassNodeId(id);
						methodNodeMapper.insertMethodNode(methodNode);
						classIdMap.put(methodNode.getClassName()+methodNode.getProjectName(), id);
					}
					
				}			

				sqlSession.commit();
			}
		}
		
		//获取类与类之间的关系
		Map<String,Object> queryMap = new HashMap<>();
		queryMap.put("projectName", projectName);
		Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession sqlSession = factory.openSession();
		NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
		ClassLinkMapper classLinkMapper = sqlSession.getMapper(ClassLinkMapper.class);
		nodeList = nodeMapper.findNodeList(queryMap);
		List<ClassLink> linkList = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(astAnalysis.getSources())) {
			for(Source source:astAnalysis.getSources()) {
				Integer sourceId = 0;
				//首先获取当前Source属于那个顶点的id
				for(Node node:nodeList) {
					if(node.getName().equals(source.getNames())) {
						sourceId = node.getId();
						break;
					}
				}
				//1继承关系，主要根据source的superClass节点信息
				if(source.getSuperClassName() != null && source.getSuperClassName() != "") {
					for(Node node:nodeList) {
						if(node.getName().equals(source.getSuperClassName())) {
							ClassLink link = new ClassLink();
							link.setSource(sourceId);
							link.setTarget(node.getId());
							link.setLinkType(1);
							link.setName("extend");
							linkList.add(link);
							break;
						}
					}
				}
				//2实现关系，主要根据source的interface节点信息
				//获取接口信息的List
				List<String> interfaceNameList = new ArrayList<>();
				if(source.getInterfaceName() != null && source.getInterfaceName() != "") {
					String interfaceListStr = source.getInterfaceName();
					Map interfaceMap = FastJsonUtil.jsonStrToMap(interfaceListStr);
					if(interfaceMap != null && interfaceMap.size() > 0) {						
						List<Map> interfaceList = new ArrayList<>();
						for(Object key : interfaceMap.keySet()) {
							if(key.equals("interface")) {
								interfaceList = (List<Map>) interfaceMap.get(key);
							}
						}
						if(CollectionUtil.isNotNullOrEmpty(interfaceList)) {
							for(Map map : interfaceList) {
								if(map.containsKey("type")) {
									interfaceNameList.add((String)map.get("type"));
								}
							}
						}
					}
					if(CollectionUtil.isNotNullOrEmpty(interfaceNameList)) {
						/**
						 * 遍历获取实现关系信息
						 */
						for(String interfaceName:interfaceNameList) {
							for(Node node:nodeList) {
								if(node.getName().equals(interfaceName)) {
									ClassLink link = new ClassLink();
									link.setSource(sourceId);
									link.setTarget(node.getId());
									link.setLinkType(2);
									link.setName("implement");
									linkList.add(link);
									break;
								}
							}
						}
					}
				}
				
				//3成员变量关系，依赖关系，主要根据fields节点信息
				List<String> fieldList = new ArrayList<>();
				if(source.getFields() != null && source.getFields() != "") {
					String fieldJsonStr = source.getFields();
					Map fieldMap = FastJsonUtil.jsonStrToMap(fieldJsonStr);
					//拿取public和private成员变量的信息
					List<Map> privateList = new ArrayList<>();
					List<Map> publicList = new ArrayList<>();
					List<Map> protectedList = new ArrayList<>();
					for(Object key : fieldMap.keySet()) {
						if(key.equals("private")) {
							privateList = (List<Map>) fieldMap.get(key);
						}else if(key.equals("public")) {
							publicList = (List<Map>) fieldMap.get(key);
						}else if(key.equals("protected")) {
							protectedList = (List<Map>) fieldMap.get(key);
						}
					}
					fieldList.addAll(getFieldClassList(privateList));
					fieldList.addAll(getFieldClassList(publicList));
					fieldList.addAll(getFieldClassList(protectedList));
					if(CollectionUtil.isNotNullOrEmpty(fieldList)) {
						for(String fieldName:fieldList) {
							for(Node node:nodeList) {
								if(node.getName().equals(fieldName)) {									
									ClassLink link = new ClassLink();
									link.setSource(sourceId);
									link.setTarget(node.getId());
									link.setLinkType(3);
									link.setName("field");
									if(linkList.contains(link)) { //存在则不增加对应的关系
										continue;
									}
									linkList.add(link);
									break;
								}
							}
						}
					}
				}
				//4参数关系，弱关联关系，主要根据methods节点信息
				List<String> methodClassList = new ArrayList<>(); 
				if(source.getMethods() != null && source.getMethods() != "") {
					String methodJsonStr = source.getMethods();
					Map methodMap = FastJsonUtil.jsonStrToMap(methodJsonStr);
					//拿取public和private方法的信息
					List<Map> privateList = new ArrayList<>();
					List<Map> publicList = new ArrayList<>();
					List<Map> protectedList = new ArrayList<>();
					for(Object key : methodMap.keySet()) {
						if(key.equals("private")) {
							privateList = (List<Map>) methodMap.get(key);
						}else if(key.equals("public")) {
							publicList = (List<Map>) methodMap.get(key);
						}else if(key.equals("protected")) {
							protectedList = (List<Map>) methodMap.get(key);
						}
					}
					methodClassList.addAll(getMethodClassList(privateList));
					methodClassList.addAll(getMethodClassList(publicList));
					methodClassList.addAll(getMethodClassList(protectedList));
					if(CollectionUtil.isNotNullOrEmpty(methodClassList)) {
						for(String methodParameters:methodClassList) {
							for(Node node:nodeList) {
								if(node.getName().equals(methodParameters)) {
								    if(sourceId.equals(node.getId())) {								
								    	continue;
								    }
									ClassLink link = new ClassLink();
									link.setSource(sourceId);
									link.setTarget(node.getId());
									link.setLinkType(4);
									link.setName("method");
									if(linkList.contains(link)) { //存在则不增加对应的关系
										continue;
									}
									linkList.add(link);
									break;
								}
							}
						}
					}
				}
			}
			if(CollectionUtil.isNotNullOrEmpty(linkList)) {
				for(ClassLink link:linkList) {

					link.setProjectName(projectName);
					classLinkMapper.insertLink(link);
					sqlSession.commit();
				}
			}
		}
		
		//获取树信息
		if(CollectionUtil.isNotNullOrEmpty(astAnalysis.getClassTreeList())) {
			for(ClassTree classTree : astAnalysis.getClassTreeList()) {
				ClassTreeMapper classTreeMapper = sqlSession.getMapper(ClassTreeMapper.class);
				classTreeMapper.insertClassTree(classTree);
				sqlSession.commit();
			}
		}
		if(CollectionUtil.isNotNullOrEmpty(astAnalysis.getMethodTreeList())) {
			for(MethodTree methodTree : astAnalysis.getMethodTreeList()) {
				MethodTreeMapper methodTreeMapper = sqlSession.getMapper(MethodTreeMapper.class);
				methodTreeMapper.insertMethodTree(methodTree);
				sqlSession.commit();
			}
		}
		
		//获取项目量化信息
		List<String> authorList = new ArrayList<>();
		List<ClassLink> classLinkList = new ArrayList<>();
		ProjectInfo projectInfo = new ProjectInfo();
		queryMap.put("projectName", projectName);
		SourceMapper sourceMapper = sqlSession.getMapper(SourceMapper.class);
		ProjectInfoMapper projectInfoMapper = sqlSession.getMapper(ProjectInfoMapper.class);
		ClassNodeMapper classNodeMapper = sqlSession.getMapper(ClassNodeMapper.class);
		MethodNodeMapper methodNodeMapper = sqlSession.getMapper(MethodNodeMapper.class);
		sources = sourceMapper.findSourceMessageList(queryMap);
		classNodeList = classNodeMapper.findClassNodeList(queryMap);
		methodNodeList = methodNodeMapper.findMethodNodeByProjectName(queryMap);
		classLinkList = classLinkMapper.findLinkList(queryMap);
		if(CollectionUtil.isNotNullOrEmpty(classNodeList)) {
			Integer authorSum = 0;
			Integer classJavadoc = 0;
			for(ClassNode classNode : classNodeList) {
				String authorName = classNode.getAuthor();
				if(classNode.getIsJavadoc().equals("true")) {
					classJavadoc++;
				}
				if(authorName == null || authorName.equals("anonymous")) {
					continue;
				}else if(authorList.contains(authorName)) {
					continue;
				}else {
					authorList.add(authorName);
					authorSum++;
				}
				
			}
			projectInfo.setClassJavadoc(classJavadoc);
			projectInfo.setAuthorSum(authorSum);
		}
		if(CollectionUtil.isNotNullOrEmpty(sources)) {
			Integer classSum = 0;
			Integer methodSum = 0;
			Integer lineSum = 0;
			List<Source> sourceList = new ArrayList<>(sources);
			for(Source source : sourceList) {
				if(source.getNames() != null) {
					classSum += 1;
				}
				if(source.getMethodSum() != null) {
					methodSum += source.getMethodSum();
				}
				if(source.getLineSum() != null ) {
					lineSum += source.getLineSum();
				}
			}
			projectInfo.setProjectName(projectName);
			projectInfo.setClassSum(classSum);
			projectInfo.setMethodSum(methodSum);
			projectInfo.setLineSum(lineSum);
			projectInfo.setCreateTime(new Date());
		}
		if(CollectionUtil.isNotNullOrEmpty(methodNodeList)) {
			Integer methodJavadoc = 0;
			List<MethodNode> methodList = new ArrayList<>(methodNodeList);
			for(MethodNode methodNode : methodList) {
				if(methodNode.getIsJavadoc() != null && methodNode.getIsJavadoc().equals("true")) {
					methodJavadoc++;
				}
			}
			projectInfo.setMethodJavadoc(methodJavadoc);
		}
		if(CollectionUtil.isNotNullOrEmpty(classLinkList)) {
			Integer classLinkSum = classLinkList.size();
			projectInfo.setClassLinkSum(classLinkSum);
		}
		projectInfoMapper.insertProjectInfo(projectInfo);
		sqlSession.commit();
		
		//插入方法关联关系
		queryMap.put("projectName", projectName);
		MethodLinkMapper methodLinkMapper = sqlSession.getMapper(MethodLinkMapper.class);
		sources = sourceMapper.findSourceList(queryMap);
		if(CollectionUtil.isNotNullOrEmpty(sources)) {
			List<Source> sourceList = new ArrayList<>(sources);
			Map<String,Object> queryClassNodeIdsMap = new HashMap<>();
			queryClassNodeIdsMap.put("projectName", projectName);
			List<Integer> classIds = new ArrayList<>();
			classIds = classNodeMapper.findClassNodeIds(queryClassNodeIdsMap);
			for(Source source : sourceList) {
				List<MethodLink> methodLinkList = getMethodLink(source,classIds);
				if(CollectionUtil.isNotNullOrEmpty(methodLinkList)) {
					for(MethodLink methodLink : methodLinkList) {
						methodLinkMapper.insertMethodLink(methodLink);
						sqlSession.commit();
					}
				}
			}
		}
	}
	
	public void scannerFolder(File folder,String rootPath,List<Source> sources) {
		File xmlFile = null;
		
		if(rootPath.contains(":")){
			xmlFile = new File("D:\\ZKH\\JavaProject\\toXml\\"+rootPath.substring(rootPath.lastIndexOf("\\"),rootPath.length()));
		}else{
			xmlFile = new File("D:\\ZKH\\JavaProject\\toXml\\"+rootPath);
		}		
		if(!xmlFile.exists()) {
			xmlFile.mkdirs();
		}
		//System.out.println("A");
		File[] files = folder.listFiles();
		//System.out.println("A");
		//System.out.println(files.length);
		for(int i=0;i<files.length;i++) {
			File file = files[i];
			if(file.isDirectory()) {
				scannerFolder(file, rootPath+"/"+file.getName(),sources);
			}else {
				//是文件
				String absolute = file.getAbsolutePath();
				//System.out.println(absolute);
				if(absolute.endsWith(".java")) {
					absolute = absolute.replace("\\", "/");
					String filePath = rootPath + absolute.substring(absolute.lastIndexOf("/"), absolute.indexOf(".java"));
					filePath = filePath.replace("/", ".");
					try {
						ProjectXmlDirMapping projectXmlDirMapping = getXMLInfo(file.getPath(),projectName);
						//getDBInfo(file.getPath());
						Source source = new Source();
						//ClassMessagePo classMessagePo = new ClassMessagePo();
						System.out.println("file:"+file.getPath());
						System.out.println("filePath:"+filePath);
						//classMessagePo = getDBInfoToSource(file.getPath(),projectName);
						List<ClassMessagePo> classMessagePos = new ArrayList<>();
						classMessagePos = getDBInfoToSourceList(file.getPath(),projectName);
						if(CollectionUtil.isNotNullOrEmpty(classMessagePos)) {
							for(ClassMessagePo classMessagePo : classMessagePos) {
								if(classMessagePo != null) {
									if(classMessagePo.getSource() != null) {
										source = classMessagePo.getSource();
										sources.add(source);
									}
									if(classMessagePo.getNode() != null) {
										nodeList.add(classMessagePo.getNode());
									}
									if(classMessagePo.getClassNode() != null) {
										classNodeList.add(classMessagePo.getClassNode());
									}
									if(CollectionUtil.isNotNullOrEmpty(classMessagePo.getMethodNodes())) {
										methodNodeList.addAll(classMessagePo.getMethodNodes());
									}
									if(classMessagePo.getClassTree() != null) {
										classTreeList.add(classMessagePo.getClassTree());
									}
									if(CollectionUtil.isNotNullOrEmpty(classMessagePo.getMethodTrees())) {
										methodTreeList.addAll(classMessagePo.getMethodTrees());
									}
									if(classMessagePo.getType() != null) {
										typeList.add(classMessagePo.getType());
									}
								}								
							}
						}
						if(projectXmlDirMapping != null) {
							projectXmlDirMappings.add(projectXmlDirMapping);
						}
						
						//System.out.println("A");
						//getBOXmlInfo(file.getPath());
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	public static List<ClassMessagePo> getDBInfoToSourceList(String PATH,String projectName) 
	{	
		//每次文件一开始初始化,调用连初始值
		System.out.println(PATH);
		List<ClassMessagePo> classMessagePos = new ArrayList<>();
		try {	
				Map<String, Object> importMap = new HashMap<>();//只存于当前内存区，便于调用搜索
				List<Object> importList = new ArrayList<>();//最后保存于硬盘介质，如数据库表
				List<TagElement> tagElementList = new ArrayList<>();//注释信息
				String packageName = "";
				String mainClassPackageName = "";
				// 将源文件解析成ast树，并且返回编译单元作为树的根节点
				CompilationUnit comp = ASTUtil.getCompilationUnit(PATH);
				//获取代码行数
				ProjectInfoVisit pi = new ProjectInfoVisit();
				comp.accept(pi);
				Integer lineSum = pi.lineSum;
				Javadoc javadoc = pi.javadoc;
				String isJavadoc = pi.isJavadoc;
				// 获得headNode信息，唯一
				HeadInfoVisitToDB hi = new HeadInfoVisitToDB();
				if(comp == null) {
					return classMessagePos;
				}
				comp.accept(hi);
				if(hi.packageNode != null) {
					packageName = hi.packageNode.getName().toString();
				}else {
					packageName = "src";
				}
				for (ImportDeclaration item : hi.importNodeList)
				{
					String importName = item.getName().toString();
					importList.add(importName);
					if(importName.indexOf(".")!=-1) {
						importMap.put(importName.substring(importName.lastIndexOf(".")+1, importName.length()), importName);
					}
				}
				// 获得所有类的信息，不唯一
				TypeDeclarationVisit td = new TypeDeclarationVisit();
				comp.accept(td);
				List<TypeDeclaration> classNodeList = new ArrayList<>();
				if(td.getClassNode() != null) {
					mainClassPackageName = packageName + "." + ASTUtil.changeCode(td.getClassNode().getName().toString());
				}
				classNodeList.addAll(td.getClassNodeList());
				classNodeList.addAll(td.getOtherNode());
				if(CollectionUtil.isNotNullOrEmpty(classNodeList)) {
					for(TypeDeclaration classNode : classNodeList) {
						TreeTraverseUtil classTreeUtil = new TreeTraverseUtil();
						ProjectInfo projectInfo = new ProjectInfo();
						ClassMessagePo classMessagePo = new ClassMessagePo();
						CountLength cl_1 = new CountLength();
						Source source = new Source();
						Node node = new Node();
						ClassNode classNodeToDB = new ClassNode();
						ClassTree classTree = new ClassTree();
						NumOverrideMethod norm = new NumOverrideMethod();
						List<MethodNode> methodNodes = new ArrayList<>();
						List<MethodTree> methodTrees = new ArrayList<>();
						ExpressVisitToDB.sortNum = 1;
						String packageClassName = "";
						String superClassName = "";//加上父类的包名
						Map<String,Object> superInterfaceAllMap = new HashMap<>();
						List<Object> superInterfaceList = new ArrayList<>();//接口名和类型
						superInterfaceAllMap.put("interface", superInterfaceList);
						Map<String, Object> fieldMap = new HashMap<>();//field节点信息
						Map<String, Object> methodMap = new HashMap<>();//method节点信息
						Map<String, Object>	tagElementMap = new HashMap<>();
						Map<String,Object> methodNameMap = new HashMap<>();//methodName节点信息
						Map<String,Object> importIndex = new HashMap<>();//import节点信息
						Map<String, Integer> classTreeMap = new HashMap<>(); //树结点信息
						Map<String, Integer> methodTreeMap = new HashMap<>(); //树结点信息
						List<String> basicType = new ArrayList<>();//基本类型
						//添加基本的数据类型
						basicType.add("int");
						basicType.add("Integer");
						basicType.add("byte");
						basicType.add("Byte");
						basicType.add("float");
						basicType.add("Float");
						basicType.add("double");
						basicType.add("Double");
						basicType.add("char");
						basicType.add("Character");
						basicType.add("short");
						basicType.add("Short");
						basicType.add("long");
						basicType.add("Long");
						basicType.add("boolean");
						basicType.add("Boolean");
						basicType.add("String");
						basicType.add("Object");
						importIndex.put("import", importList);
						String fileName = "";
						String className = "";
						
						fieldMap.put("public", new ArrayList<Object>());
						fieldMap.put("private", new ArrayList<Object>());
						fieldMap.put("protected", new ArrayList<Object>());
						methodMap.put("public", new ArrayList<Object>());
						methodMap.put("private", new ArrayList<Object>());
						methodMap.put("protected", new ArrayList<Object>());
						TypeInfoVisitToDB ti = new TypeInfoVisitToDB();
						classNode.accept(ti);
						className = ASTUtil.changeCode(ti.name);						
						packageClassName = packageName + "." + className;
						if(!packageClassName.equals(mainClassPackageName)) {
							//System.out.println("A");
							ASTNode parentNode =  classNode.getParent();
							ASTNode parentMethodNode = classNode.getParent();
							while(true) {
								if(parentMethodNode != null && (parentMethodNode instanceof TypeDeclaration || parentMethodNode instanceof CompilationUnit)) {
									break;
								}
								else if(parentMethodNode != null && parentMethodNode instanceof MethodDeclaration) {
									MethodDeclaration parentNodeMethodDeclaration = (MethodDeclaration) parentMethodNode;
									//System.out.println(parentNodeMethodDeclaration.getParent().getClass());
									//System.out.println(parentNodeMethodDeclaration.getParent().getParent().getClass());
									//System.out.println(parentNodeMethodDeclaration.getName().toString());
									String classMethodName = "";
									classMethodName = parentNodeMethodDeclaration.getName().toString()+ "(";
									List<SingleVariableDeclaration> parameters = parentNodeMethodDeclaration.parameters();
									if(CollectionUtil.isNotNullOrEmpty(parameters)) {
										//System.out.println(parentNodeMethodDeclaration.parameters().toString());
										for(SingleVariableDeclaration temp:parameters) {
											classMethodName = classMethodName + temp.getType().toString() + ",";
										}
										classMethodName = classMethodName.substring(0,classMethodName.length()-1);
									}
									className = classMethodName + ")" + "." + className;							
									parentMethodNode = parentMethodNode.getParent();
								}else if(parentMethodNode != null){
									parentMethodNode = parentMethodNode.getParent();
								}else {
									break;
								}
							}							
							while(true) {
								if(parentNode != null && parentNode instanceof TypeDeclaration) {
									TypeDeclaration parentNodeTypeDeclaration = (TypeDeclaration) parentNode;
									//System.out.println(parentNodeTypeDeclaration.getName().toString());									
									className = parentNodeTypeDeclaration.getName().toString()+ "." + className;
									parentNode =  parentNodeTypeDeclaration.getParent();
								}else if(parentNode != null) {
									parentNode = parentNode.getParent();
								}
								else {
									break;
								}	
							}												
						}

						//获得注释信息
						if(classNode.getJavadoc() != null) {
							classNodeToDB.setIsJavadoc("true");
						}else {
							classNodeToDB.setIsJavadoc("false");
						}
						//获得树结构信息
						classTreeMap = classTreeUtil.treeTraverse(classNode);
						//类的代码行数
						cl_1.countLength(classNode.toString(), "\n");						
						int NMNOPARAM = 0;//类内无参数方法个数
						int wmc = 0;//Weighted Method Count
						MethodDeclaration[] methodList = classNode.getMethods();
						FieldDeclaration[] fieldList = classNode.getFields();
						int fieldSum = fieldList.length;
						if(methodList != null) {
							for(int i=0; i<methodList.length; i++) {
								McCabe mcCabe = new McCabe();
								MethodVariableVisit mv = new MethodVariableVisit();
								methodList[i].accept(mv);
								int nogv = mv.NOGV;
								if(methodList[i].parameters().size()==0 && nogv!=0) {
									NMNOPARAM++;
								}
								wmc = wmc + mcCabe.getMcCabe(methodList[i]);
							}
						}
						
						NumProtMembersInParent nProM = new NumProtMembersInParent(classNodeList);
						
//						int overridden = norm.calculate_NORM(classNode);						
						
						packageClassName = packageName + "." + className;
						System.out.println(packageClassName);
						node.setName(packageClassName);
						node.setPackageName(packageName);
						node.setNodeType(1);//表示类顶点
						node.setProjectName(projectName);
						classNodeToDB.setJavadoc(javadoc);
						classNodeToDB.setClassName(packageClassName);
						classNodeToDB.setPackageName(packageName);
						classNodeToDB.setProjectName(projectName);
						classNodeToDB.setType(1);
						classNodeToDB.setLOC(cl_1.getCodeLength());
						classNodeToDB.setNMNOPARAM(NMNOPARAM);
						classNodeToDB.setWMC(wmc);
						classNodeToDB.setNOM(methodList.length);
						classNodeToDB.setNOF(fieldSum);
						classTree.setClassName(packageClassName);
						classTree.setProjectName(projectName);
						classTree.setMaxChildren(classTreeMap.get("maxChildren"));
						classTree.setNodeSum(classTreeMap.get("nodeSum"));
						classTree.setTreeDepth(classTreeMap.get("treeDepth"));
						classTree.setTreeWidth(classTreeMap.get("treeWidth"));

						//获取父类的全类名
						if(ti.superClass != null) {
							if(importMap.containsKey(ASTUtil.changeCode(ti.superClass.toString()))) {
								superClassName = (String) importMap.get(ASTUtil.changeCode(ti.superClass.toString(),false));
							}else {
								superClassName = packageName + "." + ASTUtil.changeCode(ti.superClass.toString(),false);
							}
						}
						//获取接口信息
						if(ti.superInterface != null && ti.superInterface.size() > 0) {
							for(ASTNode item : ti.superInterface) {
								if(importMap.containsKey(ASTUtil.changeCode(item.toString()))) {
									Map<String,Object> superInterfaceMap = new HashMap<>();
									superInterfaceMap.put("name", ASTUtil.changeCode(item.toString()));
									superInterfaceMap.put("type", importMap.get(ASTUtil.changeCode(item.toString(),false)));
									superInterfaceList.add(superInterfaceMap);
								}else {
									Map<String,Object> superInterfaceMap = new HashMap<>();
									superInterfaceMap.put("name", ASTUtil.changeCode(item.toString()));
									superInterfaceMap.put("type", packageName+"."+ASTUtil.changeCode(item.toString(),false));
									superInterfaceList.add(superInterfaceMap);
								}
							}
						}
						ti.setInnerClass(td.getOtherNode());
						// Field
						for (FieldDeclaration item : ti.FieldList)
						{
							FieldInfoVisitToDB fi = new FieldInfoVisitToDB();
							item.accept(fi);
							for (Modifier item2 : fi.modifiers)
							{
								if("public".equals(item2.getKeyword()+"")) {
									List<Object> publicFields = (List<Object>) fieldMap.get("public");
									for(String key:fi.fieldInfoMap.keySet()) {	
										if(importMap.containsKey(fi.fieldInfoMap.get(key))) {
											fi.fieldInfoMap.put(key, importMap.get(fi.fieldInfoMap.get(key)));
										}else {
											if(basicType.contains(fi.fieldInfoMap.get(key))) {
												fi.fieldInfoMap.put(key,fi.fieldInfoMap.get(key));
											}else {
												fi.fieldInfoMap.put(key, packageName+"."+fi.fieldInfoMap.get(key));
											}	
										}
									}
									publicFields.add(fi.fieldInfoMap);
								}
								else if("private".equals(item2.getKeyword()+"")) {
									List<Object> privateFileds = (List<Object>) fieldMap.get("private");
									for(String key:fi.fieldInfoMap.keySet()) {	
										if(importMap.containsKey(fi.fieldInfoMap.get(key))) {
											fi.fieldInfoMap.put(key, importMap.get(fi.fieldInfoMap.get(key)));
										}else {
											if(basicType.contains(fi.fieldInfoMap.get(key))) {
												fi.fieldInfoMap.put(key,fi.fieldInfoMap.get(key));
											}else {
												fi.fieldInfoMap.put(key, packageName+"."+fi.fieldInfoMap.get(key));
											}
										}
									}
									privateFileds.add(fi.fieldInfoMap);
								}
								else if("protected".equals(item2.getKeyword()+"")) {
									List<Object> proetectedFileds = (List<Object>) fieldMap.get("protected");
									for(String key:fi.fieldInfoMap.keySet()) {	
										if(importMap.containsKey(fi.fieldInfoMap.get(key))) {
											fi.fieldInfoMap.put(key, importMap.get(fi.fieldInfoMap.get(key)));
										}else {
											if(basicType.contains(fi.fieldInfoMap.get(key))) {
												fi.fieldInfoMap.put(key,fi.fieldInfoMap.get(key));
											}else {
												fi.fieldInfoMap.put(key, packageName+"."+fi.fieldInfoMap.get(key));
											}									}
									}
									proetectedFileds.add(fi.fieldInfoMap);
								}
								
							}
							for (VariableDeclarationFragment item2 : fi.fragemtns)
							{
								String str = null;
								if (item2.getInitializer() != null)
								{
									str = item2.getInitializer().toString();
								}
							}
						}
				
						List<String> methodNames = new ArrayList<>();
						if(ti.methodList != null) {			
							for (MethodDeclaration item : ti.methodList) {			
								methodNames.add(item.getName().toString());
							}
						}
						methodNameMap.put("methodNames", methodNames);
						/**
						 * 不保存get,set方法
						 * 
						 */
						List<String> noNeedToSaveMethodName = new ArrayList<>();//不需要保存的方法名
						//获取所有成员变量名
						if(fieldMap != null && fieldMap.size() > 0) {
							//私有成员变量
							List<Object> privateField = (List<Object>) fieldMap.get("private");
							if(privateField != null && privateField.size() > 0) {
								for(Object item : privateField) {
									if(item instanceof Map) {
										Map<String,Object> itemMap = (Map) item;
										if(itemMap != null && itemMap.size() > 0) {
											Set<String> keySet = itemMap.keySet();
											for(String key : keySet) {
												noNeedToSaveMethodName.add(("get"+key).toLowerCase());
												noNeedToSaveMethodName.add(("set"+key).toLowerCase());
											}
										}
									}
								}
							}
							//公有成员变量
							List<Object> publicField = (List<Object>) fieldMap.get("public");
							if(publicField != null && publicField.size() > 0) {
								for(Object item : publicField) {
									if(item instanceof Map) {
										Map<String,Object> itemMap = (Map) item;
										if(itemMap != null && itemMap.size() > 0) {
											Set<String> keySet = itemMap.keySet();
											for(String key : keySet) {
												noNeedToSaveMethodName.add(("get"+key).toLowerCase());
												noNeedToSaveMethodName.add(("set"+key).toLowerCase());
											}
										}
									}
								}
							}
							//protected成员变量
							List<Object> protectedField = (List<Object>) fieldMap.get("protected");
							if(protectedField != null && protectedField.size() > 0) {
								for(Object item : protectedField) {
									if(item instanceof Map) {
										Map<String,Object> itemMap = (Map) item;
										if(itemMap != null && itemMap.size() > 0) {
											Set<String> keySet = itemMap.keySet();
											for(String key : keySet) {
												noNeedToSaveMethodName.add(("get"+key).toLowerCase());
												noNeedToSaveMethodName.add(("set"+key).toLowerCase());
											}
										}
									}
								}
							}
						}
						methodNames.remove(className);
						for (MethodDeclaration item : ti.methodList)
						{	
							TreeTraverseUtil methodTreeUtil = new TreeTraverseUtil();
							MethodNode methodNode = new MethodNode();
							MethodTree methodTree = new MethodTree();
							CountLength cl_2 = new CountLength();
							MethodVariableVisit mv = new MethodVariableVisit();
							McCabe mcCabe = new McCabe();
							AccessToForeignData access = new AccessToForeignData();
							int nos = 0;
							methodNode.setClassName(packageClassName);//设置全类
							methodNode.setProjectName(projectName);
							if(item.getJavadoc()!=null) {
								methodNode.setIsJavadoc("true");
							}else {
								methodNode.setIsJavadoc("false");
							}
							//方法内语句数
							if(item.getBody()!=null) {
								nos = item.getBody().statements().size();
							}
							//参数个数
							int nop = item.parameters().size();
							cl_2.countLength(item.toString(), "\n");//方法代码行数
							methodNode.setMethodLine(cl_2.getCodeLength());
							methodTree.setMLOC(cl_2.getCodeLength());
							methodNode.setNOS(nos);
							methodTree.setNOS(nos);
							methodTree.setNOP(nop);
							//方法内全局变量个数
							item.accept(mv);
							int nogv = mv.NOGV;
							//方法圈复杂度
							int cyclomatic = mcCabe.getMcCabe(item);																			
							//获取方法节点树信息
							methodTreeMap = methodTreeUtil.treeTraverse(item);
							//ATFD
							int atfd = access.calculate_ATFD(item);
							methodTree.setProjectName(projectName);
							methodTree.setClassName(packageClassName);
							methodTree.setMaxChildren(methodTreeMap.get("maxChildren"));
							methodTree.setNodeSum(methodTreeMap.get("nodeSum"));
							methodTree.setTreeDepth(methodTreeMap.get("treeDepth"));
							methodTree.setTreeWidth(methodTreeMap.get("treeWidth"));
							methodTree.setMethodName(item.getName().toString());
							methodTree.setNOGV(nogv);
							methodTree.setMCN(cyclomatic);
							methodTree.setATFD(atfd);
							methodTrees.add(methodTree);
							Map<String,Object> methodInfoMap = new HashMap<>();		
							StatementsVisitToDB.varFileds = new HashMap<>();
							MethodInfoVisitToDB mi = new MethodInfoVisitToDB(importMap,packageName,basicType);
							item.accept(mi);
							//根据方法名称判断,如果是get+属性名或者是set+属性名，则默认为getter和setter方法,则不做保存。
							if(noNeedToSaveMethodName.contains(mi.name.toLowerCase())) {
								methodNames.remove(mi.name);
								continue;
							}
							//如果是构造函数,先不做保存
							if(mi.constructor) {
								continue;
							}
							methodInfoMap.put(mi.name,new HashMap<String,Object>());
							methodNode.setMethodName(mi.name);//设置方法名
							
							if(CollectionUtil.isNullOrEmpty(mi.modifiers)) {
								methodNode.setMethodType(0);
							}else {
								Modifier item2 =  mi.modifiers.get(0);
								if("public".equals(item2.getKeyword()+"")) {
									List<Object> publicMethods = (List<Object>) methodMap.get("public");
									publicMethods.add(methodInfoMap);
									methodNode.setMethodType(2);//设置方法属性类别
								}
								else if("private".equals(item2.getKeyword()+"")) {
									List<Object> privateMethods = (List<Object>) methodMap.get("private");
									privateMethods.add(methodInfoMap);
									methodNode.setMethodType(1);//设置方法属性类别
								}
								else if("protected".equals(item2.getKeyword()+"")) {
									List<Object> protectedMethods = (List<Object>) methodMap.get("protected");
									protectedMethods.add(methodInfoMap);
									methodNode.setMethodType(3);//设置方法属性类别
								}else {	
									methodNode.setMethodType(0);
								}
								/*
								for (Modifier item2 : mi.modifiers)
								{
									if("public".equals(item2.getKeyword()+"")) {
										List<Object> publicMethods = (List<Object>) methodMap.get("public");
										publicMethods.add(methodInfoMap);
										methodNode.setMethodType(2);//设置方法属性类别
									}
									else if("private".equals(item2.getKeyword()+"")) {
										List<Object> privateMethods = (List<Object>) methodMap.get("private");
										privateMethods.add(methodInfoMap);
										methodNode.setMethodType(1);//设置方法属性类别
									}
									else if("protected".equals(item2.getKeyword()+"")) {
										List<Object> protectedMethods = (List<Object>) methodMap.get("protected");
										protectedMethods.add(methodInfoMap);
										methodNode.setMethodType(3);//设置方法属性类别
									}						
								}
								*/
							}
							
							//保存方法的基本信息
							Map methodAllInfo = (Map) methodInfoMap.get(mi.name.toString());
							methodAllInfo.put("basicInfo", mi.basicInfoMap);
							/**
							 * 设置方法节点的基本信息
							 */
							if(mi.basicInfoMap != null) {
								methodNode.setReturnType((String) (mi.basicInfoMap.get("return") == null ? "void" : mi.basicInfoMap.get("return")));
								if(mi.basicInfoMap.get("parameters") != null) {
									methodNode.setArguementCount((Integer)(((Map)mi.basicInfoMap.get("parameters")).get("argumentsCount") == null 
											? 0 : ((Map)(mi.basicInfoMap.get("parameters"))).get("argumentsCount")));
									methodNode.setParameter(FastJsonUtil.objectToString(mi.basicInfoMap.get("parameters")));
								}else {
									methodNode.setArguementCount(0);
									methodNode.setParameter("{}");
								}						
							}
							methodNode.setType(1);
							methodNodes.add(methodNode);
							Map<String,Object> statementMap = new HashMap<>();
							for (Statement item2 : mi.statements)
							{			
								if(StatementsVisitToDB.varFileds != null && StatementsVisitToDB.varFileds.size()>0) {
									StatementsVisitToDB av = new StatementsVisitToDB(methodNames, statementMap, className, packageName, importMap);
									item2.accept(av);
								}else {
									StatementsVisitToDB av = new StatementsVisitToDB(fieldMap,(Map)mi.basicInfoMap.get("parameters"),methodNames,className,statementMap,packageName,importMap);
									item2.accept(av);
								}
								
							}			
							methodAllInfo.put("method", statementMap);
							
						}
						ObjectMapper mapper = new ObjectMapper();				
						String methodMapJsonStr = mapper.writeValueAsString(methodMap);
						String fieldMapJsonStr = mapper.writeValueAsString(fieldMap);
						String importMapJsonStr = mapper.writeValueAsString(importMap);
						String importIndexJsonStr = mapper.writeValueAsString(importIndex);
						String superInterfaceMapJsonStr = mapper.writeValueAsString(superInterfaceAllMap);
						String methodNamesStr = mapper.writeValueAsString(methodNameMap);
						String authorStr = mapper.writeValueAsString(tagElementMap.get("author"));
						String sinceStr = mapper.writeValueAsString(tagElementMap.get("since"));
						String versionStr = mapper.writeValueAsString(tagElementMap.get("version"));

						//System.out.println(methodMapJsonStr);
						//System.out.println(fieldMapJsonStr);
						//System.out.println(importMapJsonStr);
						//System.out.println(importIndexJsonStr);
						//System.out.println(superClassName);
						//System.out.println(superInterfaceMapJsonStr);
						source.setMethods(methodMapJsonStr);
						source.setFields(fieldMapJsonStr);
						source.setFieldSum(fieldSum);
						source.setNames(packageClassName);
						source.setImportClassMap(importIndexJsonStr);
						source.setMethodNames(methodNamesStr);
						source.setSuperClassName(superClassName);
						source.setInterfaceName(superInterfaceMapJsonStr);
						source.setProjectName(projectName);
						source.setDateChangeLastTime(new Date());
						source.setDateCreateTime(new Date());
						source.setMethodSum(CollectionUtil.isNullOrEmpty(methodNames) ? 0 : methodNames.size());
						source.setLineSum(lineSum);
						//System.out.println("End Finally");
						classMessagePo.setSource(source);
						classMessagePo.setNode(node);
						classMessagePo.setClassNode(classNodeToDB);
						classMessagePo.setMethodNodes(methodNodes);
						classMessagePo.setClassTree(classTree);
						classMessagePo.setMethodTrees(methodTrees);
						classMessagePo.setType(classNode);
						classMessagePos.add(classMessagePo);
						projectInfo.setCreateTime(new Date());
					}					
				}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		return classMessagePos;
	}
	
	private static List<String> getFieldClassList(List<Map> fieldList) {
		//遍历field方法信息列表
		List<String> fieldClassList = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(fieldList)) {
			for(Map map : fieldList) {
				if(map.size() == 1) {
					for(Object key : map.keySet()) {
						fieldClassList.add((String)map.get(key));
					}			
				}
			}
		}
		return fieldClassList;
	}
	
	private static List<String> getMethodClassList(List<Map> methodList) throws Exception{
		//遍历method方法信息列表
		List<String> methodClassList = new ArrayList<>();
		if(CollectionUtil.isNotNullOrEmpty(methodList)) {
			for(Map map : methodList) {
				if(map.size() == 1) {
					for(Object key : map.keySet()) {
						//获取basicInfo的map,包括参数信息和返回值
						Map basicInfoMap =(Map)((Map)map.get(key)).get("basicInfo");
						//获取方法参数的map
						Map parameterMap = (Map)(basicInfoMap.get("parameters"));
						if(parameterMap != null && parameterMap.size() > 0) {
							for(Object keyName:parameterMap.keySet()) {
								if(!keyName.equals("argumentsCount")) {
									methodClassList.add((String)parameterMap.get(keyName));
								}
							}
						}
					}					
				}		
			}
		}
		return methodClassList;	
	}
	
	//获取方法间的关联关系
		private static List<MethodLink> getMethodLink(Source source,List<Integer> classIds) throws IOException {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			ClassNodeMapper classNodeMapper = sqlSession.getMapper(ClassNodeMapper.class);
			List<MethodLink> methodLinks = new ArrayList<>();
			if(classIds == null) {
				classIds = new ArrayList<>();
			}
			if(source != null) {
				//获取类名和projectName
				String className = source.getNames();
				String projectName = source.getProjectName();
				//根据类名获取类节点id
				Map<String,Object> queryClassMap = new HashMap<>();
				queryClassMap.put("className", className);
				queryClassMap.put("projectName", projectName);
				Integer classNodeId = classNodeMapper.findClassNodeIdByClassName(queryClassMap);
				String methodAllInfoJson = source.getMethods();
				//System.out.println(methodAllInfoJson);
				if(methodAllInfoJson != null && methodAllInfoJson != "") {
					//System.out.println("AB");
					try {
						Map methodAllInfoMap = FastJsonUtil.jsonStrToMap(methodAllInfoJson);
						List<Map> privateMethodInfoList = new ArrayList<>();//私有方法列表
						List<Map> publicMethodInfoList = new ArrayList<>();//公有方法列表
						List<Map> protectedMethodInfoList = new ArrayList<>();//保护方法列表
						privateMethodInfoList = (List<Map>) methodAllInfoMap.get("private");
						publicMethodInfoList = (List<Map>) methodAllInfoMap.get("public");
						protectedMethodInfoList = (List<Map>) methodAllInfoMap.get("protected");
						List<MethodLink> privateMethodLinkList = getMethodLink(privateMethodInfoList, classNodeId, 1, projectName,classIds);
						List<MethodLink> publicMethodLinkList = getMethodLink(publicMethodInfoList, classNodeId, 2, projectName,classIds);
						List<MethodLink> protectedMethodLinkList = getMethodLink(protectedMethodInfoList, classNodeId, 3, projectName,classIds);
						methodLinks.addAll(privateMethodLinkList);
						methodLinks.addAll(publicMethodLinkList);
						methodLinks.addAll(protectedMethodLinkList);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
			}	
			return methodLinks;
		}
		
		//获取方法的调用关系
		private static List<MethodLink> getMethodLink(List<Map> methodInfoList,Integer classNodeId,Integer methodType,String projectName,List<Integer> classIds) throws IOException {
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSession sqlSession = factory.openSession();
			MethodNodeMapper methodNodeMapper = sqlSession.getMapper(MethodNodeMapper.class);
			ClassNodeMapper classNodeMapper = sqlSession.getMapper(ClassNodeMapper.class);
			List<MethodLink> methodLinks = new ArrayList<>();
			try {
				if(CollectionUtil.isNotNullOrEmpty(methodInfoList)) {
					for(Map temp : methodInfoList) {
						if(temp.keySet().size() == 1) {
							for(Object keyMethodName : temp.keySet()) {
							Map methodInfoMap = (Map) temp.get(keyMethodName);
								/**
								 * 获取方法的基本信息
								 * 如：返回值，参数个数，方法属性等
								 * 拿到SourceId
								 */
								Integer sourceId = 0;
								if(methodInfoMap != null) {
									Map basicInfoMap = (Map) methodInfoMap.get("basicInfo");
									String methodName = (String) keyMethodName;
									String returnType = (String) basicInfoMap.get("return");
									Integer arguementCount = 0;
									Map parameterMap = (Map) basicInfoMap.get("parameters");
									if(parameterMap != null) {
										arguementCount = (Integer) parameterMap.get("argumentsCount");
									}
									Map<String,Object> queryMethodNodeIdMap = new HashMap<>();
									queryMethodNodeIdMap.put("classNodeId", classNodeId);
									queryMethodNodeIdMap.put("methodType", methodType);
									queryMethodNodeIdMap.put("methodName", methodName);
									queryMethodNodeIdMap.put("returnType", returnType);
									queryMethodNodeIdMap.put("arguementCount", arguementCount);
									List<MethodNode> methodNodeList = methodNodeMapper.findMethodNodeList(queryMethodNodeIdMap);
									if(CollectionUtil.isNotNullOrEmpty(methodNodeList)) {
										if(methodNodeList.size() == 1) {
											sourceId = methodNodeList.get(0).getId();
										}else {
											/**
											 * 比对参数列表
											 */
											for(MethodNode tempMethodNode : methodNodeList) {
												List<Object> parameterMapValue = new ArrayList<>(parameterMap.values());
												String methodNodeParameterJson = tempMethodNode.getParameter();
												Map methodNodeParameterMap = FastJsonUtil.jsonStrToMap(methodNodeParameterJson);
												List<Object> methodNodeParameterValues =new ArrayList<>(methodNodeParameterMap.values());
												if(CollectionUtil.compareTwoList(parameterMapValue, methodNodeParameterValues)) {
													sourceId = tempMethodNode.getId();
													break;
												}
											}
										}
									}
									if(!sourceId.equals(0)) {
										/**
										 * 获取方法调用链
										 */
										Map methodMap = (Map) methodInfoMap.get("method");
										if(methodMap != null) {
											//已成员变量名为key
											for(Object fieldKey : methodMap.keySet()) {
												List<Map> methodDetailList = (List<Map>) methodMap.get(fieldKey);
												//获取这个key的类型
												if(CollectionUtil.isNotNullOrEmpty(methodDetailList)) {
													String fieldClassName = (String) methodDetailList.get(0).get("type");
													/**
													 * 判断该类型是否已经存在于表中
													 * 存在的话返回id
													 * 否则插入
													 */
													Map<String,Object> queryClassNodeMap = new HashMap<>();
													queryClassNodeMap.put("className", fieldClassName);
													queryClassNodeMap.put("projectName", projectName);
													Integer classId = 0;
													classId = classNodeMapper.findClassNodeIdByClassName(queryClassNodeMap);
													if(classId == null) {
														classId = 0;
													}
													//System.out.println("classNodeId:"+classId);
													if(classId.equals(0)) {
														classId = classNodeMapper.findClassNodeIdByClassNameAndType(queryClassNodeMap);
														if(classId == null) {
															classId = 0;
														}
														if(classId.equals(0)) {
															ClassNode classNode = new ClassNode();
															classNode.setClassName(fieldClassName);
															classNode.setProjectName("public");
															classNode.setPackageName("public");
															classNode.setType(2);//非本项目下的类
															classNodeMapper.insertClassNode(classNode);
															classId = classNode.getId();
															sqlSession.commit();
														}
													}
													if(!classId.equals(0)) {
														/**
														 * 根据classId和methodName和参数个数和参数列表获取id
														 * 获取methodNode的id作为target
														 * 注：如果不存在该id则插入新的methodNode													  
														 */
														for(Map item : methodDetailList) {
															Integer targetId = 0;
															String targetMethodName = (String) item.get("methodName");
															Integer targetArguementCount = 0;
															String targetParameter = "{}";
															List<Map> parameterMapList = (List<Map>) item.get("arguments");
															Map targetParameterMap = new HashMap<>();
															if(CollectionUtil.isNotNullOrEmpty(parameterMapList)) {
																
																for(Map tempParameter : parameterMapList) {
																	if(tempParameter.keySet().size() == 1) {
																		for(Object tempParameterKey : tempParameter.keySet()) {
																			if(tempParameter.containsKey("argumentsCount")) {
																				targetArguementCount = (Integer) tempParameter.get("argumentsCount");																							
																			}
																			targetParameterMap.put(tempParameterKey, tempParameter.get(tempParameterKey));
																		}																	
																	}																
																}
															}
															Map<String,Object> queryTargetMethodNode = new HashMap<>();
															queryTargetMethodNode.put("classNodeId", classId);
															queryTargetMethodNode.put("methodName", targetMethodName);
															queryTargetMethodNode.put("arguementCount", targetArguementCount);
															List<MethodNode> targetMethodNodeList = methodNodeMapper.findMethodNodeList(queryTargetMethodNode);
															if(CollectionUtil.isNotNullOrEmpty(targetMethodNodeList)) {
																
															}else {
																targetMethodNodeList = methodNodeMapper.findMethodNodeListByType(queryTargetMethodNode);
																if(CollectionUtil.isNotNullOrEmpty(targetMethodNodeList)) {
																	
																}else {
																	/**
																	 * 插入新的method节点
																	 */
																	MethodNode targetNewMethodNode = new MethodNode();
																	if(classIds.contains(classId)) {
																		targetNewMethodNode.setType(1);
																	}else {
																		targetNewMethodNode.setType(2);
																	}														
																	targetNewMethodNode.setClassNodeId(classId);
																	targetNewMethodNode.setMethodName(targetMethodName);
																	targetNewMethodNode.setParameter(FastJsonUtil.objectToString(targetParameterMap));
																	targetNewMethodNode.setMethodType(4);
																	targetNewMethodNode.setReturnType("Object");
																	targetNewMethodNode.setArguementCount(targetArguementCount);
																	targetNewMethodNode.setProjectName(projectName);
																	methodNodeMapper.insertMethodNode(targetNewMethodNode);
																	targetId = targetNewMethodNode.getId();
																	sqlSession.commit();
																}				
															}
															if(CollectionUtil.isNotNullOrEmpty(targetMethodNodeList)) {
																if(targetMethodNodeList.size() == 1) {
																	targetId = targetMethodNodeList.get(0).getId();
																}else {
																	for(MethodNode tempTarget : targetMethodNodeList) {
																		List<Object> targetParameterValues = new ArrayList<>(targetParameterMap.values());
																		Map tempTargetParameterMap = FastJsonUtil.jsonStrToMap(tempTarget.getParameter());
																		List<Object> tempTargetParameterValues = new ArrayList<>(tempTargetParameterMap.values());
																		if(CollectionUtil.compareTwoList(tempTargetParameterValues, targetParameterValues)) {
																			targetId = tempTarget.getId();
																			break;
																		}
																	}
																}
															}
															if(!targetId.equals(0)) {
																MethodLink methodLink = new MethodLink();
																methodLink.setSource(sourceId);
																methodLink.setTarget(targetId);
																methodLink.setProjectName(projectName);
																if(classIds.contains(classId)){
																	methodLink.setLinkType(1);
																}else{
																	methodLink.setLinkType(2);
																}																
																if(CollectionUtil.isNotNullOrEmpty(methodLinks)) {
																	boolean isNew = true;
																	for(MethodLink tempMethodLink : methodLinks) {
																		if(tempMethodLink.getSource().equals(sourceId) && tempMethodLink.getTarget().equals(targetId)) {
																			Integer timeCount = tempMethodLink.getTimeCount();
																			timeCount++;
																			tempMethodLink.setTimeCount(timeCount);
																			isNew = false;
																			break;
																		}
																		
																	}
																	if(isNew) {
																		methodLink.setTimeCount(1);
																		methodLinks.add(methodLink);
																	}
																}else {
																	methodLink.setTimeCount(1);
																	methodLinks.add(methodLink);
																}
															}
														}
													}
												}
											}
										}
									}else {
										System.out.println("出错");
									}
								}						
							}
						}
					}
				}
			}catch (Exception e) {
				// TODO: handle exception
			}	
			return methodLinks;
		}
		
		public static ProjectXmlDirMapping getXMLInfo(String PATH,String projectName)
		{
			
			ProjectXmlDirMapping projectXmlDirMapping = new ProjectXmlDirMapping();
			String fileName = "D:/project/download/xml/";
			//确定下载目录的路径
			/**
			 * 
			 */
			if(PATH.contains(":")) {
				fileName = PATH.replace(filePath, fileName);
				fileName = fileName.replace("\\", "/");
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
				fileName+=".xml";
			}else {
				fileName = PATH.substring(0, PATH.lastIndexOf("."));
				fileName+=".xml";
				fileName = "toXml" + fileName;
			}
			System.out.println("download:" + fileName);
			String downloadDir = fileName.substring(0,fileName.lastIndexOf("/"));
			File downloadDirectory = new File(downloadDir);
			if (!downloadDirectory.exists()) {
				downloadDirectory.mkdirs();
			}
			PrintStream old = System.out;// 注意这个一定要提前保存。后边System.out会改变
			FileOutputStream fileOutputStream;
			
			try
			{
				fileOutputStream = new FileOutputStream(fileName, true);
				PrintStream printStream = new PrintStream(fileOutputStream);
				System.setOut(printStream);
				System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			}
			
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}

			// 将源文件解析成ast树，并且返回编译单元作为树的根节点
			CompilationUnit comp = ASTUtil.getCompilationUnit(PATH);

			System.out.println("<CompilationUnit>");

			// 获得headNode信息，唯一
			HeadInfoVisit hi = new HeadInfoVisit();
			comp.accept(hi);
			String packageName = hi.packageNode == null ? "src" :hi.packageNode.getName().toString();
			System.out.println("<Package>" + packageName + "</Package>");
			for (ImportDeclaration item : hi.importNodeList)
			{
				System.out.println("<Import>" + item.getName().toString() + "</Import>");
			}

			// 获得所有类的信息，不唯一
			TypeDeclarationVisit td = new TypeDeclarationVisit();
			comp.accept(td);
			TypeDeclaration classNode = td.getClassNode();// 获得主class
			TypeInfoVisitToDB ti = new TypeInfoVisitToDB();
			if(classNode != null) {
				classNode.accept(ti);//存在控指针
				String className = ASTUtil.changeCode(ti.name);
				System.out.println("<ClassName>" + className + "</ClassName>");
				System.out.print("<Modifiers>");
				for (Modifier item : ti.modifiers)
				{
					System.out.print(item.getKeyword() + " ");
				}
				System.out.println("</Modifiers>");

				if (ti.superClass != null)
					System.out.println("<SuperClass>" + ASTUtil.changeCode(ti.superClass.toString()) + "</SuperClass>");
				else
					System.out.println("<SuperClass>" + ti.superClass + "</SuperClass>");

				System.out.print("<SuperInterfaces>");
				for (ASTNode item : ti.superInterface)
				{
					System.out.print(ASTUtil.changeCode(item.toString()) + " ");
				}
				System.out.println("</SuperInterfaces>");

				ti.setInnerClass(td.getOtherNode());
				System.out.print("<InnerClass>");
				for (TypeDeclaration item : ti.InnerClassList)
				{
					System.out.print(ASTUtil.changeCode(item.getName().toString()) + " ");
				}
				System.out.println("</InnerClass>");

				// Field

				for (FieldDeclaration item : ti.FieldList)
				{

					FieldInfoVisit fi = new FieldInfoVisit();
					item.accept(fi);

					System.out.println("<Field>");
					System.out.println("<Type>" + ASTUtil.changeCode(fi.type.toString()) + "</Type>");
					System.out.print("<Modifiers>");
					for (Modifier item2 : fi.modifiers)
					{
						System.out.print(item2.getKeyword() + " ");
					}
					System.out.println("</Modifiers>");

					System.out.print("<Fragments>");
					for (VariableDeclarationFragment item2 : fi.fragemtns)
					{
						System.out.print(item2.getName().toString() + "=");
						String str = null;
						if (item2.getInitializer() != null)
						{
							str = item2.getInitializer().toString();
						}
						System.out.print(str);
					}
					System.out.println("</Fragments>");
					System.out.println("</Field>");

				}

				// Method

				for (MethodDeclaration item : ti.methodList)
				{

					MethodInfoVisit mi = new MethodInfoVisit();
					item.accept(mi);

					System.out.println("<Method>");

					System.out.print("<Modifiers>");
					for (Modifier item2 : mi.modifiers)
					{
						System.out.print(item2.toString() + " ");
					}
					System.out.println("</Modifiers>");

					System.out.println("<MethodName>" + mi.name + "</MethodName>");
					System.out.println("<IsConstructor>" + mi.constructor + "</IsConstructor>");

					// 可能返回null类型，这样使用不了toString方法
					if (mi.returnType != null)
						System.out.println("<ReturnType>" + ASTUtil.changeCode(mi.returnType.toString()) + "</ReturnType>");
					else
						System.out.println("<ReturnType>" + mi.returnType + "</ReturnType>");

					System.out.print("<Parameters>");
					for (ASTNode item2 : mi.parameters)
					{
						System.out.print(ASTUtil.changeCode(item2.toString()) + " ");
					}
					System.out.println("</Parameters>");

					System.out.print("<Statements>");
					for (Statement item2 : mi.statements)
					{
						
						StatementVisitToXML av = new StatementVisitToXML();
						item2.accept(av);
					}
					System.out.println("</Statements>");
					System.out.println("</Method>");

				}

				System.out.println("</CompilationUnit>");

				System.setOut(old);
				projectXmlDirMapping.setProjectName(projectName);
				projectXmlDirMapping.setXmlDir(fileName);
				projectXmlDirMapping.setClassName(className);
				projectXmlDirMapping.setDateCreateTime(new Date());
			}else {
				return null;
			}
			return projectXmlDirMapping;
		}

}
