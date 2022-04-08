package geso.dms.center.beans.banggiablc.imp;

import geso.dms.center.beans.banggiablc.IBanggiablcList;
import geso.dms.center.beans.banggiablc.imp.BanggiablcList;
import geso.dms.center.beans.banggiablc.IBanggiablc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.db.sql.dbutils;

public class BanggiablcList implements IBanggiablcList
{
	private static final long serialVersionUID = -927977546783610214L;
	
	String userId;
	String ten;
	ResultSet dvkd;
	ResultSet kbh;
	String kbhId = "";
	String loaikhachhangId;
	ResultSet LoaiKhachHang;
	String dvkdId;
	String trangthai;
	List<IBanggiablc> bgblclist;
	dbutils db;
	String msg = "";
	
	
	String view ="";
	
	//Constructor
	public BanggiablcList(String[] param)
	{
		this.ten = param[0];		
		this.dvkdId = param[1];
		this.trangthai = param[3];
		this.loaikhachhangId = "";
		this.db = new dbutils();
	}
	
	public BanggiablcList()
	{
		this.ten= "";		
		this.dvkdId= "";
		this.trangthai = "2";
		this.loaikhachhangId = "";
		this.db = new dbutils();
		init("");
	}
	public String getLoaikhachhangId() {
		return loaikhachhangId;
	}
	public void setLoaikhachhangId(String loaikhachhangId) {
		this.loaikhachhangId = loaikhachhangId;
	}
	public ResultSet getLoaiKhachHang() {
		return LoaiKhachHang;
	}

	public void setLoaiKhachHang(ResultSet loaiKhachHang) {
		LoaiKhachHang = loaiKhachHang;
	}

	public ResultSet getKbh() {
		return kbh;
	}
	public void setKbh(ResultSet kbh) {
		this.kbh = kbh;
	}
	
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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

	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public List<IBanggiablc> getBgblcList() 
	{
		return this.bgblclist;
	}

	public void setBgblcList(List<IBanggiablc> bgblclist) 
	{
		this.bgblclist = bgblclist;
	}


	public ResultSet getDvkd() 
	{
		return this.dvkd;
	}

	public void setDvkd(ResultSet dvkd) 
	{
		this.dvkd = dvkd;
	}

	public boolean saveNewBgblc() 
	{
		return false;
	}

	public boolean UpdateBgblc() 
	{
		return false;
	}

	
	private void createDvkdRS(){  				
				
		//this.dvkd  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1' ");;
		this.dvkd  =  this.db.get("select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai = '1' order by a.donvikinhdoanh");
		this.kbh  =  this.db.get(" select pk_seq ,ten,diengiai from kenhbanhang where trangthai = 1 " );
		this.LoaiKhachHang = this.db.get("select SMARTID + '-' + DIENGIAI as ten,pk_seq from loaicuahang where trangthai = '1' ");
	}

	public void createBanggiablcBeanList(String query){  	
	    
		//ResultSet rs =  db.get(query);
		 db.viewQuery(query, dataSearch);
		ResultSet rs= db.getByPreparedStatement(query, dataSearch);
		List<IBanggiablc> bgblclist = new ArrayList<IBanggiablc>();
		if (rs != null){		
			IBanggiablc bgblcBean;
			String[] param = new String[15];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("ten");
					param[2]= rs.getString("dvkd");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					
					bgblcBean = new Banggiablc(param);
					bgblcBean.setKbhId(rs.getString("KBH"));
					bgblcBean.setTungay(rs.getString("tungay"));
					bgblclist.add(bgblcBean);															
				}
				rs.close();
			}catch(Exception e){
		
			}
		}
		
		this.bgblclist = bgblclist;
	}

	public void init(String search){

		String query;
		
		if (search.length()>0){
			query = search;
		}else{
			
			query =  	"\n select a.tungay,kbh.ten KBH, a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, c.ten as nguoitao" +
						"\n	, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd" +
						"\n		, b.pk_seq as dvkdId " +
						"\n from banggiabanlechuan a " +
						"\n inner join donvikinhdoanh b on  a.dvkd_fk=b.pk_seq" +
						"\n inner join nhanvien c on  a.nguoitao = c.pk_seq " +
						"\n inner join nhanvien d on  a.nguoisua = d.pk_seq " +
						"\n inner join kenhbanhang kbh on a.kbh_fk = kbh.pk_seq " +
						"\n where 1 = 1   ";
		}
		query += "\n order by a.tungay desc ";
		
		createBanggiablcBeanList(query);
	    
		createDvkdRS();
	}

	@Override
	public void DbClose() {
		try{
		// TODO Auto-generated method stub
		if(this.dvkd!=null){ this.dvkd.close(); }
		if(this.kbh!=null){ this.kbh.close(); }
		if(this.LoaiKhachHang!=null){ this.LoaiKhachHang.close(); }
		if(this.db!=null){
			db.shutDown();
		}
		}catch(Exception er){
			
		}
	}
	public String getKbhId() {
		return kbhId;
	}
	public void setKbhId(String kbhId) {
		this.kbhId = kbhId;
	}
	
}

