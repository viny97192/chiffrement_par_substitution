import java.io.*;
import java.text.Normalizer;

public class Decrypt {
	
	public String[] substring(String file, int key_length){

		String[] t = new String[key_length] ;
		String line;
		int i = 0;
		BufferedReader br;

		for(int k=0;k<key_length;k++)
			t[k] = "";

		try{
			br = new BufferedReader(new FileReader(new File(file)));	

			while((line = br.readLine()) != null){
				line = format(line);

				for(int j=0;j<line.length();j++){
					if(i == key_length)
						i = 0;
					t[i] += line.charAt(j);
					if(line.charAt(j) >= 97 && line.charAt(j) <= 122)
						i++;
				}
			}
		}

		catch(Exception e){
			e.printStackTrace();
		}

		return t;
	}

	public int[] statistic_analysis(String s){
		int[] lettres = new int[26];

		for(int i=0;i<s.length();i++)
			lettres[s.charAt(i)-97]++;

		return lettres;
	}

	public static String stripAccents(String s) {
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
	}

	public static String format(String s){
		//s = stripAccents(s.toLowerCase());
		return stripAccents(s).toLowerCase();
	}
}
