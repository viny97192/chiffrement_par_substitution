import java.io.*;
import java.text.Normalizer;
import java.util.*;

public class Decrypt {

	static Decalage d = new Decalage();
	static Permutation p = new Permutation();
	static Vigenere v = new Vigenere();

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

		HashMap<Character, Integer> map = new HashMap<Character, Integer>();

		if (cipher.charAt(0) == 'v') {
			int key_length = Integer.parseInt(args[2]);
			String[] substrings = substring(file, key_length);
			String key = "";
			char[] max_frequency = new char[substrings.length-1];

			for(int i=0;i<substrings.length-1;i++){
				/*
				System.out.println("substrings["+i+"] = "+substrings[i]+"\n");
				System.out.println("substrings["+i+"].length() = "+substrings[i].length()+"\n");
				*/

				character_frequency(map,substrings[i]);
				max_frequency[i] = get_max_frequency_character(map);
				System.out.println("caractère de fréquence max : "+max_frequency[i]);
				System.out.println("e-"+max_frequency[i]+" = "+Math.abs('e'-max_frequency[i]));
				/*
				System.out.println(map.toString()+"\n");
				*/
			}

			System.out.println("key = "+key);

			for(int i=0,l = max_frequency.length;i<l;i++)
				key += (char) (Math.abs('e'-max_frequency[i])+97);

			System.out.println("key = "+key);
			System.out.println(v.dechiffrer(substrings[key_length],key));
		}

		else{
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(file)));

				while ((line = br.readLine()) != null) {

					line = format(line);
					texteComplet += line + "\n";
					if (cipher.charAt(0) == 'c') {
						if (strat.charAt(0) == '1') {

						} else if (strat.charAt(0) == '2') {
							character_frequency(map, line);

						} else if (strat.charAt(0) == '3') {

						} else {
							continue;
						}
					} else if (cipher.charAt(0) == 'p') {

					} else {
						System.out.println("Méthode de chiffrement invalide");
					}
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		/*
		if (strat.charAt(0) == '2') {
			print_cipher(map, texteComplet);
		} else if (strat.charAt(0) == '3') {
			boolean word = false;
			int decal = 1, cpt = 0;
			int nbMots = countWords(texteComplet);
			String[] splited = texteComplet.split("\\W+");
			while (decal < 26) {
				for (int i = 0; i < splited.length; i++) {
					try {
						BufferedReader br = new BufferedReader(new FileReader(new File("liste_francais.txt")));
						word = false;
						while ((line = br.readLine()) != null) {
							line = format(line);
							String essai = d.dechiffrer(splited[i], decal);
							if (line.equals(essai)) {
								cpt++;
								word = true;
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (cpt > nbMots / 2) {
						System.out.println(d.dechiffrer(texteComplet, decal));
						return;
					}
				}
				System.out.println(decal);
				decal++;
			}
			System.out.println("decal = " + decal);
			// System.out.println(nbMots + " " + cpt);
		}
	*/
	}

	public static void character_frequency(HashMap<Character, Integer> map, String line) {
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
	}

	public static void print_cipher(HashMap<Character, Integer> map, String texteComplet) {
		// System.out.println(map.toString());
		char charMaxFreq = get_max_frequency_character(map);
		// System.out.println(charMaxFreq);
		charMaxFreq -= 97;
		if (charMaxFreq >= 5) {
			System.out.println(d.dechiffrer(texteComplet, charMaxFreq - 4));
		} else {
			System.out.println(d.dechiffrer(texteComplet, charMaxFreq + 22));
		}
	}

	public static char get_max_frequency_character(HashMap<Character, Integer> map) {
		char charMaxFreq;
		return charMaxFreq = Collections.max(map.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue())
				.getKey();
	}

	public static String[] substring(String file, int key_length) {

		String[] t = new String[key_length+1];
		String line;
		int i = 0;
		BufferedReader br;

		for (int k = 0; k < key_length+1; k++)
			t[k] = "";

		try {
			br = new BufferedReader(new FileReader(new File(file)));

			while ((line = br.readLine()) != null) {
				line = format(line);

				t[key_length] += line+"\n";

				for (int j = 0; j < line.length(); j++) {
					if (i == key_length)
						i = 0;
					if (line.charAt(j) >= 97 && line.charAt(j) <= 122){
						t[i] += line.charAt(j);
						i++;
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return t;
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

	public static int countWords(String s) {

		int wordCount = 0;

		boolean word = false;
		int endOfLine = s.length() - 1;

		for (int i = 0; i < s.length(); i++) {
			if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
				word = true;
			} else if (!Character.isLetter(s.charAt(i)) && word) {
				wordCount++;
				word = false;
			} else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
				wordCount++;
			}
		}
		return wordCount;
	}

}
