<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.tieuchithuong.imp.*" %>
<%@page import="geso.dms.center.beans.tieuchithuong.*" %>
<%@ page  import = "geso.dms.center.beans.dieukienkhuyenmai.ISanpham" %>
<%@ page  import = "geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
 	ITieuchithuongSKU obj =(ITieuchithuongSKU)session.getAttribute("tctskuBean");
	List<INhomsp> nhomsp = obj.getNhomspList();
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

<script type="text/javascript" src="..scripts/jquery-1.js"></script>
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
<script type="text/javascript" src="../scripts/nhomspthuong.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>

<link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	$(".nhomsanpham0").colorbox({width:"90%", inline:true, href:"#nhomsanpham0"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
                return false;
            });
           
            $(".nhomsanpham1").colorbox({width:"90%", inline:true, href:"#nhomsanpham1"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
                return false;
            });
            
            $(".nhomsanpham2").colorbox({width:"90%", inline:true, href:"#nhomsanpham2"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
                return false;
            });
            
            $(".nhomsanpham3").colorbox({width:"90%", inline:true, href:"#nhomsanpham3"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
                return false;
            });
            
            $(".nhomsanpham4").colorbox({width:"90%", inline:true, href:"#nhomsanpham4"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
                return false;
            });
          
        });
    </script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
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
		
		var up= document.getElementById("filename" );
		if( up.value != '' )
		  {
			  alert("Vui lòng cập nhật file trước");
			  return;
		  }
		 document.forms['tctsku'].removeAttribute('enctype', "multipart/form-data", 0);
	  var thang = document.getElementById("thang").value;
	  var nam = document.getElementById("nam").value;

	  if( thang == '' )
	  {
		  alert("Chọn tháng cần đặt chỉ tiêu");
		  return;
	  }
	  	
	  if( nam == '' )
	  {
		  alert("Chọn năm cần đặt chỉ tiêu");
		  return;
	  }
	  
	  document.forms["tctsku"].action.value = "save";
	  document.forms["tctsku"].submit(); 
  }
	
	function replaces()
	{
		for(k = 0; k < 5; k++)
		{
			var masp = document.getElementsByName('nhomsanpham' + k + '.masanpham');
			var tensp = document.getElementsByName('nhomsanpham' + k + '.tensanpham');
			
			for(p=0; p < masp.length; p++)
			{
				if(masp.item(p).value != "")
				{
					var sp = masp.item(p).value;

					var pos = parseInt(sp.indexOf(" - "));
					if(pos > 0)
					{
						masp.item(p).value = sp.substring(0, pos);
						tensp.item(p).value = sp.substr(parseInt(sp.indexOf(" - ")) + 3);					
					}
				}
				else
				{
					tensp.item(p).value = "";
				}			
			}
		}

		setTimeout(replaces, 100);
	}	
	
	function submitform2(pos)
	{  
		var sl=document.getElementById("error");
		/* if(sl.value.length==0)
			{
				alert('Vui lòng upload file trước!');
				return;
			}
		 */
		 document.forms['tctsku'].removeAttribute('enctype', "multipart/form-data");
		var diengiai = document.getElementById('nhomsanpham' + pos + '.diengiai').value; 
		document.getElementById("dkkmDiengiai" + pos).value = document.getElementById('nhomsanpham' + pos + '.diengiai').value;
		
		if(diengiai == '')
		{
			alert('Bạn phải nhập diễn giải cho nhóm sản phẩm');
			return;
		}
		
		var trongso = document.getElementById('nhomsanpham' + pos + '.trongso').value;
		if(trongso == '')
		{
			trongso = '0';
		}

		var sanpham = '';
		var thuongSS = '';
		var thuongTDSS = '';
		var thuongSR = '';
		var thuongTDSR = '';
		var thuongASM = '';
		var thuongTDASM = '';
		
		var masanpham = document.getElementsByName('nhomsanpham' + pos + '.masanpham');
		var thuong_SS = document.getElementsByName('nhomsanpham' + pos + '.thuongSS');
		var thuong_TDSS = document.getElementsByName('nhomsanpham' + pos + '.thuongTDSS');
		var thuong_SR = document.getElementsByName('nhomsanpham' + pos + '.thuongSR');
		var thuong_TDSR = document.getElementsByName('nhomsanpham' + pos + '.thuongTDSR');
		var thuong_ASM = document.getElementsByName('nhomsanpham' + pos + '.thuongASM');
		var thuong_TDASM = document.getElementsByName('nhomsanpham' + pos + '.thuongTDASM');
		
		for(i = 0; i < masanpham.length; i++)
		{
			if(masanpham.item(i).value != '')
			{
				sanpham += masanpham.item(i).value + ';';
				
				if(thuong_SS.item(i).value != '')
					thuongSS += thuong_SS.item(i).value + ';';
				else
					thuongSS += '0;';	
				
				if(thuong_TDSS.item(i).value != '')
					thuongTDSS += thuong_TDSS.item(i).value + ';';
				else
					thuongTDSS += '0;';
				
				if(thuong_SR.item(i).value != '')
					thuongSR += thuong_SR.item(i).value + ';';
				else
					thuongSR += '0;';
					
				if(thuong_TDSR.item(i).value != '')
					thuongTDSR += thuong_TDSR.item(i).value + ';';
				else
					thuongTDSR += '0;';
					
				if(thuong_ASM.item(i).value != '')
					thuongASM += thuong_ASM.item(i).value + ';';
				else
					thuongASM += '0;';
					
				if(thuong_TDASM.item(i).value != '')
					thuongTDASM += thuong_TDASM.item(i).value + ';';
				else
					thuongTDASM += '0;';
			}
		}
		
		if(sanpham == '')
		{
			alert('Bạn phải chọn sản phẩm cho nhóm sản phẩm');
			return;
		}
		
		
		var xmlhttp;
		if (window.XMLHttpRequest)
		{
		  xmlhttp=new XMLHttpRequest();
		}
		else
		{
		  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		xmlhttp.onreadystatechange=function()
		{
		   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		   {
			  if(xmlhttp.responseText.length > 10)
			  {
				  alert('Không thể tạo mới nhóm sản phẩm, vui lòng kiểm tra lại các thông tin. \n');
			  }
			  else
			  {
				  alert('Tạo mới nhóm sản phẩm thành công. \n');
				  document.getElementById("dkkmId" + pos).value = xmlhttp.responseText;
				  
				  document.getElementById("dkkmDiengiai" + pos).value = diengiai;
				  document.getElementById("dkkmTrongso" + pos).value = document.getElementById('nhomsanpham' + pos + '.trongso').value;
				  
			   }
		    }
		}
		
		var dg = encodeURIComponent(diengiai.replace(" ","+"));
		xmlhttp.open("GET","../../AjaxTctSKU?diengiai=" + dg + "&trongso=" + trongso + "&sanpham=" + sanpham + "&thuongSS=" + thuongSS + "&thuongTDSS=" + thuongTDSS + "&thuongSR=" + thuongSR + "&thuongTDSR=" + thuongTDSR + "&thuongASM=" + thuongASM + "&thuongTDASM=" + thuongTDASM , true);
		xmlhttp.send(); 
		
	}
	function upload()
	{
		var up= document.getElementById("filename" );
		if( up.value == '' )
		  {
			  document.forms["tctsku"].dataerror.value="Không có file updload";
			  return;
		  }
		document.forms['tctsku'].setAttribute('enctype', "multipart/form-data");
	    document.forms['tctsku'].submit();
	}

</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%= obj.getId()  %>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Kinh doanh > Tiêu chí thưởng SKU tập trung > Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../TieuchithuongSKUSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin tiêu chí thưởng SKU tập trung </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="20%" class="plainlabel" >Tháng <FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel" colspan="3">
									<select name="thang" id="thang" style="width: 80px">
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
							  	<TD class="plainlabel">Năm <FONT class="erroralert"> </FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
									<select name="nam" id="nam" style="width :80px">
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
										<option value=<%=n %> ><%=n %></option> 
									<% } }
									%>
									</select>
						  	  	</TD>
						  </TR>
						   <TR>
						  	  	<TD class="plainlabel">Scheme <FONT class="erroralert"> </FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="scheme" id="scheme" value="<%= obj.getScheme() %>"> 
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel"> &nbsp; </TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<% if(obj.getIsthung().equals("1")){ %>
						  	  			<input type="checkbox" name="IsThung" value="1" checked="checked"><i> Số lượng tính theo thùng </i>
						  	  		<%} else { %> 
						  	  			<input type="checkbox" name="IsThung" value="1" > <i> Số lượng tính theo thùng </i>
						  	  		<% } %> 
						  	  	</TD>
						  </TR>
						  <TR style="display: none">
						  	  	<TD class="plainlabel">Phải đạt</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="phaidat" id="phaidat" value="0" onkeypress="return keypress(event);" style="text-align: right;"> 
						  	  	</TD>
						  </TR>
						    <TR>
						  	  	<TD class="plainlabel">Chọn tập tin</TD>
						  	  	<TD class="plainlabel">
						  	  		<INPUT type="file" name="filename" id="filename"  size="40" value=''>
						  	  	</TD>
						  	</TR>
						  	 <TR>							  	
						  	  	<TD class="plainlabel" colspan="2">
						  	  	<a class="button2"  href="javascript:upload()">
									<img style="top: -4px;" src="../images/button.png" alt="">Cập nhật</a>&nbsp;&nbsp;&nbsp;&nbsp;	
						  	  	</TD>
						  	</TR>
						  <TR style="display: none">
						  	  	<TD class="plainlabel">Thưởng SR <FONT class="erroralert"> </FONT></TD>
						  	  	<TD class="plainlabel">
						  	  		<input type="text" name="thuong" id="thuong" value="<%= obj.getThuong() %>" onkeypress="return keypress(event);" style="text-align: right;"> VNĐ
						  	  	</TD>
						  	  	<TD class="plainlabel">Thưởng tối đa SR</TD>
						  	  	<TD class="plainlabel">
						  	  		<input type="text" name="thuongtoida" id="thuongtoida" value="<%= obj.getThuongtoida() %>" onkeypress="return keypress(event);" style="text-align: right;"> VNĐ
						  	  	</TD>
						  </TR>
						  <TR style="display: none">
						  	  	<TD class="plainlabel">Thưởng SS <FONT class="erroralert"> </FONT></TD>
						  	  	<TD class="plainlabel">
						  	  		<input type="text" name="thuongGS" id="thuongGS" value="<%= obj.getThuongGS() %>" onkeypress="return keypress(event);" style="text-align: right;"> VNĐ
						  	  	</TD>
						  	  	<TD class="plainlabel">Thưởng tối đa SS</TD>
						  	  	<TD class="plainlabel">
						  	  		<input type="text" name="thuongtoidaGS" id="thuongtoidaGS" value="<%= obj.getThuongtoidaGS() %>" onkeypress="return keypress(event);" style="text-align: right;"> VNĐ
						  	  	</TD>
						  </TR>
						</TABLE>
						<hr />
						
						<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                        <TH align="center" width="10%"> Mã</TH>
                        <TH align="left" width="15%"> Diễn giải </TH>
                        <TH align="center" width="10%" style="display: none"> Tổng lượng</TH>
                        <td width="80px" align="center" >T. SR</td>
                    	<td width="80px" align="center" >T. TĐ SR</td>
                    	<td width="80px" align="center" >T. SS</td>
                    	<td width="80px" align="center" >T. TĐ SS</td>
                    	<td width="80px" align="center" >T. ASM</td>
                    	<td width="80px" align="center" >T. TĐ ASM</td>
                        <TH align="center" width="80px">Từ mức</TH>
                        <TH align="center" width="80px">Đến mức</TH>
                        <TH align="center" width="80px">Trọng số</TH>
                    </TR>
					<% 
						int i = 0;
                        //System.out.println("So NSP lay duoc: " + nhomsp.size());
						if(nhomsp.size() > 0)
						{ 
						while(i < nhomsp.size())
						{	
							Nhomsp nsp = (Nhomsp)nhomsp.get(i);	
							
							INhomspDetail nspDetail = nsp.getSpDetail();
							List<ISanpham> spList = nspDetail.getSpList();
					%>
							
							<TR class='tbdarkrow'>
	                        <TD align="center">
	                        	<input type="text" id='dkkmId<%= i %>' name="dkkmId" size="10" value="<%= nsp.getId() %>" 
		                        			onkeyup="ajax_showOptions(this,'nhomthuong',event)" AUTOCOMPLETE="off" style="width: 75%">
		                        
		                        <a class="nhomsanpham<%= i %>" href="#">
		                        		<img style="top: -4px;" src="../images/vitriluu.png" title="Tạo mới điều kiện"></a>
				                <div style='display:none'>
			                        <div id='nhomsanpham<%= i %>' style='padding:0px 5px; background:#fff;'>
			                        	<h4 align="left">Tạo mới nhóm sản phẩm thưởng</h4>
										<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
			                            	<tr>
			                                	<td width="150px" valign="top" align="left"><span style="font-size: 12px">Diễn giải</span></td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="nhomsanpham<%= i %>.diengiai" id="nhomsanpham<%= i %>.diengiai" value="<%= nsp.getDiengiai() %>" />
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><span style="font-size: 12px">Trọng số</span></td>
			                                    <td valign="top" align="left">
			                                     <%
								                        String kqx = nsp.getTrongso();
								                        if(kqx.length()!=0)
								                        {
								                        	kqx = Math.round(Double.parseDouble(nsp.getTrongso())) + "" ;
								                        }
								                        %>
										             <input type="text" name="nhomsanpham<%= i %>.trongso" id="nhomsanpham<%= i %>.trongso" 
			                                    			value="<%=kqx %>" style="text-align: right;"/>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td colspan="2">
			                                		<table align="left" cellpadding="0" cellspacing="1">
				                                		<tr class="tbheader">
				                                			<td width="130px" align="center" ><span style="font-size: 0.9em">Mã sản phẩm</span></td>
				                                			<td width="260px" align="center" ><span style="font-size: 0.9em">Tên sản phẩm</span></td>       			
				                                			<td width="90px" align="center" ><span style="font-size: 0.9em">T. SR</span></td>
				                                			<td width="90px" align="center" ><span style="font-size: 0.9em">T. TĐ SR</span></td>
				                                			<td width="90px" align="center" ><span style="font-size: 0.9em">T. SS</span></td>
				                                			<td width="90px" align="center" ><span style="font-size: 0.9em">T. TĐ SS</span></td>
				                                			<td width="90px" align="center" ><span style="font-size: 0.9em">T. ASM</span></td>
				                                			<td width="90px" align="center" ><span style="font-size: 0.9em">T. TĐ ASM</span></td>
				                                		</tr>
				                                	</table>
				                                	<div id="nhomsanpham<%= i %>.tbsanpham" style="width: 100%; max-height: 400px; overflow: auto">
				                                	<table align="left" cellpadding="0" cellspacing="1">
				                                	<% 
				                                	int count = 0;
				                                	while(count < spList.size())
				                                	{
				                                		ISanpham sp = spList.get(count);
				                                		%>
				                                		<tr>
				                                			<td width="130px" align="center">
				                                				<input type="text" value="<%= sp.getMasanpham() %>" style="width: 130px" name="nhomsanpham<%= i %>.masanpham" 
				                                						onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
				                                			</td>
				                                			<td width="260px" align="left">
				                                				<input type="text" value="<%= sp.getTensanpham() %>" name="nhomsanpham<%= i %>.tensanpham" style="width: 260px" readonly="readonly">
				                                			</td>       			
				                                			<td width="90px" align="left">
				                                				<input type="text" value="<%= sp.getThuongSR() %>" name="nhomsanpham<%= i %>.thuongSR" style="width: 90px; text-align: right;" onkeypress="return keypress(event);" >
				                                			</td>
				                                			<td width="90px" align="left">
				                                				<input type="text" value="<%= sp.getThuongTDSR() %>" name="nhomsanpham<%= i %>.thuongTDSR" style="width: 90px; text-align: right;" onkeypress="return keypress(event);" >
				                                			</td>
				                                			<td width="90px" align="left">
				                                				<input type="text" value="<%= sp.getThuongSS() %>" name="nhomsanpham<%= i %>.thuongSS" style="width: 90px; text-align: right;" onkeypress="return keypress(event);" >
				                                			</td>
				                                			<td width="90px" align="left">
				                                				<input type="text" value="<%= sp.getThuongTDSS() %>" name="nhomsanpham<%= i %>.thuongTDSS" style="width: 90px; text-align: right;" onkeypress="return keypress(event);" >
				                                			</td>
				                                			<td width="90px" align="left">
				                                				<input type="text" value="<%= sp.getThuongASM() %>" name="nhomsanpham<%= i %>.thuongASM" style="width: 90px; text-align: right;" onkeypress="return keypress(event);" >
				                                			</td>
				                                			<td width="90px" align="left">
				                                				<input type="text" value="<%= sp.getThuongTDASM() %>" name="nhomsanpham<%= i %>.thuongTDASM" style="width: 90px; text-align: right;" onkeypress="return keypress(event);">
				                                			</td>
				                                		</tr>
				                                	<% count++; } %>
				                                	<% for(int pos=count; pos < 10; pos++){ %>
				                                		<tr>
				                                			<td width="130px" align="center">
				                                				<input type="text" value="" style="width: 130px" name="nhomsanpham<%= i %>.masanpham" 
				                                						onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
				                                			</td>
				                                			<td width="260px" align="left">
				                                				<input type="text" value="" name="nhomsanpham<%= i %>.tensanpham" style="width: 260px" readonly="readonly">
				                                			</td> 			
				                                			<td width="90px" align="left">
				                                				<input type="text" value="" name="nhomsanpham<%= i %>.thuongSR" style="width: 90px; text-align: right;" onkeypress="return keypress(event);" >
				                                			</td>
				                                			<td width="90px" align="left">
				                                				<input type="text" value="" name="nhomsanpham<%= i %>.thuongTDSR" style="width: 90px; text-align: right;" onkeypress="return keypress(event);" >
				                                			</td>
				                                			<td width="90px" align="left">
				                                				<input type="text" value="" name="nhomsanpham<%= i %>.thuongSS" style="width: 90px; text-align: right;" onkeypress="return keypress(event);" >
				                                			</td>
				                                			<td width="90px" align="left">
				                                				<input type="text" value="" name="nhomsanpham<%= i %>.thuongTDSS" style="width: 90px; text-align: right;" onkeypress="return keypress(event);" >
				                                			</td>
				                                			<td width="90px" align="left">
				                                				<input type="text" value="" name="nhomsanpham<%= i %>.thuongASM" style="width: 90px; text-align: right;" onkeypress="return keypress(event);" >
				                                			</td>
				                                			<td width="90px" align="left">
				                                				<input type="text" value="" name="nhomsanpham<%= i %>.thuongTDASM" style="width: 90px; text-align: right;" onkeypress="return keypress(event);">
				                                			</td>
				                                		</tr>
				                                	<%} %>
			                                		</table>
			                                		</div>
			                                	</td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left" colspan="2">
			        								<a class="button" href="javascript:submitform();">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> Nhập lại  </a>
			        								&nbsp;&nbsp;&nbsp;
			        								<a class="button" href="javascript:submitform2(<%= i %>);">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> Lưu nhóm sản phẩm </a>
			                                	</td>
			                                </tr>
			                            </table>
									</div>
				                </div>
		                        
	                        </TD>
	                        <TD align="left"><input type="text" name="dkkmDiengiai" id="dkkmDiengiai<%= i %>" size="80" value="<%=nsp.getDiengiai() %>" readonly="readonly"></TD>
	                        <TD align="left"><input type="text" name="dkkmTSR" id="dkkmTSR<%= i %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
	                        <TD align="left"><input type="text" name="dkkmTTDSR" id="dkkmTTDSR<%= i %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
 	                        <TD align="left"><input type="text" name="dkkmTSS" id="dkkmTSS<%= i %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
 	                        <TD align="left"><input type="text" name="dkkmTDSS" id="dkkmTDSS<%= i %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
	                        <TD align="left"><input type="text" name="dkkmTASM" id="dkkmTASM<%= i %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
	                        <TD align="left"><input type="text" name="dkkmTTDASM" id="dkkmTTDASM<%= i %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD> 
	                            
	                                   <TD align="center" style="display: none">
	                        	<input type="text" name="dkkmTongluong" id="dkkmTongluong<%= i %>" size="6" value="<%=nsp.getTongluong() %>" style="text-align:right" readonly></TD>
	                         <%
	                         String kq=nsp.getTumuc();
	                        if(kq!=null && kq.replaceAll(" ","").length()>0)
	                        	kq =  Math.round(Double.parseDouble(nsp.getTumuc())) +"" ;
	                        %>
	                        <TD align="center"><input type="text" name="dkkmTumuc" id="dkkmTumuc<%= i %>" size="6" value="<%= kq%>" style="text-align:right" onkeypress="return keypress(event);"></TD>	
	                        <%
	                         kq=nsp.getDenmuc();
	                        if(kq!=null && kq.replaceAll(" ","").length()>0)
	                        	kq = Math.round(Double.parseDouble(nsp.getDenmuc())) +"" ;
	                        %>
	                        <TD align="center"><input type="text" name="dkkmDenmuc" id="dkkmDenmuc<%= i %>" size="6" value="<%= kq %>" style="text-align:right" onkeypress="return keypress(event);"></TD>	
	                         <%
	                         kq=nsp.getTrongso();
	                        if(kq!=null && kq.replaceAll(" ","").length()>0)
	                        	kq = Math.round(Double.parseDouble(nsp.getTrongso()))  +"" ;
	                        %>
	                        <TD align="center"><input type="text" name="dkkmTrongso" id="dkkmTrongso<%= i %>" size="6" value="<%= kq%>" style="text-align:right" onkeypress="return keypress(event);"></TD>						            
	                    </TR>
		                   
                    	<% i++; } } %>
                    	
                    	<% for(int j = i; j < 5; j++){ %>
                    		<TR class='tbdarkrow'>
		                        <TD align="center">
		                        	<input type="text" id='dkkmId<%= j %>' name="dkkmId" size="10" value="" 
		                        			onkeyup="ajax_showOptions(this,'nhomthuong',event)" AUTOCOMPLETE="off" style="width: 75%">
			                        
			                        <a class="nhomsanpham<%= j %>" href="#">
			                        		<img style="top: -4px;" src="../images/vitriluu.png" title="Tạo mới điều kiện"></a>
					                <div style='display:none; '>
				                        <div id='nhomsanpham<%= j %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Tạo mới nhóm sản phẩm thưởng</h4>
											<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
				                            	<tr>
			                                	<td width="150px" valign="top" align="left"><span style="font-size: 12px">Diễn giải</span></td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="nhomsanpham<%= j %>.diengiai" id="nhomsanpham<%= j %>.diengiai" value="" />
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><span style="font-size: 12px">Trọng số</span></td>
			                                    <td valign="top" align="left">
			                                    	<input type="text" name="nhomsanpham<%= j %>.trongso" id="nhomsanpham<%= j %>.trongso" 
			                                    			value="" style="text-align: right;" onkeypress="return keypress(event);"/>
			                                    </td>
			                                </tr>
				                              <tr>
				                                	<td colspan="2">
				                                		<table align="left" cellpadding="0px" cellspacing="1px">
					                                		<tr class="tbheader">
					                                			<td width="100px" align="center" ><span style="font-size: 0.9em">Mã sản phẩm</span></td>
					                                			<td width="200px" align="center" ><span style="font-size: 0.9em">Tên sản phẩm</span></td>		                                			
					                                			<td width="80px" align="center" ><span style="font-size: 0.9em">T. SR</span></td>
					                                			<td width="80px" align="center" ><span style="font-size: 0.9em">T. TĐ SR</span></td>
					                                			<td width="80px" align="center" ><span style="font-size: 0.9em">T. SS</span></td>
					                                			<td width="80px" align="center" ><span style="font-size: 0.9em">T. TĐ SS</span></td>
					                                			<td width="80px" align="center" ><span style="font-size: 0.9em">T. ASM</span></td>
					                                			<td width="80px" align="center" ><span style="font-size: 0.9em">T. TĐ ASM</span></td>
					                                		</tr>
					                                	</table>
					                                	<div id="nhomsanpham<%= j %>.tbsanpham" style="width: 100%; max-height: 200px; overflow: auto">
					                                	<table align="left" cellpadding="0px" cellspacing="1px">
					                                	<% for(int pos=0; pos < 10; pos++){ %>
					                                		<tr>
					                                			<td width="100px" align="center">
					                                				<input type="text" value="" style="width: 100px" name="nhomsanpham<%= j %>.masanpham" 
					                                						onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
					                                			</td>
					                                			<td width="200px" align="left">
					                                				<input type="text" value="" name="nhomsanpham<%= j %>.tensanpham" style="width: 200px" readonly="readonly">
					                                			</td>                   			
					                                			<td width="80px" align="left">
					                                				<input type="text" value="" name="nhomsanpham<%= j %>.thuongSR" style="width: 80px; text-align: right;" onkeypress="return keypress(event);" >
					                                			</td>
					                                			<td width="80px" align="left">
					                                				<input type="text" value="" name="nhomsanpham<%= j %>.thuongTDSR" style="width: 80px; text-align: right;" onkeypress="return keypress(event);" >
					                                			</td>
					                                			<td width="80px" align="left">
					                                				<input type="text" value="" name="nhomsanpham<%= j %>.thuongSS" style="width: 80px; text-align: right;" onkeypress="return keypress(event);" >
					                                			</td>
					                                			<td width="80px" align="left">
					                                				<input type="text" value="" name="nhomsanpham<%= j %>.thuongTDSS" style="width: 80px; text-align: right;" onkeypress="return keypress(event);" >
					                                			</td>
					                                			<td width="80px" align="left">
					                                				<input type="text" value="" name="nhomsanpham<%= j %>.thuongASM" style="width: 80px; text-align: right;" onkeypress="return keypress(event);" >
					                                			</td>
					                                			<td width="80px" align="left">
					                                				<input type="text" value="" name="nhomsanpham<%= j %>.thuongTDASM" style="width: 80px; text-align: right;" onkeypress="return keypress(event);">
					                                			</td>
					                                		</tr>
					                                	<%} %>
				                                		</table>
				                                		</div>
				                                	</td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left" colspan="2">
				        								<a class="button" href="javascript:submitform();">
				        								<img style="top: -4px;" src="../images/button.png" alt=""> Nhập lại </a>
				        								&nbsp;&nbsp;&nbsp;
				        								<a class="button" href="javascript:submitform2(<%= j %>);">
				        								<img style="top: -4px;" src="../images/button.png" alt=""> Lưu nhóm sản phẩm </a>
				                                	</td>
				                                </tr>
				                            </table>
										</div>
					                </div>
			                        
		                        </TD>
		                        <TD align="left"><input type="text" name="dkkmDiengiai" id="dkkmDiengiai<%= j %>" size="80" value="" readonly></TD>
		                        
		                        <TD align="left"><input type="text" name="dkkmTSR" id="dkkmTSR<%= j %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
		                        <TD align="left"><input type="text" name="dkkmTTDSR" id="dkkmTTDSR<%= j %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
		                        <TD align="left"><input type="text" name="dkkmTSS" id="dkkmTSS<%= j %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
		                        <TD align="left"><input type="text" name="dkkmTDSS" id="dkkmTDSS<%= j %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
		                        <TD align="left"><input type="text" name="dkkmTASM" id="dkkmTASM<%= j %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
		                        <TD align="left"><input type="text" name="dkkmTTDASM" id="dkkmTTDASM<%= j %>" size="80" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>
		                        <TD align="center" style="display: none">
		                        	<input type="text" name="dkkmTongluong" id="dkkmTongluong<%= j %>" size="6" value="" style="text-align:right" readonly></TD>
		                        <TD align="center"><input type="text" name="dkkmTumuc" id="dkkmTumuc<%= j %>" size="6" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>	
	                        	<TD align="center"><input type="text" name="dkkmDenmuc" id="dkkmDenmuc<%= j %>" size="6" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>	
		                        <TD align="center"><input type="text" name="dkkmTrongso" id="dkkmTrongso<%= j %>" size="6" value="" style="text-align:right" onkeypress="return keypress(event);"></TD>	
		                    </TR>
                    	<%} %>
                       <TR>
                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
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
<script type="text/javascript">
	replaces();
</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%}%>
