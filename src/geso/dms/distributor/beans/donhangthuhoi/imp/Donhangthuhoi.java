package geso.dms.distributor.beans.donhangthuhoi.imp;

import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.beans.donhangthuhoi.IDonhangthuhoi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.dms.distributor.db.sql.dbutils;

public class Donhangthuhoi implements IDonhangthuhoi, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String pxkId;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String dhId;
	String msg;
	
	ResultSet nhanviengn;
	String nvgnId;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	//in file pdf
	ResultSet khachhangRs;
	List<ISanpham> spList;
	List<ISanpham> spkmList;
		
	dbutils db;
	
	public Donhangthuhoi(String[] param)
	{
		this.id = param[0];
		this.pxkId = param[1];
		this.trangthai = param[2];
		this.ngaytao = param[3];
		this.nguoitao = param[4];
		this.ngaysua = param[5];
		this.nguoisua = param[6];
		this.dhId = param[7];
		this.msg = "";
		db = new dbutils();
	}
	
	public Donhangthuhoi(String id)
	{
		this.id = id;
		this.pxkId = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nvgnId = "";
		this.dhId = "";
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
	
	public ResultSet getNhanvienGN() 
	{		
		return this.nhanviengn;
	}
	
	public void setNhanvienGN(ResultSet nhanviengn) 
	{
		this.nhanviengn = nhanviengn;		
	}
	
	public String getNvgnId() 
	{		
		return this.nvgnId;
	}
	
	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;		
	}
	
	public String getPhieuxuatkho() 
	{
		return this.pxkId;
	}

	public void setPhieuxuatkho(String pxkId) 
	{
		this.pxkId = pxkId;
	}
	
	public String getDonhang() 
	{
		return this.dhId;
	}

	public void setDonhang(String donhang) 
	{
		this.dhId = donhang;
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
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.sitecode = rs.getString("sitecode");
				
			}else
			{
				this.nppId = "";
				this.nppTen = "";
				this.sitecode = "";				
			}
			
		}catch(Exception e){}	
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}

	public boolean CreateDhth() 
	{
		return true;
	}

	public void init() 
	{
		this.getNppInfo();
	
		String query = "select dhth.pk_seq as dhthId, dhth.donhang_fk as dhId, dhth.ngaytao, kh.ten, kh.diachi, kh.dienthoai ";
		query = query + " from donhangthuhoi dhth inner join donhang dh on dhth.donhang_fk = dh.pk_seq inner join khachhang kh on dh.khachhang_fk = kh.pk_seq ";
		query = query + " where dhth.pk_seq='" + this.id + "'";
		this.khachhangRs =  db.get(query);
		
		query = "select sp.pk_seq as spId, sp.ma as spMa, sp.ten as spTen, dhth_sp.soluong from donhangthuhoi_sanpham dhth_sp inner join sanpham sp on dhth_sp.sanpham_fk = sp.pk_seq  where dhth_sp.dhth_fk = '" + this.id + "'";
		ResultSet sanphamRS = db.get(query);
		List<ISanpham> sanphamList = new ArrayList<ISanpham>();
		if(sanphamRS != null)
		{
			String[] param = new String[8];
			ISanpham sp = null;	
			try 
			{
				while(sanphamRS.next())
				{
					param[0] = sanphamRS.getString("spId");
					param[1] = sanphamRS.getString("spMa");
					param[2] = sanphamRS.getString("spTen");
					param[3] = sanphamRS.getString("soluong");
					param[4] = "";
					param[5] = "";
					param[6] = "";
					
					sp = new Sanpham(param);
					sanphamList.add(sp);
				}
				sanphamRS.close();
			} 
			catch(Exception e) {}
			finally{
				try {
					if(sanphamRS != null)
					sanphamRS.close();
				} catch(Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.spList = sanphamList;
		
	}
	
	public void createRS()
	{
		this.getNppInfo();
		this.createNvgnRs();
	}
	
	private void createNvgnRs()
	{
		String sql = "select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai='1'";
		this.nhanviengn = db.get(sql);
	}
	
	public ResultSet getKhachhang() 
	{
		return this.khachhangRs;
	}

	public void setKhachhang(ResultSet khachangRs)
	{
		this.khachhangRs = khachangRs;
	}

	public List<ISanpham> getSanphamList() 
	{
		return this.spList;
	}

	public void setSanphamList(List<ISanpham> spList) 
	{
		this.spList = spList;
	}

	public List<ISanpham> getSpkmList() 
	{
		return this.spkmList;
	}

	public void setSpkmList(List<ISanpham> spkmList) 
	{
		this.spkmList = spkmList;
	}
		
	public void DBclose() 
	{
		
		if(this.nhanviengn != null)
			try {
				if(this.nhanviengn != null)
				this.nhanviengn.close();
				if(this.db != null)
					this.db.shutDown();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	
}

