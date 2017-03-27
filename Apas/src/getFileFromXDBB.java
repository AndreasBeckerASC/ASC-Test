import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class getFileFromXDBB {
	
	public static void main(String[] args ) {
		
		String requestString = "<?xml version = \"1.0\" encoding = \"UTF-8\"?>" +
				               "   <MESSAGE DTD=\"XMLMSG\" VERSION=\"1.4.0.0\">" +
				               "      <COMMAND>" +
				               "         <REQUEST DTD=\"FileServer\"  NAME=\"GetFile\" VERSION=\"1.4.0.0\" ID=\"1\">" +
				               "            <PARAM NAME=\"DEALER\" VALUE=\"" + args[0] + "\"/>" +
				               "            <PARAM NAME=\"COUNTRY_CODE\" VALUE=\"" + args[1] + "\"/>" + 
	         				   "            <PARAM NAME=\"BRAND_ID\" VALUE=\"" + args[2] + "\"/>" +
	         				   "            <PARAM NAME=\"FILE_IDENT\" VALUE=\"" + args[3] + "\"/>" + 
	         				   "            <PARAM NAME=\"SERIAL\" VALUE=\"" + args[4] + "\"/>" +
	         	 			   "         </REQUEST>" +
					           "      </COMMAND>" + 
				               "   </MESSAGE>";
		
		URL url;
		try {
			url = new URL(args[5]);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        
	        conn.setConnectTimeout(15000);
	        conn.setReadTimeout(15000);
	        
	        conn.setDoOutput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-type", "text/xml;charset=UTF-8");
	        
	        PrintStream ps = new PrintStream(conn.getOutputStream(), false, "utf-8");
	        
	        ps.println(requestString);
	        ps.close();
	        conn.connect();
	        
	        if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
	        	
	        	String boundary = conn.getContentType();
	        	boundary = boundary.substring(boundary.indexOf("boundary=") + 9).replace("\"", "");
	        	
	        	String receiveFile = "/home/vw/xdbb/receive.xml";
	        	
	        	if (!args[6].contains("-1"))
	        		receiveFile = "/usr/asp" + args[6] + "/vw/xdbb/receive.xml";
	        	
	        	FileOutputStream fos = new FileOutputStream(receiveFile);
	        	
	        	byte[] buf = new byte[1024];
	        	int n = 0;
	        	while (-1!=(n=conn.getInputStream().read(buf))) {
	        		
	        		fos.write(buf, 0, n);
	        		
	        	}
	        	fos.close();
	        	conn.getInputStream().close();   
	        	
	        	BufferedReader in = null; 
	        	BufferedWriter out = null;
	        	
	        	String file = "/home/vw/xdbb/file.txt";
	        	
	        	if (!args[6].contains("-1"))
	        		file = "/usr/asp" + args[6] + "/vw/xdbb/file.txt";
	        	
	        	in = new BufferedReader(new FileReader(receiveFile)); 
	        	out = new BufferedWriter(new FileWriter(file));
	        	
	        	String zeile = null; 
	        	
	        	int bound = 0;
	        	
	            while ((zeile = in.readLine()) != null) { 
	            	
	            	if (zeile.contains(boundary))
	            		bound++;
	            	
	            	if (bound == 2) {
	            		
	            		if ( (!zeile.contains(boundary)) && (!zeile.contains("Content-Type:")) && (zeile.length() > 0) ) {
	            			out.write(zeile);
	            			out.write("\n");
	            		}
	            	}

	            } 
	            
	            in.close();
	            out.flush();
	            out.close();
	        	   
	        }
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
