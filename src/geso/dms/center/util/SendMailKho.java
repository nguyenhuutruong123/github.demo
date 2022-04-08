package geso.dms.center.util;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.*;
import java.io.*;
import java.sql.*;

import geso.dms.center.db.sql.*;
import geso.dms.distributor.db.sql.dbutils;

public class SendMailKho
{
	protected String SMTP_HOST_NAME ;
	protected String SMTP_AUTH_USER ;
	protected String SMTP_AUTH_PWD  ;
	private  String emailFromAddress; 
  
	public SendMailKho()
	{
	}
  
	public void postMail( String[] recipients, String subject, String message ) throws MessagingException
	{
		boolean debug = false;
		dbutils sql = new dbutils();
		/*SMTP_HOST_NAME = "mail.geso.us";
		SMTP_AUTH_USER = "chinhpc@geso.us";
		SMTP_AUTH_PWD = "chinhphancong";
		emailFromAddress = "chinhpc@geso.us";*/

		//Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(props, auth);

        session.setDebug(debug);

        // create a message
        Message msg = new MimeMessage(session);

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(emailFromAddress);
    
        InternetAddress[] addressTo = new InternetAddress[1];
        for (int i = 1; i <= new Integer(recipients[0]).intValue(); i++)
        {
	        System.out.println(recipients[i]);
	        addressTo[0] = new InternetAddress(recipients[i]);
	        msg.setFrom(addressFrom);
	        msg.setRecipients(Message.RecipientType.TO, addressTo);
	        msg.setSubject(subject);
	        msg.setContent(message, "text/html; charset=UTF-8");//msg.setContent(message, "text/plain");
	        try{
	          Transport.send(msg);
	        }catch(SendFailedException err){err.toString();}
        }
	}

