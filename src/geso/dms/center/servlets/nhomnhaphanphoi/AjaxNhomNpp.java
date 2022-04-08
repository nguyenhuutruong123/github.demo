package geso.dms.center.servlets.nhomnhaphanphoi;

import geso.dms.center.beans.nhomnhaphanphoi.INhomNhaPhanPhoi;
import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AjaxNhomNpp")
public class AjaxNhomNpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AjaxNhomNpp() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		String kenhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"));
		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId"));
		String tinhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tinhId"));
		String qhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("qhId"));
		String nhomId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomId"));
		String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
		dbutils db = new dbutils();
		
		HttpSession session = request.getSession();				
		INhomNhaPhanPhoi nnppBean = (INhomNhaPhanPhoi)session.getAttribute("nnppBean");
		
		System.out.println("[Ajax Nhom]");
		String str="";
		if(id.equals("tbNpp")) 
		{
			String query = 
					"select " + 
					"	isnull(kbh.TEN,N'Chưa xác định') as Kenh, " + 
					"	isnull(v.TEN,N'Chưa xác định') as Vung, " + 
					"	isnull(kv.TEN,N'Chưa xác định') as KhuVuc, " + 
					"	isnull(tt.TEN,N'Chưa xác định') as Tinh, "+ 
					"	isnull(qh.TEN,N'Chưa xác định') as QuanHuyen, " + 
					"	npp.PK_SEQ,npp.MA,npp.TEN,npp.DIACHI " + 
					"from NHAPHANPHOI npp " + 
					"left join KHUVUC kv on " + 
					"kv.PK_SEQ=npp.KHUVUC_FK " + 
					"left join VUNG v on v.PK_SEQ=kv.VUNG_FK "+ 
					"left join NHAPP_KBH nppK on nppK.NPP_FK=npp.PK_SEQ " + 
					"left join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_FK " + 
					"left join TINHTHANH tt on tt.PK_SEQ=qh.TINHTHANH_FK " + 
					"left join KENHBANHANG kbh on kbh.PK_SEQ=nppK.KBH_FK where 1=1  ";
			query+=" and npp.pk_seq not in (select npp_fk from nhomnpp_npp   ";
			if(nhomId.length()>0)
				query+=" where  nhomnpp_fk!='"+nhomId+"'  ";
			query+="  )  ";
			if(kenhId.length()>0)
			{
				kenhId = kenhId.substring(0, kenhId.length() - 1);
				query += " and npp.pk_seq in( select npp_fk from NHAPP_KBH where kbh_fk in(" +kenhId + ") )";
			}
			if(vungId.length()>0)
			{
				vungId = vungId.substring(0, vungId.length() - 1);
				query += " and v.pk_seq in( "+vungId+" )";
			}
			if(kvId.length()>0)
			{
				kvId = kvId.substring(0, kvId.length() - 1);
				query += " and npp.khuvuc_fk in( "+kvId+" )";
			}
			if(tinhId.length()>0)
			{
				tinhId = tinhId.substring(0, tinhId.length() - 1);
				query += " and tt.pk_seq in( "+tinhId+" )";
			}
			if(qhId.length()>0)
			{
				qhId = qhId.substring(0, qhId.length() - 1);
				query += " and qh.pk_seq in( "+qhId+" )";
			}
			System.out.println("[Ajax Nhom NPP query] : "+query);
			System.out.println(" Ajax Chon: '" + nnppBean.getNppId() + "' " );
			
			ResultSet rs = db.get(query);
			str+="<TABLE width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"4\"  > \n"+							
			"<TR class=\"tbheader\"> \n"+
			"<TH width=\"10%\">Kênh bán hàng</TH> \n"+
			"<TH width=\"10%\">Vùng</TH> \n"+
			"<TH width=\"10%\">Khu vực</TH> \n"+
			"<TH width=\"10%\">Tỉnh thành</TH> \n"+
			//"<TH width=\"10%\">Quận huyện</TH> \n"+
			"<TH width=\"10%\">Mã nhà phân phối</TH> \n"+
			"<TH width=\"20%\">Nhà phân phối</TH> \n"+
			"<TH width=\"30%\">Địa chỉ</TH> \n"+
			"<TH width=\"15%\">Chọn"+
			"<input type=\"checkbox\" name=\"chon\" onClick=\"checkedAll(document.nspForm.nppId)\"> " +
			"</TH> ";
			try 
			{
				int i = 0;
				while(rs.next())
				{
					if (i % 2 == 0)
						str += "<TR class= \"tblightrow\" > ";
					else
						str +=	"<TR class= \"tbdarkrow\" > ";
					str +="<TD align=\"left\" class=\"textfont\">"+ rs.getString("kenh")+"</TD>";
					str+="<TD align=\"left\" class=\"textfont\">"+rs.getString("vung") +"</TD>";	
					str+="<TD align=\"left\" class=\"textfont\">"+ rs.getString("khuvuc") +"</TD>";
					str+="<TD align=\"left\" class=\"textfont\">"+ rs.getString("tinh")+"</TD>";
					//str+="<TD align=\"left\" class=\"textfont\">"+ rs.getString("quanhuyen") +"</TD>";
					str+="<TD align=\"left\" class=\"textfont\">"+ rs.getString("ma") +"</TD>";
					str+="<TD align=\"center\"><div align=\"left\">"+ rs.getString("ten")+"</div></TD>";
					str+="<TD align=\"center\"><div align=\"left\">"+ rs.getString("diachi")+"</div></TD>";
					
					//str+="<TD align=\"center\"><input type=\"checkbox\" name=\"nppId\" value='"+rs.getString("pk_seq") +"'></TD> </tr>"; 
					if (nnppBean.getNppId().indexOf( rs.getString("pk_seq") )>=0  ) {						
						str+="<TD align=\"center\"><input type=\"checkbox\" name=\"nppId\" value='"+rs.getString("pk_seq") +"' checked></TD> </tr>";
					}else{ 
						str+="<TD align=\"center\"><input type=\"checkbox\" name=\"nppId\" value='"+rs.getString("pk_seq") +"'></TD> </tr>";
					}
					
					i++;
				}
				rs.close();
				str += "<tr><td class=\"plainlabel\" colspan=\"9\">&nbsp;</td></tr>";
				System.out.println("Ket qua la: " + str + "\n");
			} 
			catch(Exception e) {}
			str += "</table>";
			out.write(str);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
	}

}
