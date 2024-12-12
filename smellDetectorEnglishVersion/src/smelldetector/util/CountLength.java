package smelldetector.util;

public class CountLength {
	
	//此类用于获取字符串中换行字符的个数，str2代表子字符串
	private int codeLength=0;
	public int getCodeLength() {
			return codeLength;
	}
	public void setCodeLength(int codeLength) {
			this.codeLength = codeLength;			
	}
	public int countLength(String str1, String str2) {
			if(str1.indexOf(str2) == -1) {
				return 0;
			}else if (str1.indexOf(str2) != -1) {
					codeLength++;
					countLength(str1.substring(str1.indexOf(str2) + 
						str2.length()), str2);
					return codeLength;
			}
				return 0;
	}

}
