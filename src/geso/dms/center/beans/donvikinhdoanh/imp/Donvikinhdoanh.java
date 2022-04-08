package geso.dms.center.beans.donvikinhdoanh.imp;
import java.sql.ResultSet;
import java.util.Hashtable;

import com.nhat.replacement.TaskReplacement;

import geso.dms.center.beans.donvikinhdoanh.IDonvikinhdoanh;
import geso.dms.center.db.sql.*;


public class Donvikinhdoanh implements IDonvikinhdoanh
{
	private static final long serialVersionUID = -9217977546733610215L;
	String id;
	String dvkd;
	String diengiai;
	String ncc;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String trangthai;
	String nccId;
	String[] nccSelected;
	String msg;
	dbutils db;
	
	public Donvikinhdoanh(String[] param)
	{
		this.id = param[0];
		this.dvkd = param[1];	
		this.ncc  = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.ngaysua = param[5];
		this.nguoitao = param[6];
		this.nguoisua = param[7];	
		this.nccId = param[8];
		this.diengiai = param[9];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Donvikinhdoanh()
	{
		this.id = "";
		this.dvkd = "";
		this.ncc  = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.trangthai = "";
		this.nccId = "";
		this.diengiai = "";
		this.msg = "";
		this.db = new dbutils();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDvkd()
	{
		return this.dvkd;
	}

	public void setDvkd(String dvkd)
	{
		this.dvkd = dvkd;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public String  getNcc(){
		return this.ncc;
	}
	
	public void setNcc(String ncc){
		this.ncc = ncc;
	}
	
	public String getNgaytao()
	{
		return this.ngaytao;
		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}


	public String  getNccId(){
		return this.nccId;
	}
	
	public void setNccId(String nccId){
		this.nccId = nccId;
	}
	
	public Hashtable<Integer, String>  getNccSelected(){
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.nccSelected != null){
			int size = (this.nccSelected).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), nccSelected[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	
	public void setNccSelected(String[] nccSelected){
		this.nccSelected = nccSelected;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	public void init(){
		String query = "select donvikinhdoanh, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, diengiai from donvikinhdoanh where pk_seq='" + this.id + "'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.dvkd = rs.getString("donvikinhdoanh");
			this.trangthai = rs.getString("trangthai");
			this.ngaytao = rs.getString("ngaytao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoitao = rs.getString("nguoitao");
			this.nguoisua = rs.getString("nguoisua");
			this.diengiai = rs.getString("diengiai");
			rs.close();
		}catch(Exception e){}
		
	}

	public ResultSet getNccList(boolean all){
		
		String command;
		if (all)
			command = "select pk_seq, ten from nhacungcap";
		else
			command = "select pk_seq, ten from nhacungcap where trangthai = '1'";
		
		return this.db.get(command);
		
	}

	public ResultSet getNewNcc(String dvkdId){
		
		String command;
		command = "select pk_seq, ten from nhacungcap where trangthai='1' and pk_seq not in (select a.pk_seq from nhacungcap a, nhacungcap_dvkd b where a.pk_seq = b.ncc_fk and b.dvkd_fk='" + dvkdId + "')";
		
		return this.db.get(command);
		
	}
		boolean tenDVKD()
		{String sql =" select * from donvikinhdoanh where donvikinhdoanh = N'" + this.dvkd + "'";
		System.out.print(sql);
		ResultSet rs = db.get(sql);
		try {
			while(rs.next())
				 return true;
			rs.close();		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
			return false;
		}
		
	public boolean saveNewDvkd(){
		try{
			db.getConnection().setAutoCommit(false);
		if(tenDVKD()) return false;
			
		
		// Insert data set into table "donvikinhdoanh"
		/*String query ="insert into donvikinhdoanh( donvikinhdoanh,ngaytao,ngaysua,nguoitao,nguoisua,trangthai,diengiai) " +
		" values(N'" + this.dvkd + "','"+ this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "','" + this.trangthai + "',N'" + this.diengiai + "')";
		
		if (!db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Khong the luu vao table 'Donvikinhdoanh'";
			return false;			
		}*/
		
		String cauQuery = "insert into donvikinhdoanh( donvikinhdoanh,ngaytao,ngaysua,nguoitao,nguoisua,trangthai,diengiai) values(?,?,?,?,?,?,?)" ;
		Object[] dataQuery = {this.dvkd,this.ngaytao,this.ngaysua,this.nguoitao,this.nguoisua,this.trangthai,this.diengiai}; 
		if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery)!= 1) {
			dbutils.viewQuery(cauQuery,dataQuery);
			this.msg = "Error At : a17d9543-9507-416f-b49c-0ff24c19e40a";
			this.db.getConnection().rollback();
			return false;
		}


		 String query = "select IDENT_CURRENT('donvikinhdoanh')as dvkdId";
		 ResultSet rs = this.db.get(query);
		 
			rs.next();
			this.id = rs.getString("dvkdId");			
			try{
				//String sql_update_smartid="update donvikinhdoanh set smartid='"+this.id+"' where pk_seq='" + this.id;
				/*if(!db.update(sql_update_smartid)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					System.out.println("Khong The Thuc Hien Luu Bang Nay,Vui Long Lien He Voi Admin De Sua Loi Nay");
					return false;
				}*/
				
				String cauQuery1 = "update donvikinhdoanh set smartid=? where pk_seq=?" ;
				Object[] dataQuery1 = {this.id,this.id}; 
				if (this.db.updateQueryByPreparedStatement(cauQuery1,dataQuery1)!= 1) {
					dbutils.viewQuery(cauQuery1,dataQuery1);
					this.msg = "Error At : 2d15be74-6b72-4083-8191-5a67036f929b";
					this.db.getConnection().rollback();
					return false;
				}


			}catch(Exception er)
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				//System.out.println("Khong The Thuc Hien Luu Bang Nay,Vui Long Lien He Voi Admin De Sua Loi Nay");
				return false;
			}
			rs.close();
		// Insert data set into table "nhacungcap_dvkd"
			ResultSet ncclist = getNccList(false);
			Hashtable<Integer, String> selected;

			if (this.nccSelected != null){
				selected = this.getNccSelected();	
			}else{
				selected = new Hashtable <Integer, String>();
						
			}
		
			while (ncclist.next() ){
				String nccId = ncclist.getString("pk_seq");
				if (selected.contains(nccId)){
					//query = "insert into nhacungcap_dvkd values('" + nccId + " ' , '"+ this.id +"', '1')";
					cauQuery = "insert into nhacungcap_dvkd values(?  , ?, '1')" ;
					Object[] dataQuery2 = {nccId,this.id};
					if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery2)!= 1) {
						dbutils.viewQuery(cauQuery,dataQuery2);
						this.msg = "Error At : 3c92d8e4-1022-43b5-a9c7-8271b175f312";
						this.db.getConnection().rollback();
						return false;
					}
				}else{
					//query = "insert into nhacungcap_dvkd values('" + nccId + " ' , '"+ this.id +"', '0')";
					cauQuery = "insert into nhacungcap_dvkd values(?  , ?, '0')" ;
					Object[] dataQuery2 = {nccId,this.id};
					if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery2)!= 1) {
						dbutils.viewQuery(cauQuery,dataQuery2);
						this.msg = "Error At : 3c92d8e4-1022-43b5-a9c7-8271b175f312";
						this.db.getConnection().rollback();
						return false;
					}
				}
                System.out.println("nha cung cap:"+query);
				/*if (!db.update(query)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = this.msg + " Khong the luu vao Nhacungcap_dvkd";
					return false;			
				}*/
				

			}
			ncclist.close();
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception e){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		return true;
	}
	
