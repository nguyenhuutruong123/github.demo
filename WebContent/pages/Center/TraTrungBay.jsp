<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.tratrungbay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<% ITratrungbayList obj = (ITratrungbayList)session.getAttribute("obj"); %>
<% List<ITratrungbay> dkkmList = (List<ITratrungbay>)obj.getTratbList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/AHF/index.jsp");
}else{
	int[] quyen = new  int[5];
	quyen = util.Getquyen("TratrungbaySvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);	
	System.out.println(quyen[4]);

%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("TratrungbaySvl","",nnId);	
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
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
   
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
	    document.forms["tratbForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["tratbForm"].action.value = "Tao moi";
	    document.forms["tratbForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["tratbForm"].diengiai.value = "";
	    document.forms["tratbForm"].tungay.value = "";
	    document.forms["tratbForm"].denngay.value = "";
	    document.forms["tratbForm"].submit();
	 }
	 
	 function Xoa(id){
			
			document.forms['tratbForm'].IdXoa.value = id;
			document.forms['tratbForm'].action.value= 'Xoa';
			document.forms['tratbForm'].submit();
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

<form name="tratbForm" method="post" action="../../TratrungbaySvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="IdXoa" value=''>
<input type="hidden" name="msg" id="msg" value="<%=obj.getMsg() %>" >
<script type="text/javascript">if($('#msg').val() != "") alert($('#msg').val());</script>
<%obj.setMsg(""); %>
<div id="main" style="width:99%; padding-left:2px">
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
            <legend class="legendtitle"> Tiêu chí tìm kiếm </legend>
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
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onChange = "submitform();"/>
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days"
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onChange = "submitform();"/>
                        </TD>
                    </TR>
                    <tr >
                        <td  class="plainlabel" colspan="2">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt=""> <%=ChuyenNgu.get("Tìm kiếm",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Trả trưng bày</span>&nbsp;&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %></a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
				                    <TH align="center"><%=ChuyenNgu.get("Mã trả TB",nnId) %></TH>
				                    <TH align="center"><%=ChuyenNgu.get("Diễn giải",nnId) %></TH>
				                    <TH align="center"><%=ChuyenNgu.get("Tổng lượng",nnId) %></TH>
				                    <TH align="center"><%=ChuyenNgu.get("Tổng tiền",nnId) %></TH>
				                    <TH align="center"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
				                    <TH align="left"> <%=ChuyenNgu.get("Người tạo",nnId) %></TH>
				                    <TH align="center"> <%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
				                    <TH align="left"> <%=ChuyenNgu.get("Người sửa",nnId) %></TH>
				                    <TH align="center" >Tác vụ<%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
				                </TR>
							<%      
		                        ITratrungbay dkkm = null;
		                        int size = dkkmList.size();
		                        int m = 0;
		                        while (m < size){
		                            dkkm = dkkmList.get(m);
		                            if(m%2==0){
		                    %> 
		                         <TR class='tbdarkrow'>
		                         <%}else{ %>
		                         <TR class='tblightrow'>
		                         <%} %>
				                    <TD align="center"><%= dkkm.getId() %></TD>
				                    <TD align="left"><%= dkkm.getDiengiai() %></TD>
				                    <TD align="right"><%= dkkm.getTongluong() %></TD>										                                    
				                    <TD align="right"><%= dkkm.getTongtien() %></TD>
				                    <TD align="center"><%= dkkm.getNgaytao() %></TD>
				                    <TD align="left"><%= dkkm.getNguoitao() %></TD>
				                    <TD align="center"><%= dkkm.getNgaysua() %></TD>	
				                    <TD align="left"><%= dkkm.getNguoisua() %></TD>				
				                    <TD align="center">
				                        <TABLE cellpadding="1" cellspacing="0">
				                            <TR><TD>
				                            <%if(quyen[2]!=0){ %>
				                                <A href = "../../TratrungbayUpdateSvl?userId=<%=userId%>&update=<%= dkkm.getId()%>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border="0"></A>
				                            <%} %>
				                            </TD>
				                           	<td> &nbsp; </td>
				                            <TD>
				                            <%if(quyen[1]!=0){ %>
<%-- 				                                <A href = "../../TratrungbaySvl?userId=<%=userId%>&delete=<%= dkkm.getId() %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn trả trưng bày này?')) return false;"></A> --%>
				                           
				                           		<A href="javascript:Xoa('<%=dkkm.getId() %>');">
                                                        		<img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 
                                                        		onclick="if(!confirm('Bạn có muốn trả trưng bày này?')) return false;"></A>
				                           
				                           <%} %>
				                            </TD>
				                            <td> &nbsp; </td>
				                            <TD>
				                            <%if(quyen[3]!=0){ %>
				                                <A href = "../../TratrungbayUpdateSvl?userId=<%=userId%>&display=<%= dkkm.getId()%>"><IMG src="../images/Display20.png" alt="Hien thi" title="Hiển thị" border="0"></A>
				                            <%} %>
				                            </TD>
				                            
				                            </TR>
				                        </TABLE>									
				                    </TD>
				                </TR>
		                     <% m++; }%>														
                                
							<TR class="tbfooter">
								<TD align="center" colspan="10"> |< < 1 to 20 of 100 > >|</TD>
							</TR>
						</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
	if(dkkmList != null){ dkkmList.clear(); dkkmList = null ;} 
	
	if(obj != null)
	{
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj", null);
}%>