package geso.dms.center.beans.kehoachnhanvien.imp;

import geso.dms.center.beans.kehoachnhanvien.IKeHoachNhanVien;
import geso.dms.center.beans.kehoachnhanvien.IKeHoachNhanVienChiTiet;
import geso.dms.center.beans.kehoachnhanvien.IKeHoachNhanVienNgay;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class KeHoachNhanVien implements IKeHoachNhanVien
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String tennhanvien;
	String thang;
	String nam;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String trangthai = "0";
	String msg;
	
	IKeHoachNhanVienNgay[] ngayList;
	
	ResultSet nppRs, tinhRs, quanRs;
	
	dbutils db;
	private ResultSet ddkdRs;
	private ResultSet tbhRs;
	
	public KeHoachNhanVien(String userId, String id)
	{
		int nam = Integer.parseInt(getDateTime("yyyy"));
		int thang = Integer.parseInt(getDateTime("MM"));
		
		this.db = new dbutils();
		this.userId = userId;
		this.id = id;
		this.tennhanvien = "";
		this.thang = "" + thang;
		this.nam = "" + nam;
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.ngayList = new IKeHoachNhanVienNgay[0];
		
		//this.init();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getTenNhanVien() 
	{
		return this.tennhanvien;
	}

	public void setTenNhanVien(String tennhanvien) 
	{
		this.tennhanvien = tennhanvien;
	}
	
	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNguoitao() 
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;
	}

	public String getNgaysua() 
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoisua() 
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public boolean create()
	{
		try {
			String query;
			
			int thang = 0, nam = 0;
			try { 
				thang = Integer.parseInt(this.thang); 
			} catch(Exception e) {
				this.msg = "Th??ng nh???p v??o kh??ng ????ng ?????nh d???ng (" + this.thang + ")";
				return false;
			}
			if(thang < 1 || thang > 12) {
				this.msg = "Th??ng nh???p v??o kh??ng ????ng ?????nh d???ng (" + thang + ")";
				return false;
			}
			
			try { 
				nam = Integer.parseInt(this.nam); 
			} catch(Exception e) {
				this.msg = "N??m nh???p v??o kh??ng ????ng ?????nh d???ng (" + nam + ")";
				return false;
			}
			if(nam == 0) {
				this.msg = "L???i, Error code = 0";
				return false;
			}
			
			if(this.ngayList == null || this.ngayList.length <= 0) {
				this.msg = "Th??ng tin k??? ho???ch chi ti???t ??ang r???ng";
				return false;
			}
			
			//String tgkehoach = thang < 10 ? nam + "-0" + thang : nam + "-" + thang; 
			//String tghientai = getDateTime("yyyy-MM");
			
			String sthang;
			if(thang < 10)
				sthang = "0"+thang;
			else
				sthang = ""+thang;
			
			//Ki???m tra xem nh??n vi??n ???? t???ng t???o k??? ho???ch c???a n??m-th??ng ???? ch??a
			query = " SELECT PK_SEQ FROM KEHOACHNV WHERE NHANVIEN_FK = " + this.userId + " AND THANG = '" + sthang + "' AND NAM = " + nam;
			ResultSet rs = db.get(query);
			if(rs != null) {
				try {
					if(rs.next()) {
						rs.close();
						this.msg = "K??? ho???ch th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n "+this.tennhanvien+" ???? ???????c t???o tr?????c ????!";
						return false;
					}

					rs.close();
				} catch(Exception e) {
					this.msg = "X???y ra l???i khi ki???m tra th??ng tin k??? ho???ch (" + e.getMessage() + ")";
					return false;
				}
			}
			
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			
			this.db.getConnection().setAutoCommit(false);
			
			//Insert kehoachnv
			query = " INSERT KEHOACHNV(NHANVIEN_FK, THANG, NAM, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA) " +
					" SELECT '" + this.userId + "', '" + sthang + "', '" + nam + "', '" + this.ngaytao + "', '" + this.ngaytao + "', '" + this.nguoitao + "', " + this.nguoitao + " ";
			if (!db.update(query)) {
				this.msg = "Kh??ng th??? th??m m???i k??? ho???ch th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + "(" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			//L???y id v???a t???o
			query = " SELECT IDENT_CURRENT('KEHOACHNV') AS ID";
			rs = this.db.get(query);
			if(rs != null) {
				try {
					rs.next();
					this.id = rs.getString("ID");
					rs.close();
				} catch(Exception e) {
					this.msg = "X???y ra l???i khi l???y th??ng tin m?? k??? ho???ch v???a t???o (" + e.getMessage() + ")";
					db.getConnection().rollback();
					return false;
				}
			} else {
				this.msg = "Kh??ng th??? l???y m?? k??? ho???ch v???a t???o !";
				db.getConnection().rollback();
				return false;
			}
			
			//Insert chi ti???t
			IKeHoachNhanVienNgay ngay;
			List<IKeHoachNhanVienChiTiet> nppList, thitruongList, tbhList;
			IKeHoachNhanVienChiTiet chitiet;
			for(int i = 0; i < this.ngayList.length; i++) {
				ngay = this.ngayList[i];
				
				if(ngay != null && ngay.getNgay().length() > 0) {
					//Insert nh?? ph??n ph???i
					nppList = ngay.getNppList();
					String sngay = ngay.getNgay();
					if(Integer.parseInt(sngay) < 10)
						sngay = "0" + sngay;
					if(nppList!=null) {
						for(int j = 0; j < nppList.size(); j++) {
							chitiet = nppList.get(j);
							
							query = " INSERT KEHOACHNV_NPP(KEHOACHNV_FK, NGAY, NPP_FK, GHICHU, THANG, NAM) " +
									" SELECT '" + this.id + "', '" + sngay + "', '" + chitiet.getNppId() + "', N'" + chitiet.getGhiChu() + "','" + sthang + "', '" + nam + "'";
							if (!db.update(query)) {
								this.msg = "Kh??ng th??? th??m nh?? ph??n ph???i '" + chitiet.getNppId() + "' v??o k??? ho???ch ng??y " + sngay + " th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + "(" + query + ")";
								db.getConnection().rollback();
								return false;
							}
						}
					}
					
					//Insert th??? tr?????ng
					thitruongList = ngay.getThiTruongList();
					if(thitruongList!=null) {
						for(int j = 0; j < thitruongList.size(); j++) {
							chitiet = thitruongList.get(j);
							query = " INSERT KEHOACHNV_THITRUONG(KEHOACHNV_FK, NGAY, TINH_FK, QUANHUYEN_FK, GHICHU, THANG, NAM) " +
									" SELECT '" + this.id + "', '" + sngay + "', '" + chitiet.getTinhId() + "', '" + chitiet.getQuanHuyenId() + "', N'" + chitiet.getGhiChu() + "', '" + sthang + "', '" + nam + "'";
							System.out.println(query);
							
							if (!db.update(query)) {
								this.msg = "Kh??ng th??? th??m th??? tr?????ng '" + chitiet.getTinhId() + "', '" + chitiet.getQuanHuyenId() + "' v??o k??? ho???ch ng??y " + chitiet.getNgay() + " th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + "(" + query + ")";
								db.getConnection().rollback();
								return false;
							}
						}
					}
					
					tbhList = ngay.getTBHList();
					if(tbhList != null){
						for(int j = 0;j<tbhList.size(); j++){
							chitiet = tbhList.get(j);
							query = "insert into KEHOACHNV_TBH(GHICHU,KEHOACHNV_FK, NGAY, NPP_FK, DDKD_FK, TUYEN_FK, THANG, NAM) " +
									"VALUES (N'" + chitiet.getGhiChuTBH()+ "','" + this.id + "','" + sngay + "','" + chitiet.getNppTBHId() + "','" + chitiet.getDDKDId() + "','" + chitiet.getTBHId() + "', '" + sthang + "', '" + nam + "')";
							System.out.println("KEHOACHNV_TBH = " + query);
							if (!db.update(query)) {
								this.msg = "Kh??ng th??? th??m tuy???n b??n h??ng th??? '" +ngay.getNgay() + " v??o k??? ho???ch ng??y " + chitiet.getNgay() + " th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + "(" + query + ")";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			this.msg = "X???y ra l???i khi th??m m???i k??? ho???ch nh??n vi??n (" + er.getMessage() + ")";
			try {
				db.getConnection().rollback();
			} catch (Exception e) { }
			return false;
		}
		return true;
	}

	public boolean update() 
	{
		try {
			String query;
			
			int thangHienTai = Integer.parseInt(getDateTime("MM")),
				namHienTai = Integer.parseInt(getDateTime("yyyy")),
				ngayHienTai = Integer.parseInt(getDateTime("dd"));
			int thang = 0, nam = 0;
			try { 
				thang = Integer.parseInt(this.thang); 
			} catch(Exception e) {
				this.msg = "Th??ng nh???p v??o kh??ng ????ng ?????nh d???ng (" + this.thang + ")";
				return false;
			}
			if(thang < 1 || thang > 12) {
				this.msg = "Th??ng nh???p v??o kh??ng ????ng ?????nh d???ng (" + thang + ")";
				return false;
			}
			
			try { 
				nam = Integer.parseInt(this.nam); 
			} catch(Exception e) {
				this.msg = "N??m nh???p v??o kh??ng ????ng ?????nh d???ng (" + nam + ")";
				return false;
			}
			if(nam == 0) {
				this.msg = "L???i, Error code = 0";
				return false;
			}
			if(this.ngayList == null || this.ngayList.length <= 0) {
				this.msg = "Th??ng tin k??? ho???ch chi ti???t ??ang r???ng";
				return false;
			}
			String sthang = thang < 10 ? "0" + thang : thang + "";
			String snam = this.nam;
			
			//Ki???m tra xem nh??n vi??n ???? t???ng t???o k??? ho???ch c???a n??m-th??ng ???? ch??a
			query = " SELECT * FROM KEHOACHNV WHERE PK_SEQ = " + this.id + " AND NHANVIEN_FK = " + this.userId;
			ResultSet rs = db.get(query);
			if(rs != null) {
				try {
					if(rs.next()) {
						int tmpThang = rs.getInt("THANG");
						int tmpNam = rs.getInt("NAM");
						sthang = tmpThang < 10 ? "0" + tmpThang : tmpThang + "";
						snam = tmpNam + "";
						if(tmpThang != thang || tmpNam != nam){
							rs.close();
							this.msg = "B???n kh??ng ???????c ?????i th??ng/ n??m.";
							return false;
						}
					}

					rs.close();
				} catch(Exception e) {
					this.msg = "X???y ra l???i khi ki???m tra th??ng tin k??? ho???ch (" + e.getMessage() + ")";
					return false;
				}
			}
			
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			
			this.db.getConnection().setAutoCommit(false);
			
			//Update kehoachnv
			query = " UPDATE KEHOACHNV " +
					" SET NGAYSUA = '" + this.ngaysua + "', NGUOISUA = " + this.nguoisua + " " +
					" WHERE PK_SEQ = " + this.id;
			if (!db.update(query)) {
				this.msg = "Kh??ng th??? c???p nh???t k??? ho???ch th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + "(" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			/*String queryNgay = 
				" SELECT DISTINCT NGAY FROM KEHOACHNV_NPP WHERE LAT IS NOT NULL AND LONG IS NOT NULL AND KEHOACHNV_FK = " + this.id +
				" UNION " +
				" SELECT DISTINCT NGAY FROM KEHOACHNV_THITRUONG WHERE LAT IS NOT NULL AND LONG IS NOT NULL AND KEHOACHNV_FK = " + this.id;
			List<String> ngays = new ArrayList<String>();
			rs = db.get(queryNgay);
			try {
				while(rs.next()) {
					ngays.add(rs.getString("NGAY"));
				}
				rs.close();
			} catch(Exception e) {
				this.msg = "X???y ra l???i khi l???y th??ng tin k??? ho???ch (" + e.getMessage() + ")";
				return false;
			}*/
			
			
			
			//X??a chi ti???t c??
			//query = " DELETE KEHOACHNV_NPP WHERE KEHOACHNV_FK = " + this.id + " and fulldate >= '"+Utility.getNgayHienTai()+"'  ";
			query = " DELETE KEHOACHNV_NPP WHERE KEHOACHNV_FK = " + this.id + "   ";
			if (!db.update(query)) {
				this.msg = "Kh??ng th??? c???p nh???t k??? ho???ch th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + "(" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			query = " DELETE KEHOACHNV_THITRUONG WHERE KEHOACHNV_FK = " + this.id + "   ";
			if (!db.update(query)) {
				this.msg = "Kh??ng th??? c???p nh???t k??? ho???ch th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + "(" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			query = " DELETE KEHOACHNV_TBH WHERE KEHOACHNV_FK = " + this.id + "    ";
			if (!db.update(query)) {
				this.msg = "Kh??ng th??? c???p nh???t k??? ho???ch th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + "(" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			//Th??m chi ti???t m???i
			IKeHoachNhanVienNgay ngay;
			List<IKeHoachNhanVienChiTiet> nppList, thitruongList, tbhList;
			IKeHoachNhanVienChiTiet chitiet;
			for(int i = 0; i < this.ngayList.length; i++) {
				ngay = this.ngayList[i];
				String sngay = ngay.getNgay();
				if(Integer.parseInt(sngay) < 10)
					sngay = "0" + sngay;
				
				
				//N???u s???a k??? ho???ch th??ng hi???n t???i, n??m hi???n t???i th?? ch??? ???????c s???a ng??y >= ng??y hi???n t???i
				String ngayluu = snam +"-" +sthang + "-" + sngay; 
				//boolean condition = ngayluu.compareTo( Utility.getNgayHienTai() ) >= 0;
				//System.out.println(thang + ", " + thangHienTai + ", " + nam + ", " + namHienTai + ", " + ngay.getNgay() + ", " + ngay.getNgay() + ", " + (Integer.parseInt(ngay.getNgay()) >= ngayHienTai));
				boolean condition =true;
				
				
				if(ngay != null && condition) {
					//Insert nh?? ph??n ph???i
					nppList = ngay.getNppList();
					if(nppList!=null) {
						for(int j = 0; j < nppList.size(); j++) {
							chitiet = nppList.get(j);
							query = " INSERT KEHOACHNV_NPP(KEHOACHNV_FK, NGAY, NPP_FK, GHICHU, THANG, NAM) " +
									" SELECT '" + this.id + "', '" + sngay + "', '" + chitiet.getNppId() + "', N'" + chitiet.getGhiChu() + "', '"+sthang+"', '"+snam+"'";
							if (!db.update(query)) {
								this.msg = "Kh??ng th??? th??m nh?? ph??n ph???i '" + chitiet.getNppId() + "' v??o k??? ho???ch ng??y " + chitiet.getNgay() + " th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + "(" + query + ")";
								db.getConnection().rollback();
								return false;
							}
						}
					}
					
					//Insert th??? tr?????ng
					thitruongList = ngay.getThiTruongList();
					if(thitruongList!=null) {
						for(int j = 0; j < thitruongList.size(); j++) {
							chitiet = thitruongList.get(j);
							query = " INSERT KEHOACHNV_THITRUONG(KEHOACHNV_FK, NGAY, TINH_FK, QUANHUYEN_FK, GHICHU, THANG, NAM) " +
									" SELECT '" + this.id + "', '" + sngay + "', '" + chitiet.getTinhId() + "', '" + chitiet.getQuanHuyenId() + "', N'" + chitiet.getGhiChu() + "', '"+sthang+"', '"+snam+"' ";
							if (!db.update(query)) {
								this.msg = "Kh??ng th??? th??m th??? tr?????ng '" + chitiet.getTinhId() + "', '" + chitiet.getQuanHuyenId() + "' v??o k??? ho???ch ng??y " + chitiet.getNgay() + " th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + "(" + query + ")";
								db.getConnection().rollback();
								return false;
							}
						}
					}
					tbhList = ngay.getTBHList();
					if(tbhList != null){
						for(int j = 0;j<tbhList.size(); j++){
							chitiet = tbhList.get(j);
							query = "insert into KEHOACHNV_TBH(GHICHU,KEHOACHNV_FK, NGAY, NPP_FK, DDKD_FK, TUYEN_FK, THANG, NAM) " +
									"VALUES ( N'" + chitiet.getGhiChuTBH() + "','" + this.id + "','" + sngay + "','" + chitiet.getNppTBHId() + "','" + chitiet.getDDKDId() + "','" + chitiet.getTBHId() + "', '"+sthang+"', '"+snam+"')";
							System.out.println("KEHOACHNV_TBH = " + query);
							if (!db.update(query)) {
								this.msg = "Kh??ng th??? th??m tuy???n b??n h??ng v??o k??? ho???ch ng??y " + chitiet.getNgay() + " th??ng " + thang + " n??m " + nam + " c???a nh??n vi??n " + this.tennhanvien + ". Vui l??ng ch???n l???i.";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			query = " UPDATE KEHOACHNV_TBH SET FULLDATE =  NAM +'-' + THANG + '-' + NGAY WHERE FULLDATE IS NULL ";			
			if (!db.update(query)) {
				this.msg = "l???i";
				db.getConnection().rollback();
				return false;
			}
			
			query = " UPDATE KEHOACHNV_THITRUONG SET FULLDATE =  NAM +'-' + THANG + '-' + NGAY WHERE FULLDATE IS NULL ";			
			if (!db.update(query)) {
				this.msg = "l???i";
				db.getConnection().rollback();
				return false;
			}
			
			
			query = " UPDATE KEHOACHNV_NPP SET FULLDATE =  NAM +'-' + THANG + '-' + NGAY WHERE FULLDATE IS NULL ";			
			if (!db.update(query)) {
				this.msg = "l???i";
				db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			er.printStackTrace();
			this.msg = "X???y ra l???i khi c???p nh???t k??? ho???ch nh??n vi??n (" + er.getMessage() + ")";
			try {
				db.getConnection().rollback();
			} catch (Exception e) { }
			return false;
		}
		return true;
	}
	
	public void init(boolean display) 
	{
		if(id != null && id.length() > 0) 
		{
			String 
			query = " SELECT KH.PK_SEQ, KH.NHANVIEN_FK, NV.TEN AS NHANVIEN, KH.THANG, KH.NAM, ISNULL(KH.TRANGTHAI, 0) AS TRANGTHAI " +
					" FROM KEHOACHNV KH " +
					" INNER JOIN NHANVIEN NV ON NV.PK_SEQ = KH.NHANVIEN_FK WHERE KH.PK_SEQ = " + id;
			
	        ResultSet rs =  db.get(query);
	        try
	        {
	        	if(rs.next())
	        	{
	        		this.id = rs.getString("PK_SEQ");
	        		this.tennhanvien = rs.getString("NHANVIEN");
	        		this.thang = rs.getString("thang");
	        		this.nam = rs.getString("nam");
	        	}
	        	rs.close();
	        	System.out.println("Thang = " + this.thang);
	        	if(this.thang != null && this.thang.trim().length() > 0 && this.nam != null && this.nam.trim().length() > 0)
				{
					try
					{
						int thang = Integer.parseInt(this.thang);
						int nam = Integer.parseInt(this.nam);
						
						Calendar mycal = new GregorianCalendar(nam, thang-1, 1);
						int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
						System.out.println("daysInMonth = " + daysInMonth);
						ngayList = new IKeHoachNhanVienNgay[daysInMonth];
						
						for(int i = 0; i < daysInMonth; i++) 
						{
							IKeHoachNhanVienNgay khNgay = new KeHoachNhanVienNgay("");
							khNgay.setNgay(String.valueOf(i+1));
							ngayList[i] = khNgay;
						}
					}
					catch(Exception e) 
					{
						 e.printStackTrace();
					}
				}
	        	
	        	query = " SELECT KHNPP.KEHOACHNV_FK, KHNPP.NGAY, KHNPP.NPP_FK, ISNULL(KHNPP.LAT, '') AS LAT, ISNULL(KHNPP.LONG, '') AS LONG, ISNULL(KHNPP.GHICHU, '') AS GHICHU, ISNULL(KHNPP.GHICHU2, '') AS GHICHU2, NPP.TEN AS NPP_TEN, " +
	        			"'T1 ' + isnull(CONVERT(varchar(5), THOIDIEMDEN, 108),'00:00') + ' - ' + isnull(CONVERT(varchar(5), THOIDIEMDI, 108),'00:00') + ' * T2 ' + isnull(CONVERT(varchar(5), THOIDIEMDEN2, 108),'00:00') + ' - ' + isnull(CONVERT(varchar(5), THOIDIEMDI2, 108),'00:00') AS TIME " +
						" FROM KEHOACHNV_NPP KHNPP" +
						" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = KHNPP.NPP_FK " +
						" WHERE KEHOACHNV_FK = " + this.id +
						" ORDER BY NGAY ";
	        	System.out.println("VTNPP:"+query);
	        	rs =  db.get(query);
	        	try 
        		{
        			while(rs.next()) {
        				int ngay = rs.getInt("NGAY");
        				IKeHoachNhanVienNgay khNgay = ngayList[ngay-1];
        				
        				IKeHoachNhanVienChiTiet chitiet = new KeHoachNhanVienChiTiet(this.id);
						chitiet.setNgay(rs.getString("NGAY"));
						chitiet.setNppId(display ? rs.getString("NPP_TEN") : rs.getString("NPP_FK"));
						chitiet.setLat(rs.getString("LAT"));
						chitiet.setLon(rs.getString("LONG"));
						chitiet.setGhiChu(rs.getString("GHICHU"));
						chitiet.setThoidiem("(" + rs.getString("TIME") + ")");
						khNgay.getNppList().add(chitiet);
        			}
        			rs.close();
        		}
        		catch(Exception e) 
        		{
        			e.printStackTrace();
        		}
	        	
	        	query = " SELECT KHTT.KEHOACHNV_FK, KHTT.NGAY, KHTT.TINH_FK, ISNULL(TT.TEN, '') AS TINH_TEN, KHTT.QUANHUYEN_FK, ISNULL(QH.TEN, '') AS QUANHUYEN_TEN, ISNULL(KHTT.LAT, '') AS LAT, ISNULL(KHTT.LONG, '') AS LONG, ISNULL(KHTT.DIACHI, '') AS DIACHI, ISNULL(KHTT.GHICHU, '') AS GHICHU, ISNULL(KHTT.GHICHU2, '') AS GHICHU2 " +
						" FROM KEHOACHNV_THITRUONG KHTT " +
						" INNER JOIN TINHTHANH TT ON TT.PK_SEQ = KHTT.TINH_FK " +
						" INNER JOIN QUANHUYEN QH ON QH.PK_SEQ = KHTT.QUANHUYEN_FK " +
						" WHERE KEHOACHNV_FK = " + this.id +
						" ORDER BY NGAY ";
	        	System.out.println("VTTT:"+query);
	        	rs =  db.get(query);
	        	try 
        		{
        			while(rs.next()) {
        				int ngay = rs.getInt("NGAY");
        				IKeHoachNhanVienNgay khNgay = ngayList[ngay-1];
        				
        				IKeHoachNhanVienChiTiet chitiet = new KeHoachNhanVienChiTiet(this.id);
						chitiet.setNgay(rs.getString("NGAY"));
						chitiet.setTinhId(display ? rs.getString("TINH_TEN") : rs.getString("TINH_FK"));
						chitiet.setQuanHuyenId(display ? rs.getString("QUANHUYEN_TEN") : rs.getString("QUANHUYEN_FK"));
						chitiet.setLat(rs.getString("LAT"));
						chitiet.setLon(rs.getString("LONG"));
						chitiet.setDiaChi(rs.getString("DIACHI"));
						chitiet.setGhiChu(rs.getString("GHICHU"));
						chitiet.setGhiChu2(rs.getString("GHICHU2"));
						
						khNgay.getThiTruongList().add(chitiet);
        			}
        			rs.close();
        		}
        		catch(Exception e) 
        		{
        			e.printStackTrace();
        		}
        		
        		query = "\n select tbh.PK_SEQ, tbh.NPP_FK, p.TEN as TEN_NPP, tbh.DDKD_FK, kd.TEN AS TEN_DDKD, t.PK_SEQ AS TUYEN_FK, ISNULL(t.DIENGIAI,N'Kh??ng x??c ?????nh') as TEN_TUYEN, tbh.NGAY, isnull(tbh.GHICHU,'') as GHICHU, " +
        				"\n (select COUNT(*) from KEHOACHNV_TBH_KHACHHANG where KEHOACHNV_TBH_FK = tbh.PK_SEQ) as DAVT, " +
    					"\n isnull((select SOKH from DDKD_SOKH where DDKD_FK = tbh.DDKD_FK and YEAR(NGAY) = tbh.NAM and MONTH(NGAY) = tbh.THANG and DAY(NGAY) = tbh.NGAY ),0) as SOKH " +
						"\n from KEHOACHNV_TBH tbh join NHAPHANPHOI p on p.PK_SEQ = tbh.NPP_FK " +
						"\n join DAIDIENKINHDOANH kd on kd.PK_SEQ = tbh.DDKD_FK " +
						"\n LEFT join TUYENBANHANG t on t.PK_SEQ = tbh.TUYEN_FK AND t.DDKD_FK = tbh.DDKD_FK " +
						"\n where tbh.KEHOACHNV_FK = '"+this.id+"' order by tbh.NGAY";
        		System.out.println("VTWW:"+query);
        		System.out.println(" tbh list=  "+ query);
        		rs =  db.get(query);
	        	try 
        		{
        			while(rs.next()) {
        				int ngay = rs.getInt("NGAY");
        				IKeHoachNhanVienNgay khNgay = ngayList[ngay-1];
        				
        				IKeHoachNhanVienChiTiet chitiet = new KeHoachNhanVienChiTiet(this.id);
						chitiet.setNgay(rs.getString("NGAY"));
						chitiet.setNppTBHId(display ? rs.getString("TEN_NPP") : rs.getString("NPP_FK"));
						chitiet.setDDKDId(display ? rs.getString("TEN_DDKD") : rs.getString("DDKD_FK"));
						chitiet.setTBHId(display? rs.getString("TEN_TUYEN") : rs.getString("TUYEN_FK"));
						chitiet.setGhiChuTBH(rs.getString("GHICHU"));
						chitiet.setTyle("(" +rs.getString("DAVT")+ "/" + rs.getString("SOKH") + ")");
						khNgay.getTBHList().add(chitiet);
        			}
        			rs.close();
        		}
        		catch(Exception e) 
        		{
        			e.printStackTrace();
        		}
	       	}
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
		}
		else 
		{
			String query = " select ten from nhanvien where pk_seq = " + this.userId;
			ResultSet rs =  db.get(query);
    		try 
    		{
    			if(rs.next()) 
    			{
    				this.tennhanvien = rs.getString("ten");
    			}
    		}
	        catch(Exception e) 
	        {
	        	e.printStackTrace();
	        }
	        
			if(this.thang != null && this.thang.trim().length() > 0 && this.nam != null && this.nam.trim().length() > 0)
			{
				try
				{
					int thang = Integer.parseInt(this.thang);
					int nam = Integer.parseInt(this.nam);
					Calendar mycal = new GregorianCalendar(nam, thang-1, 1);
					int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
					
					ngayList = new IKeHoachNhanVienNgay[daysInMonth];
					
					for(int i = 0; i < daysInMonth; i++) 
					{
						IKeHoachNhanVienNgay khNgay = new KeHoachNhanVienNgay("");
						khNgay.setNgay(String.valueOf(i+1));
						ngayList[i] = khNgay;
					}
				}
				catch(Exception e) 
				{
					 e.printStackTrace();
				}
			}
		}
	}

	public void init()
	{
		init(false);
	}

	@Override
	public ResultSet getNppRs() {
		return this.nppRs;
	}

	@Override
	public void setNppRs(ResultSet nppRs) {
		this.nppRs = nppRs;
	}

	@Override
	public ResultSet getTinhRs() {
		return this.tinhRs;
	}

	@Override
	public void setTinhRs(ResultSet tinhRs) {
		this.tinhRs = tinhRs;
	}

	@Override
	public ResultSet getQuanRs() {
		return this.quanRs;
	}

	@Override
	public void setQuanRs(ResultSet quanRs) {
		this.quanRs = quanRs;
	}

	@Override
	public IKeHoachNhanVienNgay[] getNgayList() {
		return this.ngayList;
	}

	@Override
	public void setNgayList(IKeHoachNhanVienNgay[] ngayList) {
		this.ngayList = ngayList;
	}

	@Override
	public void createRs() {
		Utility util = new Utility();
		
		String query = " SELECT PK_SEQ, TEN FROM NHAPHANPHOI WHERE TRANGTHAI = 1 and pk_seq != 1  and PK_SEQ IN " + util.quyen_npp(this.userId);
		this.nppRs = this.db.get(query);
		System.out.println("GET NPP \n" + query);
		query = " SELECT distinct t.PK_SEQ, t.TEN FROM TINHTHANH t ";//join NHAPHANPHOI p on p.TINHTHANH_FK = t.PK_SEQ WHERE p.PK_SEQ IN "+ util.quyen_npp(this.userId);
		this.tinhRs = this.db.get(query);
		System.out.println("GET TT \n" + query);
		query = " SELECT TINHTHANH_FK, PK_SEQ, TEN FROM QUANHUYEN ";
		this.quanRs = this.db.get(query);
		
		query = "select a.PK_SEQ, a.TEN, a.NPP_FK  from DAIDIENKINHDOANH a   where  a.TRANGTHAI = '1' and a.NPP_FK in "+ util.quyen_npp(this.userId);
		System.out.println("\nGET DDKD = " + query);
		this.ddkdRs = this.db.get(query);
		query = "select PK_SEQ, DIENGIAI, DDKD_FK,NPP_FK from TUYENBANHANG where DDKD_FK in (select PK_SEQ from DAIDIENKINHDOANH where TRANGTHAI = '1'  ) and NPP_FK in  ( select npp_fk from phamvihoatdong where nhanvien_fk ='"+this.userId+"') order by NGAYID asc";
		System.out.println("\nGET TBH = " + query);
		this.tbhRs = db.get(query);
	}

	public void closeDB()
	{
		try 
		{
			if(this.nppRs!=null) {
				nppRs.close();
			}
			if(this.tinhRs!=null) {
				tinhRs.close();
			}
			if(this.quanRs!=null) {
				quanRs.close();
			}
			if(this.ddkdRs!=null) {
				quanRs.close();
			}
			if(this.tbhRs!=null) {
				tbhRs.close();
			}
		} 
		catch(Exception e) 
		{
			
		}
		
		if(this.db != null) {
			this.db.shutDown();
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	
	
	private String getDateTime(String pattern) 
	{
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
      
        return dateFormat.format(date);
	}

	@Override
	public String getTrangthai() {
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	@Override
	public ResultSet getDDKDRs() {
		// TODO Auto-generated method stub
		return this.ddkdRs;
	}

	@Override
	public ResultSet getTBHRs() {
		// TODO Auto-generated method stub
		return this.tbhRs;
	}
}
