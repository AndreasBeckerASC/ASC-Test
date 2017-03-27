import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;


public class leseZus {

	 public static void main(String[] args) throws IOException {
		 
		 BufferedReader br = new BufferedReader(new FileReader(args[0].trim() + "/zus.bin"));
		 
		 FileWriter fstream = new FileWriter(args[0].trim() + "/zus.txt", false);
		 
		 BufferedWriter out = new BufferedWriter(fstream);
		 
		 //char[] cbuf = new char[(int) new File("/tmp/ort.bin").length()];
		 char[] cbuf = new char[2048];
		 String myString = null;
		 
		 int bytes = br.read(cbuf);
		 
		 string_Ende(cbuf);
		 
		 myString = new String(cbuf);

		 String dummy = myString.substring(400);
		 String zeile;
		 int count;
		 
		 while ( (dummy.indexOf("DE ") > 2) && (bytes > 0) ){
			 
			zeile = dummy.substring((int)(dummy.indexOf("DE "))-2);
			
			if (zeile.charAt(6) == '@')
				count = 0;
			else
				count = (int)(zeile.charAt(6));
			
			out.write("ZD" + zeile.charAt(5) + zeile.charAt(2) + zeile.charAt(3) + " " + zeile.charAt(0) + zeile.charAt(1) + zeile.substring(7, 7 + count));
			out.newLine();
			out.flush();
			 		 
			dummy = dummy.substring(dummy.indexOf("DE ")+1);
			 
			if ( (dummy.indexOf("DE ") < 0) || (dummy.length() < 1024) ) {
				
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
				 cbuf[i] = '@';
				 
		 }
	 }

}
