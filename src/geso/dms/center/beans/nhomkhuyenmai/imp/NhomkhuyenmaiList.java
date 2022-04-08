package geso.dms.center.beans.nhomkhuyenmai.imp;

import geso.dms.center.beans.nhomkhuyenmai.INhomkhuyenmai;
import geso.dms.center.beans.nhomkhuyenmai.INhomkhuyenmaiList;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public class NhomkhuyenmaiList implements INhomkhuyenmaiList, Serializable
	{
		private static final long serialVersionUID = -9217977556733610214L;

		// Tieu chi tim kiem
		String msg = "";
		private String diengiai;
		private String trangthai;		 
		private String tungay;
		private String denngay;
		public ResultSet Dsnkm;
		private List<INhomkhuyenmai> nkmlist;
		private boolean search = false;
		
		public NhomkhuyenmaiList(String[] param)
		{
			this.diengiai = param[0];
			this.trangthai = param[2];
			this.tungay = param[3];
			this.denngay = param[4];			
		}
		
		public NhomkhuyenmaiList()
		{
			this.diengiai = "";
			this.trangthai = "";
			this.tungay = "";
			this.denngay = "";			
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
		

		public List<INhomkhuyenmai> getNkmList(){
			return this.nkmlist;
		}
		
		public void setNkmList(List<INhomkhuyenmai> nkmlist){
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
		public String getMsg() {
			// TODO Auto-generated method stub
			return this.msg;
		}

		@Override
		public void setMsg(String msg) {
			// TODO Auto-generated method stub
			this.msg = msg;
		}
		
	}
