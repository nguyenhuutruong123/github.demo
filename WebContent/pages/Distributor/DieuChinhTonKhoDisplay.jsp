 <%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.dieuchinhtonkho.IDieuchinhtonkho" %>
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

<% NumberFormat formatter = new DecimalFormat("#,###,###.##");%>

<% IDieuchinhtonkho dctkBean = (IDieuchinhtonkho)session.getAttribute("dctkBean"); %>
<% ResultSet dvkd = (ResultSet) dctkBean.getDvkd(); %>
<% ResultSet kbh = (ResultSet) dctkBean.getKbh(); %>
<% ResultSet kho = (ResultSet) dctkBean.getKho(); %>
<% String[] spId = (String[])dctkBean.getSpId(); %>
<% String[] masp = (String[])dctkBean.getMasp(); %>
<% String[] tensp = (String[])dctkBean.getTensp() ; %>
<% String[] tkht = (String[])dctkBean.getTkht(); %>
<% String[] dv = (String[])dctkBean.getDonvi(); %>
<% String[] dc = (String[])dctkBean.getDc(); %>
<% String[] giamua = (String[])dctkBean.getGiamua(); %>
<% String[] ngaynhapkho = (String[])dctkBean.getNgaynhapkho(); %>

<% 

System.out.println("here");

String[] ttien = (String[])dctkBean.getTtien(); 
String[] soluongle = (String[])dctkBean.getSoluongle();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

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
<SCRIPT src="../js/md5.js" type="text/javascript" language="javascript">
</SCRIPT>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<style type="text/css">
	#mainContainer{
		width:660px;
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
	
}
</style>



<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/AjaxDieuChinhTonKho.js"></script>
<SCRIPT src="../js/scripts.js" type="text/javascript" language="javascript"> </SCRIPT>
<SCRIPT src="../js/commun.js" type="text/javascript" language="javascript"> </SCRIPT>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<SCRIPT type="text/javascript">

function replaces()
{	
	var spId = document.getElementsByName("spId");
	var masp = document.getElementsByName("spMa");
	var tensp = document.getElementsByName("spTen");
	var donvitinh = document.getElementsByName("donvi");
	var dongia = document.getElementsByName("dongia");
	var dieuchinh = document.getElementsByName("dieuchinh");
	var tonhientai=document.getElementsByName("tonkho");
	var dieuchinh=document.getElementsByName("dieuchinh");
	var tonmoi=document.getElementsByName("tonkhomoi");
	var thanhtien = document.getElementsByName("thanhtien");
/* 	var ngaynhapkho = document.getElementsByName("ngaynhapkho"); */
	
	var n=masp.length;
	for(var i=0; i <n ; i++)
	{
		if(masp.item(i).value != "")
		{
			var sp = masp.item(i).value;
			var pos = parseInt(sp.indexOf(" - "));
			if(pos > 0)
			{
				masp.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf("[")));
				
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
				donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				var valuegia= sp.substring(0, parseInt(sp.indexOf("] [")));
				dongia.item(i).value =DinhDangTien(valuegia);
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				var ton_kho= sp.substring(0, parseInt(sp.indexOf("] [")));
				tonhientai.item(i).value =DinhDangTien(ton_kho);
				
				/* sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				var ngaynhapkho_= sp.substring(0, parseInt(sp.indexOf("] [")));
				ngaynhapkho.item(i).value = ngaynhapkho_; */
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
			    spId.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
			}		
			
		}
		else
		{
			 tensp.item(i).value = "";
			donvitinh.item(i).value = "";
			dongia.item(i).value = "";
			tonmoi.item(i).value = "";
			thanhtien.item(i).value = ""; 
		}
	}	
	
	TinhTien();
	setTimeout(replaces, 20); 
}

