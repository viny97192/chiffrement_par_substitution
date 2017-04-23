import java.io.*;
import java.text.Normalizer;
import java.util.*;

public class Decrypt {

	public static void main(String[] args) {

		if (args.length < 2) {
			System.out
					.println("Veuillez indiquer : \n1. Une méthode de chiffrement (c,p ou v)\n2. Une nom de fichier \n"
							+ "3. Si César, une stratégie (si strat 1 un 4e param étant le mot en clair)");
			return;
		}

		String file = args[1], cipher = args[0], line, strat = "", texteComplet = "";
		if (args.length > 2)
			strat = args[2];

		Decalage d = new Decalage();
		Permutation p = new Permutation();
		Vigenere v = new Vigenere();
		Dechiffre de = new Dechiffre();

		HashMap<Character, Integer> map = new HashMap<Character, Integer>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(file)));

			while ((line = br.readLine()) != null) {

				line = format(line);
				texteComplet += line + "\n";
				if (cipher.charAt(0) == 'c') {
					if (strat.charAt(0) == '1') {

					} else if (strat.charAt(0) == '2') {
						for (int i = 0; i < line.length(); i++) {
							char c = line.charAt(i);
							if (c >= 97 && c <= 122) {
								Integer val = map.get(new Character(c));
								if (val != null) {
									map.put(c, new Integer(val + 1));
								} else {
									map.put(c, 1);
								}
							}
						}

					} else if (strat.charAt(0) == '3') {

					} else {
						return;
					}
				} else if (cipher.charAt(0) == 'p') {

				} else if (cipher.charAt(0) == 'v') {

				} else {
					System.out.println("Méthode de chiffrement invalide");
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		
		if (strat.charAt(0) == '2') {
			//System.out.println(map.toString());
			char charMaxFreq = Collections.max(map.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
			//System.out.println(charMaxFreq);
			charMaxFreq -= 97;
			if(charMaxFreq >= 5){
				System.out.println(d.dechiffrer(texteComplet, charMaxFreq - 4));
			} else {
				int i = charMaxFreq;
				System.out.println(i);
				System.out.println(d.dechiffrer(texteComplet, charMaxFreq + 22));
			}
		}

	}

	public static String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}

	public static String format(String s) {
		// s = stripAccents(s.toLowerCase());
		return stripAccents(s).toLowerCase();
	}
}
