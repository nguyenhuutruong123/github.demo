package geso.dms.center.beans.sitecode_conv.imp;

import java.sql.ResultSet;

import geso.dms.center.beans.sitecode_conv.Isitecode_conv;
import geso.dms.center.db.sql.dbutils;

public class sitcode_conv implements Isitecode_conv{

	private String Sitecode;
	private String Convsitecode;
	private String Ten;
	private String Ngaytao;
	private String Ngaysua;
	private String Trangthai;
	private String Nguoitao;
	private String Nguoisua;
	private String UserId;
	private String Smg;
	private ResultSet rs_sitecode_conv;
	private dbutils db;
	private ResultSet rs_npptn;//Nha phan phoi tien nhiem
	private ResultSet rs_khuvuc;
	private String IdNpptiennhiem="";
	private String tennpptn="";
	private String KhuvucId="";
	private String NgayKs="";
	public sitcode_conv(){
		this.Trangthai="";
		this.Ten="";
		this.Sitecode="";
		this.Convsitecode="";
		this.Smg="";
		this.KhuvucId="";
		db=new dbutils();
		
	}
	public sitcode_conv(String sitecode){
		db=new dbutils();
		String sql="select conv.sitecode,conv.ten,conv.convsitecode,npp.ten as npptiennhiem,npp.pk_seq as idnpptn, "+ 
		" conv.trangthai,conv.ngaytao,conv.ngaysua,nt.ten as nguoitao,ns.ten as nguoisua,npp.ten as tenupdate from sitecode_conv conv " +
		" left join nhaphanphoi npp on npp.sitecode=conv.convsitecode"+ 
		" inner join nhanvien nt on nt.pk_seq= conv.nguoitao inner join nhanvien ns on conv.nguoisua=ns.pk_seq where conv.sitecode='"+sitecode+"'";
		
		System.out.println(sql);
		
		
		ResultSet rs=db.get(sql);
		if(rs!= null){
			try{
				 if(rs.next()){
					 this.Sitecode=rs.getString("sitecode");
					 this.Convsitecode=rs.getString("convsitecode");
					 this.Ten=rs.getString("ten");
					 this.IdNpptiennhiem=rs.getString("idnpptn");
					 if(this.IdNpptiennhiem==null){
						 this.IdNpptiennhiem="";
					 }
					 this.Smg="";
					 if(rs.getString("trangthai").equals("0") && rs.getString("convsitecode")!=""){
						 this.tennpptn=rs.getString("tenupdate");
					 }else{
						 this.tennpptn=rs.getString("npptiennhiem");
					 }
				 }
				
			}catch(Exception er){
				
			}
		}
	}
	
	@Override
	public void setsitecode(String sitecode) {
		// TODO Auto-generated method stub
		this.Sitecode=sitecode;
	}

	@Override
	public String getsitecode() {
		// TODO Auto-generated method stub
		return this.Sitecode;
	}

	@Override
	public void setconvsitecode(String convsitecode) {
		// TODO Auto-generated method stub
		this.Convsitecode=convsitecode;
	}

	@Override
	public String getconvsitecode() {
		// TODO Auto-generated method stub
		return this.Convsitecode;
	}

	@Override
	public void setngaytao(String ngaytao) {
		// TODO Auto-generated method stub
		this.Ngaytao=ngaytao;
	}

	@Override
	public String getngaytao() {
		// TODO Auto-generated method stub
		return this.Ngaytao;
	}

	@Override
	public void setngaysua(String ngaysua) {
		// TODO Auto-generated method stub
		this.Ngaysua=ngaysua;
	}

	@Override
	public String getngaysua() {
		// TODO Auto-generated method stub
		return this.Ngaysua;
	}

	@Override
	public void setten(String ten) {
		// TODO Auto-generated method stub
		this.Ten=ten;
	}

	@Override
	public String getten() {
		// TODO Auto-generated method stub
		return this.Ten;
	}

	@Override
	public void settrangthai(String trangthai) {
		// TODO Auto-generated method stub
		this.Trangthai=trangthai;
	}

