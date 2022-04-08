<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.baocao.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<% IBaocao obj = (IBaocao)session.getAttribute("obj"); %>

<% ResultSet npps = obj.getnpp();%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
ResultSet rs=obj.getRsReportName();
Utility util = (Utility) session.getAttribute("util");
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("ErpReportDoichieu","",nnId);	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>TueLinh - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
  	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 
	 function submitform()
	 {  
		 document.forms["erpDmhForm"].action.value = "submit";
	     document.forms["erpDmhForm"].submit();
	 }
	 function submitform1()
	 {  
		 document.forms["erpDmhForm"].action.value = "reload";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 
	 
	 function locsanpham()
	 {   
		 document.forms["erpDmhForm"].action.value = "search";
	     document.forms["erpDmhForm"].submit();
	 }
	
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("spIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function sellectAll2()
	 {
		 var chkAll = document.getElementById("chkAll2");
		 var spIds = document.getElementsByName("clIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function sellectAll3()
	 {
		 var chkAll = document.getElementById("chkAll3");
		 var spIds = document.getElementsByName("loaisanpham");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function sellectAll4()
	 {
		 var chkAll = document.getElementById("chkAll4");
		 var dvkdIds = document.getElementsByName("dvkdId");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < dvkdIds.length; i++)
			 {
				 dvkdIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < dvkdIds.length; i++)
			 {
				 dvkdIds.item(i).checked = false;
			 }
		 }
	 }
	 
	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="erpDmhForm" method="post" action="../../ErpReportDoichieu">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="khoTen" id="khoTen" value="" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; <%=" "+url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	<%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%= userTen %> &nbsp;
        </div>
    </div>
    
    
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> <%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId) %></legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR>
                        <TD class="plainlabel" width="15%"><%=ChuyenNgu.get("Từ ngày",nnId) %> </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTuNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" width="15%" ><%=ChuyenNgu.get("Đến ngày",nnId) %> </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    
                    <TR>
                    <TD class="plainlabel"><%=ChuyenNgu.get("Nhà Phân Phối",nnId) %> </TD>
					<TD class="plainlabel">
						<select name="nppId" id="nppId">
							<option value="" selected>All</option>
							<%if (npps != null)
									while (npps.next()) {
										if (npps.getString("pk_seq").equals(obj.getNppId())) {%>
											<option value="<%=npps.getString("pk_seq")%>" selected><%=npps.getString("ten")%></option>
									<%} else {%>
										<option value="<%=npps.getString("pk_seq")%>"><%=npps.getString("ten")%></option>
									<%}}%>
						</select>
					</TD>
                    </TR>
                    
                    <%--  <tr>
                      			<TD class="plainlabel">Chọn báo cáo </TD>
									<TD class="plainlabel">
									
										<select name="pk_sequlr" id="pk_sequlr"  >
											<option value=""></option>
											<%
											ResultSet rsduan=obj.getUrlRs();
											
											if (rsduan != null)
											   {
													while (rsduan.next())
													{
														if (rsduan.getString("pk_seq").equals(obj.getPk_seqUrl()))
														{ %>
														<option value="<%=rsduan.getString("pk_seq")%>" selected="selected"><%=rsduan.getString("duan")%></option>
														
											<% } else { %>
														<option value="<%=rsduan.getString("pk_seq")%>"><%=rsduan.getString("duan")%></option> 
														
											<% } } rsduan.close(); } %>
										</select>
									
									</TD>
                      </tr> --%>
                      
                    
             <%--         <TR>
                        <TD class="plainlabel" width="15%">Link database </TD>
                        <TD class="plainlabel" >
                            <input width="600px" type="text"  
                                   id="url" name="url" value="<%= obj.getUrl() %>"  />
                        </TD>
                    </TR>
                      <TR>
                        <TD class="plainlabel" width="15%">Username </TD>
                        <TD class="plainlabel" >
                            <input  type="text"  
                                   id="username" name="username" value="<%= obj.getUsername() %>"   />
                        </TD>
                    </TR>
                      <TR>
                        <TD class="plainlabel" width="15%">password </TD>
                        <TD class="plainlabel" >
                            <input type="text"  
                                   id="password" name="password" value="<%= obj.getpassword() %>"   />
                        </TD>
                    </TR>
                     --%>
                    

                      <tr>
                      			<TD class="plainlabel"><%=ChuyenNgu.get("Chọn báo cáo",nnId) %> </TD>
									<TD class="plainlabel">
									
										<select name="reportname" id="reportname"  >
											<option value=""></option>
											<% if (rs != null)
											   {
													while (rs.next())
													{
														if (rs.getString("TEN_THUTUC").equals(obj.getReportName()))
														{ %>
														<option value="<%=rs.getString("TEN_THUTUC")%>" selected="selected"><%=rs.getString("TENCHUCNANG")%></option>
											<% } else { %>
												<option value="<%=rs.getString("TEN_THUTUC")%>"><%=rs.getString("TENCHUCNANG")%></option>
											<% } } rs.close(); } %>
										</select>
									
									</TD>
                      </tr>
                      
                         <tr>
                             
                      	  <td colspan="2" class="plainlabel">
                            <a class="button" href="javascript:submitform();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %>   </a>
                            	
                            	    
                            	
                        </td>
                    </tr> 
                    
                    </TABLE>                  
       </fieldset>            					                    
    	</div>
    </div>  
</div>
</form>
</body>
<script type="text/javascript">
	dropdowncontent.init('lspId', "right-bottom", 300, "click");
	dropdowncontent.init('dvkdId', "right-bottom", 300, "click");
	dropdowncontent.init('masanpham', "right-bottom", 300, "click");
	dropdowncontent.init('spId', "right-bottom", 300, "click");
	
</script>
</html>

<% 
try{
	obj.close();
	
	session.setAttribute("obj", null)  ; 
}catch(Exception er){
	
}
finally{
	
}
%>