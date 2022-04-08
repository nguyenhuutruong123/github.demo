package geso.dms.center.beans.asm.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.asm.IAsmList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

public class AsmList extends Phan_Trang implements IAsmList {
	/**
	 * 
	 */
	
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	private static final long serialVersionUID = 1L;
	public String asmTen;
	public String asmMa;
	public String dienthoai;
	public String email;
	public String diachi;
	public String kbhId;
	public String dvkdId;
	public String kvId;
	String thuviec;
	public String trangthai;
	public String msg;
	public ResultSet kbh;
	public ResultSet kv;
	public ResultSet dvkd;
	public ResultSet asmlist;
	public dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	public AsmList(){
		this.asmTen = "";
		this.asmMa = "";
		this.dienthoai = "";
		this.email = "";
		this.diachi = "";
		this.kbhId = "";
		this.dvkdId = "";
		this.kvId = "";
		this.trangthai = "";
		this.msg = "";
		this.thuviec="";
		num = 1;
		currentPages = 1;
		this.db = new dbutils();
	}
	
	public String getTen(){
		return this.asmTen;
	}
	
	public void setTen(String asmTen){
		this.asmTen = asmTen;
	}
	
	public String getDienthoai(){
		return this.dienthoai;
	}
	
	public void setDienthoai(String dienthoai){
		this.dienthoai = dienthoai;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}

	public String getDiachi(){
		return this.diachi;
	}
	
	public void setDiachi(String diachi){
		this.diachi = diachi;
	}

	public String getKbhId(){
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId){
		this.kbhId = kbhId;
	}

	public String getDvkdId(){
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId){
		this.dvkdId = dvkdId;
	}

	public String getKvId(){
		return this.kvId;
	}
	
