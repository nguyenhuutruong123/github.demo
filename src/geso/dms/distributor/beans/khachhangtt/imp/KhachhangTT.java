package geso.dms.distributor.beans.khachhangtt.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import geso.dms.center.util.Utility;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.khachhangtt.IKhachhangTT;
import geso.dms.distributor.beans.khachhangtt.IHoadonKHTT;
import geso.dms.distributor.beans.khachhangtt.imp.HoadonKHTT;

public class KhachhangTT implements IKhachhangTT 
{
	String userId;
	String id;
	String ngaychungtu;
	String ngayghiso;
	String ctyId;
	String lydonop;
	String nppId;
	ResultSet nppRs;
	
	String hdId;
	ResultSet hdTCRs;
	
	String htttId;
	ResultSet htttRs;
	
	String ndId;
	ResultSet ndRs;
	
	String soin;
	
	int checkDN;  //4: Doi tac
	
	String DoiTuongTamUng;
	
	String nccId="";
	ResultSet nccRs;
	
	String nvtuId="";
	ResultSet nvtuRs;
	
	String nganhangId;
	ResultSet nganhangRs;
	String chinhanhId;
	ResultSet chinhanhRs;
	
	String nguoinoptien;
	String noidungtt;
	String sotientt;
	String sotienttNT;
	
	String sochungtu;
	String doituongId;
	
	String tungay;
	String denngay;
	
	String hoadonId;
	List<IHoadonKHTT> hoadonList;
	
	String msg;
	String thuduoc;
	String thuduocNT;
	String bpkinhdoanh;
	String cpnganhangNT;
	String chenhlech;
	String chenhlechNT;
	String tongVND;
	String tongNT;
	String tigia;
	String Tigia_hoadon;
	
	String nppIds;
	String hdIds;
	String khIds;
	String nhanvienGNIds;
	String nhanvienBHIds;
	
	String chietkhau;
	String chietkhauNT;
	
	String DoiTuongDinhKhoan;
	String DoituongdinhkhoanId;
	String MaTenDoiTuongDinhKhoan;
	String dinhkhoanco;
	String dinhkhoancoId;
	
	ResultSet KhRs;
	ResultSet NhanvienGNRs;
	ResultSet NhanvienBHRs;
	
	ResultSet sotkRs;
	dbutils db;
	Utility util;
	
	public KhachhangTT()
	{
		this.id = "";
		this.ctyId = "";
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.ndId = "100000";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.nguoinoptien = "";
		this.noidungtt = "";
		this.sotientt = "0";
		this.sotienttNT = "0";
		this.hoadonId = "";
		this.msg = "";
		this.sochungtu = "";
		this.tungay = "";
		this.denngay = "";
		this.thuduoc = "0";
		this.thuduocNT = "0";
		this.bpkinhdoanh = "";
		this.cpnganhangNT = "0";
		this.chenhlech = "0";
		this.chenhlechNT = "0";
		this.tongNT = "0";
		this.tongVND = "0";
		this.lydonop= "";
		this.chietkhauNT= "0";
		Tigia_hoadon="";
		this.tigia = "1";
		
		this.doituongId= "";
		
		this.checkDN=0;
		this.soin="";
		
		this.nppIds="";
		this.hdIds="";
		this.khIds= "";
		this.nhanvienBHIds= "";
		this.nhanvienGNIds="";
		
		this.dinhkhoanco="";
		this.dinhkhoancoId= "";
		this.DoiTuongDinhKhoan= "";
		
		this.DoiTuongTamUng="";
		this.nccId="";
		this.nvtuId="";
		
		this.hdId= "";
		
		this.hoadonList = new ArrayList<IHoadonKHTT>();
		
		this.db = new dbutils();
		this.util=new Utility();
	}
	
	public KhachhangTT(String id)
	{
		this.id = id;
		this.ctyId = "";
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.ndId = "100000";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.nguoinoptien = "";
		this.noidungtt = "";
		this.sotientt = "0";
		this.sotienttNT = "0";
		this.hoadonId = "";
		this.msg = "";
		this.sochungtu = "";
		this.tungay = "";
		this.denngay = "";
		this.thuduoc = "0";
		this.thuduocNT = "0";
		this.bpkinhdoanh = "";
		this.cpnganhangNT = "0";
		this.chenhlech = "0";
		this.chenhlechNT = "0";
		this.tongNT = "0";
		this.tongVND = "0";
		this.lydonop= "";
		this.chietkhauNT = "0";
		
		this.soin="";
		this.checkDN=0;
		this.doituongId= "";
		
		this.nppIds="";
		this.hdIds="";
		this.khIds= "";
		this.nhanvienBHIds= "";
		this.nhanvienGNIds="";
		
		this.dinhkhoanco="";
		this.dinhkhoancoId= "";
		this.DoiTuongDinhKhoan= "";
		
		this.DoiTuongTamUng="";
		this.nccId="";
		this.nvtuId="";
		
		this.hdId= "";
		
		this.tigia = "1";
		this.hoadonList = new ArrayList<IHoadonKHTT>();
		this.util=new Utility();
		this.db = new dbutils();
	}
	
	  public Integer getCheckDN() 
	  {			
		return this.checkDN;
	 }
		
	public void setCheckDN(Integer checkDN) 
	{			
		this.checkDN=checkDN;
	}
	
	public String getDoiTuongTamUng() {
		return DoiTuongTamUng;
	}

	public void setDoiTuongTamUng(String DoiTuongTamUng) {
		this.DoiTuongTamUng= DoiTuongTamUng;
		
	}
	
   public String getNccId() {
		
		return this.nccId;
	}

	
	public void setNccId(String nccId) {
		
		this.nccId=nccId;
	}
	
	   public String getSoin() {
			
			return this.soin;
		}

		
		public void setSoin(String soin) {
			
			this.soin=soin;
		}
	
	public void setNccRs(ResultSet nccRs)
	{
		this.nccRs = nccRs;
	}
	public ResultSet getNccRs() 
	{
		return nccRs;
	}
	
    public String getNvtuId() {
		
		return this.nvtuId;
	}

	
	public void setNvtuId(String nvtuId) {
		
		this.nvtuId=nvtuId;
	}
	
