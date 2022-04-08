package geso.dms.center.beans.donvikinhdoanh.imp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.donvikinhdoanh.IDonvikinhdoanhList;
import geso.dms.center.db.sql.*;

	public class DonvikinhdoanhList implements IDonvikinhdoanhList
	{
		
		List<Object> dataSearch = new ArrayList<Object>(); 
		
		public List<Object> getDataSearch() {
			return dataSearch;
		}
		public void setDataSearch(List<Object> dataSearch) {
			this.dataSearch = dataSearch;
		}
		
		private static final long serialVersionUID = -9217977546733610214L;

		private String dvkd;	
		private String nccId;
		private String tungay;
		private String denngay;
		private String trangthai;
		private String query;
		String Msg;
		private dbutils db;
		
		public DonvikinhdoanhList(String[] param)
		{
			this.dvkd = param[0];
			this.nccId = param[1];
			this.tungay = param[2];
			this.denngay = param[3];
			this.trangthai = param[4];
			this.db = new dbutils();
		}
		
		
		public DonvikinhdoanhList()
		{
			this.dvkd = "";
			this.nccId = "";
			this.tungay = "";
			this.denngay = "";
			this.trangthai = "";
			this.query = "";
			this.Msg ="";
			this.db = new dbutils();
		}

		public String getQuery(){
			return this.query;
		}

		public void setQuery(String query){
			this.query = query;
		}

		public String getDvkd(){
			return this.dvkd;
		}

		public void setDvkd(String dvkd){
			this.dvkd = dvkd;
		}
		
		public String getNccId(){
			return this.nccId;
		}

		public void setNccId(String nccId){
			this.nccId = nccId;
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
		
		public ResultSet getDvkdList(){
			if (this.query.length() == 0){
		    	this.query = "select a.pk_seq, a.donvikinhdoanh, d.ten as nhacungcap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.pk_seq as nccId, a.diengiai from donvikinhdoanh a, nhanvien b, nhanvien c, nhacungcap d, nhacungcap_dvkd e where a.PK_SEQ = e.DVKD_FK and d.PK_SEQ = e.NCC_FK and a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and e.checked='1'  ";
		    					
			}
			return this.db.getByPreparedStatement(query,dataSearch);
			
		}
		
		public ResultSet getNccList(boolean all){
			String query;
			if (all)
				query = "select pk_seq, ten from nhacungcap";
			else
				query = "select pk_seq, ten from nhacungcap where trangthai='1'";
			
			return  this.db.get(query);
			
		}
		
		public void DBClose(){
				this.db.shutDown();

		}

        public void setMsg(String Msg) {
	     	this.Msg =Msg;
			
		}

		public String getMsg() {
			return this.Msg;
		}

	}
