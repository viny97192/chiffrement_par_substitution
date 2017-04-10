public class Vigenere {
	
	public char chiffrer(char c, int key){
		return (char)((((((int)c-97)+key)%26)+97));	
	}

	public char dechiffrer(char c, int key){
		int n = ((int)c-97)-key;
		if(n < 0)
			return (char)(((26+n)%26)+97);	
		else
			return (char)((n%26)+97);
	}

	public String chiffrer(String s, String key){
		String s2 = "";

		for(int i=0,k=0,l=s.length();i<l;i++,k++){
			if(k == key.length())
				k = 0;
			s2 += chiffrer(s.charAt(i),((int)key.charAt(k))-97);
		}

		return s2;
	}

	public String dechiffrer(String s, String key){
		String s2 = "";

		for(int i=0,k=0,l=s.length();i<l;i++,k++){
			if(k == key.length())
				k = 0;
			s2 += dechiffrer(s.charAt(i),((int)key.charAt(k))-97);
		}

		return s2;
	}

}
