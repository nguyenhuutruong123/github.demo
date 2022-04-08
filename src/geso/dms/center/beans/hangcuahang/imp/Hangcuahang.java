package geso.dms.center.beans.hangcuahang.imp;

import geso.dms.center.beans.hangcuahang.IHangcuahang;
import geso.dms.center.beans.hangcuahang.IHangcuahangList;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Hangcuahang implements IHangcuahang
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String hangcuahang;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String tumuc;
	String denmuc;
	String thangtb;
	public String getThangtb() {
		return thangtb;
	}

	public void setThangtb(String thangtb) {
		this.thangtb = thangtb;
	}


	dbutils db;
	
	public Hangcuahang(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.hangcuahang = param[1];
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.tumuc = param[4];
		this.denmuc = param[5];
		this.ngaytao = param[6];
		this.nguoitao = param[7];
		this.ngaysua = param[8];
		this.nguoisua = param[9];
		this.thangtb=param[10];
		this.msg = "";
		
	}
	
	public Hangcuahang(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.hangcuahang = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.tumuc = "";
		this.denmuc = "";
		this.thangtb="";
		if(id.length() > 0)
			this.init();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getHangcuahang() 
	{
		return this.hangcuahang;
	}

	public void setHangcuahang(String hangcuahang) 
	{
		this.hangcuahang = hangcuahang;
	}
	
	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNguoitao() 
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;
	}

	public String getNgaysua() 
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoisua() 
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public boolean CreateHch()
	{
		Object[] data;
		try{
			this.db.getConnection().setAutoCommit(false);
			
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			
			String command = "insert into hangcuahang(diengiai,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,hang,tumuc,denmuc,ThangTB) values(?,?,?,?,?,?,?,?,?,?)"; 
			
			data = new Object[] {this.diengiai, this.trangthai, this.ngaytao, this.ngaytao, this.userId, this.userId, this.hangcuahang, this.tumuc.replaceAll(",", ""), this.denmuc.replaceAll(",", ""), this.thangtb};
			if(this.db.updateQueryByPreparedStatement(command, data) != 1){
				//this.msg = "insert into hangcuahang values(N'" + this.diengiai + "','" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "','" + this.hangcuahang + "')";
				this.msg="Ban khong the luu duoc. Loi o dong lenh sau : "+command;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			/*
			 * thuc hien cap nhat lai smartid
			 * 
			 */
			
			this.id = db.getPk_seq();
			String sql_update_smartid="update hangcuahang set smartid=? where pk_seq=?";
			
			data = new Object[] {this.id, this.id};
			try{
				if(this.db.updateQueryByPreparedStatement(sql_update_smartid, data) != 1){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
						System.out.println("Khong The Thuc Hien Luu Bang Nay,Vui Long Lien He Voi Admin De Sua Loi Nay");
					return false;
				}
			}catch(Exception er){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
				
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		
		}catch(Exception er){
			this.msg="Khong The Cap Nhat Hang Cua Hang Nay ";
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		return true;
	}

	public boolean UpdateHch() 
	{
		Object[] data;
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		String command ="update hangcuahang set hang = ?, diengiai = ?, trangthai=?, ngaysua = ?, nguoisua = ?, tumuc = ?, denmuc = ?,thangtb=? where pk_seq = ?";
		//System.out.println("Update : "+command);
		data = new Object[] {this.hangcuahang, this.diengiai, this.trangthai, this.ngaysua, this.userId, this.tumuc.replaceAll(",", ""), this.denmuc.replaceAll(",", ""), this.thangtb, this.id};
		if(this.db.updateQueryByPreparedStatement(command, data) != 1){
			//this.msg = "update hangcuahang set hang = '" + this.hangcuahang + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
			this.msg="Ban khong the luu duoc, can kiem tra lai du lieu";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}

		this.db.update("commit");
		return true; 
	}

	private void init()
	{	
		String query = "select a.pk_seq as id, a.hang, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua,tumuc,denmuc,thangtb"; 
		query = query + " from hangcuahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";
		query = query + " and a.pk_seq='"+ this.id + "'";
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.hangcuahang = rs.getString("hang");
        	this.diengiai = rs.getString("diengiai");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	this.tumuc = rs.getString("tumuc");
        	this.denmuc = rs.getString("denmuc");
        	this.thangtb=rs.getString("thangtb")==null?"":rs.getString("thangtb");
        	  	
       	}catch(Exception e){}
	}
	
	/////////////////////////////
	
	public static void main(String[] arg)
    {
		Hangcuahang mfar = new Hangcuahang("");
          
          System.out.println("__Ngay hien tai: " + mfar.getDateTime());
          System.out.println("__Sau do 1 thang: " + mfar.getMonth(mfar.getDateTime(), -3));
          System.out.println("__So ngay la: " + mfar.getSongay(mfar.getDateTime(), mfar.getMonth(mfar.getDateTime(), 3)));
          System.out.println("__So ngay la: " + mfar.getSongay("2013-01-06", "2013-01-31"));
          
          //String ngayCuoiThang = LastDayOfMonth(Integer.parseInt("01"), Integer.parseInt("2014"));
          String ngayCuoiThang = LastDayOfMonth(Integer.parseInt(mfar.getMonth(mfar.getDateTime(), -1).substring(5,7)), Integer.parseInt(mfar.getMonth(mfar.getDateTime(), -1).substring(1,4)));
          
          System.out.println("__Sau do 3 thang: " + 
          mfar.getMonth(mfar.getMonth(mfar.getDateTime(), -1).substring(1,7)+"-"+ngayCuoiThang, -3));
  			System.out.println("___Ngay cuoi thang: " + ngayCuoiThang);
    }
	
	private static String LastDayOfMonth(int month, int year) 
    {
        String ngay = "";
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = "31";
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = "30";
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = "29";
                    else
                        ngay = "28";
                    break;
                }
        }

        return ngay;
    } 
    
    public String getDate(String date, int songay)
    {
          String[] arr = date.split("-");
          if(arr[2].equals("01"))
                return date;
          
          Calendar lich = Calendar.getInstance();
          lich.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[2]));
          lich.add(Calendar.DATE, songay);
          
          String thang = Integer.toString(lich.get(Calendar.MONTH) + 1);
          if((lich.get(Calendar.MONTH) + 1) < 10)
                thang = "0" + thang;
          
          String ngay = Integer.toString(lich.get(Calendar.DATE));
          if(lich.get(Calendar.DATE) < 10)
                ngay = "0" + ngay;
          
          String kq = Integer.toString(lich.get(Calendar.YEAR)) + "-" + thang + "-" + ngay;
          return kq;
    }
 
    public String getMonth(String date, int sothang)
    {
          String[] arr = date.split("-");
          
          Calendar lich = Calendar.getInstance();
          lich.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[2]));
          lich.add(Calendar.MONTH, sothang);
          
          String thang = Integer.toString(lich.get(Calendar.MONTH) + 1);
          if((lich.get(Calendar.MONTH) + 1) < 10)
                thang = "0" + thang;
          
          String ngay = Integer.toString(lich.get(Calendar.DATE));
          if(lich.get(Calendar.DATE) < 10)
                ngay = "0" + ngay;
          
          
          String kq = Integer.toString(lich.get(Calendar.YEAR)) + "-" + thang + "-" + ngay;
          return kq;
    }
    
    public long getSongay(String tungay, String denngay)
    {
      Calendar c1 = Calendar.getInstance();
      Calendar c2 = Calendar.getInstance();

      String[] tn = tungay.split("-");      
      String[] dn = denngay.split("-");      
      
      c1.set(Integer.parseInt(tn[0]), Integer.parseInt(tn[1]) - 1, Integer.parseInt(tn[2]));
      c2.set(Integer.parseInt(dn[0]), Integer.parseInt(dn[1]) - 1, Integer.parseInt(dn[2]));         

      long soNgay = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);
          
          return soNgay;
    }
	
	////////////////////////////
	
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	
		
	public void closeDB(){
		if(this.db != null)
			this.db.shutDown();
	}

	
	public void settumuc(String tumuc) {
		if(tumuc.equals("")) {
			tumuc = "0";
		}
		this.tumuc = tumuc;
	}

	
	public String gettumuc() {
		
		return this.tumuc;
	}

	
	public void setdenmuc(String denmuc) {
		if(denmuc.equals("")) {
			denmuc = "0";
		}
		this.denmuc = denmuc;
	}

	
	public String getdenmuc() {
		
		return this.denmuc;
	}
	
	/*public boolean phanboHCH() {
		String str = "";
		try{
			IHangcuahangList obj =  (IHangcuahangList) new HangcuahangList();
			Hangcuahang mfar = new Hangcuahang("");
    		db = new dbutils();
    		
    		String sql = "select khachhang_fk, min(ngaynhap) as ngaynhap "+
    				     "from DONHANG "+ 
    				     "where TRANGTHAI = '1' "+ 
    				     "group by KHACHHANG_FK"; 
    		System.out.println("1.LAY NGAY NHAP :"+sql);
    		ResultSet rs = db.get(sql);
    		 
    		while(rs.next())
    		{	long ngay = mfar.getSongay(mfar.getDateTime(), mfar.getMonth(mfar.getDateTime(), 3)); // 3 thang truoc do
    			long ngay2 = mfar.getSongay(rs.getString("ngaynhap"), mfar.getDateTime()); // ngay nhap don hang
    		System.out.println("SO NGAY 1 :"+ngay+" va SO NGAY 2 "+ngay2);    		
    		
    			sql ="select * from "+
    					"( "+
						"select khachhang_fk,";
    			if(ngay2 <= ngay)
    	    		
				{
    				sql += "SUM(TONGGIATRI)/"+ngay2+"";
				}
    			else
    			{
    				sql += "SUM(TONGGIATRI)/"+ngay+"";
    			}
    				sql +=" as doanhso, 1 as 'type' "+
						"from DONHANG "+
						"where TRANGTHAI = '1' "+
						"group by KHACHHANG_FK "+
						") "+
						"khachhang left join "+
						"( "+
						"select PK_SEQ as hchId, tumuc, denmuc , 1 as 'type' from HANGCUAHANG where TRANGTHAI = '1' "+
						") "+
						"hang on khachhang.type = hang.type "+
						"where khachhang.doanhso > hang.tumuc and khachhang.doanhso <= hang.denmuc";
    		}    			    		
    		System.out.println("2.TINH DOANH SO : "+sql);
    		ResultSet rs1 = db.get(sql);
    		
    		while(rs1.next())
    		{
    			String query = "update khachhang set hch_fk = '"+ rs1.getString("hchId") +"' where pk_seq = '"+ rs1.getString("khachhang_fk") +"'";
    			
    			System.out.println("3.UPDATE HANG CUA HANG: "+query);
    			
    			if(db.update(query))
    			{	System.out.println("Toi day");
    				int n = rs.getRow();
    				this.str = "Cập nhật dữ liệu thành công. Có "+n+" khách hàng được cập nhật!";
    			}
    			
    			else	    				
    			{	this.msg = "Cập nhật dữ liệu không thành công : "+query;
    				//System.out.println("Loi :"+query);
    				geso.dms.center.util.Utility.rollback_throw_exception(db);
    			}
    		}	
    	}catch(Exception ex){this.setMessage("Loi "+ ex.toString());}
		return str;	 			
	}*/
		
}
