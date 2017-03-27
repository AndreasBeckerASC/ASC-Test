import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.CharacterDataArea;
import com.ibm.as400.access.CommandCall;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.QSYSObjectPathName;


public class PartsEye_Bestellung {

	public static void main(String[] args) throws Exception {
		
		String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
	    String URL = "jdbc:as400://localhost;naming=system;libraries=";
	    
	    StringTokenizer strTok = new StringTokenizer(args[0]);
		while (strTok.hasMoreTokens()) {
	    	URL += strTok.nextToken().toString().trim() + " ";
	    }
	    
	    Class.forName(DRIVER);
	    Connection  conn = DriverManager.getConnection(URL);
	      
	    Statement stmt = conn.createStatement();
	    
	    ResultSet rs = stmt.executeQuery("select IIFACD, IIFICD, IICKCE, IICLCE, IICMCE, IIJFTY from RRIICPP where upper(IIJFTY) like '%.PAR.RIM.%' and IISNST = 'REN'");
	    
	    AS400 as400 = new AS400();
		CommandCall command = new CommandCall(as400);
		 
		strTok = new StringTokenizer(args[0]);
		while (strTok.hasMoreTokens())
			command.run("ADDLIBLE LIB(" + strTok.nextToken().toString().trim() + ")");
		
		QSYSObjectPathName path = new QSYSObjectPathName("*LIBL", "ASPINF", "DTAARA");
		CharacterDataArea dataArea = new CharacterDataArea(as400, path.getPath());
		 
	    while (rs.next()) {
	    	
	    	String compareString = rs.getString("IIJFTY").trim().toUpperCase();
	    	
	    	File folder = null;
	    	
	    	try {
	    		folder = new File(dataArea.read().substring(200, 210) + "/filiale/in/");
	    	}
	    	catch (ObjectDoesNotExistException e) {
	    		folder = new File("/usr/filiale/in/");
	    	}

			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				if ( file.isFile() && file.getName().trim().toUpperCase().contains(compareString)) {
					
					// RIM Datei gefunden
			        String generation = file.getName().substring(27);
			        String befehl = "SBMJOB CMD(call CRIM02CL ('" + rs.getString("IIFACD") + "' '" + rs.getString("IIFICD") + "' '" + rs.getString("IICKCE") + "' '" + rs.getString("IICLCE") + "' '" + generation + "' '" + file.getName().trim() + "')) JOBD(*LIBL/PARRIM) INLLIBL(*JOBD) ";
			        command.run(befehl);
			         
			    }
			 }
	    }
	   
	}
}
