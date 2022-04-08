<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page  import = "geso.dms.center.beans.hdnhaphang.IHDnhaphang" %>
<%@ page  import = "java.sql.ResultSet" %>
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

<% IHDnhaphang hdnhaphang = (IHDnhaphang)session.getAttribute("nhaphang"); %>
<% String id = hdnhaphang.getId();   %>
<% String sohoadon = hdnhaphang.getSohoadon(); %>
<% ResultSet ncc = (ResultSet) hdnhaphang.getNcc(); %>
<% ResultSet dvkd = (ResultSet)hdnhaphang.getDvkdIds(); %>
<% ResultSet kbh = (ResultSet)hdnhaphang.getKbhIds(); %>
<% ResultSet kho = (ResultSet)hdnhaphang.getKhoIds(); %>
<% String[] masp = hdnhaphang.getMasp() ; %>
<% String[] tensp = hdnhaphang.getTensp(); %>
<% String[] sl = hdnhaphang.getSl(); %>
<% String[] tienbVAT = hdnhaphang.getTienbVAT(); %>
<% String[] giamua = hdnhaphang.getGiamua() ; %>
<% String[] donvi = hdnhaphang.getDonvi() ; %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.js"></script> 
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["nhaphangForm"].submit();
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
	document.getElementById("sohoadon").focus();
}

