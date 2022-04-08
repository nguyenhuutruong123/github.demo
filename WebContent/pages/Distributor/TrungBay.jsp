<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.donhang.*" %>
<%@ page  import = "geso.dms.distributor.beans.donhang.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.Enumeration" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% XLTrungbay xltb = (XLTrungbay)session.getAttribute("xltb"); %>
<% ResultSet TBlist = xltb.getCttbList(); %>
<% Hashtable<String, Integer> cotrungbay = (Hashtable<String, Integer>)xltb.getTrungbay(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
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
	<script type="text/javascript">
  		function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
  		{    
  			var keypressed = null;
  			if (window.event)
  				keypressed = window.event.keyCode; //IE
  			else
  				keypressed = e.which; //NON-IE, Standard
  			
  			if (keypressed < 48 || keypressed > 57)
  			{ 
  				if (keypressed == 8 || keypressed == 127)
  				{//Phím Delete và Phím Back
  					return;
  				}
  				return false;
  			}
  		}
  		
  		function submitform()
  		{  
  		   document.forms['kmForm'].action.value='submit';
  		   document.forms['kmForm'].submit();
  		}
  		 
  		function saveform()
 		{ 						
  		  document.forms['kmForm'].action.value='save';
 		  document.forms['kmForm'].submit();
  		}
  		
  		function thongbao()
  		{
  			var tt = document.forms['kmForm'].msg.value;
  			if(tt.length>0)
  		    {
  				//alert(tt);
  		   	}
  		}
  </script>
  <LINK rel="stylesheet" href="../css/main.css" type="text/css">
  <link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
  <script type="text/javascript" src="../scripts/jquery.min.js"></script>
  <script type="text/javascript" src="../scripts/speechbubbles.js"></script>
  <script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
  <script type="text/javascript">	
	jQuery(function($)
	{ 
		 $('.addspeech').speechbubble();
	})
  </script>
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
	
  </style>
  <!-- <link href="jquery-ui.css" rel="stylesheet" type="text/css"/>-->
  <script type="text/javascript" src="../scripts/Sortable/jquery.min.js"></script>
  <script type="text/javascript" src="../scripts/Sortable/jquery-ui.min.js"></script>
  
  <script>
	  $(document).ready(function() {
		$("#sortable").sortable();
	  });
  </script> 

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>

<body style="margin-left:3px">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->


<form name="kmForm" method="post" action="../../XLtrungbaySvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<INPUT type="hidden" name="dhId" value='<%=xltb.getIdDonhang() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppId" value='<%=xltb.getIdnpp() %>'>
<input type="hidden" name="khId" value='<%=xltb.getIdKh() %>'>
<input type="hidden" name="kenhId" value='<%=xltb.getIdkenh() %>'>
<input type="hidden" name="khoId" value='<%=xltb.getIdkho() %>'>
<input type="hidden" name="ngaydh" value='<%=xltb.getNgaydh() %>'>
<input type="hidden" name="msg" value='<%= xltb.getMsg() %>'>

<%System.out.println("TrungBay.jsp : kh : "+xltb.getIdKh()+" - kenh : "+xltb.getIdkenh()+" - kho : "+xltb.getIdkho()); %>
<script type="text/javascript"> 
  thongbao();
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr height="25px">
		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Đơn hàng > Áp trưng bày > Điều chỉnh</TD>
		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= xltb.getnppTen() %>  &nbsp;</TD>
	</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<TR class="tbdarkrow">
		<TD align="left" width="30"><A href = "../../DonhangUpdateSvl?userId=<%= userId %>&update=<%= xltb.getIdDonhang() %>"><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
	    <TD align="left" width="30">
	    <div id="btnSave30">
	    <A href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A>
	    </div>
	    </TD>
	    <TD></TD>
	</TR>
</table>
 

<fieldset>
	<legend><span class="legendtitle"> &nbsp;Danh sách chương trình trưng bày ( CTTB ) thỏa điều kiện </span>&nbsp;&nbsp;&nbsp;</legend>
    <table class="tabledetail" id="content" width="100%" border="0" cellspacing="1" cellpadding="2">
    	<tr class="tbheader">
        	<th width="20%"  align="center">Mã CTTB</th>           
            <th width="50%" align="center">Tên CTTB</th>
            <th width="8%" align="center">Chọn</th>
        </tr>
        <%try{
        if(TBlist != null)
		{
			int m = 0;
			String lightrow = "tblightrow";
			String darkrow = "tbdarkrow";
			while (TBlist.next()){
				if (m % 2 != 0) {%>						
					<TR class= <%=lightrow%> >
				<%} else {%>
					<TR class= <%= darkrow%> >
				<%}%>				
                    <% if( cotrungbay.containsKey(TBlist.getString("scheme") ) ) {  %>
                    	<TD align="center"><%= TBlist.getString("scheme") %></TD>                                   
						<TD align="center"><%= TBlist.getString("cttbTen") %></TD>	                                                         								
        				<TD align="center"><input type="radio" name="chon" value="<%=TBlist.getString("cttbId")%>"></TD>
       	<%m++;}}} 		
		}catch(Exception e){System.out.println("Error jsp : "+e.getMessage());}                
       	%>
       	
       	<TR class="tbfooter" > 
		<TD align="center" valign="middle" colspan="3" class="tbfooter" height=25px></TD>
		</tr>
       </table>                       
</fieldset>
</form>
</body>
</html>
<%-- <% 	
if(xlkm != null){
	xlkm.DBclose();
	xlkm = null;
	util=null;
	ctkmAvaiable=null;
	listCTKM=null;
	sanpham_soluong=null;
	
	
	session.setAttribute("xlkm",null);
	
}
%> --%>
<%}%>