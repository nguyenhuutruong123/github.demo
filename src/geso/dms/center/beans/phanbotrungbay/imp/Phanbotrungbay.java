package geso.dms.center.beans.phanbotrungbay.imp;

import geso.dms.center.beans.phanbotrungbay.IPhanbotrungbay;
import geso.dms.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class Phanbotrungbay implements IPhanbotrungbay, Serializable
{

	private static final long serialVersionUID = -248730237623651433L;
	String schemeId;
	String scheme;
	String msg;
	ResultSet schemeRS;
	ResultSet kv;
	String kvId;
	ResultSet vung;
	String vungId;
	ResultSet nppRS;
	dbutils db ;
	Hashtable<String, String> usedPro;
	Hashtable<String, Integer> npp_ns;

	Hashtable<String, Integer> npp_stt;
	public Phanbotrungbay()
	{
		this.schemeId = "0";
		this.scheme = "";
		this.msg = "";
		this.kvId = "0";
		this.vungId = "0";
		this.nppRS = null;
		this.db = new dbutils();
		this.npp_ns = new Hashtable<String, Integer>();
		this.npp_stt = new Hashtable<String, Integer>();
	}

	public String getSchemeId()
	{
		return this.schemeId;
	}

	public void setSchemeId(String schemeId)
	{
		this.schemeId = schemeId;
	}
		
	public Hashtable<String, String> getusedPro()
	{
		return this.usedPro;
	}

	public void setusedPro(Hashtable<String, String> usedPro)
	{
		this.usedPro = usedPro;
	}

	public String getScheme()
	{
		ResultSet rs = this.db.get("select scheme from cttrungbay where pk_seq='" + this.schemeId + "'");
		try{
			rs.next();
			this.scheme = rs.getString("scheme");
		}catch(Exception e){}
		return this.scheme;
	}

	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public ResultSet getSchemeRS() 
	{
		return this.schemeRS;
	}

	public void setSchemeRS(ResultSet schemeRS) 
	{
		this.schemeRS = schemeRS;
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
	public Hashtable<String, Integer> getngansach() {
		return this.npp_ns;
	}
	
	public void setngansach(Hashtable<String, Integer> value) {
		this.npp_ns = value;
	}
	public ResultSet getKv() 
	{
		return this.kv;
	}

	public void setKv(ResultSet khuvuc) 
	{
		this.kv = khuvuc;
	}

	public Hashtable<String, Integer> getSTT() {
		return this.npp_stt;
	}
	
	public void setSTT(Hashtable<String, Integer> value) {
		this.npp_stt = value;
	}
	
	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}

	public ResultSet getNpp() 
	{
		return this.nppRS;
	}

	public void setNpp(ResultSet nppRS) 
	{
		this.nppRS = nppRS;
	}
	
	private ResultSet createSchemeRS()
	{
		ResultSet schemeRS = this.db.get("select * from CTTRUNGBAY  order by ngaytao DESC");
		return schemeRS;
	}
	
	private ResultSet createVungRS()
	{  	
		ResultSet vungRS =  this.db.get("select pk_seq, diengiai from vung  where trangthai='1'");
		return vungRS;
	}
	
	private ResultSet createKvRS(){
		ResultSet kvRS;
		if (!this.vungId.equals("0")){
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");
		}else{
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1'");
		}
//		System.out.print("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");	
		return kvRS;
		
	}

	private ResultSet createNppRS()
	{
		ResultSet nppRS = null;
		String query = "";
		if(!this.schemeId.equals("0"))
		{
			query = "select b.ma, b.pk_seq , b.ten, isnull(a.ngansach, 0) as ngansach from CTTB_NPP a, nhaphanphoi b where cttb_fk='" + this.schemeId + "' and b.pk_seq=a.npp_fk ";
			
			if(!this.kvId.equals("0"))
				query+="and b.khuvuc_fk = '" + this.kvId + "'";
			
			if(!this.vungId.equals("0"))
				query +=" and b.khuvuc_fk in (select pk_seq from khuvuc where vung_fk="+this.vungId+") ";

			nppRS = this.db.get(query);
		}
		return nppRS;
	}
	
	public void init()
	{
		this.schemeRS = this.createSchemeRS();
		this.vung = this.createVungRS();
		this.kv = this.createKvRS();
		this.nppRS = this.createNppRS();
	}
	
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	@Override
	public boolean save(HttpServletRequest request)
	{
		String[] phanbo = request.getParameterValues("phanbo");
		String[] sudung = request.getParameterValues("sudung");
		String [] nppId= request.getParameterValues("nppId");
		String [] nppTen= request.getParameterValues("nppTen");
		try
		{
			this.db.getConnection().setAutoCommit(false);
			int sodong=phanbo.length;
			for(int i=0;i<sodong;i++)
			{
				String sql = "INSERT INTO phanbotrungbay(CTTB_FK,NPP_FK,NGANSACH,dasudung) values('" + this.schemeId + "','" + nppId[i] + "','" + phanbo[i].replace(",", "") + "', 0)";
				if (!db.update(sql))
				{
					sql = "update PHANBOTRUNGBAY set ngansach='" + phanbo[i].replace(",", "") + "' where CTTB_FK='" + this.schemeId + "' and npp_fk='" + nppId[i]+ "' and "+phanbo[i].replace(",", "")+">="+sudung[i].replace(",", "")+" ";
					//System.out.println("[NHOMCTTRUNGBAY_NPP]"+sql);
					if(db.updateReturnInt(sql) <=0)
					{
						this.msg="Nhà phân phối đã dùng vượt ngân sách phân bổ mới: " + nppTen[i]+ " ";
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						return false;
					}						
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			this.msg="Lỗi hệ thống "+e.getMessage();
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		return true;
	}
	public void getId_npp(String manpp, Hashtable<String, Integer> npp_ns, Hashtable<String, Integer> npp_st) {
		try{
			if(this.schemeId.length() == 0){
				this.msg = "Vui lòng chọn chương trình trưng bày";
				return;
			}
			
			String queryInsert = "";
			Enumeration<String> keyList = npp_ns.keys();
			int i = 0;
			while(keyList.hasMoreElements())
			{
				String key = keyList.nextElement();
				queryInsert += "INSERT INTO #Nhaphanphoi(STT, ma, Ngansach) VALUES("+npp_st.get(key)+", '"+key+"', '"+npp_ns.get(key)+"')\n";
				i++;
			}
			if(queryInsert.length() == 0){
				this.msg = "Không có khách hàng nào được upload";
				return;
			}
			
			String command = // test
				 " IF OBJECT_ID('tempdb.dbo.#Nhaphanphoi') IS NOT NULL DROP TABLE #Nhaphanphoi  " + 
				 " CREATE TABLE #nhaphanphoi    " + 
				 " (   " + 
				 "  	STT INT, Ma varchar(500), ngansach INT " +
				 " )  " + queryInsert;
			
			System.out.println(command);
			this.db.update(command);
			
			String query= "select t.ma from Nhaphanphoi k right join #nhaphanphoi t on t.ma = k.ma where k.PK_SEQ IS NULL";
			ResultSet rs = this.db.get(query);
			String _msg = "";
			while(rs.next()){
				_msg += rs.getString("ma") + "\n";
			}
			if(_msg.length() > 0){
				this.msg = "Nhà phân phối không tồn tại trong hệ thống: \n" + _msg;
				//return;
			}
			rs.close();
			
			query = "\n select sokh.MA from #Nhaphanphoi a inner join NHAPHANPHOI p on a.MA = p.MA " +
					"\n right join (" +
					"\n 	select k.NPP_FK, p.MA, COUNT(dk.KHACHHANG_FK) as SOKH " +
					"\n 	from DKTRUNGBAY_KHACHHANG dk inner join DANGKYTRUNGBAY ct on dk.DKTRUNGBAY_FK = ct.PK_SEQ " +
					"\n 	inner join KHACHHANG k on k.PK_SEQ = dk.KHACHHANG_FK " +
					"\n 	inner join NHAPHANPHOI p on p.PK_SEQ = k.NPP_FK " +
					"\n 	where ct.CTTRUNGBAY_FK = '"+this.schemeId+"' " +
					"\n 	GROUP BY k.NPP_FK, p.MA " +
					"\n ) sokh ON sokh.NPP_FK = p.PK_SEQ " +
					"\n WHERE a.MA IS NULL ";
			String manpp_notin = "";
			ResultSet rscheck = this.db.get(query);
			while(rscheck.next())
				manpp_notin += rscheck.getString("MA") + ", ";
			if(manpp_notin.length() > 0){
				this.msg = "Mã nhà phân phối phát sinh dữ liệu đăng ký, vui lòng thêm npp này vào ds \n" + manpp_notin;
				rscheck.close();
				return;
			}
			rscheck.close();
			
			query = "\n select sokh.MA from #Nhaphanphoi a inner join NHAPHANPHOI p on a.MA = p.MA " +
					"\n inner join (" +
					"\n 	select k.NPP_FK, p.MA, COUNT(dk.KHACHHANG_FK) as SOKH " +
					"\n 	from DKTRUNGBAY_KHACHHANG dk inner join DANGKYTRUNGBAY ct on dk.DKTRUNGBAY_FK = ct.PK_SEQ " +
					"\n 	inner join KHACHHANG k on k.PK_SEQ = dk.KHACHHANG_FK " +
					"\n 	inner join NHAPHANPHOI p on p.PK_SEQ = k.NPP_FK " +
					"\n 	where ct.CTTRUNGBAY_FK = '"+this.schemeId+"' " +
					"\n 	GROUP BY k.NPP_FK, p.MA " +
					"\n ) sokh ON sokh.NPP_FK = p.PK_SEQ " +
					"\n WHERE a.ngansach < sokh.SOKH ";
			
			rscheck = this.db.get(query);
			while(rscheck.next())
				manpp_notin += rscheck.getString("MA") + ", ";
			if(manpp_notin.length() > 0){
				this.msg = "Ngân sách đưa vào nhỏ hơn suất đã đăng ký. Các nhà phân phối: \n" + manpp_notin;
				return;
			}
			
			
			query = "update CTTB_NPP set ngansach = t.ngansach  " +
					"from  CTTB_NPP p " +
					"inner join nhaphanphoi a on a.pk_seq = p.npp_fk "+
					"inner join #Nhaphanphoi t on t.ma = a.ma where p.cttb_fk = '" + schemeId + "'" ;
			System.out.println("NPP " + query);
			
			int sonpp = this.db.updateReturnInt(query);
			
			
			
			db.update("IF OBJECT_ID('tempdb.dbo.#Nhaphanphoi') IS NOT NULL DROP TABLE #Nhaphanphoi");
			
			if(sonpp > 0)
				this.msg = "Upload thành công! Có " + sonpp + " nhà phân phối được upload.\n" + this.msg;
			else
				this.msg = "Không có nhà phân phối nào được chọn.";
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void DBClose(){
		try{
			if(schemeRS != null) schemeRS.close();
			if(kv != null) kv.close();
			if(vung != null) vung.close();
			if(nppRS != null) nppRS.close();
			db.shutDown() ;
			if(usedPro != null) usedPro.clear();
		}catch(java.sql.SQLException e){}
	}
}

