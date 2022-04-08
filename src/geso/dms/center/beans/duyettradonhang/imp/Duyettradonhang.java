package geso.dms.center.beans.duyettradonhang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.dms.center.beans.duyettradonhang.IDuyettradonhang;
import geso.dms.distributor.beans.donhangtrave.ISanpham;
import geso.dms.distributor.beans.donhangtrave.imp.Sanpham;
import geso.dms.distributor.db.sql.dbutils;

public class Duyettradonhang implements IDuyettradonhang
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id; //ma don hang
	String ngaygiaodich;
	String daidienkd;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String chietkhau;
	String tongchietkhau;
	String VAT;
	String msg;
	
	ResultSet nppRs;
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet ddkdlist;
	String ddkdId;
	
	ResultSet gsbhList;
	String gsbhId;
	String smartId;
	String khTen;
	ResultSet khlist;
	String khId;
	
	ResultSet kholist;
	String khoId;
	
	List<ISanpham> splist;
	List<ISanpham> spkmlist;
	
	String tongtientruocVAT;
	String tongtiensauVAT;
	
	String dhId;
	boolean isCheckDhtv;
	
	dbutils db;
	
	public Duyettradonhang(String[] param)
	{
		this.id = param[0];
		this.khId = param[1];
		this.ngaygiaodich = param[2];
		this.nppTen = param[3];
		this.daidienkd = param[4];
		this.trangthai = param[5];
		this.ngaytao = param[6];
		this.nguoitao = param[7];
		this.ngaysua = param[8];
		this.nguoisua = param[9];	
		this.VAT = param[10];
		this.ddkdId = param[11];
		
		this.gsbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.khoId = "";
		this.msg = "";
		
		this.dhId = "";
		this.isCheckDhtv = false;
		
		db = new dbutils();
		
	}
	
	public Duyettradonhang(String id)
	{
		this.id = id;
		this.khId = "";
		this.ngaygiaodich = "";
		this.nppTen = "";
		this.daidienkd = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";	
		this.VAT = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.tongtiensauVAT = "0";
		this.tongtientruocVAT ="0";
		this.khoId = "";
		this.msg = "";
		this.khTen = "";
		this.smartId = "";
		this.dhId = "";
		this.isCheckDhtv = false;
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
	
	public String getSmartId() 
	{		
		return this.smartId;
	}

	public void setSmartId(String smartId) 
	{
		this.smartId = smartId;		
	}

	public String getKhTen() 
	{		
		return this.khTen;
	}

	public void setKhTen(String khTen) 
	{
		this.khTen = khTen;		
	}

	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;		
	}
	
	public String getKhId() 
	{	
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
	}
	
	public String getNgaygiaodich() 
	{	
		return this.ngaygiaodich;
	}

	public void setNgaygiaodich(String ngaygiaodich) 
	{
		this.ngaygiaodich = ngaygiaodich;		
	}
	
	public String getDaidienkd() 
	{	
		return this.daidienkd;
	}
	
	public void setDaidienkd(String daidienkd) 
	{
		this.daidienkd = daidienkd;		
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
	
	public String getChietkhau() 
	{
		if(this.chietkhau.length() <= 0)
			this.chietkhau = "0";
		return this.chietkhau;
	}
	
	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;		
	}
	
	public String getVAT() 
	{
		if(this.VAT == "")
			this.VAT = "10";
		return this.VAT;
	}
	
	public void setVAT(String vat) 
	{
		this.VAT = vat;	
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
	
	public ResultSet getDdkdList() 
	{	
		return this.ddkdlist;
	}
	
	public void setDdkdList(ResultSet ddkdList)
	{
		this.ddkdlist = ddkdList;		
	}
	
	public String getDdkdId() 
	{		
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;	
	}
	
	public List<ISanpham> getSpList()
	{	
		return this.splist;
	}
	
	public void setSpList(List<ISanpham> splist) 
	{
		this.splist = splist;
	}
		
	public String getTongtientruocVAT() 
	{		
		return this.tongtientruocVAT;
	}
	
	public void setTongtientruocVAT(String tttvat) 
	{
		this.tongtientruocVAT = tttvat;		
	}

	public String getTongtiensauVAT()
	{		
		return this.tongtiensauVAT;
	}
	
	public void setTongtiensauVAT(String ttsvat) 
	{
		this.tongtiensauVAT = ttsvat;		
	}
	
	public String getGsbhId() 
	{
		return this.gsbhId;
	}

	public void setGsbhId(String gsbhId) 
	{
		this.gsbhId = gsbhId;
	}

	public ResultSet getKhList() 
	{
		return this.khlist;
	}

	public void setKhList(ResultSet khlist) 
	{
		this.khlist = khlist;
	}
	
	public void createRS() 
	{
		//this.createDdkd();	
		//this.createKho();		
		String query = "select a.pk_seq as nppId, a.ten as nppTen, 'Khu vuc ' + b.ten as kvTen ";
		query += "from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk = b.pk_seq where a.trangthai = '1' and a.sitecode = a.convsitecode order by b.pk_seq asc";
		this.nppRs = db.get(query);
		//this.nppRs = db.get("select pk_seq as nppId, ten as nppTen from nhaphanphoi where trangthai = '1' and sitecode = convsitecode");
		
		if(this.dhId.length() > 0)
			this.CreateSpList();
	}
	
	
	public void init() 
	{
		String query = "select a.pk_seq as dhtvId, a.ngaynhap, a.donhang_fk as dhId, b.pk_seq as nppId, b.ten as nppTen, b.sitecode from donhangtrave a inner join nhaphanphoi b on a.npp_fk = b.pk_seq ";
		query = query + "where a.pk_seq = '" + this.id + "'";
	
        ResultSet rs =  db.get(query);
        try
        {
            rs.next();
            this.id = rs.getString("dhtvId");
			this.ngaygiaodich = rs.getString("ngaynhap");
			this.dhId = rs.getString("dhId");
			this.nppId = rs.getString("nppId");
			this.nppTen = rs.getString("nppten");
			this.sitecode = rs.getString("sitecode");
			
			rs.close();
       	}
        catch(Exception e){}
 
        this.CreateSpList();
	}
		
	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;
	}

	public ResultSet getKhoList() 
	{
		return this.kholist;
	}

	public void setKhoList(ResultSet kholist) 
	{
		this.kholist = kholist;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose()
	{
		try 
		{
			if(!(this.ddkdlist == null))
				this.ddkdlist.close();
		} 
		catch(Exception e) {}
	}

	public ResultSet getGsbhList()
	{	
		return this.gsbhList;
	}
	
	public void setGsbhList(ResultSet gsbhList) 
	{
		this.gsbhList = gsbhList;
	}
	
	public String getTongChietKhau() 
	{
		return this.tongchietkhau;
	}

	public void setTongChietKhau(String tck) 
	{
		this.tongchietkhau = tck;
	}
	
	public void CreateSpList()
	{		
		String command = "select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua as dongia ";
		command = command + "from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq left join donvidoluong c on b.dvdl_fk = c.pk_seq ";
		command = command + " where a.donhang_fk = '" + this.dhId + "'";

		ResultSet splistRs = db.get(command);
		float tonggiatri = 0f; 
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(splistRs != null)
		{
			String[] param = new String[8];
			ISanpham sp = null;	
			try 
			{
				while(splistRs.next())
				{
					param[0] = splistRs.getString("spId");
					param[1] = splistRs.getString("spma");
					param[2] = splistRs.getString("spten");
					param[3] = splistRs.getString("soluong");
					param[4] = splistRs.getString("donvi");
					param[5] = splistRs.getString("dongia");
					param[6] = "";
					param[7] = "";
					
					tonggiatri += Float.parseFloat(param[5]) * Float.parseFloat(param[3]);
					
					sp = new Sanpham(param);
					splist.add(sp);
				}
				splistRs.close();
			} 
			catch(Exception e) {}
		}
		
		//spkm
		String query = "select  d.pk_seq as spId, a.spMa, d.ten as spten, dv.donvi, a.soluong, a.tonggiatri, b.scheme, (a.tonggiatri / a.soluong) as dongia ";
		query += "from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq left join sanpham d on a.spMa = d.ma left join donvidoluong dv on dv.pk_seq = d.dvdl_fk ";
		query += "where a.donhangId = '" + this.dhId + "' and a.spMa is not null";
		
		ResultSet spkmlistRs = db.get(query);
		if(spkmlistRs != null)
		{
			String[] param = new String[8];
			ISanpham sp = null;	
			try 
			{
				while(spkmlistRs.next())
				{
					param[0] = spkmlistRs.getString("spId");
					param[1] = spkmlistRs.getString("spma");
					param[2] = spkmlistRs.getString("spten");
					param[3] = spkmlistRs.getString("soluong");
					param[4] = spkmlistRs.getString("donvi");
					param[5] = spkmlistRs.getString("dongia");
					param[6] = spkmlistRs.getString("scheme");
					param[7] = "";
		
					sp = new Sanpham(param);
					splist.add(sp);
				}
				spkmlistRs.close();
			} 
			catch(Exception e) {}
		}
		
		this.splist = splist;
	}
	
	public boolean duyetSptrave(String id, String userId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			String ngayksthem1="";
			String query="select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay where npp_fk="+this.nppId;
			System.out.println(query);
			ResultSet rs1=this.db.get(query);
				if(rs1!=null){
					if(rs1.next()){
						ngayksthem1=rs1.getString("ngay");
					 	System.out.println("Ngay Khoa So Them " +ngayksthem1);
				    }else{
				    	db.getConnection().rollback();
						this.msg = " I.Error: " + query;
						return false;
				    }
				}else{
					db.getConnection().rollback();
					this.msg = "II.Error: " + query;
					return false;
				}
			if(rs1!=null){
				rs1.close();
			}
			
			
			 query = "update donhangtrave set ngaynhap='"+ngayksthem1+"',trangthai = '3', nguoiduyet = '" + userId + "', ngayduyet = '" + this.getDateTime() + "' where pk_seq = '" + id + "' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Error: " + query;
				return false;
			}
			
			//query = "select b.sanpham_fk, b.soluong, a.npp_fk, a.kho_fk, a.kbh_fk from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk where a.pk_seq = (select donhang_fk from donhangtrave where pk_seq = '" + id + "')";
			query = "select b.sanpham_fk, b.soluong, a.npp_fk, a.kho_fk, a.kbh_fk from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk where a.pk_seq = '" + this.dhId + "'";
			ResultSet rs = db.get(query);
			
			if(rs != null)
			{
				while(rs.next())
				{
					String sanpham_fk = rs.getString("sanpham_fk");
					String soluong = rs.getString("soluong");
					String kho_fk = rs.getString("kho_fk");
					String kbh_fk = rs.getString("kbh_fk");
					String npp_fk = rs.getString("npp_fk");
					
					String cmd = "update nhapp_kho set soluong = soluong + '" + soluong + "', available = available + '" + soluong + "' where npp_fk = '" + npp_fk + "' and kho_fk = '" + kho_fk + "' and kbh_fk = '" + kbh_fk + "' and sanpham_fk = '" + sanpham_fk + "'";
				
					if(!db.update(cmd))
					{
						db.getConnection().rollback();
						this.msg = "Error: " + cmd;
						System.out.print(this.msg);
						return false;
					}
				}
				rs.close();
			}
			
			//sanpham khuyen mai
			query = "select b.pk_seq as spId, a.soluong, a.ctkmId from donhang_ctkm_trakm a inner join sanpham b on a.spma = b.ma where a.spma is not null and a.donhangId = '" + this.dhId + "'";
			ResultSet rsKm = db.get(query);
			
			if(rsKm != null)
			{
				while(rsKm.next())
				{
					String sanpham_fk = rsKm.getString("spId");
					String soluong = rsKm.getString("soluong");
					String ctkm = rsKm.getString("ctkmId");
					
					String cmd = "update nhapp_kho set soluong = soluong + '" + soluong + "', available = available + '" + soluong + "' where npp_fk = '" + this.nppId + "' and kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkm + "') and kbh_fk = (select kbh_fk from donhang where pk_seq = '" + this.dhId + "') and sanpham_fk = '" + sanpham_fk + "'";
					
					if(!db.update(cmd))
					{
						db.getConnection().rollback();
						this.msg = "Error: " + cmd;
						System.out.print(this.msg);
						return false;
					}
				}
				rsKm.close();
			}
			
			//Cap nhat phan bo
			query = "select distinct ctkmId, trakmId, tonggiatri from donhang_ctkm_trakm where donhangid='" + this.dhId + "'";
			ResultSet rsUpdateKm = db.get(query);
			if(rsUpdateKm != null)
			{
				while(rsUpdateKm.next())
				{
					String ctkmId = rsUpdateKm.getString("ctkmId");
					String tonggiatri = rsUpdateKm.getString("tonggiatri");
					String st ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - '" + tonggiatri + "' where ctkm_fk='" + ctkmId + "' and npp_fk='" + nppId + "'";
				    db.update("update CTKhuyenmai set DASUDUNG = DASUDUNG - '" + tonggiatri + "' where pk_seq = '" + ctkmId + "'");
					db.update(st);
				}
				rsUpdateKm.close();
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
	
	public ResultSet getNppRs() 
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public String getDhId()
	{
		return this.dhId;
	}

	public void setDhId(String dhId) 
	{
		this.dhId = dhId;
	}

	public boolean isCheckDhtv()
	{
		return this.isCheckDhtv;
	}

	public void setCheckDhtv(boolean flag)
	{
		this.isCheckDhtv = flag;
	}

	public boolean createDtdhtrave(String id, String userId) 
	{
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
						
			String query = "select ddkd_fk, gsbh_fk, kho_fk, kbh_fk, khachhang_fk from donhang where pk_seq = '" + this.dhId + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					String ddkd_fk = "";
					if(rs.getString("ddkd_fk") != null)
						ddkd_fk = rs.getString("ddkd_fk");
					String gsbh_fk = "";
					if(rs.getString("gsbh_fk") != null)
						gsbh_fk = rs.getString("gsbh_fk");
					String kho_fk = "";
					if(rs.getString("kho_fk") != null)
						kho_fk = rs.getString("kho_fk");
					String kbh_fk = "";
					if(rs.getString("kbh_fk") != null)
						kbh_fk = rs.getString("kbh_fk");
					String khachhang_fk = "";
					if(rs.getString("khachhang_fk") != null)
						khachhang_fk = rs.getString("khachhang_fk");
					
					query = "insert into donhangtrave(ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, ddkd_fk, khachhang_fk, npp_fk, vat, gsbh_fk, kho_fk, kbh_fk, donhang_fk) " ;
					query = query + "values('" + this.ngaygiaodich + "','1','" + this.getDateTime() + "','" + this.getDateTime() + "','" + userId +"','" + userId +"','" + ddkd_fk + "','" + khachhang_fk + "','" + this.nppId + "','" + this.VAT + "', '" + gsbh_fk + "', '" + kho_fk + "', '" + kbh_fk + "', '" + dhId + "')";
					if(!db.update(query)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "Khong the tao moi 'DonHangTraVe' , " + query;
						return false; 
					}
					
					//luu vao bang donhangtrave_sanpham (khong can thiet, chi nhugn don hang da chot moi cho tra ve.)
					/*
					query = "select IDENT_CURRENT('donhangtrave') as dhtvId";
					
					ResultSet rsDh = this.db.get(query);
					rsDh.next();
					String dhtvId = rsDh.getString("dhId");
					rsDh.close();
					
					query = "INSERT donhangtrave_sanpham (donhangtrave_fk, sanpham_fk, soluong, kho_fk, giamua) ";
					query += "SELECT " + dhtvId + " as donhangtrave_fk, sanpham_fk, soluong, kho_fk, giamua  FROM donhang_sanpham WHERE donhang_fk = '" + this.dhId + "'";
					if(!db.update(query)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "Khong the tao moi 'donhangtrave_sanpham' , " + query;
						return false; 
					}
					*/
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

	
	public List<ISanpham> getSpkmList() 
	{
		return this.spkmlist;
	}


	public void setSpkmList(List<ISanpham> spkmlist) 
	{
		this.spkmlist = spkmlist;
	}
	
}
