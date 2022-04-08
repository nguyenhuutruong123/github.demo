<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.thaydoikhuyenmai.*" %>
<%@ page  import = "geso.dms.center.beans.thaydoikhuyenmai.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% IThayDoiKhuyenMai tdkmBean = (IThayDoiKhuyenMai)session.getAttribute("tdkmBean"); %>

<% String userId = (String) session.getAttribute("userId");  %>
<%
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	List<ISanPham> spListOld =tdkmBean.getSpListOld();
	List<ISanPham> spList =tdkmBean.getSpList();
	ResultSet ctkmRs =tdkmBean.getCtkmRs();
	ResultSet trakmRs =tdkmBean.getTrakmRs();
	ResultSet dkkmRs =tdkmBean.getDkkmRs();
	
	ResultSet ctkmRsSuDung =tdkmBean.getCtkmRsSuDung();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Vifon - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <script type="text/javascript" src="../scripts/jquery.min.js"></script>
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
	</style>
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/AjaxThayDoiKhuyenMai.js"></script>
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
    
	<script language="javascript" type="text/javascript">
		function replaces()
		{
			var masp = document.getElementsByName("masp");
			var tensp = document.getElementsByName("tensp");
			var soluong = document.getElementsByName("soluong");
			var i;
			var sodong =  masp.length;
			
			for(i=0; i <sodong; i++)
			{
				for(var k = 0;k <sodong ;k++)
				{
					if(parseInt(k)!=parseInt(i))//khong phai ma hien tai
					{
						if((masp[i].value == masp[k].value) && masp[k].value.length !=0)
							{
								alert("Sản phẩm hiện tại đã có!");
								masp.item(k).value='';
								//return;
							}
					}
				}
				
				if(masp.item(i).value != "")
				{
					var sp = masp.item(i).value;
					var pos = parseInt(sp.indexOf(" - "));
					if(pos > 0)
					{
						masp.item(i).value = sp.substring(0, pos);
						sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
						tensp.item(i).value = sp;
					}
				}
				else
				{
					tensp.item(i).value = "";
					soluong.item(i).value = "";
				}			
			}
			setTimeout(replaces, 50);
		}	
		replaces();
		function submitform()
		{   
		   document.forms["trakmForm"].submit();
		}
		
		function saveform()
		{	
			document.forms["trakmForm"].action.value = "save";
			document.forms["trakmForm"].submit();
		}
				
		function keypress(e)
		{    
			var keypressed = null;
			if (window.event)
				keypressed = window.event.keyCode; //IE
			else
				keypressed = e.which; //NON-IE, Standard
			
			if (keypressed < 48 || keypressed > 57 )
			{ 
				if (keypressed==46 || keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
				{
					return;
				}
				return false;
			}
			
		}
		
		
		
	</script>
	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$(".select2").select2();
	
});
</script>
	

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="trakmForm" method="post" action="../../ThayDoiKhuyenMaiUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý khuyến mại > Thêm sản phẩm CTKM > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;&nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ThayDoiKhuyenMaiSvl?userId=<%=userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" rows="1" style="width: 100%" readonly="readonly" readonly="readonly"><%= tdkmBean.getMsg() %></textarea>
		         <% tdkmBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Thêm sản phẩm CTKM </legend>
        		<div style="float:none; width:100%" align="left">
                <TABLE width="100%" cellpadding="6" cellspacing="0">								
                    <TR>
                        <TD class="plainlabel"  width="15%">Loại</TD>
                        <TD class="plainlabel">
                            <select name="loai" id="loai" style="width:250px;"   onChange="submitform()" class="select2"  disabled="disabled">
                            <option value="" > </option>
                                <% if(tdkmBean.getLoai().equals("1")){ %>
	                                <option value="1" selected>Điều kiện KM</option>
	                                <option value="2" >Trả KM</option>
	                            <%}else{ if(tdkmBean.getLoai().equals("2")){ %>
	                            	<option value="1" >Điều kiện KM</option>
	                                <option value="2"  selected >Trả KM</option>
	                            <%}else{%>
	                            	<option value="1" >Điều kiện KM</option>
	                                <option value="2"  >Trả KM</option>                        
	                            <%}} %>
                            </select>
                         </TD> 
                    </TR>

                    <TR>
						<TD class="plainlabel">Chương trình khuyến mại </TD>
							<TD class="plainlabel">
								<select name="ctkmId"   onChange="submitform()"   id="ctkmId" style="width:250px;"  class="select2"  disabled="disabled">
								<option value="" selected></option>
									<%if (ctkmRs != null)
											while (ctkmRs.next()) {
												if (ctkmRs.getString("pk_seq").equals(tdkmBean.getCtkmId())) {%>
													<option value="<%=ctkmRs.getString("pk_seq")%>" selected><%=ctkmRs.getString("diengiai")%></option>
											<%} else {%>
												<option value="<%=ctkmRs.getString("pk_seq")%>"><%=ctkmRs.getString("diengiai")%></option>
											<%}}%>
								</select>
							</TD>
					</TR>
                    
                    
