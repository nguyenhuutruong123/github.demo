package geso.dms.distributor.db.sql;

import geso.dms.center.db.sql.Idbutils;
import geso.dms.distributor.util.Utility;

import java.sql.*;  
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;

public class dbutils implements Idbutils,Serializable
{ 
	
	public static final String NULLVAL = "NULL";
	public static final String CURRENT_TIME = "@@@CURRENT_TIME@@@";
	public static final String MAT_KHAU = "MATKHAU";
	
	private static final long serialVersionUID = 1L;
/*	 
		private String username = "dms"; 
		 private String password = "geso!@#";  
		 private String database = "AHF";
		 private String url ="jdbc:sqlserver://118.69.168.124:1433";*/
		//that
		
		private String username = "sa";
		private String password = "Dms08Erp22";
		private String database = "AHF";
		private String url = "jdbc:sqlserver://124.158.4.155:1433";
		
		
		

	private String connectionString = url+";database="+database+";user="+username+";password="+ password +";";
	
	private Connection connection;
	private Statement statement;
	public  PreparedStatement prep;
	
    public dbutils()
    {  
    	connect();
    }  
    
  
    
	public boolean connect() {
		try 
		{
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		//connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Best;user=" + username + "; password=" + password);
    		
    		Class.forName("net.sourceforge.jtds.jdbc.Driver");
    		//connection = DriverManager.getConnection(url, username, password);
    		connection = DriverManager.getConnection(connectionString);

			return true;
		} catch (Exception ex) {
			System.out.print(ex.toString());
		}
		return false;
	}
    			
	public ResultSet get(String query) {
		try {

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			return rs;
		} catch (SQLException sqle) {
			return null;
		}
	}

	public ResultSet getScrol(String query)  
    {  
    	try {
    		
    		//statement = connection.createStatement();
    		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		ResultSet rs = statement.executeQuery( query );  
    		return rs;      	 
    	}catch ( SQLException sqle ) {	
    		System.out.print(sqle.toString());
    		return null;
        }                
    }  
	 