	public boolean UpdateDvkd(){
	//	if(tenDVKD())
	//	{this.msg = "Bi trung ten don vi kinh doanh";
	//	return false;
		//	}
		try{
			db.getConnection().setAutoCommit(false);
		
			Hashtable<Integer, String> selected;
			
			// Update table "donvikinhdoanh"
			/*String query = "update donvikinhdoanh set donvikinhdoanh=N'" + this.dvkd + "',  ngaysua = '" + this.ngaysua + "',  nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "', diengiai = N'" + this.diengiai + "' where pk_seq = '" + this.id + "'" ;
	
			if(!db.update(query)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Khong the cap nhat table 'Donvikinhdoanh'";
				return false; 
			}*/
			
			String cauQuery = "update donvikinhdoanh set donvikinhdoanh=?, ngaysua = ?, nguoisua = ?, trangthai = ?, diengiai = ? where pk_seq = ?" ;
			Object[] dataQuery = {this.dvkd,this.ngaysua,this.nguoisua,this.trangthai,this.diengiai,this.id}; 
			if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery)!= 1) {
				dbutils.viewQuery(cauQuery,dataQuery);
				this.msg = "Error At : 9b7b27cd-d529-4ba1-8d02-66031be7439e";
				this.db.getConnection().rollback();
				return false;
			}
 
