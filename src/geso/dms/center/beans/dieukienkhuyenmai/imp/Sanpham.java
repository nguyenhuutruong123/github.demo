package geso.dms.center.beans.dieukienkhuyenmai.imp;

import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;

public class Sanpham implements ISanpham
{	
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String id1;
	String id2;
	String id3;
	String id4;
	
	String masp;
	String tensp;
	String soluong;
	String dongia;
	
	String masp4;
	String tensp4;
	String soluong4;
	String dongia4;
	
	String masp1;
	String tensp1;
	String soluong1;
	String dongia1;
	
	String masp2;
	String tensp2;
	String soluong2;
	String dongia2;
	
	String masp3;
	String tensp3;
	String soluong3;
	String dongia3;
	
	String thuongSS;
	String thuongTDSS;
	String thuongSR;
	String thuongTDSR;
	String thuongASM;
	String thuongTDASM;
		
	public Sanpham()
	{
		this.id = "";
		this.id1 = "";
		this.id2 = "";
		this.id3 = "";
		this.id4 = "";
		
		
		this.masp = "";
		this.tensp = "";
		this.soluong = "";
		this.dongia = "";
		
		this.masp1 = "";
		this.tensp1 = "";
		this.soluong1 = "";
		this.dongia1 = "";
		
		this.masp2 = "";
		this.tensp2 = "";
		this.soluong2 = "";
		this.dongia2 = "";
		
		this.masp3 = "";
		this.tensp3 = "";
		this.soluong3 = "";
		this.dongia3 = "";
		
		this.masp4 = "";
		this.tensp4 = "";
		this.soluong4 = "";
		this.dongia4 = "";
		
		this.thuongSS = "";
		this.thuongTDSS = "";
		this.thuongSR = "";
		this.thuongTDSR = "";
		this.thuongASM = "";
		this.thuongTDASM = "";
	}
	
	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.soluong = param[3];
		this.dongia = param[4];
		
		this.thuongSS = "";
		this.thuongTDSS = "";
		this.thuongSR = "";
		this.thuongTDSR = "";
		this.thuongASM = "";
		this.thuongTDASM = "";
	}
	
	public Sanpham(String spId, String spMa, String spTen, String soluong, String dongia)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.soluong = soluong;
		this.dongia = dongia;
		
		this.thuongSS = "";
		this.thuongTDSS = "";
		this.thuongSR = "";
		this.thuongTDSR = "";
		this.thuongASM = "";
		this.thuongTDASM = "";
	}
	
	public String getId() 
	{	
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;	
	}
	
	public String getMasanpham()
	{	
		return this.masp;
	}
	
	public void setMasanpham(String masp)
	{
		this.masp = masp;		
	}
	
	public String getTensanpham() 
	{	
		return this.tensp;
	}
	
	public void setTensanpham(String tensp) 
	{
		this.tensp = tensp;	
	}

	public String getSoluong()
	{
		return this.soluong;
	}

	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}

	public String getDongia()
	{
		return this.dongia;
	}

	public void setDongia(String dongia) 
	{
		this.dongia = dongia;
	}

	
	public String getThuongSS() 
	{
		return this.thuongSS;
	}

	public void setThuongSS(String thuongSS) 
	{
		this.thuongSS = thuongSS;
	}

	public String getThuongTDSS() 
	{
		return this.thuongTDSS;
	}

	public void setThuongTDSS(String thuongTDSS) 
	{
		this.thuongTDSS = thuongTDSS;
	}

	public String getThuongSR()
	{
		return this.thuongSR;
	}

	public void setThuongSR(String thuongSR) 
	{
		this.thuongSR = thuongSR;
	}

	public String getThuongTDSR() 
	{
		return this.thuongTDSR;
	}

	public void setThuongTDSR(String thuongTDSR)
	{
		this.thuongTDSR = thuongTDSR;
	}

	public String getThuongASM() 
	{
		return this.thuongASM;
	}

	public void setThuongASM(String thuongASM) 
	{
		this.thuongASM = thuongASM;
	}

	public String getThuongTDASM()
	{
		return this.thuongTDASM;
	}

	public void setThuongTDASM(String thuongTDASM) 
	{
		this.thuongTDASM = thuongTDASM;
	}

	
	public String getId1() {
		
		return id1;
	}

	@Override
	public void setId1(String id1) {
		this.id1 = id1;
		
	}

	
	public String getMasanpham1() {
		
		return masp1;
	}


	public void setMasanpham1(String masp1) {
		this.masp1 = masp1;
	}

	
	public String getTensanpham1() {
		
		return tensp1;
	}

	
	public void setTensanpham1(String tensp1) {
		this.tensp1 = tensp1;
		
	}

	
	public String getSoluong1() {
	
		return soluong1;
	}

	
	public void setSoluong1(String soluong1) {
		
		this.soluong1 = soluong1;
	}

	
	public String getDongia1() {
		
		return dongia1;
	}

	
	public void setDongia1(String dongia1) {
		this.dongia1 = dongia1;
		
	}

	
	public String getId2() {
		return id2;
	}

	
	public void setId2(String id2) {
		this.id2 = id2;
		
	}

	public String getMasanpham2() {
		return masp2;
	}

	
	public void setMasanpham2(String masp2) {
		this.masp2 = masp2;
		
	}

	
	public String getTensanpham2() {
		
		return tensp2;
	}

	
	public void setTensanpham2(String tensp2) {
		this.tensp2 = tensp2;
		
	}

	
	public String getSoluong2() {
		
		return soluong2;
	}

	
	public void setSoluong2(String soluong2) {
		this.soluong2 = soluong2;
		
	}

	
	public String getDongia2() {
		
		return dongia2;
	}


	public void setDongia2(String dongia2) {
		this.dongia2 = dongia2;
		
	}


	public String getId3() {
		
		return id3;
	}

	
	public void setId3(String id3) {
		this.id3 = id3;
		
	}

	
	public String getMasanpham3() {
		return masp3;
	}

	
	public void setMasanpham3(String masp3) {
		this.masp3 = masp3;
		
	}


	public String getTensanpham3() {
		
		return tensp3;
	}

	
	public void setTensanpham3(String tensp3) {
		this.tensp3 = tensp3;
		
	}

	
	public String getSoluong3() {
	
		return soluong3;
	}


	public void setSoluong3(String soluong3) {
		this.soluong3 = soluong3;
		
	}

	
	public String getDongia3() {
		
		return dongia3;
	}


	public void setDongia3(String dongia3) {
		this.dongia3 = dongia3;
		
	}

	
	public String getId4() {
		
		return id4;
	}

	
	public void setId4(String id4) {
	
		this.id4 = id4;
	}

	
	public String getMasanpham4() {
		return masp4;
	}

	
	public void setMasanpham4(String masp4) {
		this.masp4 = masp4;
		
	}

	
	public String getTensanpham4() {
		
		return tensp4;
	}

	
	public void setTensanpham4(String tensp4) {
		this.tensp4 =  tensp4;
		
	}

	
	public String getSoluong4() {
		
		return soluong4;
	}

	
	public void setSoluong4(String soluong4) {
		this.soluong4 = soluong4;}

	
	public String getDongia4() {
		
		return dongia4;
	}


	public void setDongia4(String dongia4) {
	this.dongia4 = dongia4;
	}
	
}

