package geso.dms.center.beans.baocaokehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.beans.baocaokehoach.IBCKeHoach;
import geso.dms.center.db.sql.dbutils;

public class BCKeHoach implements IBCKeHoach {
	String userId;
	String userTen;

	ResultSet npp;
	ResultSet vung;
	ResultSet khuvuc;
	ResultSet giamsatbanhang;
	ResultSet resultList;

	String year;
	String month;

	String nppId;
	String vungId;
	String khuvucId;
	String gsbanhangId;

	String msg;

	dbutils db;

	public BCKeHoach() {
		this.userId = "";
		this.userTen = "";
		this.msg = "";
		this.year ="";
		this.month="";
		db = new dbutils();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void creates() {
		String query = "";
		try {
			query = "select pk_seq, ten from nhaphanphoi";
			this.npp = db.get(query);
			query = "select pk_seq, ten from vung";
			this.vung = db.get(query);
			query = "select pk_seq, ten from khuvuc";
			this.khuvuc = db.get(query);
			query = "select pk_seq, ten from giamsatbanhang";
			this.giamsatbanhang = db.get(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		try {
			String query = "select * from (\r\n" + 
					"\r\n" + 
					"select gs.ten as tengsbh,DATEPART(DW, b.NAM+'-'+b.THANG+'-'+b.NGAy) thu ,b.NAM+'-'+b.THANG+'-'+b.NGAy as ngay,N'Đi NPP' loai,npp.ten as hangmuc,npp.diachi,isnull(b.GHICHU,b.GHICHU2) as ghichu\r\n" + 
					" from KEHOACHNV  a inner join  KEHOACHNV_NPP b\r\n" + 
					"on a.PK_SEQ=b.KEHOACHNV_FK\r\n" + 
					"inner join NHAPHANPHOI npp on npp.PK_SEQ=b.NPP_FK\r\n" + 
					"inner join NHANVIEN nv on nv.pk_seq=a.NHANVIEN_FK\r\n" + 
					"inner join GIAMSATBANHANG gs on gs.pk_seq=nv.gsbh_fk\r\n" + 
					"inner join GSBH_KHUVUC kv on kv.GSBH_FK=gs.PK_SEQ\r\n" + 
					"inner join KHUVUC kv2 on kv2.PK_SEQ=kv.KHUVUC_FK\r\n" + 
					"inner join VUNG v on v.PK_SEQ=kv2.VUNG_FK\r\n" + 
					"where a.TRANGTHAI=1" + " and a.THANG=" + this.month + " and a.NAM=" + this.year +
					" %s %s %s %s" +
					"\r\n" + 
					"union all\r\n" + 
					"\r\n" + 
					"select gs.ten as tengsbh,DATEPART(DW, b.NAM+'-'+b.THANG+'-'+b.NGAy) thu ,b.NAM+'-'+b.THANG+'-'+b.NGAy as ngay,N'Đi thị trường' loai,tt.ten as hangmuc, tt.ten +'-'+qh.TEN as diachi,isnull(b.GHICHU,b.GHICHU2) as ghichu\r\n" + 
					" from KEHOACHNV  a inner join  KEHOACHNV_THITRUONG b\r\n" + 
					"on a.PK_SEQ=b.KEHOACHNV_FK\r\n" + 
					"inner join tinhthanh tt on tt.PK_SEQ=b.TINH_FK\r\n" + 
					"inner join QUANHUYEN qh on qh.PK_SEQ=b.QUANHUYEN_FK\r\n" + 
					"inner join NHANVIEN nv on nv.pk_seq=a.NHANVIEN_FK\r\n" + 
					"inner join GIAMSATBANHANG gs on gs.pk_seq=nv.gsbh_fk\r\n" + 
					"inner join GSBH_KHUVUC kv on kv.GSBH_FK=gs.PK_SEQ\r\n" + 
					"inner join KHUVUC kv2 on kv2.PK_SEQ=kv.KHUVUC_FK\r\n" + 
					"inner join VUNG v on v.PK_SEQ=kv2.VUNG_FK\r\n" + 
					"where a.TRANGTHAI=1" + " and a.THANG=" + this.month + " and a.NAM=" + this.year +
					" %s %s %s" +
					"\r\n" + 
					"union all\r\n" + 
					"\r\n" + 
					"select gs.ten as tengsbh,DATEPART(DW, b.NAM+'-'+b.THANG+'-'+b.NGAy) thu ,b.NAM+'-'+b.THANG+'-'+b.NGAy as ngay,N'Đi hỗ trợ NVBH' loai,ddkd.ten as hangmuc, tbh.DIENGIAI as diachi,isnull(b.GHICHU,'') as ghichu\r\n" + 
					" from KEHOACHNV  a inner join  KEHOACHNV_TBH b\r\n" + 
					"on a.PK_SEQ=b.KEHOACHNV_FK\r\n" + 
					"inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=b.ddkd_fk\r\n" + 
					"inner join TUYENBANHANG tbh on tbh.PK_SEQ=b.TUYEN_FK\r\n" + 
					"inner join NHANVIEN nv on nv.pk_seq=a.NHANVIEN_FK\r\n" + 
					"inner join GIAMSATBANHANG gs on gs.pk_seq=nv.gsbh_fk\r\n" + 
					"inner join GSBH_KHUVUC kv on kv.GSBH_FK=gs.PK_SEQ\r\n" + 
					"INNER JOIN KHUVUC KV2 ON KV2.PK_SEQ=KV.KHUVUC_FK\r\n" + 
					"INNER JOIN VUNG V ON V.PK_SEQ=KV2.VUNG_FK\r\n" + 
					"where a.TRANGTHAI=1" + " and a.THANG=" + this.month + " and a.NAM=" + this.year +
					" %s %s %s" +
					"\r\n" + 
					") as data\r\n" + 
					"order by tengsbh,ngay ASC";
			String gsbhId = " and nv.GSBH_FK is not null";
			String khuvucFk = "";
			String vungSeq = "";
			String npp = "";
			if(!gsbanhangId.equals("") && !gsbanhangId.equals("All") && gsbanhangId != null) {
				gsbhId = " and nv.GSBH_FK=" + this.gsbanhangId;
			}
			if(!khuvucId.equals("") && !khuvucId.equals("All") && khuvucId != null) {
				khuvucFk = " and kv.KHUVUC_FK=" + this.khuvucId;
			}
			if(!vungId.equals("") && !vungId.equals("All") && vungId != null) {
				vungSeq = " and v.PK_SEQ=" + this.vungId;
			}
			if(!nppId.equals("") && !nppId.equals("All") && nppId != null) {
				npp = " and npp.PK_SEQ=" + this.nppId;
			}
			String output = String.format(query, gsbhId, khuvucFk, vungSeq, npp, gsbhId, khuvucFk, vungSeq, gsbhId, khuvucFk, vungSeq);
			this.resultList = db.get(output);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	public void init() {
//		try {
//			String query = "select * from (\r\n" + 
//					"\r\n" + 
//					"select gs.ten as tengsbh,DATEPART(DW, b.NAM+'-'+b.THANG+'-'+b.NGAy) thu ,b.NAM+'-'+b.THANG+'-'+b.NGAy as ngay,N'Đi NPP' loai,npp.ten as hangmuc,npp.diachi,isnull(b.GHICHU,b.GHICHU2) as ghichu\r\n" + 
//					" from KEHOACHNV  a inner join  KEHOACHNV_NPP b\r\n" + 
//					"on a.PK_SEQ=b.KEHOACHNV_FK\r\n" + 
//					"inner join NHAPHANPHOI npp on npp.PK_SEQ=b.NPP_FK\r\n" + 
//					"inner join NHANVIEN nv on nv.pk_seq=a.NHANVIEN_FK\r\n" + 
//					"inner join GIAMSATBANHANG gs on gs.pk_seq=nv.gsbh_fk\r\n" + 
//					"inner join GSBH_KHUVUC kv on kv.GSBH_FK=gs.PK_SEQ\r\n" + 
//					"where a.TRANGTHAI=1--and a.THANG=06 and a.NAM=2018 \r\n" + 
//					"and nv.GSBH_FK is not null --and kv.KHUVUC_FK in (100140,100141)\r\n" + 
//					"\r\n" + 
//					"union all\r\n" + 
//					"\r\n" + 
//					"select gs.ten as tengsbh,DATEPART(DW, b.NAM+'-'+b.THANG+'-'+b.NGAy) thu ,b.NAM+'-'+b.THANG+'-'+b.NGAy as ngay,N'Đi thị trường' loai,tt.ten as hangmuc, tt.ten +'-'+qh.TEN as diachi,isnull(b.GHICHU,b.GHICHU2) as ghichu\r\n" + 
//					" from KEHOACHNV  a inner join  KEHOACHNV_THITRUONG b\r\n" + 
//					"on a.PK_SEQ=b.KEHOACHNV_FK\r\n" + 
//					"inner join tinhthanh tt on tt.PK_SEQ=b.TINH_FK\r\n" + 
//					"inner join QUANHUYEN qh on qh.PK_SEQ=b.QUANHUYEN_FK\r\n" + 
//					"inner join NHANVIEN nv on nv.pk_seq=a.NHANVIEN_FK\r\n" + 
//					"inner join GIAMSATBANHANG gs on gs.pk_seq=nv.gsbh_fk\r\n" + 
//					"inner join GSBH_KHUVUC kv on kv.GSBH_FK=gs.PK_SEQ\r\n" + 
//					"where a.TRANGTHAI=1\r\n" + 
//					" --and a.THANG=06 and a.NAM=2018\r\n" + 
//					" and nv.GSBH_FK is not null and kv.KHUVUC_FK in (100140,100141)\r\n" + 
//					"\r\n" + 
//					"\r\n" + 
//					"union all\r\n" + 
//					"\r\n" + 
//					"select gs.ten as tengsbh,DATEPART(DW, b.NAM+'-'+b.THANG+'-'+b.NGAy) thu ,b.NAM+'-'+b.THANG+'-'+b.NGAy as ngay,N'Đi hỗ trợ NVBH' loai,ddkd.ten as hangmuc, tbh.DIENGIAI as diachi,isnull(b.GHICHU,'') as ghichu\r\n" + 
//					" from KEHOACHNV  a inner join  KEHOACHNV_TBH b\r\n" + 
//					"on a.PK_SEQ=b.KEHOACHNV_FK\r\n" + 
//					"inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=b.ddkd_fk\r\n" + 
//					"inner join TUYENBANHANG tbh on tbh.PK_SEQ=b.TUYEN_FK\r\n" + 
//					"inner join NHANVIEN nv on nv.pk_seq=a.NHANVIEN_FK\r\n" + 
//					"inner join GIAMSATBANHANG gs on gs.pk_seq=nv.gsbh_fk\r\n" + 
//					"inner join GSBH_KHUVUC kv on kv.GSBH_FK=gs.PK_SEQ\r\n" + 
//					"where a.TRANGTHAI=1\r\n" + 
//					"--and a.THANG=06 and a.NAM=2018 \r\n" + 
//					"and nv.GSBH_FK is not null and kv.KHUVUC_FK in (100140,100141)\r\n" + 
//					"\r\n" + 
//					") as data\r\n" + 
//					"order by tengsbh,ngay ASC";
//			resultList = db.get(query);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	public ResultSet getResultList() {
		return resultList;
	}

	public void DBclose() {
		try {
			if (npp != null) {
				npp.close();
			}
			if (vung != null) {
				vung.close();
			}
			if (khuvuc != null) {
				khuvuc.close();
			}
			if (giamsatbanhang != null) {
				giamsatbanhang.close();
			}
			if (db != null) {
				db.shutDown();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getVungId() {
		return vungId;
	}

	public void setVungId(String vungId) {
		this.vungId = vungId;
	}

	public String getKhuvucId() {
		return khuvucId;
	}

	public void setKhuvucId(String khuvucId) {
		this.khuvucId = khuvucId;
	}

	public String getGsbanhangId() {
		return gsbanhangId;
	}

	public void setGsbanhangId(String gsbanhangId) {
		this.gsbanhangId = gsbanhangId;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserTen() {
		return userTen;
	}

	public void setUserTen(String userTen) {
		this.userTen = userTen;
	}

	public ResultSet getNpp() {
		return npp;
	}

	public void setNpp(ResultSet npp) {
		this.npp = npp;
	}

	public ResultSet getVung() {
		return vung;
	}

	public void setVung(ResultSet vung) {
		this.vung = vung;
	}

	public ResultSet getKhuvuc() {
		return khuvuc;
	}

	public void setKhuvuc(ResultSet khuvuc) {
		this.khuvuc = khuvuc;
	}

	public ResultSet getGiamsatbanhang() {
		return giamsatbanhang;
	}

	public void setGiamsatbanhang(ResultSet giamsatbanhang) {
		this.giamsatbanhang = giamsatbanhang;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}


}
