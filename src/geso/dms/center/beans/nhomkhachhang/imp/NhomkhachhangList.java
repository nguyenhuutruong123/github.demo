package geso.dms.center.beans.nhomkhachhang.imp;

import java.io.Serializable;
import java.util.List;
import geso.dms.center.beans.nhomkhachhang.INhomkhachhang;
import geso.dms.center.beans.nhomkhachhang.INhomkhachhangList;

	public class NhomkhachhangList implements INhomkhachhangList, Serializable
	{
		private static final long serialVersionUID = -9217977556733610214L;

		// Tieu chi tim kiem
		private String diengiai;
		private String trangthai;		 
		private String tungay;
		private String denngay;
		private List<INhomkhachhang> nkhlist;
		private boolean search = false;

		private String maKH;
		
		public NhomkhachhangList(String maKH)
		{
			this.maKH = maKH;
				
		}
		public NhomkhachhangList(String[] param)
		{
			this.diengiai = param[0];
			this.trangthai = param[1];
			this.tungay = param[2];
			this.denngay = param[3];			
		}
		
		public NhomkhachhangList()
		{
			this.maKH = "";
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
		

		public List<INhomkhachhang> getNkhList(){
			return this.nkhlist;
		}
		
		public void setNkhList(List<INhomkhachhang> nkhlist){
			this.nkhlist = nkhlist;
		}
		public boolean getSearch()
		{
			return this.search;
		}

		public void setSearch(boolean search)
		{
			this.search = search;
		}
		public void setMaKH(String maKH)
		{
			this.maKH = maKH;
		}
		public String getMaKH(){
			return this.maKH;
		}
		
	}
