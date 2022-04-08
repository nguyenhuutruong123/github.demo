package geso.dms.distributor.beans.donhangtrave;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IDonhangtraveList extends Serializable
{	
	public String getUserId(); //neu dang nhap la nha phan phoi thi hieu ngam la nppID
	public void setUserId(String userId);
	
	
	
	public ResultSet getDaidienkd();
	public void setDaidienkd(ResultSet daidienkd);	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public ResultSet getNpp();
	public void setNpp(ResultSet npp);
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
			
	public ResultSet getDhtvList();
	public void setDhtvList(ResultSet dhtvlist);
	
	public void init(String search);
	public void DBclose();
	public String getMsg();
	public void setMsg(String msg);
	public String getView();
	public void setView(String view);
	public void init_TraNguyenDonList(String search);
	public ResultSet getTrangthaiNguyenDonRs();
	public String getTrangthaiNguyenDon();
	public void setTrangthaiNguyenDon(String trangthaiNguyenDon);
	public boolean checkNull(String input);
}