	@Override
	public String gettrangthai() {
		// TODO Auto-generated method stub
		return this.Trangthai;
	}

	@Override
	public void setnguoitao(String nguoitao) {
		// TODO Auto-generated method stub
		this.Nguoitao=nguoitao;
	}

	@Override
	public String getnguoitao() {
		// TODO Auto-generated method stub
		return this.Nguoitao;
	}

	@Override
	public void setnguoisua(String nguoisua) {
		// TODO Auto-generated method stub
		this.Nguoisua=nguoisua;
	}

	@Override
	public String getnguoisua() {
		// TODO Auto-generated method stub
		return this.Nguoisua;
	}

	@Override
	public void Init(String sql) {
		// TODO Auto-generated method stub
		//neu  nha phan phoi chua c� co nghia la null thi se lay ten cua nha phan phoi tien nhiem va trong trang thai la chua chot
		String sql_getsitecode_conv="select conv.sitecode,conv.ten,isnull (conv.convsitecode,'') as convsitecode,conv.tennpptn as npptiennhiem,npp.pk_seq as idnpptn, "+ 
		" conv.trangthai,conv.ngaytao,conv.ngaysua,nt.ten as nguoitao,ns.ten as nguoisua,npp.ten as tenupdate from sitecode_conv conv " +
		" left join nhaphanphoi npp on npp.sitecode=conv.convsitecode"+ 
		" inner join nhanvien nt on nt.pk_seq= conv.nguoitao inner join nhanvien ns on conv.nguoisua=ns.pk_seq  order by conv.trangthai";

	//System.out.println(sql_getsitecode_conv);
		if(sql!=""){
			sql_getsitecode_conv=sql;
		}

		rs_sitecode_conv=db.get(sql_getsitecode_conv);
		
	}

	@Override
	public ResultSet getsistecode_conv() {
		// TODO Auto-generated method stub
		return rs_sitecode_conv;
	}

	@Override
	public void setUserid(String userid) {
		// TODO Auto-generated method stub
		this.UserId=userid;
	}

	@Override
	public String getUserid() {
		// TODO Auto-generated method stub
		return this.UserId;
	}

	@Override
	public void setMsg(String smg) {
		// TODO Auto-generated method stub
		this.Smg=smg;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.Smg;
	}

	@Override
	public void setRsNppTienNhiem() {
		// TODO Auto-generated method stub
		String sql="select pk_seq,ten,sitecode from nhaphanphoi where sitecode=convsitecode and trangthai=1";//Nhung nha phan phoi  co du lieu
		if(!this.KhuvucId.equals("")){
			sql=sql+" and khuvuc_fk='"+this.KhuvucId+"'";
		}
		//System.out.println(sql);

		rs_npptn=db.get(sql);
		
	}

	@Override
	public ResultSet getRsNppTienNhiem() {
		// TODO Auto-generated method stub
		return rs_npptn;
	}

	@Override
	public void setIdNppTienNhiem(String idnpptiennhiem) {
		// TODO Auto-generated method stub
	this.IdNpptiennhiem=idnpptiennhiem;
	}

	@Override
	public String getIdNppTienNhiem() {
		// TODO Auto-generated method stub
		return this.IdNpptiennhiem;
	}
	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		
		String sql="update sitecode_conv set convsitecode= (select sitecode from nhaphanphoi where pk_seq='"+this.IdNpptiennhiem+"') where sitecode='"+this.Sitecode+"' ";
	
