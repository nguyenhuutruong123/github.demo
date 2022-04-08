
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="geso.dms.center.beans.chitieunhaphanphoi.imp.ChiTieuNhaphanphoi"%>
<%@page import="geso.dms.center.beans.chitieunhaphanphoi.IChiTieuNhaphanphoi"%>
<%@page import="geso.dms.center.beans.chitieunhaphanphoi.imp.CTNhaphanphoi"%>
<%@page import="geso.dms.center.beans.chitieunhaphanphoi.ICTNhaphanphoi"%>
<%@page import="geso.dms.center.beans.chitieunhaphanphoi.imp.CTNhaphanphoi_NSP"%>
<%@page import="geso.dms.center.beans.chitieunhaphanphoi.ICTNhaphanphoi_NSP"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%NumberFormat formatter = new DecimalFormat("#,###,##0.##");%>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	IChiTieuNhaphanphoi objbean=(ChiTieuNhaphanphoi)session.getAttribute("obj");
	Utility util = (Utility) session.getAttribute("util");
 	List<ICTNhaphanphoi> nvList = objbean.getListCTNhaphanphoi();
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("ChiTieuNhaphanphoiSvl","",nnId);	
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">


<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>
<SCRIPT language="JavaScript" type="text/javascript"> 
function submitform()
{
    document.forms["ChiTieuTTForm"].submit();
}

function xuatexcel()
{
	document.forms['ChiTieuTTForm'].action.value= 'excel';
	document.forms['ChiTieuTTForm'].submit();
}


function save(){
	
	 document.forms["ChiTieuTTForm"].dataerror.value=" ";
 	 var thang=document.forms["ChiTieuTTForm"].thang.value;
  	 var nam=document.forms["ChiTieuTTForm"].nam.value; 
  	 
  if(nam==0){
	  document.forms["ChiTieuTTForm"].dataerror.value="Chọn năm cần đạt chỉ tiêu ";
	  return;
  }
  if(thang==0){
	  document.forms["ChiTieuTTForm"].dataerror.value="Chọn tháng cần đạt chỉ tiêu ";
	  return;
	  }
 
	  
  //kiem tra xem thang nam dat chi tieu co hop le hay khong
 	 var d=new Date();
	 var year_= d.getFullYear();
	 var month_=d.getMonth()+1;	 
		/*  if(nam<year_){
			 
			  document.forms["ChiTieuTTForm"].dataerror.value="Thời gian đặt chỉ tiêu không hợp lý. Phải đặt thời gian chỉ tiêu lớn hơn thời gian hiện thời ";
				return; 
		 }else if(nam==year_ && thang<month_){
			  document.forms["ChiTieuTTForm"].dataerror.value="Thời gian đặt chỉ tiêu không hợp lý. Phải đặt thời gian chỉ tiêu lớn hơn thời gian hiện thời";
				return; 
		 }  */
 
		if(document.forms["ChiTieuTTForm"].filename.value == "")
	    {   
		   document.forms["ChiTieuTTForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
	    }
		else{
		 	document.forms["ChiTieuTTForm"].setAttribute('enctype', "multipart/form-data", 0);
		 	document.forms["ChiTieuTTForm"].submit();	
	    }


}

</SCRIPT>
	<link media="screen" rel="stylesheet" href="../css/colorbox.css">
	    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	<%   for(int k = 0; k < nvList.size(); k++) { %>
        	          
	            $(".apchoNhanVien<%= k %>").colorbox({width:"90%", inline:true, href:"#apchoNhanVien<%= k %>"});
	         
	            $("#click").click(function(){ 
	                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
	                return false;
	            });
	            
            <% } %>
        });
    </script>