    public boolean update(String query)
    {  
    	try {  
    		statement = connection.createStatement();  
    		statement.executeUpdate( query );  
    		
    		return true;  
    	}  
    	catch ( SQLException sqle ) { 
    		sqle.printStackTrace();
    		return false;  
    	}  
    }  
    
   
    public int insertQuery(String tableName,String[] key ,Object[] data)
    {
    	String queryLog = "";
    	String valueLog = "";
    	
    	try 
    	{  
    		System.out.println("data :"+ data.length);
	    	/*String insertTableSQL = "INSERT INTO DBUSER"
	    		+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) VALUES"
	    		+ "(?,?,?,?)";*/
	    	if(data == null || data.length <=0) return -1;
	    	
	    	String value = "";
	    	String keyInsert = "";
	    	
	    	for(int i = 0; i < key.length ; i ++)
	    	{
	    		if(keyInsert.trim().length() > 0)
	    			keyInsert += "," + key[i];
	    		else
	    			keyInsert =  key[i];
	    	}
	    	
	    	for(int i = 0; i < data.length;i++ )
	    	{
	    		String item = "";
	    		if(data[i].equals(CURRENT_TIME))
	    			item = "dbo.GetLocalDate(default)";
	    		else if(key[i].equals(MAT_KHAU) )
	    			item = "pwdencrypt(?)";
	    		else
	    			item = "?";
	    		
	    		String itemLog = "";
	    		if(data[i].equals(CURRENT_TIME))
	    			itemLog = "dbo.GetLocalDate(default)";
	    		else if(key[i].equals(MAT_KHAU) )
	    			itemLog = "pwdencrypt('"+data[i]+"')";
	    		else
	    			itemLog = "'"+data[i]+"'";
	    		
	    		if(value.trim().length() > 0)
	    			value += ","+item;
	    		else
	    			value =item;
	    		
	    		if(valueLog.trim().length() > 0)
	    			valueLog += ","+itemLog;
	    		else
	    			valueLog =itemLog;
	    	}  
	    	
	    	
	    	String insertTableSQL = "insert "+tableName+"("+keyInsert+") " + "VALUES("+value+")";
	    	
		    PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
		    
		    for(int i= 0; i < data.length ; i++ )
		    {
		    	System.out.println("i= :"+ i);
		    	if(!data[i].equals(CURRENT_TIME))
	    		{
		    		
			    	if(data[i] == null || data[i].equals(NULLVAL))
			    		preparedStatement.setNull(i+1,java.sql.Types.VARCHAR);
			    	else
			    		preparedStatement.setObject(i+1, data[i]);
	    		}
		
		    }
	
		    // execute insert SQL stetement
		    return preparedStatement.executeUpdate();
	    	
    	}  
    	catch ( Exception sqle ) { 
    		System.out.println("queryLog=  "+ queryLog);
    		sqle.printStackTrace();
    		return -1;
   
    	}  
    }
    
    
    
    
    public static void main(String[] args) {
		try
		{
	    	dbutils db = new dbutils();
			/*String[] key = {"ten",MAT_KHAU};
	    	String query = " insert into nhanvien(ten, ngaysinh, dangnhap, matkhau, email, dienthoai" +
	    			",trangthai, ngaytao, ngaysua, nguoitao, nguoisua, phanloai, sessionid,loai, bm_fk, asm_fk, gsbh_fk, nhanmaildms) ";  
	    	
	    	Object[] data = { "dak'snak'","","fit.cv", "123456".getBytes(StandardCharsets.UTF_8), "","","1","2018-01-01","2018-01-01","100001","100001"
	    					,"2","2018-07-26","0",NULLVAL,NULLVAL,NULLVAL,"1"};
	    	
	    	String query = "insert testaa(textA,thoidiem)";
	    	Object[] data = {"'V?? em ???? ch??i ngu'",CURRENT_TIME};
	    	
	    	boolean a= db.insertQuery(query, data);
	    	
	    	
	    	
	    	
	    	String query = " update nhanvien set sessionid = ?,matkhau = pwdencrypt(?)  where pk_seq =" + id;
	    	Object[] data = {"2018-07-20","123456","1000333"};
	    	int a = db.updateQuery(query, data);
	    	System.out.println("a = "+ a);*/
	    	String ten = "";
	    	
	    	String query = " select ten from nhanvien where trangthai = ? ";	    	
	    	List<Object> data =  new ArrayList<Object>();
	    	data.add("1");
	    	
	    	if(ten.length() > 0)
	    	{
	    		
	    		query += " and ten like ? ";
	    		data.add( "%"+ten+"%" );
	    	}
	    	
	    	viewQuery(query, data);
	    	ResultSet rs= db.getByPreparedStatement(query, data);	    	
	    	rs.next();
	    	
	    	System.out.println("data =" + rs.getString("ten"));
	    	db.shutDown();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
    
    public ResultSet getRsByPro(String ProcName,String[] param) {
        try{
       // CallableStatement cStmt = connection.prepareCall("call getRsKpi_report '2012-01-01' , '2012-03-01'" );
       
        String SPsql = "EXEC "+ ProcName +" ";   // for stored proc taking 2 parameters
         // java.sql.Connection 
        String param_num="";
        if(param != null)
		{
			for(int i = 0; i < param.length; i++){
				param_num=param_num+"?,";
			}
			param_num = param_num.substring(0, param_num.length() - 1); //cat dau , cuoi
		}
        System.out.println(SPsql+param_num);
        PreparedStatement ps = connection.prepareStatement(SPsql+param_num);
        ps.setEscapeProcessing(true);
       // ps.setQueryTimeout(<timeout value>);
      
        if(param != null)
		{
			for(int i = 0; i < param.length; i++){
				ps.setString(i+1, param[i]);
			}
			
		}
        
        ResultSet rs = ps.executeQuery();
        return rs;
        }  
    	catch ( SQLException sqle ) { 
    		System.out.print("Loi Trong Qua trinh Lay Resultset,file dbutils.java distributor  :"+sqle.toString());    		
    		return null;
    	}  
   }
    public int execProceduce(String procName, String[] param)
    {
		try 
		{
			String query = "";
	    	for(int i = 0; i < param.length; i++)
	    		query += "?,";
	    	if(query.length() > 0)
	    		query += "?"; //tham so dau ra, luu ket qua sau khi thuc thi thu tuc
	    	
	    	//query = "{call exOutParams(?,?)}";
	    	query = "{call " + procName + "(" + query + ")}";
	    	System.out.println("Query la: " + query);
	    	
			CallableStatement cstmt = connection.prepareCall(query);
			for(int i = 0; i < param.length; i++)
			{
				cstmt.setString(i + 1, param[i]);
			}

	    	//????ng k?? tham s??? ?????u ra
			cstmt.registerOutParameter(param.length + 1, java.sql.Types.INTEGER);
	    	cstmt.execute();

	    	//L???y gi?? tr??? tr??? v???
	    	int resual = cstmt.getInt(param.length + 1);
	    
	    	return resual;
		} 
		catch (SQLException e) 
		{
			System.out.println("Loi: " + e.toString());
			e.printStackTrace();
			return -1;
		}
    }
     
    public String execProceduce2(String procName, String[] param)
    {
    	try {  
    		statement = connection.createStatement();  
    		  
    		String query = procName;
    		
    		if(param != null)
    		{
    			String paramList = "";
    			for(int i = 0; i < param.length; i++)
    				paramList += "'" + param[i] + "',";
    			paramList = paramList.substring(0, paramList.length() - 1); //cat dau , cuoi
    			
    			query += " " + paramList;
    		}
    		
    		statement.executeUpdate( query );
    			
    		return "";  
    	}  
    	catch ( SQLException sqle ) {  
    		return sqle.toString();  
    	}  
    }
    public int updateReturnInt(String query)  
    {  
    	try {  
    		statement = connection.createStatement();  
    		return	statement.executeUpdate( query );  
    	}  
    	catch ( SQLException sqle ) {  
    		sqle.printStackTrace();
    		return -1;  
    	}  
    }  

    
    public boolean shutDown()  
    {  
    	try {  
    		if(statement != null)
    			statement.close();
    		
    		if(connection != null)
    			connection.close();  
   
    		return true;  
    	}  
    	catch ( SQLException sqlex ) {  
    		return false;  
    	}  
    }  
    
    public List<List<String>> RStoList(ResultSet rs, int num){
    	List<List<String>> list = new ArrayList<List<String>>();

    	try{
    		while (rs.next()){
    			List<String> tmp = new ArrayList<String>();
    			for (int i = 1; i<= num; i++){
    				tmp.add(rs.getString(i));
    			}
    			list.add(tmp);
    		}
    	}catch( SQLException sqlex ) {
    		
    	}
    	
    	return list;
    }
  
    public Connection getConnection()
    {
    	return this.connection;
    }

	@Override
	public PreparedStatement createPreparedStatement(String sql, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int executeUpdate(String sql, Object... args) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSet executeQuery(String sql, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object executeScalar(String sql, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeConnection(ResultSet rs) {
		// TODO Auto-generated method stub
		
	}

	public static void viewQuery(String query ,List<Object> data)
	{
		Object[] arr = new String[data.size()];
		arr = data.toArray(arr);
		viewQuery(query,arr);
	}

	public static void viewQuery(String query ,Object[] data)
	{
		String queryLog = query;
		for(int i= 0; i < data.length ; i++ ) {
			queryLog = queryLog.replaceFirst("\\?", (data[i]==null?"null":"'"+data[i].toString().replaceAll("\\?","--")+ "'"));	
		}
		System.out.println("db center: query = "+ queryLog);
	}
	
	public int updateQueryByPreparedStatement(String query ,List<Object> data)
	{
		Object[] arr = new String[data.size()];
		arr = data.toArray(arr);
		return updateQueryByPreparedStatement(query,arr);
	}
	
	// l???y kh??a identity m???i t??? t???o ra sau khi insert = this.prep ( h???i Huy )
	public String getPk_seq()
	{
		try
		{
			ResultSet rs = this.prep.getGeneratedKeys();
			rs.next();
			return rs.getString(1);
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	public int updateQueryByPreparedStatement(String query ,Object[] data)
    {
   	
    	try 
    	{      		
	    	// "UPDATE DBUSER SET USERNAME = ? WHERE USER_ID = ?"
	    	if(data == null || data.length <=0) return -1;
	    	
	    	
	    	prep = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		    
		    for(int i= 0; i < data.length ; i++ )
		    {
		    	System.out.println("i= :"+ i);
		    	if(data[i] == null || data[i].equals(NULLVAL))
		    		prep.setNull(i+1,java.sql.Types.VARCHAR);
		    	else
		    		prep.setObject(i+1, data[i]);
		    }
		  
		    return prep.executeUpdate();
	    	
	    	
	    	//return true;
    	}  
    	catch ( Exception sqle ) { 
    		
    		sqle.printStackTrace();
    		return -1;
    		//return false;  
    	}  
    }

	public ResultSet getByPreparedStatement(String query,List<Object> data)  
	{
		Object[] arr = new String[data.size()];
		arr = data.toArray(arr);
		return getByPreparedStatement(query,arr);
	}
	
	public ResultSet getByPreparedStatement(String query,Object[] data)  
    {  
		   	
    	try 
    	{  
    	
	    	// "UPDATE DBUSER SET USERNAME = ? WHERE USER_ID = ?"
	    	if(data == null ) return null;
	    	
	    	
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    prep = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);  
		    for(int i= 0; i < data.length ; i++ )
		    {
		    	
		    	if(data[i] == null || data[i].equals(NULLVAL))
		    		preparedStatement.setNull(i+1,java.sql.Types.VARCHAR);
		    	else
		    		preparedStatement.setObject(i+1, data[i]);
		    	
		    }
		    ResultSet rs = preparedStatement.executeQuery();  
    		return rs; 
	    	
	    	
	    	//return true;
    	}  
    	catch ( Exception sqle ) { 
    		sqle.printStackTrace();
    		return null;
    		//return false;  
    	}  
             
    }
	
	public int update_v2(String query ,Object[] data)
    {
   	
    	try 
    	{      		
	    	// "UPDATE DBUSER SET USERNAME = ? WHERE USER_ID = ?"
	    	if(data == null || data.length <=0) return -1;
	    	
	    	
	    	prep = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		    
		    for(int i= 0; i < data.length ; i++ )
		    {
		    	System.out.println("i= :"+ i);
		    	if(data[i] == null || data[i].equals(NULLVAL))
		    		prep.setNull(i+1,java.sql.Types.VARCHAR);
		    	else
		    		prep.setObject(i+1, data[i]);
		    }
		  
		    return prep.executeUpdate();
	    	
	    	
	    	//return true;
    	}  
    	catch ( Exception sqle ) { 
    		//dbutils.viewQuery(query,data);
    		sqle.printStackTrace();
    		return -1;
    		//return false;  
    	}  
    }

	public ResultSet get_v2(String query,List<Object> data)  
	{
		Object[] arr = new String[data.size()];
		arr = data.toArray(arr);
		return getByPreparedStatement(query,arr);
	}
	
	public ResultSet get_v2(String query,Object[] data)  
	{
		Object[] arr = new String[data.length];
		arr = data;
		return getByPreparedStatement(query,arr);
	}
	
	public static Object[] setObject(Object... ObjectArrays) {
		Object[] temp = null;
		if (ObjectArrays.length >0) {
			temp = new Object[ObjectArrays.length];
			for (int i = 0; i < ObjectArrays.length; i++) {
				temp[i] = ObjectArrays[i];
			}
		}
		
		return temp;
	}
	
	public static Object[] AddToObject(Object[] obj, Object... ObjectArrays) {
		
		if (!Utility.isValid(obj)) {
			return null;
		}
		
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
				
		if (ObjectArrays != null && ObjectArrays.length > 0) {
			for (int i = 0; i < ObjectArrays.length; i++) {
				temp.add(ObjectArrays[i]);
			}
		}
		
		return temp.toArray(new Object[temp.size()]);
	}
}  
