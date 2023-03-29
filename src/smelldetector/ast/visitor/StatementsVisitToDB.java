package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class StatementsVisitToDB extends ASTVisitor{
	
	private Map<String,Object> fieldMap ;
	private Map<String,Object> fields = new HashMap<>();
	private Map<String,Object> parameterMap;
	private List<String> methodNames;//当前类的方法名
	private Map<String,Object> statementMap;//存方法的调用连
	private String className;//保存该方法的类名
	private String packageName;//该类的包名
	private Map<String,Object> importMap;//引用的包信息
	private static int sortNum = 1;//调用顺序
	private List<String> basicType = new ArrayList<>();//基本类型
	private Map<String,Object> basicTypeField = new HashMap<>();
	public static Map<String,Object> varFileds;//包括局部变量
	public StatementsVisitToDB(Map<String, Object> fieldMap,Map<String,Object> parameterMap,List<String> methodNames,String className,Map<String,Object> statementMap,String packageName,Map<String,Object> importMap) {
		super();
		this.fieldMap = fieldMap;
		this.parameterMap = parameterMap;
		this.methodNames = methodNames;
		this.className = className;
		this.statementMap = statementMap;
		this.packageName = packageName;
		this.importMap = importMap;
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
		if(fieldMap != null) {
			//获取private的成员变量
			List<Object> privateField = (List<Object>) fieldMap.get("private");
			if(privateField != null && privateField.size() > 0) {
				for(Object item : privateField) {
					if(item instanceof Map) {
						Map itemMap = (Map) item ;
						fields.putAll(itemMap);
					}
				}
			}
			//获取public的成员变量
			List<Object> publicField = (List<Object>) fieldMap.get("public");
			if(publicField != null && publicField.size() > 0) {
				for(Object item : publicField) {
					if(item instanceof Map) {
						Map itemMap = (Map) item ;
						fields.putAll(itemMap);
					}
				}
			}		
		}
		//获取该函数的参数列表
		if(parameterMap!= null && parameterMap.size() > 0 ){
			fields.putAll(parameterMap);
		}
		if(varFileds!=null&&varFileds.size()!=0) {
			fields = varFileds;
		}
		//除去基本类型的参数
		if(fields!=null && fields.size() > 0) {
			for(String key : fields.keySet()) {
				if(key.equals("argumentsCount")) {
					continue;
				}
				String typeValue = (String) fields.get(key);
				if(basicType.contains(typeValue)) {
					 basicTypeField.put(key, typeValue);
				}
			}
		}
	}
	
	
	public StatementsVisitToDB(List<String> methodNames, Map<String, Object> statementMap, String className,
			String packageName, Map<String, Object> importMap) {
		super();
		this.methodNames = methodNames;
		this.statementMap = statementMap;
		this.className = className;
		this.packageName = packageName;
		this.importMap = importMap;
		if(varFileds!=null&&varFileds.size()!=0) {
			fields = varFileds;
		}
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
		//除去基本类型的参数
		if(fields!=null && fields.size() > 0) {
			for(String key : fields.keySet()) {
				 if(key.equals("argumentsCount")) {
					continue;
				 }
				 String typeValue = (String) fields.get(key);
				 if(basicType.contains(typeValue)) {
					 basicTypeField.put(key, typeValue);
				 }
			}
		}
	}


	public StatementsVisitToDB() {
		super();
	}

	public boolean visit(IfStatement node)
	{
		//判断是否是方法调用
		ExpressVisitToDB visitor = new ExpressVisitToDB(fields,methodNames,statementMap,className,basicTypeField,packageName,importMap,basicType);
		node.accept(visitor);
		return false;
	}

	public void endVisit(IfStatement node) 
	{
		
	}

	
	public boolean visit(ExpressionStatement node)
	{
		
		ExpressVisitToDB visitor = new ExpressVisitToDB(fields,methodNames,statementMap,className,basicTypeField,packageName,importMap,basicType);
		node.accept(visitor);
		return false;
	}
	public void endVisit(ExpressionStatement node) 
	{
		
	}
	
	public boolean visit(BreakStatement node)
	{
		
		return false;
	}
	public void endVisit(BreakStatement node) 
	{
	}
	
	public boolean visit(ContinueStatement node)
	{
		return false;
	}
	public void endVisit(ContinueStatement node) 
	{
		
	}
	
	public boolean visit(ForStatement node)
	{
		
		ExpressVisitToDB visitor = new ExpressVisitToDB(fields,methodNames,statementMap,className,basicTypeField,packageName,importMap,basicType);
		node.accept(visitor);
		return false;
	}
	
	public void endVisit(ForStatement node) 
	{
		
	}
	public boolean visit(SwitchStatement node)
	{
		ExpressVisitToDB visitor = new ExpressVisitToDB(fields,methodNames,statementMap,className,basicTypeField,packageName,importMap,basicType);
		node.accept(visitor);
		return false;
	}
	public void endVisit(SwitchStatement node) 
	{
		
	}
	
	public boolean visit(ReturnStatement node)
	{
		ExpressVisitToDB visitor = new ExpressVisitToDB(fields,methodNames,statementMap,className,basicTypeField,packageName,importMap,basicType);
		node.accept(visitor);
		return false;
	}
	public void endVisit(ReturnStatement node) 
	{
	}
	
	public boolean visit(TypeDeclarationStatement node)
	{


		return false;
	}
	public void endVisit(TypeDeclarationStatement node) 
	{
		
	}
	
	
	public boolean visit(VariableDeclarationStatement node)
	{
		
		ExpressVisitToDB visitor = new ExpressVisitToDB(fields,methodNames,statementMap,className,basicTypeField,packageName,importMap,basicType);
		node.accept(visitor);
		
		return false;
	}
	public void endVisit(VariableDeclarationStatement node) 
	{
		
	}
	
	public boolean visit(WhileStatement node)
	{
		
		ExpressVisitToDB visitor = new ExpressVisitToDB(fields,methodNames,statementMap,className,basicTypeField,packageName,importMap,basicType);
		node.accept(visitor);
		
		return false;
	}
	public void endVisit(WhileStatement node) 
	{
		
	}
	
	public boolean visit(ThrowStatement node)
	{
		
		ExpressVisitToDB visitor = new ExpressVisitToDB(fields,methodNames,statementMap,className,basicTypeField,packageName,importMap,basicType);
		node.accept(visitor);
	
		return false;
	}
	public void endVisit(ThrowStatement node) 
	{
	}
	
	public boolean isNeedToSave(String statement) {
		if(statement.indexOf("(")!= -1) {
			Set<String> fieldNames = fields.keySet();
			for(String fieldName : fieldNames) {
				if(statement.indexOf(fieldName) != -1){
					if(!basicTypeField.containsKey(fieldName)) {
						return true;
					}
				}
			}
			//存在调用本类自身的方法的情况
			for(String methodName : methodNames) {
				if(statement.indexOf(methodName) != -1){
					return true;
				}
			}
		}		
		return false;
	}
	
	public void saveInfoToMap(String statement) {
		/**
		 * 存在的情况：
		 * 1 本身方法的调用如：methodName()
		 * 2 this.methodName()
		 * 3 其他类型的调用 a.method()
		 * 4 长调用链 a.().().()......
		 */
		//判断语句是否包含"."		
		if(statement.indexOf(".") != -1) {
			//是否是长调用链
			String[] statements = statement.split("\\.");
			if(statements.length > 2){ //长调用连
				
			}else {
				String fieldName = statements[0];
				if(fieldName.equals("this")) {
					if(statementMap.containsKey("this")) {
						List<Object> methodInfo = (List<Object>) statementMap.get("this");
						Map<String,Object> methodInfoMap = new HashMap<>();
						methodInfoMap.put("type", className);
						methodInfoMap.put("methodName", statements[1]);
						methodInfoMap.put("sort", sortNum);
						methodInfo.add(methodInfoMap);
						statementMap.put("this", methodInfo);
					}else {
						List<Object> methodInfo = new ArrayList<>();
						Map<String,Object> methodInfoMap = new HashMap<>();
						methodInfoMap.put("type", className);
						methodInfoMap.put("methodName", statements[1]);
						methodInfoMap.put("sort", sortNum);
						methodInfo.add(methodInfoMap);
						statementMap.put("this", methodInfo);
					}
				}else {
					if(fields.containsKey(fieldName)) {
						String typeName = (String) fields.get(fieldName);
						if(statementMap.containsKey(fieldName)) {
							List<Object> methodInfo = (List<Object>) statementMap.get(fieldName);
							Map<String,Object> methodInfoMap = new HashMap<>();
							methodInfoMap.put("type", typeName);
							methodInfoMap.put("methodName", statements[1]);
							methodInfoMap.put("sort", sortNum);
							methodInfo.add(methodInfoMap);
							statementMap.put(fieldName, methodInfo);
						}else {
							List<Object> methodInfo = new ArrayList<>();
							Map<String,Object> methodInfoMap = new HashMap<>();
							methodInfoMap.put("type", typeName);
							methodInfoMap.put("methodName", statements[1]);
							methodInfoMap.put("sort", sortNum);
							methodInfo.add(methodInfoMap);
							statementMap.put(fieldName, methodInfo);
						}
					}
				}
			}
		}else {
			//没有"."，证明是本身类方法啊调用			
			if(statementMap.containsKey("this")) {
				List<Object> methodInfo = (List<Object>) statementMap.get("this");
				Map<String,Object> methodInfoMap = new HashMap<>();
				methodInfoMap.put("type", className);
				methodInfoMap.put("methodName", statement);
				methodInfoMap.put("sort", sortNum);
				methodInfo.add(methodInfoMap);
				statementMap.put("this", methodInfo);
			}else {
				List<Object> methodInfo = new ArrayList<>();
				Map<String,Object> methodInfoMap = new HashMap<>();
				methodInfoMap.put("type", className);
				methodInfoMap.put("methodName", statement);
				methodInfoMap.put("sort", sortNum);
				methodInfo.add(methodInfoMap);
				statementMap.put("this", methodInfo);
			}
		}
		sortNum ++;
	}

}
