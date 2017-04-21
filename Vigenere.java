public class Vigenere {
	
	public char chiffrer(char c, char key){
		return (char)(((((c-97)+(key-97))%26)+97));	
	}

	public char dechiffrer(char c, int key){
		int n = c-key;
		if(n < 0)
			return (char)(((26+n)%26)+97);	
		else
			return (char)((n%26)+97);
	}

	public String chiffrer(String s, String key){
		String s2 = "";

		for(int i=0,k=0,l=s.length();i<l;i++){
			if(k == key.length())
				k = 0;
			if(s.charAt(i) >= 97 && s.charAt(i) <= 122){
				s2 += chiffrer(s.charAt(i),key.charAt(k));
				k++;
			}
			else
				s2 += s.charAt(i);
		}

		return s2;
	}

	public String dechiffrer(String s, String key){
		String s2 = "";

		for(int i=0,k=0,l=s.length();i<l;i++){
			if(k == key.length())
				k = 0;
			if(s.charAt(i) >= 97 && s.charAt(i) <= 122){
				s2 += dechiffrer(s.charAt(i),key.charAt(k));
				k++;
			}
			else
				s2 += s.charAt(i);
		}

		return s2;
	}

}
