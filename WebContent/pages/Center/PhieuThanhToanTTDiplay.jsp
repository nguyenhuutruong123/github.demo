<%@page import="geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT_SP"%>
<%@page import="geso.dms.center.beans.phieuthanhtoantt.IPhieuThanhToanTT_DH"%>
<%@page import="java.util.List"%>
<%@page import="geso.dms.center.beans.phieuthanhtoantt.IPhieuThanhToanTT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.phieuthanhtoan.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<% IPhieuThanhToanTT obj = (IPhieuThanhToanTT)session.getAttribute("obj"); %>
<% ResultSet rs_vung = (ResultSet)obj.getRs_vung()  ;%>
<% ResultSet rs_khuvuc = (ResultSet)obj.getRs_KhuVuc();%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen=(String)session.getAttribute("userTen"); %>
<% ResultSet rs_nhaphanphoi = (ResultSet)obj.getRs_NhaPhanPhoi();
	List<IPhieuThanhToanTT_DH> listdonhang=obj.getListDonHang();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
   
   	<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	</style>
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/ajax-dynamic-list-kh.js"></script>
   	  	
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
    </script>
    <script language="javascript" type="text/javascript">
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

<form name="pttForm" method="post" action="../../PhieuThanhToanTTNewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type="hidden" name="id" value='<%=obj.getId() %>'>
<input type="hidden" name="formname" value="updateform" >
<input type="hidden" name="action" value='1'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Phiếu thanh toán> Xem 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn  <%=userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
           </div>
  	
    <div align="left" style="width:100%; float:none">
    <fieldset>
    	<legend class="legendtitle">Phiếu thu tiền</legend>
           <TABLE width="100%" cellpadding="5px" cellspacing="0px">
             <TR>
                <TD class="plainlabel"  width="25%">Ngày thanh toán</TD>
                <TD class="plainlabel" colspan="2">
                    <input readonly="readonly"  type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" "
                           id="ngaythanhtoan" name="ngaythanhtoan" id = "ngaythanhtoan" value="<%=obj.getNgaythanhtoan() %>" maxlength="10" />
                </TD>
            </TR>	
            <TR>
                <TD class="plainlabel">Chọn nhà phân phối</TD>
                <TD class="plainlabel" colspan="2">
                    <select name="nhappid" id="nhappid"  disabled="disabled">
                        <option value="">&nbsp;</option>
                            <% if(rs_nhaphanphoi != null){
					  		try{ while(rs_nhaphanphoi.next()){ 
		    					if(rs_nhaphanphoi.getString("pk_seq").equals(obj.getIdNhaPhanPhoi().trim())){ %>
		      					<option value='<%=rs_nhaphanphoi.getString("pk_seq")%>' selected><%=rs_nhaphanphoi.getString("ten") %></option>
		      				<%}else{ %>
		     					<option value='<%=rs_nhaphanphoi.getString("pk_seq")%>'><%=rs_nhaphanphoi.getString("ten") %></option>
		     			<%}} rs_vung.close(); }catch(java.sql.SQLException e){} }%>
                     </select>
                </TD>
            </TR> 
            <TR>
                <TD class="plainlabel">Diễn giải  </TD>
                <TD class="plainlabel" colspan="2"><input readonly="readonly" type="text" name="diengiai" id ="diengiai" size="50" value ='<%= obj.getDiengiai() %>'> </TD>
            </TR> 
       
            <TR>
                        <TD class="plainlabel" >Hình thức </TD>
                        <TD class="plainlabel" colspan="2">
                        <%if(obj.getHinhthuc().equals("0")) {%>
                            <input type="radio" name="hinhthuc" value="0" checked>Tiền mặt &nbsp;
                            <input type="radio" name="hinhthuc" value="1" >Chuyển khoản &nbsp;
                        <%} else { %>
                            <input type="radio" name="hinhthuc" value="0">Tiền mặt &nbsp;
                            <input type="radio" name="hinhthuc" value="1" checked >Chuyển khoản &nbsp;
                        <% }%>
                        </TD>
             </TR>
            <TR>
                <TD class="plainlabel" valign="middle">Thanh toán </TD>
                <TD class="plainlabel" valign="middle">
                    <input type="text" readonly="readonly" name="thanhtoan" id="thanhtoan" value="<%=Math.round(obj.getSotien()) %>"  style="text-align:right" > VND
                </TD>  
                              
            </TR>           
               					
        </TABLE>
       
        <hr>
            <div style="width:100%; float:none" align="left">
            <table style="width:100%;" cellpadding="3px" cellspacing="1px">
                <tr class="plainlabel">
                    <th align="center" width="20%">Đơn hàng</th>
                    <th align="center" width="20%">Ngày ĐH</th>
                    <th align="center" width="15%">Tiền ĐH</th>
                    <th align="center" width="10%">Thanh toán</th>
                </tr>
                <% int m = 0;
               
                if(listdonhang!=null){
                  while(m<listdonhang.size()) {
                	  IPhieuThanhToanTT_DH donhang=listdonhang.get(m);
                	  
                    if (m % 2 != 0) {%>						
					<TR class= "tblightrow">
					  <%} else {%>
				   <TR class= "tbdarkrow">
					<%}%>
					<TD align="center"> <%=donhang.getIdDonHang() %></TD>
					<TD align="center"><%=donhang.getNgayDat() %> </TD>
					<TD align="center"><%= Math.round(donhang.getTienDonHang()) %></TD>	
					 <TD align="center"><%= Math.round(donhang.getTienThanhToan()) %></TD>
                    </TR>
                  <% m++;}
                }
                  %>
                 <tr class="plainlabel">
                	<td colspan="9">&nbsp;</td>
                </tr>
            </table>
        </div>            
    </fieldset>
    </div>  
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

