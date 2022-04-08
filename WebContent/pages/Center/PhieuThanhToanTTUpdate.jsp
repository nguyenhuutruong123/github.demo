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

	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	function tingtongtien(){
		var thanhtoan=document.getElementsByName("thanhtoan1");
		var tiennocu=document.forms['pttForm'].sodudau.value;
		var tongtien=0; 
		for(i = 0; i < thanhtoan.length; i++)
			{
			   tongtien = parseFloat(tongtien) +parseFloat(thanhtoan.item(i).value);
		    }
		document.forms['pttForm'].thanhtoan.value=tongtien;
		
		document.forms['pttForm'].conlai.value=parseFloat(tiennocu)-parseFloat(tongtien);
	}
	function thanhtoan(){
		 var sotien = parseFloat(document.getElementById("thanhtoan").value);
		 var tiendonhang=document.getElementsByName("tiendonhang1");
			var dathanhtoan=document.getElementsByName("dathanhtoan1");
			var thanhtoan=document.getElementsByName("thanhtoan1");
		   for(i = 0; i < thanhtoan.length; i++)
		   {
			   var phaitra=parseFloat(tiendonhang.item(i).value)-parseFloat(dathanhtoan.item(i).value);
			   if(sotien>0){
				  if(sotien>phaitra){
					  thanhtoan.item(i).value=phaitra;
					  sotien=sotien-phaitra;
				  } 
				  else{
					  thanhtoan.item(i).value=sotien;
					  sotien=0;
				  }
			   }else{
				   thanhtoan.item(i).value=0;
			   }
		   }
		   var tiennocu=document.forms['pttForm'].sodudau.value;
		    sotien = document.getElementById("thanhtoan").value;
			document.forms['pttForm'].conlai.value=parseFloat(tiennocu)-parseFloat(sotien);

	}
	function checkall(value){
		var checkone=document.getElementsByName("checkone");
		var giatricheck=document.getElementsByName("valuecheck");
		var chuoi;
		if(value==true){
			chuoi="1";
			
		}else{
			chuoi="0";
		}
		var tiendonhang=document.getElementsByName("tiendonhang1");
		var dathanhtoan=document.getElementsByName("dathanhtoan1");
		var thanhtoan=document.getElementsByName("thanhtoan1");
		var conlai1=document.getElementsByName("conlai1");
		
		for(var i=0;i<checkone.length;i++ ){
			checkone.item(i).checked=value;
			giatricheck.item(i).value=chuoi;
			if(value==true){
				thanhtoan.item(i).value=parseFloat(tiendonhang.item(i).value)-parseFloat(dathanhtoan.item(i).value);
				conlai1.item(i).value=0;						
			}
			else{
				thanhtoan.item(i).value=0;
				conlai1.item(i).value=parseFloat(tiendonhang.item(i).value)-parseFloat(dathanhtoan.item(i).value);
			}
		}
		tingtongtien();
	}
	
	function recheck(){
		var tiendonhang=document.getElementsByName("tiendonhang1");
		var dathanhtoan=document.getElementsByName("dathanhtoan1");
		var thanhtoan=document.getElementsByName("thanhtoan1");
		var conlai1=document.getElementsByName("conlai1");
		
		var checkone=document.getElementsByName("checkone");
		var giatricheck=document.getElementsByName("valuecheck");
		for(var i=0;i<checkone.length;i++ ){
			if(checkone.item(i).checked==true){
				giatricheck.item(i).value="1";
				thanhtoan.item(i).value=parseFloat(tiendonhang.item(i).value)-parseFloat(dathanhtoan.item(i).value);
				conlai1.item(i).value=0;
			}else {
				giatricheck.item(i).value="0";
				thanhtoan.item(i).value=0;
				conlai1.item(i).value=parseFloat(tiendonhang.item(i).value)-parseFloat(dathanhtoan.item(i).value);
			}	
		}
		tingtongtien();
	}
	function tinhtienthua(){
		var tiendonhang=document.getElementsByName("tiendonhang1");
		var dathanhtoan=document.getElementsByName("dathanhtoan1");
		var thanhtoan=document.getElementsByName("thanhtoan1");
		var conlai1=document.getElementsByName("conlai1");
		
		for(var i=0;i<conlai1.length;i++ ){
			
				var tien=	parseFloat(tiendonhang.item(i).value)-parseFloat(dathanhtoan.item(i).value)-parseFloat(thanhtoan.item(i).value);
				if(tien>0){
					conlai1.item(i).value=	tien;
				}else{
					thanhtoan.item(i).value=parseFloat(tiendonhang.item(i).value)-parseFloat(dathanhtoan.item(i).value);
					conlai1.item(i).value=0;
				}
	   }
	}
	//buoc nay kiem tra tong so tien phai tra nho hon tong so tien nha cung cap thanh toan
	
	function kiemtra()
	{  var tong = 0;
	   var thanhtoan = document.getElementsByName("thanhtoan1");
	   var sotien = document.getElementById("thanhtoan").value;
		var tiendonhang=document.getElementsByName("tiendonhang1");
		var dathanhtoan=document.getElementsByName("dathanhtoan1");
		var phaitra=0;
	
	   for(i = 0; i < thanhtoan.length; i++)
	   {
		   tong = tong + parseFloat(thanhtoan.item(i).value);
		   phaitra=phaitra+parseFloat(tiendonhang.item(i).value+dathanhtoan.item(i).value);
	   }
		if(sotien >phaitra){
			if(tong<phaitra){
				document.forms['pttForm'].dataerror.value ='Bạn chưa thanh toán hết tổng số tiền cho các đơn hàng ';
				return false;
			}
			
		}else{
			if(tong < sotien){
				document.forms['pttForm'].dataerror.value ='Bạn chưa thanh toán hết tổng số tiền cho các đơn hàng ';
				return false;
			}
		}
		
		if(sotien<tong){
			document.forms['pttForm'].dataerror.value ='Bạn trả tiền cho các đơn hàng vượt quá tổng số,vui lòng kiểm tra lại  ';
			return false;
		}
		return true;
	}
	function saveform()
	{   
		document.forms['pttForm'].dataerror.value ='';
		if(kiemtra())
		{
			var ngaythanhtoan = document.getElementById("ngaythanhtoan").value;
			var diengiai = document.getElementById("diengiai").value;
			var sotien = document.getElementById("thanhtoan").value;
			
			var h = parseFloat(sotien);
			if(h<= 0 | ngaythanhtoan.length <=0 || diengiai.length <=0 )
			 { 
				document.forms['pttForm'].dataerror.value='Bạn phải nhập đủ thông tin';
				return;
			 }
			document.forms['pttForm'].action.value= 'update';
			document.forms['pttForm'].submit();
		}
	}
	
	 function loadkhuvuc(){
		 document.forms['pttForm'].action.value='loadnpp';
		document.forms['pttForm'].submit();	 
	 }
	function loadnhapp(){
		document.forms['pttForm'].action.value='submit';
	    document.forms['pttForm'].submit();
	}
	
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
        	Quản lý công nợ > Phiếu thanh toán> Chỉnh sửa 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn  <%=userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
  	<div align="left" style="width:100%%; float: none"> 
    	<fieldset>
        	<legend class="legendtitle">Báo lỗi nhập liệu </legend>
            <textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style ="width:100%" rows="1" readonly="readonly"><%=obj.getMsg() %></textarea>
           
        </fieldset>
    </div>
    <div align="left" style="width:100%; float:none">
    <fieldset>
    	<legend class="legendtitle">Phiếu thu tiền</legend>
           <TABLE width="100%" cellpadding="5px" cellspacing="0px">
             <TR>
                <TD class="plainlabel"  width="25%">Ngày thanh toán</TD>
                <TD class="plainlabel" colspan="2">
                    <input  type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" "
                           id="ngaythanhtoan" name="ngaythanhtoan" id = "ngaythanhtoan" value="<%=obj.getNgaythanhtoan() %>" maxlength="10" />
                </TD>
            </TR>	
              <TR>
                <TD class="plainlabel">Lọc khu vực</TD>
                <TD class="plainlabel" colspan="2">
                    <select name="khuvucid" id="khuvucid" onchange="javascript:loadkhuvuc();">
                        <option value="">&nbsp;</option>
                            <% if(rs_khuvuc != null){
					  		try{ while(rs_khuvuc.next()){ 
		    					if(rs_khuvuc.getString("pk_seq").equals(obj.getKhuVucId().trim())){ %>
		      					<option value='<%=rs_khuvuc.getString("pk_seq")%>' selected><%=rs_khuvuc.getString("ten") %></option>
		      				<%}else{ %>
		     					<option value='<%=rs_khuvuc.getString("pk_seq")%>'><%=rs_khuvuc.getString("ten") %></option>
		     			<%}} rs_khuvuc.close(); }catch(java.sql.SQLException e){} }%>
                     </select>
                </TD>
            </TR> 	
            <TR>
                <TD class="plainlabel">Chọn nhà phân phối</TD>
                <TD class="plainlabel" colspan="2">
                    <select name="nhappid" id="nhappid" onchange="javascript:loadnhapp();">
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
                <TD class="plainlabel">Diễn giải </TD>
                <TD class="plainlabel" colspan="2"><input type="text" name="diengiai" id ="diengiai" size="50" value ='<%= obj.getDiengiai() %>'> </TD>
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
                <TD class="plainlabel" width="15%">Ngày hóa đơn:Từ ngày</TD>
                <TD class="plainlabel" colspan="2">
                     <input  type="text" name="ngayhoadon" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" onchange="loadnhapp();"
                          value="<%=obj.getTungay() %>" maxlength="10" />
                </TD> 
                         
            </TR> 
            <TR>
                <TD class="plainlabel" width="15%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Đến ngày</TD>
                <TD class="plainlabel" colspan="2">
                     <input type="text" name="ngayhoadon1" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" onchange="loadnhapp();"
                             value="<%= obj.getDenngay() %>" maxlength="10" >
                </TD> 
                             
            </TR> 
             <TR>
                <TD class="plainlabel" valign="middle">Số tiền thanh toán còn lại </TD>
                <TD class="plainlabel" valign="middle" colspan="2">
                    <input type="text" name="sodudau" id="sodudau" value="<%=Math.round(obj.getNoCu())%>" readonly style="text-align:right"> VND
                </TD>                   
            </TR>   
            <TR>
                <TD class="plainlabel" valign="middle">Thanh toán </TD>
                <TD class="plainlabel" valign="middle">
                    <input type="text" name="thanhtoan" id="thanhtoan" value="<%=Math.round(obj.getSotien()) %>"  style="text-align:right" > VND
                </TD>  
                <TD class="plainlabel" valign="middle">Còn lại 
                	<input type="text" name="conlai"  id="conlai" value="" readonly style="text-align:right"> VND
                </TD>                 
            </TR>           
                <tr >
                        <td class="plainlabel" colspan="2">
                            <a class="button" href="javascript:thanhtoan();">
                                <img style="top: -4px;" src="../images/button.png" alt="">Thực hiện </a>&nbsp;&nbsp;&nbsp;&nbsp;
                         </td>
                         <TD class="plainlabel" colspan="2">
                         </TD>
                    </tr>    					
        </TABLE>
       
        <hr>
            <div style="width:100%; float:none" align="left">
            <table style="width:100%;" cellpadding="3px" cellspacing="1px">
                <tr class="plainlabel">
                    <th align="center" width="20%">Đơn hàng</th>
                    <th align="center" width="20%">Ngày ĐH</th>
                    <th align="center" width="15%">Tiền ĐH</th>
                    <th align="center" width="10%">Đã thanh toán</th>
                    <th align="center" width="15%">Trả hết <input type="checkbox" name="checkall1" onclick="javascript=checkall(this.checked);" "></th>
                    <th align="center" width="10%">Thanh toán</th>
                    <th align="center"width="10%">Còn lại</th>
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
					<TD align="center"> <input type ="hidden" name="iddonhang" value="<%=donhang.getIdDonHang()%>"><%=donhang.getIdDonHang() %></TD>
					<TD align="center"><%=donhang.getNgayDat() %><input type="hidden" value="<%=donhang.getNgayDat() %>" name="ngaydathang"> </TD>
					<TD align="center"><input type ="hidden" name ="tiendonhang1"  value ='<%=Math.round(donhang.getTienDonHang()) %>'><%= Math.round(donhang.getTienDonHang()) %></TD>
					<TD align="center"><input type ="hidden" name ="dathanhtoan1"  value ='<%=donhang.getDaThanhToan() %>'><%=donhang.getDaThanhToan() %></TD>
					<%if(donhang.getId().equals("1")) {%>
					 <TD align="center"><input type ="checkbox" name ="checkone" checked="checked" onclick="recheck();"> <input type="hidden" value="<%=donhang.getId()%> " name="valuecheck" ></TD>
					<%}else{ %>
						<TD align="center"><input type ="checkbox" name ="checkone" onclick="recheck();"> <input type="hidden" value="<%=donhang.getId()%> " name="valuecheck" ></TD>
					<%} %>
					 <TD align="center"><input type ="text" style="width:100%" name ="thanhtoan1" value="<%=Math.round(donhang.getTienThanhToan()) %>" onkeypress="return keypress(event);" onchange = "tinhtienthua();" ></TD>
					 <TD align="center"><input type ="text" style="width:100%"  name ="conlai1" readonly ></TD>
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

