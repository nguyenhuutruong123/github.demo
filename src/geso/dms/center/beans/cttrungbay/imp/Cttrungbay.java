package geso.dms.center.beans.cttrungbay.imp;

import geso.dms.center.beans.cttrungbay.*;
import geso.dms.center.beans.cttrungbay.imp.Nhomsptrungbay;
import geso.dms.distributor.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Cttrungbay implements ICttrungbay
{
	String userId;
	String id;
	String scheme;
	String diengiai;
	
	String ngaytds;
	String ngaykttds;
	String ngaytb;
	String ngaykttb;
	String ngaydk;
	String ngayktdk;
	
	String type;
	String ngansach;
	String dasudung;
	String solantt;
	
	String nguoitao;
	String nguoisua;
	String ngaytao;
	String ngaysua;
	String msg;
	
	List<INhomsptrungbay> nsptbList = new ArrayList<INhomsptrungbay>();
	ResultSet tratbRs;
	String[] tratbIds;
	String[] pheptoans;
	
	//Search tratrungbay
	String ttb_diengiai;
	String ttb_tungay;
	String ttb_denngay;
	
	//Search nhomsptrungbay
	String nsptb_diengiai;
	String nsptb_tungay;
	String nsptb_denngay;
	
	String isDkthem;
	
	ResultSet kbhRs;
	ResultSet VungRs;
	ResultSet KhuvucRs;
	ResultSet NppRs;
	
	String kbhIds;
	String vungIds;
	String kvIds;
	String nppIds;
	String active;
	
	String mucphanbo;
	
	ResultSet loaiKhRs;
	String loaiKhIds;
	ResultSet vitriKhRs;
	String vitriKhIds;
	ResultSet hangKhRs;
	String hangKhIds;
	
	String phaimuadonhang;
	dbutils db;
	
	public Cttrungbay(String[] param)
	{
		this.id = param[0];
		this.scheme = param[1];
		this.diengiai = param[2];
		
		this.ngaytds = param[3];
		this.ngaykttds = param[4];
		this.ngaytb = param[5];
		this.ngaykttb = param[6];
		
		this.type = param[7];
		this.ngansach = param[8];
		this.dasudung = param[9];
		this.solantt = param[10];
		
		this.ngaytao = param[11];
		this.nguoitao = param[12];
		this.ngaysua = param[13];
		this.nguoisua = param[14];		
		this.msg = "";
		
		/*this.dangkyPDA = param[15];	*/
		
		//this.tratbId = "";
		this.ttb_diengiai = "";
		this.ttb_tungay = "";
		this.ttb_denngay = "";
		this.nsptb_diengiai = "";
		this.nsptb_tungay = "";
		this.nsptb_denngay = "";
		
		this.kbhIds = "";
		this.vungIds = "";
		this.kvIds = "";
		this.nppIds = "";
		this.active = "0";
		
		this.isDkthem = "0";
		this.mucphanbo = "0";
		
		this.loaiKhIds = "";
		this.vitriKhIds = "";
		this.hangKhIds = "";
		
		this.phaimuadonhang = "0";
		db = new dbutils();
	}
	
	public Cttrungbay(String id)
	{
		this.id = id;
		this.scheme = "";
		this.diengiai = "";
		this.ngaytds = "";
		this.ngaykttds = "";
		this.ngaytb = "";
		this.ngaykttb = "";
		this.type = "";
		this.ngansach = "";
		this.dasudung = "";
		this.solantt = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		
		this.ngaydk = "";
		this.ngayktdk = "";
		
		this.kbhIds = "";
		this.vungIds = "";
		this.kvIds = "";
		this.nppIds = "";
		
		//this.tratbId = "";
		this.ttb_diengiai = "";
		this.ttb_tungay = "";
		this.ttb_denngay = "";
		this.nsptb_diengiai = "";
		this.nsptb_tungay = "";
		this.nsptb_denngay = "";
		this.isDkthem = "0";
		this.active = "0";
		
		this.mucphanbo = "0";
		this.dangkyPDA = "0";
		
		this.loaiKhIds = "";
		this.vitriKhIds = "";
		this.hangKhIds = "";
		
		this.phaimuadonhang = "0";
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
	
	public String getScheme()
	{
		return this.scheme;
	}
	
	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public String getDiengiai()
	{
		return diengiai;
	}
	
	public String getNgayTds()
	{
		return this.ngaytds;
	}
	
	public void setNgayTds(String ngaytds)
	{
		this.ngaytds = ngaytds;
	}
	
	public String getNgayktTds()
	{
		return this.ngaykttds;
	}
	
	public void setNgayktTds(String ngaykttds)
	{
		this.ngaykttds = ngaykttds;
	}
	
	public String getNgayTb()
	{
		return this.ngaytb;
	}
	
	public void setNgayTb(String ngaytb)
	{
		this.ngaytb = ngaytb;
	}
	
	public String getNgayktTb()
	{
		return this.ngaykttb;
	}
	
	public void setNgayktTb(String ngayktttb)
	{
		this.ngaykttb = ngayktttb;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getNgansach()
	{
		return this.ngansach;
	}
	
	public void setNgansach(String ngansach)
	{
		this.ngansach = ngansach;
	}
	
	public String getDasudung()
	{
		return this.dasudung;
	}
	
	public void setDasudung(String dasudung)
	{
		this.dasudung = dasudung;
	}
	
	public List<INhomsptrungbay> getNsptbList() 
	{		
		return this.nsptbList;
	}
	
	public void setNsptbList(List<INhomsptrungbay> nsptblist) 
	{
		this.nsptbList = nsptblist;
	}

	public ResultSet getTratbRs()
	{		
		return this.tratbRs;
	}
	
	public void setTratbRs(ResultSet tratbRs) 
	{
		this.tratbRs = tratbRs;	
	}
	
	public Hashtable<String, Integer> getTratbId()
	{	
		Hashtable<String, Integer> select = new Hashtable<String, Integer>();
		if(this.tratbIds != null){
			int size = (this.tratbIds).length;
			int m = 0;
			while(m < size){
				select.put(this.tratbIds[m], Integer.parseInt(this.pheptoans[m]));
				
				//System.out.println("Tra tb o buoc nay: " + this.tratbIds[m] + " --- Phep toan tuong ung la: " + this.pheptoans[m] + "\n");
				m++;
			}
		}else{
			select.put("null", new Integer(0));
		}
		return select;
	}
	
	public void setTratbId(String[] tratbIds, String[] pheptoans) 
	{
		this.tratbIds = tratbIds;
		this.pheptoans = pheptoans;
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
		
	public boolean CreateCttb() 
	{
		
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		if(this.mucphanbo.equals("0"))
		{
			if(this.nppIds.length() <= 0)
			{
				this.msg = "Bạn phải chọn nhà phân phối.";
				return false;
			}
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
				
			String dkPDA = "0";
			if(this.dangkyPDA != null && this.dangkyPDA.trim().length() >0)
			{
				dkPDA = this.dangkyPDA;
			}
			
			String query = "Insert into Cttrungbay(dangkyPDA, scheme, diengiai, ngayTds, ngayktTds, ngaytrungbay, ngayketthuctb, ngaydangky, ngayketthucdk, type, ngansach, solanthanhtoan, ngaytao, nguoitao, ngaysua, nguoisua, isdkthem, LEVEL_PHANBO, phaimuaDH) " +
						"values( '" + dkPDA + "' ,N'" + this.scheme + "', N'";
			query = query + this.diengiai + "', '" + convertDate(this.ngaytds) + "' , '" + convertDate(this.ngaykttds) + "' , '" + convertDate(this.ngaytb) + "', '" + convertDate(this.ngaykttb) + "', '" + convertDate(this.ngaydk) + "', '" + convertDate(this.ngayktdk) + "', " +
					"'" + this.type + "', '" + this.ngansach + "', '" + this.solantt + "', '" + this.ngaytao + "', '" + this.nguoitao + "', " +
							"'" + this.ngaytao + "', '" + this.nguoitao + "', '" + this.isDkthem + "', '" + this.mucphanbo + "', '" + this.phaimuadonhang + "' )";
			System.out.println("tao CTTB : "+query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi 'Cttrungbay', " + query;
				return false; 
			}
						
			//lay nsptbID current
			String cttbCurrent = "";
			query = "select scope_identity() as cttbId";
			
			ResultSet rsCttb = this.db.get(query);						
			rsCttb.next();
			cttbCurrent = rsCttb.getString("cttbId");
			rsCttb.close();
			
			query = " insert ctkhuyenmai (scheme,DIENGIAI,CTTB_FK) " +
					"  select N'"+this.scheme+"',N'"+this.diengiai+"',"+cttbCurrent+" where not exists (select 1 from ctkhuyenmai x where x.cttb_fk ="+cttbCurrent+" )  ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Không thể tạo mới chương trình trưng bày. Vui lòng kiểm tra lại thông tin đã nhập.";
				return false; 
			}
			
			
			for(int i = 0; i < this.nsptbList.size(); i++)
			{
				Nhomsptrungbay nsptb = (Nhomsptrungbay)this.nsptbList.get(i);	
				query = "Insert into CTTB_NHOMSPTRUNGBAY(cttrungbay_fk, nhomsptrungbay_fk, pheptoan, thutudieukien) values('" + cttbCurrent + "', '" + nsptb.getId() + "', '" + nsptb.getPheptoan() + "', '" + Integer.toString(i + 1) + "')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the tao moi 'CTTB_NHOMSPTRUNGBAY', " + query;
					return false; 
				}
			}
			
			for(int i = 0; i < this.tratbIds.length; i++ )
			{
				query = "Insert into CTTB_TRATB(cttrungbay_fk, tratrungbay_fk, pheptoan, thutudieukien) values('" + cttbCurrent + "', '" + this.tratbIds[i] + "', '" +  this.pheptoans[i] + "', '" + Integer.toString(i + 1) + "')";
				System.out.println("Query chen du lieu la: " + query + "\n");
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the tao moi 'CTTB_TRATB', " + query;
					return false; 
				}
			}

			if(this.mucphanbo.equals("0"))
			{
				if(this.nppIds.length() > 0)
				{	
					String[] listNpp = this.nppIds.split(",");
					for(int i = 0; i < listNpp.length; i++)
					{
						query = "Insert into CTTB_NPP(cttb_fk, Npp_fk) values('" + cttbCurrent + "', '" + listNpp[i] + "')";
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Khong the tao moi 'CTTB_NPP', " + query;
							return false; 
						}
					}
				}
			}
			else
			{
				if(this.mucphanbo.equals("1"))  //PHAN BO THEO MIEN ( = VUNG BEN GESO )
				{
					query = "Insert CTTB_MIEN ( CTTB_FK, VUNG_FK ) " +
							"select '" + cttbCurrent + "', pk_seq from VUNG where pk_seq > 0 ";
					if(this.vungIds.trim().length() > 0)
						query += " and pk_seq in ( " + this.vungIds + " ) ";
					
					if(!db.update(query))
		    		{
		    			db.getConnection().rollback();
		    			this.setMessage("6.Khong The tao moi CTTB_MIEN loi :"+ query);
		    			return false;
		    		}
				}
				else  //PHAN BO THEO VUNG ( = KHUVUC BEN GESO )
				{
					query = "Insert CTTB_VUNG ( CTTB_FK, KHUVUC_FK ) " +
							"select '" + cttbCurrent + "', pk_seq from KHUVUC where pk_seq > 0 ";
					if(this.kvIds.trim().length() > 0)
						query += " and pk_seq in ( " + this.kvIds + " ) ";
					
					if(!db.update(query))
		    		{
		    			db.getConnection().rollback();
		    			this.setMessage("7.Khong The tao moi CTTB_VUNG loi :"+ query);
		    			return false;
		    		}
				}
			}
			
			if(this.loaiKhIds.trim().length() > 0)
			{
				query = "Insert CTTB_LOAIKH( CTTB_FK, LOAIKH_FK ) " +
						"select '" + cttbCurrent + "', pk_seq from LOAICUAHANG where pk_seq > 0 ";
				if(this.loaiKhIds.trim().length() > 0)
					query += " and pk_seq in ( " + this.loaiKhIds + " ) ";
				
				if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("6.Khong The tao moi CTTB_LOAIKH loi :"+ query);
	    			return false;
	    		}
			}
			
			if(this.hangKhIds.trim().length() > 0)
			{
				query = "Insert CTTB_HANGKH( CTTB_FK, HANGKH_FK ) " +
						"select '" + cttbCurrent + "', pk_seq from HANGCUAHANG where pk_seq > 0 ";
				if(this.loaiKhIds.trim().length() > 0)
					query += " and pk_seq in ( " + this.hangKhIds + " ) ";
				
				if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("6.Khong The tao moi CTTB_HANGKH loi :"+ query);
	    			return false;
	    		}
			}
			
			if(this.vitriKhIds.trim().length() > 0)
			{
				query = "Insert CTTB_VITRIKH( CTTB_FK, VITRIKH_FK ) " +
						"select '" + cttbCurrent + "', pk_seq from VITRICUAHANG where pk_seq > 0 ";
				if(this.vitriKhIds.trim().length() > 0)
					query += " and pk_seq in ( " + this.vitriKhIds + " ) ";
				
				if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("6.Khong The tao moi CTTB_VITRIKH loi :"+ query);
	    			return false;
	    		}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		} 
		catch(Exception e) 
		{
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.msg="Khong The Thuc Hien Luu Chuong trinh trung bay Nay,Vui Long Lien He Voi Admin De Sua Loi Nay.Loi :" +e.toString();
			
			return false;
		}
		
		return true;
	}
	
	public boolean UpdateCttb() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		if(this.mucphanbo.equals("0"))
		{
			if(this.nppIds.length() <= 0)
			{
				this.msg = "Bạn phải chọn nhà phân phối.";
				return false;
			}
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
				
			String query = "Update Cttrungbay set scheme = N'" + this.scheme + "', diengiai = N'" + this.diengiai + "', ngaytds = '" + convertDate(this.ngaytds) + "', " +
							"ngaykttds = '" + convertDate(this.ngaykttds) + "', ngaytrungbay = '" + convertDate(this.ngaytb) + "', ngayketthuctb = '" + convertDate(this.ngaykttb) + "', " +
							" ngaydangky = '" + convertDate(this.ngaydk) + "', ngayketthucdk = '" + convertDate(this.ngayktdk) + "', solanthanhtoan = '" + this.solantt + "',  type = '" + this.type + "', ngansach = '" + this.ngansach + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "', isdkThem = '" + this.isDkthem + "', LEVEL_PHANBO = '" + this.mucphanbo + "', phaimuaDH = '" + this.phaimuadonhang + "' where pk_seq = '" + this.id + "'";
			
			System.out.println("Query cap nhat la: " + query + "\n");
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'Cttrungbay', " + query;
				return false; 
			}
			
			
			
			query = " update ctkhuyenmai set scheme = N'"+this.scheme+"', diengiai = N'"+this.diengiai+"' where CTTB_FK =  " + this.id;
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi 'Cttrungbay', " + query;
				return false; 
			}
			
						
			query = "delete from CTTB_NHOMSPTRUNGBAY where cttrungbay_fk = '" + this.id + "'";
			db.update(query);
			
			for(int i = 0; i < this.nsptbList.size(); i++)
			{
				Nhomsptrungbay nsptb = (Nhomsptrungbay)this.nsptbList.get(i);	
				query = "Insert into CTTB_NHOMSPTRUNGBAY(cttrungbay_fk, nhomsptrungbay_fk, pheptoan, thutudieukien) values('" + this.id + "', '" + nsptb.getId() + "', '" + nsptb.getPheptoan() + "', '" + Integer.toString(i + 1) + "')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the tao moi 'CTTB_NHOMSPTRUNGBAY', " + query;
					return false; 
				}
			}
			
			query = "delete from CTTB_TRATB where cttrungbay_fk = '" + this.id + "'";
			db.update(query);
			for(int i = 0; i < this.tratbIds.length; i++ )
			{
				query = "Insert into CTTB_TRATB(cttrungbay_fk, tratrungbay_fk, pheptoan, thutudieukien) values('" + this.id + "', '" + this.tratbIds[i] + "', '" +  this.pheptoans[i] + "', '" + Integer.toString(i + 1) + "')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the tao moi 'CTTB_TRATB', " + query;
					return false; 
				}
			}
			
			if(this.mucphanbo.equals("0"))
			{
				if(this.nppIds.length() > 0)
				{	
					db.update("delete from cttb_npp where cttb_fk = '" + this.id + "'");
					String[] listNpp = this.nppIds.split(",");
					for(int i = 0; i < listNpp.length; i++)
					{
						query = "Insert into CTTB_NPP(cttb_fk, npp_fk) values('" + this.id + "', '" + listNpp[i] + "')";
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Khong the tao moi 'CTTB_NPP', " + query;
							return false; 
						}
					}
				}
			}
			else
			{
				if(this.mucphanbo.equals("1"))  //PHAN BO THEO MIEN ( = VUNG BEN GESO )
				{
					db.update("delete CTTB_MIEN where cttb_fk = '" + this.id + "'");
					
					query = "Insert CTTB_MIEN ( CTTB_FK, VUNG_FK ) " +
							"select '" + this.id + "', pk_seq from VUNG where pk_seq > 0 ";
					if(this.vungIds.trim().length() > 0)
						query += " and pk_seq in ( " + this.vungIds + " ) ";
					
					System.out.println("__INSERT PHAN BO: " + query);
					if(!db.update(query))
		    		{
		    			db.getConnection().rollback();
		    			this.setMessage("6.Khong The tao moi CTTB_MIEN loi :"+ query);
		    			return false;
		    		}
				}
				else  //PHAN BO THEO VUNG ( = KHUVUC BEN GESO )
				{
					db.update("delete CTTB_VUNG where cttb_fk = '" + this.id + "'");
					
					query = "Insert CTTB_VUNG ( CTTB_FK, KHUVUC_FK ) " +
							"select '" + this.id + "', pk_seq from KHUVUC where pk_seq > 0 ";
					if(this.kvIds.trim().length() > 0)
						query += " and pk_seq in ( " + this.kvIds + " ) ";
					
					if(!db.update(query))
		    		{
		    			db.getConnection().rollback();
		    			this.setMessage("7.Khong The tao moi CTTB_VUNG loi :"+ query);
		    			return false;
		    		}
				}
			}
			
			query = "delete from CTTB_LOAIKH where cttb_fk = '" + this.id + "'";
			db.update(query);
			
			query = "delete from CTTB_HANGKH where cttb_fk = '" + this.id + "'";
			db.update(query);
			
			query = "delete from CTTB_VITRIKH where cttb_fk = '" + this.id + "'";
			db.update(query);
			
			if(this.loaiKhIds.trim().length() > 0)
			{
				query = "Insert CTTB_LOAIKH( CTTB_FK, LOAIKH_FK ) " +
						"select '" + this.id + "', pk_seq from LOAICUAHANG where pk_seq > 0 ";
				if(this.loaiKhIds.trim().length() > 0)
					query += " and pk_seq in ( " + this.loaiKhIds + " ) ";
				
				if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("6.Khong The tao moi CTTB_LOAIKH loi :"+ query);
	    			return false;
	    		}
			}
			
			if(this.hangKhIds.trim().length() > 0)
			{
				query = "Insert CTTB_HANGKH( CTTB_FK, HANGKH_FK ) " +
						"select '" + this.id + "', pk_seq from HANGCUAHANG where pk_seq > 0 ";
				if(this.loaiKhIds.trim().length() > 0)
					query += " and pk_seq in ( " + this.hangKhIds + " ) ";
				
				if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("6.Khong The tao moi CTTB_HANGKH loi :"+ query);
	    			return false;
	    		}
			}
			
			if(this.vitriKhIds.trim().length() > 0)
			{
				query = "Insert CTTB_VITRIKH( CTTB_FK, VITRIKH_FK ) " +
						"select '" + this.id + "', pk_seq from VITRICUAHANG where pk_seq > 0 ";
				if(this.vitriKhIds.trim().length() > 0)
					query += " and pk_seq in ( " + this.vitriKhIds + " ) ";
				
				if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("6.Khong The tao moi CTTB_VITRIKH loi :"+ query);
	    			return false;
	    		}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		} 
		catch(Exception e) {}
		return true;
	}
	
	public void createRS() 
	{
		this.createKbhRs();
		this.createVungRs();
		this.createKhuvucRs();
		this.createNppRs();
		
		String query = "";
		String ttb = "";
		if(this.id.length() == 0)
		{
			query = "select top(20) pk_seq as tratbId, diengiai, isnull(tongluong, 0) as tongluong, isnull(tongtien, 0) as tongtien, case loai when 2 then 'Tra san pham' when 1 then 'Tra tien' end as loai ";
			query = query + "from tratrungbay where pk_seq > '0' ";			
		}
		else
		{
			if(this.tratbIds!=null){
				for(int i = 0; i < this.tratbIds.length; i++)
					ttb += this.tratbIds[i] + ",";
				if(ttb.length() > 0)
					ttb = ttb.substring(0, ttb.length() - 1);
				query = "select top(19) pk_seq as tratbId, diengiai, isnull(tongluong, 0) as tongluong, isnull(tongtien, 0) as tongtien, case loai when 2 then 'Tra san pham' when 1 then 'Tra tien' end as loai ";
				query = query + "from tratrungbay where pk_seq not in (" + ttb + ")";
			}
		}
		if(this.ttb_diengiai.length() > 0)
			query = query + " and Upper(diengiai) like Upper('%" + this.ttb_diengiai + "%') ";
		if(this.ttb_tungay.length() > 0)
			query = query + " and ngaytao >= '" + convertDate(this.ttb_tungay) + "'";
		if(this.ttb_denngay.length() > 0)
			query = query + " and ngaytao <= " + convertDate(this.ttb_denngay) + "'";
		
		if(this.id.length() > 0)
		{
			query = query + " union (select pk_seq as tratbId, diengiai, isnull(tongluong, 0) as tongluong, isnull(tongtien, 0) as tongtien, case loai when 2 then 'Tra san pham' when 1 then 'Tra tien' end as loai ";
			query = query +	"from tratrungbay where pk_seq in (" + ttb + ") ) ";
		}
		query = query + " order by pk_seq DESC ";
		
		this.tratbRs = db.get(query);
		
		this.loaiKhRs = db.get("select PK_SEQ, DIENGIAI as TEN from LOAICUAHANG where TRANGTHAI = '1'");
		this.hangKhRs = db.get("select PK_SEQ, DIENGIAI as TEN from HANGCUAHANG where TRANGTHAI = '1'");
		this.vitriKhRs = db.get("select PK_SEQ, DIENGIAI as TEN from VITRICUAHANG where TRANGTHAI = '1'");
		
	}

	private void createTratbIds() 
	{
		ResultSet rs = db.get("select tratrungbay_fk, pheptoan from cttb_tratb where cttrungbay_fk = '" + this.id + "'");
		System.out.println("select tratrungbay_fk, pheptoan from cttb_tratb where cttrungbay_fk = '" + this.id + "'");
		if(rs != null)
		{
			try 
			{
				String ttb = "";
				String pt = "";
				while(rs.next())
				{
					ttb += rs.getString("tratrungbay_fk") + ",";
					System.out.println("ttb : "+ttb);
					pt += rs.getString("pheptoan") + ",";
				}
				if(ttb.length() > 0)
				{
					System.out.println("Tra trung bay la: " + ttb + " -- Phep toan tuong ung: " + pt + "\n");
				    this.tratbIds = ttb.split(",");
					this.pheptoans = pt.split(",");
				}
				rs.close();
			} 
			catch(Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	private void createKbhRs() 
	{
		this.kbhRs = db.get("select pk_seq, ten from kenhbanhang where trangthai = '1'");
	}
	
	private void createVungRs() 
	{
		this.VungRs = db.get("select * from vung");
	}
	
	private void createKhuvucRs() 
	{
		String sql = "";
		if(this.vungIds.length() > 0)
		{
			sql = "select * from khuvuc where vung_fk in (" + this.vungIds + ")";
			this.KhuvucRs = db.get(sql); 
		}
		else
		{
			sql = "select * from khuvuc";
			this.KhuvucRs = db.get(sql);
		}
	}

	private void createNppRs() 
	{
		String query = "select pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode = convsitecode ";
	   	
	   	//loc theo khu vuc
	   	if(kvIds.length() > 0)
	   		query = "select pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode = convsitecode and khuvuc_fk in (" + kvIds + ") ";
	   	
	   	//khong chon khu vuc, loc theo vung
	   	if(vungIds.length() > 0)
	   		query = "select pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode = convsitecode and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in (" + vungIds + ") ) ";
	    
	   	if(kbhIds.length() > 0)
	   		query = query + " and pk_seq in (select npp_fk from nhapp_kbh where kbh_fk in (" + kbhIds + "))";
	   	
    	this.NppRs = db.get(query);
    	
    	if(this.id.length() > 0)
    	{
    		//tao nppIds
    		query = "select npp_fk as nppId from cttb_npp where cttb_fk ='"+ this.id +"'";    		
    		ResultSet rs = db.get(query);
    		String nppIds = "";
    		if(rs != null)
    		{
    			try 
    			{
					while(rs.next())
					{
						if(rs.getString("nppId") != null)
							nppIds += rs.getString("nppId") + ",";
					}
					rs.close();
				} 
    			catch(Exception e) {}
    			   			
    			if(nppIds.length() > 0)
    			{
    				nppIds = nppIds.substring(0, nppIds.length() - 1);
    				this.nppIds = nppIds;
    			}
    		}
    	}
	}

	private void createNsptbList()
	{
		String query = "select top(200) a.pk_seq as nsptbId, a.diengiai, isnull(a.tongluong, 0) as tongluong, isnull(a.tongtien, 0) as tongtien, b.pheptoan ";
		query = query + "from nhomsptrungbay a inner join cttb_nhomsptrungbay b on a.pk_seq = b.nhomsptrungbay_fk where b.cttrungbay_fk = '" + this.id + "'";
		
		System.out.println("Query khi cap nhat la: " + query + "\n");
		
		ResultSet rs = db.get(query);
		List<INhomsptrungbay> listDkkm = new ArrayList<INhomsptrungbay>();
		if(rs != null)
		{
			try 
			{
				INhomsptrungbay nsptb = null;
				while(rs.next())
				{
					String dkkmId = rs.getString("nsptbId");
					String diengiai = rs.getString("diengiai");
					String tongluong = rs.getString("tongluong");
					String tongtien = rs.getString("tongtien");
					String pheptoan = rs.getString("pheptoan");
					nsptb = new Nhomsptrungbay(dkkmId, diengiai, tongluong, tongtien);
					nsptb.setPheptoan(pheptoan);
					listDkkm.add(nsptb);
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
		this.nsptbList = listDkkm;
	}
	
	public void init() 
	{
		String query = "select a.PK_SEQ as cttbId, a.SCHEME, a.ngaytds, a.ngaykttds, a.ngaytrungbay as ngaytb, a.ngayketthuctb as ngaykttb, a.ngaydangky, a.ngayketthucdk, " +
				" isnull(a.DIENGIAI, '') as diengiai, a.TYPE, a.solanthanhtoan as solantt, a.NGANSACH, a.DASUDUNG, a.NGAYTAO, a.NGAYSUA, b.TEN as nguoitao, c.TEN as nguoisua, isnull(isdkthem, 'false') as isdkthem, isnull(LEVEL_PHANBO, 0) as LEVEL_PHANBO, isnull(phaimuaDH, 0) as phaimuaDH " +
				" from CTTRUNGBAY a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ where a.pk_seq = '" + this.id + "'";
		
		System.out.println("Gia tri cau init la: " + query + "\n");
		
		ResultSet rs = db.get(query);

		try
        {
            rs.next();    	
            this.id = rs.getString("cttbId");
            this.scheme = rs.getString("scheme");
			this.diengiai = rs.getString("diengiai");
			
			this.ngaytds = rs.getString("ngaytds");
			this.ngaykttds = rs.getString("ngaykttds");
			this.ngaytb = rs.getString("ngaytb");
			this.ngaykttb = rs.getString("ngaykttb");
			this.ngaydk = rs.getString("ngaydangky");
			this.ngayktdk = rs.getString("ngayketthucdk");
			
			this.type = rs.getString("type");
			this.ngansach = rs.getString("ngansach");
			this.solantt = rs.getString("solantt");
			this.dasudung = rs.getString("dasudung");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");
			this.isDkthem = rs.getString("isdkthem");
			this.mucphanbo = rs.getString("LEVEL_PHANBO");
			this.phaimuadonhang = rs.getString("phaimuaDH");
			System.out.println("is Dang ky them la: " + this.isDkthem + "\n");
			rs.close();
       	}
        catch(Exception e){}
        this.createNsptbList();
        this.createTratbIds();
        this.createRS();
        
      //INIT MIEN + VUNG NEU LA PHAN BO THEO CAC MUC NAY
        ResultSet rsData = db.get("select KHUVUC_FK from CTTB_VUNG where CTTB_FK ='" + this.id + "'");
        if(rsData != null)
        {
        	try 
        	{
        		String kvStr = "";
				while(rsData.next())
				{
					kvStr += rsData.getString("KHUVUC_FK") + ",";
				}
				rsData.close();
				
				if(kvStr.trim().length() > 0)
				{
					this.kvIds = kvStr.substring(0, kvStr.length() - 1);
				}
			} 
        	catch (Exception e) { System.out.println("EXCEPTION KV: " + this.vungIds ); }
        }
        
        rsData = db.get("select VUNG_FK from CTTB_MIEN where CTTB_FK ='" + this.id + "'");
        if(rsData != null)
        {
        	try 
        	{
        		String vungStr = "";
				while(rsData.next())
				{
					vungStr += rsData.getString("VUNG_FK") + ",";
				}
				rsData.close();
				
				if(vungStr.trim().length() > 0)
				{
					this.vungIds = vungStr.substring(0, vungStr.length() - 1);
				}
			} 
        	catch (Exception e) { System.out.println("EXCEPTION VUNG: " + this.vungIds ); }
        	
        	System.out.println("VUNG LA: " + this.vungIds );
        }
        
        rsData = db.get("select LOAIKH_FK from CTTB_LOAIKH where CTTB_FK ='" + this.id + "'");
        if(rsData != null)
        {
        	try 
        	{
        		String vungStr = "";
				while(rsData.next())
				{
					vungStr += rsData.getString("LOAIKH_FK") + ",";
				}
				rsData.close();
				
				if(vungStr.trim().length() > 0)
				{
					this.loaiKhIds = vungStr.substring(0, vungStr.length() - 1);
				}
			} 
        	catch (Exception e) { System.out.println("EXCEPTION LOAI KH: " + this.loaiKhIds ); }
        	
        	System.out.println("LOAI KH LA: " + this.loaiKhIds );
        }
        
        rsData = db.get("select HANGKH_FK from CTTB_HANGKH where CTTB_FK ='" + this.id + "'");
        if(rsData != null)
        {
        	try 
        	{
        		String vungStr = "";
				while(rsData.next())
				{
					vungStr += rsData.getString("HANGKH_FK") + ",";
				}
				rsData.close();
				
				if(vungStr.trim().length() > 0)
				{
					this.hangKhIds = vungStr.substring(0, vungStr.length() - 1);
				}
			} 
        	catch (Exception e) { System.out.println("EXCEPTION HANG KH: " + this.hangKhIds ); }
        	
        	System.out.println("HANG KH LA: " + this.hangKhIds );
        }
        
        rsData = db.get("select VITRIKH_FK from CTTB_VITRIKH where CTTB_FK ='" + this.id + "'");
        if(rsData != null)
        {
        	try 
        	{
        		String vungStr = "";
				while(rsData.next())
				{
					vungStr += rsData.getString("VITRIKH_FK") + ",";
				}
				rsData.close();
				
				if(vungStr.trim().length() > 0)
				{
					this.vitriKhIds = vungStr.substring(0, vungStr.length() - 1);
				}
			} 
        	catch (Exception e) { System.out.println("EXCEPTION VI TRI KH: " + this.vitriKhIds ); }
        	
        	System.out.println("VI TRI KH LA: " + this.vitriKhIds );
        }
        
        
	}
		
	public String getTtb_diengiai() 
	{	
		return this.ttb_diengiai;
	}
	
	public void setTtb_diengiai(String ttb_diengiai) 
	{
		this.ttb_diengiai = ttb_diengiai;		
	}
	
	public String getTtb_tungay() 
	{	
		return this.ttb_tungay;
	}
	
	public void setTtb_tungay(String ttb_tungay) 
	{
		this.ttb_tungay = ttb_tungay;		
	}
	
	public String getTtb_denngay() 
	{		
		return this.ttb_denngay;
	}
	
	public void setTtb_denngay(String ttb_denngay)
	{
		this.ttb_denngay = ttb_denngay;		
	}
	
	public String getNsptb_diengiai() 
	{	
		return this.nsptb_diengiai;
	}
	
	public void setNsptb_diengiai(String nsptb_diengiai) 
	{
		this.nsptb_diengiai = nsptb_diengiai;		
	}
	
	public String getNsptb_tungay() 
	{	
		return this.nsptb_tungay;
	}
	
	public void setNsptb_tungay(String nsptb_tungay) 
	{
		this.nsptb_tungay = nsptb_tungay;		
	}
	
	public String getNsptb_denngay() 
	{		
		return this.nsptb_denngay;
	}
	
	public void setNsptb_denngay(String nsptb_denngay)
	{
		this.nsptb_denngay = nsptb_denngay;		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String convertDate(String date)
	{
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	public String getSolantt() 
	{
		return this.solantt;
	}

	public void setSolantt(String solantt) 
	{
		this.solantt = solantt;
	}

	public String getNgayBddk() 
	{
		return this.ngaydk;
	}

	public void setNgayBddk(String ngayBddk) 
	{
		this.ngaydk = ngayBddk;
	}

	public String getNgayKtdk() 
	{	
		return this.ngayktdk;
	}

	public void setNgayKtdk(String ngayKtdk) 
	{
		this.ngayktdk = ngayKtdk;
	}

	public String isDangkythem() 
	{
		return this.isDkthem;
	}

	public void setDangkythem(String flag) 
	{
		this.isDkthem = flag;
	}

	public void setKbhRs(ResultSet kbh) 
	{
		this.kbhRs = kbh;
	}

	public ResultSet getKbhRs() 
	{
		return this.kbhRs;
	}
	
	public void setVungRs(ResultSet vung) 
	{
		this.VungRs = vung;
	}
	
	public ResultSet getVungRs()
	{
		return this.VungRs;
	}
	
	public void setKhuvucRs(ResultSet khuvuc) 
	{		
		this.KhuvucRs = khuvuc;
	}

	
	public ResultSet getKhuvucRs() 
	{
		return this.KhuvucRs;
	}

	public void setNppRs(ResultSet npp)
	{
		this.NppRs = npp;
	}

	public ResultSet getNppRs()
	{
		return this.NppRs;
	}
	
	public String getKbhIds() 
	{
		return this.kbhIds;
	}

	public void setKbhIds(String kenhIds)
	{
		this.kbhIds = kenhIds;	
	}

	public String getVungIds() 
	{
		return this.vungIds;
	}

	public void setVungIds(String vungIds) 
	{
		this.vungIds = vungIds;
	}

	public String getKhuvucIds()
	{
		return this.kvIds;
	}

	public void setKhuvucIds(String kvIds) 
	{
		this.kvIds = kvIds;
	}

	public String getNppIds() 
	{
		return this.nppIds;
	}

	public void setNppIds(String nppIds) 
	{
		this.nppIds = nppIds;	
	}

	public void setActive(String active)
	{
		this.active = active;
	}

	public String getActive() 
	{
		return this.active;
	}

	
	public void DbClose() {			
		try{
			if(kbhRs!=null){
				kbhRs.close();
			}
			if(VungRs!=null){
				VungRs.close();
			}
			if(KhuvucRs!=null){
				KhuvucRs.close();
			}
			if(NppRs!=null){
				NppRs.close();
			}
			if(tratbRs!=null){
				tratbRs.close();
			}
			if(db!=null){
				db.shutDown();
			}
			if(nsptbList!=null)
			{
				nsptbList.clear();
				nsptbList=null;
			}
			
		}catch(Exception er){
			
		}
	}

	public void setMucphanbo(String mucpb) {
		
		this.mucphanbo = mucpb;
	}

	
	public String getMucphanbo() {
		
		return this.mucphanbo;
	}
	
	String dangkyPDA = "";
	public String getDangkyPDA() {
		return dangkyPDA;
	}
	public void setDangkyPDA(String dangkyPDA) {
		this.dangkyPDA = dangkyPDA;
	}

	
	public void setLoaikhRs(ResultSet loaiKh) {
		
		this.loaiKhRs = loaiKh;
	}

	
	public ResultSet getLoaikhRs() {
		
		return this.loaiKhRs;
	}

	
	public void setLoaikhIds(String lkhIds) {
		
		this.loaiKhIds = lkhIds;
	}

	
	public String getLoaikhIds() {
		
		return this.loaiKhIds;
	}

	
	public void setHangkhRs(ResultSet hangKh) {
		
		this.hangKhRs = hangKh;
	}

	
	public ResultSet getHangkhRs() {
		
		return this.hangKhRs;
	}

	
	public void setHangkhIds(String hangIds) {
		
		this.hangKhIds = hangIds;
	}

	
	public String getHangkhIds() {
		
		return this.hangKhIds;
	}

	
	public void setVitrikhRs(ResultSet vtKh) {
		
		this.vitriKhRs = vtKh;
	}

	
	public ResultSet getVitrikhRs() {
		
		return this.vitriKhRs;
	}

	
	public void setVitrikhIds(String vtIds) {
		
		this.vitriKhIds = vtIds;
	}

	
	public String getVitrikhIds() {
		
		return this.vitriKhIds;
	}

	
	public String getPhaimuadonhang() {

		return this.phaimuadonhang;
	}


	public void setPhaimuadonhang(String phaimuadonhang) {

		this.phaimuadonhang = phaimuadonhang;
	}
	
}
