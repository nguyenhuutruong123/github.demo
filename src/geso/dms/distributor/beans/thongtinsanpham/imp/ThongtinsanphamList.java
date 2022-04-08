package geso.dms.distributor.beans.thongtinsanpham.imp;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.thongtinsanpham.IThongtinsanphamList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

public class ThongtinsanphamList extends Phan_Trang implements IThongtinsanphamList, Serializable
{
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	private static final long serialVersionUID = -9217977556733610214L;

	private String userId;
	private String masp;
	private String nppTen;
	private String nppId;
	private String tensp;
	private String dvkdId;
	private String nhId;	
	private String nganhId;
	private String clId;
	private String trangthai;
	private ResultSet dvkd;
	String view = "";

	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}

	private ResultSet nh;
	private ResultSet nganh;
	private ResultSet cl;
	private ResultSet splist;

	 dbutils db;// = new dbutils();

	int currentPages;
	int[] listPages;

	public ThongtinsanphamList(String[] param)
	{
		this.tensp = param[0];
		this.dvkdId = param[1];
		this.nhId = param[2];	
		this.nganhId = param[2];		 
		this.clId = param[3];
		this.trangthai = param[4];
		this.db = new dbutils();
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

		init("");
	}

	public ThongtinsanphamList()
	{
		this.nppTen = "";
		this.masp = "";
		this.tensp = "";
		this.dvkdId = "";
		this.nhId = "";	
		this.nganhId = "";	
		this.clId = "";
		this.trangthai = "2";
		this.db = new dbutils();
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

	}

	public String getNganhId() {
		return nganhId;
	}

	public void setNganhId(String nganhId) {
		this.nganhId = nganhId;
	}

	public ResultSet getNganh() {
		return nganh;
	}

	public void setNganh(ResultSet nganh) {
		this.nganh = nganh;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
		/*
			String query = "select a.pk_seq, a.ten from nhaphanphoi a, nhanvien b where a.ma = b.dangnhap and b.pk_seq = '" + this.userId + "'";

			ResultSet rs = this.db.get(query);
			try
			{
				if (rs != null)
				{
					rs.next();
					this.nppId = rs.getString("pk_seq");
					this.nppTen = rs.getString("ten");
					rs.close();
				}
				else
				{
					this.nppId = "";
					this.nppTen = "";
				}
			}
			catch(Exception e){}
		 */
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
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

	public String getMasp(){
		return this.masp;
	}

	public void setMasp(String masp){
		this.masp = masp;
	}

	public String getTensp(){
		return this.tensp;
	}

	public void setTensp(String tensp){
		this.tensp = tensp;
	}

	public String getDvkdId(){
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId){
		this.dvkdId = dvkdId;
	}

	public String getNhId(){
		return this.nhId;
	}

	public void setNhId(String nhId){
		this.nhId = nhId;
	}

	public String getClId(){
		return this.clId;
	}

	public void setClId(String clId){
		this.clId = clId;
	}

	public String getTrangthai(){
		return this.trangthai;
	}

	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}				

	public ResultSet getDvkd(){
		return this.dvkd;
	}

	public void setDvkd(ResultSet dvkd){
		this.dvkd = dvkd;
	}

	public ResultSet getNh(){
		return this.nh;
	}

	public void setNh(ResultSet nh){
		this.nh = nh;
	}

	public ResultSet getCl(){
		return this.cl;
	}

	public void setCl(ResultSet cl){
		this.cl = cl;
	}

	public ResultSet getThongtinsanphamList(){
		return this.splist;
	}

	public void setThongtinsanphamList(ResultSet splist){
		this.splist = splist;
	}


	private ResultSet createDvkdRS(){

		//ResultSet dvkdRS =  this.db.get("select pk_seq, donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1' ");
		ResultSet dvkdRS = this.db.get("select distinct  c.donvikinhdoanh as dvkd, c.pk_seq as dvkdId  from nhacungcap_dvkd a, nhapp_nhacc_donvikd b, donvikinhdoanh c where a.pk_seq = b.ncc_dvkd_fk and c.pk_seq = a.dvkd_fk and b.npp_fk='" + this.nppId + "'");  
		System.out.println("select distinct  c.donvikinhdoanh as dvkd, c.pk_seq as dvkdId  from nhacungcap_dvkd a, nhapp_nhacc_donvikd b, donvikinhdoanh c where a.pk_seq = b.ncc_dvkd_fk and c.pk_seq = a.dvkd_fk and b.npp_fk='" + this.nppId + "'");
		//				this.db.get("select distinct  a.pk_seq, a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId  from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");

		return dvkdRS;
	}

	private ResultSet createNhRS(){

		ResultSet nhRS;
		if (dvkdId.length()>0){
			nhRS =  this.db.get("select pk_seq, ten from nhanhang where trangthai='1' and dvkd_fk='" + dvkdId + "'");
		}else{
			nhRS =  this.db.get("select pk_seq, ten from nhanhang where trangthai='1'");
		}

		return nhRS;

	}

	private ResultSet createClRS() {  	

		String query = "select distinct a.pk_seq, a.ten from chungloai a, nhanhang_chungloai b where a.trangthai='1' and a.pk_seq = b.cl_fk ";

		if (this.nhId.length()>0){
			if(this.nhId.length()>0){
				query = query + "  and b.nh_fk = '" + this.nhId + "'";
			}
		}

		return this.db.get(query);

	}
	

	public void CreateRS(){

		this.dvkd = createDvkdRS();
		
		this.nh = createNhRS();
		this.cl= createClRS();

	}
	private void getNppInfo()
	{		
		
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		System.out.println("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");				
			}else
			{
				this.nppId = "";
				this.nppTen = "";			
			}
			
		}catch(Exception e){}	
		/*
		Utility util=new Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		*/
		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		System.out.println("User Bi Rong :"+this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
	
	}

	public void init(String querystr){
		try
		{
			getNppInfo();
			String query;			

			if (querystr.length()>0)
			{
				query = querystr;
			}
			else
			{		
				Utility util=new Utility();
				/*				query ="select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq,a.ma,a.ten,b.donvikinhdoanh  as dvkd,b.pk_seq as dvkdId,c.ten as chungloai, e.pk_seq as nhId,d.donvi,e.ten as nhanhang,d.pk_seq as clId,a.trangthai, isnull(f.giabanlechuan, '0') as giablc ";
				query = query + " from sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
				query = query  + " left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq inner join BANGGIABANLECHUAN_KHUVUC h on h.BANGGIABANLECHUAN_FK = g.PK_SEQ ";
				query = query  + "where b.pk_seq in (select c.pk_Seq from nhapp_nhacc_donvikd a inner join nhacungcap_dvkd b on a.ncc_dvkd_fk = b.pk_seq inner join donvikinhdoanh c on b.dvkd_fk = c.pk_seq "+
				"inner join nhacungcap ncc on ncc.pk_Seq=b.ncc_fk where a.npp_fk='"+util.getIdNhapp(this.userId)+"') and h.KHUVUC_FK = '"+kv+"'";
				 */
				String nppid = util.getIdNhapp(this.userId);

				query = "\n select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq,a.ma,a.ten,b.donvikinhdoanh as dvkd," +
						"\n b.pk_seq as dvkdId,c.ten as NGANHHANG, e.pk_seq as nhId,d.donvi,e.ten as nhanhang,d.pk_seq as clId," +
						"\n a.trangthai,[dbo].[GiaBanLeNppSanPham](null,100025,"+nppid+",a.pk_seq, [dbo].GetNgayHienTai()     ) as giablc " +
						"\n from sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq " +
						"\n left join NGANHHANG c on a.NGANHHANG_fk = c.pk_seq " +
						"\n left join donvidoluong d on a.dvdl_fk = d.pk_seq " +
						"\n left join nhanhang e on a.nhanhang_fk = e.pk_seq " +
						"\n where b.pk_seq in (select c.pk_Seq from nhapp_nhacc_donvikd a inner join nhacungcap_dvkd b on a.ncc_dvkd_fk = b.pk_seq inner join donvikinhdoanh c on b.dvkd_fk = c.pk_seq "+
						"\n inner join nhacungcap ncc on ncc.pk_Seq=b.ncc_fk where a.npp_fk='"+util.getIdNhapp(this.userId)+"') ";



			}
			String subquery = "  ";
			//this.db.viewQuery(query, dataSearch);
			super.setItems(30);
			this.splist = super.createSplittingData_v2(db, super.getItems(), super.getSplittings(),"pk_seq desc", query, dataSearch, subquery); //this.db.get(query);
			CreateRS();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void closeRS(){
		try{			
			if(this.cl != null)
				this.cl.close();
			if(this.nh != null)
				this.nh.close();
			if(this.splist != null)
				this.splist.close();
			if(this.dvkd != null)
				this.dvkd.close();
			if(this.db != null)
				this.db.shutDown();
		}catch(Exception e){}
	}

	public int getCurrentPage() 
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages()
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as skh from sanpham");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("skh"));
			rs.close();
			return count;
		}
		catch(Exception e) {}

		return 0;
	}

	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		this.closeRS();
	}

}
