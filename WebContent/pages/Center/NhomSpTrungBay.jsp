<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.nhomsptrungbay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<% INhomsptrungbayList obj = (INhomsptrungbayList)session.getAttribute("obj"); %>
<% List<INhomsptrungbay> nsptbList = (List<INhomsptrungbay>)obj.getNsptbList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("NhomsptrungbaySvl","",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);%>

<% obj.setNextSplittings(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("NhomsptrungbaySvl","",nnId);	
	%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	
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
  	
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
   
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
	    if(confirm("Ban co muon dang xuat?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 function submitform()
	 {   
	    document.forms["nsptbForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["nsptbForm"].action.value = "Tao moi";
	    document.forms["nsptbForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["nsptbForm"].diengiai.value = "";
	    document.forms["nsptbForm"].tungay.value = "";
	    document.forms["nsptbForm"].denngay.value = "";
	    document.forms["nsptbForm"].submit();
	 }
	 
	 
	 function Xoa(id){
			
			document.forms['nsptbForm'].IdXoa.value = id;
			document.forms['nsptbForm'].action.value= 'Xoa';
			document.forms['nsptbForm'].submit();
		}
	</SCRIPT>

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

<form name="nsptbForm" method="post" action="../../NhomsptrungbaySvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="IdXoa" value=''>
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value="<%=obj.getMgs() %>" >
<script type="text/javascript">if(document.forms["nsptbForm"].msg.value != "") alert(document.forms["nsptbForm"].msg.value);</script>
<%obj.setMgs(""); %>
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	<%= " " + url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset>
            <legend class="legendtitle"> Tiêu chí tìm kiếm  </legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px">								                 
                    <TR>
                        <TD class="plainlabel" valign="middle" width="15%"><%=ChuyenNgu.get("Diễn giải",nnId) %></TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="diengiai" value="<%= obj.getDiengiai() %>">
                        </TD>                        
                    </TR>            
                    <TR>
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10"/>
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days"  
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10"/>
                        </TD>
                    </TR>
                    <tr >
                        <td  class="plainlabel" colspan="2">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt=""><%=ChuyenNgu.get("Tìm kiếm",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Điều kiện trưng bày </span>&nbsp;&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %></a><%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
				                    <TH align="center"><%=ChuyenNgu.get("Mã điều kiện",nnId) %></TH>
				                    <TH align="center"><%=ChuyenNgu.get("Diễn giải",nnId) %></TH>
				                    <TH align="center"><%=ChuyenNgu.get("Tổng lượng",nnId) %></TH>
				                    <TH align="center"><%=ChuyenNgu.get("Tổng tiền",nnId) %></TH>
				                    <TH align="center"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
				                    <TH align="left"><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
				                    <TH align="center"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
				                    <TH align="left"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
				                    <TH align="center" ><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
				                </TR>
							<%      
		                        INhomsptrungbay nsptb = null;
		                        int size = nsptbList.size();
		                        int m = 0;
		                        while (m < size){
		                            nsptb = nsptbList.get(m);
		                            if(m%2==0){
		                    %> 
		                         <TR class='tbdarkrow'>
		                         <%}else{ %>
		                           <TR class='tblightrow'>
		                         <%} %>
				                    <TD align="center"><%= nsptb.getId() %></TD>
				                    <TD align="left"><%= nsptb.getDiengiai() %></TD>
				                    <TD align="right"><%= nsptb.getTongluong() %></TD>										                                    
				                    <TD align="right"><%= nsptb.getTongtien() %></TD>
				                    <TD align="center"><%= nsptb.getNgaytao() %></TD>
				                    <TD align="left"><%= nsptb.getNguoitao() %></TD>
				                    <TD align="center"><%= nsptb.getNgaysua() %></TD>	
				                    <TD align="left"><%= nsptb.getNguoisua() %></TD>				
				                    <TD align="center">
				                        <TABLE cellpadding="1" cellspacing="0">
				                            <TR>
				                            <TD>
				                            <% if(quyen[3]!=0){%>
				                                <A href = "../../NhomsptrungbayUpdateSvl?userId=<%=userId%>&display=<%= nsptb.getId()%>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>
				                                <%} %>
				                            </TD><TD>
				                            <TD>
				                            <% if(quyen[2]!=0){%>
				                                <A href = "../../NhomsptrungbayUpdateSvl?userId=<%=userId%>&update=<%= nsptb.getId()%>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border="0"></A>
				                                <%} %>
				                            </TD><TD>
				                            <%if(quyen[1]!=0){ %>
<%-- 				                                <A href = "../../NhomsptrungbaySvl?userId=<%=userId%>&delete=<%= nsptb.getId() %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa nhóm sản phẩm trưng bày này ?')) return false;"></A> --%>
				                            
				                             <%if(quyen[1]!=0){ %>
                                                        	<A href="javascript:Xoa('<%=nsptb.getId() %>');">
                                                        		<img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 
                                                        		onclick="if(!confirm('Bạn có muốn xóa điều kiện trưng bày này?')) return false;"></A>
                                                    <%} %>
				                            
				                            
				                            <%} %>
				                            </TD></TR>
				                        </TABLE>									
				                    </TD>
				                </TR>
		                     <% m++; }%>														

 <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('nsptbForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('nsptbForm', eval(nsptbForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('nsptbForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('nsptbForm', eval(nsptbForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('nsptbForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>  


						</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<%
  if(nsptbList != null){ nsptbList.clear() ; nsptbList = null;}
	obj.DBclose(); obj = null;
	session.setAttribute("obj",null);

}%>