<%if(tdkmBean.getLoai().equals("1")){ %>
                    <TR>
						<TD class="plainlabel">Điều kiện KM </TD>
							<TD class="plainlabel">
								<select name="dkkmId"  id="dkkmId" style="width:350px;" class="select2"  onChange="submitform()" disabled="disabled">
									<option value="" selected>All</option>
									<%if (dkkmRs != null)
											while (dkkmRs.next()) {
												if (dkkmRs.getString("pk_seq").equals(tdkmBean.getDkkmId()  )) {%>
													<option value="<%=dkkmRs.getString("pk_seq")%>" selected><%=dkkmRs.getString("diengiai")%></option>
											<%} else {%>
												<option value="<%=dkkmRs.getString("pk_seq")%>"><%=dkkmRs.getString("diengiai")%></option>
											<%}}%>
								</select>
							</TD>
					</TR>
					
					<TR>
		                    <TD class="plainlabel">Loại điều kiện</TD>
		                    <TD class="plainlabel">
		                        <select name="type" id="type">
		                        <% if(tdkmBean.getType().equals("1")){ %>                           
		                            <option value="1" selected>Bắt buộc nhập số lượng từng sản phẩm</option>
		                            <option value="2">Bất kỳ trong</option>
		                            <option value=""> </option>
		                        <%}else if(tdkmBean.getType().equals("2")){ %>                      	
		                            <option value="2" selected>Bất kỳ trong</option>
		                            <option value="1">Bắt buộc nhập số lượng từng sản phẩm</option>
		                            <option value=""> </option>
		                        <%}else{ %>
		                        	<option value="" selected> </option>                       	
		                            <option value="1">Bắt buộc nhập số lượng từng sản phẩm</option>      
		                            <option value="2">Bất kỳ trong</option>          
		                        <% } %>
		                        </select>
		                     </TD> 
               		 </TR>
               		 
               		 <TR>
                    <TD width="15%" class="plainlabel" valign="top">Hình thức</TD>
                    <TD class="plainlabel" valign="middle">  
                    <% if(tdkmBean.getHinhthuc().equals("2")){ %>              	
                        <input type="radio" name="option" value="1" id="tonglg" onChange="submitform();" >Nhập tổng lượng &nbsp;
                        <input type="radio" name="option" value="2" id="tongtn" onChange="submitform();" checked>Nhập tổng tiền &nbsp; 
                    <%} else{ %>
                    	<input type="radio" name="option" value="1" id="tonglg" onChange="submitform();" checked>Nhập tổng lượng &nbsp;
                        <input type="radio" name="option" value="2" id="tongtn" onChange="submitform();" >Nhập tổng tiền &nbsp; 
                    <%} %>                    
                    </TD>
                </TR>
                
                <% if(tdkmBean.getHinhthuc().equals("2")){ %>              
	               <TR>
	                    <TD class="plainlabel">Tổng tiền </TD>
	                    <% if(tdkmBean.getType().equals("1")){ %>
	                    	<TD class="plainlabel"><input type="text" name="tongtien" id="tongtien" value="<%= tdkmBean.getTongtien() %>" style="text-align:right" size="15" readonly> VND</TD> 
	                    <%}else{ %>
	                    	<TD class="plainlabel"><input type="text" name="tongtien" id="tongtien" value="<%= tdkmBean.getTongtien() %>" style="text-align:right" size="15" onkeypress="return keypress(event);"> VND</TD> 
	                    <%} %>
	                </TR>
                <%}else{ %>
	                <TR>
	                    <TD class="plainlabel">Tổng lượng </TD>
	                    <% if(tdkmBean.getType().equals("1")){ %>
	                    	<TD class="plainlabel"><input type="text" name="tongluong" id="tongluong" value="<%= tdkmBean.getTongluong() %>" style="text-align:right" size="15" readonly></TD> 
	                    <%}else{ %>
	                    	<TD class="plainlabel"><input type="text" name="tongluong" id="tongluong" value="<%= tdkmBean.getTongluong() %>" style="text-align:right" size="15" onkeypress="return keypress(event);"></TD> 
	                    <%} %>
	                </TR> 
                <%} %>  
               		 
					
					
<%}else if(tdkmBean.getLoai().equals("2")) { %>
					<TR>
						<TD class="plainlabel">Trả KM </TD>
							<TD class="plainlabel">
								<select name="trakmId"  id="trakmId" style="width:350px;" class="select2"  onChange="submitform()" disabled="disabled">
									<option value="" selected>All</option>
									<%if (trakmRs != null)
											while (trakmRs.next()) {
												if (trakmRs.getString("pk_seq").equals(tdkmBean.getTrakmId()  )) {%>
													<option value="<%=trakmRs.getString("pk_seq")%>" selected><%=trakmRs.getString("diengiai")%></option>
											<%} else {%>
												<option value="<%=trakmRs.getString("pk_seq")%>"><%=trakmRs.getString("diengiai")%></option>
											<%}}%>
								</select>
							</TD>
					</TR>
					
					 <TR>
	                        <TD class="plainlabel">Hình thức </TD>
	                        <TD class="plainlabel">
	                            <select name="hinhthuc" id="hinhthuc" >
		                        <% if(tdkmBean.getHinhthuc().equals("1")){ %>                           
		                            <option value="1" selected>Bắt buộc nhập số lượng từng sản phẩm</option>
		                            <option value="2">Bất kỳ trong</option>
		                            <option value=""> </option>
		                        <%}else{if(tdkmBean.getHinhthuc().equals("2")){ %>                      	
		                            <option value="2" selected>Bất kỳ trong</option>
		                            <option value="1">Bắt buộc nhập số lượng từng sản phẩm</option>
		                            <option value=""> </option>
		                        <%}else{ %>
		                        	<option value="" selected> </option>                       	
		                            <option value="1">Bắt buộc nhập số lượng từng sản phẩm</option>      
		                            <option value="2">Bất kỳ trong</option>                    
		                        <% }} %>
	                            </select>
	                        </TD> 
	                    </TR> 
	                    
	                  <TR>
	                        <TD class="plainlabel">Tổng lượng </TD>
	                        <TD class="plainlabel">
	                        <% if(tdkmBean.getHinhthuc().equals("1")){ %>
	                        	<input type="text" name="tongluong" id="tongluong" value="<%= tdkmBean.getTongluong() %>" style="text-align:right" style="width:95%" readonly>
	                        <%}else{ %>
	                        	<input type="text" name="tongluong" id="tongluong" value="<%= tdkmBean.getTongluong() %>" style="text-align:right" style="width:95%" onkeypress="return keypress(event);">
	                        <%} %>
	                        </TD> 
	                    </TR> 		
					
<%} %>					
<%if( tdkmBean.getTrakmId().length()>0 ||tdkmBean.getDkkmId().length()>0 ){ %>                    
                    <TR>
						<TD class="plainlabel">Chương trình khuyến mại sử dụng </TD>
							<TD class="plainlabel">
								<select name="ctkmId"   disabled="disabled" id="ctkmId" style="width:250px;"   multiple="multiple"  >
									<%if (ctkmRsSuDung != null)
											while (ctkmRsSuDung.next()) {
												if (ctkmRsSuDung.getString("pk_seq").equals(tdkmBean.getCtkmId())) {%>
													<option value="<%=ctkmRsSuDung.getString("pk_seq")%>" selected><%=ctkmRsSuDung.getString("diengiai")%></option>
											<%} else {%>
												<option value="<%=ctkmRsSuDung.getString("pk_seq")%>"><%=ctkmRsSuDung.getString("diengiai")%></option>
											<%}}%>
								</select>
							</TD>
					</TR>
<%} %>
              <TR>
                </TABLE>
                </div>