</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onLoad='init();'>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form id="nhaphangForm" name="nhaphangForm" method="post" action="../../HDnhaphangDisplayTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="id" value='<%=id %>'>
<input type="hidden" name="action" value='nhaphang'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD colspan="4" align='left' valign='top'> 
        
        <TABLE width="100%" cellspacing="0" cellpadding="0">
        	<TR>
            	<TD>
                	<TABLE width="100%" cellspacing="1" cellpadding="0">
                    	<TR>
                        	<TD align="left" class="tbnavigation">
                            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr height="22">
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tồn kho &gt;&nbsp;Hóa đơn nhập hàng &gt;&nbsp;Hiển thị
                                   
                                        <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %> &nbsp;</TD>
                                    </tr>
                                  </table>
                              </TD>
                         </TR> 
                     </TABLE>
		            <TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
        		        <TR ><TD >
                		    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        		<TR class = "tbdarkrow">

                            		<TD width="30" align="left"><A href="../../HDnhaphangTTSvl?userId=<%=userId%>"  ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>                            		
                            		<TD  align="left">&nbsp;</TD>
                        		</TR>
                    		</TABLE>
                		</TD></TR>

		            </TABLE>
                    
                     <TABLE border="0" width="100%" cellspacing = 0 cellpadding = 0>
               			 <tr>
                    		<TD align="left" colspan="4" class="legendtitle">
                        		<FIELDSET>
                        		<LEGEND class="legendtitle">Báo lỗi nhập liệu  </LEGEND>              
                        			<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%" readonly="readonly" rows="1" ><%= hdnhaphang.getMessage() %></textarea>
                        			<% hdnhaphang.setMessage(""); %>
                        		</FIELDSET>
                   			</TD>
                		</tr>

                        <TR>
                            <TD width="100%" align="left">
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Nhận hàng&nbsp;</LEGEND>                                                                                 
                                <%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
                                <TABLE  width="100%" cellpadding="4" cellspacing="0">
                                <TR>
                                	<TD width="15%" class="plainlabel">Nhà phân phối </TD>
                              		<TD colspan="5" class="plainlabel"><%= hdnhaphang.getNppTen() %>                              	                                     	
                              	</TD>                                      	                                      																	
                                </TR>

								<TR class="tblightrow">
                                         <TD  class="plainlabel">Nhà cung cấp  </TD>
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
                                                 <% }catch(java.sql.SQLException e){  } %>
                                                                    
                                                 </select>                                             
                                       </TD>
                                        
                                        <TD width="15%" class="plainlabel" >Ngày chứng từ </TD>
									  	<TD class="plainlabel" >
											<input type="text" name="tungay" value="<%=hdnhaphang.getNgaychungtu() %>" size="20" readonly="readonly">
										</TD>                                      
																		
                                  </TR>
								
								 <TR>     	                                      	
										<TD class="plainlabel">Đơn vị kinh doanh </TD>
				    					<TD class="plainlabel">
				    						<SELECT  name="dvkdId"  readonly="readonly">
								  		
									  	 <% try{ while(dvkd.next()){ 
										    	if(dvkd.getString("dvkdId").equals(hdnhaphang.getDvkdId())){ %>
													<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
											  <%}else{ %>
													<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
										  <%}}}catch(java.sql.SQLException e){} %>	
                    						</select>
                    					</TD>
                    					
                    					<TD class="plainlabel" >Ngày nhận hàng </TD>
									  	<TD class="plainlabel" >
											<input type="text" name="ngaynhanhang" value="<%= hdnhaphang.getNgaynhanhang() %>" size="20" readonly="readonly">
										</TD>
                                                                                          							                                      
                                </TR>
										
								<TR>										
									<TD class="plainlabel">Kênh bán hàng </TD>
			    					<TD class="plainlabel">
			    						<SELECT  name="kbhId" readonly="readonly">
							  		
								  	 <%
								  	 if(hdnhaphang.getKbhId()==null){
								  		 hdnhaphang.setKbhId("");
								  	 }
								  		 
								  	 try{
								  		 while(kbh.next()){ 
									    	if(kbh.getString("kbhId").equals(hdnhaphang.getKbhId())){ %>
												<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
										  <%}else{ %>
												<option value='<%=kbh.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
									  <%}}
								  		 }catch(java.sql.SQLException e){} %>	
                   						</select>
                   					</TD>	
                   					
                   					<TD  class="plainlabel">VAT (10%) </TD>
                                        <TD colspan="3" class="plainlabel"><input type="text" name="vat" id="vat"  value="<%= formatter.format(Float.parseFloat(hdnhaphang.getTongVat())) %>" readonly="readonly" style="text-align: right">
                                         VND</TD>                     					
                                                                                      						
								</TR>
	
								<TR>											
									<TD class="plainlabel">Nhập vào kho </TD>
			    					<TD class="plainlabel">
			    						<SELECT  name="khoId"  >
								  	 <% try{ while(kho.next()){ 
									    	if(kho.getString("khoId").equals(hdnhaphang.getKhoId())){ %>
												<option value='<%=kho.getString("khoId")%>' selected><%=kho.getString("kho") %></option>
											<%}else{ %>
												<option value='<%=kho.getString("khoId")%>'><%=kho.getString("kho") %></option>
									  <%}}}catch(java.sql.SQLException e){} %>	
                   						</select>
                   					</TD>
                   					
                   					<TD  class="plainlabel">Tổng số tiền(chưa VAT) </TD>
                                    <TD colspan="3" class="plainlabel"><input type="text" name="tongtienbvat" id="tongtienbvat" readonly="readonly" value="<%= formatter.format(Float.parseFloat(hdnhaphang.getTongtienbVAT())) %>" style="text-align: right"  >
                                     VND </TD>           					
                                    					
								</TR>
								
								 <TR>                                       										

                                        <TD  class="plainlabel">Số hóa đơn </TD>
                                        <TD colspan="0" class="plainlabel"> <input type="text" name="sohoadon" id="sohoadon" readonly="readonly" value="<%=hdnhaphang.getSohoadon()%>"  style="text-align: right" >
                                        
                                        <TD  class="plainlabel">Tổng số tiền (có VAT) </TD>
                                   		<TD colspan="3" class="plainlabel"><input type="text" name="tongtienavat" id="tongtienavat" readonly="readonly" value="<%= formatter.format(Float.parseFloat(hdnhaphang.getTongtienaVAT())) %>"  style="text-align: right" >
                                     	 VND </TD>							
                              	</TR>					
                                
                                <TR class="tblightrow">
                                  <TD  class="plainlabel">Bằng chữ </TD>
                                  <TD colspan="3" class="plainlabel"> <span id="lblDocSo" style="font-weight:bold;font-style:italic;"></span> </TD>
                               </TR>

                                                </TABLE>
                                            
                                                <TABLE  width = 100% cellpadding="0"  border="0" cellspacing="1" >
                                                    <TR class="tbheader" >
                                                        <TH width="15%">Mã sản phẩm </TH>
                                                        <TH width="30%">Tên sản phẩm</TH>
                                                        <TH width="10%">Số lượng </TH>
                                                        <TH width="15%">Đơn vị </TH>
                                                        <TH width="15%">Giá mua </TH>
                                                        <TH width="15%">T. tiền (w/o VAT) </TH>
                                                    </TR>

															                                                    
                                    		<% 

               								String lightrow = "tblightrow";
               								
                                            int size = new Integer(hdnhaphang.getSize()).intValue();
                                            
                                    		for(int i=0;i< size ;i++){ 			%>			
													<TR class= <%=lightrow%> >         
                                                       <TD><input type="text"  name="masp" value='<%= masp[i] %>'  readonly="readonly" style=" width: 100%"> </TD>
                                                       <TD><input type="text"  name="tensp" value='<%= tensp[i] %>'  readonly="readonly" style=" width: 100%"></TD>
                                                       <TD><input type="text" name='<%="sl"+masp[i]%>'  value='<%= sl[i] %>' id='<%= "sl"+masp[i]%>'  onChange= setTTienbVAT(); onKeyPress="return submitenter(this,event)") style="text-align: right; width :100%" readonly="readonly"> </TD>
                                                       <TD><input type="text"  name="dv" value='<%= donvi[i] %>' readonly="readonly" style="text-align: center;width:100%"></TD>
                       								   <TD><input type="text"  name='<%="dg"+masp[i]%>'  value='<%= giamua[i] %>' id='<%= "dg"+masp[i]%>'  readonly="readonly" style="text-align: right;width:100%"> </TD>
                                                       <TD><input type="text"  name='<%= "t"+ masp[i]%>' value= '<%= formatter.format(Float.parseFloat(tienbVAT[i])) %>' id='<%= "t"+ masp[i]%>'  readonly="readonly" style="text-align: right; width:100%">  </TD>																							
												                                              
                                                  </TR>
                                            <%} %>

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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<% if (!(ncc == null)) ncc.close(); %>
<% if (!(dvkd == null)) dvkd.close(); %>
<% 
	if(kbh!=null){
		kbh.close();
	}
	if(kho!=null){
		kho.close();
	}
	masp=null;
	tensp=null;
	sl=null;
	tienbVAT=null;
	giamua=null;
	donvi=null;
	session.setAttribute("nhaphang",null);
	if(hdnhaphang != null){
		hdnhaphang.DBclose();
		hdnhaphang = null;}
		%>
<%}%>