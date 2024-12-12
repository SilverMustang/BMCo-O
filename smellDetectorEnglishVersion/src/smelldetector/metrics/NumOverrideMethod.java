package smelldetector.metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class NumOverrideMethod {
	
	public int calculate_NORM(TypeDeclaration node) {
		int overridden = 0;
		List<ITypeBinding> supers = new ArrayList<>();
		ITypeBinding baseClass = (ITypeBinding) node.resolveBinding();
		supers = findAllSuperClass(baseClass, supers);
		try {
			IMethodBinding[] baseClassMethods = baseClass.getDeclaredMethods();
			List<IMethodBinding> counted = new ArrayList<>();
			for(IMethodBinding myMethod : baseClassMethods) {
				overridden = countMethods(supers, overridden, counted, myMethod);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return overridden;
	}

	private int countMethods(List<ITypeBinding> supers, int overridden, List<IMethodBinding> counted,
			IMethodBinding myMethod) {
		// TODO Auto-generated method stub
		int totalOverridden = overridden;
		for(ITypeBinding superClass : supers) {
			IMethodBinding[] inheritedMethods = superClass.getDeclaredMethods();
			for(IMethodBinding inheritedMethod : inheritedMethods) {
				if (counted.contains(myMethod)) {
					continue;
				}
				IMethodBinding inherited = inheritedMethod;
				int inheritedModifier = inherited.getModifiers();
				if(inherited.isConstructor()) {
					continue;
				}
				// don't count abstract methods unless preferences dictate it
				if(Modifier.isAbstract(inheritedModifier)) {
					continue;
				}
//				if(myMethod.overrides(inherited)) {
//					totalOverridden++;
//					counted.add(myMethod);
//				}
				if(isSimilar(myMethod, inherited)) {
					totalOverridden++;
					counted.add(myMethod);
				}
			}
		}
		return totalOverridden;
	}

	private List<ITypeBinding> findAllSuperClass(ITypeBinding baseClass, List<ITypeBinding> supers) {
		// TODO Auto-generated method stub
		ITypeBinding superClass = baseClass.getSuperclass();
		if(superClass!=null) {
			try {
				supers.add(superClass);
				supers = findAllSuperClass(superClass, supers);
			}catch(Exception e) {
				System.out.println(e);
			}			
		}
		return supers;
	}
	
	private boolean isSimilar(IMethodBinding baseMethod, IMethodBinding superMethod) {
		String baseMethodName = baseMethod.getName();
		String superMethodName = superMethod.getName();
		ITypeBinding[] baseMethodPara = baseMethod.getParameterTypes();
		ITypeBinding[] superMethodPara = superMethod.getParameterTypes();
		if(baseMethodName.equals(superMethodName) && Arrays.equals(baseMethodPara, superMethodPara)) {
			return true;
		}else {
			return false;
		}
		
	}

}
