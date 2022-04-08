

package geso.dms.center.beans.chitieunhaphanphoi.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import geso.dms.center.beans.chitieunhaphanphoi.ICTNhaphanphoi;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.center.beans.chitieunhaphanphoi.imp.CTNhaphanphoi_NSP;
import geso.dms.center.beans.chitieunhaphanphoi.ICTNhaphanphoi_NSP;
public class CTNhaphanphoi implements ICTNhaphanphoi {


	String Id = "";
	String nppId ="";
	String ten = "";
	//double salesout = 0;
	//double donhang = 0;
	//double skudonhang = 0;
	//double sokhmoi = 0;
	//double salesin = 0;
	//double chitieuluong = 0;
	double DoanhSoBanRa  = 0;
	double SoLuongBanRa  = 0;
	double DoanhSoMuaVao  = 0;
	double SoLuongMuaVao  = 0;
	
	double TonKhoNgay  = 0;
	double SoDonHang  = 0;
	double TyLeGiaoHang  = 0;
	double ThoiGianGiaoHang = 0;
	List<ICTNhaphanphoi_NSP> ctNspList = new ArrayList<ICTNhaphanphoi_NSP>();
	
	
	
	public CTNhaphanphoi(String[] param,dbutils db,String chitieuId)
	{
		int i = 0;
		this.Id = param[i++];
		this.nppId = param[i++];
		this.ten  = param[i++];
		
		this.DoanhSoBanRa = Double.parseDouble(param[i++]);
		this.SoLuongBanRa = Double.parseDouble(param[i++]);
		this.DoanhSoMuaVao = Double.parseDouble(param[i++]);
		this.SoLuongMuaVao = Double.parseDouble(param[i++]);
		
		this.SoDonHang = Double.parseDouble(param[i++]);
		this.TyLeGiaoHang = Double.parseDouble(param[i++]);
		this.ThoiGianGiaoHang = Double.parseDouble(param[i++]);
		this.initNSP(chitieuId,db);
	}
	
	public void initNSP(String chitieuId,dbutils db)
	{
		try
		{
			ctNspList = new ArrayList<ICTNhaphanphoi_NSP>();			
			String query =  "select a.loai, a.nsp_fk,b.TEN as tennsp, a.Doanhso  -- " + 
			 "\n  from CHITIEUNHAPHANPHOI_NPP_NSP a -- " + 
			 "\n 	inner join nhomsanphamchitieu b on a.NSP_FK = b.PK_SEQ -- " + 
			 "\n where CTNPP_NPP_FK ='"+this.Id+"'";
					
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String[] param = new String[4];
				param[0] = rs.getString("loai");
				param[1] = rs.getString("nsp_fk");
				param[2] = rs.getString("tennsp");
				param[3] = rs.getString("Doanhso");
				//param[4] = rs.getString("Soluong");
				ICTNhaphanphoi_NSP a = new CTNhaphanphoi_NSP(param);
				ctNspList.add(a);
			}
			rs.close();
		}
		catch (Exception e) {
			 ctNspList = new ArrayList<ICTNhaphanphoi_NSP>();
		}
	}
	
	public List<ICTNhaphanphoi_NSP> getCtNspList() {
		return ctNspList;
	}
	public void setCtNspList(List<ICTNhaphanphoi_NSP> ctNspList) {
		this.ctNspList = ctNspList;
	}
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getTen() {
		return ten;
	}
	public String getNppId() {
		return nppId;
	}
	public void setNppId(String manv) {
		this.nppId = manv;
	}
	
	//this.DoanhSoBanRa = Double.parseDouble(param[3]);
	public double getDoanhSoBanRa() {
		return DoanhSoBanRa;
	}
	//this.SoLuongBanRa = Double.parseDouble(param[4]);
	public double getSoLuongBanRa() {
		return SoLuongBanRa;
	}
	//this.DoanhSoMuaVao = Double.parseDouble(param[5]);
	public double getDoanhSoMuaVao() {
		return DoanhSoMuaVao;
	}	
	//this.SoLuongMuaVao = Double.parseDouble(param[6]);
	public double getSoLuongMuaVao() {
		return SoLuongMuaVao;
	}
	//this.TonKhoNgay = Double.parseDouble(param[7]);
	public double getTonKhoNgay() {
		return TonKhoNgay;
	}
	//this.SoDonHang = Double.parseDouble(param[8]);
	public double getSoDonHang() {
		return SoDonHang;
	}
	//this.TyLeGiaoHang = Double.parseDouble(param[9]);
	public double getTyLeGiaoHang() {
		return TyLeGiaoHang;
	}
	public double getThoiGianGiaoHang() {
		return ThoiGianGiaoHang;
	}
	
}
