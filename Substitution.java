public interface Substitution {
	
	public char chiffrer(char c, int key);

	public char dechiffrer(char c, int key);

	public char chiffrer(char c, char key);

	public char dechiffrer(char c, char key);

	public String chiffrer(String s, int key);

	public String dechiffrer(String s, int key);

	public String chiffrer(String s, String key);

	public String dechiffrer(String s, String key);
}