</fieldset>
               
               <div align="left" style="width:100%; float:none; clear:none;">
                <fieldset>
    			<legend class="legendtitle"> Sản phẩm hiện tại </legend>
               
        		   <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
		                <TR class="tbheader">                   
		                 <% if(tdkmBean.getType().equals("1")){ %>
		                	<TH align="center" width="15%">Mã sản phẩm</TH>
		                	<TH align="left" width="73%">Tên sản phẩm</TH>
		               	 	<TH align="center" width="10%">Số lượng</TH>
		                 <%}else{ %>
		                 	<TH align="center" width="15%">Mã sản phẩm</TH>
		                	<TH align="left" width="73%">Tên sản phẩm</TH>
		               	 	<TH align="center" width="10%">Số lượng</TH>
		               
		                 <%} %>
		                </TR>
		                <% 
		               			for(int i = 0; i < spListOld.size(); i++)
		               			{
		               				SanPham sp = (SanPham)spListOld.get(i);  %>
		               				<TR class='tbdarkrow'>
		               					
				                    	<td align="center">
				                    		<input type="text"  value="<%= sp.getMa() %>" name = "spMaOld" onkeyup="ajax_showOptions(this,'abc',event)"  AUTOCOMPLETE="off">
				                    		<input type="hidden"  value="<%= sp.getId() %>" name = "spIdOld" >
				                    		</td>
				                   		<td align="left"><input type="text"  value="<%= sp.getTen() %>" name= "spTenOld" readonly></td>
				                    	<td align="right"><input type="text" name="soluongOld"   value="<%=sp.getSoluong() %>"  readonly="readonly" style="text-align:right;" onkeypress="return keypress(event);"></td>
			                		</TR>
		               		<% }  %>
                		 <tr class="tbfooter">
                    		<td colspan="4">&nbsp; </td>
                		</tr>                            		
            		</TABLE> 
            	</fieldset>
        	</div>      
        	
        	<div align="left" style="width:100%; float:none; clear:none;">
        	
        	 <fieldset>
    			<legend class="legendtitle"> Sản phẩm mới </legend>
        		   <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
		                <TR class="tbheader">                   
		                	<TH align="center" width="15%">Mã sản phẩm</TH>
		                	<TH align="left" width="73%">Tên sản phẩm</TH>
		               	 	<TH align="center" width="10%">Số lượng /Số tiền</TH>
		                </TR>
		                <% 
		                	String readonly="";
		               	 	
               				if(tdkmBean.getType().equals("2"))
               					readonly="readonly=\"readonly\"";
               				
               				System.out.println("[Type]"+tdkmBean.getType());
               				
		               			for(int i = 0; i < spList.size(); i++)
		               			{
		               				SanPham sp = (SanPham)spList.get(i);  %>
		               				<TR class='tbdarkrow'>
				                    	<td align="center">
				                    			<input type="text"  value="<%= sp.getMa() %>" name = "masp" onkeyup="ajax_showOptions(this,'abc',event)"  AUTOCOMPLETE="off">
				                    		</td>
				                   		<td align="left"><input type="text"  value="<%= sp.getTen() %>" name= "tensp" readonly></td>
				                    	<td align="right"><input type="text"  <%=readonly %> name="soluong"  value="<%=sp.getSoluong() %>"  style="text-align:right;" onkeypress="return keypress(event);"></td>
			                		</TR>
		               		<% }  %>
		               		
		               		<% 
		               			for(int i = 0; i < 40; i++)
		               			{
		               				 %>
		               				<TR class='tbdarkrow'>
				                    	<td align="center">
				                    			<input type="text"  value="" name = "masp" onkeyup="ajax_showOptions(this,'abc',event)"  AUTOCOMPLETE="off">
				                    		</td>
				                   		<td align="left"><input type="text"  value="" name= "tensp" readonly></td>
				                    	<td align="right"><input type="text"   <%=readonly %> name="soluong"    style="text-align:right;" onkeypress="return keypress(event);"></td>
			                		</TR>
		               		<% }  %>
		               		
                		<tr class="tbfooter">
                    		<td colspan="4">&nbsp;</td>
                		</tr>            
            	</TABLE> 
            	
            	</fieldset>
            
        	</div>      
        	
        	
		</div>         
    </div>
<%tdkmBean.closeDB(); %>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>