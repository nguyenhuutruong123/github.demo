package geso.dms.center.util;

public interface IPhanTrang {
	
	public int getNum();
	public void setNum(int num);
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] pagesList);
	public int getLastPage();
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage);
}
