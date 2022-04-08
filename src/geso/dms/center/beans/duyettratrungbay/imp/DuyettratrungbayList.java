package geso.dms.center.beans.duyettratrungbay.imp;

import geso.dms.center.beans.duyettratrungbay.IDuyettratrungbayList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DuyettratrungbayList implements IDuyettratrungbayList, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8158813770943365236L;
	String cttbId;
	int solantt;
	int lantt;
	String msg;
	ResultSet duyetRS;
	ResultSet kv;
	String kvId;
	ResultSet vung;
	String vungId;
	String nppId;
	String trangthai,userId;
	dbutils db ;
	Utility Ult = new Utility();
	private ResultSet cttbRs;
	private ResultSet nppRs;

	public DuyettratrungbayList()
	{
		this.cttbId = "";
		this.solantt = 1;
		this.lantt = 1;
		this.msg = "";
		this.kvId = "";
		this.vungId = "";
		this.nppId = "";
		this.trangthai = "";
		this.db = new dbutils();
		Ult	= new Utility();
	}
		
	public int getSolantt() 
	{		
		return this.solantt;
	}
	
	public void setSolantt(int solantt)
	{
		this.solantt = solantt;		
	}
	
	public String getNppId() 
	{		
		return this.nppId;
	}
	
	public void setNppId(String nppId)
	{
		this.nppId = nppId;		
	}
	
	public int getLantt() 
	{	
		return this.lantt;
	}
	
	public void setLantt(int lantt)
	{
		this.lantt = lantt;		
	}

	public String getTrangthai(){
		
		return this.trangthai;
	}

	
	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	
	public ResultSet getVung() 
	{
		return this.vung;
	}

	public void setVung(ResultSet vung) 
	{
		this.vung = vung;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}

	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}
	
	private ResultSet createVungRS(){  	
		String sql_vung="select pk_seq, diengiai from vung  where trangthai='1'";
		ResultSet vungRS =  this.db.get(sql_vung);
		//System.out.println("__vung__"+sql_vung);
		return vungRS;
	}
	
	
	private ResultSet createKvRS(){
		ResultSet kvRS;
		if (this.vungId.length() > 0){
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");
		}else{
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1'");
		}
		return kvRS;
		
	}
	
	public void init()
	{
		//this.vung = this.createVungRS();
		//this.kv = this.createKvRS();
		this.cttbRs = this.db.get("select * from CTTRUNGBAY");
		/*String query = "select * from NHAPHANPHOI where 1 = 1 ";
		if(this.vungId.length() > 0)
			query += " and KHUVUC_FK IN (SELECT PK_SEQ FROM KHUVUC WHERE VUNG_FK = '"+this.vungId+"')";
		if(this.kvId.length() > 0)
			query += " and KHUVUC_FK = '"+this.kvId+"'";
		this.nppRs = this.db.get(query);*/
		
		String query = "SELECT dn.PK_SEQ, dn.DIENGIAI, tb.SCHEME, dn.NGAYDENGHI, dn.NGAYSUA, nv1.TEN as NGUOITAO, nv2.TEN as NGUOISUA, dn.TRANGTHAI " +
				"FROM DENGHITRATRUNGBAY dn inner join CTTRUNGBAY tb on tb.PK_SEQ = dn.CTTRUNGBAY_FK " +
				"inner join NHANVIEN nv1 on dn.NGUOITAO = nv1.PK_SEQ "+
				"inner join NHANVIEN nv2 on dn.NGUOISUA = nv2.PK_SEQ";
		
		if(this.cttbId.length()>0){
			query=query + " and tb.pk_seq  like '%"+ this.cttbId +"%'";
			 
		}
		if(this.trangthai.length()>0){
			query=query + " and dn.TRANGTHAI = '"+ this.trangthai +"' ";
		}
		query +="\n order by  dn.NGAYDENGHI ";
		//System.out.println("query init : "+ query);
		this.duyetRS = this.db.get(query);
	}
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getUserId()
	{
		
		return this.userId;
	}
	public void setUserId(String UserId)
	{
		this.userId=UserId;
		
	}

	
	public void closeDB()
	{
			try
			{	
				if(this.duyetRS!=null)
					this.duyetRS.close();
				
				if(this.vung!=null)
					this.vung.close();
				
				if(this.kv!=null)
					this.kv.close();				
				
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}


	@Override
	public ResultSet getDuyettraRS() {
		return this.duyetRS;
	}

	@Override
	public ResultSet getKvRs() {
		return this.kv;
	}

	@Override
	public ResultSet getCttbRs() {
		return cttbRs;
	}

	@Override
	public String getCttbId() {
		return this.cttbId;
	}

	@Override
	public void setCttbId(String cttbId) {
		this.cttbId = cttbId;
	}

	@Override
	public ResultSet getNppRs() {
		return this.nppRs;
	}

	@Override
	public void setTrangthai(String tt) {
		// TODO Auto-generated method stub
		this.trangthai = tt;
	}

}