<script type="text/javascript" src="../scripts/ajax.js"></script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value='<%=objbean.getID()==null?"":objbean.getID()%>'>
<input type="hidden" name="trangthai" value='<%=objbean.getTrangThai()%>'>
<input type="hidden" name="tenform" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">
							 <%
							 if(!objbean.getDisplay().equals("1")){
								 if(objbean.getID()==null)
								 {%>
									 &nbsp; Quản lý chỉ tiêu &gt; Khai báo &gt; Upload chỉ tiêu nhà phân phối > Tạo mới
								 <%}
								 else
								 {%>
									 &nbsp; Quản lý chỉ tiêu &gt; Khai báo &gt; Upload chỉ tiêu nhà phân phối > Cập nhật
								 <%}
							 }
							 else{%>
								 &nbsp; Quản lý chỉ tiêu &gt; Khai báo &gt; Upload chỉ tiêu nhà phân phối > Hiển thị
							 <%}
							 %>
							 </TD>
							  
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ChiTieuNhaphanphoiSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <%if(!objbean.getDisplay().equals("1")){ %>
						    	<TD width="30" align="left" ><A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<%} %>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=objbean.getMessage()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin chỉ tiêu</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						
						<%-- <TR>
							<TD class="plainlabel" colspan=2>
												<%if(objbean.getLoai().equals("1")){ %>									  	 
									  	 			<INPUT TYPE="radio" NAME="loai" value="1" checked onChange="submitform();" >SR
									  	 			<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitform();">SS
									  	 			<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitform();">ASM
									  	 			
									  	 		<%}else{
									  	 				if(objbean.getLoai().equals("2")){ %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitform();">SR
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" checked onChange="submitform();">SS									  	 		
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitform();">ASM
									  	 					
									  	 			  <%}else{ 
									  	 				 if(objbean.getLoai().equals("3")){ %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitform();">SR
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitform();">SS
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" checked onChange="submitform();">ASM
									  	 				
										  	 			<%}else{%>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitform;">SR
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitform;">SS
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitform;">ASM
									  	 				
										  	 			
									  	 		  <%}}}%>
										  	 </TD>
						
						
						</TR> --%>
						
						
							<TR>
								<TD width="20%" class="plainlabel" ><%=ChuyenNgu.get("Tháng",nnId) %> <FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel">
									<select name="thang" style="width :50px" ">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  									  if(k==objbean.getThang()){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel"><%=ChuyenNgu.get("Năm",nnId) %></TD>
						  	  	<TD class="plainlabel">
										<select name="nam"  style="width :50px" ">
									<option value=0> </option>  
									<%
									  Calendar c2 = Calendar.getInstance();
  										int t=c2.get(Calendar.YEAR) +6;
  										int n;
  										for(n=2008;n<t;n++){
  										if(n==objbean.getNam()){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
									</select>
						  	  	</TD>
						  </TR>
						  
				  		  
						  	<TR>
						  	  	<TD class="plainlabel"><%=ChuyenNgu.get("Diễn giải",nnId) %></TD>
						  	  <TD class="plainlabel" >
						  
						  	  <input  type="text"  name="diengiai" value="<%=objbean.getDienGiai()%>" > 
						  	  		
						  	  	</TD>
						  	</TR>
						  	
						  	<TR>
						  	  	<TD class="plainlabel"><%=ChuyenNgu.get("Số ngày làm việc",nnId) %></TD>
						  	  <TD class="plainlabel" >
						  
						  	  <input  type="text"  name="songaylamviec" value="<%=objbean.getSoNgayLamViec()%>" > 
						  	  		
						  	  	</TD>
						  	</TR>
						  						  	
						  	<tr class="plainlabel">
						  
						  	  <td >
						  	  <INPUT type="file" name="filename" size="40" value=''> 
						  	  </td> 
						  	  
						  	  <td>
						  	  <a class="button2" href="javascript:xuatexcel()">
											<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất Excel",nnId) %> </a>
						  	  </td>
						  	</tr>	
						  	
						  	
						 
						  	
						  	<tr class ="planlable">
							<td colspan="2">
							
							  <TABLE width="100%" >
                                <TR class="tbheader">
                                    <TH width="10%"><%=ChuyenNgu.get("Mã NPP",nnId) %></TH> 
                                    <TH width="10%"><%=ChuyenNgu.get("Tên Nhà phân phối",nnId) %></TH>
                                    <TH width="10%"><%=ChuyenNgu.get("Doanh số bán ra",nnId) %></TH>
                                    <TH width="10%"><%=ChuyenNgu.get("Số lượng bán ra",nnId) %></TH>
                                     
                                    <TH width="10%"><%=ChuyenNgu.get("Doanh số mua vào",nnId) %></TH>
                                    <TH width="10%"><%=ChuyenNgu.get("Số lượng mua vào",nnId) %></TH>
                                    
                                    
                                    <TH width="10%"><%=ChuyenNgu.get("Tổng số đơn hàng",nnId) %></TH>
                                    
                                    <TH width="10%"><%=ChuyenNgu.get("Tỷ lệ GH thành công(%)",nnId) %></TH>
                                    <TH width="10%"><%=ChuyenNgu.get("Thời gian giao hàng TB (Giờ)",nnId) %></TH>
                                    
                                                                    
                                    <TH width="10%" align="center">&nbsp;<%=ChuyenNgu.get("Chỉ tiêu nhóm",nnId) %></TH>
                                </TR>
                                <% 

                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    
                                    for(int i = 0; i < nvList.size();i++){
                                    	ICTNhaphanphoi nv = nvList.get(i);
                                        if (i % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%=nv.getNppId()%></div></TD>                                   
                                                <TD><div align="center"><%=nv.getTen()%></div></TD>
                                                <TD><div align="center"><%=formatter.format(nv.getDoanhSoBanRa())%></div></TD>
                                                  <TD><div align="center"><%=formatter.format(nv.getSoLuongBanRa())%></div></TD>
                                                  <TD><div align="center"><%=formatter.format(nv.getDoanhSoMuaVao())%></div></TD>
                                                   <TD><div align="center"><%=formatter.format(nv.getSoLuongMuaVao())%></div></TD>
                                                	
                                                	<TD><div align="center"><%=formatter.format(nv.getSoDonHang())%></div></TD>
                                                   <TD><div align="center"><%=formatter.format(nv.getTyLeGiaoHang())%></div></TD>
                                                	<TD><div align="center"><%=formatter.format(nv.getThoiGianGiaoHang())%></div></TD>
                                                	<td>
                        			
                        			<a class="apchoNhanVien<%= i %>" href="#">
	           	 							&nbsp; <img alt="Chỉ tiêu nhóm " src="../images/vitriluu.png"></a>
		           	 				<div style='display:none'>
				                        <div id='apchoNhanVien<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Chỉ tiêu nhóm sản phẩm</h4>
											<div style="max-height: 400px">
												<table width="800px" align="center" cellpadding="0" cellspacing="1" id="nvTable<%=i%>">
							                        <tr >
							                        	<th width="200px" style="font-size: 12px"><%=ChuyenNgu.get("Loại",nnId) %></th>
							                            <th width="200px" style="font-size: 12px"><%=ChuyenNgu.get("Mã Nhóm sản phẩm",nnId) %></th>
							                            <th width="200px" style="font-size: 12px"><%=ChuyenNgu.get("Tên nhóm sản phẩm",nnId) %> </th>
							                            <th width="200px" style="font-size: 12px"><%=ChuyenNgu.get("Chỉ tiêu",nnId) %></th>
							                            
							                        </tr>
							                        
							                        <% 
							                        List<ICTNhaphanphoi_NSP> nvListDetail = nv.getCtNspList(); 
							                        for(int ii = 0; ii < nvListDetail.size(); ii++ ) 
							                        	{ 
							                        	ICTNhaphanphoi_NSP nvDetail = nvListDetail.get(ii);
							                        		%> 
							                        	
							                        	<Tr>                        		
							                        			<TD><div align="center"><%=nvDetail.getLoai()%></div></TD>
							                        			<TD><div align="center"><%=nvDetail.getNsp_fk()%></div></TD>
							                        			<TD><div align="center"><%=nvDetail.getTennsp()%></div></TD>
							                        			<TD><div align="center"><%=formatter.format(nvDetail.getChitieu())%></div></TD>
							                        			
							                        									                        		
							                        	</Tr>
							                        	
							                        <% }  %>
							                        
							                       <tr>
							                       		<td colspan="3">&nbsp;</td>
							                       </tr>
							                    </table>
						                    </div>
											
										</div>
					                </div>
                        			
                        		</td>
                                               		
                                        </TR>
                                        <%  } %>
                                    </TABLE>
							
							</td>
							
							</tr>

						  	
						  	
						  					  	
						  	
						</TABLE>
													
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
<script type="text/javascript">

//lamtrontien_phandu();
</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>