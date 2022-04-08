package geso.dms.center.beans.nhacungcap.imp;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import geso.dms.center.beans.nhacungcap.INhacungcapList;
import geso.dms.center.db.sql.*;
	public class NhacungcapList implements INhacungcapList
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
		private String nhacungcap;
		private String tenviettat;
		private String tungay;
		private String denngay;
		private String trangthai;
		private String query;
		private String Msg;
		String nguoidaidien;
		String view = "";
		String userId="";
		private ResultSet ncclist; 
		private dbutils db;
		
		public NhacungcapList(String[] param)
		{
			this.nhacungcap = param[0];
			this.tungay = param[1];
			this.denngay = param[2];
			this.trangthai = param[3];
			this.tenviettat = param[4];
			this.nguoidaidien=param[5];
			this.db = new dbutils();
		}
		
		public NhacungcapList()
		{  
			this.nhacungcap = "";
			this.tenviettat = "";
			this.tungay = "";
			this.denngay = "";
			this.trangthai = "2";
			this.query = "";
			this.Msg ="";
			this.db = new dbutils();
		}
		
		
		
		public String getView() {
			return view;
		}

		public void setView(String view) {
			this.view = view;
		}
		
		

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
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
				this.query = " select a.pk_seq, a.ten, a.diachi,a.nguoidaidien,a.dienthoai,a.sotaikhoan, a.masothue, a.tenviettat, " +
					      " a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua " +
					      " from nhacungcap a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq order by ngaytao";
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