			// Update table "nhacungcap_dvkd"
			if (this.nccSelected != null){
				selected = this.getNccSelected();
			}else{
				
				selected = new Hashtable <Integer, String>();
			}
			
			ResultSet ncclist = getNccList(false);
			String query = "";
			try{
				while (ncclist.next() ){
					String nccId = ncclist.getString("pk_seq");
					if (selected.contains(nccId)){		
						 /*query = "update nhacungcap_dvkd set checked= '1' where ncc_fk = '"+ nccId + "' and dvkd_fk = '" + this.id + "'";*/
						cauQuery = "update nhacungcap_dvkd set checked= 1 where ncc_fk = ? and dvkd_fk = ?" ;
						Object[] dataQuery1 = {nccId,this.id}; 
						
						if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery1)!= 1) {
							dbutils.viewQuery(cauQuery,dataQuery);
							this.msg = "Error At : 4f0d543c-1287-483a-bdfd-7ade4e0e452d";
							this.db.getConnection().rollback();
							return false;
						}
					}else{
						/* query = "update nhacungcap_dvkd set checked= '0' where ncc_fk = '"+ nccId + "' and dvkd_fk = '" + this.id + "'";*/
						cauQuery = "update nhacungcap_dvkd set checked= 0 where ncc_fk = ? and dvkd_fk = ?" ;
						Object[] dataQuery1 = {nccId,this.id};
						
						if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery1)!= 1) {
							dbutils.viewQuery(cauQuery,dataQuery);
							this.msg = "Error At : 4f0d543c-1287-483a-bdfd-7ade4e0e452d";
							this.db.getConnection().rollback();
							return false;
						}
					}
	
					/*if (!db.update(query)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "Khong the cap nhat table 'Nhacungcap_dvkd'";
						return false;
					}*/ 
				}ncclist.close();
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
			}catch(Exception e){
				this.msg="Loi "+e.toString();
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			
			}
		}catch(Exception e){
			e.printStackTrace();
			db.update("rollbak");
			this.msg = "Exception Update!";
			return false;
		}
		return true;
	}
	
	public ResultSet getNccListByDvkd(String dvkdId){
		
		String command; 
		command = "select a.pk_seq, a.ten, b.checked from nhacungcap a, nhacungcap_dvkd b where a.pk_seq = b.ncc_fk and b.dvkd_fk='" + dvkdId + "'";
		System.out.println("List DVKD  : "+ command);
		return  (db.get(command));
	}

	public void DBClose(){
		this.db.shutDown();
		nccSelected = null;
	}
	
	public static void main(String[] args) {
		TaskReplacement t = new TaskReplacement();
		String inl = "update nhacungcap_dvkd set checked= '0' where ncc_fk = '\"+ nccId + \"' and dvkd_fk = '\" + this.id + \"'";
		System.out.println(t.taoQuery(inl));
 
	}
}


