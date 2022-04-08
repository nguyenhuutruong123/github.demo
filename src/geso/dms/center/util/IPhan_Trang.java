package geso.dms.center.util;

import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public interface IPhan_Trang {
	
	public void setAction(String action);//so dong trong mot trang
	public String getAction();
	public void setItems(int items);//so dong trong mot trang
	public int getItems();
	public int getSplittings();//so trang
	public void setOrderByColumn(String orderByColumn);
	public String getOrderByColumn();
	
	//public void setSplittings(int splittings);//so trang
	public int getCrrSplitting();//so trang hien thi de click
	public void setCrrSplitting(int crrSplitting);//so trang hien thi de click
	public int getNxtApprSplitting();//trang can hien thi
	public void setNxtApprSplitting(int nxtApprSplitting);//trang can hien thi
	public int getTheLastSplitting();//trang cuoi cung
	//public void setTheLastSplitting(int theLastSplitting);
	
	public int[] getCrrSplittings();//day cac trang hien thi de click hien tai
	public void setSplittings(int splittings);
	public void setCrrSplittings(int[] crrSplittings);//day cac trang hien thi de click hien tai
	public void setNextSplittings();
	public int[] getNextSplittings();
	//public int[] createNextSplittings();// day cac trang hien thi de click tiep theo
	
	//public ResultSet getSplittingData(String query);//du lieu mot trang
	void setTheLastSplitting(String query);
	void setSplittingData(String query);
	ResultSet getSplittingData();
	void setCrrApprSplitting(int crrApprSplitting);
	int getCrrApprSplitting();
	void setAttribute(HttpServletRequest request, String action,
			String listName, String crrApprSplittingName,
			String nxtApprSplittingName);
	ResultSet createData(String orderByColumn, String query);
	ResultSet createSplittingData(int items, int splittings,
			String orderByColumn, String query);
	
	public void settingPage(ServletContext svlCtxt);
	//ResultSet createSplittingData(HttpServletRequest request, String orderByColumn, String query);
}
