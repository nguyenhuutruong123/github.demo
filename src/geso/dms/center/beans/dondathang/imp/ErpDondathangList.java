package geso.dms.center.beans.dondathang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.dondathang.IErpDondathangList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

public class ErpDondathangList extends Phan_Trang implements IErpDondathangList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String vungId, khuvucId, kbhId;
	ResultSet vungRs, khuvucRs, kbhRs;
	
	String nppTen;
	String msg;
	String Sodondathang = "";
	ResultSet nppRs;
	ResultSet DondathangRs;
	
	String loaidonhang;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public ErpDondathangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
		currentPages = 1;
		num = 1;
		
		this.vungId = "";
		this.khuvucId = "";
		this.kbhId = "";			
		
		this.db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
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

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String search)
	{
		String query = "";
		
		Utility util = new Utility();
        
		/*query="select distinct  a.TRANGTHAI,b.sopo,a.soso,a.ngaychungtu,a.sochungtu, a.kenh,'100368' as nguoitao,'100368' as nguoisua,a.manhapp as manpp,a.loai_phieu  \n"+
			  "	 from apps.TBL_PHIEUXUATKHO a \n"+
			  "	 inner join apps.V_DONHANG b on a.soso=b.soso where  loai_phieu like N'%PHIEU XUAT KHO%' \n"+
			  "	 and  to_char(a.created_date, 'yyyy-mm-dd') > '2015-12-06' and a.trangthai=0 and b.sopo is not null ";
		
		try {
			
			OracleConnUtils dbsys=new OracleConnUtils();
			ResultSet rssys=dbsys.get(query);
			try {
				while(rssys.next())
				{
					db.getConnection().setAutoCommit(false);
					query="update ERP_Dondathang set soso='"+rssys.getString("soso")+"' where pk_seq="+rssys.getString("sopo");
					if(db.updateReturnInt(query)<=0)
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
					}
					else
					{
						db.getConnection().commit();
						db.getConnection().setAutoCommit(true);
					}
						
				}
				rssys.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dbsys.shutDown();
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		if(search.length() > 0)
			query = search;
		else
		{
			query = 
					"select a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN, b.ten + '-' + b.diengiai as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, "+
					"		NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE ,  " +
					"   	(	SELECT COUNT(*) "+   
					"   		FROM ERP_YCXUATKHO_DDH "+   
					"   			INNER JOIN ERP_YCXUATKHO ON ERP_YCXUATKHO_DDH.ycxk_fk=ERP_YCXUATKHO.PK_SEQ "+   
					"   		WHERE ERP_YCXUATKHO_DDH.ddh_fk=a.PK_SEQ AND ERP_YCXUATKHO.TRANGTHAI=2 "+   
					"   	) as DaXuatKho,TT_DuyetLai,a.ckThanhToan, a.soso,isnull(a.avat,0) as avat, (select ten from vung where pk_seq in (select vung_fk from khuvuc where pk_seq = c.khuvuc_fk)) as vungTen "+					
					"from ERP_Dondathang a left join KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 and a.NPP_DACHOT = '1' and a.kbh_fk in "+util.quyen_kenh(this.userId);
					//AND A.NPP_FK IN  "+util.quyen_npp(userId) +"  ";
		} 
		
		query += " and a.loaidonhang = '" + this.loaidonhang + "' ";
		query += " and a.npp_fk in "+util.quyen_npp(this.userId);
		System.out.println("___CHUYEN KHO: " + query);
//		this.DondathangRs = createSplittingData_New(db,50, 10, " trangthai asc,ngaydonhang desc ", query);
		this.DondathangRs = super.createSplittingData_v2(db,super.getItems(), super.getSplittings(), "pk_seq desc", query, dataSearch, "");
		
		String sql = "";
		this.kbhRs = db.get(" select pk_seq,ten,diengiai from kenhbanhang ");

		this.vungRs = db.get("select pk_seq,ten,diengiai from vung ");	

		if (this.vungId.length() > 0)
		{
			this.khuvucRs = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
		} else
			this.khuvucRs = db.get("select pk_seq,ten from khuvuc ");

		sql = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' ";
		if (this.khuvucId.length() > 0)
		{
			sql = sql + " and khuvuc_fk ='" + this.khuvucId + "'";
		}
		if (this.vungId.length() > 0)
		{
			sql = sql + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		}				
		if (this.kbhId.length() > 0)
			sql = sql + " and pk_seq in (select npp_fk from NHAPP_KBH where kbh_fk ='" + this.kbhId + "')";						
			sql += " order by ten ";
		
		this.nppRs = db.getScrol(sql);		
		
		//this.nppRs = db.get("select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'");
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public ResultSet getDondathangRs() 
	{
		return this.DondathangRs;
	}

	public void setDondathangRs(ResultSet nkRs) 
	{
		this.DondathangRs = nkRs;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getNppTen() {
		
		return this.nppTen;
	}

	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getVungId() {
		
		return this.vungId;
	}

	
	public void setVungId(String vungId) {
		
		this.vungId = vungId;
	}

	
	public ResultSet getVungRs() {
		
		return this.vungRs;
	}

	
	public void setVungRs(ResultSet vungRs) {
		
		this.vungRs = vungRs;
	}

	
	public String getKhuvucId() {
		
		return this.khuvucId;
	}

	
	public void setKhuvucId(String kvId) {
		
		this.khuvucId = kvId;
	}

	
	public ResultSet getKhuvucRs() {
		
		return this.khuvucRs;
	}

	
	public void setKhuvucRs(ResultSet kvRs) {
		
		this.khuvucRs = kvRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	
	public String getSoDonDathang() {
		
		return this.Sodondathang;
	}

	
	public void setSoDonDathang(String SoDonDathang) {
		
		this.Sodondathang  = SoDonDathang;
	}

	public List<Object> getDataSearch() {
		return dataSearch;
	}

	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}

}
