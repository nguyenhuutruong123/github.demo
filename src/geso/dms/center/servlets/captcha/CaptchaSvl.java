package geso.dms.center.servlets.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CaptchaSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public CaptchaSvl() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int width = 250;
		int height = 60;
		
		/*
		char data[][] = {
		{ "z", "e", "t", "c", "o", "d", "e" },
		{ "l", "i", "n", "u", "x" },
		{ "f", "r", "e", "e", "b", "s", "d" },
		{ "u", "b", "u", "n", "t", "u" },
		{ "j", "e", "e" }
		};
		*/
		
		String data[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "v", "w" , "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		
		BufferedImage bufferedImage = new BufferedImage(width, height,
		BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = bufferedImage.createGraphics();

		Font font = new Font("Georgia", Font.BOLD, 30);
		g2d.setFont(font);

		RenderingHints rh = new RenderingHints(
		RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);

		//GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, 0, height/2, Color.GRAY, true);
		
		//GradientPaint gp = new GradientPaint(0, 0, Colo, 0, height/2, null, true);
		//g2d.setPaint(gp);
		g2d.fillRect(0, 0, width, height);

		g2d.setColor(new Color(255, 153, 0));

		Random r = new Random();
		int index = Math.abs(r.nextInt()) % 5;

		//String captcha = String.copyValueOf(data[index]);
		String captcha = "";
		for(int i = 0; i < 4; i++)
		{
			int idex = r.nextInt(data.length);
			if(idex >= 30)
				idex = 30;
			if(idex <= 0)
				idex = 0;
			
			captcha += data[idex];
		}
		
		//captcha = "geso123";
		
		System.out.println("Captcha Svl: " + captcha);
		request.getSession().setAttribute("captcha", captcha );

		int x = 0;
		int y = 0;

		//for (int i=0; i< data[index].length; i++) 
		for (int i = 0; i < captcha.length(); i++) 
		{
			x += 25 + (Math.abs(r.nextInt()) % 15);
			y = 30 + Math.abs(r.nextInt()) % 20;
			//g2d.drawChars(new char[]{data[i]}, i, 1, x, y);
			if(i % 2 == 0)
			{
				g2d.setColor(new Color(r.nextInt(255), 153, 0));
				g2d.drawString(captcha.substring(i, i+1).toUpperCase(), x, y);
			}
			else
			{
				g2d.setColor(new Color(r.nextInt(200), 113, 140));
				g2d.drawString(captcha.substring(i, i+1).toUpperCase(), x, y);
			}
		}
		
		g2d.dispose();

		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		ImageIO.write(bufferedImage, "png", os);
		os.close();
	} 
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}

}
