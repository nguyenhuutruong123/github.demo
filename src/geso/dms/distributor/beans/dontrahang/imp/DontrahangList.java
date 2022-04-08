package geso.dms.distributor.beans.dontrahang.imp;
import java.io.Serializable;
import java.sql.ResultSet;



import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.beans.dontrahang.IDontrahangList;

public class DontrahangList extends Phan_Trang  implements IDontrahangList, Serializable
{
	
	String view = "";
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String sophieu;
	String lydo;
	String msg;
	
	ResultSet nhapkhoRs;
	ResultSet khRs;
	String khId;
	
	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	String nguoitaoId;
	ResultSet nguoitaoRs;

	
	dbutils db;
	
	public DontrahangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.sophieu="";
		this.sochungtu="";
		this.lydo = "";
		this.msg = "";
		this.nguoitaoId = "";
		this.db = new dbutils();
	}
	
	public String getNguoitaoId() {
		return nguoitaoId;
	}
	public void setNguoitaoId(String nguoitaoId) {
		this.nguoitaoId = nguoitaoId;
	}
	public ResultSet getNguoitaoRs() {
		return nguoitaoRs;
	}
	public void setNguoitaoRs(ResultSet nguoitaoRs) {
		this.nguoitaoRs = nguoitaoRs;
	}
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}


	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String search)
	{
		if(!this.view.equals("TT"))
			this.getNppInfo();
		Utility util = new Utility();
		String query = "";
        
		if(search.length() > 0)
			query = search;
		else
		{
			query =	
					"	select isnull((select round(sum(soluong*dongia*(1+ptvat/100)),0)  from dontrahang_sp where dontrahang_fk=a.pk_seq),0) as tienavat,isnull(a.ghichu,'') as ghichu,a.pk_Seq,b.MA as nppMa,b.TEN as nppTen,a.NGAYTRA,c.TEN as nguoiTao,d.TEN as nguoiSua,e.TEN as tructhuoc,a.TRANGTHAI,a.SOTIENBVAT,a.Modified_Date,a.created_date "+
					"		from DONTRAHANG a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "+
					"		inner join NHANVIEN c on c.PK_SEQ=a.NGUOITAO  "+
					"		inner join NHANVIEN d on d.PK_SEQ=a.NGUOISUA "+
					"		inner join NhaCungCap e on e.PK_SEQ=a.NCC_FK " +
					" where 1=1 ";
			if(this.view.equals("TT"))
				query +=" and a.npp_fk in ("+util.quyen_npp(this.userId)+")  ";
			else
				query +=  " and a.npp_fk='"+this.nppId+"' " ;
			
		} 
		System.out.println("___CHUYEN KHO: " + query);
		
		this.nhapkhoRs = createSplittingData(50, 10, "NGAYTRA desc, pk_Seq desc, TRANGTHAI asc ", query);
		this.khRs = db.get("select PK_SEQ, TEN from NhaCungCap where trangthai = '1'");
		this.nguoitaoRs = db.get("select pk_seq, ten from nhanvien a where trangthai = '1' and  exists (select 1 from DONTRAHANG where a.pk_seq = nguoitao ) ");
		
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getSophieu()
	{
		return sophieu;
	}

	public void setSophieu(String sophieu) 
	{
		this.sophieu = sophieu;
	}

	public String getLydo() 
	{
		return lydo;
	}

	public void setLydo(String lydo) 
	{
		this.lydo = lydo;
	}

	public String getTungayTao() 
	{
		return this.tungay;
	}

	public void setTungayTao(String tungay) 
	{
		this.tungay =tungay;	
	}

	public String getDenngayTao() 
	{
		return this.denngay;
	}

	public void setDenngayTao(String denngay) 
	{
		this.denngay = denngay;
	}

	public ResultSet getNhapkhoRs() 
	{
		return this.nhapkhoRs;
	}

	public void setNhapkhoRs(ResultSet nkRs) 
	{
		this.nhapkhoRs = nkRs;
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
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		System.out.println("NPPID: "+this.nppId +" USERID: "+this.userId);
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public String getSochungtu() {
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu=sochungtu;
	}

	public ResultSet getKhRs() {
		return this.khRs;
	}

	public void setKhRs(ResultSet khrs) {
		this.khRs=khrs;
		
	}

	public String getKhId() {
		return this.khId;
	}

	public void setKhId(String KhId) {
		this.khId=KhId;
		
	}
	
	
	
}