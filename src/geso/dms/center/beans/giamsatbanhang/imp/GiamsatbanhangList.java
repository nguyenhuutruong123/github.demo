package geso.dms.center.beans.giamsatbanhang.imp;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.beans.giamsatbanhang.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.FileLog;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

public class GiamsatbanhangList extends Phan_Trang implements IGiamsatbanhangList
{
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	private static final long serialVersionUID = -9217977546733610214L;
	String asmId = "";
	public String getAsmId() {
		return asmId;
	}
	public void setAsmId(String asmId) {
		this.asmId = asmId;
	}
	// Tieu chi tim kiem
	String userId;
	String ten;
	String sodienthoai;
	String trangthai;
	String Msg;
	String thuviec;
	ResultSet kenhbanhang;
	String kbhId;
	dbutils db;
	Utility Ult;
	String Smartid;
	HttpServletRequest request;
	ResultSet gsbhListRs;

	ResultSet asmRs;
	private List<IGiamsatbanhang> gsbhlist;

	public ResultSet getAsmRs() {
		return asmRs;
	}
	public void setAsmRs(ResultSet asmRs) {
		this.asmRs = asmRs;
	}
	public GiamsatbanhangList(String[] param)
	{
		this.ten = param[1];
		this.sodienthoai = param[2];
		this.kbhId = param[3];
		this.trangthai = param[4];
		this.Msg ="";
		this.thuviec="";
		db = new dbutils();

		//init("");
	}

	public GiamsatbanhangList()
	{   
		this.Msg ="";
		this.ten = "";
		this.sodienthoai = "";
		this.kbhId = "";
		this.trangthai = "2";
		this.thuviec="";
		this.Smartid = "";
		Ult = new Utility();
		db = new dbutils();
	}

	public void initSplitting(){
		init("");
		setCrrSplitting(getTheLastSplitting()<=15?getTheLastSplitting():15);
	}

	public ResultSet getGsbhListRs() {
		return gsbhListRs;
	}
	public void setGsbhListRs(ResultSet gsbhListRs) {
		this.gsbhListRs = gsbhListRs;
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

	public List<IGiamsatbanhang> getGsbhList()
	{
		return this.gsbhlist;
	}

	public void setGsbhList(List<IGiamsatbanhang> gsbhlist)
	{
		this.gsbhlist = gsbhlist;
	}

	public ResultSet getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(ResultSet kenhbanhang)
	{
		this.kenhbanhang = kenhbanhang;
	}

	public String getKbhId() 
	{
		return this.kbhId;
	}

	public void setKbhId(String kbhId) 
	{
		this.kbhId = kbhId;
	}

	private void createKbhRS()
	{  				
		String sql ="select diengiai as kbhTen, pk_seq as kbhId from kenhbanhang where trangthai='1'";// and pk_seq in " + Ult.quyen_kenh(this.userId);
		//System.out.println(sql);
		this.kenhbanhang =  this.db.get(sql);
		
		String query = " select pk_seq, ten from asm where trangthai = 1 ";
		if(this.kbhId.length()>0){
			query+=" and kbh_fk = " + this.kbhId;
		}
		this.asmRs =  this.db.get(query);
	}

	public void createGsbhBeanList(String query)
	{  
		this.gsbhListRs = super.createSplittingData_v2(db,super.getItems(), super.getSplittings(), "pk_seq desc", query, dataSearch, "");
		//createSplittingData(request, "id desc", query); //db.get(query);
		/*
		ResultSet rs = createSplittingData(50, 10, "id desc", query); //createSplittingData(request, "id desc", query); //db.get(query);
		/*List<IGiamsatbanhang> gsbhlist = new ArrayList<IGiamsatbanhang>();
		if (rs != null){		
			IGiamsatbanhang gsbhBean;
			String[] param = new String[20];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("ten");
					param[2]= rs.getString("diachi");
					param[3]= rs.getString("sodienthoai");
					param[4]= rs.getString("email");
					param[5]= rs.getString("nccTen");
					param[6]= rs.getString("kbhTen");
					param[7]= rs.getString("trangthai");
					param[8]= rs.getString("ngaytao");
					param[9]= rs.getString("nguoitao");
					param[10]= rs.getString("ngaysua");
					param[11]= rs.getString("nguoisua");
					param[12]= rs.getString("nccId");
					param[13]= rs.getString("kbhId");
					param[14]= rs.getString("tinhtrang");
					param[15] = rs.getString("Smartid");
					param[16] = rs.getString("HinhAnh");
					gsbhBean = new Giamsatbanhang(param);
					gsbhlist.add(gsbhBean);															
				}
			}catch(Exception e)
			{
				System.out.println("loi..........."+e.toString());
			}
		
		this.gsbhlist = gsbhlist;
		}
		*/
	}
	
	/*
	 * Phan Quyen Chi HIen thi nhung giam sat ma user do co quyen kenh va npp ma gs phu trach
	 */
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "\n select a.pk_seq,isnull(a.tinhtrang,'0') as tinhtrang,a.HinhAnh,a.pk_seq as id, a.smartid, " +
			"\n a.ten as ten, a.diachi as diachi, a.dienthoai as sodienthoai, a.email as email, b.tenviettat as nccTen, " +
			"\n b.pk_seq as nccId, c.ten as kbhTen, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, " +
			"\n e.ten as nguoisua, a.ngaysua as ngaysua, c.pk_seq as kbhId " +
			"\n from giamsatbanhang a left join  nhacungcap b on a.ncc_fk = b.Pk_seq " +
			"\n left join  kenhbanhang c on c.pk_seq = a.Kbh_fk " +
			"\n left join nhanvien d on d.pk_seq = a.nguoitao " +
			"\n left join nhanvien e on e.pk_seq = a.nguoisua where 1 = 1 ";
		}
		else
		{
			query = search;
		}
		System.out.println("Init List: "+query);
		//setOrderByColumn("a.pk_seq");

		//phanquyen
		/*Utility ut = new Utility();
		query += " and c.pk_seq in " + ut.quyen_kenh(userId)+" and a.pk_seq in "+ut.quyen_gsbh(userId) +" " +
				" union select  isnull(a.tinhtrang,'0') as tinhtrang,a.pk_seq as id, a.ten as ten, a.diachi as diachi, a.dienthoai as sodienthoai, a.email as email, b.tenviettat as nccTen, b.pk_seq as nccId, c.ten as kbhTen, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, e.ten as nguoisua, a.ngaysua as ngaysua, c.pk_seq as kbhId from giamsatbanhang a, nhacungcap b, kenhbanhang c, nhanvien d, nhanvien e where a.ncc_fk=b.pk_seq and a.kbh_fk = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq AND a.pk_Seq not in (select gsbh_Fk from  NHAPP_GIAMSATBH  )     ";*/
		//System.out.println("user id: "+userId + "\n sql:"+query);

		createGsbhBeanList(query);  
		createKbhRS();
	}

	public void setMsg(String Msg) {
		this.Msg =Msg;
	}
	public String getMsg() {
		return this.Msg;
	}

	public void DBClose() {

		if(this.kenhbanhang !=null)
			try {
				this.kenhbanhang.close();
				if(db!=null)
					db.shutDown();
			} catch(Exception e) {

				e.printStackTrace();
			}
	}

	public String getThuviec() {
		return this.thuviec;
	}


	public void setThuviec(String thuviec) {
		this.thuviec=thuviec;
	}

	@Override
	public String getSmartId() {
		// TODO Auto-generated method stub
		return this.Smartid;
	}

	@Override
	public void setSmartId(String smartId) {
		// TODO Auto-generated method stub
		this.Smartid = smartId;
	}

}
