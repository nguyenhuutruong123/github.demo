package geso.dms.distributor.beans.catalog.imp;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import geso.dms.distributor.beans.catalog.*;
import geso.dms.center.db.sql.dbutils;

	public class catalogList implements IcatalogList, Serializable
	{
		private static final long serialVersionUID = -9217977556733610214L;

		// Tieu chi tim kiem
		private String ten;
		private String chungloai;
		private String ghichu;		 
		private String duongdan;
		private ResultSet catalogList;
		String msg = "";

		dbutils db;
		
		public catalogList(String[] param)
		{
			this.ten = param[0];
			this.chungloai = param[1];
			this.ghichu = param[2];
			this.duongdan = param[3];
			
			db = new dbutils();
		}
		
		public catalogList()
		{
			this.ten = "";
			this.chungloai = "";
			this.ghichu = "";
			this.duongdan = "";
			db = new dbutils();
		}

		
		public void init(String search){
			
			String query = "";
			if(search.trim().length() > 0)
			{
				query = search;
			}
			else
			{
				query = "select a.MA,a.nhanhang as nhanhang_fk,a.pk_seq,a.ten,chungloai_fk,ghichu,duongdan,a.ngaytao,a.ngaysua,b.ten as nguoitao,c.ten as nguoisua from Catalog a inner join nhanvien b on a.nguoitao=b.pk_seq inner join nhanvien c on c.pk_seq=a.nguoisua ";
			}
			this.catalogList = db.get(query);
			
		}
		
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getTen() {
			return ten;
		}
		public void setTen(String ten) {
			this.ten = ten;
		}

		public String getChungloai() {
			return chungloai;
		}

		public void setChungloai(String chungloai) {
			this.chungloai = chungloai;
		}

		public String getGhichu() {
			return ghichu;
		}

		public void setGhichu(String ghichu) {
			this.ghichu = ghichu;
		}

		public String getDuongdan() {
			return duongdan;
		}

		public void setDuongdan(String duongdan) {
			this.duongdan = duongdan;
		}

		public ResultSet getCatalogList() {
			return catalogList;
		}

		public void setCatalogList(ResultSet catalogList) {
			this.catalogList = catalogList;
		}

		
	}
