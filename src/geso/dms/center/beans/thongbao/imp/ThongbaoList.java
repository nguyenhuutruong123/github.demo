package geso.dms.center.beans.thongbao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.beans.thongbao.IThongbaoList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;

public class ThongbaoList extends Phan_Trang implements IThongbaoList
{
	private static final long serialVersionUID = 1L;
	String ID,USERID,TRANGTHAI,TIEUDE,NOIDUNG, NHANVIEN,NGAYTAO,NGAYBATDAU,NGAYKETTHUC,MSG;
	ResultSet thongbaoRs;
	ResultSet thongbaonhanvienRs;
	private int num;
	private int[] listPages;
	private int currentPages;
	dbutils db;
	
	String task;
	String viewMode;
	String loaithongbao;
	String trangthai;
	
	public ThongbaoList()
	{
		this.db=new dbutils();
		this.ID="";
		this.USERID="";
		this.TRANGTHAI="";
		this.NHANVIEN="";
		this.NGAYTAO="";
		this.NGAYBATDAU="";
		this.NGAYKETTHUC="";
		this.TIEUDE="";
		this.NOIDUNG = "";
		currentPages = 1;
		num = 1;
		this.viewMode = "1"; //Center
		this.loaithongbao = "";
		this.task = "";
	}
	
	public ThongbaoList(String userid)
	{
		this.db=new dbutils();
		this.ID="";
		this.USERID="";
		this.NHANVIEN=userid;
		this.TRANGTHAI="";
		this.NGAYBATDAU="";
		this.NGAYKETTHUC="";
		this.NGAYTAO="";
		this.TIEUDE="";
		this.NOIDUNG = "";
		currentPages = 1;
		num = 1;
		this.viewMode = "0"; //NPP
		this.loaithongbao = "";
		this.task = "";
	}
	
	public String getId() {
		
		return this.ID;
	}

	
	public void setId(String id) {
		
		this.ID=id;
	}

	
	public String getUserId() 
	{
		
		return this.USERID;
	}

	
	public void setUserId(String userId) 
	{
		this.USERID=userId;
		
	}

	public String getTieude() {
		
		return this.TIEUDE;
	}

	
	public void setTieude(String tieude) {
		
		this.TIEUDE=tieude;
	}
	
