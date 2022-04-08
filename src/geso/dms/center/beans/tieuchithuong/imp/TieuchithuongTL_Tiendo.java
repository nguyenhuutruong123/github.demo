package geso.dms.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import geso.dms.center.beans.tieuchithuong.ITieuchithuongTL;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTL_Tiendo;
import geso.dms.center.db.sql.dbutils;

public class TieuchithuongTL_Tiendo implements ITieuchithuongTL_Tiendo 
{
	String muc;
	String tiendo;
	String tungay;
	String denngay;
	String phaidat;
	
	public TieuchithuongTL_Tiendo()
	{
		muc = "";
		tiendo = "";
		tungay = "";
		denngay = "";
		phaidat = "";
	}
	
	@Override
	public String getMuc() {
		return this.muc;
	}
	@Override
	public void setMuc(String muc) {
		this.muc = muc;
	}
	@Override
	public String getTiendo() {
		return this.tiendo;
	}
	@Override
	public void setTiendo(String tiendo) {
		this.tiendo = tiendo;
	}
	@Override
	public String getTungay() {
		return this.tungay;
	}
	@Override
	public void setTungay(String tungay) {
		this.tungay = tungay;
	}
	@Override
	public String getDenngay() {
		return this.denngay;
	}
	@Override
	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}
	@Override
	public String getPhaidat() {
		return this.phaidat;
	}
	@Override
	public void setPhaidat(String phaidat) {
		this.phaidat = phaidat;
	}
	
}
