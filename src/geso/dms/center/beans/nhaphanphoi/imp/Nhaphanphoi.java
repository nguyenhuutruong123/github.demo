package geso.dms.center.beans.nhaphanphoi.imp;

import geso.dms.center.beans.nhaphanphoi.INhaphanphoi;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import com.nhat.replacement.TaskReplacement;

public class Nhaphanphoi implements INhaphanphoi {
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
	String trangthaidms;
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
	String tansuatdh ="";
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

	String manpp;
	String diachi2;
	String nppc1;
	String pttangtruong;
	ResultSet nppc1Rs;
	// boolean checkgsbh;
	String diabanId = "";
	ResultSet diabanRs;

	double ChietKhau = 0;
	
	public double getChietKhau() {
		return ChietKhau;
	}
	public void setChietKhau(double chietKhau) {
		ChietKhau = chietKhau;
	}
	
	public Nhaphanphoi(String[] param) {
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
		this.kenhId = param[22];
		this.trangthaidms = param[23];
		this.isChiNhanh = "0";
		this.nguoidaidien = "";
		this.db = new dbutils();
		this.error = "";
		
		this.manpp = "";
		this.diachi2 = "";
		this.nppc1 = "";
		this.pttangtruong = "";
	}

	public Nhaphanphoi(String id) {
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
		this.trangthaidms = "1";
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
		this.kenhId ="";
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
		this.loainpp = "";
		this.tructhuocId = "";
		this.songayno = "";
		this.sotienno = "";
		this.ttppId = "";

		this.manpp = "";
		this.diachi2 = "";
		this.nppc1 = "";
		this.pttangtruong = "";
		
		this.db = new dbutils();
		if (id.length() > 0)
			this.init();
		else
			this.createRS();
	}

