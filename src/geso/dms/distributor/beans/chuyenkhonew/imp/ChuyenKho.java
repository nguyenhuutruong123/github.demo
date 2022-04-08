package geso.dms.distributor.beans.chuyenkhonew.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import geso.dms.distributor.beans.chuyenkhonew.IChuyenKho;
import geso.dms.distributor.beans.chuyenkhonew.ISanPham;
import geso.dms.distributor.beans.chuyenkhonew.imp.SanPham;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

public class ChuyenKho implements IChuyenKho
{
	

	String id, userId, nppId, nppTen, ngaynhap, kbhId, nccId, msg, ghichu, vat, dvkdId, tienBVAT, tienAVAT;
	ResultSet nccRs, kbhRs, dvkdRs, khoRs;
	String khochuyenId,khonhanId;
	String dhId;
	String trangThai;
	String thuevat;
	public String getThuevat() {
		return thuevat;
	}

	public void setThuevat(String thuevat) {
		this.thuevat = thuevat;
	}


	String view;
	String nppCk;
	String ngayDc;
	String ngayKsgn = "";
	String file = "";

	Hashtable<String, String> dvdlList;
	NumberFormat formatter = new DecimalFormat("#,###,###.###");
	dbutils db;
	List<ISanPham> spList;

	public ChuyenKho(String id, String userId, String nppId, String nppTen, String ngaynhap, String kbhId, String nccId, String msg, String ghichu, String vat, String dvkdId)
	{
		this.id = id;
		this.userId = userId;
		this.nppId = nppId;
		this.nppTen = nppTen;
		this.ngaynhap = ngaynhap;
		this.kbhId = kbhId;
		this.nccId = nccId;
		this.msg = msg;
		this.ghichu = ghichu;
		this.vat = vat;
		this.dvkdId = dvkdId;
		this.view = "";
		this.thuevat = "8";
		this.nppCk = "";
		this.ngayDc = "";
		db = new dbutils();
	}

	public ChuyenKho()
	{
		this.id = "";
		this.userId = "";
		this.nppId = "";
		this.nppTen = "";
		this.khochuyenId ="";
		this.khonhanId ="";
		this.ngaynhap = "";
		this.thuevat = "8";
		this.kbhId = "";
		this.nccId = "";
		this.msg = "";
		this.ghichu = "";
		this.vat = "10";
		this.dvkdId = "";
		this.tienAVAT = "";
		this.tienBVAT = "";
		this.dhId = "";
		this.trangThai = "";
		this.view = "";
		this.nppCk = "";
		this.ngayDc = "";
		
		spList = new ArrayList<ISanPham>();
		dvdlList= new Hashtable<String, String>();
		db = new dbutils();
	}

