package geso.dms.center.util;

import geso.dms.distributor.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhanTrang implements IPhanTrang {

	private int num;
	private int[] listPages;
	@Override
	public int getCurrentPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentPage(int current) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getListPages() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static int[] getListPages(int num) {
		// TODO Auto-generated method stub
		int[]lp = new int[num];
		for(int i = 0; i < num; i++)
			lp[i]=i+1;
		return lp;
	}

	@Override
	public void setListPages(int[] listPages) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLastPage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static int getLastPage(ResultSet rs){

		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("c"));
			rs.close();
			return count;
		}
		catch(Exception e) {}
		
		return 0;
		
	}

	@Override
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage){
		// TODO Auto-generated method stub
		int[] list = new int[num];
		 if(action.equals("next"))
	 	    {

	 	    	for(int i = 0; i < listPage.length; i++)
	 	    		list[i] = Integer.parseInt(listPage[i]) + 1;

	 	    }
	 	    
	 	    if(action.equals("prev"))
	 	    {

	 	    	for(int i = 0; i < listPage.length; i++)
	 	    		list[i] = Integer.parseInt(listPage[i]) - 1;

	 	    }
	    	
	    	if(action.equals("view"))
		    {
		    	
	    	
		    	if(currentPage == 1)
		    	{
		    		list = new int[num];
		    		for(int i = 0; i< num; i++)
		    			list[i] = i+1;
		    	}
		    	else
		    	{
		    		if(currentPage == -1)
		    		{
		    			//int pos = obj.getLastPage() / n; //so dong tren 1 trang
		    			currentPage = theLastPage;
		    			int j = 0;
		    			int k = num;
		    			while(j < num)
		    			{
		    				list[j] = theLastPage - k;
		    				j++;
		    				k--;
		    			}
		    		}
		    		else
		    		{
				    	for(int i = 0; i < listPage.length; i++)
				    		list[i] = Integer.parseInt(listPage[i]) + 1;
		    		}
		    	}
		    	
		    	
		    	//obj.setUserId(userId);
		    }
	    	return list;
	}

	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = new int[num];
		for(int i = 0; i < this.num; i++)
			listPages[i]=i+1;
	}

	public static ResultSet getSplitData(String query, int currentPages, int items, int num) {
		// TODO Auto-generated method stub
		query = "select top("+items+") * from (" + query + ") as List";
    	if (currentPages > 0)
    	{
    		int pos = (currentPages - 1) * num;
    		query = query + " where stt > '" + Integer.toString(pos) + "'";	
    	}
    	if(currentPages == -1){
    		
    		int pos = (getTheLastPage(query) - 1) * num;
    		query = query + " where stt > '" + Integer.toString(pos) + "'";
    	}
		dbutils db = new dbutils();
		return db.get(query);
	}

	public static int getTheLastPage(String mainSql) {
		// TODO Auto-generated method stub
		
		dbutils db = new dbutils();
		String q = "select count(stt) as c from ("+mainSql+") sc";
		ResultSet rs = db.get(q);
		return getLastPage(rs);
	}


}
