package geso.dms.center.beans.khachhangmt.imp;

import geso.dms.center.beans.khachhangmt.IKhachhangMTList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public class KhachhangMTList extends Phan_Trang implements IKhachhangMTList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String ten;
	String sodienthoai;
	String trangthai;
	
	ResultSet npplist;
	
	String nccId;
	String dvkdId;	
	ResultSet khuvuc;
	String kvId;
	ResultSet loainpp;
	String loainppId;
	String Msg;
	dbutils db;
	double MucChietKhau=0;
	String DiaChi;
	String DiaChiXuatHD="";
	String MaSoThue;

	private int num;

	private int[] listPages;

	private int currentPages;

	private HttpServletRequest request;
	//Constructor
	public KhachhangMTList(String[] param)
	{
		this.ten = param[0];
		this.sodienthoai = param[1];
		this.trangthai = param[2];
		this.nccId = param[3];
		this.kvId = param[4];
		this.dvkdId = param[5];
		this.loainppId = param[6];
		this.db = new dbutils();
	}
	
	public KhachhangMTList()
	{
		this.ten = "";
		this.sodienthoai = "";
		this.trangthai = "2";
		this.nccId = "";
		this.kvId = "";
		this.loainppId = "";
		this.dvkdId = "";
		this.Msg ="";
		this.DiaChi="";
		this.MaSoThue="";
		this.MucChietKhau=0;
		this.DiaChiXuatHD="";
		this.db = new dbutils();


		currentPages = 1;
				num = 1;
		
	}
	
	public HttpServletRequest getRequestObj() 
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request) 
	{
		this.request = request;
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

	public String getSodienthoai() 
	{
		return this.sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) 
	{
		this.sodienthoai = sodienthoai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getNccId() 
	{
		return this.nccId;
	}

	public void setNccId(String nccId) 
	{
		this.nccId = nccId;
	}

	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}
	
	public String getLoaiNppId()
	{
		return this.loainppId;
	}
	
	public void setLoaiNppId(String loainppId)
	{
		this.loainppId = loainppId;
	}

	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public ResultSet getNppList() 
	{
		return this.npplist;
	}

	public void setNppList(ResultSet npplist) 
	{
		this.npplist = npplist;
	}

	public ResultSet getKhuvuc() 
	{
		return this.khuvuc;
	}

	public void setKhuvuc(ResultSet khuvuc) 
	{
		this.khuvuc = khuvuc;
	}
	
	public ResultSet getLoaiNPP() 
	{
		return this.loainpp;
	}

	public void setLoaiNPP(ResultSet loainpp) 
	{
		this.loainpp = loainpp;
	}
		
	public void createKvRS()
	{
		this.khuvuc = this.db.get("select ten as kvTen, pk_seq as kvId from khuvuc order by ten");
	}
	
	public void createLoaiNppRs()
	{
		this.loainpp = this.db.get("select pk_seq,ma,ten from LoaiNPP");
	}
	
	public void createNppBeanList(String query)
	{
		ResultSet rs = createSplittingData(50, 10, "id desc", query);// createSplittingData(request, "id desc", query);//this.db.get(query);
		this.npplist = rs;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select  a.ma as nppMa,a.pk_seq as id, " + " ISNULL((select lnpp.Ten from LoaiNPP lnpp where lnpp.pk_seq=a.loainpp),'NA') as tenloainpp" + ", "
					+ " a.ten as nppTen, a.diachi, a.dienthoai, d.ten as khuvuc, a.trangthai, a.ngaytao, "+
					  " a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhaphanphoi a inner join nhanvien b on b.pk_seq=a.nguoitao "+ 
					 " inner join  nhanvien c on c.pk_seq=a.nguoisua "+
					 " left join  khuvuc d  on a.khuvuc_fk=d.pk_seq where 1=1   ";
		
		}
		else
		{
			query = search;
		}
		
		Utility util = new Utility();
		query+=" and a.isKHMT = 1 and a.trangthai=1 ";//AND A.PK_SEQ IN "+util.quyen_npp(userId)+" ";
		
		createNppBeanList(query);  
		createKvRS();
		createLoaiNppRs();
		System.out.println("QUERY NPP:" + query);
	}
	
	public void DBclose(){
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}

	
	public void setMsg(String Msg) {
		this.Msg = Msg;		
	}
	public String getMsg() {
			return this.Msg;
	}

	@Override
	public void setMaSoThue(String masothue) {
		// TODO Auto-generated method stub
		this.MaSoThue=masothue;
	}

	@Override
	public String getMaSoThue() {
		// TODO Auto-generated method stub
		return this.MaSoThue;
	}

	@Override
	public void setDiaChi(String diachi) {
		// TODO Auto-generated method stub
		this.DiaChi=diachi;
	}

	@Override
	public String getDiaChi() {
		// TODO Auto-generated method stub
		return this.DiaChi;
	}

	@Override
	public void setMucChietKhau(double mucchietkhau) {
		// TODO Auto-generated method stub
		this.MucChietKhau=mucchietkhau;
	}

	@Override
	public double getMucChietKhau() {
		// TODO Auto-generated method stub
		return this.MucChietKhau;
	}

	@Override
	public void setDiaChiXuatHD(String diachixhd) {
		// TODO Auto-generated method stub
		this.DiaChiXuatHD=diachixhd;
	}

	@Override
	public String getDiaChiXuatHD() {
		// TODO Auto-generated method stub
		return this.DiaChiXuatHD;
	}
	
	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		// TODO Auto-generated method stub
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		// TODO Auto-generated method stub
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		// TODO Auto-generated method stub
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		// TODO Auto-generated method stub
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		// TODO Auto-generated method stub
		ResultSet rs = db.get("select count(*) as c from nhaphanphoi");
		return PhanTrang.getLastPage(rs);

	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		// TODO Auto-generated method stub
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}		



	
}
