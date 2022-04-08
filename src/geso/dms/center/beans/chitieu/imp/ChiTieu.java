package geso.dms.center.beans.chitieu.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.beans.chitieu.IChiTieu;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

public class ChiTieu implements IChiTieu {

	double Chitiet=0;
	String UserID;
	double Id;
	String Thang;
	String Nam;
	String NgayTao;
	String NguoiTao;
	String NguoiSua;
	String NgaySua;
	String NgayKetThuc;
	String KhuVuc="";
	String DienGiai;
	String TenKhuVuc;
	String Message;
	String Dvkd_id;
	String TenDVkd;
	String SoNgayLamViec;
	double ChiTieu;
	String KenhId;
	String TrangThai;
	String LoaiChiTieu;
	ResultSet rsChiTieuNV;
	ResultSet rsKenh;
	ResultSet RsDVKD;
	ResultSet	RsNppNhomSp; 

	String ChuoiNhomSP;
	String[] NhomSP;
	String[] NhomSPId;
	List<ChiTieuNPP> listchitieunpp=new ArrayList<ChiTieuNPP>();
	List<ChiTieu> listchitieu =new ArrayList<ChiTieu>();
	ResultSet rsChitieuPri;
	ResultSet rsChitieuPriNpp;
	ResultSet rsChitieuPriSS;
	ResultSet rsChitieuPriASM;
	ResultSet rsChitieuPriRSM;
	dbutils db;
	String Tumuc;
	String ToiMuc;
	
	String view;
	
	public ChiTieu()
	{

		this.NgayKetThuc="";
		this.SoNgayLamViec="";
		this.Message="";
		this.DienGiai="";
		this.KhuVuc="";
		this.Dvkd_id="";
		this.KenhId="";
		this.Thang="";
		this.Nam="";
		this.LoaiChiTieu="";
		this.Tumuc="";
		this.ToiMuc="";
		this.apdung="0";
		this.quy="";
		this.view ="";
		db=new dbutils();
	}
	public ChiTieu(String id,String loaict)
	{
		this.NgayKetThuc="";
		this.SoNgayLamViec="";
		this.Message="";
		this.DienGiai="";
		this.KhuVuc="";
		this.Dvkd_id="";
		this.KenhId="";
		this.Thang="";
		this.Nam="";
		this.LoaiChiTieu="";
		this.Tumuc="";
		this.ToiMuc="";
		this.apdung="0";
		this.quy="";
		this.view ="";
		db=new dbutils();
		try
		{
			String  sql_getdata="";
			String sql_chitieunpp="";
			if(loaict.equals("0")){

				sql_getdata="SELECT  c.Quy,c.ApDung,isnull(c.loaichitieu,'') as loaichitieu ,  c.kenh_fk,c.trangthai,  C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU,C.DIENGIAI,C.DVKD_FK,donvikinhdoanh, C.NGAYKETTHUC,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
						"NS.TEN AS NGUOISUA FROM dbo.CHITIEU AS C inner join donvikinhdoanh d on d.pk_seq=c.dvkd_fk INNER JOIN "+
						"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ where c.trangthai!='2' and c.pk_seq="+ id;

			}else{

				sql_getdata="SELECT  c.Quy,c.ApDung, isnull(c.loaichitieu,'') as loaichitieu ,c.trangthai, c.kenh_fk, C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU,C.DIENGIAI,C.DVKD_FK,donvikinhdoanh, C.NGAYKETTHUC,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
						"NS.TEN AS NGUOISUA FROM dbo.CHITIEU_SEC AS C inner join donvikinhdoanh d on d.pk_seq=c.dvkd_fk INNER JOIN "+
						"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ where c.trangthai!='2' and c.pk_seq="+ id;
			}
			ResultSet rsChiTieu=	db.get(sql_getdata);
			
			System.out.println("[Init]"+sql_getdata);
			
			if(rsChiTieu!=null)
			{
				while(rsChiTieu.next()){
					this.setID(rsChiTieu.getDouble("PK_SEQ"));
					
					this.setApdung(rsChiTieu.getString("Apdung")==null?"0":rsChiTieu.getString("Apdung"));
					this.setThang(rsChiTieu.getString("THANG")==null?"":rsChiTieu.getString("THANG"));
					this.setNam(rsChiTieu.getString("NAM")==null?"":rsChiTieu.getString("NAM"));
					this.setQuy(rsChiTieu.getString("Quy")==null?"":rsChiTieu.getString("Quy"));
					
					this.setChitieu(rsChiTieu.getDouble("CHITIEU"));
					this.setNgayKetThuc(rsChiTieu.getString("NGAYKETTHUC"));
					this.setNgayTao(rsChiTieu.getString("NGAYTAO"));
					this.setNgaySua(rsChiTieu.getString("NGAYSUA"));
					this.setDienGiai(rsChiTieu.getString("DIENGIAI"));
					this.setNguoiTao(rsChiTieu.getString("NGUOITAO"));
					this.setNguoiSua(rsChiTieu.getString("NGUOISUA"));
					this.setTenDVKD(rsChiTieu.getString("donvikinhdoanh"));
					this.setDVKDID(rsChiTieu.getString("DVKD_FK"));
					this.setSoNgayLamViec(rsChiTieu.getString("SONGAYLAMVIEC"));
					this.setKenhId(rsChiTieu.getString("kenh_fk"));
					this.setTrangThai(rsChiTieu.getString("trangthai"));
					this.setLoaiChiTieu(rsChiTieu.getString("loaichitieu"));

				}
			}
			rsChiTieu.close();
			if(loaict.equals("0"))
			{
				String sql=
						"select distinct NHOMSP_FK as nspId, nspTen = case when nhomsp_fk = 0 then N'Chỉ tiêu' else b.TEN end "+
								" from chitieu_nhapp_nhomsp a left join NHOMSANPHAM b on b.PK_SEQ=a.NHOMSP_FK "+
								" where CHITIEU_FK='"+this.Id+"' ";
				ResultSet rs=this.db.get(sql);
				String[] nhom =new String[10];
				String[] nhomid =new String[10];

				int i=0;
				String strselect="";
				String strngoac="[0]";
				String strsumsi="sum(CT0) as CT0";

				if(rs!=null)
				{
					while (rs.next())
					{
						nhomid[i]=rs.getString("nspId");
						nhom[i]=rs.getString("nspTen");
						if(i==0)
						{
							strsumsi = ", sum(CT"+rs.getString("nspId")+") as CT"+rs.getString("nspId")+"";
							strselect=" ,["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
							strngoac=" ["+rs.getString("nspId")+"]";
						}else
						{
							strsumsi = strsumsi + ", sum(CT"+rs.getString("nspId")+") as CT"+rs.getString("nspId")+"";
							strselect=strselect+", ["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
							strngoac=strngoac+ ",["+rs.getString("nspId")+"]";
						}
						i++;
					}
				}
				this.NhomSP=nhom;
				this.NhomSPId=nhomid;	

				sql=	  
					"	select  CHITIEU_FK,v.PK_SEQ as vungId,v.TEN as vungTeN,kv.PK_SEQ as kvId, "+
					"	kv.TEN as kvTen,npp.PK_SEQ as nppId,npp.MA as nppMa,npp.TEN as nppTen,CHITIEU_FK "+strselect+" "+
					"	from chitieu_nhapp_nhomsp   "+
					"	pivot  "+
					"	( "+
					"	sum(CHITIEU) for NHOMSP_FK in ("+strngoac+") "+
					"	)as p inner join NHAPHANPHOI npp on npp.PK_SEQ=p.NPP_FK "+
					"	left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK "+
					"	left join VUNG v on v.PK_SEQ=kv.VUNG_FK "+
					"	where p.CHITIEU_FK="+this.Id+""+
					"	order by v.TEN,kv.TEN,npp.TEN ";
				System.out.println("chi tieu npp "+sql);
				this.rsChitieuPriNpp=db.get(sql);

				sql=	"\n	select g.PK_SEQ as ssId, g.SMARTID as ssMa, g.TEN as ssTen "+strsumsi+" "+
						"\n	from CHITIEU a inner join "+
						"\n	(select CHITIEU_FK, NPP_FK "+strselect+" "+
						"\n		from "+
						"\n		( "+
						"\n			select CHITIEU_FK, NPP_FK, NHOMSP_FK, CHITIEU from CHITIEU_NHAPP_NHOMSP "+
						"\n		) p "+
						"\n	pivot (sum(chitieu) for nhomsp_fk in ( "+strngoac+" )) AS t) aa on aa.CHITIEU_FK = a.PK_SEQ "+
						"\n	inner join NHAPHANPHOI d on d.PK_SEQ=aa.NPP_FK  "+
						"\n	inner join NHAPP_GIAMSATBH e on d.PK_SEQ = e.NPP_FK  "+
						"\n inner join giamsatbanhang g on e.gsbh_fk = g.pk_seq"+
						"\n	where a.pk_seq = "+this.Id+""+
						"\n	group by g.PK_SEQ, g.SMARTID, g.TEN	";
				System.out.println("chi tieu ss "+sql);
				this.rsChitieuPriSS=db.get(sql);
				
				sql=	"\n	select asm.PK_SEQ as asmId, asm.SMARTID as asmMa, asm.TEN as asmTen "+strsumsi+" "+
						"\n	from CHITIEU a inner join "+
						"\n	(select CHITIEU_FK, NPP_FK "+strselect+" "+
						"\n		from "+
						"\n		( "+
						"\n			select CHITIEU_FK, NPP_FK, NHOMSP_FK, CHITIEU from CHITIEU_NHAPP_NHOMSP "+
						"\n		) p "+
						"\n	pivot (sum(chitieu) for nhomsp_fk in ( "+strngoac+" )) AS t) aa on aa.CHITIEU_FK = a.PK_SEQ "+
						"\n	inner join NHAPHANPHOI d on d.PK_SEQ = aa.NPP_FK  	"+
						"\n	inner join KHUVUC kv on kv.PK_SEQ = d.KHUVUC_FK "+
						"\n	inner join ASM on ASM.PK_SEQ = kv.ASM_fk "+
						"\n	where a.pk_seq = "+this.Id+""+
						"\n group by asm.PK_SEQ, asm.SMARTID, asm.TEN";
				System.out.println("chi tieu ss "+sql);
				this.rsChitieuPriASM=db.get(sql);
				
				sql=	"\n	select bm.pk_seq as rsmId, bm.SMARTID as rsmMa, bm.TEN as rsmTen "+strsumsi+" "+
						"\n	from CHITIEU a inner join "+
						"\n	(select CHITIEU_FK, NPP_FK "+strselect+" "+
						"\n		from "+
						"\n		( "+
						"\n			select CHITIEU_FK, NPP_FK, NHOMSP_FK, CHITIEU from CHITIEU_NHAPP_NHOMSP "+
						"\n		) p "+
						"\n	pivot (sum(chitieu) for nhomsp_fk in ( "+strngoac+" )) AS t) aa on aa.CHITIEU_FK = a.PK_SEQ "+
						"\n	inner join NHAPHANPHOI d on d.PK_SEQ = aa.NPP_FK  	"+
						"\n	inner join KHUVUC kv on kv.PK_SEQ = d.KHUVUC_FK "+
						"\n	inner join VUNG v on v.PK_SEQ = kv.VUNG_FK "+
						"\n inner join BM on bm.PK_SEQ = v.bm_FK "+
						"\n	where a.pk_seq = "+this.Id+""+
						"\n group by bm.PK_SEQ, bm.SMARTID, bm.TEN";
				System.out.println("chi tieu ss "+sql);
				this.rsChitieuPriRSM=db.get(sql);

			}else
			{
				String sql=
						"	select nhomsp_fk as nspId,b.TEN  as nspTen from chitieusec_nhapp_nhomsp  a "+
								"		inner join NHOMSANPHAM  b on b.PK_SEQ=a.NHOMSP_FK "+
								"	 where  CHITIEUSEC_FK='"+this.Id+"' "+  
								"	 union   "+
								"	 select distinct nhomsanpham_fk as nspId ,c.TEN as nspTen "+
								"	 from chitieunpp_ddkd_nhomsp a inner join CHITIEUNPP b on b.PK_SEQ=a.CHITIEUNPP_FK "+  
								"		inner join NHOMSANPHAM  c on a.NHOMSANPHAM_FK=c.PK_SEQ "+
								"	 where b.THANG='"+this.Thang+"' and b.NAM='"+this.Nam+"' and b.KENH_FK='"+this.KenhId+"' and b.DVKD_FK='"+this.Dvkd_id+"' "; 

				System.out.println("[NhomSanPham]"+sql);
				ResultSet rs=this.db.get(sql);
				String[] nhom =new String[10];
				String[] nhomid =new String[10];

				int i=0;
				String strselect="";
				String strngoac="[0]";

				if(rs!=null)
				{
					while (rs.next())
					{
						nhomid[i]=rs.getString("nspId");
						nhom[i]=rs.getString("nspTen");
						if(i==0)
						{
							strselect=" ,["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
							strngoac=" [0], ["+rs.getString("nspId")+"]";
						}else
						{
							strselect=strselect+", ["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
							strngoac=strngoac+ ",["+rs.getString("nspId")+"]";
						}
						i++;
					}
				}
				this.NhomSP=nhom;
				this.NhomSPId=nhomid;	

				sql=
						"	 select v.TEN as vungTen,kv.TEN as kvTen, \n"+
								"		npp.PK_SEQ as nppId,npp.MA as nppMa,npp.TEN as nppTen,  \n"+
								"		a.CHITIEU_SEC_FK,NHAPP_FK,CHITIEU as ctSalesOut,SODONHANG as ctSoDonHang, \n"+
								"		SKU as ctSKU,DOPHU as ctDoPhu,sanluongtrendh as ctSanLuong,SoKhachHang_MuaHang as ctSoKhachHang_MuaHang,SoKhachHang_PhatSinh as ctSoKhachHang_PhatSinh,GiaoHang as ctGiaoHang,TonKho as ctTonKho "+strselect+"  \n"+
								"	from chitieu_nhapp_sec a \n"+
								"	left join  \n"+
								"	( \n"+
								"		select CHITIEUSEC_FK,NPP_FK,NHOMSP_FK,CHITIEU as ctNhom  \n"+
								"		from  chitieusec_nhapp_nhomsp  \n"+
								"		where CHITIEUSEC_FK='"+this.Id+"' \n"+
								"	)p pivot  \n"+
								"	( \n"+
								"		 sum(ctNhom) for NHOMSP_FK in ("+strngoac+" )  "+  
								"	)as ctNhom on ctNhom.CHITIEUSEC_FK=a.CHITIEU_SEC_FK and a.NHAPP_FK=ctNhom.NPP_FK "+
								"		left join NHAPHANPHOI npp on npp.PK_SEQ=a.NHAPP_FK "+
								"		left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK "+
								"		left join VUNG v on v.PK_SEQ=kv.VUNG_FK "+
								"	where a.CHITIEU_SEC_FK='"+this.Id+"' " +
								" order by v.ten,kv.ten,npp.ten  ";

				System.out.println("[ctNpp]"+sql);

				this.RsNppNhomSp=db.get(sql);


				sql=	
						" 	select v.TEN as vTen,kv.TEN as kvTen,npp.PK_SEQ as nppId,npp.MA as nppMa,npp.TEN as nppTen,  \n"+
								"		c.PK_SEQ as ddkdId,c.TEN as ddkdTen,b.CHITIEU as ctSalesOut,b.DOPHU as ctDoPhu, \n"+
								"		b.SODONHANG as ctSoDonHang,b.sanluongtrendh as ctSanLuong,b.SKU,SoKhachHang_MuaHang as ctSoKhachHang_MuaHang,SoKhachHang_PhatSinh as ctSoKhachHang_PhatSinh,GiaoHang as ctGiaoHang,TonKho as ctTonKho "+strselect+"   \n"+
								"	from       \n"+
								"	(    \n"+
								"		select a.nhapp_fk,b.chitieu,b.DOPHU,b.SODONHANG,b.sanluongtrendh,b.SKU,b.chitieunpp_fk,b.ddkd_fk,b.SoKhachHang_MuaHang ,b.SoKhachHang_PhatSinh,b.GiaoHang,b.TonKho \n"+   
								"		from    chitieunpp a  inner join chitieunpp_ddkd b on a.pk_seq=b.chitieunpp_fk    \n"+
								"		where a.THANG='"+this.Thang+"' and a.NAM='"+this.Nam+"' and a.KENH_FK='"+this.KenhId+"' and a.DVKD_FK='"+this.Dvkd_id+"' \n"+
								"	) b   \n"+
								"	left join    \n"+
								"	(          \n"+
								"		select a.chitieu as ctNhom ,a.chitieunpp_fk,a.ddkd_fk,a.nhomsanpham_fk     \n"+
								"		from chitieunpp_ddkd_nhomsp  a  inner join CHITIEUNPP b on b.PK_SEQ=a.CHITIEUNPP_FK \n"+
								"		where b.THANG='"+this.Thang+"' and b.NAM='"+this.Nam+"' and b.KENH_FK='"+this.KenhId+"' and b.DVKD_FK='"+this.Dvkd_id+"' \n"+     	    
								"	) p  pivot \n"+           
								"	(       \n"+
								"		sum(ctNhom)   for nhomsanpham_fk in  ("+strngoac+" )     \n"+
								"	) as ctnhom on ctnhom.chitieunpp_fk=b.chitieunpp_fk   and b.ddkd_fk=ctnhom.ddkd_fk \n"+          
								"	inner join daidienkinhdoanh c on b.ddkd_fk=c.pk_seq      \n"+ 
								"	inner join nhaphanphoi npp on npp.pk_seq=b.nhapp_fk \n"+      
								"	left join khuvuc kv on kv.pk_seq=npp.khuvuc_fk  \n"+    
								"	left join VUNG v on v.PK_SEQ=kv.VUNG_FK \n"+
								"	where 1=1 " +
								" order by v.ten,kv.ten,npp.ten,c.ten  " ;
				System.out.println("[ctDDKD]"+sql);
				this.rsChiTieuNV=db.get(sql);
			}
		}catch(Exception er)
		{
			er.printStackTrace();
			this.Message="Error ChiTieu.java - line : 88- detail error :"+ er.toString();
		}
		this.Message="";
	}


