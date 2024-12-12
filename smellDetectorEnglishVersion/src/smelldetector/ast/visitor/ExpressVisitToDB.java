package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import smelldetector.ast.visitor.StatementsVisitToDB;

public class ExpressVisitToDB extends ASTVisitor{
	
	public String rightHandSide;
	public String methodName;
	private Map<String,Object> fields = new HashMap<>();
	private List<String> methodNames;//当前类的方法名
	private Map<String,Object> statementMap;//存方法的调用连
	private String className;//保存该方法的类名
	private Map<String,Object> basicTypeField = new HashMap<>();
	private String packageName;//包名
	private Map<String,Object> importMap;
	private List<String> basicType = new ArrayList<>();//基本类型
	public static int sortNum = 1;//调用顺序
	
	public ExpressVisitToDB(Map<String, Object> fields, List<String> methodNames, Map<String, Object> statementMap,
			String className, Map<String, Object> basicTypeField,String packageName,Map<String, Object> importMap,List<String> basicType) {
		super();
		this.fields = fields;
		this.methodNames = methodNames;
		this.statementMap = statementMap;
		this.className = className;
		this.basicTypeField = basicTypeField;
		this.packageName = packageName;
		this.importMap = importMap;
		this.basicType = basicType;
	}

	
	//父节点是赋值语句
	@Override
	public boolean visit(VariableDeclarationFragment node) {		
		Object parent = node.getParent();
		if(parent instanceof VariableDeclarationStatement) {
			VariableDeclarationStatement parentNode = (VariableDeclarationStatement)parent;
			String value = parentNode.getType().toString();
			if(!basicTypeField.containsKey(value)) {
				List<VariableDeclarationFragment> fragemtns = parentNode.fragments();
				if(fragemtns != null && fragemtns.size() == 1) {
					if(importMap.containsKey(value)) {
						fields.put(fragemtns.get(0).getName().toString(), importMap.get(value));
					}else {
						if(basicType.contains(value)) {
							fields.put(fragemtns.get(0).getName().toString(), value);
						}else {
							fields.put(fragemtns.get(0).getName().toString(), packageName+"."+value);
						}
					}			
				}
			}
		}	
		StatementsVisitToDB.varFileds = fields;
		return true;
	}
	//赋值表达式
	@Override
	public boolean visit(Assignment node) {
		rightHandSide = node.getRightHandSide().toString();
		String leftField= node.getLeftHandSide().toString();
		return true;
	}
	
	//方法调用
	@Override
	public boolean visit(MethodInvocation node) {
		/*
		//拿到父节点，表达式有可能是赋值语句	
		Object parent = node.getParent().getParent();
		if(parent instanceof VariableDeclarationStatement) {
			VariableDeclarationStatement parentNode = (VariableDeclarationStatement)parent;
			
			String value = parentNode.getType().toString();
			if(!basicTypeField.containsKey(value)) {
				List<VariableDeclarationFragment> fragemtns = parentNode.fragments();
				if(fragemtns != null && fragemtns.size() == 1) {
					fields.put(fragemtns.get(0).getName().toString(), value);
				}
			}
			
		}
		*/
		methodName = node.getName().toString();
		//获取调用方法的参数列表
		List parameters = node.arguments();
		List<Object> para = new ArrayList<>();
		if(parameters!=null && parameters.size()>0) {
			Map<String,Object> argumentsCount = new HashMap<>();
			argumentsCount.put("argumentsCount", parameters.size());
			para.add(argumentsCount);
			for(int i=0;i<parameters.size();i++) {
				//数字字面量
				if(parameters.get(i) instanceof NumberLiteral) {
					Map<String,Object> map = new HashMap<>();
					map.put(parameters.get(i).toString(),"Number");
					para.add(map);
				}else if(parameters.get(i) instanceof StringLiteral) { //String字面量
					Map<String,Object> map = new HashMap<>();
					map.put(parameters.get(i).toString(), "String");
					para.add(map);
				}else if(parameters.get(i) instanceof BooleanLiteral){
					Map<String,Object> map = new HashMap<>();
					map.put(parameters.get(i).toString(), "Boolean");
					para.add(map);
				}else if(parameters.get(i) instanceof SimpleName) { 
					if(fields.containsKey(parameters.get(i).toString())) {
						Map<String,Object> map = new HashMap<>();
						map.put(parameters.get(i).toString(), fields.get(parameters.get(i).toString()).toString());
						para.add(map);
					}else {
						Map<String,Object> map = new HashMap<>();
						map.put(parameters.get(i).toString(),"default");
						para.add(map);
					}
				}else if(parameters.get(i) instanceof InfixExpression) { //中缀表达式,一般为boolean类型
					Map<String,Object> map = new HashMap<>();
					map.put(parameters.get(i).toString(), "Boolean");
					para.add(map);
				}else if(parameters.get(i) instanceof MethodInvocation) {
					Map<String,Object> map = new HashMap<>();
					map.put(parameters.get(i).toString(), "Method");
					para.add(map);
				}
			}
		}
		String statement = node.toString();
		saveInfoToMap(statement,para);
		return true;
	}

