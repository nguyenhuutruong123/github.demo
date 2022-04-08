<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.mokhoaso.IMokhoaso" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IMokhoaso mksBean = (IMokhoaso)session.getAttribute("mksBean"); %>
<% ResultSet vung = (ResultSet)mksBean.getVung(); %>
<% ResultSet kv = (ResultSet)mksBean.getKhuvuc(); %>
<% ResultSet kenh = (ResultSet)mksBean.getKenh(); %>
<% ResultSet npp = (ResultSet)mksBean.getNpp(); %>

<% ResultSet nppRs = (ResultSet)mksBean.getNppRs() ;

	int[] quyen = new  int[6];
	quyen = util.Getquyen("MokhoasoSvl","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("MokhoasoSvl","",nnId);	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
	document.forms['mksForm'].submit();
}

function checkedAll() {
	
	var nppChon = document.getElementsByName("nppChon");
	var chonAll =  document.getElementById('chonAll').checked;
	for (var i =0; i < nppChon.length; i++) 
 	{
	 	var cb = nppChon.item(i) ;
		if (chonAll == false){
			cb.checked = false;
		}else{
			cb.checked = true;
		}
 	}
}

function Mokhoaso()
{
	 var nppId = document.getElementById('nppId');
	 if (nppId.value == '') {
		 alert('Vui lòng chọn chi nhánh/nhà phân phối!');
		 return;
	 }
	 
	 ngay = document.getElementById('ngay');
	 if (ngay.value == '') {
		 alert('Vui lòng chọn ngày khóa sổ!');
		 return;
	 }
	 
	document.forms['mksForm'].action.value= 'open';
	 var r = confirm("Bạn chắc chắn muốn mở khóa sổ nhà phân phối đã chọn ?");
	 if (r == false) {		 
	     return;
	 }
	
	document.forms['mksForm'].submit();
}

function KhoaSo()
{
	 var nppId = document.getElementById('nppId');
	 if (nppId.value == '') {
		 alert('Vui lòng chọn nhà phân phối!');
		 return;
	 }
	 ngay = document.getElementById('ngay');
	 if (ngay.value == '') {
		 alert('Vui lòng chọn ngày mở khóa sổ!');
		 return;
	 }
	document.forms['mksForm'].action.value= 'close';
	 var r = confirm("Bạn chắc chắn muốn mở khóa sổ nhà phân phối đã chọn ? Lưu ý  các nghiệp vụ liên quan sẽ được hoàn thành, hoặc dời ngày nghiệp vụ ");
	 if (r == false) {		 
	     return;
	 }
	
	document.forms['mksForm'].submit();
	
}

function KhoaSoThang()
{
	document.forms['mksForm'].action.value= 'KhoaSoThang';
	 var r = confirm("Bạn chắc chắn muốn khóa sổ tháng các npp này??? ");
	 if (r == false) {		 
	     return;
	 }
	document.forms['mksForm'].submit();
	
}

function khoasotudong()
{
	document.forms['mksForm'].action.value= 'khoasotudong';
	 var r = confirm("Bạn chắc chắn muốn chọn khóa sổ tự động các sổ tháng các npp này??? ");
	 if (r == false) {		 
	     return;
	 }
	document.forms['mksForm'].submit();
	
}
function ngung_khoasotudong()
{
	document.forms['mksForm'].action.value= 'ngung_khoasotudong';
	 var r = confirm("Bạn chắc chắn muốn chọn khóa sổ tự động các sổ tháng các npp này??? ");
	 if (r == false) {		 
	     return;
	 }
	document.forms['mksForm'].submit();
	
}