	public void setChitieu(double chitieu) {

		this.ChiTieu=chitieu;
	}


	public double getChitieu() {

		return this.ChiTieu;
	}


	public void setID(double id) {

		this.Id=id;	
	}


	public double getID() {

		return Id;
	}


	public void setThang(String thang) {

		this.Thang=thang;
	}


	public String getThang() {

		return this.Thang;
	}


	public void setNam(String nam) {

		this.Nam=nam;
	}


	public String getNam() {
		if(this.Nam.length() > 0)
		{
			return this.Nam;	
		}else
		{
			return this.getDateTime().substring(0, 4);
		}
	}


	public void setKhuVucID(String khuvucid) {

		this.KhuVuc=khuvucid;
	}


	public String getKhuVucID() {

		return this.KhuVuc;
	}


	public void setNgayKetThuc(String ngayketthuc) {

		this.NgayKetThuc=ngayketthuc;
	}


	public String getNgayKetThuc() {

		return this.NgayKetThuc;
	}


	public void setNgayTao(String ngaytao) {

		this.NgayTao =ngaytao;
	}


	public String getNgayTao() {

		return this.NgayTao;
	}


	public void setNgaySua(String ngaysua) {

		this.NgaySua=ngaysua;
	}


	public String getNgaySua() {

		return this.NgaySua;
	}


	public void setDienGiai(String diengiai) {

		this.DienGiai=diengiai;
	}


	public String getDienGiai() {

		return this.DienGiai;
	}
	public boolean KiemTraHopLe()
	{
		if(this.NguoiTao.toString().equals("null") || this.NguoiTao.equals("")){
			this.Message="Ten Nguoi Dung Khong Hop Le,Vui Long Dang Xuat De Thu Lai!";
			return false;
		}
		if(this.KenhId==null || this.KenhId.equals("")){
			this.Message="Vui Long Chon Kenh!";
			return false;
		}
		if(this.Dvkd_id==null || this.Dvkd_id.equals("")){
			this.Message="Vui Long Chon Don Vi Kinh Doanh!";
			return false;
		}
		if(this.Thang.length()==0)
		{
			this.Message="Vui Long Chon Thang";
			return false;
		}
		if(this.Nam.length()==0){
			this.Message="Vui Long Chon Nam";
			return false;
		}
		try{
			Integer.parseInt(this.SoNgayLamViec);
		}catch(Exception er){
			this.Message="So Ngay Lam Viec Khong Hop Le,Vui Long Kiem Tra Lai";
			return false;
		}
		System.out.println("[Msg]"+this.Message);
		return true;
	}