	public String postMailHTML( String To, String Cc, String subject, String mesg ) throws MessagingException
	{
		SMTP_HOST_NAME = "mail.geso.us";
		SMTP_AUTH_USER = "hoangth@geso.us";
		SMTP_AUTH_PWD = "Hoang@123";
    	emailFromAddress = "hoangth@geso.us";
    
    	Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.ssl.trust", "*");		
		props.put("mail.smtp.port", "25");// 26 hoặc 465

		/*Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
			}
		  });*/
		
		Session session = Session.getDefaultInstance(props, new SMTPAuthenticator());
		
		try 
		{
			MimeMessage message = new MimeMessage(session);
			message.setHeader("Content-Type", "text/html; charset=UTF-8");
			
			message.setFrom(new InternetAddress(emailFromAddress));
			message.setContent(message, "text/html; charset=UTF-8");
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To));
			if(Cc.trim().length() > 0)
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(Cc));
			
			message.setSubject(subject, "UTF-8");
			message.setContent(mesg, "text/html; charset=UTF-8");
			
			Transport.send(message);

			System.out.println("Gui MAIL thanh cong toi: " + To);
			return "";

		} 
		catch (Exception e) 
		{
			//throw new RuntimeException(e);
			System.out.println("Lỗi gửi mail to " + e.toString());
			e.printStackTrace();
			return "Lỗi gửi mail to " + e.toString();
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

	public static void main(String[] arg)
	{
		
		Timer time = new Timer(); // Instantiate Timer Object
		TimerTask st = new TimerTask() {
			
			@Override
			public void run() {
				
				Calendar cal2 = Calendar.getInstance();
			       int hour = cal2.get(Calendar.HOUR_OF_DAY);
			       if(hour == 23)
			       {	
			    	   SendMailKho sendMAIL = new SendMailKho();
						try 
						{
							
							String sql = "\n declare @booked int, @xnt int "+
									 "\n declare cur_capnhat cursor "+
				
				 					"\n for select b.npp_fk from khoasongay b where ngayks = '2018-03-02' --and npp_fk  in (113125) "+
				
				 					"\n open cur_capnhat  --mo Cursor "+
				
				 					"\n declare @nppId numeric(18, 0) "+
				
				 					"\n set @booked = 0 set @xnt = 0 "+
				 					"\n while (0 = 0) "+
				
				 					"\n     begin "+
				
				 					"\n         Fetch next from cur_capnhat into @nppId "+
				
				 					"\n         if(@@fetch_status != 0) ---duyet het roi` "+
				 					"\n 			break "+
							
				 					"\n 		declare @tungay varchar(10) set @tungay = '2018-02-06' "+
						
				 					"\n 		set @booked = @booked + ( select COUNT(*)  "+
				 					"\n 		from nhapp_kho a inner join nhaphanphoi b on a.NPP_FK = b.PK_SEQ   "+
				 					"\n 		left join ufnBooked() c on a.kho_fk = c.KHO_FK and a.npp_fk = c.NPP_FK and a.sanpham_fk = c.SANPHAM_FK and a.kbh_fk = c.KBH_FK  "+
				 					"\n 		where b.PK_SEQ = @nppId and a.booked <> isnull(c.SOLUONG,0)  "+
				 					"\n 		) "+
						
				 					"\n 		set @xnt = @xnt + ( select COUNT(*)  "+
				 					"\n 		from ufn_XNT_TuNgay_DenNgay_FULL_New(@nppId, @tungay, '') a left join NHAPP_KHO b on a.kho_fk = b.KHO_FK "+
				 					"\n 		and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK  "+
				 					"\n 		where (a.cuoiky <> ISNULL(b.soluong, 0) or a.cuoiky <> ISNULL(b.booked,0)+ISNULL(b.available,0)) "+
				 					"\n 		) "+
						
				 					"\n 		----select *  "+
				 					"\n 		--update a set a.booked =	 isnull(c.SOLUONG,0)  "+
				 					"\n 		--from nhapp_kho a inner join nhaphanphoi b on a.NPP_FK = b.PK_SEQ   "+
				 					"\n 		--left join ufnBooked() c on a.kho_fk = c.KHO_FK and a.npp_fk = c.NPP_FK and a.sanpham_fk = c.SANPHAM_FK and a.kbh_fk = c.KBH_FK  "+
				 					"\n 		--where b.PK_SEQ = @nppId and a.booked <> isnull(c.SOLUONG,0) "+
						
				 					"\n 		----select npp.MA as MaNpp, sp.MA as MaSP,cuoiky, BOOKED,a.*, b.* "+
				 					"\n 		--update b set b.SOLUONG = a.cuoiky, b.AVAILABLE = a.cuoiky - b.BOOKED --cặp 1 "+
				 					"\n 		----update b set b.SOLUONG = 0, b.AVAILABLE = 0 --cặp 2 "+
				 					"\n 		--from ufn_XNT_TuNgay_DenNgay_FULL_New(@nppId, @tungay, '') a left join NHAPP_KHO b on a.kho_fk = b.KHO_FK "+
				 					"\n 		--and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK  "+
				 					"\n 		--left join NHAPHANPHOI npp on a.npp_fk = npp.PK_SEQ "+
				 					"\n 		--left join sanpham sp on sp.PK_SEQ = a.sanpham_fk "+
				 					"\n 		--where (a.cuoiky <> ISNULL(b.soluong, 0) or a.cuoiky <> ISNULL(b.booked,0)+ISNULL(b.available,0)) "+
				 					"\n 		----and a.cuoiky >= ISNULL(b.booked,0) --cặp 1 "+
				 					"\n 		----and a.cuoiky < ISNULL(b.booked,0) --cặp 2, book lố "+
				
				 					"\n     end "+
				 					"\n close cur_capnhat "+
				
				 					"\n deallocate cur_capnhat "+
				
				
				 					"\n select  @booked book , @xnt lech";
							String book = null;
							String lech = null;
							dbutils db = new dbutils();
							ResultSet rs = db.get(sql);
							try {
								if (rs.next()) {
									book = rs.getString("book");
									lech = rs.getString("lech");
								}
								rs.close();
							} catch (Exception er) {
							}
				
							String msg = sendMAIL.postMailHTML("hoangth@geso.us,diemdt@geso.us", "", "Mail Kho SGP", "sai book: "+book+", lệch kho "+lech+" ");
							//sendMAIL.postMail(new String[]{"xuantvt@geso.us"}, "TEST", "ABC 123");
							//System.out.println("MSG::: " + msg);
							
							System.out.println("MSG::: "+msg);
						} 
						catch (Exception e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			       }
			}
		};
		time.schedule(st, 0, 1000*1*60*60); // Create Repetitively task for every 1 secs
	}

}


