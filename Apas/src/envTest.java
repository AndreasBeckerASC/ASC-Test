
public class envTest {
	
	public static void main(String[] args){
		
		if (System.getProperty("os.name").equals("OS/400")) 
			System.out.println(System.getenv().get("HOME"));
		else
			System.out.println(System.getenv().get("TMP"));
		
		System.out.println(System.getenv().get("TMP"));
		
	}

}
