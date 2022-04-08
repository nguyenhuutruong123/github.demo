package geso.dms.distributor.beans.xuatkho.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import geso.dms.distributor.beans.xuatkho.IXuatKho;
import geso.dms.distributor.beans.xuatkho.ISanPham;
import geso.dms.distributor.beans.xuatkho.imp.SanPham;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

public class XuatKho implements IXuatKho
{
	

	String id, userId, nppId, nppTen, ngaynhap, kbhId, nccId, msg, ghichu, vat, dvkdId, tienBVAT, tienAVAT;
	ResultSet nccRs, kbhRs, dvkdRs, khoRs;
	String khoNppId,khoTtId;
	

	Hashtable<String, String> dvdlList;
	Hashtable<String, String> khoTTList;
	Hashtable<String, String> khoNppList;
	

	NumberFormat formatter = new DecimalFormat("#,###,###.###");
	dbutils db;
	List<ISanPham> spList;

	public XuatKho(String id, String userId, String nppId, String nppTen, String ngaynhap, String kbhId, String nccId, String msg, String ghichu, String vat, String dvkdId)
	{
		this.id = id;
		this.userId = userId;
		this.nppId = nppId;
		this.nppTen = nppTen;
		this.ngaynhap = ngaynhap;
		this.kbhId = kbhId;
		this.nccId = nccId;
		this.msg = msg;
		this.ghichu = ghichu;
		this.vat = vat;
		this.dvkdId = dvkdId;
		db = new dbutils();
	}

	public XuatKho()
	{
		this.id = "";
		this.userId = "";
		this.nppId = "";
		this.nppTen = "";
		this.khoNppId ="";
		this.khoTtId ="";
		this.ngaynhap = "";
		this.kbhId = "";
		this.nccId = "";
		this.msg = "";
		this.ghichu = "";
		this.vat = "10";
		this.dvkdId = "";
		this.tienAVAT = "";
		this.tienBVAT = "";
		
		spList = new ArrayList<ISanPham>();
		dvdlList= new Hashtable<String, String>();
		khoTTList = new Hashtable<String, String>();
		khoNppList =  new Hashtable<String, String>();
		db = new dbutils();
	}

