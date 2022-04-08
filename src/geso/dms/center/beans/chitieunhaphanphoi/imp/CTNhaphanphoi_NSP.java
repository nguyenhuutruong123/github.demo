

package geso.dms.center.beans.chitieunhaphanphoi.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import geso.dms.center.beans.chitieunhaphanphoi.ICTNhaphanphoi_NSP;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

public class CTNhaphanphoi_NSP implements ICTNhaphanphoi_NSP {

	String loai = "";
	String nsp_fk = "";
	String tennsp = "";
	double chitieu = 0;
	double soluong = 0;
	
	public CTNhaphanphoi_NSP(String[] param)
	{
		if(param[0].equals("1"))
			this.loai ="Chỉ tiêu bán ra(Doanh Số)";		
		else  if(param[0].equals("2"))
			this.loai ="Chỉ tiêu bán ra(Số lượng)";
		else  if(param[0].equals("3"))
			this.loai ="Chỉ tiêu mua vào(Doanh Số)";
		else  if(param[0].equals("4"))
			this.loai ="Chỉ tiêu mua vào(Số lượng)";
		else  if(param[0].equals("6"))
			this.loai ="Tồn kho theo quy định(Ngày)";
		else
			
			this.loai = param[0];
		
		this.nsp_fk = param[1];
		this.tennsp  = param[2];
		this.chitieu = Double.parseDouble(param[3]);
		
	}
	public String getLoai() {
		return loai;
	}
	public void setLoai(String loai) {
		this.loai = loai;
	}
	public String getNsp_fk() {
		return nsp_fk;
	}
	public void setNsp_fk(String nsp_fk) {
		this.nsp_fk = nsp_fk;
	}
	public String getTennsp() {
		return tennsp;
	}
	public void setTennsp(String tennsp) {
		this.tennsp = tennsp;
	}

	
	public double getSoluong() {
		return soluong;
	}
	public void setSoluong(double chitieu) {
		this.soluong = chitieu;
	}
	public double getChitieu() {

		return chitieu;
	}
	public void setChitieu(double DoanhSo) {
		this.chitieu = DoanhSo;
		
	}
	
}
