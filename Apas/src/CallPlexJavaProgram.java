
public class CallPlexJavaProgram {

		public static void main(String[] args)
	    {
			int anzahl = new Integer(args[1]);
	        String[] ParmsOut = new String[anzahl+2];
	        String FunctionName = args[0];
	        String ProfilePath = "";
	        String EnvironmentName = "Default";
	        
	        String[] ParmsIn = new String[anzahl+1];
	        
	        
	        if (args[2].equals("-1")) 
	        	ProfilePath = "/home/dc/gala/.";
	        else
	        	ProfilePath = "/usr/asp" + args[2].toLowerCase() + "/dc/gala/.";
	        
	        for (int i=0;i<anzahl;i++) 
	        	ParmsIn[i] = args[i+2];
	        
	        System.out.println(FunctionName + "," + ProfilePath + "," + EnvironmentName + "," + anzahl);
	        
	        ParmsOut = ObRun.ObPanel.ObLaunch.callFunction(FunctionName, ProfilePath, ParmsIn, EnvironmentName);
	        
	        if (ParmsOut[anzahl+1].equals("*OK"))
	        	System.exit(2);
	        else if (ParmsOut[anzahl+1].equals("*OKRM"))
	        	System.exit(3);
	        else if (ParmsOut[anzahl+1].equals("*SST"))
	        	System.exit(4);
	        else if (ParmsOut[anzahl+1].equals("*URL"))
	        	System.exit(5);
	        else if (ParmsOut[anzahl+1].equals("*USER"))
	        	System.exit(6);
	        else if (ParmsOut[anzahl+1].equals("*PW"))
	        	System.exit(7);
	        else if (ParmsOut[anzahl+1].equals("*LIC"))
	        	System.exit(8);
	        else if (ParmsOut[anzahl+1].equals("*ERR"))
	        	System.exit(9);
	        else if (ParmsOut[anzahl+1].equals("*HDL"))
	        	System.exit(10);
	        else if (ParmsOut[anzahl+1].equals("*REQ"))
	        	System.exit(11);
	        else if (ParmsOut[anzahl+1].equals("*RES"))
	        	System.exit(12);
	        else if (ParmsOut[anzahl+1].equals("*WEBS"))
	        	System.exit(13);
	        else if (ParmsOut[anzahl+1].equals("*PARM"))
	        	System.exit(14);
	        else if (ParmsOut[anzahl+1].equals("*PRTF"))
	        	System.exit(15);
	        else 
	        	System.exit(2);
	        
	    }
		
	}

