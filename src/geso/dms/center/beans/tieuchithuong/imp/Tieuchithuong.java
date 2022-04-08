package geso.dms.center.beans.tieuchithuong.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.tieuchithuong.ITieuchithuong;
import geso.dms.center.db.sql.dbutils;

public class Tieuchithuong implements ITieuchithuong
{
	public String tctId; 	
	public String diengiai;
	public ResultSet tct;
	public ResultSet nhomsp;

	public String tungay;
	public String denngay;

	public ResultSet nhomtc;
	public String nhomtcId;

	public String tcdiengiai;

	public String nhomspId;

	public String loai;

	public String loaiDS;

	public String thuongnsp;
	public String dstoithieudh;

	public String tongthuong;
	public String tldstoithieu;

	public String dvkdId;
	public String kbhId;
	public String thang;
	public String nam;
	public String tc;
	public String tcfk;
	public ResultSet tieuchi;

	public String[] ctct ;
	public String[] stt ;
	public String[] tu ;
	public String[] den ;
	public String[] thuong ;

	public String userId;	
	public String msg;
	public String action;

	String loaithuong;
	String hienthi;
	
	String nhomsp_fk = "";
	
	public String getNhomsp_fk() {
		return nhomsp_fk;
	}
	public void setNhomsp_fk(String nhomsp_fk) {
		this.nhomsp_fk = nhomsp_fk;
	}
	public String getHienthi() {
		return hienthi;
	}

	public void setHienthi(String hienthi) {
		this.hienthi = hienthi;
	}

	public String getLoaithuong() {
		return loaithuong;
	}

	public void setLoaithuong(String loaithuong) {
		this.loaithuong = loaithuong;
	}
	public String LoaiTieuChi;
	ResultSet rsKenh;
	ResultSet rsDVKD;

	public String toithieu;
	public String toida;

	dbutils db;

	
	public String[] tctctYeuCauIdList ;
	public String[] giatriTctctList;
	ResultSet tctctYeuCauRs;
	
	
	public String[] spDieuKienList ;
	public String[] soluongSpDieuKienList;
	ResultSet spDieuKienRs;
	
