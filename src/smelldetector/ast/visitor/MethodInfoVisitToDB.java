package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smelldetector.util.CollectionUtil;

import org.eclipse.jdt.core.dom.*;

public class MethodInfoVisitToDB extends ASTVisitor{

	public List<Modifier> modifiers;
	public boolean constructor;
	public Type returnType;
	public Type typeGenericity;
	public String name;
	public List<SingleVariableDeclaration> parameters;
	public List<Statement> statements;
	public Map<String, Object> basicInfoMap = new HashMap<>();	
	public Map<String,Object> importMap ;
	public List<String> basicType;
	public String packageName;
	public MethodInfoVisitToDB()
	{
		modifiers = new ArrayList<Modifier>();
		parameters = new ArrayList<SingleVariableDeclaration>();
		statements = new ArrayList<Statement>();
	}

	
	
	public MethodInfoVisitToDB(Map<String, Object> importMap,String packageName,List<String> basicType) {
		super();
		modifiers = new ArrayList<Modifier>();
		parameters = new ArrayList<SingleVariableDeclaration>();
		statements = new ArrayList<Statement>();
		this.importMap = importMap;
		this.packageName = packageName;
		this.basicType = basicType;
	}



	public boolean visit(MethodDeclaration node)
	{
		//参数信息的Map
		Map<String,Object> parameterMap = new HashMap<>();
		// 这里把注解过滤掉。
		for (Object item : node.modifiers())
		{
			if (item.toString().charAt(0) != '@')
				modifiers.add((Modifier) item);
		}

		constructor = node.isConstructor();
		returnType = node.getReturnType2();
		//basicInfoMap.put("return", returnType!=null ? returnType.toString() : "");
		if(returnType != null) {
			if(returnType.toString().equals("void")) {
				basicInfoMap.put("return", "void");
			}
			else if(basicType.contains(returnType.toString())) {
				basicInfoMap.put("return", returnType.toString());
			}else {
				if(importMap!=null) {
					String typeName = returnType.toString();
					if(typeName.indexOf("<")!=-1) {
						typeName = typeName.substring(0,typeName.indexOf("<"));
					}
					if(importMap.containsKey(typeName)){
						basicInfoMap.put("return", importMap.get(typeName));
					}else {
						basicInfoMap.put("return", packageName+"."+returnType.toString());
					}
				}else {
					basicInfoMap.put("return", packageName+"."+returnType.toString());
				}
			}			
		}else {
			basicInfoMap.put("return", "");
		}
		name = node.getName().toString();
		parameters = node.parameters();
		if(CollectionUtil.isNotNullOrEmpty(parameters)) {
			parameterMap.put("argumentsCount", parameters.size());
			for(SingleVariableDeclaration temp:parameters) {
				if(basicType.contains(temp.getType().toString())) {
					parameterMap.put(temp.getName().toString(),temp.getType().toString());
				}else {
					if(importMap!=null) {
						String typeName = temp.getType().toString();
						if(typeName.indexOf("<")!=-1) {
							typeName = typeName.substring(0,typeName.indexOf("<"));
						}
						if(importMap.containsKey(typeName)){
							parameterMap.put(temp.getName().toString(),importMap.get(typeName));
						}else {
							parameterMap.put(temp.getName().toString(), packageName+"."+temp.getType().toString());
						}
					}else {
						parameterMap.put(temp.getName().toString(), packageName+"."+temp.getType().toString());
					}
				}					
			}
		}
		basicInfoMap.put("parameters", parameterMap);
		statements = node.getBody() == null ? new ArrayList<Statement>():node.getBody().statements();
		
		return false;
	}
}
