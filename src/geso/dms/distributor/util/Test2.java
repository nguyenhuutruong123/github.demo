package geso.dms.distributor.util;

import geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Donhang;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.beans.donhang.*;
import jxl.Cell;
import jxl.Sheet;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class Test2 extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public Test2()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);

		if (session != null && !session.isNew())
		{
			out.println(request.getRequestedSessionId());
		} else
		{
			out.println("expired");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			this.out = response.getWriter();
			dbutils db = new dbutils();
			IDonhang dhBean;
			dhBean=new Donhang("");
			dhBean.setUserId(userId); //phai co UserId truoc khi Init
			Utility util = new Utility();
			readExcel("",dhBean);
		
		}
	}

	private void readExcel(String fileName, IDonhang dhBean)
	{
		fileName = "C:\\FileUploadDonHang.xls";
		File inputWorkbook = new File(fileName);
		jxl.Workbook w = null;
		try
		{
			//	db.getConnection().setAutoCommit(false);
				w = jxl.Workbook.getWorkbook(inputWorkbook);
				jxl.Sheet sheet = w.getSheet(0);
				int sodong = sheet.getRows();
				int socot = sheet.getColumns();
				System.out.println("So dong " + sodong + "socot " + socot);
				// sheet.getCell(cot, dong);
				Hashtable<String, String> htbKhoId=this.gethtbKho();
				Hashtable<String, String> htbSanPhamId=this.gethtbSanpham();
				List<ISanpham>	spList = new ArrayList<ISanpham>();
				String SmartIdPrev="";
				int soDh = 0;
				String NgayNhap;
				String NppId ;
				String DdkdId ;
				String GsbhId;
				String KhId="";
				String MaKho;
				String KhoId = "";
				String MaSp;
				String SanPhamId;
				String SanPham;
				String KbhId;
				String SoLuong;
				String DonGia;
				String ChietKhau;
				int SoDongDh = 1;
				double Vat = 0;
				int SoDong;
				double TongTienAVAT=0;
				double TongTienBVAT = 0;	
				double TongTienVAT = 0;
				double TienCK=0;
				double TongTienCK=0;
				String Scheme; 
				String SmartId;
				String query;
				Hashtable<String, String> hbGsbh =getHtbGSBH(); 
				Hashtable<String, Integer> DH_SO_DONG = new Hashtable<String, Integer>();
				for (int i =1 ; i < sodong; i++)
				{
					NgayNhap=getValue(sheet, 0, i).trim();
					NppId=getValue(sheet,1, i).trim();
					DdkdId=getValue(sheet,2, i).trim();
					KhId=getValue(sheet,3, i).trim();
					NppId=getValue(sheet,1, i).trim();
					SmartId=NgayNhap+"_"+NppId+"_"+DdkdId+"_"+KhId;
					if(!DH_SO_DONG.containsKey(SmartId))
					{
						DH_SO_DONG.put(SmartId, 1);
					}
					else
					{
						int soluong = DH_SO_DONG.get(SmartId);
						DH_SO_DONG.put(SmartId, soluong + 1);
					//	System.out.println("[SmartId]"+SmartId+"[So dong /don hang]"+DH_SO_DONG.get(SmartId));
					}
				}
				for (int i =1 ; i < sodong; i++)
				{
					//jxl.Cell cell = null;
					NgayNhap=getValue(sheet, 0, i).trim();
					NppId=getValue(sheet,1, i).trim();
					DdkdId=getValue(sheet,2, i).trim();
					GsbhId=hbGsbh.get(DdkdId);
					KhId=getValue(sheet,3, i).trim();
					MaKho=getValue(sheet,4, i).trim();
					MaSp=getValue(sheet,5, i).trim();
					if(MaSp.trim().equals(""))
						SanPhamId="NULL";
					else
						SanPhamId=htbSanPhamId.get(MaSp);
					if(MaKho.trim().length()>0)
						KhoId=htbKhoId.get(MaKho);
					
					SoLuong=getValue(sheet,6, i).replace(",", "");
					DonGia=getValue(sheet,7, i).replace(",", "").trim();
					ChietKhau=getValue(sheet,9, i).replace(",", "");
					Vat=Double.parseDouble(getValue(sheet,8, i).replace(",", "").trim().length()==0?"0":getValue(sheet,8, i).replace(",", "").trim());
					if(ChietKhau.trim().length()==0)
						ChietKhau="0";
					if(DonGia.length()==0)
						DonGia="0";
					if(SoLuong.length()==0)
						SoLuong="0";
					
					
					TienCK=(Double.parseDouble(SoLuong)*Double.parseDouble(DonGia)  )*100/Double.parseDouble(ChietKhau);
					
					Scheme=getValue(sheet,10, i).trim();
					SmartId=NgayNhap+"_"+NppId+"_"+DdkdId+"_"+KhId;
					dhBean.setNgaygiaodich(NgayNhap);
					dhBean.setDaidienkd(DdkdId);
					dhBean.setGsbhId(GsbhId);
					dhBean.setKhId(KhId);
					dhBean.setKhoId(KhoId);
				
					
					if(!SmartId.equals(SmartIdPrev))
					{
						spList.clear();
						SoDongDh=1;
						soDh++;
						SmartIdPrev=SmartId;
						TongTienCK=TienCK;
						TongTienVAT=Vat;		
						TongTienBVAT=Double.parseDouble(SoLuong)*Double.parseDouble(DonGia);
						TongTienAVAT=Double.parseDouble(SoLuong)*Double.parseDouble(DonGia);
						dhBean.setTongChietKhau(String.valueOf(TongTienCK));
						dhBean.setTongtiensauVAT(String.valueOf(TongTienBVAT));
						
						System.out.println("[Diff]"+"[SmartId]"+SmartId+"[SmartIdPrev]"+SmartIdPrev+"[SoDh]"+soDh);
						
						ISanpham sanpham = null;
						sanpham=new Sanpham();
						sanpham.setId(SanPhamId);
						sanpham.setDongia(DonGia);
						sanpham.setMasanpham(MaSp);
						sanpham.setSoluong(SoLuong);
						sanpham.setChietkhau(ChietKhau);
						spList.add(sanpham);
						
						dhBean.setSpList(spList);
						
						if(DH_SO_DONG.get(SmartId)==SoDongDh)
						{
							System.out.println("Tao don hang__"+"[So dong]"+SoDongDh);
							if(CheckInsertOrUpdate(SmartId)==0)
								this.CreateDonHang(dhBean, spList);
								else
								this.UpdateDonHang(dhBean, spList);
						}
					}
					else if(SmartId.equals(SmartIdPrev))
					{
						TongTienCK+=TienCK;
						TongTienVAT+=Vat;		
						TongTienBVAT+=Double.parseDouble(SoLuong)*Double.parseDouble(DonGia);
						TongTienAVAT+=Double.parseDouble(SoLuong)*Double.parseDouble(DonGia);
						dhBean.setTongChietKhau(String.valueOf(TongTienCK));
						dhBean.setTongtiensauVAT(String.valueOf(TongTienBVAT));
						System.out.println("[Same]"+"[SmartId]"+SmartId+"[SmartIdPrev]"+SmartIdPrev+"[SoDh]"+soDh);
						SoDongDh++;
						
						if(DH_SO_DONG.get(SmartId)==SoDongDh)
						{
							System.out.println("Tao don hang__"+"[So dong]"+SoDongDh);
							if(CheckInsertOrUpdate(SmartId)==0)
								this.CreateDonHang(dhBean, spList);
							else
								this.UpdateDonHang(dhBean, spList);
						}
					}
				}
		} catch (BiffException | IOException e)
		{
			System.out.println("Loi doc file" + e.getMessage());
			e.printStackTrace();
		}

}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		return cell.getContents();
	}

	private Hashtable<String, String> gethtbSanpham()
	{
		dbutils db = new dbutils();
		Hashtable<String, String> htb = new Hashtable<String, String>();
		ResultSet rs = db.get("select pk_seq,ma from sanpham");
		try
		{
			while (rs.next())
			{
				htb.put(rs.getString("ma"), rs.getString("pk_seq"));
			}
			rs.close();
		} catch (Exception e)
		{
			System.out.println("Nhung Loi Thong Thuong  san pham : " + e.toString());
		}
		db.shutDown();
		return htb;
	}

	private Hashtable<String, String> gethtbKho()
	{
		dbutils db = new dbutils();
		Hashtable<String, String> htb = new Hashtable<String, String>();
		ResultSet rs = db.get("select pk_seq,ma from KHOTT");
		try
		{
			while (rs.next())
			{
				htb.put(rs.getString("ma"), rs.getString("pk_seq"));
			}
			rs.close();
		} catch (Exception e)
		{
			System.out.println("Nhung Loi Thong Thuong  san pham : " + e.toString());
		}
		db.shutDown();
		return htb;
	}
	
	private Hashtable<String, String> getHtbGSBH()
	{
		dbutils db = new dbutils();
		Hashtable<String, String> htb = new Hashtable<String, String>();
		ResultSet rs = db.get("SELECT DDKD_FK,GSBH_FK from DDKD_GSBH");
		try
		{
			while (rs.next())
			{
				htb.put(rs.getString("DDKD_FK"), rs.getString("GSBH_FK"));
			}
			rs.close();
		} catch (Exception e)
		{
			System.out.println("Nhung Loi Thong Thuong : " + e.toString());
		}
		db.shutDown();
		return htb;
	}
	
	boolean CreateDonHang(IDonhang dhBean,List<ISanpham> spList)
	{
		String ngayTao=getDateTime();
		geso.dms.distributor.db.sql.dbutils db=new geso.dms.distributor.db.sql.dbutils();
		String query="";
		query="insert into DonHang(ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, vat, tonggiatri, chietkhau, ddkd_fk, gsbh_fk, khachhang_fk, npp_fk, kho_fk, kbh_fk, tinhtrang)" +
			" values('"+dhBean.getNgaygiaodich()+"',0, '"+ngayTao+"','"+ngayTao+"','"+dhBean.getUserId()+"','"+dhBean.getUserId()+"','"+dhBean.getVAT()+"','"+dhBean.getTongtiensauVAT()+"','"+dhBean.getTongChietKhau()+"','"+dhBean.getDdkdId()+"','"+dhBean.getGsbhId()+"','"+dhBean.getKhId()+"','"+dhBean.getNppId()+"','"+dhBean.getKhoId()+"',(SELECT KBH_FK FROM KHACHHANG WHERE PK_sEQ='"+dhBean.getKhId()+"'),0 )";
		try
		{
			db.getConnection().setAutoCommit(false);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("_______Error Code 312________"+query );
				return false;
			}
			query="SCOPE_IDENTITY ";
			ResultSet rs=db.get(query);
			if(rs!=null)
			{
				rs.next();
				dhBean.setId(rs.getString("1"));
				rs.close();
			}
			for(int m = 0; m < spList.size(); m++)
			{
				ISanpham sp = spList.get(m);
				query="insert donhang_sanpham(sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau,Scheme)values" +
					""+sp.getId()+",'"+dhBean.getId()+"','"+sp.getSoluong()+"','"+dhBean.getKhoId()+"','"+sp.getDongia()+"','"+sp.getChietkhau()+"','"+sp.getSoluong()+"','"+sp.getScheme()+"' ";
				if(!db.update(query))
				{
					System.out.println("_______Error Code 329________"+query );
					db.getConnection().rollback();
					return false;
				}
				query="update nhapp_kho set booked = booked +'"+sp.getSoluong()+"', available = available - '"+sp.getSoluong()+"' where sanpham_fk = @spId and npp_fk = '"+dhBean.getNppId()+"' and kho_fk = '"+dhBean.getKhoId()+"' and kbh_fk = (select kbh_fk from khachhang where pk_Seq='"+dhBean.getKhId()+"')";
				if(!db.update(query))
				{
					System.out.println("_______Error Code 335________"+query );
					db.getConnection().rollback();
					return false;
				}
			}
			db.getConnection().commit();
			db.getConnection().rollback();
		} catch (SQLException e)
		{
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				System.out.println("_______Error Code 348________"+e1.getMessage() );
				e1.printStackTrace();
			}
			System.out.println("_______Error Code 351________"+e.getMessage() );
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	boolean UpdateDonHang(IDonhang dhBean,List<ISanpham> spList)
	{
		String ngayTao=getDateTime();
		geso.dms.distributor.db.sql.dbutils db=new geso.dms.distributor.db.sql.dbutils();
		String query="";
		query="update donhang set ngaynhap = '"+dhBean.getNgaygiaodich()+"', ddkd_fk ='"+dhBean.getDdkdId()+"' , gsbh_fk ='"+dhBean.getGsbhId()+"', khachhang_fk ='"+dhBean.getKhId()+"', KHO_FK ='"+dhBean.getKhoId()+"', chietkhau ='"+dhBean.getTongChietKhau()+"',"+ 
			" ngaysua ='"+ngayTao+"',nguoisua ='"+dhBean.getUserId()+"',tonggiatri ='"+dhBean.getTongtiensauVAT()+"', vat ='"+dhBean.getVAT()+"',kbh_fk =(select kbh_fk from khachhang where pk_Seq='"+dhBean.getKhId()+"') where pk_seq ='"+dhBean.getId()+"'";
		try
		{
			db.getConnection().setAutoCommit(false);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("_______Error Code 362________"+query );
				return false;
			}
			query=
			"UPDATE NHAPP_KHO SET BOOKED=NHAPP_KHO.BOOKED-DH_SP.SOLUONG,AVAILABLE=AVAILABLE+DH_SP.SOLUONG "+
			"FROM "+ 
			"	NHAPP_KHO NHAPP_KHO INNER JOIN DONHANG_SANPHAM DH_SP ON DH_SP.KHO_FK=NHAPP_KHO.KHO_FK AND DH_SP.SANPHAM_FK=NHAPP_KHO.SANPHAM_FK "+ 
			"	INNER JOIN DONHANG DH ON DH.PK_SEQ=DH_SP.DONHANG_FK AND DH.NPP_FK=NHAPP_KHO.NPP_FK AND NHAPP_KHO.KBH_FK=DH.KBH_FK "+
			"WHERE DH_SP.DONHANG_FK='"+dhBean.getId()+"'" ; 
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("_______Error Code 372________"+query );
				return false;
			}
			query=" delete from donhang_sanpham where donhang_fk ='"+dhBean.getId()+"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("_______Error Code 380________"+query );
				return false;
			}
			for(int m = 0; m < spList.size(); m++)
			{
				ISanpham sp = spList.get(m);
				query="insert donhang_sanpham(sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau,Scheme)values" +
					""+sp.getId()+",'"+dhBean.getId()+"','"+sp.getSoluong()+"','"+dhBean.getKhoId()+"','"+sp.getDongia()+"','"+sp.getChietkhau()+"','"+sp.getSoluong()+"','"+sp.getScheme()+"' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					System.out.println("_______Error Code 391________"+query );
					return false;
				}
				query="update nhapp_kho set booked = booked +'"+sp.getSoluong()+"', available = available - '"+sp.getSoluong()+"' where sanpham_fk = @spId and npp_fk = '"+dhBean.getNppId()+"' and kho_fk = '"+dhBean.getKhoId()+"' and kbh_fk = (select kbh_fk from khachhang where pk_Seq='"+dhBean.getKhId()+"')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					System.out.println("_______Error Code 399________"+query );
					return false;
				}
			}
			db.getConnection().commit();
			db.getConnection().rollback();
		} catch (SQLException e)
		{
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				System.out.println("_______Error Code 412________"+e1.getMessage() );
				e1.printStackTrace();
			}
			System.out.println("_______Error Code 415________"+e.getMessage() );
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public int CheckInsertOrUpdate(String SmartId)
	{
		dbutils db=new dbutils();
		String query="SELECT COUNT(*) NUMB FROM DONHANG WHERE SMARTID='"+SmartId+"'";
		ResultSet rs=db.get(query);
		int count=-1;
		if(rs!=null)
		{
			try
			{
				while(rs.next())
					count =rs.getInt("numb");
				rs.close();
				return count;
			} catch (SQLException e)
			{
				e.printStackTrace();
				return -1;
			}
		}
		if(db!=null)
			db.shutDown();
		System.out.print("Cout"+count);
		return count;
	}
	
	
	public static void main(String[] arg)
	{
		/*Test2 p = new Test2();
		IDonhang obj = new Donhang("");
		p.readExcel("", obj);*/
		dbutils db = new dbutils();
		
		String mavung = "11";
		String query = "select distinct  case  \n" +  
				"when b.VUNG_FK  = 100030  then 11 \n" +  
				"when b.VUNG_FK = 100032 then 12 \n" +  
				"when b.VUNG_FK = 100031 then 13 \n" +  
				"when b.VUNG_FK = 100029 then 15 \n" +  
				"else 14 \n" +  
				"end as mavung,kh.pk_seq makh	 \n" +  
				" from KHUVUC_QUANHUYEN a inner join KHUVUC b on a.KHUVUC_FK = b.PK_SEQ "
				+ "inner join khachhang kh on kh.quanhuyen_fk = a.quanhuyen_fk \n";
		System.out.println("MaVung: "+query);
		ResultSet rs = db.get(query);
		try {
			while(rs.next())
			{
				mavung = rs.getString("mavung");
				query = "select isnull(MAX(cast(subString(isnull(SMARTID,'0'),3,LEN(SMARTID) ) as int))+1,'1') as ma from khachhang where SMARTID like '"+mavung+"%'";
				System.out.println("ma kh "+query);
				ResultSet rs1 = db.get(query);
				rs1.next();
				String makh = rs1.getString("ma");
				rs1.close();
				
				String chuoi = "";
				for (int i = 0; i < (5 - makh.length()); i++)
					chuoi += "0";
				String ma = mavung+chuoi + makh;
				query = "update khachhang set smartid = '"+ma+"' where pk_seq = "+rs.getString("makh");
				db.update(query);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	
}
