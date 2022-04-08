package geso.dms.distributor.beans.banggiablnpp.imp;


import geso.dms.distributor.beans.banggiablnpp.IBanggiablnpp;
import geso.dms.distributor.beans.banggiablnpp.ISanpham;
import geso.dms.distributor.beans.banggiablnpp.imp.Sanpham;
import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.FormAction;

public class Banggiablnpp implements IBanggiablnpp
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String tenbangia;
	String donvikinhdoanh;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String dvkdId;
	String msg;
	String tungay;
	String kenh;
	String view;

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}
	String trangthai = "";
	ResultSet ncc;
	ResultSet dvkd;
	
	List<ISanpham> splist;
	
	String nppId;
	String nppTen;
	String sitecode;
	dbutils db;

	public Banggiablnpp(String[] param)
	{
		this.id = param[0];
		this.tenbangia = param[1];
		this.donvikinhdoanh = param[2];
		this.ngaytao = param[3];
		this.nguoitao = param[4];
		this.ngaysua = param[5];
		this.nguoisua = param[6];
		this.nppId = param[7];
		this.dvkdId = param[8];
		this.tungay = "";
		this.kenh = "";
		this.msg = "";
	
	}
	
	public Banggiablnpp(String id)
	{
		this.id = id;
		this.tenbangia = "";
		this.donvikinhdoanh = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nppId = "";
		this.dvkdId = "";
		this.msg = "";
		this.tungay = "";
		this.kenh = "";
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
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;	
	}
	
	public String getTenbanggia()
	{
		return this.tenbangia;
	}
	
	public void setTenbanggia(String tenbanggia)
	{
		this.tenbangia = tenbanggia;
	}
	
	public String getDonvikinhdoanh()
	{
		return this.donvikinhdoanh;
	}
	
	public void setDonvikinhdoanh(String donvikinhdoanh)
	{
		this.donvikinhdoanh = donvikinhdoanh;
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
		
	public String getDvkdId()
	{
		return this.dvkdId;
	}
		
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}
	
	public ResultSet getNcc()
	{
		return this.ncc;
	}
	
	public void setNcc(ResultSet ncc)
	{
		this.ncc = ncc;
	}
	
	public ResultSet getDvkd()
	{
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd)
	{
		this.dvkd = dvkd;
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
	
	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	private void getNppInfo()
	{
		/*
		ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
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
			
		}catch(Exception e){}	
		*/
		//Phien ban moi
		Utility util=new Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public boolean CreateBgbl(String[] spId, String[] spGiabanlechuan, String[] spGiabanlenpp) 
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			//sua muc tieu kiem tra co bang gia cho nha phan phoi va don vi kinh doanh nao do
			List<Object> data = new ArrayList<Object>();
			String query = "select count(pk_seq) as num from banggiabanlenpp where dvkd_fk= ? and npp_fk = ?";
			ResultSet rs = db.getByPreparedStatement(query, new Object[]{this.nppId,this.dvkdId});



			rs.next();
			if(rs.getString("num").equals("0")){
				// Update table "Don Hang"
				query = "insert into Banggiabanlenpp(ten, ngaytao, ngaysua, nguoitao, nguoisua, dvkd_fk, npp_fk) " +
				" select ?,?,?,?,?,?,? ";
				//query = query + "values( N'" + this.tenbangia + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','" + this.dvkdId + "','" + this.nppId + "')";
		data.clear();
		data.add(this.tenbangia);data.add(this.ngaytao);data.add(this.ngaytao);data.add(this.nguoitao);data.add(this.nguoitao);data.add(this.dvkdId);data.add(this.nppId);
		if( this.db.updateQueryByPreparedStatement(query, data)<0 )
		{
			dbutils.viewQuery(query, data);
			this.msg="Lỗi tạo mới NVBH(1)";
			this.db.getConnection().rollback();
			return false;
		}	
				query = "select IDENT_CURRENT('Banggiabanlenpp') as bgblnppId";
				rs = this.db.get(query);
				rs.next();
				this.id = rs.getString("bgblnppId");
				
				if(spId != null)
				{
					int m = 0;
					while(m < spId.length)
					{
						if(spGiabanlenpp[m].length() > 0) //chi them nhung san pham co gia
						{
						//	System.out.println("asdas:" +spGiabanlechuan[m] +"  "+spGiabanlenpp[m]);
							String sql = "insert into bgbanlenpp_sanpham(bgbanlenpp_fk, sanpham_fk, giabanlechuan, giabanlenpp) values('"+ this.id +"','" + spId[m] + "','" + spGiabanlechuan[m].replaceAll(",", "") + "','" + spGiabanlenpp[m].replaceAll(",", "") + "')";
				           
							if(Integer.parseInt(spGiabanlechuan[m].replaceAll(",", "")) < Integer.parseInt(spGiabanlenpp[m].replaceAll(",", "")))
				            {
				            	geso.dms.center.util.Utility.rollback_throw_exception(this.db);
								this.msg = "Ban can kiem tra lai, gia ban khong duoc > gia chuan";
								return false; 
				            }
							
							if(!this.db.update(sql))
							{
								geso.dms.center.util.Utility.rollback_throw_exception(this.db);
								this.msg = "Loi khi cap nhat bang bgbanlenpp_sanpham, ";
								return false; 
							}
						}
						m++;
					}
				}
				if(rs!=null){
					rs.close();
				}
			}else{
				this.msg = "Bang gia cho Don vi kinh doanh " + this.donvikinhdoanh + " da ton tai";
				
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		}catch(Exception e){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg="Khong The Them Bang Gia Nay,Vui Long Kiem Tra Lai,Xay Ra Loi Sau : "+ e.toString();
			return false;
		}
		return true;
	}
	
	public boolean UpdateBgbl(String[] spId, String[] spGiabanlechuan, String[] spGiabanlenpp,String[] giathung,String[] quycach) 
	{	
		dbutils cn=new dbutils();
	
		try{
		cn.getConnection().setAutoCommit(false);
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		// Update table "Don Hang"
		String query = "update Banggiabanlenpp set ten=N'" + this.tenbangia + "', ngaysua='" + this.ngaysua + "', nguoisua='" + this.nguoisua + "' where pk_seq='" + this.id + "'";
		if(!cn.update(query))
		{
			cn.update("rollback");
			this.msg = "Khong the cap nhat 'Bang gia ban le' , ";
			return false; 
		}

		if(spId != null)
		{
			query = "delete from bgbanlenpp_sanpham where bgbanlenpp_fk= '" + this.id + "'" ;
			if(!cn.update(query)){
				cn.update("rollback");
				this.msg = "Khong the cap nhat 'Bang gia ban le' , ";
				return false;
			}
		
			int m = 0;
			while(m < spId.length)
			{
				if(spGiabanlenpp[m].trim().length() > 0 ) //chi them nhung san pham co gia
				{
					double giabl = Double.parseDouble(spGiabanlenpp[m].replaceAll(",", ""));
					if(Double.parseDouble(spGiabanlechuan[m].replaceAll(",", "")) > giabl)
		            {
		            	cn.update("rollback");
						this.msg = "Ban can kiem tra lai, gia ban khong duoc < gia chuan";
						return false; 
		            }
					
					String sql = "insert into bgbanlenpp_sanpham(bgbanlenpp_fk, sanpham_fk, giabanlechuan, giabanlenpp) " +
								 "values('" + this.id + "', '" + spId[m] + "', '" + spGiabanlechuan[m].replaceAll(",", "") + "', '" + giabl + "')";
					//System.out.println("CAP NHAT: " + sql);
					if(!cn.update(sql))
					{
						cn.update("rollback");
						this.msg = "Loi khi cap nhat bang bgbanlenpp_sanpham, ";
						return false; 
					}
				}
				m++;
			}
		}	
		cn.getConnection().commit();
		cn.getConnection().setAutoCommit(true);

		}catch(Exception er){
			cn.update("rollback");
			er.printStackTrace();
			this.msg="Loi Khi Cap Nhat Lại Bang Gia,Vui Long Bao Voi Admin. Error : 352 ";
			return false;
		}
		return true;
	}
		
	public void init() 
	{
		this.getNppInfo();
		//String sql ="select * from vwbanggiabanlenpp where nppId='" + this.nppId + "' and bgblId='" + this.id + "'";
		
		//NPP khong duoc phep sua gia ban,chenh 10% so voi bang gia mau cho NPP
		String sql = "select PK_SEQ as bgblId, DVKD_FK as dvkdId, TEN as bgblTen, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA " +
					 "from BANGGIABANLENPP where PK_SEQ = ?";
		ResultSet rs = db.getByPreparedStatement(sql, new Object[]{this.id});
		
		try
        {
            rs.next();        	
            this.id = rs.getString("bgblId");
			this.tenbangia = rs.getString("bgblTen");
			this.dvkdId = rs.getString("dvkdId");			
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");					
			rs.close();
       	}
        catch(Exception e){}
        this.createDonvikd();
        this.createNhacungcap();
        this.createSanphamList();
	}
	
	private void createNhacungcap()
	{
		this.ncc = this.db.get("select pk_seq as nccId, ten as nccTen from nhacungcap");
	}
	
	private void createDonvikd()
	{
//		String sql = "select a.pk_seq as dvkdId, a.donvikinhdoanh as dvkdTenviettat, a.diengiai as dvkdTen from donvikinhdoanh a,Nhacungcap_dvkd b where a.pk_seq = b.dvkd_fk and a.trangthai='1' and b.checked ='1'";
		//String sql = "select distinct  c.donvikinhdoanh as dvkdTenviettat, c.pk_seq as dvkdId, c.diengiai as dvkdTen  from nhacungcap_dvkd a, nhapp_nhacc_donvikd b, donvikinhdoanh c where a.pk_seq = b.ncc_dvkd_fk and c.pk_seq = a.dvkd_fk and c.trangthai='1' and a.checked ='1' and b.npp_fk='" + this.nppId + "'";
		String sql ="select pk_seq as dvkdId,diengiai as dvkdTen from donvikinhdoanh ";
		this.dvkd = this.db.get(sql);
	}
	
	private void createSanphamList()
	{
		//lay san pham co gia trong bang gia
		String sql = " select bgbl_sp.sanpham_fk as spId, sp.ma as spMa, sp.ten as spTen, bgbl_sp.GIABANLENPP, bgbl_sp.GIABANLECHUAN, dv.donvi " +
					 "from BGBANLENPP_SANPHAM bgbl_sp inner join sanpham sp on bgbl_sp.sanpham_fk = sp.pk_seq  " +
					 	"inner join DONVIDOLUONG dv on sp.dvdl_fk = dv.pk_seq   " +
					 "where bgbl_sp.BGBANLENPP_FK = '" + this.id + "'    ";
		if(this.dvkdId.trim().length() > 0)
			sql += " and sp.dvkd_fk = '" + this.dvkdId + "' ";
		
		sql +=	"order by bgbl_sp.sanpham_fk ";
		
		System.out.println("SP co gia :"+sql);
		ResultSet sp_bgbl = db.get(sql);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		NumberFormat formatter = new DecimalFormat("#,###,###.####");
		System.out.println("[Init 1]"+sql);
		if(sp_bgbl != null)
		{
			String[] param = new String[5];
			ISanpham sp = null;	
			try 
			{
				while(sp_bgbl.next())
				{
					param[0] = sp_bgbl.getString("spId");
					param[1] = sp_bgbl.getString("spMa");
					param[2] = sp_bgbl.getString("spTen");
					param[3] = formatter.format(sp_bgbl.getDouble("giabanlechuan"));
					param[4] = formatter.format(sp_bgbl.getDouble("giabanlenpp"));
					sp = new Sanpham(param);
					sp.setQuycach(sp_bgbl.getString("donvi"));
					splist.add(sp);
				}
				sp_bgbl.close();
			} 
			catch(Exception e) { e.printStackTrace(); }
		}
		
		this.splist = splist;		
	}
	
	public void createRS() 
	{
		this.getNppInfo();
		this.createNhacungcap();
		this.createDonvikd();
		this.createSanphamList();
	}

	public List<ISanpham> getSpList() 
	{
		return this.splist;
	}

	public void setSpList(List<ISanpham> splist) 
	{
		this.splist = splist;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{
		try 
		{
			if (!(ncc == null))	
				ncc.close();
			if (!(dvkd == null))
				dvkd.close();
			if(splist!=null){
				splist.clear();
			}
			if(db != null)
				db.shutDown();
		} 
		catch(Exception e) {}
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getKenh() {
		
		return this.kenh;
	}

	
	public void setKenh(String kenh) {
		
		this.kenh = kenh;
	}
	
	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	
	
}
