import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;

public class PersistantLineManager {

	public static void main(String[] args) {
		
		MultiThreadedHttpConnectionManager connectionManager = 
	      		new MultiThreadedHttpConnectionManager();
	    
		Protocol.registerProtocol("https", new Protocol("https", new MySSLSocketFactory(), 443));
		
		HttpClient client = new HttpClient(connectionManager);
		
		GetMethod  httpget = new GetMethod("https://www.motiveintegrator.com/STARCDS/services/Server?action=disconnect&version=1.4&id=G0815");
        
		try {
            try {
				client.executeMethod(httpget);
				InputStream in = httpget.getResponseBodyAsStream();
				
				byte[] buffer = new byte[10000];
				int len ;
				while ((len = in.read(buffer)) > 0) {
				       System.out.println(new String(buffer).substring(0, len));
				}
				
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // print response to stdout
            
        } finally {
            // be sure the connection is released back to the connection 
            // manager
        	httpget.releaseConnection();
        }
    

    }
}
