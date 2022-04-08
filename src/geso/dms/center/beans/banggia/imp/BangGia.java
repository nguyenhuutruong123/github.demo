package geso.dms.center.beans.banggia.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.dms.center.beans.banggia.IBangGia;
import geso.dms.center.beans.banggia.IBangGia_Sp;
import geso.dms.center.db.sql.dbutils;

public class BangGia implements IBangGia
{
	String view = "";
	String userId;
	String id;
	
	String ten;
	
	String chietKhau;
	
	String[] nppIdCks = new String[0];
	String[] nppChietKhaus = new String[0];

	String loai="";
	public String getLoai() {
		return loai;
	}


	public void setLoai(String loai) {
		this.loai = loai;
	}


	public String getChietKhau() 
	{
		return chietKhau;
	}


	public void setChietKhau(String chietKhau) 
	{
		this.chietKhau = chietKhau;
	}


	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	String nppId;
	ResultSet nppRs;
	
	String trangthai;
	String msg;
	
	List<IBangGia_Sp> spList;
	dbutils db;
	
	NumberFormat formater = new DecimalFormat("##,###,###");
	NumberFormat formater2 = new DecimalFormat("##,###,###.####");
	public BangGia()
	{
		this.userId = "";
		this.id = "";
		this.ten = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.trangthai = "1";
		this.msg = "";
		this.nppId = "";
		this.tungay="";
		this.chietKhau ="";
		this.loai="";
		this.spList = new ArrayList<IBangGia_Sp>();
		this.db = new dbutils();
	}
	
	
	public BangGia(String id)
	{
		this.userId = "";
		this.id = id;
		this.kbhId = "";
		this.dvkdId = "";
		this.trangthai = "1";
		this.msg = "";
		this.nppId = "";
		this.tungay ="";
		this.chietKhau ="";
		this.loai="";
		this.spList = new ArrayList<IBangGia_Sp>();
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
		String query="";
		if(this.loai.equals("0"))
		{
			query="select TEN, DVKD_FK, KENH_FK, TRANGTHAI,TuNgay,ChietKhau from BANGGIAB2B where PK_SEQ = '" + this.id + "'";
		 
		}
		else
		{
			query = "select TEN, DVKD_FK, KENH_FK, TRANGTHAI,TuNgay,ChietKhau from BANGGIAMUANPP where PK_SEQ = '" + this.id + "'";
		}
		ResultSet rs = db.get(query);
	
			try 
			{
				while(rs.next())
				{
					this.ten = rs.getString("TEN");
					this.dvkdId = rs.getString("DVKD_FK");
					this.kbhId = rs.getString("KENH_FK");
					this.trangthai = rs.getString("TRANGTHAI");
					
					this.tungay = rs.getString("TuNgay");
					this.chietKhau = rs.getFloat("ChietKhau")==0?"": formater.format(rs.getDouble("ChietKhau") ) ;
					
				}
				rs.close();
			}
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
				e.printStackTrace();
			}
		//init KhachHangApDung
		query = "select NPP_FK from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK = '" + this.id + "' ";
		System.out.println("Lay khach hang: " + query);
		ResultSet rsCheck = db.get(query);
		try 
		{
			String khApDung = "";
			int count = 0;
			while(rsCheck.next())
			{
				khApDung += rsCheck.getString("NPP_FK") + ",";
				count++;
				
			}
			rsCheck.close();
			
			if(khApDung.trim().length() > 0)
			{
					khApDung = khApDung.substring(0, khApDung.length() - 1);
					this.nppId = khApDung;
			}
		} 
		catch (Exception e) {e.printStackTrace();}
	//	System.out.println("Khach hang nppId: " + this.nppId);
		this.createRs();
	}
	
	public boolean createBanggia()
	{	
		try 
		{
			
			if(checkExits(db))
			{
				this.msg = "Ngày bắt đầu áp dụng bảng giá không được phép nhỏ hơn từ ngày áp dụng của bảng giá đang có";
				return false;
			}
			
			
			if(this.ten.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}
			
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			if(this.kbhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kênh bán hàng";
				return false;
			}
			
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong bảng giá";
				return false;
			}
			
			if(this.nppId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng áp dụng bảng giá";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "\n insert into BANGGIAMUANPP(ten, ngaytao, ngaysua, nguoitao, nguoisua, dvkd_fk, KENH_FK, trangthai,TuNgay) " +
			"\n values(?,?,?,?, ?,?,?, '0',?)";
			Object[] data;
			data= new Object[]   { this.ten, getDateTime(), getDateTime() , this.userId ,this.userId , this.dvkdId , this.kbhId ,tungay };

			if (this.db.updateQueryByPreparedStatement(query, data)!= 1) 
			{
				dbutils.viewQuery(query, data);
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				db.getConnection().rollback();
				return false;
			}
			
			String bgbanId = db.getPk_seq();		
			
			for (int i = 0; i < this.spList.size(); i++)
			{
				IBangGia_Sp sp = this.spList.get(i);
				String gia = sp.getGiaban().trim().length() <= 0 ? "0" : sp.getGiaban().trim().replaceAll(",", "");
				String chonban = sp.getChonban();
				if (gia.equals("0"))
					chonban = "0";

				query = "\n insert into bgmuanpp_sanpham (bgmuanpp_fk,sanpham_fk,giamuanpp,trangthai,chietkhau) " +
				"\n values(?, ?,?,?,?)";
				data = new Object[]   { bgbanId , sp.getIdsp() ,gia,chonban,sp.getSpchietkhau()};
				if (this.db.updateQueryByPreparedStatement(query, data) !=1) 
				{				
					dbutils.viewQuery(query, data);
					this.msg = "2. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
					db.getConnection().rollback();
					return false;
				}
			}
				
			if(this.nppId.trim().length() > 0)
			{
				query = "\n insert into BANGGIAMUANPP_NPP(BANGGIAMUANPP_FK, NPP_FK) " +
				"\n select '" + bgbanId + "', pk_seq from NhaPhanPhoi where pk_seq in ( " + this.nppId  + " ) ";
				
				if (!this.db.update(query))
				{
					//this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP " + query;
					this.msg = "3. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
					this.db.getConnection().rollback();
					return false;
				}
				/*query=" select c.ten as tennpp, b.ten as tenbg from BANGGIAMUANPP_NPP a inner join BANGGIAMUANPP b on a.BANGGIAMUANPP_FK=b.pk_seq "+
                   " inner join nhaphanphoi c on c.pk_seq=a.npp_fk  where b.TUNGAY='"+this.tungay+"' and b.pk_seq<>"+bgbanId+" and a.NPP_FK in ( " + this.nppId  + " )  ";
				ResultSet checkRs=db.get(query);
				String loi="";
				if(checkRs!=null){
					if(checkRs.next()){
						loi+="Nhà phân phối đã tồn tại trong bảng giá cùng ngày: "+checkRs.getString("tennpp");
					}
				}
				if(loi.length()>0){
					this.msg = loi;
					this.db.getConnection().rollback();
					return false;
				}*/
				
				if (this.nppIdCks!=null && this.nppChietKhaus != null)
				{
					for( int k=0;k< this.nppIdCks.length;k++)
					{
						query=	"\n update 	BANGGIAMUANPP_NPP " +
						"\n set chietkhau="+this.nppChietKhaus[k] +" " +
						"\n where BANGGIAMUANPP_FK="+bgbanId+" and " +
						"\n NPP_FK = "+this.nppIdCks[k]+" ";
						if(!this.db.update(query))
						{
							this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP ( cap nhat chiet khau)  ";
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
				
			}			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}	
	
	private boolean checkHOPLE()
	{
		String query = "\n select count(*) as soDong " +
		"\n from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.pk_seq = b.dondathang_fk " +
		"\n where a.tungay >= '" + this.tungay + "'" +
		"\n and sanpham_fk in ( select SANPHAM_FK from BANGGIAB2B_SANPHAM where BGMUANPP_FK = '" + this.id + "' and GIABAN != 0 ) ";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				if(rs.next())
				{
					if( rs.getInt("soDong") >= 1 )
						return false;
				}
				rs.close();
			} 
			catch (Exception e) {e.printStackTrace(); }
		}
		
		return true;		
	}
	
	public boolean updateBanggia() 
	{
		try 
		{		
			if (1==2) {
				if (!checkHOPLE())
				{
					//this.msg = "Bảng giá đã được áp dụng. Bạn không thể thay đổi.";
					//return false;
				}
			}
			
			//CHECK NPP DA CO NGAY AP DUNG TU NGAY ROI THI KHONG DUOC THAY DOI TU NGAY
			//CHECK XEM CO NPP NAO DUOC AP DUNG BANG GIA TRUNG TU NGAY CHUA
			String query = "\n select a.TUNGAY, c.MA, COUNT(a.PK_SEQ) as soBANGGIA  " +
			"\n from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
			"\n inner join NHAPHANPHOI c on b.NPP_FK = c.PK_SEQ  " +
			"\n where a.PK_SEQ != '" + this.id + "' and a.TUNGAY = ( select tungay from BANGGIAMUANPP where pk_seq = '" + this.id + "' ) and a.KENH_FK = '" + kbhId + "' " +
			"\n and a.DVKD_FK = '" + dvkdId + "' AND B.NPP_FK IN (SELECT NPP_FK FROM BANGGIAMUANPP_NPP WHERE BANGGIAMUANPP_FK='"+this.id+"' )  " +
			"\n group by a.TUNGAY, c.MA " +
			"\n having COUNT(a.PK_SEQ) >= 1 ";			
			System.out.println("___CHECK NPP SELECTED: " + query);
			ResultSet rsNPP = db.get(query);
			String nppKhongHopLe = "";
			String tungay = "";
			
			if (rsNPP != null)
			{
				while (rsNPP.next())
				{
					nppKhongHopLe += rsNPP.getString("MA") + ",";
					System.out.println("____SO BG KHONG HOP LE: " + nppKhongHopLe);					
					tungay = rsNPP.getString("TUNGAY");
				}
				rsNPP.close();
			}
			
			System.out.println("____NPP KHONG HOP LE: " + nppKhongHopLe);
			if(nppKhongHopLe.trim().length() > 0)
			{
				this.msg = "Các nhà phân phối ( " + nppKhongHopLe + " ) đã tồn tại trong bảng giá có từ ngày ( " + tungay + " ) ";
				System.out.println("ERROR: " + this.msg);
//				db.getConnection().rollback();
				return false;
			}
			
			query = "\n SELECT COUNT(*) AS SoDong "+    
			"\n FROM DONDATHANG DH "+ 
			"\n WHERE EXISTS "+
			"\n ( "+
			"\n     SELECT * FROM "+
			"\n     BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
			"\n     B ON A.PK_SEQ=B.BANGGIAMUANPP_FK  "+
			"\n     WHERE  B.NPP_FK=DH.NPP_FK "+
			"\n     AND A.KENH_FK=DH.KBH_FK AND A.DVKD_FK=DH.DVKD_FK "+
			"\n     AND A.TUNGAY<=DH.NGAYDAT AND DH.TRANGTHAI!=6 "+
			"\n     AND A.PK_SEQ='"+id+"' AND A.TUNGAY!='"+this.tungay+"' "+
			"\n ) " ;
			System.out.println("[DONDATHANG]" + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				this.msg = "Bảng giá đã được sử dụng trong đơn đặt hàng ,vui lòng kiểm tra lại !";
				return false;
			}
			
			query = "\n SELECT COUNT(*) AS SoDong "+    
			"\n FROM DONHANG DH "+
			"\n WHERE EXISTS "+
			"\n ( "+
			"\n     SELECT * FROM  "+
			"\n     BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
			"\n     B ON A.PK_SEQ=B.BANGGIAMUANPP_FK "+
			"\n     WHERE  B.NPP_FK=DH.NPP_FK "+
			"\n     AND A.KENH_FK=DH.KBH_FK "+
			"\n     AND A.TUNGAY<=DH.NGAYNHAP AND DH.TRANGTHAI!=2 "+
			"\n     AND A.PK_SEQ='"+id+"' AND A.TUNGAY!='"+this.tungay+"' "+
			"\n ) ";			
			rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				this.msg = "Bảng giá đã được sử dụng trong đơn hàng ,vui lòng kiểm tra lại !";
				return false;
			}	
			
			if(this.ten.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}
			
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			if(this.kbhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kênh bán hàng";
				return false;
			}
			
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong bảng giá";
				return false;
			}
			
			if(this.nppId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng áp dụng bảng giá";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "\n update BANGGIAMUANPP set   TuNgay=?, ten = ?, ngaysua = ?, " +
			"\n nguoisua = ?,KENH_FK = ?, trangthai = ?,ChietKhau="+0+" where pk_seq = ? ";
			Object[] data;
			System.out.println(this.kbhId);
			data = new Object[]   {this.tungay, this.ten , getDateTime() , this.userId ,this.kbhId ,this.trangthai, this.id};
			
			if( this.db.updateQueryByPreparedStatement(query, data)!=1 ) 
			{
				dbutils.viewQuery(query, data);
				//this.msg = "Không thể cập nhật BANGGIA: " + query;
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}
			
			String bgbanId = this.id;
			
			query="delete from BGMUANPP_SANPHAM where BGMUANPP_FK="+bgbanId +" ";
			if(!db.update(query))
			{
				//this.msg = "Không thể cập nhật BGMUANPP_SANPHAM: " + query;
				this.msg = "2. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}
			
			query="delete from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK="+bgbanId +" ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BANGGIAMUANPP_NPP: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IBangGia_Sp sp = this.spList.get(i);
				String gia = sp.getGiaban().trim().length() <= 0 ? "0" : sp.getGiaban().trim().replaceAll(",", "");
				String chonban = sp.getChonban();
				if(gia.equals("0"))
					chonban = "0";
				
				query = "insert into BGMUANPP_SANPHAM(BGMUANPP_FK, sanpham_fk, GiaMuaNPP,trangthai,chietkhau) " +
						"values(?,?,?,?,?)";
				
				data= new Object[]   { bgbanId, sp.getIdsp() ,gia,chonban,sp.getSpchietkhau()};
				db.viewQuery(query, data);
				if( this.db.updateQueryByPreparedStatement(query, data)!=1 ) 
					{
								
						//this.msg = "Không thể tạo mới BGMUANPP_SANPHAM " + query;
						this.msg = "3. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
						db.getConnection().rollback();
						return false;
					}
			}
				
			if(this.nppId.trim().length() > 0)
			{
				query = "insert into BANGGIAMUANPP_NPP(BANGGIAMUANPP_FK, NPP_FK) " +
						"select '" + bgbanId + "', pk_seq from NhaPhanPhoi where pk_seq in ( " + this.nppId  + " ) ";
				
				if(!this.db.update(query))
				{
					//this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP " + query;
					this.msg = "4. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
					this.db.getConnection().rollback();
					return false;
				}
				//System.out.println("CHuan bi vo update xhiet khau");
			//	System.out.println("CHuan bi vo update xhiet khau"+ this.nppIdCks.length + " " + this.nppChietKhaus);
				if( this.nppIdCks!=null && this.nppChietKhaus!=null )
				{
					for( int k=0;k< this.nppIdCks.length;k++)
					{	
						/*query=	"	update 	BANGGIAMUANPP_NPP " +
								"	set chietkhau=" +this.nppChietKhaus[k] +" " +
								"	where 	BANGGIAMUANPP_FK="+ bgbanId +" and " +
								"			NPP_FK ="+this.nppIdCks[k] ;
						
						data= new Object[]   { Integer.parseInt(this.nppChietKhaus[k]) ,bgbanId , this.nppIdCks[k]};
						db.viewQuery(query, data);
						if( this.db.updateQueryByPreparedStatement(query, data)!=1 ) 
						if( this.db.updateReturnInt(query)<=0)
						{
							//this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP ( cap nhat chiet khau)  " + query;
							
							this.msg = "5. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
							this.db.getConnection().rollback();
							return false;
						}*/
						
						query=	"	update 	BANGGIAMUANPP_NPP " +
								"	set chietkhau="+this.nppChietKhaus[k] +" " +
								"	where 	BANGGIAMUANPP_FK="+bgbanId+" and " +
								"			NPP_FK = "+this.nppIdCks[k]+" ";
						if(!this.db.update(query))
						{
							this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP ( cap nhat chiet khau)  " ;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		return true;
	}
	
	public boolean updateBanggia_B2B() 
	{
		try 
		{
			
			if (1==2) {
				if(!checkHOPLE())
				{
					//this.msg = "Bảng giá đã được áp dụng. Bạn không thể thay đổi.";
					//return false;
				}
			}
			
	/*		//CHECK NPP DA CO NGAY AP DUNG TU NGAY ROI THI KHONG DUOC THAY DOI TU NGAY
			//CHECK XEM CO NPP NAO DUOC AP DUNG BANG GIA TRUNG TU NGAY CHUA
			String query = "select a.TUNGAY, c.MA, COUNT(a.PK_SEQ) as soBANGGIA  " +
							"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
								"inner join NHAPHANPHOI c on b.NPP_FK = c.PK_SEQ  " +
							"where a.PK_SEQ != '" + this.id + "' and a.TUNGAY = ( select tungay from BANGGIAMUANPP where pk_seq = '" + this.id + "' ) and a.KENH_FK = '" + kbhId + "' " +
									"and a.DVKD_FK = '" + dvkdId + "' AND B.NPP_FK IN (SELECT NPP_FK FROM BANGGIAMUANPP_NPP WHERE BANGGIAMUANPP_FK='"+this.id+"' )  " +
							"group by a.TUNGAY, c.MA " +
							"having COUNT(a.PK_SEQ) >= 1 ";
			
			System.out.println("___CHECK NPP SELECTED: " + query);
			ResultSet rsNPP = db.get(query);
			String nppKhongHopLe = "";
			String tungay = "";
			
			if(rsNPP != null)
			{
				while(rsNPP.next())
				{
					nppKhongHopLe += rsNPP.getString("MA") + ",";
					System.out.println("____SO BG KHONG HOP LE: " + nppKhongHopLe);
					
					tungay = rsNPP.getString("TUNGAY");
				}
				rsNPP.close();
			}
			
			System.out.println("____NPP KHONG HOP LE: " + nppKhongHopLe);
			if(nppKhongHopLe.trim().length() > 0)
			{
				this.msg = "Các nhà phân phối ( " + nppKhongHopLe + " ) đã tồn tại trong bảng giá có từ ngày ( " + tungay + " ) ";
				System.out.println("ERROR: " + this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			query = 
				"	 SELECT COUNT(*) AS SoDong "+    
				"	 FROM DONDATHANG DH "+ 
				"	 WHERE EXISTS "+
				"	 ( "+
				"		SELECT * FROM "+
				"		BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
				"		B ON A.PK_SEQ=B.BANGGIAMUANPP_FK  "+
				"		WHERE  B.NPP_FK=DH.NPP_FK "+
				"		AND A.KENH_FK=DH.KBH_FK AND A.DVKD_FK=DH.DVKD_FK "+
				"		AND A.TUNGAY<=DH.NGAYDAT AND DH.TRANGTHAI!=6 "+
				"		AND A.PK_SEQ='"+id+"' AND A.TUNGAY!='"+this.tungay+"' "+
				"	 ) " ;
			System.out.println("[DONDATHANG]" + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				this.msg = "Bảng giá đã được sử dụng trong đơn đặt hàng ,vui lòng kiểm tra lại !";
				return false;
			}
			query = 
			" SELECT COUNT(*) AS SoDong "+    
			"  FROM DONHANG DH "+
			" WHERE EXISTS "+
			"  ( "+
			"	SELECT * FROM  "+
			"	BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
			"	B ON A.PK_SEQ=B.BANGGIAMUANPP_FK "+
			"	WHERE  B.NPP_FK=DH.NPP_FK "+
			"	AND A.KENH_FK=DH.KBH_FK "+
			"	AND A.TUNGAY<=DH.NGAYNHAP AND DH.TRANGTHAI!=2 "+
			"	AND A.PK_SEQ='"+id+"' AND A.TUNGAY!='"+this.tungay+"' "+
			"  ) ";
			
			rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				this.msg = "Bảng giá đã được sử dụng trong đơn hàng ,vui lòng kiểm tra lại !";
				return false;
			}*/
			
			
			String query="";
			if(this.ten.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}
			
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			if(this.kbhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kênh bán hàng";
				return false;
			}
			
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong bảng giá";
				return false;
			}
			
			/*if(this.nppId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng áp dụng bảng giá";
				return false;
			}*/
			
			db.getConnection().setAutoCommit(false);
			
			query = "update BANGGIAB2B set   TuNgay='"+this.tungay+"', ten = N'" + this.ten + "', ngaysua = '" + getDateTime() + "', " +
					"nguoisua = '" + this.userId + "', trangthai = '" + this.trangthai + "',ChietKhau="+0+" where pk_seq = '" + this.id + "'";
			
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BANGGIA: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			String bgbanId=this.id;
			
			query="delete from BANGGIAB2B_SANPHAM where BANGGIAB2B_FK="+bgbanId +" ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BANGGIAB2B_SANPHAM: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			/*query="delete from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK="+bgbanId +" ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BANGGIAMUANPP_NPP: " + query;
				this.db.getConnection().rollback();
				return false;
			}*/
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IBangGia_Sp sp = this.spList.get(i);
				String gia = sp.getGiaban().trim().length() <= 0 ? "0" : sp.getGiaban().trim().replaceAll(",", "");
				String chonban = sp.getChonban();
				if(gia.equals("0"))
					chonban = "0";
				
				query = "insert into BANGGIAB2B_SANPHAM(BANGGIAB2B_FK, sanpham_fk, GIABAN,trangthai,chietkhau) " +
						"values('" + bgbanId + "', '" + sp.getIdsp() + "','" +gia + "','"+chonban+"',"+sp.getSpchietkhau()+")";
					if(!this.db.update(query))
					{				
						this.msg = "Không thể tạo mới BANGGIAB2B_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
			}
				
		/*	if(this.nppId.trim().length() > 0)
			{
				query = "insert into BANGGIAMUANPP_NPP(BANGGIAMUANPP_FK, NPP_FK) " +
						"select '" + bgbanId + "', pk_seq from NhaPhanPhoi where pk_seq in ( " + this.nppId  + " ) ";
				
				if(!this.db.update(query))
				{
					this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP " + query;
					this.db.getConnection().rollback();
					return false;
				}
				//System.out.println("CHuan bi vo update xhiet khau");
			//	System.out.println("CHuan bi vo update xhiet khau"+ this.nppIdCks.length + " " + this.nppChietKhaus);
				if( this.nppIdCks!=null && this.nppChietKhaus!=null )
				{
					for( int k=0;k< this.nppIdCks.length;k++)
					{	
						query=	"	update 	BANGGIAMUANPP_NPP " +
								"	set chietkhau="+this.nppChietKhaus[k] +" " +
								"	where 	BANGGIAMUANPP_FK="+bgbanId+" and " +
								"			NPP_FK = "+this.nppIdCks[k]+" ";
						
						System.out.println("cau chiet khau " + query);
						if(!this.db.update(query))
						{
							this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP ( cap nhat chiet khau)  " + query;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}*/
			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		return true;
	}
	
	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
			if(nppRs!=null)nppRs.close();
			if(kbhRs!=null)kbhRs.close();
			if(dvkdRs!=null)dvkdRs.close();
		} 
		catch (Exception e) {e.printStackTrace();}
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getTen() {
		
		return this.ten;
	}

	
	public void setTen(String ten) {
		
		this.ten = ten;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	public List<IBangGia_Sp> getSpList() {
		
		return this.spList;
	}


	public void setSpList(List<IBangGia_Sp> spList) {
		
		this.spList = spList;
	}
	
	public void createRs()
	{
		String query = "\n select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId " +
		"\n from donvikinhdoanh a,nhacungcap_dvkd b " +
		"\n where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' " + 
		"\n order by a.donvikinhdoanh ";
		this.dvkdRs  =  this.db.get(query);

		query = "select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'";
		this.kbhRs = this.db.get(query);
		
		if (this.kbhId.trim().length() > 0 && this.dvkdId.length() > 0 && this.tungay.length( ) > 0)
		{	
			// lấy những nhà NPP chưa được chia trong bảng giá có hiệu lực từ ngày
			query = "\n SELECT distinct 0 as chietkhaunpp, a.PK_SEQ, a.Ma , a.Ten, " +
			"\n isnull(a.diachi, 'NA') as diachi, isnull(a.dienthoai, 'NA') as dienthoai  " +
			"\n from NhaPhanPhoi a" +				
			"\n where a.TrangThai = '1' " +
			"\n order by a.ten asc ";			
			System.out.println("___Khoi tao khach hang: " + query);
			this.nppRs = db.get(query);
		}
		
		String querySP = "";
		if (this.dvkdId.trim().length() > 0 && this.kbhId.trim().length() > 0 && (this.nppId.trim().length() > 0 || this.loai.equals("0")))
		{
			String banggia_fk = this.id.trim().length() <= 0 ? "0" : this.id.trim();
			if (this.loai.equals("0"))
			{
				querySP = "\n select isnull(c.chietkhau,0) as chietkhausp,a.PK_SEQ as spId, a.MA as spMa, isnull(a.TEN, '') as spTen, " +
				"\n ISNULL(b.donvi, 'NA') as donvi, isnull(c.GIABAN, 0) as giaban,isnull(c.trangthai, 0) as trangthai " +
				"\n from SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " +   
				"\n left join BANGGIAB2B_SANPHAM c on a.pk_seq = c.sanpham_fk " +
				"\n and c.BANGGIAB2B_FK = '"+banggia_fk+"'     "+
				"\n where a.DVKD_FK = '"+this.dvkdId+"' and a.trangthai = '1' " +
				"\n order by spMa asc, spTen asc  ";					
			}
			else
			{
				querySP = "\n select isnull(c.chietkhau,0) as chietkhausp,a.PK_SEQ as spId, a.MA as spMa, isnull(a.TEN, '') as spTen, " +
				"\n ISNULL(b.donvi, 'NA') as donvi, isnull(c.giaMuaNPP, 0) as giaban,isnull(c.trangthai, 0) as trangthai " +
				"\n from SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " +   
				"\n left join BGMUANPP_SANPHAM c on a.pk_seq = c.sanpham_fk " +
				"\n and c.BGMUANPP_FK = '"+banggia_fk+"' " +
				"\n where a.DVKD_FK = '"+this.dvkdId+"' and a.trangthai = '1' " + 
				"\n order by spMa asc, spTen asc  ";					
			}			
			
			System.out.println("--- Lay SP: " + querySP);		
			
			if(this.spList.size() <= 0)
			{
				ResultSet rsSp = db.get(querySP);
				if(rsSp != null)
				{
					try 
					{
						List<IBangGia_Sp> spList = new ArrayList<IBangGia_Sp>();
						
						while(rsSp.next())
						{
							IBangGia_Sp sp = new BangGia_Sp();
							sp.setIdsp(rsSp.getString("spId"));
							sp.setMasp(rsSp.getString("spMa"));
							sp.setTensp(rsSp.getString("spTen"));
							sp.setDonvi(rsSp.getString("donvi"));
							sp.setGiaban( rsSp.getDouble("giaban")==0?  "" : formater2.format(rsSp.getDouble("giaban"))  );	
							sp.setChonban(rsSp.getString("trangthai"));
							sp.setSpchietkhau(rsSp.getString("chietkhausp"));
							spList.add(sp);
						}
						rsSp.close();
						
						this.spList = spList;
					} 
					catch (Exception e) 
					{
						System.out.println("Exception Khoi Tao: " + e.getMessage());
						e.printStackTrace();
					}					
				}
			}			
		}		
	}
	
	private boolean checkExits(dbutils db) 
	{
		/*String query=
				"select count(*) as sodong from BANGGIAB2B where DVKD_FK="+this.dvkdId+" and KENH_FK="+this.kbhId+" " +
				" AND TUNGAY > '" + this.tungay + "'    ";
		
		if(this.id.length() > 0)
			query+= " and pk_seq != " + this.id +" ";
		ResultSet rs = db.get(query);
		int sodong = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			} catch(Exception e) {e.printStackTrace(); sodong = 0; }
		}
		if(sodong > 0)
			return true;*/
		return false;
	}

	
	public boolean createBanggia_B2B()
	{	
		try 
		{
			
			if(checkExits(db))
			{
				this.msg = "Ngày bắt đầu áp dụng bảng giá không được phép nhỏ hơn từ ngày áp dụng của bảng giá đang có";
				return false;
			}
			
			
			if(this.ten.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}
			
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			if(this.kbhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kênh bán hàng";
				return false;
			}
			
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong bảng giá";
				return false;
			}
			
			
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into BANGGIAB2B(ten, ngaytao, ngaysua, nguoitao, nguoisua, dvkd_fk, KENH_FK, trangthai,TuNgay) " +
							"values(N'" + this.ten + "','" + getDateTime() + "','" + getDateTime() + "','" + this.userId + "', " +
								"'" + this.userId + "','" + this.dvkdId + "','" + this.kbhId + "', '" + this.trangthai + "','"+tungay+"')";
			
			System.out.println("__Tao moi ERP_BANGGIABAN: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới BANGGIA " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rsBg = this.db.get("select IDENT_CURRENT('BANGGIAB2B') as bgbanId");
			rsBg.next();
			
			String bgbanId = rsBg.getString("bgbanId");		
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IBangGia_Sp sp = this.spList.get(i);
				String gia = sp.getGiaban().trim().length() <= 0 ? "0" : sp.getGiaban().trim().replaceAll(",", "");
				String chonban = sp.getChonban();
				if(gia.equals("0"))
					chonban = "0";
				
				query = "insert into BANGGIAB2B_SANPHAM (BANGGIAB2B_FK,SANPHAM_FK,GIABAN,trangthai,chietkhau)" +
						"values('" + bgbanId + "', '" + sp.getIdsp() + "','" +gia + "','"+chonban+"',"+sp.getSpchietkhau()+")";
					if(!this.db.update(query))
					{				
						this.msg = "Không thể tạo mới BANGGIA_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
			}
				
						
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		return true;
	}
	
	

	String tungay;
	@Override
	public String getTuNgay() 
	{
		return tungay;
	}


	@Override
	public void setTuNgay(String tungay) 
	{
		this.tungay =tungay;
	}
	
	@Override
	public ResultSet getNppRs() 
	{
		return nppRs;
	}


	@Override
	public void setNppRs(ResultSet NppRs) 
	{
		this.nppRs	= NppRs;
	}


	@Override
	public String getNppId() 
	{
		return this.nppId;
	}


	@Override
	public void setNppId(String nppId) 
	{
		this.nppId =nppId;
	}


	@Override
	public String[] getNppIdCks() {
		return this.nppIdCks;
	}


	@Override
	public void setNppIdCks(String[] ids) {
		this.nppIdCks = ids;
	}


	@Override
	public String[] getNppChietKhaus() {
		return this.nppChietKhaus;
	}


	@Override
	public void setNppChietKhaus(String[] cks) {
		this.nppChietKhaus = cks;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	
	
}