		if(!db.update(sql)){
			this.setMsg("Loi dong lenh :" + sql);
		return false;
		}
	return true;
	}
	@Override
	public boolean chot() {
		// TODO Auto-generated method stub
		//cap nhat trang thai va cap nhat convsitecode
		
		
		try{
			db.getConnection().setAutoCommit(false);
			//lay sitecode cua npp tien nhiem
			String sql="select sitecode from nhaphanphoi where pk_seq='"+this.IdNpptiennhiem+"'";
			ResultSet rs=db.get(sql);
			if(rs.next()){
				this.Convsitecode=rs.getString("sitecode");
			}
			//Cap nhat lai convsitecode la sitecode cua npp tien nhiem,va cap nhat ten npp tien nhiem
			 sql="update sitecode_conv set trangthai=1,convsitecode='"+this.Convsitecode+"',tennpptn='"+this.tennpptn+"' where sitecode='"+this.Sitecode+"' ";
			if(!db.update(sql)){
				this.setMsg("Loi dong lenh :" + sql);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			//cap nhat ten npp tien nhiem thanh ten npp moi
			sql="update nhaphanphoi set ten='"+this.Ten+"',trangthai='1',ma='"+this.Sitecode+"',pass='"+this.Sitecode+"' where pk_Seq='"+this.IdNpptiennhiem+"'";
			if(!db.update(sql)){
				this.setMsg("Loi dong lenh :" + sql);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			//Them user dang nhap 
			 sql = "insert into nhanvien(ten, ngaysinh, dangnhap, matkhau, email, dienthoai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, phanloai, islogin, sessionid,convsitecode)  " +
			 		"values('" + this.Ten + "','','" + this.Sitecode + "',pwdencrypt('" + this.Sitecode + "'),'','','1','" + this.Ngaysua + "','" + this.Ngaysua + "','" + this.UserId + "','" + this.UserId + "','1','0','No','"+this.Convsitecode+"')";
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMsg("Loi dong lenh :" + sql);
				return false; 
			}
			
			//cap nhat lai trang thai cua cac nha phan phoi ke nhiem truoc,muc dich chi de 1 nha phan phoi ke nhiem
			sql="update sitecode_conv set trangthai='2' where convsitecode=(select sitecode from nhaphanphoi where pk_Seq='"+this.IdNpptiennhiem+"') and sitecode<>'"+this.Sitecode+"'";
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMsg("Loi dong lenh :" + sql);
				return false; 
			}
			//huy cac dang nhap cua cac nha phan phoi ke nhiem truoc,de mot dang nhap cua npp ke nhiem moi
			sql="update nhanvien set trangthai='0' where dangnhap in (select sitecode from sitecode_conv where convsitecode='"+this.Convsitecode+"' and sitecode!='"+this.Sitecode+"') or dangnhap in (select sitecode from nhaphanphoi where pk_seq='"+this.IdNpptiennhiem+"')";
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMsg("Loi dong lenh :" + sql);
				return false; 
			}
			db.update("commit");
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
		
	}
	@Override
	public void settennpptn(String tennpptn) {
		// TODO Auto-generated method stub
		this.tennpptn=tennpptn;
	}
	@Override
	public String gettennpptn() {
		// TODO Auto-generated method stub
		return this.tennpptn;
	}
	@Override
	public ResultSet getRsloctheokhuvuc() {
		// TODO Auto-generated method stub
		return this.rs_khuvuc;
	}
	@Override
	public void setRsKhuvuc() {
		// TODO Auto-generated method stub
		String sql="select pk_seq,ten from khuvuc ";//Nhung nha phan phoi  co du lieu
		db=new dbutils();
		rs_khuvuc=db.get(sql);
	}
	@Override
	public String getKhuVucId() {
		// TODO Auto-generated method stub
		return this.KhuvucId;
	}
	@Override
	public void setkhuvucId(String khuvucid) {
		// TODO Auto-generated method stub
		this.KhuvucId=khuvucid;
	}
	@Override
	public boolean TaoNPPMoi() {
		// TODO Auto-generated method stub
		try{
		this.db.getConnection().setAutoCommit(false);
		if(this.KhuvucId ==null ||this.KhuvucId.equals("")){
			this.setMsg("Vui Long Chon Khu Vuc Cho Nha Phan Phoi Moi");
			
			return false;
		}
		if(this.getsitecode().equals("")){
			this.setMsg("Vui Long Thu Lai,SiteCode Nha Phan Phoi Khong Duoc Rong.");
			return false;
		}
		//Cho kho mac dinh la DWF1,khi vao sa moi hoat dong.
		String sql="insert into nhaphanphoi (ten,ngaytao,ngaysua,nguoitao,nguoisua,trangthai,sitecode,convsitecode,khuvuc_fk,KHOSAP,ma,pass)"+
		"values('"+this.getten()+"','"+this.Ngaytao+"','"+this.Ngaytao+"','"+this.UserId+"','"+this.UserId+"','0','"+this.getsitecode()+"','"+this.getsitecode()+"','"+this.KhuvucId+"','DWF1','"+this.getsitecode()+"','"+this.getsitecode()+"')";
		
		if(!db.update(sql)){
			this.setMsg("Khong The Chuyen Sang Nha Phan Phoi Moi,Vui Long Thu Lai,Neu Khong Duoc,Vui Long Lien He Voi Admin De Duoc Giup Do.Loi :"+sql);
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		//Them user dang nhap 
		 sql = "insert into nhanvien(ten, ngaysinh, dangnhap, matkhau, email, dienthoai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, phanloai, islogin, sessionid,convsitecode) " +
		 		" values('" + this.Ten + "','','" + this.Sitecode + "',pwdencrypt('" + this.Sitecode + "'),'','','1','" + this.Ngaytao + "','" + this.Ngaytao + "','" + this.UserId + "','" + this.UserId + "','1','0','No','"+this.Sitecode+"')";
		if(!db.update(sql)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMsg("Loi dong lenh :" + sql);
			return false; 
		}
		sql="delete sitecode_conv where sitecode='"+this.Sitecode+"'";
		if(!db.update(sql)){
			this.setMsg("Khong The Chuyen Sang Nha Phan Phoi Moi,Vui Long Thu Lai,Neu Khong Duoc,Vui Long Lien He Voi Admin De Duoc Giup Do.Loi :"+sql);
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
			
		}
		/*
		 * thuc hien cap nhat lai phan quyen
		 * 
		 */
		sql="select pk_seq from nhaphanphoi where sitecode='"+this.getsitecode()+"'";
		ResultSet rs=db.get(sql);
		String id="";
		if(rs.next()){
			id=rs.getString("pk_seq");
		}
			rs.close();
		 sql="select distinct nhanvien_fk,npp.khuvuc_fk from phamvihoatdong inner join nhaphanphoi npp on npp_fk=npp.pk_seq where khuvuc_fk="+this.KhuvucId ;
		 rs=db.get(sql);
		 while (rs.next()){
		   sql="insert into phamvihoatdong(nhanvien_fk,npp_fk) values("+rs.getString("nhanvien_fk")+",'"+id+"') ";
		  
		   if(!db.update(sql)){
			   geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			   this.setMsg("Khong the cap nhat loi :" + sql);
			   return false;
		   }
		 }
		 if(rs!=null){
			 rs.close();
		 }
		
		//them ngay khoa so moi
		 
		 sql=" insert into khoasongay(ngayksgannhat,ngayks,ngaytao,nguoitao,npp_fk,chon,doanhso)" +
		 		" values ('"+this.getNgaykhoaso()+"','"+this.NgayKs+"','"+this.Ngaytao+"','"+this.UserId+"','"+id+"','1','1')" ;
		 System.out.println("Get Sql Update khoa so ngay : "+sql);
		  if(!db.update(sql)){
			   geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			   this.setMsg("Khong the cap nhat loi :" + sql);
			   return false;
		   }
		
		
		this.db.update("commit");
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.setMsg("Khong the cap nhat loi :" + er.toString());
			   return false;
		}
	
		return true;
	}
	@Override
	public void NgayKhoaSo(String ngayks) {
		// TODO Auto-generated method stub
		this.NgayKs=ngayks;
	}
	@Override
	public String getNgaykhoaso() {
		// TODO Auto-generated method stub
		return this.NgayKs;
	}
	@Override
	public void DBClose() {
		try{
		// TODO Auto-generated method stub
		if(rs_sitecode_conv!=null){
			rs_sitecode_conv.close();
		}
		if(rs_npptn!=null){
			rs_npptn.close();
		}
		if(rs_khuvuc!=null){
			rs_khuvuc.close();
		}

		if(db!=null){
			db.shutDown();
		}
		}catch(Exception er){
			
		}
	}
}
