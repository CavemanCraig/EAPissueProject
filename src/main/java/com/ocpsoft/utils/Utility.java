package com.ocpsoft.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.jboss.forge.parser.JavaParser;
import org.jboss.forge.parser.java.JavaClass;
import org.jboss.forge.parser.java.Member;

public class Utility {
	private static final String useThisToVerifyTestCaseName = "() throws InterruptedException";
	
	public static boolean memberIsATestCase(Member<JavaClass, ?> member){
		if(member.toString().contains(useThisToVerifyTestCaseName)){
			return true;
		}
		return false;
	}
	
	public static Member<JavaClass, ?> getMemberFromTestCaseName(String testName, String className){
		File testClassFile = new File(Constants.ROOT_FILE_PATH + className + ".java");
		JavaClass testClass = null;
		try {
			testClass = (JavaClass) JavaParser.parse(testClassFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		List<Member<JavaClass, ?>> allMembers = testClass.getMembers();
		for (Member<JavaClass, ?> member : allMembers) {
			if(member.toString().contains(testName) && memberIsATestCase(member)){
				return member;
			}
		}
		return null;
	}
	
	public static String getMemberNameIfTestCase(Member<JavaClass, ?> member){
		if(memberIsATestCase(member)){
			return member.getName();
		}
		return null;
	}
	
	public static String[] getStepsFromMethod(Member<JavaClass, ?> member){
		if(member == null){
			return null;
		}
		String[] steps = member.getOrigin().getMethod(getMemberNameIfTestCase(member)).getBody().split(";");
		String[] returnArray = new String[ steps.length - 1 ];  //Last line is always just a newline
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = clearNewLinesOutOfString(steps[i]);
		}
		return returnArray;
	}
	
	public static String clearNewLinesOutOfString(String string){
		String returnVal = string.replaceAll("\n ", "");
		returnVal = returnVal.replaceAll("\n", "");
		if(returnVal.equals("")){
			return null;
		}
		return returnVal;
	}
	
}
