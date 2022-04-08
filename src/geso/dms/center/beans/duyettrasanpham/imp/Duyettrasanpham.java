package geso.dms.center.beans.duyettrasanpham.imp;

import geso.dms.center.beans.duyettrasanpham.IDuyettrasanpham;
import geso.dms.distributor.beans.donhangtrave.ISanpham;
import geso.dms.distributor.beans.donhangtrave.imp.Sanpham;
import geso.dms.distributor.db.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Duyettrasanpham implements IDuyettrasanpham 
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
	
	String tongtientruocVAT;
	String tongtiensauVAT;
	
	
	dbutils db;
	
	public Duyettrasanpham(String[] param)
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
		
		db = new dbutils();
		
	}
	
	public Duyettrasanpham(String id)
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
		this.createDdkd();	
		this.createKho();		
	}
	
	private void createDdkd()
	{
		//tao gsbh
		String sql ="select a.pk_seq,a.ten from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk where npp_fk ='"+ this.nppId +"'";
		this.gsbhList = db.get(sql);
		
		String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where npp_fk ='"+ this.nppId +" ' and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from nhapp_giamsatbh where gsbh_fk ='"+ this.gsbhId +"' and npp_fk = '" + this.nppId + "') )";
		this.ddkdlist = db.get(query);
	}
	
	private void createKhRs()
	{
		String query = "select a.pk_seq as khId, a.ten as khTen, a.diachi, ISNULL(b.CHIETKHAU,0) as chietkhau ";
		query = query + "from KHACHHANG a left join MUCCHIETKHAU b on a.CHIETKHAU_FK = b.PK_SEQ where a.npp_fk = '" + this.nppId + "' ";
		
		this.khlist = db.get(query);
	}
	
	private void createKho()
	{
		this.kholist = db.get("select distinct PK_SEQ as khoId, Ten, Diengiai from KHO where PK_SEQ in (select kho_fk from NHAPP_KHO where npp_fk = '" + this.nppId + "')");
	}
	
	public void init() 
	{
		String query = "select a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, a.khachhang_fk as khId, a.gsbh_fk as gsbhId, g.ten as khTen, g.smartid, a.kho_fk as khoId, b.ten as nguoitao, c.ten as nguoisua, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, f.pk_seq as nppId, f.sitecode";
		query = query + " from donhangtrave a left join nhanvien b on a.nguoitao = b.pk_seq left join nhanvien c on a.nguoisua = c.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " inner join khachhang g on a.khachhang_fk=g.pk_seq ";
		query = query + " where a.pk_seq='" + this.id + "'";
	
        ResultSet rs =  db.get(query);
        try
        {
            rs.next();
            this.id = rs.getString("dhId");
			this.khId = rs.getString("khId");
			this.khTen = rs.getString("khTen");
			this.smartId = rs.getString("smartId").substring(rs.getString("smartId").indexOf("_")+1, rs.getString("smartId").length());
			this.ngaygiaodich = rs.getString("ngaynhap");
			
			this.daidienkd = rs.getString("ddkdTen");
			this.trangthai = rs.getString("trangthai");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");
			this.VAT = "10";
			this.ddkdId = rs.getString("ddkdId");
			this.khoId = rs.getString("khoId");
			
			this.gsbhId = "";
			if(rs.getString("gsbhId") != null)
				this.gsbhId = rs.getString("gsbhId");
			
			this.nppId = rs.getString("nppId");
			this.nppTen = rs.getString("nppten");
			this.sitecode = rs.getString("sitecode");
			
			rs.close();
       	}
        catch(Exception e){}
        
        this.createDdkd();
		this.createKhRs();
        this.createKho();
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
		command = command + "from donhangtrave_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq left join donvidoluong c on b.dvdl_fk = c.pk_seq ";
		command = command + " where a.donhangtrave_fk = '" + this.id + "'";

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
			} 
			catch(Exception e) {}
		}
		this.tongtiensauVAT = Float.toString(tonggiatri);
		this.splist = splist;
	}

	public boolean duyetSptrave(String id, String userId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String ngayksthem1="";
			String query="select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay where npp_fk=(select npp_fk from donhangtrave where pk_Seq="+id+")";
			System.out.println(query);
			ResultSet rs1=this.db.get(query);
				if(rs1!=null){
						if(rs1.next()){
							ngayksthem1=rs1.getString("ngay");
						 	System.out.println("Ngay Khoa So Them " +ngayksthem1);
					    }else{
					    	db.getConnection().rollback();
							this.msg = "I.Error: " + query;
							return false;
					    }
				}else{
					db.getConnection().rollback();
					this.msg = "II.Error: " + query;
					return false;
				}
			
			 query = "update donhangtrave set ngaynhap='"+ngayksthem1+"',trangthai = '3', nguoiduyet = '" + userId + "', ngayduyet = '" + this.getDateTime() + "' where pk_seq = '" + id + "' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Error: " + query;
				return false;
			}
			
			query = "select b.sanpham_fk, b.soluong, a.npp_fk, a.kho_fk, a.kbh_fk from donhangtrave a inner join donhangtrave_sanpham b on a.pk_seq = b.donhangtrave_fk where a.pk_seq = '" + id + "'";
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
					
					System.out.println("Cau lenh cap nhat: " + cmd + "\n");
					
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
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}

		return true;
	}
	

}
