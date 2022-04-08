

package geso.htp.center.beans.chitieunhanvien.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import geso.htp.center.beans.chitieunhanvien.*;
import geso.htp.center.util.Utility;
import geso.htp.distributor.db.sql.dbutils;

public class Nhanvien implements INhanvien {

	String id;
	String ten;
	String tuan1;
	String tuan2;
	String tuan3;
	String tuan4;
	
	public Nhanvien(){
		this.id = "";
		this.ten = "";
		this.tuan1 = "";
		this.tuan2 = "";
		this.tuan3 = "";
		this.tuan4 = "";
	}

	@Override
	public void setID(String id) {
		this.id = id;
	}

	@Override
	public String getID() {
		return this.id;
	}

	@Override
	public void setTen(String ten) {
		this.ten = ten;
	}

	@Override
	public String getTen() {
		return this.ten;
	}

	@Override
	public void setTuan1(String tuan1) {
		this.tuan1 = tuan1;
	}

	@Override
	public String getTuan1() {
		return this.tuan1;
	}

	@Override
	public void setTuan2(String tuan2) {
		this.tuan2 = tuan2;
	}

	@Override
	public String getTuan2() {
		return this.tuan2;
	}

	@Override
	public void setTuan3(String tuan3) {
		this.tuan3 = tuan3;
	}

	@Override
	public String getTuan3() {
		return this.tuan3;
	}

	@Override
	public void setTuan4(String tuan4) {
		this.tuan4 = tuan4;
	}

	@Override
	public String getTuan4() {
		return this.tuan4;
	}
	
}