	public ChuyenKho(String id)
	{
		this.id=id;
		this.userId = "";
		this.nppId = "";
		this.nppTen = "";
		this.khochuyenId ="";
		this.khonhanId ="";
		this.ngaynhap = "";
		this.kbhId = "";
		this.nccId = "";
		this.msg = "";
		this.ghichu = "";
		this.vat = "10";
		this.dvkdId = "";
		this.tienAVAT = "";
		this.tienBVAT = "";
		this.thuevat = "8";

		this.dhId = "";
		this.trangThai = "";
		this.view = "";
		this.nppCk = "";
		this.ngayDc = "";
		
		spList = new ArrayList<ISanPham>();
		dvdlList= new Hashtable<String, String>();
		db = new dbutils();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getNppId()
	{
		return nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getNgaynhap()
	{
		return ngaynhap;
	}

	public void setNgaynhap(String ngaynhap)
	{
		this.ngaynhap = ngaynhap;
	}

	public String getKbhId()
	{
		return kbhId;
	}

	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}

	public String getNccId()
	{
		return nccId;
	}

	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getGhichu()
	{
		return ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public ResultSet getNccRs()
	{
		return nccRs;
	}

	public void setNccRs(ResultSet nccRs)
	{
		this.nccRs = nccRs;
	}

	public ResultSet getKbhRs()
	{
		return kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs)
	{
		this.kbhRs = kbhRs;
	}

	public ResultSet getDvkdRs()
	{
		return dvkdRs;
	}

	public void setDvkdRs(ResultSet dvkdRs)
	{
		this.dvkdRs = dvkdRs;
	}

	public String getNppTen()
	{
		return nppTen;
	}

	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}

	public String getDvkdId()
	{
		return dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	@Override
	public void createRs( String nppId)
	{
		getNppInfo();
		
		if( this.nppId.length() == 0) {
			this.nppId = nppId;
		}		
						
		String query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '" + this.nppId
				+ "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
		this.dvkdRs = this.db.get(query);
		query = "select c.pk_seq as kbhId, c.diengiai as kbh from kenhbanhang c where  trangthai = 1 order by isnull(c.smartId,99999999),c.pk_seq ";
		this.kbhRs = this.db.get(query);
		
		query = "select top(1) ngayks as nksgn from KHOASONGAY ksn where ksn.npp_fk = '" + this.nppId + "'  order by ngayksgannhat desc ";
		System.out.println("#####" + query);
		ResultSet ngayksgannhat = this.db.get(query);
		try {
			if( ngayksgannhat.next() ) {
				this.ngayKsgn = (String) ngayksgannhat.getString("nksgn");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		query = "select distinct a.pk_seq as khoId, a.ten as ten,a.diengiai from kho a, nhapp_kho b where a.pk_seq = b.kho_fk and b.npp_fk = '" + this.nppId + "' and a.trangthai ='1'";
		this.khoRs = this.db.getScrol(query);

		query = "select d.pk_seq as nccId, d.ten as nccTen from nhaphanphoi a, nhacungcap_dvkd b, nhapp_nhacc_donvikd c, nhacungcap d where c.ncc_dvkd_fk = b.pk_seq and a.pk_seq = c.npp_fk and b.ncc_fk = d.pk_seq and a.pk_seq = '" + this.nppId + "'";
		this.nccRs = this.db.get(query);
		
		query="select DONVI,PK_SEQ as dvdlId from DONVIDOLUONG ";
		ResultSet rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				dvdlList.put(rs.getString("dvdlId"),rs.getString("DONVI"));
			}
			if(rs!=null)rs.close();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}

	private void getNppInfo()
	{
		
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();		
		this.nppId = util.getIdNhapp(this.userId);						
		this.nppTen = util.getTenNhaPP();
	}

	
	
	@Override
	public void init( String nppId )
	{
		String query=
		"select isnull(thuevat,0) as thuevat,ngayDieuChinh, npp.ten, ck.trangthai, ck.pk_seq, ck.KHOCHUYEN_FK, ck.KHONHAN_FK, ck.npp_fk , ck.kbh_fk, ck.ncc_fk, ck.GhiChu, ck.DVKD_FK,isnull(ck.filename,'') as filename "
		+ " from ChuyenKho ck left join NHAPHANPHOI npp on ck.NPP_FK = npp.PK_SEQ "+
		"where ck.pk_seq='"+this.id+"' ";		
		System.out.println("========" + query);
		
		ResultSet rs=this.db.get(query);
		try
		{			
			while(rs.next())
			{				
				this.dhId =rs.getString("pk_seq");
				this.thuevat =rs.getString("thuevat");
				this.khochuyenId=rs.getString("khochuyen_fk");
				this.khonhanId=rs.getString("khonhan_fk");
				this.nppId=rs.getString("npp_fk");
				this.nppCk = rs.getString("ten");				
				this.kbhId=rs.getString("kbh_fk");
				this.nccId=rs.getString("ncc_fk");
				this.dvkdId=rs.getString("dvkd_fk");
				this.ghichu=rs.getString("GhiChu");
				this.trangThai = rs.getString("trangthai");
				this.ngayDc = rs.getString("NgayDieuChinh");
				this.file  = rs.getString("filename") == null?"": rs.getString("filename");
			}
			if(rs!=null)
				rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		
		//this.ngayDc
		
		query=
	"		select dhsp.SanPham_FK as spId,sp.MA as spMa,sp.ten as spTen,dhsp.dvdl_fk as dvdlId, "+
	"			dhsp.SoLuong,dhsp.SoLuongChuan,dhsp.DonGia,dhsp.soluong+kho.available as tonkho,dhsp.THANHTIEN,qc.soluong1,qc.soluong2 "+ 	
	"		from ChuyenKho_SanPham dhsp        "+
	"			inner join chuyenkho ck on ck.pk_Seq=dhsp.chuyenkho_fk "+           
	"			inner join nhapp_kho kho on kho.npp_fk=ck.npp_fk and kho.kbh_fk=ck.kbh_fk and kho.kho_fk=ck.khochuyen_fk  and kho.sanpham_Fk=dhsp.SANPHAM_FK "+
	"			inner join SANPHAM  sp on sp.PK_SEQ=dhsp.SanPham_FK 	 "+
	"			left join QUYCACH qc on qc.SANPHAM_FK=dhsp.SANPHAM_FK and qc.DVDL1_FK=sp.DVDL_FK and qc.DVDL2_FK=100018 "+
	"		where dhsp.ChuyenKho_FK='"+this.id+"' "; 
		System.out.println("[spList]"+query);
		final long startTime = System.currentTimeMillis();
		ResultSet spRs = this.db.get(query);
		try
		{
			while (spRs.next())
			{
				ISanPham sp = null;
				int soluong1 = spRs.getInt("soluong1")==0?1:spRs.getInt("soluong1");
				int soluong2 = spRs.getInt("soluong2")==0?1:spRs.getInt("soluong2");
				int quycach =soluong1/soluong2;
				sp = new SanPham(spRs.getString("spId"), spRs.getString("spma"), spRs.getString("spten"), spRs.getString("dongia"), spRs.getString("dvdlId"));
				sp.setSoluong(spRs.getString("soluong")==null?"":spRs.getString("soluong"));
				sp.setSoluongchuan( spRs.getString("soluongchuan")==null?"": spRs.getString("soluongchuan"));
				sp.setTonkho( formatter.format(spRs.getDouble("tonkho")));
				sp.setThanhtien( spRs.getString("thanhtien"));
				sp.setQuycach(quycach+"" );
				spList.add(sp);
			}
			final long endTime = System.currentTimeMillis();
			System.out.println("Total execution time: " + (endTime - startTime) );
			System.out.println("Total List: "+spList.size() );
			if(spRs!=null)spRs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		createRs( nppId );
	}

	@Override
	public boolean save()
	{							
		if( (this.ngayDc.compareTo(this.ngayKsgn) <= 0) ) {			
			this.msg = "Bạn phải chọn ngày điều chỉnh lớn hơn ngày khóa sổ gần nhất !";
			return false;
		}
		if(this.khochuyenId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn kho chuyển !";
			return false;
		}
		
		if(this.khonhanId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn kho nhận !";
			return false;
		}
		
		if(this.khochuyenId.trim().equals(this.khonhanId.trim())) {
			this.msg = "Kho phải chọn kho chuyển và kho nhận khác nhau ! ";
			return false;
		}
		
		if(this.userId==""||this.userId==null)
		{
			this.msg="Vui lòng đăng nhập lại !";
			return false;
		}
		if(this.nppId==""||this.nppId==null)
		{
			this.msg=" Không xác nhận được nhà phân phối, vui lòng đăng nhập lại !";
			return false;
		}
		if(this.dvkdId==""||this.dvkdId==null)
		{
			this.msg=" Vui lòng chọn đại diện kinh doanh !";
			return false;
		}
		if(this.kbhId==""||this.kbhId==null)
		{
			this.msg=" Vui lòng chọn kênh bán hàng !";
			return false;
		}
		
		if(this.spList.size()<=0)
		{
			this.msg=" Không có sản phẩm nào !";
			return false;
		}
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			
			String msgKS  = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV(this.nppId, this.ngayDc , db);
			if( msgKS.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				this.msg = msgKS;
				return false;
			}
			
			
			
			String query= // 	KBH_FK	NPP_FK	NgayGoiLenTT	KHOCHUYEN_FK	KHONHAN_FK	NGUOITAO	NGUOISUA	NGAYTAO	NGAYSUA	TRANGTHAI	GHICHU	DVKD_FK	NCC_FK	NgayTTXacNhan	NGAYCHOT
			"insert into ChuyenKho(thuevat, NgayDieuChinh, NgayGoilenTT, KBH_FK,NPP_FK, KHOCHUYEN_FK, KHONHAN_FK, NguoiTao, NgayTao, NguoiSua, NgaySua, TrangThai,GhiChu,filename,ngaygiotao)" +
			"select '" + this.thuevat + "','" + this.ngayDc + "', '" + getDateTime() +"','"+this.kbhId+"','"+this.nppId+"','"+this.khochuyenId+"','"+this.khonhanId+"','"+this.userId+"','"+getDateTime()+"','"+this.userId+"','"+getDateTime()+"',0,N'"+this.ghichu+"',N'"+this.file+"',CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114)   ";

			if(!this.db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg=" Khong thể tạo đơn chuyển kho. Vui lòng liên hệ admin ! "+query;
				return false;
			}		
			
			query = "select IDENT_CURRENT('ChuyenKho') as dmhId";
			
			ResultSet rsDmh = db.get(query);	
			
			if(rsDmh.next())
			{
				this.id = rsDmh.getString("dmhId");
				rsDmh.close();
			}
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanPham sp = this.spList.get(i);
					
					double tonkho =Double.parseDouble(sp.getTonkho());
					double soluong =Double.parseDouble(sp.getSoluong());
					double quycach =Double.parseDouble(sp.getQuycach());
					tonkho =tonkho * quycach;
					
					if(soluong>tonkho)
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg="Tồn kho sản phẩm ("+sp.getTen() +") không đủ ! Vui lòng kiểm tra lại ^^ ";
						return false;
					}
					
					System.out.println("[spId]"+sp.getId()+"[spMa]"+sp.getMa()+"[soluong]"+sp.getSoluong()+"[Quycach]"+sp.getQuycach()+"[tonkho]"+sp.getTonkho());
					/*query ="update nhapp_kho set available = available -'"+ sp.getSoluong() +"',booked=booked+'"+sp.getSoluong()+"' where sanpham_fk='"+sp.getId()+"' and kho_fk='"+this.khochuyenId+"' and npp_fk='"+this.nppId+"' and kbh_fk='"+this.kbhId+"'";
					if(!this.db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg=" Vui lòng liên hệ admin để xử lý lỗi ! "+query;
						return false;
					}	*/	
					geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
					msg=util.Update_NPP_Kho_Sp(this.ngayDc,this.id, "chuyen kho_988", db, this.khochuyenId, sp.getId(), this.nppId, this.kbhId, 0.0,  soluong, (-1)*soluong, 0.0);
			    	if(msg.length()>0)
			    	{
			    		db.getConnection().rollback();
			    		this.msg = "Lỗi kho khi tạo đơn hàng: " + this.msg;
			    		return false;
			    	}	
					
					query=
					" insert into ChuyenKho_SanPham(ChuyenKho_FK,SanPham_FK,DVDL_FK,SoLuong,SoLuongChuan,DonGia,ThanhTien) " +
					" select '"+this.id+"','"+sp.getId()+"','"+sp.getDvdlId()+"',"+sp.getSoluong()+","+sp.getSoluongchuan()+","+sp.getDongia()+", "+sp.getSoluong()+"*"+sp.getDongia()+" ";
					System.out.println("[ChuyenKho_Sp]"+query);
					if(!this.db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg=" Vui lòng liên hệ admin để xử lý lỗi ! "+query;
						return false;
					}		
					//geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
				
		
			    	query=  " select KHO_FK,SANPHAM_FK,KBH_FK,NGAYNHAPKHO,available  from NHAPP_KHO_CHITIET "+  
			    			" where NPP_FK ="+nppId+" and KBH_FK= " +this.kbhId +
			    			" and KHO_FK="+this.khochuyenId+"  and SANPHAM_FK =  "+ sp.getId() +
			    			" AND AVAILABLE >0  and NGAYNHAPKHO<='"+this.ngayDc+"'"+
			    			" order by NGAYNHAPKHO,AVAILABLE ";
			    	ResultSet rssp=db.get(query);
			    	double soluongdenghi=soluong ;
	
			    	while(rssp.next() && soluongdenghi >0){
			    		double soluong_avai= rssp.getDouble("AVAILABLE");
			    		double soluongcapnhat=0;
			    		if(soluong_avai >soluongdenghi){
			    			soluongcapnhat= soluongdenghi;
			    			soluongdenghi=0;
			    		}else{
			    			soluongcapnhat =soluong_avai;
			    			soluongdenghi =soluongdenghi - soluong_avai;
			    		}
	
			    		String _khoid=rssp.getString("kho_fk");
			    		String _kbhid=rssp.getString("KBH_FK");
			    		String ngaynhapkho=rssp.getString("NGAYNHAPKHO");
			    		
			    		query=
								" insert into ChuyenKho_SanPham_chitiet(ChuyenKho_FK,SanPham_FK,DVDL_FK,SoLuong,SoLuongChuan,DonGia,ThanhTien,ngaynhapkho) " +
								" select '"+this.id+"','"+sp.getId()+"','"+sp.getDvdlId()+"',"+soluongcapnhat+","+soluongcapnhat+","+sp.getDongia()+
								", "+sp.getSoluong()+"*"+sp.getDongia()+" ,'"+ngaynhapkho+"'";
								System.out.println("[ChuyenKho_Sp]"+query);
						if (db.updateReturnInt(query)<=0) 
						{
							this.msg="Không thể cập nhật : "+query;
							db.getConnection().rollback();
							return false;
						}
						 
						String msg1=util.Update_NPP_Kho_Sp_Chitiet(this.ngayDc, "create chuyenkho  DHID: "+this.id ,  db, _khoid, sp.getId(), nppId, _kbhid, ngaynhapkho, 0,soluongcapnhat,(-1)* soluongcapnhat, 0, 0);
						if (msg1.length()> 0) 
						{
							this.msg=msg1;
							db.getConnection().rollback();
							return false;
						}
	
			    	}
			    	rssp.close();
					
					if(soluongdenghi!=0){
						 
						this.msg=  "Số lượng đề xuất trong lô chi tiết của sản phẩm  tới ngày (ngày cấu hình hóa đơn)"+this.ngayDc+" không còn đủ, " +
								" vui lòng kiểm tra báo cáo ( xuất nhập tồn,tồn hiện tại) theo lô để biết thêm chi tiết ";
						db.getConnection().rollback();
						return false;
						
					}
		
				    	
				}
			}		
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			e.printStackTrace();
			this.msg="Vui lòng liên hệ Admin với lỗi sau "+e.getMessage();
			return false;
		}
	}
	
	@Override
	public boolean edit()
	{		
		if( (this.ngayDc.compareTo(this.ngayKsgn) <= 0) ) {			
			this.msg = "Bạn phải chọn ngày điều chỉnh lớn hơn ngày khóa sổ gần nhất !";
			return false;
		}
		if(this.khochuyenId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn kho chuyển !";
			return false;
		}
		if(this.khonhanId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn kho nhận !";
			return false;
		}
		
		if(this.userId==""||this.userId==null)
		{
			this.msg = " Vui lòng đăng nhập lại !";
			return false;
		}
		if(this.nppId==""||this.nppId==null)
		{
			this.msg=" Không xác nhận được nhà phân phối, vui lòng đăng nhập lại !";
			return false;
		}
		if(this.dvkdId==""||this.dvkdId==null)
		{
			this.msg=" Vui lòng chọn đơn vị kinh doanh !";
			return false;
		}
		if(this.kbhId==""||this.kbhId==null)
		{
			this.msg= " Vui lòng chọn kênh bán hàng !";
			return false;
		}
		
		if(this.spList.size()<=0)
		{
			this.msg= " Không có sản phẩm nào !";
			return false;
		}
		
		if(this.khochuyenId.trim().equals(this.khonhanId.trim())) {
			this.msg = "Kho phải chọn kho chuyển và kho nhận khác nhau ! ";
			return false;
		}
		
		try
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			this.db.getConnection().setAutoCommit(false);
			
			String msgKS  = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV(this.nppId, this.ngayDc , db);
			if( msgKS.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				this.msg = msgKS;
				return false;
			}
			
			
			geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
			String query=	 "SELECT CK.ngaydieuchinh,CKSP.SANPHAM_FK,CK.KHOCHUYEN_FK,KHO.NPP_FK,KHO.KBH_FK,cksp.SOLUONG   "+
					"			FROM CHUYENKHO CK INNER JOIN CHUYENKHO_SANPHAM CKSP ON CKSP.CHUYENKHO_FK=CK.PK_sEQ "+
					"		INNER JOIN NHAPP_KHO KHO ON KHO.NPP_FK=CK.NPP_FK AND KHO.KBH_FK=CK.KBH_FK AND KHO.SANPHAM_FK =CKSP.SANPHAM_FK "+
					"		AND KHO.KHO_FK=CK.KHOCHUYEN_FK " +
					"      WHERE CK.PK_SEQ='"+id+"'      ";
					
				System.out.println("UPDATE NPP KHO: " + query  );
		    ResultSet ckRs = db.get(query);
		    while(ckRs.next())
		    {
		    	String kho_fk=ckRs.getString("KHOCHUYEN_FK");
				String nppId=ckRs.getString("npp_fk");	
				String kenh =ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaynhap  =ckRs.getString("ngaydieuchinh");
				//String tensp  =ckRs.getString("tensp");
				Double soluong = ckRs.getDouble("soluong");


				msg=util.Update_NPP_Kho_Sp(ngaynhap,this.id, "revert chuyen kho truoc khi luu _1454552" , db, kho_fk, sanpham_fk, nppId, kenh, 0.0, (-1)* soluong, soluong, 0.0);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					this.msg = "Lỗi kho khi tạo đơn hàng: " + this.msg;
					return false;
				}		
		    }			
		    
		    
		    
		
		    String query_ct=	 "SELECT CK.ngaydieuchinh,CKSP.SANPHAM_FK,CK.KHOCHUYEN_FK,CK.NPP_FK,CK.KBH_FK,cksp.SOLUONG, cksp.ngaynhapkho ,CK.KHOCHUYEN_FK   "
		    		+ "FROM CHUYENKHO CK INNER JOIN CHUYENKHO_SANPHAM_chitiet CKSP ON CKSP.CHUYENKHO_FK=CK.PK_sEQ   " +
					"      WHERE CK.PK_SEQ='"+id+"'      ";
					
				System.out.println("UPDATE NPP KHO: " + query_ct  );
		    ckRs = db.get(query_ct);
		    while(ckRs.next())
		    {
		    	String kho_fk=ckRs.getString("KHOCHUYEN_FK");
				String nppId=ckRs.getString("npp_fk");	
				String kenh =ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaynhap  =ckRs.getString("ngaydieuchinh");
				//String tensp  =ckRs.getString("tensp");
				Double soluong = ckRs.getDouble("soluong");
		    	String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
					String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "revert chuyen kho truoc khi luu DHID: "+this.id ,  db, kho_fk, sanpham_fk, nppId, kenh, ngaynhapkho, 0,(-1)*soluong, soluong, 0, 0);
					if (msg1.length()> 0) 
					{
						this.msg=msg1;
						db.getConnection().rollback();
						return false;
					}
		    }		
			
			
			
