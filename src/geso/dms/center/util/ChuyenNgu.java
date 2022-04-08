package geso.dms.center.util;
import java.io.Serializable;
import redis.clients.jedis.Jedis;
public class ChuyenNgu implements Serializable
{
	//public  static final String host = "localhost";
	public  static final String host = "42.117.1.226";
	public static final int port = 8111;
	public static final int timeout = 2000;
	//public static final Jedis jedis = new Jedis( host,port,10000); 

	//vi
	//en
	//zh
	
	public static long set(String key, String languageId, String value)
	{
		if (value != null)
		value = value.trim();
		
/*		//if(jedis.hget(key,languageId) == null)
			return  jedis.hset(key, languageId, value);
		
		//return -1;
*/			
		return -1;
	}
	
	public static boolean checkExits(String key, String languageId, Jedis jedis)
	{
		return jedis.hget(key,languageId)!= null;
	}
	
	
	public static String get(String key, String languageId)
	{	
		String kq = key;

		/*Jedis j = null;
		try
		{
			j = new Jedis( host,port,timeout); 

			if(j != null && j.hget(key,languageId) != null)
				kq = j.hget(key,languageId);
			//j.quit();
			j.close();
			j = null;
		}
		catch (Exception e) {
			kq = key;
			e.printStackTrace();	
		}
*/		return kq;
	}
	
	public static String get(String key, String languageId, Jedis j)
	{	
		String kq = key;
		
		/*try
		{
			if(j != null && j.hget(key,languageId) != null)
				kq = j.hget(key,languageId);

		}
		catch (Exception e) {
			kq = key;
			e.printStackTrace();			
		}*/
		return kq;
	}
	
	public static void Save()
	{
		Jedis j = null;
		try
		{
			j = new Jedis( host,port,10000); 			
			j.save();
			j.close();
			j = null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void Save(Jedis j)
	{
		try
		{	
			j.save();
		}
		catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public static void main(String[] args) {
		System.out.println("345678910JQKA");
	}
}
