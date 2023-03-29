package smelldetector.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.jdt.core.dom.*;

import smelldetector.ast.visitor.*;

public class ASTUtil {
	
	//这个是获得特征标记串的String字符，并分成四部分子串用ParInfo[]保存，方便加权计算
		public static String ASTgetString2(CompilationUnit comp,String []ParInfo)
		{
			StringBuilder javaAST=new StringBuilder();//全部javaAST
			StringBuilder javaAST2=new StringBuilder();//头文件
			StringBuilder javaAST3=new StringBuilder();//内部类+field文件
			StringBuilder javaAST4=new StringBuilder();//method文件
			StringBuilder javaAST5=new StringBuilder();//内部语句
			
			// 获得headNode信息，唯一
			HeadInfoVisit hi = new HeadInfoVisit();
			comp.accept(hi);

			//MyHeadNode myHeadNode = new MyHeadNode(hi.packageNode, hi.importNodeList);
			javaAST.append("Pa "+ hi.packageNode.getName().toString()+" ");
			javaAST2.append("Pa "+ hi.packageNode.getName().toString()+" ");
			//javaAST.append("Package "+ myHeadNode.packageName.getName().toString()+" ");


			for (ImportDeclaration item :hi.importNodeList)
			{
				javaAST.append("Im " + ASTUtil.getLastName(item.getName().toString())+" ");
				javaAST2.append("Im " + ASTUtil.getLastName(item.getName().toString())+" ");
				//javaAST.append("Import " + item.getName().toString()+" ");

			}

			// 内部类
			// 获得所有typeNode的信息，不唯一
			TypeDeclarationVisit td = new TypeDeclarationVisit();
			comp.accept(td);
			TypeDeclaration classNode = td.getClassNode();// 获得主class

			TypeInfoVisitToDB ti = new TypeInfoVisitToDB();
			classNode.accept(ti);

			javaAST.append("ClN " + ti.name+" ");
			javaAST.append("Mo ");
			javaAST3.append("ClN " + ti.name+" ");
			javaAST3.append("Mo ");
			//javaAST.append("ClassName " + ti.name+" ");
			//javaAST.append("Modifiers ");
			for (Modifier item : ti.modifiers)
			{
				javaAST.append(item.getKeyword() + " ");
				javaAST3.append(item.getKeyword() + " ");
			}

			if (ti.superClass != null)
			{
				javaAST.append("SuC " + ti.superClass.toString()  + " ");
				javaAST3.append("SuC " + ti.superClass.toString()  + " ");
				//javaAST.append("SuperClass " + ti.superClass.toString()  + " ");
			}
				else {
					javaAST.append("SuC "+ ti.superClass  + " ");
					javaAST3.append("SuC "+ ti.superClass  + " ");
				}
				//javaAST.append("SuperClass "+ ti.superClass  + " ");
		
			javaAST.append("SuI ");
			javaAST3.append("SuI ");
			//javaAST.append("SuperInterfaces ");
			for (ASTNode item : ti.superInterface)
			{
				javaAST.append(item.toString() + " ");
				javaAST3.append(item.toString() + " ");
			}

			ti.setInnerClass(td.getOtherNode());

			javaAST.append("InC ");
			javaAST3.append("InC ");
			//javaAST.append("InnerClass ");
			for (TypeDeclaration item : ti.InnerClassList)
			{
				javaAST.append(item.getName().toString() + " ");
				javaAST3.append(item.getName().toString() + " ");
			}

			//Field
			for (FieldDeclaration item : ti.FieldList)
			{

				FieldInfoVisit fi = new FieldInfoVisit();
				item.accept(fi);

				javaAST.append("Fi ");
				javaAST.append("Ty " + fi.type.toString()+" ");
				javaAST.append("Mo ");
				javaAST3.append("Fi ");
				javaAST3.append("Ty " + fi.type.toString()+" ");
				javaAST3.append("Mo ");
				//javaAST.append("Field ");
				//javaAST.append("Type " + fi.type.toString()+" ");
				//javaAST.append("Modifiers ");
				
				for (Modifier item2 : fi.modifiers)
				{
					javaAST.append(item2.getKeyword() + " ");
					javaAST3.append(item2.getKeyword() + " ");
				}
				javaAST.append("Fr ");
				javaAST3.append("Fr ");
				//javaAST.append("Fragments ");
				for (VariableDeclarationFragment item2 : fi.fragemtns)
				{
					javaAST.append(item2.getName().toString() + "=");
					javaAST3.append(item2.getName().toString() + "=");
					String str = null;
					if (item2.getInitializer() != null)
					{
						str = item2.getInitializer().toString();
					}
					javaAST.append(str+" ");
					javaAST3.append(str+" ");
					}

			}

			// Method
			//遍历主类
			for (MethodDeclaration item : ti.methodList)
			{

				MethodInfoVisitToDB mi = new MethodInfoVisitToDB();
				item.accept(mi);

				javaAST.append("Me "+"Mo ");
				javaAST4.append("Me "+"Mo ");
				//javaAST.append("Method "+"Modifiers ");
				
				for (Modifier item2 : mi.modifiers)
				{
					javaAST.append(item2.toString() + " ");
					javaAST4.append(item2.toString() + " ");
				
				}

				javaAST.append("MeN " + mi.name+" ");
				javaAST.append("IsCt " + mi.constructor +" ");
				javaAST4.append("MeN " + mi.name+" ");
				javaAST4.append("IsCt " + mi.constructor +" ");
				//javaAST.append("MethodName " + mi.name+" ");
				//javaAST.append("IsConstructor " + mi.constructor +" ");

				// 可能返回null类型，这样使用不了toString方法
				if (mi.returnType != null)
				{
					javaAST.append("ReT " + mi.returnType.toString() +" ");
					javaAST4.append("ReT " + mi.returnType.toString() +" ");
					//javaAST.append("ReturnType " + mi.returnType.toString() +" ");
				}
				else
				{
					javaAST.append("ReT " + mi.returnType+" ");
					javaAST4.append("ReT " + mi.returnType+" ");
					//javaAST.append("ReturnType " + mi.returnType+" ");
				}
					
				javaAST.append("Par ");
				javaAST4.append("Par ");
				//javaAST.append("Parameters ");
				
				for (ASTNode item2 : mi.parameters)
				{
					javaAST.append(item2.toString()+" ");
					javaAST4.append(item2.toString()+" ");
				}
				
				
				//Statement
				//javaAST.append("Stm ");
				
				//javaAST5.delete(0, javaAST.length());//每次统计完都要进行清空
				javaAST5.append("Stm ");
				//javaAST.append("Statements ");
				
				for (Statement item2 : mi.statements)
				{
					//接受statemen遍历生成String,放在javaAST5里
					StatementVisitToString av= new StatementVisitToString(javaAST5);
					item2.accept(av);
					javaAST5=av.getAst();
				}	
				//System.out.print(javaAST5.length()+" ");
				//System.out.println(javaAST5);		
				
			}
			javaAST.append(javaAST5);	//生成好所有的javaAST5，再进行总的添加
			
			
			
			//System.out.println(javaAST.length());
			
			ParInfo[0]=javaAST2.toString();
			ParInfo[1]=javaAST3.toString();
			ParInfo[2]=javaAST4.toString();
			ParInfo[3]=javaAST5.toString();
		//	System.out.println("!!"+ParInfo[0].length()+" "+ParInfo[1].length()+" "+ParInfo[2].length()+" "+ParInfo[3].length());;
			System.out.println(javaAST.length());
			System.out.println(javaAST.toString());
			return javaAST.toString();
		}
		
