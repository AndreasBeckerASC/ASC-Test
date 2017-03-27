import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.asc_ag.toolkit.base.db.JDBCConnection;
import com.asc_ag.toolkit.base.log.Log;
import com.asc_ag.toolkit.base.log.LogManager;
import com.asc_ag.toolkit.base.server.I5Starter;



public class splitt {
	
	public static I5Starter starter;
	public static String repcusStr;
	
	public static void main(String[] args) {
		
		System.out.println("hello");
		
	
//		byte[] vector =  { (byte)44, (byte)64, (byte)211, (byte)99, (byte)57, (byte)6, (byte)115, (byte)210, (byte)254, (byte)65, (byte)84, (byte)35, (byte)15, (byte)225, (byte)123, (byte)156 };
//		
//		byte[] key = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
//		
//		System.out.println(encrypt(key, vector, "hello world"));
//		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		System.out.println(sdf.format(timestamp));
//		init();
//		
//		String sql = "select * FROM system_value_info WHERE SYSTEM_VALUE_NAME = 'QSRLNBR'";
//		
//		ResultSet rs;
//		
//		try {
//			rs = SQLStatementCache.executeQuery(sql);
//			
//			if (rs.next())
//				System.out.println(rs.getNString("CURRENT_CHARACTER_VALUE").trim());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		CharacterDataArea dtaa = new CharacterDataArea(starter.getConnection(), "/QSYS.LIB/" + repcusStr + ".LIB/ASPINF.DTAARA");
//		
//		try {
//			System.out.println(dtaa.read().substring(3, 5));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			System.out.println("kein ASP: " + repcusStr);
//		}
//		
//		JDBCConnection.closeConnection();
//		starter.closeConnection();
//		
	}
	
	public static void init() {
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("./obclient.properties"));
			if (!LogManager.getInstance().isInitialized())
		    	LogManager.getInstance().init("DCOM", "DCOMUSER", properties);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String user = properties.getProperty("Environment.Default.User", "").trim();
		String password = properties.getProperty("Environment.Default.Password", "").trim();
		String libraries = properties.getProperty("Environment.Default.DataSource", "").trim().split(";")[1].split("=")[1];
		String server = properties.getProperty("Environment.Default.DataSource", "").split("//")[1].split(";")[0];
 
		try {
			JDBCConnection.createConnection(server, libraries, user, password);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LogManager.getInstance().write(new Log(e1));
		}
		
		starter = new I5Starter(properties);
		
		starter.createConnection(properties.getProperty("Environment.Default.User", "").trim(), 
				properties.getProperty("Environment.Default.Password", "").trim()); 
		
		if (!starter.addLibraries()) {
			LogManager.getInstance().write(new Log("debug", "Fehler bei addLibraries"));
		}
		
		if (libraries.contains(","))
			repcusStr = libraries.substring(libraries.indexOf("REPCUS"), libraries.indexOf(",", libraries.indexOf("REPCUS")));
		else
			repcusStr = libraries.substring(libraries.indexOf("REPCUS"), libraries.indexOf(" ", libraries.indexOf("REPCUS")));
		
		
	}

	 public static String encrypt(byte[] key, byte[] initVector, String value) {
	        try {
	            IvParameterSpec iv = new IvParameterSpec(initVector);
	            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

	            byte[] encrypted = cipher.doFinal(value.getBytes());
	            
	            return Base64.encodeBase64String(encrypted);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }


}
