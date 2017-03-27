import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;


public class leseAWWZ {

	 public static void main(String[] args) throws IOException {
		 
		 BufferedReader br = new BufferedReader(new FileReader(args[0].trim() + "/awzz.bin"));
		 
		 FileWriter fstream = new FileWriter(args[0].trim() + "/awzz.txt", false);
		 
		 BufferedWriter out = new BufferedWriter(fstream);
		 
		 char[] cbuf = new char[128];
		 char[] ch = new char[1];
		 int i=0;
		 int count = 0;
		 String myString = null;
		 try {
			 
//			 while (count < 128) {
//				br.read(ch);
//				cbuf[count] = ch[0];
//				count++;
//			 }
		   int bytes = br.read(cbuf);
		    
		    string_Ende(cbuf);
		    myString = new String(cbuf);
		    String dummy, zeile;
		    
		     while (bytes > 0) {
		    	 
		    	 if ( (myString.length() < 15) || (!myString.substring(15).contains(" * ")) ) {
		    		 bytes = br.read(cbuf);
		    		 string_Ende(cbuf);
		    		 myString += new String(cbuf);
		    	 }
		    	 
		    	 dummy = myString.substring(0, myString.substring(8).indexOf(" * ")+1).trim();
		    	 //System.out.println(dummy);
//		    	 out.write(myString);
//		         out.newLine();
//		         out.write(dummy);
//		         out.newLine();
//		         out.write(dummy.length());
//		         out.newLine();
		         
		         while (dummy.length() < 25)
		        	 dummy += " ";
		         
		         if (dummy.length() > 39) {
		        	 if (dummy.charAt(38) == '@') {
		        		 count=0;
		        	 }
		        	 else
		        		 count = (int)dummy.charAt(38);
		         }
		         
		         if ( (dummy.length() > 39) && (count > 0)) {
		        	 zeile = "TX" + dummy.charAt(20) + dummy.substring(22, 24) + " " + dummy.substring(2, 13)  + dummy.charAt(21) + dummy.substring(13, 20) + dummy.substring(25, 38) + dummy.substring(39, 39 + count);
		         } else
		        	 zeile = "TX" + dummy.charAt(20) + dummy.substring(22, 24) + " " + dummy.substring(2, 13)  + dummy.charAt(21) + dummy.substring(13, 20) + dummy.substring(25);
		         
		         while (zeile.length() < 74)
		        	 zeile += " ";
		         
		         if (dummy.length() > 39)
		        	 zeile += dummy.substring(39 + count);
		         
		         out.write(zeile.replaceAll("@", " "));
		         out.newLine();
		         
		         myString = myString.substring(myString.substring(8).indexOf(" * ") + 1);
		        
		         if ( (myString.length() < 15) || (!myString.substring(15).contains(" * ")) ) {
		        	 bytes = br.read(cbuf);
		    		 string_Ende(cbuf);
		    		 myString += new String(cbuf);
		    	 }
		     }
		 } finally {
		     br.close();
		     out.flush();
			 out.close();
			 fstream.close();
		 }
		 
		 
	}
	 
	 public static void string_Ende(char[] cbuf) {
		 
		 for (int i=0; i<cbuf.length; i++) {
			 if ( (cbuf[i] == (char)0x00) && (cbuf[i] != '$') )
				 cbuf[i] = '@';
				 
		 }
	 }

}
