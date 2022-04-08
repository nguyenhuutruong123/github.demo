package geso.dms.center.beans.cttrungbay.imp;

import geso.dms.center.beans.cttrungbay.*;
import geso.dms.distributor.db.sql.dbutils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CttrungbayList implements ICttrungbayList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String diengiai;
	String tungay;
	String denngay;
	String msg;
		
	List<ICttrungbay> cttbList;
	
	dbutils db;
	
	public CttrungbayList(String[] param)
	{
		this.diengiai = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		db = new dbutils();
	}
	
	public CttrungbayList()
	{
		this.diengiai = "";
		this.denngay = "";
		this.tungay = "";
		db = new dbutils();
		String msg="";
	}
	
	public String getMsg() {
		if(msg==null){
			msg="";
		}
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getDiengiai() 
	{		
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public String getTungay() 
	{		
		return this.tungay;
	}
	
	public void setTungay(String tungay)
	{
		this.tungay = tungay;		
	}
	
	public String getDenngay() 
	{		
		return this.denngay;
	}
	
	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;		
	}
	
	public List<ICttrungbay> getCttbList() 
	{		
		return this.cttbList;
	}
	
	public void setCttbList(List<ICttrungbay> cttbList) 
	{
		this.cttbList = cttbList;	
	}
	
	public void init(String search) 
	{
		db = new dbutils();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = "select a.PK_SEQ as cttbId, a.SCHEME,a.diengiai, a.ngaytds, a.ngaykttds, a.ngaytrungbay as ngaytb, a.ngayketthuctb as ngaykttb, isnull(a.DIENGIAI, '') as diengiai, a.TYPE, a.solanthanhtoan as solantt, a.NGANSACH, a.DASUDUNG, a.NGAYTAO, a.NGAYSUA, b.TEN as nguoitao, c.TEN as nguoisua ";
			query = query + " from CTTRUNGBAY a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ ";
			query = query + " order by a.ngaytrungbay DESC, a.ngayketthuctb DESC";
		}
		else
		{
			query = search;
		}				
		this.createCttbBeanList(query);	
	}
	
	private void createCttbBeanList(String query) 
	{
		ResultSet rs =  db.get(query);
		List<ICttrungbay> cttbList = new ArrayList<ICttrungbay>();
		
		if(rs != null)
		{
			String[] param = new String[15];
			ICttrungbay cttbBean = null;
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("cttbId");
					param[1]= rs.getString("scheme");
					param[2]= rs.getString("diengiai");
					
					param[3]= rs.getString("ngaytds");
					param[4]= rs.getString("ngaykttds");
					param[5]= rs.getString("ngaytb");
					param[6]= rs.getString("ngaykttb");
					
					param[7]= rs.getString("type");
					param[8] = rs.getString("diengiai");
					param[9]= rs.getString("dasudung");
					param[10]= rs.getString("solantt");
					
					param[11]= rs.getString("ngaytao");
					param[12]= rs.getString("nguoitao");
					param[13]= rs.getString("ngaysua");
					param[14]= rs.getString("nguoisua");
					
					cttbBean = new Cttrungbay(param);
					cttbList.add(cttbBean);
				}
				rs.close();
			}
			catch(Exception e) {e.printStackTrace();}	
		}
		this.cttbList = cttbList;	
	}
	
	public void DBclose() 
	{
			if(cttbList!=null)
			{
				cttbList.clear();
				cttbList=null;
			}
			if(db!=null) db.shutDown();
	}

	
}