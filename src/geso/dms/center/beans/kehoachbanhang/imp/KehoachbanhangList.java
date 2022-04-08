package geso.dms.center.beans.kehoachbanhang.imp;
import geso.dms.center.beans.kehoachbanhang.IKehoachbanhang;
import geso.dms.center.beans.kehoachbanhang.IKehoachbanhangList;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.tuyenbanhang.ITuyenbanhangList;
import geso.dms.distributor.beans.tuyenbanhang.ITuyenbanhang;
import geso.dms.distributor.db.sql.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class KehoachbanhangList extends Phan_Trang implements IKehoachbanhangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String tuyenbh;
	ResultSet ddkd;
	String ddkdId;
	
	String nppId;
	String nppTen;
	String sitecode;
		
	String Message;
	List<IKehoachbanhang> tbhlist;
	dbutils db;

	private int num;

	private int[] listPages;

	private int currentPages;

	private HttpServletRequest request;
	
	public KehoachbanhangList(String[] param)
	{
		this.tuyenbh = param[0];
		this.ddkdId = param[1];
	}
	
	public KehoachbanhangList()
	{
		this.tuyenbh = "";
		this.ddkdId = "";
		currentPages = 1;
		num = 1;
		this.Message="";
		db = new dbutils();
	}
	
	public HttpServletRequest getRequestObj() 
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request) 
	{
		this.request = request;
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTuyenbh() 
	{
		return this.tuyenbh;
	}

	public void setTuyenbh(String tuyenbh) 
	{
		this.tuyenbh = tuyenbh;
	}

	public ResultSet getDdkd() 
	{
		return this.ddkd;
	}

	public void setDdkd(ResultSet ddkd) 
	{
		this.ddkd = ddkd;
	}

	public String getDdkdId() 
	{
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId)
	{
		this.ddkdId = ddkdId;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.sitecode = rs.getString("sitecode");				
			}else
			{
				this.nppId = "";
				this.nppTen = "";
				this.sitecode = "";				
			}
			
		}catch(Exception e){}
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public List<IKehoachbanhang> getTbhList() 
	{
		return this.tbhlist;
	}

	public void setTbhList(List<IKehoachbanhang> tbhlist) 
	{
		this.tbhlist = tbhlist;
	}
	
	public void createDdkdRS()
	{
		this.getNppInfo();
		String sql ="select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where trangthai = '1' and pk_seq in (select ddkd_fk from DDKD_GSBH where GSBH_FK in (select PK_SEQ from GIAMSATBANHANG where DVKD_FK=100069 ))   order by ten ASC ";
	//	System.out.println("chuoi thu:" +  sql);
		this.ddkd = db.get(sql);
		
	}
	
	public void createTbhBeanList(String query)
	{
		ResultSet rs = createSplittingData(50, 10, "TRANGTHAI asc,ngaytao desc", query);// createSplittingData(request, "tbhId desc", query);//this.db.get(query);
		List<IKehoachbanhang> tbhlist = new ArrayList<IKehoachbanhang>();
			
		if(rs != null)
		{
			String[] param = new String[10];
			IKehoachbanhang tbhBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("tbhId");
					param[1]= rs.getString("tbhTen");
					param[2]= rs.getString("ddkdTen");
					param[3]= rs.getString("ngaylamviec");
					param[4]= rs.getDate("ngaytao").toString();
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getDate("ngaysua").toString();
					param[7]= rs.getString("nguoisua");
					param[8]= rs.getString("ddkdId");		
					param[9]= rs.getString("trangthai");		
					
					tbhBean = new Kehoachbanhang(param);
					tbhlist.add(tbhBean);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
			
		}
		this.tbhlist = tbhlist;
	}
		
	public void init(String search) 
	{
		//db = new dbutils();
		this.getNppInfo();
		String query="";	
		if (search.length() == 0)
		{
			query = "\n select a.pk_seq as tbhId,a.ngay as ngayId, a.ghichu as tbhTen, a.ngaytao, a.ngaysua, " +
					"\n ''as ngaylamviec, isnull(b.ten,d1.TEN) as nguoitao,a.trangthai, isnull(c.ten,d1.TEN) as nguoisua,";
			query = query +	"\n d.pk_seq as ddkdId, d.ten as ddkdTen, '' as nppTen, '' as nppId";
			query = query + "\n from kehoachbanhang a left join nhanvien b on a.nguoitao = b.pk_seq " +
					"\n left join nhanvien c on a.nguoisua = c.pk_seq " +
					"\n inner join daidienkinhdoanh d on a.ddkd_fk = d.pk_seq " +
					"\n left join DAIDIENKINHDOANH d1 on d1.PK_SEQ = a.NGUOITAO " +
					"\n left join DAIDIENKINHDOANH d2 on d2.PK_SEQ = a.NGUOISUA "; 
		}
		else
		{
			query = search;
		}
	
		System.out.println("Init: "+query);
		this.createTbhBeanList(query);  
		this.createDdkdRS();
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
		
		ResultSet rs = db.get("select count(*) as c from tuyenbanhang");
		return PhanTrang.getLastPage(rs);

	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	
	public void DBclose() {
		
		
		try {
		
			if(this.ddkd != null)
				this.ddkd.close();
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	
	public void setMsg(String msg) {
		
		this.Message=msg;
	}

	
	public String getMsg() {
		
		return this.Message;
	}

	String Trangthai = "";
	public String getTrangthai() {
		
		return Trangthai;
	}

	
	public void setTrangthai(String Trangthai) {
	this.Trangthai = Trangthai;
		
	}		

	
	
}
