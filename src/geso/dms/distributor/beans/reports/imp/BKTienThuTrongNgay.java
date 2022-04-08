package geso.dms.distributor.beans.reports.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.util.Utility;

import geso.dms.distributor.beans.reports.IBKTienThuTrongNgay;
import geso.dms.distributor.db.sql.dbutils;

public class BKTienThuTrongNgay implements IBKTienThuTrongNgay, Serializable {

	private String tuNgay;
	private String denNgay;
	private String userId;
	private ResultSet rsBKTienThuTrongNgay;
	private String nppID;
	private String ngayKS;
	

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		String sql = "SELECT KBH.TEN as kbh, PTT_KH.PK_SEQ AS so, PTT_NVGN.NGAYTHU as ngay, KH.PK_SEQ AS nguoiNopID, KH.TEN as nguoiNop, isnull(PTT_KH.SOTIEN, 0) AS  soTien ,isnull(PTT_KH.SODU, 0) as SODU, PTT_NVGN.TRANGTHAI"  										
			+" FROM PHIEUTHUTIEN_KH PTT_KH"										
			+" INNER JOIN KHACHHANG KH ON KH.PK_SEQ = PTT_KH.KHACHHANG_FK"										
			+" INNER JOIN KENHBANHANG KBH ON KH.KBH_FK = KBH.PK_SEQ"							
			+" INNER JOIN PHIEUTHUTIEN_NVGN PTT_NVGN ON PTT_NVGN.PK_SEQ = PTT_KH.PTTNVGN_FK"										
			+" WHERE NGAYTHU >= '"+this.tuNgay+"' AND NGAYTHU <= '"+this.denNgay+"'";
		dbutils db = new dbutils();
		System.out.println("Tien Thu Trong Ngay :"+sql);
		this.rsBKTienThuTrongNgay = db.get(sql);
	}
	
	

	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		
		try {
			if(rsBKTienThuTrongNgay != null)
				rsBKTienThuTrongNgay.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	@Override
	public void setTuNgay(String tuNgay) {
		// TODO Auto-generated method stub
		this.tuNgay = tuNgay;
		
	}

	@Override
	public String getTuNgay() {
		// TODO Auto-generated method stub
		return this.tuNgay;
	}

	@Override
	public void setDenNgay(String denNgay) {
		// TODO Auto-generated method stub
		this.denNgay = denNgay;
	}

	@Override
	public String getDenNgay() {
		// TODO Auto-generated method stub
		return this.denNgay;
	}

	@Override
	public ResultSet getBKTienThuTrongNgay() {
		// TODO Auto-generated method stub
		return this.rsBKTienThuTrongNgay;
		
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public String getNPPID() {
		// TODO Auto-generated method stub
		return this.nppID;
	}

	@Override
	public String getNgayKS() {
		// TODO Auto-generated method stub
		Utility ut = new Utility();
		this.nppID = ut.getIdNhapp(userId);
		dbutils db = new dbutils();
		ResultSet rs = db.get("select * from khoasongay where npp_fk='"+this.nppID+"'");
		if(rs != null)
		{
			try {
				rs.next();
				this.ngayKS = rs.getString("ngayks");
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{try {
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			
			
		}
		if(db != null)
			db.shutDown();
		return this.ngayKS;
	}

}