	public String getDiabanId() {
		return diabanId;
	}
	public void setDiabanId(String diabanId) {
		this.diabanId = diabanId;
	}
	public ResultSet getDiabanRs() {
		return db.get("select pk_seq, ten from diaban where trangthai = 1");
	}
	public void setDiabanRs(ResultSet diabanRs) {
		this.diabanRs = diabanRs;
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
		//system.out.println("Vao roi:");
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
	
	public String getTrangthaidms() {
		return this.trangthaidms;
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
	
	public void setTrangthaidms(String trangthaidms) {
		this.trangthaidms = trangthaidms;
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
		if (this.dvkd_nccIds!=null){
			for(int i=0;i<this.dvkd_nccIds.length;i++){
				idSelected+= (String)dvkd_nccIds[i]+"-";
			}
		}
		////system.out.println("Chuoi id duoc chon:"+ idSelected);
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
				//system.out.println("ID lua chon don vi kinh doanh: "+ this.dvkd_nccIds[m]);
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
		if (gsbh_kbhIds!=null){
			for(int i=0;i<(this.gsbh_kbhIds).length;i++){
				idSelected+=this.gsbh_kbhIds[i]+"-";
			}
		}
		return idSelected;

	}

	public String getNgayDh_IdSelected(){
		String idSelected="";
		if (ngaydhIds!=null){
			for(int i=0;i<(this.ngaydhIds).length;i++){
				idSelected+=this.ngaydhIds[i]+"-";
			}
		}
		return idSelected;

	}

	public boolean checkma(String ma)
	{  
		String sql;
		//if (this.id.length()==0){
		sql = "select count(*) as num from nhaphanphoi where ma ='" + this.ma + "'";
		//}else{
		//sql = "select count(*) as num from nhaphanphoi where pk_seq <> '"+ this.id +"' and ma ='" + this.ma + "'";
		//}
		//system.out.println("thong tin sp:"+sql);
		ResultSet rs = db.get(sql);
		if (rs != null)
			try 
		{
				rs.next();
				if (rs.getString("num").equals("0"))
					return false;
		} 
		catch (SQLException e){return false;}
		return true;
	}
	public static void main(String[] args) {
	
		
		TaskReplacement t = new TaskReplacement();
		String a = "update nhaphanphoi set chietkhau=\"+this.ChietKhau+\", diaban_fk=\"+(this.diabanId.equals(\"\")?\"null\":this.diabanId)+\",IsChiNhanh='\"+ this.isChiNhanh+ \"',TenTat=N'\"		+ this.tentat+ \"',khosap='\"		+ this.IdKhoTT+ \"', \"\r\n" + 
				"			+ \"\\n khuvuc_fk = \"+this.kvId+\",ngaysua = '\"+ this.ngaysua+ \"', \"\r\n" + 
				"			+ \"\\n nguoisua = '\"+ this.nguoisua	+ \"', \"\r\n" + 
				"			+ \"\\n priandsecond ='\"+ this.prisec+ \"', tinhthanh_fk=\"+( this.tpId.length() <= 0 ?null:this.tpId)+ \", quanhuyen_fk=\"+ (this.qhId.length() <= 0 ? null:this.qhId)+ \",convsitecode=sitecode,\"+ \"\"\r\n" + 
				"			+ \"\\n ngaybatdau = '\"+ this.tungay	+ \"',ngayketthuc='\" + this.denngay+ \"', dckho = N'\"	+ this.dckho+ \"', giayphepkd ='\"+ this.giayphepkd+ \"', \"\r\n" + 
				"			+ \"\\n sotaikhoannh = '\"+ this.sotk	+ \"', fax = '\"+ this.fax+ \"',DiaBanHd=N'\"+ this.DiaBanHd+ \"' \"+ \",NganHang=N'\"+ this.NganHangId	+ \"',\"\r\n" + 
				"			+ \"\\n ChiNhanh=N'\"	+ this.ChiNhanhId+\"',chunhaphanphoi='\"+ this.ChuNhaPhanPhoi+\"',tonantoan='\"+ this.TonAnToan.replaceAll(\",\", \"\")+\"',\"\r\n" + 
				"			+ \"\\n muahangtu='\"+ this.MuaHangTu+\"',lichdathang='\"+ this.LichDatHang+\"',trangthaidms='\"+ this.trangthaidms+\"',\"\r\n" + 
				"			+ \"\\n ChuTaiKhoan=N'\"+ this.ChuTaiKhoan+ \"',SoNgayNo=\"	+ (this.songayno.length() <= 0 ? \"null\" : this.songayno)+ \",\"\r\n" + 
				"			+ \"\\n SoTienNo=\"+ (this.sotienno.length() <= 0 ? \"null\" : this.sotienno)+ \",LoaiNPP=\"+ (this.loainpp.length() <= 0 ? \"null\" : this.loainpp)+ \",\"\r\n" + 
				"			+ \"\\n Tructhuoc_fk=\"+ (this.tructhuocId.length() <= 0 ? \"null\": this.tructhuocId) + \"  \" + \", nguoidaidien = N'\"+ this.nguoidaidien + \"', \"\r\n" + 
				"			+ \"\\n diachi2 = N'\"+this.diachi2+\"', pttangtruong = '\"+this.pttangtruong+\"', nppc1 = \"+(this.nppc1.length() <= 0 ? \"null\" : this.nppc1)+\",quytrinhbanhang = '\"+this.QuyTrinhBanHang+\"',TANSUATDATHANG = '\"+this.tansuatdh+\"', kbh_fk = \"+this.kenhId+\" \"\r\n" + 
				"			+ \"\\n where pk_seq = '\" + this.id + \"'";
		
		//system.out.println(t.taoQuery(a));
	}
	
	
	public boolean Trung_ma_npp (Idbutils db , String id,String ma) throws Exception
	{
		String query = " select count(*)ss from nhaphanphoi where pk_seq !="+id+" and manpp= '"+ ma +"' ";
		ResultSet rs = db.get(query);
		rs.next();
		boolean kq = rs.getInt("ss") > 0;
		rs.close();
		return kq;
	}

	public boolean CreateNpp(HttpServletRequest request) {

		try {
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			this.db.getConnection().setAutoCommit(false);

			if(this.tpId == null || this.tpId.trim().length() <=0 )
			{
				
				this.msg = "Vui lòng chọn tỉnh thành";
				this.db.getConnection().rollback();
				return false;
			}
			
			if(Trung_ma_npp(db, "0", this.manpp))
			{
				this.msg = this.manpp + " đã tồn tại trong hệ thống!";
				this.db.getConnection().rollback();
				return false;
			}
			
			
			String cauQuery = "\n insert into nhaphanphoi(chietkhau, diaban_fk, isChiNhanh, TenTat, ten, ngaytao, ngaysua, nguoitao, " +
			"\n nguoisua, dienthoai, diachi, khuvuc_fk, trangthai, trangthaidms, ma, pass,npptn_fk, priandsecond, " +
			"\n tinhthanh_fk, quanhuyen_fk, masothue, diachixhd, khosap, ngaybatdau, ngayketthuc, dckho, giayphepkd, " +
			"\n sotaikhoannh, fax, DiaBanHd, NganHang, ChiNhanh, ChuTaiKhoan, SoNgayNo, SoTienNo, LoaiNPP, Tructhuoc_fk, " +
			"\n nguoidaidien, chunhaphanphoi, tonantoan, muahangtu, lichdathang, manpp, diachi2, nppc1, pttangtruong, " +
			"\n quytrinhbanhang, TANSUATDATHANG, kbh_fk)" +
			"\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			"\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
			Object[] dataQuery = {this.ChietKhau,(this.diabanId.equals("")? null :this.diabanId),this.isChiNhanh,this.tentat,this.ten,this.ngaytao,this.ngaytao,this.nguoitao,this.nguoitao,this.sodienthoai,this.diachi,(this.kvId.equals("")? null :this.kvId),this.trangthai,this.trangthaidms,this.ma,this.pass,this.npptnId,this.prisec,(this.tpId.length() <=0?null:this.tpId),(this.qhId.length() <= 0?null:this.qhId),this.masothue,this.diachixhd,this.IdKhoTT,this.tungay,this.denngay,this.dckho,this.giayphepkd,this.sotk,this.fax,this.DiaBanHd,this.NganHangId,this.ChiNhanhId,this.ChuTaiKhoan,(this.songayno.length() <= 0 ?null: this.songayno),(this.sotienno.length() <= 0 ?null: this.sotienno),(this.loainpp.length() <= 0 ?null: this.loainpp),(this.tructhuocId.length() <= 0 ?null : this.tructhuocId),this.nguoidaidien,this.ChuNhaPhanPhoi,this.TonAnToan.replace(",",""),this.MuaHangTu,this.LichDatHang,this.manpp,this.diachi2,(this.nppc1.length() <= 0 ?null: this.nppc1),this.pttangtruong,this.QuyTrinhBanHang,this.tansuatdh,"100025"}; 
			if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery)!= 1) {
				dbutils.viewQuery(cauQuery,dataQuery);
				this.msg = "Lỗi tạo mới 1.";
				this.db.getConnection().rollback();
				return false;
			}
			this.id = db.getPk_seq();

			String query = "\n insert Nhaphanphoi_chietkhau_log(NPP_FK,ChietKhau) " +
			"\n select pk_Seq, chietkhau from nhaphanphoi where pk_seq =  "+ this.id;
			db.update(query);

			if (this.dvkd_nccIds != null)
			{
				int size = (this.dvkd_nccIds).length;
				int m = 0;
				while (m < size)				
				{	
					query = "\n insert into nhapp_nhacc_donvikd values('"+ this.id  +"','" + this.dvkd_nccIds[m] + "')";
					if (!this.db.update(query))
					{
						this.db.getConnection().rollback();
						this.msg = "Lỗi tạo mới 2.";
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
					if (this.gsbh_kbhIds[n] != null)
					{
						String ngaybatdau = request.getParameter("ngaybatdau"+gsbh_kbhIds[n])==null?"":request.getParameter("ngaybatdau"+gsbh_kbhIds[n]).trim().length()<=0?getDateTime():request.getParameter("ngaybatdau"+gsbh_kbhIds[n]).trim();
						String ngayketthuc = request.getParameter("ngayketthuc"+gsbh_kbhIds[n])==null?"":request.getParameter("ngayketthuc"+gsbh_kbhIds[n]).trim().length()<=0?"2100-01-01":request.getParameter("ngayketthuc"+gsbh_kbhIds[n]).trim();

						query = "\n insert into nhapp_giamsatbh(npp_fk, gsbh_fk,ngaybatdau,ngayketthuc) " +
						"\n values('" + this.id + "','" + this.gsbh_kbhIds[n] + "','"+ngaybatdau+"','"+ngayketthuc+"')";
						if (!this.db.update(query))
						{   
							this.db.getConnection().rollback();
							this.msg = "Lỗi tạo mới 3.";
							return false;
						}

						query = "\n insert into DDKD_GSBH(DDKD_FK,GSBH_FK) "+
						"\n SELECT pk_seq,'"+gsbh_kbhIds[n]+"' " +
						"\n FROM DAIDIENKINHDOANH WHERE NPP_FK IN ("+this.id+")  ";
						if (!this.db.update(query))
						{
							this.msg = "Lỗi tạo mới 4.";
							this.db.getConnection().rollback();
							return false;
						}					
					}
					n++;
				}
			}
			
			query = "\n insert into nhapp_kbh(npp_fk, kbh_fk) "+
			"\n select  "+this.id+", 100025 " ;
			if (!db.update(query))
			{
				this.msg = "Lỗi tạo mới 5.";
				this.db.getConnection().rollback();
				return false;
			}

			// Tự động tạo NVGN, khi tạo NPP mới
			query = "\n insert into NHANVIENGIAONHAN( ten, trangthai,diachi,npp_fk,ngaytao,ngaysua,nguoitao,nguoisua,dienthoai,matkhau ) "+
			"\n select  N'NVGN','1','NA',"+this.id+", '"+getDateTime()+"', '"+getDateTime()+"','"+userId+"','"+userId+"', 'NA', PWDENCRYPT('123456') " ;
			if (!db.update(query))
			{
				
				this.msg = "Lỗi tạo mới 6.";
				this.db.getConnection().rollback();
				return false;
			}	

			query = "\n insert BANGGIABANLENPP (NGAYBATDAU,KBH_FK,TEN, DVKD_FK, NPP_FK, BANGGIABANLECHUAN_FK, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA)  " + 
			"\n select tungay,bg.KBH_FK,bg.TEN, DVKD_FK, npp.PK_SEQ, bg.pk_seq, '" + getDateTime() + "', " + this.userId + ",'" + getDateTime() + "', " + this.userId + "  " + 
			"\n from BANGGIABANLECHUAN bg , NHAPHANPHOI npp  " + 
			"\n where  bg.TRANGTHAI= 1 and npp.pk_seq =   " + this.id + 
			"\n and not exists " +
			"\n ( " +
			"\n     select 1 from BANGGIABANLENPP x  " + 
			"\n     where x.NPP_FK = npp.PK_SEQ and x.BANGGIABANLECHUAN_FK = bg.PK_SEQ " +
			"\n )";
			if (!(this.db.update(query)))
			{
				this.msg = "Lỗi tạo mới 7.";
				this.db.getConnection().rollback();
				return false;
			}
			
			query =  "\n insert banggiabanlenpp_loaikhachhang(bgblnpp_fk, lkh_fk)  " + 
					 "\n select b.pk_seq, a.lkh_fk " +
					 "\n	from BangGiaBanLeChuan_LoaiKhachHang a inner join banggiabanlenpp b on a.bgblc_fk = b.BANGGIABANLECHUAN_FK " +
					 "\n and b.npp_fk = " + this.id + 
					 "\n where not exists (select 1 from banggiabanlenpp_loaikhachhang x where x.bgblnpp_fk = b.PK_SEQ and a.lkh_fk = x.lkh_fk)  " ;
			if(!(this.db.update(query)))
			{
				this.msg = "Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
				this.db.getConnection().rollback();
				return false;
			}

			query = "\n insert BGBANLENPP_SANPHAM(BGBANLENPP_FK, SANPHAM_FK, GIABANLENPP, GIABANLECHUAN)  " + 
			"\n select b.PK_SEQ, sanpham_fk, giabanlechuan, giabanlechuan  " + 
			"\n from BANGGIABLC_SANPHAM a  " + 
			"\n inner join BANGGIABANLENPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and b.npp_fk =   " + this.id + 
			"\n where  not exists (select 1 from  BGBANLENPP_SANPHAM x where x.BGBANLENPP_FK = b.PK_SEQ and a.SANPHAM_FK = x.SANPHAM_FK)  " ;
			if (!(this.db.update(query)))
			{
				this.msg = "Lỗi tạo mới 8.";
				this.db.getConnection().rollback();
				return false;
			}		

			if (this.ngaydhIds != null) {
				int size = (this.ngaydhIds).length;
				int m = 0;

				while (m < size) {
					if (this.ngaydhIds[m] != null) {
						String sql = "\n insert into npp_ngaydh( npp_fk, ndh_fk) " +
						"\n values('"+ this.id + "','" + this.ngaydhIds[m] + "')";
						//system.out.println("4.tao NPP_NGAYDH : " + sql);
						if (!db.update(sql)) {
							this.db.getConnection().rollback();
							this.db.getConnection().setAutoCommit(true);
							this.msg = "Lỗi tạo mới 11.";
							return false;
						}
					}
					m++;
				}
			}

			if (this.ttppId.length() > 0) {
				query = "\n insert into TTPP_NPP(ttpp_fk,npp_fk) select pk_Seq,'"+ this.id+ "' " +
				"\n from trungtamphanphoi where pk_Seq in ("+ this.ttppId + ") ";
				if (!db.update(query)) {
					this.db.getConnection().rollback();
					this.db.getConnection().setAutoCommit(true);
					this.msg = "Lỗi tạo mới 12.";
					return false;
				}
			}

			query = "\n INSERT INTO LichSu_GSBH_NPP(NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC) " +
			"\n	SELECT NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC FROM NHAPP_GIAMSATBH A " + 
			"\n	WHERE NOT EXISTS " + 
			"\n ( " + 
			"\n     SELECT 1 FROM LichSu_GSBH_NPP B " +
			"\n     WHERE B.NPP_FK = A.NPP_FK AND A.GSBH_FK = B.GSBH_FK " +
			"\n	) AND A.NPP_FK = " + this.id;
			if (!db.update(query)) {
				this.msg = "Lỗi tạo mới 13.";
				this.db.getConnection().rollback();
				return false;
			}
			query = " UPDATE NHAPHANPHOI SET TIMKIEM =UPPER(DBO.FTBODAU(TEN))+' '+UPPER(DBO.FTBODAU(ISnull(DIACHI,'')))+' '+UPPER(DBO.FTBODAU(ISnull(MA,''))) +' '+ISnull(DIENTHOAI,'') WHERE PK_SEQ='"+this.id+"' "; 
			if (!(db.update(query)))
			{
				this.msg = "Lỗi tạo mới 14.";
				this.db.getConnection().rollback();
				return false;
			}

			query = "\n insert phamvihoatdong " +
			"\n select pk_seq,"+id+" " +
			"\n from nhanvien where phanloai = 2 and isnull(loai,0) not in (1,2,3) ";
			db.update(query);

			query = "\n insert BANGGIAMUANPP_NPP(BANGGIAMUANPP_FK,NPP_FK,ChietKhau) " +
			"\n select a.pk_seq, b.pk_seq, 0 ck  " +
			"\n from BANGGIAMUANPP a, NHAPHANPHOI b  " +
			"\n where a.trangthai = 1 and b.PK_SEQ = "+this.id+
			"\n and not exists " +
			"\n (select 1 from  BANGGIAMUANPP_NPP x where x.BANGGIAMUANPP_FK = a.PK_SEQ and x.NPP_FK = b.PK_SEQ ) ";
			db.update(query);

			query = "\n insert DMS_Route(CODEROUTE, TEN, TRANGTHAI, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, NPP_FK) " +
			"\n select npp.manpp + a.ten, a.ten, 1, "+userId+", "+userId+", '"+this.ngaytao+"', '"+this.ngaytao+"', "+this.id+
			"\n from dms_routename a " +
			"\n inner join nhaphanphoi npp on 1 = 1 " +
			"\n where npp.pk_seq = " + this.id + 
			"\n and not exists (select 1 from DMS_Route where npp_fk = npp.pk_seq) ";
			//system.out.println("Insert DMS_Route: "+query);
			db.update(query);
			
			/*query = 
				" insert into nhapp_kho(npp_fk, kbh_fk, KHO_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE) " +
				" select npp.PK_SEQ, kenh.PK_SEQ as kbhId, kho.PK_SEQ as khoId, sp.PK_SEQ as spId, " +
				" 0 as SoLuong, 0 as Booked, 0 as avail " +
				" from KHO kho, SANPHAM sp, KENHBANHANG kenh, NHAPHANPHOI npp " +  
				" where not exists ( select 1 from NHAPP_KHO a where a.KHO_FK = kho.PK_SEQ and a.KBH_FK = kenh.PK_SEQ and a.SANPHAM_FK = sp.PK_SEQ and a.npp_fk = npp.pk_Seq ) " + 
				" and npp.pk_Seq = "+this.id+" ";
			if (!db.update(query))
			{
				this.msg = "Lỗi tạo mới 9.";
				this.db.getConnection().rollback();
				return false;
			}	

			query = "\n	insert into nhapp_kho_ChiTiet(npp_fk, kbh_fk, KHO_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, ngaynhapkho) " +
			"\n	select npp.PK_SEQ, kenh.PK_SEQ as kbhId, kho.PK_SEQ as khoId, sp.PK_SEQ as spId, " +
			"\n     0 as SoLuong, 0 as Booked, 0 as avail, convert(char(10),getdate(),126) " +
			"\n from KHO kho, SANPHAM sp, KENHBANHANG kenh, NHAPHANPHOI npp " +  
			"\n	where not exists " +  
			"\n ( " +
			"\n     select 1 " +
			"\n     from nhapp_kho_ChiTiet a " +
			"\n     where a.KHO_FK = kho.PK_SEQ and a.KBH_FK = kenh.PK_SEQ " +
			"\n     and a.SANPHAM_FK = sp.PK_SEQ and a.npp_fk = npp.pk_Seq " +
			"\n )	and npp.pk_seq = " + this.id;
			if (!db.update(query))
			{
				this.msg = "Lỗi tạo mới 10.";
				this.db.getConnection().rollback();
				return false;
			}*/
			
			this.db.getConnection().commit();
		} 
		catch (Exception e) {
			e.printStackTrace();
			this.msg = "Lỗi ngoại lệ tạo mới: "+e.getMessage();
			try { this.db.getConnection().rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
			return false;
		}
		finally{ try { this.db.getConnection().setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } }
		return true;
	}

	public boolean UpdateNpp(HttpServletRequest request) {

		try {

			String query = "";
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			
			if (!Utility.KiemTra_PK_SEQ_HopLe( this.id, "nhaphanphoi", this.db))
			{
				this.msg = "Định danh không hợp lệ!";
				return false;
			}

			db.getConnection().setAutoCommit(false);			
			
			
			if(Trung_ma_npp(db, this.id, this.manpp))
			{
				this.msg = this.manpp + " đã tồn tại trong hệ thống!";
				this.db.getConnection().rollback();
				return false;
			}
			
			if(this.tpId == null || this.tpId.trim().length() <=0 )
			{
				
				this.msg = "Vui lòng chọn tỉnh thành";
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "\n update nhaphanphoi set ten = ?, diachixhd = ?,diachi = ?,manpp = ?,ma = ?,masothue = ?, dienthoai = ?, " +
			"\n chietkhau=?, diaban_fk=?,IsChiNhanh=?,TenTat=?,khosap=?, " +
			"\n khuvuc_fk =?,ngaysua = ?,nguoisua = ?,priandsecond =?, tinhthanh_fk=?, quanhuyen_fk=?, " +
			"\n convsitecode=sitecode,ngaybatdau = ?,ngayketthuc=?, dckho = ?, giayphepkd =?,sotaikhoannh = ?, " +
			"\n fax = ?,DiaBanHd=?,NganHang=?,ChiNhanh=?,chunhaphanphoi=?,tonantoan=?,muahangtu=?,lichdathang=?, " +
			"\n trangthai = ? ,trangthaidms=?,ChuTaiKhoan=?,SoNgayNo=?,SoTienNo=?,LoaiNPP=?,Tructhuoc_fk=?, nguoidaidien = ?, " +
			"\n diachi2 = ?, pttangtruong = ?, nppc1 =?,quytrinhbanhang = ?,TANSUATDATHANG = ?, kbh_fk =? " +
			"\n where pk_seq = ? " ;
			
			Object[] dataQuery = {ten,diachixhd,diachi,manpp,ma,masothue,sodienthoai,this.ChietKhau,
					(this.diabanId.equals("")? null :this.diabanId),this.isChiNhanh,this.tentat,
					this.IdKhoTT,this.kvId,this.ngaysua,this.nguoisua,this.prisec,
					(this.tpId.length() <= 0 ? null : this.tpId), (this.qhId.length() <= 0 ? null:this.qhId),
					this.tungay,this.denngay,this.dckho,this.giayphepkd,this.sotk,this.fax,this.DiaBanHd,
					this.NganHangId,this.ChiNhanhId,this.ChuNhaPhanPhoi,this.TonAnToan.replaceAll(",", ""),
					this.MuaHangTu,this.LichDatHang,this.trangthai,this.trangthaidms,this.ChuTaiKhoan,
					(this.songayno.length() <= 0 ?null: this.songayno),(this.sotienno.length() <= 0 ?null: this.sotienno),
					(this.loainpp.length() <= 0 ?null: this.loainpp),(this.tructhuocId.length() <= 0 ?null : this.tructhuocId),
					this.nguoidaidien,this.diachi2,this.pttangtruong,(this.nppc1.length() <= 0 ?null: this.nppc1),
					this.QuyTrinhBanHang,this.tansuatdh,"100025",this.id}; 
			if (this.db.updateQueryByPreparedStatement(query,dataQuery)!= 1) {
				dbutils.viewQuery(query,dataQuery);
				this.msg = "Lỗi cập nhật 1.";
				this.db.getConnection().rollback();
				return false;
			}

			query = "\n insert Nhaphanphoi_chietkhau_log(NPP_FK,ChietKhau) " +
			"\n select pk_Seq, chietkhau from nhaphanphoi where pk_seq = "+ this.id;
			db.update(query);

			query = "Delete from nhapp_nhacc_donvikd where npp_fk='" + this.id+ "'";
			if (!db.update(query)) 
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				this.msg = "Lỗi cập nhật 2.";
				return false;
			}

			query = "\n insert nhapp_giamsatbh_log " +
			"\n select *, '"+getDateTime()+"', '"+this.userId+"' from  nhapp_giamsatbh where npp_fk=" + this.id + "";
			if (!db.update(query)) 
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				this.msg = "Lỗi cập nhật 3.";
				return false;
			}

			query = "delete from nhapp_giamsatbh where npp_fk = " + this.id + "";
			if (!db.update(query)) 
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				this.msg = "Lỗi cập nhật 4.";
				return false;
			}

			query = " delete nhapp_kbh where npp_fk=" + this.id + " ";
			if (!db.update(query)) 
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				this.msg = "Lỗi cập nhật 6.";
				return false;
			}

			query = "delete npp_ngaydh where npp_fk = '" + this.id + "'";
			if (!db.update(query)) 
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				this.msg = "Lỗi cập nhật 7.";
				return false;
			}

