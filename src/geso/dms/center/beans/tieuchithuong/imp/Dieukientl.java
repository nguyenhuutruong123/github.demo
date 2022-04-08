package geso.dms.center.beans.tieuchithuong.imp;

import java.util.ArrayList;

import geso.dms.center.beans.tieuchithuong.IDieukientl;
import geso.dms.center.beans.tieuchithuong.IDieukientlDetail;

public class Dieukientl implements IDieukientl
{
	String id;
	String id1;
	String id2;
	String id3;
	String id4;
	String diengiai;
	String tongluong;
	String tongtien;
	
	String diengiai1;
	String tongluong1;
	String tongtien1;
	
	String diengiai2;
	String tongluong2;
	String tongtien2;
	
	String diengiai4;
	String tongluong4;
	String tongtien4;
	
	String diengiai3;
	String tongluong3;
	String tongtien3;
	String pheptoan;
	String pheptoan1;
	String pheptoan2;
	String pheptoan3;
	String pheptoan4;
	String thutudk;
	String thutudk1;
	String thutudk2;
	String thutudk3;
	String thutudk4;
	boolean theothung;
	
	IDieukientlDetail dkDetail;
	IDieukientlDetail dkDetail1;
	IDieukientlDetail dkDetail2;
	IDieukientlDetail dkDetail3;
	IDieukientlDetail dkDetail4;
	
	
	public Dieukientl()
	{
		this.id = "";
		this.id1 = "";
		this.id2 = "";
		this.id3 = "";
		this.id4 = "";
		this.diengiai = "";
		this.tongluong = "";
		this.tongtien = "";
		this.pheptoan = "";
		this.thutudk = "";
		this.theothung = false;
		
		this.diengiai1 = "";
		this.tongluong1 = "";
		this.tongtien1 = "";
		this.pheptoan1 = "";
		this.thutudk1 = "";
		
		this.diengiai2 = "";
		this.tongluong2 = "";
		this.tongtien2 = "";
		this.pheptoan2 = "";
		this.thutudk2 = "";
		
		this.diengiai3 = "";
		this.tongluong3 = "";
		this.tongtien3 = "";
		this.pheptoan3 = "";
		this.thutudk3 = "";
		
		this.diengiai4 = "";
		this.tongluong4 = "";
		this.tongtien4 = "";
		this.pheptoan4 = "";
		this.thutudk4 = "";
		
		this.dkDetail = new DieukientlDetail();
		this.dkDetail1 = new DieukientlDetail();
		this.dkDetail2 = new DieukientlDetail();
		this.dkDetail3 = new DieukientlDetail();
		this.dkDetail4 = new DieukientlDetail();
	}
	
	public Dieukientl(String id, String diengiai, String tongluong, String tongtien, String thutudk)
	{
		this.id = id;
		this.id1 = id1;
		this.id2 = id2;
		this.id3 = id3;
		this.id4 = id4;
		this.diengiai = diengiai;
		this.tongluong = tongluong;
		this.tongtien = tongtien;
		this.thutudk = thutudk;
		this.theothung = false;
		this.diengiai1 = "";
		this.tongluong1 = "";
		this.tongtien1 = "";
		this.pheptoan1 = "";
		this.thutudk1 = "";
		
		this.diengiai2 = "";
		this.tongluong2 = "";
		this.tongtien2 = "";
		this.pheptoan2 = "";
		this.thutudk2 = "";
		
		this.diengiai3 = "";
		this.tongluong3 = "";
		this.tongtien3 = "";
		this.pheptoan3 = "";
		this.thutudk3 = "";
		
		this.diengiai4 = "";
		this.tongluong4 = "";
		this.tongtien4 = "";
		this.pheptoan4 = "";
		this.thutudk4 = "";
		this.dkDetail = new DieukientlDetail();
		this.dkDetail1 = new DieukientlDetail();
		this.dkDetail2 = new DieukientlDetail();
		this.dkDetail3 = new DieukientlDetail();
		this.dkDetail4 = new DieukientlDetail();
	}
	
	
	
	
	public String getId() 
	{	
		return this.id;
	}
	
	public void setId(String Id) 
	{
		this.id = Id;		
	}
	
	public String getId1() 
	{	
		return this.id1;
	}
	
	public void setId1(String Id1) 
	{
		this.id1 = Id1;		
	}
	
	public String getId2() 
	{	
		return this.id2;
	}
	
	public void setId2(String Id2) 
	{
		this.id2 = Id2;		
	}
	
	public String getId3() 
	{	
		return this.id3;
	}
	
	public void setId3(String Id3) 
	{
		this.id3 = Id3;		
	}
	
	public String getId4() 
	{	
		return this.id4;
	}
	
	public void setId4(String Id4) 
	{
		this.id4 = Id4;		
	}
	
	public String getDiengiai() 
	{		
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;		
	}
	
	public String getTongluong() 
	{		
		return this.tongluong;
	}
	
	public void setTongluong(String tongluong) 
	{
		this.tongluong = tongluong;		
	}
	
	public String getTongtien() 
	{		
		return this.tongtien;
	}
	
