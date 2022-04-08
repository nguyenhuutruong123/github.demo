<%@page import="geso.dms.distributor.beans.catalog.imp.catalog"%>
<%@page import="geso.dms.center.beans.asm.imp.AsmList"%>
<%@ page import = "geso.dms.center.beans.bcchart.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>

<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.dms.center.beans.bcchart.*" %>
<%@ page import = "geso.dms.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util"); 	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/opv/index.jsp");
	}else{ 
		IBcchart obj =(IBcchart)session.getAttribute("obj");

	
	String[] arrIdMien=obj.getArrIDMien();
	String[] arrTenMien=obj.getArrTenMien();
	ResultSet spRs=obj.getSpRs();
	ResultSet rskenh=obj.getRsKenh();
	ResultSet rsvung=obj.getRsVung();
	Long [][]doanhso = obj.getDataDoanhSoNgay();

%>
<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<%NumberFormat formatter2 = new DecimalFormat("#,###,###.####"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>OPV - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$( ".days" ).datepicker({
				changeMonth: true,
				changeYear: true
			});
		});
	</script>
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
	jQuery(document).ready(function()
	{
		$("select:not(.notuseselect2)").chosen();     
		
	});
	
	/* $(document).ready(function(){
		  $(".path").mouseover(function(){
		    $(".path").css{background-color: Highlight; color: HighlightText;};
		  });
		  $(".path").mouseout(function(){
		    $(".path").css{ display: block; color: WindowText; background-color: Window; margin: 0; padding: 0; width: 100%; };
		  });
		}); */
