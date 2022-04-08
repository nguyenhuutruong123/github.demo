<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="geso.dms.distributor.beans.denghidathangnpp.IDanhsachsanpham"%>
<%@page import="geso.dms.distributor.beans.denghidathangnpp.IDenghidathangnpp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.denghidathang.IDenghidathang" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page import="java.util.*;"%>  
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IDenghidathangnpp dndhBean = (IDenghidathangnpp)session.getAttribute("dndhBean"); %>
<% ResultSet ncc = (ResultSet) dndhBean.getNcc(); %>
<% String nccId = (String) dndhBean.getNccId(); %>

<% ResultSet dvkd = (ResultSet)dndhBean.getDvkdIds(); %>
<% String dvkdId = (String)dndhBean.getDvkdId(); %>

<% ResultSet kbh = (ResultSet)dndhBean.getKbhIds(); %>
<% String kbhId = (String)dndhBean.getKbhId();
   List<IDanhsachsanpham> danhsachsanpham = dndhBean.getDanhsachsanpham(); 
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Acecook - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<SCRIPT src="../js/md5.js" type="text/javascript" language="javascript">
</SCRIPT>
<SCRIPT src="../js/scripts.js" type="text/javascript"
    language="javascript">
</SCRIPT>
<SCRIPT src="../js/commun.js" type="text/javascript"
    language="javascript">