			query=
			"UPdate ChuyenKho set thuevat='"+this.thuevat+ "',NCC_FK='"+this.nccId+ "', NgayDieuChinh = '" + this.ngayDc +"',DVKD_FK='"+this.dvkdId+"',KBH_FK='"+this.kbhId+"',NPP_FK='"+this.nppId+"',KHOCHUYEN_FK='"+this.khochuyenId+"',KHONHAN_FK='"+this.khonhanId+"',NguoiSua='"+this.userId+"',NgaySua='"+getDateTime()+"',GhiChu=N'"+this.ghichu+"',filename = N'"+this.file+"', ngaygiosua = CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114)  "
					+ " where pk_seq='"+this.id+"' and trangthai= 0 " ;
			System.out.println("[Save]"+query);
			if(this.db.updateReturnInt(query)!=1 )
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg= " Vui lòng liên hệ admin để xử lý lỗi !  "+query;
				return false;
			}		
			query="delete from ChuyenKho_SanPham where ChuyenKho_FK='"+this.id+"'";
			if(!this.db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg=" Vui lòng liên hệ admin để xử lý lỗi !  "+query;
				return false;
			}	
			
			query="delete from ChuyenKho_SanPham_chitiet where ChuyenKho_FK='"+this.id+"'";
			if(!this.db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg=" Vui lòng liên hệ admin để xử lý lỗi !  "+query;
				return false;
			}	
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanPham sp = this.spList.get(i);
					
					double tonkho =Double.parseDouble(sp.getTonkho());
					double soluong =Double.parseDouble(sp.getSoluong());
					double quycach =Double.parseDouble(sp.getQuycach());
					tonkho =tonkho *quycach;
					if(soluong>tonkho)
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg="Tồn kho sản phẩm ("+sp.getTen() +") không đủ ! Vui lòng kiểm tra lại ^^ ";
						return false;
					}
					System.out.println("[spId]"+sp.getId()+"[spMa]"+sp.getMa()+"[soluong]"+sp.getSoluong()+"[Quycach]"+sp.getQuycach()+"[tonkho]"+sp.getTonkho());
					/*query ="update nhapp_kho set available = available -'"+sp.getSoluong()+"', booked = booked +'"+sp.getSoluong()+"' where sanpham_fk='"+sp.getId()+"' and kho_fk='"+this.khochuyenId+"' and npp_fk='"+this.nppId+"' and kbh_fk='"+this.kbhId+"'";
					
					if(!this.db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg="Vui lòng liên hệ admin để xử lý lỗi !"+query;
						return false;
					}	*/
					//geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
					msg=util.Update_NPP_Kho_Sp(this.ngayDc,this.id, "chuyen kho_988", db, this.khochuyenId, sp.getId(), this.nppId, this.kbhId, 0.0,  soluong, (-1)*soluong, 0.0);
			    	if(msg.length()>0)
			    	{
			    		db.getConnection().rollback();
			    		this.msg = "Lỗi kho khi tạo đơn hàng: " + this.msg;
			    		return false;
			    	}
					query=
					" insert into ChuyenKho_SanPham(ChuyenKho_FK,SanPham_FK,DVDL_FK,SoLuong,SoLuongChuan,DonGia,ThanhTien) " +
					" select '"+this.id+"','"+sp.getId()+"','"+sp.getDvdlId()+"',"+sp.getSoluong()+","+sp.getSoluongchuan()+","+sp.getDongia()+", "+sp.getSoluong()+"*"+sp.getDongia()+" ";
					System.out.println("[ChuyenKho_Sp]"+query);
					if(!this.db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg="Vui lòng liên hệ admin để xử lý lỗi ! "+query;
						return false;
					}
					query=  " select KHO_FK,SANPHAM_FK,KBH_FK,NGAYNHAPKHO,available  from NHAPP_KHO_CHITIET "+  
			    			" where NPP_FK ="+nppId+" and KBH_FK= " +this.kbhId +
			    			" and KHO_FK="+this.khochuyenId+"  and SANPHAM_FK =  "+ sp.getId() +
			    			" AND AVAILABLE >0  and NGAYNHAPKHO<='"+this.ngayDc+"'"+
			    			" order by NGAYNHAPKHO,AVAILABLE ";
			    	ResultSet rssp=db.get(query);
			    	double soluongdenghi=soluong ;
	
			    	while(rssp.next() && soluongdenghi >0){
			    		double soluong_avai= rssp.getDouble("AVAILABLE");
			    		double soluongcapnhat=0;
			    		if(soluong_avai >soluongdenghi){
			    			soluongcapnhat= soluongdenghi;
			    			soluongdenghi=0;
			    		}else{
			    			soluongcapnhat =soluong_avai;
			    			soluongdenghi =soluongdenghi - soluong_avai;
			    		}
	
			    		String _khoid=rssp.getString("kho_fk");
			    		String _kbhid=rssp.getString("KBH_FK");
			    		String ngaynhapkho=rssp.getString("NGAYNHAPKHO");
			    		
			    		query=" insert into ChuyenKho_SanPham_chitiet(ChuyenKho_FK,SanPham_FK,DVDL_FK,SoLuong,SoLuongChuan,DonGia,ThanhTien,ngaynhapkho) " +
						" select '"+this.id+"','"+sp.getId()+"','"+sp.getDvdlId()+"',"+soluongcapnhat+","+soluongcapnhat+","+sp.getDongia()+", "+sp.getSoluong()+"*"+sp.getDongia()+",'"+ngaynhapkho+"' ";
						System.out.println("[ChuyenKho_Sp]"+query);
						if (db.updateReturnInt(query)<=0) 
						{
							this.msg="Không thể cập nhật : "+query;
							db.getConnection().rollback();
							return false;
						}
						 System.out.println("ngay nhap kho "+ngaynhapkho);
						String msg1=util.Update_NPP_Kho_Sp_Chitiet(this.ngayDc, "create donhang DHID: "+this.id ,  db, _khoid, sp.getId(), nppId, _kbhid, ngaynhapkho, 0,soluongcapnhat,(-1)* soluongcapnhat, 0, 0);
						if (msg1.length()> 0) 
						{
							this.msg=msg1;
							db.getConnection().rollback();
							return false;
						}
	
			    	}
			    	rssp.close();
					
					if(soluongdenghi!=0){
						 
						this.msg=  "Số lượng đề xuất trong lô chi tiết của sản phẩm  tới ngày (ngày cấu hình hóa đơn)"+this.ngayDc+" không còn đủ, " +
								" vui lòng kiểm tra báo cáo ( xuất nhập tồn,tồn hiện tại) theo lô để biết thêm chi tiết ";
						db.getConnection().rollback();
						return false;
						
					}
					
				}
			}
			ResultSet rssp = db.get("select sanpham_fk, soluong, b.npp_fk, b.khochuyen_fk as kho_fk, b.ngaydieuchinh from CHUYENKHO_SANPHAM a inner join CHUYENKHO b"
							+ " on a.DIEUCHINHTONKHO_FK = b.pk_seq where b.pk_seq = '"+ id + "' ");
			if (rssp != null) {
				while (rssp.next()) {
					ResultSet rsngay = db.get("select * from [dbo].[uf_CacNgayTrongKhoangThoiGian]( '"+ rssp.getString("ngaydieuchinh")+ "','"+ getDateTime() + "') ");

					double m = 0;
					if (rsngay != null) {
						while (rsngay.next()) {
							String a = "select cuoiky,sanpham_fk from [dbo].[ufn_XNT_TuNgay_DenNgay_Trongky]('"
									+ rssp.getString("npp_fk")+ "','"+ rssp.getString("ngaydieuchinh")
									+ "','"+ rsngay.getString("ngay")+ "' ) "
									+ "\n where npp_fk = '"+ rssp.getString("npp_fk")
									+ "' and kho_fk ='"+ rssp.getString("kho_fk")
									+ "' and sanpham_fk = '"+ rssp.getString("sanpham_fk") + "'  ";
							System.out.println("______+_:" + a);
							ResultSet rschecktk = db.get(a);
							if (rschecktk.next()) {
								m = rschecktk.getDouble("cuoiky");
							}
							if (m < 0) {
								geso.dms.center.util.Utility.rollback_throw_exception(this.db);
								this.msg=" Lỗi cập nhật dctk, không đủ kho xnt trong ky từ ngày đơn hàng đến ngày hiện tại! ";
								return false;
							}
						}
					}
				}

			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			e.printStackTrace();
			this.msg="Vui lòng liên hệ admin để xử lý lỗi ! "+e.getMessage();
			return false;
		}
	}

	@Override
	public void DBclose()
	{
		
			try
			{
				if(this.khoRs!=null)
				this.khoRs.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public String getVat()
	{
		return vat;
	}

	public void setVat(String vat)
	{
		this.vat = vat;
	}

	public List<ISanPham> getSpList()
	{
		return spList;
	}

	public void setSpList(List<ISanPham> spList)
	{
		this.spList = spList;
	}


	public ResultSet getKhoRs()
	{
		return khoRs;
	}

	public void setKhoRs(ResultSet khoRs)
	{
		this.khoRs = khoRs;
	}

	public String getTienBVAT()
	{
		return tienBVAT;
	}

	public void setTienBVAT(String tienBVAT)
	{
		this.tienBVAT = tienBVAT;
	}

	public String getTienAVAT()
	{
		return tienAVAT;
	}

	public void setTienAVAT(String tienAVAT)
	{
		this.tienAVAT = tienAVAT;
	}

	@Override
	public java.util.Hashtable<String, String> getDvdlList()
	{
		return this.dvdlList;
	}

	@Override
	public void setDvdlList(java.util.Hashtable<String, String> dvdlList)
	{
		this.dvdlList = dvdlList;
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getKhochuyenId()
	{
		return khochuyenId;
	}

	public void setKhochuyenId(String khochuyenId)
	{
		this.khochuyenId = khochuyenId;
	}

	public String getKhonhanId()
	{
		return khonhanId;
	}

	public void setKhonhanId(String khonhanId)
	{
		this.khonhanId = khonhanId;
	}

	@Override
	public String getDhId() {		
		return this.dhId;
	}

	@Override
	public void setDhId(String dhId) {
		this.dhId = dhId;		
	}

	@Override
	public String getTrangThai() {		
		return this.trangThai;
	}

	@Override
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public String getView() {
		return this.view;
	}

	@Override
	public void setView(String view) {
		this.view = view;		
	}

	@Override
	public String getNppCk() {
		
		return this.nppCk;
	}

	@Override
	public void setNppCk(String nppCk) {
		this.nppCk = nppCk;
		
	}


	@Override
	public String getNgayDc() {		
		return this.ngayDc;
	}

	@Override
	public void setNgayDc(String ngayDc) {
		this.ngayDc = ngayDc;		
	}
	public String[] getFile() {
		
		return this.file.split(",");
	}

	
	public void setFile(String file) {
		this.file=file;
		
	}
}
