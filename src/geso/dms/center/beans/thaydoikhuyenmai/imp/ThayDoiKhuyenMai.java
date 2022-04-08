package geso.dms.center.beans.thaydoikhuyenmai.imp;

import geso.dms.center.beans.thaydoikhuyenmai.ISanPham;
import geso.dms.center.beans.thaydoikhuyenmai.IThayDoiKhuyenMai;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThayDoiKhuyenMai implements IThayDoiKhuyenMai
{
	String id, userId, trakmId, dkkmId, loai, hinhthuc, msg,ctkmId;

	

	ResultSet trakmRs, dkkmRs, ctkmRs;

	List<ISanPham> spList,spListOld;
	
	dbutils db;

	public	ThayDoiKhuyenMai()
	{
		this.id="";
		this.userId="";
		this.trakmId="";
		this.dkkmId="";
		this.loai="";
		this.hinhthuc="";
		this.type="";
		this.msg="";
		this.ctkmId="";
		spList= new ArrayList<ISanPham>();
		spListOld = new ArrayList<ISanPham>();
		db = new dbutils();
	}

	public ThayDoiKhuyenMai(String id)
	{
		super();
		this.id = id;
		this.userId="";
		this.trakmId="";
		this.dkkmId="";
		this.loai="";
		this.hinhthuc="";
		this.type="";
		this.msg="";
		this.ctkmId="";
		spList= new ArrayList<ISanPham>();
		spListOld = new ArrayList<ISanPham>();
		db = new dbutils();
	}

	public ThayDoiKhuyenMai(String id, String userId, String trakmId, String dkkmId, String loai, String hinhthuc, String msg, List<ISanPham> spList)
	{
		super();
		this.id = id;
		this.userId = userId;
		this.trakmId = trakmId;
		this.dkkmId = dkkmId;
		this.loai = loai;
		this.hinhthuc = hinhthuc;
		this.msg = msg;
		this.spList = spList;
		db = new dbutils();
	}

	public ResultSet getTrakmRs()
	{
		return trakmRs;
	}

	public void setTrakmRs(ResultSet trakmRs)
	{
		this.trakmRs = trakmRs;
	}

	public ResultSet getDkkmRs()
	{
		return dkkmRs;
	}

	public void setDkkmRs(ResultSet dkkmRs)
	{
		this.dkkmRs = dkkmRs;
	}

	public ResultSet getCtkmRs()
	{
		return ctkmRs;
	}

	public void setCtkmRs(ResultSet ctkmRs)
	{
		this.ctkmRs = ctkmRs;
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

	public String getTrakmId()
	{
		return trakmId;
	}

	public void setTrakmId(String trakmId)
	{
		this.trakmId = trakmId;
	}

	public String getDkkmId()
	{
		return dkkmId;
	}

	public void setDkkmId(String dkkmId)
	{
		this.dkkmId = dkkmId;
	}

	public String getLoai()
	{
		return loai;
	}

	public void setLoai(String loai)
	{
		this.loai = loai;
	}
	
	String type;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getHinhthuc()
	{
		return hinhthuc;
	}

	public void setHinhthuc(String hinhthuc)
	{
		this.hinhthuc = hinhthuc;
	}

	public boolean save()
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			
/*			String query=
				" insert into ThayDoiKM(Loai,TraKM_FK,DKKM_FK,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI,CTKM_FK) "+
				" SELECT '"+this.loai+"',"+(this.trakmId.length()<=0?"null":this.trakmId)+","+(this.dkkmId.length()<=0?"null":this.dkkmId)+",'"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"',0,"+(this.ctkmId.trim().length()<=0?"null":this.ctkmId)+" ";
			System.out.println("[ThayDoiKm]"+query);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			query = "select IDENT_CURRENT('ThayDoiKM') as trakmId";
			ResultSet rsTrakm = this.db.get(query);						
			rsTrakm.next();
			this.id = rsTrakm.getString("trakmId");
			rsTrakm.close();
			System.out.println("[ThayDoiKm]"+query);*/
			
			String query = "INSERT INTO THAYDOIKM(Loai,TraKM_FK,DKKM_FK,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI,CTKM_FK) select ?,?,?,?,?,?,?,0,?";

			Object[] data = {this.loai,(this.trakmId.length()<=0?null:this.trakmId),(this.dkkmId.length()<=0?null:this.dkkmId),this.getDateTime(),this.userId,this.getDateTime(),this.userId,(this.ctkmId.trim().length()<=0?null:this.ctkmId)};
			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
				this.db.getConnection().rollback();
				return false;
			}
			this.id = db.getPk_seq();
			
			int soSp=this.spList.size();
			System.out.println("[ThayDoiKm]"+this.spList.size());
			
			if(soSp<=0)
			{
				this.msg="Vui lòng nhập sản phẩm !";
				this.db.getConnection().rollback();
				return false;
			}
			
			if(soSp>0)
			{
				for(int i=0;i<soSp;i++)
				{
					ISanPham sp = this.spList.get(i);
					String soluong = "null";
					
					System.out.print("[Type Save]"+this.type);
					
					if(this.type.trim().equals("1"))
                  	{ 	
						soluong=sp.getSoluong();
						if(soluong.trim().length() <= 0)
						{
							db.getConnection().rollback();
							this.msg = "Vui lòng nhập số lượng của sản phẩm: " + sp.getMa();
							return false;
						}
             		}
	/*				query="INSERT INTO  ThayDoiKM_SanPham(ThayDoiKM_FK,SanPham_FK,SoLuong) " +
						  "select '"+this.id+"',pk_seq,"+soluong+" " +
						  "from sanpham " +
						  "  where ma='"+sp.getMa()+"' ";
					
					System.out.println("[ThayDoiKm]"+query);
					if(!this.db.update(query))
					{
						this.db.getConnection().rollback();
						this.msg="Lỗi hệ thống "+query;
						return false;
					}*/
					
					query = "INSERT INTO  THAYDOIKM_SANPHAM(ThayDoiKM_FK,SanPham_FK,SoLuong) select ?,pk_seq,?"+
								" from sanpham " +
								"  where ma=? ";

					Object[] data1 = {this.id,soluong,sp.getMa()};
					dbutils.viewQuery(query,data1);
					if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
						this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			String logKm="";
			Object[] data2 = null;
			if(this.trakmId.length()>0&&!this.trakmId.equals("null")) {
//				logKm=" SELECT '"+this.id+"',CTKHUYENMAI_FK FROM CTKM_TRAKM  WHERE TRAKHUYENMAI_FK='"+this.trakmId+"' ";
				
				logKm=" SELECT '"+this.id+"',CTKHUYENMAI_FK FROM CTKM_TRAKM  WHERE TRAKHUYENMAI_FK=? ";
				data2 = new Object[] {this.trakmId};
			} else if(this.dkkmId.length()>0&&!this.dkkmId.equals("null")) {
//				logKm=" SELECT '"+this.id+"',CTKHUYENMAI_FK FROM CTKM_DKKM WHERE DKKHUYENMAI_FK='"+this.dkkmId+"' ";
				
				logKm=" SELECT '"+this.id+"',CTKHUYENMAI_FK FROM CTKM_DKKM WHERE DKKHUYENMAI_FK=? ";
				data2 = new Object[] {this.dkkmId};
			}
			
			query=
			"INSERT INTO THAYDOIKM_CTKM(THAYDOIKM_FK,CTKM_FK) "+logKm;
	/*		System.out.println("[ThayDoiKm]"+query);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Lỗi hệ thống "+query;
				return false;
			}*/
			
			dbutils.viewQuery(query,data2);
			if (this.db.updateQueryByPreparedStatement(query,data2) != 1) {
				this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
			try
			{
				this.db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			this.msg="Lỗi hệ thống Exception "+e.getMessage();
			return false;
		}
		
		return true;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	public boolean edit()
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
	/*		String query=" update ThayDoiKM set Loai='"+this.loai+"',TraKM_FK="+(this.trakmId.length()<=0?"null":this.trakmId)+",DKKM_FK="+(this.dkkmId.length()<=0?"null":this.dkkmId)+",NGAYSUA='"+getDateTime()+"',NGUOISUA='"+this.userId+"',CTKM_FK="+(this.ctkmId.trim().length()<=0?"null":this.ctkmId)+" WHERE PK_SEQ='"+this.id+"' ";
			System.out.println("[ThayDoiKm]"+query);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Lỗi hệ thống "+query;
				return false;
			}*/
			
			String query=" update ThayDoiKM set Loai=?,TraKM_FK=?,"
					+ "DKKM_FK=?,NGAYSUA=?,NGUOISUA=?,"
							+ "CTKM_FK=? WHERE PK_SEQ=? ";

			Object[] data = {this.loai,(this.trakmId.length()<=0?null:this.trakmId),(this.dkkmId.length()<=0?null:this.dkkmId),
					getDateTime(),this.userId,(this.ctkmId.trim().length()<=0?null:this.ctkmId),this.id};
			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
				this.db.getConnection().rollback();
				return false;
			}
			
			query="delete from ThayDoiKM_SanPham where ThayDoiKM_FK='"+this.id+"'";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			query="delete from THAYDOIKM_CTKM where THAYDOIKM_FK='"+this.id+"' ";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			
			int soSp=this.spList.size();
			System.out.println("[ThayDoiKm]"+this.spList.size());
			if(soSp<=0)
			{
				this.msg="Vui lòng nhập sản phẩm !";
				this.db.getConnection().rollback();
				return false;
			}
			
			if(soSp>0)
			{
				for(int i=0;i<soSp;i++)
				{
					ISanPham sp = this.spList.get(i);
					String soluong = "null";
			
					System.out.print("[Type Save]"+this.type);
					
					if(this.type.trim().equals("1"))
                  	{ 	
						soluong=sp.getSoluong();
						if(soluong.trim().length() <= 0)
						{
							db.getConnection().rollback();
							this.msg = "Vui lòng nhập số lượng của sản phẩm: " + sp.getMa();
							return false;
						}
             		}
		/*			query="INSERT INTO  ThayDoiKM_SanPham(ThayDoiKM_FK,SanPham_FK,SoLuong) " +
						  "select '"+this.id+"',pk_seq,"+soluong+" " +
						  "from sanpham " +
						  "  where ma='"+sp.getMa()+"' ";
					
					System.out.println("[ThayDoiKm]"+query);
					if(!this.db.update(query))
					{
						this.db.getConnection().rollback();
						this.msg="Lỗi hệ thống "+query;
						return false;
					}*/
					
					query="INSERT INTO  ThayDoiKM_SanPham(ThayDoiKM_FK,SanPham_FK,SoLuong) " +
							  "select ?,pk_seq,? " +
							  "from sanpham " +
							  "  where ma=? ";

					Object[] data1 = {this.id,soluong,sp.getMa()};
					dbutils.viewQuery(query,data1);
					if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
						this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			String logKm="";
			Object[] data2 = null;
			if(this.trakmId.length()>0&&!this.trakmId.equals("null")) {
//				logKm=" SELECT '"+this.id+"',CTKHUYENMAI_FK FROM CTKM_TRAKM  WHERE TRAKHUYENMAI_FK='"+this.trakmId+"' ";
				
				logKm=" SELECT '"+this.id+"',CTKHUYENMAI_FK FROM CTKM_TRAKM  WHERE TRAKHUYENMAI_FK=? ";
				data2 = new Object[] {this.trakmId};
			} else if(this.dkkmId.length()>0&&!this.dkkmId.equals("null")) {
//				logKm=" SELECT '"+this.id+"',CTKHUYENMAI_FK FROM CTKM_DKKM WHERE DKKHUYENMAI_FK='"+this.dkkmId+"' ";
				
				logKm=" SELECT '"+this.id+"',CTKHUYENMAI_FK FROM CTKM_DKKM WHERE DKKHUYENMAI_FK=? ";
				data2 = new Object[] {this.dkkmId};
			}
			
			query=
			"INSERT INTO THAYDOIKM_CTKM(THAYDOIKM_FK,CTKM_FK) "+logKm;
	/*		System.out.println("[ThayDoiKm]"+query);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Lỗi hệ thống "+query;
				return false;
			}*/
			
			dbutils.viewQuery(query,data2);
			if (this.db.updateQueryByPreparedStatement(query,data2) != 1) {
				this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
			try
			{
				this.db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			this.msg="Lỗi hệ thống Exception "+e.getMessage();
			return false;
		}
		
		return true;
	}

	public void createRs()
	{
		
		String query=	
		"select PK_SEQ,SCHEME,SCHEME+' - ' + DIENGIAI as DienGiai from CTKHUYENMAI km where DenNgay>='"+getDateTime()+"' ";
		if(this.ctkmId.length()>0)
			query+=" union select PK_SEQ,SCHEME,SCHEME+' - ' + DIENGIAI as DienGiai from CTKHUYENMAI km where pk_seq='"+this.ctkmId+"' ";
		this.ctkmRs=this.db.get(query);
		
		
		if(this.loai.equals("1"))
		{
			 query="select PK_SEQ, Cast(pk_Seq as varchar(10) ) +' - ' + DIENGIAI as DienGiai,LOAI,TONGLUONG,TONGTIEN from DIEUKIENKHUYENMAI where exists (select * from ctkm_dkkm where DKKHUYENMAI_FK =pk_Seq)  " ;
			 query+=
			  " and pk_seq not in (select dkkm_fk from thaydoikm where trangthai=0   and DKKM_FK is not null " ;
			 if(this.id.length()>0 )
				 query+=" and pk_seq !='"+this.id+"' ";
			 query+=
			  ") ";
			 
			 System.out.println("[Km]"+query);
			 
			 if(this.ctkmId.length()>0)
				 query+=" and pk_seq in ( select dkkhuyenmai_fk from ctkm_dkkm where ctkhuyenmai_fk="+this.ctkmId+"  ) ";
			 this.dkkmRs= this.db.get(query);			
		}else if(this.loai.equals("2"))
		{
			query="select PK_SEQ,  Cast(pk_Seq as varchar(10) ) +' - ' + DIENGIAI as DienGiai,HINHTHUC,LOAI,TONGLUONG,TONGTIEN,CHIETKHAU  from TRAKHUYENMAI where exists (select * from CTKM_TRAKM  where TRAKHUYENMAI_FK=pk_seq) and LOAI=3 ";
			
			query+=
					  " and pk_seq not in (select trakm_fk from thaydoikm where trangthai=0  and trakm_fk is not null " ;
					 if(this.id.length()>0 )
						 query+=" and pk_seq !='"+this.id+"' ";
					 query+=
					  ") ";
			
			 if(this.ctkmId.length()>0)
				 query+=" and pk_seq in ( select trakhuyenmai_fk from ctkm_trakm where ctkhuyenmai_fk="+this.ctkmId+"  ) ";
			this.trakmRs =this.db.get(query);
		}
		if(this.trakmId.length()>0)
		{
			query=
			"select PK_SEQ,SCHEME,SCHEME+' - ' + DIENGIAI as DienGiai from CTKHUYENMAI km "+
			" where exists ( select  * from CTKM_TRAKM  where CTKHUYENMAI_FK=km.PK_SEQ and TRAKHUYENMAI_FK='"+this.trakmId+"' ) ";
			this.ctkmRsSuDung=this.db.get(query);
			
			query="select * from  traKhuyenmai where pk_Seq='"+this.trakmId+"'" ;
			ResultSet rs = db.get(query);
			try
			{
				while(rs.next())
				{
					this.hinhthuc =rs.getString("HINHTHUC");
					this.type =rs.getString("HINHTHUC");
					this.tongluong  = rs.getString("tongluong")==null?"":rs.getString("tongluong");
					this.tongtien  = rs.getString("tongtien")==null?"":rs.getString("tongtien");
					this.chietkhau =rs.getString("CHIETKHAU")==null?"":rs.getString("CHIETKHAU");
				}
				if(rs!=null)rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			query = "select a.soluong, a.dongia, b.pk_seq, b.MA, b.TEN from trakhuyenmai_sanpham a inner join SANPHAM b on a.SanPham_FK = b.PK_SEQ where a.trakhuyenmai_fk = '" + this.trakmId + "'";				
			rs = db.get(query);
			List<ISanPham> splist = new ArrayList<ISanPham>();
			if(rs != null)
			{
				try 
				{
					ISanPham sp = null;
					while(rs.next())
					{
						String spId = rs.getString("pk_seq");
						String ma = rs.getString("MA");
						String ten = rs.getString("TEN");
						String dongia = rs.getString("dongia");
						String soluong = "";
						if(rs.getString("soluong") != null)
							soluong = rs.getString("soluong");
						sp = new SanPham(spId, ma, ten, soluong, dongia);
						splist.add(sp);
					}
				} 
				catch (SQLException e) {}
			}
			this.spListOld=splist;
			
		}
		if(this.dkkmId.length()>0)
		{
			query=
			"select PK_SEQ,SCHEME,SCHEME+' - ' + DIENGIAI as DienGiai from CTKHUYENMAI km "+
			" where exists ( select  * from CTKM_DKKM  where CTKHUYENMAI_FK=km.PK_SEQ and DKKHUYENMAI_FK='"+this.dkkmId+"') ";
			this.ctkmRsSuDung=this.db.get(query);
			
			query="select * from  DIEUKIENKHUYENMAI where pk_Seq='"+this.dkkmId+"'" ;
			ResultSet rs = db.get(query);
			try
			{
				while(rs.next())
				{
					this.type = rs.getString("loai");
					this.tongluong  = rs.getString("tongluong")==null?"":rs.getString("tongluong");
					this.tongtien  = rs.getString("tongtien")==null?"":rs.getString("tongtien");
					
					 if(this.tongtien.length() > 0)
				        	this.hinhthuc = "2";
				        else
				        	this.hinhthuc = "1";
					
				}
				if(rs!=null)rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			query = "select a.soluong, b.pk_seq, b.MA, b.TEN, isnull(a.batbuoc, 0) as batbuoc " +
					"from dieukienkm_sanpham a inner join SANPHAM b on a.SanPham_FK = b.PK_SEQ " +
					"where a.dieukienkhuyenmai_fk = '" + this.dkkmId + "'";		
				rs = db.get(query);
				List<ISanPham> splist = new ArrayList<ISanPham>();
				if(rs != null)
				{
					try 
					{
						ISanPham sp = null;
						while(rs.next())
						{
							String spId = rs.getString("pk_seq");
							String ma = rs.getString("MA");
							String ten = rs.getString("TEN");
							String soluong = "";
							if(rs.getString("soluong") != null)
								soluong = rs.getString("soluong");
							
							sp = new SanPham(spId, ma, ten, soluong, "");
							sp.setBatbuoc(rs.getString("batbuoc"));
							splist.add(sp);
						}
						rs.close();
					} 
					catch(Exception e) { System.out.println("___EXCEPTION: " + e.getMessage()); }
				}
				this.spListOld = splist;
		}
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public List<ISanPham> getSpList()
	{
		return spList;
	}

	public void setSpList(List<ISanPham> spList)
	{
		this.spList = spList;
	}
	
	public void init()
	{
		String query="select pk_seq,loai,trakm_fk,dkkm_fk,ctkm_fk  from ThayDoiKM where pk_seq='"+this.id+"' ";
		ResultSet rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.loai=rs.getString("loai");
				this.trakmId= rs.getString("trakm_fk")==null?"":rs.getString("trakm_fk");
				this.dkkmId =rs.getString("dkkm_fk")==null?"":rs.getString("dkkm_fk");
				this.ctkmId =rs.getString("ctkm_fk")==null?"":rs.getString("ctkm_fk");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		query="select a.sanpham_fk ,b.MA as spMa,b.TEN as spTen,a.soluong,batbuoc from ThayDoiKM_SanPham a "+
			  "		inner join SANPHAM b on b.PK_SEQ=a.sanpham_fk "   +
			  "	where a.thaydoikm_fk='"+this.id+"' "	;
		rs = db.get(query);
		List<ISanPham> splist = new ArrayList<ISanPham>();
		if(rs != null)
		{
			try 
			{
				ISanPham sp = null;
				while(rs.next())
				{
					String spId = rs.getString("sanpham_fk");
					String ma = rs.getString("spMa");
					String ten = rs.getString("spTen");
					String soluong = "";
					if(rs.getString("soluong") != null)
						soluong = rs.getString("soluong");
					
					sp = new SanPham(spId, ma, ten, soluong, "");
					sp.setBatbuoc(rs.getString("batbuoc")==null?"":rs.getString("batbuoc"));
					splist.add(sp);
				}
				rs.close();
				this.spList=splist;
			} 
			catch(Exception e) {e.printStackTrace(); }
		}
		createRs();
	}
	
	public String getCtkmId()
	{
		return ctkmId;
	}

	public void setCtkmId(String ctkmId)
	{
		this.ctkmId = ctkmId;
	}
	
	
	String tongluong,tongtien,chietkhau;

	public String getTongluong()
	{
		return tongluong;
	}

	public void setTongluong(String tongluong)
	{
		this.tongluong = tongluong;
	}

	public String getTongtien()
	{
		return tongtien;
	}

	public void setTongtien(String tongtien)
	{
		this.tongtien = tongtien;
	}

	public String getChietkhau()
	{
		return chietkhau;
	}

	public void setChietkhau(String chietkhau)
	{
		this.chietkhau = chietkhau;
	}
	public List<ISanPham> getSpListOld()
	{
		return spListOld;
	}

	public void setSpListOld(List<ISanPham> spListOld)
	{
		this.spListOld = spListOld;
	}
	
	ResultSet ctkmRsSuDung;

	public ResultSet getCtkmRsSuDung() {
		return ctkmRsSuDung;
	}

	public void setCtkmRsSuDung(ResultSet ctkmRsSuDung) {
		this.ctkmRsSuDung = ctkmRsSuDung;
	}

	public void closeDB()
	{
		
			try
			{	if(this.trakmRs!=null)
				this.trakmRs.close();
			
				if(this.dkkmRs!=null)
					this.dkkmRs.close();
				
				if(this.ctkmRs!=null)
					this.ctkmRs.close();
			} catch (SQLException e)
			{
				if(this.db!=null)this.db.shutDown();
				e.printStackTrace();
			}
	}
}
