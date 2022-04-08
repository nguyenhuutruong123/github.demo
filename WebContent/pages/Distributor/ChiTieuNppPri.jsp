<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.sql.SQLException"%>
<%@page import="geso.dms.distributor.beans.chitieunpp.imp.ChiTieuNhaPP"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.banggiasieuthi.*" %>
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

<%	
	Integer thang=(Integer)session.getAttribute("thang");
	Integer nam=(Integer)session.getAttribute("nam");
	String tumuc= (String)session.getAttribute("tumuc");//dung de tim kiem tu muc toi muc chi tieu xac dinh
	String toimuc= (String)session.getAttribute("toimuc");
	String tenhapp=(String)session.getAttribute("tennhapp");
	String dvkdid=(String)session.getAttribute("dvkdid");
	ResultSet rs_dvkd=(ResultSet)session.getAttribute("rs_dvkd");
	ResultSet rs_chitieu_pri=(ResultSet)session.getAttribute("rs_chitieu_pri");
	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("ChiTieuNppPriSvl","", userId);
	%>
	<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("ChiTieuNppPriSvl","",nnId);	
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
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

	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
		document.forms['bgstForm'].toimuc.value="";
		document.forms['bgstForm'].tumuc.value="";
	    document.forms['bgstForm'].nam.value=0;
	    document.forms['bgstForm'].thang.value=0;
	    document.forms['bgstForm'].selectdvkd.value="";
	    document.forms['bgstForm'].thang.value=0;
	    document.forms['bgstForm'].action.value= 'search';
		document.forms['bgstForm'].submit();
	}

	function submitform()
	{
	
		document.forms['bgstForm'].action.value= 'search';
		document.forms['bgstForm'].submit();
	}

	function newform()
	{
		document.forms['bgstForm'].action.value= 'new';
		document.forms['bgstForm'].submit();
	}
	
	</SCRIPT>
	<script type="text/javascript">