	public Tieuchithuong()
	{
		this.tungay = "";
		this.denngay = "";

		this.dvkdId = "";
		this.kbhId = "";
		this.tctId = "";
		this.thang = "";
		this.nam = "";

		this.loai = "";
		this.loaiDS = "";
		this.thuongnsp = "";
		this.dstoithieudh = "0";

		this.nhomtcId = "";
		this.tcdiengiai = "";

		this.tc = "";
		this.tcfk = "";
		this.action = "";
		this.diengiai="";
		this.msg = "";
		this.db = new dbutils();		
		this.tct = null;
		this.LoaiTieuChi="";
		this.toithieu = "";
		this.toida = "";
		this.nhomspId = "";
		this.tongthuong = "0";
		this.tldstoithieu = "0";
		this.hienthi="";
	}

	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}

	public String getDiengiai(){
		return this.diengiai;
	}

	public void setDvkd(String dvkdId){
		this.dvkdId = dvkdId;
	}

	public String getDvkd(){
		return this.dvkdId;
	}

	public void setKbh(String kbhId){
		this.kbhId = kbhId;
	}

	public String getKbh(){
		return this.kbhId;
	}

	public void setNam(String nam){
		this.nam = nam;
	}

	public String getNam(){
		return this.nam;
	}

	public void setThang(String thang){
		this.thang = thang;
	}

	public String getThang(){
		return this.thang;
	}

	public void setTct(ResultSet tct){
		this.tct = tct;
	}

	public ResultSet getTct(){
		return this.tct;
	}

	public void setTctId(String tctId){
		this.tctId = tctId;
	}

	public String getTctId(){
		return this.tctId;
	}

	public void setTieuchi(String tc){
		this.tc = tc;
	}

	public String getTieuchi(){
		return this.tc;
	}

	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
		
	}

	

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return this.msg;
	}

	public void setAction(String action){
		this.action = action;
	}

	public String getAction(){
		return this.action;
	}

	public void setTieuchiRS(ResultSet tieuchi){
		this.tieuchi = tieuchi;
	}

	public ResultSet getTieuchiRS(){
		return this.tieuchi;
	}

	public void setData(String[] ctct, String[] stt, String[] tu, String[] den, String[] thuong){
		this.ctct = ctct;
		this.stt = stt;
		this.tu = tu;
		this.den = den;
		this.thuong = thuong;

	}
	
	public boolean Save()
	{
		try
		{

			String query;
			if(this.tctId.length() > 0 && !this.tctId.equals("0") )
			{
				db.getConnection().setAutoCommit(false);

				/*	query = "UPDATE TIEUCHITINHTHUONG SET DIENGIAI =N'"+ this.diengiai + "', thang = '" + this.thang + "', nam = '" + this.nam + "'," +
					"DVKD_FK='" + this.dvkdId + "', KBH_FK = '"+ this.kbhId + "', NGUOISUA ='" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "', tongthuong = '"+ this.tongthuong.replaceAll(",", "") +"', " +
					"TYLEDOANHSOTOITHIEU = '"+ this.tldstoithieu.replaceAll(",", "") +"' " +
					"WHERE PK_SEQ = '" + this.tctId + "'";

					//System.out.println("115.Update TCT: " + query);
					if(!this.db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Lỗi::" + query;
						return false;
					}*/

				query = "\n UPDATE TIEUCHITINHTHUONG SET DIENGIAI = ?, thang = ?, nam = ?," +
				"\n DVKD_FK=?, KBH_FK = ?, NGUOISUA =?, NGAYSUA = ?, tongthuong = ?, " +
				"\n TYLEDOANHSOTOITHIEU = ? " +
				"\n WHERE PK_SEQ = '" + this.tctId + "'";					
				Object[] data = {this.diengiai,this.thang,this.nam,this.dvkdId,this.kbhId,this.userId,this.getDateTime(),this.tongthuong.replaceAll(",", ""),
						this.tldstoithieu.replaceAll(",", "")};
				dbutils.viewQuery(query,data);
				if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
					this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý.";
					this.db.getConnection().rollback();
					return false;
				}

				int num = 1;
				String chuoi = "";

				System.out.println("bo ay : "+this.ctct.length);
				if(this.ctct.length > 0)
				{
					for(int i = 0; i < this.ctct.length; i++)
					{
						chuoi += this.ctct[i] +","+ this.stt[i] +","+ this.tu[i].replaceAll(",", "") +","+ this.den[i].replaceAll(",", "") +","+ this.thuong[i].replaceAll(",", "") +";";
					}
				}
				
				if(this.tcfk == null || this.tcfk.length() <= 0)
				{
					db.getConnection().rollback();
					this.msg = "Lỗi: Chưa chọn tiêu chí";
					return false;
				}

				Object[] data2;

				if (chuoi != "")
				{
					/*query = " UPDATE TIEUCHITHUONG_CHITIET SET NOIDUNG = '" + chuoi.substring(0, chuoi.length() - 1) + "', " +
						" NGUOISUA = '" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "', TOITHIEU = '"+ this.toithieu.replaceAll(",", "") +"', " +
						" TOIDA = '"+ this.toida.replaceAll(",", "") +"', NHOMSP_FK = "+ (this.nhomspId == "" ? null :  this.nhomspId) +", THUONG = '"+ this.thuongnsp.replaceAll(",", "") +"', LOAI = '"+ this.loai +"', DSTOITHIEUDH = '"+ this.dstoithieudh.replaceAll(",", "") +"', LOAIDS = '"+ this.loaiDS +"', TUNGAY = "+ tungay +", DENNGAY = "+ denngay +" "+
						" WHERE TIEUCHITINHTHUONG_FK = '" + this.tctId + "' AND PK_SEQ = '" + this.tcfk + "' ";*/

					query = "\n UPDATE TIEUCHITHUONG_CHITIET SET NOIDUNG = ?, " +
					"\n NGUOISUA = ?, NGAYSUA = ?, TOITHIEU = ?, " +
					"\n TOIDA = ?, NHOMSP_FK = ?, THUONG = ?, LOAI = ?, DSTOITHIEUDH = ?, LOAIDS = ?, TUNGAY = ?, DENNGAY = ? "+
					"\n WHERE TIEUCHITINHTHUONG_FK = '" + this.tctId + "' AND PK_SEQ = '" + this.tcfk + "' ";

					data2 = new Object[] {chuoi.substring(0, chuoi.length() - 1),this.userId,this.getDateTime(), this.toithieu.replaceAll(",", ""),
							this.toida.replaceAll(",", ""),(this.nhomspId.equals("") ? null :  this.nhomspId),
							this.thuongnsp.replaceAll(",", ""),this.loai,this.dstoithieudh.replaceAll(",", ""),this.loaiDS,tungay,denngay};
				}
				else
				{
					query = "\n UPDATE TIEUCHITHUONG_CHITIET SET NGUOISUA = ?, NGAYSUA = ?, TOITHIEU = ?, " +
					"\n TOIDA = ?, NHOMSP_FK = ?, THUONG = ?, LOAI = ?, DSTOITHIEUDH = ?, LOAIDS = ?, TUNGAY = ?, DENNGAY = ? "+
					"\n WHERE TIEUCHITINHTHUONG_FK = '" + this.tctId + "' AND PK_SEQ = '" + this.tcfk + "' ";

					data2 = new Object[] {this.userId,this.getDateTime(),this.toithieu.replaceAll(",", ""),this.toida.replaceAll(",", ""),
							(this.nhomspId.equals("") ? null :  this.nhomspId),this.thuongnsp.replaceAll(",", ""),this.loai,
							this.dstoithieudh.replaceAll(",", ""),this.loaiDS,tungay,denngay};
				}

				dbutils.viewQuery(query,data2);
				if (this.db.updateQueryByPreparedStatement(query,data2) != 1) {
					this.msg = "2. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
					this.db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return false;
				}

				/*System.out.println("116.Update TCT lan 2 : " + query);
					if(!this.db.update(query))
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						this.msg = "Lỗi cập nhật" + query;
						return false;
					}	*/		
				query = "DELETE TieuChiThuong_ChiTiet_MucThuong WHERE TCTCT_FK =" + this.tcfk;
				if(!this.db.update(query))
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					this.msg = "Lỗi cập nhật" + query;
					System.out.println("loi=" + query );
				}
				
				query = "SELECT PK_SEQ AS TCTCT_FK, NOIDUNG FROM TieuChiThuong_ChiTiet WHERE pk_Seq =" +this.tcfk;
				System.out.println(query);
				ResultSet rs = db.get(query);
				while(rs.next())
				{
					String[] strNoidung = chuoi.split(";");
					for(int i = 0; i < strNoidung.length; i++)
					{ 
						/*	query = "INSERT INTO TieuChiThuong_ChiTiet_MucThuong (TCTCT_FK, STT, TU, DEN, THUONG) VALUES ('"+ rs.getString("TCTCT_FK") +"', '"+ strNoidung[i].split(",")[1] +"', '"+ strNoidung[i].split(",")[2] +"', '"+ strNoidung[i].split(",")[3] +"', '"+ strNoidung[i].split(",")[4] +"')";
							if(!this.db.update(query))
							{
								db.getConnection().rollback();
								db.getConnection().setAutoCommit(true);
								this.msg = "Lỗi cập nhật" + query;
								return false;
							}*/

						query = "INSERT INTO TIEUCHITHUONG_CHITIET_MUCTHUONG (TCTCT_FK, STT, TU, DEN, THUONG) select ?,?,?,?,?";

						Object[] data1 = {rs.getString("TCTCT_FK"),strNoidung[i].split(",")[1],strNoidung[i].split(",")[2],
								strNoidung[i].split(",")[3],strNoidung[i].split(",")[4]};

						dbutils.viewQuery(query,data1);
						if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
							this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
							this.db.getConnection().rollback();
							db.getConnection().setAutoCommit(true);
							return false;
						}
					}
				}


				if(this.loaithuong.equals("2"))
				{}
				if(this.loaithuong.equals("1"))
				{}

				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				this.msg = "Tiêu chí tính thưởng đã được cập nhật";
				return true;
			}
			else
			{			
				db.getConnection().setAutoCommit(false);

				/*	query = "INSERT INTO TIEUCHITINHTHUONG(trangthai ,NgayTao,NguoiTao,NGAYSUA,NGUOISUA,DienGiai,THANG, NAM, DVKD_FK, KBH_FK, LOAI, TONGTHUONG, TYLEDOANHSOTOITHIEU) " +
				"SELECT 0,'" + this.getDateTime() + "', " + this.userId + ",'" + this.getDateTime() + "'," + this.userId + ",N'"+this.diengiai+"'," + this.thang + ", " + this.nam + ", " + this.dvkdId + ", "+ this.kbhId + ", "+this.LoaiTieuChi+", "+this.tongthuong+", "+this.tldstoithieu;
				if(!this.db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Lỗi::" + query;
					return false;
				}

				query = "SELECT SCOPE_IDENTITY() as id";
				ResultSet rs = this.db.get(query);
				rs.next();
				this.tctId = rs.getString("id");*/

				query = "\n INSERT INTO TIEUCHITINHTHUONG(trangthai,NgayTao,NguoiTao,NGAYSUA,NGUOISUA," +
				"\n DienGiai,THANG,NAM,DVKD_FK,KBH_FK,LOAI,TONGTHUONG,TYLEDOANHSOTOITHIEU) " +
				"\n select ?,?,?,?,?,?,?,?,?,?,?,?,?";
				Object[] data = {0,this.getDateTime(),this.userId,this.getDateTime(),this.userId,
						this.diengiai,this.thang,this.nam,this.dvkdId,this.kbhId,this.LoaiTieuChi,
						this.tongthuong,this.tldstoithieu};
				dbutils.viewQuery(query,data);
				if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
					this.msg = "3. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ Admin để xử lý";
					this.db.getConnection().rollback();
					return false;
				}
				this.tctId = db.getPk_seq();

				db.getConnection().commit();
				this.msg = "Tạo mới Loại chỉ tiêu thành công";
				return true;

			}
		}
		catch (Exception e) {
			this.msg = "Lỗi ngoại lệ 2: "+e.getMessage();
			e.printStackTrace();
			return false;
		}
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);	
	}	

	public void init()
	{
		//CreateRs();
		System.out.println("loai thuong "+this.loaithuong);
		String query = "";
		if(this.action.equals("copy"))
		{
			query = "INSERT INTO TIEUCHITINHTHUONG(THANG, NAM, DVKD_FK, KBH_FK, LOAI, TONGTHUONG, TYLEDOANHSOTOITHIEU,npp_fk, tructhuoc_fk) " +
			"SELECT THANG, NAM, DVKD_FK, KBH_FK, LOAI, TONGTHUONG, TYLEDOANHSOTOITHIEU,npp_fk, tructhuoc_fk FROM TIEUCHITINHTHUONG WHERE PK_SEQ = '" + this.tctId + "'";
			this.db.update(query);

			query = "SELECT SCOPE_IDENTITY() as id";
			ResultSet rs = this.db.get(query);
			try
			{
				rs.next();
				String id = rs.getString("id");
				query = "UPDATE TIEUCHITINHTHUONG SET DIENGIAI = ' ', THANG = ' ', NGUOITAO='" + this.userId + "', NGUOISUA='" + this.userId + "'," +
				"NGAYTAO = '" + this.getDateTime() + "', NGAYSUA = '" + this.getDateTime() + "', TRANGTHAI = '0' " +
				"WHERE PK_SEQ ='" + id + "'";
				this.db.update(query);


				query = "INSERT INTO TIEUCHITHUONG_CHITIET (TIEUCHITINHTHUONG_FK, DIENGIAI, NOIDUNG, TIEUCHI, NGUOISUA, NGAYSUA, TOITHIEU, TOIDA, NHOMSP_FK, THUONG, DSTOITHIEUDH, LOAI, LOAIDS, LOAINVBH, TUNGAY, DENNGAY) " +
				"SELECT '"+ id +"' AS TEUCHITINHTHUONG_FK, DIENGIAI, NOIDUNG, TIEUCHI, '"+ this.userId +"' AS NGUOISUA, '"+ this.getDateTime() +"' AS NGAYSUA, TOITHIEU, TOIDA, NHOMSP_FK, THUONG, DSTOITHIEUDH, LOAI, LOAIDS, LOAINVBH, TUNGAY, DENNGAY " +
				"FROM TIEUCHITHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK = '"+ this.tctId +"' ";

				//System.out.println("insert : "+query);
				this.db.update(query);

				query = "SELECT PK_SEQ AS TCTCT_FK, NOIDUNG FROM TieuChiThuong_ChiTiet WHERE TIEUCHITINHTHUONG_FK  =" +this.tctId;
				System.out.println(query);
				rs = db.get(query);
				while(rs.next())
				{
					String[] strNoidung = rs.getString("noidung").split(";");
					for(int i = 0; i < strNoidung.length; i++)
					{ 
						query = "INSERT INTO TieuChiThuong_ChiTiet_MucThuong (TCTCT_FK, STT, TU, DEN, THUONG) VALUES ('"+ rs.getString("TCTCT_FK") +"', '"+ strNoidung[i].split(",")[1] +"', '"+ strNoidung[i].split(",")[2] +"', '"+ strNoidung[i].split(",")[3] +"', '"+ strNoidung[i].split(",")[4] +"')";
						this.db.update(query);
					}
				}

				this.tctId = id;
			}catch(Exception e){}
		}


		if(this.tctId.length() > 0)
		{
			query = "\n SELECT TCT.PK_SEQ, TCT.DIENGIAI, KBH.PK_SEQ AS KBHID, " + 
			"\n DVKD.PK_SEQ AS DVKDID, TCT.THANG, TCT.NAM ,tct.loai, ISNULL(TCT.TONGTHUONG,0) AS TONGTHUONG, ISNULL(TCT.TYLEDOANHSOTOITHIEU, 0) AS TYLEDOANHSOTOITHIEU " +
			"\n FROM TIEUCHITINHTHUONG TCT " +
			"\n INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = TCT.KBH_FK " +
			"\n INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = TCT.DVKD_FK " +
			"\n WHERE TCT.PK_SEQ = '" + this.tctId + "' ";
			System.out.println("TCT: "+query);
			this.tct = this.db.get(query);
			try{
				this.tct.next();
				this.diengiai = this.tct.getString("diengiai");
				this.kbhId = this.tct.getString("kbhId");
				this.dvkdId = this.tct.getString("dvkdId");
				this.thang = this.tct.getString("thang");
				this.nam = this.tct.getString("nam");
				this.LoaiTieuChi=this.tct.getString("loai");

				this.tongthuong = this.tct.getString("tongthuong");
				this.tldstoithieu = this.tct.getString("tyledoanhsotoithieu");

				//System.out.println("Loai nek k: "+this.tct.getString("loai"));

			}catch(Exception e)
			{
				e.printStackTrace();
			}

			
			System.out.println("loai la "+this.loai);
			/*if(this.loaithuong.equals("3"))
			{
			query = 				
				"SELECT pk_seq, tieuchitinhthuong_fk, pk_seq as tcfk, noidung, nguoisua, ngaysua, isnull(toithieu, 0) as toithieu" +
				", isnull(toida, 0) as toida, isnull(nhomsp_fk, 0) as nhomsp_fk, isnull(thuong, 0) as thuong, isnull(loai, 0) as loai" +
				", isnull(dstoithieudh, 0) as dstoithieudh,   " +
				"ISNULL(LOAIDS, 0) AS LOAIDS, ISNULL(TUNGAY, '') AS TUNGAY, ISNULL(DENNGAY, '') AS DENNGAY " +
				"FROM TIEUCHITHUONG_CHITIET " + 
				"WHERE TIEUCHITINHTHUONG_FK = '"+ this.tctId +"' AND tieuchi='" + tc + "' ";
			}
			if(this.loaithuong.equals("2"))
			{
			query = 				
				"SELECT pk_seq, tieuchitinhthuong_fk, pk_seq as tcfk,  dbo.[Getthuong_chitieu](pk_seq,2,(" +
				" select distinct v.PK_SEQ from PHAMVIHOATDONG a inner join NHAPHANPHOI b "+
							 "  on a.Npp_fk=b.PK_SEQ inner join TINHTHANH tt on tt.PK_SEQ=b.TINHTHANH_FK "+
							 "	inner join VUNG v on v.PK_SEQ=tt.VUNG_FK inner join NHANVIEN nv "+
							 "	on nv.PK_SEQ=a.Nhanvien_fk where nv.PK_SEQ="+this.userId+ 
							 ")) as noidung, nguoisua, ngaysua, isnull(toithieu, 0) as toithieu" +
				", isnull(toida, 0) as toida, isnull(nhomsp_fk, 0) as nhomsp_fk, isnull(thuong, 0) as thuong, isnull(loai, 0) as loai" +
				", isnull(dstoithieudh, 0) as dstoithieudh,   " +
				"ISNULL(LOAIDS, 0) AS LOAIDS, ISNULL(TUNGAY, '') AS TUNGAY, ISNULL(DENNGAY, '') AS DENNGAY " +
				"FROM TIEUCHITHUONG_CHITIET " + 
				"WHERE TIEUCHITINHTHUONG_FK = '"+ this.tctId +"' AND tieuchi='" + tc + "' ";
			}
			if(this.loaithuong.equals("1"))
			{
			query = 				
				"SELECT pk_seq, tieuchitinhthuong_fk, pk_seq as tcfk,  dbo.[Getthuong_chitieu](pk_seq,1,( select distinct b.pk_seq from  NHAPHANPHOI b  \n"+
								 "					inner join TINHTHANH tt on tt.PK_SEQ=b.TINHTHANH_FK  \n"+
								 "					inner join VUNG vv on vv.PK_SEQ=tt.VUNG_FK   \n"+
								 "					inner join NHANVIEN nv  \n"+
								 "					on b.SITECODE=nv.CONVSITECODE   \n"+
								 "					 where nv.PK_SEQ="+this.userId+")"+
				 ") as noidung, nguoisua, ngaysua, isnull(toithieu, 0) as toithieu" +
				", isnull(toida, 0) as toida, isnull(nhomsp_fk, 0) as nhomsp_fk, isnull(thuong, 0) as thuong, isnull(loai, 0) as loai" +
				", isnull(dstoithieudh, 0) as dstoithieudh,   " +
				"ISNULL(LOAIDS, 0) AS LOAIDS, ISNULL(TUNGAY, '') AS TUNGAY, ISNULL(DENNGAY, '') AS DENNGAY " +
				"FROM TIEUCHITHUONG_CHITIET " + 
				"WHERE TIEUCHITINHTHUONG_FK = '"+ this.tctId +"' AND tieuchi='" + tc + "' ";
			}
			*/

			query = 				
				"\n SELECT pk_seq, tieuchitinhthuong_fk, pk_seq as tcfk, noidung, nguoisua, ngaysua, isnull(toithieu, 0) as toithieu" +
				"\n , isnull(toida, 0) as toida, isnull(nhomsp_fk, 0) as nhomsp_fk, isnull(thuong, 0) as thuong, isnull(loai, 0) as loai" +
				"\n , isnull(dstoithieudh, 0) as dstoithieudh,   " +
				"\n ISNULL(LOAIDS, 0) AS LOAIDS, ISNULL(TUNGAY, '') AS TUNGAY, ISNULL(DENNGAY, '') AS DENNGAY " +
				"\n FROM TIEUCHITHUONG_CHITIET " + 
				"\n WHERE TIEUCHITINHTHUONG_FK = '"+ this.tctId +"' AND tieuchi='" + tc + "' ";

			if(this.tcfk != null && !this.tcfk.equals(""))
			{ 
				query += " and pk_seq = '"+ this.tcfk +"'"; 
			}
			this.tct = this.db.getScrol(query);
			try {
				while (tct.next()){
					this.nhomsp_fk = tct.getString("nhomsp_fk");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.println("1.Query Khoi Tao: " + query);

			//query  = "SELECT DISTINCT TIEUCHI AS TC, DIENGIAI FROM TIEUCHITINHTHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK='"+ this.tctId  +"'";
			query  = "\n SELECT a.pk_seq AS TC, a.tieuchi, a.DIENGIAI+' ('+b.diengiai+')' diengiai " +
					"\n FROM TIEUCHITHUONG_CHITIET a  inner join tieuchitinhthuong tong on a.tieuchitinhthuong_Fk = tong.pk_seq " +
					"\n inner join tieuchi b on a.tieuchi = b.tieuchi and b.LOAI = tong.LOAI " +
					"\n WHERE a.TIEUCHITINHTHUONG_FK='"+ this.tctId  +"' order by a.tieuchi, a.pk_seq asc ";
			System.out.println("2.Get Tieu Chi: " + query);

			this.tieuchi = this.db.get(query);

			
		}
		CreateRs();

	}
	public void CreateRs() {

		this.rsDVKD=db.get("select pk_Seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai = 1 ");
		this.rsKenh=db.get("select pk_Seq,ten from kenhbanhang where trangthai = 1 ");
		
		String text = "";
		
		if (this.nhomsp_fk !=  null && this.nhomsp_fk.length() > 0) {
			text = "\n select PK_SEQ, (CAST(PK_SEQ AS VARCHAR(50)) + ' - ' + TEN) AS TEN from NhomSanPhamChiTieu where pk_seq = "+this.nhomsp_fk+
			"\n union all " +
			"\n select PK_SEQ, (CAST(PK_SEQ AS VARCHAR(50)) + ' - ' + TEN) AS TEN from NhomSanPhamChiTieu " +
			"\n where TRANGTHAI = '1' and month(tungay)<= "+this.thang+" and month(denngay) >= "+this.thang + " " +
			"\n and year(tungay) <= " + this.nam + " and year(denngay) >= " + this.nam;
		}
		else {
			text = "\n select PK_SEQ, (CAST(PK_SEQ AS VARCHAR(50)) + ' - ' + TEN) AS TEN from NhomSanPhamChiTieu " +
			"\n where TRANGTHAI = '1' and month(tungay)<= "+this.thang+" and month(denngay) >= "+this.thang + " " +
			"\n and year(tungay) <= " + this.nam + " and year(denngay) >= " + this.nam;
		}
		this.nhomsp = db.get(text);
		
		this.nhomtc = this.db.get("SELECT * FROM TIEUCHI WHERE LOAI = '"+ this.LoaiTieuChi +"' order by tieuchi ");
	}

	public void closeDB(){
		try{
			if(rsDVKD!=null){
				rsDVKD.close();
			}
			if(rsKenh!=null){
				rsKenh.close();
			}
			if (this.db != null)
				this.db.shutDown();
		}catch(Exception er){

		}
	}


	public void SetLoaiTieuChi(String loaitieuchi) {

		this.LoaiTieuChi=loaitieuchi;
	}


	public String GetLoaiTieuChi() {

		return this.LoaiTieuChi;
	}


	public ResultSet GetRsKenh() {

		return this.rsKenh;
	}


	public ResultSet GetRsDVKD() {

		return this.rsDVKD;
	}


	public void setToithieu(String toithieu) {

		this.toithieu = toithieu;
	}


	public String getToithieu() {

		return this.toithieu;
	}


	public void setToida(String toida) {

		this.toida = toida;
	}


	public String getToida() {

		return this.toida;
	}


	public ResultSet GetRsNhomSp() {

		return this.nhomsp;
	}


	public String getNhomsp() {

		return this.nhomspId;
	}


	public void setNhomsp(String nhomsp) {

		this.nhomspId = nhomsp;
	}


	public void setLoai(String loai) {

		this.loai = loai;
	}


	public String getLoai() {

		return this.loai;
	}


	public void setThuongnsp(String thuongnsp) {

		this.thuongnsp = thuongnsp;
	}


	public String getThuongnsp() {

		return this.thuongnsp;
	}


	public void setDstoithieuDH(String dstoithieudh) {

		this.dstoithieudh = dstoithieudh;
	}


	public String getDstoithieuDH() {

		return this.dstoithieudh;
	}


	public void setTongThuong(String tongthuong) {

		this.tongthuong = tongthuong;
	}


	public String getTongThuong() {

		return this.tongthuong;
	}


	public void setTileDStoithieu(String tldstoithieu) {

		this.tldstoithieu = tldstoithieu;
	}


	public String getTileDStoithieu() {

		return this.tldstoithieu;
	}


	public void setTieuchiFK(String tcfk) {

		this.tcfk = tcfk;	
	}


	public String getTieuchiFK() {

		return this.tcfk;
	}


	public void setLoaiDS(String loaids) {

		this.loaiDS = loaids;
	}


	public String getLoaiDS() {

		return this.loaiDS;
	}


	public void setNhomtcRS(ResultSet nhomtc) {

		this.nhomtc = nhomtc;
	}


	public ResultSet getNhomtcRS() {

		return this.nhomtc;
	}


	public void setTCDiengiai(String tcdiengiai) {

		this.tcdiengiai = tcdiengiai;
	}


	public String getTCDiengiai() {

		return this.tcdiengiai;
	}


	public void setTCNhomId(String nhomtcid) {

		this.nhomtcId = nhomtcid;
	}


	public String getTCNhomId() {

		return this.nhomtcId;
	}

	@Override
	public boolean xoaTC() {

		boolean result = true;
		String query = "DELETE TIEUCHITHUONG_CHITIET WHERE PK_SEQ = '"+ this.tcfk +"' AND TIEUCHITINHTHUONG_FK = '"+ this.tctId +"'";
		System.out.println("delete tc : "+query);
		if(!this.db.update(query)){
			result = false;
		}
		query = "DELETE TieuChiThuong_ChiTiet_MucThuong WHERE TCTCT_FK = '"+ this.tcfk +"' ";
		System.out.println("delete tc : "+query);
		if(!this.db.update(query)){
			result = false;
		}
		if(result) 
			this.msg = "Tiêu chí đã được xóa.";
		else
			this.msg = "Lỗi trong quá trình xóa tiêu chí. Vui lòng liên hệ trung tâm để được xử lý.";
		return result;
	}

	@Override
	public String getTungay() {
		// TODO Auto-generated method stub
		return this.tungay;
	}

	@Override
	public void setTungay(String tungay) {
		// TODO Auto-generated method stub
		this.tungay = tungay;
	}

	@Override
	public String getDenngay() {
		// TODO Auto-generated method stub
		return this.denngay;
	}

	@Override
	public void setDenngay(String denngay) {
		// TODO Auto-generated method stub
		this.denngay = denngay;
	}
	
	public String[] getTctctYeuCauIdList() {
		return tctctYeuCauIdList;
	}
	public void setTctctYeuCauIdList(String[] tctctYeuCauIdList) {
		this.tctctYeuCauIdList = tctctYeuCauIdList;
	}
	public String[] getGiatriTctctList() {
		return giatriTctctList;
	}
	public void setGiatriTctctList(String[] giatriTctctList) {
		this.giatriTctctList = giatriTctctList;
	}
	public ResultSet getTctctYeuCauRs() {
		return tctctYeuCauRs;
	}
	
	public String[] getSoluongSpDieuKienList() {
		return soluongSpDieuKienList;
	}
	public String[] getSpDieuKienList() {
		return spDieuKienList;
	}
	public ResultSet getSpDieuKienRs() {
		return spDieuKienRs;
	}
	public void setSoluongSpDieuKienList(String[] soluongSpDieuKienList) {
		this.soluongSpDieuKienList = soluongSpDieuKienList;
	}
	public void setSpDieuKienList(String[] spDieuKienList) {
		this.spDieuKienList = spDieuKienList;
	}
	public void setSpDieuKienRs(ResultSet spDieuKienRs) {
		this.spDieuKienRs = spDieuKienRs;
	}
	
}
