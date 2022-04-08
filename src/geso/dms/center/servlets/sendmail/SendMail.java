package geso.dms.center.servlets.sendmail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class SendMail
{
	  protected String SMTP_HOST_NAME;
	  protected String SMTP_AUTH_USER;
	  protected String SMTP_AUTH_PWD;
	  private String emailFromAddress; 
	  public SendMail() { }
  
	  public String postMailHTML( String To, String Cc, String subject, String mesg, String name ,String filename) throws MessagingException
	  {
		   /* SMTP_HOST_NAME = "smtp.office365.com";
		    SMTP_AUTH_USER = "dms@traphaco.com.vn";
		    SMTP_AUTH_PWD = "Tra@@123";
		    emailFromAddress = "dms@traphaco.com.vn";
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.ssl.trust", "*");		
			props.put("mail.smtp.port", "587");*/
		  
		   /* Gmail SMTP server address: smtp.gmail.com
		    Gmail SMTP username: Your Gmail address (e.g. example@gmail.com)
		    Gmail SMTP password: Your Gmail password
		    Gmail SMTP port (TLS): 587
		    Gmail SMTP port (SSL): 465
		    Gmail SMTP TLS/SSL required: yes*/
		  
			/*SMTP_HOST_NAME = "mail.geso.us";
		    SMTP_AUTH_USER = "thangpt@geso.us";
		    SMTP_AUTH_PWD = "Thang@123";
		    emailFromAddress = "thangpt@geso.us";
		    Properties props = new Properties();
	        props.put("mail.smtp.host", SMTP_HOST_NAME);
	        props.put("mail.smtp.auth", "true");
		    */
		    
		    SMTP_HOST_NAME = "smtp.gmail.com";
		    SMTP_AUTH_USER = "dmsahf@gmail.com";
		    SMTP_AUTH_PWD = "dmsahf2019";
		    emailFromAddress = "dmsahf@gmail.com";
		     
			// Get system properties
	        Properties properties = System.getProperties();

	        properties.put("mail.smtp.starttls.enable", "true"); 
	        properties.put("mail.smtp.host", "smtp.gmail.com");

	        properties.put("mail.smtp.port", "587");
	        properties.put("mail.smtp.auth", "true");
	        
	        Authenticator authenticator = new Authenticator () {
	              public PasswordAuthentication getPasswordAuthentication(){
	            	  String username = SMTP_AUTH_USER;
	  		        String password = SMTP_AUTH_PWD;
	  		        return new PasswordAuthentication(username, password);
	                  //userid and password for "from" email address 
	              }
	          };
	          
          Session session = Session.getDefaultInstance( properties , authenticator); 
			
			//Session session = Session.getInstance(props, new SMTPAuthenticator());
			
			try 
			{
				MimeMessage message = new MimeMessage(session);
				message.setHeader("Content-Type", "text/html; charset=UTF-8");
				
				message.setFrom(new InternetAddress(emailFromAddress));
				//message.setContent(message, "text/html; charset=UTF-8");
				
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To));
				if(Cc.trim().length() > 0)
					message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(Cc));
				
				message.setSubject(subject, "UTF-8");
				message.setContent(mesg, "text/html; charset=UTF-8");
				
				//MimeBodyPart messageBodyPart = new MimeBodyPart();
		       // Multipart multipart = new MimeMultipart();
		        //messageBodyPart = new MimeBodyPart();
		        //String file = "C:\\java-tomcat\\thongbao\\"+filename;
		        //String fileName =name;
		       // DataSource source = new FileDataSource(file);
		       // messageBodyPart.setDataHandler(new DataHandler(source));
		       // messageBodyPart.setFileName(fileName);
		        //multipart.addBodyPart(messageBodyPart);
		        //message.setContent(multipart);
				Transport.send(message);
	
				System.out.println("Mail has been sent successfully to : " + To);
				return "";
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				//throw new RuntimeException(e);
				System.out.println("Error : " + e.toString());
				return "Error : " + e.toString();
			}
    }


	private class SMTPAuthenticator extends javax.mail.Authenticator
	{
	    public PasswordAuthentication getPasswordAuthentication()
	    {
	        String username = SMTP_AUTH_USER;
	        String password = SMTP_AUTH_PWD;
	        return new PasswordAuthentication(username, password);
	    }
	}
}


