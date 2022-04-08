<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.dondathang.IDondathang" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Best/index.jsp");
	}else{ %>

<% IDondathang ddhBean = (IDondathang)session.getAttribute("ddhBean"); %>
<% ResultSet ncc = (ResultSet) ddhBean.getNcc(); %>
<% ResultSet dvkd = (ResultSet)ddhBean.getDvkdIds(); %>
<% ResultSet kbh = (ResultSet)ddhBean.getKbhIds(); %>
<% String[] spId = ddhBean.getSpId() ; %>
<% String[] masp = ddhBean.getMasp() ; %>
<% String[] denghi = ddhBean.getDenghi(); %>
<% String[] tensp = ddhBean.getTensp(); %>
<% String[] ton = ddhBean.getTonicp(); %>
<% String[] sl = ddhBean.getSl(); %>
<% Hashtable tmasp = ddhBean.getThieuMasp() ; %>
<% String[] tienbVAT = ddhBean.getTienbVAT(); %>
<% String[] dg = ddhBean.getDongia(); %>
<% String[] dv = ddhBean.getDonvi() ; %>
<% String[] qc = ddhBean.getQuycach() ; %>

<% ResultSet cnList = (ResultSet)ddhBean.getCongnoList(); %>

<% String[] tspId = ddhBean.gettSpId() ; %>
<% String[] tdenghi = ddhBean.gettDenghi(); %>
<% String[] ttensp = ddhBean.gettTensp(); %>
<% String[] tton = ddhBean.gettTonicp(); %>
<% String[] tsl = ddhBean.gettSl(); %>
<% String[] tmasparr = ddhBean.getThieuMaspArray(); %>
<% String[] ttienbVAT = ddhBean.gettTienbVAT(); %>
<% String[] tdg = ddhBean.gettDongia(); %>
<% String[] tdv = ddhBean.gettDonvi() ; %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
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

<SCRIPT type="text/javascript"> 
function setTTienbVAT(){
	var thuesuat=document.getElementById("thuesuat").value;
	var i = <%= ddhBean.getSize()%>;
	var masp = new Array(<%= ddhBean.getMaspstr() %>);	
	var tongtienbvat = 0;
	
	for(x=0; x<i; x++){
		var slId = "sl" + masp[x];

		var sl= document.getElementById(slId).value;
		sl=sl.replace(/\,/g,'');
		
		if(sl.length==0) 
			sl="0";
			
		
		
		var dgId = "dg"  + masp[x];
		
		var dg= document.getElementById(dgId).value;
	
		var tien = parseFloat(sl)*parseFloat(dg.replace(/\,/g,''));
		
		var tienId = "t"  + masp[x];
				
		if(!(isNaN(tien))){
			document.getElementById(tienId).value = formatCurrency(tien);
			tongtienbvat = parseFloat(tongtienbvat) + parseFloat(tien);
		}
	}
	document.getElementById("tongtienbvat").value = formatCurrency(tongtienbvat); 
	var vat = Math.round(tongtienbvat*thuesuat);
	document.getElementById("vat").value = formatCurrency(vat);
	var tongtienavat = Math.round(tongtienbvat)+Math.round(tongtienbvat*thuesuat);
	document.getElementById("tongtienavat").value = formatCurrency(tongtienavat);
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";
		
}
      

function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   return false;
   }
else
   return true;
}

function chotform()
{  
	document.getElementById("chotid").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";	
	document.forms['ddhForm'].action.value='chot';
   document.forms["ddhForm"].submit();
}

 function saveform()
{
	 document.getElementById("saveid").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";	
	document.forms['ddhForm'].action.value='save';
    document.forms["ddhForm"].submit();
}

function printform()
{   
	document.forms['ddhForm'].action.value='print';	
   	document.forms["ddhForm"].submit();
}

