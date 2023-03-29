package smelldetector.metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import smelldetector.util.CollectionUtil;

public class NumOverrideMethods {
	
	List<TypeDeclaration> classNodes;
	
	public NumOverrideMethods(List<TypeDeclaration> classNodes) {
		this.classNodes = classNodes;
	}
	
	public int calculate_NORM(TypeDeclaration node) {
		List<ITypeBinding> classBindings = new ArrayList<>();
		List<ITypeBinding> superBindings = new ArrayList<>();
		int overrides = 0;
		float ratio = 0;
		if(CollectionUtil.isNotNullOrEmpty(classNodes)) {
			for(TypeDeclaration classNode : classNodes) {
				classBindings.add(classNode.resolveBinding());
			}
		}
		
		ITypeBinding baseClass = node.resolveBinding().getSuperclass();
		if(baseClass != null) {
			while(baseClass != null && !superBindings.contains(baseClass)) {
				for(ITypeBinding classBinding : classBindings) {
					if(classBinding.getQualifiedName().equals(baseClass.getQualifiedName())) {
						superBindings.add(classBinding);
						baseClass = classBinding.getSuperclass();
					}
				}
				if(!superBindings.contains(baseClass)) {
					superBindings.add(baseClass);
					baseClass = baseClass.getSuperclass();
				}
			}
			
			ITypeBinding child = node.resolveBinding();
			IMethodBinding[] childMethods = child.getDeclaredMethods();
			if(superBindings != null && childMethods != null) {
				for(IMethodBinding childMethod : childMethods) {
					for(ITypeBinding superBinding : superBindings) {
						IMethodBinding[] superClassMethods = superBinding.getDeclaredMethods();
						for(IMethodBinding superClassMethod : superClassMethods) {
							String childMethodSig = childMethod.getName();
							String superMethodSig = superClassMethod.getName();
//							if(!childMethod.isConstructor() && childMethod.overrides(superClassMethod)) {
//								overrides++;
//							}
							ITypeBinding[] childMethodParams = childMethod.getParameterTypes();
							ITypeBinding[] superMethodParams = superClassMethod.getParameterTypes();
							for(ITypeBinding childMethodParam : childMethodParams) {
								childMethodSig = childMethodSig + childMethodParam.getName();
							}
							for(ITypeBinding superMethodParam : superMethodParams) {
								superMethodSig = superMethodSig + superMethodParam.getName();
							}
							if(!childMethod.isConstructor() && childMethodSig.equals(superMethodSig)) {
								overrides++;
							}
						}
					}
				}
				ratio = overrides/(float)childMethods.length;
			}
		}		

		return overrides;
	}

}