	public void saveInfoToMap(String statement,List<Object> para) {
		String fieldName = "";
		if(statement.indexOf("."+methodName)!=-1){
			int index = statement.indexOf("."+methodName);
			fieldName = statement.substring(0, index);
		}else {
			if(statement.indexOf(methodName)==0) {
				fieldName = "this";
			}
		}
		//如果调用的成员是基本类型的则，不做保存	
		if(!basicTypeField.containsKey(fieldName)) {
			if(fieldName.equals("this")) {
				if(statementMap.containsKey("this")) {
					List<Object> methodInfo = (List<Object>) statementMap.get("this");
					Map<String,Object> methodInfoMap = new HashMap<>();
					//methodInfoMap.put("type", className);
					methodInfoMap.put("methodName", methodName);
					methodInfoMap.put("sort", sortNum);
					methodInfoMap.put("arguments", para);
					methodInfo.add(methodInfoMap);
					statementMap.put("this", methodInfo);
				}else {
					List<Object> methodInfo = new ArrayList<>();
					Map<String,Object> methodInfoMap = new HashMap<>();
					methodInfoMap.put("type", packageName+"."+className);
					methodInfoMap.put("methodName", methodName);
					methodInfoMap.put("sort", sortNum);
					methodInfoMap.put("arguments", para);
					methodInfo.add(methodInfoMap);
					statementMap.put("this", methodInfo);
				}
				sortNum ++;
			}else {
				if(fields.containsKey(fieldName)) {
					String typeName = (String) fields.get(fieldName);
					if(typeName.lastIndexOf(".")!=-1) {
						typeName = typeName.substring(typeName.lastIndexOf(".")+1, typeName.length());
					}
					if(statementMap.containsKey(fieldName)) {
						List<Object> methodInfo = (List<Object>) statementMap.get(fieldName);
						Map<String,Object> methodInfoMap = new HashMap<>();
						if(!basicType.contains(typeName)) {
							/**
							 * 可能存在导包的问题
							 * 例如java.util.*这种导包模式的问题
							 */
							if(importMap.containsKey(typeName)) {
								typeName = (String) importMap.get(typeName);
							}else {
								typeName = packageName + "." + typeName;
							}
						}				
						//methodInfoMap.put("type", typeName);
						methodInfoMap.put("methodName", methodName);
						methodInfoMap.put("sort", sortNum);
						methodInfoMap.put("arguments", para);
						methodInfo.add(methodInfoMap);
						statementMap.put(fieldName, methodInfo);
					}else {
						List<Object> methodInfo = new ArrayList<>();
						Map<String,Object> methodInfoMap = new HashMap<>();
						if(!basicType.contains(typeName)) {
							if(typeName.indexOf("<")!=-1) {
								typeName = typeName.substring(0,typeName.indexOf("<"));
							}
							if(importMap.containsKey(typeName)) {
								typeName = (String) importMap.get(typeName);
							}else {
								typeName = packageName + "." + typeName;
							}
						}	
						methodInfoMap.put("type", typeName);
						methodInfoMap.put("methodName", methodName);
						methodInfoMap.put("sort", sortNum);
						methodInfoMap.put("arguments", para);
						methodInfo.add(methodInfoMap);
						statementMap.put(fieldName, methodInfo);
					}
					sortNum ++;
				}
			}	
			
		}
		
	}

}
