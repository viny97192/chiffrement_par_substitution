import java.io.*;
import java.text.Normalizer;

public class Chiffre {

	public static void main(String[] args){

		if(args.length < 3){
			System.out.println("Veuillez indiquer : \n1. Une méthode de chiffrement (c,p ou v)\n2. Une clé \n3. Un nom de fichier");
			return;
		}
		
		String file = args[2], cipher = args[0], key = args[1], line;
		Decalage d = new Decalage();
		Permutation p = new Permutation();
		Vigenere v = new Vigenere();
		

		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(file)));	

			while((line = br.readLine()) != null){
				
				line = format(line);

				if(cipher.charAt(0) == 'c'){
					System.out.println(d.chiffrer(line,Integer.parseInt(key)));
				}	
				else if(cipher.charAt(0) == 'p'){
					System.out.println(p.chiffrer(line,key));
				}
				else if(cipher.charAt(0) == 'v'){
					System.out.println(v.chiffrer(line,key));
				}
				else{
					System.out.println("Méthode de chiffrement invalide");
				}
			}
		}

		catch(Exception e){
			e.printStackTrace();
		}
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
