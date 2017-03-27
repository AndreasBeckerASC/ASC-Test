import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;


public class leseOrt {

	 public static void main(String[] args) throws IOException {
		 
		 BufferedReader br = new BufferedReader(new FileReader(args[0].trim() + "/ort.bin"));
		 
		 FileWriter fstream = new FileWriter(args[0].trim() + "/ort.txt", false);
		 
		 BufferedWriter out = new BufferedWriter(fstream);
		 
		 //char[] cbuf = new char[(int) new File("/tmp/ort.bin").length()];
		 char[] cbuf = new char[4096];
		 String myString = null;
		 
		 int bytes = br.read(cbuf);
		 
		 string_Ende(cbuf);
		 
		 myString = new String(cbuf);

		 String dummy = myString.substring(2230);
		 
		 String zeile;
		 
		 while (bytes > 0) {
			 
			 zeile = dummy.substring((int)(dummy.indexOf("DE "))-2);
			 
			 out.write("OR" + zeile.charAt(5) + zeile.charAt(2) + zeile.charAt(3) + " " + zeile.charAt(0) + zeile.charAt(1) + zeile.substring(7, 7 + (int)(zeile.charAt(6))));
			 out.newLine();
			 		 
			 dummy = dummy.substring(dummy.indexOf("DE ")+1);
			if (dummy.indexOf("DE ") < 0) {
				
				bytes = br.read(cbuf);
				 
				string_Ende(cbuf);
				 
				myString = new String(cbuf);
				 
				dummy = dummy + myString;
				 
			 }
		 }
		 
		 out.flush();
		 out.close();
		 
		 
	}
	 
	 public static void string_Ende(char[] cbuf) {
		 
		 for (int i=0; i<cbuf.length; i++) {
			 if ( (cbuf[i] == (char)0x00) && (cbuf[i] != '$') )
				 cbuf[i] = ' ';
				 
		 }
	 }

}
