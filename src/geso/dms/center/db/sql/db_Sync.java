package geso.dms.center.db.sql;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class db_Sync  implements Serializable {	
	private static final long serialVersionUID = 1L;

	public  final String DBNAME="SGPTHAT";
	
	private String username = "salesup";
	private String password = "salesup@123";
	//private String url = "jdbc:jtds:sqlserver://localhost:1433/"+DBNAME+"";

	private String url = "jdbc:jtds:sqlserver://115.79.59.115:14333/"+DBNAME+"";
	
	
	
	
	private Connection connection;
	private Statement statement;
	public  PreparedStatement prep;

	public db_Sync()
	{  
		connect();
	}  


	public boolean connect()  
	{  
		try 
		{  

			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);

			return true;  
		}catch ( Exception ex ) {
			System.out.print(ex.toString());    		
		}  
		return false;  
	}  

	public ResultSet get(String query)  
	{  
		try {

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery( query );  
			return rs;      	 
		}catch ( SQLException sqle ) {	
			System.out.print(sqle.toString());
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

	public int updateReturnInt(String query)  
	{  
		try {  
			statement = connection.createStatement();  
			return	statement.executeUpdate( query );  
		}  
		catch ( SQLException sqle ) {  
			System.out.println(sqle);
			return -1;  
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
			System.out.println("Lỗi "+sqle);
			return false;  
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


			CallableStatement cstmt = connection.prepareCall(query);
			for(int i = 0; i < param.length; i++)
			{
				cstmt.setString(i + 1, param[i]);
			}

			//đăng ký tham số đầu ra
			cstmt.registerOutParameter(param.length + 1, java.sql.Types.INTEGER);
			cstmt.execute();
			System.out.println("Query la: " + query);
			//Lấy giá trị trả về
			int resual = cstmt.getInt(param.length + 1);

			return resual;
		} 
		catch (SQLException e) 
		{
			System.out.println("Loi: " + e.toString());
			return -1;
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
			System.out.println("___Param: " + SPsql+param_num);
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
			System.out.print("Loi Trong Qua trinh Lay Resultset,file dbutils.java center  :"+sqle.toString());    		
			return null;
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

	public Connection getConnection(){
		return this.connection;
	}

	/**
	 * Tạo đối tượng PreparedStatement và cấp giá trị cho các tham số trong câu lệnh sql
	 * @param sql câu lệnh sql chứa các tham số (dấu ?)
	 * @param args giá trị các tham số trong câu sql
	 * @return đối tượng PreparedStatement đã được cấp giá trị cho các tham số
	 * @exception lỗi tạo đối tượng PreparedStatement
	 */
	public  PreparedStatement createPreparedStatement(String sql, Object...args){
		try{

			PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(int i=1;i<=args.length;i++){
				statement.setObject(i, args[i-1]);
			}
			return statement;
		}
		catch (Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}


	/**
	 * Thực thi câu lệnh thao tác dữ liệu (INSERT, UPDATE, DELETE)
	 * @param sql câu lệnh thao tác chứa tham số (?)
	 * @param args giá trị các tham số trong câu lệnh sql
	 * @return số bản ghi bị ảnh hưởng bởi câu lệnh sql
	 * @exception lỗi thao tác dữ liệu
	 */
	public  int executeUpdate(String sql, Object...args)
	{
		try
		{
			PreparedStatement statement =createPreparedStatement(sql, args);
			int rows = statement.executeUpdate();

			System.out.println("[executeUpdate]"+sql);
			for(int i=0;i<args.length;i++)
			{
				System.out.println("[args]"+args[i]);
			}
			return rows;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Truy vấn dữ liệu (SELECT)
	 * @param sql câu lệnh truy vấn chứa tham số (?)
	 * @param args giá trị tham số trong câu lệnh sql
	 * @return ResultSet chứa kết quả truy vấn
	 * @exception lỗi truy vấn dữ liệu
	 */
	public  ResultSet executeQuery(String sql, Object...args){
		try{
			PreparedStatement statement = createPreparedStatement(sql, args);
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		}
		catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Truy vấn một giá trị đơn. 
	 * Ví dụ: đếm số mặt hàng có giá từ 100 đến 250
	 * String sql = "SELECT COUNT(*) FROM Products WHERE UnitPrice BETWEEN ? AND ?";
	 * long count = (long)JdbcUtil.executeScalar(sql, 100, 250);
	 * @param sql câu lệnh truy vấn chứa tham số (?)
	 * @param args giá trị tham số của câu lệnh sql
	 * @return giá trị truy vấn được
	 * @exception lỗi truy vấn
	 */
	public  Object executeScalar(String sql, Object...args){
		try{
			Object value = null;
			ResultSet resultSet = executeQuery(sql, args);
			if(resultSet.next()){
				value = resultSet.getObject(1);
			}
			closeConnection(resultSet);
			return value;
		}
		catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Đóng kết nối mà ResultSet đang sử dụng
	 * @param rs ResultSet cần đóng kết nối
	 * @exception lỗi đóng kết nối
	 */
	public  void closeConnection(ResultSet rs){
		try{
			rs.getStatement().getConnection().close();
		}
		catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}
}  

