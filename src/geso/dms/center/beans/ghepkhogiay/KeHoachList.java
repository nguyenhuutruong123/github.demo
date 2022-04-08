package geso.dms.center.beans.ghepkhogiay;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;

import java.sql.ResultSet;

public class KeHoachList  extends Phan_Trang{
	private String tuNgay;
	private String denNgay;
	private String trangThai;
	private String toHopId;
	private String kichBanId;
	private String donHangId;
	private ResultSet keHoachRs;
	private dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	public KeHoachList()
	{
		this.tuNgay = "";
		this.denNgay = "";
		this.trangThai = "";
		this.toHopId = "";
		this.kichBanId = "";
		this.donHangId = "";
		this.currentPages = 1;
		this.num = 1;
		db = new dbutils();
	}
	
	public void init() 
	{
		StringBuilder query = new StringBuilder("SELECT KB.PK_SEQ, KB.TRANGTHAI, KB.NGAYTAO, KB.NGAYSUA, KB.NGAYKEHOACH, KB.TONGHAOHUT, \n" +
												"ISNULL(NVT.TEN, '') AS NGUOITAO, ISNULL(NVS.TEN, '') AS NGUOISUA \n" +
												" FROM KICHBAN  KB " + 
												" LEFT JOIN NHANVIEN NVT ON NVT.PK_SEQ = KB.NGUOITAO \n" +
												" LEFT JOIN NHANVIEN NVS ON NVS.PK_SEQ = KB.NGUOISUA \n" +
												" WHERE 1 = 1");
		
		if (this.tuNgay.trim().length() > 0)
		{
			query.append(" AND KB.NGAYTAO >= '" + this.tuNgay + "' \n");
		}
		if (this.denNgay.trim().length() > 0)
		{
			query.append(" AND KB.NGAYTAO <= '" + this.denNgay + "' \n");
		}
		if (this.trangThai.trim().length() > 0)
		{
			query.append(" AND KB.TRANGTHAI = '" + this.trangThai + "' \n");
		}
		
		if (this.toHopId.trim().length() > 0)
			query.append(" AND KB.PK_SEQ = (SELECT TH.KICHBAN_FK FROM TOHOP TH WHERE TH.PK_SEQ = " + this.toHopId + ") \n");
		
		if (this.kichBanId.trim().length() > 0)
			query.append(" AND KB.PK_SEQ = " + this.kichBanId + " \n");
		
		if (this.donHangId.trim().length() > 0)
			query.append(" AND KB.PK_SEQ IN (SELECT KB1.PK_SEQ \n" + 
			" FROM KICHBAN KB1  \n" + 
			" INNER JOIN TOHOP TH ON TH.KICHBAN_FK = KB1.PK_SEQ \n" + 
			" INNER JOIN TOHOP_CHITIET CT ON CT.TOHOP_FK = TH.PK_SEQ \n" + 
			" WHERE CT.DONHANG_FK = " + this.donHangId + ") \n");
		
		System.out.println("query init kich ban: " + query);
		this.keHoachRs = createSplittingData(50, 10, "NGAYTAO desc, NGAYSUA desc, trangthai asc ", query.toString());
	}
	
	public void DBclose() 
	{
		try 
		{
			if(this.keHoachRs != null)
				this.keHoachRs.close();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(this.db != null)
				this.db.shutDown();
		}
	}
	public String getTuNgay() {
		return tuNgay;
	}
	public void setTuNgay(String tuNgay) {
		this.tuNgay = tuNgay;
	}
	public String getDenNgay() {
		return denNgay;
	}
	public void setDenNgay(String denNgay) {
		this.denNgay = denNgay;
	}
	public ResultSet getKeHoachRs() {
		return keHoachRs;
	}
	public void setKeHoachRs(ResultSet keHoachRs) {
		this.keHoachRs = keHoachRs;
	}
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}
	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public int getCurrentPage()
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages() 
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from ERP_YEUCAUNGUYENLIEU");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setToHopId(String toHopId) {
		this.toHopId = toHopId;
	}

	public String getToHopId() {
		return toHopId;
	}

	public void setKichBanId(String kichBanId) {
		this.kichBanId = kichBanId;
	}

	public String getKichBanId() {
		return kichBanId;
	}

	public void setDonHangId(String donHangId) {
		this.donHangId = donHangId;
	}

	public String getDonHangId() {
		return donHangId;
	}
}
