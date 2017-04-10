public class Permutation {
	
	public String chiffrer(String s, char[] t){
		String s2 = "";

		if(t.length != 26)
			return "Taille de la permutation non conforme";
			
		for(int i=0,l=s.length();i<l;i++)
			s2 += t[((int)s.charAt(i)-97)];

		return s2;
	}
}
