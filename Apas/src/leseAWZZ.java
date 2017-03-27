import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;


public class leseAWZZ {

	 public static void main(String[] args) throws IOException {
		 
		 BufferedReader br = new BufferedReader(new FileReader(args[0].trim() + "/awwz.bin"));
		 
		 FileWriter fstream = new FileWriter(args[0].trim() + "/awwz.txt", false);
		 
		 BufferedWriter out = new BufferedWriter(fstream);
		 
		 char[] cbuf = new char[35];
		 
		 try {
		    int bytes = br.read(cbuf);

		     while (bytes > 0) {
		    	 String dummy = new String(cbuf);
		         out.write("WZ" + cbuf[20] + dummy.substring(2, 13) + cbuf[34] + dummy.substring(13, 20) + dummy.substring(21, 28) + cbuf[28] + cbuf[29] + dummy.substring(30, 34));
		         out.newLine();
		         bytes = br.read(cbuf);
		     }
		     
		 } finally {
		     br.close();
		 }
		 
		 out.flush();
		 out.close();
		 fstream.close();
		 
	}

}
