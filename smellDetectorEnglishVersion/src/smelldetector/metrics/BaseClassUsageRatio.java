package smelldetector.metrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import smelldetector.util.CollectionUtil;

public class BaseClassUsageRatio {
	
	List<TypeDeclaration> classNodes;
	List<ITypeBinding> classBindings = new ArrayList<>();
	TypeDeclaration node;
	
	public BaseClassUsageRatio(List<TypeDeclaration> classNodes, TypeDeclaration node) {
		this.classNodes = classNodes;
		this.node = node;
	}
	
	public float calculate_BUR(int nProtM) {
		float ratio = 1;
		float uses = 0;
		if(CollectionUtil.isNotNullOrEmpty(classNodes)) {
			for(TypeDeclaration classNode : classNodes) {
				classBindings.add(classNode.resolveBinding());		
			}
		}
		LocalEntityVisit le = new LocalEntityVisit();
		node.accept(le);
		
		if(nProtM > 0) {
			List<MethodInvocation> ListOfLocalMethodInvoked = le.ListOfLocalMethodInvoked;
			List<FieldAccess> ListOfLocalFieldUsed = le.ListOfLocalFieldUsed;
			ITypeBinding declaration = node.resolveBinding().getSuperclass();
			if(declaration!=null) {
				declaration = findClass(declaration);
			}			
			IMethodBinding[] methods = declaration.getDeclaredMethods();
			
			if(methods!=null  && ListOfLocalMethodInvoked!=null) {
				for(IMethodBinding method:methods){
					if(Modifier.isProtected(method.getModifiers())){
						for(MethodInvocation methodInv:ListOfLocalMethodInvoked){
							
							if(methodInv.resolveMethodBinding().getKey().equals(method.getKey())){
								uses++;
								break;
							}
						}
					}
				}
			}
			
			IVariableBinding[] fields = declaration.getDeclaredFields();
			if(fields!=null && ListOfLocalFieldUsed!=null){
				for(IVariableBinding field:fields){
					if(Modifier.isProtected(field.getModifiers())){
						for(FieldAccess fieldAcc:ListOfLocalFieldUsed){
							if(fieldAcc.resolveFieldBinding().getKey().equals(field.getKey())){
								uses++;
								break;
							}
						}
					}
				}
			}
			ratio = uses / nProtM;
		}
		return ratio;
	}
	
	public ITypeBinding findClass(ITypeBinding declaration) {
		for(ITypeBinding classBinding : classBindings) {
			if(classBinding.getQualifiedName().equals(declaration.getQualifiedName())) {
				return classBinding;
			}
		}
		return declaration;
	}

	private class LocalEntityVisit extends ASTVisitor{
		
//		private TypeDeclaration node;
		String nameOfParentClass = null;		
		public LocalEntityVisit(){
			nameOfParentClass = node.resolveBinding().getQualifiedName();
		}	
		
		List<MethodInvocation> ListOfLocalMethodInvoked = new ArrayList<MethodInvocation>();
		List<FieldAccess> ListOfLocalFieldUsed = new ArrayList<FieldAccess>();
		
		
		private List<String> calculate_SC(TypeDeclaration node) {
			List<String> nameOfClasses = new ArrayList<>();
			List<String> namesOfSuperClasses = new ArrayList<>();
			if(CollectionUtil.isNotNullOrEmpty(classNodes)) {
				for(TypeDeclaration classNode : classNodes) {
					nameOfClasses.add(classNode.resolveBinding().getQualifiedName());
				}
			}
			ITypeBinding superClass = node.getSuperclassType()!=null?node.getSuperclassType().resolveBinding():null;

			while(superClass != null && nameOfClasses.contains(superClass.getQualifiedName())) {
				for(ITypeBinding classBinding : classBindings) {
					if(classBinding.getQualifiedName().equals(superClass.getQualifiedName())) {
						namesOfSuperClasses.add(classBinding.getQualifiedName());
						superClass = classBinding.getSuperclass();
					}
				}
				if(!namesOfSuperClasses.contains(superClass.getQualifiedName())) {
					namesOfSuperClasses.add(superClass.getQualifiedName());
					superClass = superClass.getSuperclass();
				}
			}
			return namesOfSuperClasses;
		}
		
		@Override
		public boolean visit(MethodInvocation node) {
			if(node.resolveMethodBinding()!=null && node.resolveMethodBinding().getDeclaringClass()!=null) {
				ITypeBinding declaraingClass = findClass(node.resolveMethodBinding().getDeclaringClass());
				String nameOfClass = declaraingClass.getQualifiedName();
				if(nameOfClass!=null) {
					if(nameOfClass.equals(nameOfParentClass)) {
						if(!ListOfLocalMethodInvoked.contains(node)){
							ListOfLocalMethodInvoked.add(node);
						}
					}
				}
			}
			return true;
		}
		
		@Override
		public boolean visit(FieldAccess node) {
			if(node.resolveFieldBinding()!=null && node.resolveFieldBinding().getDeclaringClass()!=null) {
				ITypeBinding declaraingClass = findClass(node.resolveFieldBinding().getDeclaringClass());
				String nameOfClass = declaraingClass.getQualifiedName();
				if(nameOfClass!=null) {
					if(!isForeignClass(nameOfClass)) {
						if(!ListOfLocalFieldUsed.contains(node)){
							ListOfLocalFieldUsed.add(node);
						}
					}
				}
			}
			return true;
		}

		private boolean isForeignClass(String nameOfClass){
			List<String> namesOfSuperClasses = calculate_SC(node);
			if(!nameOfClass.equals(nameOfParentClass) && !namesOfSuperClasses.contains(nameOfClass)){
				return true;
			}
			return false;
		}
	}

}