	public void setTongtien(String tongtien) 
	{
		this.tongtien = tongtien;	
	}
	
	
	
	
	public String getDiengiai1() 
	{		
		return this.diengiai1;
	}
	
	public void setDiengiai1(String diengiai1) 
	{
		this.diengiai1 = diengiai1;		
	}
	
	public String getTongluong1() 
	{		
		return this.tongluong1;
	}
	
	public void setTongluong1(String tongluong1) 
	{
		this.tongluong1 = tongluong1;		
	}
	
	public String getTongtien1() 
	{		
		return this.tongtien1;
	}
	
	public void setTongtien1(String tongtien1) 
	{
		this.tongtien1 = tongtien1;	
	}
	
	
	
	public String getDiengiai2() 
	{		
		return this.diengiai2;
	}
	
	public void setDiengiai2(String diengiai2) 
	{
		this.diengiai2 = diengiai2;		
	}
	
	public String getTongluong2() 
	{		
		return this.tongluong2;
	}
	
	public void setTongluong2(String tongluong2) 
	{
		this.tongluong2 = tongluong2;		
	}
	
	public String getTongtien2() 
	{		
		return this.tongtien2;
	}
	
	public void setTongtien2(String tongtien2) 
	{
		this.tongtien2 = tongtien2;	
	}
	
	
	
	public String getDiengiai3() 
	{		
		return this.diengiai3;
	}
	
	public void setDiengiai3(String diengiai3) 
	{
		this.diengiai3 = diengiai3;		
	}
	
	public String getTongluong3() 
	{		
		return this.tongluong3;
	}
	
	public void setTongluong3(String tongluong3) 
	{
		this.tongluong3 = tongluong3;		
	}
	
	public String getTongtien3() 
	{		
		return this.tongtien3;
	}
	
	public void setTongtien3(String tongtien3) 
	{
		this.tongtien3 = tongtien3;	
	}
	
	
	
	
	
	public String getDiengiai4() 
	{		
		return this.diengiai4;
	}
	
	public void setDiengiai4(String diengiai4) 
	{
		this.diengiai4 = diengiai4;		
	}
	
	public String getTongluong4() 
	{		
		return this.tongluong4;
	}
	
	public void setTongluong4(String tongluong4) 
	{
		this.tongluong4 = tongluong4;		
	}
	
	public String getTongtien4() 
	{		
		return this.tongtien4;
	}
	
	public void setTongtien4(String tongtien4) 
	{
		this.tongtien4 = tongtien4;	
	}
	
	public String getThutudk() 
	{		
		return this.thutudk;
	}
	
	public void setThutudk(String thutu) 
	{
		this.thutudk = thutu;		
	}
	
	public String getThutudk1() 
	{		
		return this.thutudk1;
	}
	
	public void setThutudk1(String thutu1) 
	{
		this.thutudk1 = thutu1;		
	}
	public String getThutudk2() 
	{		
		return this.thutudk2;
	}
	
	public void setThutudk2(String thutu2) 
	{
		this.thutudk2 = thutu2;		
	}
	public String getThutudk3() 
	{		
		return this.thutudk3;
	}
	
	public void setThutudk3(String thutu3) 
	{
		this.thutudk3 = thutu3;		
	}
	public String getThutudk4() 
	{		
		return this.thutudk4;
	}
	
	public void setThutudk4(String thutu4) 
	{
		this.thutudk4 = thutu4;		
	}
	
	public String getPheptoan() 
	{		
		return this.pheptoan;
	}
	
	public void setPheptoan(String pheptoan) 
	{
		this.pheptoan = pheptoan;		
	}

	public IDieukientlDetail getDieukienDetail() 
	{
		return this.dkDetail;
	}

	public void setDieukienDetail(IDieukientlDetail dieukienDetail) 
	{
		this.dkDetail = dieukienDetail;
	}
	
	public IDieukientlDetail getDieukienDetail1() 
	{
		return this.dkDetail1;
	}

	public void setDieukienDetail1(IDieukientlDetail dieukienDetail1) 
	{
		this.dkDetail1 = dieukienDetail1;
	}
	
	public IDieukientlDetail getDieukienDetail2() 
	{
		return this.dkDetail2;
	}

	public void setDieukienDetail2(IDieukientlDetail dieukienDetail2) 
	{
		this.dkDetail2 = dieukienDetail2;
	}
	
	public IDieukientlDetail getDieukienDetail3() 
	{
		return this.dkDetail3;
	}

	public void setDieukienDetail3(IDieukientlDetail dieukienDetail3) 
	{
		this.dkDetail3 = dieukienDetail3;
	}
	
	public IDieukientlDetail getDieukienDetail4() 
	{
		return this.dkDetail4;
	}

	public void setDieukienDetail4(IDieukientlDetail dieukienDetail4) 
	{
		this.dkDetail4 = dieukienDetail4;
	}

	public boolean isTheothung() 
	{
		return this.theothung;
	}

	public void setTheothung(boolean theothung) 
	{
		this.theothung = theothung;
	}

	

}
