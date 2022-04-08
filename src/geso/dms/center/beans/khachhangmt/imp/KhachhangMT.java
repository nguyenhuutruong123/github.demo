package geso.dms.center.beans.khachhangmt.imp;

import geso.dms.center.beans.khachhangmt.IKhachhangMT;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class KhachhangMT implements IKhachhangMT {
	private static final long serialVersionUID = -9217977546733610214L;

	String nguoidaidien;
	String userId;
	String id;
	String ten;
	String diachi;// dia chi nhan hang
	String diachixhd = "";
	String IdKhoTT;
	String masothue = "";
	String tpId;
	String qhId;
	String sodienthoai;
	String dvkd;
	String khuvuc; // Ten khu vuc trong nha phan phoi
	String trangthai;
	String ma;
	String pass;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String error;
	String prisec;
	String tungay;
	String denngay;
	String dckho;
	String DiaBanHd;
	String NganHangId, ChiNhanhId, ChuTaiKhoan;
	String giayphepkd, sotk, fax;
	// Chủ nhà phân phối
	// Tồn an toàn
	// Mua hàng từ
	// Lịch đặt hàng
	// Bán theo quy trình
	String ChuNhaPhanPhoi;
	String TonAnToan;
	String MuaHangTu;
	String LichDatHang;
	String QuyTrinhBanHang;
	// ----
	ResultSet rsNganHang, rsChiNhanh;
	ResultSet khuvucList;
	String kvId;

	ResultSet tp;
	ResultSet qh;

	ResultSet nhapptiennhiem;
	String npptnId;

	ResultSet dvkd_nccList;
	ResultSet dvkd_nccSelected;
	String[] dvkd_nccIds;
	ResultSet gsbh_kbhList;
	ResultSet gsbh_kbhSelected;
	String[] gsbh_kbhIds;
	String gsbhnpp;

	ResultSet NgaydhList;
	ResultSet NgaydhSelected;
	String[] ngaydhIds;
	ResultSet MuahangtuRs;
	ResultSet rs_khott;
	dbutils db;

	// boolean checkgsbh;

	public KhachhangMT(String[] param) {
		this.id = param[0];
		this.ten = param[1];
		this.diachi = param[2];
		this.sodienthoai = param[3];
		this.trangthai = param[4];
		this.ngaytao = param[5];
		this.nguoitao = param[6];
		this.ngaysua = param[7];
		this.nguoisua = param[8];
		this.dvkd = param[9];
		this.khuvuc = param[10];
		this.kvId = param[11];
		this.npptnId = param[12];
		this.ma = param[13];
		this.pass = param[14];
		this.tungay = param[15];
		this.denngay = param[16];
		this.dckho = param[17];
		this.msg = "";
		this.DiaBanHd = param[18];
		this.NganHangId = param[19];
		this.ChiNhanhId = param[20];
		this.ChuTaiKhoan = param[21];
		this.isChiNhanh = "0";
		this.nguoidaidien = "";
		this.db = new dbutils();
		this.error = "";
		this.maddt = "";
		this.diachi2 = "";
		this.tsdathang = "";
		this.loainpp = "0";
	}

	public KhachhangMT(String id) {
		this.id = id;
		this.ten = "";
		this.diachi = "";
		this.masothue = "";
		this.diachixhd = "";
		this.IdKhoTT = "";
		this.tpId = "";
		this.qhId = "";
		this.sodienthoai = "";
		this.trangthai = "1";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.khuvuc = "";
		this.dvkd = "";
		this.kvId = "";
		this.npptnId = "";
		this.msg = "";
		this.ma = "";
		this.pass = "";
		this.prisec = "";
		this.tungay = "";
		this.denngay = "";
		this.tungay = "";
		this.denngay = "";
		this.dckho = "";
		this.sotk = "";
		this.giayphepkd = "";
		this.fax = "";
		this.DiaBanHd = "";
		this.NganHangId = "";
		this.ChiNhanhId = "";
		this.ChuTaiKhoan = "";
		this.tentat = "";
		this.isChiNhanh = "0";
		this.nguoidaidien = "";
		this.MuaHangTu="";
		this.ChuNhaPhanPhoi="";
		this.TonAnToan="";
		this.error="";
		this.LichDatHang="";
		this.QuyTrinhBanHang="";
		this.loainpp = "0";
		this.tructhuocId = "";
		this.songayno = "";
		this.sotienno = "";
		this.ttppId = "";

		this.maddt = "";
		this.diachi2 = "";
		this.tsdathang = "";
		
		this.db = new dbutils();
		if (id.length() > 0)
			this.init();
		else
			this.createRS();
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTen() {
		return this.ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getDiachi() {
		return this.diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String getTpId() {
		return this.tpId;
	}

	public void setTpId(String tpId) {
		this.tpId = tpId;
	}

	public String getQhId() {
		return this.qhId;
	}

	public void setQhId(String qhId) {
		this.qhId = qhId;
	}

	public String getSodienthoai() {
		System.out.println("Vao roi:");
		return this.sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) {
		this.sodienthoai = sodienthoai;
	}

	public String getDvkd() {
		return this.dvkd;
	}

	public void setDvkd(String dvkd) {
		this.dvkd = dvkd;
	}

	public String getKv() {
		return this.khuvuc;
	}

	public void setKv(String khuvuc) {
		this.khuvuc = khuvuc;
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setMaSAP(String ma) {
		this.ma = ma;
	}

	public String getMaSAP() {
		return this.ma;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	// Chủ nhà phân phối
	// Tồn an toàn
	// Mua hàng từ
	// Lịch đặt hàng
	// Bán theo quy trình
	public void setChuNhaPhanPhoi(String cnpp) {
		this.ChuNhaPhanPhoi = cnpp;
	}
	public String getChuNhaPhanPhoi(){
		return this.ChuNhaPhanPhoi;
	}
	public void setTonAnToan(String tat) {
		this.TonAnToan = tat;
	}
	public String getTonAnToan(){
		return this.TonAnToan;
	}
	public void setMuaHangTu(String mht) {
		this.MuaHangTu = mht;
	}
	public String getMuaHangTu(){
		return this.MuaHangTu;
	}
	public void setLichDatHang(String ldh) {
		this.LichDatHang = ldh;
	}
	public String getLichDatHang(){
		return this.LichDatHang;
	}
	public void setQuyTrinhBanHang(String bhtqt) {
		this.QuyTrinhBanHang = bhtqt;
	}
	public String getQuyTrinhBanHang(){
		return this.QuyTrinhBanHang;
	}
	public String getPass() {
		return this.pass;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getNgaytao() {
		return this.ngaytao;

	}

	public void setNgaytao(String ngaytao) {
		this.ngaytao = ngaytao;
	}

	public String getNgaysua() {
		return this.ngaysua;

	}

	public void setNgaysua(String ngaysua) {
		this.ngaysua = ngaysua;
	}

	public String getNguoitao() {
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}

	public String getNguoisua() {
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) {
		this.nguoisua = nguoisua;
	}

	public String getMessage() {
		return this.msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}
	public void setError(String Error){
		this.error += Error + "\r\n";
	}
	public String getError(){
		return this.error;
	}

	public ResultSet getTp() {
		return this.tp;
	}

	public void setTp(ResultSet tp) {
		this.tp = tp;
	}

	public ResultSet getQh() {
		return this.qh;
	}

	public void setQh(ResultSet qh) {
		this.qh = qh;
	}

	public ResultSet getKhuvuc() {
		return this.khuvucList;
	}
	public ResultSet getMuahangtuRs(){
		return this.MuahangtuRs;
	}
	public void setKhuvuc(ResultSet khuvucList) {
		this.khuvucList = khuvucList;
	}

	public ResultSet getNhappTiennhiem() {
		return this.nhapptiennhiem;
	}

	public void setNhappTiennhiem(ResultSet nhapptiennhiem) {
		this.nhapptiennhiem = nhapptiennhiem;
	}

	public String getKvId() {
		return this.kvId;
	}

	public void setKvId(String kvId) {
		this.kvId = kvId;
	}

	public String getNpptnId() {
		return this.npptnId;
	}

	public void setNpptnId(String npptnId) {
		this.npptnId = npptnId;
	}

	public String getPriSec() {
		if (this.prisec == null) {
			return "";
		} else {
			return this.prisec;
		}
	}

	public void setPriSec(String prisec) {
		this.prisec = prisec;
	}

	public ResultSet getDvkd_NccList() {
		return this.dvkd_nccList;
	}

	public void setDvkd_NccList(ResultSet dvkd_ncclist) {
		this.dvkd_nccList = dvkd_ncclist;
	}

	public ResultSet getDvkd_NccSelected() {
		return this.dvkd_nccSelected;
	}

	public void setDvkd_NccSelected(ResultSet dvkd_nccselected) {
		this.dvkd_nccSelected = dvkd_nccselected;
	}


	public String getDvkd_NccIdSelected(){
		String idSelected="";
		if(this.dvkd_nccIds!=null){
			for(int i=0;i<this.dvkd_nccIds.length;i++){
				idSelected+= (String)dvkd_nccIds[i]+"-";
			}
		}
		//System.out.println("Chuoi id duoc chon:"+ idSelected);
		return idSelected;
	}

	public Hashtable<Integer, String> getDvkd_NccIds() {
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.dvkd_nccIds != null) {
			int size = (this.dvkd_nccIds).length;
			int m = 0;
			while (m < size) {
				selected.put(new Integer(m), this.dvkd_nccIds[m]);
				m++;
				System.out.println("ID lua chon don vi kinh doanh: "+ this.dvkd_nccIds[m]);
			}
		} else {
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setDvkd_NccIds(String[] dvkd_nccIds) {

		this.dvkd_nccIds = dvkd_nccIds;
	}

	public ResultSet getGsbh_KbhList() {
		return this.gsbh_kbhList;
	}

	public void setGsbh_KbhList(ResultSet gsbh_kbhlist) {
		this.gsbh_kbhList = gsbh_kbhlist;
	}

	public ResultSet getGsbh_KbhSelected() {
		return this.gsbh_kbhSelected;
	}

	public void setGsbh_KbhSelected(ResultSet gsbh_kbhselected) {
		this.gsbh_kbhSelected = gsbh_kbhselected;
	}

	public Hashtable<Integer, String> getGsbh_KbhIds() {
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.gsbh_kbhIds != null) {
			int size = (this.gsbh_kbhIds).length;
			int m = 0;
			while (m < size) {
				selected.put(new Integer(m), this.gsbh_kbhIds[m]);
				m++;
			}
		} else {
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setGsbh_KbhIds(String[] gsbh_kbhIds) {
		this.gsbh_kbhIds = gsbh_kbhIds;
	}
	public String getGsbh_KbhIdSelected(){
		String idSelected="";
		if(gsbh_kbhIds!=null){
			for(int i=0;i<(this.gsbh_kbhIds).length;i++){
				idSelected+=this.gsbh_kbhIds[i]+"-";
			}
		}
		return idSelected;

	}

	public String getNgayDh_IdSelected(){
		String idSelected="";
		if(ngaydhIds!=null){
			for(int i=0;i<(this.ngaydhIds).length;i++){
				idSelected+=this.ngaydhIds[i]+"-";
			}
		}
		return idSelected;

	}

	public boolean checkma(String ma)
	{  
		String sql;
		//if(this.id.length()==0){
		sql = "select count(*) as num from nhaphanphoi where ma ='" + this.ma + "'";
		//}else{
		//sql = "select count(*) as num from nhaphanphoi where pk_seq <> '"+ this.id +"' and ma ='" + this.ma + "'";
		//}
		System.out.println("thong tin sp:"+sql);
		ResultSet rs = db.get(sql);
		if(rs != null)
			try 
		{
				rs.next();
				if(rs.getString("num").equals("0"))
					return false;
		} 
		catch (SQLException e){return false;}
		return true;
	}

	public boolean CreateNpp(HttpServletRequest request) {
		dbutils cn = new dbutils();
		try {
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			cn.getConnection().setAutoCommit(false);

			String query = "insert into nhaphanphoi(isChiNhanh,maddt,ten, ngaytao, ngaysua, nguoitao, nguoisua, dienthoai, diachi, khuvuc_fk, trangthai, ma, pass, npptn_fk, priandsecond,tinhthanh_fk,quanhuyen_fk,masothue,diachixhd,khosap,ngaybatdau,ngayketthuc,dckho,giayphepkd,sotaikhoannh,fax,DiaBanHd,NganHang,ChiNhanh,ChuTaiKhoan,SoNgayNo,SoTienNo,LoaiNPP,Tructhuoc_fk, nguoidaidien,chunhaphanphoi,muahangtu,lichdathang,quytrinhbanhang,isKHMT,diachi2,tansuatdathang)"+ 
					" values('"+ this.isChiNhanh+ "',N'"+ this.maddt+ "',N'"+ this.ten+ "','"+ this.ngaytao+ "','"+ this.ngaytao+ "','"+ this.nguoitao+ "','"+ this.nguoitao+ "','"+ this.sodienthoai+ "',N'"+ this.diachi+ "','"+ this.kvId+ "','"+ this.trangthai+ "','"+ this.ma+ "','"+ this.pass+ "','"+ this.npptnId+ "','0',"+ this.tpId+ ","+ this.qhId+ ",'"+ this.masothue+ "',N'"+ this.diachixhd+ "','"+ this.IdKhoTT+ "','"+ this.tungay+ "','"+ this.denngay+ "','"+ this.dckho+ "','"+ this.giayphepkd+ "','"+ this.sotk+ "','"+ this.fax+ "',N'"+ this.DiaBanHd+ "',"+ "N'"+ this.NganHangId+ "',N'"+ this.ChiNhanhId+ "',N'"+ this.ChuTaiKhoan+ "',"+ (this.songayno.length() <= 0 ? "NULL" : this.songayno)+ ","+ (this.sotienno.length() <= 0 ? "NULL" : this.sotienno)+ ","+ (this.loainpp.length() <= 0 ? "NULL" : this.loainpp)+ ","+ (this.tructhuocId.length() <= 0 ? "NULL": this.tructhuocId)+ ", N'"+ this.nguoidaidien+"','"+this.ChuNhaPhanPhoi+"','"+this.MuaHangTu+"','"+this.LichDatHang+"',"+this.QuyTrinhBanHang+ ", '1', N'"+this.diachi2+"', '"+this.tsdathang+"')";
			if (!cn.update(query)) 
			{
				cn.update("rollback");
				this.msg = "Khong the Tao Moi nhaphanphoi: " + query;
				return false;
			}

			query = "select  scope_identity()  as nppId";

			ResultSet rs = cn.get(query);
			rs.next();
			this.id = rs.getString("nppId");
			rs.close();

			if (this.dvkd_nccIds != null)
			{
				int size = (this.dvkd_nccIds).length;
				int m = 0;
				while(m < size)				
				{	
					query = "insert into nhapp_nhacc_donvikd values('"+ this.id  +"','" + this.dvkd_nccIds[m] + "')";
					System.out.println("2. Tao NPP_NCC_DVKD : " +query ) ; 
					if(!cn.update(query))
					{
						cn.getConnection().rollback();
						this.msg = "Khong the cap nhat bang nhapp_nhacc_donvikd: " + query;
						return false; 
					}
					m++;
				}
			}

			if (this.gsbh_kbhIds != null)
			{
				int size = (this.gsbh_kbhIds).length;
				int n = 0;

				while(n < size)
				{
					if(this.gsbh_kbhIds[n] != null)
					{
						String ngaybatdau=request.getParameter("ngaybatdau"+gsbh_kbhIds[n])==null?"":request.getParameter("ngaybatdau"+gsbh_kbhIds[n]).trim().length()<=0?getDateTime():request.getParameter("ngaybatdau"+gsbh_kbhIds[n]).trim();
						String ngayketthuc=request.getParameter("ngayketthuc"+gsbh_kbhIds[n])==null?"":request.getParameter("ngayketthuc"+gsbh_kbhIds[n]).trim().length()<=0?"2100-01-01":request.getParameter("ngayketthuc"+gsbh_kbhIds[n]).trim();

						query="insert into nhapp_giamsatbh(npp_fk, gsbh_fk,ngaybatdau,ngayketthuc) " +
								"values('" + this.id + "','" + this.gsbh_kbhIds[n] + "','"+ngaybatdau+"','"+ngayketthuc+"')";

						if(!cn.update(query))
						{   
							cn.update("rollback");
							this.msg = "Lỗi phát sinh trong quá trình cập nhật "+query;
							return false;
						}
						query=
								"insert into DDKD_GSBH(DDKD_FK,GSBH_FK) "+
										" SELECT pk_seq,'"+gsbh_kbhIds[n]+"' " +
										" FROM DAIDIENKINHDOANH WHERE NPP_FK IN ("+this.id+")  ";
						if(!cn.update(query))
						{
							this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
							cn.update("rollback");
							return false;
						}					
					}
					n++;
				}
			}


			/*query=
					"insert into nhapp_kbh(npp_fk, kbh_fk) "+
							" select distinct "+this.id+",kbh_fk" +
							" from giamsatbanhang " +
							" where pk_Seq in (select gsbh_Fk from nhapp_giamsatbh where npp_fk ="+this.id+")";*/
			query=
					"insert into nhapp_kbh(npp_fk, kbh_fk) "+
							" select distinct "+this.id+",100021";
			if(!cn.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				cn.update("rollback");
				return false;
			}	



			/*query=
					" insert into BANGGIABANLENPP(ten,ngaytao,ngaysua,nguoitao,nguoisua,dvkd_fk,npp_fk,KBH_FK,BANGGIABANLECHUAN_FK) "+
							" select  bg.TEN,bg.NGAYTAO,bg.NGAYSUA,bg.NGUOITAO,bg.NGUOISUA,bg.DVKD_FK,npp.PK_SEQ ,bg.kenh_fk,bg.PK_SEQ  "+
							" from   "+
							"	NHAPP_NHACC_DONVIKD npp_dv   "+
							"	inner join nhacungcap_dvkd ncc_dv on ncc_dv.pk_seq=npp_dv.ncc_dvkd_fk   "+
							"	inner join nhaphanphoi npp on npp_dv.npp_fk= npp.pk_seq   "+
							"	inner join NHAPP_KBH on NHAPP_KBH.NPP_FK=npp.PK_SEQ   "+
							"	inner join BANGGIABANLECHUAN bg on bg.DVKD_FK = ncc_dv.DVKD_FK and bg.kenh_fk=NHAPP_KBH.KBH_FK  "+  
							" where ncc_dv.checked='1' and npp.trangthai='1'  "+ 
							" and not exists  "+ 
							" (  "+
							"	 select * from BANGGIABANLENPP bgl   "+
							"	 where bgl.NPP_FK=npp.PK_SEQ and bgl.kbh_fk=bg.kenh_fk "+
							"	 and bgl.DVKD_FK=bg.DVKD_FK  "+
							" )and npp.pk_seq="+this.id+" ";
			if(!cn.update(query))
			{	
				cn.update("rollback");
				this.msg = "Khong the cap nhat nhapp_kbh: " + query;
				return false; 
			}


			query=
					"	insert into BGBANLENPP_SANPHAM(BGBANLENPP_FK,GIABANLECHUAN,GIABANLENPP,SANPHAM_FK) "+  
							"	select bgnpp.PK_SEQ ,bgsp.GIABANLECHUAN,bgsp.GIABANLECHUAN,bgsp.SANPHAM_FK "+   
							"	from  "+
							"	BANGGIABANLECHUAN bg inner join  BANGGIABLC_SANPHAM bgsp on bgsp.BGBLC_FK=bg.PK_SEQ "+
							"	inner join BANGGIABANLENPP bgnpp on bgnpp.BANGGIABANLECHUAN_FK=bg.PK_SEQ "+
							"	where not exists "+  
							"	( 	 "+
							"		select * from BGBANLENPP_SANPHAM bgl 	 "+
							"		where bgl.SANPHAM_FK=bgsp.SANPHAM_FK and bgnpp.PK_SEQ=bgl.BGBANLENPP_FK  "+ 
							"	) and bgnpp.npp_fk='"+this.id+"' " ;

			System.out.println("[BGBANLENPP_SANPHAM]"+query);
			if (!(cn.update(query)))
			{
				this.msg = "Có lỗi xảy ra trong quá trình cập nhật "+query;
				cn.update("rollback");							
				return false;		
			}*/


			/*
			 * Chen nhung san pham chua co trong kho.
			 * Theo don vi kinh doanh va kenh ban hang cua npp
			 */
			/*query=
					"	insert into nhapp_kho(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE)   "+
							"	select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 as SoLuong,0 as Booked,0 as avail  from KHO kho,SANPHAM sp,KENHBANHANG kenh ,NHAPHANPHOI npp "+  
							"	where not exists "+  
							"	( 	 "+
							"		select * from  NHAPP_KHO a 	 "+
							"		where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ 	 "+
							"		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq   "+
							"	) and kenh.PK_SEQ in (select kbh_fk from NHAPP_KBH where NPP_FK="+this.id+")   and sp.DVKD_FK  in (select b.DVKD_FK from NHAPP_NHACC_DONVIKD a inner join NHACUNGCAP_DVKD b on a.NCC_DVKD_FK=b.PK_SEQ and a.NPP_FK="+this.id+" ) "+ 
							"	and npp.pk_Seq="+this.id+" ";
			if(!cn.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				cn.update("rollback");
				return false;
			}	*/



			if (this.ngaydhIds != null) {
				int size = (this.ngaydhIds).length;
				int m = 0;

				while (m < size) {
					if (this.ngaydhIds[m] != null) {
						String sql = "insert into npp_ngaydh( npp_fk, ndh_fk) values('"
								+ this.id + "','" + this.ngaydhIds[m] + "')";
						System.out.println("4.tao NPP_NGAYDH : " + sql);
						if (!cn.update(sql)) {
							cn.update("rollback");
							this.msg = "Khong the cap nhat bang npp_ngaydh";
							return false;
						}
					}
					m++;
				}
			}
			/*query = "insert into nhanvien(ten,dangnhap,matkhau,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,phanloai,convsitecode, sessionid) values("
					+ "N'"+ this.ten+ "','"	+ this.ma+ "', pwdencrypt('"+ this.ma+ "'),1,'"	+ this.getDateTime()+ "','"	+ this.getDateTime()+ "','"
					+ this.userId+ "','"+ this.userId + "','1','" + this.ma + "','2012-01-01')";
			if (!cn.update(query)) {
				cn.update("rollback");
				this.msg = "Khong the cap nhat bang npp_ngaydh";
				return false;
			}*/

			if (this.ttppId.length() > 0) {
				query = " insert into  TTPP_NPP(ttpp_fk,npp_fk) select pk_Seq,'"+ this.id+ "' from trungtamphanphoi where pk_Seq in ("+ this.ttppId + ") ";
				if (!cn.update(query)) {
					cn.update("rollback");
					this.msg = "Khong the cap nhat bang TTPP_NPP" + query;
					return false;
				}
			}

			/*query = "select distinct nhanvien_fk,npp.khuvuc_fk from phamvihoatdong inner join nhaphanphoi npp on npp_fk=npp.pk_seq where khuvuc_fk="+ this.kvId;
			System.out.println(query);
			rs = cn.get(query);
			while (rs.next()) 
			{
				query = "insert into phamvihoatdong(nhanvien_fk,npp_fk) values("+ rs.getString("nhanvien_fk") + ",'" + id + "') ";
				if (!cn.update(query)) 
				{
					cn.update("rollback");
					this.msg = "Khong the cap nhat loi :" + query;
					return false;
				}
			}

			query = " insert into PHAMVIHOATDONG(Nhanvien_fk,Npp_fk) "+ " select '" + this.userId + "','" + this.id + "'   "
					+ "	where not exists " + "	( "+ "	select * from PHAMVIHOATDONG where Nhanvien_fk='"+ this.userId + "'  " + "	and Npp_fk='" + this.id + "' "	+ " ) ";

			if (!cn.update(query)) {
				cn.update("rollback");
				this.msg = "Khong the cap nhat loi :" + query;
				return false;
			}*/

			if (rs != null) {
				rs.close();
			}

			/*query = " INSERT INTO LichSu_GSBH_NPP(NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC) "
					+ "	SELECT NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC FROM NHAPP_GIAMSATBH A  "
					+ "	WHERE NOT EXISTS  "+ "	(  "+ "		SELECT * FROM LichSu_GSBH_NPP B  "
					+ "	WHERE B.NPP_FK=A.NPP_FK AND A.GSBH_FK=B.GSBH_FK "
					+ "	) AND A.NPP_FK=" + this.id + " ";
			if (!cn.update(query)) {
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật " + query;
				cn.update("rollback");
				return false;
			}*/
			query = " UPDATE NHAPHANPHOI SET TIMKIEM =UPPER(DBO.FTBODAU(TEN))+' '+UPPER(DBO.FTBODAU(ISNULL(DIACHI,'')))+' '+UPPER(DBO.FTBODAU(ISNULL(MA,''))) +' '+ISNULL(DIENTHOAI,'') WHERE PK_SEQ='"+this.id+"' "; 
			if(!(cn.update(query)))
			{
				this.msg="Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
				cn.update("rollback");
				return false;
			}

			/*query =
					"	insert into nhapp_kho(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE)    "+
							"	select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 "+ 
							"	as SoLuong,0 as Booked,0 as avail  from KHO kho,SANPHAM sp, "+
							"		KENHBANHANG kenh ,NHAPHANPHOI npp "+ 
							"		where not exists "+ 
							"	( 	  "+
							"		select * from  NHAPP_KHO a 	 "+
							"		where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ 	 "+
							"		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq    "+
							"	) and kenh.PK_SEQ in (select kbh_fk from NHAPP_KBH where NPP_FK=npp.PK_SEQ) "+   
							"	and sp.DVKD_FK  in (select b.DVKD_FK from NHAPP_NHACC_DONVIKD a   "+
							"	inner join NHACUNGCAP_DVKD b on a.NCC_DVKD_FK=b.PK_SEQ and a.NPP_FK=npp.PK_SEQ)  "; 

			if(!(cn.update(query)))
			{
				this.msg="Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
				cn.update("rollback");
				return false;
			}*/
			cn.getConnection().commit();
			cn.getConnection().setAutoCommit(true);
		} catch (Exception e) {

			this.msg = "Vui long kiem tra lai du lieu Ban da nhap"
					+ e.toString();
			cn.update("rollback");
			return false;
		}

		return true;
	}

	public boolean UpdateNpp(HttpServletRequest request) {
		dbutils cn = new dbutils();
		try {

			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;

			cn.getConnection().setAutoCommit(false);

			String query = "";
			query = "update nhaphanphoi set IsChiNhanh='"+ this.isChiNhanh+ "',maddt=N'"+ this.maddt+ "',khosap='"+ this.IdKhoTT+ "', masothue='"	+ this.masothue	+ "', diachixhd=N'"	+ this.getDiaChiXuatHoaDon()+ "', ten=N'"+ this.ten	+ "', dienthoai='"	+ this.sodienthoai+ "', diachi=N'"	+ this.diachi+ "', khuvuc_fk = '"+ this.kvId+ "',  ngaysua = '"	+ this.ngaysua+ "',  nguoisua = '"+ this.nguoisua	+ "', trangthai = '"+ this.trangthai+ "', ma='"	+ this.ma+ "', pass ='"	+ this.pass	+ "', tinhthanh_fk='"+ this.tpId+ "', quanhuyen_fk='"+ this.qhId+ "',convsitecode=sitecode,"+ "ngaybatdau = '"	+ this.tungay	+ "',ngayketthuc='" + this.denngay+ "', dckho = N'"	+ this.dckho+ "', giayphepkd ='"+ this.giayphepkd+ "', sotaikhoannh = '"	+ this.sotk	+ "', fax = '"		+ this.fax+ "',DiaBanHd=N'"	+ this.DiaBanHd+ "' "+ ",NganHang=N'"+ this.NganHangId	+ "',ChiNhanh=N'"	+ this.ChiNhanhId+"',chunhaphanphoi='"+ this.ChuNhaPhanPhoi+"',muahangtu='"+ this.MuaHangTu+"',lichdathang='"+ this.LichDatHang+"',quytrinhbanhang='"+ this.QuyTrinhBanHang+ "',ChuTaiKhoan=N'"+ this.ChuTaiKhoan+ "',SoNgayNo="	+ (this.songayno.length() <= 0 ? "NULL" : this.songayno)+ ",SoTienNo="+ (this.sotienno.length() <= 0 ? "NULL" : this.sotienno)+ ",LoaiNPP="+ (this.loainpp.length() <= 0 ? "NULL" : this.loainpp)	+ ",Tructhuoc_fk="	+ (this.tructhuocId.length() <= 0 ? "NULL": this.tructhuocId) + "  " + ", nguoidaidien = N'"+ this.nguoidaidien + "', diachi2 = N'"+this.diachi2+"', tansuatdathang = '"+this.tsdathang+"' where pk_seq = '" + this.id + "'";
			if (!cn.update(query)) 
			{
				cn.update("rollback");
				this.msg = "Khong the cap nhat nhaphanphoi: " + query;
				return false;
			}

			query = "Delete from nhapp_nhacc_donvikd where npp_fk='" + this.id+ "'";
			if (!cn.update(query)) 
			{
				cn.update("rollback");
				this.msg = "Lỗi phát sinh trong quá trình cập nhật " + query;
				return false;
			}

			query = "delete from  nhapp_giamsatbh where npp_fk=" + this.id + "";
			if (!cn.update(query)) 
			{
				cn.update("rollback");
				this.msg = "Lỗi phát sinh trong quá trình cập nhật " + query;
				return false;
			}

			query = "delete from DDKD_GSBH WHERE DDKD_FK IN (SELECT PK_SEQ FROM DAIDIENKINHDOANH WHERE NPP_FK ="+ this.id + " )";
			if (!cn.update(query)) 
			{
				cn.update("rollback");
				this.msg = "Lỗi phát sinh trong quá trình cập nhật " + query;
				System.out.println("Error__" + query);
				return false;
			}

			query = " delete nhapp_kbh where npp_fk=" + this.id + " ";
			if (!cn.update(query)) 
			{
				cn.update("rollback");
				this.msg = "Lỗi phát sinh trong quá trình cập nhật " + query;
				return false;
			}

			query = "delete npp_ngaydh where npp_fk = '" + this.id + "'";
			if (!cn.update(query)) 
			{
				cn.update("rollback");
				this.msg = "Lỗi phát sinh trong quá trình cập nhật " + query;
				return false;
			}

			query = "delete TTPP_NPP where npp_fk = '" + this.id + "'";
			if (!cn.update(query)) 
			{
				cn.update("rollback");
				this.msg = "Lỗi phát sinh trong quá trình cập nhật " + query;
				return false;
			}

			if (this.ttppId.length() > 0) 
			{
				query = " insert into  TTPP_NPP(ttpp_fk,npp_fk) select pk_Seq,'"+ this.id+ "' from trungtamphanphoi where pk_Seq in ("+ this.ttppId + ") ";
				if (!cn.update(query)) 
				{
					cn.update("rollback");
					this.msg = "Khong the cap nhat bang TTPP_NPP";
					return false;
				}
			}


			if (this.dvkd_nccIds != null)
			{
				int size = (this.dvkd_nccIds).length;
				int m = 0;
				while(m < size)				
				{	
					query = "insert into nhapp_nhacc_donvikd values('"+ this.id  +"','" + this.dvkd_nccIds[m] + "')";
					System.out.println("2. Tao NPP_NCC_DVKD : " +query ) ; 
					if(!cn.update(query))
					{
						cn.getConnection().rollback();
						this.msg = "Khong the cap nhat bang nhapp_nhacc_donvikd: " + query;
						return false; 
					}
					m++;
				}
			}

			if (this.ngaydhIds != null) 
			{
				int size = (this.ngaydhIds).length;
				int m = 0;

				while (m < size) 
				{
					if (this.ngaydhIds[m] != null) 
					{
						String sql = "insert into npp_ngaydh( npp_fk, ndh_fk) values('"+ this.id + "','" + this.ngaydhIds[m] + "')";
						if (!cn.update(sql)) 
						{
							cn.update("rollback");
							this.msg = "Khong the cap nhat bang npp_ngaydh";
							return false;
						}
					}
					m++;
				}
			}

			if (this.gsbh_kbhIds != null)
			{
				int size = (this.gsbh_kbhIds).length;
				int n = 0;

				while(n < size)
				{
					if(this.gsbh_kbhIds[n] != null)
					{
						String ngaybatdau=request.getParameter("ngaybatdau"+gsbh_kbhIds[n])==null?"":request.getParameter("ngaybatdau"+gsbh_kbhIds[n]).trim().length()<=0?getDateTime():request.getParameter("ngaybatdau"+gsbh_kbhIds[n]).trim();
						String ngayketthuc=request.getParameter("ngayketthuc"+gsbh_kbhIds[n])==null?"":request.getParameter("ngayketthuc"+gsbh_kbhIds[n]).trim().length()<=0?"2100-01-01":request.getParameter("ngayketthuc"+gsbh_kbhIds[n]).trim();

						query="insert into nhapp_giamsatbh(npp_fk, gsbh_fk,ngaybatdau,ngayketthuc) " +
								"values('" + this.id + "','" + this.gsbh_kbhIds[n] + "','"+ngaybatdau+"','"+ngayketthuc+"')";

						if(!cn.update(query))
						{   
							cn.update("rollback");
							this.msg = "Lỗi phát sinh trong quá trình cập nhật "+query;
							return false;
						}
						query=
								"insert into DDKD_GSBH(DDKD_FK,GSBH_FK) "+
										" SELECT pk_seq,'"+gsbh_kbhIds[n]+"' " +
										" FROM DAIDIENKINHDOANH WHERE NPP_FK IN ("+this.id+")  ";
						if(!cn.update(query))
						{
							this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
							cn.update("rollback");
							return false;
						}					
					}
					n++;
				}
			}

			query=
					"insert into nhapp_kbh(npp_fk, kbh_fk) "+
							" select distinct "+this.id+",kbh_fk" +
							" from giamsatbanhang " +
							" where pk_Seq in (select gsbh_Fk from nhapp_giamsatbh where npp_fk ="+this.id+")";
			if(!cn.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				cn.update("rollback");
				return false;
			}


			/*query=
					" insert into BANGGIABANLENPP(ten,ngaytao,ngaysua,nguoitao,nguoisua,dvkd_fk,npp_fk,KBH_FK,BANGGIABANLECHUAN_FK) "+
							" select  bg.TEN,bg.NGAYTAO,bg.NGAYSUA,bg.NGUOITAO,bg.NGUOISUA,bg.DVKD_FK,npp.PK_SEQ ,bg.kenh_fk,bg.PK_SEQ  "+
							" from   "+
							"	NHAPP_NHACC_DONVIKD npp_dv   "+
							"	inner join nhacungcap_dvkd ncc_dv on ncc_dv.pk_seq=npp_dv.ncc_dvkd_fk   "+
							"	inner join nhaphanphoi npp on npp_dv.npp_fk= npp.pk_seq   "+
							"	inner join NHAPP_KBH on NHAPP_KBH.NPP_FK=npp.PK_SEQ   "+
							"	inner join BANGGIABANLECHUAN bg on bg.DVKD_FK = ncc_dv.DVKD_FK and bg.KENH_FK=NHAPP_KBH.KBH_FK  "+  
							" where ncc_dv.checked='1' and npp.trangthai='1'  "+ 
							" and not exists  "+ 
							" (  "+
							"	 select * from BANGGIABANLENPP bgl   "+
							"	 where bgl.NPP_FK=npp.PK_SEQ and bgl.kbh_fk=bg.kenh_fk "+
							"	 and bgl.DVKD_FK=bg.DVKD_FK  "+
							" )and npp.pk_seq="+this.id+" ";
			if(!cn.update(query))
			{	
				cn.update("rollback");
				this.msg = "Khong the cap nhat nhapp_kbh: " + query;
				return false; 
			}

			query=
					"	insert into BGBANLENPP_SANPHAM(BGBANLENPP_FK,GIABANLECHUAN,GIABANLENPP,SANPHAM_FK) "+  
							"	select bgnpp.PK_SEQ ,bgsp.GIABANLECHUAN,bgsp.GIABANLECHUAN,bgsp.SANPHAM_FK "+   
							"	from  "+
							"	BANGGIABANLECHUAN bg inner join  BANGGIABLC_SANPHAM bgsp on bgsp.BGBLC_FK=bg.PK_SEQ "+
							"	inner join BANGGIABANLENPP bgnpp on bgnpp.BANGGIABANLECHUAN_FK=bg.PK_SEQ "+
							"	where not exists "+  
							"	( 	 "+
							"		select * from BGBANLENPP_SANPHAM bgl 	 "+
							"		where bgl.SANPHAM_FK=bgsp.SANPHAM_FK and bgnpp.PK_SEQ=bgl.BGBANLENPP_FK  "+ 
							"	) and bgnpp.npp_fk='"+this.id+"' " ;

			System.out.println("[BGBANLENPP_SANPHAM]"+query);
			if (!(cn.update(query)))
			{
				this.msg = "Có lỗi xảy ra trong quá trình cập nhật "+query;
				cn.update("rollback");							
				return false;		
			}*/

			/*
			 * Chen nhung san pham chua co trong kho.
			 * Theo don vi kinh doanh va kenh ban hang cua npp
			 */
			/*query=
					"	insert into nhapp_kho(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE)   "+
							"	select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 as SoLuong,0 as Booked,0 as avail  from KHO kho,SANPHAM sp,KENHBANHANG kenh ,NHAPHANPHOI npp "+  
							"	where not exists "+  
							"	( 	 "+
							"		select * from  NHAPP_KHO a 	 "+
							"		where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ 	 "+
							"		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq   "+
							"	) and kenh.PK_SEQ in (select kbh_fk from NHAPP_KBH where NPP_FK="+this.id+")   and sp.DVKD_FK  in (select b.DVKD_FK from NHAPP_NHACC_DONVIKD a inner join NHACUNGCAP_DVKD b on a.NCC_DVKD_FK=b.PK_SEQ and a.NPP_FK="+this.id+" ) "+ 
							"	and npp.pk_Seq="+this.id+" ";
			if(!cn.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				cn.update("rollback");
				return false;
			}	*/


			// kiem tra nha phan phối này có user đang nhập chưa?,chua có thì
			// tạo mới nhân viên.
			/*query = " select npp.pk_seq,npp.sitecode,npp.ten ,isnull( nv.dangnhap,'')  as dangnhap from  nhaphanphoi npp  left join "+ 
					" nhanvien nv  on nv.convsitecode=sitecode where  npp.pk_seq='"+ this.id + "'  ";

			ResultSet rs = cn.get(query);
			if (rs.next()) 
			{
				// tao moi nhan vien
				if (rs.getString("dangnhap").equals("")) 
				{
					query = "insert into nhanvien(ten,dangnhap,matkhau,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,phanloai,convsitecode, sessionid) values("+ "N'"+ this.ten+ "','"+ this.ma+ "', pwdencrypt('"	+ this.ma+ "'),1,'"	+ this.getDateTime()+ "','"	+ this.getDateTime()+ "','"	+ this.userId+ "','"+ this.userId+ "','1','"+ rs.getString("sitecode") + "','2012-01-01')";
					if (!cn.update(query)) 
					{
						cn.update("rollback");
						this.msg = "Khong the cap nhat bang npp_ngaydh";
						return false;
					}
				}
			}
			rs.close();
			query = "select sitecode from nhaphanphoi where pk_seq='" + this.id+ "'";
			rs = cn.get(query);
			String sitecode = "";
			while (rs.next()) 
			{
				sitecode = rs.getString("sitecode");
			}
			rs.close();
			query = "update nhanvien set trangthai='" + this.trangthai+ "' where CONVSITECODE='" + sitecode + "'";
			if (!cn.update(query)) 
			{
				cn.update("rollback");
				this.msg = "Khong the cap nhat loi :" + query;
				return false;
			}*/
			// System.out.println("Cap nhat trang thai cua nhan vien"+query);
			/*query = " select distinct nhanvien_fk,npp.khuvuc_fk from phamvihoatdong "+ 
					" inner join nhaphanphoi npp on npp_fk=npp.pk_seq where khuvuc_fk="+ this.kvId+ " and nhanvien_fk not in (select nhanvien_fk from phamvihoatdong where npp_fk="+ this.id + ")";
			System.out.println(query);
			ResultSet rs = cn.get(query);
			while (rs.next()) 
			{
				query = "insert into phamvihoatdong(nhanvien_fk,npp_fk) values("+ rs.getString("nhanvien_fk") + ",'" + id + "') ";
				if (!cn.update(query)) 
				{
					cn.update("rollback");
					this.msg = "Khong the cap nhat loi :" + query;
					return false;
				}
			}
			if (rs != null)
				rs.close();
			
			
			// Cập nhật lại Ngày kết thúc cho GSBH cũ = Ngày bắt đầu -1 của GS mới của NPP (Nếu có)
			query = "select COUNT(*) dem from LichSu_GSBH_NPP where NPP_FK = "+ this.id +" ";
			ResultSet rsKT = db.get(query);
			int dem = 0;
			while (rsKT.next()) 
			{
				dem = rsKT.getInt("dem");
			}
			if(rsKT!= null)
				rsKT.close();
			
			if(dem > 0)
			{
				query = " UPDATE K set K.NGAYKETTHUC = CONVERT(VARCHAR(10), DATEADD(day,-1, K.NGAYBATDAU),21) "+
						" FROM "+
						" (SELECT TOP 1 a.NgayKetThuc, b.NGAYBATDAU  "+
						" FROM LichSu_GSBH_NPP a inner join NHAPP_GIAMSATBH b on a.NPP_FK = b.NPP_FK "+
						" WHERE a.NPP_FK = "+ this.id +"  "+
						" ORDER BY a.NgayBatDau desc) K ";
				if (!cn.update(query)) {
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật " + query;
					cn.update("rollback");
					return false;
				}
			}
			
			query = " INSERT INTO LichSu_GSBH_NPP(NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC) "
					+ "	SELECT NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC FROM NHAPP_GIAMSATBH A  "
					+ "	WHERE NOT EXISTS  "	+ "	(  "
					+ "							SELECT * FROM LichSu_GSBH_NPP B  "
					+ "							WHERE B.NPP_FK=A.NPP_FK AND A.GSBH_FK=B.GSBH_FK "
					+ "	) AND A.NPP_FK=" + this.id + " ";
			if (!cn.update(query)) {
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật " + query;
				cn.update("rollback");
				return false;
			}

			query = "UPDATE A SET NGAYBATDAU=B.NGAYBATDAU,NGAYKETTHUC=B.NGAYKETTHUC FROM LichSu_GSBH_NPP A INNER JOIN "
					+ "		NHAPP_GIAMSATBH B ON B.NPP_FK=A.NPP_FK  AND B.GSBH_FK=A.GSBH_FK "
					+ "		WHERE A.NPP_FK=" + this.id + " ";
			if (!cn.update(query)) 
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật " + query;
				cn.update("rollback");
				return false;
			}*/
			query = " UPDATE NHAPHANPHOI SET TIMKIEM =UPPER(DBO.FTBODAU(TEN))+' '+UPPER(DBO.FTBODAU(ISNULL(DIACHI,'')))+' '+UPPER(DBO.FTBODAU(ISNULL(MA,''))) +' '+ISNULL(DIENTHOAI,'') WHERE PK_SEQ='"+this.id+"' "; 
			if(!(cn.update(query)))
			{
				this.msg="Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
				cn.update("rollback");
				return false;
			}
			/*query =
					"	insert into nhapp_kho(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE)    "+
							"	select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 "+ 
							"	as SoLuong,0 as Booked,0 as avail  from KHO kho,SANPHAM sp, "+
							"		KENHBANHANG kenh ,NHAPHANPHOI npp "+ 
							"		where not exists "+ 
							"	( 	  "+
							"		select * from  NHAPP_KHO a 	 "+
							"		where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ 	 "+
							"		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq    "+
							"	) and kenh.PK_SEQ in (select kbh_fk from NHAPP_KBH where NPP_FK=npp.PK_SEQ) "+   
							"	and sp.DVKD_FK  in (select b.DVKD_FK from NHAPP_NHACC_DONVIKD a   "+
							"	inner join NHACUNGCAP_DVKD b on a.NCC_DVKD_FK=b.PK_SEQ and a.NPP_FK=npp.PK_SEQ)  "; 

			if(!(cn.update(query)))
			{
				this.msg="Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
				cn.update("rollback");
				return false;
			}*/

			cn.getConnection().commit();
			cn.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "Exception " + e.getMessage();
			cn.update("rollback");
			return false;
		} finally {
			if (cn != null)
				cn.shutDown();
		}
		return true;
	}

	public ResultSet createTpRS() {
		ResultSet tpRS = this.db
				.get("select ten as tpTen, pk_seq as tpId from tinhthanh order by ten");
		return tpRS;
	}

	public ResultSet createQhRS() {
		ResultSet qhRS = null;
		if (this.tpId == null) {
			this.tpId = "";
		}
		if (this.tpId.length() > 0) {
			qhRS = this.db
					.get("select ten as qhTen, pk_seq as qhId from quanhuyen where tinhthanh_fk='"
							+ this.tpId + "' order by ten");
		} else {
			qhRS = this.db
					.get("select ten as qhTen, pk_seq as qhId from quanhuyen order by ten");
		}

		return qhRS;
	}

	public ResultSet createKvRS() {
		ResultSet kvRS = this.db
				.get("select ten as kvTen, pk_seq as kvId from khuvuc order by ten");
		return kvRS;
	}

	public ResultSet createNpptnRS() {
		// Lay ra nhung npp da ngung hoat dong va phai co du lieu
		ResultSet npptnRS = this.db.get("select pk_seq as npptnId, ten as npptnTen from nhaphanphoi where sitecode=convsitecode order by ten");
		return npptnRS;
	}

	public int CountResultSet(ResultSet Rs){
		try {

			Rs.last();
			int k = Rs.getRow();
			Rs.beforeFirst();
			System.out.println("So dong trong ResultSet: "+ k);			
			Rs.beforeFirst();
			return k;
		} catch(Exception e){
			return 0;
		}

	}
	public void setArrayDvkdSelected(){
		ResultSet Rs = this.dvkd_nccSelected;
		if(Rs!=null){

			int n = CountResultSet(Rs);
			String [] dvkdid = new String[n];
			try {
				int i = 0;
				while(Rs.next()){	    				
					dvkdid[i] = Rs.getString("dvkd_nccId");
					i++;        			
				}    			
				Rs.beforeFirst();
				this.dvkd_nccIds = dvkdid;
			} catch (Exception e) {
				System.out.println("Loi roi!");
			}


		}
	}

	public void setArrayGsbhSelected(){
		ResultSet Rs = this.gsbh_kbhSelected;
		if(Rs!=null){

			int n = CountResultSet(Rs);
			String [] gsbhid = new String[n];
			try {
				int i = 0;
				while(Rs.next()){	    				
					gsbhid[i] = Rs.getString("gsbhId");
					i++;        			
				}    			
				Rs.beforeFirst();
				this.gsbh_kbhIds = gsbhid;
			} catch (Exception e) {
				System.out.println("Loi roi!");
			}


		}
	}

	public void setArrayNgaydhSelected(){
		ResultSet Rs = this.NgaydhSelected;
		if(Rs!=null){

			int n = CountResultSet(Rs);
			String [] ngaydhid = new String[n];
			try {
				int i = 0;
				while(Rs.next()){	    				
					ngaydhid[i] = Rs.getString("pk_seq");
					i++;        			
				}    			
				Rs.beforeFirst();
				this.ngaydhIds = ngaydhid;
			} catch (Exception e) {
				System.out.println("Loi roi!");
			}


		}
	}
	public void createDvkd_NccList() {
		if (this.id.length() > 0) {
			String query = "select b.pk_seq as dvkd_nccId, c.donvikinhdoanh as dvkdTen, c.pk_seq as dvkdId, d.ten as nccTen, d.pk_seq as nccId";
			query = query + " from nhapp_nhacc_donvikd a inner join nhacungcap_dvkd b on a.ncc_dvkd_fk = b.pk_seq inner join donvikinhdoanh c on b.dvkd_fk = c.pk_seq";
			query = query + " inner join nhacungcap d on b.ncc_fk = d.pk_seq";
			query = query + " where a.npp_fk='" + this.id + "'";
			this.dvkd_nccSelected = this.db.getScrol(query);
			//setArrayDvkdSelected(this.dvkd_nccSelected);

			String query2 = "select DISTINCT a.pk_seq as dvkd_nccId, b.donvikinhdoanh as dvkdTen, b.pk_seq as dvkdId, c.ten as nccTen, "
					+ " c.pk_seq as nccId from nhacungcap_dvkd a inner join donvikinhdoanh b on a.dvkd_fk = b.pk_seq and a.checked ='1'"
					+ " inner join nhacungcap c on a.ncc_fk = c.pk_seq   where a.pk_seq not in(select b.pk_seq from nhapp_nhacc_donvikd a "
					+ " inner join nhacungcap_dvkd b on a.ncc_dvkd_fk = b.pk_seq inner join donvikinhdoanh dvkd on dvkd.pk_seq=b.dvkd_fk  "
					+ " where b.checked=1 and dvkd.trangthai='1' and npp_fk="
					+ this.id + "  ) and b.trangthai='1'";

			this.dvkd_nccList = this.db.get(query2);
		} else {
			String query2 = "select distinct a.pk_seq as dvkd_nccId, b.donvikinhdoanh as dvkdTen, b.pk_seq as dvkdId, c.ten as nccTen, c.pk_seq as nccId from nhacungcap_dvkd a, donvikinhdoanh b, nhacungcap c where a.ncc_fk =c.pk_seq and a.dvkd_fk = b.pk_seq and a.checked='1' and b.trangthai ='1'";
			this.dvkd_nccList = this.db.get(query2);

		}
	}

	public void createGsbh_KbhList() {
		if (this.id.length() > 0) {
			String query = "select  distinct  a.pk_seq as gsbh_kbhId, a.ten as gsbhTen, a.pk_seq as gsbhId, a.dienthoai as sodienthoai, "
					+ "c.diengiai as kbhTen, c.pk_seq as kbhId, b.ngaybatdau, b.ngayketthuc "
					+ "from giamsatbanhang a "
					+ "inner join gsbh_khuvuc gs_kv on a.pk_seq = gs_kv.gsbh_fk "
					+ "inner join nhapp_giamsatbh b on a.pk_seq = b.gsbh_fk and a.trangthai ='1' "
					+ "inner join NHAPHANPHOI npp on npp.PK_SEQ = b.NPP_FK  "
					+ "inner join kenhbanhang c on a.kbh_fk = c.pk_seq where b.npp_fk='"
					+ this.id + "'";

			System.out.println("[Query_GSBH ]" + query);

			this.gsbh_kbhSelected = this.db.getScrol(query);
			//setArrayGsbhSelected(this.gsbh_kbhSelected);
			query =

					"select distinct a.pk_seq as gsbh_kbhId, a.ten as gsbhTen, a.pk_seq as gsbhId, a.dienthoai as sodienthoai, "
							+ "b.diengiai as kbhTen, b.pk_seq as kbhId,'' as ngaybatdau,'' as  ngayketthuc "
							+ "from giamsatbanhang a inner join gsbh_khuvuc on   gsbh_khuvuc.gsbh_fk=a.pk_seq  "
							+ "inner join kenhbanhang b on a.kbh_fk = b.pk_seq and a.trangthai ='1' and gsbh_khuvuc.khuvuc_fk = "+ this.kvId
							+ "where a.pk_seq not in(select a.gsbh_fk from nhapp_giamsatbh a inner join giamsatbanhang b on a.gsbh_fk = b.pk_seq where a.npp_fk='"
							+ this.id + "' and a.NGAYKETTHUC >= '"+ this.getDateTime()
							+ "') "
							+ "union "
							+ "select distinct a.pk_seq as gsbh_kbhId, a.ten as gsbhTen, a.pk_seq as gsbhId, "
							+ "a.dienthoai as sodienthoai, b.diengiai as kbhTen, b.pk_seq as kbhId,'' as ngaybatdau,'' as ngayketthuc "
							+ "from giamsatbanhang a "
							+ "inner join kenhbanhang b on a.kbh_fk = b.pk_seq and a.trangthai ='1'   "
							+ "inner join gsbh_khuvuc gsbh_kv on gsbh_kv.gsbh_fk=a.pk_seq   "
							+ "where a.pk_seq not in(select a.gsbh_fk from nhapp_giamsatbh a inner join giamsatbanhang b on a.gsbh_fk = b.pk_seq where a.npp_fk="
							+ this.id
							+ " and a.NGAYKETTHUC >= '"
							+ this.getDateTime()
							+ "') " + "and gsbh_kv.khuvuc_fk =" + this.kvId;

			System.out.println("[Query___]" + query);
			this.gsbh_kbhList = this.db.get(query);

		} else {
			String query = "select  a.pk_seq as gsbh_kbhId, a.ten as gsbhTen, a.pk_seq as gsbhId, a.dienthoai as sodienthoai, b.diengiai as kbhTen, b.pk_seq as kbhId "
					+ ",'' as ngaybatdau,'' as ngayketthuc from giamsatbanhang a inner join gsbh_khuvuc gs_kv on a.pk_seq = gs_kv.gsbh_fk and gs_kv.khuvuc_fk='"
					+ this.kvId
					+ "', kenhbanhang b where a.kbh_fk = b.pk_seq and a.trangthai ='1'";

			System.out.println("[Query_1111]" + query);

			this.gsbh_kbhList = this.db.get(query);
		}
	}

	public void createRS() {
		String query = "NULL";
		if (this.id.length() > 0)
			query = "select NPP_FK,GSBH_FK from NHAPP_GIAMSATBH where NPP_FK !='"
					+ this.id + "'";
		else
			query = "select NPP_FK,GSBH_FK from NHAPP_GIAMSATBH ";

		System.out.println("GSBH : " + query);
		ResultSet rs = this.db.get(query);
		String gs = "";

		if (rs != null) {
			try {
				while (rs.next())
					gs += rs.getString("GSBH_FK") + ",";
			} catch (Exception e) {
			}
		}
		this.gsbhnpp = gs;

		this.tp = this.createTpRS();
		this.qh = createQhRS();
		this.khuvucList = this.createKvRS();
		this.MuahangtuRs = CreateMuaHangTu();
		this.createDvkd_NccList();
		this.createGsbh_KbhList();
		this.createNgaydhList();

		query = "select pk_seq,ma,ten from LoaiNPP ";
		this.loaiNppRs = this.db.get(query);

		query = "select a.pk_seq as id, a.TEN as Ten, d.ten as nhacungcap, a.trangthai, a.ngaytao, ";
		query += "a.ngaysua, b.ten as nguoitao,  c.ten as nguoisua, d.pk_seq as nccId, a.MA ";
		query += "from TRUNGTAMPHANPHOI a, nhanvien b, nhanvien c, nhacungcap d, TTPP_NCC  e ";
		query += "where a.PK_SEQ = e.TTPP_FK and d.PK_SEQ = e.NCC_FK  and a.nguoitao = b.PK_SEQ and ";
		query +=  "a.nguoisua = c.PK_SEQ";
		//query = "select PK_SEQ as id,ma,TEN from TRUNGTAMPHANPHOI";
		this.ttppRs = this.db.get(query);

		query = "select PK_SEQ as id,ma,TEN from TRUNGTAMPHANPHOI";
		this.tructhuocRs = this.db.get(query);

		if (loainpp.equals("1")) {
			query = " select PK_SEQ as id,ma,TEN from NHAPHANPHOI where loainpp in (0,2) and trangthai=1  ";

		}

		if (loainpp.equals("2")) {
			query = " select PK_SEQ as id,ma,TEN from NHAPHANPHOI where loainpp in (0) and trangthai=1 ";

		}
		if (loainpp.equals("3")) {
			query = " select PK_SEQ as id,ma,TEN from NHAPHANPHOI where loainpp in (0) and trangthai=1 ";

		}
		this.tructhuocRs = this.db.get(query);
		System.out.println("____________" + query);
		query = "SELECT v.ten as vTen,kv.Ten as kvTen ,b.pk_Seq as nppId,b.ma as nppMA,b.Ten as nppTen,a.NgayBatDau,a.NgayKetThuc,KBH.TEN AS KBHtEN,GS.TEN AS GSBHTEN "
				+ "  FROM LichSu_GSBH_NPP A INNER JOIN NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK  "
				+ " INNER JOIN GIAMSATBANHANG GS ON GS.PK_sEQ=A.GSBH_FK  "
				+ " INNER JOIN KENHBANHANG KBH ON KBH.PK_sEQ=GS.KBH_FK  "
				+ "   left join khuvuc kv on kv.pk_Seq=b.khuvuc_Fk   "
				+ "		left join vung v on v.pk_Seq=kv.vung_fk  "
				+ " WHERE A.NPP_FK='" + this.id + "'  ";

		this.gsQlRs = this.db.get(query);

	}
	public ResultSet CreateMuaHangTu(){

		String tv = "select pk_seq, ten from  NHAPHANPHOI where 1=1";
		String query ="";
		if(this.loainpp.equals("1")){// Loại 1 là NPP/ Đại lý
			// mua từ Tổng thầu và DDT
			query = "select 0 as pk_seq, 'DDT' as ten ";
			query += "union all "+ tv + " and LoaiNPP = 4 and TrangThai = 1";

		}
		if(this.loainpp.equals("4")){ // Tổng thầu 
			// ma từ Trung tâm phân phối hoặc chi nhánh hoặc đại đồng tiến
			query = "select 102 as pk_seq, N'Trung tâm phân phối' as ten ";
			query += "union all select 0 as pk_seq, 'DDT' as ten ";
			query+="union all "+ tv+ " and LoaiNPP = 2 and TrangThai = 1";

		}

		if(this.loainpp.equals("5")){ // Cửa hàng hợp tác
			query =tv + " and LoaiNPP = 1 and TrangThai = 1";
			// Mua hàng từ NPP
		}
		if(this.loainpp.equals("3")){ // Show room
			// Mua hàng từ NPP -1000 để không hiển thị
			query= "select 0 as pk_seq, 'DDT' as ten";
		}
		if(this.loainpp.equals("2")){ // Chi nhánh
			// Mua hàng từ DDT, Trung tâm pp
			query += "select 0 as pk_seq, 'DDT' as ten ";
			query += "union all select 102 as pk_seq, N'Trung tâm phân phối' as ten ";
		}

		if(query==""){
			query=tv;
		}


		this.MuahangtuRs = this.db.get(query);
		System.out.println("Query mua hang tu: "+ query);
		return this.MuahangtuRs;
	}
	private String getinfonpp(String id) {
		String sql = "select * from NHAPHANPHOI npp ,NHANVIEN nv "
				+ "where npp.SITECODE=nv.CONVSITECODE and nv.PK_SEQ=" + id;
		return "";
	}

	private void init() {
		String query = " select a.khosap as khott,a.masothue,a.diachixhd,a.pk_seq as id, a.ten as nppTen, a.dienthoai, a.diachi, a.trangthai, "
				+ " a.ma, a.pass, a.ngaytao, b.nguoitao, a.ngaysua, c.nguoisua, d.ten as kvTen, d.pk_seq as kvId, a.priandsecond as "
				+ " prisec, a.tinhthanh_fk as tpId, a.quanhuyen_fk as qhId,a.ngaybatdau,a.ngayketthuc,a.dckho, a.giayphepkd, a.sotaikhoannh, a.fax,isnull(a.DiaBanHd,'')DiaBanHd ,"
				+ " ISNULL(a.NganHang,'')NganHang,ISNULL(a.ChiNhanh,'')ChiNhanh,ISNULL(a.ChuTaiKhoan,'')as ChuTaiKhoan,isnull(a.tentat,a.ten) as TenTat,a.IsChiNhanh,a.tructhuoc_fk as tructhuocId,a.SoNgayNo,a.SoTienNo,a.loainpp, ISNULL(a.nguoidaidien,'')as nguoidaidien "
				+ ",a.chunhaphanphoi, a.tonantoan,a.muahangtu,a.lichdathang,a.quytrinhbanhang, a.maddt, a.diachi2, a.tansuatdathang "
				+ "from "
				+ " nhaphanphoi a inner join nhanvien b on b.pk_seq=a.nguoitao "
				+ " inner join  nhanvien c on c.pk_seq=a.nguoisua "
				+ " left join  khuvuc d  on a.khuvuc_fk=d.pk_seq "
				+ " where  a.pk_seq = '" + this.id + "'";
		System.out.println("IN "+query);
		ResultSet rs = this.db.get(query);

		try {
			rs.next();



			this.ChuNhaPhanPhoi = rs.getString("chunhaphanphoi")==null ? "Na":rs.getString("chunhaphanphoi");
			this.TonAnToan=rs.getString("tonantoan")==null ? "0" :rs.getString("tonantoan");
			this.MuaHangTu=rs.getString("muahangtu")==null ? "0":rs.getString("muahangtu");
			this.LichDatHang=rs.getString("lichdathang")==null ? "Na":rs.getString("lichdathang");
			this.QuyTrinhBanHang=rs.getString("quytrinhbanhang")==null ? "Na":rs.getString("quytrinhbanhang");

			this.masothue = rs.getString("masothue");
			this.diachixhd = rs.getString("diachixhd");
			this.id = rs.getString("id");
			this.ten = rs.getString("nppTen");
			this.sodienthoai = rs.getString("dienthoai")== null ? "" : rs.getString("dienthoai");
			this.diachi = rs.getString("diachi");
			this.tpId = rs.getString("tpId");
			this.qhId = rs.getString("qhId");
			this.trangthai = rs.getString("trangthai");
			this.ma = rs.getString("ma");
			this.pass = rs.getString("pass");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");
			this.tungay = rs.getString("ngaybatdau")== null ? "" : rs.getString("ngaybatdau");
			this.denngay = rs.getString("ngayketthuc")== null ? "" : rs.getString("ngayketthuc");
			this.dckho = rs.getString("dckho");
			this.giayphepkd = rs.getString("giayphepkd");
			this.sotk = rs.getString("sotaikhoannh");
			this.fax = rs.getString("fax")== null ? "" : rs.getString("fax");
			this.dvkd = "";
			this.khuvuc = rs.getString("kvTen");
			this.kvId = rs.getString("kvId");
			this.DiaBanHd = rs.getString("DiaBanHd");
			this.NganHangId = rs.getString("NganHang");
			this.ChiNhanhId = rs.getString("ChiNhanh");
			this.ChuTaiKhoan = rs.getString("ChuTaiKhoan");
			this.tentat = rs.getString("TenTat");
			this.isChiNhanh = rs.getString("isChiNhanh") == null ? "0" : rs.getString("isChiNhanh");
			this.nguoidaidien = rs.getString("nguoidaidien");
			DecimalFormat df2 = new DecimalFormat("#,###,###");
			this.songayno = rs.getString("songayno") == null ? "" : df2.format(rs.getDouble("songayno"));
			this.sotienno = rs.getString("sotienno") == null ? "" : df2.format(rs.getDouble("sotienno"));
			this.loainpp = rs.getString("loainpp") == null ? "0" : rs.getString("loainpp");
			this.tructhuocId = rs.getString("tructhuocId") == null ? "0" : rs.getString("tructhuocId");
			this.IdKhoTT = rs.getString("khott") == null ? "" : rs.getString("khott");
			this.maddt = rs.getString("maddt")==null?"":rs.getString("maddt");
			this.diachi2 = rs.getString("diachi2")==null?"":rs.getString("diachi2");
			this.tsdathang = rs.getString("tansuatdathang")==null?"":rs.getString("tansuatdathang");
			
			String st = rs.getString("prisec");
			if (st == null)
				st = "0";
			this.prisec = st;
			this.npptnId = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		createRS();
		System.out.println("NPP ID: "+ this.id);
		query = "select ttpp_fk from TTPP_NPP where npp_fk='" + this.id + "'";
		rs = db.get(query);
		try {
			int i = 0;
			while (rs.next()) {
				if (i == 0) {
					this.ttppId += rs.getString("ttpp_fk");
				} else {
					this.ttppId += "," + rs.getString("ttpp_fk");
				}
				i++;
			}
			System.out.println("Trung tâm pp ID: "+ this.ttppId);
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void DBclose() {

		try {
			if (rsNganHang != null)
				this.rsNganHang.close();

			if (rsChiNhanh != null)
				this.rsChiNhanh.close();

			if (this.NgaydhSelected != null)
				this.NgaydhSelected.close();

			if (this.NgaydhList != null)
				this.NgaydhList.close();

			if (this.gsbh_kbhList != null)
				this.gsbh_kbhList.close();

			if (this.dvkd_nccList != null)
				this.dvkd_nccList.close();

			if (this.dvkd_nccSelected != null)
				this.dvkd_nccSelected.close();

			if (this.gsbh_kbhSelected != null)
				this.gsbh_kbhSelected.close();

			if (this.rs_khott != null)
				this.rs_khott.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (!(this.db == null)) {
			this.db.shutDown();
		}
	}

	public void setDiaChiXuatHoaDon(String _diachixhd) {

		this.diachixhd = _diachixhd;
	}

	public String getDiaChiXuatHoaDon() {

		return this.diachixhd;
	}

	public void setMaSoThue(String _masothue) {

		this.masothue = _masothue;
	}

	public String getmaSoThue() {

		return this.masothue;
	}

	public ResultSet getrs_khott() {

		dbutils db = new dbutils();
		ResultSet rs_khottnew = db
				.get("select pk_seq,ten from khott where trangthai!='2'");
		return rs_khottnew;
	}

	public void setKhoTT(String khott) {

		this.IdKhoTT = khott;
	}

	public String getKhoTT() {

		return this.IdKhoTT;
	}

	public ResultSet getNgaydhSelected() {

		return this.NgaydhSelected;
	}

	public void setNgaydhSelected(ResultSet ngaydhselected) {

		this.NgaydhSelected = ngaydhselected;
	}

	public ResultSet getNgaydhList() {

		return this.NgaydhList;
	}

	public void setNgaydhList(ResultSet ngaydhlist) {

		this.NgaydhList = ngaydhlist;
	}


	public Hashtable<Integer, String> getNgaydhIds() {
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.ngaydhIds != null) {
			int size = (this.ngaydhIds).length;
			int m = 0;
			while (m < size) {
				selected.put(new Integer(m), this.ngaydhIds[m]);
				m++;
			}
		} else {
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setNgaydhIds(String[] ngaydhIds) {
		this.ngaydhIds = ngaydhIds;
	}

	public void createNgaydhList() {
		String query = "";
		String query2 = "";
		if (this.id.length() > 0) {
			query = "select pk_seq, ngay from ngaydathang where pk_seq in (select ndh_fk from npp_ngaydh where npp_fk = '"
					+ this.id + "') ";
			this.NgaydhSelected = db.getScrol(query);
			//setArrayNgaydhSelected(this.NgaydhSelected);
			query2 = "select pk_seq, ngay from ngaydathang where pk_seq not in (select ndh_fk from npp_ngaydh where npp_fk = '"
					+ this.id + "')";
			this.NgaydhList = db.get(query2);
		} else {
			query = "select pk_seq,ngay from ngaydathang";
			this.NgaydhList = db.get(query);
		}
	}

	public String getTungay() {

		return tungay;
	}

	public void setTungay(String tungay) {

		this.tungay = tungay;
	}

	public String getDenngay() {

		return denngay;
	}

	public void setDenngay(String denngay) {

		this.denngay = denngay;
	}

	public String getDiaChiKho() {

		return dckho;
	}

	public void setDiaChiKho(String dckho) {

		this.dckho = dckho;
	}

	public String getGsbhnpp() {
		return this.gsbhnpp;
	}

	public void setGsbhnpp(String gsbhnpp) {
		this.gsbhnpp = gsbhnpp;
	}

	public String getSotk() {

		return sotk;
	}

	public void setSotk(String sotk) {

		this.sotk = sotk;
	}

	public String getGiayphepKD() {

		return giayphepkd;
	}

	public void setGiayphepKD(String gpkd) {

		this.giayphepkd = gpkd;
	}

	public String getFax() {

		return fax;
	}

	public void setFax(String fax) {

		this.fax = fax;
	}

	public String getDiaBanHd() {
		return this.DiaBanHd;
	}

	public void setDiaBanHd(String DiaBanHd) {
		this.DiaBanHd = DiaBanHd;

	}

	public String getNganHangId() {

		return this.NganHangId;
	}

	public void setNganHangId(String NganHangId) {
		this.NganHangId = NganHangId;

	}

	public String getChiNhanhId() {
		return this.ChiNhanhId;
	}

	public void setChiNhanhId(String ChiNhanhId) {
		this.ChiNhanhId = ChiNhanhId;
	}

	public String getChuTaiKhoan() {
		return this.ChuTaiKhoan;
	}

	public void setChuTaiKhoan(String ChuTaiKhoan) {
		this.ChuTaiKhoan = ChuTaiKhoan;

	}

	String tentat;

	public String getTentat() {
		return tentat;
	}

	public void setTentat(String tentat) {
		this.tentat = tentat;
	}

	ResultSet gsQlRs;

	public ResultSet getGsQlRs() {
		return gsQlRs;
	}

	public void setGsQlRs(ResultSet gsQlRs) {
		this.gsQlRs = gsQlRs;
	}

	String isChiNhanh;

	public String getIsChiNhanh() {
		return isChiNhanh;
	}

	public void setIsChiNhanh(String isChiNhanh) {
		this.isChiNhanh = isChiNhanh;
	}

	String songayno ="", sotienno, ttppId, tructhuocId, loainpp;
	ResultSet ttppRs, tructhuocRs, loaiNppRs;

	public ResultSet getLoaiNppRs() {
		return loaiNppRs;
	}

	public void setLoaiNppRs(ResultSet loaiNppRs) {
		this.loaiNppRs = loaiNppRs;
	}

	public ResultSet getTtppRs() {
		return ttppRs;
	}

	public void setTtppRs(ResultSet ttppRs) {
		this.ttppRs = ttppRs;
	}

	public String getSongayno() {
		return songayno;
	}

	public void setSongayno(String songayno) {
		this.songayno = songayno;
	}

	public String getSotienno() {
		return sotienno;
	}

	public void setSotienno(String sotienno) {
		this.sotienno = sotienno;
	}

	public String getTtppId() {
		return ttppId;
	}

	public void setTtppId(String ttppId) {
		this.ttppId = ttppId;
	}

	public String getTructhuocId() {
		return tructhuocId;
	}

	public void setTructhuocId(String tructhuocId) {
		this.tructhuocId = tructhuocId;
	}

	public String getLoaiNpp() {
		return loainpp;
	}

	public void setLoaiNpp(String loainpp) {
		this.loainpp = loainpp;
	}

	@Override
	public String getNguoidaidien() {

		return this.nguoidaidien;
	}

	@Override
	public void setNguoidaidien(String ndd) {

		this.nguoidaidien = ndd;
	}

	@Override
	public ResultSet getTructhuocRs() 
	{
		return null;
	}

	@Override
	public void setTructhuocRs(ResultSet tructhuocRs) 
	{


	}
	String maddt, diachi2, tsdathang;
	@Override
	public String getMaDDT() {
		return this.maddt;
	}

	@Override
	public void setMaDDT(String maddt) {
		this.maddt = maddt;
	}

	@Override
	public String getDiachi2() {
		return this.diachi2;
	}

	@Override
	public void setDiachi2(String diachi2) {
		this.diachi2 = diachi2;
	}

	@Override
	public String getTSdathang() {
		return this.tsdathang;
	}

	@Override
	public void setTSdathang(String tsdathang) {
		this.tsdathang = tsdathang;
	}





}
