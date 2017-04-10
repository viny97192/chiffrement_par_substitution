/*
try{
	BufferedReader br = new BufferedReader(new FileReader(new File(file)));	
	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("encrypted_"+file)));
	String line;
	key %= 26;

	while((line = br.readLine()) != null){
		line = toLowerNoAccent(line);
		for(int i=0, l=line.length();i<l;i++){
			if((int)line.charAt(i) >= 97 && (int)line.charAt(i) <= 122)
				pw.print((char)((((((int)line.charAt(i)-97)+key)%26)+97)));	
			else
				pw.print(line.charAt(i));
		}	

		pw.flush();
		pw.print('\n');
		pw.flush();
	}

	br.close();
	pw.close();
}
catch(Exception e){
	e.printStackTrace();
}
*/
try{
	BufferedReader br = new BufferedReader(new FileReader(new File(file)));	
	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("decrypted_"+file)));
	String line;
	key %= 26;

	while((line = br.readLine()) != null){
		line = toLowerNoAccent(line);
		for(int i=0, l=line.length();i<l;i++){
			if((int)line.charAt(i) >= 97 && (int)line.charAt(i) <= 122)
				pw.print((char)((((((int)line.charAt(i)-97)-key)%26)+97)));	
			else
				pw.print(line.charAt(i));
		}	

		pw.flush();
		pw.print('\n');
		pw.flush();
	}

	br.close();
	pw.close();
}
catch(Exception e){
	e.printStackTrace();
}

static String toLowerNoAccent(String s){
	return s.toLowerCase().replace('à','a').replace('â','a').replace('ä','a').replace('é','e').replace('è','e').replace('ê','e').replace('ë','e').replace('î','i').replace('ï','i').replace('ô','o').replace('ö','o').replace('û','u').replace('ü','u').replace('ù','u').replace('ŷ','y').replace('ÿ','y').replace('ç','c');
}