</SCRIPT>

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<SCRIPT type="text/javascript">
function submitform()
{   
	var ngaydenghi=document.forms["dndhFormnpp"].ngaydn.value;
	var nhacungcap=document.forms["dndhFormnpp"].nccId.value;
	var kenh=document.forms["dndhFormnpp"].kbhId.value;
	var dvkd=document.forms["dndhFormnpp"].dvkdId.value;
	var tanso=document.forms["dndhFormnpp"].tanso.value;
	if(ngaydenghi==""){
		document.forms["dndhFormnpp"].dataerror.value="Nhập ngày đề nghị";
		return;
	}
	if(ngaydenghi==""){
		document.forms["dndhFormnpp"].dataerror.value="Nhập ngày đề nghị";
		return;
	}
	if(nhacungcap==""){
		document.forms["dndhFormnpp"].dataerror.value="Chọn nhà cung cấp để gửi đề nghị";
		return;
	}
	if(kenh==""){
		document.forms["dndhFormnpp"].dataerror.value="Chọn kênh để gửi đề nghị";
		return;
	}
	if(dvkd==""){
		document.forms["dndhFormnpp"].dataerror.value="Chọn đơn vị kinh doanh để gửi đề nghị";
		return;
	}
	if(tanso==""){
		document.forms["dndhFormnpp"].dataerror.value="Nhập tần số đề nghị đặt hàng";
		return;
	}
   document.forms["dndhFormnpp"].submit();
}
function thuchien()
{
	var ngaydenghi=document.forms["dndhFormnpp"].ngaydn.value;
	var nhacungcap=document.forms["dndhFormnpp"].nccId.value;
	var kenh=document.forms["dndhFormnpp"].kbhId.value;
	var dvkd=document.forms["dndhFormnpp"].dvkdId.value;
	var tanso=document.forms["dndhFormnpp"].tanso.value;
	if(ngaydenghi==""){
		document.forms["dndhFormnpp"].dataerror.value="Nhập ngày đề nghị";
		return;
	}
	if(ngaydenghi==""){
		document.forms["dndhFormnpp"].dataerror.value="Nhập ngày đề nghị";
		return;
	}
	if(nhacungcap==""){
		document.forms["dndhFormnpp"].dataerror.value="Chọn nhà cung cấp để gửi đề nghị";
		return;
	}
	if(kenh==""){
		document.forms["dndhFormnpp"].dataerror.value="Chọn kênh để gửi đề nghị";
		return;
	}
	if(dvkd==""){
		document.forms["dndhFormnpp"].dataerror.value="Chọn đơn vị kinh doanh để gửi đề nghị";
		return;
	}
	if(tanso==""){
		document.forms["dndhFormnpp"].dataerror.value="Nhập tần số đề nghị đặt hàng";
		return;
	}
	var today = new Date(ngaydenghi);//đổi ra kiểu ngày tháng và bị lỗi, khi đó nó có giá trị Invalid Date
		if(today=="Invalid Date"){
			 document.forms["dhForm"].dataerror.value="Ngày đề nghị đặt hàng không đúng định dạng ngày tháng,kiểu định dạng yyyy-MM-dd ";
	    return; 
			}
	document.forms["dndhFormnpp"].action.value ="thuchien";
	document.forms["dndhFormnpp"].submit();
}
function saveform()
{
	var ngaydenghi=document.forms["dndhFormnpp"].ngaydn.value;
	var nhacungcap=document.forms["dndhFormnpp"].nccId.value;
	var kenh=document.forms["dndhFormnpp"].kbhId.value;
	var dvkd=document.forms["dndhFormnpp"].dvkdId.value;
	var tanso=document.forms["dndhFormnpp"].tanso.value;
	if(ngaydenghi==""){
		document.forms["dndhFormnpp"].dataerror.value="Nhập ngày đề nghị";
		return;
	}
	if(ngaydenghi==""){
		document.forms["dndhFormnpp"].dataerror.value="Nhập ngày đề nghị";
		return;
	}
	if(nhacungcap==""){
		document.forms["dndhFormnpp"].dataerror.value="Chọn nhà cung cấp để gửi đề nghị";
		return;
	}
	if(kenh==""){
		document.forms["dndhFormnpp"].dataerror.value="Chọn kênh để gửi đề nghị";
		return;
	}
	if(dvkd==""){
		document.forms["dndhFormnpp"].dataerror.value="Chọn đơn vị kinh doanh để gửi đề nghị";
		return;
	}
	if(tanso==""){
		document.forms["dndhFormnpp"].dataerror.value="Nhập tần số đề nghị đặt hàng";
		return;
	}
	document.forms["dndhFormnpp"].action.value ="save";
	document.forms["dndhFormnpp"].submit();

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

<form name="dndhFormnpp" method="post" action="../../DenghidathangnppUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="nppId" type="hidden" value='<%=dndhBean.getNppId() %>' size="30">
<input type="hidden" name="action" value='next'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD colspan="4" align='left' valign='top'> <!--begin body Dossier-->

            <TABLE width="100%" cellspacing="0" cellpadding="0">
                <TR>
                    <TD align="left" >
	                	<TABLE width="100%" cellspacing="1" cellpadding="0">
    	                	<TR>
        	                	<TD align="left">
            	                	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                	                	<tr height="22">
                    	                	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý kho &gt; &nbsp;Quản lý mua hàng &gt; &nbsp; Đề nghị &gt; &nbsp; tạo mới
                                   
                        	                <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= dndhBean.getNppTen() %> &nbsp;</TD>
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
                            <%if(dndhBean.getTrangthai().trim().equals("0")){ %>
                             <TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border = "1"  style="border-style:outset"></A></TD>
                            <TD align="left" >&nbsp;</TD>
                            <%} %>
                            
                        </TR>
                    </TABLE>
                </TD></TR>
            </TABLE>

            <TABLE width = 100% cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%" readonly="readonly" rows="1"  ><%=dndhBean.getMessage()%></textarea>
						<% dndhBean.setMessage(""); %>
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

                                    <LEGEND class="legendtitle">&nbsp;Đề nghị đặt hàng &nbsp;</LEGEND>
                                                <TABLE cellpadding = 4 cellspacing = 0 width = "100%" border = 0>
                                                    <TR>
                                                        <TD width="20%" class="plainlabel">Nhà phân phối  </TD>
                                                        <TD width="70%" class="plainlabel"><%= dndhBean.getNppTen() %></TD>                                                   
                                                    </TR>

                            
                                                    <TR >
                                                        <TD class="plainlabel">Ngày đề nghị </TD>
                                                        <TD class="plainlabel">
        
                                                            <table border=0 cellpadding = 0 cellspacing = 0>
                                                                <tr>
                                                                    <td class="plainlabel"><input class="days" type="text" name="ngaydn" size="11" value="<%=dndhBean.getNgaydn() %>" ></td>
                                                                                                                                     
                                                                </tr>
                                                            </table>                                                
                                                        </TD>
                                                    </TR>
                                                    <TR class="tblightrow">
                                                        <TD  class="plainlabel">Nhà cung cấp </TD>
                                                        <TD  class="plainlabel"> 
                                                          <select name="nccId" onChange="submitform();">
                                                          	
                                                           <% try{ %>
                                                       			    	
                                                         	<%   if(ncc != null){
                                                         			while(ncc.next()){      
                                                            	      if (ncc.getString("nccId").equals(nccId)){ %>   
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

                                                    </TR>
													<TR>
														<TD class="plainlabel">Đơn vị kinh doanh </TD>
								    					<TD class="plainlabel">
								    						<SELECT  name="dvkdId"  onChange="submitform();">
								  								<OPTION value""></OPTION>
														  	 <% if(!(dvkd ==null)){
														  	 		try{ while(dvkd.next()){ 
															    		if(dvkd.getString("dvkdId").equals(dndhBean.getDvkdId())){ %>
								      										<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      							  	  <%}else{ %>
								     										<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     							  <%}}}catch(java.sql.SQLException e){} 
								     							 }%>	
                                  							</select>
                                  						</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Kênh bán hàng </TD>
								    					<TD class="plainlabel">
								    						<SELECT  name="kbhId" >
								  								<OPTION value""></OPTION>
														  	 <% if(kbh != null) 
														  	  try{ while(kbh.next()){ 
															    	if(kbh.getString("kbhId").equals(dndhBean.getKbhId())){ %>
								      									<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
								      							  <%}else{ %>
								     									<option value='<%=kbh.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
								     							  <%}}}catch(java.sql.SQLException e){} %>	
                                  							</select>
                                  						</TD>
													</TR>
                                                    <TR >
                                                        <TD class="plainlabel">Tần số đặt hàng</TD>
                                                        <TD class="plainlabel">
        
                                                            <table border=0 cellpadding = 0 cellspacing = 0>
                                                                <tr>
                                                                    <td class="plainlabel"><input type="text" name="tanso" size="5" value="<%=dndhBean.getTanso() %>" ></td>
                                                                    <td class="plainlabel">&nbsp;lần / tuần</td>
                                                                </tr>
                                                            </table>                                                
                                                        </TD>
                                                    </TR>
                                                	<TR>
														<TD class="plainlabel">Tổng số tiền(chưa Vat)</TD>
								    					<TD class="plainlabel">
								    					 <input type ="text" name ="tongchuavat" id ="tongchuavat" value ="0" readonly>
								    					</TD>
													</TR>
                                                   <TR>
														<TD class="plainlabel">VAT(10%)</TD>
								    					<TD class="plainlabel">
								    					 <input type ="text" name ="vat" id ="vat" value ="0" readonly>
								    					</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Tổng số có(chưa Vat)</TD>
								    					<TD class="plainlabel">
								    					 <input type ="text" name ="tongcovat" id ="tongcovat" value ="0" readonly>
								    					</TD>
													</TR>
													<TR>
														
													
														<TD class="plainlabel" colspan=2>
														 <%if(dndhBean.getTrangthai().trim().equals("0")){ %>
                           
														<a class="button2" name="next" href="javascript:thuchien();" >
													<img style="top: -4px;" src="../images/button.png" alt="">Tiếp tục</a>&nbsp;&nbsp;&nbsp;&nbsp;
													<%} %>	
													<!--	<INPUT type="submit" name="next" value="Tiep tuc">  --></TD>
													</TR>
													<tr>
                                                   <TABLE  width = "100%" cellpadding="0" class="tabledetail"  cellspacing="1" >
	                                                    <TR class="tbheader" >
	                                                        <TH width="13%">Mã sản phẩm </TH>
	                                                        <TH width="17%">Tên sản phẩm</TH>
	                                                        <TH width="7%">Tồn cuối(L) </TH>
	                                                        <TH width="7%">TB bán/ngày(L) </TH>                                                        
	                                                        <TH width="7%">Tồn ngày(L) </TH>
	                                                        <TH width="7%">Dữ kiện bán(L) </TH>
	                                                        <TH width="7%">Đề nghị đặt(T) </TH>
	                                                        <TH width="7%">Đơn vị </TH>
	                                                        <TH width="7%">Đơn giá(T) </TH>
	                                                        <TH width="7%">T. tiền </TH>
	                                                        <TH width="7%">Đã đặt </TH>
	                                                        <TH width="7%">Còn lại </TH>
	                                                    </TR>
	                                                    <% int i =0;
	                                                        int size = danhsachsanpham.size();
	                                                        if(size>0)
	                                                    	System.out.println(i);
	                                                       	long tongtien = 0;
	                                                        long tongtiencovat=0;
	                                                        while(i < size) {
	                                                       IDanhsachsanpham	sp = danhsachsanpham.get(i);
	                                                    //   tongtien = tongtien + Integer.parseInt(sp.getConlai());
	                                                         tongtien = tongtien + Integer.parseInt(sp.getTongtien()+"");
	                                                       if(i % 2 == 0){ 
	                                                    %>  
	                                                    	<TR class='tblightrow'>
	                                                    	<%} else { %>
	                                                    	<TR class='tblightrow'> 
	                                                    	<%} %>
	                                                        <TD ><input type="hidden" name="idsanpham" value="<%=sp.getIdSanPham() %>" >  <input style="width:100%" type ="text"name ="masp"  value ='<%=sp.getMasp() %>' readonly> </TD>
	                                                        <TD  ><input  style="width:100%" type ="text"name ="tensanpham"  value ='<%= sp.getTensp() %>'readonly></TD>
	                                                        <TD  ><input  style="width:100%" type ="text"name ="toncuoi"  value ='<%= sp.getTonCuoi() %>'readonly> </TD>
	                                                        <TD  ><input   type ="text" style="width:100%" name ="bantheongay"  value ='<%=sp.getBantheongay() %>'readonly> </TD>                                                        
	                                                        <TD  ><input  type ="text" style="width:100%" name ="tonngay" value ='<%=sp.getTonngay()%>'readonly> </TD>
	                                                        <TD  ><input  type ="text" style="width:100%" name ="dukienban"  value ='<%=sp.getDukienban()%>'readonly> </TD>
	                                                        <TD  ><input type ="text" name ="denghi"  style="width:100%" value ='<%=sp.getDenghi()%>'readonly> </TD>
	                                                        <TD  ><input type="text" value=" <%=sp.getDonvi()  %>" readonly="readonly"> </TD>
	                                                         <TD  ><input  type ="text" name ="dongia" style="width:100%"  value ='<%=sp.getDongia() %>'readonly> </TD>
	                                                         <TD ><input  type ="text" name ="tongtien" style="width:100%"  value ='<%=sp.getTongtien()%>'readonly> </TD>
	                                                        <TD  ><input  type ="text" name ="dadat" style="width:100%"  value ='0' readonly> </TD>
	                                                        <TD  ><input  type ="text" name ="conlai" id ="conlai" style="width:100%"  value ='<%=sp.getConlai()%>'readonly> </TD>
	                                                    </TR>
	                                                   <%
	                                                   		tongtien=tongtien + Long.parseLong(sp.getTongtien());
	                                                   		i++;
	                                                   }
	                                                        int vat =(int)(Float.parseFloat(tongtien+"") * Float.parseFloat("0.1"));
	                                                   		tongtiencovat= tongtien+ vat;
	                                                        System.out.println("tong Tien :"+tongtien);
	                                                        System.out.println("tong Tien Co Vat :"+tongtiencovat);
	                                                        
	                                                   %>
	                                                   
	                                               </TABLE>
                                                 
													  </tr>
                                                   
                                                </TABLE>
                                               </FIELDSET>
                                            </TD>
                                    </TR>

											<script type="text/javascript">
										        var tongtien = document.getElementById("tongchuavat");
										        var vat = document.getElementById("vat");
										        var tongcovat = document.getElementById("tongcovat");
										        
										        tongtien.value ='<%= tongtien%>';
										        vat.value ='10';
										        tongcovat.value ='<%=tongtiencovat%>';
											</script>
                                   
                                </TABLE>
                                
                        </TD>
                    </TR>
                     
            </TABLE>
    </td>

  </tr>

</table>


</TABLE>
</form>

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<% if(!(ncc == null))  ncc.close(); %>
<% if(!(dvkd == null)) dvkd.close(); %>
<% if(!(kbh == null)) kbh.close(); %>
<% if(dndhBean != null){
	dndhBean.DBclose();
	dndhBean = null;
}%>
<%}%>