<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.cttrungbay.*" %>
<%@ page  import = "geso.dms.center.beans.cttrungbay.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% ICttrungbay cttbBean = (ICttrungbay)session.getAttribute("cttbBean"); %>
<% List<INhomsptrungbay> nsptbList = cttbBean.getNsptbList(); %>
<% ResultSet tratbRs = cttbBean.getTratbRs(); %>



<% ResultSet kbhRs = cttbBean.getKbhRs(); %>
<% ResultSet vungRs = cttbBean.getVungRs(); %>
<% ResultSet kvRs = cttbBean.getKhuvucRs(); %>
<% ResultSet nppRs = cttbBean.getNppRs(); %>

<% ResultSet loaikhRS = (ResultSet)cttbBean.getLoaikhRs(); %>
<% ResultSet hangkhRS = (ResultSet)cttbBean.getHangkhRs(); %>
<% ResultSet vitrikhRS = (ResultSet)cttbBean.getVitrikhRs(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

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
	<script type="text/javascript" src="../scripts/cttrungbayList.js"></script>
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
            $(".button1").hover(function(){
                $(".button1 img")
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
	
   <script>
	  $(document).ready(function() {
			$("#accordion").accordion({autoHeight: false}); //autoHeight content set false
			$( "#accordion" ).accordion( "option", "icons", { 'header': 'ui-icon-plus', 'headerSelected': 'ui-icon-minus' } );
			$( "#accordion" ).accordion({ active: <%= cttbBean.getActive() %> });
	  });
  </script>
  
    <link media="screen" rel="stylesheet" href="../css/colorbox.css">
	<!-- <script src="../scripts/colorBox/jquery.min.js"></script> -->
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	$(".button1").colorbox({width:"35%", inline:true, href:"#nhomsptrungbay"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Best - Project.");
                return false;
            });
            
            $(".button2").colorbox({width:"35%", inline:true, href:"#tratrungbay"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Best - Project.");
                return false;
            });
        });
    </script>
    <script language="javascript" type="text/javascript">
		
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cttbForm" method="post" action="../../CttrungbayUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%= cttbBean.getId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="ttbTungay" value=''>
<input type="hidden" name="ttbDenngay" value=''>
<input type="hidden" name="ttbDiengiai" value=''>
<input type="hidden" name="nsptbTungay" value=''>
<input type="hidden" name="nsptbDenngay" value=''>
<input type="hidden" name="nsptbDien_giai" value=''>
<input type="hidden" name="active" value='<%= cttbBean.getActive() %>'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Qu???n l?? tr??ng b??y > Ch????ng tr??nh tr??ng b??y > Hi???n th???
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Ch??o m???ng ban<%= userTen %> &nbsp; &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../CttrungbaySvl?userId=<%=userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        
    </div>
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> B??o l???i nh???p li???u</legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= cttbBean.getMessage() %></textarea>
		         <% cttbBean.setMessage(""); %>
    	</fieldset>
  	</div>
    <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
    <fieldset>
    	<legend class="legendtitle">Khai b??o ch????ng tr??nh tr??ng b??y</legend>
        <div style="width:100%; float:none" align="left">
                <TABLE width="100%" cellpadding="6" cellspacing="0">								
                    <TR>
                        <TD class="plainlabel">Scheme </TD>
                        <TD class="plainlabel"  ><input type="text" name="scheme" id="scheme" size="30" value="<%= cttbBean.getScheme() %>"></TD>
                                   		
                        <TD class="plainlabel" valign="top">Di???n gi???i </TD>
                        <TD class="plainlabel" valign="top" colspan="3" ><textarea name="diengiai" id="diengiai" style="width:400px" rows="1"><%= cttbBean.getDiengiai() %></textarea></TD>
                    </TR>               
                    <TR>
                        <TD class="plainlabel" >Th???i gian t??nh doanh s???</TD>
                        <TD class="plainlabel" colspan="5" >
                            T??? ng??y&nbsp;&nbsp; <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                   id="ngayTds" name="ngayTds" value="<%= cttbBean.getNgayTds() %>" maxlength="10"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;?????n ng??y&nbsp;&nbsp;&nbsp; <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                    id="ngayKtTds" name="ngayKtTds" value="<%= cttbBean.getNgayktTds() %>" maxlength="10"/>
                        </TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" >Th???i gian tr??ng b??y</TD>
                        <TD class="plainlabel" colspan="5" >
                           T??? ng??y &nbsp;&nbsp; <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                   id="ngayTb" name="ngayTb" value="<%= cttbBean.getNgayTb() %>" maxlength="10"/>
                       &nbsp;&nbsp;&nbsp;&nbsp;?????n ng??y &nbsp;&nbsp; <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                    id="ngayKtTb" name="ngayKtTb" value="<%= cttbBean.getNgayktTb() %>" maxlength="10"/>
                       
                        </TD>
                    </TR>
                 
                    <TR style="display: none">
                        <TD class="plainlabel" >Th???i gian ????ng k??</TD>
                        <TD class="plainlabel" colspan="5" >
                        <% if(cttbBean.getType().equals("2")){ %>
                            <input type="checkbox" value="2" name="type" id ="type" checked>T??? ng??y k???t th??c t??nh doanh s??? ?????n ng??y k???t th??c tr??ng b??y
                        <%}else{ %>
                        	<input type="checkbox" value="2" name="type" id ="type">T??? ng??y k???t th??c t??nh doanh s??? ?????n ng??y k???t th??c tr??ng b??y
                        <%} %> 
                        </TD>
                    </TR>
                    
                     <TR style="display: none">
                        <TD class="plainlabel" ></TD>
                        <TD class="plainlabel" colspan="5" >
                           T??? ng??y &nbsp;&nbsp; <input type="text" size="10" name="ngay1" id ="ngay1" readonly value ="">
                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;?????n ng??y&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" size="10" id ="ngay2" name="ngay2" readonly value="">
                         </TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" >Th???i gian ????ng k??</TD>
                        <TD class="plainlabel" colspan="5" >
                           T??? ng??y &nbsp;&nbsp; <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                   id="ngayDk" name="ngayDk" value="<%= cttbBean.getNgayBddk() %>" maxlength="10"/>
                       &nbsp;&nbsp;&nbsp;&nbsp;?????n ng??y &nbsp;&nbsp; <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                    id="ngayKtDk" name="ngayKtDk" value="<%= cttbBean.getNgayKtdk() %>" maxlength="10"/>
                       
                        </TD>
                    </TR>

					<TR style="display:none">
                        <TD class="plainlabel"></TD>
                        <TD class="plainlabel" colspan="5" >
                         <%--  	<% System.out.println("Gia tri dang ky them la: " + cttbBean.isDangkythem());  
                          	if(cttbBean.isDangkythem().equals("1")){ %>
                          		<input type="checkbox" name="isDkthem" checked="checked"> Cho ph??p ????ng k?? th??m.
                          	<%} else{ %>
                          		<input type="checkbox" name="isDkthem"> Cho ph??p ????ng k?? th??m.
                          	<%} %>
                          	 --%>
                          
	                        <% if(cttbBean.getPhaimuadonhang().equals("1")){ %>
	                            <input type="checkbox" value="1" name="phaimuadonhang" id ="type" checked>Ph???i mua ????n h??ng
	                        <%}else{ %>
	                        	<input type="checkbox" value="1" name="phaimuadonhang" id ="type">Ph???i mua ????n h??ng
	                        <%} %> 
                         </TD>
                    </TR>

                    <TR>
                        <TD class="plainlabel" >S??? l???n thanh to??n </TD>
                        <TD class="plainlabel" >
                        	<input type="text" name="solantt" id="solantt" size="30" style="text-align:right" value="<%= cttbBean.getSolantt() %>" onkeypress="return keypress(event);">
                        </TD>
                        <TD class="plainlabel" >Ng??n s??ch</TD>
                        <TD class="plainlabel" colspan="3" >
                        	<input type="text" name="ngansach" id="ngansach" size="30" style="text-align:right" value="<%= cttbBean.getNgansach() %>" onkeypress="return keypress(event);">
                        </TD>
                        
                    </TR>
                    <TR>
                        <TD class="plainlabel" >M???c ph??n b???</TD>
                        <TD class="plainlabel">
                        	<select name="mucphanbo" onchange="submitform();" >
                            	<% if( cttbBean.getMucphanbo().equals("0") ){ %>
                            		<option value="0" selected="selected" >To??n qu???c</option>
                            		<option value="1" >Mi???n</option>
                            		<option value="2" >V??ng</option>
	                            <%}else{ if( cttbBean.getMucphanbo().equals("1") ){ %>
	                            	<option value="0" >To??n qu???c</option>
                            		<option value="1" selected="selected" >Mi???n</option>
                            		<option value="2" >V??ng</option>
	                            <%} else { %> 
	                            	<option value="0" >To??n qu???c</option>
                            		<option value="1" >Mi???n</option>
                            		<option value="2" selected="selected" >V??ng</option>
	                            <% } }  %>
                            </select>
                        </TD>
                        <TD class="plainlabel" >???? s??? d???ng</TD>
                        <TD class="plainlabel" colspan="3" >
                        	<input type="text" name="dasudung" id="dasudung" size="30" style="text-align:right" value="<%= cttbBean.getDasudung() %>" readonly >
                        </TD>
                    </TR>	
                    <% if( cttbBean.getMucphanbo().equals("1") || cttbBean.getMucphanbo().equals("2") ) { %>
	                    <TR>
	                    	<% if(cttbBean.getMucphanbo().equals("1") ) { %>
		                    	<TD class="plainlabel" >Mi???n </TD>
		                        <TD class="plainlabel" colspan="5" >
		                            <select name="vung" multiple="multiple"  >
							       		<option value="" >Ch???n h???t</option>
				                        <% if(vungRs != null) {
				                         while(vungRs.next()) 
				                         {
				                           if(cttbBean.getVungIds().indexOf(vungRs.getString("pk_seq")) >= 0 ){ %>
				                             <option value="<%= vungRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= vungRs.getString("ten") %></option>
				                         <%}else { %>
				                         	<option value="<%=vungRs.getString("pk_seq") %>" style="padding: 2px"><%= vungRs.getString("ten") %></option>
				                         <%} }}%>        	
				                    </select>
		                        </TD>
	                        <% } else { %>
	                        	<TD class="plainlabel" >V??ng </TD>
		                        <TD class="plainlabel" colspan="5" >
		                            <select name="khuvuc" multiple="multiple"  >
										<option value="">Ch???n h???t</option>
							            <% if(kvRs != null) {
							            	while(kvRs.next())
					                          {
					                            if(cttbBean.getKhuvucIds().indexOf(kvRs.getString("pk_seq")) >= 0 )
					                            { %>
					                              <option value="<%=kvRs.getString("pk_seq") %>" selected style="padding: 2px"><%=kvRs.getString("ten") %></option> 
					                          <%}else { %>
					                          	<option value="<%=kvRs.getString("pk_seq") %>" style="padding: 2px"><%=kvRs.getString("ten") %></option>
					                          <%}}}%>
				                    </select>
		                        </TD>
	                        <% } %>
	                    </TR>
                    <% } %>
                    
                    <TR>
                    	<TD width="120px;" class="plainlabel" >Lo???i kh??ch h??ng </TD>
                        <TD width="220px;" class="plainlabel"  >
                            <select name="loaikhId" id="loaikhId" multiple="multiple"  >
					        	<option value=""></option>
		                        <% if(loaikhRS != null) {
		                         while(loaikhRS.next()) 
		                         {
		                           if(cttbBean.getLoaikhIds().indexOf(loaikhRS.getString("pk_seq")) >= 0 ){ %>
		                             <option value="<%= loaikhRS.getString("pk_seq") %>" selected style="padding: 2px" ><%= loaikhRS.getString("ten") %></option>
		                         <%}else { %>
		                         	<option value="<%= loaikhRS.getString("pk_seq") %>" style="padding: 2px"><%= loaikhRS.getString("ten") %></option>
		                         <%} } loaikhRS.close(); }%>        	
		                    </select>
		                 </TD>
		                 <TD width="120px;" class="plainlabel" >H???ng kh??ch h??ng </TD>
                        <TD width="220px;" class="plainlabel"  >
                            <select name="hangkhId" id="hangkhId" multiple="multiple"  >
					        	<option value=""></option>
		                        <% if(hangkhRS != null) {
		                         while(hangkhRS.next()) 
		                         {
		                           if(cttbBean.getHangkhIds().indexOf(hangkhRS.getString("pk_seq")) >= 0 ){ %>
		                             <option value="<%= hangkhRS.getString("pk_seq") %>" selected style="padding: 2px" ><%= hangkhRS.getString("ten") %></option>
		                         <%}else { %>
		                         	<option value="<%= hangkhRS.getString("pk_seq") %>" style="padding: 2px"><%= hangkhRS.getString("ten") %></option>
		                         <%} } hangkhRS.close(); }%>        	
		                    </select>
		                 </TD>
		                 <TD width="120px;" class="plainlabel" >V??? tr?? kh??ch h??ng </TD>
                        <TD class="plainlabel"  >
                            <select name="vitrikhId" id="vitrikhId" multiple="multiple"  >
					        	<option value=""></option>
		                        <% if(vitrikhRS != null) {
		                         while(vitrikhRS.next()) 
		                         {
		                           if(cttbBean.getVitrikhIds().indexOf(vitrikhRS.getString("pk_seq")) >= 0 ){ %>
		                             <option value="<%= vitrikhRS.getString("pk_seq") %>" selected style="padding: 2px" ><%= vitrikhRS.getString("ten") %></option>
		                         <%}else { %>
		                         	<option value="<%= vitrikhRS.getString("pk_seq") %>" style="padding: 2px"><%= vitrikhRS.getString("ten") %></option>
		                         <%} } vitrikhRS.close(); }%>        	
		                    </select>
		                 </TD>
                    </TR>
                    					
                </TABLE>       
         </div>
         <div id="accordion"  style="width:100%; height:auto; float:none; font-size:1.0em" align="left">
            <h1 style="font-size:1.8em"><a href="#">??i???u ki???n tr??ng b??y</a></h1>
			<div style="height:auto">
                <TABLE width="100%" class="tabledetail" border="0" cellspacing="1px" cellpadding="1px">
                    <TR class="plainlabel">
                        <TH align="center" width="10%">M??</TH>
                        <TH align="left" width="50%"> Di???n gi???i </TH>
                        <TH align="center" width="10%"> T???ng l?????ng</TH>
                        <TH align="center" width="10%">T???ng ti???n</TH>
                        <TH align="center" width="10%">Ph??p to??n</TH>
                    </TR>
					<% 
						int i = 0;
						if(nsptbList.size() > 0){ 
						while(i < nsptbList.size()){	
						Nhomsptrungbay nsptb = (Nhomsptrungbay)nsptbList.get(i); %>
		                    <TR class='tbdarkrow'>
		                        <TD align="center"><input type="text" name="nsptbId"  value="<%= nsptb.getId() %>" onkeyup="ajax_showOptions(this,'abc',event)" AUTOCOMPLETE="off"></TD>
		                        <TD align="left"><input type="text" name="nsptbDiengiai"  value="<%= nsptb.getDiengiai() %>" readonly></TD>
		                        <TD align="center"><input type="text" name="nsptbTongluong"  value="<%= nsptb.getTongluong() %>" style="text-align:right" readonly></TD>							           
		                        <TD align="center"><input type="text" name="nsptbTongtien"  value="<%= nsptb.getTongtien() %>" style="text-align:right" readonly></TD>
		                        <TD align="center">
		                        	<select name="nsptbPheptoan">
		                        	<% if(nsptb.getPheptoan().equals("2")){ %>
		                                <option value="2" selected>Or</option>
		                                <option value="1">And</option>
		                            <%}else{ %>		                            	
		                                <option value="1" selected>And</option>
		                                <option value="2">Or</option>
		                            <%}%>
		                            </select>
		                        </TD>
		                    </TR>
                    	<% i++; }} %>
                    	<% for(int j = i; j < 20; j++){ %>
                    		<TR class='tbdarkrow'>
		                        <TD align="center"><input type="text" name="nsptbId" value="" onkeyup="ajax_showOptions(this,'abc',event)" AUTOCOMPLETE="off"></TD>
		                        <TD align="left"><input type="text" name="nsptbDiengiai" value="" readonly></TD>
		                        <TD align="center"><input type="text" name="nsptbTongluong"  value="" style="text-align:right" readonly></TD>							           
		                        <TD align="center"><input type="text" name="nsptbTongtien" value="" style="text-align:right" readonly></TD>
		                        <TD align="center">
		                        	<select name="nsptbPheptoan">
		                            	<option value="1" selected="selected">And</option>
		                                <option value="2">Or</option>     
		                            </select>
		                        </TD>
		                    </TR>
                    	<%} %>
                    <TR>
                        <TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
                    </TR>
				</TABLE>
				<br><a class="button1" href="#">
                    <img style="top: -4px;" src="../images/button.png" alt="">T??m ki???m ??i???u ki???n tr??ng b??y</a>
	                <div style='display:none'>
                        <div id='nhomsptrungbay' style='padding:0px 5px; background:#fff;'>
                        	<h4 align="left">T??m ki???m nh??m s???n ph???m tr??ng b??y</h4>
							<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
                            	<tr>
                                	<td width="18%" valign="top" align="left">Di???n gi???i</td>
                                    <td valign="top" align="left"><textarea name="nsptb_diengiai" id="nsptb_diengiai" style="width:250px;" rows="2"><%= cttbBean.getNsptb_diengiai() %></textarea></td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left">T??? ng??y</td>
                                    <td valign="top" align="left">
                                    	<input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                  					id="nsptb_tungay" name="nsptb_tungay" value="<%= cttbBean.getNsptb_tungay() %>" maxlength="10" />
                                    </td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left">?????n ng??y</td>
                                    <td valign="top" align="left">
                                    	<input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                  					id="nsptb_denngay" name="nsptb_denngay" value="<%= cttbBean.getNsptb_denngay() %>" maxlength="10" />
                                    </td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left"></td>
                                    <td valign="top" align="left">
                                    <a class="button" href="javascript:submitform();">
        								<img style="top: -4px;" src="../images/button.png" alt="">T??m ki???m</a>
                                    </td>
                                </tr>
                            </table>
						</div>
	                </div>
     			</div>  
     	
         	 <h1 style="font-size:1.8em"><a href="#">Tr??? tr??ng b??y</a></h1> 
             <div style="height:auto">
              <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                  <TR class="plainlabel">
                      <TH align="center">M??</TH>
                      <TH align="left"> Di???n gi???i </TH>
                      <TH align="center">T???ng l?????ng</TH>
                      <TH align="center">T???ng ti???n</TH>
                      <TH align="center">Lo???i</TH>
                      <TH align="center" width="10%">Ph??p to??n</TH>
                      <TH align="center">Ch???n</TH>
                  </TR>
				  <% int pos = 0; 
				  if(tratbRs != null){
						  try{ while(tratbRs.next()){  %>
			      			<TR class='tbdarkrow'>
		                      <TD align="center"><%= tratbRs.getString("tratbId") %></TD>
		                      <TD align="left"><%= tratbRs.getString("diengiai") %></TD>
		                      <TD align="center"><%= tratbRs.getString("tongluong") %></TD>										                                    
		                      <TD align="center"><%= tratbRs.getString("tongtien") %></TD>
		                      <TD align="center"><%= tratbRs.getString("loai") %></TD>
		                      <TD align="center">
		                        	<select name="tratbPheptoan">
		                        	<%
		                        	String key = tratbRs.getString("tratbId");
		                        	if(cttbBean.getTratbId().containsKey(key)) 
		                        	{
		                        		//System.out.println("Phep toan la: " + cttbBean.getTratbId().get(key) + "\n");
		                        		if(cttbBean.getTratbId().get(key) == 2)
		                        		{%>
		                        			<option value="1">And</option>
		                                	<option value="2" selected="selected">Or</option> 
		                        		<%}
		                        		else{%>
		                        			<option value="1" selected="selected">And</option>
		                                	<option value="2">Or</option> 
		                        		<%}
		                        	}
		                        	else{ %>
		                        		<option value="1" selected="selected">And</option>
		                                <option value="2">Or</option>
		                        	<%}%>
		                            </select>
		                      </TD>
		                      <TD align="center">
		                      <% if(cttbBean.getTratbId().get(key) != null){ %>
		                      		<input type="checkbox" name="tratbIds" value="<%= tratbRs.getString("tratbId") + "," + Integer.toString(pos) %>" checked>
		                      <%}else{ %>
		                      		<input type="checkbox" name="tratbIds" value="<%= tratbRs.getString("tratbId") + "," + Integer.toString(pos) %>" >
		                      <%} %>
		                      </TD>
		                  </TR>
			     <%pos++; } tratbRs.close(); }catch(java.sql.SQLException e){} }%>

                  <TR>
                      <TD align="center" colspan="11" class="plainlabel">&nbsp;</TD>
                  </TR>
              	 </TABLE>   
                  <br><a class="button2" href="#" style="font-size:14px">
                      <img style="top: -4px;" src="../images/button.png" alt="">T??m ki???m tr??? tr??ng b??y</a>
                      <div style='display:none'>
                        <div id='tratrungbay' style='padding:0px 5px; background:#fff;'>
                        	<h4 align="left">T??m ki???m tr??? tr??ng b??y</h4>
							<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
                            	<tr>
                                	<td width="20%" valign="top" align="left">Di???n gi???i</td>
                                    <td valign="top" align="left"><textarea name="ttb_diengiai" id="ttb_diengiai" style="width:250px" rows="1"><%= cttbBean.getTtb_diengiai() %></textarea></td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left">T??? ng??y</td>
                                    <td valign="top" align="left">
                                    	<input type="text" size="10" class="w8em format-d-m-y divider-dash highlight-days-12" 
                                  					id="ttb_tungay" name="ttb_tungay" value="<%= cttbBean.getTtb_tungay() %>" maxlength="10" />
                                    </td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left">?????n ng??y</td>
                                    <td valign="top" align="left">
                                    	<input type="text" size="10" class="w8em format-d-m-y divider-dash highlight-days-12" 
                                  					id="ttb_denngay" name="ttb_denngay" value="<%= cttbBean.getTtb_denngay() %>" maxlength="10" />
                                    </td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left"></td>
                                    <td valign="top" align="left">
                                    <a class="button" href="javascript:submitform();">
        								<img style="top: -4px;" src="../images/button.png" alt="">T??m ki???m</a>
                                    </td>
                                </tr>
                            </table>
						</div>
	                </div>
             </div>
            
            <% if(cttbBean.getMucphanbo().equals("0") ) { %>
            
            <h1 style="font-size:1.8em"><a href="#">Nh?? ph??n ph???i</a></h1>
            <div style="height:auto">
            <TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px">
                <TR class="plainlabel" valign="bottom">
                <th colspan="3">
                   
                   <fieldset style="width: 30%; float: left;"> 
			       <legend>K??nh b??n h??ng &nbsp;</legend> 
			       <select name="kbhIds" multiple="multiple">
                        <% if(kbhRs != null) {
                         while(kbhRs.next()) 
                         {
                           if(cttbBean.getKbhIds().indexOf(kbhRs.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= kbhRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= kbhRs.getString("ten") %></option>
                         <%}else { %>
                         	<option value="<%=kbhRs.getString("pk_seq") %>" style="padding: 2px"><%= kbhRs.getString("ten") %></option>
                         <%}} kbhRs.close(); }%>        	
                    </select>
                    </fieldset>
                    
			       <fieldset style="width: 30%; float: left;">
			       <legend>V??ng &nbsp;</legend> 
			       <select name="vungIds" multiple="multiple">
                        <% if(vungRs != null) {
                         while(vungRs.next()) 
                         {
                           if(cttbBean.getVungIds().indexOf(vungRs.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= vungRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= vungRs.getString("ten") %></option>
                         <%}else { %>
                         	<option value="<%= vungRs.getString("pk_seq") %>" style="padding: 2px"><%= vungRs.getString("ten") %></option>
                         <%}} vungRs.close(); }%>        	
                    </select>
                    </fieldset>
                    
                    <fieldset style="width: 30%; float: left;"> 
					<legend>V??ng &nbsp;</legend>
					<select name="kvIds" multiple="multiple">
			            <% if(kvRs != null) {
			            	while(kvRs.next())
	                          {
	                            if(cttbBean.getKhuvucIds().indexOf(kvRs.getString("pk_seq")) >= 0 )
	                            { %>
	                              <option value="<%= kvRs.getString("pk_seq") %>" selected style="padding: 2px"><%= kvRs.getString("ten") %></option> 
	                          <%}else { %>
	                          	<option value="<%= kvRs.getString("pk_seq") %>" style="padding: 2px"><%= kvRs.getString("ten") %></option>
	                          <%}} kvRs.close(); }%>
                    </select>
                    </fieldset>
			   </th>
				</TR>
                <tr class="plainlabel" style="padding: 5px">
                	<th colspan="3">
                		<a class="button" href="javascript:submitform();">
        					<img style="top: -4px;" src="../images/button.png" alt=""> Hi???n th??? Npp theo ??i???u ki???n</a>
                	</th>
                </tr>
                    <TR class="tbheader">
                        <TH align="center" width="10%">M??</TH>
                        <TH align="left" width="50%"> T??n </TH>
                        <TH align="center" width="10%"> Ch???n t???t c??? <input type ="checkbox" name ="all" onclick ="All()"></TH>
                    </TR>
					<%
					int k = 0;
					while(nppRs.next()) {
					if(k % 2==0){
					%>
	                   	<TR class='tbdarkrow'>
	               <%}else{ %>
	                    <TR class='tblightrow'>
	               <%} %>
                        <TD align="center"><%= nppRs.getString("pk_seq") %></TD>
	                    <TD align="left"><%= nppRs.getString("ten") %></TD>
	                    <% if(cttbBean.getNppIds().indexOf((nppRs.getString("pk_seq"))) >= 0 ) {%>
	                    	<TD align="center"><input type ="checkbox" name ="nppIds" value ="<%= nppRs.getString("pk_seq")%>" checked="checked"></TD>
	                    <%} else {%>
	                      	<TD align="center"><input type ="checkbox" name ="nppIds" value ="<%= nppRs.getString("pk_seq")%>"></TD>
	                  	<%} %>
                   </TR>
	                <% k++;}%>
                    <TR>
                        <TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
                    </TR>
                    </TABLE>
              </div>     
            
            <% } %>
            
         </div>
    </fieldset>
  </div>    
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%

try{
if(tratbRs!=null){
tratbRs.close();
}
if(kbhRs!=null){
	kbhRs.close();
	}
if(kbhRs!=null){
	kbhRs.close();
	}
if(kvRs!=null){
	kvRs.close();
	}
if(nppRs!=null){
	nppRs.close();
	}
cttbBean.DbClose();

}catch(Exception er){	
}
%>
