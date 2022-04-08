package geso.dms.center.beans.banggiamuanpp.imp;

import geso.dms.center.beans.banggiamuanpp.IBanggiamuanpp;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import geso.dms.center.db.sql.*;
public class Banggiamuanpp implements IBanggiamuanpp
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String userTen;
	String id;
	String ten;
	String dvkd;
	String dvkdId;
	String kenh;
	String kenhId;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	dbutils db;
	String maspstr;
	ResultSet dvkdIds;
	ResultSet kenhIds;
	String[] spIds;
	String[] masp;
	String[] tensp;
	
	ResultSet sanphamlist;
	ResultSet newsplist;
	String[] giamuanpp;
	String[] dv;
	String[] tthai; 
	
	public Banggiamuanpp(String[] param)
	{
		this.db = new dbutils();
		this.id 		= param[0];
		this.ten 		= param[1];
		this.dvkd 		= param[2];
		this.kenh		= param[3];
		this.trangthai 	= param[4];
		this.ngaytao 	= param[5];
		this.nguoitao 	= param[6];
		this.ngaysua 	= param[7];
		this.nguoisua 	= param[8];
		this.msg = "";
		this.dvkdId = "";
		this.kenhId = "";
		createRS();
	}
	
	public Banggiamuanpp()
	{	
		this.db = new dbutils();
		this.id 		= "";
		this.ten 		= "";
		this.dvkd 		= "";
		this.kenh		= "";
		this.trangthai 	= "1";
		this.ngaytao 	= "";
		this.nguoitao 	= "";
		this.ngaysua 	= "";
		this.nguoisua 	= "";
		this.msg = "";
		this.dvkdId = "";
		this.kenhId = "";
		createRS();
	}

	public String getUserId() 
	{
		return this.userId;
	}
	
	public void setUserId(String userId) 
	{
		this.userId = userId;
		this.userTen = "Nobody";
		ResultSet rs = this.db.get("select ten from nhanvien where pk_seq ='" + this.userId + "'");
		try{
			rs.next();
			this.userTen = rs.getString("ten");
		}catch(Exception e){}
			
	}
	
	public String getUserTen() 
	{
		
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
	}

	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}
	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getDvkd() 
	{
		return this.dvkd;
	}
	
	public void setDvkd(String dvkd) 
	{
		this.dvkd = dvkd;
	}

	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
	}
	
	public String getKenh() 
	{
		return this.kenh;
	}
	
	public void setKenh(String kenh) 
	{
		this.kenh = kenh;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
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
	
	public ResultSet getDvkdIds() 
	{
		return this.dvkdIds;
	}

	public void setDvkdIds(ResultSet dvkdIds) 
	{
		this.dvkdIds = dvkdIds;
	}

	public ResultSet getKenhIds() 
	{
		return this.kenhIds;
	}

	public void setKenhIds(ResultSet kenhIds) 
	{
		this.kenhIds = kenhIds;
	}

	public String[] getSpIds() 
	{
		return this.spIds;
	}

	public void setSpIds(String[] spIds) 
	{
		this.spIds = spIds;
	}

	public String[] getMasp() 
	{
		return this.masp;
	}

	public void setMasp(String[] masp) 
	{
		this.masp = masp;
	}

	public String[] getTensp() 
	{
		return this.tensp;
	}

	public void setTensp(String[] tensp) 
	{
		this.tensp = tensp;
	}

	public String[] getTthai() 
	{
		return this.tthai;
	}

	public void setTthai(String[] tthai) 
	{
		this.tthai = tthai;
	}

	public String[] getDonvi() 
	{
		return this.dv;
	}

	public void setDonvi(String[] dv) 
	{
		this.dv = dv;
	}

	public String getMaspstr() 
	{
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}
	
	public ResultSet getSanPhamList() 
	{
		return this.sanphamlist;
	}

	public void setSanPhamList(ResultSet sanphamlist) 
	{
		this.sanphamlist = sanphamlist;
	}

	public ResultSet getNewSanPhamList() 
	{
		return this.newsplist;
	}

	public void setNewSanPhamList(ResultSet newsplist) 
	{
		this.newsplist = newsplist;
	}

	public String[] getGiamuanpp(){
		return this.giamuanpp;
	}
	
	public void setGiamuanpp(String[] giamuanpp){
		this.giamuanpp = giamuanpp;
	}
	
	public boolean CreateBgmuanpp(HttpServletRequest request) 
	{	
		try{
			this.db.getConnection().setAutoCommit(false);
		
			String query;
		
//			query = "select count(pk_seq) as count from banggiamuanpp where dvkd_fk='" + this.dvkdId + "' and kenh_fk = '" + this.kenhId + "'";
			
//			ResultSet rs = this.db.get(query);
//			rs.next();
//			String count = rs.getString("count");
//			rs.close();
//			if (!count.equals("0") & this.kenhId.equals("100000")){
//				this.msg = "Bảng giá mua dành cho NPP kênh GT đã tồn tại";
//				return false;
//			}else{
				String command = "insert into banggiamuanpp values(N'" + this.ten + "','" + this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "','" + this.dvkdId + "','" + this.kenhId + "', '" + this.trangthai + "')";

				if(!this.db.update(command)){	
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.msg = command;
					return false;
				}

				ResultSet tmp = this.db.get("select IDENT_CURRENT('banggiamuanpp') as bgmuanppId");
				tmp.next();
				
				String bgmuanppId = tmp.getString("bgmuanppId");				
				
				createSpArray();
				for(int i = 0; i < this.spIds.length; i++){
					String gia = request.getParameter("gia" + this.spIds[i]);
					if (gia.length()==0){
						gia = "0";
					}
					
					String trangthai = request.getParameter("chbox" + this.spIds[i]);
					if (trangthai != null){
						trangthai = "1";
					}else{
						trangthai = "0";
					}
					if (gia.equals("0"))
						trangthai="0";
					gia=gia.replaceAll(",", "");
					command = "insert into bgmuanpp_sanpham values('" + bgmuanppId + "', '" + this.spIds[i] + "', '" + gia + "','" + trangthai + "')";
					if(!this.db.update(command)){	
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg = command;
						return false;
					}
				}
				tmp.close();
//			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception e){
			this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + e.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
			}
		
		return true ;
	}

	public boolean UpdateBgmuanpp(HttpServletRequest request) 
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			String command="update banggiamuanpp set ten = N'" + this.ten + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "', dvkd_fk='" + this.dvkdId + "', kenh_fk = '"+ this.kenhId + "' where pk_seq = '" + this.id + "'";
			if(!db.update(command)){
				this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + command;
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			createSpArray();
			for(int i = 0; i < this.spIds.length; i++){
				String gia = request.getParameter("gia" + this.spIds[i]);
				//System.out.println("Gia Bi Null la :--------"+gia);
				if (gia.length()==0){
					gia = "0";
				}
				
				String trangthai = request.getParameter("chbox" + this.spIds[i]);
				if (trangthai != null){
					trangthai = "1";
				}else{
					trangthai = "0";
				}
				
				if (gia.equals("0"))
					trangthai="0";
				gia=gia.replaceAll(",","");
				 command = "insert into bgmuanpp_sanpham values('" + this.id + "', '" + this.spIds[i] + "', '" + gia + "','" + trangthai + "')";
				if(!this.db.update(command))
				{				
					 command = "update bgmuanpp_sanpham set giamuanpp = '" + gia + "', trangthai = '" + trangthai + "' where bgmuanpp_fk = '" + this.id + "' and sanpham_fk = '" + this.spIds[i] + "'";
					 if(!this.db.update(command))
					 {
						 geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						 this.msg = command;
						 return false;
					 }
					 
					 
					 
				}
				gia=gia.replaceAll("'", "");
				/*command ="insert into log_banggiamua (userid,sanpham_fk,banggiamua,giamoi,trangthai) values" +
						" ('"+this.nguoisua+"','"+this.spIds[i]+"','"+this.id +"','"+gia+"','"+trangthai+"') ";
				
				if(!this.db.update(command))
				 {
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					 this.msg = command;
					 return false;
				 }*/
			
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception  e){
			this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + e.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
			}
		return true;
	}

	private void createDvkdRS(){  				
		//this.dvkdIds  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1'");
		this.dvkdIds  =  this.db.get("select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
	}

	private void createKenhRS(){  				
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
	}

	private void createSpRS(){
		if (this.id.trim().length()>0){
			if (this.dvkdId.length()==0){			
				ResultSet rs = db.get("select dvkd_fk as dvkdId, ten, kenh_fk as kenhId, trangthai from banggiamuanpp where pk_seq = '" + this.id + "'");
				try{
					rs.next();
					this.dvkdId = rs.getString("dvkdId");
					this.kenhId = rs.getString("kenhId");
					this.ten = rs.getString("ten");
					this.trangthai = rs.getString("trangthai");
					rs.close();
				}catch(Exception e){}
				
			}
			
			this.sanphamlist = this.db.get("select a.pk_seq as id, a.ma, a.ten, c.giamuanpp from sanpham a, donvikinhdoanh b, bgmuanpp_sanpham c, banggiamuanpp d where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.bgmuanpp_fk = d.pk_seq and d.pk_seq = '"+ this.id + "' order by ma");

			this.newsplist = this.db.get("select a.pk_seq as id, a.ma, a.ten from sanpham a, donvikinhdoanh b where a.dvkd_fk = b.pk_seq and b.pk_seq = '"+ this.dvkdId + "' and a.pk_seq not in (select a.pk_seq as id from sanpham a, donvikinhdoanh b, bgmuanpp_sanpham c, banggiamuanpp d where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.bgmuanpp_fk = d.pk_seq and d.pk_seq = '"+ this.id + "') order by ma ");
			
		}else{
			if (this.dvkdId.length() > 0){			
				this.sanphamlist = this.db.get("select a.pk_seq as id, a.ma, a.ten from sanpham a, donvikinhdoanh b where a.dvkd_fk = b.pk_seq and b.pk_seq = '" + this.dvkdId + "' order by ma");
			}else{
				ResultSet rs = this.db.get("select pk_seq as dvkdId from donvikinhdoanh order by pk_seq");
				try{
					rs.next();
					this.dvkdId = rs.getString("dvkdId");
					this.ten = "";
					this.trangthai = "1";
					rs.close();
				}catch(Exception e){}
				this.sanphamlist = this.db.get("select a.pk_seq as id, a.ma, a.ten from sanpham a, donvikinhdoanh b where a.dvkd_fk = b.pk_seq and b.pk_seq = '" + this.dvkdId + "' order by ma");
			}
			
		}
	}
	
	private void createSpArray(){
		String query ="";
		Statement st;
		ResultSet rs;
		int count = 0;
		try{
		if (this.id.trim().length()>0){
			if (this.dvkdId.length()==0){			
				rs = this.db.get("select dvkd_fk as dvkdId, ten, kenh_fk as kenhId, trangthai from banggiamuanpp where pk_seq = '" + this.id + "'");
				try{
					rs.next();
					this.dvkdId = rs.getString("dvkdId");
					this.kenhId = rs.getString("kenhId");
					this.ten = rs.getString("ten");
					this.trangthai = rs.getString("trangthai");
					st = rs.getStatement();
					st.close();
					rs.close();
				}catch(Exception e){}
			}
			//Dem  nhung san pham co trong bang gia
			query = "select count(a.pk_seq) as num from sanpham a inner join bgmuanpp_sanpham c on a.pk_seq = c.sanpham_fk "+ 
				 " inner join banggiamuanpp d on  c.bgmuanpp_fk = d.pk_seq "+ 
				 " inner join   donvikinhdoanh b on  b.pk_seq= d.dvkd_fk "+
				 " left join  donvidoluong e on  a.dvdl_fk = e.pk_seq "+
				 " where d.pk_seq = '"+this.id+"'  ";
			//System.out.println("Lay gia mua :" +query);
			
			rs = this.db.get(query);
			rs.next();
			count = Integer.valueOf(rs.getString("num")).intValue();
			st = rs.getStatement();
			st.close();
			rs.close();
			//Dem nhung san pham khong co trong bang gia nhung trang thai=1 sau do cong don san pham lai
			
			//query = "+ this.dvkdId + "' and a.pk_seq not in (select a.pk_seq from sanpham a, donvikinhdoanh b, bgmuanpp_sanpham c, banggiamuanpp d, donvidoluong e where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.bgmuanpp_fk = d.pk_seq and a.dvdl_fk = e.pk_seq and d.pk_seq = '"+ this.id + "')";
			query="select count(a.pk_seq) as num from sanpham a "+ 
			" left join donvidoluong c on  a.dvdl_fk = c.pk_seq "+
			" inner join donvikinhdoanh b on  a.dvkd_fk = b.pk_seq "+  
			" where b.pk_seq = '"+this.dvkdId+"' and   a.trangthai ='1' and "+
			" a.pk_seq not in (select a.pk_seq from sanpham a, donvikinhdoanh b, bgmuanpp_sanpham c, banggiamuanpp d"+
			"  where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.bgmuanpp_fk = d.pk_seq "+ 
			"  and d.pk_seq = '"+this.id+"') ";	
				//System.out.println("Dem Gi Day : " +query);
			rs = this.db.get(query);
			rs.next();
			count = count + Integer.valueOf(rs.getString("num")).intValue();
			st = rs.getStatement();
			st.close();			
			rs.close();
			this.spIds = new String[count];
			this.masp = new String[count];
			this.tensp = new String[count];
			this.giamuanpp = new String[count];
			this.dv = new String[count];
			this.tthai = new String[count];
			
			//query = "select a.pk_seq as id, a.ma, a.ten, c.giamuanpp, c.trangthai, e.donvi from sanpham a, donvikinhdoanh b, bgmuanpp_sanpham c, banggiamuanpp d, donvidoluong e where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.bgmuanpp_fk = d.pk_seq and a.dvdl_fk = e.pk_seq and d.pk_seq = '"+ this.id + "' order by a.ma";
			//lay bang gia san pham , roi dua vao mang vua khoi tao bang bien dem o phia tren
			query = "select a.pk_seq as id, a.ma, a.ten, c.giamuanpp, c.trangthai, isnull(e.donvi,'Chua xac dinh') as donvi  from sanpham a "+
				" inner join   bgmuanpp_sanpham c on a.pk_seq = c.sanpham_fk "+
				" inner join  banggiamuanpp d on  c.bgmuanpp_fk = d.pk_seq "+ 
				" inner join donvikinhdoanh b on b.pk_seq= d.dvkd_fk "+
				" left join donvidoluong e on e.pk_seq=a.dvdl_fk "+
				" where d.pk_seq = '"+this.id+"' order by a.ma ";
			
			//System.out.println("Bang San Pham :"+query);
			count = 0;
			rs = this.db.get(query);
			maspstr = "";
			while(rs.next()){
				this.spIds[count] = rs.getString("id");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.spIds[count] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + this.spIds[count] + "'";
				}
				
				this.masp[count] = rs.getString("ma");
				this.tensp[count] = rs.getString("ten");
				this.giamuanpp[count] = rs.getString("giamuanpp");
				this.tthai[count]= rs.getString("trangthai");
				this.dv[count] = rs.getString("donvi");
				count++;
			}
			st = rs.getStatement();
		    st.close();
			rs.close();
			
			//query = "select a.pk_seq as id, a.ma, a.ten, c.donvi from sanpham a, donvikinhdoanh b, donvidoluong c where a.dvkd_fk = b.pk_seq and a.dvdl_fk = c.pk_seq and b.pk_seq = '"+ this.dvkdId + "' and a.pk_seq not in (select a.pk_seq from sanpham a, donvikinhdoanh b, bgmuanpp_sanpham c, banggiamuanpp d, donvidoluong e where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.bgmuanpp_fk = d.pk_seq and a.dvdl_fk = e.pk_seq and d.pk_seq = '"+ this.id + "') order by a.ma ";
			//cau lenh nay sua lai,trong phan not in khong ket bang voi bang donvidoluong,vi ket bang se khong lay het sp trong bang gia-->dan toi cau lenh nay van lay them duoc san pham ma da co trong bang gia khi  chua co DVDL
			
			query ="select a.pk_seq as id, a.ma, a.ten,isnull(c.donvi,'Chua xac dinh') as donvi  from sanpham a "+ 
			" left join donvidoluong c on  a.dvdl_fk = c.pk_seq "+
			" inner join donvikinhdoanh b on  a.dvkd_fk = b.pk_seq "+  
			" where b.pk_seq = '"+this.dvkdId+"' and   a.trangthai='1' and "+
			" a.pk_seq not in (select a.pk_seq from sanpham a, donvikinhdoanh b, bgmuanpp_sanpham c, banggiamuanpp d "+
			" where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.bgmuanpp_fk = d.pk_seq " +
			"  and d.pk_seq = '"+this.id+"')";
			
			//System.out.println("Bang San Pham  khong co trong bang gia :"+query);
			
			rs = this.db.get(query);
			
			while(rs.next()){
				this.spIds[count] = rs.getString("id");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.spIds[count] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + this.spIds[count] + "'";
				}
				
				this.masp[count] = rs.getString("ma");
				this.tensp[count] = rs.getString("ten");
				this.giamuanpp[count] = "0";
				this.tthai[count] = "0";
				this.dv[count] =  rs.getString("donvi");
				count++;
			}
			//System.out.println(this.maspstr);
			st = rs.getStatement();
			st.close();		
			rs.close();
			
		}else{
			
			if (this.dvkdId.length() == 0){			
				rs = this.db.get("select pk_seq as dvkdId from donvikinhdoanh order by pk_seq");
				rs.next();
				this.dvkdId = rs.getString("dvkdId");
				this.ten = "";
				this.trangthai = "1";

				st = rs.getStatement();
				st.close();		
				rs.close();

			}
			query = "select count(a.pk_seq) as num from sanpham a inner join "+ 
			" donvikinhdoanh b on a.dvkd_fk = b.pk_seq  "+
 			" left join  donvidoluong c on  a.dvdl_fk = c.pk_seq  where b.pk_seq = '"+this.dvkdId+"'  and a.trangthai='1'";
			rs = this.db.get(query);
			rs.next();
			
			count = Integer.valueOf(rs.getString("num")).intValue();
			this.spIds = new String[count];
			this.masp = new String[count];
			this.tensp = new String[count];
			this.giamuanpp = new String[count];
			this.tthai = new String[count];	
			this.dv = new String[count];

			st = rs.getStatement();
			st.close();		
			rs.close();
			
			//query = "select a.pk_seq as id, a.ma, a.ten, c.donvi from sanpham a, donvikinhdoanh b, donvidoluong c where a.dvkd_fk = b.pk_seq and a.dvdl_fk = c.pk_seq and b.pk_seq = '" + this.dvkdId + "' order by a.ma";
			query = "select a.pk_seq as id, a.ma, a.ten, isnull(c.donvi,'Chua xac dinh') as donvi from sanpham a inner join "+ 
			" donvikinhdoanh b on a.dvkd_fk = b.pk_seq  "+
 			" left join  donvidoluong c on  a.dvdl_fk = c.pk_seq  where b.pk_seq = '"+this.dvkdId+"' and a.trangthai='1' ";

			rs = this.db.get(query);
			count = 0;
			maspstr = "";
			while(rs.next()){
				this.spIds[count] = rs.getString("id");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.spIds[count] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + this.spIds[count] + "'";
				}
					
				this.masp[count] = rs.getString("ma");
				this.tensp[count] = rs.getString("ten");
				this.giamuanpp[count] = "0";
				this.tthai[count] = "0";
				this.dv[count]  = rs.getString("donvi");
				count++;
			}
			
			st = rs.getStatement();
			st.close();		
			rs.close();

		}
		}catch(Exception e){}
	}

	public void createRS(){
		createDvkdRS();
		createKenhRS();
		createSpArray();
     	//createSpRS();
	}
	
	public void init(){
		String query = "select ten, dvkd_fk as dvkdId, kenh_fk as kenhId, trangthai from banggiamuanpp where pk_seq ='" + this.id + "'";
		ResultSet rs = this.db.get(query);
		try{		
			rs.next();
			this.ten = rs.getString("ten");
			this.dvkdId = rs.getString("dvkdId");
			this.kenhId = rs.getString("kenhId");
			this.trangthai = rs.getString("trangthai");
			
			Statement st = rs.getStatement();
			st.close();		
			rs.close();

		}catch(Exception e){}
		createRS();
	}

	
	public void closeDB(){
		try{
			Statement st;
			if(this.sanphamlist != null){
				st = this.sanphamlist.getStatement();
				st.close();		
				this.sanphamlist.close();
			}

			if(this.newsplist != null){
				st = this.newsplist.getStatement();
				st.close();		
				this.newsplist.close();
			}
			
			if(this.dvkdIds != null){
				st = this.dvkdIds.getStatement();
				st.close();		
				this.dvkdIds.close();
			}
			
			if(this.kenhIds != null){
				st = this.kenhIds.getStatement();
				st.close();		
				this.kenhIds.close();
			}
			
			this.db.shutDown();
		}catch(Exception e){}

	}
}