	public ResultSet getNvtuRs() 
	{
		return nvtuRs;
	}
	public void setNvtuRs(ResultSet nvtuRs) 
	{
		this.nvtuRs = nvtuRs;
	}
	
	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;
	}
	public String getChietkhau()
	{
		return chietkhau;
	}
	
	public void setChietkhauNT(String chietkhauNT) 
	{
		this.chietkhauNT = chietkhauNT;
	}
	public String getChietkhauNT()
	{
		return chietkhauNT;
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getNgaychungtu() 
	{
		return this.ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) 
	{
		this.ngaychungtu = ngaychungtu;
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;	
	}

	public ResultSet getNppRs()
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public ResultSet getHdTCRs()
	{
		return this.hdTCRs;
	}

	public void setHdTCRs(ResultSet hdTCRs) 
	{
		this.hdTCRs = hdTCRs;
	}
	
	public ResultSet getSotkRs()
	{
		return this.sotkRs;
	}

	public void setSotkRs(ResultSet sotkRs) 
	{
		this.sotkRs = sotkRs;
	}

	public String getHtttId() 
	{
		return this.htttId;
	}

	public void setHtttId(String htttId)
	{
		this.htttId = htttId;
	}

	public ResultSet getHtttRs() 
	{
		return this.htttRs;
	}

	public void setHtttRs(ResultSet htttRs)
	{
		this.htttRs = htttRs;	
	}

	public String getNganhangId()
	{
		return this.nganhangId;
	}

	public void setNganhangId(String nganhangId)
	{
		this.nganhangId = nganhangId;
	}

	public ResultSet getNganhangRs() 
	{
		return this.nganhangRs;
	}

	public void setNganhangRs(ResultSet nganhangRs) 
	{
		this.nganhangRs = nganhangRs;
	}

	public String getChinhanhId() 
	{
		return this.chinhanhId;
	}

	public void setChinhanhId(String cnId)
	{
		this.chinhanhId = cnId;
	}

	public ResultSet getChinhanhRs()
	{
		return this.chinhanhRs;
	}

	public void setChinhanhRs(ResultSet chinhanhRs) 
	{
		this.chinhanhRs = chinhanhRs;
	}

	public String getNguoinoptien() 
	{
		return this.nguoinoptien;
	}

	public void setNguoinoptien(String nguoinoptien)
	{
		this.nguoinoptien = nguoinoptien;
	}

	public String getNoidungtt()
	{
		return this.noidungtt;
	}

	public void setNoidungtt(String ndtt) 
	{
		this.noidungtt = ndtt;
	}

	public String getSotientt() 
	{
		return this.sotientt;
	}

	public void setSotientt(String sotientt) 
	{
		this.sotientt = sotientt;
	}

	public String getSotienttNT() 
	{
		return this.sotienttNT;
	}

	public void setSotienttNT(String sotienttNT) 
	{
		this.sotienttNT = sotienttNT;
	}

	public String getHoadonIds() 
	{
		return this.hoadonId;
	}

	public void setHoadonIds(String hdIds) 
	{
		this.hoadonId = hdIds;
	}

	public List<IHoadonKHTT> getHoadonRs() 
	{
		return this.hoadonList;
	}

	public void setHoadonRs(List<IHoadonKHTT> hoadonRs)
	{
		this.hoadonList = hoadonRs;
	}

	public String getThuduoc() 
	{
		return this.thuduoc;
	}

	public void setThuduoc(String thuduoc)
	{
		this.thuduoc = thuduoc;
	}

	public String getThuduocNT() 
	{
		return this.thuduocNT;
	}

	public void setThuduocNT(String thuduocNT)
	{
		this.thuduocNT = thuduocNT;
	}

	public String getBpkinhdoanh() 
	{
		return this.bpkinhdoanh;
	}

	public void setBpkinhdoanh(String Bpkinhdoanh)
	{
		this.bpkinhdoanh = Bpkinhdoanh;
	}

	public String getChiphinganhangNT() 
	{
		return this.cpnganhangNT;
	}

	public void setChiphinganhangNT(String cpnganhangNT)
	{
		this.cpnganhangNT = cpnganhangNT;
	}

	public String getChenhlech() 
	{
		return this.chenhlech;
	}

	public void setChenhlech(String chenhlech)
	{
		this.chenhlech = chenhlech;
	}

	public String getChenhlechNT() 
	{
		return this.chenhlechNT;
	}

	public void setChenhlechNT(String chenhlechNT)
	{
		this.chenhlechNT = chenhlechNT;
	}

	public String getTigia() 
	{
		return this.tigia;
	}

	public void setTigia(String tigia)
	{
		this.tigia= tigia;
	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public String getTongNT() 
	{
		return this.tongNT;
	}

	public void setTongNT(String tongNT)
	{
		this.tongNT = tongNT;
	}

	public String getTongVND() 
	{
		return this.tongVND;
	}

	public void setTongVND(String tongVND)
	{
		this.tongVND = tongVND;
	}

	public boolean createTTHD() 
	{
		this.getNppInfo();
			if(this.hoadonList.size() < 0)
			{
				this.msg = "Vui lòng chọn hóa đơn thu tiền";
				return false;
			}

		
		if(this.sotientt.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}
		
		try 
		{
		
			
			db.getConnection().setAutoCommit(false);

			// LAY SOIN MAX			
			String query = "";
			query = "select  MAX(ISNULL(SOIN,0))as SOIN_MAX from KHACHHANGTRATRUOC ";
			ResultSet laySOIN = db.get(query);
			String chuoi="";
			if(laySOIN!= null)
			{
				while(laySOIN.next())
				{
					chuoi = ("000"+ (laySOIN.getLong("SOIN_MAX")>0 ? (laySOIN.getLong("SOIN_MAX")+1) :"1"));
					
				}laySOIN.close();
			}
			chuoi = chuoi.substring(chuoi.length() - 3, chuoi.length());
			this.soin =  chuoi;
			
			// 1 phiếu chỉ lưu 1 NVGN/NVBH/KH
			if(this.nhanvienBHIds.trim().length() <= 0) this.nhanvienBHIds = "NULL";
			if(this.nhanvienGNIds.trim().length() <= 0) this.nhanvienGNIds = "NULL";
			if(this.khIds.trim().length() <= 0) this.khIds = "NULL";
			
		    query = "";
			double Conlai= Double.parseDouble(this.sotientt.replace(",", "")) - Double.parseDouble(this.thuduoc.replace(",", ""));
			
				query = "Insert KHACHHANGTRATRUOC( SOTIENTHU,SOTIENHD ,CONLAI ,NGAYCHUNGTU,TRANGTHAI, GHICHU, " +
						" NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA , NPP_FK, SOIN, KHACHHANG_FK, NVGN_FK, NVBH_FK) " +
						"values("  +this.sotientt.replace(",", "")+ ", "+this.thuduoc.replace(",", "") + ", "+Conlai+ ","+
						" '" + this.ngaychungtu + "', '0', N'" + this.noidungtt + "',  " +
						" '" +  getDateTime() + "', '" + this.userId + "', '"+  getDateTime() +"', '"+ this.userId +"','"+ this.nppId +"', '"+ this.soin +"'," +
						" "+ this.khIds +", "+this.nhanvienGNIds+" , "+ this.nhanvienBHIds +")";
				
			
			
			
			System.out.println(query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi KHACHHANGTRATRUOC: " + query;
				System.out.println("[ErpThutien.createTTHD] error message:" + this.msg);
				db.getConnection().rollback();
				return false;
			}
			
				String tthdCurrent = "";
				query = "select IDENT_CURRENT('KHACHHANGTRATRUOC') as tthdId";
				
				ResultSet rsTthd = db.get(query);						
				if(rsTthd.next())
				{
					tthdCurrent = rsTthd.getString("tthdId");
					rsTthd.close();
				}
				
				for(int i = 0; i < this.hoadonList.size(); i++)
				{
					IHoadonKHTT hoadon = this.hoadonList.get(i);
					
					String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
					
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				
					String conlai = hoadon.getConlai().replaceAll(",", "");
					
					
					
					if(thanhtoan.length() > 0)
					{
						if(Float.parseFloat(thanhtoan) > 0)
						{							
							String npp_fk= hoadon.getNppId();
							String kh_fk= hoadon.getKhId();
							if(npp_fk.trim().length() == 0)
							{
								npp_fk= "NULL";
							}
							if(kh_fk.trim().length() == 0)
							{
								kh_fk= "NULL";
							}
								query = "Insert KHACHHANGTRATRUOC_HOADON(KHTT_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, NPP_FK ,KHACHHANG_FK) " +
										"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "', " + npp_fk + "," + kh_fk + ")";
							
								System.out.println("INSERT KHACHHANGTRATRUOC_HOADON: ......"+query);
								
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi KHACHHANGTRATRUOC_HOADON: " + query;
									System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
									db.getConnection().rollback();
									return false;
								}
							
						}
					}
				}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			try 
			{
				System.out.println(e.toString());
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public boolean updateTTHD() 
	{
    this.getNppInfo();
		
			if(this.hoadonList.size() < 0)
			{
				this.msg = "Vui lòng chọn hóa đơn thu tiền";
				return false;
			}

		
		if(this.sotientt.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}
		
		
		try 
		{
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
						

			String query = "";
			
			double Conlai= Double.parseDouble(this.sotientt.replace(",", "")) - Double.parseDouble(this.thuduoc.replace(",", ""));
	
			// 1 phiếu chỉ lưu 1 NVGN/NVBH/KH
			if(this.nhanvienBHIds.trim().length() <= 0) this.nhanvienBHIds = "NULL";
			if(this.nhanvienGNIds.trim().length() <= 0) this.nhanvienGNIds = "NULL";
			if(this.khIds.trim().length() <= 0) this.khIds = "NULL";
			
				query = "update KHACHHANGTRATRUOC set SOTIENTHU= "+ this.sotientt.replace(",", "") +" ,SOTIENHD= "+ this.thuduoc.replace(",", "") +", CONLAI= "+ Conlai +",  "+
						" NGAYCHUNGTU = '" + this.ngaychungtu + "', GHICHU = N'" + this.noidungtt + "', " +
						" NGAYSUA = '" +  getDateTime() + "', NGUOISUA = '" + this.userId + "', NPP_FK='"+ this.nppId +"', " +
						" KHACHHANG_FK = "+ this.khIds +", NVBH_FK = "+ this.nhanvienBHIds +" , NVGN_FK = "+ this.nhanvienGNIds +" " +
						" where PK_SEQ = '"  + this.id + "'";
				
			
			
			
			System.out.println(query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat KHACHHANGTRATRUOC: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}

				query = "delete KHACHHANGTRATRUOC_HOADON where KHTT_FK = '" + this.id + "'";
				db.update(query);

				for(int i = 0; i < this.hoadonList.size(); i++)
				{
					IHoadonKHTT hoadon = this.hoadonList.get(i);
	
					String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					
					if(hoadon.getKhId().length() <=0) hoadon.setKhId("NULL");
					if(hoadon.getNppId().length() <=0) hoadon.setNppId("NULL");
					
					if(thanhtoan.length() > 0)
					{
						if(Float.parseFloat(thanhtoan) > 0)
						{							
							String npp_fk= hoadon.getNppId();
							String kh_fk= hoadon.getKhId();
							if(npp_fk.trim().length() == 0)
							{
								npp_fk= "NULL";
							}
							if(kh_fk.trim().length() == 0)
							{
								kh_fk= "NULL";
							}
								
								query = "Insert KHACHHANGTRATRUOC_HOADON(KHTT_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, NPP_FK ,KHACHHANG_FK) " +
										"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "', " + npp_fk + "," + kh_fk + ")";
							
								System.out.println(query);
								
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi KHACHHANGTRATRUOC_HOADON: " + query;
									System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
									db.getConnection().rollback();
									return false;
								}
							
						
						}
					}
				}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			try 
			{
				System.out.println(e.toString());
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public boolean chotTTHD(String userId)
	{
		try 
		{
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			
			String query = "update KHACHHANGTRATRUOC set TRANGTHAI = '1', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' where PK_SEQ = '"  + this.id + "'";
			System.out.println("1.Cap nhat KHACHHANGTRATRUOC : " + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the chot ERP_THUTIENNPP: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
															
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (java.sql.SQLException e) 
		{
			try 
			{
				db.getConnection().rollback();
				this.msg = "Lỗi khi chốt thu tiền: " + e.getMessage();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
	}
	
	public void init()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = " select tt.pk_seq, tt.ngaychungtu, tt.trangthai, tt.NVGN_FK, tt.NVBH_FK, tt.KHACHHANG_FK, "+
					   " isnull(tt.ghichu, '') as ghichu, "+
					   "  ISNULL(tt.sotienHD, 0) AS SOTIENHD,  isnull(tt.sotienthu, 0) as DATHU "+
						" from KHACHHANGTRATRUOC tt  "+
						" where tt.pk_seq = '" + this.id + "'";
		
		System.out.println("115.Khoi tao thu tien: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.sotientt =rs.getString("DATHU");
					this.noidungtt = rs.getString("ghichu");
					this.thuduoc = "" + rs.getString("SOTIENHD");					
					this.nhanvienGNIds =rs.getString("NVGN_FK")== null ?  "" :rs.getString("NVGN_FK")  ;
					this.nhanvienBHIds =rs.getString("NVBH_FK")== null ?  "" :rs.getString("NVBH_FK")  ;
					this.khIds =rs.getString("KHACHHANG_FK")== null ?  "" :rs.getString("KHACHHANG_FK")  ;
					
					// Doi tuong: 0 nhan vien giao nhan, 1 nhan vien ban hang, 2 khachhang
					if(this.nhanvienBHIds.trim().length() > 0)
					{
						this.doituongId = "1";
					}else if (this.nhanvienGNIds.trim().length() > 0)
					{
						this.doituongId = "0";
					}else if (this.khIds.trim().length() > 0)
					{
						this.doituongId = "2";
					}
					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		this.createRs();
	}

	
	public void initDisplay()
	{
	this.getNppInfo();
	 String	query = 	"SELECT HD.PK_SEQ, HD.NGAYXUATHD ,cast(HD.PK_SEQ as nvarchar(20)) + '-' +HD.NGAYXUATHD as TEN  " +
			"FROM ERP_HOADONNPP HD " +
			"WHERE HD.TRANGTHAI=2 AND HD.NPP_FK='"+ this.nppId +"' " +
		"UNION ALL "+
 		"SELECT HD1.PK_SEQ, HD1.NGAYXUATHD ,cast(HD1.PK_SEQ as nvarchar(20)) + '-' +HD1.NGAYXUATHD as TEN  " +
			"FROM HOADON HD1 " +
			"WHERE HD1.TRANGTHAI=2 AND HD1.NPP_FK='"+ this.nppId +"' " ;
		System.out.println("HDRS: "+query);
		this.hdTCRs = db.get(query);
		
		String sql = "select pk_seq, ma + ', ' + ten as nppTen from NHAPHANPHOI where trangthai = '1' and loainpp='4' AND TRUCTHUOC_FK ='"+ this.nppId +"' ";
		this.nppRs = db.get(sql);
		
		sql = "select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as khTen from KHACHHANG where trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.KhRs = db.get(sql);
		
		sql = "select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from NHANVIENGIAONHAN where trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.NhanvienGNRs = db.get(sql);
		
		sql = "select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from DAIDIENKINHDOANH where trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.NhanvienBHRs = db.get(sql);
		

		 query = " select tt.pk_seq, tt.ngaychungtu, tt.trangthai, tt.NVGN_FK, tt.NVBH_FK, tt.KHACHHANG_FK, "+
		   " isnull(tt.ghichu, '') as ghichu, "+
		   "  ISNULL(tt.sotienHD, 0) AS SOTIENHD,  isnull(tt.sotienthu, 0) as DATHU "+
			" from KHACHHANGTRATRUOC tt  "+
			" where tt.pk_seq = '" + this.id + "'";
		
		System.out.println("115.Khoi tao thu tien: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.sotientt =rs.getString("DATHU");
					this.noidungtt = rs.getString("ghichu");
					this.thuduoc = "" + rs.getString("SOTIENHD");					
					this.nhanvienGNIds =rs.getString("NVGN_FK")== null ?  "" :rs.getString("NVGN_FK")  ;
					this.nhanvienBHIds =rs.getString("NVBH_FK")== null ?  "" :rs.getString("NVBH_FK")  ;
					this.khIds =rs.getString("KHACHHANG_FK")== null ?  "" :rs.getString("KHACHHANG_FK")  ;
					
					// Doi tuong: 0 nhan vien giao nhan, 1 nhan vien ban hang, 2 khachhang
					if(this.nhanvienBHIds.trim().length() > 0)
					{
						this.doituongId = "1";
					}else if (this.nhanvienGNIds.trim().length() > 0)
					{
						this.doituongId = "0";
					}else if (this.khIds.trim().length() > 0)
					{
						this.doituongId = "2";
					}
					
					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
		// INIT HOADON

				query = 	"SELECT KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH, NPP.PK_SEQ AS NPPID,NPP.MAFAST + '-' + NPP.TEN AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n" + 
							"	HD.NGAYXUATHD AS NGAYHOADON, \n" +
		 	   				"	(ISNULL(HD.TONGTIENAVAT,0)- ISNULL(HD.CHIETKHAU,0) - ISNULL(DATHU.TONGTHU, '0')) AS SOTIENVND, \n" +
		 	   				"	TT_HD.SOTIENTT , 0 as IS_KHLE \n" +
		 	   				" FROM KHACHHANGTRATRUOC_HOADON TT_HD \n" +
		 	   				" INNER JOIN ERP_HOADONNPP HD ON TT_HD.HOADON_FK = HD.PK_SEQ  " +
		 	   			    " LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK AND NPP.LOAINPP=4 \n" +
		 	   		        " LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n" +
			 	   		    " AND TT_HD.KHACHHANG_FK in \n"+
					    	" (select PK_SEQ from KHACHHANG where KBH_FK=100052 and NPP_FK='" + this.nppId +"') \n"+
							" LEFT JOIN	\n" +
							" ( 	\n" +
							"	SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
							"	FROM KHACHHANGTRATRUOC_HOADON TTHD \n" +
							"	INNER JOIN KHACHHANGTRATRUOC TT ON TTHD.KHTT_FK = TT.PK_SEQ \n" + 		
							"	WHERE TT.TRANGTHAI != '2' AND TTHD.KHTT_FK != '" + this.id + "' \n" + 		
							"	AND TTHD.HOADON_FK IN \n" +
							"		(SELECT HOADON_FK FROM KHACHHANGTRATRUOC_HOADON WHERE KHTT_FK = '" + this.id + "' \n" + 						
							" ) 	\n" +	
							"	GROUP BY HOADON_FK \n" +
							" )DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n" +
							"WHERE TT_HD.KHTT_FK = '" + this.id + "'  \n" ;                      								
			query += " UNION ALL ";		
			
			    query += 
					    	"SELECT KH.PK_SEQ AS KHID ,KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n"+
					    	"	HD.NGAYXUATHD AS NGAYHOADON, \n"+
					    	"	(ISNULL(HD.TONGTIENAVAT,0)- ISNULL(HD.CHIETKHAU,0) - ISNULL(DATHU.TONGTHU, '0')) AS SOTIENVND, \n"+
					    	"	TT_HD.SOTIENTT, 0 as IS_KHLE  \n"+
					    	" FROM KHACHHANGTRATRUOC_HOADON TT_HD \n"+
					    	" LEFT JOIN HOADON HD ON TT_HD.HOADON_FK = HD.PK_SEQ   \n"+
					    	" AND TT_HD.KHACHHANG_FK in \n"+
					    	" (select PK_SEQ from KHACHHANG where KBH_FK=100025 and NPP_FK='" + this.nppId +"') \n"+
					    	" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
					    	" LEFT JOIN	\n"+
					    	" ( 	\n"+
					    	"	SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
					    	"	FROM KHACHHANGTRATRUOC_HOADON TTHD \n"+
					    	"	INNER JOIN KHACHHANGTRATRUOC TT ON TTHD.KHTT_FK = TT.PK_SEQ \n"+
					    	"	WHERE TT.TRANGTHAI != '2' AND TTHD.KHTT_FK != '" + this.id + "' \n"+
					    	"	AND TTHD.HOADON_FK IN \n"+
					    	"		(SELECT HOADON_FK FROM KHACHHANGTRATRUOC_HOADON WHERE KHTT_FK = '" + this.id + "' \n"+
					    	 ") 	\n"+
					    	"	GROUP BY HOADON_FK \n"+
					    	" )DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n"+
					    	"WHERE ISNULL(HD.LOAIHOADON,0)= 0 AND TT_HD.KHTT_FK ='" + this.id + "' AND  " +
					    	"  (ISNULL(HD.TONGTIENAVAT,0)- ISNULL(HD.CHIETKHAU,0) - ISNULL(DATHU.TONGTHU, '0')) > 0     \n";
					    	
		query += " UNION ALL ";	
			
		  query += 
		    	"SELECT KH.PK_SEQ AS KHID, KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, HD.PK_SEQ, 'GESO' AS KYHIEU, '' AS SOHOADON, \n"+
		    	"	'2014-06-30' AS NGAYHOADON, \n"+
		    	"	(ISNULL(HD.SONO,0) - ISNULL(DATHU.TONGTHU, '0')) AS SOTIENVND, \n"+
		    	"	TT_HD.SOTIENTT, 0 as IS_KHLE  \n"+
		    	" FROM KHACHHANGTRATRUOC_HOADON TT_HD \n"+
		    	" LEFT JOIN DUNO_KHACHHANG HD ON TT_HD.HOADON_FK = HD.PK_SEQ   \n"+
		    	" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
		    	" LEFT JOIN	\n"+
		    	" ( 	\n"+
		    	"	SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
		    	"	FROM KHACHHANGTRATRUOC_HOADON TTHD \n"+
		    	"	INNER JOIN KHACHHANGTRATRUOC TT ON TTHD.KHTT_FK = TT.PK_SEQ \n"+
		    	"	WHERE TT.TRANGTHAI != '2' AND TTHD.KHTT_FK != '" + this.id + "' \n"+
		    	"	AND TTHD.HOADON_FK IN \n"+
		    	"		(SELECT HOADON_FK FROM KHACHHANGTRATRUOC_HOADON WHERE KHTT_FK = '" + this.id + "' \n"+
		    	 ") 	\n"+
		    	"	GROUP BY HOADON_FK \n"+
		    	" )DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n"+
		    	"WHERE  TT_HD.KHTT_FK ='" + this.id + "' " +
		    	"       AND (ISNULL(HD.SONO,0) - ISNULL(DATHU.TONGTHU, '0')) > 0 \n";

		  
		System.out.println("1.Query khoi tao hoa don Display: " + query);
		ResultSet rsHoadon = db.get(query);
		List<IHoadonKHTT> hdList = new ArrayList<IHoadonKHTT>();
		if(rsHoadon != null)
		{
			try 
			{
				IHoadonKHTT hd = null;
				while(rsHoadon.next())
				{
					
					String id = rsHoadon.getString("PK_SEQ");							
					String kyhieu = rsHoadon.getString("KYHIEU");
					String sohoadon = rsHoadon.getString("SOHOADON");
					String ngayhd = rsHoadon.getString("NGAYHOADON");
					String avat = "" +rsHoadon.getDouble("SOTIENVND");
					String nppid= rsHoadon.getString("NPPID")==null ? "":rsHoadon.getString("NPPID");
					String manpp= rsHoadon.getString("MANPP")==null ? "":rsHoadon.getString("MANPP");
					String khid= rsHoadon.getString("KHID")==null ? "":rsHoadon.getString("KHID");
					String makh= rsHoadon.getString("MAKH")==null ? "": rsHoadon.getString("MAKH");
					
					if(nppid.equals("0")) nppid="";
					if(manpp.equals("0")) manpp="";
					String dathanhtoan = "0";
					if(this.id.length() > 0)
					{								
						if(Math.abs(rsHoadon.getDouble("SOTIENTT")) > 0){
							dathanhtoan = "" + rsHoadon.getDouble("SOTIENTT");
						}
					}
					hd = new HoadonKHTT(id, "", kyhieu, sohoadon, ngayhd, avat, "", dathanhtoan, "","","");
					hd.setNppId(nppid);
					hd.setNppMa(manpp);
					hd.setKhId(khid);
					hd.setKhMa(makh);
					hdList.add(hd);
					
				}
				rsHoadon.close();
			} 
			catch (SQLException e) {}
		}
		this.hoadonList = hdList;
	
					
	
	}
	
	public void initPdf() 
	{
		this.getNppInfo();

		String query = " select a.pk_seq as tthdId, '' as nguoinoptien, a.trangthai, a.ngaychungtu, a.soin, " +
					   "  a.ghichu as ghichu, isnull(sotienHD, 0) as thuduoc " +
					   " from KHACHHANGTRATRUOC a  " +
					   " where a.pk_seq = '" + this.id + "' ";
		
		System.out.println("[ErpThutien.initPdf] query = " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.noidungtt = rs.getString("ghichu");
					this.thuduoc =  rs.getString("thuduoc");
					this.nguoinoptien = rs.getString("nguoinoptien");
					this.soin = rs.getString("soin");
					

				}
			} 
			catch (SQLException e) 
			{
				System.out.println("115..Exception: " + e.getMessage());
			}
		}
		
		
		
	}

	public void initUnc() 
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = "select a.pk_seq, a.ngaychungtu, b.ten as nppTen, b.diachi, a.httt_fk, c.ten as nganhang_fk, a.chinhanh_fk, a.sotaikhoan, a.noidungtt, a.sotientt " +
						"from ERP_THANHTOANHOADON a inner join ERP_NHACUNGCAP b on a.npp_fk = b.pk_seq left join erp_nganhang c on a.nganhang_fk = c.pk_seq" +
						" where a.pk_seq = '" + this.id + "'";
		System.out.println("Khoi tao Unc: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.nppId = rs.getString("nppTen") + " --- " + rs.getString("diachi");
					this.htttId = rs.getString("httt_fk");

					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));

				}
			} 
			catch (SQLException e) 
			{
				System.out.println("Exception: " + e.getMessage());
			}
		}
		
	}
	
	public void createRs() 
	{
		this.getNppInfo();
		
		// Check dang nhap co phai DOI TAC hay khong
		String query = "SELECT LOAINPP FROM NHAPHANPHOI WHERE PK_SEQ='"+ this.nppId +"' ";
		
		ResultSet rss= db.get(query);
		try
		{
			if(rss!=null)
			{
				while (rss.next())
				{
					this.checkDN = rss.getInt("LOAINPP");
				}
			}
		}catch(Exception e){e.printStackTrace();}
		
		String sql= "";

		 sql = "select pk_seq, isnull(maFAST,'') + '-' + ten as khTen from KHACHHANG where trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.KhRs = db.get(sql);
		
		 sql = "select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from NHANVIENGIAONHAN where trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
			this.NhanvienGNRs = db.get(sql);
			
		sql = "select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from DAIDIENKINHDOANH where trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
			this.NhanvienBHRs = db.get(sql);
		
	 // HIỆN SỐ TIỀN THU TRƯỚC	
		
		if(this.id.trim().length() <= 0)
		{
			if(this.nhanvienBHIds.trim().length() > 0 || this.nhanvienGNIds.trim().length() > 0 || this.khIds.trim().length() > 0)
			{
				if(this.nhanvienGNIds.trim().length() > 0 )
				{
				 sql = " select isnull(a.sotien,0) - isnull(b.sotienhd,0) as sotiendathu " +
					  " from NOPTIEN a left join KHACHHANGTRATRUOC b on a.NVGN_FK=b.NVGN_FK and b.trangthai != 2 and b.NVGN_FK is not null" +
					  " where a.nvgn_fk in ( "+ this.nhanvienGNIds +") and a.trangthai=1  ";
				 
				}else if(this.nhanvienBHIds.trim().length() > 0)
				{
					sql = " select isnull(a.sotien,0) - isnull(b.sotienhd,0) as sotiendathu " +
					  " from NOPTIEN a left join KHACHHANGTRATRUOC b on a.NVBH_FK=b.NVBH_FK  and b.trangthai != 2 and b.NVBH_FK is not null" +
					  " where a.nvbh_fk in ( "+ this.nhanvienBHIds +") and a.trangthai=1  ";
				
				}else if(this.khIds.trim().length() > 0)
				{
					sql = " select isnull(a.sotien,0) - isnull(b.sotienhd,0) as sotiendathu " +
					  " from NOPTIEN a left join KHACHHANGTRATRUOC b on a.khachhang_FK=b.khachhang_FK  and b.trangthai != 2 and b.khachhang_fk is not null " +
					  " where a.khachhang_fk in ( "+ this.khIds +") and a.trangthai=1  ";
				
				}
				System.out.println("Lấy tiền thu trước :"+sql);
				ResultSet rsLayST = db.get(sql);
				try
				{
					if(rsLayST!= null)
					{
						while(rsLayST.next())
						{
							this.sotientt = rsLayST.getString("sotiendathu");
							
						}rsLayST.close();
					}
					
					
				}catch(Exception e){e.printStackTrace();}
					
				
			}
		}
		
		query = "";		
		
		if( this.hoadonList.size() <= 0)
		{
//			NumberFormat formatter = new DecimalFormat("#,###,###");
				if(this.id.length() > 0)
				{
					query += 	"SELECT KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH, NPP.PK_SEQ AS NPPID,NPP.MAFAST + '-' + NPP.TEN AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n" + 
								"	HD.NGAYXUATHD AS NGAYHOADON, \n" +
			 	   				"	(ISNULL(HD.TONGTIENAVAT,0)- ISNULL(HD.CHIETKHAU,0) - ISNULL(DATHU.TONGTHU, '0')) AS SOTIENVND, \n" +
			 	   				"	TT_HD.SOTIENTT , 0 as IS_KHLE \n" +
			 	   				" FROM KHACHHANGTRATRUOC_HOADON TT_HD \n" +
			 	   				" INNER JOIN ERP_HOADONNPP HD ON TT_HD.HOADON_FK = HD.PK_SEQ  " +
			 	   			    " LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK AND NPP.LOAINPP=4 \n" +
			 	   		        " LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n" +
				 	   		    " AND TT_HD.KHACHHANG_FK in \n"+
						    	" (select PK_SEQ from KHACHHANG where KBH_FK=100052 and NPP_FK='" + this.nppId +"') \n"+
								" LEFT JOIN	\n" +
								" ( 	\n" +
								"	SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
								"	FROM KHACHHANGTRATRUOC_HOADON TTHD \n" +
								"	INNER JOIN KHACHHANGTRATRUOC TT ON TTHD.KHTT_FK = TT.PK_SEQ \n" + 		
								"	WHERE TT.TRANGTHAI != '2' AND TTHD.KHTT_FK != '" + this.id + "' \n" + 		
								"	AND TTHD.HOADON_FK IN \n" +
								"		(SELECT HOADON_FK FROM KHACHHANGTRATRUOC_HOADON WHERE KHTT_FK = '" + this.id + "' \n" + 						
								" ) 	\n" +	
								"	GROUP BY HOADON_FK \n" +
								" )DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n" +
								"WHERE TT_HD.KHTT_FK = '" + this.id + "'  \n" ;                      								
				query += " UNION ALL ";		
				
				    query += 
						    	"SELECT KH.PK_SEQ AS KHID ,KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n"+
						    	"	HD.NGAYXUATHD AS NGAYHOADON, \n"+
						    	"	(ISNULL(HD.TONGTIENAVAT,0)- ISNULL(HD.CHIETKHAU,0) - ISNULL(DATHU.TONGTHU, '0')) AS SOTIENVND, \n"+
						    	"	TT_HD.SOTIENTT, 0 as IS_KHLE  \n"+
						    	" FROM KHACHHANGTRATRUOC_HOADON TT_HD \n"+
						    	" LEFT JOIN HOADON HD ON TT_HD.HOADON_FK = HD.PK_SEQ   \n"+
						    	" AND TT_HD.KHACHHANG_FK in \n"+
						    	" (select PK_SEQ from KHACHHANG where KBH_FK=100025 and NPP_FK='" + this.nppId +"') \n"+
						    	" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
						    	" LEFT JOIN	\n"+
						    	" ( 	\n"+
						    	"	SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
						    	"	FROM KHACHHANGTRATRUOC_HOADON TTHD \n"+
						    	"	INNER JOIN KHACHHANGTRATRUOC TT ON TTHD.KHTT_FK = TT.PK_SEQ \n"+
						    	"	WHERE TT.TRANGTHAI != '2' AND TTHD.KHTT_FK != '" + this.id + "' \n"+
						    	"	AND TTHD.HOADON_FK IN \n"+
						    	"		(SELECT HOADON_FK FROM KHACHHANGTRATRUOC_HOADON WHERE KHTT_FK = '" + this.id + "' \n"+
						    	 ") 	\n"+
						    	"	GROUP BY HOADON_FK \n"+
						    	" )DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n"+
						    	"WHERE ISNULL(HD.LOAIHOADON,0)= 0 AND TT_HD.KHTT_FK ='" + this.id + "' AND  " +
						    	"  (ISNULL(HD.TONGTIENAVAT,0)- ISNULL(HD.CHIETKHAU,0) - ISNULL(DATHU.TONGTHU, '0')) > 0     \n";
						    	
			query += " UNION ALL ";	
				
			  query += 
			    	"SELECT KH.PK_SEQ AS KHID, KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, HD.PK_SEQ, 'GESO' AS KYHIEU, '' AS SOHOADON, \n"+
			    	"	'2014-06-30' AS NGAYHOADON, \n"+
			    	"	(ISNULL(HD.SONO,0) - ISNULL(DATHU.TONGTHU, '0')) AS SOTIENVND, \n"+
			    	"	TT_HD.SOTIENTT, 0 as IS_KHLE  \n"+
			    	" FROM KHACHHANGTRATRUOC_HOADON TT_HD \n"+
			    	" LEFT JOIN DUNO_KHACHHANG HD ON TT_HD.HOADON_FK = HD.PK_SEQ   \n"+
			    	" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
			    	" LEFT JOIN	\n"+
			    	" ( 	\n"+
			    	"	SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
			    	"	FROM KHACHHANGTRATRUOC_HOADON TTHD \n"+
			    	"	INNER JOIN KHACHHANGTRATRUOC TT ON TTHD.KHTT_FK = TT.PK_SEQ \n"+
			    	"	WHERE TT.TRANGTHAI != '2' AND TTHD.KHTT_FK != '" + this.id + "' \n"+
			    	"	AND TTHD.HOADON_FK IN \n"+
			    	"		(SELECT HOADON_FK FROM KHACHHANGTRATRUOC_HOADON WHERE KHTT_FK = '" + this.id + "' \n"+
			    	 ") 	\n"+
			    	"	GROUP BY HOADON_FK \n"+
			    	" )DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n"+
			    	"WHERE  TT_HD.KHTT_FK ='" + this.id + "' " +
			    	"       AND (ISNULL(HD.SONO,0) - ISNULL(DATHU.TONGTHU, '0')) > 0 \n";
				
			  query += " UNION ALL ";					
				
				}
				
		if( Double.parseDouble(this.sotientt) > 0 && (this.nhanvienBHIds.trim().length() > 0 || this.nhanvienGNIds.trim().length() > 0 || this.khIds.trim().length() > 0))
		{
				
		// HÓA ĐƠN CỦA KHACHHANG ETC/ DOITAC
				query += 	"(SELECT HOADON.KHID, HOADON.MAKH, HOADON.NPPID ,HOADON.MANPP , HOADON.PK_SEQ, HOADON.KYHIEU, HOADON.SOHOADON, HOADON.NGAYHOADON, \n" +	
							" (ISNULL(HOADON.TONGTIENAVAT, 0)- ISNULL(DATHUTIEN.DATHANHTOAN, '0') - ISNULL(DATHANHTOAN.DATHANHTOAN, '0')) AS SOTIENVND, \n" +
							" 0 AS DATHANHTOAN, 0 as IS_KHLE \n" +
	
							"FROM ( \n" +		
							"	SELECT KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH, NPP.PK_SEQ as NPPID,NPP.MAFAST + '-' + NPP.TEN as MANPP, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON, \n" + 
							"	HD.TONGTIENAVAT-HD.CHIETKHAU AS TONGTIENAVAT \n" +		
							"	FROM ERP_HOADONNPP HD 	" +
							"         LEFT join NHAPHANPHOI NPP on HD.NPP_DAT_FK= NPP.PK_SEQ AND NPP.LOAINPP=4 \n" +
							"        LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n" +
							"	WHERE  HD.TRANGTHAI = '2' and HD.NPP_FK = "+ this.nppId +"   \n";
			
			//TÌM KIẾM THEO KH
				if(this.khIds.length() > 0)
				{
					query += "AND KH.PK_SEQ  in (" + this.khIds + ") ";
				}
			//TÌM KIẾM THEO NVBH
				if(this.nhanvienBHIds.length() > 0)
				{
					query += "AND HD.PK_SEQ  in ( SELECT C.PK_SEQ " +
					"                     FROM ERP_DONDATHANGNPP A INNER JOIN ERP_HOADONNPP_DDH B ON A.PK_SEQ = B.DDH_FK" +
					"                          INNER JOIN ERP_HOADONNPP C ON B.HOADONNPP_FK= C.PK_SEQ " +
					"                     WHERE C.TRANGTHAI=2 AND A.DDKD_FK IN (" + this.nhanvienBHIds + ") ) ";
				}

				if(this.id.length() > 0)
				{
					query += "AND HD.PK_SEQ NOT IN (SELECT HOADON_FK FROM KHACHHANGTRATRUOC_HOADON WHERE KHTT_FK = '" + this.id + "') \n";
					
				}
			
				query += 	") HOADON \n" + 
							"LEFT JOIN ( \n" +	
							"	SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
							"	FROM  \n" +	
							"	( 	\n" +					
							"		SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
							"		FROM KHACHHANGTRATRUOC_HOADON TTHD \n" +
							"		INNER JOIN KHACHHANGTRATRUOC TT ON TTHD.KHTT_FK = TT.PK_SEQ \n" +   		
							"		WHERE TT.NPP_FK= '"+ this.nppId +"' AND  TT.TRANGTHAI NOT IN (2)\n"	; 
				
							
				if(this.id.trim().length() > 0){
					query += " 		AND TT.PK_SEQ  != '" + this.id + "' \n";
				}
			
				query +=	" 		GROUP BY HOADON_FK \n" +  	
							"	) HOADONDATT  \n" +
							"	GROUP BY HOADON_FK " +   
							")DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADON_FK \n" +
							"LEFT JOIN ( \n" +	
							"	SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
							"	FROM  \n" +	
							"	( 	\n" +					
							"		SELECT TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
							"		FROM ERP_THUTIENNPP_HOADON TTHD \n" +
							"		INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" +   		
							"		WHERE TT.NPP_FK= '"+ this.nppId +"' AND  TT.TRANGTHAI NOT IN (2)\n"	+			
				        	" 		GROUP BY HOADONNPP_FK \n" +  	
							"	) HOADONDATT  \n" +
							"	GROUP BY HOADONNPP_FK " +   
							")DATHUTIEN ON HOADON.PK_SEQ = DATHUTIEN.HOADONNPP_FK \n" +
							
							" WHERE HOADON.TONGTIENAVAT - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') > 0  \n" ;
					if(this.hdIds.length() > 0)		
					{
						query += " AND HOADON.PK_SEQ in ( "+ this.hdIds +" )" ;
					}

				query += " )\n"; 
				
				query += " UNION ALL ";
				
		// HÓA ĐƠN CỦA KHACH HANG OTC
				// Đối vs khách hàng lẻ khi mới vào Trang tạo mới: mặc định trả hết
				query += 	"(SELECT HOADON.KHID, HOADON.MAKH, HOADON.NPPID ,HOADON.MANPP , HOADON.PK_SEQ, HOADON.KYHIEU, HOADON.SOHOADON, HOADON.NGAYHOADON, \n" +	
							" (ISNULL(HOADON.TONGTIENAVAT, 0) - ISNULL(DATHUTIEN.DATHANHTOAN, '0') - ISNULL(DATHANHTOAN.DATHANHTOAN, '0')) AS SOTIENVND, \n" +
							" 0 AS DATHANHTOAN, (SELECT COUNT(PK_SEQ) FROM KHACHHANG WHERE ISNULL(KhongKyHopDong,0)=0 AND ISNULL(XuatKhau,0)=0 AND PK_SEQ=HOADON.KHID ) AS IS_KHLE \n" +
	
							"FROM ( \n" +		
							"		SELECT KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH,'0' as NPPID, '0' as MANPP, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON,  \n"+
							"        HD.TONGTIENAVAT - HD.CHIETKHAU AS TONGTIENAVAT \n"+
							"        FROM HOADON HD 	  \n"+
							"             LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
							"        WHERE  ISNULL(HD.LOAIHOADON,0) = 0 AND HD.TRANGTHAI = '2' and HD.NPP_FK = "+ this.nppId +"  \n";
			
			//TÌM KIẾM THEO KH
				if(this.khIds.length() > 0)
				{
					query += " AND KH.PK_SEQ  in (" + this.khIds + ") ";
				}
			//TÌM KIẾM THEO NVBH
				if(this.nhanvienBHIds.length() > 0)
				{
					query += "AND HD.PK_SEQ  in ( SELECT C.PK_SEQ " +
					"                     FROM DONHANG A INNER JOIN HOADON_DDH B ON A.PK_SEQ = B.DDH_FK" +
					"                          INNER JOIN HOADON C ON B.HOADON_FK= C.PK_SEQ " +
					"                     WHERE C.TRANGTHAI=2 AND A.DDKD_FK IN (" + this.nhanvienBHIds + ") ) ";
				}
			//TÌM KIẾM THEO NVGN
				if(this.nhanvienGNIds.length() > 0)
				{
					query += "AND HD.PK_SEQ  in ( SELECT C.PK_SEQ     "+                 
                    " FROM  PHIEUXUATKHO K INNER JOIN PHIEUXUATKHO_DONHANG A ON K.PK_SEQ= A.PXK_FK   "+                         
                    "       INNER JOIN HOADON_DDH B ON A.DONHANG_FK = B.DDH_FK             "+              
                    "       INNER JOIN HOADON C ON B.HOADON_FK= C.PK_SEQ               "+       
                    " WHERE C.TRANGTHAI=2 AND K.NVGN_FK IN ("+ this.nhanvienGNIds +") ) "; 
				}
			// TÌM HD TÀI CHÍNH
				if(this.hdIds.length() > 0)		
				{
					query += " AND HOADON.PK_SEQ in ( "+ this.hdIds +" )" ;
				}
				if(this.id.length() > 0)
				{
					query += "AND HD.PK_SEQ NOT IN (SELECT HOADON_FK FROM KHACHHANGTRATRUOC_HOADON WHERE KHTT_FK = '" + this.id + "') \n";
					
				}
			
				query += 	") HOADON \n" + 
							"LEFT JOIN ( \n" +	
							"	SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
							"	FROM  \n" +	
							"	( 	\n" +					
							"		SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
							"		FROM KHACHHANGTRATRUOC_HOADON TTHD \n" +
							"		INNER JOIN KHACHHANGTRATRUOC TT ON TTHD.KHTT_FK = TT.PK_SEQ \n" +   		
							"		WHERE TT.NPP_FK= '"+ this.nppId +"' AND  TT.TRANGTHAI NOT IN (2)\n"	; 
				
							
				if(this.id.trim().length() > 0){
					query += " 		AND TT.PK_SEQ  != '" + this.id + "' \n";
				}
			
				query +=	" 		GROUP BY HOADON_FK \n" +  	
							"	) HOADONDATT  \n" +
							"	GROUP BY HOADON_FK " +   
							")DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADON_FK \n" +
							"LEFT JOIN ( \n" +	
							"	SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
							"	FROM  \n" +	
							"	( 	\n" +					
							"		SELECT TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
							"		FROM ERP_THUTIENNPP_HOADON TTHD \n" +
							"		INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" +   		
							"		WHERE TT.NPP_FK= '"+ this.nppId +"' AND  TT.TRANGTHAI NOT IN (2)\n"	; 			
				query +=	" 		GROUP BY HOADONNPP_FK \n" +  	
							"	) HOADONDATT  \n" +
							"	GROUP BY HOADONNPP_FK " +   
							")DATHUTIEN ON HOADON.PK_SEQ = DATHUTIEN.HOADONNPP_FK \n" +
				//
							
							" WHERE HOADON.TONGTIENAVAT - ISNULL(DATHUTIEN.DATHANHTOAN, '0') - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') > 0  \n" ;

				query += " )\n"; 			
		
				query += "UNION ALL ";
			
				
		 // DU NO CUA KHACH HANG TINH DEN 30/06/2014
				query += " SELECT KH.PK_SEQ AS KHID, KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, DN.PK_SEQ, 'GESO' AS KYHIEU,'' AS SOHOADON, '2014-06-30' AS NGAYHOADON," +
						 "       ( DN.SONO - ISNULL(DATHUTIEN.DATHANHTOAN, '0') - ISNULL(DATHANHTOAN.DATHANHTOAN, '0'))  AS SOTIENVND, 0 AS DATHANHTOAN, 0 AS IS_KHLE " +
						 " FROM DUNO_KHACHHANG DN INNER JOIN KHACHHANG KH ON DN.KHACHHANG_FK=KH.PK_SEQ " ;
						
				query +=	 "      LEFT JOIN ( \n" +	
						 "	      SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
						 "	      FROM  \n" +	
						 "	       ( 	\n" +					
						 "		    SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
						 "			FROM KHACHHANGTRATRUOC_HOADON TTHD \n" +
						 "				 INNER JOIN KHACHHANGTRATRUOC TT ON TTHD.KHTT_FK = TT.PK_SEQ \n" +   		
						 "			WHERE TT.NPP_FK= '"+ this.nppId +"' AND  TT.TRANGTHAI NOT IN (2)\n"	; 	
				
				if(this.id.trim().length() > 0){
					query += " 		AND TT.PK_SEQ  != '" + this.id + "' \n";
				}
				
				query +=	" 		GROUP BY HOADON_FK \n" +  	
							"	   ) HOADONDATT  \n" +
							"	GROUP BY HOADON_FK " +   
							")DATHANHTOAN ON DN.PK_SEQ = DATHANHTOAN.HOADON_FK " +		
				"      LEFT JOIN ( \n" +	
				 "	      SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
				 "	      FROM  \n" +	
				 "	       ( 	\n" +					
				 "		    SELECT TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
				 "			FROM ERP_THUTIENNPP_HOADON TTHD \n" +
				 "				INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" +   		
				 "			WHERE TT.NPP_FK= '"+ this.nppId +"' AND  TT.TRANGTHAI NOT IN (2)\n"	+ 			
		       	 " 		    GROUP BY HOADONNPP_FK \n" +  	
				 "	      ) HOADONDATT  \n" +
				 "	     GROUP BY HOADONNPP_FK " +   
				 "      )DATHUTIEN ON DN.PK_SEQ = DATHUTIEN.HOADONNPP_FK " +
				"   WHERE DN.SONO - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') > 0  \n" ;
				
				//TÌM KIẾM THEO KH
				if(this.khIds.length() > 0)
				{
					query += " AND KH.PK_SEQ  in (" + this.khIds + ") ";
					
				}
				if(this.id.length() > 0)
				{
					query += " AND DN.PK_SEQ NOT IN (SELECT HOADON_FK FROM KHACHHANGTRATRUOC_HOADON WHERE KHTT_FK = '" + this.id + "') \n";
					
				}
			    
				query += " ORDER BY KHID ASC, NPPID ASC,  NGAYHOADON DESC ";
			
		        System.out.println("1.Query khoi tao hoa don1: " + query);
		  }
				ResultSet rsHoadon = db.get(query);
				List<IHoadonKHTT> hdList = new ArrayList<IHoadonKHTT>();
				if(rsHoadon != null)
				{
					try 
					{
						IHoadonKHTT hd = null;
						while(rsHoadon.next())
						{
							
							String id = rsHoadon.getString("PK_SEQ");							
							String kyhieu = rsHoadon.getString("KYHIEU");
							String sohoadon = rsHoadon.getString("SOHOADON");
							String ngayhd = rsHoadon.getString("NGAYHOADON");
							String avat = "" +rsHoadon.getDouble("SOTIENVND");
							String nppid= rsHoadon.getString("NPPID")==null ? "":rsHoadon.getString("NPPID");
							String manpp= rsHoadon.getString("MANPP")==null ? "":rsHoadon.getString("MANPP");
							String khid= rsHoadon.getString("KHID")==null ? "":rsHoadon.getString("KHID");
							String makh= rsHoadon.getString("MAKH")==null ? "": rsHoadon.getString("MAKH");
							
							String isKHLe = rsHoadon.getString("IS_KHLE");
							
							if(nppid.equals("0")) nppid="";
							if(manpp.equals("0")) manpp="";
							
							String dathanhtoan = "0";
							if(this.id.length() > 0)
							{								
								if(Math.abs(rsHoadon.getDouble("SOTIENTT")) > 0){
									dathanhtoan = "" + rsHoadon.getDouble("SOTIENTT");
								}
							}
							hd = new HoadonKHTT(id, "", kyhieu, sohoadon, ngayhd, avat, "", dathanhtoan, "","","");
							hd.setNppId(nppid);
							hd.setNppMa(manpp);
							hd.setKhId(khid);
							hd.setKhMa(makh);
							hd.setIsKHLe(isKHLe);
							hdList.add(hd);
							
						}
						rsHoadon.close();
					} 
					catch (SQLException e) {}
				}
				this.hoadonList = hdList;
				
			
		}
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{
		
	}

	
	public String getNgayghiso() 
	{
		return this.ngayghiso;
	}

	public void setNgayghiso(String ngayghiso) 
	{
		this.ngayghiso = ngayghiso;
	}

	public String getNoidungId()
	{
		return this.ndId;
	}

	public void setNoidungId(String ndId) 
	{
		this.ndId = ndId;
	}

	public ResultSet getNoidungRs()
	{
		return this.ndRs;
	}

	public void setNoidungRs(ResultSet ndRs) 
	{
		this.ndRs = ndRs;	
	}

	
	public String getSochungtu() {
		
		return this.sochungtu;
	}

	
	public void setSochungtu(String soct) {
		
		this.sochungtu = soct;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	@Override
	public String getTigia_hoadon() {
		// TODO Auto-generated method stub
		return Tigia_hoadon;
	}

	@Override
	public void setTigia_hoadon(String _Tigia_hoadon) {
		// TODO Auto-generated method stub
		this.Tigia_hoadon=_Tigia_hoadon;
	}

	public String getDinhkhoanco() {
		return this.dinhkhoanco;
	}

	public void setDinhkhoanco(String dinhkhoanco) {
		this.dinhkhoanco= dinhkhoanco;
		
	}

	public String getDinhkhoancoId() {
		return this.dinhkhoancoId;
	}

	public void setDinhkhoancoId(String dinhkhoancoId) {
		this.dinhkhoancoId = dinhkhoancoId;
		
	}

	public String getDoiTuongDinhKhoan() {
		return this.DoiTuongDinhKhoan;
	}

	public void setDoiTuongDinhKhoan(String DoiTuongDinhKhoan) {
		this.DoiTuongDinhKhoan= DoiTuongDinhKhoan;
		
	}

	public String getDoituongdinhkhoanId() {
		return this.DoituongdinhkhoanId;
	}

	public void setDoituongdinhkhoanId(String DoituongdinhkhoanId) {
		this.DoituongdinhkhoanId = DoituongdinhkhoanId;
		
	}

	public String getMaTenDoiTuongDinhKhoan() {
		return this.MaTenDoiTuongDinhKhoan;
	}

	public void setMaTenDoiTuongDinhKhoan(String MaTenDoiTuongDinhKhoan) {
		this.MaTenDoiTuongDinhKhoan= MaTenDoiTuongDinhKhoan;
		
	}

	@Override
	public String getHdId() {
		// TODO Auto-generated method stub
		return this.hdId;
	}

	@Override
	public void setHdId(String hdId) {
		this.hdId =hdId;
		
	}

	@Override
	public String getLydonop() {
		return this.lydonop;
	}

	@Override
	public void setLydonop(String lydonop) {
		this.lydonop = lydonop;
		
	}

	public String getNppIds() {
		
		return this.nppIds;
	}

	
	public void setNppIds(String nppIds ) {
		
		this.nppIds = nppIds ;
	}
	
	public String getHdIds() {
		
		return this.hdIds;
	}

	
	public void setHdIds(String hdIds ) {
		
		this.hdIds = hdIds ;
	}

	@Override
	public String getKhIds() {
		// TODO Auto-generated method stub
		return this.khIds;
	}

	@Override
	public void setKhIds(String khIds) {
		this.khIds=khIds;
		
	}

	@Override
	public ResultSet getKhRs() {
		// TODO Auto-generated method stub
		return this.KhRs;
	}

	@Override
	public void setKhRs(ResultSet khRs) {
		this.KhRs=khRs;
		
	}

	public String getNhanvienGNIds()
	{
		return this.nhanvienGNIds;
	}
	public void setNhanvienGNIds(String nhanvienGNIds)
	{
		this.nhanvienGNIds = nhanvienGNIds;
	}
	public ResultSet getNhanvienGNRs()
	{
		return this.NhanvienGNRs;
	}
	public void setNhanvienGNRs(ResultSet nhanvienGNRs)
	{
		this.NhanvienGNRs= nhanvienGNRs;
	}
	
	public String getNhanvienBHIds()
	{
		return this.nhanvienBHIds;
	}
	public void setNhanvienBHIds(String nhanvienBHIds)
	{
		this.nhanvienBHIds= nhanvienBHIds;
	}
	public ResultSet getNhanvienBHRs()
	{
		return this.NhanvienBHRs;
	}
	public void setNhanvienBHRs(ResultSet nhanvienBHRs)
	{
		this.NhanvienBHRs= nhanvienBHRs;
	}

	@Override
	public String getDoituongId() {
		// TODO Auto-generated method stub
		return this.doituongId ;
	}

	@Override
	public void setDoituongId(String doituongId) {
		this.doituongId = doituongId;
		
	}
	
	
}