		//这个是获得自定义AST类型的String字符，没有用parInfo保存
		public static String ASTgetString(CompilationUnit comp)
		{
			StringBuilder javaAST=new StringBuilder();//全部javaAST
			
			// 获得headNode信息，唯一
			HeadInfoVisit hi = new HeadInfoVisit();
			comp.accept(hi);

			javaAST.append("Pa "+ hi.packageNode.getName().toString()+" ");
			//javaAST.append("Package "+ hi.packageNode.getName().toString()+" ");


			for (ImportDeclaration item : hi.importNodeList)
			{
				javaAST.append("Im " + ASTUtil.getLastName(item.getName().toString())+" ");
					//javaAST.append("Import " + item.getName().toString()+" ");

			}

			// 内部类
			// 获得所有typeNode的信息，不唯一
			TypeDeclarationVisit td = new TypeDeclarationVisit();
			comp.accept(td);
			TypeDeclaration classNode = td.getClassNode();// 获得主class

			TypeInfoVisitToDB ti = new TypeInfoVisitToDB();
			classNode.accept(ti);

			javaAST.append("ClN " + ti.name+" ");
			javaAST.append("Mo ");
			//javaAST.append("ClassName " + ti.name+" ");
			//javaAST.append("Modifiers ");
			for (Modifier item : ti.modifiers)
			{
				javaAST.append(item.getKeyword() + " ");
			}


			if (ti.superClass != null)
			{
				javaAST.append("SuC " + ti.superClass.toString()  + " ");
					//javaAST.append("SuperClass " + ti.superClass.toString()  + " ");
			}
				else {
					javaAST.append("SuC "+ ti.superClass  + " ");
				}
				//javaAST.append("SuperClass "+ ti.superClass  + " ");
		
			javaAST.append("SuI ");
			//javaAST.append("SuperInterfaces ");
			for (ASTNode item : ti.superInterface)
			{
				javaAST.append(item.toString() + " ");
			}

			ti.setInnerClass(td.getOtherNode());

			javaAST.append("InC ");
			//javaAST.append("InnerClass ");
			for (TypeDeclaration item : ti.InnerClassList)
			{
				javaAST.append(item.getName().toString() + " ");
			}

			//Field
			for (FieldDeclaration item : ti.FieldList)
			{

				FieldInfoVisit fi = new FieldInfoVisit();
				item.accept(fi);

				javaAST.append("Fi ");
				javaAST.append("Ty " + fi.type.toString()+" ");
				javaAST.append("Mo ");
					//javaAST.append("Field ");
				//javaAST.append("Type " + fi.type.toString()+" ");
				//javaAST.append("Modifiers ");
				
				for (Modifier item2 : fi.modifiers)
				{
					javaAST.append(item2.getKeyword() + " ");
					}
				javaAST.append("Fr ");
					//javaAST.append("Fragments ");
				for (VariableDeclarationFragment item2 : fi.fragemtns)
				{
					javaAST.append(item2.getName().toString() + "=");
					String str = null;
					if (item2.getInitializer() != null)
					{
						str = item2.getInitializer().toString();
					}
					javaAST.append(str+" ");
					}

			}

			// Method
			//遍历主类
			for (MethodDeclaration item : ti.methodList)
			{

				MethodInfoVisitToDB mi = new MethodInfoVisitToDB();
				item.accept(mi);

				javaAST.append("Me "+"Mo ");
				//javaAST.append("Method "+"Modifiers ");
				
				for (Modifier item2 : mi.modifiers)
				{
					javaAST.append(item2.toString() + " ");
				
				}

				javaAST.append("MeN " + mi.name+" ");
				javaAST.append("IsCt " + mi.constructor +" ");
				//javaAST.append("MethodName " + mi.name+" ");
				//javaAST.append("IsConstructor " + mi.constructor +" ");

				// 可能返回null类型，这样使用不了toString方法
				if (mi.returnType != null)
				{
					javaAST.append("ReT " + mi.returnType.toString() +" ");
					//javaAST.append("ReturnType " + mi.returnType.toString() +" ");
				}
				else
				{
					javaAST.append("ReT " + mi.returnType+" ");
					//javaAST.append("ReturnType " + mi.returnType+" ");
				}
					
				javaAST.append("Par ");
				//javaAST.append("Parameters ");
				
				for (ASTNode item2 : mi.parameters)
				{
					javaAST.append(item2.toString()+" ");
				}
				
				//Statement
				javaAST.append("Stm ");
				//javaAST.append("Statements ");
			
				for (Statement item2 : mi.statements)
				{
					//接受statemen遍历生成String,放在javaAST5里
					StatementVisitToString av= new StatementVisitToString(javaAST);
					item2.accept(av);
					javaAST=av.getAst();
				}	
				
				}
			
				
			return javaAST.toString();
		}
		
		
		

		
		//将字符串的关键字转换，从而缩短字符长度
		public static String generateString(String str)
		{
			StringBuilder sb=new StringBuilder();
			
			String[] spaceString =str.split(" ");
			
			for(String item:spaceString)
			{
				sb.append(changeJAVAKeyWord(item)+" ");		
			}
			
			return sb.toString();
		}
		
		
		//自定义长度进行精简
		public static String changeJAVAKeyWord(String str)
		{
			String res=str;
			if(str.equals("public")) {res="pub";}
			else if(str.equals("boolean")) {res="boo";}
			else if(str.equals("break")) {res="bre";}
			else if(str.equals("byte")) {res="byt";}
			else if(str.equals("case")) {res="cas";}
			else if(str.equals("catch")) {res="cat";}
			else if(str.equals("class")) {res="cla";}
			else if(str.equals("const")) {res="con";}
			else if(str.equals("continue")) {res="cnt";}
			else if(str.equals("double")) {res="dou";}
			else if(str.equals("else")) {res="els";}
			else if(str.equals("enum")) {res="enu";}
			else if(str.equals("extends")) {res="ext";}
			else if(str.equals("final")) {res="fin";}
			else if(str.equals("finally")) {res="fil";}
			else if(str.equals("float")) {res="flo";}
			else if(str.equals("implements")) {res="imp";}
			else if(str.equals("omport")) {res="imo";}
			else if(str.equals("interface")) {res="inf";}
			else if(str.equals("long")) {res="lon";}
			else if(str.equals("private")) {res="pri";}
			else if(str.equals("protected")) {res="pro";}
			else if(str.equals("return")) {res="ret";}
			else if(str.equals("static")) {res="sta";}
			else if(str.equals("super")) {res="sup";}
			else if(str.equals("switch")) {res="swi";}
			else if(str.equals("synchronized")) {res="syn";}
			else if(str.equals("this")) {res="thi";}
			else if(str.equals("throw")) {res="thr";}
			else if(str.equals("throws")) {res="ths";}
			else if(str.equals("while")) {res="whi";}
			return res;
		}
		
		
		// 文件按大小读取字符流
		public static String readFileToString(String filePath) throws IOException
		{
			StringBuilder fileData = new StringBuilder(1000);
			// 字符流的形式读取
			BufferedReader reader = new BufferedReader(new FileReader(filePath));

			char[] buf = new char[512];
			int numRead = 0;

			while ((numRead = reader.read(buf)) != -1)
			{
				// System.out.println(numRead);
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
			}

			reader.close();
			return fileData.toString();
		}

		
		
