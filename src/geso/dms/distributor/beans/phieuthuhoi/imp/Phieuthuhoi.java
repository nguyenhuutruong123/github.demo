package geso.dms.distributor.beans.phieuthuhoi.imp;

import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.beans.phieuthuhoi.IPhieuthuhoi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.dms.distributor.db.sql.dbutils;

public class Phieuthuhoi implements IPhieuthuhoi, Serializable
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
	String msg;
	
	ResultSet nhanviengn;
	String nvgnId;
	String nvgnTen;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	//in file pdf
	List<ISanpham> spList;
	List<ISanpham> spkmList;
	List<ISanpham> spkmSauthList;
	
	dbutils db;
	public Phieuthuhoi(String[] param)
	{
		this.id = param[0];
		this.pxkId = param[1];
		this.trangthai = param[2];
		this.ngaytao = param[3];
		this.nguoitao = param[4];
		this.ngaysua = param[5];
		this.nguoisua = param[6];
		this.msg = "";
		db = new dbutils();
	}
	
	public Phieuthuhoi(String id)
	{
		this.id = id;
		this.pxkId = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nvgnId = "";
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
		/*try 
		{
			ResultSet rs = db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
			if( rs != null)
			{
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
		} 
		catch(Exception e) {}
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public boolean CreatePth() 
	{
		return true;
	}

	public boolean UpdatePth() 
	{
		return true;
	}

	public void init() 
	{
		this.getNppInfo();
		
		String query = "select b.pk_seq as pxkId, a.ngaytao, c.ten as nvgnTen from phieuthuhoi a inner join phieuxuatkho b on a.phieuxuatkho_fk = b.pk_seq inner join nhanviengiaonhan c on b.nvgn_fk = c.pk_seq ";
		query = query + " where a.pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				rs.next();
				this.pxkId = rs.getString("pxkId");
				this.ngaytao = rs.getDate("ngaytao").toString();
				this.nvgnTen = rs.getString("nvgnTen");
				rs.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		
		query = "select sp.pk_seq as spId, sp.ma as spMa, sp.ten as spTen, pth_sp.soluong from phieuthuhoi_sanpham pth_sp inner join sanpham sp on pth_sp.sanpham_fk = sp.pk_seq  where pth_sp.pth_fk = '" + this.id + "'";
		ResultSet sanphamRS = db.get(query);
		List<ISanpham> sanphamList = new ArrayList<ISanpham>();
		//if(sanphamRS != null)
		{
			String[] param = new String[11];
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
					param[7] = "";
					
					sp = new Sanpham(param);
					sanphamList.add(sp);
				}
				sanphamRS.close();
			} 
			catch(Exception e) {e.printStackTrace();}
			finally{try {
				if(sanphamRS != null)
					sanphamRS.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		this.spList = sanphamList;
		
		query = "select c.ten as khoTen, d.ten as kbhTen, b.pk_seq as spId, b.ma as spMa, b.ten as spTen, sum(a.soluong) as soluong from phieuthuhoi_spkm a inner join sanpham b on a.sanpham_fk = b.pk_seq left join kho c on a.kho_fk = c.pk_seq ";
		query += "left join kenhbanhang d on a.kbh_fk = d.pk_seq where a.pth_fk = '" + this.id + "' group by c.ten, d.ten, b.pk_seq, b.ma, b.ten";
		
		ResultSet sanphamKmRS = db.get(query);
		List<ISanpham> sanphamKmList = new ArrayList<ISanpham>();
		if(sanphamKmRS != null)
		{
			String[] param = new String[11];
			ISanpham sp = null;	
			try 
			{
				while(sanphamKmRS.next())
				{
					param[0] = sanphamKmRS.getString("spId");
					param[1] = sanphamKmRS.getString("spMa");
					param[2] = sanphamKmRS.getString("spTen");
					param[3] = sanphamKmRS.getString("soluong");
					
					param[4] = "";
					if(sanphamKmRS.getString("khoTen") != null)
						param[4] = sanphamKmRS.getString("khoTen");
					
					param[5] = "";
					if(sanphamKmRS.getString("kbhTen") != null)
						param[5] = sanphamKmRS.getString("kbhTen");
					
					param[6] = "";
					param[7] = "";
					
					sp = new Sanpham(param);
					sanphamKmList.add(sp);
				}
				sanphamKmRS.close();
			} 
			catch(Exception e) {e.printStackTrace();}
			
			finally{try {
				if(sanphamKmRS != null)
					sanphamKmRS.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		
		this.spkmList = sanphamKmList;
	}
	
	private void createSpkmSauthuhoi()
	{
		List<ISanpham> spkmNewList = new ArrayList<ISanpham>();
		String query = "select donhang_fk as dhId from phieuxuatkho_donhang where pxk_fk = '" + this.pxkId + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String dhId = rs.getString("dhId");
					query = "select a.trakmId, a.soxuat, a.spMa, isnull(a.soluong, 0) as soluong, tkm.loai, tkm.hinhthuc, tkm.tongluong from donhang_ctkm_trakm a inner join trakhuyenmai tkm on a.trakmid = tkm.pk_seq ";
					query = query + "where a.donhangid = '" + dhId + "'";
					
					ResultSet spKhuyenmaiRS = db.get(query);
					if(spKhuyenmaiRS != null) //don hang co spkm
					{
						try 
						{
							while(spKhuyenmaiRS.next())
							{
								String trakmId = spKhuyenmaiRS.getString("trakmId");
								String type = spKhuyenmaiRS.getString("loai");
								String hinhthuc = spKhuyenmaiRS.getString("hinhthuc");
								String soxuat = spKhuyenmaiRS.getString("soxuat");
								String soluong = spKhuyenmaiRS.getString("soluong");
								if(type.equals("3")) //tra sp
								{
									if(hinhthuc.equals("2"))
									{
										//String tongluong = spKhuyenmaiRS.getString("tongluong");
										String spMa = spKhuyenmaiRS.getString("spMa");
										
										String sql = "select pk_seq as spId, ma as spMa, ten as spTen from sanpham where ma = '" + spMa + "'";
										ResultSet spKM = db.get(sql);
										if(spKM != null)
										{
											spKM.next();
											
											String slg = Integer.toString(Integer.parseInt(soxuat) * Integer.parseInt(soluong));																						
											ISanpham sp = new Sanpham(spKM.getString("spId"), spKM.getString("spMa"), spKM.getString("spTen"), slg, "", "", "", "");
											spkmNewList.add(sp);
											
											spKM.close();								
										}
									}
									else
									{
										String sql = "select sp.pk_seq as spId, sp.ma as spMa, sp.ten as spTen, tkm_sp.soluong from trakhuyenmai_sanpham tkm_sp inner join sanpham sp on tkm_sp.sanpham_fk = sp.pk_seq ";
										sql = sql +	" where tkm_sp.trakhuyenmai_fk = '" + trakmId.trim() + "'";
										ResultSet spKM = db.get(sql);
										if(spKM != null)
										{
											while(spKM.next())
											{									
												String slg = Integer.toString( Integer.parseInt(soxuat) * Integer.parseInt(spKM.getString("soluong")) );
												ISanpham sp = new Sanpham(spKM.getString("spId"), spKM.getString("spMa"), spKM.getString("spTen"), slg, "", "", "", "");
												spkmNewList.add(sp);
											}
											spKM.close();
										}
									}
								}
							}
						} 
						catch(Exception e) {}
					}
				}
				rs.close();
			} 
			catch(Exception e) {}
			finally{
				try {
					if(rs != null)
						rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

		//hieu chinh lai spkm (Cong don)
		for(int i=0; i < spkmNewList.size() - 1; i++)
		{
			Sanpham spA  = (Sanpham)spkmNewList.get(i);
			for(int j = i+1; j < spkmNewList.size(); j++)
			{				
				Sanpham spB = (Sanpham)spkmNewList.get(j);
				if(spA.getId().trim().equals(spB.getId().trim()))
				{
					int slg = Integer.parseInt(spA.getSoluong()) + Integer.parseInt(spB.getSoluong());
					spkmNewList.get(i).setSoluong(Integer.toString(slg));
					spkmNewList.remove(j);
					i--;
				}
			}
		}
		
		this.spkmSauthList = spkmNewList;
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
		try 
		{
			if(this.nhanviengn != null)
				this.nhanviengn.close();
			if(this.spList!=null){
				spList.clear();
			}
			if(this.spkmList!=null){
				spkmList.clear();
			}
			if(this.spkmSauthList!=null){
				spkmSauthList.clear();
			}
			if(this.db != null)
			this.db.shutDown();
			 spList=null;
			 spkmList=null;
			 spkmSauthList=null;
		} 
		catch(Exception e) {}
		
		
	}

	public List<ISanpham> getSpkmSauthList() 
	{
		return this.spkmSauthList;
	}

	public void setSpkmSauthList(List<ISanpham> spkmSauthList) 
	{
		this.spkmSauthList = spkmSauthList;
	}

	public String getNvgnTen() 
	{
		return this.nvgnTen;
	}

	public void setNvgnTen(String nvgnTen)
	{
		this.nvgnTen = nvgnTen;
	}
	
}
