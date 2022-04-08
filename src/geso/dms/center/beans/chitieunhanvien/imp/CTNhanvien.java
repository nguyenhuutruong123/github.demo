

package geso.htp.center.beans.chitieunhanvien.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import geso.htp.center.beans.chitieunhanvien.ICTNhanvien;
import geso.htp.center.util.Utility;
import geso.htp.distributor.db.sql.dbutils;
import geso.htp.center.beans.chitieunhanvien.imp.CTNhanvien_NSP;
import geso.htp.center.beans.chitieunhanvien.ICTNhanvien_NSP;
public class CTNhanvien implements ICTNhanvien {

	String loainv = "1";
	String Id = "";
	String manv ="";
	String ten = "";
	double salesout = 0;
	double donhang = 0;
	double skudonhang = 0;
	double sokhmoi = 0;
	double salesin = 0;
	double chitieuluong = 0;
	
	List<ICTNhanvien_NSP> ctNspList = new ArrayList<ICTNhanvien_NSP>();
	
	
	
	public CTNhanvien(String[] param,dbutils db,String chitieuId)
	{
		this.Id = param[0];
		this.manv = param[1];
		this.ten  = param[2];
		this.chitieuluong = Double.parseDouble(param[3]);
		this.salesout = Double.parseDouble(param[4]);
		this.salesin = Double.parseDouble(param[5]);
		this.donhang = Double.parseDouble(param[6]);
		this.skudonhang = Double.parseDouble(param[7]);
		this.sokhmoi = Double.parseDouble(param[8]);
		this.loainv = param[9];
		this.initNSP(chitieuId,db);
	}
	
	public void initNSP(String chitieuId,dbutils db)
	{
		try
		{
			ctNspList = new ArrayList<ICTNhanvien_NSP>();
			String query = "";
			if(loainv.equals("1"))// SR
			{
				query =  "select a.loai, a.nsp_fk,b.TEN as tennsp, a.CHITIEU  -- " + 
						 "\n  from ChiTieuNhanVien_SR_NSP a -- " + 
						 "\n 	inner join NHOMSANPHAM b on a.NSP_FK = b.PK_SEQ -- " + 
						 "\n where CTNVCT_FK ='"+this.Id+"'";
			}else
				if(loainv.equals("2"))// SS
				{
					query =  "select a.loai, a.nsp_fk,b.TEN as tennsp, a.CHITIEU  -- " + 
							 "\n  from ChiTieuNhanVien_SS_NSP a -- " + 
							 "\n 	inner join NHOMSANPHAM b on a.NSP_FK = b.PK_SEQ -- " + 
							 "\n where CTNVCT_FK ='"+this.Id+"'";
				}
				else
					if(loainv.equals("3"))// ASM
					{
						query =  "select a.loai, a.nsp_fk,b.TEN as tennsp, a.CHITIEU  -- " + 
								 "\n  from ChiTieuNhanVien_ASM_NSP a -- " + 
								 "\n 	inner join NHOMSANPHAM b on a.NSP_FK = b.PK_SEQ -- " + 
								 "\n where CTNVCT_FK ='"+this.Id+"'";
					}
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String[] param = new String[4];
				param[0] = rs.getString("loai");
				param[1] = rs.getString("nsp_fk");
				param[2] = rs.getString("tennsp");
				param[3] = rs.getString("CHITIEU");
				ICTNhanvien_NSP a = new CTNhanvien_NSP(param);
				ctNspList.add(a);
			}
			rs.close();
		}
		catch (Exception e) {
			 ctNspList = new ArrayList<ICTNhanvien_NSP>();
		}
	}
	
	public List<ICTNhanvien_NSP> getCtNspList() {
		return ctNspList;
	}
	public void setCtNspList(List<ICTNhanvien_NSP> ctNspList) {
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
	public String getManv() {
		return manv;
	}
	public void setManv(String manv) {
		this.manv = manv;
	}
	public double getChitieuluong() {
		return chitieuluong;
	}
	public void setChitieuluong(double chitieuluong) {
		this.chitieuluong = chitieuluong;
	}
	
	public double getSalesin() {
		return salesin;
	}
	public void setSalesin(double salesin) {
		this.salesin = salesin;
	}
	public double getSalesout() {
		return salesout;
	}
	public void setSalesout(double salesout) {
		this.salesout = salesout;
	}
	public double getDonhang() {
		return donhang;
	}
	public void setDonhang(double donhang) {
		this.donhang = donhang;
	}
	public void setSkudonhang(double skudonhang) {
		this.skudonhang = skudonhang;
	}
	public double getSkudonhang() {
		return skudonhang;
	}
	public void setSokhmoi(double sokhmoi) {
		this.sokhmoi = sokhmoi;
	}
	public double getSokhmoi() {
		return sokhmoi;
	}
}
