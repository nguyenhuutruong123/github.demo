<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.donhang.IChotdonhang" %>
<%@ page  import = "geso.dms.distributor.beans.donhang.imp.Chotdonhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IChotdonhang obj = (IChotdonhang)session.getAttribute("obj"); %>
<% String msg = obj.getMessege(); %>

<% ResultSet nvbh = (ResultSet)obj.getNvbhList(); %>
<% ResultSet nvgn = (ResultSet)obj.getNvgnList(); %>
<% ResultSet dhList = (ResultSet)obj.getDhList(); %>

<% Hashtable<Integer, String> dhIds = (Hashtable<Integer, String>)obj.getDhIds(); %>

<% obj.setNextSplittings();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("ChotdonhangSvl","", userId);
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
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
  	
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
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
		function CheckAll()
		{
			var selectAll = document.getElementById("selectAll");
			var chon = document.getElementsByName("dhIds");
			if(selectAll.checked)
			{
				for(i = 0; i < chon.length; i++)
					chon.item(i).checked = true;
			}
			else
			{
				for(i = 0; i < chon.length; i++)
					chon.item(i).checked = false;
			}
		}
		
		function UnCheckedAll()
		{
			var selectAll = document.getElementById("selectAll");
			selectAll.checked = false;
		}
		function Load()
		{
			var CTTrungBayId = document.getElementsByName("CTTrungBayId");
			if(CTTrungBayId.length<=0  )
				{
				var btnTraTrungBay = document.getElementById("btnTraTrungBay");
				btnTraTrungBay.style.display='none';
				}
		}
		
		function submitform()
		{   
			document.forms['cdhForm'].action.value='submitForm';
		    document.forms['cdhForm'].submit();
		}
		function checkDhIds()
		{
			var dhIds = document.getElementsByName("dhIds");
			for(i = 0; i < dhIds.length; i++)
			{
				if(dhIds.item(i).checked == true)
					return true;
			}
			return false;
		}
		function saveform()
		{	
			/*
			var nvgn = document.getElementById("nvgnList");
			if(nvgn.value == "")
			{
				alert('Ban phai chon nhan vien giao nhan...');
				return;
			}
			*/
			if(checkDhIds() == false)
			{
				alert('Bạn phải chọn đơn hàng...');
				return;
			}
			document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

			document.forms['cdhForm'].action.value='save';
		    document.forms['cdhForm'].submit();
		}
		
		function TraTrungBay(dhTrungbayId)
		 {
			document.forms['cdhForm'].dhTBId.value = dhTrungbayId; 
			document.forms['cdhForm'].action.value = "TraTrungBay";
		    document.forms['cdhForm'].submit();
		 }
	</script>
    

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="Load()">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="cdhForm" method="post" action="../../ChotdonhangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="dhTBId" value=''>

