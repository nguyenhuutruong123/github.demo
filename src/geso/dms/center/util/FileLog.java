
package geso.dms.center.util;
import java.io.*;



public class FileLog {
	


	public static String readFile(String pathFile){
		try{
			FileInputStream fos = new FileInputStream(new File(pathFile));
			Reader r = new InputStreamReader(fos, "UTF8");
			BufferedReader reader = new BufferedReader(r);
			StringBuilder text = new StringBuilder();
			String line = null;
			while((line=reader.readLine())!=null){
				text.append(line+"\n");
			}
			reader.close();
			return text.toString();
		}
		catch(Exception e){
		System.out.print("can not read the file: "+pathFile);return "";}
		
	}
	
	public static String readFile(String pathFile, int[] rows){
		try{
			FileInputStream fos = new FileInputStream(new File(pathFile));
			Reader r = new InputStreamReader(fos, "UTF8");
			BufferedReader reader = new BufferedReader(r);
			StringBuilder text = new StringBuilder();
			String line = null;
			int ir = 0;
			for(int i = 0; i < rows.length; i++){
				for(int y = rows[i]; y >= 0; y--){
					line = reader.readLine();
					if(rows[i] == ir){
						text.append(line);
						break;
					}
					ir++;
				}

			}
				
			reader.close();
			return text.toString();
		}
		catch(Exception e){
		System.out.print("can not read the file: "+pathFile);return "";}
		
	}
	
	public static boolean writeFile(String pathFile, String text, boolean overWrite){
		
		try{
			
			String oldText = "";
			if(!overWrite)
				oldText = readFile(pathFile);
			FileOutputStream fos = new FileOutputStream(pathFile);
			Writer out = new OutputStreamWriter(fos, "UTF8");
			out.write(oldText+text);
			out.close();
			return true;
		}
		catch(Exception ex){
			System.out.print("can not write the file: "+pathFile);
			return false;
		}
	}
	
	
}
