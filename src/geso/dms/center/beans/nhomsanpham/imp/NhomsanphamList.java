package geso.dms.center.beans.nhomsanpham.imp;
import java.io.Serializable;
import java.util.List;
import geso.dms.center.beans.nhomsanpham.INhomsanpham;
import geso.dms.center.beans.nhomsanpham.INhomsanphamList;

	public class NhomsanphamList implements INhomsanphamList, Serializable
	{
		String view ="";
		private static final long serialVersionUID = -9217977556733610214L;

		// Tieu chi tim kiem
		private String diengiai;
		private String thanhvien;
		private String trangthai;		 
		private String tungay;
		private String denngay;
		private String lnhom;
		private List<INhomsanpham> nsplist;
		private boolean search = false;
		
		public NhomsanphamList(String[] param)
		{
			this.diengiai = param[0];
			this.thanhvien = param[1];
			this.trangthai = param[2];
			this.tungay = param[3];
			this.denngay = param[4];	
			this.lnhom = param[5];
		}
		
		public NhomsanphamList()
		{
			this.diengiai = "";
			this.thanhvien = "";
			this.trangthai = "";
			this.tungay = "";
			this.denngay = "";
			this.lnhom = "";
		}


		public String getDiengiai(){
			return this.diengiai;
		}
		
		public void setDiengiai(String diengiai){
			this.diengiai = diengiai;
		}
		
		public String getLoaithanhvien(){
			return this.thanhvien;
		}
		
		public void setLoaithanhvien(String thanhvien){
			this.thanhvien = thanhvien;
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
		

		public List<INhomsanpham> getNspList(){
			return this.nsplist;
		}
		
		public void setNspList(List<INhomsanpham> nsplist){
			this.nsplist = nsplist;
		}
		public boolean getSearch()
		{
			return this.search;
		}

		public void setSearch(boolean search)
		{
			this.search = search;
		}

		
		public String getLoainhom() {
			
			return lnhom;
		}

		
		public void setLoainhom(String lnhom) {
			
			this.lnhom = lnhom;
		}
		public String getView() {
			return view;
		}
		public void setView(String view) {
			this.view = view;
		}
	}
