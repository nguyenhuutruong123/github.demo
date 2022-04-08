package geso.dms.distributor.beans.chitieunpp.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.chitieunpp.IChitieuSKUInTT;
import geso.dms.distributor.beans.chitieunpp.IChitieusku;


public class ChitieuSKUInTT implements IChitieuSKUInTT 
{
	String userId;
	String id;
	
	String thang;
	String nam;
	String trangthai;
	String msg;
	String diengiai;
	
	ResultSet nppRs;
	String nppIds;
	
	ResultSet nspRs;
	String nspIds;
	List<IChitieusku> spList;
	ResultSet KbhRs;
	ResultSet DvkdRs;
	
	String KbhId;
	String DvkdId;
	
	dbutils db;
	
	public ChitieuSKUInTT()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.msg = "";
		this.diengiai = "";
		this.nppIds = "";
		this.nspIds = "";
		this.KbhId="";
		this.DvkdId="";
		
		this.spList = new ArrayList<IChitieusku>();
		this.db = new dbutils();
	}
	
	public ChitieuSKUInTT(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.msg = "";
		this.diengiai = "";
		this.nppIds = "";
		this.spList = new ArrayList<IChitieusku>();
		this.db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getThang() 
	{
		return this.thang;
	}
	
	public void setThang(String thang) 
	{
		this.thang = thang;
	}
	
	public String getNam() 
	{
		return this.nam;
	}
	
	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public boolean createChiTieuThang() 
	{
		try 
		{
			if(this.DvkdId.equals("")){
				this.msg="Chọn đơn vị kinh doanh";
				return false;
			}
			if(this.KbhId.equals("")){
				this.msg="Chọn kênh bán hàng";
				return false;
				
			}
			
			if(this.thang.equals("")){
				this.msg="Chọn tháng";
				return false;
				
			}
			if(this.nam.equals("")){
				this.msg="Chọn năm";
				return false;
				
			}
			
			//check chi tieu
			String query = "select count(*) as sodong from CHITIEUSKUIN_TT where kbh_fk='"+this.KbhId+"' and dvkd_fk='"+this.DvkdId+"' and thang = '" + this.thang + "' and nam = '" + this.nam + "' and nhapp_fk = '" + this.nppIds + "'  ";
			System.out.println("___Check chi tieu: " + query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Thang " + this.thang + " Cua nam " + this.nam + " da chia chi tieu roi.";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			String ngaysua = this.getDateTime();
			
			query = "Insert CHITIEUSKUIN_TT(thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua,dvkd_fk,kbh_fk) " +
					"values('" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', '" + ngaysua + "', '" + this.userId + "', '" + ngaysua + "', '" + this.userId + "','"+this.DvkdId+"','"+this.KbhId+"')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi chi tieu thang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (SQLException e) {}
		return true;
	}

	public boolean updateChiTieuThang() 
	{
		try 
		{
			if(this.nppIds.length() <= 0)
			{
				this.msg = "Vui long chon nha phan phoi";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String ngaysua = this.getDateTime();
			
			String query = "update CHITIEUSKUIN_TT set diengiai = N'" + this.diengiai + "',  ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId + "'";
							
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat chi tieu thang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "update chitieuSKUin set trangthai = '1' where thang = '" + this.thang + "' and nam = '" + this.nam + "'  and dvkd_fk='"+this.DvkdId+"' and kbh_fk='"+this.KbhId+"' and nhapp_fk in (" + this.nppIds + ")";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat chi tieu SKU In: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//Neu da duyet het, tu dong cap nhat trang thai cua CHITIEUSKUIN_TT
			query = "select count(*) as sodong from chitieuSKUin where thang = '" + this.thang + "' and dvkd_fk='"+this.DvkdId+"' and kbh_fk='"+this.KbhId+"' and nam = '" + this.nam + "' and trangthai = '0'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count <= 0)
					{
						if(!db.update("update CHITIEUSKUIN_TT set trangthai = '1' where thang = '" + this.thang + "' and nam = '" + this.nam + "' and dvkd_fk='"+this.DvkdId+"' and kbh_fk='"+this.KbhId+"'"))
						{
							this.msg = "Khong the cap nhat CHITIEUSKUIN_TT";
							db.getConnection().rollback();
							return false;
						}
						//THUC HIEN CHO CHI TIEU VAO SS VA ASM
						
						
						
						String sql=" INSERT INTO CHITIEUPRI  " +
								"SELECT CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,'SS' ,GSBH.PK_SEQ ,SUM( ISNULL(BG.GIAMUANPP,0) * CT_SKU.CHITIEU) AS CHITIEUPRI "+ 
							" FROM CHITIEUSKUIN CT INNER JOIN CHITIEUSKUIN_SKU CT_SKU "+
							" ON CT.PK_SEQ=CT_SKU.CHITIEUSKUIN_FK "+
							" INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK=CT.NHAPP_FK  "+
							" INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ=NPP_GS.GSBH_FK "+ 
							" LEFT JOIN "+ 
							" ( "+
							" SELECT DISTINCT BGM.KENH_FK AS KBH_FK,BGM_SP.SANPHAM_FK,BGMNPP.NPP_FK,BGM_SP.GIAMUANPP AS GIAMUANPP "+
							" FROM BANGGIAMUANPP_NPP BGMNPP 		  INNER JOIN BANGGIAMUANPP BGM ON BGM.PK_SEQ = BGMNPP.BANGGIAMUANPP_FK "+ 	
							" INNER JOIN BGMUANPP_SANPHAM BGM_SP ON BGM_SP.BGMUANPP_FK = BGM.PK_SEQ  "+
							" ) AS BG  "+
							" ON BG.SANPHAM_FK=CT_SKU.SKU AND BG.KBH_FK=CT.KBH_FK AND BG.NPP_FK=CT.NHAPP_FK "+
							" WHERE NPP_GS.NGAYBATDAU <='"+this.nam+"-"+this.thang+"-01' AND SUBSTRING(NPP_GS.NGAYKETTHUC,1,4)='2100' AND CT.THANG='"+this.thang+"' and ct.nam='"+this.nam+"' and ct.dvkd_fk='"+this.DvkdId+"' and ct.bkh_fk='"+this.KbhId+"' "+
							" GROUP BY CT.KBH_FK,CT.THANG,CT.NAM,CT.DVKD_FK,GSBH.PK_SEQ";
							if(!this.db.update(sql)){
								this.msg = "Khong the cap nhat" + sql;
								db.getConnection().rollback();
								return false;
							}
							
							sql="INSERT INTO CHITIEUPRI "+
								" SELECT CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,'ASM' ,ASM.PK_SEQ ,SUM( ISNULL(BG.GIAMUANPP,0) * CT_SKU.CHITIEU) AS CHITIEUPRI "+ 
								" FROM CHITIEUSKUIN CT INNER JOIN CHITIEUSKUIN_SKU CT_SKU "+
								" ON CT.PK_SEQ=CT_SKU.CHITIEUSKUIN_FK "+
								" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=CT.NHAPP_FK "+
								" INNER JOIN ASM_KHUVUC ASM_KV ON NPP.KHUVUC_FK=ASM_KV.KHUVUC_FK "+
								" INNER JOIN ASM ON ASM.PK_SEQ=ASM_KV.ASM_FK "+
								" LEFT JOIN "+ 
								" ( "+
								" SELECT DISTINCT BGM.KENH_FK AS KBH_FK,BGM_SP.SANPHAM_FK,BGMNPP.NPP_FK,BGM_SP.GIAMUANPP AS GIAMUANPP "+
								" FROM BANGGIAMUANPP_NPP BGMNPP 		  INNER JOIN BANGGIAMUANPP BGM ON BGM.PK_SEQ = BGMNPP.BANGGIAMUANPP_FK "+ 	
								" INNER JOIN BGMUANPP_SANPHAM BGM_SP ON BGM_SP.BGMUANPP_FK = BGM.PK_SEQ "+ 
								" ) AS BG  "+
								" ON BG.SANPHAM_FK=CT_SKU.SKU AND BG.KBH_FK=CT.KBH_FK AND BG.NPP_FK=CT.NHAPP_FK "+
								" WHERE ASM_KV.NGAYBATDAU <='"+this.nam+"-"+this.thang+"-01' AND SUBSTRING(ASM_KV.NGAYKETTHUC,1,4)='2100' AND CT.THANG='"+this.thang+"' and ct.nam='"+this.nam+"' and ct.dvkd_fk='"+this.DvkdId+"' and ct.bkh_fk='"+this.KbhId+"' "+
								" GROUP BY CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK ,ASM.PK_SEQ ";
							
							if(!this.db.update(sql)){
								this.msg = "Khong the cap nhat" + sql;
								db.getConnection().rollback();
								return false;
							}
					}
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (SQLException e) {}
		return true;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void init()
	{
		String query =  "select a.kbh_fk,a.dvkd_fk,a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
						  "from CHITIEUSKUIN_TT a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
						  "where a.pk_seq = '" + this.id + "'";
		
		System.out.println("1....Khoi tao: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				
				while(rs.next())
				{
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");
					this.diengiai = rs.getString("diengiai");
					this.DvkdId=rs.getString("dvkd_fk");
					this.KbhId=rs.getString("kbh_fk");
				}
				rs.close();
			} 
			catch (SQLException e) {}
		}
		
		this.createRs();
		
	}


	public ResultSet getNppRs() 
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public String getNppIds() 
	{
		return this.nppIds;
	}

	public void setNppIds(String nppIds)
	{
		this.nppIds = nppIds;
	}

	public ResultSet getNhomspRs() 
	{
		return this.nspRs;
	}

	public void setNhomspRs(ResultSet nspRs) 
	{
		this.nspRs = nspRs;
	}

	public String getNspId() 
	{
		return this.nspIds;
	}

	public void setNspId(String nspId) 
	{
		this.nspIds = nspId;
	}

	public boolean createChiTieuSkuin() 
	{
		try 
		{
			//check chi tieu
			String query = "select count(*) as sodong from CHITIEUSKUIN where thang = '" + this.thang + "' and nam = '" + this.nam + "' and nhapp_fk = '" + this.nppIds + "' ";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Tháng " + this.thang + " của năm " + this.nam + " đã chia chỉ tiêu rồi";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			String ngaysua = this.getDateTime();
			
			query = "Insert CHITIEUSKUIN(thang, nam, diengiai, trangthai,nhapp_fk, ngaytao, nguoitao, ngaysua, nguoisua,DVKD_FK,KBH_FK) " +
					"values('" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0','"+this.nppIds +"','" + ngaysua + "', '" + this.userId + "', '" + ngaysua + "', '" + this.userId + "', '"+ this.DvkdId+"', '"+this.KbhId+ "')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			String parkCurrent = "";//Lay so PK_SEQ park vua insert
			query = "select IDENT_CURRENT('CHITIEUSKUIN') as skuId";
			
			ResultSet rsNh = db.get(query);						
			if(rsNh.next())
			{
				parkCurrent = rsNh.getString("skuId");
				rsNh.close();
			}
			for(int i=0;i<this.spList.size();i++)
			{
				 IChitieusku ddkd = this.spList.get(i);
				query = "select pk_seq from sanpham where ma='"+ ddkd.getMasanpham() +"'";
				String sp="NULL";
				ResultSet rsNhsp = db.get(query);						
				if(rsNhsp.next())
				{
					sp = rsNhsp.getString("pk_seq");
					rsNhsp.close();
				}
				if(sp=="NULL")
				{
					this.msg = "Không có sku nào có mã: " + ddkd.getMasanpham();
					db.getConnection().rollback();
					return false;
				}
				//System.out.println("queryvvvv"+query);
				query = "Insert CHITIEUSKUIN_SKU(CHITIEUSKUIN_FK, SKU,CHITIEU) " +
				"values('" + parkCurrent+ "', '" + sp + "', '" + ddkd.getSoluong() + "')";
					
					System.out.println("1.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "Lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}

	public boolean updateChiTieuSkuin() 
	{
		try 
		{
			//check chi tieu
			String query = "select count(*) as sodong from CHITIEUSKUIN where thang = '" + this.thang + "' and nam = '" + this.nam + "' and nhapp_fk = '" + this.nppIds + "' and pk_seq!='"+this.id+"'";
			System.out.println("ccccc" +query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Tháng " + this.thang + " của năm " + this.nam + " đã chia chỉ tiêu rồi";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			String ngaysua = this.getDateTime();
			
			/*query = "Insert CHITIEUSKUIN(thang, nam, diengiai, trangthai,npp_fk ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values('" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0','"+this.nppIds +"','" + ngaysua + "', '" + this.userId + "', '" + ngaysua + "', '" + this.userId + "')";*/
		 query = "update CHITIEUSKUIN set thang='"+this.thang+"',nam='"+this.nam+"',diengiai=N'"+this.diengiai+"',nhapp_fk='"+ this.nppIds +"',dvkd_fk='"+ this.DvkdId +"',kbh_fk='"+ this.KbhId +"' where pk_seq='"+this.id+"'";
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "delete CHITIEUSKUIN_SKU where chitieuskuin_fk='"+ this.id +"'";
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			/*String parkCurrent = "";//Lay so PK_SEQ park vua insert
			query = "select IDENT_CURRENT('CHITIEUSKUIN') as skuId";
			
			ResultSet rsNh = db.get(query);						
			if(rsNh.next())
			{
				parkCurrent = rsNh.getString("skuId");
				rsNh.close();
			}*/
			for(int i=0;i<this.spList.size();i++)
			{
				 IChitieusku ddkd = this.spList.get(i);
				query = "select pk_seq from sanpham where ma='"+ ddkd.getMasanpham() +"'";
				String sp="NULL";
				ResultSet rsNhsp = db.get(query);						
				if(rsNhsp.next())
				{
					sp = rsNhsp.getString("pk_seq");
					rsNhsp.close();
				}
				query = "Insert CHITIEUSKUIN_SKU(CHITIEUSKUIN_FK, SKU,CHITIEU) " +
				"values('" + this.id+ "', '" + sp + "', N'" + ddkd.getSoluong() + "')";
					
					System.out.println("1.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "Lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}

	public void initnpp() 
	{
		String query =  "select a.dvkd_fk,a.kbh_fk,a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
		  "from CHITIEUSKUIN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
		  "where a.pk_seq = '" + this.id + "'";
		 System.out.println("ccccxxxxxxxxxxxx" +query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
			try 
			{
			while(rs.next())
			{
				this.thang = rs.getString("thang");
				this.nam = rs.getString("nam");
				this.diengiai = rs.getString("diengiai");
				this.KbhId = rs.getString("kbh_fk");
				this.DvkdId = rs.getString("dvkd_fk");
			}
			rs.close();
			} 
			catch (SQLException e) {}
			}
			List<IChitieusku> ctList = new ArrayList<IChitieusku>();
			query="select a.chitieu,b.ma,b.ten from CHITIEUSKUIN_SKU a inner join sanpham b on a.sku= b.pk_seq where CHITIEUSKUIN_FK= "+this.id;
			System.out.println(query);
			ResultSet hdRs = db.get(query);
			if(hdRs != null)
			{
				try 
				{
					while(hdRs.next())
					{
						IChitieusku sp = null;
						sp = new ChitieuSku();
						
						sp.setMasanpham(hdRs.getString("ma"));
						sp.setTensanpham(hdRs.getString("ten"));
						sp.setSoluong(hdRs.getString("chitieu"));
						ctList.add(sp);
					}
					hdRs.close();
				}
				catch (SQLException e) {};
			}
			this.spList=ctList;
			this.createRs();
	}


	public List<IChitieusku> getSpList() {
		return this.spList;
	}
	public void setSpList(List<IChitieusku> spList) {

		this.spList=spList;
	}


	public String getKbhId() {
		
		return this.KbhId;
	}


	public void setKbhId(String KbhId) {
		
		this.KbhId=KbhId;
		
	}


	public String getDvkdId() {
		
		return this.DvkdId;
	}


	public void setDvkdId(String DvkdId) {
		
		this.DvkdId=DvkdId;
		
	}


	public ResultSet getDvkdRs() {
		
		return this.DvkdRs;
	}


	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.DvkdRs=dvkdRs;
		
	}


	public ResultSet getKbhRs() {
		
		return this.KbhRs;
	}


	public void setKbhRs(ResultSet kbhRs) {
		
		this.KbhRs=kbhRs;
	}

	public void DbClose() 
	{
		try{
			
			if(nppRs!=null){
				nppRs.close();
			}
			if(nspRs!=null){
				nspRs.close();
			}
			if(spList!=null)
				spList.clear();
			
			if(KbhRs!=null){
				KbhRs.close();
			}
			if(DvkdRs!=null){
				DvkdRs.close();
			}
			
			if(db!=null){
				db.shutDown();
			}
		}
		catch(Exception er){}
	}

	@Override
	public void createRs() 
	{
		String query = "select a.pk_seq as nppId, a.ma, a.ten, a.diachi, b.trangthai as trang_thai, " +
						"case b.trangthai when 0 then N'Chưa duyệt' else N'Đã duyệt' end as trangthai " +
					"from nhaphanphoi a inner join CHITIEUSKUIN b on a.pk_seq = b.nhapp_fk " +
					"where thang = '" + this.thang + "' and nam = '" + this.nam + "' and b.dvkd_fk='"+this.DvkdId+"' and b.kbh_fk='"+this.KbhId+"' " +
					"order by b.trangthai desc";

		System.out.println("2.Khoi tao NPP: " + query);
		
		this.nppRs = db.get(query);
		
		this.nspRs = db.get("select pk_seq, diengiai from  nhomsanpham where trangthai = '1' and type = '0'");
		
		this.KbhRs=db.get("select pk_seq,ten from kenhbanhang where trangthai=1");
		System.out.println("select pk_seq,ten from kenhbanhang where trangthai=1");
		this.DvkdRs=db.get("select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai=1");
		System.out.println("select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai=1");
		
	}





	
}
