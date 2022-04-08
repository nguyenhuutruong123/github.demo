<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.dondathang.IDondathang" %>
<%@ page  import = "geso.dms.distributor.beans.dondathang.imp.Dondathang" %>
<%@ page  import = "java.util.Hashtable"%>
<%@ page  import = "java.util.List"%>
<%@ page import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IDondathang ddhBean = (IDondathang)session.getAttribute("ddhBean"); %>
<% ResultSet ncc = (ResultSet) ddhBean.getNcc(); %>
<% ResultSet dvkd = (ResultSet)ddhBean.getDvkdIds(); %>
<% ResultSet kbh = (ResultSet)ddhBean.getKbhIds(); %>
<% String[] masp = ddhBean.getMasp() ; %>
<% String[] tensp = ddhBean.getTensp(); %>
<% String[] sl = ddhBean.getSl(); %>
<% String[] slduyet = ddhBean.getSlDuyet(); %>
<% String[] tienbVAT = ddhBean.getTienbVAT(); %>
<% String[] dg = ddhBean.getDongia() ; %>
<% String[] dv = ddhBean.getDv1() ; %>
<% String[] scheme = ddhBean.getScheme(); %>

<% ResultSet cnList = (ResultSet)ddhBean.getCongnoList(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.Hashtable;"%>
<HTML>
<HEAD>
<TITLE>DuongBienHoa - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<SCRIPT src="../js/md5.js" type="text/javascript" language="javascript">
</SCRIPT>
<SCRIPT src="../js/commun.js" type="text/javascript"
    language="javascript">
</SCRIPT>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery-1.3.2.min.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
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

<script type="text/javascript" language="JavaScript">
function setTTienbVAT(){
	 	
	var i = <%= ddhBean.getSize()%>;
	var masp = new Array(<%= ddhBean.getMaspstr() %>);
	var tongtienbvat = 0;
	
	for(x=0; x<i; x++){
		var slId = "sl" + masp[x];
	
		var sl= document.getElementById(slId).value;
		sl.replace(/^\s+|\s+$/, '');
		if(sl.length==0) 
			sl="0";
		
		var dgId = "dg"  + masp[x];
		
		var dg= document.getElementById(dgId).value;
	
		var tien = parseFloat(sl)*parseFloat(dg);
		
		var tienId = "t"  + masp[x];
				
		if(!(isNaN(tien))){
			document.getElementById(tienId).setAttribute("value", formatCurrency(tien));
			tongtienbvat = parseFloat(tongtienbvat) + parseFloat(tien);
		}
	}
	document.getElementById("tongtienbvat").setAttribute("value", formatCurrency(tongtienbvat)); 
	var vat = Math.round(tongtienbvat*0.1);
	document.getElementById("vat").setAttribute("value", formatCurrency(vat));
	var tongtienavat = Math.round(tongtienbvat*1.1);
	document.getElementById("tongtienavat").setAttribute("value", formatCurrency(tongtienavat));
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";
		
}
      
function init()
{
	var tongtienavat= document.getElementById("tongtienavat").value;
	tongtienavat = tongtienavat.replace(',', '').replace(',', '');
	tongtienavat = tongtienavat.replace(',', '').replace(',', '');
	tongtienavat = tongtienavat.replace(',', '').replace(',', '');
	tongtienavat = tongtienavat.replace(',', '').replace(',', '');
	tongtienavat = tongtienavat.replace(',', '').replace(',', '');
	
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
{  document.forms['ddhForm'].action.value='chot';
   document.forms["ddhForm"].submit();
}

 function saveform()
{
	document.forms['ddhForm'].action.value='save';
    document.forms["ddhForm"].submit();
}

function printform()
{   
	document.getElementById("btnPrint").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	document.forms['ddhForm'].action.value='print';
   	document.forms["ddhForm"].submit();
}

</SCRIPT>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="ddhForm" method="post" action="../../DondathangDisplaySvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%=ddhBean.getId() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD align='left' valign='top'> 

            <TABLE width="100%">
                <TR>
                    <TD align="left" class="tbnavigation">
                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
                           <tr height="22">
                               <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tồn kho &gt; Mua hàng từ NCC &gt; Đặt hàng &gt; Hiển thị
                               <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%=ddhBean.getNppTen() %> </TD>

                            </tr>
                        </table>
                     </TD>
                  </TR> 
            </TABLE>

            <TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
                <TR ><TD >
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR class = "tbdarkrow">

                            <TD width="30" align="left"><A href="javascript:history.back()"  ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A></TD>
                            <TD width="2" align="left" >&nbsp;</TD>
                            <TD width="30" align="left" >
                            <div id="btnPrint">
                            <A href="javascript: printform()" ><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A>
                            </div>
                            </TD>
                            <TD  align="left">&nbsp;</TD>
                        </TR>
                    </TABLE>
                </TD></TR>

            </TABLE>

            <TABLE width = 100% cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo</LEGEND>
				
	    				<textarea name="err" cols="96" rows="1" disabled><%=ddhBean.getMessage()%></textarea>
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
                                                <TABLE cellpadding = 4 cellspacing = 0 width = "100%" border = 0>
                                                    <TR>
                                                        <TD width="15%" class="plainlabel">Nhà phân phối </TD>
                                                        <TD  class="plainlabel"><%= ddhBean.getNppTen() %> 
                                                        	<TD class="plainlabel">Ngày đề nghị giao hàng </TD>
                                                                    <td colspan="3" class="plainlabel"><input class="days" type="text" name="ngaydenghigiaohang" size="11" value="<%= ddhBean.getNgaydenghigh() %>"></td>
                                                                                                          
                                                    </TR>

                            
                                                    <TR>
                                                        <TD class="plainlabel">Ngày đặt hàng </TD>
                                                        <TD width="30%" class="plainlabel">
        
                                                            <table border=0 cellpadding = 0 cellspacing = 0>
                                                                <tr>
                                                                    <td class="plainlabel"><input class="days" type="text" name="ngaydh" size="11" value="<%= ddhBean.getNgaydh() %>" readonly="readonly"></td>
                                                                    
                                                                </tr>

                                                            </table>                                                
                                                        </TD>
           
                                                      <TD  class="plainlabel"> Tiền thuế</TD>
                                                      <TD colspan="3" class="plainlabel"><input type="text" name="vat" id="vat"  value="<%= formatter.format(Double.parseDouble(ddhBean.getVat())) %>" readonly="readonly" style="text-align: right">
                                              		   VND</TD>                                                    
                                                    </TR>
                                                    
                                                    <TR class="tblightrow">
                                                        <TD  class="plainlabel">Nhà cung cấp </TD>
                                                        <TD  class="plainlabel"> 
                                                          <select name="nccId" readonly="readonly">
                                                          	
                                                           <% try{ %>
                                                       			    	
                                                         	<%   if(ncc != null){
                                                         			while(ncc.next()){      
                                                            	      if (ncc.getString("nccId").equals("100046")){ %>   
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
                                                                                                            
                                                      <TD  class="plainlabel"> Tổng tiền trước thuế</TD>
                                                      <TD colspan="3" class="plainlabel"><input type="text" name="tongtienbvat" id="tongtienbvat" readonly="readonly" value="<%= formatter.format(Double.parseDouble(ddhBean.getTongtienbVAT())) %>" style="text-align: right"  >
                                               	       VND</TD>                                                    
													</TR>
                                                    
                                                    <TR>                                                        
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
                                  						    
                                                      <TD  class="plainlabel"> Tổng tiền sau thuế</TD>
                                                      <TD colspan="3" class="plainlabel"><input type="text" name="tongtienavat" id="tongtienavat" readonly="readonly" value="<%= formatter.format(Double.parseDouble(ddhBean.getTongtienaVAT())) %>"  style="text-align: right" >
                                               	   	   VND</TD>                                                                                     																
                                                    </TR>                                                                                                        
													
													<TR>															
														<TD class="plainlabel">Kênh bán hàng </TD>
								    					<TD colspan="5" class="plainlabel">
								    						<SELECT  name="kbhId" style="width:80" readonly="readonly">
								  		
														  	 <% try{ while(kbh.next()){ 
															    	if(kbh.getString("kbhId").equals(ddhBean.getKbhId())){ %>
								      									<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
								      							  <%}else{ %>
								     									<option value='<%=kbh.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
								     							  <%}}}catch(java.sql.SQLException e){} %>	
                                  							</select>
                                  						</TD>													
				
                                                   </TR>
                                                   <TR class="tblightrow">
                                                      <TD  class="plainlabel"> Lý do hủy </TD>
                                                      <TD colspan="4" class="plainlabel">
                                                    	 <textarea rows="1" style="width:100% ;font-size:11pt "  readonly="readonly"  > <%=ddhBean.getLyDoHuy()%>  </textarea>
                                                    	
                                                       </TD>
                                                        <TD  class="plainlabel"> </TD>
                                                   </TR>
                                                   <TR class="tblightrow">
                                                      <TD  class="plainlabel">Ghi chú đơn hàng </TD>
                                                      <TD colspan="6" class="plainlabel">
                                                      	<textarea name="ghichu" id="ghichu" style="width: 100%" rows="2"  ><%=ddhBean.getGhiChu()%></textarea>
                                                      </TD>
                                                       
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
					                        				<td><input style="  text-align:right ; width: 100%" readonly="readonly" value="<%= formatter.format(Math.round(Double.parseDouble(cnList.getString("SOTIEN")))) %>"></td>
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
                                            
                                                <TABLE class="tabledetail"  width = 100% cellpadding="4"  border="0" cellspacing="1" >
                                                    <TR class="tbheader" >
                                                        <TH width="10%">Mã sản phẩm </TH>
                                                        <TH width="30%">Tên sản phẩm</TH>
                                                        <TH width="10%">Số lượng</TH>
                                                        <TH width="10%">Số lượng Duyệt</TH>
                                                        <TH width="10%">Đơn vị</TH>
                                                     	<TH width="10%">Đơn giá (w/o VAT)</TH>
                                                     	<TH width="10%">Thành tiền</TH>
                                                     	<TH width="10%">SCHEME</TH>
                                                    </TR>

															                                                    
                                    		<% 

               								String lightrow = "tblightrow";
                                            String darkrow = "tbdarkrow";
               								
                                            int size = new Integer(ddhBean.getSize()).intValue();
											int m = 0;                                            
                                    		for(int i=0;i< size ;i++){ 					
                                    			if(m%2 == 0){					%>
													<TR class= <%=lightrow%> >
											<% 	}else{%>	         
													<TR class= <%=darkrow%> >
											<%	} %>
         
                                                       <TD><%= masp[i] %> </TD>
                                                       <TD><%= tensp[i] %></TD>
                                                       <TD align = "right"><%= formatter.format(Double.parseDouble(sl[i])) %></TD>
                                                       <TD align = "right"><%= formatter.format(Double.parseDouble(slduyet[i])) %></TD>
                                                       <TD align = "center"><%= dv[i] %></TD>
                       								   <TD align = "right"><%= formatter.format(Double.parseDouble(dg[i])) %></TD>																
												       <TD align = "right"><%= formatter.format(Double.parseDouble(tienbVAT[i])) %></TD>     
												       <TD><%= scheme[i] %></TD>                  
                                                  </TR>
                                            <% m++;} %>
                                                    
                                                   
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

<% if (!(ncc == null)) ncc.close(); %>
<% if (!(dvkd == null)) dvkd.close(); %>
<% if (!(kbh == null)) kbh.close(); %>
<% 
if(ddhBean != null){
	ddhBean.DBclose();
	ddhBean = null;
}
 %>
<%}%>