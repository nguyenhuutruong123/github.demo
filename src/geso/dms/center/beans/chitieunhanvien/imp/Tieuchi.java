package geso.htp.center.beans.chitieunhanvien.imp;

import java.util.List;

import geso.htp.center.beans.chitieunhanvien.INhanvien;
import geso.htp.center.beans.chitieunhanvien.ITieuchi;

public class Tieuchi implements ITieuchi {
	
	String id;
	String diengiai;
	List<INhanvien> nhanvien;
	
	public Tieuchi(){
		this.id = "";
		this.diengiai = "";
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
	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	@Override
	public String getDiengiai() {
		return this.diengiai;
	}

	@Override
	public List<INhanvien> getNhanvien() {
		return this.nhanvien;
	}

	@Override
	public void setNhanvien(List<INhanvien> nhanvien) {
		this.nhanvien = nhanvien;
	}
}
