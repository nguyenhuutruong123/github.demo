package geso.dms.center.beans.khaosat.imp;

import geso.dms.center.beans.khaosat.IKhaosat;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Khaosat implements IKhaosat
{
	String userId;
	String id;
	
	String tieude;
	String diengiai;
	String bophan;
	String socauhoi;
	
	String trangthai;
	String msg;
	
	Hashtable<String, String> noidungcaihoi;
	
	ResultSet vungRs;
	String vungId;
	ResultSet kvRs;
	String kvId;
	ResultSet ddkdRs;
	String ddkdId;
	
	dbutils db;
	
	String loaict = "0";
	String tungay = "";
	String denngay = "";
	
	String doituong ="KH";
	
	
	public Khaosat()
	{
		this.userId = "";
		this.id = "";
		this.tieude = "";
		this.diengiai = "";
		this.bophan = "";
		this.socauhoi = "";

		this.trangthai = "1";
		this.msg = "";
		
		this.vungId = "";
		this.kvId = "";
		this.ddkdId = "";
		this.doituong ="KH";
		this.noidungcaihoi = new Hashtable<String, String>();
		this.db = new dbutils();
	}
	
	public Khaosat(String id)
	{
		this.userId = "";
		this.id = id;
		this.tieude = "";
		this.diengiai = "";
		this.bophan = "";
		this.socauhoi = "";

		this.trangthai = "1";
		this.msg = "";
		
		this.vungId = "";
		this.kvId = "";
		this.ddkdId = "";
		doituong ="KH";
		this.noidungcaihoi = new Hashtable<String, String>();
		this.db = new dbutils();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		String query = "select loaict,tungay,denngay, tieude, diengiai, bophan, socauhoi, trangthai,isnull(doituong,'KH') as doituong from KHAOSAT where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.tieude = rs.getString("tieude");
					this.diengiai = rs.getString("diengiai");
					this.bophan = rs.getString("bophan");
					this.socauhoi = rs.getString("socauhoi");
					this.trangthai = rs.getString("trangthai");
					this.loaict = rs.getString("loaict");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.doituong  =  rs.getString("doituong");
				}
				rs.close();
				
				if(this.socauhoi.trim().length() > 0 )
				{
					Hashtable<String, String> cauhoi_noidung = new Hashtable<String, String>();
					
					query = "select pk_seq, STT, LOAICAUHOI, CAUHOI, HUONGDANTRALOI from KHAOSAT_CAUHOI where KHAOSAT_FK = '" + this.id + "' order by STT asc";
					ResultSet rsCauHoi = db.get(query);
					if(rsCauHoi != null)
					{
						while(rsCauHoi.next())
						{
							String khaosat_cauhoi_fk = rsCauHoi.getString("pk_seq");
							String cauhoiId = "cau" + rsCauHoi.getString("STT");
							String loaicauhoi = rsCauHoi.getString("LOAICAUHOI");
							
							String cauhoi = rsCauHoi.getString("CAUHOI");
							if(cauhoi.trim().length() <= 0)
								cauhoi = " ";
							
							String huongdantraloi = rsCauHoi.getString("HUONGDANTRALOI");
							if(huongdantraloi.trim().length() <= 0)
								huongdantraloi = " ";
							
							String cautraloi = "";
							if(!loaicauhoi.equals("0"))
							{
								query = "select luachon1, luachon2, luachon3, luachon4, luachon5 from KHAOSAT_CAUHOI_DAPAN where khaosat_cauhoi_fk = '" + khaosat_cauhoi_fk + "'";
								ResultSet rsLuachon = db.get(query);
								
								if(rsLuachon != null)
								{
									while(rsLuachon.next())
									{
										String luachon1 = rsLuachon.getString("luachon1");
										if(luachon1.trim().length() <= 0)
											luachon1 = " ";
										
										String luachon2 = rsLuachon.getString("luachon2");
										if(luachon2.trim().length() <= 0)
											luachon2 = " ";
										
										String luachon3 = rsLuachon.getString("luachon3");
										if(luachon3.trim().length() <= 0)
											luachon3 = " ";
										
										String luachon4 = rsLuachon.getString("luachon4");
										if(luachon4.trim().length() <= 0)
											luachon4 = " ";
										
										String luachon5 = rsLuachon.getString("luachon5");
										if(luachon5.trim().length() <= 0)
											luachon5 = " ";
										
										cautraloi = luachon1 + "__" + luachon2 + "__" + luachon3 + "__" + luachon4 + "__" + luachon5;
									}
									rsLuachon.close();
								}
							}
							else
								cautraloi = " ";
							
							
							cauhoi_noidung.put(cauhoiId, loaicauhoi + ",," + cauhoi + ",," + huongdantraloi + ",," + cautraloi );
							
						}
						rsCauHoi.close();
					}
					
					this.noidungcaihoi = cauhoi_noidung;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception Init: " + e.getMessage());
			}
		}
		
		//init DDKD
		query = "select ddkd_fk from KHAOSAT_DDKD where khaosat_fk = '" + this.id + "'";
		rs = db.get(query);
		try 
		{
			String ddkd = "";
			while(rs.next())
			{
				ddkd += rs.getString("ddkd_fk") + ",";
			}
			rs.close();
			
			if(ddkd.trim().length() > 0)
			{
				ddkd = ddkd.substring(0, ddkd.length() - 1);
				this.ddkdId = ddkd;
				System.out.println("___Dai dien KD: " + ddkd);
			}
		} 
		catch (Exception e) {}
		
		this.createRs();
	}
	
	public boolean createKhaosat()
	{	
		try 
		{
			if(this.tieude.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập tiêu đề";
				return false;
			}
			
			if(this.bophan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn bộ phận";
				return false;
			}
			
			if(this.socauhoi.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số câu hỏi";
				return false;
			}
			
			/*if(this.ddkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhân viên áp dụng";
				return false;
			}*/
			
			if(this.noidungcaihoi.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin câu hỏi nhập vào";
				return false;
			}
			else
			{
				//Check thong tin cac cau hoi nhap vao phai hop le
				for(int i = 0; i < Integer.parseInt(this.socauhoi.trim()); i++ )
				{
					String noidung = this.noidungcaihoi.get("cau" + ( i + 1 ) );
  					
					if(noidung.trim().length() <= 0)
					{
						this.msg = "Vui lòng kiểm tra lại thông tin của câu hỏi " + (i + 1);
						return false;
					}
					
  					String loaisauhoi = "";
  					String cauhoi = "";
  					String huongdantraloi = "";
  					String dapan = "";
  					
  					if(noidung != null)
  					{
  						if(noidung.contains(",,"))
  						{
  							String[] ndArr = noidung.split(",,");
  							loaisauhoi = ndArr[0].trim();
  							cauhoi = ndArr[1].trim();
  							huongdantraloi = ndArr[2].trim();
  							dapan = ndArr[3].trim();
  						}
  					}
  					
  					if(cauhoi.trim().length() <= 0)
  					{
  						this.msg = "Vui lòng nhập nội dung của câu hỏi " + (i + 1);
						return false;
  					}
  					
  					/*if(huongdantraloi.trim().length() <= 0)
  					{
  						this.msg = "Vui lòng nhập hướng dẫn trả lời của câu hỏi " + (i + 1);
						return false;
  					}*/
  					
  					if(!loaisauhoi.equals("0"))
  					{
  						if(dapan.trim().length() <= 0)
  						{
  							this.msg = "Vui lòng nhập thông tin đáp án của câu hỏi " + (i + 1);
  							return false;
  						}
  						
  						String[] dapanArr = null;
  	  					if(dapan.trim().length() > 0)
  	  						dapanArr = dapan.split("__");
  	  					
  	  					boolean coDapAn = false;
  	  					if(dapanArr != null)
  	  					{
  	  						for(int j = 0; j < dapanArr.length; j++ )
  	  						{
  	  							if(dapanArr[j].trim().length() > 0)
  	  							{
  	  								coDapAn = true;
  	  								break;
  	  							}
  	  						}
  	  					}
  	  					
  	  					if(!coDapAn)
  	  					{
  	  						this.msg = "Vui lòng nhập lựa chọn đáp án của câu hỏi " + (i + 1);
							return false;
  	  					}
  	  					
  					}
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert KHAOSAT(doituong,loaict,tungay,denngay,tieude, diengiai, bophan, socauhoi, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values('"+this.doituong+"',"+this.loaict+",'"+this.tungay+"','"+this.denngay+"',N'" + this.tieude + "', N'" + this.diengiai + "', N'" + this.bophan + "', '" + this.socauhoi + "', '0', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới KHAOSAT " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('KHAOSAT') as ksId";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					this.id = rs.getString("ksId");
				}
				rs.close();
			}
			
			if(this.socauhoi.trim().length() > 0)
			{
				for(int i = 0; i < Integer.parseInt(this.socauhoi.trim()); i++ )
				{
					String noidung = this.noidungcaihoi.get("cau" + ( i + 1 ) );
  					System.out.println("Lay noi dung: " + noidung);
  					
  					String loaisauhoi = "";
  					String cauhoi = "";
  					String huongdantraloi = "";
  					String dapan = "";
  					
  					if(noidung != null)
  					{
  						if(noidung.contains(",,"))
  						{
  							String[] ndArr = noidung.split(",,");
  							loaisauhoi = ndArr[0].trim();
  							cauhoi = ndArr[1].trim();
  							huongdantraloi = ndArr[2].trim();
  							dapan = ndArr[3].trim();
  						}
  					}
  					
  					query = "Insert KHAOSAT_CAUHOI( KHAOSAT_FK, STT, LOAICAUHOI, CAUHOI, HUONGDANTRALOI ) " +
  							"values ( '" + this.id + "', '" + (i + 1) + "', '" + loaisauhoi + "', N'" + cauhoi + "', N'" + huongdantraloi + "' ) ";
  					if(!db.update(query))
  					{
  						this.msg = "Không thể tạo mới KHAOSAT_CAUHOI " + query;
  						db.getConnection().rollback();
  						return false;
  					}
  					
  					if(!loaisauhoi.equals("0"))
  					{
  						query = "select IDENT_CURRENT('KHAOSAT_CAUHOI') as ksId";
  						rs = db.get(query);
  						String khaosat_cauhoi_fk = "";
  						if(rs != null)
  						{
  							if(rs.next())
  							{
  								khaosat_cauhoi_fk = rs.getString("ksId");
  							}
  							rs.close();
  						}
  						
  						String[] dapanArr = null;
  	  					if(dapan.trim().length() > 0)
  	  						dapanArr = dapan.split("__");
  	  					
  	  					if(dapanArr != null)
  	  					{
  							query = "Insert KHAOSAT_CAUHOI_DAPAN(khaosat_cauhoi_fk, luachon1, luachon2, luachon3, luachon4, luachon5) " +
  									"values ( '" + khaosat_cauhoi_fk + "', N'" + dapanArr[0] + "', N'" + dapanArr[1] + "', N'" + dapanArr[2] + "', N'" + dapanArr[3] + "', N'" + dapanArr[4] + "' ) ";
  							
  	  						if(!db.update(query))
  	    					{
  	    						this.msg = "Không thể tạo mới KHAOSAT_CAUHOI_DAPAN " + query;
  	    						db.getConnection().rollback();
  	    						return false;
  	    					}
  	  					}
  					}
				}
			}
			
			/*query = "Insert KHAOSAT_DDKD(khaosat_fk, ddkd_fk) select '" + this.id + "', pk_seq " +
					"from DaiDienKinhDoanh where pk_seq in ( " + this.ddkdId + " ) ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới KHAOSAT_DDKD " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean updateKhaosat() 
	{
		try 
		{
			if(this.tieude.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập tiêu đề";
				return false;
			}
			
			if(this.bophan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn bộ phận";
				return false;
			}
			
			if(this.socauhoi.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số câu hỏi";
				return false;
			}
			
			/*if(this.ddkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhân viên áp dụng";
				return false;
			}*/
			
			if(this.noidungcaihoi.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin câu hỏi nhập vào";
				return false;
			}
			else
			{
				//Check thong tin cac cau hoi nhap vao phai hop le
				for(int i = 0; i < Integer.parseInt(this.socauhoi.trim()); i++ )
				{
					String noidung = this.noidungcaihoi.get("cau" + ( i + 1 ) );
  					
					if(noidung.trim().length() <= 0)
					{
						this.msg = "Vui lòng kiểm tra lại thông tin của câu hỏi " + (i + 1);
						return false;
					}
					
  					String loaisauhoi = "";
  					String cauhoi = "";
  					String huongdantraloi = "";
  					String dapan = "";
  					
  					if(noidung != null)
  					{
  						if(noidung.contains(",,"))
  						{
  							String[] ndArr = noidung.split(",,");
  							loaisauhoi = ndArr[0].trim();
  							cauhoi = ndArr[1].trim();
  							huongdantraloi = ndArr[2].trim();
  							dapan = ndArr[3].trim();
  						}
  					}
  					
  					if(cauhoi.trim().length() <= 0)
  					{
  						this.msg = "Vui lòng nhập nội dung của câu hỏi " + (i + 1);
						return false;
  					}
  					
  					/*if(huongdantraloi.trim().length() <= 0)
  					{
  						this.msg = "Vui lòng nhập hướng dẫn trả lời của câu hỏi " + (i + 1);
						return false;
  					}*/
  					
  					if(!loaisauhoi.equals("0"))
  					{
  						if(dapan.trim().length() <= 0)
  						{
  							this.msg = "Vui lòng nhập thông tin đáp án của câu hỏi " + (i + 1);
  							return false;
  						}
  						
  						String[] dapanArr = null;
  	  					if(dapan.trim().length() > 0)
  	  						dapanArr = dapan.split("__");
  	  					
  	  					boolean coDapAn = false;
  	  					if(dapanArr != null)
  	  					{
  	  						for(int j = 0; j < dapanArr.length; j++ )
  	  						{
  	  							if(dapanArr[j].trim().length() > 0)
  	  							{
  	  								coDapAn = true;
  	  								break;
  	  							}
  	  						}
  	  					}
  	  					
  	  					if(!coDapAn)
  	  					{
  	  						this.msg = "Vui lòng nhập lựa chọn đáp án của câu hỏi " + (i + 1);
							return false;
  	  					}
  	  					
  					}
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "Update KHAOSAT set doituong ='"+this.doituong+"' , loaict= "+this.loaict+",tungay = '"+this.tungay+"' ,denngay = '"+this.denngay+"' ,tieude = N'" + this.tieude + "', diengiai = N'" + this.diengiai + "', bophan = N'" + this.bophan + "', " +
					"socauhoi = '" + this.socauhoi + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' " +
			   "where pk_seq = '" + this.id + "' ";
							
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật KHAOSAT " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete KHAOSAT_CAUHOI_DAPAN where khaosat_cauhoi_fk in ( select pk_seq from KHAOSAT_CAUHOI where khaosat_fk = '" + this.id + "'  ) ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật KHAOSAT_CAUHOI_DAPAN " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete KHAOSAT_CAUHOI where khaosat_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật KHAOSAT_CAUHOI " + query;
				db.getConnection().rollback();
				return false;
			}
			
			/*query = "delete KHAOSAT_DDKD where khaosat_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật KHAOSAT_DDKD " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			if(this.socauhoi.trim().length() > 0)
			{
				for(int i = 0; i < Integer.parseInt(this.socauhoi.trim()); i++ )
				{
					String noidung = this.noidungcaihoi.get("cau" + ( i + 1 ) );
  					System.out.println("Lay noi dung: " + noidung);
  					
  					String loaisauhoi = "";
  					String cauhoi = "";
  					String huongdantraloi = "";
  					String dapan = "";
  					
  					if(noidung != null)
  					{
  						if(noidung.contains(",,"))
  						{
  							String[] ndArr = noidung.split(",,");
  							loaisauhoi = ndArr[0].trim();
  							cauhoi = ndArr[1].trim();
  							huongdantraloi = ndArr[2].trim();
  							dapan = ndArr[3].trim();
  						}
  					}
  					
  					query = "Insert KHAOSAT_CAUHOI( KHAOSAT_FK, STT, LOAICAUHOI, CAUHOI, HUONGDANTRALOI ) " +
  							"values ( '" + this.id + "', '" + (i + 1) + "', '" + loaisauhoi + "', N'" + cauhoi + "', N'" + huongdantraloi + "' ) ";
  					if(!db.update(query))
  					{
  						this.msg = "Không thể tạo mới KHAOSAT_CAUHOI " + query;
  						db.getConnection().rollback();
  						return false;
  					}
  					
  					if(!loaisauhoi.equals("0"))
  					{
  						query = "select IDENT_CURRENT('KHAOSAT_CAUHOI') as ksId";
  						ResultSet rs = db.get(query);
  						String khaosat_cauhoi_fk = "";
  						if(rs != null)
  						{
  							if(rs.next())
  							{
  								khaosat_cauhoi_fk = rs.getString("ksId");
  							}
  							rs.close();
  						}
  						
  						String[] dapanArr = null;
  	  					if(dapan.trim().length() > 0)
  	  						dapanArr = dapan.split("__");
  	  					
  	  					if(dapanArr != null)
  	  					{
  							query = "Insert KHAOSAT_CAUHOI_DAPAN(khaosat_cauhoi_fk, luachon1, luachon2, luachon3, luachon4, luachon5) " +
  									"values ( '" + khaosat_cauhoi_fk + "', N'" + dapanArr[0] + "', N'" + dapanArr[1] + "', N'" + dapanArr[2] + "', N'" + dapanArr[3] + "', N'" + dapanArr[4] + "' ) ";
  							
  	  						if(!db.update(query))
  	    					{
  	    						this.msg = "Không thể tạo mới KHAOSAT_CAUHOI_DAPAN " + query;
  	    						db.getConnection().rollback();
  	    						return false;
  	    					}
  	  					}
  					}
				}
			}
			
			/*query = "Insert KHAOSAT_DDKD(khaosat_fk, ddkd_fk) select '" + this.id + "', pk_seq " +
					"from DaiDienKinhDoanh where pk_seq in ( " + this.ddkdId + " ) ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới KHAOSAT_DDKD " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	
	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
		} 
		catch (Exception e) {}
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	
	public String getTieude() {
		
		return this.tieude;
	}

	
	public void setTieude(String tieude) {
		
		this.tieude = tieude;
	}

	
	public String getBophan() {
		
		return this.bophan;
	}

	
	public void setBophan(String bophan) {
		
		this.bophan = bophan;
	}

	
	public String getSocauhoi() {
		
		return this.socauhoi;
	}

	
	public void setSocauhoi(String socauhoi) {
		
		this.socauhoi = socauhoi;
	}

	
	public Hashtable<String, String> getNoidungcauhoi() {
		
		return this.noidungcaihoi;
	}

	
	public void setNoidungcauhoi(Hashtable<String, String> noidung) {
		
		this.noidungcaihoi = noidung;
	}

	
	public ResultSet getVungRs() {
		
		return this.vungRs;
	}

	
	public void setVungRs(ResultSet vungRs) {
		
		this.vungRs = vungRs;
	}

	
	public String getVungId() {
		
		return this.vungId;
	}

	
	public void setVungId(String vungId) {
		
		this.vungId = vungId;
	}

	
	public ResultSet getKhuvucRs() {
		
		return this.kvRs;
	}

	
	public void setKhuvucRs(ResultSet kvRs) {
		
		this.kvRs = kvRs;
	}

	
	public String getKhuvucId() {
		
		return this.kvId;
	}

	
	public void setKhuvucId(String kvId) {
		
		this.kvId = kvId;
	}

	
	public ResultSet getDdkdRs() {
		
		return this.ddkdRs;
	}

	
	public void setDdkdRs(ResultSet ddkdRs) {
		
		this.ddkdRs = ddkdRs;
	}

	
	public String getDdkdId() {
		
		return this.ddkdId;
	}

	
	public void setDdkdId(String ddkdId) {
		
		this.ddkdId = ddkdId;
	}

	
	public void createRs() 
	{
		this.vungRs = db.get("select PK_SEQ, TEN from VUNG");
		
		String query = "select * from KHUVUC where TRANGTHAI = '1'";
		if(this.vungId.trim().length() > 0)
			query += " and vung_fk = '" + this.vungId + "' ";
		this.kvRs = db.get(query);
		
		query = "select a.PK_SEQ, a.PK_SEQ as MANHANVIEN, a.TEN, isnull(a.DIACHI, '') as DIACHI, a.DIENTHOAI  " +
				"from DAIDIENKINHDOANH a inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ  " +
				"where a.TRANGTHAI = '1'";
		if(this.kvId.trim().length() > 0)
			query += " and b.khuvuc_fk = '" + this.kvId + "' ";
		if(this.vungId.trim().length() > 0)
			query += " and b.khuvuc_fk in ( select pk_seq from KhuVuc where vung_fk = '" + this.vungId + "' ) ";
		
		if(this.ddkdId.trim().length() > 0)
			query += " union select PK_SEQ, PK_SEQ as MANHANVIEN, TEN, isnull(DIACHI, '') as diachi, DIENTHOAI from DAIDIENKINHDOANH where pk_seq in ( " + this.ddkdId + " ) ";
		
		this.ddkdRs = db.get(query);
	}
	
	public String getDenngay() {
		return denngay;
	}
	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}
	public void setTungay(String tungay) {
		this.tungay = tungay;
	}
	public String getTungay() {
		return tungay;
	}
	public String getLoaict() {
		return loaict;
	}
	public void setLoaict(String loaict) {
		this.loaict = loaict;
	}

	public String getDoituong() {
		return this.doituong;
	}
	public void setDoituong(String doituong) {
		this.doituong = doituong;
	}

}