</script>


  <style type="text/css">
   ul { height: 300px; overflow:auto; width: 400px; border: 1px solid #000; }
   ul { list-style-type: none; margin: 0; padding: 0; overflow-x: hidden; width: 400px }
   li { margin: 0; padding: 0; }
   label { display: block; color: WindowText; background-color: Window; margin: 0; padding: 0; width: 100%; }
   label:hover { background-color: Highlight; color: HighlightText; }
  </style>	


<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
	jQuery(document).ready(function()
	{
		$("select:not(.notuseselect2)").chosen();     
		
	});
</script>
<style>
	body{
		text-align: center;
		font-family: Arial, sans-serif;
		font-size: 12px;
	}
	
	a{
		text-decoration: none;
		font-weight: bold;
		color: #555;
	}
	
	a:hover{
		color: #000;
	}
	
	div.main{
		/* margin: auto;
		*/
		text-align: left;
		width: 1200px;
	}
	
	.gvChart{
		/*border: 2px solid #850000;
		border-radius: 5px;
		-moz-border-radius: 10px;
		
			margin: auto;*/
		width: 800px;
		
	
		margin-left:0px;
		margin-top: 0px;
	}

</style>
	<SCRIPT language="javascript" type="text/javascript">


	function submitform()
	{
		
		document.forms['khtt'].action.value= 'xem';
		document.forms["khtt"].submit();
	}


	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../BCChartAllSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="view" value="<%= obj.getView() %>" >
<input type="hidden" name="action" value="1" >
<%-- <input type="hidden" id ="sosp" name="sosp" value="<%= doanhso.length %>" > --%>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" onmouseover="javascript:assignHover()">
	<TR>
		<TD colspan="4" align="left" valign="top" bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR height="22">
								<TD align="left" colspan="2" class="tbnavigation">&nbsp;Báo cáo quản trị > Báo cáo chart > Tăng trưởng doanh số theo ngày </TD>
								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left" >
									<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Phạm vi báo cáo &nbsp;</LEGEND>

										<TABLE width="100%" cellpadding="6" cellspacing="0">
									<TR >
											<TD class="plainlabel">Đến ngày</TD>
											<TD class="plainlabel">
												<input type="text" name="thang"	class="days" value='<%=obj.getThang()%>' style="width:250px"/></TD>
											<TD class="plainlabel">Vùng miền</TD>
											<TD class="plainlabel">
											<select name="spVung" id="spVung"  style="width:400px;height: 300px" >
												<option value="">&nbsp; </option>
											
												<%
												if (rsvung != null)
													while (rsvung.next()) {
														 
															if(obj.getSpVungId().indexOf(rsvung.getString("PK_SEQ")) >= 0)
														{
													%>
														 <option value="<%=rsvung.getString("PK_SEQ")%>" selected="selected" > <%=rsvung.getString("TEN")%> </option>
													<%
														}
														else{
													%>
														 <option value="<%=rsvung.getString("PK_SEQ")%>" > <%=rsvung.getString("TEN")%> </option>
													<%
															}
											 
													}
											
									%>
											</select></TD>
									</TR>
						
							
								<Tr>
									<TD class="plainlabel">Sản phẩm</TD>
										<TD class="plainlabel">
											<select name="spId" id="spId" style="width:400px;height: 300px"  multiple="multiple">
													<%if (spRs != null)
															while (spRs.next()) {
																if (obj.getSpId().indexOf(spRs.getString("pk_seq")) >=0){	%>
																<option value="<%=spRs.getString("pk_seq")%>" selected><%=spRs.getString("ma") + " - " +spRs.getString("ten")%></option>
															<%} else {%>
																<option value="<%=spRs.getString("pk_seq")%>"><%=spRs.getString("ma") + " - "+spRs.getString("ten")%></option>
													<%}}%>
											</select>
										</TD>
										<TD class="plainlabel">Kênh bán hàng</TD>
										<TD class="plainlabel">
											<select name="spKenh" id="spKenh"  style="width:400px;height: 300px" >
											<option value="">&nbsp; </option>
										<%
												if (rskenh != null)
													while (rskenh.next()) {
														 
															if(obj.getSpKenhId().indexOf(rskenh.getString("PK_SEQ")) >= 0)
														{
													%>
														 <option value="<%=rskenh.getString("PK_SEQ")%>" selected="selected" > <%=rskenh.getString("TEN")%> </option>
													<%
														}
														else{
													%>
														 <option value="<%=rskenh.getString("PK_SEQ")%>" > <%=rskenh.getString("TEN")%> </option>
													<%
															}
											 
													}
													
									%>
									
									</select>

										</TD>
									</tr>					
								
								<TR>
									<TD class="plainlabel" colspan="4">																			
									<a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo
									</a>
									</td>
								</TR>
							</TABLE>
									</FIELDSET>
								</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>

				<TR>
					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Báo cáo doanh số sản phẩm &nbsp;&nbsp;
								
							</LEGEND>
						
						
								<%
								int k = 0;
								
								
							%>
							<table align="left" id='myTable'  >
								
									<caption align="left" >Doanh số (Đơn vị tính: Nghìn đồng,ngày) </caption>
									
									<thead>
									
										<tr>
											<th>Tháng </th>
											
											<%  //In ra List ngày theo tháng
											int dodai = 0;
											
											if(obj.getThang().length() > 0)
												dodai = Integer.parseInt(obj.getThang().substring(8,10));
											if(obj.getIsCuoiThang() ==1)
												dodai= 31;
												for(int i= 0;i < dodai;i++)
												{
													%>
													<th><%= i+1 %></th>
											<% 		
												}
											
												
											%>             
										</tr>
									</thead>
									<tbody>
									<%
									int thanght = 0,thangt = 0,namht = 0,namt = 0;
									if(obj.getThang().length() > 0)
									{
										thanght = Integer.parseInt(obj.getThang().substring(5,7));
										thangt = Integer.parseInt(obj.getThang().substring(5,7));
										namht = Integer.parseInt(obj.getThang().substring(0,4));
										namt = Integer.parseInt(obj.getThang().substring(0,4));
										if(thangt == 1)
										{
											thangt = 13;
											namt = namt - 1;
										}
										
									}
									
											while( doanhso != null && k < 4) {
											 %>
									     <tr>
									     		<% if(k == 0) {%>
												<th>Doanh số tháng  <%= thanght %>-<%= namht %>  </th>
												<% } else if(k == 1) {%>
												<th>Doanh số tháng <%= (thangt -1)<=0?(thangt -1)+12:(thangt -1) %>-<%= (thangt -1)<=0?(namt-1):namt %> </th>
												<%} else if(k == 2) {%>
												<th>Doanh số tháng <%= (thangt -2)<=0?(thangt -2)+12:(thangt -2) %>-<%= (thangt -2)<=0?(namt-1):namt %> </th>
												<%} else  if(k == 3) {%>
												<th>Doanh số tháng <%= (thangt -3)<=0?(thangt -3)+12:(thangt -3) %>-<%= (thangt -3)<=0?(namt-1):namt %>  </th>
											<%} %>
											
											<% 
											
											if(obj.getThang().length() > 0)
												dodai = Integer.parseInt(obj.getThang().substring(8,10));
											if(obj.getIsCuoiThang() ==1)
												dodai= 31;
											System.out.println("DoDai: "+dodai);
											
											long tong = 0;
											for(int j= 0;j < dodai ;j++)
											{
											
													try {
														System.out.println("doanhso[k][j]: "+doanhso[k][j]);
												 	tong+= doanhso[k][j];
													}catch(Exception e)
													{
														
													}
											
											%>
													<%if( doanhso[k][j] != null  ){
													
														%>
															<td><%=tong/1000%></td>
													
													<%} %>
													
											<%
												
											}%>
										</tr>	
											
										<% k++; 
										} %>  
									</tbody>	
								</table>
								
						</FIELDSET>
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>



<script>
        function assignevent(index){
            var color = $("path:eq("+index+")").attr("stroke");
            $("path:eq("+index+")").parent().off("mouseover");
            $("path:eq("+index+")").parent().off("mouseover");

            
            $("path:eq("+index+")").parent().mouseover(function(event){
            	event.preventDefault();
                $("[stroke="+color+"]:last").attr("stroke-width",8);
            });
            $("path:eq("+index+")").parent().mouseout(function(){
                $("[stroke="+color+"]:last").attr("stroke-width",2);
             });

        } 

        
        function assignHover() {
            var num = document.getElementById("gvChartDiv1").childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[3].childNodes.length;
			num = num -1 ;
			var i ;
			for( i = 0 ; i < num ; i++){
				assignevent(i);

			}
			
        }
</script>

<script type="text/javascript" src="../scripts/Chart/jsapi"></script>
<script src="../scripts/Chart/jquery.gvChart-0.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	gvChartInit();

		jQuery(document).ready(function(){
			
			var table = '#myTable';
			jQuery(table).gvChart({
				chartType: 'LineChart',
				gvSettings: {
					vAxis: {title: 'Doanh số sản phẩm (Đơn vị tính: Nghìn đồng,Ngày) '},
					hAxis: {title: 'Ngày'},
					width: 1300,
					height:600
					}
			});
			
		});
		
</script>

</form>
</BODY>	
</html>


<%
		

		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}	
%>

