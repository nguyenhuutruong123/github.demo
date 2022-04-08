package geso.dms.center.beans.dontrahang.imp;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.center.beans.dontrahang.IDontrahangList;

public class DontrahangList  extends Phan_Trang   implements IDontrahangList, Serializable
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String sophieu;
	String lydo;
	String msg;
	
	ResultSet nhapkhoRs;
	ResultSet khRs;
	String khId;
	
	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	

	
	dbutils db;
	
	public DontrahangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.sophieu="";
		this.sochungtu="";
		this.lydo = "";
		this.msg = "";

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

	public void init(String search)
	{
		this.getNppInfo();
		Utility util = new Utility();
		String query = "";
        
		OracleConnUtils dbsys=new OracleConnUtils();	
		dbutils  db1=new dbutils();
			
		String sql="select distinct  a.soso,a.ngaychungtu,a.sochungtu, a.kenh,'100368' as nguoitao,'100368' as nguoisua,a.manhapp as manpp,a.loai_phieu  from apps.TBL_PHIEUXUATKHO a inner join apps.V_DONHANG b on a.soso=b.soso where trangthai=0 and loai_phieu like N'%PHIEU NHAP KHO%' and a.manhapp in (3638,2724,2542,2910,7262,6632,3243) and to_char(a.created_date, 'yyyy-mm-dd') > '2015-12-06'";
		System.out.println("[select] "+sql);
		ResultSet rssyn=dbsys.get(sql);
		if(rssyn!=null)
		{
			try {
				while(rssyn.next())
				{
					db1.getConnection().setAutoCommit(false);
					int flag=0;
					String ngaytra=rssyn.getString("ngaychungtu");
					String nguoitao=rssyn.getString("nguoitao");
					String nguoisua=rssyn.getString("nguoisua");
					int Trangthai=1;
					String npp_fk=rssyn.getString("manpp");
					String ncc_fk="100022";
					String so=rssyn.getString("soso");
					String sochungtu=rssyn.getString("sochungtu");
					
					sql = "select  (select count(*) as sl from dontrahang where so='"+so+"'  ) as sl,(select pk_seq from dontrahang where so='"+so+"' ) as pk_seq,(select so from dontrahang where so='"+so+"' ) as so";
					System.out.println("[check] "+sql);
					ResultSet rscheck=db1.get(sql);
					rscheck.next();
					int check=rscheck.getInt("sl");
					String socheck=rscheck.getString("so");
					String donhang_fk=rscheck.getString("pk_seq");
					if(check==0)
					{
						sql="insert into DONTRAHANG(ngaytra,nguoitao,nguoisua,trangthai,npp_fk,ncc_fk,sochungtu_erp,so) values ('"+ngaytra+"','"+nguoitao+"','"+nguoisua+"',"+Trangthai+",(select pk_seq from nhaphanphoi where ma='"+npp_fk+"'),'"+ncc_fk+"','"+sochungtu+"','"+so+"')";
						System.out.println("[insert] "+sql);
					}
					else
					{
						sql="update DONTRAHANG set ngaytra='"+ngaytra+"',npp_fk= (select pk_seq from nhaphanphoi where ma='"+npp_fk+"'),sochungtu_erp='"+sochungtu+"' where so='"+so+"'";
						System.out.println("[update] "+sql);
					}
						rscheck.close();
					if(db1.updateReturnInt(sql)<=0)
					{
						flag=1;
					}
					
					if(check==0)
					{
						sql="select scope_identity() as dhid";
						ResultSet rs11=db1.get(sql);
						rs11.next();
						donhang_fk=rs11.getString("dhid");
						rs11.close();
					}
					else
					{
						sql="delete from dontrahang_sp where dontrahang_fk="+donhang_fk;
						if(db1.updateReturnInt(sql)<=0)
						{
							flag=1;
						}
					}
					if(flag==0)
					{
						sql="select a.kenh, a.manhapp as manpp,a.soluong,a.dvt as donvitinh,a.masp as masanpham,a.sheme as scheme from apps.TBL_PHIEUXUATKHO a inner join apps.V_DONHANG b on a.soso=b.soso where b.soso='"+so+"'";
						System.out.println("[chi tiet sp] "+sql);
						ResultSet rs1=dbsys.get(sql);
						int dem = 0;
						while(rs1.next())
						{
							String kenh=rs1.getString("kenh");
							String manpp=rs1.getString("manpp");
							String soluong=rs1.getString("soluong");
							String masanpham=rs1.getString("masanpham");
							String donvi=rs1.getString("donvitinh");
							String scheme=rs1.getString("scheme")==null?"":rs1.getString("scheme");
							String kho_fk="100000";
							if(scheme.trim().length()>0)
							{
								String sqlkm="select "+ 
											"(select count(*) as sl from ctkhuyenmai where SCHEMEERP='"+scheme+"')  as sl "+
											",(select top(1) KHO_FK from ctkhuyenmai where SCHEMEERP='"+scheme+"') as kho_fk";
								ResultSet rskm=db1.get(sqlkm);
								rskm.next();
								if(rskm.getInt("sl")==0)
								{
									System.out.println("sp scheme ko ton tai"+scheme +" so so la "+so);
									flag=1;
								}
								else
								{
									kho_fk=rskm.getString("kho_fk");
								}
							}
							sql="insert into DONTRAHANG_SP (dontrahang_fk,sanpham_fk,soluong,donvi,dongia,sotienbvat,vat,sotienavat,ptvat,kbh_fk,kho_fk,scheme) values ("+donhang_fk+",(select pk_seq from sanpham where ma='"+masanpham+"'),'"+soluong+"','"+donvi+"',0,0,0,0,0,(select pk_seq from kenhbanhang where diengiai like N'"+kenh+"'),'"+kho_fk+"','"+scheme+"') ";
							System.out.println("insert san pham "+sql);
							if(db1.updateReturnInt(sql)<=0 )
							{
								flag=1;
							}
						}
					}
					
					if(flag==1)
					{
						db1.getConnection().rollback();
						db1.getConnection().setAutoCommit(true);
						System.out.println("vao rollback");
					}
					else
					{
						db1.getConnection().commit();
						db1.getConnection().setAutoCommit(true);
					}	
				}
			}
			catch (SQLException e) {
				try {
					db1.getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			db1.shutDown();
			dbsys.shutDown();
		}
		
		if(search.length() > 0)
			query = search;
		else
		{
			query =	
					"	select a.pk_Seq,b.MA as nppMa,b.TEN as nppTen,a.NGAYTRA,c.TEN as nguoiTao,d.TEN as nguoiSua,e.TEN as tructhuoc,a.TRANGTHAI,a.SOTIENBVAT,a.Modified_Date,a.created_date "+
					"		from DONTRAHANG a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "+
					"		inner join NHANVIEN c on c.PK_SEQ=a.NGUOITAO  "+
					"		inner join NHANVIEN d on d.PK_SEQ=a.NGUOISUA "+
					"		inner join NhaCungCap e on e.PK_SEQ=a.NCC_FK " +
					" where 1=1 AND A.TrangThai!=0 ";
		} 
		query += " and a.npp_fk in "+util.quyen_npp(this.userId);
		System.out.println("___CHUYEN KHO: " + query);
		
		this.nhapkhoRs = createSplittingData(50, 10, "NGAYTRA desc, pk_Seq desc, TRANGTHAI asc ", query);
		this.khRs = db.get("select PK_SEQ, TEN,MA,DiaChi from NHAPHANPHOI where trangthai = '1'");
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getSophieu()
	{
		return sophieu;
	}

	public void setSophieu(String sophieu) 
	{
		this.sophieu = sophieu;
	}

	public String getLydo() 
	{
		return lydo;
	}

	public void setLydo(String lydo) 
	{
		this.lydo = lydo;
	}

	public String getTungayTao() 
	{
		return this.tungay;
	}

	public void setTungayTao(String tungay) 
	{
		this.tungay =tungay;	
	}

	public String getDenngayTao() 
	{
		return this.denngay;
	}

	public void setDenngayTao(String denngay) 
	{
		this.denngay = denngay;
	}

	public ResultSet getNhapkhoRs() 
	{
		return this.nhapkhoRs;
	}

	public void setNhapkhoRs(ResultSet nkRs) 
	{
		this.nhapkhoRs = nkRs;
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
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		System.out.println("NPPID: "+this.nppId +" USERID: "+this.userId);
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public String getSochungtu() {
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu=sochungtu;
	}

	public ResultSet getKhRs() {
		return this.khRs;
	}

	public void setKhRs(ResultSet khrs) {
		this.khRs=khrs;
		
	}

	public String getKhId() {
		return this.khId;
	}

	public void setKhId(String KhId) {
		this.khId=KhId;
		
	}
	
	
}
