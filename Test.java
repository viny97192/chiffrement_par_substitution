import java.text.Normalizer;

public class Test {
	
	public static void main(String[] args){
		

		char c = 97;

		System.out.println(c);
		c += 5;
		System.out.println(c);


		/*
		String line;
		int cle;

		if(argv.length < 3){
			System.out.println("Veuillez indiquer un nom de fichier et une clé");
			return;
		}
		
		cle = Integer.parseInt(argv[1]);

		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(argv[0])));	
			while((line = br.readLine()) != null){
				System.out.print(chiffrer(line,cle));
			}
		
			br.close();
		}

		catch(Exception e){
			e.printStackTrace();
		}
		Permutation p = new Permutation();
		String s, s2;
		char[] t = {'d','l','r','y','v','o','h','e','z','x','w','p','t','b','g','f','j','q','n','m','u','s','k','a','c','i',};

		s = p.chiffrer("mgzvyzlghcmhjmyxssfmnhahycdlmha",t);
		System.out.println(s);
		*/
	}

	public static String stripAccents(String s) {
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
	};
}
