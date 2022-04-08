package geso.dms.center.servlets.thongbao;


import java.io.File;
import java.util.Date;
 
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy 
{
    
    //implement the rename(File f) method to satisfy the 
    // FileRenamePolicy interface contract
    public File rename(File f){
    
        //Get the parent directory path as in h:/home/user or /home/user
        String parentDir = f.getParent( );
      
        //Get filename without its path location, such as 'index.txt'
        String fname = f.getName( );
      
        //Get the extension if the file has one
        int giatri=0;
        int vitri=0;
        for(int i=0;i<fname.length();i++)
        {
        	if(fname.toCharArray()[i]=='.')
        	{
        		vitri=i;
        		giatri++;
        	}
        }
        String fileExt = "";
        if(giatri==1)
        {
	        int i = -1;
	        if(( i = fname.indexOf(".")) != -1){
	      
	            fileExt = fname.substring(i);
	            fname = fname.substring(0,i);
	        }
        }
        else
        {
        	fileExt = fname.substring(vitri);
            fname = fname.substring(0,vitri);
        }
        System.out.println("Ten file la "+fname);
        System.out.println("Dinh dang file la "+fileExt);
        //add the timestamp
        fname = fname + (","+( new Date( ).getTime( ) / 1000)+",");
      
        //piece together the filename
        fname = parentDir + System.getProperty(
            "file.separator") + fname + fileExt;
      
        File temp = new File(fname);
        
         return temp;
    }

}
