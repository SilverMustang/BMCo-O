package smelldetector.metrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import smelldetector.util.CollectionUtil;

public class NumProtMembersInParent {
	
	List<TypeDeclaration> classNodes;
	List<ITypeBinding> classBindings = new ArrayList<>();
	
	public NumProtMembersInParent(List<TypeDeclaration> classNodes) {
		this.classNodes = classNodes;
	}
	
	public int calculate_nprom(TypeDeclaration node) {
	
		int protMembers = 0;
		if(CollectionUtil.isNotNullOrEmpty(classNodes)) {
			for(TypeDeclaration classNode : classNodes) {
				classBindings.add(classNode.resolveBinding());
			}
		}
		if(node.resolveBinding().getSuperclass()!=null) {
			ITypeBinding declaration = node.resolveBinding().getSuperclass();
			ITypeBinding superClass = findSuperClass(declaration);
			IMethodBinding[] methods = superClass.getDeclaredMethods();
			if(methods!=null){
				for(IMethodBinding method:methods){
					if(Modifier.isProtected(method.getModifiers())){
						protMembers++;
					}
				}
			}
			IVariableBinding[] fields = superClass.getDeclaredFields();
			if(fields!=null){
				for(IVariableBinding field:fields){
					if(Modifier.isProtected(field.getModifiers())){
						protMembers++;
					}
				}
			}
		}
		return protMembers;
	}

	private ITypeBinding findSuperClass(ITypeBinding declaration) {
		// TODO Auto-generated method stub
		for(ITypeBinding classBinding : classBindings) {
			if(classBinding.getQualifiedName().equals(declaration.getQualifiedName())) {
				return classBinding;
			}
		}
		return declaration;		
	}
}