</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2(); 
    	});
    </script>	


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="mksForm" method="post" action="../../MokhoasoSvl" >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action" value="0">
<input type="hidden" name="userId" value='<%= userId %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation"><%=" "+url %> </TD> 
							 <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle"><%=ChuyenNgu.get("Thông báo",nnId) %> </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width :100%" rows="1"><%= mksBean.getMsg() %></textarea>

						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%" border = '0'>
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black"><%=ChuyenNgu.get("Điều kiện lọc",nnId) %></LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
						    	<TD class="plainlabel" style="width:15%"><B></b><%=ChuyenNgu.get("Vùng",nnId) %> </TD>
							    <TD class="plainlabel"><SELECT name="vungId" onChange = "submitform();">
								    <option value=""></option>
								      <% try{
								    	  	if(vung != null){
								    	  		while(vung.next()){ 
								    	  			System.out.println(vung.getString("TEN"));
								    				if(vung.getString("VUNGID").equals(mksBean.getVungId())){ %>
								      				<option value='<%=vung.getString("VUNGID")%>' selected><%=vung.getString("TEN") %></option>
								      			  <%}else{ %>
								     				<option value='<%=vung.getString("VUNGID")%>'><%=vung.getString("TEN") %></option>
								     		<%		}
								    				
								    	  		}								    			
								    	  	}
								    	  }catch(java.sql.SQLException e){} 
								      
								     		%>	  
                        				</SELECT>			
                        		</TD>
							    
							</TR>  
							<TR>  
							    <TD class="plainlabel"><B></B><%=ChuyenNgu.get("Khu vực",nnId) %> </TD>
							    <TD class="plainlabel"><SELECT name="kvId" onChange = "submitform();">
								    <option value=""></option>
								      <% try{
								    	  	if(kv != null){
								    	  		while(kv.next()){ 
								    				if(kv.getString("KVID").equals(mksBean.getKhuvucId())){ %>
								      					<option value='<%=kv.getString("KVID")%>' selected><%=kv.getString("TEN") %></option>
								      			  <%}else{ %>
								     					<option value='<%=kv.getString("KVID")%>'><%=kv.getString("TEN") %></option>
								     			  <%}
								    				
								    	  		}
								    	  	}
								    	  }catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
                        	</TR>
                        	
                        	
					     <!--  <TR>
					      		<TD class="legendtitle" colspan=3>
					      		<a class="button3" href="javascript:Mokhoaso()">
					      		 	<img style="top: -4px;" src="../images/New.png" alt="">Mở khóa sổ 
					      		 </a>   
					      		</TD>
					      </TR> -->
					      
					        
							  
						</TABLE>
						
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black"><%=ChuyenNgu.get("Khóa sổ",nnId) %></LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						<TR>	
							    <TD class="plainlabel"><B></B><%=ChuyenNgu.get("Nhà phân phối khóa ( khóa sổ / mở khóa sổ )",nnId) %> </TD>
							    <TD class="plainlabel"><SELECT id = "nppId" name="nppId" onChange = "submitform();">
								    <option value=""></option>
								      <% try{
								    	  	if(npp != null){	
								    	  		while(npp.next()){ 
								    				if(npp.getString("NPPID").equals(mksBean.getNppId())){ %>
								      					<option value='<%=npp.getString("NPPID")%>' selected><%=npp.getString("TEN") %></option>
								      			  <%}else{ %>
								     					<option value='<%=npp.getString("NPPID")%>'><%=npp.getString("TEN") %></option>
								     			  <%}
								    			}
								    	  	}
								    	  }catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
                        		
					      </TR>
					      <TR>
						      <TD width="15%" class="plainlabel"><%=ChuyenNgu.get("Ngày khóa sổ hiện tại",nnId) %> </TD>
							  <TD  class="plainlabel">
							 	  <input AUTOCOMPLETE="off" type="text" size="11" id="ngay" name="ngay" value="<%= mksBean.getNgay() %>" maxlength="10" readonly />
							  </TD>
						  </TR>
						  
						  <TR>
					      		<TD class="plainlabel" >
					      		<a class="button3" href="javascript:KhoaSo()">
					      		 	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Khóa sổ",nnId) %> 
					      		 </a>   
					      		</TD>
					      		<a class="button3" href="javascript:Mokhoaso()">
					      		 	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Mở khóa sổ",nnId) %> 
					      		 </a>   
					      		 
					      		 
					      		<TD class="plainlabel">
						      		  <a class="button3" href="javascript:KhoaSoThang()">
						      		 	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Khóa sổ tháng(*)",nnId) %> 
						      		 </a>  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					        
					      		 <a class="button3" href="javascript:khoasotudong()">
					      		 	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Chọn khóa sổ tự động(*)",nnId) %> 
					      		 </a>   
					      		 <a class="button3" href="javascript:ngung_khoasotudong()">
					      		 	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Không khóa sổ tự động(*)",nnId) %> 
					      		 </a>   
					      		 
					      		</TD>
					      </TR>
						</TABLE>
						
						 <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
	                <TR class="tbheader">
	                	<TH align="center"><%=ChuyenNgu.get("Mã",nnId) %></TH>
	                    <TH align="center"><%=ChuyenNgu.get("Tên",nnId) %></TH>
	                    <TH align="center"><%=ChuyenNgu.get("Khóa sổ tự động",nnId) %></TH>
	                    <TH align="center"><%=ChuyenNgu.get("Ngày Khóa sổ",nnId) %></TH>
	                    <TH align="center"><%=ChuyenNgu.get("Tháng Khóa sổ",nnId) %></TH>
	                    
	                    <TH >Chọn(*)  
							<input type="checkbox" name="chonAll" id = "chonAll" onclick="checkedAll();">
						</TH>
	                </TR>
	                <%
	                String tt="";
	                                    if (nppRs != null) 
	                                    {									
	                                        int m = 0;
	                                        while (nppRs.next()) 
	                                        {
	                                            if ((m % 2) == 0) {
	                                %>
	                <TR class='tbdarkrow'>
	                    <%
	                                        } else {
	                                    %>
	                
	                <TR class='tblightrow'>
	                    <%
	                                        }
	                                    %>
	                    <TD align="left"><%=nppRs.getString("manpp")%></TD>
	                    <TD align="left"><%=nppRs.getString("ten")%></TD>
	                     <TD align="center"><%=nppRs.getInt("khoasotudong")%></TD>
	                    <TD align="center"><%=nppRs.getString("NgayKS")%></TD>
	                    <TD align="center"><%=nppRs.getString("namthang")%></TD>
	                    
	                    <TD align="center">											
							<input type="checkbox" name='nppChon'  value='<%= nppRs.getString("pk_seq") %>' >											
						</TD>
	                </tr>
	                <%   } }%>
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
		$("select:not(.notuseselect2)").css({
			"width": "200px", 
			//"height": "200px"
		});
	</script>
		
		</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
		</HTML>
		
<%
try{
	
	if(vung!=null){ vung.close(); vung= null; }
	if(kv!=null){ kv.close(); kv= null;  }
	if(npp!=null){ npp.close(); npp= null;  }
	if(nppRs!=null){ nppRs.close(); nppRs= null;  }
	
	if( mksBean != null){
		mksBean.DBClose();
		mksBean=null;
	}
	session.setAttribute("mksBean",null);	
	
}
catch (Exception e) {}

}%>