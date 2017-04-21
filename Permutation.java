public class Permutation {
	
	public char chiffrer(char c, String key){
		return key.charAt(c-97);	
	}

	public char dechiffrer(char c, String key){
		return (char) (key.indexOf(c)+97);	
	}

	public String chiffrer(String s, String key){
		String s2 = "";

		for(int i=0, l=s.length();i<l;i++){
			if(s.charAt(i) >= 97 && s.charAt(i) <= 122)
				s2 += chiffrer(s.charAt(i),key);
			else
				s2 += s.charAt(i);
		}
		return s2;
	}

	public String dechiffrer(String s, String key){
		String s2 = "";

		for(int i=0, l=s.length();i<l;i++){
			if(s.charAt(i) >= 97 && s.charAt(i) <= 122)
				s2 += dechiffrer(s.charAt(i),key);
			else
				s2 += s.charAt(i);
		}
		return s2;
	}
}
