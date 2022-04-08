package geso.dms.center.beans.erp_chinhanh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;


import geso.dms.center.util.Phan_Trang;

import geso.dms.center.beans.erp_chinhanh.IErp_chinhanhList;
import geso.dms.center.db.sql.dbutils;

public class Erp_chinhanhList  extends Phan_Trang implements IErp_chinhanhList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 668328923969461422L;
	dbutils db;
	String ID;
	String userId;
	String userTen;
	String MA;
	String TEN;
	String NGAYTAO;
	String NGAYSUA;
	String NGUOITAO;
	String NGUOISUA;
	String TRANGTHAI;
	String Msg;
	ResultSet Rscn;
	
	
	public Erp_chinhanhList()
	{
		db = new dbutils();
		this.userId = "";
		this.userTen = "";
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.TRANGTHAI = "";
		this.Msg = "";
	}

	
	public String getID() {
		
		return ID;
	}

	
	public String getMA() {
		
		return MA;
	}

	
	public String getTEN() {
		
		return TEN;
	}

	
	public String getNGAYTAO() {
		
		return NGAYTAO;
	}

	
	public String getNGAYSUA() {
		
		return NGAYSUA;
	}

	
	
	
	public String getNGUOITAO() {
		
		return NGUOITAO;
	}

	
	public String getNGUOISUA() {
		
		return NGUOISUA;
	}

	
	public String getTRANGTHAI() {
		
		return TRANGTHAI;
	}

	
	public String getMsg() {
		
		return Msg;
	}

	
	public ResultSet getRscn() {
		
		return Rscn;
	}

	
	
	
	public void setID(String ID) {
		
		this.ID = ID;
	}

	public void setMA(String MA) {
		
		this.MA = MA;
	}

	
	public void setTEN(String TEN) {
		
		this.TEN = TEN;
	}

	
	public void setNGAYTAO(String NGAYTAO) {
		
		this.NGAYTAO = NGAYTAO;
	}

	
	public void setNGAYSUA(String NGAYSUA) {
		
		this.NGAYSUA = NGAYSUA;
	}

	
	public void setNGUOITAO(String NGUOITAO) {
		
		this.NGUOITAO = NGUOITAO;
	}

	
	public void setNGUOISUA(String NGUOISUA) {
		
		this.NGUOISUA = NGUOISUA;
	}

	
	public void setTRANGTHAI(String TRANGTHAI) {
		
		this.TRANGTHAI = TRANGTHAI;
	}

	
	public void setMsg(String Msg) {
		
		this.Msg = Msg;
	}

	

	
public void init(String sql) 
{
	String query =" SELECT CN.PK_SEQ AS ID_CN,CN.MA,CN.TEN,isnull(CN.TRANGTHAI,0) AS TT ,CN.NGAYTAO ,CN.NGAYSUA ,NT.TEN AS "
				+ " NGUOITAO,NS.TEN AS NGUOISUA"
				+ " FROM ERP_CHINHANH CN "
				+ " INNER JOIN NHANVIEN NT  ON NT.PK_SEQ = CN.NGUOITAO "
				+ " INNER JOIN NHANVIEN NS ON NS.PK_SEQ = CN.NGUOISUA" ;
	if (this.MA.trim().length() > 0)
		query += " and CN.ma like N'%" + this.MA+ "%'";
	if (this.TEN.trim().length() > 0)
		query += " and CN.ten like N'%" + this.TEN + "%'";
	if (this.TRANGTHAI.length() > 0)
		query += " and CN.TRANGTHAI = '" + TRANGTHAI + "' ";
	if (this.NGAYTAO.length() > 0)
		query += " and cn.ngaytao >= '%" + NGAYTAO + "%'";
	this.Rscn = createSplittingData(50, 10, " ID_CN DESC,TT  ", query);;
	
	
	
	System.out.println("query list " + query);
	
}
	
	
	
	public void DBClose() 
	{
		if(this.Rscn!=null)
			try
			{
				this.Rscn.close();
				if(this.db!=null)
					this.db.shutDown();
			} catch (SQLException e)
			{
			
				e.printStackTrace();
			}
	}

	
	
	public void setUserTen(String userten) {
		this.userTen=userten;
		
	}

	
	public String getUserTen() {
		
		return this.userTen;
	}

	
	public void setUserid(String userid) {
		
		this.userId = userid;
	}

	
	public String getUserid() {
		
		return userId;
	}

	
	public boolean CheckReferences(String column,String table)
	{
		String query="SELECT count("+column+") AS NUM  FROM "+table+" WHERE "+column+" ="+this.ID+""; 
		System.out.println("CheckReferences "+query);
		ResultSet rs = db.get(query);
		System.out.println("____Kiem tra rang buoc_____ "+query);
		try {//kiem tra ben san pham
		while(rs.next())
		{ if(rs.getString("num").equals("0"))
		   return false;
		}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		 return true;
	}

	public boolean Delete()
	{
		if(CheckReferences("ChiNhanh_FK","NhaPhanPhoi"))
		{
			this.Msg="Chi nhánh này đã được sử dụng,không thể xóa ";
			return false;
		}
		if(CheckReferences("ChiNhanh_FK","GiamSatBanHang"))
		{
			this.Msg="Chi nhánh này đã được sử dụng,không thể xóa ";
			return false;
		}
		String query = "Delete Erp_ChiNhanh Where PK_SEQ =" + this.ID + "";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xoá chi nhánh này ";
			return false;
		}
		return true;
	}

}
