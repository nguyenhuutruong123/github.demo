

package geso.htp.center.beans.chitieunhanvien.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import geso.htp.center.beans.chitieunhanvien.ICTNhanvien_NSP;
import geso.htp.center.util.Utility;
import geso.htp.distributor.db.sql.dbutils;

public class CTNhanvien_NSP implements ICTNhanvien_NSP {

	String loai = "";
	String nsp_fk = "";
	String tennsp = "";
	double chitieu = 0;
	
	public CTNhanvien_NSP(String[] param)
	{
		if(param[0].equals("0"))
			this.loai = "Không xác định";
		else if(param[0].equals("1"))
			this.loai ="Doanh số nhóm trọng tâm";
		else if(param[0].equals("2"))
			this.loai ="Độ phủ nhóm sản phẩm";
		else if(param[0].equals("3"))
			this.loai ="Doanh số SKU/Doanh số tổng";
		else this.loai = param[0];
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
	public double getChitieu() {
		return chitieu;
	}
	public void setChitieu(double chitieu) {
		this.chitieu = chitieu;
	}
}
