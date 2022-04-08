package geso.dms.center.beans.themnppctkm.imp;

import geso.dms.center.beans.themnppctkm.IThemNppCtkm;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ThemNppCtkm implements  IThemNppCtkm
{
	String userId;
	String id;

	String trangthai;
	String ghichu;

	String msg;

	dbutils db;

	public ThemNppCtkm()
	{
		this.userId = "";
		this.id = "";

		this.trangthai = "0";
		this.ghichu = "";

		this.msg = "";

		this.db = new dbutils();
	}

	
	public ThemNppCtkm(String id)
	{
		this.userId = "";
		this.id = id;

		this.trangthai = "0";
		this.ghichu = "";

		this.msg = "";

		this.db = new dbutils();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String Id)
	{
		this.id = Id;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getGhichu()
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public void init()
	{
		String query = "select trangthai,ghichu,npp_fk from themnppCtkm where pk_Seq='"+this.id+"' ";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				while(rs.next())
				{
					this.trangthai = rs.getString("trangthai");
					this.ghichu = rs.getString("ghichu");
					this.nppId = rs.getString("npp_fk");

				}
				rs.close();
			
				
				String _ctkmId = "";
				String _scheme = "";
				String _diengiai = "";
				String _loaingansach = "";
				String _ngansach = "";
				
				query=" select ctkm_fk,scheme,diengiai,loaingansach,ngansach from Themnppctkm_npp where themnppctkm_fk='"+this.id+"' ";
			
				 rs = db.get(query);
				while(rs.next())
				{
					_ctkmId += rs.getString("ctkm_fk") + "__";
					_scheme += rs.getString("scheme") + "__";
					_diengiai += rs.getString("diengiai") + "__";
					_loaingansach += rs.getString("loaingansach") + "__";
					_ngansach += rs.getString("ngansach") + "__";									
				}
				rs.close();
				
				if(_ctkmId.trim().length() > 0)
				{
					_ctkmId = _ctkmId.substring(0, _ctkmId.length() -2 );
					this.ctkmId = _ctkmId.split("__");
					
					_scheme = _scheme.substring(0, _scheme.length() -2 );
					this.scheme = _scheme.split("__");
					
					_diengiai = _diengiai.substring(0, _diengiai.length() -2 );
					this.diengiai = _diengiai.split("__");
					
					_loaingansach = _loaingansach.substring(0, _loaingansach.length() -2 );
					this.loaingansach = _loaingansach.split("__");
					
					_ngansach = _ngansach.substring(0, _ngansach.length() -2 );
					this.ngansach = _ngansach.split("__");
					
					
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("__Exception Init: " + e.getMessage());
			}
		}

		this.createRs();
	}

	public void createRs()
	{
		String query=
				"SELECT A.PK_SEQ AS NPPID, A.TEN AS NPPTEN,A.MA AS NPPMA, B.TEN AS KVTEN "+ 
				"FROM  "+
				" NHAPHANPHOI A INNER JOIN KHUVUC B ON A.KHUVUC_FK = B.PK_SEQ "+ 
				"WHERE A.TRANGTHAI = '1' ";
		/*	if(this.kvId.length()>0)
			{
				query += " and A.khuvuc_fk ='"+this.kvId+"' ";
			}*/
			query+= "order by b.ten,a.ten asc ";
			this.nppRs=this.db.get(query);
	}

	public void DbClose()
	{
		try
		{
			this.db.shutDown();
		}
		catch (Exception e) {}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@Override
	public boolean save()
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			
			if(this.nppId.length()<=0)
			{
				this.msg = "Vui lòng chọn nhà phân phối !";
				db.getConnection().rollback();
				return false;
			}
			
/*			String query =
					
				"	INSERT INTO ThemNppCtkm(TRANGTHAI,GHICHU,Npp_fk,NGAYTAO,NGAYSUA,NGUOISUA,NGUOITAO)  "+ 
				" values('"+this.trangthai+"',N'"+this.ghichu+"','"+this.nppId+"','"+getDateTime()+"','"+this.getDateTime()+"','"+this.userId+"','"+this.userId+"' )";;
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ThemNppCtkm " + query;
					db.getConnection().rollback();
					return false;
				}
				
				ResultSet rsDDH = db.get("select IDENT_CURRENT('ThemNppCtkm') as ID ");
				if(rsDDH.next())
				{
					this.id = rsDDH.getString("ID");
				}
				rsDDH.close();*/
				
				String query = "INSERT INTO THEMNPPCTKM(TRANGTHAI,GHICHU,Npp_fk,NGAYTAO,NGAYSUA,NGUOISUA,NGUOITAO) select ?,?,?,?,?,?,?";

				Object[] data = {this.trangthai,this.ghichu,this.nppId,getDateTime(),this.getDateTime(),this.userId,this.userId};
				dbutils.viewQuery(query,data);
				if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
					this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
					this.db.getConnection().rollback();
					return false;
				}
				this.id = db.getPk_seq();
				
				System.out.println("DDH ID: " + this.id);
				int kt = 0;
				for(int i = 0; i < ctkmId.length; i++)
				{
					if(ctkmId[i].trim().length() > 0)
					{
						kt++;
						System.out.println(":::_NganSach"+ngansach[i]+"::"+loaingansach[i]);
						if(loaingansach[i].equals("1")&& ngansach[i].trim().length()<=0 )
						{
							this.msg ="Vui lòng nhập ngân sách !";
							db.getConnection().rollback();
							return false;
						}
						
			/*			query = 
								"insert into ThemNppCtkm_npp(ThemNppCtkm_fk,scheme,DienGiai,ctkm_fk,npp_fk,ngansach,loaingansach) " +
										"select	'"+this.id+"','"+scheme[i]+"',N'"+diengiai[i]+"','"+ctkmId[i]+"','"+this.nppId+"','"+ngansach[i].replaceAll(",", "")+"','"+loaingansach[i]+"'" +
										"  where '"+this.nppId+"' not in (select npp_fk from phanbokhuyenmai where npp_fk='"+this.nppId+"'  and ctkm_fk='"+ctkmId[i]+"') " ;
						System.out.println("________-"+query);
						if(db.updateReturnInt(query) <= 0)
						{
							System.out.println("::::"+query);
							this.msg = "Nhà phân phối đã được phân bổ CTKM "+scheme[i] +",bạn không thể thêm !";
							db.getConnection().rollback();
							return false;
						}*/
						
						
						String temp56 = ngansach[i].replaceAll(",","");

						query = "INSERT INTO THEMNPPCTKM_NPP(ThemNppCtkm_fk,scheme,DienGiai,ctkm_fk,npp_fk,ngansach,loaingansach) select ?,?,?,?,?,?,?"+
								"  where ? not in (select npp_fk from phanbokhuyenmai where npp_fk=?  and ctkm_fk=?) " ;

						Object[] data1 = {this.id,scheme[i],diengiai[i],ctkmId[i],this.nppId,temp56,loaingansach[i],this.nppId,this.nppId,ctkmId[i]};
						dbutils.viewQuery(query,data1);
						if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
							this.msg = "Nhà phân phối đã được phân bổ CTKM "+scheme[i] +",bạn không thể thêm !";
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
				if(kt <= 0)
				{
					this.msg = "Chưa có chương trình khuyến mãi nào được chọn. Vui lòng chọn chương trình khuyến mãi !";
					db.getConnection().rollback();
					return false;
				}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		return true;
  	}

	@Override
	public boolean edit()
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			if(this.nppId.length()<=0)
			{
				this.msg = "Vui lòng chọn nhà phân phối !";
				db.getConnection().rollback();
				return false;
			}
			
	/*		String query =
					
				"	UPDATE  ThemNppCTKM SET npp_fk='"+this.nppId+"',GHICHU=N'"+this.ghichu+"',NGAYSUA='"+this.getDateTime()+"',NGUOISUA='"+this.userId+"' " +
					" where pk_Seq='"+this.id+"' "; 
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới CHANHOADON " + query;
					db.getConnection().rollback();
					return false;
				}*/
				
				String query = "	UPDATE  ThemNppCTKM SET npp_fk=?,GHICHU=?,NGAYSUA=?,NGUOISUA=? " +
						" where pk_Seq=? "; 

				Object[] data = {this.nppId,this.ghichu,this.getDateTime(),this.userId,this.id};
				dbutils.viewQuery(query,data);
				if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
					this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
					this.db.getConnection().rollback();
					return false;
				}
				
				query="delete from ThemNppCtkm_npp where ThemNppCtkm_fk='"+this.id+"'";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ThemNppCtkm_fk " + query;
					db.getConnection().rollback();
					return false;
				}
				System.out.println("DDH ID: " + this.id);
				
				int kt = 0;
				for(int i = 0; i < ctkmId.length; i++)
				{
					if(ctkmId[i].trim().length() > 0)
					{
						kt++;
						System.out.println(":::_NganSach"+ngansach[i]+"::"+loaingansach[i]);
						if(loaingansach[i].equals("1")&&  ngansach[i].length()<=0 )
						{
							this.msg ="Vui lòng nhập ngân sách !";
							db.getConnection().rollback();
							return false;
						}
						
				/*		query = 
								"insert into ThemNppCtkm_npp(ThemNppCtkm_fk,scheme,DienGiai,ctkm_fk,npp_fk,ngansach,loaingansach) " +
										"select	'"+this.id+"','"+scheme[i]+"',N'"+diengiai[i]+"','"+ctkmId[i]+"','"+this.nppId+"','"+ngansach[i].replaceAll(",", "")+"','"+loaingansach[i]+"'" +
										"  where '"+this.nppId+"' not in (select npp_fk from phanbokhuyenmai where npp_fk='"+this.nppId+"'  and ctkm_fk='"+ctkmId[i]+"') " ;
						System.out.println("________-"+query);
						if(db.updateReturnInt(query) <= 0)
						{
							System.out.println("::::"+query);
							this.msg = "Nhà phân phối đã được phân bổ CTKM "+scheme[i] +",bạn không thể thêm !"+query;
							db.getConnection().rollback();
							return false;
						}*/
						
						String temp56 = ngansach[i].replaceAll(",","");

						query = "INSERT INTO THEMNPPCTKM_NPP(ThemNppCtkm_fk,scheme,DienGiai,ctkm_fk,npp_fk,ngansach,loaingansach) select ?,?,?,?,?,?,?"+
								"  where ? not in (select npp_fk from phanbokhuyenmai where npp_fk=?  and ctkm_fk=?) " ;

						Object[] data1 = {this.id,scheme[i],diengiai[i],ctkmId[i],this.nppId,temp56,loaingansach[i],this.nppId,this.nppId,ctkmId[i]};
						dbutils.viewQuery(query,data1);
						if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
							this.msg = "Nhà phân phối đã được phân bổ CTKM "+scheme[i] +",bạn không thể thêm !";
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
				
				if(kt <= 0)
				{
					this.msg = "Chưa có chương trình khuyến mãi nào được chọn. Vui lòng chọn chương trình khuyến mãi !";
					db.getConnection().rollback();
					return false;
				}
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		return true;
  	}
	
	

	public String[] getCtkmId()
	{
		return ctkmId;
  	}


	public void setCtkmId(String[] ctkmId)
	{
		this.ctkmId = ctkmId;
  	}


	public String[] getScheme()
	{
		return scheme;
  	}


	public void setScheme(String[] scheme)
	{
		this.scheme = scheme;
	}


	public String[] getNgansach()
	{
		return ngansach;
	}


	public void setNgansach(String[] ngansach)
	{
		this.ngansach = ngansach;
  	}


	public String getNppId()
	{
		return nppId;
	}


	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}


	public ResultSet getNppRs()
	{
		return nppRs;
	}


	public void setNppRs(ResultSet nppRs)
	{
		this.nppRs = nppRs;
	}

	String[] ctkmId,scheme,ngansach,loaingansach;
	
	public String[] getLoaingansach()
	{
		return loaingansach;
	}


	public void setLoaingansach(String[] loaingansach)
	{
		this.loaingansach = loaingansach;
	}


	public String[] getNganSach()
	{
		return ngansach;
	}


	public void setNganSach(String[] loaingansach)
	{
		this.ngansach = loaingansach;
	}

	String nppId;
	ResultSet nppRs;

	String[] diengiai;

	public String[] getDiengiai()
	{
		return diengiai;
	}

	public void setDiengiai(String[] diengiai)
	{
		this.diengiai = diengiai;
	}
}
