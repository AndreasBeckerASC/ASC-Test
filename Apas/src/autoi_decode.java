import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class autoi_decode
{
  public static void main(String[] args)
    throws Exception
  {
    File file = new File(args[1].trim() + "response.xml");
    
    SAXBuilder inputbuilder = new SAXBuilder();
    Document inputdoc = inputbuilder.build(file);
    
    byte[] decoded = Base64.decodeBase64(inputdoc.getRootElement().getValue());
    
    byte[] sessionKey = new byte[''];
    


    sessionKey = args[0].getBytes();
    
    byte[] iv = new byte[16];
    byte[] plaintext = new byte[decoded.length - 16];
    
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    
    System.arraycopy(decoded, 0, iv, 0, 16);
    System.arraycopy(decoded, 16, plaintext, 0, decoded.length - 16);
    
    cipher.init(2, new SecretKeySpec(sessionKey, "AES"), new IvParameterSpec(iv));
    
    SAXBuilder builder = new SAXBuilder();
    
    InputStream ausgabe = new ByteArrayInputStream(cipher.doFinal(plaintext));
    Document doc = builder.build(ausgabe);
    
    XMLOutputter fmt = new XMLOutputter(Format.getPrettyFormat());
    
    file = new File(args[1].trim() + "response.xml");
    
    FileOutputStream fout = new FileOutputStream(file);
    
    fmt.output(doc, fout);
  }
}
