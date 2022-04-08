package geso.dms.distributor.beans.phieuthutien.imp;

import geso.dms.distributor.beans.phieuthutien.IPhieuthutien;
import geso.dms.distributor.db.sql.dbutils;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Phieuthutien implements IPhieuthutien,Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;	
	String ngay;
	String tungay;
	String denngay;
	String nvgnId;
	ResultSet nvgn;
	String pxkId;
	ResultSet pxk;
	
	String diengiai;
	String tongsotien;
	String[] khIds;
	String khId;
	ResultSet kh;
	ResultSet khselected;
	
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	boolean claim;
	
	dbutils db;
	public Phieuthutien(String id)
	{
		this.id = id;	
		this.pxkId = "";
		this.userId = "";
		this.ngay = "";
		this.nvgnId = "";
		this.khId = "";
		this.diengiai = "";
		this.tongsotien = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";		
		this.nppId = "";
		this.nppTen = "";
		this.sitecode = "";
		this.claim = false;
		this.tungay = "";
		this.denngay = "";
		db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public boolean getClaim()
	{
		return this.claim;
	}

	public void setClaim(boolean claim) 
	{
		this.claim = claim;
	}

	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getNgay()
	{
		return this.ngay;		
	}

	public void setNgay(String ngay)
	{
		this.ngay = ngay;
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
	
	public String getTungay()
	{
		return this.tungay;	
	}

	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;	
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
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
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
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
		ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
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
		
		//Phien ban moi
/*		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();*/
	}
	
	public ResultSet getNvgn() 
	{		
		return this.nvgn;
	}
	
	public void setNvgn(ResultSet nvgn) 
	{
		this.nvgn = nvgn;		
	}
	
	public ResultSet getKh() 
	{		
		return this.kh;
	}
	
	public void setKh(ResultSet kh) 
	{
		this.kh = kh;		
	}

	public ResultSet getKhSelected() 
	{		
		return this.khselected;
	}
	
	public void setKhSelected(ResultSet khSelected) 
	{
		this.khselected = khSelected;		
	}

	public ResultSet getPxk() 
	{		
		return this.pxk;
	}
	
	public void setPxk(ResultSet pxk) 
	{
		this.pxk = pxk;		
	}

	public String getNvgnId() 
	{		
		return this.nvgnId;
	}
	
	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;		
	}
	
	public String getPxkId() 
	{		
		return this.pxkId;
	}
	
	public void setPxkId(String pxkId) 
	{
		this.pxkId = pxkId;		
	}
	
	public String[] getKhIds() 
	{		
		return this.khIds;
	}
	
	public void setKhIds(String[] khIds) 
	{
		this.khIds = khIds;		
	}
	
	public String getKhId() 
	{		
		return this.khId;
	}
	
	public void setKhId(String khId) 
	{	
		this.khId = khId;
	}

	public String getTongsotien() 
	{		
		return this.tongsotien;
	}
	
	public void setTongsotien(String tongsotien) 
	{
		this.tongsotien = tongsotien;		
	}
	
	public String getDiengiai() 
	{		
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;		
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	

	
	public void createRS() 
	{
		this.getNppInfo();
		this.createNvgnRS();
		this.createPxkRS();
	}
	
	public void initNew(){
		this.getNppInfo();
		String khlist="";
		
		if(this.khIds != null){
			for(int i = 0; i < this.khIds.length; i++){
				if (i != this.khIds.length - 1)
					khlist = khlist + khIds[i] + ",";
				else
					khlist = khlist + khIds[i];
			}
		}
		
		if(this.khId.length()>0){
			if(khlist.length()>0)
				khlist = khlist + "," + this.khId;
			else
				khlist = this.khId;
		}
		//
		System.out.println("khach hang list: " + khlist);
		
		String sql = "";
		
		if(khlist.length() > 0){
					 			
			sql = 	"SELECT KH.PK_SEQ AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI , " +	
					"(SUM(ISNULL(KH_CN.SOTIENNO,0)) - ISNULL(A.SOTIEN,0)) AS TONGNO, " +
					//"SUM(ISNULL(PTT_KH.SOTIEN, 0)) AS SOTIEN, SUM(ISNULL(PTT_KH.SODU,0)) AS SODU " +
					"0 AS SOTIEN, 0 AS SODU " +
					"FROM KHACHHANG KH " +
					"INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " + 
					//"LEFT  JOIN " +
					//"PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK " + 
					"LEFT JOIN ( " +
						"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN " +
						"FROM " +
						"PHIEUTHANHTOAN PTT " + 
						"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " + 
						"WHERE KH_CN.NGAYNO >='"+this.tungay+"' AND KH_CN.NGAYNO <='"+this.denngay+"' AND KH_CN.KHACHHANG_FK IN ("+ khlist + ") " +
						"GROUP BY KH_CN.KHACHHANG_FK " +			
					")A ON A.KHID = KH_CN.KHACHHANG_FK " +
					"WHERE KH.NPP_FK='"+this.nppId+"' and  KH.PK_SEQ IN ("+ khlist + ") AND KH_CN.NGAYNO >='"+this.tungay+"' AND KH_CN.NGAYNO <='"+this.denngay+"' " +
					"GROUP BY KH.PK_SEQ, KH.TEN, KH.DIACHI, A.SOTIEN ORDER BY KHTEN" ;
			
			System.out.println("khSelected:" + sql);
			
			this.khselected = this.db.get(sql);
			if(!this.claim){

				sql = 	"SELECT KH.PK_SEQ AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI , " +	
						"(SUM(ISNULL(KH_CN.SOTIENNO,0)) - ISNULL(A.SOTIEN,0)) AS TONGNO, " +
						//"SUM(ISNULL(PTT_KH.SOTIEN, 0)) AS SOTIEN, SUM(ISNULL(PTT_KH.SODU,0)) AS SODU " +
						"0 AS SOTIEN, 0 AS SODU " +
						"FROM KHACHHANG KH " +
						"INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " + 
						//"LEFT  JOIN " +
						//"PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK " + 
						"LEFT JOIN ( " +
							"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN " +
							"FROM " +
							"PHIEUTHANHTOAN PTT " + 
							"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " + 
							"WHERE KH_CN.NGAYNO >='"+this.tungay+"' AND KH_CN.NGAYNO <='"+this.denngay+"' AND KH_CN.KHACHHANG_FK NOT IN ("+ khlist + ") " +
							"GROUP BY KH_CN.KHACHHANG_FK " +			
						")A ON A.KHID = KH_CN.KHACHHANG_FK " +
						"WHERE KH.NPP_FK='"+this.nppId+"' and  KH.PK_SEQ NOT IN ("+ khlist + ") AND KH_CN.NGAYNO >='"+this.tungay+"' AND KH_CN.NGAYNO <='"+this.denngay+"' " +
						"GROUP BY KH.PK_SEQ, KH.TEN, KH.DIACHI, A.SOTIEN ORDER BY KHTEN" ;
				
/*				sql = 	"SELECT KH_CN.KHACHHANG_FK AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI, " +
						"(SUM(CEILING(ISNULL(KH_CN.SOTIENNO,0)))-A.SOTIEN) AS TONGNO, " +
						//"ISNULL(PTT_KH.SOTIEN, 0) AS SOTIEN, " + 
						"0 AS SOTIEN, " +
						//"ISNULL(PTT_KH.SODU,0) AS SODU " +
						"0 AS SODU " +
						"FROM PHIEUXUATKHO_DONHANG PXK_DH " +
						"INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.DONHANG_FK=PXK_DH.DONHANG_FK " +
						"INNER JOIN KHACHHANG KH ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " +
						"LEFT JOIN ( " +
							"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN " +
							"FROM " +
							"PHIEUTHANHTOAN PTT " + 
							"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " + 
							"WHERE KH_CN.NGAYNO >='2012-01-01' AND KH_CN.KHACHHANG_FK NOT IN ("+ khlist + ") " +
							"GROUP BY KH_CN.KHACHHANG_FK " +			
						")A ON A.KHID = KH_CN.KHACHHANG_FK " +

						//"LEFT JOIN PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK " + 
						//"LEFT JOIN PHIEUTHUTIEN_NVGN PTT_NVGN ON PTT_NVGN.PK_SEQ = PTT_KH.PTTNVGN_FK " +
						"WHERE PXK_DH.PXK_FK= '" + this.pxkId +"' " +
						"AND KH.PK_SEQ NOT IN (" + khlist + ") " +
						"GROUP BY KH_CN.KHACHHANG_FK, KH.TEN, KH.DIACHI,A.SOTIEN " +					
						"ORDER BY KHTEN";*/
				
				System.out.println("kh :" + sql);
				this.kh = this.db.get(sql);
			}else{
				this.kh = null;
			}
		}else{		
			if(!this.claim){
				if(this.pxkId.length()>0){
/*					sql = 	"SELECT KH_CN.KHACHHANG_FK AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI, " +
							"(SUM(CEILING(ISNULL(KH_CN.SOTIENNO,0)))- A.SOTIEN) AS TONGNO, " +
							//"ISNULL(PTT_KH.SOTIEN, 0) AS SOTIEN, " + 
							"0 AS SOTIEN, " +
							//"ISNULL(PTT_KH.SODU,0) AS SODU " +
							"0 AS SODU " +
							"FROM PHIEUXUATKHO_DONHANG PXK_DH " +
							"INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.DONHANG_FK=PXK_DH.DONHANG_FK " +
							"INNER JOIN KHACHHANG KH ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " +
							"LEFT JOIN ( " +
								"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN " +
								"FROM " +
								"PHIEUTHANHTOAN PTT " + 
								"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " + 
								"WHERE KH_CN.NGAYNO >='2012-01-01' " +
								"GROUP BY KH_CN.KHACHHANG_FK " +			
							")A ON A.KHID = KH_CN.KHACHHANG_FK " +
							//	"LEFT JOIN PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK " + 
							//"LEFT JOIN PHIEUTHUTIEN_NVGN PTT_NVGN ON PTT_NVGN.PK_SEQ = PTT_KH.PTTNVGN_FK " +
							"WHERE PXK_DH.PXK_FK= '" + this.pxkId +"' " +
							"GROUP BY KH_CN.KHACHHANG_FK, KH.TEN, KH.DIACHI, A.SOTIEN " +					
							"ORDER BY KHTEN";*/
					
					sql = 	"SELECT KH.PK_SEQ AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI , " +	
							"(SUM(ISNULL(KH_CN.SOTIENNO,0)) - ISNULL(A.SOTIEN,0)) AS TONGNO, " +
							//"SUM(ISNULL(PTT_KH.SOTIEN, 0)) AS SOTIEN, SUM(ISNULL(PTT_KH.SODU,0)) AS SODU " +
							"0 AS SOTIEN, 0 AS SODU " +
							"FROM KHACHHANG KH " +
							"INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " + 
							//"LEFT  JOIN " +
							//"PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK " + 
							"LEFT JOIN ( " +
							"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN " +
							"FROM " +
							"PHIEUTHANHTOAN PTT " + 
							"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " + 
							"WHERE  KH_CN.NGAYNO >='"+this.tungay+"' AND KH_CN.NGAYNO <='"+this.denngay+"' AND KH_CN.KHACHHANG_FK IN ("+ 
								"SELECT DH.KHACHHANG_FK FROM PHIEUXUATKHO_DONHANG PXK " +
								"INNER JOIN DONHANG DH ON DH.PK_SEQ = PXK.DONHANG_FK WHERE PXK_FK = '" + this.pxkId +"' " + 
							")  " +
							"GROUP BY KH_CN.KHACHHANG_FK " +			
							")A ON A.KHID = KH_CN.KHACHHANG_FK " +
							"WHERE KH.NPP_FK='"+this.nppId+"' and  KH.PK_SEQ IN " +
							"("+ 
									"SELECT DH.KHACHHANG_FK FROM PHIEUXUATKHO_DONHANG PXK " +
									"INNER JOIN DONHANG DH ON DH.PK_SEQ = PXK.DONHANG_FK WHERE PXK_FK = '" + this.pxkId +"' " + 
							") " +
							"AND KH_CN.NGAYNO >='"+this.tungay+"' AND KH_CN.NGAYNO <='"+this.denngay+"' " +
							"GROUP BY KH.PK_SEQ, KH.TEN, KH.DIACHI, A.SOTIEN " +
							"HAVING (SUM(ISNULL(KH_CN.SOTIENNO,0)) - ISNULL(A.SOTIEN,0))> 0 " +
							"ORDER BY KHTEN" ;
					
				}else{
/*					sql = 	"SELECT KH_CN.KHACHHANG_FK AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI, " +
							"(SUM(CEILING(ISNULL(KH_CN.SOTIENNO,0)))- A.SOTIEN) AS TONGNO, " +
							//"ISNULL(PTT_KH.SOTIEN, 0) AS SOTIEN, " + 
							"0 AS SOTIEN, " +
							//"ISNULL(PTT_KH.SODU,0) AS SODU " +
							"0 AS SODU " +
							"FROM PHIEUXUATKHO_DONHANG PXK_DH " +
							"INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.DONHANG_FK=PXK_DH.DONHANG_FK " +
							"INNER JOIN KHACHHANG KH ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " +
							"LEFT JOIN ( " +
								"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN " +
								"FROM " +
								"PHIEUTHANHTOAN PTT " + 
								"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " + 
								"WHERE KH_CN.NGAYNO >='2012-01-01' " +
								"GROUP BY KH_CN.KHACHHANG_FK " +			
							")A ON A.KHID = KH_CN.KHACHHANG_FK " +
							//	"LEFT JOIN PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK " + 
							//"LEFT JOIN PHIEUTHUTIEN_NVGN PTT_NVGN ON PTT_NVGN.PK_SEQ = PTT_KH.PTTNVGN_FK " +
							"WHERE PXK_DH.PXK_FK= '" + this.pxkId +"' " +
							"GROUP BY KH_CN.KHACHHANG_FK, KH.TEN, KH.DIACHI, A.SOTIEN " +					
							"ORDER BY KHTEN";	*/
					sql = 	"SELECT KH.PK_SEQ AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI , " +	
							"(SUM(ISNULL(KH_CN.SOTIENNO,0)) - ISNULL(A.SOTIEN,0)) AS TONGNO, " +
							//"SUM(ISNULL(PTT_KH.SOTIEN, 0)) AS SOTIEN, SUM(ISNULL(PTT_KH.SODU,0)) AS SODU " +
							"0 AS SOTIEN, 0 AS SODU " +
							"FROM KHACHHANG KH " +
							"INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " + 
							//"LEFT  JOIN " +
							//"PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK " + 
							"LEFT JOIN ( " +
								"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN " +
								"FROM " +
								"PHIEUTHANHTOAN PTT " + 
								"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " + 
								"WHERE KH_CN.NGAYNO >='"+this.tungay+"' AND KH_CN.NGAYNO <='"+this.denngay+"' " +
								"GROUP BY KH_CN.KHACHHANG_FK " +			
							")A ON A.KHID = KH_CN.KHACHHANG_FK " +
							"WHERE KH.NPP_FK='"+this.nppId+"' and   KH_CN.NGAYNO >='"+this.tungay+"' AND KH_CN.NGAYNO <='"+this.denngay+"' " +							
							"GROUP BY KH.PK_SEQ, KH.TEN, KH.DIACHI, A.SOTIEN " +
							"HAVING (SUM(ISNULL(KH_CN.SOTIENNO,0)) - ISNULL(A.SOTIEN,0))> 0 " +
							"ORDER BY KHTEN ";
					
				}
				this.kh = this.db.get(sql);
				System.out.println("khlist = 0; noclaim: " + sql);
			}else{
				this.kh = null;
				this.msg = "Vui long chon it nhat mot khach hang";
				this.claim = false;
			}
			this.khselected = null;
		}
		
		this.createNvgnRS();
		this.createPxkRS();
	}
	
	public void initUpdate(){
		this.getNppInfo();
		this.createNvgnRS();
		this.createPxkRS();
		String sql;
		
		// Thuc hien thu tien
		if(this.claim){
			String khlist="";

			//khIds chua danh sach khach hang chon them
			if(this.khIds != null){
				for(int i = 0; i < this.khIds.length; i++){
					if (i != this.khIds.length - 1)
						khlist = khlist + khIds[i] + ",";
					else
						khlist = khlist + khIds[i];
				}

			}

			if(this.khId.length()>0)
				khlist = khlist + "," + this.khId;
			
			// Lay danh sach khach hang cua Phieu thu tien
			sql = "SELECT PTT_KH.KHACHHANG_FK AS KHID FROM   " +     
				  "PHIEUTHUTIEN_KH PTT_KH " +
				  "INNER JOIN PHIEUTHUTIEN_NVGN PTT_NVGN ON PTT_NVGN.PK_SEQ = PTT_KH.PTTNVGN_FK " + 		     
				  "WHERE PTT_NVGN.PK_SEQ = '"+ this.id +"'" +    
				  "GROUP BY PTT_KH.KHACHHANG_FK " ;
			
			ResultSet rs = this.db.get(sql);
			System.out.println(sql);
			try{
				if(khlist.length() == 0){
					while(rs.next()){				
						khlist = khlist  + rs.getString("khId")  + ",";
					}
				}else{
					while(rs.next()){				
						khlist = khlist  + "," + rs.getString("khId")  ;
					}						
				}
			}catch(Exception e){}			
			
			if(khlist.substring(khlist.length()-1, khlist.length()).equals(","))
				khlist = khlist.substring(0, khlist.length()-1);
//			System.out.println(khlist.substring(khlist.length()-1, khlist.length()));
			
			// Lay du lieu cua khach hang cu va moi
			sql = 	"SELECT KH.PK_SEQ AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI , " + 
					"(SUM(ISNULL(KH_CN.SOTIENNO,0)) - ISNULL(A.SOTIEN,0)) AS TONGNO, " +
					"ISNULL(THUTIEN.SOTIEN, 0) AS SOTIEN, " +
					"ISNULL(THUTIEN.SODU,0) AS SODU FROM KHACHHANG KH " +
					"INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " + 
					"LEFT  JOIN ( " +
						"SELECT PTT_KH.KHACHHANG_FK AS KHID, SUM(PTT_KH.SOTIEN) AS SOTIEN, SUM(PTT_KH.SODU) AS SODU " + 
						"FROM PHIEUTHUTIEN_KH PTT_KH " +
						"WHERE PTT_KH.PTTNVGN_FK='"+ this.id +"' AND PTT_KH.KHACHHANG_FK IN ("+ khlist + ") " +
						"GROUP BY PTT_KH.KHACHHANG_FK " +
					")THUTIEN ON THUTIEN.KHID = KH_CN.KHACHHANG_FK " + 
					"LEFT JOIN " +
					"( " +
						"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN " +
						"FROM PHIEUTHANHTOAN PTT " + 
						"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " +
						"WHERE KH_CN.NGAYNO >='2012-01-01' " + 
						"AND KH_CN.KHACHHANG_FK IN ("+ khlist + ") " +
						"GROUP BY KH_CN.KHACHHANG_FK " +
					")A ON A.KHID = KH_CN.KHACHHANG_FK " + 
					"WHERE  KH.NPP_FK='"+this.nppId+"' and  KH.PK_SEQ IN ("+ khlist + ") AND KH_CN.NGAYNO >='2012-01-01' " +
					"GROUP BY KH.PK_SEQ, KH.TEN, KH.DIACHI, " +
					"A.SOTIEN, ISNULL(THUTIEN.SOTIEN, 0),ISNULL(THUTIEN.SODU,0)  ORDER BY KHTEN"; 


			this.khselected = this.db.get(sql);
			
		
			System.out.println("Lay du lieu cua khach hang cu va moi:" + sql);
		
			sql = "SELECT NGAYTHU, DIENGIAI, SOTIEN, NVGN_FK FROM PHIEUTHUTIEN_NVGN WHERE PK_SEQ='" + this.id + "'";
		
			rs = db.get(sql);
			try{
				rs.next();
				this.ngay = rs.getString("ngaythu");
				this.diengiai = rs.getString("diengiai");
				this.tongsotien = rs.getString("sotien");
				this.nvgnId = rs.getString("nvgn_fk");
				rs.close();
			}catch(Exception e){}
		}else
		// Thuc hien chon khach hang moi
		{		
			String khlist="";
			
			// Co danh sach KH moi chon
			if(this.khIds != null){
				for(int i = 0; i < this.khIds.length; i++){
					if (i != this.khIds.length - 1)
						khlist = khlist + khIds[i] + ",";
					else
						khlist = khlist + khIds[i];
				}

				if(this.khId.length()>0)
					khlist = khlist + "," + this.khId;

				sql = 	"SELECT KH.PK_SEQ AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI , " +	
						"(SUM(ISNULL(KH_CN.SOTIENNO,0)) - ISNULL(A.SOTIEN,0)) AS TONGNO, " +
						"SUM(ISNULL(PTT_KH.SOTIEN, 0)) AS SOTIEN, SUM(ISNULL(PTT_KH.SODU,0)) AS SODU " + 
						"FROM KHACHHANG_CONGNO KH_CN " +
						"INNER JOIN KHACHHANG KH ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " + 
						"LEFT  JOIN " +
						"PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK AND PTT_KH.PTTNVGN_FK='" + this.id + "' " + 
						"LEFT JOIN ( " +
							"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN " +
							"FROM " +
							"PHIEUTHANHTOAN PTT " + 
							"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " + 
							"WHERE KH_CN.NGAYNO >='2012-01-01' AND KH_CN.KHACHHANG_FK IN ("+ khlist + ") " +
							"GROUP BY KH_CN.KHACHHANG_FK " +			
						")A ON A.KHID = KH_CN.KHACHHANG_FK " +
						"WHERE KH.NPP_FK='"+this.nppId+"' and  KH_CN.KHACHHANG_FK IN ("+ khlist + ") AND KH_CN.NGAYNO >='2012-01-01' " +
						"GROUP BY KH.PK_SEQ, KH.TEN, KH.DIACHI, A.SOTIEN ORDER BY KHTEN" ;
				System.out.println("Co danh sach KH moi:" + sql);
		
				this.khselected = this.db.get(sql);
				
			}
			
			sql = 	"SELECT DISTINCT KH_CN.KHACHHANG_FK AS KHID " +
				  	"FROM KHACHHANG_CONGNO KH_CN  inner join khachhang kh on kh.pk_Seq=KH_CN.khachhang_fk " +
				  	"INNER JOIN PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK " + 
				  	"INNER JOIN PHIEUTHUTIEN_NVGN PTT_NVGN ON PTT_NVGN.PK_SEQ = PTT_KH.PTTNVGN_FK " +
					"LEFT JOIN ( " +
						"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN " +
						"FROM " +
						"PHIEUTHANHTOAN PTT " + 
						"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " + 
						"WHERE KH_CN.NGAYNO >='2011-06-01' " + 
						"GROUP BY KH_CN.KHACHHANG_FK " +
						"HAVING (SUM(KH_CN.SOTIENNO)-SUM(PTT.SOTIEN)) > 0 " +
					")A ON A.KHID = KH_CN.KHACHHANG_FK " +
					"WHERE kh.npp_fk='"+this.nppId+"'  and  PTT_NVGN.PK_SEQ = '"+ this.id +"' AND " +
					"KH_CN.NGAYNO >='2011-06-01'" +
					"GROUP BY KH_CN.KHACHHANG_FK,  ISNULL(A.SOTIEN,0) " + 
					"HAVING (SUM(ISNULL(KH_CN.SOTIENNO,0)) - ISNULL(A.SOTIEN,0))>0 ";
 
			System.out.println("Co danh sach KH moi 2:" + sql);
			ResultSet rs = this.db.get(sql);
				
			try{
					
				while(rs.next()){
					khlist = khlist + rs.getString("khId") + ",";
				}
			}catch(Exception e){}			
			
			khlist = khlist.substring(0, khlist.length()-1);
			
			// Hien danh sach KH chua chon
			if(this.pxkId.length() > 0){
				sql = 	"SELECT KH_CN.KHACHHANG_FK AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI, " + 
						"(SUM(CEILING(ISNULL(KH_CN.SOTIENNO,0)))-ISNULL(A.SOTIEN,0)) AS TONGNO, " +
						//"ISNULL(PTT_KH.SOTIEN, 0) AS SOTIEN, ISNULL(PTT_KH.SODU,0) AS SODU " +
						"0 AS SOTIEN, 0 AS SODU " +
						"FROM KHACHHANG_CONGNO KH_CN " +
						"INNER JOIN KHACHHANG KH ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " +
						"LEFT JOIN ( " +
							"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN FROM PHIEUTHANHTOAN PTT " + 
							"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK AND KH_CN.NGAYNO >='2012-01-01' " +
							"INNER JOIN PHIEUXUATKHO_DONHANG PXK_DH ON KH_CN.DONHANG_FK=PXK_DH.DONHANG_FK " +
							"AND KH_CN.KHACHHANG_FK " + 
							"IN (SELECT KHACHHANG_FK FROM DONHANG WHERE PK_SEQ IN (SELECT DONHANG_FK FROM PHIEUXUATKHO_DONHANG WHERE PXK_FK='" + this.pxkId +"')) " +
							"WHERE KH_CN.NGAYNO >='2012-01-01' AND KH_CN.KHACHHANG_FK NOT IN ("+ khlist +") " +
							"GROUP BY KH_CN.KHACHHANG_FK " +
						")A ON A.KHID = KH_CN.KHACHHANG_FK " +
						"LEFT JOIN PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK " + 
						"LEFT JOIN PHIEUTHUTIEN_NVGN PTT_NVGN ON PTT_NVGN.PK_SEQ = PTT_KH.PTTNVGN_FK " +
						"WHERE kh.npp_fk='"+this.nppId+"'  and  KH_CN.NGAYNO >='2012-01-01' "+ 
						"AND KH_CN.KHACHHANG_FK NOT IN ("+ khlist +") " +
						"AND KH_CN.KHACHHANG_FK " + 
						"IN (SELECT KHACHHANG_FK FROM DONHANG WHERE PK_SEQ IN (SELECT DONHANG_FK FROM PHIEUXUATKHO_DONHANG WHERE PXK_FK='" + this.pxkId +"')) " +
						"GROUP BY KH_CN.KHACHHANG_FK, KH.TEN, KH.DIACHI,A.SOTIEN ORDER BY KHTEN ";			
			}else{
				sql = 	"SELECT KH_CN.KHACHHANG_FK AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI, " + 
						"(SUM(CEILING(ISNULL(KH_CN.SOTIENNO,0)))-ISNULL(A.SOTIEN,0)) AS TONGNO, " +
						//	"ISNULL(PTT_KH.SOTIEN, 0) AS SOTIEN, ISNULL(PTT_KH.SODU,0) AS SODU " +
						"0 AS SOTIEN, 0 AS SODU " +
						"FROM KHACHHANG_CONGNO KH_CN " +
						"INNER JOIN KHACHHANG KH ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " +
						"LEFT JOIN ( " +
							"SELECT KH_CN.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS SOTIEN FROM PHIEUTHANHTOAN PTT " + 
							"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK AND KH_CN.NGAYNO >='2012-01-01' " +
							"INNER JOIN PHIEUXUATKHO_DONHANG PXK_DH ON KH_CN.DONHANG_FK=PXK_DH.DONHANG_FK " +
//							"AND KH_CN.KHACHHANG_FK " + 
//							"IN (SELECT KHACHHANG_FK FROM DONHANG WHERE PK_SEQ IN (SELECT DONHANG_FK FROM PHIEUXUATKHO_DONHANG WHERE PXK_FK='" + this.pxkId +"')) " +
							"WHERE KH_CN.NGAYNO >='2012-01-01' AND KH_CN.KHACHHANG_FK NOT IN ("+ khlist +") " +
							"GROUP BY KH_CN.KHACHHANG_FK " +
						")A ON A.KHID = KH_CN.KHACHHANG_FK " +
						"LEFT JOIN PHIEUTHUTIEN_KH PTT_KH ON PTT_KH.KHACHHANG_FK = KH_CN.KHACHHANG_FK " + 
						"LEFT JOIN PHIEUTHUTIEN_NVGN PTT_NVGN ON PTT_NVGN.PK_SEQ = PTT_KH.PTTNVGN_FK " +
						"WHERE  kh.npp_fk='"+this.nppId+"'  and  KH_CN.NGAYNO >='2012-01-01' "+ 
						"AND  KH_CN.KHACHHANG_FK NOT IN ("+ khlist +") " +
//						"AND KH_CN.KHACHHANG_FK " + 
//						"IN (SELECT KHACHHANG_FK FROM DONHANG WHERE PK_SEQ IN (SELECT DONHANG_FK FROM PHIEUXUATKHO_DONHANG WHERE PXK_FK='" + this.pxkId +"')) " +
						"GROUP BY KH_CN.KHACHHANG_FK, KH.TEN, KH.DIACHI,A.SOTIEN ORDER BY KHTEN ";			
				
			}
			System.out.println("Hien danh sach chua chon:" + sql);
			this.kh = this.db.get(sql);
			
		}
	}
	
	public boolean UpdatePtt(HttpServletRequest request)
	{
		String sql = "UPDATE PHIEUTHUTIEN_NVGN SET DIENGIAI='" + this.diengiai + "', NGAYTHU = '" + this.ngay + "', SOTIEN = '" + this.tongsotien + "', NGUOISUA ='" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "' WHERE PK_SEQ ='" + this.id + "'";
		if(!db.update(sql))
		{
			System.out.println(sql);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Khong the cap nhat 'PHIEUTHUTIEN_NVGN': " + sql;
			return false;
		}

		sql = "DELETE FROM PHIEUTHUTIEN_KH WHERE PTTNVGN_FK='" + this.id + "'";
		System.out.println(sql);
		db.update(sql);

		for(int i = 0; i < this.khIds.length; i++){
			String sotien = request.getParameter("tt" + this.khIds[i]);
			String sodu = request.getParameter("sd" + this.khIds[i]);
			if(!sotien.equals("0")){				
				sql= "INSERT INTO PHIEUTHUTIEN_KH(KHACHHANG_FK, PTTNVGN_FK, SOTIEN, SODU) VALUES('" + this.khIds[i] + "','" + this.id + "','" + sotien + "','" + sodu + "')";
				System.out.println(sql);
				if(!db.update(sql)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Khong the cap nhat 'PHIEUTHUTIEN_KH': " + sql;
					return false;
				}
			}
		}
		

		return true;
	}
	
	public boolean PostPtt(){
	
		String sql =	" SELECT PTTKH.KHACHHANG_FK, PTTKH.SOTIEN FROM PHIEUTHUTIEN_NVGN PTTNVGN " +
						" INNER JOIN PHIEUTHUTIEN_KH PTTKH ON PTTNVGN.PK_SEQ = PTTKH.PTTNVGN_FK " +
						" WHERE PTTKH.PTTNVGN_FK='" + this.id + "' AND PTTNVGN.TRANGTHAI='0'";
					
		System.out.println("First sql:" + sql);
		
		
		try{
			
			this.db.getConnection().setAutoCommit(false);
			
			String	msg1= ThanhtoanCu(this.id);
			
			if(msg1.length()>0){
				System.out.println("Khong thanh cong:" + msg1);
				db.getConnection().rollback();
				this.msg="Khong Thanh Cong :" +msg1;
				return false;
			}
			ResultSet rs = this.db.get(sql);
			
			if(rs != null){
				while(rs.next()){
					double amt = Double.parseDouble(rs.getString("sotien")); // + Double.parseDouble(rs.getString("sodu"));
				
					String khId = rs.getString("khachhang_fk");
					System.out.println("Khach hang:" + khId);
					
					amt = Thanhtoan(this.db, this.id, khId, amt);
					if(amt<0){
						db.getConnection().rollback();
						 this.msg= "Loi : Khong The Cap Nhat Phieu Thanh Toan Cho Khach Hang" +khId  + ". Va Phieu Thu tien : " + this.id  ;
						 return false;
					}
					
					sql = "UPDATE PHIEUTHUTIEN_KH SET SODU = '" + Math.round(amt) + "' WHERE PTTNVGN_FK='" + this.id + "' AND KHACHHANG_FK = '" + khId + "'";
					if(!this.db.update(sql)){
						System.out.println("Khong thanh cong:" + sql);
						this.msg="Khong Thanh Cong :" +sql;
						db.getConnection().rollback();
						return false;
					}else{
						System.out.println("Thanh cong:" + sql);
					}
						
				}			
			
				sql = "UPDATE PHIEUTHUTIEN_NVGN SET TRANGTHAI = '1' WHERE PK_SEQ = '" + this.id + "'";
				if(!this.db.update(sql)){
					this.msg="Khong Thanh Cong :" +sql;
					db.getConnection().rollback();
					return false;
				}else{
					System.out.println("Thanh cong:" + sql);
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception e){
			try{
				db.getConnection().rollback();
				System.out.println("Error here :"+e.toString());
				
				return false;
			}catch(Exception er){
				
			}
			this.msg="Error here :"+e.toString();
		}
		return true;
	}

	public void DeletePtt(){
		String sql = "select count(pk_seq) as num from phieuthutien_nvgn where pk_seq ='" + this.id +"' and trangthai = '0'";
		ResultSet rs = db.get(sql);
		try{
			rs.next();
			if (rs.getString("num").equals("1")){
				db.update("update phieuthutien_nvgn set trangthai='2' where pk_seq='" + this.id +"'");
			}
			rs.close();
		}catch(Exception e){}

	}
	
	private String ThanhtoanCu(String idptt_nvgn){
		String sql = 	"SELECT PTTKH.PTTNVGN_FK,  PTTKH.KHACHHANG_FK,PTTKH.SODU as sotien FROM PHIEUTHUTIEN_KH PTTKH " +
						"INNER JOIN PHIEUTHUTIEN_NVGN PTTNVGN ON PTTNVGN.PK_SEQ = PTTNVGN_FK " +
						"WHERE PTTKH.SODU > 0 AND PTTNVGN.TRANGTHAI = '1' AND PTTNVGN.NVGN_FK  in (select NVGN_FK from PHIEUTHUTIEN_NVGN where pk_seq='"+idptt_nvgn+"' ) " +  
						"GROUP BY PTTKH.PTTNVGN_FK, PTTKH.KHACHHANG_FK, PTTKH.SODU"; 
		System.out.println("Thanhtoancu la :" + sql);
		ResultSet rs = this.db.get(sql);
		String record = "";
		try{
			if(rs != null){
				while(rs.next()){
					double amt = Double.parseDouble(rs.getString("sotien")); // + Double.parseDouble(rs.getString("sodu"));
					
					String khId = rs.getString("khachhang_fk");
			
					String pttId = rs.getString("PTTNVGN_FK");
					System.out.println("Thanhtoancu: khId" + khId);
					
					amt = Thanhtoan(this.db, pttId, khId, amt);
					if(amt<0){
						db.getConnection().rollback();
						 return "Loi : Khong The Cap Nhat Phieu Thanh Toan Cho Khach Hang" +khId  + ". Va Phieu Thu tien : " + pttId  ;
					}
					if(record.length()==0)
						record = pttId + ";" + khId + ";" + amt;
					else
						record = record + ";" + pttId + ";" + khId + ";" + amt;
			
				}
			
				rs.close();
			
				String[] sodu = record.split(";");

				for (int i = 0; i < sodu.length-2 ; i = i + 3 ){		
					sql = "UPDATE PHIEUTHUTIEN_KH SET SODU = '" + Math.round(Double.parseDouble(sodu[i+2])) + "' WHERE PTTNVGN_FK='" + sodu[i] + "' AND KHACHHANG_FK = '" + sodu[i+1] + "'";
					if(!this.db.update(sql)){
						System.out.println(sql);
						this.db.getConnection().rollback();
						return "Loi :  " + sql;
					}
				}
			}
		}catch(Exception e){
			System.out.println("Loi te le :"+e.toString());
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			
			return "Loi :  " + e.toString();
		}
		return "";
	}
	
	private double Thanhtoan(dbutils db, String pttId, String khId, double amt){
		long tmp = Math.round(amt);
/*		String sql = 	"SELECT DH.KHACHHANG_FK AS KHID, DH.PK_SEQ AS DHID, 
 * 						"CEILING(DH.TONGGIATRI - ISNULL(DH_CTKM.CHIETKHAU,0)) AS TONGGIATRI, " +
						"DH.DATHANHTOAN  " +
						"FROM DONHANG DH " +     
						"LEFT JOIN DONHANG_CTKM_TRAKM DH_CTKM ON DH_CTKM.DONHANGID = DH.PK_SEQ " +
						"WHERE DH.TRANGTHAI='1' AND (DH.TONGGIATRI - ISNULL(DH_CTKM.CHIETKHAU,0)) > DH.DATHANHTOAN " +
						"AND DH.KHACHHANG_FK= '" + khId +"'" +
						"GROUP BY DH.KHACHHANG_FK, DH.PK_SEQ, CEILING(DH.TONGGIATRI - ISNULL(DH_CTKM.CHIETKHAU,0)), DH.DATHANHTOAN";*/
		
		String sql = 	"SELECT KH_CN.KHACHHANG_FK AS KHID, KH_CN.DONHANG_FK AS DHID, KH_CN.NGAYNO,	" +
						"KH_CN.SOTIENNO AS TONGGIATRI, ISNULL(A.SOTIEN,0) AS DATHANHTOAN, " +
						"(KH_CN.SOTIENNO - ISNULL(A.SOTIEN,0)) AS CONLAI " +
						"FROM KHACHHANG_CONGNO KH_CN " +
						"INNER JOIN DONHANG DH ON DH.PK_SEQ = KH_CN.DONHANG_FK " +
						"LEFT JOIN ( " +
								"SELECT KH_CN.KHACHHANG_FK AS KHID, KH_CN.DONHANG_FK AS DHID, " + 
								"SUM(PTT.SOTIEN) AS SOTIEN " +
								"FROM " +
								"PHIEUTHANHTOAN PTT " + 
								"INNER JOIN KHACHHANG_CONGNO KH_CN ON PTT.DONHANG_FK = KH_CN.DONHANG_FK " +
								"WHERE KH_CN.KHACHHANG_FK = '"+ khId + "'"	+
								"GROUP BY KH_CN.KHACHHANG_FK, KH_CN.DONHANG_FK " +
						")A ON A.KHID = KH_CN.KHACHHANG_FK AND A.DHID = KH_CN.DONHANG_FK " + 
						"WHERE  (KH_CN.SOTIENNO - ISNULL(A.SOTIEN,0))>0 AND " +
						"KH_CN.KHACHHANG_FK = '"+ khId + "' AND KH_CN.NGAYNO >='2011-06-01'" +
						"GROUP BY KH_CN.KHACHHANG_FK, KH_CN.DONHANG_FK, KH_CN.SOTIENNO, A.SOTIEN, KH_CN.NGAYNO " +						
						"ORDER BY KH_CN.NGAYNO" ;

		
		System.out.println(sql);
		ResultSet debtRS = this.db.get(sql);
		
		String record = "";
		try{
			while(debtRS.next()){
				long debt = Math.round(Double.parseDouble(debtRS.getString("CONLAI")));
				String dhId = debtRS.getString("DHID");
	
				if(tmp - debt > 0){
					if(record.length()==0)
						record = dhId + ";" + debt;
					else
						record = record + ";" + dhId + ";" + debt;
		
					tmp = tmp - debt;
				}else
				{
					if(tmp > 0){
						if(record.length()==0)
							record = dhId + ";" + tmp;
						else
							record = record + ";" + dhId + ";" + tmp;
		
						tmp = 0;
					}	
				}
			}

			debtRS.close();
			String[] payment = record.split(";");

			for (int i = 0; i < payment.length-1 ; i = i + 2 ){
				sql = "UPDATE DONHANG SET DATHANHTOAN = DATHANHTOAN + " + payment[i+1] + " WHERE PK_SEQ ='" + payment[i] + "'";
				
				if(!this.db.update(sql)){
					System.out.println("Khong thang cong:" + sql);
					return (-1);
				}else{
					System.out.println("Thanh cong:" + sql);
				}
				
				String ngaythutien= this.getngaythutien(pttId,db);
				
				sql = "INSERT INTO PHIEUTHANHTOAN(NGAY, DONHANG_FK, SOTIEN, PTTKH_FK) VALUES( '"+ngaythutien+"' ,'" + payment[i] + "','" + payment[i+1] + "','" + pttId + "')";
				System.out.println("Phieu thanh toan insert :"+sql);
				if(!this.db.update(sql)){
					System.out.println("Khong thang cong:" + sql);
					return (-1);
				}else{
					System.out.println("Thanh cong:" + sql);
				}
				
			}

		}catch(Exception e){
		}
		return tmp;
	}
	
	private String getngaythutien(String pttId,dbutils db) {
	try{
		String sql="select ngaythu from phieuthutien_nvgn where pk_Seq="+pttId;
		ResultSet rs=db.get(sql);
		System.out.println("phieuthutien_nvgn get ngayS :"+sql);
		if(rs.next()){
					return rs.getString("ngaythu");
				}else{
					return "";
		}
		}catch(SQLException er){
			return "";
		}
		
	}

	public boolean CreatePtt(HttpServletRequest request) 
		{
		try{

			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			
			if(this.nvgnId.length() == 0){
				String sql = "select top(1) pk_seq from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai = '1'";
				this.nvgn = db.get(sql);
				if(this.nvgn != null){
					this.nvgn.next();
					this.nvgnId = this.nvgn.getString("pk_seq");
				}
			}
				
			if(this.ngay.length() == 10) //dinh dang dd-mm-yyyy (dinh dang dd-mmm-yyyy ko convert)
				this.ngay = convertDate(this.ngay);
			
			String query = "INSERT INTO PHIEUTHUTIEN_NVGN " +
			               "(NVGN_FK, NGAYTHU, DIENGIAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, NPP_FK, SOTIEN, TRANGTHAI )" +    
						   "VALUES ('" + this.nvgnId + "','" + this.ngay + "','" + this.diengiai + "','" + this.ngaytao + "','" + this.ngaytao + "','"+ this.nguoitao + "','"+ this.nguoitao + "','" + this.nppId + "','" + this.tongsotien + "','0')"; 
			
			if(!db.update(query))
			{
				System.out.println(query);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Khong the tao moi 'Phieuthutien': " + query;
				return false;
			}else{
				query = "select IDENT_CURRENT('PHIEUTHUTIEN_NVGN') as PTTID";
				ResultSet rs = db.get(query);
				rs.next();
				String pttId = rs.getString("PTTID");
				for(int i = 0; i < this.khIds.length; i++){
					String sotien = request.getParameter("tt" + this.khIds[i]);
					String sodu = request.getParameter("sd" + this.khIds[i]);
					if(!sotien.equals("0")){			
						query = "INSERT INTO PHIEUTHUTIEN_KH(KHACHHANG_FK, PTTNVGN_FK, SOTIEN, SODU) VALUES('" + this.khIds[i] + "','" + pttId + "','" + sotien + "','" + sodu + "')";
						if(!db.update(query))
						{
							System.out.println(query);
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg = "Khong the tao moi 'Phieuthutien': " + query;
							return false;
						}
					}
				}
				
			}
			}catch(Exception e){}
			return true;
		}
	
	
	private void createNvgnRS()
	{
		String sql = "select * from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai = '1'";
		System.out.println(sql);
		this.nvgn = db.get(sql);
	}

	private void createPxkRS(){	
		
		String sql = "select pk_seq, ngaylapphieu from phieuxuatkho where trangthai=1 and nvgn_fk='"+ this.nvgnId + "' and ngaylapphieu >='" + this.tungay + "' and ngaylapphieu <= '" + this.denngay + "' ";
		System.out.println(sql);
		this.pxk = db.get(sql);
		
	}
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String convertDate(String date) 
	{
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return getDateTime();
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}
	
	
	public void DBclose() 
	{
		try{
			if(kh != null) this.kh.close();
			if(nvgn != null) this.nvgn.close();
			if(pxk != null) this.pxk.close();
			if(this.db != null)
			this.db.shutDown();
		}catch(Exception e){}
	}

	
	
}
