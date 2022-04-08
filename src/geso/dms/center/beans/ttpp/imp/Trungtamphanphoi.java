package geso.dms.center.beans.ttpp.imp;

import geso.dms.center.beans.ttpp.ITrungtamphanphoi;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import com.nhat.replacement.TaskReplacement;

public class Trungtamphanphoi implements ITrungtamphanphoi {
	private static final long serialVersionUID = -9217977546733610215L;
	String id;
	String ma;
	String ten;
	String trangthai;
	String msg;
	dbutils db;

	public Trungtamphanphoi() {
		this.id = "";
		this.ma = "";
		this.trangthai = "";
		this.ten = "";
		this.msg = "";
		this.db = new dbutils();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getma() {
		return this.ma;
	}

	public void setma(String ma) {
		this.ma = ma;
	}

	public String getten() {
		return this.ten;
	}

	public void setten(String ten) {
		this.ten = ten;
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getMessage() {
		return this.msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public void init() {
		String query = "select ma,trangthai,ten from TrungTamPhanPhoi where pk_seq='"
				+ this.id + "'";
		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			this.ma = rs.getString("ma");
			this.trangthai = rs.getString("trangthai");
			this.ten = rs.getString("ten");
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		createRs();

		query = "select ncc_fk from TTPP_NCC where ttpp_fk='" + this.id + "'";
		rs = db.get(query);
		try {
			String _nccId = "";
			while (rs.next()) {
				_nccId += rs.getString("ncc_fk") + "__";
			}
			rs.close();
			if (_nccId.length() > 0) {
				_nccId = _nccId.substring(0, _nccId.length() - 2);
				this.nccId = _nccId.split("__");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	boolean masanpham() {
		String sql;
		if (this.id.length() == 0) {
			sql = "select count(*) as num from TrungTamPhanPhoi where ma ='"
					+ this.ma + "'";
		} else {
			sql = "select count(*) as num from TrungTamPhanPhoi where pk_seq <> '"
					+ this.id + "' and ma ='" + this.ma + "'";
		}
		ResultSet rs = db.get(sql);
		if (rs != null)
			try {
				rs.next();
				if (rs.getString("num").equals("0"))
					return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		return true;
	}

	public boolean save() {
		try {
			if (masanpham()) {
				this.msg = "Mã trung tâm phân phối đã trùng rồi !";
				return false;
			}

			db.getConnection().setAutoCommit(false);

			/*
			 * String query =
			 * "insert into TrungTamPhanPhoi( Ma,ngaytao,ngaysua,nguoitao,nguoisua,trangthai,ten) "
			 * + " values(N'" + this.ma + "','" + this.getDateTime() + "','" +
			 * this.getDateTime() + "','" + this.userId + "','" + this.userId +
			 * "','" + this.trangthai + "',N'" + this.ten + "')"; if
			 * (!db.update(query)) { geso.dms.center.util.Utility.rollback_throw_exception(db); this.msg =
			 * "Lỗi phát sinh khi cập nhật thông tin "+query; return false; }
			 */

			String cauQuery = "insert into TrungTamPhanPhoi( Ma,ngaytao,ngaysua,nguoitao,nguoisua,trangthai,ten) values(?,?,?,?,?,?,?)";
			Object[] dataQuery = { this.ma, this.getDateTime(),
					this.getDateTime(), this.userId, this.userId,
					this.trangthai, this.ten };
			if (this.db.updateQueryByPreparedStatement(cauQuery, dataQuery) != 1) {
				dbutils.viewQuery(cauQuery, dataQuery);
				this.msg = "Error At : bab75f5a-8ad4-4599-ac1d-f2d91a52db73";
				this.db.getConnection().rollback();
				return false;
			}

/*			String query = "select  scope_identity() as maId";
			ResultSet rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("maId");
			rs.close();*/
			
			String idTemp = db.getPk_seq();

			if (this.nccId != null) {
				for (int i = 0; i < this.nccId.length; i++) {
					if (this.nccId[i] != null) {
						/*
						 * query =
						 * "insert into TTPP_NCC(NCC_FK,TTPP_FK) values('" +
						 * nccId[i] + " ' , '" + this.id + "')"; if
						 * (!this.db.update(query)) { this.msg =
						 * "Lỗi phát sinh khi cập nhật thông tin "+query;
						 * geso.dms.center.util.Utility.rollback_throw_exception(this.db); return false; }
						 */

						String cauQuery1 = "insert into TTPP_NCC(NCC_FK,TTPP_FK) values(?  , ?)";
						Object[] dataQuery1 = { nccId[i], idTemp };
						if (this.db.updateQueryByPreparedStatement(cauQuery1,
								dataQuery1) != 1) {
							dbutils.viewQuery(cauQuery1, dataQuery1);
							this.msg = "Error At : 20a19c74-410e-48b4-a74d-615d0bbd8e99";
							this.db.getConnection().rollback();
							return false;
						}

					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}

		return true;
	}

	public void DBClose() {
		this.db.shutDown();
	}

	@Override
	public String getMa() {
		return ma;
	}

	@Override
	public void setMa(String ma) {
		this.ma = ma;
	}

	@Override
	public String getTen() {
		return this.ten;
	}

	@Override
	public void setTen(String ten) {
		this.ten = ten;
	}

	ResultSet nccRs;

	@Override
	public ResultSet getNccRs() {
		return this.nccRs;
	}

	@Override
	public void setNccRs(ResultSet nccRs) {
		this.nccRs = nccRs;
	}

	@Override
	public boolean edit() {
		try {
			if (masanpham()) {
				this.msg = "Mã trung tâm phân phối đã trùng rồi !";
				return false;
			}

			db.getConnection().setAutoCommit(false);

			/*
			 * String query =
			 * "update TrungTamPhanPhoi set Ma=N'"+this.ma+"',ngaysua='"
			 * +this.getDateTime
			 * ()+"',nguoisua='"+this.userId+"',trangthai='"+this
			 * .trangthai+"',ten=N'"+this.ten+"' where pk_Seq='"+this.id+"' " ;
			 * if (!db.update(query)) { geso.dms.center.util.Utility.rollback_throw_exception(db); this.msg =
			 * "Lỗi phát sinh khi cập nhật thông tin "+query; return false; }
			 */

			String cauQuery = "update TrungTamPhanPhoi set Ma=?,ngaysua=?,nguoisua=?,trangthai=?,ten=? where pk_Seq=?";
			Object[] dataQuery = { this.ma, this.getDateTime(), this.userId,
					this.trangthai, this.ten, this.id };
			
			dbutils.viewQuery(cauQuery, dataQuery);
			if (this.db.updateQueryByPreparedStatement(cauQuery, dataQuery) != 1) {
				
				this.msg = "Error At : 031ad7fb-c82c-4e94-b189-bb985326b416";
				this.db.getConnection().rollback();
				return false;
			}

			String query = "delete from TTPP_NCC where ttpp_fk='" + this.id
					+ "'";
			if (!this.db.update(query)) {
				this.msg = "Lỗi phát sinh khi cập nhật thông tin " + query;
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}

			if (this.nccId != null) {
				for (int i = 0; i < this.nccId.length; i++) {
					if (this.nccId[i] != null) {
						/*
						 * query =
						 * "insert into TTPP_NCC(NCC_FK,TTPP_FK) values('" +
						 * nccId[i] + " ' , '" + this.id + "')"; if
						 * (!this.db.update(query)) { this.msg =
						 * "Lỗi phát sinh khi cập nhật thông tin "+query;
						 * geso.dms.center.util.Utility.rollback_throw_exception(this.db); return false; }
						 */

						String cauQuery1 = "insert into TTPP_NCC(NCC_FK,TTPP_FK) values(?  , ?)";
						Object[] dataQuery1 = { nccId[i], this.id };
						if (this.db.updateQueryByPreparedStatement(cauQuery1,
								dataQuery1) != 1) {
							dbutils.viewQuery(cauQuery1, dataQuery1);
							this.msg = "Error At : 0ff38406-6f8a-47f1-939d-52321319a6ef";
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}

		return true;
	}

	String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	String[] nccId;

	public String[] getNccId() {
		return nccId;
	}

	public void setNccId(String[] nccId) {
		this.nccId = nccId;
	}

	@Override
	public Hashtable<Integer, String> getNccSelected() {
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.nccId != null) {
			int size = (this.nccId).length;
			int m = 0;
			while (m < size) {
				selected.put(new Integer(m), nccId[m]);
				m++;
			}
		} else {
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void createRs() {
		String query = "select PK_SEQ,TEN,DIACHI,MASOTHUE,TENVIETTAT from NHACUNGCAP where TRANGTHAI=1 ";
		this.nccRs = this.db.get(query);
	}

	public static void main(String[] args) {
		TaskReplacement t = new TaskReplacement();
		String inl = "insert into TTPP_NCC(NCC_FK,TTPP_FK) values('\" + nccId[i] + \" ' , '\" + this.id + \"')";
		System.out.println(t.taoQuery(inl));

	}

}
