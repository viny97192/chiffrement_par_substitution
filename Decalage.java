import java.io.*;

public class Decalage {
	
	public char chiffrer(char c, int key){
		char c2 = 'a';
		if(c >= 97 && c <= 122)
			c2 = (char)((((((int)c-97)+key)%26)+97));	

		else 
			c2 = c;
	
		return c2;
	}

	public char dechiffrer(char c, int key){
		int n = ((int)c-97)-key;
		if(n < 0)
			return (char)(((26+n)%26)+97);	
		else
			return (char)((n%26)+97);
	}

	public String chiffrer(String s, int key){
		String s2 = "";

		for(int i=0, l=s.length();i<l;i++){
			if(s.charAt(i) >= 97 && s.charAt(i) <= 122)
				s2 += chiffrer(s.charAt(i),key);
			else
				s2 += s.charAt(i);
		}

		return s2;
	}

	public String dechiffrer(String s, int key){
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
