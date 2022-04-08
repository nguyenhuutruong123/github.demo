package geso.dms.center.beans.nhomskus.imp;
import java.io.Serializable;
import java.util.List;

import geso.dms.center.beans.nhomskus.INhomSkus;
import geso.dms.center.beans.nhomskus.INhomSkusList;

	public class NhomSkusList implements INhomSkusList, Serializable
	{
		private static final long serialVersionUID = -9217977556733610214L;

		// Tieu chi tim kiem
		private String trangthai;		 
		private String tungay;
		private String denngay;
		private List<INhomSkus> nskuslist;
		private boolean search = false;
		
		public NhomSkusList(String[] param)
		{
			this.trangthai = param[0];
			this.tungay = param[1];
			this.denngay = param[2];	
		}
		
		public NhomSkusList()
		{
			this.trangthai = "";
			this.tungay = "";
			this.denngay = "";
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
		

		public List<INhomSkus> getSkusList(){
			return this.nskuslist;
		}
		
		public void setSkusList(List<INhomSkus> nskuslist){
			this.nskuslist = nskuslist;
		}
		
		public boolean getSearch()
		{
			return this.search;
		}

		public void setSearch(boolean search)
		{
			this.search = search;
		}
	}