function init()
{
	var tongtienavat= document.getElementById("tongtienavat").value;
	tongtienavat = tongtienavat.replace(',', '');
	tongtienavat = tongtienavat.replace(',', '');
	tongtienavat = tongtienavat.replace(',', '');
	tongtienavat = tongtienavat.replace(',', '');
	tongtienavat = tongtienavat.replace(',', '');
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";
	
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
function Dinhdang(element)
{
	element.value=DinhDangTien(element.value);
	if(element.value== '' )
	{
		element.value= '';
	}
	setTTienbVAT();
}
</SCRIPT>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onLoad = 'init();'>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="ddhForm" method="post" action="../../DondathangUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%=ddhBean.getId() %>'>
<input type="hidden" name="dvkdId" value='<%=ddhBean.getDvkdId() %>'>
<input type="hidden" name="kbhId" value='<%=ddhBean.getKbhId() %>'>
<input type="hidden" id="thuesuat" name="thuesuat" value='<%=ddhBean.getThueSuat()%>'>
    <!-- height="100%"  bgcolor="#FFFFFF"> -->
    <TR>
        <TD colspan="4" align='left' valign='top'>

            <TABLE width="100%">
                <TR>
                    <TD align="left" class="tbnavigation">
                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
                           <tr height="22">
                               <TD align="left" colspan="2" class="tbnavigation">Quản lý tồn kho &gt;&nbsp;Mua hàng từ nhà cung cấp &gt; Đặt hàng &gt; Cập nhật </TD>
      
                               <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%=ddhBean.getNppTen() %> &nbsp;</TD>

                            </tr>
                        </table>
                     </TD>
                  </TR> 
            </TABLE>

            <TABLE   width="100%" border="0" cellpadding="3" cellspacing="0">
                <TR ><TD >
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR class = "tbdarkrow">

                            <TD width="30" align="left"><A href="javascript:history.back()"  ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
                            <TD width="2" align="left" >&nbsp;</TD>
                            <TD width="30" align="left" ><A id="saveid" href="javascript: saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A></TD>
                            <TD width="2" align="left" >&nbsp;</TD>
                            <TD width="30" align="left" ><A href="javascript: printform()" ><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
                            <TD width="2" align="left">&nbsp;</TD>
                            <TD width="30" align="left" ><A id="chotid" href="javascript: chotform()" ><img src="../images/Chot.png" alt="Chot don hang" title="Chot don hang" width="30" height="30" longdesc="Chot don hang" border=1 style="border-style:outset"></A></TD>
                            <TD  align="left">&nbsp;</TD>
                            
                        </TR>
                    </TABLE>
                </TD></TR>

            </TABLE>

            <TABLE width = 100% cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="err" cols="109" rows="1"  ><%=ddhBean.getMessage()%></textarea>
						<% ddhBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			
		 	
			<TR>
                
                <TR>
                    <TD >
                        <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                            <TR>
                                <TD  align="left">
                                    <FIELDSET>
									<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
                                    <LEGEND class="legendtitle">&nbsp;Đơn đặt hàng &nbsp;</LEGEND>
                                                <TABLE cellpadding = "3" cellspacing = "0"  width = "100%" border = 0>
                                                    <TR>
                                                        <TD width="15%" class="plainlabel">Nhà phân phối </TD>
                                                        <TD  class="plainlabel"><%= ddhBean.getNppTen() %>                                                                                                                																								                                                  
                                                   <TD class="plainlabel">Ngày đề nghị giao hàng </TD>
                                                                    <td colspan="4" class="plainlabel"><input class="days" type="text" name="ngaydenghigiaohang" size="11" value="<%= ddhBean.getNgaydenghigh() %>"></td>
                                                   
                                                    </TR>

                            
                                                    <TR >
                                                        <TD class="plainlabel">Ngày đặt hàng </TD>
                                                        <TD width="30%" class="plainlabel">        
                                                            <table border=0 cellpadding = 0 cellspacing = 0>
                                                                <tr>
                                                                    <td class="plainlabel"><input class="days" type="text" name="ngaydh" size="11" value="<%= ddhBean.getNgaydh() %> " readonly="readonly">                                                                    		 
                                                                </tr>
                                                            </table>                                                
                                                        </TD>
              
                                                      <TD  class="plainlabel">VAT</TD>
                                                      <TD colspan="4"  class="plainlabel"><input type="text" name="vat" id="vat"  value="<%= formatter.format(Double.parseDouble(ddhBean.getVat())) %>" readonly="readonly" style="text-align: right">
                                             		   VND </TD>
                                                    </TR>
                                                    
                                                    <TR class="tblightrow">
                                                    	<TD  class="plainlabel">Nhà cung cấp </TD>
                                                        <TD  class="plainlabel"> 
                                                          <select name="nccId" readonly="readonly">
                                                          	
                                                           <% try{ %>
                                                       			    	
                                                         	<%   if(ncc != null){
                                                         			while(ncc.next()){      
                                                            	      if (ncc.getString("nccId").equals(ddhBean.getNccId())){ %>   
                                                                	       <option value='<%=ncc.getString("nccId")%>' selected><%=ncc.getString("nccTen")%></option>
                                                                	       
                                                                   <% }else{ %>
                                                                			<option value='<%=ncc.getString("nccId")%>'><%=ncc.getString("nccTen")%></option>   		   
                                                                 	<%} 
                                                                	} 
                                                                  }%>
                                                            <% }catch(java.sql.SQLException e){   %>
                                                                    <%  }  %>
                                                          </select>                                             
                                                        </TD> 
                                                        
                                                         <TD  class="plainlabel">Tổng số tiền (chưa VAT) </TD>
                                                      <TD colspan="4" class="plainlabel"><input type="text" name="tongtienbvat" id="tongtienbvat" readonly="readonly" value="<%= formatter.format(Double.parseDouble(ddhBean.getTongtienbVAT())) %>" style="text-align: right"  >
                                              		   VND </TD>                                                        
                                                    </TR>                                  						                                  								                                                                                                                																									                                             
                                                    
                                                    <TR class="tblightrow">
                                                      <TD width="15%" class="plainlabel">Đơn vị kinh doanh </TD>
								    					<TD class="plainlabel">
								    						<SELECT  name="dvkdId" style="width:80" readonly="readonly">
								  		
														  	 <% try{ while(dvkd.next()){ 
															    	if(dvkd.getString("dvkdId").equals(ddhBean.getDvkdId())){ %>
								      									<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      							  <%}else{ %>
								     									<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     							  <%}}}catch(java.sql.SQLException e){} %>	
                                  							</select>
                                  						</TD>	
                                  						
                                  						<TD  class="plainlabel">Tổng số tiền (có VAT) </TD>
                                                      <TD colspan="4" class="plainlabel"><input type="text" name="tongtienavat" id="tongtienavat" readonly="readonly" value="<%= formatter.format(Double.parseDouble(ddhBean.getTongtienaVAT())) %>"  style="text-align: right" >
                                             		   VND </TD>  
                                                   </TR>
                                                        
                                                    <TR class="tblightrow">
                                                        <TD class="plainlabel">Kênh bán hàng </TD>
								    					<TD colspan="6" class="plainlabel">
								    						<SELECT  name="kbhId" style="width:80" readonly="readonly">
								  		
														  	 <% try{ while(kbh.next()){ 
															    	if(kbh.getString("kbhId").equals(ddhBean.getKbhId())){ %>
								      									<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
								      							  <%}else{ %>
								     									<option value='<%=kbh.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
								     							  <%}
															 		System.out.println("KBH: " + kbh.getString("kbhId") + ";" + kbh.getString("kbh"));   	
														  	 
														  	 }}catch(java.sql.SQLException e){} %>	
                                  							</select>
                                  						</TD>	
                                                </TR>																									
		 											<TR class="tblightrow">
                                                      <TD  class="plainlabel">Ghi chú đơn hàng </TD>
                                                      <TD colspan="6" class="plainlabel">
                                                      	<textarea name="ghichu" id="ghichu" style="width: 100%" rows="2"  ><%=ddhBean.getGhiChu()%></textarea>
                                                      </TD>
                                                       
                                                   </TR>   
                                                    <TR class="tblightrow">
                                                      <TD  class="plainlabel">Bằng chữ</TD>
                                                      <TD colspan="6"  class="plainlabel"> <span id="lblDocSo" style="font-weight:bold;font-style:italic;"></span> </TD>

                                                   </TR>
                                                   
                                                    <TR class="tblightrow">
                                                      <TD  class="plainlabel"> </TD>
                                                      <TD colspan="1" class="plainlabel">
                                                      <% if(ddhBean.getDoiHang().equals("1")) {%>
                                                      <input type="checkbox" checked="checked" name="doihang" value="1" > Đơn đổi hàng 
                                                      <%}else{ %>
                                                        <input type="checkbox" value="1" name="doihang"  > Đơn đổi hàng 
                                                      <%} %>
                                                       </TD>
                                                                                                           
                                                      <TD colspan="4" class="plainlabel">
                                                      <% if(ddhBean.getLoaiDonHang().equals("1")) {%>
                                                      <input type="checkbox" checked="checked" value="1" name="loaidonhang"  > Đơn hàng khuyến mãi 
                                                      <%}else{ %>
                                                        <input type="checkbox" value="1" name="loaidonhang" >  Đơn hàng khuyến mãi 
                                                      <%} %>
                                                       </TD>
                                                       
                                                   </TR>       												
                                                   
                                                    <TR class="tblightrow">
                                                 
                                                 <TD  class="plainlabel"> </TD>
                                                 <TD class="plainlabel" colspan="1">
												 <a href="" id="khlId" rel="subcontentKhl" style="color: blue; text-decoration: underline;">
	           	 								 Các khoản thanh toán</a>
	           	 		
		                          				 <DIV id="subcontentKhl" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
						                         background-color: white; width: 800px; max-height:300px; overflow:auto; padding: 4px;">
						                    	 <table width="90%" align="center" cellpadding="2" cellspacing="1">
						                         <tr>						                            
						                            <th width="75px" style="font-size: 12px">Ngày cập nhật</th>
						                            <th width="150px" style="font-size: 12px">Diễn giải</th>
						                            <th width="60px" style="font-size: 12px">Số tiền</th>
						                            <th width="110px" style="font-size: 12px">Hình thức TT</th>
						                            						                         
						                         </tr>
				                            	<%
					                        		if(cnList != null)
					                        		{
					                        			while(cnList.next())
					                        			{ %>
					                        			
					                        			<tr>
					                        				<td><input style="width: 100%" readonly="readonly" value="<%= cnList.getString("THOIGIAN") %>"></td>
					                        				<td><input style="width: 100%" readonly="readonly" value="<%= cnList.getString("DIENGIAI") %>"></td>
					                        				<td><input style="text-align:right; width: 100%" readonly="readonly" value="<%= formatter.format(Math.round(Double.parseDouble(cnList.getString("SOTIEN")))) %>"></td>
					                        				<td><input style="width: 100%" readonly="readonly" value="<%= cnList.getString("HINHTHUCTT") %>"></td>					                        				
					                        			</tr>
					                        			
					                        		 <% } cnList.close(); } %>
						                    </table>
						                     <div align="right">
						                     	<label style="color: red" ></label>
						                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontentKhl')" >Hoàn tất</a>
						                     </div>
						            </DIV>         
												</TD>
												
												<TD colspan="4" class="plainlabel">
								                             <%	
								                             System.out.println("giao hang : "+ddhBean.getcongTyGiaohang().trim());
								                             if(ddhBean.getcongTyGiaohang().trim().equals("1")) { %>
								                             <input type="checkbox" checked="checked" value="1" name="congtyvanchuyen"  >NPP vận chuyển  
								                             <%}else{ %>
								                               <input type="checkbox" value="0" name="congtyvanchuyen" >  NPP vận chuyển 
								                             <%} %>
								                              </TD>
												</TR> 
                                                   
                                                </TABLE>
                                            
                                               <TABLE class ="tabledetail"  width = 100% cellpadding="0"  border="0" cellspacing="1" >
                                                    <TR class="tbheader" >
                                                        <TH width="10%">Mã sản phẩm </TH>
                                                        <TH width="35%">Tên sản phẩm</TH>
                                                        <TH width="10%">Đề nghị</TH>
                                                        <TH width="10%">Đặt hàng</TH>
                                                        <TH width="10%">Tồn</TH>
                                                        <TH width="10%">Đơn vị</TH>
                                                        <TH width="15%">Đơn giá (w/o VAT) </TH>
                                                        <TH width="10%">T. tiền (w/o VAT) </TH>

                                                    </TR>

															                                                    
                                    		<% 

               								String lightrow = "tblightrow";
                                            String darkrow = "tbdarkrow";
                                            int size = new Integer(ddhBean.getSize()).intValue();
                                            int m= 0;
                                            if (tmasparr != null){
												for (int n = 0; n < tmasparr.length ; n++){
													if (tmasparr[n] != null){%>
													<TR class= <%=lightrow%> >
                                                   <TD><input type="text"  name="ma" value='<%= tmasparr[n] %>' size = 17 readonly="readonly" style="color:#F00; background-color:#80CB9B; text-align:left"> </TD>
                                                   <TD><input type="text"  name="ten" value='<%= ttensp[n] %>' size = 45 readonly="readonly" style="color:#F00;  background-color:#80CB9B; text-align:left"></TD>
                                                   <TD><input type="text"  name="denghi" value= <%= formatter.format(Double.parseDouble(tdenghi[n])) %> size = 10 readonly="readonly" style="color:#F00;  background-color:#80CB9B; text-align: right;"></TD>                                                       
		                                    		                                                    
                                                    <TD>
                                                    <a class="addspeech" href="" title="San pham nay con toi da <%= tton[n] %> Thung, vui long chon lai so luong" onclick="return false;">
                                                    <input type="text" name='<%="sl"+tmasparr[n]%>'  value='<%= formatter.format(Double.parseDouble(tsl[n])) %>' id='<%= "sl"+tmasparr[n]%>' size = 10 onkeyup="Dinhdang(this)" size = "10" style="color:#F00; cursor:pointer; background-color:#80CB9B; text-align:right">
                                                    </a> 
                                                   </TD> 
                                                   
                                                   <TD>
                                                   	<input type="text"  name='<%="ton"+tmasparr[n]%>' value='<%= tton[n] %>' id='<%="ton"+tmasparr[n]%>' style="text-align: right">
                                                   </TD>                                                   

                                                   <TD><input type="text"  name='<%="dv"+tmasparr[n]%>' value='<%=dv[n]%>' size=10 readonly="readonly" style="color:#F00; background-color:#80CB9B; text-align:center"></TD>
                								   <TD><input type="text"  name='<%="dg"+tmasparr[n]%>'  value='<%= formatter.format(Double.parseDouble(tdg[n])) %>' id='<%= "dg"+tmasparr[n]%>' size = 10 readonly="readonly" style="color:#F00;  background-color:#80CB9B; text-align:right"> </TD>
	                                               <TD><input type="text"  name='<%= "t"+ tmasparr[n]%>' value= '<%= formatter.format(Double.parseDouble(ttienbVAT[n])) %>' id='<%= "t"+ tmasparr[n]%>' size = 15 readonly="readonly" style="color:#F00;  background-color:#80CB9B; text-align:right">  																						
                                    				<input type="text"  name="spId" value= '<%= tspId[n] %>' id='<%= "spId" %>' size = 15 readonly="readonly" style="text-align: right">
													<%-- <input type="hidden"  name='<%="ton"+tmasparr[n]%>' value='<%= tton[n] %>' id='<%="ton"+tmasparr[n]%>' > --%>	                                              

                                                     <input type="hidden"  name="masp" value='<%= tmasparr[n] %>' size = 17 > 
                                                     <input type="hidden"  name="tensp" value='<%= ttensp[n] %>' size = 45 >                                                        
                                                     <input type="hidden"  name='<%="dv"+tmasparr[n]%>' value='<%= tdv[n] %>' size=10 readonly="readonly" style="text-align: center">
                       								 <input type="hidden"  name='<%="dg"+tmasparr[n]%>'  value='<%= formatter.format(Double.parseDouble(tdg[n])) %>' id='<%= "dg"+tmasparr[n]%>' size = 10 readonly="readonly" style="text-align: right">
                       								    <input type="hidden"  name='<%="qc"+masp[n]%>' value='<%= qc[n] %>' id='<%="qc"+masp[n]%>' >                                      
													</TD>	
											<% 	}
												}
											}
                                            
                                            
                                    		for(int i=0;i< size ;i++){ %> 						
													<TR class= <%=lightrow%> >
													<% if(!tmasp.containsKey(masp[i])){ %>
                                                       <TD><input type="text"  name="ma" value='<%= masp[i] %>' size = 17 readonly="readonly"> </TD>
                                                       <TD><input type="text"  name="ten" value='<%= tensp[i] %>' size = 45 readonly="readonly"></TD>
													   <TD><input type="text"  name="denghi" value= <%= formatter.format(Double.parseDouble(denghi[i])) %> size = 10 readonly="readonly" style="text-align: right"></TD>													   
                                                       <TD><input type="text" name='<%="sl"+masp[i]%>'  value='<%= formatter.format(Double.parseDouble(sl[i])) %>' id='<%= "sl"+masp[i]%>' size = 10 onkeyup="Dinhdang(this)" size = "10" style="text-align: right"> </TD>
                                                       
                                                       <TD>
                                                       	<input type="text"  name='<%="ton"+masp[i]%>' value='<%= ton[i] %>' id='<%="ton"+masp[i]%>' style="text-align: right">
                                                       </TD>
                                                       
                                                       <TD><input type="text"  name='<%="dv"+masp[i]%>' value='<%=dv[i]%>' size=10 readonly="readonly" style="text-align: center"></TD>
                    								   <TD><input type="text"  name='<%="dg"+masp[i]%>'  value='<%= formatter.format(Double.parseDouble(dg[i])) %>' id='<%= "dg"+masp[i]%>' size = 10 readonly="readonly" style="text-align: right"> </TD>
                                                       <TD><input type="text"  name='<%= "t"+ masp[i]%>' value= '<%= formatter.format(Double.parseDouble(tienbVAT[i])) %>' id='<%= "t"+ masp[i]%>' size = 15 readonly="readonly" style="text-align: right">  																						
                                                       <input type="hidden"  name="spId" value='<%= spId[i] %>' > 
                                                       <input type="hidden"  name="masp" value='<%= masp[i] %>' size = 17 readonly="readonly"> 
                                                       <input type="hidden"  name="tensp" value='<%= tensp[i] %>' size = 45 readonly="readonly">                                                        
                                                       <input type="hidden"  name='<%="dv"+masp[i]%>' value='<%= dv[i] %>' size=10 readonly="readonly" style="text-align: center">
                       								   <input type="hidden"  name='<%="dg"+masp[i]%>'  value='<%= formatter.format(Double.parseDouble(dg[i])) %>' id='<%= "dg"+masp[i]%>' size = 10 readonly="readonly" style="text-align: right">
                       								   <%-- <input type="hidden"  name='<%="ton"+masp[i]%>' value='<%= ton[i] %>' id='<%="ton"+masp[i]%>' > --%>
                       								   <input type="hidden"  name='<%="qc"+masp[i]%>' value='<%= qc[i] %>' id='<%="qc"+masp[i]%>' >                                       
												    	 </TD>	
												     <%} %>
                                                  </TR>
                                            <% } %>
                                                    
                                                   
                                                </TABLE>
                                               </FIELDSET>
                                            </TD>
                                    </TR>

                                   
                                </TABLE>
                                
                        </TD>
                    </TR>   
            </TABLE>
    </td>

  </tr>
</table>

</form>
<script type="text/javascript">
	dropdowncontent.init('khlId', "right-top", 300, "click");
</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<% if(!(ncc == null)) ncc.close(); %>
<% if(!(dvkd == null)) dvkd.close(); %>
<% if(!(kbh == null)) kbh.close(); %>
<%
if(ddhBean != null){
	ddhBean.DBclose();
	ddhBean = null;
} %>
<%}%>