	public XuatKho(String id)
	{
		this.id=id;
		this.userId = "";
		this.nppId = "";
		this.nppTen = "";
		this.khoNppId ="";
		this.khoTtId ="";
		this.ngaynhap = "";
		this.kbhId = "";
		this.nccId = "";
		this.msg = "";
		this.ghichu = "";
		this.vat = "10";
		this.dvkdId = "";
		this.tienAVAT = "";
		this.tienBVAT = "";
		spList = new ArrayList<ISanPham>();
		dvdlList= new Hashtable<String, String>();
		khoTTList = new Hashtable<String, String>();
		khoNppList =  new Hashtable<String, String>();
		db = new dbutils();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getNppId()
	{
		return nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getNgaynhap()
	{
		return ngaynhap;
	}

	public void setNgaynhap(String ngaynhap)
	{
		this.ngaynhap = ngaynhap;
	}

	public String getKbhId()
	{
		return kbhId;
	}

	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}

	public String getNccId()
	{
		return nccId;
	}

	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getGhichu()
	{
		return ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public ResultSet getNccRs()
	{
		return nccRs;
	}

	public void setNccRs(ResultSet nccRs)
	{
		this.nccRs = nccRs;
	}

	public ResultSet getKbhRs()
	{
		return kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs)
	{
		this.kbhRs = kbhRs;
	}

	public ResultSet getDvkdRs()
	{
		return dvkdRs;
	}

	public void setDvkdRs(ResultSet dvkdRs)
	{
		this.dvkdRs = dvkdRs;
	}

	public String getNppTen()
	{
		return nppTen;
	}

	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}

	public String getDvkdId()
	{
		return dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	@Override
	public void createRs()
	{
		getNppInfo();
		String query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '" + this.nppId
				+ "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
		this.dvkdRs = this.db.get(query);
		query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '" + this.nppId + "' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
		this.kbhRs = this.db.get(query);

		query = "select d.pk_seq as nccId, d.ten as nccTen from nhaphanphoi a, nhacungcap_dvkd b, nhapp_nhacc_donvikd c, nhacungcap d where c.ncc_dvkd_fk = b.pk_seq and a.pk_seq = c.npp_fk and b.ncc_fk = d.pk_seq and a.pk_seq = '" + this.nppId + "'";
		this.nccRs = this.db.get(query);
		
		query="select DONVI,PK_SEQ as dvdlId from DONVIDOLUONG ";
		ResultSet rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				dvdlList.put(rs.getString("dvdlId"),rs.getString("DONVI"));
			}
			if(rs!=null)rs.close();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		query = "select distinct a.pk_seq as khoId, a.ten as ten,a.diengiai from kho a, nhapp_kho b where a.pk_seq = b.kho_fk and b.npp_fk = '" + this.nppId + "' and a.trangthai ='1'";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				khoNppList.put(rs.getString("khoId"),rs.getString("ten"));
			}
			if(rs!=null)rs.close();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		query = "select pk_seq ,ten,diachi from erp_khott ";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				khoTTList.put(rs.getString("pk_seq"),rs.getString("ten"));
			}
			if(rs!=null)rs.close();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		
	}

	private void getNppInfo()
	{
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
	}

	
	
	@Override
	public void init()
	{

		String query=
		"select pk_seq,NgayXuat,KHO_FK,KHOTT_FK,npp_fk ,kbh_fk,GhiChu from XuatKho "+
		"where pk_seq='"+this.id+"' ";
		ResultSet rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.ngaynhap=rs.getString("NgayXuat");
				this.khoNppId=rs.getString("KHO_FK");
				this.khoTtId=rs.getString("KHOTT_FK");
				this.nppId=rs.getString("npp_fk");
				this.kbhId=rs.getString("kbh_fk");
				this.ghichu=rs.getString("GhiChu");
			}
			if(rs!=null)
				rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		query=
	"		select dhsp.SanPham_FK as spId,sp.MA as spMa,sp.ten as spTen,dhsp.dvdl_fk as dvdlId, "+
	"			dhsp.SoLuong,dhsp.SoLuongChuan,dhsp.DonGia,(dhsp.soluong+kho.available) as tonkho,dhsp.THANHTIEN,qc.soluong1,qc.soluong2 "+ 	
	"		from XuatKho_SanPham dhsp        "+
	"			inner join XUATKHO ck on ck.pk_Seq=dhsp.XUATKHO_FK "+           
	"			inner join nhapp_kho kho on kho.npp_fk=ck.npp_fk and kho.kbh_fk=ck.kbh_fk and kho.kho_fk=ck.KHO_FK  and kho.sanpham_Fk=dhsp.SANPHAM_FK "+
	"			inner join SANPHAM  sp on sp.PK_SEQ=dhsp.SanPham_FK 	 "+
	"			left join QUYCACH qc on qc.SANPHAM_FK=dhsp.SANPHAM_FK and qc.DVDL1_FK=sp.DVDL_FK and qc.DVDL2_FK=100018 "+
	"		where dhsp.XuatKho_FK='"+this.id+"' "; 
		System.out.println("[spList]"+query);
		final long startTime = System.currentTimeMillis();
		ResultSet spRs = this.db.get(query);
		try
		{
			while (spRs.next())
			{
				ISanPham sp = null;
				int soluong1 = spRs.getInt("soluong1")==0?1:spRs.getInt("soluong1");
				int soluong2 = spRs.getInt("soluong2")==0?1:spRs.getInt("soluong2");
				int quycach =soluong1/soluong2;
				sp = new SanPham(spRs.getString("spId"), spRs.getString("spma"), spRs.getString("spten"), spRs.getString("dongia"), spRs.getString("dvdlId"));
				sp.setSoluong(spRs.getString("soluong")==null?"":spRs.getString("soluong"));
				sp.setSoluongchuan( spRs.getString("soluongchuan")==null?"": spRs.getString("soluongchuan"));
				sp.setTonkho( formatter.format(spRs.getDouble("tonkho")));
				sp.setThanhtien( spRs.getString("thanhtien"));
				sp.setQuycach(quycach+"" );
				spList.add(sp);
			}
			final long endTime = System.currentTimeMillis();
			System.out.println("Total execution time: " + (endTime - startTime) );
			System.out.println("Total List: "+spList.size() );
			if(spRs!=null)spRs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		createRs();
	}

	@Override
	public boolean save()
	{			
		Utility util= new Utility(); 
		if(util.KiemKeChuaDuyet(this.nppId))
		{
			this.msg ="Tồn tại kiểm kê chưa duyệt,vui lòng liên hệ trung tâm để giải quyết!";
			return false;
		}
		
		
		if(this.ngaynhap.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn ngày nhập";
			return false;
		}
		if(this.khoNppId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn kho chuyển";
			return false;
		}
		if(this.khoTtId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn kho nhập";
			return false;
		}
		
		if(this.userId==""||this.userId==null)
		{
			this.msg="Vui lòng đăng nhập lại !";
			return false;
		}
		if(this.nppId==""||this.nppId==null)
		{
			this.msg="Không xác định được Nhà phân phối ,Vui lòng đăng nhập lại !";
			return false;
		}
		if(this.dvkdId==""||this.dvkdId==null)
		{
			this.msg="Vui lòng chọn đơn vị kinh doanh !";
			return false;
		}
		if(this.kbhId==""||this.kbhId==null)
		{
			this.msg="Vui lòng chọn đơn vị kinh doanh !";
			return false;
		}
		
		if(this.spList.size()<=0)
		{
			this.msg="Không có sản phẩm nào !";
			return false;
		}
		try
		{
			this.db.getConnection().setAutoCommit(false);
			String query=
			"insert into XuatKho(NgayXuat,KBH_FK,NPP_FK,KHO_FK,KHOTT_FK,NguoiTao,NgayTao,NguoiSua,NgaySua,TrangThai,GhiChu,NCC_FK,DVKD_FK)" +
			"select '"+this.ngaynhap+"','"+this.kbhId+"','"+this.nppId+"','"+this.khoNppId+"','"+this.khoTtId+"','"+this.userId+"','"+getDateTime()+"','"+this.userId+"','"+getDateTime()+"',0,N'"+this.ghichu+"','"+this.nccId+"','"+this.dvkdId+"'   ";
			System.out.println("[Save]"+query);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.db.getConnection().rollback();
				this.msg="Vui lòng liên hệ admin với lỗi sau "+query;
				return false;
			}		
			query = "select IDENT_CURRENT('XuatKho') as dmhId";
			
			ResultSet rsDmh = db.get(query);						
			if(rsDmh.next())
			{
				this.id = rsDmh.getString("dmhId");
				rsDmh.close();
			}
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanPham sp = this.spList.get(i);
					
					double tonkho =Double.parseDouble(sp.getTonkho());
					double soluong =Double.parseDouble(sp.getSoluong());
					double quycach =Double.parseDouble(sp.getQuycach());
					tonkho =tonkho *quycach;
					if(soluong>tonkho)
					{
						this.db.getConnection().rollback();
						this.msg="Tồn kho sản phẩm ("+sp.getTen() +") không đủ,vui lòng kiểm tra lại ";
						return false;
					}
					System.out.println("[spId]"+sp.getId()+"[spMa]"+sp.getMa()+"[soluong]"+sp.getSoluong()+"[Quycach]"+sp.getQuycach()+"[tonkho]"+sp.getTonkho());
					query ="update nhapp_kho set available=available-'"+sp.getSoluong()+"',booked=booked+'"+sp.getSoluong()+"' where sanpham_fk='"+sp.getId()+"' and kho_fk='"+this.khoNppId+"' and npp_fk='"+this.nppId+"' and kbh_fk='"+this.kbhId+"'";
					if(!this.db.update(query))
					{
						this.db.getConnection().rollback();
						this.msg="Vui lòng liên hệ Admin với lỗi sau"+query;
						return false;
					}		
					query=
					" insert into XuatKho_SanPham(XuatKho_FK,SanPham_FK,DVDL_FK,SoLuong,SoLuongChuan,DonGia,ThanhTien) " +
					" select '"+this.id+"','"+sp.getId()+"','"+sp.getDvdlId()+"',"+sp.getSoluong()+","+sp.getSoluongchuan()+","+sp.getDongia()+", "+sp.getSoluong()+"*"+sp.getDongia()+" ";
					System.out.println("[XuatKho_Sp]"+query);
					if(!this.db.update(query))
					{
						this.db.getConnection().rollback();
						this.msg="Vui lòng liên hệ admin với lỗi sau "+query;
						return false;
					}		
				}
			}			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e)
		{
			try
			{
				this.db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.msg="Vui lòng liên hệ admin với lỗi sau "+e.getMessage();
			return false;
		}
	}
	@Override
	public boolean edit()
	{
		
		Utility util= new Utility(); 
		if(util.KiemKeChuaDuyet(this.nppId))
		{
			this.msg ="Tồn tại kiểm kê chưa duyệt,vui lòng liên hệ trung tâm để giải quyết!";
			return false;
		}
		
	
		if(this.ngaynhap.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn ngày nhập";
			return false;
		}
		if(this.khoNppId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn kho chuyển";
			return false;
		}
		if(this.khoTtId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn kho nhập";
			return false;
		}
		
		if(this.userId==""||this.userId==null)
		{
			this.msg="Vui lòng đăng nhập lại !";
			return false;
		}
		if(this.nppId==""||this.nppId==null)
		{
			this.msg="Không xác định được Nhà phân phối ,Vui lòng đăng nhập lại !";
			return false;
		}
		if(this.dvkdId==""||this.dvkdId==null)
		{
			this.msg="Vui lòng chọn đơn vị kinh doanh !";
			return false;
		}
		if(this.kbhId==""||this.kbhId==null)
		{
			this.msg="Vui lòng chọn đơn vị kinh doanh !";
			return false;
		}
		
		if(this.spList.size()<=0)
		{
			this.msg="Không có sản phẩm nào !";
			return false;
		}
		try
		{
			this.db.getConnection().setAutoCommit(false);
			String query=
			"		UPDATE KHO SET BOOKED=BOOKED-CKSP.SOLUONG,AVAILABLE =AVAILABLE +CKSP.SOLUONG "+
			"			FROM XUATKHO CK INNER JOIN XUATKHO_SANPHAM CKSP ON CKSP.XUATKHO_FK=CK.PK_sEQ "+
			"		INNER JOIN NHAPP_KHO KHO ON KHO.NPP_FK=CK.NPP_FK AND KHO.KBH_FK=CK.KBH_FK AND KHO.SANPHAM_FK =CKSP.SANPHAM_FK "+
			"		AND KHO.KHO_FK=CK.KHO_FK " +
			"      WHERE CK.PK_SEQ='"+id+"'      ";
			if(!db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Vui lòng liên hệ admin với lỗi sau "+query;
				return false;
			}
			query=
			"UPdate XuatKho set NgayXuat='"+this.ngaynhap+"',NCC_FK='"+this.nccId+"',DVKD_FK='"+this.dvkdId+"',KBH_FK='"+this.kbhId+"',NPP_FK='"+this.nppId+"',KHO_FK='"+this.khoNppId+"',KHOTT_FK='"+this.khoTtId+"',NguoiSua='"+this.userId+"',NgaySua='"+getDateTime()+"',GhiChu=N'"+this.ghichu+"' where pk_seq='"+this.id+"'" ;
			System.out.println("[Save]"+query);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Vui lòng liên hệ admin với lỗi sau "+query;
				return false;
			}		
			query="delete from XuatKho_SanPham where XuatKho_FK='"+this.id+"'";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Vui lòng liên hệ admin với lỗi sau "+query;
				return false;
			}	
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanPham sp = this.spList.get(i);
					
					double tonkho =Double.parseDouble(sp.getTonkho());
					double soluong =Double.parseDouble(sp.getSoluong());
					double quycach =Double.parseDouble(sp.getQuycach());
					tonkho =tonkho *quycach;
					if(soluong>tonkho)
					{
						this.db.getConnection().rollback();
						this.msg="Tồn kho sản phẩm ("+sp.getTen() +") không đủ,vui lòng kiểm tra lại ";
						return false;
					}
					System.out.println("[spId]"+sp.getId()+"[spMa]"+sp.getMa()+"[soluong]"+sp.getSoluong()+"[Quycach]"+sp.getQuycach()+"[tonkho]"+sp.getTonkho());
					query ="update nhapp_kho set available=available-'"+sp.getSoluong()+"',booked=booked+'"+sp.getSoluong()+"' where sanpham_fk='"+sp.getId()+"' and kho_fk='"+this.khoNppId+"' and npp_fk='"+this.nppId+"' and kbh_fk='"+this.kbhId+"'";
					if(!this.db.update(query))
					{
						this.db.getConnection().rollback();
						this.msg="Vui lòng liên hệ Admin với lỗi sau"+query;
						return false;
					}		
					query=
					" insert into XuatKho_SanPham(XuatKho_FK,SanPham_FK,DVDL_FK,SoLuong,SoLuongChuan,DonGia,ThanhTien) " +
					" select '"+this.id+"','"+sp.getId()+"','"+sp.getDvdlId()+"',"+sp.getSoluong()+","+sp.getSoluongchuan()+","+sp.getDongia()+", "+sp.getSoluong()+"*"+sp.getDongia()+" ";
					System.out.println("[XuatKho_Sp]"+query);
					if(!this.db.update(query))
					{
						this.db.getConnection().rollback();
						this.msg="Vui lòng liên hệ admin với lỗi sau "+query;
						return false;
					}		
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e)
		{
			try
			{
				this.db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.msg="Vui lòng liên hệ admin với lỗi sau "+e.getMessage();
			return false;
		}
	}

	@Override
	public void DBclose()
	{
		
			try
			{
				if(this.khoRs!=null)
				this.khoRs.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public String getVat()
	{
		return vat;
	}

	public void setVat(String vat)
	{
		this.vat = vat;
	}

	public List<ISanPham> getSpList()
	{
		return spList;
	}

	public void setSpList(List<ISanPham> spList)
	{
		this.spList = spList;
	}


	public ResultSet getKhoRs()
	{
		return khoRs;
	}

	public void setKhoRs(ResultSet khoRs)
	{
		this.khoRs = khoRs;
	}

	public String getTienBVAT()
	{
		return tienBVAT;
	}

	public void setTienBVAT(String tienBVAT)
	{
		this.tienBVAT = tienBVAT;
	}

	public String getTienAVAT()
	{
		return tienAVAT;
	}

	public void setTienAVAT(String tienAVAT)
	{
		this.tienAVAT = tienAVAT;
	}

	@Override
	public java.util.Hashtable<String, String> getDvdlList()
	{
		return this.dvdlList;
	}

	@Override
	public void setDvdlList(java.util.Hashtable<String, String> dvdlList)
	{
		this.dvdlList = dvdlList;
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	@Override
	public Hashtable<String, String> getKhoTtList()
	{
		return this.khoTTList;
	}

	@Override
	public void setKhotTtList(Hashtable<String, String> khoTtList)
	{
		this.khoTTList =khoTtList;
	}

	@Override
	public String getKhoNppId()
	{
		return this.khoNppId;
	}

	@Override
	public void setKhoNppId(String khoNppId)
	{
		this.khoNppId =khoNppId;
	}

	@Override
	public String getKhoTtId()
	{
		return this.khoTtId;
	}

	@Override
	public void setKhoTtId(String khoTtId)
	{
		this.khoTtId =khoTtId;
	}
	public Hashtable<String, String> getKhoNppList()
	{
		return khoNppList;
	}

	public void setKhoNppList(Hashtable<String, String> khoNppList)
	{
		this.khoNppList = khoNppList;
	}
	
}
