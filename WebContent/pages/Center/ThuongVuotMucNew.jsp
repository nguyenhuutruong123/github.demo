<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.thuongvuotmuc.imp.*" %>
<%@ page import="geso.dms.center.beans.thuongvuotmuc.*" %>
<%@ page import="geso.dms.center.beans.tinhthunhap.imp.Nhanvien" %>
<%@ page import="geso.dms.center.beans.tinhthunhap.INhanvien" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
 	IThuongvuotmuc obj =(IThuongvuotmuc)session.getAttribute("ttnBean");
	ResultSet dvkdRs = obj.getDvkdRs();
	ResultSet kbhRs = obj.getKbhRs();
	ResultSet kvRs = obj.getKhuvucRs();
	List<IThuongvuotmucDetail> tcDetailList = obj.getTieuchiDetail();
	NumberFormat formatter = new DecimalFormat("#,###,###");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
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
    <LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<style type="text/css">
		#mainContainer{
			width:600px;
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
			z-index:100200;
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
	<script type="text/javascript" src="../scripts/nhomthuongList.js"></script>
	
	<link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	<% for(int k = 0; k < 10; k++) { %>
        	
	        	$(".tieuchithuong<%= k %>").colorbox({width:"60%", inline:true, href:"#tieuchithuong<%= k %>"});
	            //Example of preserving a JavaScript event for inline calls.
	            $("#click").click(function(){ 
	                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
	                return false;
	            });
	            
	            $(".apchokhuvuc<%= k %>").colorbox({width:"50%", inline:true, href:"#apchokhuvuc<%= k %>"});
	            //Example of preserving a JavaScript event for inline calls.
	            $("#click").click(function(){ 
	                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
	                return false;
	            });
	           
	            $(".apchoNhanVien<%= k %>").colorbox({width:"90%", inline:true, href:"#apchoNhanVien<%= k %>"});
	            //Example of preserving a JavaScript event for inline calls.
	            $("#click").click(function(){ 
	                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
	                return false;
	            });
       
        <% } %>
        });
    </script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
		document.forms["tctsku"].action.value = "submit";
	    document.forms["tctsku"].submit();
	}
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function save()
	{
	  var thang = document.getElementById("thang").value;
	  if( thang == '' )
	  {
		  alert("Vui lòng chọn tháng");
		  return;
	  }
	  
	  var nam = document.getElementById("nam").value;
	  if( nam == '' )
	  {
		  alert("Vui lòng chọn năm");
		  return;
	  }
	  
	  document.forms["tctsku"].action.value = "save";
	  document.forms["tctsku"].submit(); 
  }
	
    function sellectAll(stt)
    {
    	//alert(stt);
		 var chkAll = document.getElementById("chkAll" + stt);
		 var spIds = document.getElementsByName("nv" + stt + "ChonIds");
		 
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
    function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
</SCRIPT>

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
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý nhân sự > Khai báo > Thưởng vượt mức > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thưởng vượt mức </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR valign="top">
						  	  	<TD width="15%"  class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>" >
						  	  	</TD>
						  </TR>
						  <TR>
								<TD class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="thang" id = "thang" style="width: 50px">
									<option value= ""> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ ){
										
									  if(obj.getThang().equals(Integer.toString(k)) ) {
									%>
										<option value=<%= k %> selected="selected" > <%= k %></option> 
									<%  }else{  %>
										<option value=<%= k %> > <%= k %></option> 
									<% } }%>
									</select>
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel">
									<select name="nam" id = "nam" style="width :50px">
									<option value= ""> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008; n<year_+3; n++) {
									  if(obj.getNam().equals( Integer.toString(n)) ){									  
									%>
										<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
										<option value=<%= n %> ><%=n %></option> 
									<% } }
									%>
									</select>
						  	  	</TD>
						  </TR>
						</TABLE>
					<hr />
						
					<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                        <TH align="center" width="20%">ĐVKD</TH>
                        <TH align="center" width="15%">Kênh</TH>
                        <TH align="center" width="15%">Khu vực</TH>
                        <TH align="center" width="15%">Chức vụ</TH>
                        <TH align="center" width="20%">Tiêu chí</TH>
                        <TH align="center" width="15%">Áp cho nhân viên</TH>
                    </TR>
					
					<% 
						int count = 0;
                        
                        String[] nhomthuong = null;
                        String[] tumuc = null;
                        String[] denmuc = null;
                        String[] thuong = null;

                        for(int i = 0; i < tcDetailList.size(); i++)
                        {
                        	IThuongvuotmucDetail detail = tcDetailList.get(i);
                        	
                            nhomthuong = tcDetailList.get(i).getNhomthuong();
                            tumuc = tcDetailList.get(i).getTumuc();
                            denmuc = tcDetailList.get(i).getDenmuc();
                            thuong = tcDetailList.get(i).getThuong();
                            
                        	%>
                        	
                        	<tr>
                        		<td>
                        			<select name="dvkdIds" style="width: 100%" >
                                   		<% if(dvkdRs != null)
                                   		{ 
                                   			dvkdRs.beforeFirst();
                                   			while(dvkdRs.next()) 
                                   			{  %>
                                   				<option value="<%= dvkdRs.getString("pk_seq") %>"><%= dvkdRs.getString("donvi") %></option>
                                   		 <%} }  %>
                                   	</select>
                        			<input type="hidden" style="width: 100%" name = 'denmucIds' value="" >
                        		</td>
                        		<td>
                        			<select name="kbhIds" style="width: 100%" >
                                   		<option value=""> </option>
                                   		<% if(kbhRs != null)
                                   		{ 
                                   			kbhRs.beforeFirst();
                                   			while(kbhRs.next()) 
                                   			{ 
                                   		if(detail.getKbhId().equals(kbhRs.getString("pk_seq"))){ %>
                                   				<option value="<%= kbhRs.getString("pk_seq") %>" selected="selected"><%= kbhRs.getString("diengiai") %></option>
                                   		 <% } else { %> 
                                   		 		<option value="<%= kbhRs.getString("pk_seq") %>"><%= kbhRs.getString("diengiai") %></option>
                                   		 <%  } } }  %>
                                   	</select>
                        		</td>
                        		<td align="center">
                        		
                        			<a class="apchokhuvuc<%= i %>" href="#">
	           	 							&nbsp; <img alt="Áp cho khu vực" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='apchokhuvuc<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Áp dụng cho khu vực</h4>
											
											<table width="450px" align="center">
						                        <tr>
						                            <th width="100px" style="font-size: 12px">Mã KV</th>
						                            <th width="250px" style="font-size: 12px">Khu vực</th>
						                            <th width="100px" style="font-size: 12px" align="center">Chọn</th>
						                        </tr>
				                            	<% if(kvRs != null)
			                                   		{ 
			                                   			kvRs.beforeFirst();
			                                   			while(kvRs.next()) 
			                                   			{  
			                                   				if( detail.getKvId().indexOf(kvRs.getString("pk_seq")) >= 0 ){ %>
			                                   					<Tr>
			                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>" readonly="readonly" ></td>
			                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("ten") %>" readonly="readonly" ></td>
			                                   						<td align="center">
			                                   							<input name="khuvuc<%= i %>" type="checkbox" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>"  checked="checked" >
			                                   						</td>
			                                   					</Tr>
			                                   		 		<% } else { %> 
			                                   		 			<Tr>
			                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>" readonly="readonly" ></td>
			                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("ten") %>" readonly="readonly" ></td>
			                                   						<td align="center">
			                                   							<input name="khuvuc<%= i %>" type="checkbox" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>"  >
			                                   						</td>
			                                   					</Tr>
			                                   				 <%  } } }  %>
						                       <tr>
						                       		<td colspan="3">&nbsp;</td>
						                       </tr>
						                    </table>
											
										</div>
					                </div>
                        			
                        		</td>
                        		<td>
                        			<select name="chucvu" style="width: 100%" onchange="submitform();">
                        				
                        				<% if(detail.getChucvu().equals("SS")){ %>
                        					<option value=""></option>
	                        				<option value="SR">SR</option>
	                        				<option value="SS" selected="selected">SS</option>
                        				<%} else if(detail.getChucvu().equals("SR")) { %> 
                        					<option value=""></option>
	                        				<option value="SR" selected="selected">SR</option>
	                        				<option value="SS" >SS</option>
                        				<% } else{%>
                        					<option value=""></option>
	                        				<option value="SR" >SR</option>
	                        				<option value="SS" >SS</option>
                        				<%} %>
                        			</select>
                        		</td>
                        		
                        		<td align="center">
                        			
                        			<a class="tieuchithuong<%= i %>" href="#">
	           	 							&nbsp; <img alt="Khai báo tiêu chí" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='tieuchithuong<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Khai báo tiêu chí thưởng </h4>
											
											<table width="650px" align="center">
						                        <tr>
						                            <th width="250px" align="center" style="font-size: 12px">Nhóm thưởng </th>
						                            <th width="100px" align="center" style="font-size: 12px">Từ mức</th>
						                            <th width="100px" align="center" style="font-size: 12px">Đến mức</th>
						                             <th width="100px" align="center" style="font-size: 12px" >Thưởng</th>
						                        </tr>
				                            	<% for(int pos = 0; pos < 10; pos++) { %>
					                            	<tr>
							                            <td><input type="text" value="<%= nhomthuong[pos] %>" name="nhomthuong<%= i %>" style="width: 100%" 
							                            				onkeyup="ajax_showOptions(this,'sanpham',event)"	></td>
							                            <td><input type="text" value="<%= tumuc[pos] %>" name="tumuc<%= i %>" style="width: 100%; text-align: right;" ></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="<%= denmuc[pos] %>" name="denmuc<%= i %>"></td>
							                        	<td align="center">
							                         <input type="text" style="text-align: right; width: 100%" value="<%= thuong[pos]!=null&& thuong[pos].trim().length()>0?formatter.format(Double.parseDouble(thuong[pos].replace(",",""))):"" %>" name="thuong<%= i %>" onkeyup="Dinhdang(this)">
							                            </td>
							                        </tr>
						                       <% } %>
						                       <tr>
						                       		<td colspan="5">&nbsp;</td>
						                       </tr>
						                    </table>
											
										</div>
					                </div>
                        			     
                        		</td>
                        		
                        		<td align="center">
                        			
                        			<a class="apchoNhanVien<%= i %>" href="#">
	           	 							&nbsp; <img alt="Áp cho khu vực" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='apchoNhanVien<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Áp dụng cho nhân viên</h4>
											
											<div style="max-height: 400px">
												<table width="700px" align="center" cellpadding="0" cellspacing="1">
							                        <tr >
							                        	<th width="300px" style="font-size: 12px">Nhà phân phối</th>
							                            <th width="100px" style="font-size: 12px">Mã NV</th>
							                            <th width="200px" style="font-size: 12px">Tên nhân viên</th>
							                            <th width="100px" style="font-size: 12px">
							                            	Chọn <input type="checkbox" id="chkAll<%= i %>" onchange="sellectAll(<%= i %>)" > 
							                            </th>
							                        </tr>
							                        
							                        <% 
							                        List<INhanvien> nvList = detail.getNhanvienList(); 
							                        if(nvList.size() > 0 ) 
							                        { 
							                        	for(int ii = 0; ii < nvList.size(); ii++ ) 
							                        	{ 
							                        		INhanvien nv = nvList.get(ii);
							                        		%> 
							                        	
							                        	<Tr>
							                        		<td><input value="<%= nv.getNppTen() %>" style="width: 100%" name="npp<%= i %>Id" readonly="readonly" > </td>
							                        		<td><input value="<%= nv.getId() %>" style="width: 100%; text-align: center;" name="nv<%= i %>Id" readonly="readonly" > </td>
							                        		<td><input value="<%= nv.getTen() %>" style="width: 100%" name="nv<%= i %>Ten"  readonly="readonly" > </td>
							                        		<td align="center">
							                        		<% if(detail.getNhanvienIds().indexOf(nv.getId()) >= 0 ) { %>
							                        			<input value="<%= nv.getId() %>" type="checkbox" name="nv<%= i %>ChonIds" checked="checked" >
							                        		<% } else { %> 
							                        			<input value="<%= nv.getId() %>" type="checkbox" name="nv<%= i %>ChonIds"  >
							                        		<% } %> 
							                        		</td>
							                        	</Tr>
							                        	
							                        <% } } %>
							                        
							                       <tr>
							                       		<td colspan="3">&nbsp;</td>
							                       </tr>
							                    </table>
						                    </div>
											
										</div>
					                </div>
                        			
                        		</td>
                        		
                        	</tr>
                        	
                        <% count++; }
                    %>
   
   					<% 
	                    for(int i = count; i < 10; i++) 
	                    { 
	                    	IThuongvuotmucDetail ttnDetai = new ThuongvuotmucDetail();
	                    	
	                    	 nhomthuong = ttnDetai.getNhomthuong();
	                         tumuc = ttnDetai.getTumuc();
	                         denmuc = ttnDetai.getDenmuc();
	                         thuong = ttnDetai.getThuong();
	                    	
	                    	%>
	                    	
	                    	<tr>
                        		<td>
                        			<select name="dvkdIds" style="width: 100%" >
                                   		<% if(dvkdRs != null)
                                   		{ 
                                   			dvkdRs.beforeFirst();
                                   			while(dvkdRs.next()) 
                                   			{  %>
                                   				<option value="<%= dvkdRs.getString("pk_seq") %>"><%= dvkdRs.getString("donvi") %></option>
                                   		 <%} }  %>
                                   	</select>
                        			<input type="hidden" style="width: 100%" name = 'denmucIds' value="" >
                        		</td>
                        		<td>
                        			<select name="kbhIds" style="width: 100%" >
                                   		<option value=""> </option>
                                   		<% if(kbhRs != null)
                                   		{ 
                                   			kbhRs.beforeFirst();
                                   			while(kbhRs.next()) 
                                   			{  %>
                                   				<option value="<%= kbhRs.getString("pk_seq") %>"><%= kbhRs.getString("diengiai") %></option>
                                   		 <%} }  %>
                                   	</select>
                        		</td>
                        		<td align="center">
                        			<a class="apchokhuvuc<%= i %>" href="#">
	           	 							&nbsp; <img alt="Áp cho khu vực" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='apchokhuvuc<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Áp dụng cho khu vực</h4>
											
											<table width="450px" align="center">
						                        <tr>
						                            <th width="100px" style="font-size: 12px">Mã KV</th>
						                            <th width="250px" style="font-size: 12px">Khu vực</th>
						                            <th width="100px" style="font-size: 12px" align="center">Chọn</th>
						                        </tr>
				                            	<% if(kvRs != null)
			                                   		{ 
			                                   			kvRs.beforeFirst();
			                                   			while(kvRs.next()) 
			                                   			{  
		                                   				 %> 
		                                   		 			<Tr>
		                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>" readonly="readonly" ></td>
		                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("ten") %>" readonly="readonly" ></td>
		                                   						<td align="center">
		                                   							<input name="khuvuc<%= i %>" type="checkbox" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>"  >
		                                   						</td>
		                                   					</Tr>
		                                   				 <%  } }  %>
						                       <tr>
						                       		<td colspan="3">&nbsp;</td>
						                       </tr>
						                    </table>
											
										</div>
					                </div>
                        		</td>
                        		<td>
                        			<select name="chucvu" style="width: 100%" onchange="submitform();">
                        				<option value=""></option>
                        				<option value="SR">SR</option>
                        				<option value="SS">SS</option>
                        			</select>
                        		</td>
                        		<td align="center">
	                        		<a class="tieuchithuong<%= i %>" href="#">
		           	 							&nbsp; <img alt="Khai báo tiêu chí" src="../images/vitriluu.png"></a>
		           	 				
		           	 				<div style='display:none'>
				                        <div id='tieuchithuong<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Khai báo tiêu chí thưởng </h4>
											
											<table width="650px" align="center">
						                        <tr>
						                            <th width="250px" align="center" style="font-size: 12px">Nhóm thưởng </th>
						                            <th width="100px" align="center" style="font-size: 12px">Từ mức</th>
						                            <th width="100px" align="center" style="font-size: 12px">Đến mức</th>
						                             <th width="100px" align="center" style="font-size: 12px" >Thưởng</th>
						                        </tr>
				                            	<% for(int pos = 0; pos < 10; pos++) { %>
					                            	<tr>
							                            <td><input type="text" value="<%= nhomthuong[pos] %>" name="nhomthuong<%= i %>" style="width: 100%"
							                            		 onkeyup="ajax_showOptions(this,'sanpham',event)"	 ></td>
							                            <td><input type="text" value="<%= tumuc[pos] %>" name="tumuc<%= i %>" style="width: 100%; text-align: right;" ></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="<%= denmuc[pos] %>" name="denmuc<%= i %>"></td>
							                        	<td align="center">
							                            	<input type="text" style="text-align: right; width: 100%" value="<%= thuong[pos]!=null&& thuong[pos].trim().length()>0?formatter.format(Double.parseDouble(thuong[pos].replace(",",""))):"" %>" name="thuong<%= i %>" onkeyup="Dinhdang(this)">
							                            </td>
							                        </tr>
						                       <% } %>
						                       <tr>
						                       		<td colspan="5">&nbsp;</td>
						                       </tr>
						                    </table>
											
										</div>
					                </div>
                        		</td>
                        		<td align="center">
                        			
                        			<a class="apchoNhanVien<%= i %>" href="#">
	           	 							&nbsp; <img alt="Áp cho khu vực" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='apchoNhanVien<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Áp dụng cho nhân viên</h4>
											
											<div style="max-height: 400px">
												<table width="650px" align="center" cellpadding="0" cellspacing="1">
							                        <tr>
							                        	<th width="200px" style="font-size: 12px">Nhà phân phối</th>
							                            <th width="100px" style="font-size: 12px">Mã NV</th>
							                            <th width="200px" style="font-size: 12px">Tên nhân viên</th>
							                            <th width="100px" style="font-size: 12px">Chọn</th>
							                        </tr>
							                       <tr>
							                       		<td colspan="3">&nbsp;</td>
							                       </tr>
							                    </table>
						                    </div>
											
										</div>
					                </div>
                        			
                        		</td>
                        	</tr>
	                    	
	                    <% }  %>
   
                       <TR>
                        <TD align="center" colspan="10" class="tbfooter">&nbsp;</TD>
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
</HTML>
<%}%>
