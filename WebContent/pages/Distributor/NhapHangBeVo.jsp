<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page  import = "geso.dms.distributor.beans.nhaphang.INhaphang" %>
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

<% INhaphang nhaphang = (INhaphang)session.getAttribute("nhaphang"); %>
<% String id = nhaphang.getId();   %>
<% String sohoadon = nhaphang.getSohoadon(); %>
<% ResultSet ncc = (ResultSet) nhaphang.getNcc(); %>
<% ResultSet dvkd = (ResultSet)nhaphang.getDvkdIds(); %>
<% ResultSet kbh = (ResultSet)nhaphang.getKbhIds(); %>
<% ResultSet kho = (ResultSet)nhaphang.getKhoIds(); %>
<% ResultSet Rsgsbh = (ResultSet)nhaphang.getGSBH(); %>
<%String[] spId=nhaphang.getSpId(); %>
<% String[] masp = nhaphang.getMasp() ; %>
<% String[] tensp = nhaphang.getTensp(); %>
<% String[] sl = nhaphang.getSl(); %>
<% String[] tienbVAT = nhaphang.getTienbVAT(); %>
<% String[] giamua = nhaphang.getGiamua() ; %>
<% String[] donvi = nhaphang.getDonvi() ; %>
<% String[] hangbevo = nhaphang.gethang_be_vo() ; %>

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
	
   document.getElementById("nhanhangid").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
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
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="init()" >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form id="nhaphangForm" name="nhaphangForm" method="post" action="../../NhaphangUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="id" value='<%=id %>'>
<input type="hidden" name="action" value='hangbevo'>

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
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tồn kho &gt; &nbsp;Mua hàng từ Nhà cung cấp &gt; &nbsp;Nhận hàng &gt Ghi nhận hàng bể vỡ
                                        <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= nhaphang.getNppTen() %> &nbsp;</TD>
                                    </tr>
                                  </table>
                              </TD>
                         </TR> 
                     </TABLE>
		            <TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
        		        <TR ><TD >
                		    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        		<TR class = "tbdarkrow">

                            		<TD width="30" align="left"><A href="javascript:history.back()"  ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
                            		<TD width="2" align="left" >&nbsp;</TD>
                            		<TD width="30" align="left" ></TD>
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
                        			<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%" readonly="readonly" rows="1" ><%= nhaphang.getMessage() %></textarea>
                        			<% nhaphang.setMessage(""); %>
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
                                      	<TD class="plainlabel"><%= nhaphang.getNppTen() %>
                                      	<%-- <TD class="plainlabel">
                                        	<input type="text" id="nppTen" name="nppTen" value="<%= nhaphang.getNppTen() %>" size=40 readonly="readonly"> --%>                                     		
                                      	</TD>
                                      	                                      	
										<TD width="15%" class="plainlabel" >Ngày chứng từ </TD>
									  	<TD class="plainlabel" >
											<input type="text" name="tungay" value="<%=nhaphang.getNgaychungtu() %>" size="20" readonly="readonly">
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
                                                                              
										<TD class="plainlabel" >Ngày nhận hàng </TD>
									  	<TD class="plainlabel" >
											<input type="text" name="ngaynhanhang" value="<%= nhaphang.getNgaynhanhang() %>" size="20" readonly="readonly">
										</TD>								
                                  </TR>
								
								 <TR>     	                                      	
										<TD class="plainlabel">Đơn vị kinh doanh </TD>
				    					<TD class="plainlabel">
				    						<SELECT  name="dvkdId"  readonly="readonly">
								  		
									  	 <% try{ while(dvkd.next()){ 
										    	if(dvkd.getString("dvkdId").equals(nhaphang.getDvkdId())){ %>
													<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
											  <%}else{ %>
													<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
										  <%}}}catch(java.sql.SQLException e){} %>	
                    						</select>
                    					</TD>
                    					
                                        <TD  class="plainlabel">VAT (5%) </TD>
                                        <TD colspan="3" class="plainlabel"><input type="text" name="vat" id="vat"  value="<%= formatter.format(Float.parseFloat(nhaphang.getTongVat())) %>" readonly="readonly" style="text-align: right">
                                         VND</TD>                                                    							                                      
                                </TR>
										
								<TR>										
									<TD class="plainlabel">Kênh bán hàng </TD>
			    					<TD class="plainlabel">
			    						<SELECT  name="kbhId" readonly="readonly">
							  		
								  	 <%
								  	 if(nhaphang.getKbhId()==null){
								  		 nhaphang.setKbhId("");
								  	 }
								  		 
								  	 try{
								  		 while(kbh.next()){ 
									    	if(kbh.getString("kbhId").equals(nhaphang.getKbhId())){ %>
												<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
										  <%}else{ %>
												<option value='<%=kbh.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
									  <%}}
								  		 }catch(java.sql.SQLException e){} %>	
                   						</select>
                   					</TD>	
                   					                   					
                                    <TD  class="plainlabel">Tổng số tiền(chưa VAT) </TD>
                                    <TD colspan="3" class="plainlabel"><input type="text" name="tongtienbvat" id="tongtienbvat" readonly="readonly" value="<%= formatter.format(Float.parseFloat(nhaphang.getTongtienbVAT())) %>" style="text-align: right"  >
                                     VND </TD>                                                    						
								</TR>
	
								<TR>											
									<TD class="plainlabel">Nhập vào kho </TD>
			    					<TD class="plainlabel">
			    						<SELECT  name="khoId"  >
								  	 <% try{ while(kho.next()){ 
									    	if(kho.getString("khoId").equals(nhaphang.getKhoId())){ %>
												<option value='<%=kho.getString("khoId")%>' selected><%=kho.getString("kho") %></option>
											<%}else{ %>
												<option value='<%=kho.getString("khoId")%>'><%=kho.getString("kho") %></option>
									  <%}}}catch(java.sql.SQLException e){} %>	
                   						</select>
                   					</TD>
                   					         					
                                    <TD  class="plainlabel">Tổng số tiền (có VAT) </TD>
                                    <TD colspan="3" class="plainlabel"><input type="text" name="tongtienavat" id="tongtienavat" readonly="readonly" value="<%= formatter.format(Float.parseFloat(nhaphang.getTongtienaVAT())) %>"  style="text-align: right" >
                                     VND </TD>						
								</TR>
								
								 <TR>                                       
										<TD class="plainlabel">Giám sát bán hàng </TD>
				    					<TD class="plainlabel">
				    						<SELECT  name="gsbhId"  >
				    						
									  	 <% try{ while(Rsgsbh.next()){ 
										    	if(Rsgsbh.getString("pk_seq").equals(nhaphang.getGsbhId())){ %>
													<option value='<%=Rsgsbh.getString("pk_seq")%>' selected><%=Rsgsbh.getString("ten") %></option>
												<%}else{ %>
													<option value='<%=Rsgsbh.getString("pk_seq")%>'><%=Rsgsbh.getString("ten") %></option>
										  <%}}}catch(java.sql.SQLException e){} %>	
                    						</select>
                    					</TD>

                                        <TD  class="plainlabel">Nhập số hóa đơn </TD>
                                        <TD colspan="3" class="plainlabel"> <input type="text" name="sohoadon" id="sohoadon" value="<%=nhaphang.getSohoadon()%>"  style="text-align: right" >						
                              	</TR>					
                                
                                <TR class="tblightrow">
                                  <TD  class="plainlabel">Bằng chữ </TD>
                                  <TD colspan="3" class="plainlabel"> <div id="lblDocSo" style="font-weight:bold;font-style:italic;"></div> </TD>
                               </TR>                                                               
       
                                <TR class="tblightrow">
                                  <TD  class="plainlabel" align="left" colspan="4">
                                  <a class="button2" id="nhanhangid" onclick="javascript:submitform();">
								  <img style="top: -4px;"  src="../images/button.png" alt="">Ghi nhận hàng bể vỡ</a>&nbsp;&nbsp;&nbsp;&nbsp;	                                                     
                                  
                                  </TD>
                               </TR>

                            </TABLE>
                        
                                                <TABLE  width = 100% cellpadding="0"  border="0" cellspacing="1" >
                                                    <TR class="tbheader" >
                                                        <TH width="15%">Mã sản phẩm </TH>
                                                        <TH width="30%">Tên sản phẩm</TH>
                                                        <TH width="10%">Số lượng </TH>
                                                        <TH width="10%">Đơn vị </TH>
                                                        <TH width="10%">Giá mua </TH>
                                                        <TH width="15%">T. tiền (w/o VAT) </TH>
                                                        <TH width="10%">Hàng bể vỡ </TH>
                                                    </TR>

															                                                    
                                    		<% 

               								String lightrow = "tblightrow";
               								
                                            int size = new Integer(nhaphang.getSize()).intValue();
                                            
                                    		for(int i=0;i< size ;i++){ 			%>			
													<TR class= <%=lightrow%> >         
                                                       <TD>
                                                       		<input type="text"  name="masp" value='<%= masp[i] %>'  readonly="readonly" style=" width: 100%"> 
                                                       		<input type="hidden" name="spId"  value='<%=spId[i]%>'  readonly="readonly" style=" width: 100%"/>
                                                       </TD>
                                                       <TD><input type="text"  name="tensp" value='<%= tensp[i] %>'  readonly="readonly" style=" width: 100%"></TD>
                                                       <TD><input type="text" name="soluong"  value='<%= Math.round(Double.parseDouble(sl[i])) %>' id='<%= "sl"+masp[i]%>'  onChange= setTTienbVAT();  style="text-align: right; width :100%" readonly="readonly"> </TD>
                                                       <TD><input type="text"  name="donvi" value='<%= donvi[i] %>' readonly="readonly" style="text-align: center;width:100%"></TD>
                       								   <TD><input type="text"  name="dongia"  value='<%= Math.round(Double.parseDouble(giamua[i])) %>' id='<%= "dg"+masp[i]%>'  readonly="readonly" style="text-align: right;width:100%"> </TD>
                                                       <TD><input type="text"  name= "thanhtien" value= '<%= formatter.format(Float.parseFloat(tienbVAT[i])) %>' id='<%= "t"+ masp[i]%>'  readonly="readonly" style="text-align: right; width:100%">  </TD>																							
												       <TD><input type="text"  name= "hangbevo" value= '<%= formatter.format(Float.parseFloat(hangbevo[i])) %>' id='<%= "t"+ masp[i]%>'   style="text-align: right; width:100%">  </TD>                                       
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
</body>
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
	if(Rsgsbh!=null){
		Rsgsbh.close();
	}
	masp=null;
	tensp=null;
	sl=null;
	tienbVAT=null;
	giamua=null;
	donvi=null;
	session.setAttribute("nhaphang",null);
	if(nhaphang != null){
		nhaphang.DBclose();
		nhaphang = null;}
		%>
	
<%}%>