	public void init(String query) 
	{
		String sql="";
		 if(query.trim().length() <= 0)
		 {
			 sql= "select  ct.pk_seq,ct.trangthai, ct.tinhtrang, ct.noidung,ct.tieude,ct.ngaybatdau,ct.filename, case when len(ct.ngayketthuc) <= 0 then N'Vô thời hạn' else ct.ngayketthuc end as ngayketthuc," +
			 		"ct.ngaytao,ct.nguoitao,ct.ngaysua,ct.nguoisua ,NV.TEN as TENNV,NV.PK_SEQ as MANV,NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS, ct.nguoitao as nguoitaoTB " +
			 	  "from thongbao ct inner join NHANVIEN nv on ct.nguoitao = nv.PK_SEQ inner join NHANVIEN nv2 on ct.NGUOISUA = nv2.PK_SEQ where isnull(ct.hienthi,1) = 1 ";
			 
			 if(this.loaithongbao.trim().length() > 0)
				 sql += " and ct.loaithongbao = '" + this.loaithongbao + "' ";
			 else
				 sql += " and ct.loaithongbao != '5' "; //chi lay Van ban, khong lay thong bao
			 if(!this.viewMode.equals("0"))
				 sql += " and ct.nguoitao = '" + this.USERID + "' ";
			 
		 }
		 else 
		 { sql = query;}
		 
		 System.out.println("Init Thong bao1: " + sql);
		 this.thongbaoRs = createSplittingData(50, 10, "TRANGTHAI asc , PK_SEQ  desc", sql);
		 this.createRs();
	}

	
	public void DBClose() 
	{
		if(this.thongbaoRs!=null)
			try 
			{
				this.thongbaoRs.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		if (this.db != null)
			this.db.shutDown();
	}

	public String getTrangthai() 
	{		
		return this.TRANGTHAI;
	}


	
	public void setTrangthai(String trangthai)
	{
		this.TRANGTHAI=trangthai;
	}


	public String getNhanvien() 
	{
		return this.NHANVIEN;
	}


	public void setNhanvien(String nhanvien) 
	{
		this.NHANVIEN=nhanvien;
	}

	
	public void setMsg(String Msg) 
	{
		this.MSG=Msg;	
	}

	
	public String getMsg() {
		
		return this.MSG;
	}

	
	public ResultSet getThongbaoList() 
	{	
		return this.thongbaoRs;
	}

	
	public void setThongbaoList(ResultSet thongbao) 
	{	
		this.thongbaoRs=thongbao;
	}

	public ResultSet getThongbaonhanvienList() 
	{
		return this.thongbaonhanvienRs;
	}

	public void setThongbaonhanvienList(ResultSet thongbaonhanvien) 
	{
		this.thongbaonhanvienRs=thongbaonhanvien;
	}

	public void createRs() 
	{
		System.out.println("select * from nhanvien");
		this.thongbaonhanvienRs = db.get("select * from nhanvien");
	}
	public String getNgaytao() 
	{
		return this.NGAYTAO;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.NGAYTAO=ngaytao;
	}
	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		System.out.println("ddddddddd"+"select count(*) as c from thongbao_nhanvien where nhanvien_fk='"+this.NHANVIEN+"'");
		ResultSet rs = db.get("select count(*) as c from thongbao_nhanvien where nhanvien_fk='"+this.NHANVIEN+"'");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	public void initNhanvien(String query) 
	{
		 String sql="";
		 if(query == "")
		 {
			 sql = "select distinct a.pk_seq,a.tieude,a.filename,a.noidung,b.trangthai,a.ngaybatdau,a.ngayketthuc,a.ngaytao,c.Ten as nguoitao, a.nguoitao as nguoitaotb, a.ngaysua,a.nguoisua, a.loaithongbao " +
			 	   "from thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk inner join NHANVIEN c on a.nguoitao = c.PK_SEQ " +
			 	   "where isnull(a.hienthi,1) = 1 and  b.trangthai != '2' and nhanvien_fk = '" + this.NHANVIEN + "' ";
			 /*if(this.viewMode.equals("0"))
				 sql += " and a.nguoitao = '" + this.USERID + "' ";*/
			 if(this.loaithongbao.trim().length() > 0)
				 sql += " and a.loaithongbao = '" + this.loaithongbao + "' ";
		 }
		 else 
			 sql = query;
		 
		 System.out.println("lay thong bao nhan vien: "+sql);
		 this.thongbaoRs = createSplittingData(50, 10, "NGAYBATDAU DESC,TRANGTHAI asc ", sql);
	}
	
	public String getNgaybatdau() 
	{
		return this.NGAYBATDAU;
	}


	public void setNgaybatdau(String ngaybatdau)
	{
		this.NGAYBATDAU=ngaybatdau;
	}


	public String getNgayketthuc() 
	{
		return this.NGAYKETTHUC;
	}


	public void setNgayketthuc(String ngayketthuc) 
	{
		this.NGAYKETTHUC=ngayketthuc;
	}

	public String getViewMode() {

		return this.viewMode;
	}

	public void setViewMode(String viewMode) {
		
		this.viewMode = viewMode;
	}

	
	public String getNoidung() {
		
		return this.NOIDUNG;
	}

	
	public void setNoidung(String noidung) {
		
		this.NOIDUNG = noidung;
	}

	
	public String getLoaithongbao() {
		
		return this.loaithongbao;
	}

	
	public void setLoaithongbao(String loaithongbao) {
		
		this.loaithongbao = loaithongbao;
	}

	
	public String getTask() {

		return this.task;
	}


	public void setTask(String task) {
		
		this.task = task;
	}
	

}