function UpdateSoLuongDieuChinh(i)
{
	 var spId = document.getElementsByName("spId");
	var masp = document.getElementsByName("spMa");
	var tensp = document.getElementsByName("spTen");
	var donvitinh = document.getElementsByName("donvi");
	var dongia = document.getElementsByName("dongia");
	var dieuchinh = document.getElementsByName("dieuchinh");
	var tonhientai=document.getElementsByName("tonkho");
	var dieuchinh=document.getElementsByName("dieuchinh");
	var tonmoi=document.getElementsByName("tonkhomoi");
	var thanhtien = document.getElementsByName("thanhtien");
	if(isNaN(Number(dieuchinh.item(i).value)))
	{
		dieuchinh.item(i).value='';
		 tonmoi.item(i).value='';
	}

	 if(!isNaN(Number(dieuchinh.item(i).value)))
	 {
		 var ton_hien_tai=tonhientai.item(i).value.replace(/,/g,"");
		 var dieu_chinh =dieuchinh.item(i).value.replace(/,/g,"");
		 var sau_dieu_chinh =tonmoi.item(i).value.replace(/,/g,"");
		 var don_gia =dongia.item(i).value.replace(/,/g,"");
		 
		 
		 tonmoi.item(i).value=parseFloat(ton_hien_tai)+parseFloat(dieu_chinh);
			tonmoi.item(i).value=DinhDangDonGia(tonmoi.item(i).value);
			dieuchinh.item(i).value=DinhDangDonGia(dieuchinh.item(i).value);
		
		var thanh_tien=parseFloat(dieu_chinh)*parseFloat(don_gia);
		thanhtien.item(i).value=DinhDangDonGia(thanh_tien);
		
	 }
	 
	 if(tonmoi.item(i).value<0)
	{
		 dieuchinh.item(i).value='';
		 tonmoi.item(i).value='';
	} 
	 TinhTien(); 
	
}

function UpdateTonMoi(i)
{
	var spId = document.getElementsByName("spId");
	var masp = document.getElementsByName("spMa");
	var tensp = document.getElementsByName("spTen");
	var donvitinh = document.getElementsByName("donvi");
	var dongia = document.getElementsByName("dongia");
	var dieuchinh = document.getElementsByName("dieuchinh");
	var tonhientai=document.getElementsByName("tonkho");
	var dieuchinh=document.getElementsByName("dieuchinh");
	var tonmoi=document.getElementsByName("tonkhomoi");
	var thanhtien = document.getElementsByName("thanhtien");	
	
	if(isNaN(Number(dieuchinh.item(i).value)))
	{
		dieuchinh.item(i).value='';
		tonmoi.item(i).value='';
	}

	 if(!isNaN(Number(tonmoi.item(i).value)))
	 {			
		 var ton_hien_tai = tonhientai.item(i).value.replace(/,/g,"");
		 var ton_moi = tonmoi.item(i).value.replace(/,/g,"");
		 var dieu_chinh = dieuchinh.item(i).value.replace(/,/g,"");
		 var don_gia =dongia.item(i).value.replace(/,/g,"");
		 
		 dieuchinh.item(i).value = parseFloat(ton_moi)-parseFloat(ton_hien_tai);
		 
		 tonmoi.item(i).value = DinhDangDonGia(tonmoi.item(i).value);
		 dieuchinh.item(i).value = DinhDangDonGia(dieuchinh.item(i).value);
		 
		 
		
 		//var thanh_tien=parseFloat(dieu_chinh.item(i).value)*parseFloat(don_gia);
		var thanh_tien=parseFloat(dieuchinh.item(i).value.replace(/,/g,""))*parseFloat(don_gia);
		//alert("Vuna====dieuChinh====" + dieuchinh.item(i).value.replace(/,/g,""));
		
		thanhtien.item(i).value=DinhDangDonGia(thanh_tien);
	 }
	 
	 if(tonmoi.item(i).value<0)
	{
		 dieuchinh.item(i).value='';
		 tonmoi.item(i).value='';
	}
	 TinhTien();
	
}

function DinhDangDonGia(num) 
{
	 num = num.toString().replace(/\$|\,/g,'');
	 
	var sole = '';
	if(num.indexOf(".") >= 0)
	{
		sole = num.substring(num.indexOf('.'));
	}
	
	if(isNaN(num))
	num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	num = Math.floor(num/100).toString();
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	num = num.substring(0,num.length-(4*i+3))+','+
	num.substring(num.length-(4*i+3));

	var kq;
	if(sole.length >= 0)
	{
		kq = (((sign)?'':'-') + num) + sole;
	}
	else
		kq = (((sign)?'':'-') + num);
	
	//alert(kq);
	return kq;
}