	public boolean SaveChiTieu() 
	{
		try
		{
			if(!KiemTraHopLe())
			{
				return false;
			}
			db.getConnection().setAutoCommit(false);
			String sql_checkexit="select pk_seq from ChiTieu where thang= " +this.Thang+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" and trangthai!=2";
			ResultSet rs_check=db.get(sql_checkexit);
			if(rs_check!=null)
			{
				if(rs_check.next())
				{
					this.setMessage("Chi Tieu Trong Thang Da Thiet Lap cho cac nha phan phoi thuoc khu vuc nay, Vui Long Kiem Tra Lai");
					return false;
				}
			}
			/*String sqlSaveChiTieu=
					"insert into CHITIEU(ApDung,Quy,THANG,NAM,CHITIEU,NGAYKETTHUC,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,DIENGIAI,TRANGTHAI,DVKD_FK,songaylamviec,kenh_fk) " +
							"values ("+this.apdung+","+(this.quy.length()<=0?"NULL":this.quy)+","+(this.Thang.length()<=0?"NULL":this.Thang)+","+this.Nam+","+this.ChiTieu+",'"+this.NgayKetThuc+"','"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"',N'"+this.DienGiai+"','0',"+this.Dvkd_id+","+this.SoNgayLamViec+","+this.KenhId+")";*/
			String sqlSaveChiTieu=
					"insert into CHITIEU(ApDung,THANG,NAM,CHITIEU,NGAYKETTHUC,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,DIENGIAI,TRANGTHAI,DVKD_FK,songaylamviec,kenh_fk) " +
							"values ("+this.apdung+","+(this.Thang.length()<=0?"NULL":this.Thang)+","+this.Nam+","+this.ChiTieu+",'"+this.NgayKetThuc+"','"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"',N'"+this.DienGiai+"','0',"+this.Dvkd_id+","+this.SoNgayLamViec+","+this.KenhId+")";
			System.out.println("---1 insert into CHITIEU " + sqlSaveChiTieu);
			if(!db.update(sqlSaveChiTieu))
			{
				this.Message="Ban Khong Them Cap Nhat Chi Tieu Nay, Vui Long Thu Lai,Loi Cau Lenh   - 146 line -Form : Chitieuttchovung.java - Error here :  " +sqlSaveChiTieu;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}

			String query = "select scope_identity() as dhId";
			ResultSet rsDh = db.get(query);	
			try
			{
				rsDh.next();
				this.setID(rsDh.getDouble("dhId"));
			}catch(Exception ex){
				return false;
			}
			rsDh.close();

			String chuoi=this.getKhuVucID();
			String sql="select npp.pk_seq as nppid, a.ma "+chuoi+" from chitieutmp a inner join  nhaphanphoi npp on npp.ma=a.ma";
			ResultSet rs=db.get(sql);
			String [] mang=this.ChuoiNhomSP.split(";");
			while (rs.next())
			{
				for(int k=0;k<mang.length;k++)
				{
					sql="insert into CHITIEU_NHAPP_NHOMSP(chitieu_fk, npp_fk, nhomsp_fk, chitieu)  select '"+this.Id+"','"+rs.getString("nppid")+"','"+mang[k]+"','"+rs.getString("manhom"+(k+1))+"'";
					System.out.println(sql);
					if(!db.update(sql)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.setMessage(sql);
						return false;
					}
				}
			}

			sql=" insert into chitieu_nhapp(chitieu_fk,nhapp_fk,chitieu ) select  chitieu_fk,npp_fk,sum(chitieu) "+
					" from CHITIEU_NHAPP_NHOMSP where chitieu_fk="+this.Id+" group by chitieu_fk,npp_fk ";
			System.out.println("insert chitieu_nhapp " + sql);
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}
			
			sql=" update CHITIEU set CHITIEU = i.tong "
				+ "from ( select chitieu_fk, sum(c.CHITIEU) as tong from CHITIEU_NHAPP_NHOMSP c where CHITIEU_FK = "+this.Id+" group by CHITIEU_FK) i "
				+ "where CHITIEU.PK_SEQ = i.CHITIEU_FK ";
			System.out.println("insert chitieu_nhapp " + sql);
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er)
		{
			er.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;
		}
		return true;
	}

	public boolean EditChiTieu() 
	{
		try
		{
			if(!KiemTraHopLe())
			{
				return false;
			}

			db.getConnection().setAutoCommit(false);
			String sql_checkexit="select pk_seq from ChiTieu where thang= " +this.Thang+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" and trangthai!=2 and pk_seq <>" +this.getID();
			ResultSet rs_check=db.get(sql_checkexit);
			if(rs_check!=null)
			{
				if(rs_check.next())
				{
					this.setMessage("Chi Tieu Trong Thang Da Thiet Lap cho cac nha phan phoi thuoc khu vuc nay, Vui Long Kiem Tra Lai");
					return false;
				}
			}

			//update lai bang chitieu_trungtam;
			/*String sqlEditChiTieu="update CHITIEU set ApDung="+apdung+",Quy="+(this.quy.length()<=0?"NULL":this.quy)+",THANG="+(this.Thang.length()<=0?"NULL":this.Thang)+",NAM="+this.Nam+",CHITIEU="+this.ChiTieu+",NGAYKETTHUC='"+this.NgayKetThuc+
					"',NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',DIENGIAI='"+this.DienGiai+"' ,dvkd_fk="+this.Dvkd_id+" ,songaylamviec="+this.SoNgayLamViec+" ,kenh_fk="+this.KenhId+"  where pk_seq="+ this.Id;*/
			String sqlEditChiTieu="update CHITIEU set ApDung="+apdung+",THANG="+(this.Thang.length()<=0?"NULL":this.Thang)+",NAM="+this.Nam+",CHITIEU="+this.ChiTieu+",NGAYKETTHUC='"+this.NgayKetThuc+
					"',NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',DIENGIAI='"+this.DienGiai+"' ,dvkd_fk="+this.Dvkd_id+" ,songaylamviec="+this.SoNgayLamViec+" ,kenh_fk="+this.KenhId+"  where pk_seq="+ this.Id;

			if(!db.update(sqlEditChiTieu))
			{
				this.Message="Loi  : "+ sqlEditChiTieu;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}


			String chuoi=this.getKhuVucID();

			String sql="delete CHITIEU_NHAPP_NHOMSP where chitieu_fk= "+this.Id;
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="delete CHITIEU_NHAPP where chitieu_fk= "+this.Id;
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="select npp.pk_seq as nppid, a.ma "+chuoi+" from chitieutmp a inner join  nhaphanphoi npp on npp.ma=a.ma";
			System.out.println("lay thong tin "+sql);
			ResultSet rs=db.get(sql);
			String [] mang=this.ChuoiNhomSP.split(";");

			while (rs.next()){
				for(int k=0;k<mang.length;k++){
					sql="insert into CHITIEU_NHAPP_NHOMSP  values('"+this.Id+"','"+rs.getString("nppid")+"','"+mang[k]+"','"+rs.getString("manhom"+(k+1))+"')";
					if(!db.update(sql)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.setMessage(sql);
						return false;
					}
				}
			}
			sql=" insert into chitieu_nhapp(chitieu_fk,nhapp_fk,chitieu ) select  chitieu_fk,npp_fk,sum(chitieu) "+
					" from CHITIEU_NHAPP_NHOMSP where chitieu_fk="+this.Id+" group by chitieu_fk,npp_fk ";
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			er.printStackTrace();
			this.Message="Khong the Chinh Sua Chi Tieu :"+ er.toString();
			return false;
		}
		return true;
	}

	public boolean DeleteChitieu() {

		dbutils db=new dbutils();	
		try
		{
			db.getConnection().setAutoCommit(false);
			String sqlDelChiTieu="update CHITIEU set trangthai='2' where pk_seq="+ this.Id;
			if(!db.update(sqlDelChiTieu)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the xoa chi tieu moi nay, loi : " + er.toString());
			return false;
		}
		return false;
	}


	public void setUserId(String userid) {

		this.UserID=userid;
	}


	public String getUserId() {

		return this.UserID;
	}


	public List<ChiTieu> getChiTieu() {

		return listchitieu;
	}


	public void setListChiTieu(String sql,String loaict) 
	{
		String  sql_getdata="";
		Utility Ult = new Utility(); 
		sql_getdata="SELECT   c.Quy,c.ApDung ,C.PK_SEQ,c.trangthai,kenh_fk, C.THANG, C.NAM,  C.CHITIEU,C.DIENGIAI, C.NGAYKETTHUC,C.DVKD_FK,D.donvikinhdoanh,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
				"NT.PK_SEQ AS NGUOISUA FROM dbo.CHITIEU_SEC AS C INNER JOIN "+
				"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ" +
				" inner join DONVIKINHDOANH D on D.pk_seq=C.DVKD_FK where 1=1 "+
				" and kenh_fk in "+ Ult.quyen_kenh(this.UserID);
		if(sql!="")
		{
			sql_getdata=sql;
		} 
		try
		{
			ResultSet rsChiTieu=	db.get(sql_getdata);
			/*if(rsChiTieu!=null)*/
			{
				while(rsChiTieu.next()){
					ChiTieu CT=new ChiTieu();
					CT.setID(rsChiTieu.getDouble("PK_SEQ"));
					CT.setThang(rsChiTieu.getString("THANG")==null?"":rsChiTieu.getString("THANG"));
					CT.setNam(rsChiTieu.getString("NAM")==null?"":rsChiTieu.getString("NAM"));
					CT.setQuy(rsChiTieu.getString("QUY")==null?"":rsChiTieu.getString("QUY"));
					CT.setApdung(rsChiTieu.getString("apdung")==null?"":rsChiTieu.getString("apdung"));
					CT.setChitieu(rsChiTieu.getDouble("CHITIEU"));
					CT.setNgayKetThuc(rsChiTieu.getString("NGAYKETTHUC"));
					CT.setNgayTao(rsChiTieu.getString("NGAYTAO"));
					CT.setNgaySua(rsChiTieu.getString("NGAYSUA"));
					CT.setDienGiai(rsChiTieu.getString("DIENGIAI"));
					CT.setTenDVKD(rsChiTieu.getString("DONVIKINHDOANH"));
					CT.setDVKDID(rsChiTieu.getString("DVKD_FK"));
					CT.setNguoiTao(rsChiTieu.getString("NGUOITAO"));
					CT.setNguoiSua(rsChiTieu.getString("NGUOISUA"));
					CT.setSoNgayLamViec(rsChiTieu.getString("SONGAYLAMVIEC"));
					CT.setTrangThai(rsChiTieu.getString("trangthai"));
					CT.setKenhId(rsChiTieu.getString("kenh_fk"));
					listchitieu.add(CT);
				}
			}
		}catch(Exception er)
		{
			er.printStackTrace();
		}
	}



	public void setTenKhuVuc(String tenkhuvuc) {

		this.TenKhuVuc=tenkhuvuc;
	}


	public String getTenKhuVuc() {

		return this.TenKhuVuc;
	}


	public void setNguoiTao(String nguoitao) {

		this.NguoiTao=nguoitao;		
	}


	public String getNguoiTao() {

		return this.NguoiTao;
	}


	public void setNguoiSua(String nguoisua) {

		this.NguoiSua=nguoisua;
	}


	public String getNguoiSua() {

		return this.NguoiSua;
	}


	public void setMessage(String strmessage) {

		this.Message=strmessage;
	}


	public String getMessage() {

		return this.Message;
	}

	public List<ChiTieuNPP> getListChiTieuNPP() {

		return this.listchitieunpp;
	}

	public void setListChiTieuNPP(List<ChiTieuNPP> list) {

		this.listchitieunpp=list;
	}

	public void setDVKDID(String dvkdid) {

		this.Dvkd_id=dvkdid;
	}

	public String getDVKDId() {

		return this.Dvkd_id;
	}

	public void setTenDVKD(String tendvkd) {

		this.TenDVkd=tendvkd;
	}

	public String getTenDVKD() {

		return this.TenDVkd;
	}

	public boolean SaveChiTieu_Sec() 
	{
		try
		{
			if(!KiemTraHopLe())
			{
				return false;
			}
			db.getConnection().setAutoCommit(false);
			double tongchitieusellsout=0;
			//kiem tra xenm chi tieu trong thoi gian nay da co chua
			String sql="select pk_seq from CHITIEU_SEC where thang= " +this.Thang+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" and trangthai!=2";
			ResultSet rs_check=db.get(sql);
			if(rs_check.next())
			{//co du lieu
				this.setMessage("Chi Tieu Trong Thang Da Thiet Lap cho cac nha phan phoi thuoc khu vuc nay, Vui Long Kiem Tra Lai");
				return false;
			}

			// thuc hien insert
			sql="insert into CHITIEU_SEC(LOAICHITIEU,THANG,NAM,CHITIEU,NGAYKETTHUC,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI,DVKD_FK,songaylamviec,kenh_fk,diengiai) " +
					"values (1,"+this.Thang+","+this.Nam+","+this.ChiTieu+",'"+this.NgayKetThuc+"','"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"','0',"+this.Dvkd_id+","+this.SoNgayLamViec+","+this.KenhId+",N'"+this.DienGiai+"')";
			if(!db.update(sql))
			{
				this.Message="Loi :"+sql;	
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}

			//thuc hien insert chi tiet
			String query = "select scope_identity() as dhId";
			ResultSet rsDh = db.get(query);	
			try
			{
				rsDh.next();
				this.setID(rsDh.getDouble("dhId"));
				rsDh.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}

			//Chuoi de lay nhung nhom hang dua vao
			String chuoi=this.getKhuVucID();

			sql="select NPP.PK_SEQ,a.ma,a.sellsout,a.dophu,a.sanluongtrendh,a.donhang,SoKhachHang_MuaHang,SoKhachHang_PhatSinh,TonKho,GiaoHang"+chuoi+" FROM " +
					" chitieutmp a inner join NHAPHANPHOI NPP on NPP.PK_SEQ=a.ma where chucvu='NPP' order by NPP.PK_SEQ";
			ResultSet rs=db.get(sql);

			System.out.println("___Chia_Chi_Tieu_NVBH "+sql);

			String idchitieunpp="";
			while (rs.next())
			{	
				double chitieusellsout=0;
				String [] mang=this.ChuoiNhomSP.split(";");
				for(int k=0;k<mang.length;k++)
				{
					sql="insert into CHITIEUSEC_NHAPP_NHOMSP values('"+this.Id+"','"+rs.getString("ma")+"','"+mang[k]+"','"+rs.getDouble("manhom"+(k+1))+"')";
					chitieusellsout=chitieusellsout+ rs.getDouble("manhom"+(k+1));
					if(!db.update(sql))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.setMessage(sql);
						return false;
					}
				}
				tongchitieusellsout=tongchitieusellsout+ chitieusellsout;
				sql="insert into chitieu_nhapp_sec(CHITIEU_SEC_fk,nhapp_fk,chitieu,sodonhang,DOPHU,sanluongtrendh,SoKhachHang_MuaHang,SoKhachHang_PhatSinh,TonKho,GiaoHang) values("+this.getID()+","+rs.getString("ma")+","+chitieusellsout+",'"+rs.getInt("donhang")+"','"+rs.getInt("dophu")+"',"+rs.getFloat("sanluongtrendh")+","+rs.getFloat("SoKhachHang_MuaHang")+","+rs.getFloat("SoKhachHang_PhatSinh")+","+rs.getDouble("TonKho")+","+rs.getDouble("GiaoHang")+")";
				if(!db.update(sql))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.Message= "Khong the them chi tieu chi tieu :" +sql;
					return false;
				}
				// chia chỉ tiêu cho nhà phân phối sau đó chia đều cho các nhân viên bán hàng theo kênh và đơn vị kinh doanh của giám sát bán hàng.
				idchitieunpp="";
				sql="insert into chitieunpp (loaichitieu,thang,nam,chitieu,nhapp_fk,ngaytao,nguoitao,ngaysua,nguoisua,trangthai,dvkd_fk,songaylamviec,kenh_fk,ngayketthuc,diengiai)" +
						"values ('"+this.LoaiChiTieu+"','"+this.getThang()+"','"+this.getNam()+"',"+chitieusellsout+",'"+rs.getString("ma")+"','"+this.getNgayTao()+"','"+this.getUserId()+"'," +
						"'"+this.NgaySua+"','"+this.getUserId()+"','0','"+this.getDVKDId()+"','"+this.getSoNgayLamViec()+"','"+this.getKenhId()+"','"+this.NgayKetThuc+"','"+this.getDienGiai()+"' )";
				System.out.println(sql);
				if(!db.update(sql))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.setMessage(sql);
					return false;
				}
				//thuc hien insert chi tiet
				query = "select scope_identity() as Id";
				rsDh = db.get(query);	
				try
				{
					rsDh.next();
					idchitieunpp=rsDh.getString("id");
					rsDh.close();
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}

				sql="select ddkd.pk_seq from  daidienkinhdoanh ddkd inner join "+
						" ddkd_gsbh a on a.ddkd_fk=ddkd.pk_seq "+
						" inner join giamsatbanhang gsbh on gsbh.pk_seq=a.gsbh_fk "+
						" where gsbh.trangthai=1 and  ddkd.trangthai='1' and ddkd.npp_fk="+rs.getString("ma")+" and gsbh.dvkd_fk="+this.Dvkd_id+" and gsbh.kbh_fk="+this.KenhId; 	
				System.out.println("Danh sach dai dien kinh doanh : "+sql);
				ResultSet rsddkd=this.db.get(sql);
				String[] mangddkd= new String[50];
				int i =0;
				while (rsddkd.next())
				{
					mangddkd[i]=rsddkd.getString("pk_seq");
					i=i+1;
				}
				rsddkd.close();

				for (int j=0;j<i;j++)
				{
					sql="insert into chitieunpp_ddkd (chitieunpp_fk,ddkd_fk,chitieu,sodonhang,sku,dophu,sanluongtrendh,SoKhachHang_MuaHang,SoKhachHang_PhatSinh,GiaoHang,TonKho) values("+idchitieunpp+","+mangddkd[j]+","+chitieusellsout/i+","+rs.getInt("donhang")+",'0',"+ rs.getInt("dophu")+","+rs.getFloat("sanluongtrendh")+","+rs.getFloat("SoKhachHang_MuaHang")+","+rs.getFloat("SoKhachHang_PhatSinh")+","+rs.getFloat("GiaoHang")+","+rs.getFloat("TonKho")+") ";
					if(!db.update(sql))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.setMessage(sql);
						return false;
					}
					for(int k=0;k<mang.length;k++)
					{
						sql="insert into CHITIEUNPP_DDKD_NHOMSP values('"+idchitieunpp+"','"+mangddkd[j]+"','"+mang[k]+"',"+rs.getDouble("manhom"+(k+1))/i+")";
						if(!db.update(sql))
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.setMessage(sql);
							return false;
						}
					}
				}
			}

			sql="update chitieu_sec set chitieu="+tongchitieusellsout+" where pk_seq="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}

			sql=" insert into CHITIEUSEC_GSBH_NHOMSP   SELECT  "+this.Id+ ",B.GSBH_FK,A.NHOMSANPHAM_FK,SUM(A.CHITIEU) FROM CHITIEUNPP_DDKD_NHOMSP A INNER JOIN "+
					" CHITIEUNPP CT ON CT.PK_SEQ=CHITIEUNPP_FK "+
					" INNER JOIN DDKD_GSBH B ON B.DDKD_FK= A.DDKD_FK "+
					" WHERE CT.THANG="+this.getThang()+" AND CT.NAM="+this.getNam()+" and CT.KENH_FK="+this.getKenhId()+"  AND CT.DVKD_FK="+this.getDVKDId()+
					"  GROUP BY B.GSBH_FK,A.NHOMSANPHAM_FK";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}		
			sql=
					"INSERT INTO CHITIEUSEC_GSBH (chitieusec_fk,gsbh_fk,dophu,donhang,chitieu,sku,sanluongtrendh,TonKho,GiaoHang,SoKhachHang_MuaHang,SoKhachHang_PhatSinh ) "+
							" SELECT "+this.Id+",B.GSBH_FK ,ISNULL(SUM(DOPHU),0),ISNULL( SUM(SODONHANG),0),ISNULL(SUM(A.CHITIEU),0),ISNULL(SUM(SKU),0),ISNULL(SUM(sanluongtrendh),0),sum(TonKho),sum(GiaoHang),sum(SoKhachHang_MuaHang),sum(SoKhachHang_PhatSinh) "+
							" FROM CHITIEUNPP_DDKD A INNER JOIN  "+
							" CHITIEUNPP CT ON CT.PK_SEQ=CHITIEUNPP_FK "+
							" INNER JOIN DDKD_GSBH B ON B.DDKD_FK= A.DDKD_FK "+
							" WHERE CT.THANG="+this.getThang()+" AND CT.NAM="+this.getNam()+" and CT.KENH_FK="+this.getKenhId()+"  AND CT.DVKD_FK="+this.getDVKDId()+
							" GROUP BY B.GSBH_FK "; 
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
			if(rs!=null) rs.close();

			sql=
					"	insert into CHITIEUSEC_MCP(CHITIEUSEC_FK,KHACHHANG_FK,DDKD_FK,NPP_FK,GSBH_FK) "+	
							"	SELECT '"+this.Id+"',b.KHACHHANG_FK,c.DDKD_FK,a.NPP_FK,d.GSBH_FK "+
							"	FROM KHACHHANG a inner join KHACHHANG_TUYENBH b on b.KHACHHANG_FK=a.PK_SEQ "+
							"		inner join TUYENBANHANG c on c.PK_SEQ=b.TBH_FK "+
							"		left join DDKD_GSBH d on d.DDKD_FK=c.DDKD_FK  "+
							"	group by b.KHACHHANG_FK,c.DDKD_FK,a.NPP_FK,d.GSBH_FK ";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			er.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean EditChiTieu_Sec() {

		try
		{
			if(!KiemTraHopLe())
			{
				return false;
			}
			db.getConnection().setAutoCommit(false);
			//kiem tra xenm chi tieu trong thoi gian nay da co chua
			String sql="select pk_seq from CHITIEU_SEC where thang= " +this.Thang+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" and trangthai != 2 and pk_seq <>'"+this.Id+"'";
			ResultSet rs_check=db.get(sql);
			{
				if(rs_check.next())
				{
					this.setMessage("Chi Tieu Secondary Trong Thang Da Thiet Lap cho cac nha phan phoi thuoc khu vuc nay, Vui Long Kiem Tra Lai");
					return false;
				}
			}
			sql="delete chitieunpp_ddkd where chitieunpp_fk in (select ctnpp.pk_seq from chitieunpp ctnpp inner join chitieu_sec ct on ct.thang=ctnpp.thang and ct.nam=ctnpp.nam and ct.dvkd_fk=ctnpp.dvkd_fk and ct.kenh_fk=ctnpp.kenh_fk where ct.pk_seq='"+this.Id+"' )";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql=" select nhapp_fk,trangthai from  chitieunpp where pk_seq in   (select ctnpp.pk_seq from chitieunpp ctnpp inner join chitieu_sec ct on ct.thang=ctnpp.thang and ct.nam=ctnpp.nam and ct.dvkd_fk=ctnpp.dvkd_fk and ct.kenh_fk=ctnpp.kenh_fk where ct.pk_seq='"+this.Id+"' )";
			System.out.println(sql);
			Hashtable<String,String> htb=new Hashtable<String, String>();

			ResultSet rsnpp_trangthai=db.get(sql);
			while (rsnpp_trangthai.next())
			{
				htb.put(rsnpp_trangthai.getString("nhapp_fk").trim(), rsnpp_trangthai.getString("trangthai"));
			}
			rsnpp_trangthai.close();

			sql="delete CHITIEUNPP_DDKD_NHOMSP where chitieunpp_fk in  (select ctnpp.pk_seq from chitieunpp ctnpp inner join chitieu_sec ct on ct.thang=ctnpp.thang and ct.nam=ctnpp.nam and ct.dvkd_fk=ctnpp.dvkd_fk and ct.kenh_fk=ctnpp.kenh_fk where ct.pk_seq='"+this.Id+"' )";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql=" delete chitieunpp where pk_seq in   (select ctnpp.pk_seq from chitieunpp ctnpp inner join chitieu_sec ct on ct.thang=ctnpp.thang and ct.nam=ctnpp.nam and ct.dvkd_fk=ctnpp.dvkd_fk and ct.kenh_fk=ctnpp.kenh_fk where ct.pk_seq='"+this.Id+"' )";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="delete CHITIEUSEC_GSBH where chitieusec_fk="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="delete CHITIEUSEC_GSBH_NHOMSP where chitieusec_fk="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="delete CHITIEUSEC_ASM where chitieusec_fk="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="delete CHITIEUSEC_ASM_NHOMSP where chitieusec_fk="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="delete CHITIEUSEC_NHAPP_NHOMSP where chitieusec_fk="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}
			//XOA CHI TIEU CAC NHA PHAN PHOI
			sql="delete chitieu_nhapp_sec where chitieu_sec_fk="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}
			String chuoi=this.getKhuVucID();
			sql="select ddkd.npp_fk,a.ma,a.sellsout,a.dophu,sanluongtrendh,a.donhang,SoKhachHang_MuaHang,SoKhachHang_PhatSinh,giaohang,tonkho"+chuoi+" from chitieutmp a inner join daidienkinhdoanh ddkd on ddkd.pk_seq=a.ma where chucvu='SR' order by npp_fk";
			System.out.println("---1 lay du lieu "+sql);
			ResultSet rs=db.get(sql);
			String npp_fk="";
			String idchitieunpp="";
			double chitieunpp=0;
			double sodonhang=0;
			float sanluongtrendh=0;
			double dosku=0;
			double tongchitieu=0;
			double SoKhachHang_MuaHang=0;
			double SoKhachHang_PhatSinh=0;
			double GiaoHang=0;
			double TonKho=0;

			//list danh sach cac chi tieu
			List<ChiTieuNPP>  ChitieuNppList = new ArrayList<ChiTieuNPP>();
			while (rs.next())
			{
				if(!npp_fk.equals(rs.getString("npp_fk")))
				{	
					if(!idchitieunpp.equals(""))
					{
						sql="update chitieunpp set chitieu="+ chitieunpp +" where pk_seq="+ idchitieunpp;
						if(!db.update(sql))
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.setMessage(sql);
							return false;
						}
						tongchitieu=tongchitieu+ chitieunpp;
						ChiTieuNPP npp_new = new ChiTieuNPP();	
						npp_new.setNhaPPId(npp_fk);
						npp_new.setSoDonHang(""+Math.round(sodonhang));
						npp_new.setSoKhachHang_MuaHang(""+Math.round(SoKhachHang_MuaHang));
						npp_new.setSoKhachHang_PhatSinh(""+Math.round(SoKhachHang_PhatSinh));
						npp_new.setSoSku(""+0);
						npp_new.setSoTien(chitieunpp);
						npp_new.setSanluongtrendh(""+sanluongtrendh);
						npp_new.setGiaoHang(GiaoHang+"");
						npp_new.setTonKho(TonKho+"");
						ChitieuNppList.add(npp_new);
						chitieunpp=0;
						sodonhang=0;
						sanluongtrendh=0;
						SoKhachHang_MuaHang=0;
						SoKhachHang_PhatSinh=0;
						GiaoHang=0;
						TonKho=0;
					}

					String tt=htb.get(rs.getString("npp_fk").trim())==null? "0" :htb.get(rs.getString("npp_fk").trim());

					sql="insert into chitieunpp (thang,nam,chitieu,nhapp_fk,ngaytao,nguoitao,ngaysua,nguoisua,trangthai,dvkd_fk,songaylamviec,kenh_fk,ngayketthuc,loaichitieu,diengiai)" +
							"values ('"+this.getThang()+"','"+this.getNam()+"','0','"+rs.getString("npp_fk")+"','"+this.getNgayTao()+"','"+this.getUserId()+"'," +
							"'"+this.NgaySua+"','"+this.getUserId()+"','"+tt+"','"+this.getDVKDId()+"','"+this.getSoNgayLamViec()+"','"+this.getKenhId()+"','"+this.NgayKetThuc+"','"+this.LoaiChiTieu+"','"+this.getDienGiai()+"' )";
					if(!db.update(sql))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.setMessage(sql);
						return false;
					}
					//thuc hien insert chi tiet
					String query = "select scope_identity() as Id";
					ResultSet rsDh = db.get(query);	
					try
					{
						rsDh.next();
						idchitieunpp=rsDh.getString("id");
					}catch(Exception ex)
					{
						ex.printStackTrace();
					}
					npp_fk=rs.getString("npp_fk");

				}
				//them vao chi tieu nhom san pham
				double chitieuddkd=0;

				String [] mang=this.ChuoiNhomSP.split(";");
				for(int k=0;k<mang.length;k++)
				{
					sql="insert into CHITIEUNPP_DDKD_NHOMSP values('"+idchitieunpp+"','"+rs.getString("ma")+"','"+mang[k]+"','"+rs.getDouble("manhom"+(k+1))+"')";
					chitieuddkd=chitieuddkd+ rs.getDouble("manhom"+(k+1));
					if(!db.update(sql))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.setMessage(sql);
						return false;
					}
				}
				sql="insert into chitieunpp_ddkd (chitieunpp_fk,ddkd_fk,CHITIEU,sodonhang,sku,dophu,sanluongtrendh,SoKhachHang_MuaHang,SoKhachHang_PhatSinh,GiaoHang,TonKho) " +
						" values("+idchitieunpp+","+rs.getString("ma")+",'"+chitieuddkd+"','"+rs.getInt("donhang")+"','0','"+rs.getInt("dophu")+"',"+rs.getFloat("sanluongtrendh")+","+rs.getFloat("SoKhachHang_MuaHang")+","+rs.getFloat("SoKhachHang_PhatSinh") +","+rs.getDouble("GiaoHang")+","+rs.getDouble("TonKho")+")";
				chitieunpp=chitieunpp+ chitieuddkd;
				sodonhang=sodonhang+ rs.getFloat("donhang");
				sanluongtrendh=sanluongtrendh+rs.getFloat("sanluongtrendh");
				SoKhachHang_MuaHang+=rs.getFloat("SoKhachHang_MuaHang");
				SoKhachHang_PhatSinh+=rs.getFloat("SoKhachHang_PhatSinh");

				GiaoHang += rs.getDouble("GiaoHang");
				TonKho += rs.getDouble("TonKho");
				if(!db.update(sql))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.setMessage(sql);
					return false;
				}
			}
			//cap nhat dong cuoi
			if(!idchitieunpp.equals(""))
			{
				sql="update chitieunpp set chitieu="+ chitieunpp +" where pk_seq="+ idchitieunpp;
				if(!db.update(sql))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.setMessage(sql);
					return false;
				}
				ChiTieuNPP npp_new = new ChiTieuNPP();	
				npp_new.setNhaPPId(npp_fk);
				npp_new.setSoDonHang(""+ Math.round(sodonhang));
				npp_new.setSoSku(""+0);
				npp_new.setSoTien(chitieunpp);
				npp_new.setSanluongtrendh(""+sanluongtrendh);
				npp_new.setSoKhachHang_MuaHang(""+Math.round(SoKhachHang_MuaHang));
				npp_new.setSoKhachHang_PhatSinh(""+Math.round(SoKhachHang_PhatSinh));
				npp_new.setGiaoHang(GiaoHang+"");
				npp_new.setTonKho(TonKho+"");
				tongchitieu=tongchitieu+ chitieunpp;
				ChitieuNppList.add(npp_new);
			}
			rs.close();
			this.setChitieu(tongchitieu);
			this.setListChiTieuNPP(ChitieuNppList);
			//////*******************************
			//update lai bang chitieu_trungtam;
			sql="update CHITIEU_SEC set LOAICHITIEU='"+this.LoaiChiTieu+"',THANG="+this.Thang+",NAM="+this.Nam+",CHITIEU="+this.ChiTieu+",NGAYKETTHUC='"+this.NgayKetThuc+
					"',NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',dvkd_fk="+this.Dvkd_id+",songaylamviec="+this.SoNgayLamViec+",diengiai=N'"+this.DienGiai+"' ,kenh_fk="+this.KenhId+" where pk_seq="+ this.Id;
			if(!db.update(sql))
			{
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}

			int count=0;
			if(this.listchitieunpp!=null)
			{
				while (count <this.listchitieunpp.size())
				{
					ChiTieuNPP khuvuc=new ChiTieuNPP();
					khuvuc=this.listchitieunpp.get(count);
					sql="insert into chitieu_nhapp_sec(CHITIEU_SEC_fk,nhapp_fk,chitieu,sodonhang,sku,sanluongtrendh,SoKhachHang_MuaHang,SoKhachHang_PhatSinh,GiaoHang,TonKho)   " +
							" values("+this.getID()+","+khuvuc.getNhaPPId()+","+khuvuc.getSoTien()+",'"+khuvuc.getSodonhang()+"','"+khuvuc.getsoSku()+"',"+khuvuc.getSanluongtrendh()+","+khuvuc.getSoKhachHang_MuaHang()+","+khuvuc.getSoKhachHang_PhatSinh()+","+khuvuc.getGiaoHang()+","+khuvuc.getTonKho()+")";
					System.out.println("Insert chi tieu NPP : "+sql);
					if(!db.update(sql))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
						return false;	
					}
					count++;
				}
			}

			sql=" insert into CHITIEUSEC_NHAPP_NHOMSP   " +
					" select "+this.Id+",nhapp_fk,nhomsanpham_fk,sum(b.chitieu) from chitieunpp CT  "+
					" inner join  CHITIEUNPP_DDKD_NHOMSP b on CT.pk_seq=b.chitieunpp_fk "+
					" where  CT.THANG="+this.getThang()+" AND CT.NAM="+this.getNam()+" and CT.KENH_FK="+this.getKenhId()+"  AND CT.DVKD_FK="+this.getDVKDId()+
					" group by 	 nhapp_fk,nhomsanpham_fk";	
			System.out.println("sqlSaveChiTieu : 681 Chitieuttchovung " +sql);
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}

			sql=" insert into CHITIEUSEC_GSBH_NHOMSP(ChiTieuSEC_FK,GSBH_FK,NHOMSANPHAM_FK,CHITIEU)   SELECT  "+this.Id+ ",B.GSBH_FK,A.NHOMSANPHAM_FK,SUM(A.CHITIEU) FROM CHITIEUNPP_DDKD_NHOMSP A INNER JOIN "+
					" CHITIEUNPP CT ON CT.PK_SEQ=CHITIEUNPP_FK "+
					" INNER JOIN DDKD_GSBH B ON B.DDKD_FK= A.DDKD_FK "+
					" WHERE CT.THANG="+this.getThang()+" AND CT.NAM="+this.getNam()+" and CT.KENH_FK="+this.getKenhId()+"  AND CT.DVKD_FK="+this.getDVKDId()+
					"  GROUP BY B.GSBH_FK,A.NHOMSANPHAM_FK";

			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				System.out.println("sqlSaveChiTieu : 681 Chitieuttchovung " +sql);
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}

			sql="INSERT INTO CHITIEUSEC_GSBH (chitieusec_fk,gsbh_fk,dophu,donhang,chitieu,sku,sanluongtrendh,SoKhachHang_MuaHang,SoKhachHang_PhatSinh,TonKho,GiaoHang) "+
					" SELECT "+this.Id+",B.GSBH_FK ,ISNULL(SUM(DOPHU),0),ISNULL( SUM(SODONHANG),0),ISNULL(SUM(A.CHITIEU),0),ISNULL(SUM(SKU),0),ISNULL(SUM(sanluongtrendh),0),Sum(SoKhachHang_MuaHang),sum(SoKhachHang_PhatSinh),sum(TonKho),sum(GiaoHang) "+
					" FROM CHITIEUNPP_DDKD A INNER JOIN  "+
					" CHITIEUNPP CT ON CT.PK_SEQ=CHITIEUNPP_FK "+
					" INNER JOIN DDKD_GSBH B ON B.DDKD_FK= A.DDKD_FK "+
					" WHERE CT.THANG="+this.getThang()+" AND CT.NAM="+this.getNam()+" and CT.KENH_FK="+this.getKenhId()+"  AND CT.DVKD_FK="+this.getDVKDId()+
					" GROUP BY B.GSBH_FK ";

			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;				
			}
			//update lai tong chi tieu cua npp
			sql="update CHITIEU_SEC  set CHITIEU=isnull((select sum(chitieu) from chitieu_nhapp_sec a where a.CHITIEU_SEC_fk=pk_seq),0) where pk_Seq ="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}
			sql="UPDATE CHITIEUNPP SET CHITIEU= isnull((SELECT SUM(CHITIEU) FROM CHITIEUNPP_DDKD_NHOMSP WHERE CHITIEUNPP.PK_SEQ=CHITIEUNPP_FK),0) ";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}

			sql="delete CHITIEUSEC_MCP where CHITIEUSEC_FK='"+this.Id+"' ";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}

			sql=
					"	insert into CHITIEUSEC_MCP(CHITIEUSEC_FK,KHACHHANG_FK,DDKD_FK,NPP_FK,GSBH_FK) "+	
							"	SELECT '"+this.Id+"',b.KHACHHANG_FK,c.DDKD_FK,a.NPP_FK,d.GSBH_FK "+
							"	FROM KHACHHANG a inner join KHACHHANG_TUYENBH b on b.KHACHHANG_FK=a.PK_SEQ "+
							"		inner join TUYENBANHANG c on c.PK_SEQ=b.TBH_FK "+
							"		left join DDKD_GSBH d on d.DDKD_FK=c.DDKD_FK  "+
							"	group by b.KHACHHANG_FK,c.DDKD_FK,a.NPP_FK,d.GSBH_FK ";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}		
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er)
		{
			er.printStackTrace();
			this.setMessage("Vui Long Kiem Tra Lai.Loi :"+er.toString());
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}

	public boolean DeleteChitieu_Sec() {


		try{



			db.getConnection().setAutoCommit(false);

			String sql="select thang,nam,kenh_fk,dvkd_fk from chitieu_sec where pk_seq="+ this.Id;
			ResultSet rs=this.db.get(sql);
			if(rs!=null){
				if(rs.next()){
					this.Thang=rs.getString("thang");
					this.Nam=rs.getString("nam");
					this.Dvkd_id=rs.getString("dvkd_fk");
					this.KenhId=rs.getString("kenh_fk");
				}
			}
			/// cap nhat lai chi tieu cua npp**************
			sql="delete chitieunpp_ddkd where chitieunpp_fk in (select pk_seq from chitieunpp where thang='"+this.Thang+"' and nam='"+this.Nam+"' and dvkd_fk='"+this.Dvkd_id+"' and kenh_fk='"+this.KenhId+"')";
			//System.out.println(sql);
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}


			sql="delete CHITIEUNPP_DDKD_NHOMSP where chitieunpp_fk in (select pk_seq from chitieunpp where thang='"+this.Thang+"' and nam='"+this.Nam+"' and dvkd_fk='"+this.Dvkd_id+"' and kenh_fk='"+this.KenhId+"')";
			//System.out.println(sql);
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql=" delete chitieunpp where thang='"+this.Thang+"' and nam='"+this.Nam+"' and dvkd_fk='"+this.Dvkd_id+"' and kenh_fk='"+this.KenhId+"'";
			//System.out.println(sql);
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="delete CHITIEUSEC_GSBH where chitieusec_fk="+this.Id;
			//System.out.println(sql);
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}
			sql="delete CHITIEUSEC_GSBH_NHOMSP where chitieusec_fk="+this.Id;
			//System.out.println(sql);
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="delete CHITIEUSEC_ASM where chitieusec_fk="+this.Id;
			// //System.out.println(sql);
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}
			sql="delete CHITIEUSEC_ASM_NHOMSP where chitieusec_fk="+this.Id;
			//System.out.println(sql);
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			//XOA CHI TIEU CAC NHA PHAN PHOI



			sql="delete chitieu_nhapp_sec where chitieu_sec_fk="+this.Id;
			// //System.out.println(sql);
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="delete CHITIEUSEC_NHAPP_NHOMSP where chitieusec_fk="+this.Id;
			////System.out.println(sql);
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}
			sql="delete CHITIEU_SEC  where pk_seq="+ this.Id;
			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;
			}
			sql="delete CHITIEU_NHANVIEN  where chitieusec_fk= "+this.Id;
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the xoa chi tieu moi nay, loi : " + er.toString());
			return false;
		}
		return false;
	}

	public void setSoNgayLamViec(String songaylamviec) {

		this.SoNgayLamViec=songaylamviec;
	}

	public String getSoNgayLamViec() {

		return this.SoNgayLamViec;
	}

	public String getKenhId() {

		return this.KenhId;
	}

	public void setKenhId(String kenhid) {

		this.KenhId=kenhid;
	}

	public ResultSet getRsKenh() {

		return this.rsKenh;

	}

	public String getTenKenh() {

		String sql="select pk_seq,ten from kenhbanhang where pk_seq="+this.KenhId;

		ResultSet rs=db.get(sql);
		if(rs!=null){
			try{
				if(rs.next()){
					String ten= rs.getString("ten");
					rs.close();
					return ten;

				}
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}

	public void setTrangThai(String trangthai) {

		this.TrangThai=trangthai;
	}

	public String getTrangThai() {

		return this.TrangThai;
	}

	public boolean ChotChiTieu() {


		try{
			db.getConnection().setAutoCommit(false);
			String sqlchot="update CHITIEU set trangthai='1' where pk_seq="+ this.Id;
			if(!db.update(sqlchot)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}

	public boolean ChotChiTieu_Sec() {


		//System.out.println("slq chot  : "+sqlchot);

		try{
			db.getConnection().setAutoCommit(false);
			String sql="update CHITIEU_SEC set trangthai='1' where pk_seq="+ this.Id;
			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			//chot trang thai duoi nha phan phoi de nha phan phoi bat dau sua cap nhat cho npp sua là 3
			sql="update b set b.trangthai=1 "+
					" from chitieunpp b inner join chitieu_sec a on "+
					" a.thang=b.thang and a.nam=b.nam and a.dvkd_fk=b.dvkd_fk and a.kenh_fk=b.kenh_fk "+
					" where a.pk_seq=" + this.Id;
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;

	}

	public void closeDB() {

		try{
			this.rsChiTieuNV.close();
			if(this.db!=null){
				db.shutDown();
			}
		}catch(Exception er){

		}
	}

	public boolean UnChotChiTieu_Sec() {




		try{
			db.getConnection().setAutoCommit(false);
			String sql="update CHITIEU_SEC set trangthai='0' where pk_seq="+ this.Id;
			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}



			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}

	public ResultSet getRsChitieunhanvien() {

		return this.rsChiTieuNV;
	}

	public String[] getNhomSp() {

		return this.NhomSP;
	}

	public String getChuoiNhomSp() {

		return this.ChuoiNhomSP;
	}

	public void setChuoiNhomSp(String chuoinhomsp) {

		this.ChuoiNhomSP=chuoinhomsp;
	}

	public void setRsPri(String sql) {

		//Utility Ult=new Utility();	
		if(sql.equals("")){
			sql = "SELECT   C.PK_SEQ,c.trangthai,kbh.ten as kenh, C.THANG, C.NAM,  C.CHITIEU,C.DIENGIAI, C.NGAYKETTHUC,C.DVKD_FK,D.donvikinhdoanh,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
					"NS.TEN AS NGUOISUA FROM dbo.CHITIEU AS C INNER JOIN "+
					"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ" +
					" inner join DONVIKINHDOANH D on D.pk_seq=C.DVKD_FK " +
					" inner  join kenhbanhang kbh on kbh.pk_seq=c.kenh_fk " ;
		}
		System.out.println("Lay Chi Tieu : "+sql);
		this.rsChitieuPri=this.db.get(sql);
	}

	public ResultSet rsChitieuPri() {

		return this.rsChitieuPri;
	}

	public void DbClose() {

		try
		{
			this.db.shutDown();
			if(rsChiTieuNV!=null)rsChiTieuNV.close();
			if(rsChitieuPri!=null) rsChitieuPri.close();
			if(kyRs!=null)this.kyRs.close();
			if(quyRs!=null)this.quyRs.close();
		}catch(Exception er)
		{
				er.printStackTrace();
		}finally 
		{
			this.db.shutDown();
		}

	}

	public ResultSet getRsDvdk() {

		return this.RsDVKD;
	}

	public void CreateRs() 
	{
		String sql="select pk_Seq,ten from  kenhbanhang where trangthai='1' ";
		this.rsKenh=  db.get(sql);

		sql="select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai=1";
		this.RsDVKD=db.get(sql);
		
		sql=" select TLNTC_FK,TenKy,Thang,Nam,TuNgay,DenNgay from Ky where TLNTC_FK='"+this.getNam()+"' ";
		this.kyRs=this.db.get(sql);
		
		sql ="select TLNTC_FK,Ten,Quy,Nam,TuNgay,DenNgay from Quy where TLNTC_FK="+this.getNam()+"";
		this.quyRs = this.db.get(sql);
	}

	public ResultSet rs_chitieuprinpp() {

		return rsChitieuPriNpp;
	}
	
	public ResultSet rs_chitieupriss() {

		return rsChitieuPriSS;
	}
	
	public ResultSet rs_chitieupriasm() {

		return rsChitieuPriASM;
	}
	
	public ResultSet rs_chitieuprirsm() {

		return rsChitieuPriRSM;
	}

	public boolean DeleteChitieuSkuin() {

		try{
			db.getConnection().setAutoCommit(false);
			String sql="delete chitieu_nhapp_nhomsp where chitieu_fk ="+ this.Id;
			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			
			sql="delete CHITIEU_GSBH_NHOMSP where chitieu_fk="+this.Id;

			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			sql="delete chitieu_nhapp where chitieu_fk="+this.Id;
			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			sql="delete chitieu where pk_seq="+this.Id;
			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			db.getConnection().commit();

			db.getConnection().setAutoCommit(true);


		}catch(Exception er){
			return false;
		}

		return false;
	}

	public boolean ChotChitieuSkuin() {
		try{
			db.getConnection().setAutoCommit(false);
			String sql="delete chitieu_nhapp where chitieu_fk="+this.Id;
			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			sql="update chitieu set trangthai='1' where pk_seq="+this.Id;
			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}


			sql="insert into chitieu_nhapp select "+this.Id+",npp_fk,sum(chitieu) from chitieu_nhapp_nhomsp  " +
					" where chitieu_fk="+this.Id+"  group by npp_fk  ";
			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}

			sql="delete CHITIEU_GSBH_NHOMSP where chitieu_fk="+this.Id;
			//System.out.println(sql);
			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			///update target salesup
			sql="INSERT INTO CHITIEU_GSBH_NHOMSP "+
					" select "+this.Id+", c.gsbh_fk,nhomsp_fk , sum(b.chitieu) as chitieu "+ 
					" from chitieu ct inner join  "+
					" CHITIEU_NHAPP_NHOMSP  b on ct.pk_seq=b.chitieu_fk  "+
					" inner join nhapp_giamsatbh   c  on b.npp_fk=c.npp_fk "+ 
					" WHERE  CT.PK_SEQ= "+ this.Id + " AND c.NGAYKETTHUC >='"+getDateTime()+"' AND c.NGAYBATDAU <= '" + getDateTime().substring(0,7)+ "-01'"+
					" group by   ct.kenh_fk,ct.dvkd_fk, c.gsbh_fk,nhomsp_fk ";

			//System.out.println(sql);

			if(!db.update(sql)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}


			db.getConnection().commit();

			db.getConnection().setAutoCommit(true);


		}catch(Exception er){
			return false;
		}

		return false;
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public boolean UnChotChitieuSkuin() {

		try{
			db.getConnection().setAutoCommit(false);
			String sql="delete chitieu_nhapp where chitieu_fk="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			sql="update chitieu set trangthai='0' where pk_seq="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er)
		{
			er.printStackTrace();
			return false;
		}

		return false;
	}

	public void setLoaiChiTieu(String loaichitieu) {

		this.LoaiChiTieu=loaichitieu;
	}

	public String getLoaiChiTieu() {

		return this.LoaiChiTieu;
	}

	public String GetTumuc() {

		return this.Tumuc;
	}

	public void SetTumuc(String tumuc) {

		this.Tumuc=tumuc;
	}

	public String GetToimuc() {

		return this.ToiMuc;
	}

	public void SetToimuc(String toimuc) {

		this.ToiMuc=toimuc;
	}

	public ResultSet getRsNppNhomSp() {

		return RsNppNhomSp;
	}

	@Override
	public boolean EditChiTieu(HttpServletRequest request) 
	{
		try
		{
			if(!KiemTraHopLe())
			{
				return false;
			}
			db.getConnection().setAutoCommit(false);
			String sql="select pk_seq from ChiTieu where thang= " +this.Thang+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" and trangthai!=2 and pk_seq <>" +this.getID();


			ResultSet rs_check=db.get(sql);
			if(rs_check!=null)
			{
				if(rs_check.next())
				{
					this.setMessage("Chi Tieu Trong Thang Da Thiet Lap cho cac nha phan phoi thuoc khu vuc nay, Vui Long Kiem Tra Lai");
					return false;
				}
				rs_check.close();
			}

			sql=
					"	select distinct nhomsp_fk as nspId,b.TEN  as nspTen from CHITIEU_NHAPP_NHOMSP  a "+
							"		inner join NHOMSANPHAM  b on b.PK_SEQ=a.NHOMSP_FK "+
							"	 where  CHITIEU_FK='"+this.Id+"' ";  
			System.out.println("[NhomSanPham]"+sql);
			ResultSet rs=this.db.get(sql);
			String[] nhom =new String[10];
			String[] nhomid =new String[10];

			int i=0;
			String strselect="";
			String strngoac="[0]";

			if(rs!=null)
			{
				while (rs.next())
				{
					nhomid[i]=rs.getString("nspId");
					nhom[i]=rs.getString("nspTen");
					if(i==0)
					{
						strselect=" ,["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
						strngoac=" [0], ["+rs.getString("nspId")+"]";
					}else
					{
						strselect=strselect+", ["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
						strngoac=strngoac+ ",["+rs.getString("nspId")+"]";
					}
					i++;
				}
				rs.close();
			}
			this.NhomSP=nhom;
			this.NhomSPId=nhomid;

			sql="update CHITIEU set ApDung="+this.apdung+",Quy="+(this.quy.length()<=0?"NULL":this.quy)+",THANG="+(this.Thang.length()<=0?"NULL":this.Thang)+",NAM="+this.Nam+",CHITIEU="+this.ChiTieu+",NGAYKETTHUC='"+this.NgayKetThuc+
					"',NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',DIENGIAI='"+this.DienGiai+"' ,dvkd_fk="+this.Dvkd_id+" ,songaylamviec="+this.SoNgayLamViec+" ,kenh_fk="+this.KenhId+"  where pk_seq="+ this.Id;
			
			System.out.println("[CHITIEU]"+sql);
			
			if(!db.update(sql))
			{
				this.Message="Loi  : "+ sql;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}

			sql="delete CHITIEU_NHAPP_NHOMSP where chitieu_fk= "+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}

			sql="delete CHITIEU_NHAPP where chitieu_fk= "+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}
			i=0;
			String[] nppId= request.getParameterValues("nppId");
			if(nppId!=null)
			{
				int soNpp= nppId.length;
				while(i<soNpp)
				{
					for(int k=0;k<NhomSPId.length;k++)
					{
						if(NhomSPId[k]!=null)
						{
							double 	chitieu=0;
							String[] mang= request.getParameterValues("ctNhom"+ NhomSPId[k]);
							try
							{
								chitieu=Double.parseDouble( mang[i].replace(",",""));
							}catch(Exception er)
							{
								er.printStackTrace();
								geso.dms.center.util.Utility.rollback_throw_exception(db);
								this.setMessage("Vui lòng kiểm tra lại thông tin chỉ tiêu nhóm sản phẩm");
								return false;
							}
							if(chitieu>0)
							{
								sql="INSERT INTO CHITIEU_NHAPP_NHOMSP(CHITIEU_FK,NPP_FK,NHOMSP_FK,CHITIEU) " +
										" values('"+this.Id+"','"+nppId[i]+"','"+NhomSPId[k]+"',"+chitieu+")";
								if(!db.update(sql))
								{
									geso.dms.center.util.Utility.rollback_throw_exception(db);
									this.setMessage(sql);
									return false;
								}	 
							}
						}
					}
					i++;
				}
			}
			sql=" insert into chitieu_nhapp(chitieu_fk,nhapp_fk,chitieu ) select  chitieu_fk,npp_fk,sum(chitieu) "+
					" from CHITIEU_NHAPP_NHOMSP where chitieu_fk="+this.Id+" group by chitieu_fk,npp_fk ";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}
			sql=
					" update ct set CHITIEU=ctNpp.ChiTieu "+
							" from "+
							"	chitieu ct inner join "+ 
							"( "+
							"	select SUM(chitieu) as ChiTieu,CHITIEU_FK  from chitieu_nhapp ctNpp "+
							"	group by CHITIEU_FK "+
							" )as ctNpp on ctNpp.CHITIEU_FK=ct.PK_SEQ" +
							" where ctNpp.CHITIEU_FK='"+this.Id+"'  ";

			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(sql);
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.Message="Khong the Chinh Sua Chi Tieu :"+ er.toString();
			er.printStackTrace();
			return false;			
		}
		return true;
	}

	@Override
	public boolean SaveChiTieu_Sec(HttpServletRequest request) 
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			String sql="delete from chitieutmp";
			if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
			if(!KiemTraHopLe())
			{
				return false;
			}
			sql=
					"	select nhomsp_fk as nspId,b.TEN  as nspTen from chitieusec_nhapp_nhomsp  a "+
							"		inner join NHOMSANPHAM  b on b.PK_SEQ=a.NHOMSP_FK "+
							"	 where  CHITIEUSEC_FK='"+this.Id+"' "+  
							"	 union   "+
							"	 select distinct nhomsanpham_fk as nspId ,c.TEN as nspTen "+
							"	 from chitieunpp_ddkd_nhomsp a inner join CHITIEUNPP b on b.PK_SEQ=a.CHITIEUNPP_FK "+  
							"		inner join NHOMSANPHAM  c on a.NHOMSANPHAM_FK=c.PK_SEQ "+
							"	 where b.THANG='"+this.Thang+"' and b.NAM='"+this.Nam+"' and b.KENH_FK='"+this.KenhId+"' and b.DVKD_FK='"+this.Dvkd_id+"' "; 

			System.out.println("[NhomSanPham]"+sql);
			ResultSet rs=this.db.get(sql);
			String[] nhom =new String[10];
			String[] nhomid =new String[10];

			int i=0;
			String strselect="";
			String strngoac="[0]";

			if(rs!=null)
			{
				while (rs.next())
				{
					nhomid[i]=rs.getString("nspId");
					nhom[i]=rs.getString("nspTen");
					if(i==0)
					{
						strselect=" ,["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
						strngoac=" [0], ["+rs.getString("nspId")+"]";
					}else
					{
						strselect=strselect+", ["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
						strngoac=strngoac+ ",["+rs.getString("nspId")+"]";
					}
					i++;
				}
				rs.close();
			}
			this.NhomSP=nhom;
			this.NhomSPId=nhomid;

			sql="update CHITIEU_SEC set ApDung="+this.apdung+",Quy="+(this.quy.length()<=0?"NULL":this.quy)+",THANG="+(this.Thang.length()<=0?"NULL":this.Thang)+",NAM="+this.Nam+",CHITIEU="+this.ChiTieu+",NGAYKETTHUC='"+this.NgayKetThuc+
					"',NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',dvkd_fk="+this.Dvkd_id+",songaylamviec="+this.SoNgayLamViec+",diengiai=N'"+this.DienGiai+"' ,kenh_fk="+this.KenhId+" where pk_seq="+ this.Id;
			if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
			sql="delete chitieunpp_ddkd where chitieunpp_fk in (select ctnpp.pk_seq from chitieunpp ctnpp inner join chitieu_sec ct on ct.thang=ctnpp.thang and ct.nam=ctnpp.nam and ct.dvkd_fk=ctnpp.dvkd_fk and ct.kenh_fk=ctnpp.kenh_fk where ct.pk_seq='"+this.Id+"' )";
			if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
			sql="delete CHITIEUNPP_DDKD_NHOMSP where chitieunpp_fk in  (select ctnpp.pk_seq from chitieunpp ctnpp inner join chitieu_sec ct on ct.thang=ctnpp.thang and ct.nam=ctnpp.nam and ct.dvkd_fk=ctnpp.dvkd_fk and ct.kenh_fk=ctnpp.kenh_fk where ct.pk_seq='"+this.Id+"' )";

			if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}

			sql="delete CHITIEUSEC_GSBH where chitieusec_fk="+this.Id;
			if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}

			sql="delete CHITIEUSEC_GSBH_NHOMSP where chitieusec_fk="+this.Id;
			if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}

			sql="delete CHITIEUSEC_ASM where chitieusec_fk="+this.Id;
			if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}

			sql="delete CHITIEUSEC_ASM_NHOMSP where chitieusec_fk="+this.Id;
			if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}

			sql="delete CHITIEUSEC_NHAPP_NHOMSP where chitieusec_fk="+this.Id;
			if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}

			sql="delete CHITIEU_NHAPP_SEC where CHITIEU_SEC_FK='"+this.Id+"'";
			if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
			String[] nppId = request.getParameterValues("nppId");
			String[] ctSoDonHang = request.getParameterValues("ctSoDonHang");
			String[] ctSanLuong = request.getParameterValues("ctSanLuong");
			String[] ctSalesOut = request.getParameterValues("ctSalesOut");
			String[] ctSoKhachHang_MuaHang = request.getParameterValues("ctSoKhachHang_MuaHang");
			String[] ctSoKhachHang_PhatSinh = request.getParameterValues("ctSoKhachHang_PhatSinh");
			String[] ctGiaoHang = request.getParameterValues("ctGiaoHang");
			String[] ctTonKho = request.getParameterValues("ctTonKho");
			i=0;
			while (nppId!=null&&i< nppId.length )
			{

				double	ct_SoDonHang=0;
				double	ct_SanLuong=0;
				double	ct_SalesOut=0;
				double	ct_SoKhachHang_MuaHang=0;
				double	ct_SoKhachHang_PhatSinh=0;
				try
				{
					ct_SalesOut=Double.parseDouble(ctSalesOut[i].replace(",",""));
					ct_SoDonHang=Double.parseDouble(ctSoDonHang[i].replace(",",""));
					ct_SanLuong=Double.parseDouble(ctSanLuong[i].replace(",",""));

					ct_SoKhachHang_MuaHang=Double.parseDouble(ctSoKhachHang_MuaHang[i].replace(",",""));
					ct_SoKhachHang_PhatSinh=Double.parseDouble(ctSoKhachHang_PhatSinh[i].replace(",",""));

				}catch(Exception er)
				{
					er.printStackTrace();
				}
				if(ct_SalesOut>0||ct_SoDonHang>0||ct_SanLuong>0||ct_SoKhachHang_MuaHang>0||ct_SoKhachHang_PhatSinh>0)
				{
					sql=
							"INSERT INTO CHITIEU_NHAPP_SEC(CHITIEU_SEC_FK,NHAPP_FK,CHITIEU,SODONHANG,SANLUONGTRENDH,SoKhachHang_MuaHang,SoKhachHang_PhatSinh,GiaoHang,TonKho)  " +
									" values("+this.Id+","+nppId[i]+","+ ct_SalesOut+",'"+ct_SoDonHang+"','"+ct_SanLuong+"',"+ct_SoKhachHang_MuaHang+","+ct_SoKhachHang_PhatSinh+","+ctGiaoHang[i]+","+ctTonKho[i]+")";
					System.out.println("[Dong]"+i+"[nppId]"+nppId[i]+"[Insert]"+sql);
					if(!this.db.update(sql))
					{
						this.db.getConnection().rollback();
						this.Message= "Khong the them chi tieu chi tieu :" +sql;
						return false;
					}
				}
				if(this.NhomSPId!=null && this.NhomSPId.length>0)
				{
					for(int k=0;k<NhomSPId.length;k++)
					{
						if(NhomSPId[k]!=null)
						{
							double  ctNhom=0;
							String[] mang= request.getParameterValues("ctNppNspId_"+ NhomSPId[k]);
							try
							{
								ctNhom=Double.parseDouble( mang[i].replace(",",""));
							}catch(Exception er){er.printStackTrace();}

							if(ctNhom>0)
							{
								sql="insert into CHITIEUSEC_NHAPP_NHOMSP (CHITIEUSEC_FK,NPP_FK,NHOMSP_FK,CHITIEU)  " +
										" values('"+this.Id+"','"+ nppId[i]+"','"+NhomSPId[k]+"',"+ctNhom+")";
								if(!db.update(sql))
								{
									this.db.getConnection().rollback();
									this.setMessage(sql);
									return false;
								}	 
							}
						}
					}
				}
				i++;
			}
			String[] ddkd_NppId = request.getParameterValues("ddkd_NppId");
			String[] ddkdId = request.getParameterValues("ddkdId");
			String[] ctSoDonHang_Ddkd = request.getParameterValues("ctSoDonHang_Ddkd");
			String[] ctSanLuong_Ddkd = request.getParameterValues("ctSanLuong_Ddkd");
			String[] ctSalesOut_Ddkd = request.getParameterValues("ctSalesOut_Ddkd");
			String[] ctSoKhachHang_MuaHang_Ddkd = request.getParameterValues("ctSoKhachHang_MuaHang_Ddkd");
			String[] ctSoKhachHang_PhatSinh_Ddkd = request.getParameterValues("ctSoKhachHang_PhatSinh_Ddkd");
			String[] ctGiaoHang_Ddkd = request.getParameterValues("ctGiaoHang_Ddkd");
			String[] ctTonKho_Ddkd = request.getParameterValues("ctTonKho_Ddkd");

			String nppid_bk="";
			String ctid="";
			i=0;
			if(ddkd_NppId!=null)
			{	
				while(i<  ddkdId.length)
				{
					if(!ddkd_NppId[i].trim().equals(nppid_bk))
					{
						nppid_bk=ddkd_NppId[i].trim();
						ctid=getCtidNpp(this.db,nppid_bk);
						if(ctid.equals(""))
						{
							this.db.getConnection().rollback();
							this.setMessage("không tạo được chỉ tiêu dưới NPP :" + nppid_bk);
							return false;
						}
						sql="delete CHITIEUNPP_DDKD where CHITIEUNPP_FK="+ctid+" " ;
						if(!db.update(sql))
						{
							this.db.getConnection().rollback();
							this.setMessage(sql);
							return false;
						}
						sql="delete CHITIEUNPP_DDKD_NHOMSP where CHITIEUNPP_FK="+ctid+" " ;
						if(!db.update(sql))
						{
							this.db.getConnection().rollback();
							this.setMessage(sql);
							return false;
						}

						System.out.println("[IdCtNpp]"+ctid);
					}

					double	ct_SoDonHang=0;
					double	ct_SanLuong=0;
					double	ct_SalesOut=0;
					double	ct_SoKhachHang_MuaHang=0;
					double	ct_SoKhachHang_PhatSinh=0;
					try
					{
						ct_SalesOut=Double.parseDouble(ctSalesOut_Ddkd[i].replace(",",""));
						ct_SoDonHang=Double.parseDouble(ctSoDonHang_Ddkd[i].replace(",",""));
						ct_SanLuong=Double.parseDouble(ctSanLuong_Ddkd[i].replace(",",""));

						ct_SoKhachHang_MuaHang=Double.parseDouble(ctSoKhachHang_MuaHang_Ddkd[i].replace(",",""));
						ct_SoKhachHang_PhatSinh=Double.parseDouble(ctSoKhachHang_PhatSinh_Ddkd[i].replace(",",""));

					}catch(Exception er){er.printStackTrace();}
					if(ct_SalesOut>0||ct_SoDonHang>0||ct_SanLuong>0||ct_SoKhachHang_MuaHang>0||ct_SoKhachHang_PhatSinh>0)
					{
						sql="INSERT INTO CHITIEUNPP_DDKD(CHITIEUNPP_FK,DDKD_FK,CHITIEU,SODONHANG,SANLUONGTRENDH,SoKhachHang_MuaHang,SoKhachHang_PhatSinh,GiaoHang,TonKho)" +
								" values ("+ctid+","+ddkdId[i]+","+ct_SalesOut+",'"+ct_SoDonHang+"','"+ct_SanLuong+"',"+ct_SoKhachHang_MuaHang+","+ct_SoKhachHang_PhatSinh+","+ctGiaoHang_Ddkd[i]+","+ctTonKho_Ddkd[i]+")";
						System.out.println("[Dong]"+i+"[DDKD_FK]"+ddkdId[i]+"[Insert]"+sql);
						if(!db.update(sql))
						{
							this.db.getConnection().rollback();
							this.setMessage(sql);
							return false;
						}
					}					

					for(int k=0;k<NhomSPId.length;k++)
					{
						if(NhomSPId[k]!=null)
						{
							double chitieu=0;
							String[] mang= request.getParameterValues("ctDdkdNspId_"+ NhomSPId[k]);
							try
							{
								chitieu=Double.parseDouble( mang[i].replace(",",""));
							}catch(Exception er)
							{
								er.printStackTrace();
							}
							if(chitieu>0)
							{
								sql="INSERT INTO CHITIEUNPP_DDKD_NHOMSP (CHITIEUNPP_FK,DDKD_FK,NHOMSANPHAM_FK,CHITIEU)  " +
										" values('"+ctid+"','"+ ddkdId[i]+"','"+NhomSPId[k]+"',"+chitieu+")";
								System.out.println("[Dong]"+i+"[DDKD_FK]"+ddkdId[i]+"[Insert]"+sql);
								if(!db.update(sql))
								{
									this.db.getConnection().rollback();
									this.setMessage(sql);
									return false;
								}	 
							}
						}
					}
					i++;
				}
			}
			sql=
					"update c set CHITIEU=a.CHITIEU "+
							"	from  chitieu_nhapp_sec a inner join CHITIEU_SEC b on b.PK_SEQ=a.CHITIEU_SEC_FK "+
							"	inner join CHITIEUNPP c on c.THANG=b.THANG and c.NAM=b.NAM "+
							"	and c.DVKD_FK=b.DVKD_FK and c.KENH_FK=b.KENH_FK and c.NHAPP_FK=a.NHAPP_FK " +
							" where a.CHITIEU_SEC_FK='"+this.Id+"' ";
			if(!db.update(sql))
			{
				this.db.getConnection().rollback();
				this.setMessage("__Lỗi cập nhật__"+sql);
				return false;
			}	
			sql=" insert into CHITIEUSEC_GSBH_NHOMSP(ChiTieuSEC_FK,GSBH_FK,NHOMSANPHAM_FK,CHITIEU)   SELECT  "+this.Id+ ",B.GSBH_FK,A.NHOMSANPHAM_FK,SUM(A.CHITIEU) FROM CHITIEUNPP_DDKD_NHOMSP A INNER JOIN "+
					" CHITIEUNPP CT ON CT.PK_SEQ=CHITIEUNPP_FK "+
					" INNER JOIN DDKD_GSBH B ON B.DDKD_FK= A.DDKD_FK "+
					" WHERE CT.THANG="+this.getThang()+" AND CT.NAM="+this.getNam()+" and CT.KENH_FK="+this.getKenhId()+"  AND CT.DVKD_FK="+this.getDVKDId()+
					"  GROUP BY B.GSBH_FK,A.NHOMSANPHAM_FK";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				System.out.println("sqlSaveChiTieu : 681 Chitieuttchovung " +sql);
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
			sql=
					"INSERT INTO CHITIEUSEC_GSBH (chitieusec_fk,gsbh_fk,dophu,donhang,chitieu,sku,sanluongtrendh,GiaoHang,TonKho ) "+
							" SELECT "+this.Id+",B.GSBH_FK ,ISNULL(SUM(DOPHU),0),ISNULL( SUM(SODONHANG),0),ISNULL(SUM(A.CHITIEU),0),ISNULL(SUM(SKU),0),ISNULL(SUM(sanluongtrendh),0),sum(GiaoHang),sum(TonKho) "+
							" FROM CHITIEUNPP_DDKD A INNER JOIN  "+
							" CHITIEUNPP CT ON CT.PK_SEQ=CHITIEUNPP_FK "+
							" INNER JOIN DDKD_GSBH B ON B.DDKD_FK= A.DDKD_FK "+
							" WHERE CT.THANG="+this.getThang()+" AND CT.NAM="+this.getNam()+" and CT.KENH_FK="+this.getKenhId()+"  AND CT.DVKD_FK="+this.getDVKDId()+
							" GROUP BY B.GSBH_FK "; 
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
			sql="update CHITIEU_SEC  set CHITIEU=isnull((select sum(chitieu) from chitieu_nhapp_sec a where a.CHITIEU_SEC_fk=pk_seq),0) where pk_Seq ="+this.Id;
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			this.setMessage("Exception__"+e.getMessage());
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		System.out.println("End_________");
		return true;
	}

	private String getCtidNpp(dbutils db2,String Nppid) 
	{
		try
		{
			String chitieunppid="";
			String  sql=" select pk_seq from  chitieunpp where thang="+this.Thang+" and nam="+this.Nam + 
					" and dvkd_fk= "+this.Dvkd_id +"  and  nhapp_fk ="+Nppid+" and kenh_fk ="+this.KenhId;
			ResultSet rsce=db2.get(sql);
			if(rsce.next())
			{
				chitieunppid=rsce.getString("pk_seq");
			}else
			{
				sql=" insert into chitieunpp (thang,nam,chitieu,nhapp_fk,ngaytao,nguoitao,ngaysua,nguoisua,trangthai,dvkd_fk,songaylamviec,kenh_fk,ngayketthuc,diengiai)" +
						" values ('"+this.Thang+"','"+this.Nam+"','"+0+"','"+Nppid+"','"+this.getDateTime()+"','"+this.getUserId()+"'," +
						"'"+this.getDateTime()+"','"+this.getUserId()+"','0','"+this.Dvkd_id +"','"+this.SoNgayLamViec+"','"+this.KenhId+"' ,'"+this.NgayKetThuc+"',N'"+this.DienGiai+"')";
				if(!db2.update(sql))
				{
					this.setMessage(sql);
					return "";
				}
				String query = "select IDENT_CURRENT('chitieunpp') as dhId";
				ResultSet rsDh = db2.get(query);	
				try
				{
					rsDh.next();
					chitieunppid= rsDh.getString("dhId");
					rsDh.close();
				}catch(Exception ex)
				{
					this.Message=ex.toString();
					ex.printStackTrace();
					return "";
				}
			}
			rsce.close();
			return chitieunppid;
		}catch(Exception err)
		{
			err.printStackTrace();
			return "";
		}
	}

	@Override
	public String[] getNhomSpId() {

		return this.NhomSPId;
	}


	String apdung,quy;
	public String getApdung()
	{
		return apdung;
	}
	public void setApdung(String apdung)
	{
		this.apdung = apdung;
	}
	public String getQuy()
	{
		return quy;
	}
	public void setQuy(String quy)
	{
		this.quy = quy;
	}

	ResultSet quyRs,kyRs;
	public ResultSet getQuyRs()
	{
		return quyRs;
	}
	public void setQuyRs(ResultSet quyRs)
	{
		this.quyRs = quyRs;
	}
	public ResultSet getKyRs()
	{
		return kyRs;
	}
	public void setKyRs(ResultSet kyRs)
	{
		this.kyRs = kyRs;
	}

	public String getView() 
	{		
		return this.view;
	}

	public void setView(String view) 
	{	
		this.view = view;
	}

}

