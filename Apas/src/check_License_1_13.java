
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;



public class check_License_1_13
{
  public static void main(String[] args)
  {
    String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
    String URL = "jdbc:as400://localhost;naming=system;libraries=" + args[5].trim();
    String catalog_name = "";
    String lizenz = "";
    String valid_To = "2991231";
   
    Connection conn = null;
    try
    {
      Class.forName(DRIVER);
      conn = DriverManager.getConnection(URL);
      
      Statement stmt = conn.createStatement();
      
      ResultSet rs = stmt.executeQuery("SELECT * FROM syscatalogs WHERE CATALOG_TYPE = 'LOCAL'");
      if (rs.next()) {
        catalog_name = rs.getString("CATALOG_NAME");
      }
      
      String selectStr = "SELECT * FROM RRNKCPP where NKFBCD='" + args[2].trim() + "' and NKNSCF='" + args[3].trim() + "' and NKFACD='" + args[0].trim() + "' and NKFICD='" + args[1].trim() + "' and NKOPCF='" + args[4].trim() + "'";
      
      rs = stmt.executeQuery(selectStr);
      if (rs.next())
      {
        lizenz = rs.getString("NKL6TT");
        valid_To = rs.getString("NKCYDT");
        if (valid_To.length() == 6) {
          valid_To = "1" + valid_To;
        }
      }
      String code = ek_verschluesseln(catalog_name.trim() + args[3].trim() + args[2].trim() + valid_To.trim() + args[0].trim() + args[1].trim() + args[4].trim());
      
      rs.close();
      stmt.close();
      conn.close();
      
      DateFormat dateFormat = new SimpleDateFormat("1yyMMdd");
      Calendar currentDateTime = GregorianCalendar.getInstance();
      
      if (new Integer(valid_To).intValue() <= new Integer(dateFormat.format(currentDateTime.getTime())).intValue()) {
        System.exit(4);
      } else if (code.equals(lizenz.trim())) {
    	  System.exit(5);
      } else {
    	  System.exit(6);
      }
    }
    catch (Exception e)
    {
    	System.exit(6);
    }
  }
  
  public static String ek_verschluesseln(String str)
  {
    byte[] sessionKey = new byte[''];
    sessionKey = new String("Bf9kLp74dG1sxTaC").getBytes();
    byte[] verschluesselt = null;
    byte[] linebreak = new byte[0];
    Base64 coder = new Base64(32, linebreak, true);
    
    Cipher cipher = null;
    try
    {
      cipher = Cipher.getInstance("AES");
      cipher.init(1, new SecretKeySpec(sessionKey, "AES"));
      verschluesselt = cipher.doFinal(str.getBytes());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return new String(coder.encode(verschluesselt));
  }
  
}