function TinhTien()
{
	var thanhtien = document.getElementsByName("thanhtien");
	var tongtien = 0;
	var tongtienbvat = 0;
	for(var i=0; i < thanhtien.length; i++)
	{
		if(thanhtien.item(i).value != "")
		{
			var thanh_tien = thanhtien.item(i).value.replace(",", "");
			while(thanh_tien.match(","))
			{
				thanh_tien=thanh_tien.replace(",","");
			}
			tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
		}
	}
	document.getElementById("ttien").value= formatCurrency(tongtien); 
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtien) + " ?????ng Vi???t Nam";		
	
	
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
	element.value = DinhDangTien(element.value);
	if(element.value== '' )
	{
		element.value= '';
	}
}	


function isNumeric(n)
{
    var n2 = n;
    n = parseFloat(n);
    return (n!='NaN' && n2==n);
}         
function submitform()
{      
	 document.forms['dctkForm'].action.value='1';
   document.forms["dctkForm"].submit();
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
{  document.forms['dctkForm'].action.value='chot';
   document.forms["dctkForm"].submit();
}
function ExportExcel()
{
	 	 
	document.forms['dctkForm'].action.value='xuatexcel';
    document.forms["dctkForm"].submit();
}
 function saveform()
{
	document.getElementById("btnSave").innerHTML = "<img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'>";
	document.forms['dctkForm'].action.value='save';
    document.forms["dctkForm"].submit();
}
 
function printform()
{   
   document.forms["dctkForm"].submit();
}

function ExportExcel()
{
	 	 
	document.forms['dctkForm'].action.value='xuatexcel';
    document.forms["dctkForm"].submit();
}


function upload(){// nhan nut cap nhat

	   if(document.forms["dctkForm"].filename.value==""){
		   
		   document.forms["dctkForm"].dataerror.value="Ch??a t??m file upload l??n. Vui l??ng ch???n file c???n upload.";
	   }else{
		 document.forms["dctkForm"].setAttribute('enctype', "multipart/form-data", 0);
	//	 document.forms["dctkForm"].loaiupload.value = "1"
			 var res_field = document.forms["dctkForm"].filename.value;   
		  var extension = res_field.substr(res_field.lastIndexOf('.') + 1).toLowerCase();
		  var allowedExtensions = ['xls'];
		  if (res_field.length > 0)
		     {
		          if (allowedExtensions.indexOf(extension) === -1) 
		             {
		               alert('Sai Format. Ch??? ???????c ph??p Upload ?????nh d???ng file excel: ' + allowedExtensions.join(', ') + '');
		               return ;
		             }
		    }	
		 document.forms["dctkForm"].submit();	
	   }

}
function Xoafile(name)
{
	if(confirm("B???n mu???n x??a file ????nh k??m"))
	{
		document.forms["dctkForm"].removename.value = name;
		document.forms["dctkForm"].action.value = "remove";
	    document.forms['dctkForm'].submit();
	}
	else
		return false;
}
function upload2(){// nhan nut cap nhat

	   if(document.forms["dctkForm"].fileup.value==""){
		   
		   document.forms["dctkForm"].dataerror.value="Ch??a t??m file ????nh k??m upload l??n. Vui l??ng ch???n file ????nh k??m c???n upload.";
	   }else{
		 document.forms["dctkForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["dctkForm"].submit();	
	   }

}

function ThemSanPham()
{
	 var sl = window.prompt("Nh???p s??? l?????ng s???n ph???m mu???n th??m", '');
	 if(isNaN(sl) == false && sl > 0)
	 {
		 document.forms['dctkForm'].sodong.value=sl;
		 document.forms["dctkForm"].submit();
	 }
	 else
	 {
		
		 alert('S??? l?????ng b???n nh???p kh??ng h???p l???. M???i l???n b???n ch??? ???????c th??m t???i ??a 30 s???n ph???m');
		 return;
	 }
}

</SCRIPT>

</HEAD>
<body>
<form name="dctkForm" method="post" action="../../DieuchinhtonkhoUpdateSvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="nppId" type="hidden" value='<%=dctkBean.getNppId() %>' size="30">
<INPUT name="id" type="hidden" value='<%=dctkBean.getId() %>'  >

<INPUT name="action" type="hidden" value='1' size="30">
<input type="hidden" name="sodong" value='<%=dctkBean.getSodong() %>' />
<input type="hidden" name="removename" value='0' />
<table width="100%" border="0" cellspacing="0" cellpadding="0"  >
  <tr>
    <td colspan = 4 valign="top">
                <TABLE border =0 width = 100% >

                <TBODY>
                    <TR>
                        <TD align="left" >
                            <TABLE width="100%" cellpadding="0" cellspacing="0">
                                <TR>
                                    <TD align="left" class="tbnavigation">
                                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                           <tr height="22">
                                               <TD align="left" colspan="2" class="tbnavigation">Qu???n l?? t???n kho > ??i???u ch???nh t???n kho > Hi???n th???</TD>
                                               <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n  <%= dctkBean.getNppTen() %> &nbsp;</TD>

                                            </tr>
                                        </table>
                                     </TD>
                                </TR>   
                            </TABLE>
                        <TD></TR>
                        <TR><TD>
                            <TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
                                <TR ><TD >
                                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <TR class = "tbdarkrow">
                                            <TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay v???"  title="Quay v???" border="1" longdesc="Quay v???" style="border-style:outset"></A></TD>
                                          	<TD align="left">&nbsp; 
                                             	<A href="javascript:ExportExcel()"> <IMG width="30px" height="30px" src="../images/excel.gif" title="Xu???t Excel" alt="Xu???t Excel" border="1px" style="border-style: outset"></A>
                                            </TD>
                                        </TR>
                                    </TABLE>
                                </TD></TR>

                            </TABLE>
                        </TD>           
                    </TR>
                    <TR>
                        <TD>
                            <TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >
                                <TR>
                                    <TD align="left" colspan="4" class="legendtitle">
                                    <FIELDSET>

                                    <LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>
                
                                    <textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%" rows="1" ><%= dctkBean.getMessage() %></textarea>
                                    <% dctkBean.setMessage(""); %>
                                    </FIELDSET>
                                   </TD>
                                </TR>
                                
                                <TR>                       
                                     <TD  align="left">   
                                       <FIELDSET>
                                        <LEGEND class="legendtitle">&nbsp;??i???u ch???nh t???n kho </LEGEND>
                                                        
                                           <TABLE cellpadding = "3" cellspacing = "0" width = "100%" border = 0>
                                            <TR >
                                                <TD width="10%" class="plainlabel"> Nh?? ph??n ph???i</TD>
                                                <TD colspan="3" width="40%" class="plainlabel"><%= dctkBean.getNppTen() %></TD>                                                                   
                                            </TR>

                                            <TR >
                                                <TD class="plainlabel">Ng??y ??i???u ch???nh </TD>
                                                <TD class="plainlabel">
                                                    <input class="days" type="text" onChange = "submitform();" name="ngaydc" size="10	" value="<%= dctkBean.getNgaydc() %>" readonly="readonly">                                         
                                                </TD>
                                                                                                
							    				<TD class="plainlabel">????n v??? kinh doanh</TD>
							      				<TD class="plainlabel">
								    				<SELECT  name="dvkdId"  onChange = "submitform();">								      
							  	 					
							  	 					<% try{ 
							  	 							while(dvkd.next()){ 
							  	 								if(dvkd.getString("dvkdId").equals(dctkBean.getDvkdId())){ %>
							      									<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkd") %></option>
							      						   	  <%}else{ %>
							     									<option value='<%=dvkd.getString("dvkdId")%>' ><%=dvkd.getString("dvkd")  %></option>
							     							  <%}
							  	 							}
							  	 						}catch(java.sql.SQLException e){} %>	
								     	
													</SELECT></TD>											
                                            </TR>
                                            
                                             <TR >
                                                <TD class="plainlabel">L?? do ??i???u ch???nh </TD>
                                                <%String[] lydodieuchinh=new String[] {"??i???u ch???nh ki???m h??ng","Xu???t h??ng h???ng"}; %>
                                                <TD class="plainlabel">
                                                   <input  name="lydo" value="<%=dctkBean.getLydodc() %>">
                                                </TD>
							  					<TD class="plainlabel">K??nh b??n h??ng </TD>
							  					<TD class="plainlabel">
							      					<SELECT name="kbhId" onChange = "submitform();">								      
							  	 						
							  	 					<% try{ while(kbh.next()){ 
							    							if(kbh.getString("kbhId").equals(dctkBean.getKbhId())){ %>
							      								<option value='<%= kbh.getString("kbhId")%>' selected><%=kbh.getString("kbh") %></option>
							      						  <%}else{ %>
							     								<option value='<%= kbh.getString("kbhId")%>'><%= kbh.getString("kbh") %></option>
							     						<%}}}catch(java.sql.SQLException e){} %>	
								     	
                               						</SELECT>
                               					</TD>							  		
                                            </TR>                                            																					                                		

                                			<TR class="tblightrow">
                                     			<TD  class="plainlabel">Gi?? tr??? ??i???u ch???nh </TD>
                                     			<TD  class="plainlabel"><input name="ttien" id="ttien" type="text" value="<%= dctkBean.getGtdc() %>" style="background-color: #FFFFFF">
                                              		VND </TD>
                                              	                                              	
                                 				 <TD class="plainlabel">Kho </TD>
								  				 <TD class="plainlabel"><SELECT name="khoId" onChange = "submitform();">
								  					<option value="" > </option>
												<%  try{
								  						while(kho.next()){								  			
								  							if (dctkBean.getKhoId().equals(kho.getString("khoId"))){ %>								  			
								  								<option value="<%= kho.getString("khoId")%>" selected><%= kho.getString("ten")+"-"+ kho.getString("diengiai")%></option>
								  		  				  <%}else{ %>		
								  		  						<option value="<%= kho.getString("khoId")%>" ><%= kho.getString("ten")+"-"+kho.getString("diengiai")%></option>
								  						  <%}
								  					    }
								  						}catch (java.sql.SQLException e){} %>
                                  						</SELECT></TD>  			                              
                                			</TR>
                                			<TR class="tblightrow">
                                     			<TD  class="plainlabel">B???ng ch??? </TD>
                                     			<TD colspan="3" class="plainlabel"> <span id="lblDocSo" style="font-weight:bold;font-style:italic;"></span> </TD>
                                			</TR>
                                			<tr class="plainlabel">
										  	  <td >
										  	  <INPUT type="file" name="filename" size="40" value=''> 
										  	  </td> 
										  	  <td colspan="3">
										  			&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:upload()">
													<img style="top: -4px;" src="../images/button.png" alt="">S???n ph???m UpLoad</a>							
										  	 </td>
										  	</tr>
                                			 <TR>
										  	  	<TD class="plainlabel">File ????nh k??m
										  	  		<div><div id="input"  style="float: left;"><INPUT type="file" name="fileup" value=""></div>
										  	  		&nbsp;&nbsp;
										  	  		<img src="../images/xpcollapse1.gif" onclick="javascript:upload2()" style="cursor: pointer;" alt="Th??m file" width="20" height="20" longdesc="Th??m file" border = 0>
										  	  		</div>
										  	  		<% if(dctkBean.getFile() != null)
										  	  		if(dctkBean.getFile().length > 0 ) {	
										  	  			for(int i=0;i <dctkBean.getFile().length; i++)
										  	  			if(dctkBean.getFile()[i].length() > 0)
										  	  			{%>
										  	  			<input type="hidden" name = "filedk" value = "<%=dctkBean.getFile()[i]%>">
										  	  			<div id="tenfilea"><p><%= dctkBean.getFile()[i] %><img src="../images/Delete20.png" onclick="Xoafile('<%= dctkBean.getFile()[i]%>')" style="cursor: pointer;" alt="X??a" width="20" height="20" longdesc="Cap nhat" border = 0></p></div>
										  	  			<%}
									  	  			}%>
										  	  	</TD>
										  	  	<TD class="plainlabel"></TD>
										  	  		<TD class="plainlabel"></TD>
										  	  		 		<TD class="plainlabel"></TD>
										  	  	
				  		</TR>
                                			
                                          
                               					</TABLE>
                           				   </FIELDSET>

	                                  </TD>
                              
                                 
                               </TR>    
                               
                               <TR>
                                    <TD>
                                    <fieldset>
                                        <TABLE  width = 100% cellpadding="1" border="1" style="border-color: #80CB9B" cellspacing="0" >
                                            <TR class="tbheader" >
                                            	<th align="center" width="3%" >STT</th>
                                                <TH width="12%">M?? s???n ph???m </TH>
                                                <TH >T??n s???n ph???m </TH>
                                                <TH width="8%">T???n hi???n t???i </TH>
                                                <TH width="8%">????n v??? </TH>
                                                 <!-- <TH width="8%">Ng??y nh???p kho </TH> -->
                                                <TH width="8%" >T???n m???i</TH>
                                                <TH width="15%">??i???u ch???nh (+ / - ) </TH>                                                
                                                <TH width="8%" >????n gi?? b??n</TH>
                                                <TH width="8%" >Th??nh ti???n </TH>
                                                
                                            </TR>
                                            
                                            
                                         		<% 
                                         		String lightrow = "tblightrow";
                                                String darkrow = "tbdarkrow";
                                                int m= 0;
                                                if(masp!=null){
                                                for(int i=0; i < masp.length; i++){ 
    												if(m%2 == 0){					%>
    													<TR class= <%=lightrow%> >
    											<% 	}else{%>	         
    													<TR class= <%=lightrow%> >
    											<%	} %>
<td style="text-align: center;" > <%= i + 1 %> </td>
															<TD>
																<input type="text" name="spMa"    value='<%=masp[i]%>'  onkeyup="ajax_showOptions(this,'abc',event)"   />
															</TD>
															<TD>
																<input type="text" name="spTen" value='<%=tensp[i]%>' readonly="readonly" style="text-align: left; width: 100%; border: 0">
															</TD>
															<TD>
																<input type="text" name='tonkho' value="<%= tkht[i] %>" readonly="readonly"  onchange="Dinhdang(this)" style="width: 100%; text-align: right; border: 0"   >
															</TD>
																<TD>
																<input type="text" name='donvi' value="<%= dv[i] %>"   readonly="readonly"  onchange="Dinhdang(this)" style="width: 100%; text-align: right; border: 0"   >
															</TD>
																<%-- <TD>
																<input type="text" name='ngaynhapkho' value="<%= ngaynhapkho[i] %>"   readonly="readonly"    style="width: 100%; text-align: right; border: 0"   >
															</TD> --%>
															<TD>
																<input type="text" name='tonkhomoi' value="<%=soluongle[i] %>"    style="width: 100%; text-align: right; border: 1"  onKeyPress="return submitenter(this,event);"  onchange="UpdateTonMoi(<%=i %>)" >
															</TD>
															<TD>
																<input type="text" name='dieuchinh'   value="<%=dc[i] %>" style="width: 100%; text-align: right;" onchange="UpdateSoLuongDieuChinh(<%=i %>)"  >
															</TD>
															<TD>
																<input type="text" name='dongia'  value="<%=giamua[i] %>" onchange="Dinhdang(this)" style="width: 100%; text-align: left;" readonly="readonly">
															</TD>
															<TD>
																<input type="text" name='thanhtien' id='thanhtien<%=i %>' value="<%=ttien[i] %>"   onchange="Dinhdang(this)" style="width: 100%; text-align: right;" readonly="readonly"> 
																<input type="hidden" name="spId" value="<%= spId[i] %>">																
															</TD>
														</TR>
														<% m++; }} %>
													 
                                           
                                      </TABLE>
                                      </fieldset>
                                    </TD>
                              </TR>
								<!-- <tr>
									<td colspan="9">
							  		&nbsp;&nbsp;&nbsp;&nbsp;	<a class="button2" href="javascript:ThemSanPham()">
	                         			<img style="top: -4px;" src="../images/add.png" alt="">Th??m s???n ph???m</a>&nbsp;&nbsp;&nbsp;&nbsp;
							  	
							  		</td>
								</tr> -->
                              
                          </TABLE>
                          
                        </TD>
                    </TR>   
                    
                </TBODY>
            </TABLE>
    </td>

  </tr>
</table>
<script type="text/javascript">
	replaces();
</script>

</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>

</html>
<% 	


	try{
		if(dvkd != null)
			dvkd.close();
		if(kbh != null)
			kbh.close();
	
		if(kho != null)
			kho.close();

		if(dctkBean != null){
			dctkBean.DBclose();
			dctkBean = null;
		}			
		session.setAttribute("dctkBean",null);
		spId=null;
		masp=null;
		tensp=null;
		tkht=null;
		dv=null;
		dc=null;
		giamua=null;
		ttien=null;
	
	}
	catch (SQLException e) {}

%>
<%}%>
 