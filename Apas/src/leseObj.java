import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;


public class leseObj {

	 public static void main(String[] args) throws IOException {
		 
		 BufferedReader br = new BufferedReader(new FileReader(args[0].trim() + "/objDE.bin"));
		 
		 FileWriter fstream = new FileWriter(args[0].trim() + "/obj.txt", false);
		 
		 BufferedWriter out = new BufferedWriter(fstream);
		 
		  char[] cbuf = new char[2048];
		 String myString = null;
		 
		 int bytes = br.read(cbuf);
		 
		 string_Ende(cbuf);
		 
		 myString = new String(cbuf);

		 String dummy = myString;
		 String zeile, ausgabe;
		 int count, count1;
		 
		 while ( (dummy.indexOf("DE ") > 7) && (bytes > 0) ){
			 
			zeile = dummy.substring((int)(dummy.indexOf("DE A"))-7);
			
			if (zeile.charAt(11) == '@')
				count = 0;
			else
				count = (int)(zeile.charAt(11));
			
			ausgabe = "OB" + zeile.charAt(10) + zeile.charAt(7) + zeile.charAt(8) + " " + zeile.substring(0,7) + zeile.substring(12, 12 + count);
			
			while (ausgabe.length() < 49)
				ausgabe += " ";
			
			count1 = count;
			
			if (zeile.charAt(12+count1) == '@')
				count = 0;
			else
				count = (int)(zeile.charAt(12+count1));
			
			ausgabe += zeile.substring(13+count1, 13+count+count1); 
			
			out.write(ausgabe);
			out.newLine();
			out.flush();
			 		 
			dummy = dummy.substring(dummy.indexOf("DE A")+1);
			 
			if ( (dummy.indexOf("DE A") < 0) || (dummy.length() < 1496) ) {
				
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
			 if ( (cbuf[i] == (char)0x00)  )
				 cbuf[i] = '@';
				 
		 }
	 }

}
