package geso.dms.center.beans.nhomthuong.imp;

import geso.dms.center.beans.nhomthuong.INhomthuong;
import geso.dms.center.beans.nhomthuong.INhomthuongList;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public class NhomthuongList implements INhomthuongList, Serializable
	{
		private static final long serialVersionUID = -9217977556733610214L;

		// Tieu chi tim kiem
		private String diengiai;
		private String type;
		private String trangthai;		 
		private String tungay;
		private String denngay;
		public ResultSet Dsnkm;
		private List<INhomthuong> nkmlist;
		private boolean search = false;
		
		public NhomthuongList(String[] param)
		{
			this.diengiai = param[0];
			this.trangthai = param[2];
			this.tungay = param[3];
			this.denngay = param[4];
			this.type = param[5];
		}
		
		public NhomthuongList()
		{
			this.diengiai = "";
			this.trangthai = "";
			this.tungay = "";
			this.denngay = "";	
			this.type = "";
		}


		public String getDiengiai(){
			return this.diengiai;
		}
		
		public void setDiengiai(String diengiai){
			this.diengiai = diengiai;
		}
		
		public String getTrangthai(){
			return this.trangthai;
		}
		
		public void setTrangthai(String trangthai){
			this.trangthai = trangthai;
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
		

		public List<INhomthuong> getNkmList(){
			return this.nkmlist;
		}
		
		public void setNkmList(List<INhomthuong> nkmlist){
			this.nkmlist = nkmlist;
		}
		public boolean getSearch()
		{
			return this.search;
		}

		public void setSearch(boolean search)
		{
			this.search = search;
		}

	   public void setDsnkm(ResultSet Dsnkm) {
		 this.Dsnkm = Dsnkm;
			
		}

		
		public ResultSet getDsnkm() {
			return Dsnkm;
		}

		@Override
		public void setType(String type) {
			this.type = type;
		}

		@Override
		public String getType() {
			return this.type;
		}
		
	}