	//获得字符串最后一个.之后的名字
		public static String getLastName(String str)
		{
			
			char a[]=str.toCharArray();
			int flag=0;
			for (int i = 0; i < str.length(); i++)
			{
				if(a[i]=='.') {flag=i+1;}
			}
			return str.substring(flag);
		}
		
		public static boolean judgeParent(ASTNode node,String str)
		{
			if(node.getParent()!=null)
			return getLastName(node.getParent().getClass().getName()).equals(str);
			return  false;
		}
		
		
		//xml的转换，因为输入到浏览器，有些字符要转移才能正确显示，例如'<'
		public static String changeCode(String str)
		{

			StringBuilder stb = new StringBuilder();
			if(str != null && str != ""){
				for (char a : str.toCharArray())
				{
					if (a == '<')
					{
						stb.append("&lt;");
					}
					else if (a == '>')
					{
						stb.append("&gt;");
					}
					else if (a == '&')
					{
						stb.append("&amp;");
					}
					else if (a == '\'')
					{
						stb.append("&apos;");
					}
					else if (a == '\"')
					{
						stb.append("&quot;");
					}
					else
						stb.append(a);
				}
				return stb.toString();
			}else{
				return "";
			}
			
		}

		//xml的转换，因为输入到浏览器，有些字符要转移才能正确显示，例如'<'
		public static String changeCode(String str,boolean flag)
		{
			StringBuilder stb = new StringBuilder();
			if(!flag) {
				if(str != null && str != ""){
					for (char a : str.toCharArray())
					{
						if (a == '<')
						{
							break;
						}					
						else
							stb.append(a);
					}
					return stb.toString();
				}else{
					return "";
				}
			}
			else {
				if(str != null && str != ""){
					for (char a : str.toCharArray())
					{
						if (a == '<')
						{
							stb.append("&lt;");
						}
						else if (a == '>')
						{
							stb.append("&gt;");
						}
						else if (a == '&')
						{
							stb.append("&amp;");
						}
						else if (a == '\'')
						{
							stb.append("&apos;");
						}
						else if (a == '\"')
						{
							stb.append("&quot;");
						}
						else
							stb.append(a);
					}
					return stb.toString();
				}else{
					return "";
				}
			}
			
			
		}

		
		//读取java源文件，生成AST，并且返回根节点作为编译单元
		public static CompilationUnit getCompilationUnit(String javaFilePath)
		{
			byte[] input = null;
			try
			{
				BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));
				input = new byte[bufferedInputStream.available()];
				bufferedInputStream.read(input);
				bufferedInputStream.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			ASTParser astParser = ASTParser.newParser(AST.JLS8);
			astParser.setKind(ASTParser.K_COMPILATION_UNIT);
			astParser.setResolveBindings(true);
			astParser.setBindingsRecovery(true);
			astParser.setStatementsRecovery(true);
			astParser.setSource(new String(input).toCharArray());
			astParser.setEnvironment(null, null, null, true);
			astParser.setUnitName("Program.java");			

			CompilationUnit result = (CompilationUnit) (astParser.createAST(null));

			return result;
		}
}
