package geso.dms.center.beans.donvidoluong.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.db.sql.*;
import geso.dms.center.beans.donvidoluong.IDonvidoluongList;

	public class DonvidoluongList implements IDonvidoluongList
	{
		private static final long serialVersionUID = -9217977546733610214L;
		
		
		List<Object> dataSearch = new ArrayList<Object>(); 
		
		public List<Object> getDataSearch() {
			return dataSearch;
		}
		public void setDataSearch(List<Object> dataSearch) {
			this.dataSearch = dataSearch;
		}
		
		String view="";
		

		

		// Tieu chi tim kiem
		private String dvdl;
		private String diengiai;
		private String tungay;
		private String denngay;
		private String trangthai;	
		private String query;
		String Msg;
		private dbutils db;
		
		public DonvidoluongList(String[] param)
		{
			this.diengiai = param[0];
			this.tungay = param[1];
			this.denngay = param[2];
			this.trangthai = param[3];
			this.dvdl = param[4];
			this.db = new dbutils();
		}
		
		public DonvidoluongList()
		{
			this.dvdl = "";
			this.diengiai = "";
			this.tungay = "";
			this.denngay = "";
			this.trangthai = "2";
			this.query = "";
			this.Msg ="";
			this.db = new dbutils();
		}

		public String getDvdl(){
			return this.dvdl;
		}
		
		public void setDvdl(String dvdl){
			this.dvdl = dvdl;
		}

		public String getDiengiai(){
			return this.diengiai;
		}
		
		public void setDiengiai(String diengiai){
			this.diengiai = diengiai;
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

		public String getQuery(){
			return this.query;
		}
		
		public void setQuery(String query){
			this.query = query;
		}

		public ResultSet getDonvilist(){
			if (this.query.length() == 0)
				this.query = "select a.pk_seq, a.donvi, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from donvidoluong a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ "; 

			//return this.db.get(this.query);
			return this.db.getByPreparedStatement(query, dataSearch);
		}
		
		public void DBClose(){
			this.db.shutDown();

		}

		public void setMsg(String Msg) {
		
			this.Msg = Msg;
		}

		public String getMsg() {
		
			return this.Msg;
		}
		
		public String getView() {
			return view;
		}
		public void setView(String view) {
			this.view = view;
		}
	}
