package geso.dms.center.beans.upload.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.beans.upload.IUpload;

public class Upload implements IUpload
{
	String id, msg, nppId,nppMa, khId, userId, thang, nam, trangthai,type, ngayTao, ngaySua, nguoiTao, nguoiSua;
	dbutils db;
	ResultSet rsTonkho;
	ResultSet rsSalesIn;
	ResultSet rsForecast;
	ResultSet rsNpp, rsKh;
	ResultSet vungRs,khuvucRs,nppcotonkhoRs;
	String spNpp,khuvucId,vungId;
	String Ngaykhoaso;
	String task ;

	public Upload()
	{
		this.thang = getMonth();
		this.nam = getYear();
		this.trangthai = "";
		this.msg = "";
		this.Ngaykhoaso="";
		this.khId = "";
		this.userId = "";
		this.type="";
		this.ngaySua = "";
		this.nguoiSua = "";
		this.ngayTao = "";
		this.nguoiTao = "";
		this.spNpp = ""; 
		this.vungId="";
		this.nppId="";
		this.khuvucId="";
		this.nppMa="";
		this.task = "";
		this.db = new dbutils();
	}

	public Upload(String id)
	{
		this.id = id;
		this.thang = getMonth();
		this.nam = getYear();
		this.trangthai = "";
		this.msg = "";
		this.khId = "";
		this.userId = "";
		this.type="";
		this.ngaySua = "";
		this.nguoiSua = "";
		this.ngayTao = "";
		this.nguoiTao = "";
		this.spNpp = ""; 
		this.vungId="";
		this.nppId="";
		this.khuvucId="";
		this.nppMa="";
		this.task = "";
		this.db = new dbutils();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getKhId()
	{
		return khId;
	}

	public void setKhId(String khId)
	{
		this.khId = khId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getThang()
	{
		return thang;
	}

	public void setThang(String thang)
	{
		this.thang = thang;
	}

	public String getNam()
	{
		return nam;
	}

	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getNppId()
	{
		return nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getTrangthai()
	{
		return trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public ResultSet getRsNpp()
	{
		return rsNpp;
	}

	public void setRsNpp(ResultSet rsNpp)
	{
		this.rsNpp = rsNpp;
	}

	public ResultSet getRsKh()
	{
		return rsKh;
	}

	public void setRsKh(ResultSet rsKh)
	{
		this.rsKh = rsKh;
	}

	public void createRs()
	{

	}

	public void closeDB()
	{

	}

	public boolean SalesIn()
	{
		System.out.print("SalesIn");
		if(this.thang.equals("0")||this.nam.equals("0"))
		{
			this.msg="Vui lòng chọn thời gian";
			return false;
		}		
		String thoigian=this.nam+'-'+this.thang+"-01";
		if(this.thang.length()<=1)
		{
			thoigian=this.nam+"-0"+this.thang+"-01";
		}
		try
		{
			this.db.getConnection().setAutoCommit(false);
			String query = " delete from SalesIn_Details where SalesIn_FK =(select pk_Seq from SalesIn where thang='"+this.thang+"' and Nam='" + this.nam + "')";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			query = " delete from SalesIn where thang='"+this.thang+"' and Nam='" + this.nam + "' ";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Lỗi hệ thống "+query;
				return false;
			}

			query = "insert into SalesIn(ThoiGian,Thang,Nam,NguoiTao,NgayTao,NguoiSua,NgaySua,TrangThai) values" + " ('"+thoigian+"','" + this.thang + "','" + this.nam + "','" + this.userId + "','" + this.getDateTime()
					+ "','" + this.userId + "','" + this.getDateTime() + "',0 )";
			if (!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Lỗi hệ thống " + query;
				return false;
			}

			query = "select IDENT_CURRENT('SalesIn') as dmhId";
			ResultSet rs = db.get(query);						
			if(rs.next())
			{
				this.id = rs.getString("dmhId");
				rs.close();
			}
			query=
					"select SUM(soluong) as soluong,manpp,MaSp,spTen,NppId,spId\n  "+ 
							" from \n  "+ 
							"( \n  "+ 
							"	 select MaSp,spTen,(select pk_Seq from sanpham where ma=masp) as spId,maNpp,soluong,\n  "+ 
							"		(select pk_seq from nhaphanphoi where ma=manpp)as NppId \n  "+ 
							"	from SalesIn_Tmp\n  "+ 
							")as tmp\n  "+ 
							"group by manpp,MaSp,spTen,NppId,spId\n  "+ 
							"order by NppId ";
			System.out.println("[SalesIn] "+query);
			rs=this.db.get(query);
			while(rs.next())
			{
				String spId=rs.getString("spId");
				String spMa=rs.getString("MaSp");
				String spTen=rs.getString("spTen");
				String soluong=rs.getString("soluong");
				String nppMa=rs.getString("maNpp");
				String nppId=rs.getString("nppId");
				if(spId==null&&nppId!=null)
				{
					this.db.getConnection().rollback();
					this.msg+="Sản phẩm mã "+spMa +"--tên " +spTen +" chưa có trong hệ thống \n";
				}
				/*if(nppId==null)
				{
					this.db.getConnection().rollback();
					this.msg+="Store mã "+nppMa +" chưa có trong hệ thống \n";
				}*/
				if(spId!=null&&nppId!=null)
				{		
					int sodong=0;
					query="select count(*) as numb from SalesIn_Details where SalesIn_FK='"+this.id+"' and npp_fk='"+nppId+"' and SanPham_FK='"+spId+"'";
					ResultSet rsCheck=this.db.get(query);
					if(rsCheck!=null)
					{
						rsCheck.next();
						sodong=rsCheck.getInt(1);

						rsCheck.close();
					}
					if(sodong>0)
					{
						this.db.getConnection().rollback();
						this.msg += "Sản phẩm"+spTen +" đã có trong file upload của Store "+nppMa ;
						return false;
					}			

					query="insert into SalesIn_Details(SalesIn_FK,SanPham_FK,Soluong,Npp_fk)" +
							" select '"+this.id+"','"+spId+"','"+soluong+"','"+nppId+"' ";
					if (!this.db.update(query))
					{
						this.db.getConnection().rollback();
						this.msg = "Lỗi hệ thống " + query;
						return false;
					}
				}
			}
			rs.close();
			if(this.msg.length()>0)
			{
				this.db.getConnection().rollback();
				return false;
			}			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return true;
	}


	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@Override
	public boolean Stock()
	{
		System.out.print("Stock");

		if(this.thang.equals("0")||this.nam.equals("0"))
		{
			this.msg="Vui lòng chọn thời gian";
			return false;
		}
		String thoigian=this.nam+'-'+this.thang+"-01";
		if(this.thang.length()<=1)
		{
			thoigian=this.nam+"-0"+this.thang+"-01";
		}
		try
		{
			String query="select TrangThai from TonKho where ThoiGian='"+thoigian+"'";
			ResultSet rs=this.db.get(query);
			int trangthai=0;
			if(rs!=null)
			{
				while(rs.next())
				{
					trangthai=rs.getInt(1);
				}
				rs.close();
			}
			if(trangthai==1)
			{
				this.msg="Tồn kho "+thoigian +" đã chốt !Vui lòng chọn thời gian khác ";
				return false;
			}

			query=
					"select COUNT(*) as sodong,nppMa,spMa "+
							"from TonKho_Tmp "+
							"group by nppMa,spMa "+
							"having COUNT(*)>1 ";
			rs=this.db.get(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					this.msg+=" Trùng mã sản phẩm "+rs.getString("spMa")+" của Store "+rs.getString("nppMa");
					return false;
				}
				rs.close();
			}

			query=
					"select distinct spMa "+
							"from TonKho_Tmp  "+
							"where spMa not in "+
							"(select ma from SANPHAM) ";
			rs=this.db.get(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					this.msg+="Sản phẩm "+rs.getString("spMa")+" chưa có trong hệ thống ";
					return false;
				}
				rs.close();
			}

			query=	
					"	select nppMa " +
							"	from TonKho_Tmp   "+
							"	where nppMa not in "+
							"	(select  ma from NHAPHANPHOI)  ";
			rs=this.db.get(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					this.msg+="Store "+rs.getString("nppMa")+" chưa có trong hệ thống ";
					return false;
				}
				rs.close();
			}				

			query=
					" SELECT A.NHAP,TT.SOLUONG,ISNULL(TT.SANPHAM_FK,A.SPMA) AS SANPHAM_FK,A.nppMa,T.TEN AS TONGTHAU,T.MA AS TONGTHAUMA  "+
							"		FROM TONKHO_TMP A INNER JOIN NHAPHANPHOI B ON A.NPPMA=B.MA INNER JOIN SANPHAM C ON C.MA=A.SPMA 	 "+
							"		INNER JOIN NHAPHANPHOI T ON T.PK_SEQ=B.TongThau_FK 	 "+
							"	FULL OUTER JOIN "+ 
							"	(  "+
							"		SELECT SUM(NHSP.SOLUONG) AS SOLUONG,NH.NPP_FK AS TONGTHAUID,NHSP.SANPHAM_FK "+
							"		FROM NHAPHANG NH INNER JOIN  "+
							"		NHAPHANG_SP NHSP ON NHSP.NHAPHANG_FK=NH.PK_SEQ	 "+
							"			INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=NH.NPP_FK AND NPP.TONGTHAU_FK IS NULL "+ 
							"			INNER JOIN NHAPP_KBH C ON C.NPP_FK=NPP.PK_SEQ "+
							"		WHERE C.KBH_FK=100052 AND NH.NGAYCHUNGTU LIKE '"+this.nam+"-"+(this.thang.length()<=1?"0"+this.thang:this.thang+"%")+"'"+			
							"		GROUP BY NH.NPP_FK,NHSP.SANPHAM_FK "+
							"	) AS TT ON TT.TONGTHAUID=B.TongThau_FK AND A.spMa=TT.SANPHAM_FK "+
							"	WHERE ISNULL( A.NHAP,0) > ISNULL(TT.SOLUONG,0) ";

			System.out.print("Kiem tra TongNhap]"+query);

			rs=this.db.get(query);
			while(rs.next())
			{
				this.msg+="Số lượng nhập của sản phẩm ["+rs.getString("sanpham_Fk")+"] " +rs.getDouble("Nhap")+ "  Store [ "+rs.getString("nppMa")+"] lớn hơn tổng nhập của tổng thầu ["+rs.getDouble("SoLuong")+"] \n";
			}
			rs.close();
			if(this.msg.length()>0)
			{
				return false;
			}

			this.db.getConnection().setAutoCommit(false);
			query = " delete from TonKho_NPP where tonkho_fk =(select pk_Seq from tonkho where thang='" + this.thang + "'  and Nam='" + this.nam + "')";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			query = " delete from TonKho where thang='" + this.thang + "'  and Nam='" + this.nam + "'";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			query = "insert into TonKho(ThoiGian,Thang,Nam,NguoiTao,NgayTao,NguoiSua,NgaySua,TrangThai) values" + 
					" ('"+thoigian+"','" + this.thang + "','" + this.nam + "','" + this.userId + "','" + this.getDateTime()+ "','" + this.userId + "','" + this.getDateTime() + "',0 )";
			if (!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Lỗi hệ thống " + query;
				return false;
			}

			query = "select IDENT_CURRENT('TonKho') as dmhId";
			rs = db.get(query);						
			if(rs.next())
			{
				this.id = rs.getString("dmhId");
				rs.close();
			}

			query =
					" select b.PK_SEQ as nppId,a.nppMa,c.PK_SEQ as spId,a.spMa,a.TonDau,a.TonCuoi,a.Nhap,d.toncuoi as tondauht,b.ten as nppTen "+ 
							" from TONKHo_tmp a left join NHAPHANPHOI b on a.nppMa=b.MA "+
							" left join SANPHAM c on c.ma=a.spMa " +
							"left join " +
							" ( " +
							"	select toncuoi,sanpham_fk,npp_fk from tonkho a inner join tonkho_npp b on a.pk_seq=b.tonkho_fk "+
							"	where a.ThoiGian=(select convert(varchar(10),DATEADD(month,-1,'"+thoigian+"'),20) ) "+
							") d on c.pk_seq=d.sanpham_fk and b.pk_seq=d.npp_fk ";

			System.out.println("Query TonKho "+query);
			rs=this.db.get(query);
			while(rs.next())
			{
				String spId=rs.getString("spId");
				String spMa=rs.getString("spMa");

				String nppMa=rs.getString("nppMa");
				String nppTen=rs.getString("nppTen");
				String nppId=rs.getString("nppId");

				float tondauHt=rs.getFloat("tondauht");
				float tondau=rs.getFloat("tondau");
				float nhap=rs.getFloat("nhap");
				float toncuoi=rs.getFloat("toncuoi");
				float ban=tondau+nhap-toncuoi;
				if(ban<0)
				{
					this.msg="Xuất nhập không đúng thực tế Store "+nppMa +" của sản phẩm "+spMa;
					this.db.getConnection().rollback();
					return false;
				}

				if( (Float)rs.getFloat("Tondau")!=null && tondau!=tondau)
				{
					this.msg+="Tồn đầu hệ thống "+tondauHt +" khác tồn trong file "+tondau +" sản phẩm "+spMa + " của Store "+nppMa +"\n";
					this.db.getConnection().rollback();
					return false;
				}
				query="insert into TonKho_NPP(TonKho_fk,SanPham_FK,Npp_fk,TonDau,Nhap,TonCuoi,Ban)" 
						+" select '"+this.id+"','"+spId+"','"+nppId+"','"+tondau+"','"+nhap+"','"+toncuoi+"',"+ban+" ";
				if (!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg = "Lỗi hệ thống " + query;
					return false;
				}
			}
			rs.close();
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			try 
			{
				this.db.getConnection().rollback();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			this.db.shutDown();
			this.msg="Lỗi hệ thống  "+e.getMessage();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public String getType()
	{
		return this.type;
	}

	@Override
	public void setType(String type)
	{
		this.type=type;

	}

	@Override
	public void setNguoiTao(String nguoitao) {
		this.nguoiTao = nguoitao;
	}

	@Override
	public String getNguoiTao() {
		return this.nguoiTao;
	}

	@Override
	public void setNguoiSua(String nguoisua) {
		this.nguoiSua = nguoisua;
	}

	@Override
	public String getNguoiSua() {
		return this.nguoiSua;
	}

	@Override
	public void setNgayTao(String ngaytao) {
		this.ngayTao = ngaytao;
	}

	@Override
	public String getNgayTao() {
		return this.ngayTao;
	}

	@Override
	public void setNgaySua(String ngaySua) {
		this.ngaySua = ngaySua;
	}

	@Override
	public String getNgaySua() {
		return ngaySua;
	}

	@Override
	public void setSpNpp(String value) {
		this.spNpp = value;
	}

	@Override
	public String getSpNpp() {
		return this.spNpp;
	}

	@Override
	public void createRs_Tonkho()
	{
		String query=
				"select a.pk_seq as tonkhoId,a.thang,a.nam,b.ten as nguoitao,a.ngaytao,a.trangthai "+ 
						"from tonkho a inner join nhanvien b "+
						"on b.pk_seq =a.nguoitao ";
		this.rsTonkho=this.db.get(query);
		System.out.println("Init tonkho" +query);
	}

	@Override
	public ResultSet getRsTonkho()
	{
		return this.rsTonkho;
	}

	@Override
	public void setRsTonkho(ResultSet rsTonkho)
	{
		this.rsTonkho=rsTonkho;
	}

	public ResultSet getRsSalesIn()
	{
		return rsSalesIn;
	}

	public void setRsSalesIn(ResultSet rsSalesIn)
	{
		this.rsSalesIn=rsSalesIn;
	}

	public void createRs_SalesIn()
	{
		String query=
				"select a.pk_seq salesId,a.thang,a.nam,b.ten as nguoitao,a.ngaytao from salesIn a "+
						"inner join nhanvien b on b.pk_seq=a.nguoitao ";
		this.rsSalesIn=this.db.get(query);
		System.out.print("Init tonkho" +query);
	}

	@Override
	public ResultSet getRsForeCast()
	{
		return this.rsForecast;
	}


	public void setRsForeCast(ResultSet rsforecast)
	{
		this.rsForecast=rsforecast;

	}


	public void createRs_ForeCast()
	{
		String query=
				"select a.pk_seq forecastId,a.thang,a.nam,b.ten as nguoitao,a.ngaytao from SalesForeCast a "+
						"inner join nhanvien b on b.pk_seq=a.nguoitao ";
		this.rsForecast=this.db.get(query);
		System.out.print("Init createRs_ForeCast" +query);
	}


	private String getMonth() 
	{
		DateFormat dateFormat = new SimpleDateFormat("MM");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private String getYear() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return dateFormat.format(date);	
	}


	/***  PHẦN THÊM BẰNG CHƯƠNG TRÌNH   ***/	

	public boolean checkThangNam() {
		if(this.thang != null && this.nam !=  null) {
			String query = "select COUNT(*) as num from TonKho where Thang="+this.thang+" and Nam="+this.nam;//+" and TrangThai=1";
			if(this.id.length()>0)
				query+=" and pk_Seq!='"+this.id+"'";
			System.out.println("[Upload.checkThangNam] query = " + query);
			try {
				ResultSet rs = this.db.get(query);
				rs.next();

				String num = rs.getString("num");
				System.out.println("[Upload.checkThangNam] number = " + num);

				if(num.equals("0")) {
					rs.close();
					return true;
				}

				rs.close();
			} catch (SQLException e) {
				System.out.println("[Upload.checkThangNam] Exception Message = " + e.getMessage());
			}
		}

		return false;
	}

	public void init()
	{

	}

	public String createSpNpp() 
	{
		String result = "";

		String _thang = "00";
		if(this.thang != null) {
			_thang = this.thang.trim().length() < 2 ? "0" + this.thang.trim() : this.thang.trim();
		}

		if(this.id == null || this.id.trim().length() == 0) 
		{
			if(thang != null && nam != null) {
				String query = 
						" select a.nppId ,a.nppMa,a.nppTen,a.spId,a.spMa,a.spTen,ISNULL(tk.TonCuoi,0) as TonDau,'' as Nhap,'' as Ban,'' as TonCuoi " +
								" from " +
								" (" +
								"	select sp.pk_seq as spId,sp.MA as spMa,sp.Ten as spTen,npp.PK_SEQ as nppId,npp.TEN as nppTen,npp.MA as nppMa " +
								"	from " +
								"	(	select * from " +
								"		SANPHAM sp " +
								"		where TRANGTHAI=1 " +
								"	)as sp ," +
								"	(" +
								"		select * from NHAPHANPHOI a inner join NHAPP_KBH b on a.PK_SEQ=b.NPP_FK" +
								"		where KBH_FK=100052 and a.TongThau_FK is not null" +
								"		and NPP_FK in (select NPP_FK from PHAMVIHOATDONG where Nhanvien_fk='"+this.userId+"') and a.trangthai=1 " +
								"	) as npp " +
								" ) as a	" +
								" left join " +
								" (	" +
								"	select * " +
								"	from  TonKho a inner join TonKho_NPP b on a.PK_SEQ=b.TonKho_FK" +
								"	where ThoiGian=(select convert(varchar(10),DATEADD(month,-1,'"+this.nam+"-"+_thang+"-01'),20) )" +
								" ) as tk on tk.NPP_FK=a.nppId and tk.SanPham_FK=a.spId " ;
				System.out.println("[Upload.createSpNpp] query = " + query);

				ResultSet rs = db.get(query);
				try {
					//Idea pattern : nppId1,nppTen1#sp1Id,sp1Ten,sp1TonDau|sp2Id,sp2Ten,sp2TonDau...;nppId2,nppTen2#sp1Id,sp1Ten,sp1TonDau|sp2Id,sp2Ten,sp2TonDau...

					rs.next();
					String currentNppId = rs.getString("nppId");
					String currentNppTen = rs.getString("nppTen");
					String currentSpId = rs.getString("spId");
					String currentSpMa = rs.getString("spMa");
					String currentSpTen = rs.getString("spTen");
					String currentTonDau = rs.getString("TonDau");

					String str = currentNppId + "," + currentNppTen + "#" + currentSpId + "," + currentSpMa + "," + currentSpTen + "," + currentTonDau + ",,";
					result += str;
					while(rs.next()) {
						String nextNppId = rs.getString("nppId");
						if(nextNppId.trim().equals(currentNppId.trim())) {
							//Cùng npp, chỉ cần thêm sản phẩm
							currentSpId = rs.getString("spId");
							currentSpMa = rs.getString("spMa");
							currentSpTen = rs.getString("spTen");
							currentTonDau = rs.getString("TonDau");
							str = "|" + currentSpId + "," + currentSpMa + "," + currentSpTen + "," + currentTonDau + ",,";
							result += str;
						} else {
							//Khác npp, tạo npp mới cùng sản phẩm của dòng đó
							currentNppId = rs.getString("nppId");
							currentNppTen = rs.getString("nppTen");
							currentSpId = rs.getString("spId");
							currentSpMa = rs.getString("spMa");
							currentSpTen = rs.getString("spTen");
							currentTonDau = rs.getString("TonDau");
							str = ";" + currentNppId + "," + currentNppTen + "#" + currentSpId + "," + currentSpMa + "," + currentSpTen + "," + currentTonDau + ",,";
							result += str;
						}
					}
					rs.close();
				} catch (SQLException e) {
					System.out.println("[Upload.createSpNpp] Exception Message = " + e.getMessage());
				}

				this.spNpp = result;

			}
		} else {
			//Add TONKHO information from database
			String query = "select * from TONKHO where PK_SEQ = '" + this.id + "'";
			System.out.println("[Upload.createSpNpp] query = " + query);
			ResultSet rs = db.get(query);
			try {
				rs.next();
				this.thang = rs.getString("THANG");
				this.nam = rs.getString("NAM");				

				query = 
						"select AA.nppId,AA.nppMa,AA.nppTen,AA.spId,AA.spMa,AA.spTen,SUM(AA.TonDau) as TonDau,SUM(aa.Nhap) as Nhap,SUM(aa.Ban)as Ban,SUM(aa.TonCuoi) as TonCuoi from " +
								" (" +
								" 	select c.PK_SEQ as nppId,c.MA as nppMa,c.TEN as nppTen,d.PK_SEQ as spId,d.MA as spMa,d.TEN as spTen,isnull(b.TonDau,0)as TonDau,ISNULL(b.Nhap,0) as Nhap,ISNULL(b.Ban,0) AS Ban ,ISNULL(b.TonCuoi,0)as TonCuoi " +
								" 	from TonKho a inner join TonKho_NPP b on b.TonKho_FK=a.PK_SEQ " +
								" 		inner join NHAPHANPHOI c  on c.PK_SEQ=b.NPP_FK " +
								" 		right join SANPHAM d on d.PK_SEQ=b.SanPham_FK " +
								" 		where c.PK_SEQ in(select NPP_FK from PHAMVIHOATDONG where Nhanvien_fk='"+this.userId+"') and a.PK_SEQ='"+this.id+"'" +
								" 		and a.PK_SEQ='"+this.id+"' and d.TRANGTHAI=1" +
								" 	union all" +
								" " +
								" 	select a.nppId ,a.nppMa,a.nppTen,a.spId,a.spMa,a.spTen,ISNULL(tk.TonCuoi,0) as TonDau,'' as Nhap,'' as Ban,'' as TonCuoi " +
								" 	from " +
								" 	(" +
								" 		select sp.pk_seq as spId,sp.MA as spMa,sp.Ten as spTen,npp.PK_SEQ as nppId,npp.TEN as nppTen,npp.MA as nppMa" +
								" 		from " +
								" 		(	select * from " +
								" 			SANPHAM sp" +
								" 			where TRANGTHAI=1" +
								" 		)as sp ," +
								" 		(" +
								" 			select * from NHAPHANPHOI a inner join NHAPP_KBH b on a.PK_SEQ=b.NPP_FK" +
								" 			where KBH_FK=100052 and a.TongThau_FK is not null" +
								" 			and NPP_FK in (select NPP_FK from PHAMVIHOATDONG where Nhanvien_fk="+this.userId+")" +
								" 		)as npp" +
								" 	)as a	" +
								" 	left join " +
								" 	(	" +
								" 		select * " +
								" 		from  TonKho a inner join TonKho_NPP b on a.PK_SEQ=b.TonKho_FK" +
								" 		where ThoiGian=(select convert(varchar(10),DATEADD(month,-1,'"+this.nam+"-"+_thang+"-01'),20) )" +
								" 	)as tk on tk.NPP_FK=a.nppId and tk.SanPham_FK=a.spId	" +
								" ) AA" +
								" group by AA.nppId,AA.nppMa,AA.nppTen,AA.spId,AA.spMa,AA.spTen " +
								" order by AA.nppId, AA.spId ";
				System.out.println("[Upload.createSpNpp] query = " + query);

				ResultSet rs1 = db.get(query);
				try {
					//Idea pattern : nppId1,nppTen1#sp1Id,sp1Ten,sp1TonDau|sp2Id,sp2Ten,sp2TonDau...;nppId2,nppTen2#sp1Id,sp1Ten,sp1TonDau|sp2Id,sp2Ten,sp2TonDau...
					rs1.next();
					String currentNppId = rs1.getString("nppId");
					String currentNppTen = rs1.getString("nppTen"); if(currentNppTen == null) currentNppTen = ""; else currentNppTen = currentNppTen.trim().replace(",", " ").replace("|", " ").replace("#", " ");
					String currentSpId = rs1.getString("spId");
					String currentSpMa = rs1.getString("spMa");
					String currentSpTen = rs1.getString("spTen");
					String currentTonDau = rs1.getString("TonDau");
					String currentNhap = rs1.getString("Nhap"); if(currentNhap.equals("0.0")) currentNhap = "";
					String currentTonCuoi = rs1.getString("TonCuoi"); if(currentTonCuoi.equals("0.0")) currentTonCuoi = "";

					String str = currentNppId + "," + currentNppTen + "#" + currentSpId + "," + currentSpMa + "," + currentSpTen + "," + currentTonDau + "," + currentNhap + "," + currentTonCuoi;
					result += str;
					while(rs1.next()) {
						String nextNppId = rs1.getString("nppId");
						if(nextNppId.trim().equals(currentNppId.trim())) {
							//Cùng npp, chỉ cần thêm sản phẩm
							currentSpId = rs1.getString("spId");
							currentSpMa = rs1.getString("spMa");
							currentSpTen = rs1.getString("spTen");
							currentTonDau = rs1.getString("TonDau");
							currentNhap = rs1.getString("Nhap");  if(currentNhap.equals("0.0")) currentNhap = "";
							currentTonCuoi = rs1.getString("TonCuoi");	 if(currentTonCuoi.equals("0.0")) currentTonCuoi = "";
							str = "|" + currentSpId + "," + currentSpMa + "," + currentSpTen + "," + currentTonDau + "," + currentNhap + "," + currentTonCuoi;
							result += str;
						} else {
							//Khác npp, tạo npp mới cùng sản phẩm của dòng đó
							currentNppId = rs1.getString("nppId");
							currentNppTen = rs1.getString("nppTen"); if(currentNppTen == null) currentNppTen = ""; else currentNppTen = currentNppTen.trim().replace(",", " ").replace("|", " ").replace("#", " ");
							currentSpId = rs1.getString("spId");
							currentSpMa = rs1.getString("spMa");
							currentSpTen = rs1.getString("spTen");
							currentTonDau = rs1.getString("TonDau");
							currentNhap = rs1.getString("Nhap");  if(currentNhap.equals("0.0")) currentNhap = "";
							currentTonCuoi = rs1.getString("TonCuoi");	 if(currentTonCuoi.equals("0.0")) currentTonCuoi = "";
							str = ";" + currentNppId + "," + currentNppTen + "#" + currentSpId + "," + currentSpMa + "," + currentSpTen + "," + currentTonDau + "," + currentNhap + "," + currentTonCuoi;
							result += str;
						}
					}
					rs1.close();
					this.spNpp = result;
					/*while(rs1.next()) {
						String currentNppId = rs1.getString("nppId");
						String currentNppTen = rs1.getString("nppTen");
						String currentSpId = rs1.getString("spId");
						String currentSpMa = rs1.getString("spMa");
						String currentSpTen = rs1.getString("spTen");
						String currentTonDau = rs1.getString("TonDau");
						String currentNhap = rs1.getString("Nhap"); if(currentNhap.equals("0.0")) currentNhap = "";
						String currentTonCuoi = rs1.getString("TonCuoi"); if(currentTonCuoi.equals("0.0")) currentTonCuoi = "";

						String str = currentNppId + "," + currentNppTen + "#" + currentSpId + "," + currentSpMa + "," + currentSpTen + "," + currentTonDau + "," + currentNhap + "," + currentTonCuoi;
						result += str;
						while(rs1.next()) {
							String nextNppId = rs1.getString("nppId");
							if(nextNppId.trim().equals(currentNppId.trim())) {
								//Cùng npp, chỉ cần thêm sản phẩm
								currentSpId = rs1.getString("spId");
								currentSpMa = rs1.getString("spMa");
								currentSpTen = rs1.getString("spTen");
								currentTonDau = rs1.getString("TonDau");
								currentNhap = rs1.getString("Nhap");  if(currentNhap.equals("0.0")) currentNhap = "";
								currentTonCuoi = rs1.getString("TonCuoi");	 if(currentTonCuoi.equals("0.0")) currentTonCuoi = "";
								str = "|" + currentSpId + "," + currentSpMa + "," + currentSpTen + "," + currentTonDau + "," + currentNhap + "," + currentTonCuoi;
								result += str;
							} else {
								//Khác npp, tạo npp mới cùng sản phẩm của dòng đó
								currentNppId = rs1.getString("nppId");
								currentNppTen = rs1.getString("nppTen");
								currentSpId = rs1.getString("spId");
								currentSpMa = rs1.getString("spMa");
								currentSpTen = rs1.getString("spTen");
								currentTonDau = rs1.getString("TonDau");
								currentNhap = rs1.getString("Nhap");  if(currentNhap.equals("0.0")) currentNhap = "";
								currentTonCuoi = rs1.getString("TonCuoi");	 if(currentTonCuoi.equals("0.0")) currentTonCuoi = "";
								str = ";" + currentNppId + "," + currentNppTen + "#" + currentSpId + "," + currentSpMa + "," + currentSpTen + "," + currentTonDau + "," + currentNhap + "," + currentTonCuoi;
								result += str;
							}
						}
						rs1.close();

						this.spNpp = result;
					}*/
				}
				catch(Exception e) {

				}
				rs.close();
			} catch(Exception e) {

			}

		}
		System.out.println("[Upload.createSpNpp] result = " + result);

		return result;
	}

	@Override
	public boolean create() {
		System.out.println("________Create________");
		try {

			this.db.getConnection().setAutoCommit(false);

			this.ngayTao = getDateTime();
			this.nguoiTao = this.userId;

			String query = "";

			String _thang = "00";
			if(this.thang != null) {
				_thang = this.thang.trim().length() < 2 ? "0" + this.thang.trim() : this.thang.trim();
			}

			String thoigian=this.nam+'-'+_thang+"-01";



			//Insert table TONKHO
			query = "INSERT INTO TONKHO(ThoiGian,Thang,Nam,NguoiTao,NgayTao,NguoiSua,NgaySua,TrangThai) values" + 
					" ('"+thoigian+"','" + this.thang + "','" + this.nam + "','" + this.userId + "','" + this.getDateTime()+ "','" + this.userId + "','" + this.getDateTime() + "',0 )";
			if (!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Lỗi hệ thống " + query;
				return false;
			}

			query = "select IDENT_CURRENT('TonKho') as dmhId";
			ResultSet rs = db.get(query);						
			if(rs.next())
			{
				this.id = rs.getString("dmhId");
				rs.close();
			}

			//Insert table TONKHO_SANPHAM

			//Split this.spNpp to separate string to get data
			if(this.spNpp == null) { this.spNpp = ""; }
			else { this.spNpp = this.spNpp.trim(); }
			if(this.spNpp.length() > 0) {

				String[] npps = this.spNpp.split(";");
				for(int i = 0; i < npps.length; i++) {
					String nppData = npps[i].trim();
					if(nppData.length() > 0) {
						String[] data = nppData.split("#");
						System.out.println("[Upload.create] nppInfos = " + data[0]);
						System.out.println("[Upload.create] spInfos = " + data[1]);
						String[] nppInfos = data[0].split(",");
						String[] sps = data[1].split("\\|");
						System.out.println("[Upload.create] sps = " + sps.length);

						for(int j = 0; j < sps.length; j++) {
							String spInfos = sps[j].trim();
							System.out.println("[Upload.create] spInfo[" + j + "] = " + spInfos);

							if(spInfos.length() > 0) {
								String[] spData = spInfos.split(",");
								int tondau, toncuoi, nhap, ban;

								try { tondau = (int)Float.parseFloat(spData[3].trim()); } 
								catch(Exception e) { 
									tondau = 0;
								}
								try { nhap = (int)Float.parseFloat(spData[4].trim()); } 
								catch(Exception e) { 
									nhap = 0;
								}
								try { toncuoi = (int)Float.parseFloat(spData[5].trim()); } 
								catch(Exception e) { 
									toncuoi = 0;
								}
								ban = tondau + nhap - toncuoi;

								query = " insert into TonKho_NPP(TonKho_fk,SanPham_FK,Npp_fk,TonDau,Nhap,TonCuoi,Ban) " 
										+	" select '"+this.id+"', '"+spData[0].trim()+"', '"+nppInfos[0].trim()+"', '"+tondau+"', '"+nhap+"', '"+toncuoi+"',"+ban+" ";

								if (!this.db.update(query))
								{
									this.db.getConnection().rollback();
									this.msg = "Lỗi hệ thống " + query;
									return false;
								}
							}
						}
					}					
				}

				query=
						" SELECT A.NHAP,TT.SOLUONG,ISNULL(TT.SANPHAM_FK,c.MA) AS SANPHAM_FK,B.MA AS nppMa,T.TEN AS TONGTHAU,T.MA AS TONGTHAUMA  "+
								"		FROM TonKho_NPP  A INNER JOIN NHAPHANPHOI B ON A.NPP_FK=B.PK_SEQ INNER JOIN SANPHAM C ON C.PK_SEQ=A.SANPHAM_FK 	 "+
								"		INNER JOIN NHAPHANPHOI T ON T.PK_SEQ=B.TongThau_FK 	 "+
								"	FULL OUTER JOIN "+ 
								"	(  "+
								"		SELECT SUM(NHSP.SOLUONG) AS SOLUONG,NH.NPP_FK AS TONGTHAUID,NHSP.SANPHAM_FK "+
								"		FROM NHAPHANG NH INNER JOIN  "+
								"		NHAPHANG_SP NHSP ON NHSP.NHAPHANG_FK=NH.PK_SEQ	 "+
								"			INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=NH.NPP_FK AND NPP.TONGTHAU_FK IS NULL "+ 
								"			INNER JOIN NHAPP_KBH C ON C.NPP_FK=NPP.PK_SEQ "+
								"		WHERE C.KBH_FK=100052 AND NH.NGAYCHUNGTU LIKE '"+this.nam+"-"+(this.thang.length()<=1?"0"+this.thang:this.thang+"%")+"'"+			
								"		GROUP BY NH.NPP_FK,NHSP.SANPHAM_FK "+
								"	) AS TT ON TT.TONGTHAUID=B.TongThau_FK AND c.ma=TT.SANPHAM_FK "+
								"	WHERE ISNULL( A.NHAP,0) > ISNULL(TT.SOLUONG,0) AND A.TonKho_fk='"+this.id+"'";

				System.out.print("Kiem tra TongNhap]"+query);

				rs=this.db.get(query);
				while(rs.next())
				{
					this.msg+="Số lượng nhập của sản phẩm ["+rs.getString("sanpham_Fk")+"] " +rs.getDouble("Nhap")+ "  Store [ "+rs.getString("nppMa")+"] lớn hơn tổng nhập của tổng thầu ["+rs.getDouble("SoLuong")+"] \n";
				}
				rs.close();
				if(this.msg.length()>0)
				{
					this.db.getConnection().rollback();
					System.out.println("[SQL]"+query);
					return false;
				}
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			} else {
				System.out.println("[Upload.create] spNpp string is empty! ");
				return false;
			}
		}
		catch(Exception e) {
			this.msg = "Xảy ra lỗi khi tạo mới nhiệm vụ (Lỗi: " + e.getMessage() + ")";
			System.out.println("[Upload.create] Exception Message = " + e.getMessage());
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return false;
		}

		System.out.println("[Upload.create] -> Created successfully!");
		return true;

	}

	@Override
	public boolean update() {
		System.out.println("________Update________");
		try {
			String query = "";
			this.db.getConnection().setAutoCommit(false);			
			this.ngaySua = getDateTime();

			query = "UPDATE TONKHO SET NguoiSua='" + this.userId + "', NgaySua = '" + getDateTime() +"' where PK_SEQ = '" + this.id + "'";
			if (!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Lỗi hệ thống: " + query;
				return false;
			}

			//Xóa dữ liệu cũ
			query = "DELETE FROM TONKHO_NPP WHERE TONKHO_FK = '" + this.id + "' AND NPP_FK IN (select NPP_FK from PHAMVIHOATDONG where Nhanvien_fk="+this.userId+")";
			if (!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Lỗi hệ thống: " + query;
				return false;
			}

			//Thêm dữ liệu mới

			//Split this.spNpp to separate string to get data
			if(this.spNpp == null) { this.spNpp = ""; }
			else { this.spNpp = this.spNpp.trim(); }
			if(this.spNpp.length() > 0) {

				String[] npps = this.spNpp.split(";");
				for(int i = 0; i < npps.length; i++) {
					String nppData = npps[i].trim();
					if(nppData.length() > 0) {
						String[] data = nppData.split("#");
						System.out.println("[Upload.create] nppInfos = " + data[0]);
						System.out.println("[Upload.create] spInfos = " + data[1]);
						String[] nppInfos = data[0].split(",");
						String[] sps = data[1].split("\\|");
						System.out.println("[Upload.create] sps = " + sps.length);

						for(int j = 0; j < sps.length; j++) {
							String spInfos = sps[j].trim();
							System.out.println("[Upload.create] spInfo[" + j + "] = " + spInfos);

							if(spInfos.length() > 0) {
								String[] spData = spInfos.split(",");
								int tondau, toncuoi, nhap, ban;

								try { tondau = (int)Float.parseFloat(spData[3].trim()); } 
								catch(Exception e) { 
									tondau = 0;
								}
								try { nhap = (int)Float.parseFloat(spData[4].trim()); } 
								catch(Exception e) { 
									nhap = 0;
								}
								try { toncuoi = (int)Float.parseFloat(spData[5].trim()); } 
								catch(Exception e) { 
									toncuoi = 0;
								}
								ban = tondau + nhap - toncuoi;

								query = " insert into TonKho_NPP(TonKho_fk,SanPham_FK,Npp_fk,TonDau,Nhap,TonCuoi,Ban) " 
										+	" select '"+this.id+"', '"+spData[0].trim()+"', '"+nppInfos[0].trim()+"', '"+tondau+"', '"+nhap+"', '"+toncuoi+"',"+ban+" ";

								if (!this.db.update(query))
								{
									this.db.getConnection().rollback();
									this.msg = "Lỗi hệ thống " + query;
									return false;
								}
							}
						}
					}					
				}

				query=
						" SELECT A.NHAP,TT.SOLUONG,ISNULL(TT.SANPHAM_FK,c.MA) AS SANPHAM_FK,B.MA AS nppMa,T.TEN AS TONGTHAU,T.MA AS TONGTHAUMA  "+
								"		FROM TonKho_NPP  A INNER JOIN NHAPHANPHOI B ON A.NPP_FK=B.PK_SEQ INNER JOIN SANPHAM C ON C.PK_SEQ=A.SANPHAM_FK 	 "+
								"		INNER JOIN NHAPHANPHOI T ON T.PK_SEQ=B.TongThau_FK 	 "+
								"	FULL OUTER JOIN "+ 
								"	(  "+
								"		SELECT SUM(NHSP.SOLUONG) AS SOLUONG,NH.NPP_FK AS TONGTHAUID,NHSP.SANPHAM_FK "+
								"		FROM NHAPHANG NH INNER JOIN  "+
								"		NHAPHANG_SP NHSP ON NHSP.NHAPHANG_FK=NH.PK_SEQ	 "+
								"			INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=NH.NPP_FK AND NPP.TONGTHAU_FK IS NULL "+ 
								"			INNER JOIN NHAPP_KBH C ON C.NPP_FK=NPP.PK_SEQ "+
								"		WHERE C.KBH_FK=100052 AND NH.NGAYCHUNGTU LIKE '"+this.nam+"-"+(this.thang.length()<=1?"0"+this.thang:this.thang+"%")+"'"+			
								"		GROUP BY NH.NPP_FK,NHSP.SANPHAM_FK "+
								"	) AS TT ON TT.TONGTHAUID=B.TongThau_FK AND c.ma=TT.SANPHAM_FK "+
								"	WHERE ISNULL( A.NHAP,0) > ISNULL(TT.SOLUONG,0) AND A.TonKho_fk='"+this.id+"'";


				System.out.print("Kiem tra TongNhap]"+query);
				ResultSet rs=this.db.get(query);
				while(rs.next())
				{
					this.msg+="Số lượng nhập của sản phẩm ["+rs.getString("sanpham_Fk")+"] " +rs.getDouble("Nhap")+ "  Store [ "+rs.getString("nppMa")+"] lớn hơn tổng nhập của tổng thầu ["+rs.getDouble("SoLuong")+"] \n";
				}
				rs.close();
				if(this.msg.length()>0)
				{
					this.db.getConnection().rollback();
					System.out.println("[SQL]"+query);
					return false;
				}

				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			} else {
				System.out.println("[Upload.create] spNpp string is empty! ");
				return false;
			}
		}
		catch (Exception e) 
		{
			this.msg = "Xảy ra lỗi khi tạo mới nhiệm vụ (Lỗi: " + e.getMessage() + ")";
			System.out.println("[Upload.create] Exception Message = " + e.getMessage());
			try 
			{
				this.db.getConnection().rollback();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			db.shutDown();
			return false;
		}
		return true;
	}

	@Override
	public ResultSet createNppList() {
		//Lay danh sach nha phan phoi thuoc kenh MT, la store va thuoc pham vi hoat dong cua this.userId
		String query = "  select PK_SEQ, TEN from NHAPHANPHOI a inner join NHAPP_KBH b on a.PK_SEQ=b.NPP_FK "+
				" where KBH_FK=100052 and a.TongThau_FK is not null "+
				" and NPP_FK in (select NPP_FK from PHAMVIHOATDONG where Nhanvien_fk='"+this.userId+"') ";
		System.out.println("[Upload.createNppList] query = " + query);
		this.rsNpp = this.db.get(query);

		return this.rsNpp;
	}

	@Override
	public ResultSet getVungRs()
	{
		return this.vungRs;
	}

	@Override
	public void setVungRs(ResultSet vungRs)
	{
		this.vungRs=vungRs;
	}

	@Override
	public ResultSet getKhuvucRs()
	{
		return this.khuvucRs;
	}

	@Override
	public void setKhuvucRs(ResultSet khuvucRs)
	{
		this.khuvucRs=khuvucRs;

	}

	@Override
	public ResultSet getNppCoTonKhoRs()
	{
		return this.nppcotonkhoRs;
	}

	@Override
	public void setNppCoTonkhoRs(ResultSet tonkhoRs)
	{
		this.nppcotonkhoRs=tonkhoRs;

	}

	@Override
	public void initImportRs()
	{
		String query =  "\n select case when exists ( select 1 from khoasongay  x where x.npp_fk = npp.pk_seq) then 1 else 0 end coks " +
						"\n ,PK_SEQ as nppId, manpp as nppMa,Ten as nppTen from NHAPHANPHOI npp "+
						"\n where trangthai = 1  ";
		System.out.println("nppcotonkhoRs: "+query);
		this.nppcotonkhoRs = this.db.get(query);

		query="select PK_SEQ as kvId,TEN as KvTen from KHUVUC ";
		if(this.vungId.length()>0)
			query+=" and vung_fk='"+this.vungId+"' ";
		this.khuvucRs=this.db.get(query);

		query="select PK_SEQ as vungId,TEN as vungTen from VUNG";
		this.vungRs=this.db.get(query);

		query=
				"select PK_SEQ as nppId, manpp as nppMa,TEN as nppTen from NHAPHANPHOI "+ 
						"where trangthai = 1 and PK_SEQ in (select npp_fk from NHAPP_KBH where KBH_FK in ( '100021','100025','1000011','1000012' ))";

		if(this.khuvucId.length()>0)
			query+=" and khuvuc_fk in ("+this.khuvucId+")";

		if(this.vungId.length()>0)
			query+=" and khuvuc_fk in( select pk_seq from vung where pk_seq= "+this.vungId+")";		
		this.rsNpp=this.db.get(query);

	}

	public String getKhuvucId()
	{
		return khuvucId;
	}

	public void setKhuvucId(String khuvucId)
	{
		this.khuvucId = khuvucId;
	}

	public String getVungId()
	{
		return vungId;
	}

	public void setVungId(String vungId)
	{
		this.vungId = vungId;
	}
	private String Delete_TonKhoDauKy (String nppId,String ngayks)
	{
		String query= "";
		
		try
		{
			/*query="select count(*) as sodong from donhang where npp_fk="+nppId+" AND TRANGTHAI <> 2  ";
			System.out.println("\n lay don hang  : "+query);

			ResultSet rs=db.get(query);
			
			while(rs.next())
			{
				System.out.println("\n lay don hang   sdong  1111: "+rs.getInt("sodong"));
				if(rs.getInt("sodong") >0)
				{ 
					System.out.println("\n lay don hang   sdong : "+rs.getInt("sodong"));
					return "Nhà phân phối này đã có  đơn hàng!";
				}
			}
			rs.close();
			query="select count(*) as sodong from nhaphang where TRANGTHAI <> 2 and npp_fk='"+nppId+"'  ";
			rs=db.get(query);
			while(rs.next())
			{
				if(rs.getInt("sodong") >0)
				{
					return "Nhà phân phối này đã có  nhập hàng!";
				}
			}
			rs.close();

			query="select count(*)as sodong from DONTRAHANG where trangthai <> 2 and  npp_fk='"+nppId+"' ";
			rs=db.get(query);
			while(rs.next())
			{
				if(rs.getInt("sodong") >0)
				{
					return "Nhà phân phối này đã có  đơn trả hàng!";
				}
			}
			rs.close();*/

			query="delete from nhapp_kho where npp_fk='"+nppId+"'";

			System.out.println("queyr : "+query);
			if(!db.update(query))
			{
				return "Lỗi hệ thống "+query;
			}

			query=" delete from nhapp_kho_chitiet where npp_fk='"+nppId+"'";
			System.out.println("queyr : "+query);
			if(!db.update(query))
			{
				return "Lỗi hệ thống "+query;
			}


			query="delete from tonkhongay where npp_fk='"+nppId+"'";
			System.out.println("queyr : "+query);
			if(!db.update(query))
			{
				return "Lỗi hệ thống "+query;
			}

			query="delete from tonkhongay_chitiet where npp_fk='"+nppId+"'";
			System.out.println("queyr : "+query);
			if(!db.update(query))
			{
				return "Lỗi hệ thống "+query;
			}

			query="delete from khoasongay where npp_fk='"+nppId+"'";
			if(!db.update(query))
			{
				return "Lỗi hệ thống "+query;
			}
			
			query="delete from khoasothang where npp_fk='"+nppId+"'";
			if(!db.update(query))
			{
				return "Lỗi hệ thống "+query;
			}

		} catch (Exception e)
		{
			return e.toString();
		}
		return "";
	}
	/*
	 * Chen ton kho ngay,ks ngay doi voi npp chua co don hang
	 * Nhap Hang
	 * (non-Javadoc)
	 * @see geso.vifon.center.beans.upload.IUpload#importTonKho()
	 */
	@Override
	public void importTonKho()
	{ 
		String query = "\n select nppMa,spMa,khoTen,kbhTen " +
					"\n	from TonKho_Tmp " +
					"\n	group by nppMa,spMa,khoTen,kbhTen " +
					"\n	having COUNT(*)>1 ";
		
		System.out.println("[Query]"+query);
		ResultSet	 rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Trùng sản phẩm "+rs.getString("spMa")+"trong file của nhà phân phối "+rs.getString("nppMa")+ "\n";
				return;
			}
			if(rs!=null)rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		query = "\n select distinct nppMa from tonkho_tmp where nppMa !='"+this.nppMa+"'";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg += "Vui lòng chọn đúng file Import hoặc chọn đúng nhà phân phối \n";
				return;
			}
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		query = "\n	select nppMa "+
				"\n	from TonKho_Tmp  "+
				"\n	where nppMa  not in ( select  manpp from NHAPHANPHOI) ";

		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Nhà phân phối "+rs.getString("nppMa")+" không có trong hệ thống \n";
				return;
			}
			if(rs!=null)rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		String msgNV = CheckNghiepVuPhatSinh("DONHANG",this.db);
		if(msgNV.length() > 0 )
		{
			this.msg+= msgNV;
			return;
		}
		msgNV = CheckNghiepVuPhatSinh("NHAPHANG",this.db);
		if(msgNV.length() > 0 )
		{
			this.msg+= msgNV;
			return;
		}
		msgNV = CheckNghiepVuPhatSinh("DIEUCHINHTONKHO",this.db);
		if(msgNV.length() > 0 )
		{
			this.msg+= msgNV;
			return;
		}

		msgNV = CheckNghiepVuPhatSinh("DONTRAHANG",this.db);
		if(msgNV.length() > 0 )
		{
			this.msg+= msgNV;
			return;
		}
		
		
		msgNV = CheckNghiepVuPhatSinh("donhangtrave",this.db);
		if(msgNV.length() > 0 )
		{
			this.msg+= msgNV;
			return;
		}
		
		msgNV = CheckNghiepVuPhatSinh("chuyenkho",this.db);
		if(msgNV.length() > 0 )
		{
			this.msg+= msgNV;
			return;
		}


		query =	"\n	select distinct khoTEN "+
				"\n	from TonKho_Tmp a   "+
				"\n	where khoTEN NOT  in ( select TEN from KHO )" ;

		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Kho "+rs.getString("khoTEN")+" chưa có trên Hệ Thống  \n";
				return;
			}
			if(rs!=null)rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		query =	"\n	select distinct kbhtEN "+
				"\n	from TonKho_Tmp a   "+
				"\n	where kbhtEN NOT   in ( select  TEN from kENHbanhang )" ;

		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Kênh bán hàng "+rs.getString("kbhtEN")+" chưa có trên Hệ Thống  \n";
				return;
			}
			if(rs!=null)rs.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		query = "\n select distinct spMa,spten "+
						"\n from TonKho_Tmp  "+
						"\n where spMa  not in ( select  ma from SANPHAM) ";

		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Sản phẩm chưa có trong hệ thống "+rs.getString("spMa")+" - "+rs.getString("spTen") +"\n";
			}
			if(rs!=null)rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		if(this.msg.length() >0){
			return;
		}
		if(this.Ngaykhoaso.trim().equals(""))
		{
			this.msg="Vui lòng chọn ngày khóa sổ ";
			return;
		}
		query="select pk_Seq from nhaphanphoi where manpp='"+this.nppMa+"'";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.nppId=rs.getString("pk_Seq");
			}
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		

		try
		{
			//Bổ sung sản phẩm trong npp kho để không bị trường hợp view lên không thấy sản phẩm
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			
			db.getConnection().setAutoCommit(false);
			
			
			System.out.println("Nha phan phoi nek : "+this.nppId);
			String errr= this.Delete_TonKhoDauKy(this.nppId,this.Ngaykhoaso);

			if(errr.length() >0)
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+errr;
				return;
			}

			query = "\n insert into Log_Import(Npp_fk,UserId,GhiChu,NgayKs)  " +
					"\n select '"+nppId+"','"+userId+"',N'"+this.ghichu+"','"+Ngaykhoaso+"' ";
			if(!db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				return;
			}

			String Log_ImportID=null;
			ResultSet rsDDH = db.get("select Scope_identity() as ID ");
			if(rsDDH.next())
			{
				Log_ImportID= rsDDH.getString("ID");
			}
			rsDDH.close();

			query = "\n INSERT INTO  Log_Import_SanPham(Log_Import_FK,Npp_fk,KBH_FK,Kho_FK,SanPham_FK,SoLuong)  "+
					"\n	    select  '"+Log_ImportID+"' ,npp.PK_SEQ as nppId,(select pk_Seq From KenhBanHang where ten=tk.kbhTen ) as kenhId, " +
					"\n (select pk_Seq From kho where ten=tk.khoTen ) as khoId,sp.PK_SEQ as spId,tk.SoLuong as soluong "+ 
					"\n	    from " +
					"\n		TonKho_Tmp tk inner join SANPHAM sp on sp.MA=tk.spMa "+
					"\n		inner join NHAPHANPHOI npp on npp.ma=tk.nppMa "  ;
			if(!db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				return;
			}

			query = "\n	    select (select pk_Seq From kho where ten=tk.khoTen ) as khoId,npp.PK_SEQ as nppId,sp.PK_SEQ as spId,tk.SoLuong as soluong, "+
					"\n		0 as booked, ( select top(1) giabanlechuan from BANGGIABLC_SANPHAM where sanpham_fk=sp.pk_seq) as giamua, " +
					"\n		(select pk_Seq From KenhBanHang where ten=tk.kbhTen ) as kenhId, " +
					"\n		sp.MA as spMa,sp.TEN as spTen "+
					"\n	    from " +
					"\n		TonKho_Tmp tk inner join SANPHAM sp on sp.MA=tk.spMa "+
					"\n		inner join NHAPHANPHOI npp on npp.manpp=tk.nppMa "  ;
			System.out.println("Query Insert TonKho: "+query);
			rs=this.db.get(query);			

			while(rs.next())
			{
				String khoId=rs.getString("khoId");
				String nppId=rs.getString("nppId");
				String spId=rs.getString("spId");
				String kenhId=rs.getString("kenhId");
				float soluong=rs.getFloat("soluong");
				float giamua=rs.getFloat("giamua");
				query = "\n insert into NHAPP_KHO(KHO_FK,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK)"+
								"\n select '"+khoId+"','"+nppId+"','"+spId+"','"+soluong+"',0,"+soluong+",'"+giamua+"','"+kenhId+"' ";
				if(db.updateReturnInt(query)!=1)
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					System.out.println("[Error nhapp_kho] "+query);
					return;
				}
				
				query = "\n insert into NHAPP_KHO_ChiTiet(ngaynhapkho,KHO_FK,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK)"+
							"\n select '"+this.Ngaykhoaso+"','"+khoId+"','"+nppId+"','"+spId+"','"+soluong+"',0,"+soluong+",'"+giamua+"','"+kenhId+"' ";
				if(db.updateReturnInt(query)!=1)
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					System.out.println("[Error nhapp_kho]"+query);
					return;
				}
				
				query = "\n INSERT INTO TONKHONGAY(KHO_FK,NPP_FK,SANPHAM_FK,NGAY,SOLUONG,KBH_FK)"+
						"\n select '"+khoId+"','"+nppId+"','"+spId+"','"+this.Ngaykhoaso+"','"+soluong+"','"+kenhId+"'";
				if(db.updateReturnInt(query)!=1)
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					System.out.println("[Error TONKHONGAY]"+query);
					return;
				}
				query = "\n INSERT INTO TONKHONGAY_chitiet(ngaynhapkho,KHO_FK,NPP_FK,SANPHAM_FK,NGAY,SOLUONG,KBH_FK)"+
						"\n select '"+this.Ngaykhoaso+"','"+khoId+"','"+nppId+"','"+spId+"','"+this.Ngaykhoaso+"','"+soluong+"','"+kenhId+"'";
				if(db.updateReturnInt(query)!=1)
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					System.out.println("[Error TONKHONGAY]"+query);
					return;
				}
			}

			query = "\n INSERT INTO KHOASONGAY(NGAYKSGANNHAT,NGAYKS,NGAYTAO,NGUOITAO,NPP_FK,chon,DOANHSO,isTonDau) "+
							"\n SELECT '"+this.Ngaykhoaso+"' as nksgn,'"+this.Ngaykhoaso+"' as nks,'"+getDateTime()+"' as ngaytao,"+this.userId+" AS NTAO,'"+this.nppId+"' AS nppId,0 as Chon,0 as dso,1 istondau ";
			if(db.updateReturnInt(query) <=0)
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				System.out.println("[Error TONKHONGAY]"+query);
				return;
			}
			
			
			query ="\n insert khoasothang(NPP_FK,thangks,nam,ngaytao,nguoitao,created_date ) " +
			"\n select NPP_FK, month( DATEADD(month,-1,min(ngayks))) thang,  year( DATEADD(month,-1,min(ngayks))) nam" +
			"\n				,'"+getDateTime()+"',"+this.userId+",dbo.GetLocalDate(default) " +
			"\n from KHOASONGAY" +
			"\n	where npp_fk =  "+this.nppId + 
			"\n group by NPP_FK " ;
			if(db.updateReturnInt(query) <=0)
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				System.out.println("[Error khoasothang]"+query);
				return;
			}
			
			query = " update khoasothang set ngaydauthang =  cast( nam as varchar) + '-' + dbo.PlusZero(thangks,2) + '-01'  where npp_fk =  "+ this.nppId;
			if(db.updateReturnInt(query) <=0)
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				System.out.println("[Error khoasothang]"+query);
				return;
			}
			
			

			if(this.msg.length()>0)
			{
				this.db.getConnection().rollback();
				return;
			}
			
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			this.msg=e.toString();
			e.printStackTrace();
		}
	}

	public static String CheckNghiepVuPhatSinh(String table ,Idbutils db)
	{
		String query =	"\n select nppMa "+
		"\n	from TonKho_Tmp a inner join nhaphanphoi b on a.nppMa=b.manpp  "+
		"\n	where b.pk_seq  in ( select  NPP_FK from DONTRAHANG  )" ;

		ResultSet rs=db.get(query);
		try
		{
			if(rs.next())
			{
				rs.close();
				return "Nhà phân phối đã có "+table+" \n";

			}
			if(rs!=null)rs.close();
			return "";
		} catch (SQLException e)
		{
			e.printStackTrace();
			return "Exception:" + e.getMessage();
		}
	}
	
	@Override
	public String getNgaykhoaso() 
	{
		return this.Ngaykhoaso;
	}

	@Override
	public void setNgaykhoaso(String ngaykhoaso) 
	{

		this.Ngaykhoaso=ngaykhoaso;
	}
	private String New_Delete_TonKhoDauKy (String nppId)
	{
		String query="";
		try
		{
			db.getConnection().setAutoCommit(false);
			query="DELETE DONHANG_SANPHAM WHERE DONHANG_FK IN (SELECT PK_SEQ FROM DONHANG WHERE NPP_FK='"+nppId+"' AND NGAYNHAP LIKE '2013-09%' ) ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("DONHANG_SANPHAM : "+query);
			query=" DELETE DONHANG_CTKM_TRAKM WHERE DONHANGID IN (SELECT PK_SEQ FROM DONHANG WHERE NPP_FK='"+nppId+"' AND NGAYNHAP LIKE '2013-09%' )";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("DONHANG_CTKM_TRAKM : "+query);
			query=
					"	DELETE FROM PHIEUTHUHOI_SANPHAM WHERE PTH_FK IN "+  
							" (  SELECT PK_SEQ FROM PHIEUTHUHOI WHERE NPP_FK='"+nppId+"' AND DONHANG_FK "+   
							" IN  (SELECT PK_SEQ FROM DONHANG WHERE NPP_FK='"+nppId+"' AND NGAYNHAP LIKE '2013-09%' )     ) "; 
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			query=
					"	DELETE FROM PHIEUTHUHOI_SANPHAM_CHITIET WHERE PTH_FK IN "+  
							" (  SELECT PK_SEQ FROM PHIEUTHUHOI WHERE NPP_FK='"+nppId+"' AND DONHANG_FK "+   
							" IN  (SELECT PK_SEQ FROM DONHANG WHERE NPP_FK='"+nppId+"' AND NGAYNHAP LIKE '2013-09%' )     ) "; 
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("PHIEUTHUHOI_SANPHAM_CHITIET : "+query);

			query="DELETE FROM PHIEUTHUHOI_SPKM WHERE PTH_FK IN  (  SELECT PK_SEQ FROM PHIEUTHUHOI WHERE NPP_FK='"+nppId+"' AND DONHANG_FK  IN  (SELECT PK_SEQ FROM DONHANG WHERE NPP_FK='"+nppId+"' AND NGAYNHAP LIKE '2013-09%' )     ) ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("PHIEUTHUHOI_SPKM : "+query);

			query="DELETE FROM KHACHHANG_CONGNO WHERE DONHANG_FK IN  (SELECT PK_SEQ FROM DONHANG WHERE NPP_FK='"+nppId+"' AND NGAYNHAP LIKE '2013-09%' )";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("KHACHHANG_CONGNO : "+query);

			query="DELETE FROM PHIEUTHUHOI WHERE  NPP_FK='"+nppId+"' AND DONHANG_FK  IN  (SELECT PK_SEQ FROM DONHANG WHERE NPP_FK='"+nppId+"' AND NGAYNHAP LIKE '2013-09%' ) ";  
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("PHIEUTHUHOI : "+query);

			query="DELETE PHIEUXUATKHO_DONHANG WHERE DONHANG_FK IN(SELECT PK_SEQ FROM DONHANG WHERE NPP_FK='"+nppId+"' AND NGAYNHAP LIKE '2013-09%' ) ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("PHIEUXUATKHO_DONHANG : "+query);

			query="DELETE PHIEUXUATKHO_SPKM WHERE PXK_FK IN(     SELECT PK_SEQ FROM PHIEUXUATKHO WHERE NPP_FK IN ('"+nppId+"') AND NGAYLAPPHIEU LIKE '2013-09%'   )";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("PHIEUXUATKHO_SPKM : "+query);

			query="DELETE PHIEUXUATKHO_SPKM_CHITIET WHERE PXK_FK IN(SELECT PK_SEQ FROM PHIEUXUATKHO WHERE NPP_FK IN ('"+nppId+"') AND NGAYLAPPHIEU LIKE '2013-09%'             )";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("PHIEUXUATKHO_SPKM_CHITIET : "+query);

			query="DELETE PHIEUXUATKHO_SANPHAM WHERE PXK_FK IN(SELECT PK_SEQ FROM PHIEUXUATKHO WHERE NPP_FK IN ('"+nppId+"') AND NGAYLAPPHIEU LIKE '2013-09%' )";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("PHIEUXUATKHO_SANPHAM : "+query);

			query="DELETE PHIEUXUATKHO_SANPHAM_CHITIET WHERE PXK_FK IN(SELECT PK_SEQ FROM PHIEUXUATKHO WHERE NPP_FK IN ('"+nppId+"') AND NGAYLAPPHIEU LIKE '2013-09%'   )";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("PHIEUXUATKHO_SANPHAM_CHITIET : "+query);

			query="DELETE PHIEUXUATKHO WHERE NPP_FK IN ('"+nppId+"') AND NGAYLAPPHIEU LIKE '2013-09%'  ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("PHIEUXUATKHO : "+query);

			query="DELETE DONHANG WHERE NPP_FK='"+nppId+"' AND NGAYNHAP >= '2013-09-01'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("DONHANG : "+query);

			query="DELETE TONKHONGAY WHERE NPP_FK='"+nppId+"' AND NGAY>='2013-08-31'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("TONKHONGAY : "+query);

			query="DELETE TONKHONGAY_CHITIET WHERE NPP_FK='"+nppId+"' AND NGAY>='2013-08-31' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("TONKHONGAY_CHITIET : "+query);

			query="DELETE KHOASONGAY  WHERE NPP_FK='"+nppId+"' AND NGAYKS >='2013-08-31' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("KHOASONGAY : "+query);

			query="UPDATE NHAPHANG SET TRANGTHAI=0 WHERE NPP_FK='"+nppId+"' AND NGAYNHAN >='2013-09-01' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			query="UPDATE NHAPP_KHO SET SOLUONG=0,BOOKED=0,AVAILABLE=0 WHERE NPP_FK='"+nppId+"' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("NHAPP_KHO : "+query);
			query="UPDATE NHAPP_KHO_CHITIET SET SOLUONG=0,BOOKED=0,AVAILABLE=0 WHERE NPP_FK='"+nppId+"' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("NHAPP_KHO_CHITIET : "+query);
			query="UPDATE DONHANG SET TRANGTHAI=2 WHERE NPP_FK='"+nppId+"' AND TRANGTHAI IN(0,3) AND NGAYNHAP<='2013-08-31' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi hệ thống "+query;
			}
			System.out.println("DONHANG : "+query);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return e.toString();
		}
		return "";
	}
	@Override
	public void NewImport()
	{

		String query=" select distinct khoId from tonkho_tmp where khoid not in (100000,100001) ";

		ResultSet  rs=this.db.get(query);
		try
		{
			if (rs.next())
			{
				this.msg+=" Vui lòng kiểm tra lại file ,bạn đang để kiểu chữ ,hoặc bạn đang để rỗng (ô trống) ở cột thứ 7 .\n Vui lòng sửa theo định dạng: \n  Kho chính :100000 \n Kho khuyến mãi :100001   \n";
				return;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		query=
				" select nppMa,spMa,KhoId "+
						"	from TonKho_Tmp "+
						"	group by nppMa,spMa,KhoId "+
						"	having COUNT(*)>1 ";
		System.out.println("[Query]"+query);

		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Trùng sản phẩm "+rs.getString("spMa")+" trong file của nhà phân phối "+rs.getString("nppMa")+ "\n";
				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		query=" select distinct nppMa from tonkho_tmp where nppMa !='"+this.nppMa+"'";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Vui lòng chọn đúng file Import hoặc chọn đúng nhà phân phối \n "+rs.getString("nppMa");
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		query=
				"	select nppMa "+
						"	from TonKho_Tmp  "+
						"	where nppMa  not in ( select  ma from NHAPHANPHOI) ";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Nhà phân phối "+rs.getString("nppMa")+" không có trong hệ thống \n";
				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		query=
				" select spMa,spten "+
						" from TonKho_Tmp  "+
						" where spMa  not in ( select  ma from SANPHAM) ";

		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Sản phẩm chưa có trong hệ thống "+rs.getString("spMa")+" - "+rs.getString("spTen") +"\n";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		query=
				"   select sp.MA as spMa,sp.TEN as spTen "+
						"   from  "+ 
						"	TonKho_Tmp tk inner join SANPHAM sp on sp.MA=tk.spMa "+
						"	left join QUYCACH qc on qc.SANPHAM_FK=sp.PK_SEQ and qc.DVDL2_FK=100018 "+
						"   where qc.DVDL2_FK is null";

		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Sản phẩm chưa có quy cách thùng trong  hệ thống "+rs.getString("spMa")+" - "+rs.getString("spTen") +"\n";

			}
		} catch (Exception e)
		{
			e.printStackTrace();
			
		}
		//kiem tra trong du lieu da co phat sinh  chua ? 

		if(this.msg.length() >0)
		{
			return;
		}
		query="select pk_Seq from nhaphanphoi where ma='"+this.nppMa+"'";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.nppId=rs.getString("pk_Seq");
			}
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return ;
		}
		System.out.println("[nppId] "+this.nppId);
		String errr= this.New_Delete_TonKhoDauKy(this.nppId);
		if(errr.length() >0)
		{
			this.msg=errr;
			return;
		}
		query=
				"	    select tk.khoId,npp.PK_SEQ as nppId,sp.PK_SEQ as spId,tk.TonDau*qc.soluong1/qc.soluong2+ isnull(tk.soluongle,0) as soluong,\n"+
						"		0 as booked,  isnull(bg.GIABANLECHUAN,0) as giamua,100025 as kenhId,sp.MA as spMa,sp.TEN as spTen \n"+
						"	    from \n" +
						"		TonKho_Tmp tk inner join SANPHAM sp on sp.MA=tk.spMa \n"+
						"		left join QUYCACH qc on qc.SANPHAM_FK=sp.PK_SEQ and qc.DVDL2_FK=100018 \n" +
						"		inner join NHAPHANPHOI npp on npp.ma=tk.nppMa \n"  +
						"		left join BANGGIABLC_SANPHAM bg on bg.SANPHAM_FK=sp.PK_SEQ ";
		System.out.println("[Query Insert TonKho]"+query);
		rs=this.db.get(query);			
		try
		{
			db.getConnection().setAutoCommit(false);
			while(rs.next())
			{
				String khoId=rs.getString("khoId");
				String nppId=rs.getString("nppId");
				String spId=rs.getString("spId");
				String kenhId="100025";
				float soluong=rs.getFloat("soluong");
				float giamua=rs.getFloat("giamua");

				query = "update NHAPP_KHO set soluong='"+soluong+"',booked=0,available='"+soluong+"',giamua='"+giamua+"' where  KHO_FK='"+khoId+"' and NPP_FK ='"+nppId+"' and SANPHAM_FK='"+spId+"'  and KBH_FK='"+kenhId+"' ";
				int sodong=db.updateReturnInt(query);
				if(sodong==0)
				{
					query=
							"insert into NHAPP_KHO(KHO_FK,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK)"+
									" select '"+khoId+"','"+nppId+"','"+spId+"','"+soluong+"',0,"+soluong+",'"+giamua+"','"+kenhId+"' ";					
					if(!db.update(query))
					{
						this.db.getConnection().rollback();
						this.msg+="Lỗi hệ thống "+query;
						System.out.println("[Error NHAPP_KHO]"+query);
						return;
					}
				}	
				query = "update NHAPP_KHO_CHITIET set soluong='"+soluong+"',booked=0,available='"+soluong+"',giamua='"+giamua+"' where  KHO_FK='"+khoId+"' and NPP_FK ='"+nppId+"' and SANPHAM_FK='"+spId+"'  and KBH_FK='"+kenhId+"' and solo='DMSVIFON201308' ";
				sodong=db.updateReturnInt(query);
				if(sodong==0)
				{
					query="insert into NHAPP_KHO_CHITIET(KHO_FK,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK,SOLO,NGAYHETHAN)"+
							" select '"+khoId+"','"+nppId+"','"+spId+"','"+soluong+"',0,"+soluong+",'"+giamua+"','"+kenhId+"','DMSVIFON201308','2014-12-01' ";
					if(!db.update(query))
					{
						this.db.getConnection().rollback();
						this.msg+="Lỗi hệ thống "+query;
						System.out.println("[Error TONKHONGAY]"+query);
						return;
					}
				}
				query=
						"INSERT INTO TONKHONGAY(KHO_FK,NPP_FK,SANPHAM_FK,NGAY,SOLUONG,KBH_FK)"+
								" select '"+khoId+"','"+nppId+"','"+spId+"','2013-08-31','"+soluong+"','"+kenhId+"'";
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					System.out.println("[Error TONKHONGAY]"+query);
					return;
				}
				query=
						"INSERT INTO TONKHONGAY_CHITIET(KHO_FK,NPP_FK,SANPHAM_FK,NGAY,SOLUONG,KBH_FK,SOLO,NGAYSANXUAT)"+
								" select '"+khoId+"','"+nppId+"','"+spId+"','2013-08-31','"+soluong+"','"+kenhId+"','DMSVIFON201308','2014-12-01' ";
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					System.out.println("[Error TONKHONGAY]"+query);
					return;
				}
			}
			query=
					"INSERT INTO KHOASONGAY(NGAYKSGANNHAT,NGAYKS,NGAYTAO,NGUOITAO,NPP_FK,chon,DOANHSO) "+
							"SELECT '2013-08-31' as nksgn,'2013-08-31' as nks,'2013-09-06' as ngaytao,100671 AS NTAO,'"+this.nppId+"' AS nppId,0 as Chon,0 as dso ";
			if(!db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				System.out.println("[Error KHOASONGAY]"+query);
				return;
			}

			if(this.msg.length()>0)
			{
				this.db.getConnection().rollback();
				return;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.msg=e.toString();
			e.printStackTrace();
		}
	}

	@Override
	public String getNppMa()
	{
		return this.nppMa;
	}

	@Override
	public void setNppMa(String nppMa)
	{
		this.nppMa=nppMa;
	}

	public String DuaLaiTonKhoNgay()
	{	

		String query=" select distinct khoId from tonkho_tmp where khoid not in (100000,100001) ";
		ResultSet  rs=this.db.get(query);
		try
		{
			if (rs.next())
			{
				return " Vui lòng kiểm tra lại file ,bạn đang để kiểu chữ ,hoặc bạn đang để rỗng (ô trống) ở cột thứ 7 .\n Vui lòng sửa theo định dạng: \n  Kho chính :100000 \n Kho khuyến mãi :100001   \n";
			}
			if(rs!=null)rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		query=
				" select nppMa,spMa,KhoId "+
						"	from TonKho_Tmp "+
						"	group by nppMa,spMa,KhoId "+
						"	having COUNT(*)>1 ";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				return "Trùng sản phẩm "+rs.getString("spMa")+" trong file của nhà phân phối "+rs.getString("nppMa")+ "\n";				
			}
			if(rs!=null)rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		query=" select distinct nppMa from tonkho_tmp where nppMa !='"+this.nppMa+"'";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				return "Vui lòng chọn đúng file Import hoặc chọn đúng nhà phân phối \n";
			}
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		query=
				"	select nppMa "+
						"	from TonKho_Tmp  "+
						"	where nppMa  not in ( select  ma from NHAPHANPHOI) ";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				return "Nhà phân phối "+rs.getString("nppMa")+" không có trong hệ thống \n";
			}
			if(rs!=null)rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		query=
				" select spMa,spten "+
						" from TonKho_Tmp  "+
						" where spMa  not in ( select  ma from SANPHAM) ";

		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Sản phẩm chưa có trong hệ thống "+rs.getString("spMa")+" - "+rs.getString("spTen") +"\n";
			}
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		query=
				"   select sp.MA as spMa,sp.TEN as spTen "+
						"   from  "+ 
						"	TonKho_Tmp tk inner join SANPHAM sp on sp.MA=tk.spMa "+
						"	left join QUYCACH qc on qc.SANPHAM_FK=sp.PK_SEQ and qc.DVDL2_FK=100018 "+
						"   where qc.DVDL2_FK is null";

		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.msg+="Sản phẩm chưa có quy cách thùng trong  hệ thống "+rs.getString("spMa")+" - "+rs.getString("spTen") +"\n";
			}
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			return "Lỗi hệ thống "+e.getMessage();
		}		
		query="select pk_Seq from nhaphanphoi where ma='"+this.nppMa+"'";
		rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.nppId=rs.getString("pk_Seq");
			}
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			return "Lỗi hệ thống "+e.getMessage();
		}
		if(this.userId==null || this.userId.equals(""))
		{
			return "Vui lòng đăng nhập lại !";
		}

		try
		{
			query="select pk_Seq from nhaphanphoi where ma='"+this.nppMa+"'";
			rs=this.db.get(query);
			try
			{
				while(rs.next())
				{
					this.nppId=rs.getString("pk_Seq");
				}
				if(rs!=null)rs.close();
			} catch (Exception e)
			{
				return "Lỗi hệ thống "+query;
			}
			int songayDakS=0;
			query="select DATEDIFF(day,'2013-09-01',(select max(ngayks) from khoasongay where NPP_FK="+nppId+")) as SoNgayDaKs ";
			rs=this.db.get(query);
			while(rs.next())
			{
				songayDakS=rs.getInt("SoNgayDaKs");
			}
			if(rs!=null)rs.close();

			this.db.getConnection().setAutoCommit(false);
			query="delete from khoasongay where npp_fk='"+this.nppId+"' and ngayks >'2013-08-31'";
			if(!db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				System.out.println("[Error TONKHONGAY]"+query);
				return "Lỗi hệ thống "+query;
			}
			System.out.println("[khoasongay]"+query);
			query="delete from tonkhongay where npp_fk='"+this.nppId+"' and ngay >='2013-08-31'";
			if(!db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				System.out.println("[Error TONKHONGAY]"+query);
				return "Lỗi hệ thống "+query;
			}
			System.out.println("[tonkhongay]"+query);
			query="delete from tonkhongay_chitiet where npp_fk='"+this.nppId+"' and ngay >='2013-08-31' ";
			if(!db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				System.out.println("[Error TONKHONGAY]"+query);
				return "Lỗi hệ thống "+query;
			}
			System.out.println("[tonkhongay_chitiet]"+query);
			query="update nhapp_kho set soluong=0,booked=0,available=0 where npp_fk='"+this.nppId+"'  ";
			if(!db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				System.out.println("[Error TONKHONGAY]"+query);
				return "Lỗi hệ thống "+query;
			}
			System.out.println("[nhapp_kho]"+query);
			query="update nhapp_kho_chitiet set soluong=0,booked=0,available=0 where npp_fk='"+this.nppId+"'  ";
			if(!db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg+="Lỗi hệ thống "+query;
				System.out.println("[Error TONKHONGAY]"+query);
				return "Lỗi hệ thống "+query;
			}
			System.out.println("[nhapp_kho_chitiet]"+query);
			query=
					"	    select tk.khoId,npp.PK_SEQ as nppId,sp.PK_SEQ as spId,tk.TonDau*qc.soluong1/qc.soluong2+ isnull(tk.soluongle,0) as soluong,\n"+
							"		0 as booked,  isnull(bg.GIABANLECHUAN,0) as giamua,100025 as kenhId,sp.MA as spMa,sp.TEN as spTen \n"+
							"	    from \n" +
							"		TonKho_Tmp tk inner join SANPHAM sp on sp.MA=tk.spMa \n"+
							"		left join QUYCACH qc on qc.SANPHAM_FK=sp.PK_SEQ and qc.DVDL2_FK=100018 \n" +
							"		inner join NHAPHANPHOI npp on npp.ma=tk.nppMa \n"  +
							"		left join BANGGIABLC_SANPHAM bg on bg.SANPHAM_FK=sp.PK_SEQ ";
			System.out.println("[Query Insert TonKho]"+query);
			rs=this.db.get(query);			
			while(rs.next())
			{
				String khoId=rs.getString("khoId");
				String nppId=rs.getString("nppId");
				String spId=rs.getString("spId");
				String kenhId="100025";
				float soluong=rs.getFloat("soluong");
				float giamua=rs.getFloat("giamua");

				query = "update NHAPP_KHO set soluong='"+soluong+"',booked=0,available='"+soluong+"',giamua='"+giamua+"' where  KHO_FK='"+khoId+"' and NPP_FK ='"+nppId+"' and SANPHAM_FK='"+spId+"'  and KBH_FK='"+kenhId+"' ";
				int sodong=db.updateReturnInt(query);
				if(sodong==0)
				{
					query=
							"insert into NHAPP_KHO(KHO_FK,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK)"+
									" select '"+khoId+"','"+nppId+"','"+spId+"','"+soluong+"',0,"+soluong+",'"+giamua+"','"+kenhId+"' ";					
					if(!db.update(query))
					{
						this.db.getConnection().rollback();
						System.out.println("[Error NHAPP_KHO]"+query);
						return "Lỗi hệ thống "+query;
					}
				}	
				//System.out.println("[NHAPP_KHO]"+query);
				query = "update NHAPP_KHO_CHITIET set soluong='"+soluong+"',booked=0,available='"+soluong+"',giamua='"+giamua+"' where  KHO_FK='"+khoId+"' and NPP_FK ='"+nppId+"' and SANPHAM_FK='"+spId+"'  and KBH_FK='"+kenhId+"' and solo='DMSVIFON201308' ";
				sodong=db.updateReturnInt(query);
				if(sodong==0)
				{
					query="insert into NHAPP_KHO_CHITIET(KHO_FK,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK,SOLO,NGAYHETHAN)"+
							" select '"+khoId+"','"+nppId+"','"+spId+"','"+soluong+"',0,"+soluong+",'"+giamua+"','"+kenhId+"','DMSVIFON201308','2014-12-01' ";
					if(!db.update(query))
					{
						this.db.getConnection().rollback();
						System.out.println("[Error TONKHONGAY]"+query);
						return "Lỗi hệ thống "+query;
					}
				}
				//System.out.println("[NHAPP_KHO_CHITIET]"+query);
				query=
						"INSERT INTO TONKHONGAY(KHO_FK,NPP_FK,SANPHAM_FK,NGAY,SOLUONG,KBH_FK)"+
								" select '"+khoId+"','"+nppId+"','"+spId+"','2013-08-31','"+soluong+"','"+kenhId+"'";
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					System.out.println("[Error TONKHONGAY]"+query);
					return "Lỗi hệ thống "+query;
				}
				//System.out.println("[TONKHONGAY]"+query);
				query=
						"INSERT INTO TONKHONGAY_CHITIET(KHO_FK,NPP_FK,SANPHAM_FK,NGAY,SOLUONG,KBH_FK,SOLO,NGAYSANXUAT)"+
								" select '"+khoId+"','"+nppId+"','"+spId+"','2013-08-31','"+soluong+"','"+kenhId+"','DMSVIFON201308','2014-12-01' ";
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					System.out.println("[Error TONKHONGAY]"+query);
					return "Lỗi hệ thống "+query;
				}
				//System.out.println("[TONKHONGAY_CHITIET]"+query);
			}
			if(rs!=null)rs.close();

			for(int i=0;i<=songayDakS;i++)
			{
				String tungay="(select convert(varchar(10),DATEADD(DAY,"+i+",'2013-09-01'),20) ) ";
				query=
						"SELECT SUM(DATA.SOLUONG) AS TONCUOI, NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK,NGAYNHAN   " +  
								"FROM   " +  
								"(  " +  
								"	SELECT B.NPP_FK,B.KBH_FK,a.KHO_FK,C.PK_SEQ AS SANPHAM_FK,SUM(CAST(SOLUONG AS INT)) AS SOLUONG	,N'2.Nhập hàng bán' AS TYPE,NGAYNHAN  " +  
								"	FROM NHAPHANG_SP A INNER JOIN NHAPHANG B ON A.NHAPHANG_FK = B.PK_SEQ		  " +  
								"		INNER JOIN SANPHAM C ON C.MA = A.SANPHAM_FK   " +  
								"	WHERE   B.TRANGTHAI =1 AND B.NGAYNHAN ="+tungay+"   AND B.NPP_FK='"+this.nppId+"'  " +  
								"	GROUP BY B.NPP_FK,B.KBH_FK,C.PK_SEQ,a.KHO_FK,NGAYNHAN  " +  
								"	UNION ALL  " +  
								"	SELECT DH.NPP_FK,DH.KBH_FK,DHSP.KHO_FK,DHSP.SANPHAM_FK,(-1)*SUM(SOLUONG) AS SOLUONG ,N'4.Xuất hàng bán' AS TYPE ,NGAYNHAP  " +  
								"			FROM DONHANG_SANPHAM DHSP    " +  
								"			INNER JOIN DONHANG DH ON DH.PK_SEQ = DHSP.DONHANG_FK  	  " +  
								"	WHERE DH.TRANGTHAI =1 AND DH.NGAYNHAP ="+tungay+"  AND DH.NPP_FK='"+this.nppId+"'  " +  
								"	GROUP BY  DH.NPP_FK,DH.KBH_FK,DHSP.SANPHAM_FK,DHSP.KHO_FK 	,NGAYNHAP	  " +  
								"	UNION ALL  " +  
								"	SELECT DONH.NPP_FK,DONH.KBH_FK,KM.KHO_FK,SPKM.PK_SEQ AS SANPHAM_FK,(-1)* ISNULL(SUM(TRAKM_KHACHHANG.SOLUONG),0) AS SOLUONG  ,N'5.Xuất hàng KM' AS TYPE,NGAYNHAP  " +  
								"	FROM DONHANG_CTKM_TRAKM TRAKM_KHACHHANG  				  " +  
								"	INNER JOIN   " +  
								"	(  	  " +  
								"		SELECT *   	FROM DONHANG   	  " +  
								"		WHERE TRANGTHAI =1  " +  
								"		AND NGAYNHAP ="+tungay+"  AND NPP_FK= '"+this.nppId+"'   " +  
								"	) DONH ON DONH.PK_SEQ = TRAKM_KHACHHANG.DONHANGID    " +  
								"	INNER JOIN SANPHAM SPKM ON SPKM.MA = TRAKM_KHACHHANG.SPMA  	  " +  
								"	INNER JOIN CTKHUYENMAI KM ON KM.PK_SEQ = TRAKM_KHACHHANG.CTKMID   " +  
								"	WHERE LEN(TRAKM_KHACHHANG.SPMA) >0 	  " +  
								"	GROUP BY DONH.NPP_FK,DONH.KBH_FK,KM.KHO_FK,SPKM.PK_SEQ ,NGAYNHAP	  " +  
								"	UNION ALL  " +  
								"	SELECT  DCTK.NPP_FK,DCTK.KBH_FK,DCTK.KHO_FK,DCTK_SP.SANPHAM_FK ,SUM( CAST( ISNULL(DCTK_SP.DIEUCHINH,0) AS INT) ) AS SOLUONG, N'8.Điều chỉnh' AS TYPE,NGAYDC 	    " +  
								"	FROM   " +  
								"	DIEUCHINHTONKHO DCTK    " +  
								"		inner JOIN DIEUCHINHTONKHO_SP DCTK_SP ON DCTK_SP.DIEUCHINHTONKHO_FK = DCTK.PK_SEQ   " +  
								"	WHERE   DCTK.TRANGTHAI =1 AND DCTK.NGAYDC ="+tungay+"  AND  CAST( ISNULL(DCTK_SP.DIEUCHINH,0) AS INT)<>0   " +  
								"		AND DCTK.NPP_FK= '"+this.nppId+"'   " +  
								"	GROUP BY  DCTK.NPP_FK,DCTK.KBH_FK,DCTK.KHO_FK,DCTK_SP.SANPHAM_FK,NGAYDC  " +  
								"	UNION ALL  " +  
								"	SELECT  DH.NPP_FK,  DH.KBH_FK, DH.KHO_FK,   " +  
								"	ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK,     " +  
								"		(-1)*SUM(ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG)) AS SOLUONG  ,N'7.1.KH Trả hàng bán' AS TYPE	  ,NGAYDUYET  " +  
								"	FROM  DONHANGTRAVE DH    	  " +  
								"		LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ  	  " +  
								"		LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK     " +  
								"	WHERE DH.TRANGTHAI = 3 AND DH.NPP_FK='"+this.nppId+"' AND DH.NGAYDUYET ="+tungay+"   " +  
								"	GROUP BY DH.NPP_FK, DH.KHO_FK, DH.KBH_FK,ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK	) ,NGAYDUYET  " +  
								" ) AS DATA      " +
								" GROUP BY NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK,NGAYNHAN  " ;
				System.out.println("[XNT]"+query);
				rs=db.get(query);


				while (rs.next())
				{
					String nppId = rs.getString("NPP_FK");
					String khoId = rs.getString("KHO_FK");
					String spId = rs.getString("SANPHAM_FK");
					String kenhId = rs.getString("KBH_FK");
					int toncuoi= rs.getInt("toncuoi");
					String ngay =  rs.getString("NGAYNHAN");

					query=	" select AVAILABLE ,sp.ten ,sp.ma " +
							" from      sanpham sp left join nhapp_kho a  on sp.pk_seq=a.sanpham_fk " +
							" where npp_fk='"+nppId+"'  and sanpham_fk='"+spId+"' and kho_fk='"+khoId+"' and kbh_fk='"+kenhId+"' ";
					ResultSet rssp=this.db.get(query);
					String str="";
					if(rssp.next())
					{
						str=rssp.getString("ma") +" "+ rssp.getString("ten")+""+ rssp.getString("AVAILABLE");
					}
					rssp.close();
					query=" UPDATE NHAPP_KHO SET SOLUONG=SOLUONG + "+toncuoi+",AVAILABLE=AVAILABLE + "+toncuoi+" " +
							" where npp_fk='"+nppId+"'  and sanpham_fk='"+spId+"' and kho_fk='"+khoId+"' and kbh_fk='"+kenhId+"' ";
					int sodong =this.db.updateReturnInt(query);
					if(sodong <0 )
					{
						this.db.getConnection().rollback();
						return "Lỗi hệ thống "+query + " . \n "+ str;
					}

					if(sodong==0)
					{
						query=
								"insert into NHAPP_KHO(KHO_FK,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK)"+
										" select '"+khoId+"','"+nppId+"','"+spId+"','"+toncuoi+"',0,"+toncuoi+",'0','"+kenhId+"' ";					
						if(!db.update(query))
						{
							this.db.getConnection().rollback();
							return "Lỗi hệ thống "+query;
						}
					}
					//	System.out.println("[NHAPP_KHO]"+query);
					query=" UPDATE NHAPP_KHO_CHITIET SET SOLUONG=SOLUONG + "+toncuoi+",AVAILABLE=AVAILABLE + "+toncuoi+" where npp_fk='"+nppId+"'  and sanpham_fk='"+spId+"' and kho_fk='"+khoId+"' and kbh_fk='"+kenhId+"' AND SOLO='DMSVIFON201308' ";
					sodong =this.db.updateReturnInt(query);
					if(sodong <0 ){
						this.db.getConnection().rollback();
						return "Lỗi hệ thống "+query;
					}

					if(sodong==0)
					{
						query="insert into NHAPP_KHO_CHITIET(KHO_FK,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK,SOLO,NGAYHETHAN)"+
								" select '"+khoId+"','"+nppId+"','"+spId+"','"+toncuoi+"',0,"+toncuoi+",'0','"+kenhId+"','DMSVIFON201308','2014-12-01' ";				
						if(!db.update(query))
						{
							this.db.getConnection().rollback();
							return "Lỗi hệ thống "+query;
						}
					}
					//	System.out.println("[NHAPP_KHO_CHITIET]"+query);

				}
				//cAP NHAT TON KHO NGAY




				query=
						"INSERT INTO KHOASONGAY(NGAYKSGANNHAT,NGAYKS,NGAYTAO,NGUOITAO,NPP_FK,CHON,DOANHSO)"+
								" select "+tungay+","+tungay+",'"+getDateTime()+"','100671',"+nppId+",'1','1' ";
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					return "Lỗi hệ thống "+query;
				}

				query=  "	 insert into tonkhongay(kho_fk, npp_fk, sanpham_fk, ngay, soluong, kbh_fk) " +
						"	select distinct kho_fk, npp_fk, sanpham_fk,"+tungay+" as ngay, soluong, kbh_fk "+
						" from nhapp_kho where npp_fk ="+this.nppId+" and soluong > 0 ";	
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					return "Lỗi hệ thống "+query;
				}
				query="   	delete from 	TONKHONGAY_CHITIET where NGAY="+tungay+" and NPP_FK= 	"+this.nppId;

				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					return "Lỗi hệ thống "+query;
				}
				query=  " insert into TONKHONGAY_CHITIET(kho_fk, npp_fk, sanpham_fk, ngay, soluong, kbh_fk, solo, ngaysanxuat) "+ 
						" select distinct kho_fk, npp_fk, sanpham_fk, "+tungay+" as ngay, soluong, kbh_fk, solo, ngayhethan  "+
						" from NHAPP_KHO_CHITIET where npp_fk = "+nppId+" and soluong > 0 ";
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					return "Lỗi hệ thống "+query;
				}



			}

			//NHAP HANG TRONG NGAY KHOA SO +1 
			query =   " SELECT B.NPP_FK,B.KBH_FK,A.KHO_FK,C.PK_SEQ AS SANPHAM_FK,SUM(CAST(SOLUONG AS INT)) AS SOLUONG   " +  
					" ,NGAYNHAN  	  " +  
					" FROM NHAPHANG_SP A INNER JOIN NHAPHANG B ON A.NHAPHANG_FK = B.PK_SEQ		  " +  
					" INNER JOIN SANPHAM C ON C.MA = A.SANPHAM_FK   	  " +  
					" WHERE   B.TRANGTHAI =1 AND   " +  
					" B.NGAYNHAN = (select  Replace(convert(char(10), DATEADD(day, 1, cast(MAX(NGAYKS) as datetime))  "+
					" , 102) , '.', '-' )   FROM KHOASONGAY WHERE NPP_FK= "+this.nppId+")  " +  
					" AND B.NPP_FK='"+this.nppId+"'" +  
					" GROUP BY B.NPP_FK,B.KBH_FK,C.PK_SEQ,A.KHO_FK,NGAYNHAN  ";

			System.out.println("[NHAP HANG NGAY CUOI CUNG  : ]"+query);
			rs=db.get(query);
			while (rs.next())
			{
				String nppId = rs.getString("NPP_FK");
				String khoId = rs.getString("KHO_FK");
				String spId = rs.getString("SANPHAM_FK");
				String kenhId = rs.getString("KBH_FK");
				int soluong= rs.getInt("SOLUONG");

				query=" UPDATE NHAPP_KHO SET SOLUONG=SOLUONG + "+soluong+",AVAILABLE=AVAILABLE + "+soluong+"  " +
						"where npp_fk='"+nppId+"'  and sanpham_fk='"+spId+"' and kho_fk='"+khoId+"' and kbh_fk='"+kenhId+"' ";

				System.out.println( " \n Nhap Kho Chi Tiet Nek "+query);


				int sodong =this.db.updateReturnInt(query);

				if(sodong <0 ){
					this.db.getConnection().rollback();
					return "Lỗi hệ thống "+query;
				}


				if(sodong==0)
				{
					query=
							"insert into NHAPP_KHO(KHO_FK,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK)"+
									" select '"+khoId+"','"+nppId+"','"+spId+"','"+soluong+"',0,"+soluong+",'0','"+kenhId+"' ";					
					if(!db.update(query))
					{
						this.db.getConnection().rollback();
						return "Lỗi hệ thống "+query;
					}
				}

				//	System.out.println("[NHAPP_KHO]"+query);
				query=" UPDATE NHAPP_KHO_CHITIET SET SOLUONG=SOLUONG + "+soluong+",AVAILABLE=AVAILABLE + "+soluong+"  " +
						"where npp_fk='"+nppId+"'  and sanpham_fk='"+spId+"' and kho_fk='"+khoId+"' and kbh_fk='"+kenhId+"' " +
						" AND SOLO='DMSVIFON201308' ";
				sodong =this.db.updateReturnInt(query);

				if(sodong <0 ){
					this.db.getConnection().rollback();
					return "Lỗi hệ thống "+query;
				}

				if(sodong==0)
				{
					query="insert into NHAPP_KHO_CHITIET(KHO_FK,NPP_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIAMUA,KBH_FK,SOLO,NGAYHETHAN)"+
							" select '"+khoId+"','"+nppId+"','"+spId+"','"+soluong+"',0,"+soluong+",'0','"+kenhId+"','DMSVIFON201308','2014-12-01' ";				
					if(!db.update(query))
					{
						this.db.getConnection().rollback();
						return "Lỗi hệ thống "+query;
					}
				}
				//	System.out.println("[NHAPP_KHO_CHITIET]"+query);

			}




			//cAP NHAT TON KHO NGAY



			// DON HANG VA DON HANG CHUA XUAT KHO trang thai =0 

			query="  SELECT SUM(SOLUONG) AS TONCUOI,NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK  " +  
					"FROM   (    " +  
					"		SELECT DH.NPP_FK,DH.KBH_FK,DHSP.KHO_FK,DHSP.SANPHAM_FK,SUM(SOLUONG) AS SOLUONG ,  " +  
					"		N'4.Xu?t hàng bán' AS TYPE ,NGAYNHAP  			  " +  
					"		FROM DONHANG_SANPHAM DHSP    			  " +  
					"		INNER JOIN DONHANG DH ON DH.PK_SEQ = DHSP.DONHANG_FK  	    " +  
					"		WHERE DH.TRANGTHAI =0    " +  
					"		     " +  
					"		AND DH.NPP_FK="+this.nppId+"    " +  
					"		GROUP BY  DH.NPP_FK,DH.KBH_FK,DHSP.SANPHAM_FK,DHSP.KHO_FK 	,NGAYNHAP	    " +  
					"UNION ALL  	  " +  
					"		SELECT DONH.NPP_FK,DONH.KBH_FK,KM.KHO_FK,SPKM.PK_SEQ AS SANPHAM_FK,  " +  
					"		 ISNULL(SUM(TRAKM_KHACHHANG.SOLUONG),0) AS SOLUONG  ,  " +  
					"		N'5.Xu?t hàng KM' AS TYPE,NGAYNHAP  	  " +  
					"		FROM DONHANG_CTKM_TRAKM TRAKM_KHACHHANG  				   " +  
					"		INNER JOIN     " +  
					"		(  	  		SELECT *   	FROM DONHANG   	  		  " +  
					"		WHERE TRANGTHAI =0  		  " +  
					"		    " +  
					"		AND NPP_FK= "+this.nppId+"   	) DONH ON DONH.PK_SEQ = TRAKM_KHACHHANG.DONHANGID     " +  
					"		INNER JOIN SANPHAM SPKM ON SPKM.MA = TRAKM_KHACHHANG.SPMA  	    " +  
					"		INNER JOIN CTKHUYENMAI KM ON KM.PK_SEQ = TRAKM_KHACHHANG.CTKMID    " +  
					"		WHERE LEN(TRAKM_KHACHHANG.SPMA) >0 	    " +  
					"		GROUP BY DONH.NPP_FK,DONH.KBH_FK,KM.KHO_FK,SPKM.PK_SEQ ,NGAYNHAP	  " +  
					"   " +  
					") AS DATA  GROUP BY NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK  ";

			System.out.println("[XNT]"+query);
			rs=db.get(query);
			while (rs.next())
			{
				String nppId = rs.getString("NPP_FK");
				String khoId = rs.getString("KHO_FK");
				String spId = rs.getString("SANPHAM_FK");
				String kenhId = rs.getString("KBH_FK");
				int toncuoi= rs.getInt("toncuoi");


				//	System.out.println("[NHAPP_KHO]"+query);
				query=	" UPDATE NHAPP_KHO  SET booked=booked + "+toncuoi+",AVAILABLE=AVAILABLE - "+toncuoi+" " +
						" where npp_fk='"+nppId+"'  and sanpham_fk='"+spId+"' and kho_fk='"+khoId+"' and kbh_fk='"+kenhId+"' " ;


				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					return "Lỗi hệ thống "+query;
				}



			}


			// don hang da xuat kho trang thai 

			query=     "  SELECT SUM(SOLUONG) AS TONCUOI,NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK  " +  
					"  FROM   (    " +  
					"		SELECT DH.NPP_FK,DH.KBH_FK,DHSP.KHO_FK,DHSP.SANPHAM_FK,  SUM(SOLUONG) AS SOLUONG ,  " +  
					"		N'4.Xu?t hàng bán' AS TYPE ,NGAYNHAP  			  " +  
					"		FROM DONHANG_SANPHAM DHSP    			  " +  
					"		INNER JOIN DONHANG DH ON DH.PK_SEQ = DHSP.DONHANG_FK  	    " +  
					"		WHERE DH.TRANGTHAI = 3    " +  
					"		AND DH.NPP_FK="+this.nppId+"    " +  
					"		GROUP BY  DH.NPP_FK,DH.KBH_FK,DHSP.SANPHAM_FK,DHSP.KHO_FK 	,NGAYNHAP	    " +  
					" UNION ALL  	  " +  
					"		SELECT DONH.NPP_FK,DONH.KBH_FK,KM.KHO_FK,SPKM.PK_SEQ AS SANPHAM_FK,  " +  
					"		  ISNULL(SUM(TRAKM_KHACHHANG.SOLUONG),0) AS SOLUONG  ,  " +  
					"		N'5.Xu?t hàng KM' AS TYPE,NGAYNHAP  	  " +  
					"		FROM DONHANG_CTKM_TRAKM TRAKM_KHACHHANG  				   " +  
					"		INNER JOIN     " +  
					"		(  	  		SELECT *   	FROM DONHANG   	  		  " +  
					"		WHERE TRANGTHAI =3  		  " +  
					"		    " +  
					"		AND NPP_FK= "+this.nppId+"   	) DONH ON DONH.PK_SEQ = TRAKM_KHACHHANG.DONHANGID     " +  
					"		INNER JOIN SANPHAM SPKM ON SPKM.MA = TRAKM_KHACHHANG.SPMA  	    " +  
					"		INNER JOIN CTKHUYENMAI KM ON KM.PK_SEQ = TRAKM_KHACHHANG.CTKMID    " +  
					"		WHERE LEN(TRAKM_KHACHHANG.SPMA) >0 	    " +  
					"		GROUP BY DONH.NPP_FK,DONH.KBH_FK,KM.KHO_FK,SPKM.PK_SEQ ,NGAYNHAP	  " +  
					"   " +  
					") AS DATA  GROUP BY NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK  ";

			System.out.println("[XNT]"+query);
			rs=db.get(query);
			while (rs.next())
			{
				String nppId = rs.getString("NPP_FK");
				String khoId = rs.getString("KHO_FK");
				String spId = rs.getString("SANPHAM_FK");
				String kenhId = rs.getString("KBH_FK");
				int toncuoi= rs.getInt("toncuoi");



				//	System.out.println("[NHAPP_KHO]"+query);
				query=	" UPDATE NHAPP_KHO  SET soluong =soluong  - "+toncuoi+" ,AVAILABLE= AVAILABLE - "+toncuoi+" " +
						" where npp_fk='"+nppId+"'  and sanpham_fk='"+spId+"' and kho_fk='"+khoId+"' and kbh_fk='"+kenhId+"' " ;
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					return "Lỗi hệ thống "+query;
				}



				query=	" UPDATE NHAPP_KHO_CHITIET  SET soluong =soluong  - "+toncuoi+" ,AVAILABLE= AVAILABLE - "+toncuoi+" " +
						" where npp_fk='"+nppId+"'  and sanpham_fk='"+spId+"' and kho_fk='"+khoId+"' and kbh_fk='"+kenhId+"' AND SOLO='DMSVIFON201308' " ;
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					return "Lỗi hệ thống "+query;
				}

			}

			/*
			 * 
			 * Đơn hàng đã chốt (1) sau ngày khóa sổ cuối cùng
			 * 
			 */
			String ngaySauKsMax ="(select convert(varchar(10),DATEADD(DAY,1, (select max(ngayks) from khoasongay where npp_fk="+this.nppId +")   ),20) ) ";
			query=     "  SELECT SUM(SOLUONG) AS TONCUOI,NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK  " +  
					"  FROM   (    " +  
					"		SELECT DH.NPP_FK,DH.KBH_FK,DHSP.KHO_FK,DHSP.SANPHAM_FK,  SUM(SOLUONG) AS SOLUONG ,  " +  
					"		N'4.Xu?t hàng bán' AS TYPE ,NGAYNHAP  			  " +  
					"		FROM DONHANG_SANPHAM DHSP    			  " +  
					"		INNER JOIN DONHANG DH ON DH.PK_SEQ = DHSP.DONHANG_FK  	    " +  
					"		WHERE DH.TRANGTHAI = 1    " +  
					"		AND DH.NPP_FK="+this.nppId+"  and dh.ngaynhap >="+ngaySauKsMax +" " +  
					"		GROUP BY  DH.NPP_FK,DH.KBH_FK,DHSP.SANPHAM_FK,DHSP.KHO_FK 	,NGAYNHAP	    " +  
					" UNION ALL  	  " +  
					"		SELECT DONH.NPP_FK,DONH.KBH_FK,KM.KHO_FK,SPKM.PK_SEQ AS SANPHAM_FK,  " +  
					"		  ISNULL(SUM(TRAKM_KHACHHANG.SOLUONG),0) AS SOLUONG  ,  " +  
					"		N'5.Xu?t hàng KM' AS TYPE,NGAYNHAP  	  " +  
					"		FROM DONHANG_CTKM_TRAKM TRAKM_KHACHHANG  				   " +  
					"		INNER JOIN     " +  
					"		(  	 SELECT *   	FROM DONHANG dh  	WHERE TRANGTHAI =1  and dh.ngaynhap >="+ngaySauKsMax +"	 	  " +  
					"		AND NPP_FK= "+this.nppId+"   	) DONH ON DONH.PK_SEQ = TRAKM_KHACHHANG.DONHANGID     " +  
					"		INNER JOIN SANPHAM SPKM ON SPKM.MA = TRAKM_KHACHHANG.SPMA  	    " +  
					"		INNER JOIN CTKHUYENMAI KM ON KM.PK_SEQ = TRAKM_KHACHHANG.CTKMID    " +  
					"		WHERE LEN(TRAKM_KHACHHANG.SPMA) >0 	    " +  
					"		GROUP BY DONH.NPP_FK,DONH.KBH_FK,KM.KHO_FK,SPKM.PK_SEQ ,NGAYNHAP	  " +  
					"   " +  
					") AS DATA  GROUP BY NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK  ";

			System.out.println("[XNT]"+query);
			rs=db.get(query);
			while (rs.next())
			{
				String nppId = rs.getString("NPP_FK");
				String khoId = rs.getString("KHO_FK");
				String spId = rs.getString("SANPHAM_FK");
				String kenhId = rs.getString("KBH_FK");
				int toncuoi= rs.getInt("toncuoi");

				//	System.out.println("[NHAPP_KHO]"+query);
				query=	" UPDATE NHAPP_KHO  SET soluong =soluong  - "+toncuoi+" ,AVAILABLE= AVAILABLE - "+toncuoi+" " +
						" where npp_fk='"+nppId+"'  and sanpham_fk='"+spId+"' and kho_fk='"+khoId+"' and kbh_fk='"+kenhId+"' " ;
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					return "Lỗi hệ thống "+query;
				}



				query=	" UPDATE NHAPP_KHO_CHITIET  SET soluong =soluong  - "+toncuoi+" ,AVAILABLE= AVAILABLE - "+toncuoi+" " +
						" where npp_fk='"+nppId+"'  and sanpham_fk='"+spId+"' and kho_fk='"+khoId+"' and kbh_fk='"+kenhId+"' AND SOLO='DMSVIFON201308' " ;
				if(!db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg+="Lỗi hệ thống "+query;
					return "Lỗi hệ thống "+query;
				}

			}



			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "Lỗi hệ thống "+e.getMessage();
		}
		return "";
	}

	String ghichu="";

	public String getGhichu()
	{
		return ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}
	
	public void setTask(String task) 
	{
		this.task =task;
	}

	public String getTask() 
	{
		return this.task;
	}

}