<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng> Chốt đơn hàng
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn  <%= obj.getNppTen() %> &nbsp; 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
<!--     	<A href="javascript:backform()"> -->
<!--         	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A> -->
        <label id="btnSave">
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Chốt đơn hàng đã chọn" alt="Chốt đơn hàng đã chọn" border ="1px" style="border-style:outset"></A>
        </label>
    </div>
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">Thông báo </legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%" rows="1" readonly="readonly"><%= obj.getMessege() %></textarea>
		         <% obj.setMessege(""); %>
    	</fieldset>
  	</div>
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Tiêu chí tìm kiếm </legend>        	
        	<div style="float:none; width:100%" align="left">
            	<table width="100%" cellspacing="0" cellpadding="6px">
            		<tr>
           				<td class="plainlabel" >Nhân viên giao nhận </td> 
                        <td class="plainlabel">
                           <select name="nvgnTen" id="nvgnTen" onChange="submitform()">
                            <option value="">&nbsp;</option>
                            <% if(nvgn != null){
                                  try{ while(nvgn.next()){ 
                                    if(nvgn.getString("nvgnId").equals(obj.getNvgnId())){ %>
                                        <option value='<%=nvgn.getString("nvgnId")%>' selected><%=nvgn.getString("nvgnTen") %></option>
                                    <%}else{ %>
                                        <option value='<%=nvgn.getString("nvgnId")%>'><%=nvgn.getString("nvgnTen") %></option>
                            <%}} nvgn.close(); }catch(java.sql.SQLException e){} }%>
                        </select>
                        </td>
                    </tr>
                    <tr>
           				<td class="plainlabel" width="15%">Nhân viên bán hàng </td> 
                        <td class="plainlabel">
                            <select name="nvbhTen" id="nvbhTen" onChange="submitform()">
                            <option value="">&nbsp;</option>
                            <% if(nvbh != null){
                                  try{ while(nvbh.next()){ 
                                    if(nvbh.getString("nvbhId").equals(obj.getNvbhId())){ %>
                                        <option value='<%=nvbh.getString("nvbhId")%>' selected><%=nvbh.getString("nvbhTen") %></option>
                                    <%}else{ %>
                                        <option value='<%=nvbh.getString("nvbhId")%>'><%=nvbh.getString("nvbhTen") %></option>
                            <%}} nvbh.close(); }catch(java.sql.SQLException e){} }%>
                        </select>
                        </td>                       
                    </tr>
                    <tr>
           				<td class="plainlabel">Ngày giao hàng </td> 
                        <td class="plainlabel">
                        	<input type="text" size="11" class="days" 
                                    id="ngaygiao" name="ngaygiao" value="<%= obj.getNgaygiao() %>" maxlength="10"/>
                        </td>
                    </tr> 
                    <tr >
                    	<td   class="plainlabel" colspan="2">
	                    <a class="button2" href="javascript:submitform()" >
	        				<img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm</a>
                    	</td>
                    </tr>                             
                 </table>
                 <hr>
                 <table width="100%" cellpadding="4px" cellspacing="1px">
                
                 	<tr class="tbheader">
                    	<th align="center">Mã đơn hàng</th>
                        <th align="center">Ngày nhập</th>
                        <th align="center">Mã khách hàng</th>
                        <th align="left">Tên khách hàng</th>
                        <th valign="middle" width="10%" align="center">Chọn
                        	<input type="checkbox" name="selectAll" id="selectAll" onChange="CheckAll()"></th>
						<th align="left" width="50px">Tác vụ</th>
                    </tr>
                    <%
                    int count=0;
					if(dhList != null){
					try{ while(dhList.next())
					{ 
					%>
					<TR class= "tbdarkrow" >
						<TD align="center">
						<%= dhList.getString("dhId") %></TD>
						<TD align="center"><%= dhList.getDate("ngaynhap").toString() %></TD>
						<TD align="center"><%= dhList.getString("smartid") %></TD>	
						<TD align="left"><%= dhList.getString("khTen") %></TD>									
						<% if(dhIds.contains(dhList.getString("dhId"))){ %>
							<TD align="center"><input name="dhIds" type="checkbox" value ="<%= dhList.getString("dhId") + "," + dhList.getString("khId") + "," + dhList.getString("tonggiatri") + "," + dhList.getString("nvgnId") + "," + dhList.getString("pxkId") + "," + dhList.getString("ngaynhap") %>" checked onChange="UnCheckedAll()"></TD>
						<%}else{%>
							<TD align="center"><input name="dhIds" type="checkbox" value ="<%= dhList.getString("dhId") + "," + dhList.getString("khId") + "," + dhList.getString("tonggiatri") + "," + dhList.getString("nvgnId") + "," + dhList.getString("pxkId") + "," + dhList.getString("ngaynhap") %>" onChange="UnCheckedAll()"></TD>
						<%}%> 
						<TD>
						<% if(dhList.getString("DENGHITRATRUNGBAYId") != null ){
							count++;

							ResultSet rs = obj.getTrungbayRs(dhList.getString("dhId")); 	%>
							
							<a href="" id="PlnId<%= count %>" rel="subcontentPlnId<%= count %>">  						
								<img alt="Nhập số lượng sản phẩm trưng bày"  title="Nhập số lượng sản phẩm trưng bày" src="../images/Promotion.png"></a>
								
							<DIV id="subcontentPlnId<%= count %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; 
										background-color: white; width: 600px; max-height:250px; overflow:auto; padding: 4px; z-index: 100000000; ">
			 	                
			                    <table width="98%" align="center" cellpadding="0px" cellspacing="1px">
			                    	<tr class="tbheader">
										<th align="center" style="width: 70%;" >Sản phẩm</th>
										<th align="center" style="width: 15%;" >Tồn hiện tại</th>
										<th align="center" style="width: 15%;" >Số lượng</th>
			        				</tr>
			                        <% if( rs!=null ) {
			                        	int i = 0;
			                        	
			                        	String diengiaiTKM = "";
			                        	while(rs.next())
			                        	{ 
			                        		NumberFormat format = new DecimalFormat("#,###,###");
			                        		String SoLuong = rs.getString("SoLuong").equals("0") ? "" : rs.getString("SoLuong");
			                        		String scheme = rs.getString("SCHEME");
			                        		String diengiai = rs.getString("DIENGIAI");
			                        		String tongluong = rs.getString("TONGLUONG");
			                        		String pheptoan = rs.getString("pheptoan");
			                        		String loai = rs.getString("loai");
			                        		
			                        		if(!diengiaiTKM.equals(diengiai))
			                        		{
			                        			diengiaiTKM = diengiai;
			                        			
			                        			String dd = " - " + diengiai;
			                        			if(loai.equals("2"))
			                        				dd += " ---- Tổng lượng: " + format.format( rs.getDouble("TONGLUONG") );
			                        			else
			                        				dd += " ---- Tổng tiền: " + format.format( rs.getDouble("TONGLUONG") );
			                        			
			                        			%>
			                        			
			                        			<tr>
			                        				<td colspan="3" style="background-color: #CCC;"  >
			                        					<% if(pheptoan.equals("2")) { %>
			                        						<input type="radio" name="<%= dhList.getString("dhId") %>_ttbIdChon" value="<%= rs.getString("TRATRUNGBAY_FK") %>" >
			                        					<% } else { %>
			                        						<input type="checkbox" name="<%= dhList.getString("dhId") %>_ttbIdChon" value="<%= rs.getString("TRATRUNGBAY_FK") %>" checked="checked" onclick="return false;" >
			                        					<% } %>
			                        					<span style="color:red; font-weight: bold; " > <%= scheme %></span>
			                        					<%= dd %>
			                        					
			                        					<input type="hidden" name="<%= dhList.getString("dhId") %>_<%= rs.getString("TRATRUNGBAY_FK") %>_TongLuong" value="<%= tongluong %>"  >
			                        					<input type="hidden" name="<%= dhList.getString("dhId") %>_<%= rs.getString("TRATRUNGBAY_FK") %>_cttbID" value="<%= rs.getString("cttbID") %>"  >
			                        					<input type="hidden" name="<%= dhList.getString("dhId") %>_<%= rs.getString("TRATRUNGBAY_FK") %>_cttbSOXUAT" value="<%= rs.getString("DAT") %>"  >
			                        					<input type="hidden" name="<%= dhList.getString("dhId") %>_<%= rs.getString("TRATRUNGBAY_FK") %>_cttbLOAI" value="<%= loai %>"  >
			                        					
			                        				</td>
			                        			</tr>
			                        			<% if(loai.equals("2")) { %>
				                        			<tr>
				                        				<td>
				                        					<input type="hidden" style="width: 100%;" name="<%= dhList.getString("dhId") %>_<%= rs.getString("TRATRUNGBAY_FK") %>_spID" value="<%= rs.getString("spID") %>"  >
				                        					<input type="text" style="width: 100%;" value="<%= rs.getString("spTEN") %>" readonly="readonly" >
				                        				</td>
				                        				<td><input type="text" style="width: 100%; text-align: right;" value="<%= rs.getString("AVAI") %>" readonly="readonly" ></td>
				                        				<td>
				                        					<input type="text" style="width: 100%; text-align: right;" name="<%= dhList.getString("dhId") %>_<%= rs.getString("TRATRUNGBAY_FK") %>_spSOLUONG" value="<%= SoLuong %>" <%= rs.getString("hinhthuc").equals("2") ? "" : " readonly='readonly'  " %> AUTOCOMPLETE="off" > 
				                        				</td>
				                        			</tr>
			                        			<% } %>
			                        			
			                        		<% } else { %>
			                        			
			                        			<% if(loai.equals("2")) { %>
				                        			<tr>
				                        				<td>
				                        					<input type="hidden" style="width: 100%;" name="<%= dhList.getString("dhId") %>_<%= rs.getString("TRATRUNGBAY_FK") %>_spID" value="<%= rs.getString("spID") %>"  >
				                        					<input type="text" style="width: 100%;" value="<%= rs.getString("spTEN") %>" readonly="readonly" >
				                        				</td>
				                        				<td><input type="text" style="width: 100%; text-align: right;" value="<%= rs.getString("AVAI") %>" readonly="readonly" ></td>
				                        				<td>
				                        					<input type="text" style="width: 100%; text-align: right;" name="<%= dhList.getString("dhId") %>_<%= rs.getString("TRATRUNGBAY_FK") %>_spSOLUONG" value="<%= SoLuong %>" <%= rs.getString("hinhthuc").equals("2") ? "" : " readonly='readonly'  " %> AUTOCOMPLETE="off" > 
				                        				</td>
				                        			</tr>
			                        			<% } %>
			                        			
			                        		<% }
			                        	}
			                        } 
			                        %>
			                        
			                     </table>
			                     <div align="right" style="margin-right: 20px;" >
				                      <br />
				                      <a class="button" href="javascript:TraTrungBay(<%= dhList.getString("dhId") %>)">
												<img style="top: -4px;" src="../images/button.png" alt=""> Chốt đơn hàng  </a> &nbsp;&nbsp;&nbsp;&nbsp;    
				                      <a class="button2" href="javascript:dropdowncontent.hidediv('subcontentPlnId<%= count %>')" > 
				                      			<img style="top: -4px;" src="../images/button.png" alt=""> Đóng lại </a>
			                     </div>
				            </DIV> 
							
							<%} %>
						</TD>
                    </TR> 
                    <%} dhList.close(); } catch(Exception e){ e.printStackTrace(); } }%>
                    
                 </table>
            </div>         
    </fieldset>	
    </div>
</div>
</form>
<script type="text/javascript">
 <% for(int i = 0; i <= count; i++) { %>
		dropdowncontent.init('PlnId<%= i %>', "left-top", 300, "click");
 <% } %>
</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<% 	
	
	try{
		if(nvbh != null){ nvbh.close(); nvbh = null; }
		if(nvgn != null){ nvgn.close(); nvgn = null; }
		if(dhList != null){ dhList.close(); dhList = null; }
				
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		
		session.setAttribute("obj",null);
	}
	catch (SQLException e) {e.printStackTrace();}

%>
<%}%>