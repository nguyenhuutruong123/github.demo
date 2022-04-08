<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.dondathang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpDondathangList obj = (IErpDondathangList)session.getAttribute("obj"); 
%>

<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<% ResultSet nppRs = obj.getNppRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
   ResultSet vungs = obj.getVungRs();
   ResultSet khuvucs = obj.getKhuvucRs();
   ResultSet kenhs = obj.getKbhRs();
   NumberFormat formatter = new DecimalFormat("#,###,###");
%>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpDondathangSvl","", userId);
		/* quyen = util.Getquyen("ErpDondathangSvl","&loaidonhang="+obj.getLoaidonhang()+"",userId); */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>dms - Project</title>
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
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
	    document.forms["ckParkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["ckParkForm"].action.value = "Tao moi";
	    document.forms["ckParkForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["ckParkForm"].tungay.value = "";
	    document.forms["ckParkForm"].denngay.value = "";
	    document.forms["ckParkForm"].trangthai.value = "";
	    
	    document.forms["ckParkForm"].kenhId.value = "";
	    document.forms["ckParkForm"].vungId.value = "";
	    document.forms["ckParkForm"].khuvucId.value = "";
	    
	    document.forms["ckParkForm"].nppId.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
	 
	 function processing(id,chuoi){
		 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";
		 document.getElementById(id).href = chuoi;
		  
	}
	 
	 function DMS_ChietKhauThanhToan()
	 {   
		document.forms["ckParkForm"].action.value = "DMS_ChietKhauThanhToan";
	    document.forms["ckParkForm"].submit();
	 }
	 
	</SCRIPT>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
		$(document).ready(function()
		{
			$(".select2").select2();
		});
	</script>
	
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		th {
		cursor: pointer;
		}	
  	</style>
	
	

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="ckParkForm" method="post" action="../../ErpDondathangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="loaidonhang" value='<%= obj.getLoaidonhang() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 


<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý bán hàng > Chức năng > Đơn đặt hàng
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
                	 <TR>
                        <TD class="plainlabel" width="100px">Từ ngày</TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel" width="100px">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" name="denngay" id="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                    <TR>
						<TD class="plainlabel">Kênh bán hàng</TD>
						<TD class="plainlabel">
							<select name="kenhId" id="kenhId" onchange="submitform();">
								<option value="" selected>All</option>
								<%if (kenhs != null){
										while (kenhs.next()) {
											if (kenhs.getString("pk_seq").equals(obj.getKbhId())) {%>
											<option value="<%=kenhs.getString("pk_seq")%>" selected><%=kenhs.getString("diengiai")%></option>
								<%} else { %>
									<option value="<%=kenhs.getString("pk_seq")%>"><%=kenhs.getString("diengiai")%></option>
								<%}}}%>
							</select>
						</TD>
						
						<TD class="plainlabel">Miền</TD>
						<TD class="plainlabel">
							<select name="vungId" id="vungId" onchange="submitform();">
							<option value="" selected>All</option>
							<%if (vungs != null){
									while (vungs.next()) {
										if (vungs.getString("pk_seq").equals(obj.getVungId())) {%>
									<option value="<%=vungs.getString("pk_seq")%>" selected><%=vungs.getString("ten")%></option>
								<%} else {%>
									<option value="<%=vungs.getString("pk_seq")%>"><%=vungs.getString("ten")%></option>
							<%}}}%>
						</select>
						</TD>
                   	</TR>
                     <TR>
                     	<TD class="plainlabel">Khu Vực</TD>
						<TD class="plainlabel">
						<select name="khuvucId" id="khuvucId" onchange="submitform();">
							<option value="" selected>All</option>
							<%if (khuvucs != null){
									while (khuvucs.next()) {
										if (khuvucs.getString("pk_seq").equals(obj.getKhuvucId())) {%>
											<option value="<%=khuvucs.getString("pk_seq")%>" selected><%=khuvucs.getString("ten")%></option>
									<%} else {%>
										<option value="<%=khuvucs.getString("pk_seq")%>"><%=khuvucs.getString("ten")%></option>
									<%}}}%>
						</select>
						</TD>
                  
                     	<TD class="plainlabel" width="100px">Nhà phân phối</TD>
                        <TD class="plainlabel">
                            <select name = "nppId" id="nppId" class="select2" style="width: 200px" onchange="submitform();" >
	                    		<option value=""> </option>
	                        	<%
	                        		if(nppRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(nppRs.next())
	                        			{  
	                        			if( nppRs.getString("pk_seq").equals(obj.getNppTen())){ %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
	                        		 <% } } nppRs.close();} catch(Exception ex){}
	                        		}
	                        	%>
	                    	</select>
                        </TD>
                     	</TR>
                     	<TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" colspan = '1'>
                           <select name="trangthai" onchange="submitform();"  >
								<% if (obj.getTrangthai().equals("0")){%>
								  	<option value="0" selected>Chưa chốt</option>
								  	<option value="1">Chờ duyệt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3" >Đã hủy</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("1")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1" selected>Chờ duyệt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3" >Đã hủy</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("2")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Chờ duyệt</option>
								  	<option value="2" selected>Đã duyệt</option>
								  	<option value="3" >Đã hủy</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("3")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Chờ duyệt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3" selected >Đã hủy</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("4")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Chờ duyệt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3"  >Đã hủy</option>
								  	<option value="4" selected >Hoàn tất</option>
								  	<option value="" ></option>
								<%} else  {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Chờ duyệt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3" >Đã hủy</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="" selected ></option>
							<%} %>
                           </select>
                        </TD>  
                        
                        <TD class="plainlabel" >Số đơn hàng</TD>
									<TD class="plainlabel">
										<input type="text" name="sodonhang" size="11" value="<%= obj.getSoDonDathang() %>" onChange="submitform();">
									</TD>
                        
                                              
                    </TR>    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                
                                  <!-- <a class="button2" href="javascript:DMS_ChietKhauThanhToan()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Tạo chiết khấu thanh toán</a>&nbsp;&nbsp;&nbsp;&nbsp; -->
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Đơn đặt hàng </span>&nbsp;&nbsp;
            	<%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a><%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" style="width: 5%" >Mã số</TH>
	                    <TH align="center" style="width: 7%" >Ngày đặt</TH>
	                    <TH align="center" style="width: 8%" >Vùng</TH>
	                    <TH align="center" style="width: 13%" >Nhà phân phối</TH>
	                    <TH align="center" style="width: 5%" >Số SO</TH>
	                    <TH align="center" style="width: 5%" >Kho đặt</TH>
	                    <TH align="center" style="width: 5%" >Tổng tiền</TH>
	                    <TH align="center" style="width: 8%" >Trạng thái</TH>
	                    <TH align="center" style="width: 7%" >Ngày tạo</TH>
	                    <TH align="center" style="width: 8%" >Người tạo</TH>
	                    <TH align="center" style="width: 8%" >Ngày sửa</TH>
	                    <TH align="center" style="width: 10%" >Người sửa</TH>
	                    <TH align="center" style="width: 10%" >Tác vụ</TH>
	                </TR>
					<%
                 		if(nhapkhoRs != null)
                 		{
                 			int m = 0;
                 			while(nhapkhoRs.next())
                 			{  		
                 				double ckThanhToan =nhapkhoRs.getDouble("ckThanhToan");
                 				String chuoiFORMAT = "";
                 				String diengiai="";
	                    		if(ckThanhToan>0)
	                    		{
	                    			diengiai= " Chiết khấu "+ formatter.format( ckThanhToan ) ; 	
	                    		}
	                    		
	                    		if(nhapkhoRs.getString("NOTE").trim().length() > 0 && !nhapkhoRs.getString("NOTE").contains("Convert từ đề nghị") )
             						chuoiFORMAT = " style='color: red;' onMouseover=\"ddrivetip('" + nhapkhoRs.getString("NOTE") + "', 250)\"; onMouseout='hideddrivetip()' ";
             						else  if(ckThanhToan>0)
             						{
             							chuoiFORMAT = " style='color: blue;' onMouseover=\"ddrivetip('"+diengiai+"', 250)\"; onMouseout='hideddrivetip()' ";
             						}
                 				
                 				if((m % 2 ) == 0) 
                 				{  
                 				%>
		                         	<TR class='tbdarkrow' <%= chuoiFORMAT %> >
		                        <%}else{ %>
		                          	<TR class='tblightrow' <%= chuoiFORMAT %> >
		                        <%} %>
		                    <TD align="center"><%= nhapkhoRs.getString("PK_SEQ") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("NGAYDONHANG") %></TD>
		                     <TD ><%= nhapkhoRs.getString("vungTEN") %></TD>
		                    <TD ><%= nhapkhoRs.getString("nppTEN") %></TD>
		                    <TD ><%= nhapkhoRs.getString("soso")==null?"":nhapkhoRs.getString("soso") %></TD>   
		                    <TD ><%= nhapkhoRs.getString("khoTEN") %></TD>  
		                    <TD ><%= formatter.format(nhapkhoRs.getDouble("AVAT")) %></TD>  
		                    	 <TD align="center">
		                    	<%
		                    	
		                    		
		                    		int TT_DuyetLai=nhapkhoRs.getInt("TT_DuyetLai");
		                    		String trangthai = "";
		                    	
		                    		String tt = nhapkhoRs.getString("trangthai");
		                    		if(tt.equals("0")) //NPP TAO
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    			{
		                    				trangthai += "Chờ duyệt";
		                    			}
		                    			else if(tt.equals("2"))
		              					{
		              						trangthai +="Đã duyệt";
		              						if(nhapkhoRs.getInt("DaXuatKho") >0 )
		              							trangthai +="Đã xuất kho ";
		              					}
		                    			else if(tt.equals("3"))
		                    			{
		                    				trangthai += "Đã hủy";
	                    				}
		                    			else if(tt.equals("4"))
		                    			{
		                    				trangthai += " Hoàn tất";
	                    				}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= nhapkhoRs.getString("NGUOITAO") %></TD>
         					<TD align="center"><%= nhapkhoRs.getString("NGAYSUA") %></TD>
							<TD align="center"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>
		                    
		                    <%if(quyen[Utility.SUA]!=0){ %>
		                   		 <A id='<%="copydhid" + nhapkhoRs.getString("PK_SEQ") %>' href="">
								<img  src="../images/copy20.png" alt="Copy đơn hàng" width="20" height="20"  border=0	onclick="if(!confirm('Bạn có muốn copy đơn hàng ( <%= nhapkhoRs.getString("pk_seq") %> ) này?')) {return false ;}else{ processing('<%="copydhid" + nhapkhoRs.getString("pk_seq") %>' , '../../ErpDondathangUpdateSvl?userId=<%=userId%>&copy=<%= nhapkhoRs.getString("pk_seq") %>');}" >
								</A>&nbsp;
                                	<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangUpdateSvl?userId="+userId+"&update="+nhapkhoRs.getString("PK_SEQ") +"&tungay="+obj.getTungay()+"&denngay="+obj.getDenngay()+"&vungId="+obj.getVungId()+"&khuvucId="+obj.getKhuvucId()+"&kbhId="+obj.getKbhId()+"&nppId="+obj.getNppTen()+"&trangthai="+obj.getTrangthai()+"")%>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                               <% } %>
                                <%if(quyen[Utility.CHOT]!=0){ %>
    	                             <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +  "ErpDondathangSvl?userId="+userId+"&chot="+ nhapkhoRs.getString("PK_SEQ") +"&loaidonhang="+ obj.getLoaidonhang() +"&tungay="+obj.getTungay()+"&denngay="+obj.getDenngay()+"&vungId="+obj.getVungId()+"&khuvucId="+obj.getKhuvucId()+"&kenhId="+obj.getKbhId()+"&nppId="+obj.getNppTen()+"&trangthai="+obj.getTrangthai()+"")%>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt đơn đặt hàng này?')) return false;"></A>&nbsp; 
	                           		<%-- <A href=""><img src="../images/Chot.png" alt="Chốt"  title="Chốt"  width="20" height="20"  border=0	onclick="if(!confirm('Bạn có muốn chốt đơn hàng này?')) {return false ;}else{ processing('<%="chotdhid" + nhapkhoRs.getString("pk_seq") %>' , '../../ErpDondathangSvl?userId=<%=userId%>&chot=<%= nhapkhoRs.getString("PK_SEQ") %>&loaidonhang=<%= obj.getLoaidonhang() %>&tungay=<%=obj.getTungay()%>&denngay=<%=obj.getDenngay()%>&vungId=<%=obj.getVungId()%>&khuvucId=<%=obj.getKhuvucId()%>&kenhId=<%=obj.getKbhId()%>&nppId=<%=obj.getNppTen()%>&trangthai=<%=obj.getTrangthai()%>');}" >
														</A> --%>
	                           
	                           
	                            <% } %>
	                            
	                           
                                
                                <%if(quyen[Utility.XOA]!=0){ %>
                                	<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangSvl?userId="+userId+"&delete="+ nhapkhoRs.getString("PK_SEQ") +"&loaidonhang="+ obj.getLoaidonhang() +"&tungay="+obj.getTungay()+"&denngay="+obj.getDenngay()+"&vungId="+obj.getVungId()+"&khuvucId="+obj.getKhuvucId()+"&kenhId="+obj.getKbhId()+"&nppId="+obj.getNppTen()+"&trangthai="+obj.getTrangthai()+"")%>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa đơn đặt hàng này?')) return false;"></A>
                              	<% } %>	
                              	<%if(quyen[Utility.XEM]!=0){ %>
			                    		<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangUpdateSvl?userId="+userId+"&display="+ nhapkhoRs.getString("PK_SEQ") +"")%>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
			                    		<A href="../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangExcelSvl?userId="+userId+"&excel="+nhapkhoRs.getString("PK_SEQ") +"")%>">
																<img src="../images/excel.gif" alt="In đơn hàng chi tiết Pdf" width="20"
																height="20" longdesc="In file Pdf" border=0>
																</A>
			                    	<% } %>	
                              									
		                    <%} else{ %>
		                    
			                    	<%if(quyen[Utility.XEM]!=0){ %>
			                    		<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangUpdateSvl?userId="+userId+"&display="+ nhapkhoRs.getString("PK_SEQ") +"")%>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
			                    		<A href="../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangExcelSvl?userId="+userId+"&excel="+nhapkhoRs.getString("PK_SEQ") +"")%>">
																<img src="../images/excel.gif" alt="In đơn hàng chi tiết Pdf" width="20"
																height="20" longdesc="In file Pdf" border=0>
																</A>
			                    	<% } %>	
			                    	  <% if(tt.equals("1")){ %>
			                    	 <%if(quyen[Utility.CHOT]!=0){ %>
    	                            <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangSvl?userId="+userId+"&openOrder="+ nhapkhoRs.getString("PK_SEQ") +"&loaidonhang="+ obj.getLoaidonhang() +"&tungay="+obj.getTungay()+"&denngay="+obj.getDenngay()+"&vungId="+obj.getVungId()+"&khuvucId="+obj.getKhuvucId()+"&kenhId="+obj.getKbhId()+"&nppId="+obj.getNppTen()+"&trangthai="+obj.getTrangthai()+"")%>"><img src="../images/unChot.png" alt="Chuyển về Npp" title="Chuyển về Npp" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chuyển đơn đặt hàng này về NPP ?')) return false;"></A>&nbsp;
	                            <% } }%>
	                            
	                             <% if(tt.equals("2")){ %>
			                    	 <%if(quyen[Utility.HUYCHOT]!=0){ %>
    	                            <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangSvl?userId="+userId+"&MoChot="+ nhapkhoRs.getString("PK_SEQ") +"&loaidonhang="+ obj.getLoaidonhang() +"&tungay="+obj.getTungay()+"&denngay="+obj.getDenngay()+"&vungId="+obj.getVungId()+"&khuvucId="+obj.getKhuvucId()+"&kenhId="+obj.getKbhId()+"&nppId="+obj.getNppTen()+"&trangthai="+obj.getTrangthai()+"")%>"><img src="../images/unChot.png" alt="Chuyển về Npp" title="Mở chốt đơn đặt hàng" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn mở chốt đơn đặt hàng này?')) return false;"></A>&nbsp;
	                            <% } }%>
		                    <% } %>
		                    
		                    	
		                    
		                    </TD>
		                </TR>
                     <% m++; } nhapkhoRs.close(); } %>
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
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
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
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
	if(nhapkhoRs != null){ nhapkhoRs.close(); nhapkhoRs = null;}
	if(nppRs != null){ nppRs.close(); nppRs = null;}
	if(vungs != null){ vungs.close(); vungs = null;}
	if(khuvucs != null){ khuvucs.close(); khuvucs = null;}
	if(kenhs != null){ kenhs.close(); kenhs = null;}
	
	if(obj != null){
		obj.DBclose(); obj =null;		
	}
	session.setAttribute("obj",null); 
	
	
	
	
}%>