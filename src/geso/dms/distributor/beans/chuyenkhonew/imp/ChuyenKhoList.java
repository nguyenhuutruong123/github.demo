package geso.dms.distributor.beans.chuyenkhonew.imp;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.chuyenkhonew.IChuyenKhoList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;

public class ChuyenKhoList extends Phan_Trang implements IChuyenKhoList, Serializable
{
	String view = "";
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	
	
	private static final long serialVersionUID = 6858020173468336341L;
	private String userId;
	
	private String sochungtu;
	private String nguoitao;
	private String nppTen;
	private String nppId;
	private String tungay;
	private String denngay;
	private String trangthai,msg;
	private ResultSet dhList;
	private ResultSet nvRs;

	private dbutils db;
	int currentPages;
	public ChuyenKhoList(String[] param)
	{
		this.db = new dbutils();
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.userId = "";
		this.nguoitao = "";
		this.sochungtu = "";

		this.nppTen = "";
		this.nppId = "";
		this.msg = "";
		currentPages = 1;
		createDdhlist("");
	}
	
	public ChuyenKhoList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppTen = "";
		this.nguoitao = "";
		this.sochungtu = "";

		this.nppId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.msg="";
		currentPages = 1;
	}
	public ResultSet getNvRs() {
		return nvRs;
	}
	public void setNvRs(ResultSet nvRs) {
		this.nvRs = nvRs;
	}


	public String getSochungtu() {
		return sochungtu;
	}
	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}
	public String getNguoitao() {
		return nguoitao;
	}
	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
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

	public void createDdhlist(String querystr)
	{
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		getNPPInfo();
		String query;
		if (querystr.length()>0)
		{
			query = querystr;
		}else
		{							
				query = 
						"	select case when dh.TRANGTHAI=0 then 0  when dh.TRANGTHAI=1 then 1  when dh.TRANGTHAI=2 then 3  when dh.TRANGTHAI=3 then 2 end sapxep,"
						+ " dh.npp_fk as nppId,dh.ngaydieuchinh, dh.NgayTTXacNhan, dh.pk_seq as nhaphangId, dh.NgayGoiLenTT, nt.TEN as NguoiTao, dh.ngaygiotao as ngaytao,"+
						"   ns.TEN as NguoiSua, dh.ngaygiosua as ngaysua, nd.ten as nguoiduyet, dh.ngaygiochot as ngaychot, dh.TrangThai "+
						"	from ChuyenKho dh inner join NHANVIEN nt on  dh.NguoiTao=nt.PK_SEQ "+
						"		inner join NHANVIEN ns on ns.PK_SEQ=dh.NguoiSua "+
						"		left join NHANVIEN nd on nd.PK_SEQ=dh.Nguoiduyet ";				
				
				if (this.nppId.length() > 0 ) {
					query += "where dh.npp_fk='"+this.nppId+"' ";
				} else {
					query += " where dh.trangthai <> 0" ;
				}
		}
		if (this.nppId.length() <= 0 ) {
			query += " and dh.npp_fk in "+util.quyen_npp(this.userId);
		}
		System.out.println("=======QUERY=====" + query);
		this.dhList =  createSplittingData(10, 15, "sapxep asc, ngaydieuchinh desc", query);
		this.nvRs =  this.db.get(" select pk_seq,ten from nhanvien where trangthai =1 ");

	}

	private void getNPPInfo()
	{
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
	}

	@Override
	public void DBclose(){
		
		try {
			if(this.dhList != null)
				this.dhList.close();
			if(!(this.db == null)){
				this.db.shutDown();
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getMsg()
	{
		return this.msg;
	}

	@Override
	public void setMsg(String msg)
	{
		this.msg=msg;
	}

	@Override
	public int getCurrentPage() {
		return this.currentPages;
	}

	@Override
	public void setCurrentPage(int current) {
		this.currentPages = current;
	}
}
