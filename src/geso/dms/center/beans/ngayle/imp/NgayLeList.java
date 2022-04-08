package geso.dms.center.beans.ngayle.imp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.ngayle.INgayLeList;
import geso.dms.center.beans.nhacungcap.INhacungcapList;
import geso.dms.center.db.sql.*;
	public class NgayLeList implements INgayLeList
	{
		List<Object> dataSearch = new ArrayList<Object>(); 
		
		public List<Object> getDataSearch() {
			return dataSearch;
		}
		public void setDataSearch(List<Object> dataSearch) {
			this.dataSearch = dataSearch;
		}
		
		private static final long serialVersionUID = -9217977546733610214L;

		// Tieu chi tim kiem
		private String ngaytao;
		private String ngaysua;
		private String nguoitao;
		private String nguoisua;
		private String ngayle;
		private String diengiai;
		private String nhacungcap;
		private String tenviettat;
		private String tungay;
		private String denngay;
		private String trangthai;
		private String query;
		private String Msg;
		String nguoidaidien;
		private ResultSet ncclist; 
		private dbutils db;
		
		public NgayLeList(String[] param)
		{
			this.nhacungcap = param[0];
			this.tungay = param[1];
			this.denngay = param[2];
			this.trangthai = param[3];
			this.tenviettat = param[4];
			this.nguoidaidien=param[5];
			this.db = new dbutils();
		}
		
		public NgayLeList()
		{  
			this.nhacungcap = "";
			this.tenviettat = "";
			this.tungay = "";
			this.denngay = "";
			this.trangthai = "2";
			this.ngaytao = "";
			this.ngaysua = "";
			this.nguoitao = "";
			this.nguoisua = "";
			this.ngayle = "";
			this.diengiai = "";
			this.query = "";
			this.Msg ="";
			this.db = new dbutils();
		}

		
		
		public String getNgaytao() {
			return ngaytao;
		}

		public void setNgaytao(String ngaytao) {
			this.ngaytao = ngaytao;
		}

		public String getNgaysua() {
			return ngaysua;
		}

		public void setNgaysua(String ngaysua) {
			this.ngaysua = ngaysua;
		}

		public String getNguoitao() {
			return nguoitao;
		}

		public void setNguoitao(String nguoitao) {
			this.nguoitao = nguoitao;
		}

		public String getNguoisua() {
			return nguoisua;
		}

		public void setNguoisua(String nguoisua) {
			this.nguoisua = nguoisua;
		}

		public String getNgayle() {
			return ngayle;
		}

		public void setNgayle(String ngayle) {
			this.ngayle = ngayle;
		}

		public String getDiengiai() {
			return diengiai;
		}

		public void setDiengiai(String diengiai) {
			this.diengiai = diengiai;
		}

		public String getQuery(){
			return this.query;
		}
		
		public void setQuery(String query){
			this.query = query;
		}

		public ResultSet getNccList(){
			return this.ncclist;
		}
		
		public void setNccList(ResultSet ncclist){
			this.ncclist = ncclist;
		}

		public String getNhacungcap(){
			return this.nhacungcap;
		}
		
		public void setNhacungcap(String nhacungcap){
			this.nhacungcap = nhacungcap;
		}
		
		public String getTenviettat(){
			return this.tenviettat;
		}
		
		public void setTenviettat(String tenviettat){
			this.tenviettat = tenviettat;
		}

		public String getTungay(){
			return this.tungay;
		}
		
		public void setTungay(String tungay){
			this.tungay = tungay;
		}

		public String getDenngay(){
			return this.denngay;
		}
		
		public void setDenngay(String denngay){
			this.denngay = denngay;
		}
		
		public String getTrangthai(){
			return this.trangthai;
		}
		
		public void setTrangthai(String trangthai){
			this.trangthai = trangthai;
		}
		
		public void init(){
			if(query.length() == 0)
			{
				/*this.query = " select a.pk_seq,a.ngaytao, a.ngaysua,a.nguoitao,a.nguoisua,a.ngayle, a.diengiai," +
					      "\n b.ten as nguoitao, c.ten as nguoisua " +
					      "\n from ngayle a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq order by ngaytao";*/
				this.query = "select a.pk_seq, a.ngaysua, a.ngaytao,a.nguoisua,a.nguoitao, a.ngayle, a.diengiai "
		    			+ "from ngayle a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq "; 
			}
			
	    	this.ncclist = this.db.getByPreparedStatement(query,dataSearch);
	    	
		}

		public void DBClose(){
			try{
				if(ncclist != null) this.ncclist.close();
				this.db.shutDown();
			}catch(Exception e){}
		}

		public void setMsg(String Msg) {
			this.Msg = Msg;			
		}

		public String getMsg() {
			return this.Msg;
		}
	}
