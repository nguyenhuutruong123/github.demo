package geso.dms.center.beans.tratrungbay.imp;
import geso.dms.center.beans.tratrungbay.ITratrungbayList;
import geso.dms.center.beans.tratrungbay.ITratrungbay;
import geso.dms.center.beans.tratrungbay.imp.Tratrungbay;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.dms.distributor.db.sql.dbutils;

public class TratrungbayList implements ITratrungbayList
{
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String diengiai;
	String tungay;
	String denngay;
		
	List<ITratrungbay> tratbList;
	
	dbutils db;

	private String msg;
	
	public TratrungbayList(String[] param)
	{
		this.diengiai = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		db = new dbutils();
	}
	
	public TratrungbayList()
	{
		this.msg = "";
		this.diengiai = "";
		this.denngay = "";
		this.tungay = "";
		db = new dbutils();
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
	
	public List<ITratrungbay> getTratbList() 
	{		
		return this.tratbList;
	}
	
	public void setTratbList(List<ITratrungbay> tratbList) 
	{
		this.tratbList = tratbList;	
	}
	
	public void init(String search) 
	{
		
		Utility Ult = new Utility();
		String query = "";	
		if (search.length() == 0)
		{
			query = "select a.PK_SEQ as trakmId, a.diengiai, isnull(a.TONGLUONG, 0) as tongluong, isnull(a.TONGTIEN, 0) as tongtien, a.loai, a.hinhthuc, a.ngaytao, a.ngaysua, b.TEN as nguoitao, c.TEN as nguoisua ";
			query = query + " from tratrungbay a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ ";
			query = query + " order by a.ngaytao DESC, a.pk_seq DESC";
		}
		else
		{
			query = search;
		}				
		this.createtrakmBeanList(query);	
	}
	
	private void createtrakmBeanList(String query) 
	{
		ResultSet rs = db.getByPreparedStatement(query, dataSearch);
		List<ITratrungbay> tratbList = new ArrayList<ITratrungbay>();
		
		if(rs != null)
		{
			String[] param = new String[10];
			ITratrungbay trakmBean = null;
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("trakmId");
					param[1]= rs.getString("diengiai");
					param[2]= rs.getString("tongtien");
					param[3] = rs.getString("tongluong");
					param[4]= rs.getString("loai");
					param[5]= rs.getString("hinhthuc");
					param[6]= rs.getString("ngaytao");
					param[7]= rs.getString("nguoitao");
					param[8]= rs.getString("ngaysua");
					param[9]= rs.getString("nguoisua");
					
					trakmBean = new Tratrungbay(param);
					tratbList.add(trakmBean);
				}
				rs.close();
			}
			catch(Exception e) {}		
		}
		this.tratbList = tratbList;	
	}
	
	public void DBclose() 
	{
				
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.msg = msg;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.msg;
	}
}

