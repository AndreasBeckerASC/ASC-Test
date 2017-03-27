import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;


public class leseTat {

	 public static void main(String[] args) throws IOException {
		 
		 BufferedReader br = new BufferedReader(new FileReader(args[0].trim() + "/tat.bin"));
		 
		 FileWriter fstream = new FileWriter(args[0].trim() + "/tat.txt", false);
		 
		 BufferedWriter out = new BufferedWriter(fstream);
		 
		  char[] cbuf = new char[2048];
		 String myString = null;
		 
		 int bytes = br.read(cbuf);
		 
		 string_Ende(cbuf);
		 
		 myString = new String(cbuf);

		 String dummy = myString.substring(100);
		 String zeile;
		 int count;
		 
		 while ( (dummy.indexOf("DE ") > 3) && (bytes > 0) ){
			 
			zeile = dummy.substring((int)(dummy.indexOf("DE A"))-3);
			
			if (zeile.charAt(6) == '@')
				count = 0;
			else
				count = (int)(zeile.charAt(7));
			
			out.write("AT" + zeile.charAt(6) + zeile.charAt(3) + zeile.charAt(4) + " " + zeile.charAt(0) + zeile.charAt(1) + zeile.charAt(2) + zeile.substring(8, 8 + count));
			out.newLine();
			out.flush();
			 		 
			dummy = dummy.substring(dummy.indexOf("DE A")+1);
			 
			if ( (dummy.indexOf("DE A") < 0) || (dummy.length() < 1024) ) {
				
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
