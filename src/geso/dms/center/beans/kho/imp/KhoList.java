package geso.dms.center.beans.kho.imp;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.kho.IKho;
import geso.dms.center.beans.kho.IKhoList;

	public class KhoList implements IKhoList
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
		private String ten;
		private String diengiai;
		private String tungay;
		private String denngay;
		private String trangthai;
		private String Msg;
		private List<IKho> kholist; 
		
		public KhoList(String[] param)
		{
			this.ten = param[0];
			this.diengiai = param[1];
			this.tungay = param[2];
			this.denngay = param[3];
			this.trangthai = param[4];
		}
		
		public KhoList()
		{
			this.ten = "";
			this.diengiai = "";
			this.tungay = "";
			this.denngay = "";
			this.trangthai = "2";
			this.Msg ="";
		}

		public List<IKho> getKhoList(){
			return this.kholist;
		}
		
		public void setKhoList(List<IKho> kholist){
			this.kholist = kholist;
		}

		public String getTen(){
			return this.ten;
		}
		
		public void setTen(String ten){
			this.ten = ten;
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

		public void setMsg(String Msg) {
			this.Msg = Msg;
			
		}

		public String getMsg() {
		
			return this.Msg;
		}

	
	
	}
