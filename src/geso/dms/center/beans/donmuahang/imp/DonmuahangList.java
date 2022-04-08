package geso.dms.center.beans.donmuahang.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.beans.donmuahang.IDonmuahangList;

public class DonmuahangList extends Phan_Trang implements IDonmuahangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	private String userId;
	private String nppten;
	private String ten;
	private String sku;
	private String tungay;
	private String denngay;
	private String trangthai;
	private String maspstr;
	private String vungId;
	private String kvId;	
	private String so;
	private ResultSet vungList;
	private ResultSet kvList;
	private ResultSet dhList;
	private ResultSet dhdoneList;
	private ResultSet dhKTlist;
	private String malist;
	private dbutils db;

	String view ;
	String msg="";
	public DonmuahangList(String[] param)
	{
		this.db = new dbutils();
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		createDdhlist("");
	}
	
	public DonmuahangList()
	{
		this.db = new dbutils();
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.vungId = "";
		this.kvId	= "";
		this.so = "";
		this.maspstr = "";
		this.nppten = "";
		this.view = "";
	}
	
	public ResultSet getDhList()
	{
		return this.dhList;
	}
	
	public void setDhList(ResultSet dhList)
	{
		this.dhList = dhList;
	}

	public ResultSet getDhDoneList()
	{
		return this.dhdoneList;
	}
	
	public void setDhDoneList(ResultSet dhdonelist)
	{
		this.dhdoneList = dhdonelist;
	}

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getTen()
	{
		return this.ten;
	}
	
	public void setTen(String ten)
	{
		this.ten = ten;
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
	
	@Override
	public String getSO() 
	{	
		return so;
	}

	@Override
	public void setSO(String so) {
		
		this.so = so;
	}
	
	@Override
	public ResultSet getDdhKTList() 
	{		
		return this.dhKTlist;
	}

	public String getMaspstr() 
	{
		String query = "select pk_seq as id, ma, ten from sanpham order by pk_seq";
		ResultSet rs = this.db.get(query);
		try
		{
			while(rs.next())
			{
				if (this.maspstr.length()==0)
				{
					this.maspstr = "\"" + rs.getString("ma") +  " - " + rs.getString("ten") + "\"";
				}
				else
				{
					this.maspstr = this.maspstr + ",\"" + rs.getString("ma") +  " - " + rs.getString("ten") + "\"";
				}
			}
		}
		catch(java.sql.SQLException e){}		
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}
	
	public ResultSet getKvList(){
		
		return this.kvList;
	}
	
	public void setKvList(ResultSet kvList){
		this.kvList = kvList;
	}
	
	public String getKvId(){
		return this.kvId;
	}
	
	public void setKvId(String kvId){
		this.kvId = kvId;
	}
	
	public void createDdhlist(String querystr){
		String query;
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();	
		if (querystr.length()>0){
			query = querystr;
		}else{
			query ="select convert(varchar, a.ngaydat, 120) as ngaydat,isnull( convert(varchar, a.ngaygiodat, 103) + '' + substring( convert(varchar, a.ngaygiodat, 120),11,20),'')   as  ngaygiodat, " +
			"   isnull( convert(varchar, hdnh.ngaygiohd, 103) + '' + substring( convert(varchar, hdnh.ngaygiohd, 120),11,20),'')  as ngaygiohd , " +
			"   isnull( convert(varchar, nh.thoigianhangve, 103) + '' + substring( convert(varchar, nh.thoigianhangve, 120),11,20),'') as  ngayhangve , " +
			"   isnull( convert(varchar, LOGNHAP.date, 103) + '' + substring( convert(varchar, LOGNHAP.date, 120),11,20),'') as  ngaynhanhang , " +
			"   isnull( convert(varchar, nh.ngayinpgh, 103) + '' + substring( convert(varchar, nh.ngayinpgh, 120),11,20),'') as  ngayhd , " +
			"   isnull( convert(varchar, a.NGAYDUYET, 103) + '' + substring( convert(varchar, a.NGAYDUYET, 120),11,20),'') as  ngayduyet  , a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,   "+
			"   isnull(a.sotienavat,0) as sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid,  "+
			"	isnull(nh.sotienbvat,'0') as tienhd , "+
			" isnull(nh.trangthai,0) as isnhanhang, isnull(thang,'') as thang, isnull(nam,'') as nam ,isnull(a.loaidonhang,0) as loaidonhang, a.DONDATHANG_FROM_FK "+
			" from dondathang a " +
			" left join nhanvien b on  a.nguoitao = b.pk_seq "+   
			" left join nhanvien c on a.nguoisua = c.pk_seq "+  
			" left join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+ 
			" left join  nhaphanphoi  f  on a.npp_fk = f.pk_seq    "+
			" left join nhaphang nh on nh.dathang_fk=a.pk_seq   " +
			" left join LOGNHAP on LOGNHAP.nhaphang_fk=nh.pk_seq  "+
			" left join hdnhaphang hdnh on hdnh.dathang_fk=a.pk_seq "+
			 " where  f.pk_seq in "+ ut.quyen_npp(userId) ;
				
			
		}
		System.out.println("Khoi tao don hang"+query);
		this.dhList =  createSplittingData(50, 10, "trangthai asc, ngaydat desc, chungtu desc", query); //this.db.msget2(query);
	
		
		query = "select ten from nhanvien where pk_seq='" + this.userId + "'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.ten = rs.getString("ten");
			rs.close();
		}catch(java.sql.SQLException e){}
		
		this.vungList = db.get(" select * from vung ");
		
		query=" select distinct kv.pk_Seq as kvid, kv.ten as  kv from phamvihoatdong inner join " +
			  " nhaphanphoi npp on npp.pk_seq=npp_fk  "+ 
			  " inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk " +
			  " where nhanvien_fk= '"+ this.userId +"'";
		if(this.vungId.trim().length() > 0) query += " and kv.vung_fk = "+ this.vungId +" ";
		this.kvList = this.db.get(query);

	}
	
	public void DBclose(){
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}

	@Override
	public void createDdhKTlist(String querystr) {
		
		String query;
		//geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();	
		if (querystr.length()>0){
			query = querystr;
		}else
		{
					
			/*query =" select distinct a.ngaydat, a.PREFIX, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,  "+
            " a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, isnull (nh.ngaychungtu,'0')as ngayhd, "+
            " isnull(nh.sotienbvat,'0') as tienhd ,1 as loai from dondathang a inner join "+ 
            " nhanvien b on  a.nguoitao = b.pk_seq   "+
            " inner join  nhanvien c on a.nguoisua = c.pk_seq  "+
            " inner join dondathang_sp d on a.pk_seq = d.dondathang_fk "+ 
            " inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+
            " inner join  nhaphanphoi  f  on a.npp_fk = f.pk_seq  "+ 
            " left join nhaphang nh on nh.dathang_fk=a.pk_seq  "+
            " where ( a.trangthai  > 1  and a.trangthai <=3) "+
            "union " +
            " select dth.ngaytra as ngaydat, dth.PREFIX, dth.pk_seq as chungtu,npp.ten as  nppTen,dvkd.donvikinhdoanh,dth.sotienavat, "+
            " nt.ten as nguoitao,ns.ten as nguoisua,dth.trangthai,'' as soid,'' as ngayhd,0 as tienhd, 2 as loai  from dontrahang dth inner join nhaphanphoi npp on  dth.npp_fk=npp.pk_seq "+
            " inner join nhanvien nt on nt.pk_seq=dth.nguoitao "+
            " inner join nhanvien ns on ns.pk_seq=dth.nguoisua "+
            " inner join donvikinhdoanh dvkd on dvkd.pk_Seq=dth.dvkd_fk " +
            " inner join dontrahang_sp dth_sp on dth.pk_seq=dth_sp.dontrahang_fk"+
            " where dth.trangthai >=2 and dth.trangthai <=3 ";*/
			
			query = " select distinct a.ngaydat, a.PREFIX, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,"+   
					"  cast(a.sotienavat as float) as sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, "+ 
			 " isnull (nh.ngaychungtu,'0')as ngayhd,  cast(isnull(nh.sotienbvat,'0') as float) as tienhd ,1 as loai from dondathang a "+ 
			 " inner join  nhanvien b on  a.nguoitao = b.pk_seq    inner join  nhanvien c on a.nguoisua = c.pk_seq "+  
			 " inner join dondathang_sp d on a.pk_seq = d.dondathang_fk  inner join donvikinhdoanh e on "+ 
			 " a.dvkd_fk = e.pk_seq   inner join  nhaphanphoi  f  on a.npp_fk = f.pk_seq    "+
			 " left join nhaphang nh on nh.dathang_fk=a.pk_seq   where ( a.trangthai  > 1  and a.trangthai <=3) "+
			 "  union  "+
			 "  select distinct dth.ngaytra as ngaydat, dth.PREFIX, dth.pk_seq as chungtu,npp.ten as  "+
			 "  nppTen,dvkd.donvikinhdoanh,cast(  dth.sotienavat as float) as sotienavat,  nt.ten as nguoitao,ns.ten as nguoisua,dth.trangthai,'' "+ 
			 "  as soid,'' as ngayhd,cast (0 as float)  as tienhd, 2 as loai  from dontrahang dth inner join nhaphanphoi npp on  "+
			 "   dth.npp_fk=npp.pk_seq  inner join nhanvien nt on nt.pk_seq=dth.nguoitao  inner join nhanvien ns "+
			 "    on ns.pk_seq=dth.nguoisua  inner join donvikinhdoanh dvkd on dvkd.pk_Seq=dth.dvkd_fk  "+
			 "     inner join dontrahang_sp dth_sp on dth.pk_seq=dth_sp.dontrahang_fk where dth.trangthai >=2 "+
			 "      and dth.trangthai <=3 ";
			 
			
		}
		System.out.println("Khoi tao duyet don hang: " + query);
		
		this.dhKTlist =  createSplittingData(50, 10, "chungtu desc", query); //this.db.msget2(query);
	
		
		query = "select ten from nhanvien where pk_seq='" + this.userId + "'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.ten = rs.getString("ten");
			rs.close();
		}catch(java.sql.SQLException e){}
		query="select distinct kv.pk_Seq as kvid, kv.ten as  kv from phamvihoatdong inner join " +
			 " nhaphanphoi npp on npp.pk_seq=npp_fk  "+ 
			 " inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk " +
			 " where nhanvien_fk= '"+ this.userId +"'";
		this.kvList = this.db.get(query);	
	}



	@Override
	public void SetMsg(String msg) {
		
		this.msg=msg;
	}

	@Override
	public String getMsg() {
		
		return this.msg;
	}

	@Override
	public String getnppTen() {
		
		return this.nppten;
	}

	@Override
	public void setnppTen(String nppten) {
		
		this.nppten = nppten;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId= vungId;
		
	}

	public ResultSet getVungList() 
	{
		return this.vungList;
	}

	public void setVungList(ResultSet vungList) 
	{
		this.vungList = vungList;
		
	}
	
	public String getView() 
	{
		return this.view;
	}

	public void setView(String view) 
	{
		this.view= view;
		
	}
	
}