package geso.dms.center.util;

import java.io.Serializable;

public class GlobalValue implements Serializable
{
	private static final long serialVersionUID = 1214324L;
	public static  final boolean dev_mode = true;
	
	
	public static boolean getDevmode()
	{
		return dev_mode;
	}	
	
	public static final String SECRET_KEY = "GESO1234";
	public static final String loaiMaHoa = "1";
}
