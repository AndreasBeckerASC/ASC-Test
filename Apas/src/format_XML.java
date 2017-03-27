import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class format_XML {
	
	public static void main(String[] args) throws IOException, Exception {
		
		InputStream is = new FileInputStream(args[0]);
		
	    if (is.available() <= 0) {
	    	is.close();
	    	System.exit(0);
	    }
    
	    Source xmlInput = new StreamSource(is);
	    
	    File file;
		FileWriter writer;
		
		StringWriter stringWriter = new StringWriter();
	    StreamResult xmlOutput = new StreamResult(stringWriter);
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    transformerFactory.setAttribute("indent-number", 2);
	    Transformer transformer = transformerFactory.newTransformer(); 
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.transform(xmlInput, xmlOutput);
	    
	    is.close();
	    
	    file = new File(args[0]);
		writer = new FileWriter(file);
		
	    writer.write(xmlOutput.getWriter().toString());
	     
	    writer.flush();
		writer.close();
    
    
  }
}
