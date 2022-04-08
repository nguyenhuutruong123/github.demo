package geso.dms.distributor.beans.denghitratb.imp;

import geso.dms.distributor.beans.denghitratb.IDenghitratb;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class Denghitratb implements IDenghitratb, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	
	String id;	
	String tgBatdau;
	String tgKetthuc;
	String solantt;
	int type;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String diengiai;
	String tgTinhds; //thoi gian bat dau tinh donah so
	String tgKtTinhds;
	String scheme;
	ResultSet cttrunggbay;
	String cttbId;
	String cttbTen;
	ResultSet nvbanhang;
	String[] nvbhIds;
	
	ResultSet khlist;
	ResultSet khSelectedlist;
		
	String nppId;
	String nppTen;
	String sitecode,LanThanhToan;
		
	dbutils db;

	private String sosuatphanbo;

	private String sosuatdaphanbo;
	
	public Denghitratb(String[] param)
	{
		this.id = param[0];
		this.cttbId = param[1];
		this.ngaytao = param[2];
		this.nguoitao = param[3];
		this.ngaysua = param[4];
		this.nguoisua = param[5];
		this.trangthai = param[6];
		this.diengiai =param[7];
		this.scheme=param[8];
		this.solantt =param[9];
		this.tgBatdau = "";
		this.tgKetthuc = "";
		this.msg = "";
		this.tgTinhds = "";
		this.tgKtTinhds = "";
		this.sosuatphanbo = "";
		this.sosuatdaphanbo = "";
		this.cttbTen = "";
		this.nppId = "";
		this.nppTen = "";
		this.sitecode = "";
		db = new dbutils();
	}
	
	public Denghitratb(String id)
	{
		this.id = id;
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.cttbId = "";
		this.tgBatdau = "";
		this.tgKetthuc = "";
		this.tgTinhds = "";
		this.tgKtTinhds = "";
		this.sosuatphanbo = "";
		this.sosuatdaphanbo = "";
		this.solantt = "";
		this.diengiai ="";
		this.scheme ="";
		this.msg = "";
		db = new dbutils();
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
	
	public String getNgaytao()
	{
		return this.ngaytao;		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;	
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
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
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public ResultSet getNvBanhang() 
	{		
		return this.nvbanhang;
	}
	
	public void setNvBanhang(ResultSet nvbanhang) 
	{
		this.nvbanhang = nvbanhang;		
	}
	
	public ResultSet getCttrungbay() 
	{	
		return this.cttrunggbay;
	}
	
	public void setCttrungbay(ResultSet cttrungbay) 
	{
		this.cttrunggbay = cttrungbay;		
	}
	
	public String getCttbId() 
	{		
		return this.cttbId;
	}
	
	public void setCttbId(String cttbId) 
	{
		this.cttbId = cttbId;		
		this.solanthanhtoan("new");
	}
	
	public String getCttbTen() 
	{		
		return this.cttbTen;
	}
	
	public void setCttbTen(String cttbten) 
	{
		this.cttbTen = cttbten;		
	}
	
	public String getThoigianbd() 
	{		
		return this.tgBatdau;
	}
	
	public void setThoigianbd(String tgBatdau) 
	{
		this.tgBatdau = tgBatdau;		
	}
	
	public String getThoigiankt() 
	{		
		return this.tgKetthuc;
	}
	
	public void setThoigiankt(String tgKetthuc) 
	{
		this.tgKetthuc = tgKetthuc;		
	}
	
	public String getSolantt() 
	{		
		return this.solantt;
	}
	
	public void setSolantt(String solantt)
	{
		this.solantt = solantt;		
	}

	public ResultSet getKhList() 
	{
		return this.khlist;
	}

	public void setKhList(ResultSet khlist)
	{
		this.khlist = khlist;
	}
	
	public int getType()
	{
		return this.type;
	}

	public void setType(int type) 
	{
		this.type = type;
	}
	
	public boolean CreateDnttb(String[] khIds, String[] ddkdIds, String[] dangky, String[] denghi) 
	{
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			String query = "insert into denghitratrungbay(cttrungbay_fk, ngaydenghi, ngaysua, nguoitao, nguoisua, npp_fk, trangthai, lanthanhtoan) ";
			query = query  + "values('" + this.cttbId + "', '" + this.ngaytao + "', '" + this.ngaytao + "', '"  + this.nguoitao + "', '" + this.nguoitao + "', '" + this.nppId + "', '0', '" + this.solantt + "')";

			if(!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Khong the tao moi 'DENGHITRATRUNGBAY' " + query;
				return false;
			}
			
			query = "select IDENT_CURRENT('denghitratrungbay') as dnttbId";
			
			ResultSet rsDnttb = this.db.get(query);						
			rsDnttb.next();
			String DnttbId = rsDnttb.getString("dnttbId");
			rsDnttb.close();
			
			if(khIds.length > 0)
			{
				for(int i = 0; i < khIds.length; i++)
				{
					if(denghi[i].length() <= 0)
						denghi[i] = "0";
					//Khoa bo luu dai dien kinh doanh vao bang denghitratb_khachhang --dong thuan
					query = "insert into DENGHITRATB_KHACHHANG(denghitratb_fk, khachhang_fk, xuatdangky, xuatdenghi) ";
					query = query + "values('" + DnttbId + "', '" + khIds[i] + "', '" + dangky[i] + "', '" + denghi[i] + "')";
					if(!db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "Khong the tao moi 'DENGHITRATB_KHACHHANG' " + query;
						return false;
					}
				}
			}			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
		 geso.dms.center.util.Utility.rollback_throw_exception(db);
		 return false;
		}
		return true;
	}
	
	public boolean UpdateDnttb(String[] khIds, String[] ddkdIds, String[] dangky, String[] denghi) 
	{	
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		try 
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update denghitratrungbay set nguoisua='" + this.nguoisua + "', ngaysua='" + this.ngaysua + "' where pk_seq= "+ this.id;
			if(!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Khong the cap nhat 'denghitratrungbay' " + query;
				return false;
			}
			if(khIds.length > 0)
			{
				query = "delete from DENGHITRATB_KHACHHANG where denghitratb_fk='" + this.id + "'";
				if(!db.update(query))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Loi khi cap nhat 'DENGHITRATB_KHACHHANG' " + query;
					return false;
				}
				for(int i = 0; i < khIds.length; i++)
				{
					if(denghi[i].length() <= 0)
						denghi[i] = "0";
					query = "insert into DENGHITRATB_KHACHHANG(denghitratb_fk, khachhang_fk, xuatdangky, xuatdenghi) ";
					query = query + "values('" + this.id + "', '" + khIds[i] + "', '" + dangky[i] + "', '" + denghi[i] + "')";
					if(!db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "Khong the tao moi 'DENGHITRATB_KHACHHANG' " + query;
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Khong the tao moi,xay ra loi : " + e.toString();
			return false;
		}
		return true;
	}
	
	public void createRS() 
	{
		this.getNppInfo();
		this.initThoigian();
		this.createCttbTaomoiRs();	
		this.createNvbhRs();			
	}
	/*	Lay nhung cttb mà npp chưa làm đề nghị thanh toán và cttb dc 
	 * phân bổ cho npp và npp đã đăng ký tham gia cttb này(npp phải chốt đăng ký)
	 */
	private void createCttbTaomoiRs()
	{
		String query = 
				"select pk_seq as cttbId, scheme, diengiai as cttbTen \n " +
				"from cttrungbay where pk_seq not in \n" +
				" (select distinct a.cttrungbay_fk from denghitratrungbay a, cttrungbay b where a.npp_fk= '"+this.nppId+"' and a.lanthanhtoan = b.solanthanhtoan and b.pk_seq=a.cttrungbay_fk) \n" +
				" and pk_seq in (select CTTB_FK from phanbotrungbay where  npp_fk ='"+this.nppId+"') " +
				"and PK_SEQ in(select CTTRUNGBAY_FK from DANGKYTRUNGBAY where NPP_FK='"+this.nppId+"' and TRANGTHAI=1)";
		System.out.println("createCttbTaomoiRs:.."+query);
		this.cttrunggbay = db.get(query);
	}
	
	private void createCttbUpdateRs()
	{
		String sql = "select pk_seq as cttbId, scheme, diengiai as cttbTen from cttrungbay ";
		sql = sql + "where pk_seq = '" + this.cttbId + "'";
		System.out.println("createCttbUpdateRs:.."+sql);
		this.cttrunggbay = db.get(sql);
	}
	
	private void createNvbhRs()
	{
		String sql = "select pk_seq as nvbhId, ten as nvbhTen, diachi, dienthoai from daidienkinhdoanh where npp_fk = '" + this.nppId + "' and trangthai = '1'";
		this.nvbanhang = db.get(sql);
	}
	
	
	private void initThoigian()
	{
		//lay ngay bat dau - ket thuc tinh donah so
		String query = 
				"select ngaytds as nbd, ngaykttds as nkt, ngaytrungbay, ngayketthuctb, solanthanhtoan, type,scheme \n" +
				"from cttrungbay where pk_seq='" + this.cttbId + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				rs.next();
				this.tgTinhds = rs.getString("nbd") == null? "":rs.getString("nbd");
				this.tgKtTinhds = rs.getString("nkt") == null? "":rs.getString("nkt");
				this.tgBatdau = rs.getString("ngaytrungbay") == null? "":rs.getString("ngaytrungbay");
				this.tgKetthuc = rs.getString("ngayketthuctb") == null ? "":rs.getString("ngayketthuctb");
				this.solantt = rs.getString("solanthanhtoan") == null? "":rs.getString("solanthanhtoan");
				this.type = rs.getInt("type");
				this.scheme = rs.getString("scheme");
				rs.close();
				//lay ra so suat phan bo
				String sql ="select ngansach-dasudung  as soxuatconlai from phanbotrungbay where cttb_fk ='"+ this.cttbId +"' and npp_fk ='"+ this.nppId +"'";
				System.out.println(sql);
				ResultSet tb = db.get(sql);
				tb.next();
				this.sosuatphanbo = tb.getString("soxuatconlai") == null? "":tb.getString("soxuatconlai");
				//lay ra tong so xuat da phan bo
				sql ="select sum(a.soxuat) as num from DKTRUNGBAY_KHACHHANG a,dangkytrungbay b,phanbotrungbay c where a.dktrungbay_fk = b.pk_seq and b.CTTRUNGBAY_FK = c.cttb_fk and c.npp_fk= '"+ this.nppId +"' and c.cttb_fk ='"+ this.cttbId +"'";
				System.out.println(sql);
				ResultSet ts = db.get(sql);
				
				if(ts!= null)
				{
				    ts.next();
				    System.out.println("so xuat da phan bo:"+ts.getString("num"));
					this.sosuatdaphanbo = ts.getString("num") == null ? "":ts.getString("num");
					
					ts.close();	
					
				}
				else
					this.sosuatdaphanbo ="0";
				
			} 
			
			catch(Exception e) {}
			finally{try {
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			try 
			{  int h = Integer.parseInt(this.sosuatdaphanbo);
				
			}
			catch(Exception e)
			{
				this.sosuatdaphanbo ="0";
				
			}
		}
	}

	private void solanthanhtoan(String action){
		if(this.cttbId.length()>0){
			String query  = "select max(lanthanhtoan) as num from denghitratrungbay where cttrungbay_fk='"+ this.cttbId + "' and npp_fk='" + this.nppId + "'"; 
			ResultSet rs = db.get(query);
			int lanthanhtoan = 0;
			try{
				if(rs != null){
					rs.next();
					lanthanhtoan = rs.getInt("num");
				}
			}catch(Exception e){}
			finally{try {
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	
			if(action.equals("new")){
				this.solantt = "" + (lanthanhtoan + 1);
			}else{
				this.solantt = "" + lanthanhtoan;
			}
		}
	}
  	//Lay ra nhung khach hang co dang ky trung bay 
	//thuoc nha phan phoi va nam trong tuyen ban hang cua dai dien kinh doanh
	//va loai ra nhung khach hang da lam de nghi tra trung bay
	public void createKhRs()
	{	
		String str = "";
		if(this.nvbhIds != null)
		{
			for(int i = 0; i < this.nvbhIds.length; i++)
				str = str + "'" + this.nvbhIds[i] + "',";
			str = str.substring(0, str.length() - 1); //cat dau phay cuoi cung
		}
		if(str.length() == 0)
			str = "''"; 
		
		String query = "select a.khachhang_fk as khId, b.ten as khTen, b.diachi, b.dienthoai, a.ddkd_fk as ddkdId, a.soxuat, a.dangky \n" +
				"from dktrungbay_khachhang a inner join khachhang b on a.khachhang_fk = b.pk_seq inner join dangkytrungbay c on a.dktrungbay_fk = c.pk_seq  \n" +
				"where a.dangky > 0 and c.npp_fk = '" + this.nppId + "' and c.cttrungbay_fk='" + this.cttbId + "' and a.khachhang_fk in \n"+ 
				"(select khachhang_fk from khachhang_tuyenbh kh_tbh inner join tuyenbanhang tbh on tbh.pk_Seq=kh_tbh.tbh_fk  where 1=1 " ;
		if(str.trim().length()>0)
			query+=" and tbh.ddkd_fk in (" + str + ")) \n" ;
		query+=" and a.khachhang_fk not in(select khachhang_fk from denghitratb_khachhang where DENGHITRATB_FK in \n"+
				"( \n"+
				"select pk_Seq from DENGHITRATRUNGBAY where CTTRUNGBAY_FK='"+this.cttbId+"' and LANTHANHTOAN=1 \n"+
				")) \n";

		if(this.id.length() > 0)
			query = query + " and a.khachhang_fk not in (select khachhang_fk from denghitratb_khachhang where denghitratb_fk = '" + this.id + "') \n";
		this.khlist = db.get(query);		
		System.out.println("Khach Hang"+query);
	}
	
	public void createNvbhIds()
	{
		String query = "select ddkd_fk from denghitratb_khachhang where denghitratb_fk='" + this.id + "'";
		String str = "";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				int m = 0;
				while(rs.next())
				{
					str = str + rs.getString("ddkd_fk") + ",";
					m++;
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
		if(str.length() > 0)
		{
			str = str.substring(0, str.length() - 1);
			this.nvbhIds = str.split(",");
		}		
	}
	
	private void createKhSelectedList()
	{
		String query ="select a.khachhang_fk as khId,b.smartId, b.ten as khTen, b.diachi, b.dienthoai, a.ddkd_fk as ddkdId, "+
			" tb_kh.DANGKY as dangky, isnull(tb_kh.Dat, 0) as dat, a.XUATDENGHI   as denghi, a.xuatduyet as duyet "+ 
			" from DENGHITRATRUNGBAY dnttb inner join  denghitratb_khachhang a on a.DENGHITRATB_FK=dnttb.PK_SEQ "+ 
		" inner join khachhang b on a.khachhang_fk = b.pk_seq   "+ 
		 " inner join DANGKYTRUNGBAY dktb on dktb.CTTRUNGBAY_FK=dnttb.CTTRUNGBAY_FK and dktb.NPP_FK=dnttb.NPP_FK "+
		 " inner join DKTRUNGBAY_KHACHHANG tb_kh on tb_kh.DKTRUNGBAY_FK=dktb.PK_SEQ and tb_kh.KHACHHANG_FK=a.KHACHHANG_FK "+
		 " where a.denghitratb_fk = '"+this.id+"' and dktb.trangthai=1";
		System.out.println("_____Khach hang lam de nghi tra trung bay____"+query);
		this.khSelectedlist = db.get(query);
	}
	
	public void init() 
	{
		this.getNppInfo();
		
		String query =" SELECT TRA_TB.*,TB.SCHEME,TB.DIENGIAI"+ 
			" FROM " +
			"(SELECT PK_SEQ AS DKTBID,CTTRUNGBAY_FK AS CTTBID,NGAYDENGHI,NGAYSUA,TRANGTHAI FROM DENGHITRATRUNGBAY  WHERE PK_SEQ= '"+this.id+"')TRA_TB "+
			" INNER JOIN CTTRUNGBAY TB ON TRA_TB.CTTBID=TB.PK_SEQ"+  
			" ORDER BY TRA_TB.TRANGTHAI,  TRA_TB.NGAYDENGHI DESC";
		ResultSet rs =  db.get(query);
    System.out.println("__Init De nghi tra trung bay__"+query);
		try
        {
            rs.next();        	
            this.id = rs.getString("dktbId");
            this.cttbId = rs.getString("cttbId");
			      this.trangthai = rs.getString("trangthai");
      	    this.diengiai=rs.getString("diengiai");
			rs.close();
       	}
        catch(Exception e){}
        
        this.createCttbUpdateRs();
        this.createNvbhRs();		
		this.initThoigian();  
		this.solanthanhtoan("update");
		this.createKhSelectedList();
		this.createNvbhIds();
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getTgbdTinhds() 
	{
		return this.tgTinhds;
	}

	public void setTgbdTinhds(String tgbdTinhds)
	{
		this.tgTinhds = tgbdTinhds;
	}

	public String getTgktTinhds() 
	{
		return this.tgKtTinhds;
	}

	public void setTgktTinhds(String tgktTinhds) 
	{
		this.tgKtTinhds = tgktTinhds;
	}
	
	public Hashtable<Integer, String> getNvbhIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.nvbhIds != null){
			int size = (this.nvbhIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.nvbhIds[m]);
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setNvbhIds(String[] nvbhIds) 
	{
		this.nvbhIds = nvbhIds;
	}

	public ResultSet getKhSelectedList() 
	{
		return this.khSelectedlist;
	}

	public void setKhSelectedList(ResultSet khSelectedlist) 
	{
		this.khSelectedlist = khSelectedlist;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{
		try{
			
		this.db.shutDown();
		if( cttrunggbay!=null){
			cttrunggbay.close();
		}
		if( nvbanhang!=null){
			nvbanhang.close();
		}
		if( khlist!=null){
			khlist.close();
		}
		if( khSelectedlist!=null){
			khSelectedlist.close();
		}
		nvbhIds=null;
		}catch(Exception er){
			
		}
	}

	
	public String getdiengiai() {
		
		return this.diengiai;
	}

	
	public void setdiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}

	
	public String getcheme() {
		
		return this.scheme;
	}

	
	public void setscheme(String scheme) {
		
		this.scheme =scheme;
	}
	
	public void setSosuatphanbo(String sosuatphanbo) {
		this.sosuatphanbo = sosuatphanbo;
	}

	public String getSosuatphanbo() {
		return this.sosuatphanbo;
	}

	public void setSosuatdaphanbo(String sosuatdaphanbo) {
	
		this.sosuatdaphanbo = sosuatdaphanbo;
	}
	public String getSosuatdaphanbo() {
		
		return this.sosuatdaphanbo;
	}



}