	public void setKvId(String kvId){
		this.kvId = kvId;
	}

	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}

	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getKbh(){
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh){
		this.kbh = kbh;
	}


	public ResultSet getDvkd(){
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd){
		this.dvkd = dvkd;
	}

	public ResultSet getKv(){
		return this.kv;
	}
	
	public void setKv(ResultSet kv){
		this.kv = kv;
	}

	public ResultSet getAsmlist(){
		return this.asmlist;
	}
	
	public void setAsmlist(ResultSet asmlist){
		this.asmlist = asmlist;
	}
	
	public void init(){
		String sql = 	"SELECT PK_SEQ AS KBHID, TEN AS KBH FROM KENHBANHANG WHERE TRANGTHAI='1' "; //AND PK_SEQ='100000' OR PK_SEQ = '100002'";
		this.kbh = this.db.get(sql);
		
		sql  	=		"SELECT PK_SEQ AS KVID, TEN AS KV FROM KHUVUC WHERE TRANGTHAI='1'";
		this.kv = this.db.get(sql);
		
		sql 	= 		"SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH WHERE TRANGTHAI='1'";
		this.dvkd = this.db.get(sql);
		
		sql 	=		" SELECT ASM.PK_SEQ AS ASMID, ASM.SMARTID AS ASMMA,isnull(ASM.TINHTRANG,'1') TinhTrang, ASM.TEN AS ASMTEN, ASM.DIENTHOAI, ASM.TRANGTHAI, KBH.TEN AS KBH, " + 
						"\n ASM.NGAYTAO, NV1.TEN AS NGUOITAO, ASM.NGAYSUA, NV2.TEN AS NGUOISUA, "+ // KHUVUC.PK_SEQ AS KVID," +
						"\n [dbo].[GetVungASM](asm.pk_seq) as KV, DVKD.DONVIKINHDOANH AS DVKD " +
						"\n FROM ASM ASM " +
						"\n left JOIN KENHBANHANG KBH ON KBH.PK_SEQ = ASM.KBH_FK " +
						//"left JOIN ASM_KHUVUC ASM_KV ON ASM_KV.ASM_FK = ASM.PK_SEQ " +
						//"left JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = ASM_KV.KHUVUC_FK " +
						"\n INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = ASM.NGUOITAO " +
						"\n INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = ASM.NGUOISUA " +
						"\n left JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = ASM.DVKD_FK " +
						"\n WHERE ASM.TRANGTHAI >= '0' ";
		
		if (this.kbhId.length() > 0) 
		{
			sql = sql + "AND KBH.PK_SEQ = ? ";
			this.getDataSearch().add( "" + this.kbhId  + "" );	

		}
		if (this.dvkdId.length() > 0) 
		{
			sql = sql + "AND DVKD.PK_SEQ = ? ";
			this.getDataSearch().add( "" + this.dvkdId  + "" );	
			
		}
		if (this.trangthai.length() > 0 & !this.trangthai.equals("2")) 
		{
			sql = sql + "AND ASM.TRANGTHAI = ? ";
			this.getDataSearch().add( "" + this.trangthai  + "" );	

		}
		if (this.asmTen.length() > 0) 
		{
			sql = sql + "AND dbo.ftBoDau(ASM.TEN) LIKE  N'%'+upper(dbo.ftBoDau(?))+'%'";
			this.getDataSearch().add( "" + this.asmTen  + "" );	

		}
		if (this.asmMa.length() > 0) 
		{
			sql = sql + "AND ASM.SMARTID LIKE ? ";
			this.getDataSearch().add( "%" + this.asmMa  + "%" );	

		}
		if (this.dienthoai.length() > 0) 
		{
			sql = sql + "AND ASM.DIENTHOAI LIKE ? ";
			this.getDataSearch().add( "%" + this.dienthoai  + "%" );	
		}
		if (this.thuviec.length() > 0) 
		{
			sql = sql + "AND isnull(ASM.tinhtrang,'1')  = ? ";
			this.getDataSearch().add( "" + this.thuviec  + "" );	

		}
		if (this.kvId.length() > 0) 
		{
			sql =" SELECT ASM.PK_SEQ AS ASMID, ASM.SMARTID AS ASMMA,isnull(ASM.TINHTRANG,'1') TinhTrang, ASM.TEN AS ASMTEN, ASM.DIENTHOAI, ASM.TRANGTHAI, KBH.TEN AS KBH, " + 
					"\n ASM.NGAYTAO, NV1.TEN AS NGUOITAO, ASM.NGAYSUA, NV2.TEN AS NGUOISUA, "+ // KHUVUC.PK_SEQ AS KVID, KHUVUC.TEN AS KV," +
					"\n [dbo].[GetVungASM](asm.pk_seq) as KV, DVKD.DONVIKINHDOANH AS DVKD " +
					"\n FROM ASM ASM " +
					"\n left JOIN KENHBANHANG KBH ON KBH.PK_SEQ = ASM.KBH_FK " +
					"\n left JOIN ASM_KHUVUC ASM_KV ON ASM_KV.ASM_FK = ASM.PK_SEQ " +
					"\n left JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = ASM_KV.KHUVUC_FK " +
					"\n INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = ASM.NGUOITAO " +
					"\n INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = ASM.NGUOISUA " +
					"\n left JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = ASM.DVKD_FK " +
					"\n WHERE ASM.TRANGTHAI >= '0' "+
					"\n AND KHUVUC.PK_SEQ = '" + this.kvId + "' ";
			
			if (this.kbhId.length() > 0) 
			{
				sql = sql + "AND KBH.PK_SEQ =?";
				this.getDataSearch().add( "" + this.kbhId  + "" );	

			}
			if (this.dvkdId.length() > 0)
			{
				sql = sql + "AND DVKD.PK_SEQ = ?";
				this.getDataSearch().add( "" + this.dvkdId  + "" );	

			}
			if (this.trangthai.length() > 0 & !this.trangthai.equals("2"))
			{
				sql = sql + "AND ASM.TRANGTHAI = ? ";
				this.getDataSearch().add( "" + this.trangthai  + "" );	

			}
			if (this.asmTen.length() > 0)
			{
				sql = sql + "AND dbo.ftBoDau(ASM.TEN) LIKE  N'%'+upper(dbo.ftBoDau(?))+'%'";
				this.getDataSearch().add( "" + this.asmTen  + "" );	

			}
			if (this.asmMa.length() > 0)
			{
				sql = sql + "AND ASM.SMARTID LIKE ? ";
				this.getDataSearch().add( "%" + this.asmMa  + "%" );	

			}
			if (this.dienthoai.length() > 0)
			{
				sql = sql + "AND ASM.DIENTHOAI LIKE ? ";
				this.getDataSearch().add( "%" + this.dienthoai  + "%" );	

			}
			if (this.thuviec.length() > 0) 
			{
				sql = sql + "AND isnull(ASM.tinhtrang,'1') = ?";
				this.getDataSearch().add( "" + this.thuviec  + "" );	

			}
		}
		 
		
	/*	Utility util = new Utility();
		sql+=" AND ASM.PK_SEQ IN "+util.quyen_asm(this.userId)+"";
		*/
		
		System.out.println(sql);
		
		this.asmlist =  super.createSplittingData_v2(db,super.getItems(), super.getSplittings(), "ASMID desc", sql, dataSearch, "");
		
		
	}
	
	public void Delete(String asmid, String kvId){
		try{
			this.db.getConnection().setAutoCommit(false);
			
			 if(!Utility.KiemTra_PK_SEQ_HopLe(asmid, "ASM",  db))
			   {
			   
			    this.msg = "Lỗi ID.";
			    return ; 
			   }
			 
			String sql = "DELETE FROM ASM_KHUVUC WHERE ASM_FK = '" + asmid + "' ";
			if(!this.db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg="Khong The Thuc Hien Xoa ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
				return ;
			}
		
			sql = "SELECT COUNT(*) AS NUM FROM ASM_KHUVUC WHERE ASM_FK = '" + asmid + "'";
			ResultSet rs = this.db.get(sql);
			rs.next();
		
			if(rs.getString("NUM").equals("0")){
				sql = "DELETE FROM ASM WHERE PK_SEQ ='" + asmid + "'";
				if(!this.db.update(sql))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.msg="Khong The Thuc Hien Xoa ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
					return ;
				}
			}
			if(rs!=null)
				rs.close();
			 this.db.getConnection().commit();
			 this.db.getConnection().setAutoCommit(true);
		}catch(Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg="Khong The Thuc Hien Xoa ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
			return ;
		}
	}
	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		ResultSet rs = db.get("select count(*) as c from asm ");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	public void DBClose(){
		try{
			if(this.asmlist != null) this.asmlist.close();
			if(this.dvkd != null) this.dvkd.close();
			if(this.kbh != null) this.kbh.close();
			if(this.kv != null) this.kv.close();
			if(this.db != null) this.db.shutDown();
		}catch(Exception e){}
	}

	@Override
	public String getThuviec() {
		// TODO Auto-generated method stub
		return this.thuviec;
	}

	@Override
	public void setThuviec(String thiec) {
		this.thuviec=thiec;
		
	}
	
	String userId;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getMa() {
		return this.asmMa;
	}

	public void setMa(String asmMa) 
	{
		this.asmMa = asmMa;
	}
}
