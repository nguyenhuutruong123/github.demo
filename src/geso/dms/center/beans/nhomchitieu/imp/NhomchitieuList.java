package geso.dms.center.beans.nhomchitieu.imp;

import geso.dms.center.beans.nhomchitieu.INhomchitieu;
import geso.dms.center.beans.nhomchitieu.INhomchitieuList;
import geso.dms.center.util.Phan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public class NhomchitieuList extends Phan_Trang implements INhomchitieuList, Serializable
{
		private static final long serialVersionUID = -9217977556733610214L;

		// Tieu chi tim kiem
		private String diengiai;
		private String trangthai;		 
		private String tungay;
		private String denngay;
		public ResultSet Dsnkm;
	
	
		
		public NhomchitieuList(String[] param)
		{
			this.diengiai = param[0];
			this.trangthai = param[2];
			this.tungay = param[3];
			this.denngay = param[4];			
		}
		
		public NhomchitieuList()
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

	   public void setDsnkm(ResultSet Dsnkm) {
		 this.Dsnkm = Dsnkm;
			
		}
	   public void init()
	   {
		   String query="select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao," +
		    		" c.ten as nguoisua,a.TuNgay,a.DenNgay,a.TinhThuong from nhomsanpham a, nhanvien b, nhanvien c" +
		    		" where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ  and a.loainhom = '3' ";
		   
			geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
			if (this.diengiai.length() > 0)
			{
				query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";

			}

			if (tungay.length() > 0)
			{
				query = query + " and a.ngaytao >= '" + tungay + "'";

			}

			if (denngay.length() > 0)
			{
				query = query + " and a.ngaytao <= '" + denngay + "'";

			}

			if (trangthai.length() > 0)
			{
				query = query + " and a.trangthai = '" + trangthai + "'";

			}
		   System.out.println("trạng thái " + trangthai);
		  this.Dsnkm =createSplittingData(50, 10, "trangthai asc, ngaysua desc, pk_seq desc", query); 
	   }
	   

		
		public ResultSet getDsnkm() {
			return Dsnkm;
		}

		String userId;
		@Override
		public String getUserId()
		{
			return userId;
		}

		@Override
		public void setUserId(String userId)
		{
			this.userId =userId;
		}
		
	}
