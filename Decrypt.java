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

		HashMap<Character, Integer> map = initMap();

		if (cipher.charAt(0) == 'v') {
			int key_length = Integer.parseInt(args[2]), decalage;
			String[] substrings = substring(file, key_length);
			String key = "";
			char[] max_frequency = new char[key_length];

			/*
			for(int i=0;i<substrings.length-1;i++)
				System.out.println(d.dechiffrer(substrings[i],i)+"\n");
			*/

			for(int i=0;i<key_length;i++){
				character_frequency(map,substrings[i]);
				max_frequency[i] = get_max_frequency_character(map);
				System.out.println("max_frequency["+i+"] = "+max_frequency[i]);
				map.clear();
				map = initMap();
			}

			/*
			decalage = max_frequency[max_frequency.length-1] - 97;
			if(decalage >= 5) {
				decalage -= 4;
			} else {
				 decalage += 22;
			}

			System.out.println("decalage : "+decalage);
			key += (char) ((decalage%26)+97);
			System.out.println("key : "+key);
			*/

			for(int i=0;i<max_frequency.length;i++){
				decalage = max_frequency[i] - 97;
				if(decalage >= 5) {
					decalage -= 4;
				} else {
					 decalage += 22;
				}

				key += (char) ((decalage%26)+97);
			}

			System.out.println("key = "+key);
			System.out.println(v.dechiffrer(substrings[key_length],"abc"));
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
							if (args[3] == null)
								return;

						} else {
							continue;
						}
					} 

					/*
					 else if{
						System.out.println("Méthode de chiffrement invalide");
					}
					*/
				}

				if(cipher.charAt(0) == 'p'){
					String key;
					
					long startTime = System.currentTimeMillis();
					character_frequency(map,texteComplet);				
					map = sortByValue(map);
					key = generate_key(map);
					
					long endTime = System.currentTimeMillis();

					System.out.println("Temps de décryptage permutation : "+(endTime-startTime));

					System.out.println(p.dechiffrer(texteComplet,key));
				}
				
				br.close();
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		int nbMots = countWords(texteComplet);

		if (cipher.charAt(0) == 'c' && strat.charAt(0) == '2') {
			print_cipher(map, texteComplet);
		} else if (cipher.charAt(0) == 'c' && strat.charAt(0) == '3') {
			int decal = 1, cpt = 0;
			String[] splited = texteComplet.split("\\W+");
			while (decal < 26) {
				for (int i = 0; i < splited.length; i++) {
					try {
						BufferedReader br = new BufferedReader(new FileReader(new File("liste_francais.txt")));
						while ((line = br.readLine()) != null) {
							line = format(line);
							String essai = d.dechiffrer(splited[i], decal);
							if (line.equals(essai)) {
								cpt++;
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
		} else if (cipher.charAt(0) == 'c' && strat.charAt(0) == '1') {
			String motClair = args[3];
			ArrayList<String> tabMotsMemeLong = new ArrayList<String>();
			int longueur = motClair.length(), i = 0, decalage = 0;
			String[] splited = texteComplet.split("\\W+");
			for (int n = 0; n < splited.length; n++) {
				int l = splited[n].length();
				if (l == longueur) {
					tabMotsMemeLong.add(splited[n]);
				}
			}
			// MAINTENANT IL FAUT ESSAYER LES DECALAGES CORRESPONDANTS AUX MOTS
			// TROUVES SUR LE TEXTE CRYPTE JUSQU'A AVOIR LE BON
			for (int k = 0; k < tabMotsMemeLong.size(); k++) {
				int ascii1 = motClair.charAt(0);
				int ascii2 = tabMotsMemeLong.get(k).charAt(0);
				int cpt = 0;
				if (ascii1 < ascii2) {
					decalage = ascii2 - ascii1;
				} else {
					decalage = ascii2 - ascii1 + 26;
				}
				if(decalage == 26)
					decalage = 0;
				System.out.println(decalage);
				for (int m = 0; m < splited.length; m++) {
					try {
						BufferedReader br = new BufferedReader(new FileReader(new File("liste_francais.txt")));
						while ((line = br.readLine()) != null) {
							line = format(line);
							String essai = d.dechiffrer(splited[m], decalage);
							if (line.equals(essai)) {
								cpt++;
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (cpt > nbMots / 2) {
						System.out.println(d.dechiffrer(texteComplet, decalage));
						return;
					}
				}
			}
		}

	}

	public static void character_frequency(HashMap<Character, Integer> map, String line) {
		char c = 'a';
		Integer val;
			
		for (int i = 0; i < line.length(); i++) {
			c = line.charAt(i);
			if (c >= 97 && c <= 122) {
				val = map.get(new Character(c));
				map.put(c, new Integer(val + 1));
			}
		}
	}

	public static void print_cipher(HashMap<Character, Integer> map, String texteComplet) {
		char charMaxFreq = get_max_frequency_character(map);
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

		for (int k = 0; k < t.length; k++)
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
			br.close();
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

	public static HashMap<Character, Integer> initMap() {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		char c = 'a';

		while(c <= 122){
			map.put(c,0);
			c++;
		}

		return map;
	}

	public static HashMap<Character, Integer> sortByValue(HashMap<Character, Integer> unsortHashMap) {

		List<HashMap.Entry<Character, Integer>> list =
			new LinkedList<HashMap.Entry<Character, Integer>>(unsortHashMap.entrySet());

		Collections.sort(list, new Comparator<HashMap.Entry<Character, Integer>>() {
		    public int compare(HashMap.Entry<Character, Integer> o1,
				       HashMap.Entry<Character, Integer> o2) {
			return (o1.getValue()).compareTo(o2.getValue());
		    }
		});

		HashMap<Character, Integer> sortedMap = new LinkedHashMap<Character, Integer>();
		for (HashMap.Entry<Character, Integer> entry : list) {
		    sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public static String generate_key(HashMap<Character,Integer> map){
		ArrayList<Character> keys = new ArrayList<Character>(map.keySet());
		String key = "";
		int[] indexes = {24,10,14,15,25,7,11,8,23,3,2,17,13,21,18,12,6,20,22,19,16,9,1,4,5,0};

		for(int i=0;i<indexes.length;i++)
			key += keys.get(indexes[i]);
		/*
		key += keys.get(24);
		key += keys.get(10);
		key += keys.get(14);
		key += keys.get(15);
		key += keys.get(25);
		key += keys.get(7);
		key += keys.get(11);
		key += keys.get(8);
		key += keys.get(23);
		key += keys.get(3);
		key += keys.get(2);
		key += keys.get(17);
		key += keys.get(13);
		key += keys.get(21);
		key += keys.get(18);
		key += keys.get(12);
		key += keys.get(6);
		key += keys.get(20);
		key += keys.get(22);
		key += keys.get(19);
		key += keys.get(16);
		key += keys.get(9);
		key += keys.get(1);
		key += keys.get(4);
		key += keys.get(5);
		key += keys.get(0);
		*/

		return key;
	}

	public int[] statistic_analysis(String s){
		int[] t = new int[26];

		for(int i=0;i<s.length();i++)
			t[s.charAt(i)-97]++;
		
		return t;
	}

	public char get_max(int[] t){
		int max = t[0], index = 0;

		for(int i=1;i<t.length;i++){
			if(max < t[i]){
				max = t[i];
				index = i;
			}
		}

		return (char) (index+97);
	}
}
