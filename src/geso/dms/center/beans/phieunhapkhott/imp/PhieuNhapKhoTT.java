package geso.dms.center.beans.phieunhapkhott.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc;
import geso.dms.center.beans.phieunhapkhott.IPhieuNhapKhoTT;
import geso.dms.center.util.SendMail;
import geso.dms.distributor.db.sql.dbutils;

public class PhieuNhapKhoTT implements IPhieuNhapKhoTT {
 
	String Id;
	String NgayNhap;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	String KhoTT_Id;
	String TenKhoTT;
	String TrangThai;
	String Message;
	String[] recipient;
	List<PhieuNhapKhoTT_SanPham> listsanpham=new ArrayList<PhieuNhapKhoTT_SanPham>();
	List<PhieuNhapKhoTT> listPhieuNhapkhott=new ArrayList<PhieuNhapKhoTT>();
	Hashtable sp;
	public PhieuNhapKhoTT(){
		this.NgayNhap="";
		this.NgayTao="";
		this.NguoiTao="";
		this.NgaySua="";
		this.NguoiSua="";
		this.KhoTT_Id="";
		this.TenKhoTT="";
		this.TrangThai="";
		this.Id="";
		this.Message="";
	}
	public PhieuNhapKhoTT(String id){
		dbutils db=new dbutils();
	String 	sql_getdata="select a.pk_seq,a.ngaynhap,a.trangthai,a.ngaytao,a.ngaysua,NT.Ten as nguoitao,NS.ten as nguoisua,khott_fk,b.ten from phieunhapkhott a inner join khott "+
		" b on a.khott_fk=b.pk_seq  inner join nhanvien as NT on a.nguoitao=NT.pk_seq inner join nhanvien as NS on NS.Pk_seq=a.nguoisua  where a.pk_seq= "+id;
	ResultSet rs_getdata=db.get(sql_getdata);
	
	if(rs_getdata!=null){
		try{
			while(rs_getdata.next()){
				
				this.setId(rs_getdata.getString("pk_seq"));
				this.setKhoId(rs_getdata.getString("khott_fk"));
				this.setNgayNhap(rs_getdata.getString("ngaynhap"));
				this.setNgaytao(rs_getdata.getString("ngaytao"));
				this.setNguoitao(rs_getdata.getString("nguoitao"));
				this.setNgaysua(rs_getdata.getString("ngaysua"));
				this.setNguoisua(rs_getdata.getString("nguoisua"));
				this.setTenKho(rs_getdata.getString("ten"));
				this.setTrangthai(rs_getdata.getString("trangthai"));
				this.setMessage("");
			}
			
		}catch(Exception err){
			System.out.println("Error 147 PhieuNhapKhoTT  :"+err.toString());
		}
	}
	String sql_getchitietsp="select phieunhapkhott_fk,sanpham_fk,soluong,b.ten,b.ma from phieunhapkhott_sanpham a inner join sanpham b on a.sanpham_fk=b.pk_seq  where phieunhapkhott_fk=" +id;
	ResultSet rs_getsanpham=db.get(sql_getchitietsp);
	listsanpham.clear();
	if(rs_getsanpham!=null){
		try{
			while(rs_getsanpham.next()){
				PhieuNhapKhoTT_SanPham pnksp=new PhieuNhapKhoTT_SanPham();
				pnksp.setId(rs_getsanpham.getString("phieunhapkhott_fk"));
				pnksp.setSanPhamId(rs_getsanpham.getString("ma"));
				pnksp.setTenSanPham(rs_getsanpham.getString("ten"));
				pnksp.setSoLuong(rs_getsanpham.getInt("soluong"));
				listsanpham.add(pnksp);
			}
		}catch(Exception er){
			System.out.println("Error 75 PhieuNhapKhoTT  :"+er.toString());
		}
		
	}
	}
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
	}

	@Override
	public String getNgayNhap() {
		// TODO Auto-generated method stub
		return this.NgayNhap;
	}

	@Override
	public void setNgayNhap(String ngaynhap) {
		// TODO Auto-generated method stub
		this.NgayNhap=ngaynhap;
	}

	@Override
	public String getTenKho() {
		// TODO Auto-generated method stub
		return this.TenKhoTT;
	}

	@Override
	public void setTenKho(String TenKho) {
		// TODO Auto-generated method stub
		this.TenKhoTT=TenKho;
	}

	@Override
	public String getKhoID() {
		// TODO Auto-generated method stub
		return this.KhoTT_Id;
	}

	@Override
	public void setKhoId(String KhoId) {
		// TODO Auto-generated method stub
		this.KhoTT_Id=KhoId;
	}

	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.TrangThai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		// TODO Auto-generated method stub
		this.TrangThai=trangthai;
	}

	@Override
	public String getNgaytao() {
		// TODO Auto-generated method stub
		return this.NgayTao;
	}

	@Override
	public void setNgaytao(String ngaytao) {
		// TODO Auto-generated method stub
		this.NgayTao=ngaytao;
	}

	@Override
	public String getNguoitao() {
		// TODO Auto-generated method stub
		return this.NguoiTao;
	}

	@Override
	public void setNguoitao(String nguoitao) {
		// TODO Auto-generated method stub
		this.NguoiTao=nguoitao;
	}

	@Override
	public String getNgaysua() {
		// TODO Auto-generated method stub
		return this.NgaySua;
	}

	@Override
	public void setNgaysua(String ngaysua) {
		// TODO Auto-generated method stub
		this.NgaySua=ngaysua;
	}

	@Override
	public String getNguoisua() {
		// TODO Auto-generated method stub
		return this.NguoiSua;
	}

	@Override
	public void setNguoisua(String nguoisua) {
		// TODO Auto-generated method stub
		this.NguoiSua=nguoisua;
	}

	@Override
	public void setListNhapKho(String sql_get) {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		String sql_getdata="";
		if(sql_get.equals("")){
			sql_getdata="select a.pk_seq,a.ngaynhap,a.trangthai,a.ngaytao,a.ngaysua,NT.Ten as nguoitao,NS.ten as nguoisua,khott_fk,b.ten from phieunhapkhott a inner join khott "+
			" b on a.khott_fk=b.pk_seq  inner join nhanvien as NT on a.nguoitao=NT.pk_seq inner join nhanvien as NS on NS.Pk_seq=a.nguoisua  ";
		}
		else{
			sql_getdata=sql_get;
		}
		ResultSet rs_getdata=db.get(sql_getdata);
		if(rs_getdata!=null){
			try{
				while(rs_getdata.next()){
					PhieuNhapKhoTT pnktt=new PhieuNhapKhoTT();
					pnktt.setId(rs_getdata.getString("pk_seq"));
					pnktt.setKhoId(rs_getdata.getString("khott_fk"));
					pnktt.setNgayNhap(rs_getdata.getString("ngaynhap"));
					pnktt.setNgaytao(rs_getdata.getString("ngaytao"));
					pnktt.setNguoitao(rs_getdata.getString("nguoitao"));
					pnktt.setNgaysua(rs_getdata.getString("ngaysua"));
					pnktt.setNguoisua(rs_getdata.getString("nguoisua"));
					pnktt.setTenKho(rs_getdata.getString("ten"));
					pnktt.setTrangthai(rs_getdata.getString("trangthai"));
					
				listPhieuNhapkhott.add(pnktt);	
				}
			}catch(Exception err){
				System.out.println("Error 147 PhieuNhapKhoTT  :"+err.toString());
			}
		}
		
	}

	@Override
	public List<PhieuNhapKhoTT> getListNhapKho() {
		// TODO Auto-generated method stub
		return this.listPhieuNhapkhott;
	}
	@Override
	public void setListSanPham(List<PhieuNhapKhoTT_SanPham> list) {
		// TODO Auto-generated method stub
		this.listsanpham=list;
	}
	@Override
	public List<PhieuNhapKhoTT_SanPham> getListSanPham() {
		// TODO Auto-generated method stub
		return this.listsanpham;
	}
	@Override
	public void setMessage(String message) {
		// TODO Auto-generated method stub
		this.Message=message;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.Message;
	}
	@Override
	public boolean SavePhieuNhapKhoTT() {
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		String sqlSavePhieuNhapKho="INSERT INTO [Best].[dbo].[PHIEUNHAPKHOTT]([NGAYNHAP],[TRANGTHAI],[NGAYTAO] ,[NGAYSUA] ,[NGUOITAO] ,[NGUOISUA],[KHOTT_FK])  VALUES "+
		"('"+this.NgayNhap+"','0','"+this.NgayTao+"','"+this.NgaySua+"','"+this.NguoiTao+"','"+this.NguoiSua+"','"+this.KhoTT_Id+"')";
			if(!db.update(sqlSavePhieuNhapKho)){
			this.Message="Khong the them moi phieu nhap kho nay";
			System.out.println("sqlSavePhieuNhapKho : 146 PhieuNhapKhoTT " +sqlSavePhieuNhapKho);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		//thuc hien insert chi tiet
		String query = "select IDENT_CURRENT('PHIEUNHAPKHOTT') as pnkId";
		ResultSet rsDh = db.get(query);	
		try
		{
			rsDh.next();
		this.setId(rsDh.getString("pnkId"));
		}catch(Exception ex){
			return false;
		}
		int count=0;
		if(this.listsanpham!=null){
			while (count <this.listsanpham.size()){
				PhieuNhapKhoTT_SanPham sanpham=new PhieuNhapKhoTT_SanPham();
				sanpham=this.listsanpham.get(count);
				//Kiem tra san pham nay co ton tai trong bang sanpham khong 
				String pk_seq=checksanphamexit(sanpham.getSanPhamId());
				
				if(pk_seq.equals("")){//Truong hop khong lay duoc ma
					this.Message=" Khong the them moi don hang nay,san pham "+sanpham.getSanPhamId()+" khong ton tai,vui long kiem tra lai ma san pham nay ";
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
				}else{
					double giavon=getdongia(pk_seq);
					if(giavon==0){
						this.Message="San pham "+sanpham.getSanPhamId()+" chua co gia von ,vui long kiem tra lai  san pham nay ";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					if(sanpham.SoLuong==0){
						this.Message="San pham "+sanpham.getSanPhamId()+" chua nhap so luong ,vui long kiem tra lai so luong san pham nay ";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					String sql_insertdetail="insert into PhieuNhapKhoTT_SanPham([PHIEUNHAPKHOTT_FK],[SANPHAM_FK],[SOLUONG],[DONGIA]) values("+this.getId()+","+pk_seq+","+sanpham.getSoLuong()+","+giavon+")";
										
					if(!db.update(sql_insertdetail)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						System.out.println("sql_insertdetail : 146 PhieuNhapKhoTT " +sql_insertdetail);
						return false;
					}
					//thuc hien tang so luong ton trong kho khi thuc hien them phieu nhap kho tt
					//String sql_updatekho="update khott_sp set soluong=soluong+"+sanpham.getSoLuong()+",available= available+"+ sanpham.SoLuong +" where sanpham_fk="+pk_seq+" and khott_fk="+ this.KhoTT_Id ;
					//if(!db.update(sql_updatekho)){
					//	geso.dms.center.util.Utility.rollback_throw_exception(db);
					//	System.out.println("sql_updatekho : 146 PhieuNhapKhoTT " +sql_updatekho);
					//	return false;
					//}
				}
				count++;
			}
		}
		if(count==0){
			this.Message=" Khong the them moi don hang nay, chua co san pham nao duoc chon";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
			
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them Phieu Nhap Kho nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	/**
	 * Phuong thuc nay dung de lay gia von cua mot mat hang san pham, lay tu bang banggiavontt_sanpham,
	 *  dieu kien loc theo id hang va luachon=1, trong bang nay sanpham_fk se la duy nhat,moi san pham 
	 *  chi co 1 gia von
	 * @param pk_seq
	 * @return
	 */
	private double getdongia(String pk_seq) {
		// TODO Auto-generated method stub
		String sqlcheckexitsp="select sanpham_fk,giavon,a.dvkd_fk from banggiavontt_sanpham  b inner join banggiavontt a on a.pk_seq=b.banggiavontt_fk  where chonban='1' and sanpham_fk=" +pk_seq;
		//System.out.println(sqlcheckexitsp);
		dbutils db=new dbutils();
		ResultSet rs_db= db.get(sqlcheckexitsp);
		if(rs_db!=null){
			try{
			if(rs_db.next()){
				return Double.parseDouble( rs_db.getString("giavon"));
			}
			}catch(Exception er){
				return 0;
			}
		}
		return 0;
	}
	/**
	 * phuong thuc nay dung de lay pk_seq cua san pham khi biet ma san pham
	 * @param sanPhamId
	 * @return
	 */
	private String checksanphamexit(String sanPhamId) {
		// TODO Auto-generated method stub
		String sqlcheckexitsp="select pk_seq from sanpham where ma='"+sanPhamId+"' and trangthai!='2'";
		dbutils db=new dbutils();
		ResultSet rs_db= db.get(sqlcheckexitsp);
		if(rs_db!=null){
			try{
			if(rs_db.next()){
				return rs_db.getString("pk_seq");
			}
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	@Override
	public boolean EditPhieuNhapKhoTT() {
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		String sqleditphieunhapkho="update [Best].[dbo].[PHIEUNHAPKHOTT] set [NGAYSUA]='"+this.NgaySua+"',[NGUOISUA]='"+this.NguoiSua+"',[KHOTT_FK]="+this.KhoTT_Id+", " +
									" ngaynhap='"+this.NgayNhap+"' where pk_seq= "+this.Id;
			if(!db.update(sqleditphieunhapkho)){
			System.out.println("sqleditphieunhapkho : 146 PhieuNhapKhoTT " +sqleditphieunhapkho);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		     }
		//Thuc hien xoa het dah sach cu
			String sqldeletedetail="delete from PhieuNhapKhoTT_SanPham where PHIEUNHAPKHOTT_FK= "+this.Id;
			if(!db.update(sqldeletedetail)){
				System.out.println("sqldeletedetail : 146 PhieuNhapKhoTT " +sqldeletedetail);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			  }
			
		int count=0;
		if(this.listsanpham!=null){
			while (count <this.listsanpham.size()){
				PhieuNhapKhoTT_SanPham sanpham=new PhieuNhapKhoTT_SanPham();
				sanpham=this.listsanpham.get(count);
				//Kiem tra san pham nay co ton tai trong bang sanpham khong 
				String pk_seq=checksanphamexit(sanpham.getSanPhamId());
				if(pk_seq.equals("")){//Truong hop khong lay duoc ma
					this.Message=" Khong the them moi don hang nay,san pham "+sanpham.getSanPhamId()+" khong ton tai,vui long kiem tra lai ma san pham nay ";
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
				}else{
					double giavon=getdongia(pk_seq);
					if(giavon==0){
						this.Message="San pham "+sanpham.getSanPhamId()+" chua co gia von ,vui long kiem tra lai  san pham nay ";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					if(sanpham.SoLuong==0){
						this.Message="San pham "+sanpham.getSanPhamId()+" chua nhap so luong ,vui long kiem tra lai so luong san pham nay ";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					String sql_insertdetail="insert into PhieuNhapKhoTT_SanPham([PHIEUNHAPKHOTT_FK],[SANPHAM_FK],[SOLUONG]) values("+this.getId()+","+pk_seq+","+sanpham.getSoLuong()+")";
					if(!db.update(sql_insertdetail)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						System.out.println("sql_insertdetail : 146 PhieuNhapKhoTT " +sql_insertdetail);
						return false;
					}
					
				}
				count++;
			}
		}
		if(count==0){
			this.Message=" Khong the them moi don hang nay, chua co san pham nao duoc chon";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them Phieu Nhap Kho nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	@Override
	public boolean DeletePhieuNhapKhoTT() {
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		
		//Thuc hien xoa het dah sach cu
			String sqldelete="update PhieuNhapKhoTT set trangthai='2' where pk_seq= "+this.Id;
			if(!db.update(sqldelete)){
				System.out.println("sqldeletedetail : 146 PhieuNhapKhoTT " +sqldelete);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			  }
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them Phieu Nhap Kho nay, loi : " + er.toString());
			return false;
		}
		return true;
		
	}
	@Override
	public boolean ChotPhieuNhapKhoTT() {
		Hashtable ht = getThieuhang();
		
		dbutils db=new dbutils();	
		
		try
		{
		db.getConnection().setAutoCommit(false);
		String sqleditphieunhapkho="update [Best].[dbo].[PHIEUNHAPKHOTT] set [NGAYSUA]='"+this.NgaySua+"',[NGUOISUA]="+this.NguoiSua+",trangthai='1'" +
									"  where pk_seq= "+this.Id;
		
			if(!db.update(sqleditphieunhapkho)){
				this.Message="Khong The Chot Phieu Nhap Kho Nay ";
			System.out.println("sqleditphieunhapkho : 146 PhieuNhapKhoTT " +sqleditphieunhapkho);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		     }
			//truoc khi thuc hien xoa thi thuc hien cap nhat lai so luong trong kho
			String sql_selectdetail="select soluong,sanpham_fk from PhieuNhapKhoTT_SanPham where PHIEUNHAPKHOTT_FK= "+this.Id;
			ResultSet rs_getdetail=db.get(sql_selectdetail);
			if(rs_getdetail!=null){
				try{
					while (rs_getdetail.next()){
						int soluong_=rs_getdetail.getInt("soluong");
						String masp=getMaSanPham(rs_getdetail.getString("sanpham_fk"));
						String sql_updatekho="update tonkhoicp set stock=stock+"+soluong_+",available= available+"+ soluong_ +" where masp='"+masp+"' and kho='"+ this.KhoTT_Id+"'" ;
						if(!db.update(sql_updatekho)){
							this.Message="Ban khong the cap nhat lai san pham : " + masp;
							System.out.println(sql_updatekho);
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							return false;
						 }else{
							 if(ht.containsKey(rs_getdetail.getString("sanpham_fk"))){
								 String spId = rs_getdetail.getString("sanpham_fk");
								 String khott = (String) ht.get(spId);
								 String sql="update theodoithieuhang set soluongtt = isnull(soluongtt,0) + " + rs_getdetail.getString("soluong") + ", ngaytt = '" + this.NgaySua + "', trangthai='1' where sanpham_fk='" + spId + "' and khott = '" + this.KhoTT_Id + "'";
								 System.out.println(sql);
								 
								 db.update(sql); 
								 db.update("update theodoithieuhang set trangthai='2' where soluongtt >= soluongdt and soluongtt >=soluongthieu");
								 
				    			 this.getEmail();
					    		
					    		 SendMail sm = new SendMail();
					    		 String mesg = "Kinh chao Quy Khach Hang, \n\nSan pham ";
					    			if(sp.containsKey(Id)){
					    				mesg = mesg + ht.get(Id) + " da co hang lai \n\n";
					    			}
					    			
					    			mesg = mesg + "Kinh moi Quy khach hang dat hang tro lai. \n\nCam on va tran trong\n\nHe thong BEST";
					    			sm.postMail(recipient, "Thong bao co hang ", mesg);
					    		 
								 
							 }
						 }
					}
				}catch(Exception er){
					System.out.println("Error 358 PhieuNhapkhoTT : "+er.toString() );
				}
			}
		
		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them Phieu Nhap Kho nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	private String getMaSanPham(String idsp) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String sqlcheckexitsp="select ma from sanpham where pk_seq='"+idsp+"' and trangthai!='2'";
		dbutils db=new dbutils();
		ResultSet rs_db= db.get(sqlcheckexitsp);
		if(rs_db!=null){
			try{
			if(rs_db.next()){
				return rs_db.getString("ma");
			}
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	@Override
	public void setListSanPhamById() {
		// TODO Auto-generated method stub
		listsanpham.clear();
		dbutils db=new dbutils();
		String sql_getchitietsp="select phieunhapkhott_fk,sanpham_fk,soluong,b.ten,b.ma from phieunhapkhott_sanpham a inner join sanpham b on a.sanpham_fk=b.pk_seq  where phieunhapkhott_fk=" +this.Id;
		ResultSet rs_getsanpham=db.get(sql_getchitietsp);
		if(rs_getsanpham!=null){
			try{
				while(rs_getsanpham.next()){
					PhieuNhapKhoTT_SanPham pnksp=new PhieuNhapKhoTT_SanPham();
					pnksp.setId(rs_getsanpham.getString("phieunhapkhott_fk"));
					pnksp.setSanPhamId(rs_getsanpham.getString("ma"));
					pnksp.setTenSanPham(rs_getsanpham.getString("ten"));
					pnksp.setSoLuong(rs_getsanpham.getInt("soluong"));
					listsanpham.add(pnksp);
				}
			}catch(Exception er){
				System.out.println("Error 75 PhieuNhapKhoTT  :"+er.toString());
			}
			
		}
	}

	private Hashtable getThieuhang(){
		dbutils db=new dbutils();
		ResultSet rs = db.get("select khott_fk from phieunhapkhott where pk_seq='" + this.Id + "'");
				
		Hashtable ht = new Hashtable<String, String>();
		Hashtable sp = new Hashtable<String, String>();
		try{
			rs.next();
			this.KhoTT_Id = rs.getString("khott_fk");
			
			ResultSet th = db.get("select sanpham_fk, khott from theodoithieuhang where khott='" + this.KhoTT_Id + "'");
			System.out.println("select sanpham_fk, khott from theodoithieuhang where khott='" + this.KhoTT_Id + "'");
		
			
			ht.put("0", "0");
		
			while(th.next()){
				ht.put(th.getString("sanpham_fk"), th.getString("khott"));
			}
			
			rs = db.get("select a.pk_seq as id, b.ma as masp, b.ten as tensp from theodoithieuhang a inner join sanpham b on b.pk_seq = a.sanpham_fk");
			
			sp.put("0", "0");
			try{
				while(th.next()){
					sp.put(rs.getString("id"), rs.getString("masp")+ " - " + rs.getString("tensp"));
				}
				
			}catch(Exception e){}
			
		}catch(Exception e){}
		
		return ht;
	}
	
	private void getEmail(){
		dbutils db = new dbutils(); 
		ResultSet rs = db.get("select count(email) as num from email");
		 try{
			 rs.next();
			 int n = Integer.valueOf(rs.getString("num")).intValue();
			 if(n > 0){
				 this.recipient = new String[n+1];
				 this.recipient[0] = "" + n;
				 
				 rs = db.get("select email from email");
				 int i = 1;
				 
				 while(rs.next()){
					 this.recipient[i] = rs.getString("email");
					 i++;
				 }				 
			 }
		 }catch(Exception e){}		 
		 		 
	}

}
