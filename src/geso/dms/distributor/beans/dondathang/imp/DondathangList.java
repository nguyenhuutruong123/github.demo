package geso.dms.distributor.beans.dondathang.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.dondathang.IDondathangList;
import geso.dms.distributor.db.sql.dbutils;

public class DondathangList extends Phan_Trang implements IDondathangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	private String userId;
	private String nppTen;
	private String nppId;
	private String sku;
	private String tungay;
	private String denngay;
	private String trangthai;
	private ResultSet dhList;
	private String malist;
	private dbutils db;
	public DondathangList(String[] param)
	{
		this.db = new dbutils();
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		createDdhlist("");
	}
	
	public DondathangList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		
	}
	
	public ResultSet getDhList()
	{
		return this.dhList;
	}
	
	public void setDhList(ResultSet dhList)
	{
		this.dhList = dhList;
	}

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
		getNPPInfo();
	}

	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getNppTen()
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}
	
	public String getSKU()
	{
		return this.sku;
	}
	
	public void setSKU(String sku)
	{
		this.sku = sku;
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMalist()
	{
		return this.malist;
	}
	
	public void setMalist(String malist)
	{
		this.malist = malist;
	}

	public void createDdhlist(String querystr){
		String query;
			
			if (querystr.length()>0){
				query = querystr;
			}else{
				//query = "select distinct a.ngaydat, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai from dondathang a, nhanvien b, nhanvien c, dondathang_sp d, donvikinhdoanh e where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.dondathang_fk and a.dvkd_fk = e.pk_seq and a.npp_fk='"+ this.nppId +"'";//order by a.trangthai, a.pk_seq";
				query = " select a.ngaydat, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua,"+ 
						" a.trangthai, sum(d.soluongduyet) as soluongduyet, sum(d.soluong) as soluong, a.dondathang_from_fk, isnull(a.ghichu, '') as ghichu "+
						" from dondathang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq"+
						" inner join dondathang_sp d on a.pk_seq = d.dondathang_fk inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq"+
						" where a.npp_fk='"+ this.nppId +"'"+
						" group by a.ngaydat, a.pk_seq, e.donvikinhdoanh, a.sotienavat, b.ten, c.ten, a.trangthai, a.dondathang_from_fk, a.ghichu ";
				System.out.println("Khoi tao : "+query);
			}
			this.dhList =  createSplittingData(10, 15, "chungtu desc", query);//this.db.msget2(query);
			if(this.dhList != null) System.out.println("ok now");

	}

	private void getNPPInfo(){
		/*String query = "select a.pk_seq, a.ten from nhaphanphoi a, nhanvien b where a.ma = b.dangnhap and b.pk_seq = '" + this.userId + "'";
		
		ResultSet rs = this.db.get(query);
		try{
			if (rs != null){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				rs.close();
			}else{
				this.nppId = "";
				this.nppTen = "";
			}			
		}catch(java.sql.SQLException e){}*/
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
	
	}

	@Override
	public void DBclose(){
		
		try {
			if(this.dhList != null)
				this.dhList.close();
			if(!(this.db == null)){
				this.db.shutDown();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}