$( function() {
	//Created By: Brij Mohan
	//Website: http://techbrij.com
	function groupTable($rows, startIndex, total)
	{
		if (total === 0)
		{
			return;
		}
		var i , currentIndex = startIndex, count=1, lst=[];
		var tds = $rows.find('td:eq('+ currentIndex +')');
		var ctrl = $(tds[0]);
		lst.push($rows[0]);
		for (i=1;i<=tds.length;i++){
		if (ctrl.text() ==  $(tds[i]).text()){
		count++;
		$(tds[i]).addClass('deleted');
		lst.push($rows[i]);
		}
		else{
			if (count>1){
			ctrl.attr('rowspan',count);
			groupTable($(lst),startIndex+1,total-1)
			}
			count=1;
			lst = [];
			ctrl=$(tds[i]);
			lst.push($rows[i]);
		}
		}
	}
	var rowCount = $('#sku tr').length;

	groupTable($('#sku tr:has(td)'),0,rowCount);
	$('#sku .deleted').remove();
	});
    </script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="bgstForm" method="post" action="../../ChiTieuNppPriSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
       
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">

                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %></TD>  
                            <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng bạn",nnId) %>   &nbsp;<%=userTen %>  </TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId) %> &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                             <TR>
                             <TD width="20%" class="plainlabel" ><%=ChuyenNgu.get("Tháng",nnId) %> &nbsp;&nbsp;  <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="thang" style="width :50px">
									<option value=0> </option>  
									<%
									int k=1;
									for(k=1;k<=12;k++){
									  if(k==thang){
									  
									%>
									<option value=<%=k %> selected="selected" > <%=k %></option> 
									<%
									  }else{
									 %>
									<option value=<%=k %> ><%=k %></option> 
									<%
									  }
									  }
									%>
									</select>
								</TD>
                             </TR>
                              <TR>
                             <TD width="20%" class="plainlabel" ><%=ChuyenNgu.get("Năm",nnId) %> &nbsp;&nbsp;  <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="nam"  style="width :50px">
									<option value=0> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008;n<year_+3;n++){
									  if(n==nam){									  
									%>
									<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
									<option value=<%=n %> ><%=n %></option> 
									<%
									  }
									  }
									%>
									</select>
								</TD>
                             </TR >
                              <tr class="plainlabel">
                             <td><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %> </td>
                             <td>
                             <select name=selectdvkd >
                                 <option value =""> </option>  
                             <%
                             if (rs_dvkd!=null){
                            	 while (rs_dvkd.next()){                      				                       				
                       				 if(rs_dvkd.getString("pk_seq").equals(dvkdid)){ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>" selected="selected"> <%=rs_dvkd.getString("donvikinhdoanh") %></option>
                       				<%}else{ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>"> <%=rs_dvkd.getString("donvikinhdoanh") %></option>
                       				<%}     		
                            	 }
                             }
                             %>
                             </select>
                             </td>                           
                             </tr>
                             <tr class="plainlabel" >
                             <td> <%=ChuyenNgu.get("Mức chỉ tiêu",nnId) %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%=ChuyenNgu.get("Từ mức",nnId) %> &nbsp;&nbsp;&nbsp; 
                             </td>
                             <td>
                             <input name="tumuc" type=text value="<%= tumuc %>" >
                            &nbsp;&nbsp;&nbsp; <%=ChuyenNgu.get("Tới mức",nnId) %>  &nbsp;&nbsp;&nbsp;  <input name="toimuc" type=text value="<%= toimuc %>" > </td> 
                             </tr>
                             <tr class="plainlabel" > <td colspan="2" > 
                             <a class="button3" href="javascript:submitform()">
                           	<img style="top: -4px;" src="../images/Search30.png" alt=""><%=ChuyenNgu.get("Tìm kiếm",nnId) %> </a>   &nbsp;&nbsp;&nbsp;
                           		<a class="button2" href="javascript:clearform()">
							<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
                           	
                             </td> </tr>
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

                <TR>
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Quản lý chỉ tiêu",nnId) %> &nbsp;&nbsp;	                           
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            
                            <TABLE id="sku" width="100%" style="font-size: 14px;" border="1" cellspacing="1" cellpadding="0" >							
								<TR class="tbheader">
									
									 <TH width=""><%=ChuyenNgu.get("Tháng",nnId) %> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Năm",nnId) %>  </TH>
                                    <TH > <%=ChuyenNgu.get("Đơn vị KD",nnId) %> </TH>    
                                    <TH ><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TH>	   
									<TH width="10%"><%=ChuyenNgu.get("Mã nhóm",nnId) %></TH>
									<TH width="40%"> <%=ChuyenNgu.get("Tên nhóm",nnId) %>(%)</TH>
									<TH width="10%"><%=ChuyenNgu.get("Chỉ tiêu",nnId) %> </TH>
									
								</TR>
								<%
								  NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
						int m=0;
										if(rs_chitieu_pri != null)
										{
											while (rs_chitieu_pri.next()){
													
					%>
									<tr >
								<TD ><%=rs_chitieu_pri.getString("Thang")%></TD>
									<TD ><%=rs_chitieu_pri.getString("nam")%></TD>
									<TD ><%=rs_chitieu_pri.getString("donvikinhdoanh")%></TD>
									<TD ><%=rs_chitieu_pri.getString("kenhbanhang")%></TD>
									<TD ><%=rs_chitieu_pri.getString("pk_seq")%></TD>
									<TD ><%=rs_chitieu_pri.getString("ten") %></TD>
									<TD > <%=formatter.format(rs_chitieu_pri.getDouble("chitieu"))%> </TD>
								    </tr>
									<%
									m++;
								}
							}
							%>
						</TABLE>		
                            
                            </TD>
                        </TR>
                    </TABLE>
                    </FIELDSET>
                    </TD>
                </TR>

        </TABLE>
        </TD>
    </TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<% 	
	
	try{
	
		if(rs_chitieu_pri != null){ rs_chitieu_pri.close(); rs_chitieu_pri= null;}
		if(rs_dvkd != null){ rs_dvkd.close(); rs_dvkd = null; }
	}
	catch (SQLException e) {}

%>
<%}%>