package geso.dms.center.servlets.thongbao;

import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThongbaoAjax extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ThongbaoAjax() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
		/*HttpSession session = request.getSession();
		String userId=(String)session.getAttribute("userId");
		String query ="update NHANVIEN set ISLOGIN='0' where PK_SEQ='"+userId+"'";
		dbutils db=new dbutils();
		db.update(query);
		db.shutDown();
		session.invalidate();*/
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String task = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if(task==null)
			task="";
	
		if(task.equals("download"))
		{
			
			dbutils db=new dbutils();
		    String id= geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
		    String query = "select filename from thongbao where pk_seq='"+id+"'";
			System.out.println("cau select: "+query);
			ResultSet rs = db.get(query);
			String filename="";
			try {
				rs.next();
				filename = rs.getString("filename");
			} catch (SQLException e) {
				
			}
			String[] spli=new String[3];
  			spli= filename.split(",");
		    FileInputStream fileToDownload = new FileInputStream("C:\\java-tomcat\\dinhkem\\"+filename);
		    ServletOutputStream output = response.getOutputStream();
		    response.setContentType("application/octet-stream");
		    String tenfile=spli[0]+spli[2];
		    //System.out.println(tenfile);
		    response.setHeader("Content-Disposition","attachment; filename=\"" + tenfile + "\"");
		    response.setContentLength(fileToDownload.available());
		    int c;
		    while ((c = fileToDownload.read()) != -1)
		    {
		    output.write(c);
		    }
		    output.flush();
		    output.close();
		    fileToDownload.close();
		}
		else 
		{
		    PrintWriter out = response.getWriter();
			Utility util = new Utility();
			String q = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("q")));
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			
				
			
			if(q != null)
			{
				
				dbutils db = new dbutils();
				String query ="";
				String chuoi2="";
				try
				{
					query ="select count(*) sodong from  nhanvien where ( gsbh_fk is not null or BM_FK is not null  ) and pk_seq = " +id;
					System.out.println("Thong Bao: "+query);
					ResultSet rsCheck = db.get(query);
					rsCheck.next();
					int isGSBH = rsCheck.getInt("sodong");
					if(isGSBH >0)
					{
						//
					//	System.out.println("aaaa=" +;
						if(getDateTime().compareTo("2015-07-31") <0  )// demo cho hồng mai tạm
						{
							query =  "\n DECLARE @denngay varchar(10)='"+getDateTime()+"'  -- " + 
							 "\n declare @tungay varchar(10)= convert(varchar(10),DATEADD(dd, -90, @denngay) ,120) -- " + 
							 "\n select case when tonkho < dh.soluong*TONKHOTOITHIEU then 1 else 0 end loai , npp.PK_SEQ nppId ,npp.TEN as npp , sp.ten as sanpham,dh.soluong tbNgay,tonkho,npp.TONKHOTOITHIEU ,npp.TonKhoAnToan ,npp.TONKHOTOITHIEU * dh.soluong as kqToiThieu ,npp.TonKhoAnToan*dh.soluong as kqAnToan -- " + 
							 "\n from NHAPHANPHOI npp -- " + 
							 "\n inner join PHAMVIHOATDONG pvhd on pvhd.npp_fk = npp.pk_seq and pvhd.nhanvien_fk = "+id+" -- " + 
							 "\n inner join TrungBinhBanNgay dh on dh.npp_fk = npp.PK_SEQ and dh.tungay <=@denngay and dh.denngay >=@denngay   -- " + 
							 "\n inner join SANPHAM sp on dh.SANPHAM_FK = sp.PK_SEQ -- " + 
							 "\n left join  -- " + 
							 "\n ( -- " + 
							 "\n 	select NPP_FK,sanpham_fk, SUM(soluong)tonkho  -- " + 
							 "\n 	from NHAPP_KHO  -- " + 
							 "\n 	group by NPP_FK,sanpham_fk -- " + 
							 "\n )kho on kho.SANPHAM_FK = dh.SANPHAM_FK and kho.NPP_FK =dh.NPP_FK -- " +
							 "\n where npp.loainpp <> 0 and  tonkho < dh.soluong*TONKHOTOITHIEU or tonkho > npp.TonKhoAnToan*dh.soluong  " +
							 "\n Union  " + 
							 "\n select case when tonkho < dh.soluong*TONKHOTOITHIEU then 1 else 0 end loai ,npp.PK_SEQ nppId,npp.TEN as npp , sp.ten as sanpham,dh.soluong tbNgay,tonkho,npp.TONKHOTOITHIEU,npp.TonKhoAnToan,npp.TONKHOTOITHIEU * dh.soluong as kqToiThieu,npp.TonKhoAnToan*dh.soluong as kqAnToan --" +
							 "\n from NHAPHANPHOI npp -- " + 
							 "\n inner join PHAMVIHOATDONG pvhd on pvhd.npp_fk = npp.pk_seq and pvhd.nhanvien_fk = "+id+" -- " +
							 "\n inner  join -- " + 
							 "\n ( -- " + 
							 "\n 	select NPP_FK,SANPHAM_FK,SUM(dhsp.soluong)/90  as soluong -- " + 
							 "\n 	from DONHANG dh -- " + 
							 "\n 	inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK -- " + 
							 "\n 	where dh.TRANGTHAI = 1 and dh.NGAYNHAP>=@tungay and dh.NGAYNHAP <=@denngay -- " + 
							 "\n 	group by npp_fk,SANPHAM_FK -- " + 
							 "\n )dh on dh.npp_fk = npp.pk_seq  -- " + 
							 "\n inner join SANPHAM sp on dh.SANPHAM_FK = sp.PK_SEQ -- " + 
							 "\n left join  -- " + 
							 "\n ( -- " + 
							 "\n 	select NPP_FK,sanpham_fk, SUM(soluong)tonkho  -- " + 
							 "\n 	from NHAPP_KHO  -- " + 
							 "\n 	group by NPP_FK,sanpham_fk -- " + 
							 "\n )kho on kho.SANPHAM_FK = dh.SANPHAM_FK and kho.NPP_FK =dh.NPP_FK -- " +
							 "\n where npp.pk_seq != 106306 and  npp.loainpp <> 0 and tonkho < dh.soluong*TONKHOTOITHIEU or tonkho > npp.TonKhoAnToan*dh.soluong "  +
							 "\n order by npp desc, loai desc" ;
						}
						else
						{
							query =  "\n DECLARE @denngay varchar(10)='"+getDateTime()+"'  -- " + 
							 "\n declare @tungay varchar(10)= convert(varchar(10),DATEADD(dd, -90, @denngay) ,120) -- " + 
							 "\n select case when tonkho < dh.soluong*TONKHOTOITHIEU then 1 else 0 end loai ,npp.PK_SEQ nppId,npp.TEN as npp , sp.ten as sanpham,dh.soluong tbNgay,tonkho,npp.TONKHOTOITHIEU,npp.TonKhoAnToan,npp.TONKHOTOITHIEU * dh.soluong as kqToiThieu,npp.TonKhoAnToan*dh.soluong as kqAnToan -- " + 
							 "\n from NHAPHANPHOI npp -- " + 
							 "\n inner join PHAMVIHOATDONG pvhd on pvhd.npp_fk = npp.pk_seq and pvhd.nhanvien_fk = "+id+" -- " +
							 "\n inner  join -- " + 
							 "\n ( -- " + 
							 "\n 	select NPP_FK,SANPHAM_FK,SUM(dhsp.soluong)/90  as soluong -- " + 
							 "\n 	from DONHANG dh -- " + 
							 "\n 	inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK -- " + 
							 "\n 	where dh.TRANGTHAI = 1 and dh.NGAYNHAP>=@tungay and dh.NGAYNHAP <=@denngay -- " + 
							 "\n 	group by npp_fk,SANPHAM_FK -- " + 
							 "\n )dh on dh.npp_fk = npp.pk_seq  -- " + 
							 "\n inner join SANPHAM sp on dh.SANPHAM_FK = sp.PK_SEQ -- " + 
							 "\n left join  -- " + 
							 "\n ( -- " + 
							 "\n 	select NPP_FK,sanpham_fk, SUM(soluong)tonkho  -- " + 
							 "\n 	from NHAPP_KHO  -- " + 
							 "\n 	group by NPP_FK,sanpham_fk -- " + 
							 "\n )kho on kho.SANPHAM_FK = dh.SANPHAM_FK and kho.NPP_FK =dh.NPP_FK -- " +
							 "\n where npp.loainpp <> 0 and tonkho < dh.soluong*TONKHOTOITHIEU or tonkho > npp.TonKhoAnToan*dh.soluong "  +
							 "\n order by npp desc, loai desc " ;
						}
						System.out.println("query = " + query);
						ResultSet rs2 = db.get(query);
						int i =0;
						if(rs2 != null)
						while(rs2.next())
						{
							
							if(i==0)
							{
								chuoi2 = "<p align=\"center\" style=' color: red; font-weight: bold;' >Cảnh báo tồn kho</p>" +
										 "<p  align=\"center\"  style='color: black; font-size: 15px' >Màu đỏ: dưới mức tối thiểu; Màu xanh: trên mức tối đa</p> " ;
								chuoi2 += "<div align=\"center\"  style=\"height:200px; overflow: auto;\" >";
								chuoi2 +="<TABLE  width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"4\">" +
										 "<TR class=\"tbheader\">" +
										 " <TH width=\"30%\">Nhà phân phối</TH> " +
										 " <TH width=\"30%\">Sản phẩm</TH> " +
										 " <TH width=\"10%\">Trung bình bán/Ngày</TH> " +
										 " <TH width=\"10%\">Tồn kho tối thiểu</TH> " +
										 " <TH width=\"10%\">Tồn kho an toàn</TH> " +
										 " <TH width=\"10%\">Tồn hiện tại</TH> " +
										 "</TR>";
								
							}
							String kqTT ="",kqAT="";
							
							if(rs2.getString("loai").equals("1"))
							{
								kqTT = rs2.getString("kqToiThieu");
								kqAT = "";
							}
							else
							{
								kqTT = "";
								kqAT = rs2.getString("kqAnToan");
							}
								
							chuoi2 +="<TR>" +
										 "<td><input type=\"text\" value=\""+rs2.getString("npp")+"\" style=\"width: 100%;\" readonly> </td>" +
										 (rs2.getString("loai").equals("1") ? 
											 "<td><input type=\"text\" value=\""+rs2.getString("sanpham")+"\" style=\"width: 100%; color:red;\" readonly> </td>"	 
										 :
											 "<td><input type=\"text\" value=\""+rs2.getString("sanpham")+"\" style=\" width: 100%;color:blue;\" readonly> </td>"
										 ) +
										 "<td><input type=\"text\" value=\""+rs2.getString("tbNgay")+"\" style=\"width: 100%;\" readonly> </td>" +
										 "<td><input type=\"text\" value=\""+kqTT+"\" style=\"width: 100%;\" readonly> </td>" +
										 "<td><input type=\"text\" value=\""+kqAT+"\" style=\"width: 100%;\" readonly> </td>"+
										 
										 "<td><input type=\"text\" value=\""+rs2.getString("tonkho")+"\" style=\"width: 100%;\" readonly> </td>"+
									 "</TR>";
							//chuoi2 += "<li><p style=\"color: black;font-size:10px\">"+rs2.getString("sanpham") +"</p></li> ";
							i++;
						}
						if(chuoi2.trim().length() >0)
							//chuoi2 += "</div></ul>"; 
							chuoi2 += "</TABLE></div>";
					}
					
				
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				
				query =  "select count(pk_seq) as soluong from thongbao a " +
						   		"where  exists (select top(1)* from THONGBAO_NHANVIEN where nhanvien_fk = 100368 and trangthai = 0 and thongbao_fk = a.pk_seq)";
				System.out.println("Dem so thong bao: "+query);
				ResultSet rs = db.get(query);
				int soTB = 0;
				try 
				{
					if(rs.next())
					{
						soTB = rs.getInt("soluong");
					}
					rs.close();
				} 
				catch(Exception e) 
				{
					e.printStackTrace();
				}
				String chuoi = "";

				
				if(chuoi2.trim().length() >0)
					chuoi = chuoi2;
				//out.write("<p style=' color: red; font-weight: bold;' >Hộp thư đến (" + soTB +  ")</p>");
				chuoi += "<div align=\"center\"><p align=\"center\" style=' color: red; font-weight: bold;' >Hộp thư mới chưa đọc (" + soTB +  ")</p>";

				query = "select top 10 pk_seq,tieude from THONGBAO a  " +
						"where  exists (select top(1)* from THONGBAO_NHANVIEN where nhanvien_fk = 100368 and trangthai = 0 and thongbao_fk = a.pk_seq) order by ngaybatdau desc";
				System.out.println("thong bao NEW: " + query);

				rs = db.get(query);
				if(rs != null)
				{
					chuoi += "<ul  align=\"center\"  id=\"thongbao\" style=\"display:none\"><li align=\"center\"><p  align=\"center\" style=\"color:red \">Các thư đến chưa đọc:</p></li>";
					try 
					{
						while(rs.next())
						{
							chuoi += "<li><A href=\"../../ThongbaoUpdateSvl?task=capnhatnv&id="+ rs.getString("PK_SEQ") +"\"><strong>"+rs.getString("tieude") +"</A></strong></li> ";
						}
						rs.close();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					finally
					{ 
						if(db!=null)db.shutDown();
					}
					chuoi += "</ul></div>";
				}
				
				out.write(chuoi);
				
			}
			
		}
	}
}
