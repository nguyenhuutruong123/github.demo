package geso.dms.distributor.beans.quanlykhuyenmai.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.beans.quanlykhuyenmai.ITrakhuyenmaiNpp;
import geso.dms.distributor.db.sql.dbutils;

public class TrakhuyenmaiNpp implements ITrakhuyenmaiNpp 
{
	String userId;
	String ctkmId;
	String trakmId;
	String tungay;
	String denngay;
	String scheme;
	String ctkmDiengiai;
	String diengiai;
	String nppId;
	String nppTen;
	String sitecode;
	String msg;
	String trangthai;
	
	List<ISanpham> spList;
	String spIds;
	dbutils db;
	
	public TrakhuyenmaiNpp()
	{
		this.ctkmId = "";
		this.trakmId = "";
		this.tungay = "";
		this.denngay = "";
		this.scheme = "";
		this.ctkmDiengiai = "";
		this.diengiai = "";
		this.spIds = "";
		this.msg = "";
		this.trangthai = "";
		
		db = new dbutils();
	}
	
	public TrakhuyenmaiNpp(String ctkmId, String tkmId)
	{
		this.ctkmId = ctkmId;
		this.trakmId = tkmId;
		this.tungay = "";
		this.denngay = "";
		this.scheme = "";
		this.ctkmDiengiai = "";
		this.diengiai = "";
		this.spIds = "";
		this.msg = "";
		this.trangthai = "";
		
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

	public String getScheme() 
	{
		return this.scheme;
	}

	public void setScheme(String scheme) 
	{
		this.scheme = scheme;
	}

	public String getTungay() 
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay() 
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
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

	public void setMsg(String Msg) 
	{
		this.msg = Msg;
	}

	public String getMsg() 
	{
		return this.msg;
	}
	
	private void getNppInfo()
	{	
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		System.out.println(this.nppTen);
		this.sitecode=util.getSitecode();
	}

	
	public String getCtkmId() 
	{
		return this.ctkmId;
	}

	public void setCtkmId(String ctkmId) 
	{
		this.ctkmId = ctkmId;
	}

	public String getTkmId() 
	{
		return this.trakmId;
	}

	public void setTkmId(String tkmId) 
	{
		this.trakmId = tkmId;
	}

	public String getCtkmDiengiai() 
	{
		return this.ctkmDiengiai;
	}

	public void setCtkmDiengiai(String diengiai) 
	{
		this.ctkmDiengiai = diengiai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public List<ISanpham> getSpList() 
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList) 
	{
		this.spList = spList;
	}

	public void init() 
	{
		this.getNppInfo();
		String query = "select a.pk_seq as ctkmId, a.SCHEME, a.diengiai as ctkmDiengiai, c.PK_SEQ as tkmId, c.DIENGIAI, c.HINHTHUC, a.TUNGAY, a.DENNGAY from CTKHUYENMAI a inner join CTKM_TRAKM b on a.PK_SEQ = b.CTKHUYENMAI_FK inner join TRAKHUYENMAI c on b.TRAKHUYENMAI_FK = c.PK_SEQ   " + 
		" where a.pk_seq = '" + this.ctkmId + "' and c.pk_seq = '" + this.trakmId + "' order by a.pk_seq desc";
		
		System.out.println("Câu init "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.scheme = rs.getString("Scheme");
					this.ctkmDiengiai = rs.getString("ctkmDiengiai");
					this.diengiai = rs.getString("diengiai");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
		
		this.createSpList();
	}

	private void createSpList() 
	{
		List<ISanpham> spList = new ArrayList<ISanpham>();
		String query = "select COUNT(*) as sodong from TRAKM_NHAPP where trakm_fk = '" + this.trakmId + "' and ctkm_fk = '" + this.ctkmId + "' and npp_fk = '" + this.nppId + "'";
		ResultSet rsRow = db.get(query);
		try 
		{
			if(rsRow != null)
			{
				if(rsRow.next())
				{
					if(rsRow.getInt("sodong") > 0)
						this.trangthai = "DaCapNhat";
					rsRow.close();
				}
			}
		} 
		catch(Exception e1) {}
		
		System.out.println("Trang thai la: " + this.trangthai + "\n");
		
		if(this.trangthai.length() == 0)
		{
			//query = "select b.MA as spMa, a.SANPHAM_FK as spId, b.TEN as spTen, a.DONGIA from TRAKHUYENMAI_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where TRAKHUYENMAI_FK = '" + this.trakmId + "'";
			//query = "select b.MA as spMa, a.SANPHAM_FK as spId, b.TEN as spTen, a.DONGIA, c.AVAILABLE as hienhuu " + 
					//"from TRAKHUYENMAI_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +
					//"inner join (select SANPHAM_FK, AVAILABLE from NHAPP_KHO where NPP_FK = '" + this.nppId + "' and KHO_FK = (select KHO_FK from ctkhuyenmai where PK_SEQ = '" + this.ctkmId + "') and KBH_FK in (select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "')) c on b.PK_SEQ = c.SANPHAM_FK " +
					//"where a.TRAKHUYENMAI_FK = '" + this.trakmId + "'";
			query = " SELECT f.MA as spMa, f.PK_SEQ as spId, f.TEN as spTen, e.dongia,  g.AVAILABLE as hienhuu \n"+
				    " FROM Trakhuyenmai_sanpham a  \n" +
					"      inner join (select distinct c.BGMUANPP_FK, c.SANPHAM_FK, c.GIAMUANPP as dongia \n"+
					"                  from NHAPHANPHOI a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.NPP_FK \n" +
					"                                     inner join BGMUANPP_SANPHAM c on b.BANGGIAMUANPP_FK = c.BGMUANPP_FK  \n"+
					"                  where a.PK_SEQ = '" + this.nppId + "' and c.GIAMUANPP > 0) e on a.sanpham_fk = e.SANPHAM_FK  \n" +
					"      inner join (select top 1 PK_SEQ  \n"+
					"                  from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bgnpp on bg.PK_SEQ = bgnpp.BANGGIAMUANPP_FK  \n"+
					"                  where bgnpp.NPP_FK = '" + this.nppId + "' \n"+
					"                        and  bg.TUNGAY <= (select TUNGAY from CTKHUYENMAI where PK_SEQ = '" + this.ctkmId + "')  \n"+
					"                  order by TUNGAY desc) h on e.BGMUANPP_FK = h.PK_SEQ \n"+
					"      inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ  \n"+
					"      inner join (select SANPHAM_FK, AVAILABLE from NHAPP_KHO where NPP_FK = '" + this.nppId + "' \n" +
					"                   and KHO_FK = (select KHO_FK from ctkhuyenmai where PK_SEQ = '" + this.ctkmId + "') and KBH_FK in (select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "')) g on f.PK_SEQ = g.SANPHAM_FK " +
					" WHERE a.TRAKHUYENMAI_FK = '" + this.trakmId + "' order by g.AVAILABLE desc";
			System.out.println("lay gia san pham: " + query + "\n");
		}
		else
		{
			//query = "select b.MA as spMa, a.SANPHAM_FK as spId, b.TEN as spTen, a.DONGIA, a.thutuuutien from TRAKM_NHAPP a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where a.ctkm_fk = '" + this.ctkmId + "' and a.npp_fk = '" + this.nppId + "' and a.trakm_fk = '" + this.trakmId + "' order by a.thutuuutien asc";
			query = "select b.MA as spMa, a.SANPHAM_FK as spId, b.TEN as spTen, a.DONGIA, a.thutuuutien, c.AVAILABLE as hienhuu " +
					"from TRAKM_NHAPP a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +
					"inner join (select SANPHAM_FK, AVAILABLE from NHAPP_KHO where NPP_FK = '" + this.nppId + "' and KHO_FK = (select KHO_FK from ctkhuyenmai where PK_SEQ = '" + this.ctkmId + "') and KBH_FK in (select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "')) c on b.PK_SEQ = c.SANPHAM_FK " +
					"where a.ctkm_fk = '" + this.ctkmId + "' and a.npp_fk = '" + this.nppId + "' and a.trakm_fk = '" + this.trakmId + "' order by a.thutuuutien ASC";			
		}
		
		System.out.println("Query la: " + query + "\n");
		
		ResultSet sp = db.get(query);
		if(sp != null)
		{
			try 
			{
				ISanpham sanpham = null;
				int i = 1;
				while(sp.next())
				{
					sanpham = new Sanpham();
					sanpham.setMasanpham(sp.getString("spMa"));
					sanpham.setId(sp.getString("spId"));
					sanpham.setTensanpham(sp.getString("spTen"));
					sanpham.setDongia(sp.getString("dongia"));
					//System.out.println("Don gia la: " + sp.getString("dongia") + "\n");
					sanpham.setSoluong(sp.getString("hienhuu"));
					if(this.trangthai.length() > 0)
						sanpham.setDonvitinh(sp.getString("thutuuutien")); 
					else
						sanpham.setDonvitinh(Integer.toString(i)); //luu thu tu uu tien
					i++;
					
					spList.add(sanpham);
				}
				sp.close();
			} 
			catch(Exception e) {}
		}
		this.spList = spList;
	}

	public boolean save() 
	{
		String query = "";
		try 
		{
			db.getConnection().setAutoCommit(false);
			query="delete from TRAKM_NHAPP where ctkm_fk = '" + this.ctkmId + "' and npp_fk = '" + this.nppId + "' and trakm_fk = '" + this.trakmId + "'";
			
			if(!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Loi khi cap nhat bang :"+ query;
				return false; 
			}
			for(int i = 0; i < spList.size(); i++)
			{
				ISanpham sp = spList.get(i); 
				String thutu = "1000";
				if(sp.getDonvitinh() == "" || sp.getDonvitinh() == null)
					thutu = "1000";
				else
					thutu = sp.getDonvitinh();
				
				query = "insert TRAKM_NHAPP(trakm_fk, ctkm_fk, npp_fk, sanpham_fk, dongia, thutuuutien) values('" + this.trakmId + "', '" + this.ctkmId + "','" + this.nppId + "', '" + sp.getId() + "', '" + sp.getDongia().trim() + "', '" + thutu + "')";
				System.out.println("Query chen trakm la: " + query + "\n");
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the cap nhat: " + query;
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		
			}
		
		return true;
	}
	
	public boolean remove() 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "delete from TRAKM_NHAPP where ctkm_fk = '" + this.ctkmId + "' and npp_fk = '" + this.nppId + "' and trakm_fk = '" + this.trakmId + "'";
			if(!db.update(query)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Loi khi cap nhat bang "+query;
				return false; 
			}
			System.out.println("Lenh remove: " + query);
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			
			this.msg = "Khong the xoa tra khuyen mai nay."; return false;
			}
		
		return true;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
		if(this.db!=null){
			db.shutDown();
		}
	}
}
