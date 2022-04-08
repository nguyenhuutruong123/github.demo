package geso.dms.center.beans.khaibaockdathang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import geso.dms.center.beans.khaibaockdathang.IKhaibaoCKdathang;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class KhaibaoCKdathang implements IKhaibaoCKdathang{

	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String loaick;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String tungay;
	String denngay;
	String scheme;
	String diengiai;
	String trangthai;
	String msg;
	
	String diengiaimucvuot;
	String mucvuot;
	String ckmucvuot;
	
	ResultSet nppRs;
	String nppIds;
	ResultSet spRs;
	String spIds;
	ResultSet nhomspRs;
	String nhomspIds;
	ResultSet nganhhangRs;
	String nganhhangIds;
	ResultSet nhanhangRs;
	String nhanhangIds;
	
	ResultSet vungRs;
	String vungIds;
	ResultSet kvRs;
	String kvIds;
	
	String[] chietkhauDh;
	String[] chietkhauSp;
	String[] tumuc;
	String[] denmuc;
	String[] ghichu;
	
	dbutils db;
	
	public KhaibaoCKdathang(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.loaick = param[9];
		this.ngaytao = param[2];
		this.nguoitao = param[3];
		this.ngaysua = param[4];
		this.nguoisua = param[5];
		this.tungay = param[6];
		this.denngay = param[7];
		this.scheme = param[1];
		this.diengiai = param[8];
		this.msg = "";
		this.nppIds = "";
		this.spIds = "";
		this.diengiaimucvuot = "";
		this.mucvuot = "";
		this.ckmucvuot = "";
		this.vungIds = "";
		this.kvIds = "";
		this.nganhhangIds = "";
		this.nhanhangIds = "";
		this.nhomspIds = "";
		this.activeTab = "0";
		this.trangthai = param[9];
		this.db = new dbutils();
	}
	
	public KhaibaoCKdathang(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.loaick = "1";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.scheme = "";
		this.diengiai = "";
		this.tungay = "";
		this.denngay = "";
		this.msg = "";
		this.nppIds = "";
		this.spIds = "";
		this.diengiaimucvuot = "";
		this.mucvuot = "";
		this.ckmucvuot = "";
		this.vungIds = "";
		this.kvIds = "";
		this.nganhhangIds = "";
		this.nhanhangIds = "";
		this.nhomspIds = "";
		this.activeTab = "0";
		this.trangthai = "0";
		this.db = new dbutils();
		if(id.length() > 0)
			this.init();
	}

	String activeTab;

	public String getActiveTab()
	{
		return activeTab;
	}
	public void setActiveTab(String activeTab)
	{
		this.activeTab = activeTab;
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

	public String getMucvuot() 
	{
		return this.mucvuot;
	}

	public void setMucvuot(String mucvuot) 
	{
		this.mucvuot = mucvuot;
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
	
	public String getScheme() 
	{
		return this.scheme;
	}

	public void setScheme(String scheme) 
	{
		this.scheme = scheme;
	}
	
	public boolean CreateCkdh()
	{
		try
		{
			if(this.tungay.trim().length() <= 0)
			{
				this.msg = "Vui lÃ²ng chá»�n ngÃ y báº¯t Ä‘áº§u";
				return false;
			}
			
			if(this.denngay.trim().length() <= 0)
			{
				this.msg = "Vui lÃ²ng chá»�n ngÃ y káº¿t thÃºc";
				return false;
			}
			
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập scheme";
				return false;
			}
			
			//Check Scheme
			String query = "select count(*) as sodong from chietkhaudathang where scheme = N'" + this.scheme + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			this.db.getConnection().setAutoCommit(false);
			
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			
			String command = "insert into chietkhaudathang(loaick, ngaytao, ngaysua, nguoitao, nguoisua, tungay, denngay, scheme, diengiai, diengiaimucvuot, mucvuot, ckmucvuot, trangthai) values( '" + this.loaick + "' ,'" + this.ngaytao + "','" + this.ngaysua + "','" + this.userId + "','" + this.userId + "', '" + this.tungay + "', '" + this.denngay + "', N'" + this.scheme + "', N'" + this.diengiai + "', N'" + this.diengiaimucvuot + "', '" + this.mucvuot.replaceAll(",", "") + "', '" + this.ckmucvuot.replaceAll(",", "") + "', '1')"; 
			System.out.println("tao moi " + command);
			if (!db.update(command))
			{
				this.msg = "Khong the tao moi chiet khau: " + command;		
				this.db.getConnection().rollback();
				return false;
			}
			
			//lay dkkm current
			String ckId = "";
			query = "select IDENT_CURRENT('chietkhaudathang') as ckId";
			
			ResultSet rsCk = this.db.get(query);						
			rsCk.next();
			ckId = rsCk.getString("ckId");
			rsCk.close();
			
			System.out.println("nhà pp id: " + this.nppIds);
			if(this.nppIds.length() > 0)
			{
				String[] npp = this.nppIds.split(",");
				for(int i = 0; i < npp.length; i++)
				{
					query = "insert CHIETKHAUDATHANG_NPP(chietkhaudathang_fk, npp_fk) values('" + ckId + "','" + npp[i] + "')";
					System.out.println("tao moi " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi chietkhaudathang_npp: " + query;		
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			System.out.println("sp id " + spIds);
			if(this.spIds.length() > 0)
			{
				String[] sp = this.spIds.split(",");
				for(int i = 0; i < sp.length; i++)
				{
					query = "select * from chietkhaudathang a, chietkhaudathang_sp b where a.pk_seq = b.chietkhaudathang_fk  and b.SP_FK = '" + sp[i] + "' and (('" + this.tungay + "' between TuNgay and DenNgay) or ('" + this.denngay + "' between TuNgay and DenNgay))";
					ResultSet kt = db.get(query);
					System.out.println("kiem tra " + query);
					if(!kt.next())
					{
						query = "insert CHIETKHAUDATHANG_SP(chietkhaudathang_fk, sp_fk) values('" + ckId + "','" + sp[i] + "')";
						System.out.println("tao moi " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi chietkhaudathang_sp: " + query;		
							this.db.getConnection().rollback();
							return false;
						}
					}
					else
					{
						this.msg = "Sản phẩm đã có mức chiết khấu trong khoảng thời gian bạn đang chọn.";		
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			
//			if(this.loaick.equals("0"))
//			{
//				String sql="";
//				Enumeration<String> keys = this.chitieu.keys();
//				while(keys.hasMoreElements())
//				{
//					String key = keys.nextElement();
//					String value=this.chitieu.get(key);
//					sql +="select '"+key+"' as spId,'"+value+"' as Chietkhau union "; 
//					System.out.println("KEY____"+key);
//				}
//				
//				System.out.println("Sp id: " + this.spIds);
//				if(this.spIds.trim().length() > 0)
//				{
//					sql = sql.substring(0, sql.length() - 6);
//					query = 
//						"Insert chietkhaudathang_sp(chietkhaudathang_fk, sp_fk,Chietkhau) " + 
//						"select '"+ckId+"', spId, Chietkhau from (" + sql + ") chietkhaudathang_sp  ";
//					System.out.println("4.Insert: " + query);
//					if (!db.update(query))
//					{
//						this.msg = "3.Khong the tao moi chietkhaudathang_sp: " + query;
//						db.getConnection().rollback();
//						return false;
//					}
//				}
//			}
//			else
//			{
			System.out.println("ghi chu " + ghichu[0] + ", " + ghichu[1] + ", chietkhaudh " + chietkhauDh[0]);
				if(this.chietkhauDh != null)
				{
					String sql = "";
					for(int i = 0; i < this.tumuc.length; i++)
					{
							if(this.tumuc[i+1].trim().length() > 0 && this.denmuc[i+1].trim().length() > 0)
							{
								float a, b, c , d;
								a = Float.parseFloat(this.tumuc[i].replaceAll(",", ""));
								b = Float.parseFloat(this.denmuc[i].replaceAll(",", "")); 
								c = Float.parseFloat(this.tumuc[i+1].replaceAll(",", "")); 
								d = Float.parseFloat(this.denmuc[i+1].replaceAll(",", ""));
								System.out.println("a = " + a + " b = " + b + " c = " + c + " d = " + d);
								if((a >= b) || (c <= b) || (c >= d))
								{
									this.msg = "Mức chiết khấu bị trùng lắp.";
									db.getConnection().rollback();
									return false;
								}
							}
							else break;
					}
					for(int i = 0; i < this.tumuc.length; i++)
					{
						
						//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
						if(this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.chietkhauDh[i].trim().length() > 0)
						{
							/*String ck = this.thuongSR[i].replaceAll(",", "");
							if(this.thuongTDSR[i].trim().equals("2"))
								ck = "null";*/
							
							sql += "select N'" + this.ghichu[i].replaceAll(",", "") + "' as diengiai, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
									" '" + this.chietkhauDh[i].replaceAll(",", "") + "' as chietkhau union ";
							
						}
					}
					
					if(sql.trim().length() > 0)
					{
						sql = sql.substring(0, sql.length() - 6);
						
						query = "insert Chietkhaudathang_TIEUCHI(chietkhaudathang_fk, ghichu, tumuc, toimuc, chietkhau) " +
								"select " + ckId + ", diengiai, tumuc, denmuc, chietkhau from (" + sql + ") Chietkhaudathang_TIEUCHI ";
						
						System.out.println("2.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "2.Khong the tao moi Chietkhaudathang_TIEUCHI: " + query;
							db.getConnection().rollback();
							return false;
						}
					}	
				}
				
			//}
			this.db.getConnection().setAutoCommit(true);
			this.db.getConnection().commit();
		}
		catch (Exception e) {}
		
		return true;
	}

	public boolean UpdateCkdh() 
	{
		try
		{
			if(this.tungay.trim().length() <= 0)
			{
				this.msg = "Vui lÃ²ng chá»�n ngÃ y báº¯t Ä‘áº§u";
				return false;
			}
			
			if(this.denngay.trim().length() <= 0)
			{
				this.msg = "Vui lÃ²ng chá»�n ngÃ y káº¿t thÃºc";
				return false;
			}
			
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập scheme";
				return false;
			}
			

			
			this.db.getConnection().setAutoCommit(false);
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			
			String command ="update chietkhaudathang set loaick = '" + this.loaick + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "', tungay = '" + this.tungay + "', denngay = '" + this.denngay + "', scheme = '" + this.scheme + "', diengiai = N'" + this.diengiai + "', diengiaimucvuot = N'" + this.diengiaimucvuot + "', mucvuot = '" + this.mucvuot.replaceAll(",", "") + "', ckmucvuot = '" + this.ckmucvuot.replaceAll(",", "") + "' where pk_seq = '" + this.id + "'";
			
			if (!this.db.update(command))
			{
				this.msg = "Khong the cap nhat chiet khau: " + command;
				this.db.getConnection().rollback();
				return false;
			}
			
			db.update("delete from CHIETKHAUDATHANG_NPP where chietkhaudathang_fk = '" + this.id + "'");
			
			if(this.nppIds.length() > 0)
			{
				String[] npp = this.nppIds.split(",");
				for(int i = 0; i < npp.length; i++)
				{
					String query = "insert CHIETKHAUDATHANG_NPP(chietkhaudathang_fk, npp_fk) values('" + this.id + "','" + npp[i] + "')";
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi chietkhaudathang_npp: " + query;		
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			
			String query = "delete chietkhaudathang_sp where chietkhaudathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat chietkhaudathang_sp: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spIds.length() > 0)
			{
				String[] sp = this.spIds.split(",");
				for(int i = 0; i < sp.length; i++)
				{
					query = "select * from chietkhaudathang a, chietkhaudathang_sp b where a.pk_seq = b.chietkhaudathang_fk  and b.SP_FK = '" + sp[i] + "' and (('" + this.tungay + "' between TuNgay and DenNgay) or ('" + this.denngay + "' between TuNgay and DenNgay))";
					ResultSet kt = db.get(query);
					System.out.println("kiem tra " + query);
					if(!kt.next())
					{
						query = "insert CHIETKHAUDATHANG_SP(chietkhaudathang_fk, sp_fk) values('" + this.id + "','" + sp[i] + "')";
						System.out.println("tao moi " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi chietkhaudathang_sp: " + query;		
							this.db.getConnection().rollback();
							return false;
						}
					}
					else
					{
						this.msg = "Sản phẩm đã có mức chiết khấu trong khoảng thời gian bạn đang chọn.";		
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			
			query = "delete chietkhaudathang_tieuchi where chietkhaudathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat chietkhaudathang_tieuchi: " + query;
				db.getConnection().rollback();
				return false;
			}
			if(this.tumuc != null)
			{
				String sql = "";
				for(int i = 0; i < this.tumuc.length; i++)
				{
					System.out.println("tumuc = " + tumuc[i]);
					if(this.tumuc[i+1].trim().length() > 0 && this.denmuc[i+1].trim().length() > 0)
					{
						float a, b, c , d;
						a = Float.parseFloat(this.tumuc[i].replaceAll(",", ""));
						b = Float.parseFloat(this.denmuc[i].replaceAll(",", "")); 
						c = Float.parseFloat(this.tumuc[i+1].replaceAll(",", "")); 
						d = Float.parseFloat(this.denmuc[i+1].replaceAll(",", ""));
						System.out.println("a = " + a + " b = " + b + " c = " + c + " d = " + d);
						if((a >= b) || (c <= b) || (c >= d))
						{
							this.msg = "Mức chiết khấu bị trùng lắp.";
							db.getConnection().rollback();
							return false;
						}
					}
					else break;
				}
				for(int i = 0; i < this.tumuc.length; i++)
				{
					//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
					if(this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.chietkhauDh[i].trim().length() > 0)
					{
						/*String ck = this.thuongSR[i].replaceAll(",", "");
						if(this.thuongTDSR[i].trim().equals("2"))
							ck = "null";*/
						
						sql += "select N'" + this.ghichu[i].replaceAll(",", "") + "' as diengiai, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
								" '" + this.chietkhauDh[i].replaceAll(",", "") + "' as chietkhau union ";
						System.out.println("cap nhat " + sql);
					}
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert Chietkhaudathang_TIEUCHI(chietkhaudathang_fk, ghichu, tumuc, toimuc, chietkhau) " +
							"select " + this.id + ", diengiai, tumuc, denmuc, chietkhau from (" + sql + ") Chietkhaudathang_TIEUCHI ";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "2.Khong the tao moi Chietkhaudathang_TIEUCHI: " + query;
						db.getConnection().rollback();
						return false;
					}
				}	
				else
				{
					this.msg = "2.Khong the tao moi Chietkhaudathang_TIEUCHI: " + query;
					db.getConnection().rollback();
					return false;
				}
			}			
			
			this.db.getConnection().setAutoCommit(true);
			this.db.getConnection().commit();
		}
		catch (Exception e) {}

		return true; 
	}

	private void init()
	{	
		String query = "select a.pk_seq as id, a.scheme, a.loaick, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, a.tungay, a.denngay, isnull(a.diengiai, 'na') as diengiai from chietkhaudathang a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = '"+ this.id + "'"; 
        ResultSet rs =  this.db.get(query);
        try
        {
                 
            NumberFormat format = new DecimalFormat("#,###,###");
            while(rs.next())
			{
	        	this.id = rs.getString("id");
	        	this.scheme = rs.getString("scheme");
	        	this.loaick = rs.getString("loaick");
	        	this.ngaytao = rs.getString("ngaytao");
	        	this.nguoitao = rs.getString("nguoitao");
	        	this.ngaysua = rs.getString("ngaysua");
	        	this.nguoisua = rs.getString("nguoisua");
	        	this.tungay = rs.getString("tungay");
	        	this.denngay = rs.getString("denngay");
	        	this.diengiai = rs.getString("diengiai");
	        	this.mucvuot = rs.getString("mucvuot") != null ? format.format(rs.getDouble("mucvuot")) : "";
				this.ckmucvuot = rs.getString("ckmucVuot") != null ? format.format(rs.getDouble("chietkhauMucVuot")) : "";
			}
			rs.close();
			
//			query = "select chietkhaubanhang_fk,sp_fk,chietkhau " +
//					" from Chietkhaubanhang_sp where chietkhaubanhang_fk = '" + this.id + "' ";
//			rs = db.get(query);
//			if(rs != null)
//			{
//				this.chitieu = new Hashtable<String, String>();
//				
//				while(rs.next())
//				{
//					this.chitieu.put(rs.getString("sp_fk"), format.format(rs.getDouble("chietkhau")));
//				}
//				rs.close();
//			}	
       	}
        catch (java.sql.SQLException e){}
        
        this.createCkdh();
        this.createRs();
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	

	public void DBClose(){
		if (this.db != null) 
			this.db.shutDown();
	}

	public ResultSet getNppRs() 
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}
	
	public ResultSet getSpRs() 
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs) 
	{
		this.spRs = spRs;
	}

	public String getNppIds() 
	{
		return this.nppIds;
	}

	public void setNppIds(String nppIds) 
	{
		this.nppIds = nppIds;
	}
	
	public String getSpIds() 
	{
		return this.spIds;
	}

	public void setSpIds(String spIds) 
	{
		this.spIds = spIds;
	}
	
	public void createCkdh() 
	{
		String query = "select diengiaimucvuot, mucvuot, ckmucvuot " + "from chietkhaudathang " + "where pk_seq = '" + this.id + "'  ";
	
	System.out.println("___Khoi tao tieu chi: " + query);
	ResultSet rs = db.get(query);
	if(rs != null)
	{
		NumberFormat format = new DecimalFormat("##,###,###");
		NumberFormat format2 = new DecimalFormat("##,###,###.##");
		try 
		{
			String diengiaimuc = "";
			String mucvuot = "";
			String ckmucvuot = "";			
			
			while(rs.next())
			{
				diengiaimuc += rs.getString("diengiaimucvuot");
				mucvuot += format.format(rs.getDouble("mucvuot"));
				ckmucvuot += format.format(rs.getDouble("ckmucvuot"));
			}
			rs.close();
			
			if(mucvuot.trim().length() > 0)
			{
				this.diengiaimucvuot = diengiaimuc;
				
				this.mucvuot = mucvuot;
				
				this.ckmucvuot = ckmucvuot;
				
			}
		} 
		catch (Exception e) {
			
			System.out.println("Loi khoi tao: " + e.toString());
		}
	}
		
		query = "select ghichu, tumuc, toimuc, chietkhau " + "from chietkhaudathang_tieuchi " + "where chietkhaudathang_fk = '" + this.id + "'  ";
		
		System.out.println("___Khoi tao tieu chi: " + query);
		rs = db.get(query);
		if(rs != null)
		{
			NumberFormat format = new DecimalFormat("##,###,###");
			NumberFormat format2 = new DecimalFormat("##,###,###.##");
			try 
			{
				String tieu_chi = "";
				String tu_muc = "";
				String den_muc = "";
				String chiet_khau = "";
				
				
				while(rs.next())
				{
					tieu_chi += rs.getString("ghichu") + ",,";
					tu_muc += format.format(rs.getDouble("tumuc")) + ",,";
					den_muc += format.format(rs.getDouble("toimuc")) + ",,";
					chiet_khau += format2.format(rs.getDouble("chietkhau")) + ",,";
				}
				rs.close();
				
				if(tieu_chi.trim().length() > 0)
				{
					tieu_chi = tieu_chi.substring(0, tieu_chi.length() - 2);
					this.ghichu = tieu_chi.split(",,");
					
					tu_muc = tu_muc.substring(0, tu_muc.length() - 2);
					this.tumuc = tu_muc.split(",,");
					
					den_muc = den_muc.substring(0, den_muc.length() - 2);
					this.denmuc = den_muc.split(",,");
					
					chiet_khau = chiet_khau.substring(0, chiet_khau.length() - 2);
					this.chietkhauDh = chiet_khau.split(",,");
					
				}
			} 
			catch (Exception e) {
				
				System.out.println("Loi khoi tao: " + e.toString());
			}
		}
		
		query = "select ChietKhauDatHang_FK, sp_fk from chietkhaudathang_sp where ChietKhauDatHang_FK = '" + this.id + "' ";
		System.out.println("___KHOI TAO SP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
//			String spId = "";
//			String spChietkhau = "";
//			NumberFormat format2 = new DecimalFormat("##,###,###.##");
			try
			{
				String spId = "";
				while(rs.next())
				{
					spId += rs.getString("sp_fk") + ",";
				}
				rs.close();
				
				if(spId.trim().length() > 0)
					this.spIds = spId.substring(0, spId.length() - 1);
			}catch (Exception e){
				System.out.println("32.Loi khoi tao: " + e.toString());
			}
		}
		//System.out.println("__SP ID: " + this.spIds);
		
		
		
		query = "select npp_fk from ChietKhauDatHang_npp where ChietKhauDatHang_FK = '" + this.id + "' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
			}
		}
		
		System.out.println("__NPP ID: " + this.nppIds);
		
	}

	public void createRs() 
	{
		this.vungRs = db.get("select pk_seq, ten from VUNG where trangthai = '1'");
		
		String query = "select pk_seq, ten from KHUVUC where trangthai = '1'";
		if(this.vungIds.trim().length() > 0)
			query += " and vung_fk in ( " + this.vungIds + " ) "; 
		this.kvRs = db.get(query);
		
		query = "select PK_SEQ, MA, TEN  from NhaPhanPhoi where trangthai = '1' and PRIANDSECOND = '0' ";
		if(this.kvIds.trim().length() > 0)
			query += " and khuvuc_fk in ( " +  this.kvIds + " ) ";
		if(this.vungIds.trim().length() > 0)
			query += " and khuvuc_fk in ( select pk_seq from KhuVuc where trangthai = '1' and vung_fk in ( " + this.vungIds + " ) ) ";
		
		if(this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from chietkhaudathang_NPP where chietkhaudathang_fk = '" + this.id + "' ) ";
		}
		
		//
		if(this.nppIds.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds + " ) ";
		}
		
		query += " order by PK_SEQ desc ";
		this.nppRs = db.get(query);
		
		Utility Ult = new Utility();
		
		query = "select a.pk_seq, a.ten " +
	    		" from nhomsanpham a, nhanvien b, nhanvien c" +
	    		" where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='1' and a.pk_seq in (select nsp_fk from NHOMSANPHAM_SANPHAM where sp_fk in "+ Ult.quyen_sanpham(userId)+")";
		this.nhomspRs = db.get(query);
		
		query = "select a.pk_seq, a.ten from nganhhang a " +
				"inner join  nhanvien b on b.pk_seq=a.nguoitao " +
				"inner join  nhanvien c on a.nguoisua = c.pk_seq where 1=1";
		this.nganhhangRs = db.get(query);
		
		
		query = "select a.pk_seq, a.ten from nhanhang a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk = b.pk_seq and a.nguoitao = c.PK_SEQ and a.nguoisua = d.PK_SEQ ";
		if(nganhhangIds.trim().length() > 0)
			query += " and nganhhang_fk = '" + this.nganhhangIds + "'";
		this.nhanhangRs = db.get(query);

		query = "select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK  from SanPham where trangthai = '1' ";
		if(nganhhangIds.trim().length() > 0)
			query += " and nganhhang_fk = '" + this.nganhhangIds + "'";
		if(nhanhangIds.trim().length() > 0)
			query += " and nhanhang_fk = '" + this.nhanhangIds + "'";
		if(nhomspIds.trim().length() > 0)
			query += " and a.pk_seq in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + this.nhomspIds + "'";
		this.spRs = db.get(query);
		
	}

	public String getLoaiCK() {
		return this.loaick;
	}

	public void setLoaiCK(String loaick) {
		this.loaick = loaick;
	}
	
	public String getDiengiai() {
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}
	
	public String getDiengiaiMucvuot() {
		return this.diengiaimucvuot;
	}

	public void setDiengiaiMucvuot(String diengiaimucvuot) {
		this.diengiaimucvuot = diengiaimucvuot;
	}
	
	public String getCKMucvuot() {
		return this.ckmucvuot;
	}

	public void setCKMucvuot(String ckmucvuot) {
		this.ckmucvuot = ckmucvuot;
	}
	
	public String[] getTumuc() {
		
		return this.tumuc;
	}

	
	public void setTumuc(String[] tumuc) {
		
		this.tumuc = tumuc;
	}

	
	public String[] getDenmuc() {
		
		return this.denmuc;
	}

	
	public void setDenmuc(String[] denmuc) {
		
		this.denmuc = denmuc;
	}

	public String[] getChietkhauDh() {
		
		return this.chietkhauDh;
	}

	public void setChietkhauDh(String[] chietkhauDh) {
		this.chietkhauDh = chietkhauDh;
		
	}
	
	public String[] getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String[] ghichu) {
		this.ghichu = ghichu;
		
	}
	
	public String[] getChietkhauSP() {
		
		return this.chietkhauSp;
	}

	public void setChietkhauSP(String[] chietkhauSp) {
		this.chietkhauSp = chietkhauSp;
		
	}
	
	public ResultSet getKvRs()
	{
		return this.kvRs;
	}
	
	public void setKvRs(ResultSet kvRs)
	{
		this.kvRs = kvRs;
	}
	
	public String getKvIds()
	{
		return this.kvIds;
	}
	
	public void setKvIds(String kvIds)
	{
		this.kvIds = kvIds;
	}
	
	public ResultSet getVungRs()
	{
		return this.vungRs;
	}
	
	public void setVungRs(ResultSet vungRs)
	{
		this.vungRs = vungRs;
	}
	
	public String getVungIds()
	{
		return this.vungIds;
	}
	
	public void setVungIds(String vungIds)
	{
		this.vungIds = vungIds;
	}
	public Hashtable<String, String> getChitieu() {
		return chitieu;
	}

	public void setChitieu(Hashtable<String, String> chitieu) {
		this.chitieu = chitieu;
	}
	Hashtable<String, String> chitieu= new Hashtable<String, String>();

	@Override
	public ResultSet getNganhhangRs() {
		return this.nganhhangRs;
	}

	@Override
	public void setNganhhangRs(ResultSet nganhhangRs) {
		this.nganhhangRs = nganhhangRs;
		
	}

	@Override
	public String getNganhhangIds() {
		return this.nganhhangIds;
	}

	@Override
	public void setNganhhangIds(String nganhhangIds) {
		this.nganhhangIds = nganhhangIds;
		
	}

	@Override
	public ResultSet getNhanhangRs() {
		return this.nhanhangRs;
	}

	@Override
	public void setNhanhangRs(ResultSet nhanhangRs) {
		this.nhanhangRs = nhanhangRs;
	}

	@Override
	public String getNhanhangIds() {
		return this.nhanhangIds;
	}

	@Override
	public void setNhanhangIds(String nhanhangIds) {
		this.nhanhangIds = nhanhangIds;
		
	}

	@Override
	public ResultSet getNhomspRs() {
		return this.nhomspRs;
	}

	@Override
	public void setNhomspRs(ResultSet nhomspRs) {
		this.nhomspRs = nhomspRs;
		
	}

	@Override
	public String getNhomspIds() {
		return this.nhomspIds;
	}

	@Override
	public void setNhomspIds(String nhomspIds) {
		this.nhomspIds = nhomspIds;
		
	}

	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
		
	}

		
}