			query = "delete TTPP_NPP where npp_fk = '" + this.id + "'";
			if (!db.update(query)) 
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				this.msg = "Lỗi cập nhật 8.";
				return false;
			}

			if (this.ttppId.length() > 0) 
			{
				query = " insert into TTPP_NPP(ttpp_fk,npp_fk) select pk_Seq,'"+ this.id+ "' from trungtamphanphoi where pk_Seq in ("+ this.ttppId + ") ";
				if (!db.update(query)) 
				{
					this.db.getConnection().rollback();
					this.db.getConnection().setAutoCommit(true);
					this.msg = "Lỗi cập nhật 9.";
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
					//system.out.println("2. Tao NPP_NCC_DVKD : " +query ) ; 
					if (!db.update(query))
					{
						this.db.getConnection().rollback();
						this.db.getConnection().setAutoCommit(true);
						this.msg = "Lỗi cập nhật 10.";
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
						if (!db.update(sql)) 
						{
							this.db.getConnection().rollback();
							this.db.getConnection().setAutoCommit(true);
							this.msg = "Lỗi cập nhật 11.";
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
					if (this.gsbh_kbhIds[n] != null)
					{
						String ngaybatdau = request.getParameter("ngaybatdau"+gsbh_kbhIds[n])==null?"":request.getParameter("ngaybatdau"+gsbh_kbhIds[n]).trim().length()<=0?getDateTime():request.getParameter("ngaybatdau"+gsbh_kbhIds[n]).trim();
						String ngayketthuc = request.getParameter("ngayketthuc"+gsbh_kbhIds[n])==null?"":request.getParameter("ngayketthuc"+gsbh_kbhIds[n]).trim().length()<=0?"2100-01-01":request.getParameter("ngayketthuc"+gsbh_kbhIds[n]).trim();

						query = "\n insert into nhapp_giamsatbh(npp_fk, gsbh_fk,ngaybatdau,ngayketthuc) " +
						"\n values('" + this.id + "','" + this.gsbh_kbhIds[n] + "','"+ngaybatdau+"','"+ngayketthuc+"')";
						//system.out.println("INSERT NPP_GSBH "+query);
						if (!db.update(query))
						{   
							this.db.getConnection().rollback();
							this.db.getConnection().setAutoCommit(true);
							this.msg = "Lỗi cập nhật 12.";
							return false;
						}			
					}
					n++;
				}
			}

			query = "\n insert into nhapp_kbh(npp_fk, kbh_fk) "+
			"\n select  "+this.id+",100025";
			
			if (!db.update(query))
			{
				this.msg = "Lỗi cập nhật 14.";
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}

			query = "\n insert BANGGIABANLENPP(NGAYBATDAU, KBH_FK, TEN, DVKD_FK, NPP_FK, BANGGIABANLECHUAN_FK, " +
			"\n NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA) " + 
			"\n select tungay, bg.KBH_FK, bg.TEN, DVKD_FK, npp.PK_SEQ, bg.pk_seq, '" + getDateTime() + "', " +
			"\n " + this.userId + ",'" + getDateTime() + "', " + this.userId + "  " + 
			"\n from BANGGIABANLECHUAN bg, NHAPHANPHOI npp " + 
			"\n where bg.TRANGTHAI= 1 and npp.pk_seq = " + this.id + 
			"\n and not exists " +
			"\n ( " +
			"\n     select 1 from BANGGIABANLENPP x  " + 
			"\n     where x.NPP_FK = npp.PK_SEQ and x.BANGGIABANLECHUAN_FK = bg.PK_SEQ " +
			"\n )";
			//system.out.println("banggia npp:"+ query);
			if (!(this.db.update(query)))
			{
				this.msg = "Lỗi cập nhật 15.";
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}

			query = "\n insert BGBANLENPP_SANPHAM(BGBANLENPP_FK, SANPHAM_FK, GIABANLENPP, GIABANLECHUAN) " + 
			"\n select b.PK_SEQ, sanpham_fk, giabanlechuan, giabanlechuan  " + 
			"\n from BANGGIABLC_SANPHAM a  " + 
			"\n inner join BANGGIABANLENPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK and b.npp_fk =   " + this.id + 
			"\n where  not exists (select 1 from  BGBANLENPP_SANPHAM x where x.BGBANLENPP_FK = b.PK_SEQ and a.SANPHAM_FK = x.SANPHAM_FK)  " ;
			if (!(this.db.update(query)))
			{
				this.msg = "Lỗi cập nhật 16.";
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}

			query = "\n select npp.pk_seq,npp.sitecode,npp.ten ,isnull( nv.dangnhap,'')  as dangnhap from  nhaphanphoi npp  left join "+ 
			"\n nhanvien nv  on nv.convsitecode=sitecode where  npp.pk_seq='"+ this.id + "'  ";
			ResultSet rs = db.get(query);
			if (rs.next()) 
			{
				if (rs.getString("dangnhap").equals("")) 
				{
					query = "\n insert into nhanvien(ten,dangnhap,matkhau,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,phanloai,convsitecode, sessionid) " +
					"\n values("+ "N'"+ this.ten+ "','"+ this.ma+ "', pwdencrypt('"	+ this.ma+ "'),1," +
					"\n '"+this.getDateTime()+ "','"+this.getDateTime()+ "','"	+ this.userId+ "'," +
					"\n '"+ this.userId+ "','1','"+ rs.getString("sitecode") + "','2012-01-01')";
					if (!db.update(query)) 
					{
						this.db.getConnection().rollback();
						this.db.getConnection().setAutoCommit(true);
						this.msg = "Lỗi cập nhật 18.";
						return false;
					}
				}
			}
			rs.close();
			
			query = "select sitecode from nhaphanphoi where pk_seq='" + this.id+ "'";
			rs = db.get(query);
			String sitecode = "";
			while (rs.next()) 
			{
				sitecode = rs.getString("sitecode");
			}
			rs.close();
			
			query = "update nhanvien set trangthai='" + this.trangthai+ "' where CONVSITECODE='" + sitecode + "'";
			if (!db.update(query)) 
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				this.msg = "Lỗi cập nhật 19.";
				return false;
			}

			// Cập nhật lại Ngày kết thúc cho GSBH cũ = Ngày bắt đầu -1 của GS mới của NPP (Nếu có)
			query = "select COUNT(*) dem from LichSu_GSBH_NPP where NPP_FK = "+ this.id +" ";
			ResultSet rsKT = db.get(query);
			int dem = 0;
			while (rsKT.next()) 
			{
				dem = rsKT.getInt("dem");
			}
			if (rsKT!= null)
				rsKT.close();

			if (dem > 0)
			{
				query = "\n UPDATE K set K.NGAYKETTHUC = CONVERT(VARCHAR(10), DATEADD(day,-1, K.NGAYBATDAU),21) "+
				"\n FROM "+
				"\n (SELECT TOP 1 a.NgayKetThuc, b.NGAYBATDAU  "+
				"\n FROM LichSu_GSBH_NPP a inner join NHAPP_GIAMSATBH b on a.NPP_FK = b.NPP_FK "+
				"\n WHERE a.NPP_FK = "+ this.id +"  "+
				"\n ORDER BY a.NgayBatDau desc) K ";
				if (!db.update(query)) {
					this.msg = "Lỗi cập nhật 20.";
					this.db.getConnection().rollback();
					this.db.getConnection().setAutoCommit(true);
					return false;
				}
			}

			query = "\n INSERT INTO LichSu_GSBH_NPP(NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC) " +
			"\n	SELECT NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC FROM NHAPP_GIAMSATBH A " +
			"\n	WHERE NOT EXISTS " +
			"\n ( " +
			"\n     SELECT 1 FROM LichSu_GSBH_NPP B " +
			"\n     WHERE B.NPP_FK=A.NPP_FK AND A.GSBH_FK = B.GSBH_FK " +
			"\n	) AND A.NPP_FK = "+this.id;
			if (!db.update(query)) {
				this.msg = "Lỗi cập nhật 21.";
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}

			query = "\n UPDATE A SET NGAYBATDAU=B.NGAYBATDAU,NGAYKETTHUC=B.NGAYKETTHUC FROM LichSu_GSBH_NPP A INNER JOIN " +
			"\n NHAPP_GIAMSATBH B ON B.NPP_FK=A.NPP_FK  AND B.GSBH_FK=A.GSBH_FK " +
			"\n WHERE A.NPP_FK=" + this.id + " ";
			if (!db.update(query)) 
			{
				this.msg = "Lỗi cập nhật 22.";
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}
			
			query = "\n UPDATE NHAPHANPHOI SET TIMKIEM =UPPER(DBO.FTBODAU(TEN))+' '+UPPER(DBO.FTBODAU(ISnull(DIACHI,'')))+' '+UPPER(DBO.FTBODAU(ISnull(MA,''))) +' '+ISnull(DIENTHOAI,'') WHERE PK_SEQ='"+this.id+"' "; 
			if (!(db.update(query)))
			{
				this.msg = "Lỗi cập nhật 23.";
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}

			query = "\n insert BANGGIAMUANPP_NPP(BANGGIAMUANPP_FK,NPP_FK,ChietKhau) " +
			"\n select a.pk_seq, b.pk_seq, 0 ck  " +
			"\n from BANGGIAMUANPP a, NHAPHANPHOI b  " +
			"\n where a.trangthai = 1 and b.PK_SEQ = "+this.id+" " +
			"\n and not exists " +
			"\n ( " +
			"\n     select 1 " +
			"\n     from BANGGIAMUANPP_NPP x " +
			"\n     where x.BANGGIAMUANPP_FK = a.PK_SEQ and x.NPP_FK = b.PK_SEQ " +
			"\n ) ";
			db.update(query);

			query = "\n insert DMS_Route(CODEROUTE, TEN, TRANGTHAI, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, NPP_FK) " +
			"\n select npp.manpp + a.ten, a.ten, 1, "+userId+", "+userId+", '"+this.ngaytao+"', '"+this.ngaytao+"', "+this.id+
			"\n from dms_routename a " +
			"\n inner join nhaphanphoi npp on 1 = 1 " +
			"\n where npp.pk_seq = " + this.id + 
			"\n and not exists (select 1 from DMS_Route where npp_fk = npp.pk_seq) ";
			//system.out.println("Insert DMS_Route: "+query);
			db.update(query);
			
			/*query = "\n	insert into nhapp_kho(npp_fk, kbh_fk, KHO_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE) " +
			"\n	select npp.PK_SEQ, kenh.PK_SEQ as kbhId, kho.PK_SEQ as khoId, sp.PK_SEQ as spId, " +
			"\n     0 as SoLuong, 0 as Booked, 0 as avail " +
			"\n from KHO kho, SANPHAM sp, KENHBANHANG kenh, NHAPHANPHOI npp " +  
			"\n	where not exists " +  
			"\n	( " +
			"\n		select 1 from NHAPP_KHO a " +
			"\n		where a.KHO_FK = kho.PK_SEQ and a.KBH_FK = kenh.PK_SEQ " +
			"\n		and a.SANPHAM_FK = sp.PK_SEQ and a.npp_fk = npp.pk_Seq " +
			"\n	) 	and npp.pk_Seq = "+this.id+" ";
			if (!db.update(query))
			{
				this.msg = "Lỗi cập nhật 09.";
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}	

			query = "\n	insert into nhapp_kho_ChiTiet(npp_fk, kbh_fk, KHO_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, ngaynhapkho) " +
			"\n	select npp.PK_SEQ, kenh.PK_SEQ as kbhId, kho.PK_SEQ as khoId, sp.PK_SEQ as spId, " +
			"\n     0 as SoLuong, 0 as Booked, 0 as avail, convert(char(10),getdate(),126) " +
			"\n from KHO kho, SANPHAM sp, KENHBANHANG kenh, NHAPHANPHOI npp " +  
			"\n	where not exists " +  
			"\n ( " +
			"\n     select 1 " +
			"\n     from nhapp_kho_ChiTiet a " +
			"\n     where a.KHO_FK = kho.PK_SEQ and a.KBH_FK = kenh.PK_SEQ " +
			"\n     and a.SANPHAM_FK = sp.PK_SEQ and a.npp_fk = npp.pk_Seq " +
			"\n ) 	and npp.pk_seq = " + this.id;
			if (!db.update(query))
			{
				this.msg = "Lỗi cập nhật 010.";
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}*/

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			this.msg = "Lỗi ngoại lệ cập nhật: " + e.getMessage();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		} 	
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
			//system.out.println("So dong trong ResultSet: "+ k);			
			Rs.beforeFirst();
			return k;
		} catch(Exception e){
			return 0;
		}

	}
	public void setArrayDvkdSelected(){
		ResultSet Rs = this.dvkd_nccSelected;
		if (Rs!=null){

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
				//system.out.println("Loi roi!");
			}


		}
	}

	public void setArrayGsbhSelected(){
		ResultSet Rs = this.gsbh_kbhSelected;
		if (Rs!=null){

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
				//system.out.println("Loi roi!");
			}


		}
	}

	public void setArrayNgaydhSelected(){
		/*ResultSet Rs = this.NgaydhSelected;
		if (Rs!=null){

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
				//system.out.println("Loi roi!");
			}


		}*/
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
		boolean coGiamSat = false;
		if (this.id.length() > 0) {
			String query = "\n select distinct a.pk_seq as gsbh_kbhId, a.ten as gsbhTen, a.pk_seq as gsbhId, a.dienthoai as sodienthoai, " +
			"\n c.diengiai as kbhTen, c.pk_seq as kbhId, b.ngaybatdau, b.ngayketthuc " +
			"\n from giamsatbanhang a " +
			"\n inner join gsbh_khuvuc gs_kv on a.pk_seq = gs_kv.gsbh_fk " +
			"\n inner join nhapp_giamsatbh b on a.pk_seq = b.gsbh_fk and a.trangthai ='1' " +
			"\n inner join NHAPHANPHOI npp on npp.PK_SEQ = b.NPP_FK " +
			"\n inner join kenhbanhang c on a.kbh_fk = c.pk_seq " +
			"\n where b.npp_fk = '"+ this.id + "'";
			//system.out.println("gsbh_kbhSelected: " + query);
			this.gsbh_kbhSelected = this.db.getScrol(query);
			//setArrayGsbhSelected(this.gsbh_kbhSelected);
			
			ResultSet temprs = db.get(query);
			try {
				while (temprs.next()) {
					coGiamSat = true;
				}
				temprs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if(this.kvId.length() > 0)
			{
				query = "\n select distinct a.pk_seq as gsbh_kbhId, a.ten as gsbhTen, a.pk_seq as gsbhId, a.dienthoai as sodienthoai, " +
						"\n b.diengiai as kbhTen, b.pk_seq as kbhId, '' as ngaybatdau, '' as ngayketthuc " +
						"\n from giamsatbanhang a inner join gsbh_khuvuc on gsbh_khuvuc.gsbh_fk = a.pk_seq  " +
						"\n inner join nhapp_giamsatbh bh on bh.gsbh_fk = a.pk_seq " +
						"\n inner join nhaphanphoi npp on npp.pk_seq = bh.npp_fk " +
						"\n inner join kenhbanhang b on npp.kbh_fk = b.pk_seq and a.trangthai ='1' and gsbh_khuvuc.khuvuc_fk = "+this.kvId+
						"\n where a.pk_seq in " +
						"\n ( " +
						"\n     select a.gsbh_fk from nhapp_giamsatbh a inner join giamsatbanhang b on a.gsbh_fk = b.pk_seq " +
						"\n     where a.npp_fk = "+this.id+" and a.NGAYKETTHUC >= '"+ this.getDateTime()+"' " +
						"\n ) " +
						"\n union " +
						"\n select distinct a.pk_seq as gsbh_kbhId, a.ten as gsbhTen, a.pk_seq as gsbhId, " +
						"\n a.dienthoai as sodienthoai, b.diengiai as kbhTen, b.pk_seq as kbhId,'' as ngaybatdau,'' as ngayketthuc " +
						"\n from giamsatbanhang a " +
						"\n inner join nhapp_giamsatbh bh on bh.gsbh_fk = a.pk_seq " +
						"\n inner join nhaphanphoi npp on npp.pk_seq = bh.npp_fk " +
						"\n inner join kenhbanhang b on npp.kbh_fk = b.pk_seq and a.trangthai ='1' " +
						"\n inner join gsbh_khuvuc gsbh_kv on gsbh_kv.gsbh_fk = a.pk_seq " +
						"\n where a.pk_seq in " +
						"\n (" +
						"\n     select a.gsbh_fk from nhapp_giamsatbh a inner join giamsatbanhang b on a.gsbh_fk = b.pk_seq " +
						"\n     where a.npp_fk = "+this.id+
						"\n     and a.NGAYKETTHUC >= '"+this.getDateTime()+"'" + 
						"\n ) and gsbh_kv.khuvuc_fk =" + this.kvId;
						//system.out.println("gsbh_kbhList: " + query);
						this.gsbh_kbhList = this.db.get(query);
			}

		} 
		else 
		{
			if(this.kvId.length() > 0)
			{
				String query = "\n select a.pk_seq as gsbh_kbhId, a.ten as gsbhTen, a.pk_seq as gsbhId, " +
				"\n a.dienthoai as sodienthoai, b.diengiai as kbhTen, b.pk_seq as kbhId, '' as ngaybatdau, '' as ngayketthuc " +
				"\n from giamsatbanhang a inner join gsbh_khuvuc gs_kv on a.pk_seq = gs_kv.gsbh_fk " +
				"\n     and gs_kv.khuvuc_fk = "+ this.kvId+", kenhbanhang b " +
				"\n where a.kbh_fk = b.pk_seq and a.trangthai ='1'";
				//system.out.println("gsbh_kbhList: " + query);
				this.gsbh_kbhList = this.db.get(query);
			}
		}
		
		if (!coGiamSat) 
		{
			if(this.kvId.length() > 0)
			{
				String query = "\n select a.pk_seq as gsbh_kbhId, a.ten as gsbhTen, a.pk_seq as gsbhId, " +
				"\n a.dienthoai as sodienthoai, b.diengiai as kbhTen, b.pk_seq as kbhId, '' as ngaybatdau, '' as ngayketthuc " +
				"\n from giamsatbanhang a inner join gsbh_khuvuc gs_kv on a.pk_seq = gs_kv.gsbh_fk " +
				"\n     and gs_kv.khuvuc_fk = "+ this.kvId+", kenhbanhang b " +
				"\n where a.kbh_fk = b.pk_seq and a.trangthai ='1'";
				//system.out.println("gsbh_kbhList: " + query);
				this.gsbh_kbhList = this.db.get(query);
			}
		}
	}

	public void createRS() {
		String query = "null";
		if (this.id.length() > 0)
			query = "select NPP_FK,GSBH_FK from NHAPP_GIAMSATBH where NPP_FK !='"
					+ this.id + "'";
		else
			query = "select NPP_FK, GSBH_FK from NHAPP_GIAMSATBH ";

		//system.out.println("GSBH : " + query);
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

		query = "\n select a.pk_seq as id, a.TEN as Ten, d.ten as nhacungcap, a.trangthai, a.ngaytao, " +
		"\n a.ngaysua, b.ten as nguoitao,  c.ten as nguoisua, d.pk_seq as nccId, a.MA " +
		"\n from TRUNGTAMPHANPHOI a, nhanvien b, nhanvien c, nhacungcap d, TTPP_NCC  e " +
		"\n where a.PK_SEQ = e.TTPP_FK and d.PK_SEQ = e.NCC_FK  and a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ";
		this.ttppRs = this.db.get(query);

		query = "select ten, pk_seq from kenhbanhang ";
		this.kenhRs = this.db.get(query);		
		
		query = "select PK_SEQ as id,ma,TEN from TRUNGTAMPHANPHOI";
		this.tructhuocRs = this.db.get(query);

		if (loainpp.equals("1")) {
			query = " select PK_SEQ as id, ma, TEN from NHAPHANPHOI where loainpp in (0,2) and trangthai = 1 ";
		}

		if (loainpp.equals("2")) {
			query = " select PK_SEQ as id, ma, TEN from NHAPHANPHOI where loainpp in (0) and trangthai = 1 ";
		}
		if (loainpp.equals("3")) {
			query = " select PK_SEQ as id, ma, TEN from NHAPHANPHOI where loainpp in (0) and trangthai = 1 ";
		}
		this.tructhuocRs = this.db.get(query);

		query = "\n SELECT v.ten as vTen, kv.Ten as kvTen, b.pk_Seq as nppId, b.ma as nppMA, b.Ten as nppTen, " +
		"\n a.NgayBatDau, a.NgayKetThuc, KBH.TEN AS KBHtEN, GS.TEN AS GSBHTEN " +
		"\n FROM LichSu_GSBH_NPP A INNER JOIN NHAPHANPHOI B ON B.PK_SEQ = A.NPP_FK  " +
		"\n INNER JOIN GIAMSATBANHANG GS ON GS.PK_sEQ = A.GSBH_FK  " +
		"\n INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = GS.KBH_FK  " +
		"\n left join khuvuc kv on kv.pk_Seq = b.khuvuc_Fk " +
		"\n left join vung v on v.pk_Seq = kv.vung_fk " +
		"\n WHERE A.NPP_FK='" + this.id + "' ";
		this.gsQlRs = this.db.get(query);
		
		query = "select * from nhaphanphoi where loainpp = 1 and trangthai = 1";
		this.nppc1Rs = this.db.get(query);
	}
	
	public ResultSet CreateMuaHangTu(){

		String tv = "select pk_seq, ten from NHAPHANPHOI where 1=1";
		String query ="";
		if (this.loainpp.equals("1")){// Loại 1 là NPP/ Đại lý
			// mua từ Tổng thầu và DDT
			query = "select 0 as pk_seq, 'DDT' as ten ";
			query += "union all "+ tv + " and LoaiNPP = 4 and TrangThai = 1";
		}
		
		if (this.loainpp.equals("4")){ // Tổng thầu 
			// ma từ Trung tâm phân phối hoặc chi nhánh hoặc đại đồng tiến
			query = "select 102 as pk_seq, N'Trung tâm phân phối' as ten ";
			query += "union all select 0 as pk_seq, 'DDT' as ten ";
			query+="union all "+ tv+ " and LoaiNPP = 2 and TrangThai = 1";
		}

		if (this.loainpp.equals("5")){ // Cửa hàng hợp tác
			query =tv + " and LoaiNPP = 1 and TrangThai = 1";
			// Mua hàng từ NPP
		}
		
		if (this.loainpp.equals("3")){ // Show room
			// Mua hàng từ NPP -1000 để không hiển thị
			query= "select 0 as pk_seq, 'DDT' as ten";
		}
		
		if (this.loainpp.equals("2")){ // Chi nhánh
			// Mua hàng từ DDT, Trung tâm pp
			query += "select 0 as pk_seq, 'DDT' as ten ";
			query += "union all select 102 as pk_seq, N'Trung tâm phân phối' as ten ";
		}

		if (query==""){
			query=tv;
		}

		this.MuahangtuRs = this.db.get(query);
		//system.out.println("Query mua hang tu: "+ query);
		return this.MuahangtuRs;
	}
	
	private String getinfonpp(String id) {
		String sql = "select * from NHAPHANPHOI npp ,NHANVIEN nv "
				+ "where npp.SITECODE=nv.CONVSITECODE and nv.PK_SEQ=" + id;
		return "";
	}

	private void init() {
		String query = "\n select a.ChietKhau,a.diaban_fk,a.khosap as khott,a.masothue,a.diachixhd,a.pk_seq as id, a.ten as nppTen, a.dienthoai, a.diachi, a.trangthai,isnull(a.trangthaidms,0) as trangthaidms, "
				+ "\n a.ma, a.pass, a.ngaytao, b.nguoitao, a.ngaysua,a.kbh_fk, c.nguoisua, d.ten as kvTen, d.pk_seq as kvId, a.priandsecond as "
				+ "\n prisec, a.tinhthanh_fk as tpId, a.quanhuyen_fk as qhId,a.ngaybatdau,a.ngayketthuc,a.dckho, a.giayphepkd, a.sotaikhoannh, a.fax,isnull(a.DiaBanHd,'')DiaBanHd ,"
				+ "\n ISnull(a.NganHang,'')NganHang,ISnull(a.ChiNhanh,'')ChiNhanh,ISnull(a.ChuTaiKhoan,'')as ChuTaiKhoan,isnull(a.tentat,a.ten) as TenTat,a.IsChiNhanh,a.tructhuoc_fk as tructhuocId,a.SoNgayNo,a.SoTienNo,a.loainpp, ISnull(a.nguoidaidien,'')as nguoidaidien, "
				+ "\n a.chunhaphanphoi, a.tonantoan,a.muahangtu,a.lichdathang,a.quytrinhbanhang, A.MANPP, A.DIACHI2, A.PTTANGTRUONG, A.NPPC1 "
				+ "\n from "
				+ "\n nhaphanphoi a inner join nhanvien b on b.pk_seq=a.nguoitao "
				+ "\n inner join  nhanvien c on c.pk_seq=a.nguoisua "
				+ "\n left join  khuvuc d  on a.khuvuc_fk=d.pk_seq "
				+ "\n where  a.pk_seq = '" + this.id + "'";
		//system.out.println("IN "+query);
		ResultSet rs = this.db.get(query);

		try {
			rs.next();
			this.ChietKhau = rs.getString("ChietKhau") == null ? 0 :  rs.getDouble("ChietKhau");
			this.diabanId = rs.getString("diaban_fk");
			this.ChuNhaPhanPhoi = rs.getString("chunhaphanphoi")==null ? "":rs.getString("chunhaphanphoi");
			this.TonAnToan=rs.getString("tonantoan")==null ? "0" :rs.getString("tonantoan");
			this.MuaHangTu=rs.getString("muahangtu")==null ? "0":rs.getString("muahangtu");
			this.LichDatHang=rs.getString("lichdathang")==null ? "":rs.getString("lichdathang");
			this.QuyTrinhBanHang=rs.getString("quytrinhbanhang")==null ? "0":rs.getString("quytrinhbanhang");

			this.masothue = rs.getString("masothue");
			this.diachixhd = rs.getString("diachixhd");
			this.id = rs.getString("id");
			this.ten = rs.getString("nppTen");
			this.sodienthoai = rs.getString("dienthoai");
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
			this.tungay = rs.getString("ngaybatdau");
			this.denngay = rs.getString("ngayketthuc");
			this.dckho = rs.getString("dckho");
			this.giayphepkd = rs.getString("giayphepkd");
			this.sotk = rs.getString("sotaikhoannh");
			this.fax = rs.getString("fax");
			this.kenhId = rs.getString("kbh_fk");
			this.trangthaidms = rs.getString("trangthaidms");
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
			
			this.manpp = rs.getString("manpp") == null ? "" : rs.getString("manpp");
			this.diachi2 = rs.getString("diachi2") == null ? "" : rs.getString("diachi2");
			this.nppc1 = rs.getString("nppc1") == null ? "" : rs.getString("nppc1");
			this.pttangtruong = rs.getString("pttangtruong") == null ? "" : rs.getString("pttangtruong");

			//system.out.println("___________________"+this.trangthaidms);
			String st = rs.getString("prisec");
			if (st == null)
				st = "0";
			this.prisec = st;
			this.npptnId = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		createRS();
		//system.out.println("NPP ID: "+ this.id);
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
			//system.out.println("Trung tâm pp ID: "+ this.ttppId);
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.NgaydhSelected = db.get("select ma,ten from NhomNpp where exists (select 1 from NhomNpp_Npp where Npp_FK = "+this.id+")");
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
		String query = "select pk_seq,ten from khott where trangthai!='2'";
		if (loainpp.trim().equals("6"))
			query = "select pk_seq,ten from kho where trangthai = '1'";
		ResultSet rs_khottnew = db.get(query);
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

	String songayno ="", sotienno, ttppId,kenhId, tructhuocId, loainpp;
	ResultSet ttppRs, tructhuocRs, loaiNppRs,kenhRs;

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
	
	public ResultSet getKenhRs() {
		return kenhRs;
	}

	public void setKenhRs(ResultSet kenhRs) {
		this.kenhRs = kenhRs;
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
	
	public String getKenhId() {
		return kenhId;
	}

	public void setKenhId(String kenhId) {
		this.kenhId = kenhId;
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


	public String getNguoidaidien() {

		return this.nguoidaidien;
	}


	public void setNguoidaidien(String ndd) {

		this.nguoidaidien = ndd;
	}


	public ResultSet getTructhuocRs() 
	{
		return null;
	}


	public void setTructhuocRs(ResultSet tructhuocRs) 
	{

	}


	public String getMaNpp() {
		return this.manpp;
	}


	public void setMaNpp(String manpp) {
		this.manpp = manpp;
	}


	public String getDiachi2() {
		return this.diachi2;
	}


	public void setDiachi2(String diachi2) {
		this.diachi2 = diachi2;
	}


	public String getNppcap1() {
		return this.nppc1;
	}


	public void setNppcap1(String nppc1) {
		this.nppc1 = nppc1;
	}


	public String getPtTangTruong() {
		return this.pttangtruong;
	}


	public void setPtTangTruong(String pttangtruong) {
		this.pttangtruong = pttangtruong;
	}


	public ResultSet getNppC1Rs() {
		return this.nppc1Rs;
	}

	
	public String getTansuatdh() {
		
		return this.tansuatdh;
	}
	
	public void setTansuatdh(String Tansuatdh) {
		this.tansuatdh = Tansuatdh;
		
	}
}
