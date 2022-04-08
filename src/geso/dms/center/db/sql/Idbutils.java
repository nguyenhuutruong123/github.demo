package geso.dms.center.db.sql;

import java.sql.*;  
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public interface Idbutils 
{ 

	public String getPk_seq();
    
    public boolean connect()  ;
    public ResultSet get(String query)  ; 

    public ResultSet getScrol(String query)  ;  
  
    public int updateReturnInt(String query) ;
  
    
    
    public boolean update(String query)  ;
    
    public int execProceduce(String procName, String[] param);
    public ResultSet getRsByPro(String ProcName,String[] param);
    public String execProceduce2(String procName, String[] param);
     
    public boolean shutDown()  ;
    
    public List<List<String>> RStoList(ResultSet rs, int num);
  
    public Connection getConnection();
    /**
	 * Tạo đối tượng PreparedStatement và cấp giá trị cho các tham số trong câu lệnh sql
	 * @param sql câu lệnh sql chứa các tham số (dấu ?)
	 * @param args giá trị các tham số trong câu sql
	 * @return đối tượng PreparedStatement đã được cấp giá trị cho các tham số
	 * @exception lỗi tạo đối tượng PreparedStatement
	 */
	public  PreparedStatement createPreparedStatement(String sql, Object...args);


	/**
	 * Thực thi câu lệnh thao tác dữ liệu (INSERT, UPDATE, DELETE)
	 * @param sql câu lệnh thao tác chứa tham số (?)
	 * @param args giá trị các tham số trong câu lệnh sql
	 * @return số bản ghi bị ảnh hưởng bởi câu lệnh sql
	 * @exception lỗi thao tác dữ liệu
	 */
	public  int executeUpdate(String sql, Object...args);
	/**
	 * Truy vấn dữ liệu (SELECT)
	 * @param sql câu lệnh truy vấn chứa tham số (?)
	 * @param args giá trị tham số trong câu lệnh sql
	 * @return ResultSet chứa kết quả truy vấn
	 * @exception lỗi truy vấn dữ liệu
	 */
	public  ResultSet executeQuery(String sql, Object...args);
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
	public  Object executeScalar(String sql, Object...args);
	/**
	 * Đóng kết nối mà ResultSet đang sử dụng
	 * @param rs ResultSet cần đóng kết nối
	 * @exception lỗi đóng kết nối
	 */
	public  void closeConnection(ResultSet rs);
	
	public int updateQueryByPreparedStatement(String query ,Object[] data);
	public int updateQueryByPreparedStatement(String query ,List<Object>  data);
	public ResultSet getByPreparedStatement(String query,Object[] data)  ;
	public ResultSet getByPreparedStatement(String query,List<Object> data)  ;
	
	public ResultSet get_v2(String query,List<Object> data)  ;
	public ResultSet get_v2(String query,Object[] data)  ;
    
}  
