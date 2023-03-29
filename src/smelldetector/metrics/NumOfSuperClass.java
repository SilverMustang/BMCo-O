package smelldetector.metrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class NumOfSuperClass {
	
	List<TypeDeclaration> classNodeList = new ArrayList<>();
	
	public NumOfSuperClass(List<TypeDeclaration> classNodeList){
		this.classNodeList = classNodeList;
	}
	
	public int calculate_NSC(TypeDeclaration node) {
		List<String> superClassList = new ArrayList<>();
		List<ITypeBinding> classBindingList = new ArrayList<>();
		int nsc = 0;
		
		for(TypeDeclaration classNode : classNodeList) {
			ITypeBinding classBinding = classNode.resolveBinding();
			classBindingList.add(classBinding);
		}
		
		ITypeBinding nodeBinding = node.resolveBinding();
		ITypeBinding superBinding = nodeBinding.getSuperclass();
		while(superBinding != null && !superClassList.contains(superBinding.getQualifiedName())) {
			for(ITypeBinding classNode : classBindingList) {
				if(classNode.getQualifiedName().equals(superBinding.getQualifiedName())) {
					superClassList.add(classNode.getQualifiedName());
					superBinding = classNode.getSuperclass();
					break;
				}
			}
			if(!superClassList.contains(superBinding.getQualifiedName())) {
				superClassList.add(superBinding.getQualifiedName());
				superBinding = superBinding.getSuperclass();
			}
		}
		nsc = superClassList.size();
		return nsc;
	}

}
