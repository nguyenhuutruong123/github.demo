<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.ctkhuyenmai.*" %>
<%@ page  import = "geso.dms.center.beans.ctkhuyenmai.imp.*" %>
<%@ page  import = "geso.dms.center.beans.dieukienkhuyenmai.ISanpham" %>
<%@ page  import = "geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<% ICtkhuyenmai ctkmBean = (ICtkhuyenmai)session.getAttribute("ctkmBean"); %>
<% List<IDieukienkm> dkkmList = ctkmBean.getDkkmList(); %>
<% List<ITrakm> trakmList = ctkmBean.getTrakmList(); %>
<% ResultSet nhomspRs = ctkmBean.getNhomspRs(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
   Utility util = (Utility) session.getAttribute("util");%>
<% ResultSet khoIds = (ResultSet)ctkmBean.getkhoIds();%>
<% ResultSet Dsnpp = (ResultSet)ctkmBean.getDsnpp();%>
<% ResultSet DsnppIds = (ResultSet)ctkmBean.getDsnppSelected(); %>
<% Hashtable<Integer, String> nppIds = (Hashtable<Integer, String>)ctkmBean.getnpp(); %>
<% ResultSet Nhomkhnpp = (ResultSet)ctkmBean.getNhomkhnpp();%>
<% ResultSet vungs = (ResultSet)ctkmBean.getVungs();%>
<% ResultSet khuvucs = (ResultSet)ctkmBean.getKhuvuc();%>
<% ResultSet kbhRS = (ResultSet)ctkmBean.getKbhRs(); %>
<% ResultSet loaikhRS = (ResultSet)ctkmBean.getLoaikhRs(); %>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("CtkhuyenmaiSvl","",nnId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>AHF - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/table.css" type="text/css">
    
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	
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
			z-index:100200;
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
	</style>
	
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/ctkhuyenmaiList.js"></script>
	
   <script>
	  $(document).ready(function() {
			$("#accordion").accordion({autoHeight: false}); //autoHeight content set false
			$( "#accordion" ).accordion( "option", "icons", { 'header': 'ui-icon-plus', 'headerSelected': 'ui-icon-minus' } );
			$( "#accordion" ).accordion({ active: <%= ctkmBean.getActive() %> });
	  });
  </script>
  
    <link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	$(".dieukienkhuyenmai0").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai0"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
           
            $(".dieukienkhuyenmai1").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai1"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".dieukienkhuyenmai2").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai2"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".dieukienkhuyenmai3").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai3"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".dieukienkhuyenmai4").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai4"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            
            $(".dieukienkhuyenmai5").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai5"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
           
            $(".dieukienkhuyenmai6").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai6"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".dieukienkhuyenmai7").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai7"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".dieukienkhuyenmai8").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai8"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".dieukienkhuyenmai9").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai9"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            
            
            $(".trakhuyenmai0").colorbox({width:"46%", inline:true, href:"#trakhuyenmai0"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".trakhuyenmai1").colorbox({width:"46%", inline:true, href:"#trakhuyenmai1"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".trakhuyenmai2").colorbox({width:"46%", inline:true, href:"#trakhuyenmai2"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            $(".trakhuyenmai3").colorbox({width:"46%", inline:true, href:"#trakhuyenmai3"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".trakhuyenmai4").colorbox({width:"46%", inline:true, href:"#trakhuyenmai4"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            $(".trakhuyenmai5").colorbox({width:"46%", inline:true, href:"#trakhuyenmai5"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            $(".trakhuyenmai6").colorbox({width:"46%", inline:true, href:"#trakhuyenmai6"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            $(".trakhuyenmai7").colorbox({width:"46%", inline:true, href:"#trakhuyenmai7"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            $(".trakhuyenmai8").colorbox({width:"46%", inline:true, href:"#trakhuyenmai8"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            $(".trakhuyenmai9").colorbox({width:"46%", inline:true, href:"#trakhuyenmai9"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
        });
    </script>
    <script language="javascript" type="text/javascript">
    
    	function replacesDKKM(pos)
    	{
    		alert(pos);
    		
    		var dkkmId = document.getElementsByName("dkkmId");
			var dkkmDiengiai = document.getElementsByName("dkkmDiengiai");
			var dkkmTongluong = document.getElementsByName("dkkmTongluong");
			var dkkmTongtien = document.getElementsByName("dkkmTongtien");
			var dkkmPheptoan = document.getElementsByName("dkkmPheptoan");
			
			var i;
			for(i = 0; i < dkkmId.length; i++)
			{
				if(i == pos)
				{
					if(dkkmId.item(i).value != "")
					{
						var dkkm = dkkmId.item(i).value;
						var pos = parseInt(dkkm.indexOf(" - "));
						if(pos > 0)
						{
							dkkmId.item(i).value = dkkm.substring(0, pos);
							dkkm = dkkm.substr(parseInt(dkkm.indexOf(" - ")) + 3);
							dkkmDiengiai.item(i).value = dkkm.substring(0, parseInt(dkkm.indexOf(" [")));
							dkkm = dkkm.substr(parseInt(dkkm.indexOf(" [")) + 2);
							dkkmTongluong.item(i).value = dkkm.substring(0, parseInt(dkkm.indexOf("] [")));
							dkkm = dkkm.substr(parseInt(dkkm.indexOf("] [")) + 3);
							dkkmTongtien.item(i).value = dkkm.substring(0, parseInt(dkkm.indexOf("]")));
						}
					}
					else
					{
						dkkmId.item(i).value = "";
						dkkmDiengiai.item(i).value = "";
						dkkmTongluong.item(i).value = "";
						dkkmTongtien.item(i).value = "";
						dkkmPheptoan.item(i).value = "";
					}		
				}	
			}	
    	}
    	
		function replaces()
		{
			var dkkmId = document.getElementsByName("dkkmId");
			var dkkmDiengiai = document.getElementsByName("dkkmDiengiai");
			var dkkmTongluong = document.getElementsByName("dkkmTongluong");
			var dkkmTongtien = document.getElementsByName("dkkmTongtien");
			var dkkmPheptoan = document.getElementsByName("dkkmPheptoan");
	
			var i;
			thaydoiPhepToan();
			
			for(i=0; i < dkkmId.length; i++)
			{
				if(dkkmId.item(i).value != "")
				{
					var dkkm = dkkmId.item(i).value;
					var pos = parseInt(dkkm.indexOf(" - "));
					if(pos > 0)
					{
						dkkmId.item(i).value = dkkm.substring(0, pos);
						dkkm = dkkm.substr(parseInt(dkkm.indexOf(" - ")) + 3);
						dkkmDiengiai.item(i).value = dkkm.substring(0, parseInt(dkkm.indexOf(" [")));
						dkkm = dkkm.substr(parseInt(dkkm.indexOf(" [")) + 2);
						dkkmTongluong.item(i).value = dkkm.substring(0, parseInt(dkkm.indexOf("] [")));
						dkkm = dkkm.substr(parseInt(dkkm.indexOf("] [")) + 3);
						dkkmTongtien.item(i).value = dkkm.substring(0, parseInt(dkkm.indexOf("]")));
					}
				}
				else
				{
					dkkmId.item(i).value = "";
					dkkmDiengiai.item(i).value = "";
					dkkmTongluong.item(i).value = "";
					dkkmTongtien.item(i).value = "";
					dkkmPheptoan.item(i).value = "";
				}			
			}
			
			var tkmId = document.getElementsByName("trakmId");
			var tkmDiengiai = document.getElementsByName("trakmDiengiai");
			var tkmTongluong = document.getElementsByName("trakmTongluong");
			var tkmTongtien = document.getElementsByName("trakmTongtien");
			var tkmChietkhau = document.getElementsByName("trakmChietkhau");
			var tkmPheptoan = document.getElementsByName("trakmPheptoan");
			
			var j;
			for(j=0; j < tkmId.length; j++)
			{
				if(tkmId.item(j).value != "")
				{
					var trakm = tkmId.item(j).value;
					var pos = parseInt(trakm.indexOf(" - "));
					if(pos > 0)
					{
						tkmId.item(j).value = trakm.substring(0, pos);
						trakm = trakm.substr(parseInt(trakm.indexOf(" - ")) + 3);
						
						tkmDiengiai.item(j).value = trakm.substring(0, parseInt(trakm.indexOf(" [")));
						trakm = trakm.substr(parseInt(trakm.indexOf(" [")) + 2);
						
						tkmTongluong.item(j).value = trakm.substring(0, parseInt(trakm.indexOf("] [")));
						trakm = trakm.substr(parseInt(trakm.indexOf(" [")) + 2);
						
						tkmTongtien.item(j).value = trakm.substring(0, parseInt(trakm.indexOf("] [")));
						trakm = trakm.substr(parseInt(trakm.indexOf(" [")) + 2);
						
						tkmChietkhau.item(j).value = trakm.substring(0, parseInt(trakm.indexOf("]")));
					}
				}
				else
				{
					tkmId.item(j).value = "";
					tkmDiengiai.item(j).value = "";
					tkmTongluong.item(j).value = "";
					tkmTongtien.item(j).value = "";
					tkmChietkhau.item(j).value = "";
				}		
			}
			
			//Sanpham
			for(k = 0; k < 5; k++)
			{
				var masp = document.getElementsByName('dieukienkhuyenmai' + k + '.masanpham');
				var tensp = document.getElementsByName('dieukienkhuyenmai' + k + '.tensanpham');
				var soluong = document.getElementsByName('dieukienkhuyenmai' + k + '.soluong');

				for(p=0; p < masp.length; p++)
				{
					if(masp.item(p).value != "")
					{
						var sp = masp.item(p).value;
						var pos = parseInt(sp.indexOf(" - "));
						if(pos > 0)
						{
							masp.item(p).value = sp.substring(0, pos);
							tensp.item(p).value = sp.substr(parseInt(sp.indexOf(" - ")) + 3);					
						}
					}
					else
					{
						tensp.item(p).value = "";
						soluong.item(p).value = "";
					}			
				}
			}
			
			//Tra khuyen mai sanpham
			for(k = 0; k < 5; k++)
			{
				var masp = document.getElementsByName('trakhuyenmai' + k + '.masanpham');
				var tensp = document.getElementsByName('trakhuyenmai' + k + '.tensanpham');
				var soluong = document.getElementsByName('trakhuyenmai' + k + '.soluong');

				for(p=0; p < masp.length; p++)
				{
					if(masp.item(p).value != "")
					{
						var sp = masp.item(p).value;
						var pos = parseInt(sp.indexOf(" - "));
						if(pos > 0)
						{
							masp.item(p).value = sp.substring(0, pos);
							tensp.item(p).value = sp.substr(parseInt(sp.indexOf(" - ")) + 3);					
						}
					}
					else
					{
						tensp.item(p).value = "";
						soluong.item(p).value = "";
					}			
				}
			}
			
			setTimeout(replaces, 100);
		}	
		function xoathuoctinhDisabledPhepToan(){
			var dkkmPheptoan = document.getElementsByName("dkkmPheptoan");
			var trakmPheptoan = document.getElementsByName("trakmPheptoan");
			for(var i = 0; i < dkkmPheptoan.length; i++)
			{
				dkkmPheptoan.item(i).removeAttribute("disabled");
			}
			for(var j = 0; j < trakmPheptoan.length; j++)
			{
				trakmPheptoan.item(j).removeAttribute("disabled");
			}
			document.forms["ctkmForm"].submit();
		}
		
		function thaydoiPhepToan(){
			thayDoiPhepToanDKKM();
			thayDoiPhepToanTraKM();
		}
		
		function thayDoiPhepToanDKKM(){
        	var dkkmId = document.getElementsByName("dkkmId");
			var dkkmPheptoan = document.getElementsByName("dkkmPheptoan");
			//console.log(dkkmId.length);
			if(dkkmPheptoan.item(0).value != "")
			{
				for(i = 1; i < dkkmId.length; i++)
				{
					dkkmPheptoan.item(i).value =dkkmPheptoan.item(0).value;
					dkkmPheptoan.item(i).setAttribute("disabled","disabled");
					
				}
			}else{
				for(i = 1; i < dkkmId.length; i++)
				{
					dkkmPheptoan.item(i).setAttribute("disabled","disabled");
					
				}
			}
        }
        
        function thayDoiPhepToanTraKM(){
        	var trakmId = document.getElementsByName("trakmId");
			var trakmPheptoan = document.getElementsByName("trakmPheptoan"); 
			if(trakmPheptoan.item(0).value != "")
			{
				for(i = 1; i < trakmId.length; i++)
				{ 
					trakmPheptoan.item(i).value =trakmPheptoan.item(0).value;
					trakmPheptoan.item(i).setAttribute("disabled","disabled"); 
				}
			}else{
				for(i = 1; i < trakmId.length; i++)
				{ 
					trakmPheptoan.item(i).setAttribute("disabled","disabled"); 
				}
			}
        }
    	function kiemtraChonPhepToan(){
    		var dkkmInput = document.getElementsByName("dkkmId");
			var trakmInput = document.getElementsByName("trakmId");
			
			var k = 0;
			for(var i = 0; i < dkkmInput.length ; i++){
				if(dkkmInput.item(i).value.trim() != ''){
					k++;
				}
				if(k == 2){
					var dkkmPheptoan = document.getElementsByName("dkkmPheptoan");
					if(dkkmPheptoan.item(0) == null 
							|| dkkmPheptoan.item(0).value == "" ){
						return "Cần phải chọn phép toán cho ô DKKM đầu tiên";
					}
				}
			}
			
			k=0;
			for(var j = 0; j < trakmInput.length; j++){
				if(trakmInput.item(j).value.trim() != ''){
					k++;
				}
				if(k == 2){
					var trakmPheptoan = document.getElementsByName("trakmPheptoan");
					if(trakmPheptoan.item(0) == null || trakmPheptoan.item(0).value == ""){
						return "Cần phải chọn phép toán cho ô TRAKM đầu tiên";
					}
				}
			}
			
			return "";
		}
		function saveform()
		{
			
			if(document.getElementById("loaiCt").value == "")
			{
				alert('Vui lòng chọn loại chương trình khuyến mãi');
				return;
			}
			
			if(document.getElementById("loaiCt").value != "4")
			{
			
		
						checkDieukientrung();
						
						if(document.getElementById("scheme").value == "")
						{
							alert('Vui lòng nhập scheme');
							return;
						}
						if(document.getElementById("apdungchoId").value != "2")
						/* if(document.getElementById("schemeErp").value == "")
						{
							alert('Vui lòng nhập schemeErp');
							return;
						} */
						
						
						<% if(!ctkmBean.getkhoId().equals("100001") ){ %>
						/* var apphanbo = document.getElementById('apphanbo');
					 	if(apphanbo.checked) */
					 	if(document.getElementById("apphanbo").checked == true)
					 	{
					 		if(document.getElementById("ngansach").value == "")
							{
								alert('Vui lòng nhập ngân sách chương trình khuyến mãi');
								return;
							}
					 	}
					 	else
					 	{
					 		document.getElementById("ngansach").value = "0"; //ngan sach bang 0 = khong gioi han
					 	}
				 	<%} %>
				 	
					if(checkDieukienkm() == false)
					{
						alert('Vui lòng thiết lập điều kiện khuyến mãi, hoặc kiểm tra lại các điều kiện khuyến mãi được chọn...');
						return;
					}
					
					if(checkTrakm() == false)
					{
						alert('Lỗi... không có hình thức trả khuyến mãi nào được chọn');
						return;
					}
					
					if(checkNpp() == false)
					{
						alert('Lối...Không có nhà phân phối nào được chọn cho chương trình khuyến mãi...');
						return;
					}
			
			
			
		}
			if(document.getElementById("tungay").value == "")
			{
				alert('Vui lòng nhập thời gian bắt đầu chương trình khuyến mãi');
				return;
			}
			if(document.getElementById("denngay").value == "")
			{
				alert('Vui lòng nhập thời gian kết thúc chương trình khuyến mãi');
				return;
			}
			
			if(Date.parse(document.getElementById("denngay").value) <= Date.parse(document.getElementById("tungay").value))
			{
				alert('Thời gian kết thúc chương trình khuyến mãi phải sau thời gian bắt đầu chương trình khuyến mãi');
				return;
			}
			
			var checkPheptoanMsg = kiemtraChonPhepToan();
			if(checkPheptoanMsg != ""){
				alert(checkPheptoanMsg);
				return;
			}
			
		
			if(document.getElementById("diengiai").value.length > 300)
			{  
				alert('Lối...diễn giải không được quá 300 ký tự...');
			  	return;
			}
			
			document.forms["ctkmForm"].action.value = "save";
			document.forms["ctkmForm"].submit();
			xoathuoctinhDisabledPhepToan();
		}
	
		function keypress(e)
		{    
			var keypressed = null;
			if (window.event)
				keypressed = window.event.keyCode; //IE
			else
				keypressed = e.which; //NON-IE, Standard
			
			if (keypressed < 48 || keypressed > 57)
			{ 
				if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
				{
					return;
				}
				return false;
			}
		}
		
		function submitform()
		{  
			var active = $( "#accordion" ).accordion( "option", "active" );
			document.forms["ctkmForm"].active.value = active;
			
		    document.forms["ctkmForm"].submit();
			xoathuoctinhDisabledPhepToan();

		}
		
		function checkDieukienkm() {
			var dkkmIds = document.getElementsByName("dkkmId");
			var dkkmPheptoan = document.getElementsByName("dkkmPheptoan");
			var flag=false;
			//dieu kien dau tien ko can nhap
			for(j = 0; j < dkkmIds.length; j++) { 
				if(dkkmIds.item(j).value != "") {
					if(j > 0) {
						if(dkkmPheptoan.item(j).value == "") {
							return false;
						}
					}
					flag=true;
				} 
			}
			return flag;
		}
		
		function checkDieukientrung()
		{
			var dkkmIds = document.getElementsByName("dkkmId");
			var dkkmDiengiai = document.getElementsByName("dkkmDiengiai");
			var dkkmTongluong = document.getElementsByName("dkkmTongluong");
			var dkkmTongtien = document.getElementsByName("dkkmTongtien");
			var dkkmPheptoan = document.getElementsByName("dkkmPheptoan");
			
			for(l =0; l < parseInt(dkkmIds.length) - 1; l++)
			{
				for(m = parseInt(l) + 1; m < dkkmIds.length; m++)
				{
					if(dkkmIds.item(l).value == dkkmIds.item(m).value)
					{
						dkkmIds.item(m).value = "";
						dkkmDiengiai.item(m).value = "";
						dkkmTongluong.item(m).value = "";
						dkkmTongtien.item(m).value = "";
						dkkmPheptoan.item(m).value = "";
					}
				}
			}
		}
		
		function checkTrakm() {
			var trakmIds = document.getElementsByName("trakmId");
			for(k =0; k < trakmIds.length; k++) {
				/* if(trakmIds.item(k) != "") */
					if(trakmIds.item(k).value.length > 4)
					return true; //co chon tra khuyen mai
			}
			return false;
		}
		
		function All()
		 { 
			var npp = document.getElementsByName("npp");
			for(i=0; i<npp.length; i++)
			{
			 	if( document.ctkmForm.all.checked == true )
			 	{
			 	  	npp[i].checked = true;
				}
			 	else
			 	{
					npp[i].checked = false;
				}
			}
		}
		function checkNpp()
		{
			var nhapp = document.getElementsByName("npp");
			for( p = 0; p < nhapp.length; p++)
				if(nhapp.item(p).checked)
					return true;
			return false;
		}
		
		function seach()
		{
			var active = $( "#accordion" ).accordion( "option", "active" );
			document.forms["ctkmForm"].active.value = active;
			document.forms["ctkmForm"].action.value = "seach";
			document.forms["ctkmForm"].load.value = "1";
			document.forms["ctkmForm"].submit();
			xoathuoctinhDisabledPhepToan();
		}
		
		function tichluy()
		{ 	
			var dkkmId = document.getElementsByName("dkkmId");
			var loai = document.getElementById("loaiCt");
			
			if(loai == "3")
			{
			    for(i = 0;i< dkkmId.length;i++)
			    {
			    	dkkmId[i].value = "";
			    }
			}
		    
			var active = $( "#accordion" ).accordion( "option", "active" );
			document.forms["ctkmForm"].active.value = active;
			
			document.forms["ctkmForm"].action.value = "tichluy";
			document.forms["ctkmForm"].submit();
			xoathuoctinhDisabledPhepToan();

		}
		
		 function Show()
		 {
		 	var element = document.getElementById('ngansachct');
		 	
		 	/* var apphanbo = document.getElementById('apphanbo');
		 	if(apphanbo.checked) */
		 	if(document.getElementById("apphanbo").checked == true)
		 		element.style.display = "";
		 	else
		 		element.style.display = "none";
		 }
		 
		function submitform2(pos)
		{  
			//document.getElementById("dkkmId" + pos).value = "   ";
			var diengiai = document.getElementById('dieukienkhuyenmai' + pos + '.diengiai').value; 
			document.getElementById("dkkmDiengiai" + pos).value = document.getElementById('dieukienkhuyenmai' + pos + '.diengiai').value;
			
			if(diengiai == '')
			{
				alert('Bạn phải nhập diễn giải cho điều kiện khuyến mãi');
				return;
			}
			
			var hinhthuc = document.getElementById('dieukienkhuyenmai' + pos + '.hinhthuc').value;
			var loaidieukien = document.getElementById('dieukienkhuyenmai' + pos + '.loaidieukien').value;
			var sotong = document.getElementById('dieukienkhuyenmai' + pos + '.sotong').value;
			var isthung = document.getElementById('dieukienkhuyenmai' + pos + '.tinhtheothung');

			if(hinhthuc == 1)  //tong luong
			{
				if(loaidieukien == 2)
				{
					if(sotong == '')
					{
						alert('Bạn phải nhập tổng lượng cho điều kiện khuyến mãi');
						return;
					}
				}	
			}
			else  //tong tien
			{
				if(loaidieukien == 2)
				{
					if(sotong == '')
					{
						alert('Bạn phải nhập tổng tiền cho điều kiện khuyến mãi');
						return;
					}
				}
			}

			var sanpham = '';
			var masanpham = document.getElementsByName('dieukienkhuyenmai' + pos + '.masanpham');
			var soluong = document.getElementsByName('dieukienkhuyenmai' + pos + '.soluong');
			for(i = 0; i < masanpham.length; i++)
			{
				if(loaidieukien == 1)
				{
					if(masanpham.item(i).value != '' && soluong.item(i).value == '')
					{
						alert('Bạn phải nhập số lượng cho sản phẩm ' + masanpham.item(i).value);
						return;
					}
					else
					{
						if(masanpham.item(i).value != '' && soluong.item(i).value != '')
						{
							sanpham += masanpham.item(i).value + '__' + soluong.item(i).value + ';';
						}
					}
				}
				else
				{
					if(masanpham.item(i).value != '')
						sanpham += masanpham.item(i).value + ';';
				}
			}
			
			if(sanpham == '')
			{
				alert('Bạn phải chọn sản phẩm cho điều kiện khuyến mãi');
				return;
			}
			
			
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
			  xmlhttp=new XMLHttpRequest();
			}
			else
			{
			  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   {
				  if(xmlhttp.responseText.length > 10)
				  {
					  alert('Không thể tạo mới điều kiện khuyến mãi, vui lòng kiểm tra lại các thông tin. \n' + query);
				  }
				  else
				  {
					  alert('Tạo mới điều kiện khuyến mãi thành công.');
					  
					  document.getElementById("dkkmId" + pos).value = xmlhttp.responseText;
					  document.getElementById("dkkmDiengiai" + pos).value = diengiai;
					 
					  if(hinhthuc == 1)  //tong luong
					  {
						 document.getElementById("dkkmTongluong" + pos).value = document.getElementById('dieukienkhuyenmai' + pos + '.sotong').value;
						 document.getElementById("dkkmTongtien" + pos).value = "0";
					  }
					  else  //tong tien
					  {
						 document.getElementById("dkkmTongluong" + pos).value = "0";
						 document.getElementById("dkkmTongtien" + pos).value = document.getElementById('dieukienkhuyenmai' + pos + '.sotong').value;
				 	  }
					  //document.getElementById("cboxClose").click();
					  $('#cboxClose').click();
				   } 
			    }
			}
			
			var dg = encodeURIComponent(diengiai.replace(" ","+"));
			xmlhttp.open("GET","../../AjaxDKKM?diengiai=" + dg + "&loaidieukien=" + loaidieukien + "&hinhthuc=" + hinhthuc + "&sotong=" + sotong + "&sanpham=" + sanpham + "&isThung=" + isthung.checked, true);
			xmlhttp.send();
			
		}
		
		function submitform3(pos)
		{  
			var diengiai = document.getElementById('trakhuyenmai' + pos + '.diengiai').value; 
			document.getElementById("trakmDiengiai" + pos).value = document.getElementById('trakhuyenmai' + pos + '.diengiai').value;
			
			if(diengiai == '')
			{
				alert('Bạn phải nhập diễn giải cho trả khuyến mãi');
				return;
			}
			
			var hinhthuc = document.getElementById('trakhuyenmai' + pos + '.hinhthuc').value;
			var loaitra = document.getElementById('trakhuyenmai' + pos + '.loaitra').value;
			var sotong = document.getElementById('trakhuyenmai' + pos + '.sotong').value;
			var isthung = document.getElementById('trakhuyenmai' + pos + '.tinhtheothung');
			
			if(loaitra == 1)  //tien
			{
				if(hinhthuc == 2 && sotong == '')
				{
					alert('Bạn phải nhập tổng tiền cho trả khuyến mãi');
					return;
				}
			}
			else 
			{
				if(loaitra == 2 ) //chiet khau
				{
					if(hinhthuc == 2 && sotong == '')
					{
						alert('Bạn phải nhập phần trăm chiết khấu cho trả khuyến mãi');
						return;
					}
					else
					{
						if(sotong != '')
						{
							if(parseInt(sotong) > 100)
							{
								alert('Phần trăm chiết khấu cho trả khuyến mãi không được vượt quá 100%');
								return;
							}
						}
					}
				}
				else //san pham
				{
					if(hinhthuc == 2 && sotong == '')
					{
						alert('Bạn phải nhập tổng lượng cho trả khuyến mãi');
						return;
					}
				}
			}
			
			var sanpham = '';
			var masanpham = document.getElementsByName('trakhuyenmai' + pos + '.masanpham');
			var soluong = document.getElementsByName('trakhuyenmai' + pos + '.soluong');
			for(i = 0; i < masanpham.length; i++)
			{
				if(loaitra == 3)
				{
					if(hinhthuc == 1)
					{
						if(masanpham.item(i).value != '' && soluong.item(i).value == '')
						{
							alert('Bạn phải nhập số lượng cho sản phẩm ' + masanpham.item(i).value);
							return;
						}
						else
						{
							if(masanpham.item(i).value != '' && soluong.item(i).value != '')
							{
								sanpham += masanpham.item(i).value + '__' + soluong.item(i).value + ';';
							}
						}
					}
					else
					{
						if(masanpham.item(i).value != '')
						{
							var slg = soluong.item(i).value;
							//alert(slg);
							if(soluong.item(i).value == '')
							{
								slg = '0';
							}
							sanpham += masanpham.item(i).value + '__' + slg + ';';
						}
					}	
				}
			}
			
			if(loaitra == 3)
			{
				if(sanpham == '')
				{
					alert('Bạn phải chọn sản phẩm cho trả khuyến mãi');
					return;
				}
			}

			var xmlhttp;
			if (window.XMLHttpRequest)
			{
			  xmlhttp=new XMLHttpRequest();
			}
			else
			{
			  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   {
				  if(xmlhttp.responseText.length > 10)
				  {
					  alert('Không thể tạo mới trả khuyến mãi, vui lòng thử lại sau.');
				  }
				  else
				  {
					  alert('Tạo mới trả khuyến mãi thành công.');
					  
					  document.getElementById("trakmId" + pos).value = xmlhttp.responseText;
					  document.getElementById("trakmDiengiai" + pos).value = diengiai;
					 
					  if(loaitra == 1)  //tien
					  {
						   document.getElementById("trakmTongluong" + pos).value = "0";
						   document.getElementById("trakmTongtien" + pos).value = document.getElementById('trakhuyenmai' + pos + '.sotong').value;
						   document.getElementById("trakmChietkhau" + pos).value = "0";
					  }
					  else 
					  {
						   if(loaitra == 2) //chiet khau
						   {
								document.getElementById("trakmTongluong" + pos).value = "0";
								document.getElementById("trakmTongtien" + pos).value = "0";
								document.getElementById("trakmChietkhau" + pos).value = document.getElementById('trakhuyenmai' + pos + '.sotong').value;
						   }
						   else //san pham
						   {
								document.getElementById("trakmTongluong" + pos).value = document.getElementById('trakhuyenmai' + pos + '.sotong').value;
								document.getElementById("trakmTongtien" + pos).value = "0";
								document.getElementById("trakmChietkhau" + pos).value = "0";
						   }
					  }
					  $('#cboxClose').click();
				   } 
			    }
			}
			
			var dg = encodeURIComponent(diengiai.replace(" ","+"));
			xmlhttp.open("GET","../../TrakhuyenmaiAjax?diengiai=" + dg + "&loaitra=" + loaitra + "&hinhthuc=" + hinhthuc + "&sotong=" + sotong + "&sanpham=" + sanpham + "&isThung=" + isthung.checked, true);
			xmlhttp.send(); 
		}
		
	</script>
	
	<script type="text/javascript">	
		function ajaxOption(id, value, pos)
		{
			//alert(id + ' - Value: ' + value);
			var xmlhttp;
			//if (value == "")
			//{
			  //document.getElementById("txtHint").innerHTML="";
			 // return;
			//}
			if (window.XMLHttpRequest)
			{
			  xmlhttp=new XMLHttpRequest();
			}
			else
			{
			  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			  if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			  {
				 //alert(xmlhttp.responseText);
				 var idTableSp = id.substring(0, parseInt(id.indexOf(".")));
			     document.getElementById(idTableSp+ ".tbsanpham").innerHTML = xmlhttp.responseText;
			  }
			}
			xmlhttp.open("POST","../../AjaxDKKM?nspId="+ value+"&pos="+pos, true);
			xmlhttp.send();
		}
		function ajaxOption2(id, value, pos)
		{
			var xmlhttp;
			//if (value == "")
			//{
			 // return;
			//}
			if (window.XMLHttpRequest)
			{
			  xmlhttp=new XMLHttpRequest();
			}
			else
			{
			  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			  if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			  {
				 //alert(xmlhttp.responseText);
				 var idTableSp = id.substring(0, parseInt(id.indexOf(".")));
			     document.getElementById(idTableSp+ ".tbsanpham").innerHTML = xmlhttp.responseText;
			  }
			}
			xmlhttp.open("POST","../../TrakhuyenmaiAjax?nspId="+ value+"&pos="+pos, true);
			xmlhttp.send();
		}
		
		function AjaxDKKM(pos)
		{
	    	var dkkmId=document.getElementById("dkkmId"+pos).value;
	    	
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
			  xmlhttp=new XMLHttpRequest();
			}
			else
			{
			  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			  if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			  {
				 var idTableSp =document.getElementById("dieukienkhuyenmai"+pos);
				 if(dkkmId!='')
				{
				 	idTableSp.innerHTML = xmlhttp.responseText;
				}
			  }
			}
			xmlhttp.open("POST","../../AjaxChuongTrinhKhuyenMai?action=AjaxDKKM&dkkmId="+dkkmId+"&pos="+pos+"&ctkmId=0", true);
			xmlhttp.send();
		}
	    
	    
	    function AjaxTraKM(pos)
		{
	    	var trakmId=document.getElementById("trakmId"+pos).value;
	    	
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
			  xmlhttp=new XMLHttpRequest();
			}
			else
			{
			  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			  if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			  {
				 var idTableSp =document.getElementById("trakhuyenmai"+pos);
				 if(trakmId!='')
				{
				 	idTableSp.innerHTML = xmlhttp.responseText;
				}
			  }
			}
			xmlhttp.open("POST","../../AjaxChuongTrinhKhuyenMai?action=AjaxTraKM&trakmId="+trakmId+"&pos="+pos+"&ctkmId=0", true);
			xmlhttp.send();
		}
	</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="ctkmForm" method="post" action="../../CtkhuyenmaiUpdateSvl">  
<% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> 
<input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="tkmTungay" value=''>
<input type="hidden" name="tkmDenngay" value=''>
<input type="hidden" name="tkmDiengiai" value=''>
<input type="hidden" name="dkkmTungay" value=''>
<input type="hidden" name="dkkmDenngay" value=''>
<input type="hidden" name="dkkmDien_giai" value=''>
<input type="hidden" name="active" value='<%= ctkmBean.getActive() %>'>
<input type="hidden" name="load" value='0'>

<div id="main" style="width:100%; padding-left: 1px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	<%=url %>
        </div>
        <div align="right" style="padding:5px;" class="tbnavigation">
        	<%=ChuyenNgu.get("Chào mừng",nnId) %> <%= userTen %> &nbsp; &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> <%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= ctkmBean.getMessage() %></textarea>
		         <% ctkmBean.setMessage(""); %>
    	</fieldset>
  	</div>
    <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
    <fieldset>
    	<legend class="legendtitle"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></legend></legend>
        <div style="width:100%; float:none" align="left">
                <TABLE width="100%" cellpadding="4" cellspacing="0">								
                    <TR>
                        <TD width="130px" class="plainlabel"><%=ChuyenNgu.get("Scheme",nnId) %> </TD>
                        <TD width="250px" class="plainlabel"><input type="text" name="scheme" id="scheme" size="30" value="<%= ctkmBean.getScheme() %>"></TD>
                    
                        <TD width="130px" class="plainlabel" valign="top"><%=ChuyenNgu.get("Diễn giải",nnId) %> </TD>
                        <TD class="plainlabel" valign="top">
                        	<input type="text" name="diengiai" id="diengiai" style="width:400px" value="<%= ctkmBean.getDiengiai() %>" >
                        </TD>
                    </TR>               
                    <TR>
                    
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Loại chương trình",nnId) %> </TD>
                            <% if(!ctkmBean.getType().equals("4")){ %>
                        	<TD class="plainlabel" >
                        <%} else {%>
                       	 <TD class="plainlabel" colspan = "1" >
                        <%} %>
                        
                            <select name="loaiCt" id="loaiCt" onchange="tichluy()">
                            <% if(ctkmBean.getType().equals("1")){ %>
                                <option value="1" selected><%=ChuyenNgu.get("Bình thường",nnId) %></option>
                                <option value="2"><%=ChuyenNgu.get("On top",nnId) %></option>
                                <%-- <option value="3"><%=ChuyenNgu.get("Tích lũy",nnId) %></option>
                                <option value="4"><%=ChuyenNgu.get("Đặc biệt",nnId) %></option> --%>
                                <option value="">&nbsp;</option>
                            <%} else if(ctkmBean.getType().equals("2")){ %>
                            	<option value="2" selected><%=ChuyenNgu.get("On top",nnId) %></option>
                            	<option value="1"><%=ChuyenNgu.get("Bình thường",nnId) %></option> 
                            	<%--  <option value="3"><%=ChuyenNgu.get("Tích lũy",nnId) %></option>   
                            	   <option value="4"><%=ChuyenNgu.get("Đặc biệt",nnId) %></option> --%>                          
                                <option value="">&nbsp;</option>
                           <%--  <%}  else  if(ctkmBean.getType().equals("3")) { %>
                            	<option value="" >&nbsp;</option>
                            	<option value="1"><%=ChuyenNgu.get("Bình thường",nnId) %></option> 
                            	<option value="2"><%=ChuyenNgu.get("On top",nnId) %></option>   
                            	   <option value="4"><%=ChuyenNgu.get("Đặc biệt",nnId) %></option>
                            	 <option value="3" selected><%=ChuyenNgu.get("Tích lũy",nnId) %></option>   
                            	  <%}  else  if(ctkmBean.getType().equals("4")) { %>
                            	<option value="" >&nbsp;</option>
                            	<option value="1"><%=ChuyenNgu.get("Bình thường",nnId) %></option> 
                            	<option value="2"><%=ChuyenNgu.get("On top",nnId) %></option>   
                            	   <option value="4" selected ><%=ChuyenNgu.get("Đặc biệt",nnId) %></option>
                            	 <option value="3" ><%=ChuyenNgu.get("Tích lũy",nnId) %></option>    --%>                                                                                                      
                            <% }else{ %>
                            	<option value="" selected>&nbsp;</option>
                            	<option value="1"><%=ChuyenNgu.get("Bình thường",nnId) %></option> 
                            	<option value="2"><%=ChuyenNgu.get("On top",nnId) %></option>   
                            	 <%-- <option value="3"><%=ChuyenNgu.get("Tích lũy",nnId) %></option>
                            	    <option value="4"><%=ChuyenNgu.get("Đặc biệt",nnId) %></option> --%>           
                            <%} %>
                            </select>
                        </TD>
                        
                       <%--   <TD width="130px" class="plainlabel" valign="top">Mã IO (SAP) </TD>
                        <TD class="plainlabel" valign="top" colspan="2">
                        	<input type="text" name="io" id="id" style="width:400px" value="<%= ctkmBean.getIo() %>" >
                        </TD> --%>
                             <% if(1==1){ %>
                       <TD width="130px" class="plainlabel" valign="top"><%=ChuyenNgu.get("Scheme ERP",nnId) %> </TD>
                        <TD class="plainlabel" valign="top" colspan="2">
                        	<input type="text" maxlength="" name="schemeErp" id="schemeErp" style="width:400px" value="<%= ctkmBean.getSchemeErp() %>" >
                        </TD> 
                        <%} %>
                    </TR>
                       <% if(!ctkmBean.getType().equals("4")){ %>
                    <tr>
                    	<td class="plainlabel"><%=ChuyenNgu.get("Mức phân bổ",nnId) %></td>
                    	<TD class="plainlabel" colspan="5" >
                    		<select name="mucphanbo"  >
                            	<option value="0" selected="selected" ><%=ChuyenNgu.get("Nhà phân phối",nnId) %></option>
                            </select>
                        </TD>
                    </tr>
                    <%} %>
                     <%-- <% if(ctkmBean.getkhoId().equals("100001")) {%>
                    <TR>
	                        <TD class="plainlabel" >Thời gian NPP đặt hàng : &nbsp;&nbsp;&nbsp;&nbsp;Từ ngày</TD>
	                        <TD class="plainlabel" colspan="1" >
	                            <input type="text" size="10" class="days" 
	                                   id="tungay_dathang" name="tungay_dathang" value="<%= ctkmBean.getTuNgay_DatHang() %>" maxlength="10" /> </TD>
	                                   <TD  class="plainlabel"   > 
	                              Đến ngày   &nbsp;&nbsp;</TD>
	                             <TD  class="plainlabel" colspan="1">  <input type="text" size="10" class="days" 
	                                    id="denngay_dathang" name="denngay_dathang" value="<%= ctkmBean.getDenNgay_DatHang() %>" maxlength="10" />
	                         
	                        </TD>
                       	</TR>
                    <%} %> --%>
                    
                     <% if(ctkmBean.getType().equals("3")) {%>
					  <TR>
	                        <TD class="plainlabel" ><%=ChuyenNgu.get("Ngày đăng ký:",nnId) %> &nbsp;&nbsp;&nbsp;&nbsp;<%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
	                        <TD class="plainlabel" colspan="1" >
	                            <input type="text" size="10" class="days" autocommit=false
	                                   id="tungay" name="tungay" value="<%= ctkmBean.getTungay() %>" maxlength="10" />
	                          
	                             </TD>
	                               <TD class="plainlabel" >  <%=ChuyenNgu.get("Đến ngày",nnId) %>  </TD> 
	                               <TD  colspan="1" class="plainlabel"> <input type="text" size="10" class="days"  autocommit=false
	                                    id="denngay" name="denngay" value="<%= ctkmBean.getDenngay() %>" maxlength="10" /> </TD>
	                         
	                      
                       	</TR>
	                    <TR>
	                        <TD class="plainlabel" ><%=ChuyenNgu.get("Ngày tính doanh số:",nnId) %>&nbsp;&nbsp;&nbsp;&nbsp;<%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
	                        <TD class="plainlabel">
	                            <input type="text" size="10" class="days"  autocommit=false
	                                   id="ngayds" name="ngayds"  value="<%= ctkmBean.getngayds() %>" maxlength="10" />   </TD>
                             				<TD class="plainlabel" > <%=ChuyenNgu.get("Đến ngày",nnId) %>   </TD> 
                             <TD class="plainlabel" > 
                             <input type="text" size="10" class="days" autocommit=false id="ngayktds" name="ngayktds" value="<%= ctkmBean.getngayktds() %>" maxlength="10" />
	                         
	                        </TD>
	                    
	                       
                       	</TR>
                       	<TR>
                       	 <TD class="plainlabel" ><%=ChuyenNgu.get("Phần trăm tối đa",nnId) %></TD>
	                        <TD class="plainlabel" colspan="5">
	                            <input type="text" style="text-align: right;"
	                                   id="phantramtoida" name="phantramtoida" value="<%= ctkmBean.getPTToida() %>" maxlength="10" /> %
	                        </TD>
                       	</TR>
                   
                   <% } else {%>                
                    <TR>
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %> </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days" autocommit=false
                                   id="tungay" name="tungay" value="<%= ctkmBean.getTungay() %>" maxlength="10" />
                        </TD>
                    
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %> </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days" autocommit=false
                                    id="denngay" name="denngay" value="<%= ctkmBean.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <%} %>
                    
                    <TR>
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Kho",nnId) %> </TD>
                        <% if(!ctkmBean.getType().equals("4")){ %>
                        	<TD class="plainlabel" >
                        <%} else {%>
                       	 <TD class="plainlabel" colspan = "4" >
                        <%} %>
                            <select name="kho" onchange="submitform();" >
                            <% while(khoIds.next()) 
                            {
                              if(khoIds.getString("pk_seq").equals(ctkmBean.getkhoId())){ %>
                                <option value="<%=khoIds.getString("pk_seq") %>" selected><%= khoIds.getString("ten") + "-" + khoIds.getString("diengiai") %></option>
                                
                            <%}else { %>
                            	<option value="<%=khoIds.getString("pk_seq") %>"><%= khoIds.getString("ten") + "-" + khoIds.getString("diengiai") %></option>
                            <%} }%>
                            	
                            </select>
                        </TD>
                      <% if(!ctkmBean.getType().equals("4")){ %>
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Nhóm khách hàng",nnId) %> </TD>
                        <TD class="plainlabel">
                            <select name="nhomkhnpp">
                            <option value=""> </option>
                            <% 
                            Nhomkhnpp.beforeFirst();
                            while(Nhomkhnpp.next()) 
                            {
                              if(Nhomkhnpp.getString("pk_seq").equals(ctkmBean.getNhomkhnppId())){ %>
                                <option value="<%= Nhomkhnpp.getString("pk_seq") %>" selected><%=Nhomkhnpp.getString("diengiai") %></option>
                                
                            <%}else { %>
                            	<option value="<%= Nhomkhnpp.getString("pk_seq") %>"><%= Nhomkhnpp.getString("diengiai") %></option>
                            <%}}%>
                            	
                            </select>
                        </TD>
                        
                       
                        
                        <%} %>
                    </TR>
                    
                    <TR>
                    <TD class="plainlabel" colspan="2">
                    	 <TD class="plainlabel" ><%=ChuyenNgu.get("Nhóm khách hàng loại trừ",nnId) %> </TD>
                    	<TD class="plainlabel" >
                            <select name="nhom_kh_loai_tru">
                            <option value=""> </option>
                            <% 
                            Nhomkhnpp.beforeFirst();
                            while(Nhomkhnpp.next()) 
                            {
                              if(Nhomkhnpp.getString("pk_seq").equals(ctkmBean.getNhom_kh_loai_tru())){ %>
                                <option value="<%= Nhomkhnpp.getString("pk_seq") %>" selected><%=Nhomkhnpp.getString("diengiai") %></option>
                                
                            <%}else { %>
                            	<option value="<%= Nhomkhnpp.getString("pk_seq") %>"><%= Nhomkhnpp.getString("diengiai") %></option>
                            <%}}%>
                            	
                            </select>
                        </TD>
                    </TR>
                    
                      <% if(!ctkmBean.getType().equals("4")){ %>
                    <TR>
                    	<TD class="plainlabel" ><%=ChuyenNgu.get("Loại khách hàng",nnId) %> </TD>
                        <TD class="plainlabel" width="230px" >
                            <select name="loaikhId" id="loaikhId" multiple="multiple"  >
					        	<option value=""></option>
		                        <% if(loaikhRS != null) {
		                         while(loaikhRS.next()) 
		                         {
		                           if(ctkmBean.getLoaikhIds().indexOf(loaikhRS.getString("loaiId")) >= 0 ){ %>
		                             <option value="<%= loaikhRS.getString("loaiId") %>" selected style="padding: 2px" ><%= loaikhRS.getString("loaiTen") %></option>
		                         <%}else { %>
		                         	<option value="<%= loaikhRS.getString("loaiId") %>" style="padding: 2px"><%= loaikhRS.getString("loaiTen") %></option>
		                         <%} }}%>        	
		                    </select>
		                 </TD>
		                 
		                 <TD class="plainlabel" ><%=ChuyenNgu.get("Áp dụng cho",nnId) %></TD>
                         <TD class="plainlabel" >
                            <select name="apdungchoId" id="apdungchoId"  >
                            	<% if( ctkmBean.getApdungcho().equals("0") ) { %>
					        		<option value="0" selected="selected" ><%=ChuyenNgu.get("Sell In & Sell Out",nnId) %></option>
					        	<% } else { %>
					        		<option value="0"><%=ChuyenNgu.get("Sell In & Sell Out",nnId) %></option>
					        	<% } %>
		                        <% if( ctkmBean.getApdungcho().equals("1") ) { %>
					        		<option value="1" selected="selected" ><%=ChuyenNgu.get("Sell In",nnId) %></option>
					        	<% } else { %>
					        		<option value="1"><%=ChuyenNgu.get("Sell In",nnId) %></option>
					        	<% } %>
		                        <% if( ctkmBean.getApdungcho().equals("2") ) { %>
					        		<option value="2" selected="selected" ><%=ChuyenNgu.get("Sell Out",nnId) %></option>
					        	<% } else { %>
					        		<option value="2"><%=ChuyenNgu.get("Sell Out",nnId) %></option>
					        	<% } %>
		                    </select>
		                 </TD>
		                 
		                 <%-- <TD class="plainlabel" >Kênh bán hàng </TD>
                         <TD class="plainlabel" width="230px" >
                            <select name="kenhbanhangId" id="kenhbanhangId" onchange="submitform();" >
					        	<option value=""></option>
		                        <% if(kbhRS != null) {
		                         while(kbhRS.next()) 
		                         {
		                           if(ctkmBean.get().equals(kbhRS.getString("pk_seq"))){ %>
		                             <option value="<%= kbhRS.getString("pk_seq") %>" selected style="padding: 2px" ><%= kbhRS.getString("ten") %></option>
		                         <%}else { %>
		                         	<option value="<%= kbhRS.getString("pk_seq") %>" style="padding: 2px"><%= kbhRS.getString("ten") %></option>
		                         <%} }}%>        	
		                    </select>
		                 </TD> --%>
		                <%-- <% if(ctkmBean.getKbhIds().equals("100021")){ %> 
		                <TD class="plainlabel" >Ngân sách kế hoạch </TD>
                        <TD class="plainlabel">
                        	<input type="text" name="ngansachkehoach" id="ngansachkehoach" size="30" style="text-align:right" value="0" onkeypress="return keypress(event);">
                        </TD>
                        <% } else { %>
                        <TD class="plainlabel" ></TD>
                        <TD class="plainlabel"></TD>
                        <% } %> --%>
                    </TR>
                    <TR>
                    <TD class="plainlabel" ><%=ChuyenNgu.get("Kênh bán hàng",nnId) %> </TD>
                        <TD class="plainlabel" colspan = "4" >
                            <select name="kbhIds" id="kbhIds" multiple="multiple"  >
					        	<option value=""></option>
		                        <% if(kbhRS != null) {
		                         while(kbhRS.next()) 
		                         {
		                           if(ctkmBean.getKbhIds().indexOf(kbhRS.getString("pk_seq")) >= 0 ){ %>
		                             <option value="<%= kbhRS.getString("pk_seq") %>" selected style="padding: 2px" ><%= kbhRS.getString("ten") %></option>
		                         <%}else { %>
		                         	<option value="<%= kbhRS.getString("pk_seq") %>" style="padding: 2px"><%= kbhRS.getString("ten") %></option>
		                         <%} }}%>        	
		                    </select>
                        </TD>
                        
                    </TR>
                    <TR>
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Loại ngân sách",nnId) %> </TD>
                        <TD class="plainlabel" colspan="3" >
                            <% if(ctkmBean.getLoaiNganSach().equals("1")){ %>
                            	<input onchange="submitform();" type="radio" name="loains" value = "1" checked = "checked" id="apphanbo" ><%=ChuyenNgu.get("Áp phân bổ",nnId) %> &nbsp;&nbsp;
                            	<input onchange="submitform();" type="radio" name="loains" value = "0"  id="khonggh" ><%=ChuyenNgu.get("Không giới hạn ngân sách",nnId) %>
                            <%}else{ %>
                            	<input onchange="submitform();" type="radio" name="loains" value = "1"  id="apphanbo" ><%=ChuyenNgu.get("Áp phân bổ",nnId) %> &nbsp;&nbsp;
                            	<input onchange="submitform();" type="radio" name="loains" value= "0" checked="checked" id="khonggh" ><%=ChuyenNgu.get("Không giới hạn ngân sách",nnId) %>
                            <%} %>
                            
                           <%--  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <% if(ctkmBean.getNppTuchay().equals("1")) { %>
                            	<input type="checkbox" name="nppTuchay" value="1" checked="checked" > <%=ChuyenNgu.get("Nhà phân phối tự chạy",nnId) %>
                            <% } else { %>
                            	<input type="checkbox" name="nppTuchay" value="1"  > <%=ChuyenNgu.get("Nhà phân phối tự chạy",nnId) %>
                            <% } %> --%>
                        </TD>
                    </TR>
                    <TR id="ngansachct" style="display: none">
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Ngân sách",nnId) %> </TD>
                        <TD class="plainlabel"><input type="text" name="ngansach" id="ngansach" size="30" style="text-align:right" value="0" onkeypress="return keypress(event);"></TD>
                    </TR>
                    
                       <TR>
                       <TD class="plainlabel"></TD>
                        <TD class="plainlabel">
                        	<%-- <% if (ctkmBean.getApdungchoDHLe().equals("1")){ %> 
                        		<input type="checkbox" name="ApDUNGCHODHLE" value="1" checked="checked"><i> Áp dụng cho đơn hàng lẻ</i>
                        	<% } else { %>
                        		<input type="checkbox" name="ApDUNGCHODHLE" value="1" ><i> Áp dụng cho đơn hàng lẻ</i>
                        	 <% }  %>  &nbsp;&nbsp;
                        	  --%>
                        	 <% if (ctkmBean.getPhanbotheoDH().equals("1")){ %> 
                        		<input type="checkbox" name="PHANBOTHEODH" value="1" checked="checked"><i> <%=ChuyenNgu.get("Phân bổ ngân sách theo số suất",nnId) %> </i>
                        	<% } else { %>
                        		<input type="checkbox" name="PHANBOTHEODH" value="1" ><i> <%=ChuyenNgu.get("Phân bổ ngân sách theo số suất",nnId) %></i>
                        	 <% }  %>
                        </TD>
                        <TD class="plainlabel" colspan="3" >
                    </TR>
                    <%} %>
                </TABLE>       
         </div>
        
      <div id="accordion" style="width:100%; height:auto; float:none; font-size:1.0em" align="left">
         <% if(!ctkmBean.getType().equals("4")){ %>              
       		<h1 style="font-size:1.8em"><a href="#"><%=ChuyenNgu.get("Khai báo điều kiện khuyến mãi",nnId) %></a></h1>
			<div style="height:auto">
                <!-- <TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px" -->
                <table class="chitieutable">
                    <TR class="tbheader">
                        <TH align="center" width="10%"> <%=ChuyenNgu.get("Mã",nnId) %></TH>
                        <TH align="left" width="50%"> <%=ChuyenNgu.get("Diễn giải",nnId) %> </TH>
                        <TH align="center" width="10%"> <%=ChuyenNgu.get("Tổng lượng",nnId) %></TH>
                        <TH align="center" width="10%"> <%=ChuyenNgu.get("Tổng tiền",nnId) %></TH>
                        <TH align="center" width="10%"> <%=ChuyenNgu.get("Phép toán",nnId) %></TH>
                    </TR>
					<% 
						int i = 0;
                        System.out.println("So DKKM lay duoc: " + dkkmList.size());
						if(dkkmList.size() > 0)
						{ 
						while(i < dkkmList.size())
						{	
							Dieukienkm dkkm = (Dieukienkm)dkkmList.get(i);	
							
							IDieukienDetail dkkmDetai = dkkm.getDieukienDetail();
							List<ISanpham> spList = dkkmDetai.getSpList();
					%>
							
							<TR class='tbdarkrow'>
	                        <TD align="center">
	                        	<input type="text" id='dkkmId<%= i %>' name="dkkmId" size="10" value="<%= dkkm.getId() %>" 
	                        		onkeyup="ajax_showOptions(this,'dieukien',event)" AUTOCOMPLETE="off" style="width: 70%" >
		                        
		                        <a class="dieukienkhuyenmai<%= i %>" href="#" onclick ="AjaxDKKM(<%=i%> )" >
		                        		<img style="top: -4px;" src="../images/vitriluu.png" title="Tạo mới điều kiện"></a>
				                <div style='display:none'>
			                        <div id='dieukienkhuyenmai<%= i %>' style='padding:0px 5px; background:#fff;'>
			                        	<h4 align="left"><%=ChuyenNgu.get("Tạo mới điều kiện khuyến mãi",nnId) %></h4>
										<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
			                            	<tr>
			                                	<td width="40%" valign="top" align="left"><%=ChuyenNgu.get("Diễn giải",nnId) %></td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="dieukienkhuyenmai<%= i %>.diengiai" id="dieukienkhuyenmai<%= i %>.diengiai" value="<%= dkkmDetai.getDiengiai() %>" />
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Loại điều kiện",nnId) %></td>
			                                    <td valign="top" align="left">
			                                    	<select name="dieukienkhuyenmai<%= i %>.loaidieukien" id = "dieukienkhuyenmai<%= i %>.loaidieukien">
			                                    		<% if(dkkmDetai.getLoaidieukien().equals("1")){ %>
				                                    		<option value="2"><%=ChuyenNgu.get("Bất kỳ trong",nnId) %></option>
				                                    		<option value="1" selected="selected"><%=ChuyenNgu.get("Bắt buộc nhập số lượng",nnId) %></option>
			                                    		<%} else { %>
			                        		<option value="2" selected="selected"><%=ChuyenNgu.get("Bất kỳ trong",nnId) %></option>
				                                    		<option value="1"><%=ChuyenNgu.get("Bắt buộc nhập số lượng",nnId) %></option>
			                                    		<%} %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Hình thức",nnId) %></td>
			                                    <td valign="top" align="left">
			                                    	<select name = "dieukienkhuyenmai<%= i %>.hinhthuc" id = "dieukienkhuyenmai<%= i %>.hinhthuc" >
			                                    	<% if(dkkmDetai.getHinhthuc().equals("2")){ %>
			                                    		<option value="1"><%=ChuyenNgu.get("Nhập tổng lượng",nnId) %></option>
			                                    		<option value="2" selected="selected"><%=ChuyenNgu.get("Nhập tổng tiền",nnId) %></option>
			                                    	<%} else { %>
			                                    		<option value="1" selected="selected"><%=ChuyenNgu.get("Nhập tổng lượng",nnId) %></option>
			                                    		<option value="2"><%=ChuyenNgu.get("Nhập tổng tiền",nnId) %></option>
			                                    	<%} %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Tổng lượng / Tổng tiền",nnId) %></td>
			                                    <td valign="top" align="left">
			                                    	<input type="text" name="dieukienkhuyenmai<%= i %>.sotong" id="dieukienkhuyenmai<%= i %>.sotong" 
			                                    			value="<%= dkkmDetai.getSotong() %>" style="text-align: right;"/>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %></td>
			                                    <td valign="top" align="left">		                                    	
			                                    	<select name="dieukienkhuyenmai<%= i %>.nhomsanpham" id="dieukienkhuyenmai<%= i %>.nhomsanpham" onChange = "ajaxOption(this.id, this.value, <%= i %>)">
			                                    		<option value=""> </option>
			                                    		<% if(nhomspRs != null)
			                                    		{ 
			                                    			nhomspRs.beforeFirst();
			                                    			while(nhomspRs.next()){ if(dkkmDetai.getNhomspId().equals(nhomspRs.getString("nspId"))){ %>
			                                    				<option value="<%= nhomspRs.getString("nspId") %>"><%= nhomspRs.getString("nspTen") %></option>
			                                    		<% } else { %> 
			                                    				<option value="<%= nhomspRs.getString("nspId") %>"><%= nhomspRs.getString("nspTen") %></option>
			                                    		 <%} } } %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left" colspan="2">
			                                	<% if(dkkm.isTheothung() ) { %>
			                                		<input type="checkbox" name="dieukienkhuyenmai<%= i %>.tinhtheothung" id="dieukienkhuyenmai<%= i %>.tinhtheothung" value='1' checked="checked" > <span style="font-style: italic;"><%=ChuyenNgu.get("Số lượng tính theo Kg",nnId) %></span> 
			                                	<%} else { %>
			                                		<input type="checkbox" name="dieukienkhuyenmai<%= i %>.tinhtheothung" id="dieukienkhuyenmai<%= i %>.tinhtheothung" value='1' > <span style="font-style: italic;"><%=ChuyenNgu.get("Số lượng tính theo Kg",nnId) %></span>
			                                	<%} %>
			                                	</td>
			                                </tr>
			                                <tr>
			                                	<td colspan="2">
			                                		<table align="left" cellpadding="2px" cellspacing="2px">
				                                		<tr>
				                                			<th width="100px" align="center"><%=ChuyenNgu.get("Mã sản phẩm",nnId) %></th>
				                                			<th width="250px" align="left"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %></th>
				                                			<th width="60px" align="left"><%=ChuyenNgu.get("Số lượng",nnId) %></th>
				                                		</tr>
				                                	</table>
				                                	<div id="dieukienkhuyenmai<%= i %>.tbsanpham" style="width: 100%; max-height: 150px; overflow: auto">
				                                	<table align="left" cellpadding="2px" cellspacing="2px">
				                                	<% 
				                                	int count = 0;
				                                	while(count < spList.size())
				                                	{
				                                		ISanpham sp = spList.get(count);
				                                		%>
				                                		<tr>
				                                			<td width="100px" align="center">
				                                				<input type="text" value="<%= sp.getMasanpham() %>" style="width: 100px" name="dieukienkhuyenmai<%= i %>.masanpham" 
				                                						onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
				                                			</td>
				                                			<td width="250px" align="left">
				                                				<input type="text" value="<%= sp.getTensanpham() %>" name="dieukienkhuyenmai<%= i %>.tensanpham" style="width: 250px" readonly="readonly">
				                                			</td>
				                                			<td width="60px" align="center">
				                                				<input type="text" value="<%= sp.getSoluong() %>" name="dieukienkhuyenmai<%= i %>.soluong" style="width: 60px; text-align: right;">
				                                			</td>
				                                		</tr>
				                                	<% count++; } %>
				                                	<% for(int pos=count; pos < 50; pos++){ %>
				                                		<tr>
				                                			<td width="100px" align="center">
				                                				<input type="text" value="" style="width: 100px" name="dieukienkhuyenmai<%= i %>.masanpham" 
				                                						onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
				                                			</td>
				                                			<td width="250px" align="left">
				                                				<input type="text" value="" name="dieukienkhuyenmai<%= i %>.tensanpham" style="width: 250px" readonly="readonly">
				                                			</td>
				                                			<td width="60px" align="center">
				                                				<input type="text" value="" name="dieukienkhuyenmai<%= i %>.soluong" style="width: 60px; text-align: right;">
				                                			</td>
				                                		</tr>
				                                	<%} %>
			                                		</table>
			                                		</div>
			                                	</td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left" colspan="2">
			        								<a class="button" href="javascript:submitform();">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Nhập lại",nnId) %>  </a>
			        								&nbsp;&nbsp;&nbsp;
			        								<a class="button" href="javascript:submitform2(<%= i %>);">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Lưu điều kiện",nnId) %>  </a>
			                                	</td>
			                                </tr>
			                            </table>
									</div>
				                </div>
		                        
	                        </TD>
	                        <TD align="left"><input type="text" name="dkkmDiengiai" id="dkkmDiengiai<%= i %>" size="80" value="<%= dkkm.getDiengiai() %>" readonly></TD>
	                        <TD align="center"><input type="text" name="dkkmTongluong" id="dkkmTongluong<%= i %>" size="6" value="<%= dkkm.getTongluong() %>" style="text-align:right" readonly></TD>							           
	                        <TD align="center"><input type="text" name="dkkmTongtien" id="dkkmTongtien<%= i %>"  size="6" value="<%= dkkm.getTongtien() %>" style="text-align:right" readonly></TD>
	                        <TD align="center">
	                        	<select name="dkkmPheptoan">
	                        	<% if(dkkm.getPheptoan().trim().equals("1")){ %>
	                            	<option value="1" selected="selected"><%=ChuyenNgu.get("And",nnId) %></option>
	                                <option value="2"><%=ChuyenNgu.get("Or",nnId) %></option>  
	                                <option value=""></option>   
	                            <%} else { if(dkkm.getPheptoan().trim().equals("2")){ %>
	                            	<option value="1"><%=ChuyenNgu.get("And",nnId) %></option>
	                                <option value="2" selected="selected"><%=ChuyenNgu.get("Or",nnId) %></option>  
	                                <option value=""></option>  
	                            <% } else { %>
	                            	<option value="" selected> </option> 
	                            	<option value="1"><%=ChuyenNgu.get("And",nnId) %></option>
	                                <option value="2"><%=ChuyenNgu.get("Or",nnId) %></option>  
	                             <%} } %>
	                            </select>
	                            <input type="hidden" name="dkkmThutu" size="6" value="<%= i %>" style="text-align:right">
	                        </TD>
	                    </TR>
		                   
                    	<% i++; } } %>
                    	<% for(int j = i; j < 10; j++){ %>
                    		<TR class='tbdarkrow'>
		                        <TD align="center">
		                        	<input type="text" id='dkkmId<%= j %>' name="dkkmId" size="10" value="" onkeyup="ajax_showOptions(this,'dieukien',event)" 
		                        		 AUTOCOMPLETE="off" style="width: 70%">
			                        
			                        <a class="dieukienkhuyenmai<%= j %>" href="#" onclick ="AjaxDKKM(<%=j%> )">
			                        		<img style="top: -4px;" src="../images/vitriluu.png" title="Tạo mới điều kiện"></a>
					                <div style='display:none'>
				                        <div id='dieukienkhuyenmai<%= j %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left"><%=ChuyenNgu.get("Tạo mới điều kiện khuyến mãi",nnId) %></h4>
											<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
				                            	<tr>
				                                	<td width="40%" valign="top" align="left"><%=ChuyenNgu.get("Diễn giải",nnId) %></td>
				                                    <td valign="top" align="left">
					                                    <input type="text" name="dieukienkhuyenmai<%= j %>.diengiai" id="dieukienkhuyenmai<%= j %>.diengiai" value="" />
				                                    </td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left"><%=ChuyenNgu.get("Loại điều kiện",nnId) %></td>
				                                    <td valign="top" align="left">
				                                    	<select name="dieukienkhuyenmai<%= j %>.loaidieukien" id = "dieukienkhuyenmai<%= j %>.loaidieukien">
				                                    		<option value="2"><%=ChuyenNgu.get("Bất kỳ trong",nnId) %></option>
				                                    		<option value="1"><%=ChuyenNgu.get("Bắt buộc nhập số lượng",nnId) %></option>
				                                    	</select>
				                                    </td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left"><%=ChuyenNgu.get("Hình thức",nnId) %></td>
				                                    <td valign="top" align="left">
				                                    	<select name = "dieukienkhuyenmai<%= j %>.hinhthuc" id = "dieukienkhuyenmai<%= j %>.hinhthuc" >
				                                    		<option value="1"><%=ChuyenNgu.get("Nhập tổng lượng",nnId) %></option>
				                                    		<option value="2"><%=ChuyenNgu.get("Nhập tổng tiền",nnId) %></option>
				                                    	</select>
				                                    </td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left"><%=ChuyenNgu.get("Tổng lượng / Tổng tiền",nnId) %></td>
				                                    <td valign="top" align="left">
													<input type="text" name="dieukienkhuyenmai<%= j %>.sotong" id="dieukienkhuyenmai<%= j %>.sotong" value="" style="text-align: right;">
				                                    </td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left"><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %></td>
				                                    <td valign="top" align="left">		                                    	
				                                    	<select name="dieukienkhuyenmai<%= j %>.nhomsanpham" id="dieukienkhuyenmai<%= j %>.nhomsanpham" onChange = "ajaxOption(this.id, this.value, <%= j %>)">
				                                    		<option value=""> </option>
				                                    		<% if(nhomspRs != null)
				                                    		{ 
				                                    			nhomspRs.beforeFirst();
				                                    			while(nhomspRs.next()){ %>
				                                    				<option value="<%= nhomspRs.getString("nspId") %>"><%= nhomspRs.getString("nspTen") %></option>
				                                    		<%} } %>
				                                    	</select>
				                                    </td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left" colspan="2">
				                                		<input type="checkbox" name="dieukienkhuyenmai<%= j %>.tinhtheothung" id="dieukienkhuyenmai<%= j %>.tinhtheothung" value='1' > <span style="font-style: italic;"><%=ChuyenNgu.get("Số lượng tính theo Kg",nnId) %></span>
				                                	</td>
				                                </tr>
				                                <tr>
				                                	<td colspan="2">
				                                		<table align="left" cellpadding="2px" cellspacing="2px">
					                                		<tr>
					                                			<th width="100px" align="center"><%=ChuyenNgu.get("Mã sản phẩm",nnId) %></th>
					                                			<th width="250px" align="left"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %></th>
					                                			<th width="60px" align="left"><%=ChuyenNgu.get("Số lượng",nnId) %></th>
					                                		</tr>
					                                	</table>
					                                	<div id="dieukienkhuyenmai<%= j %>.tbsanpham" style="width: 100%; max-height: 150px; overflow: auto">
					                                	<table align="left" cellpadding="2px" cellspacing="2px">
					                                	<% for(int pos=0; pos < 50; pos++){ %>
					                                		<tr>
					                                			<td width="100px" align="center">
					                                				<input type="text" value="" style="width: 100px" name="dieukienkhuyenmai<%= j %>.masanpham" 
					                                						onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
					                                			</td>
					                                			<td width="250px" align="left">
					                                				<input type="text" value="" name="dieukienkhuyenmai<%= j %>.tensanpham" style="width: 250px" readonly="readonly">
					                                			</td>
					                                			<td width="60px" align="center">
					                                				<input type="text" value="" name="dieukienkhuyenmai<%= j %>.soluong" style="width: 60px; text-align: right;">
					                                			</td>
					                                		</tr>
					                                	<%} %>
				                                		</table>
				                                		</div>
				                                	</td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left" colspan="2">
				        								<a class="button" href="javascript:submitform();">
				        								<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Nhập lại",nnId) %>  </a>
				        								&nbsp;&nbsp;&nbsp;
				        								<a class="button" href="javascript:submitform2(<%= j %>);">
				        								<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Lưu điều kiện",nnId) %>  </a>
				                                	</td>
				                                </tr>
				                            </table>
										</div>
					                </div>
			                        
		                        </TD>
		                        <TD align="left"><input type="text" name="dkkmDiengiai" id="dkkmDiengiai<%= j %>" size="80" value="" readonly></TD>
		                        <TD align="center"><input type="text" name="dkkmTongluong" id="dkkmTongluong<%= j %>" size="6" value="" style="text-align:right" readonly></TD>							           
		                        <TD align="center"><input type="text" name="dkkmTongtien" id="dkkmTongtien<%= j %>"  size="6" value="" style="text-align:right" readonly></TD>
		                        <TD align="center">
		                        	<select name="dkkmPheptoan">
		                            	<option value="" selected></option>
		                            	<option value="1"><%=ChuyenNgu.get("And",nnId) %></option>
		                                <option value="2"><%=ChuyenNgu.get("Or",nnId) %></option>     
		                            </select>
		                            <input type="hidden" name="dkkmThutu" size="6" value="<%= j %>" style="text-align:right">
		                        </TD>
		                    </TR>
                    	<%} %>
                    <TR>
                        <TD align="center" colspan="10" class="tbfooter">&nbsp;</TD>
                    </TR>
				</TABLE>
				
			 </div>
			 <h1 style="font-size:1.8em"><a href="#"><%=ChuyenNgu.get("Trả khuyến mãi",nnId) %></a></h1> 
             <div style="height:auto">
              <!-- <TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px"> -->
              <table class="chitieutable">
                  <TR class="tbheader">
                      <TH align="center" width="10%"> <%=ChuyenNgu.get("Mã",nnId) %> </TH>
                      <TH align="left" width="40%" > <%=ChuyenNgu.get("Diễn giải",nnId) %> </TH>
                      <TH align="center" width="10%" > <%=ChuyenNgu.get("Tổng lượng",nnId) %></TH>
                      <TH align="center" width="10%"> <%=ChuyenNgu.get("Tổng tiền",nnId) %></TH>
                      <TH align="center" width="10%"> <%=ChuyenNgu.get("Chiết khấu",nnId) %></TH>
                      <TH align="center" width="10%" > <%=ChuyenNgu.get("Phép toán",nnId) %></TH>
                  </TR>
                  <% 
						int pos = 0;
						if(trakmList.size() > 0)
						{ 
						while(pos < trakmList.size())
						{	
							Trakm tkm = (Trakm)trakmList.get(pos);	

							ITrakmDetail tkmDetai = tkm.getTraDetail();
							List<ISanpham> spTraList = tkmDetai.getSpList();
					%>
							
							<TR class='tbdarkrow'>
	                        <TD align="center">
	                        	<input type="text" id='trakmId<%= pos %>' name="trakmId" size="10" value="<%= tkm.getId() %>" 
	                        				onkeyup="ajax_showOptions(this,'trakhuyenmai',event)" AUTOCOMPLETE="off" style="width: 70%">
		                        
		                        <a class="trakhuyenmai<%= pos %>" href="#" onclick ="AjaxTraKM(<%=pos%>)">
		                        		<img style="top: -4px;" src="../images/vitriluu.png" title="Tạo mới trả khuyến mãi"></a>
				                <div style='display:none'>
			                        <div id='trakhuyenmai<%= pos %>' style='padding:0px 5px; background:#fff;'>
			                        	<h4 align="left"><%=ChuyenNgu.get("Tạo mới trả khuyến mãi",nnId) %></h4>
										<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
			                            	<tr>
			                                	<td width="40%" valign="top" align="left"><%=ChuyenNgu.get("Tạo mới trả khuyến mãi",nnId) %></td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="trakhuyenmai<%= pos %>.diengiai" id="trakhuyenmai<%= pos %>.diengiai" value="<%= tkmDetai.getDiengiai() %>" />
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Loại trả",nnId) %></td>
			                                    <td valign="top" align="left">
			                                    	<select name="trakhuyenmai<%= pos %>.loaitra" id = "trakhuyenmai<%= pos %>.loaitra">
			                                    		<% if(tkmDetai.getLoaitra().equals("1")){ %>
			                                    			<option value="1" selected="selected"><%=ChuyenNgu.get("Trả tiền",nnId) %></option>
			                                    			<option value="3"><%=ChuyenNgu.get("Trả sản phẩm",nnId) %></option>
			                                    			<option value="2"><%=ChuyenNgu.get("Trả chiết khấu",nnId) %></option>
			                                    		<%} else { if (tkmDetai.getLoaitra().equals("2")){  %>
			                                    			<option value="2" selected="selected"><%=ChuyenNgu.get("Trả chiết khấu",nnId) %></option>
			                                    			<option value="3"><%=ChuyenNgu.get("Trả sản phẩm",nnId) %></option>
			                                    			<option value="1"><%=ChuyenNgu.get("Trả tiền",nnId) %></option>
			                                    		<%}else { %>
			                                    			<option value="3" selected="selected"><%=ChuyenNgu.get("Trả sản phẩm",nnId) %></option>
			                                    			<option value="1"><%=ChuyenNgu.get("Trả tiền",nnId) %></option>
			                                    			<option value="2"><%=ChuyenNgu.get("Trả chiết khấu",nnId) %></option>
			                                    		 <%} } %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Hình thức",nnId) %></td>
			                                    <td valign="top" align="left">
			                                    	<select name = "trakhuyenmai<%= pos %>.hinhthuc" id = "trakhuyenmai<%= pos %>.hinhthuc" >
			                                    	<% if(tkmDetai.getHinhthuc().equals("1")){ %>
			                                    		<option value="2"><%=ChuyenNgu.get("Bất kỳ trong",nnId) %></option>
			                                    		<option value="1" selected="selected" ><%=ChuyenNgu.get("Bắt buộc nhập số lượng",nnId) %></option>
			                                    	<%} else { %>
			                                    		<option value="2" selected="selected"><%=ChuyenNgu.get("Bất kỳ trong",nnId) %></option>
			                                    		<option value="1"><%=ChuyenNgu.get("Bắt buộc nhập số lượng",nnId) %></option>
			                                    	<%} %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Tổng lượng / Tổng tiền",nnId) %></td>
			                                    <td valign="top" align="left">
			                                    	<input type="text" name="trakhuyenmai<%= pos %>.sotong" id="trakhuyenmai<%= pos %>.sotong" 
			                                    			value="<%= tkmDetai.getSotong() %>" style="text-align: right;"/>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %></td>
			                                    <td valign="top" align="left">		                                    	
			                                    	<select name="trakhuyenmai<%= pos %>.nhomsanpham" id="trakhuyenmai<%= pos %>.nhomsanpham" onChange = "ajaxOption2(this.id, this.value, <%= pos %>)">
			                                    		<option value=""> </option>
			                                    		<% if(nhomspRs != null)
			                                    		{ 
			                                    			nhomspRs.beforeFirst();
			                                    			while(nhomspRs.next()){ if(tkmDetai.getNhomspId().equals(nhomspRs.getString("nspId"))){ %>
			                                    				<option value="<%= nhomspRs.getString("nspId") %>"><%= nhomspRs.getString("nspTen") %></option>
			                                    		<% } else { %> 
			                                    				<option value="<%= nhomspRs.getString("nspId") %>"><%= nhomspRs.getString("nspTen") %></option>
			                                    		 <%} } } %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr style="display: none">
			                                	<td valign="top" align="left" colspan="2">
			                                	<% if(tkm.isTheothung() ) { %>
			                                		<input type="checkbox" name="trakhuyenmai<%= pos %>.tinhtheothung" id="trakhuyenmai<%= pos %>.tinhtheothung"  value='1' checked="checked" > <span style="font-style: italic;">Số lượng tính theo Kg</span>
			                                	<%} else { %>
			                                		<input type="checkbox" name="trakhuyenmai<%= pos %>.tinhtheothung" id="trakhuyenmai<%= pos %>.tinhtheothung"  value='1' > <span style="font-style: italic;">Số lượng tính theo Kg</span>
			                                	<%} %>
			                                	</td>
			                                </tr>
			                                <tr>
			                                	<td colspan="2">
			                                		<table align="left" cellpadding="2px" cellspacing="2px">
				                                		<tr>
				                                			<th width="100px" align="center"><%=ChuyenNgu.get("Mã sản phẩm",nnId) %></th>
				                                			<th width="250px" align="left"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %></th>
				                                			<th width="60px" align="left"><%=ChuyenNgu.get("Số lượng",nnId) %></th>
				                                		</tr>
				                                	</table>
				                                	<div id="trakhuyenmai<%= pos %>.tbsanpham" style="width: 100%; max-height: 150px; overflow: auto">
				                                	<table align="left" cellpadding="2px" cellspacing="2px">
				                                	<% 
				                                	int count = 0;
				                                	while(count < spTraList.size())
				                                	{
				                                		ISanpham sp = spTraList.get(count);
				                                		%>
				                                		<tr>
				                                			<td width="100px" align="center">
				                                				<input type="text" value="<%= sp.getMasanpham() %>" style="width: 100px" name="trakhuyenmai<%= pos %>.masanpham" 
				                                						onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
				                                			</td>
				                                			<td width="250px" align="left">
				                                				<input type="text" value="<%= sp.getTensanpham() %>" name="trakhuyenmai<%= pos %>.tensanpham" style="width: 250px" readonly="readonly">
				                                			</td>
				                                			<td width="60px" align="center">
				                                				<input type="text" value="<%= sp.getSoluong() %>" name="trakhuyenmai<%= pos %>.soluong" style="width: 60px; text-align: right;">
				                                			</td>
				                                		</tr>
				                                	<% count++; } %>
				                                	<% for(int sopt=count; sopt < 50; sopt++){ %>
				                                		<tr>
				                                			<td width="100px" align="center">
				                                				<input type="text" value="" style="width: 100px" name="trakhuyenmai<%= pos %>.masanpham" 
				                                						onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
				                                			</td>
				                                			<td width="250px" align="left">
				                                				<input type="text" value="" name="trakhuyenmai<%= pos %>.tensanpham" style="width: 250px" readonly="readonly">
				                                			</td>
				                                			<td width="60px" align="center">
				                                				<input type="text" value="" name="trakhuyenmai<%= pos %>.soluong" style="width: 60px; text-align: right;">
				                                			</td>
				                                		</tr>
				                                	<%} %>
			                                		</table>
			                                		</div>
			                                	</td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left" colspan="2">
			        								<a class="button" href="javascript:submitform();">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Nhập lại",nnId) %>  </a>
			        								&nbsp;&nbsp;&nbsp;
			        								<a class="button" href="javascript:submitform3(<%= pos %>);">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Lưu trả khuyến mãi",nnId) %>  </a>
			                                	</td>
			                                </tr>
			                            </table>
									</div>
				                </div>
		                        
	                        </TD>
	                        <TD align="left"><input type="text" name="trakmDiengiai" id="trakmDiengiai<%= pos %>" size="80" value="<%= tkm.getDiengiai() %>" readonly></TD>
	                        <TD align="center"><input type="text" name="trakmTongluong" id="trakmTongluong<%= pos %>" size="6" value="<%= tkm.getTongluong() %>" style="text-align:right" readonly></TD>							           
	                        <TD align="center"><input type="text" name="trakmTongtien" id="trakmTongtien<%= pos %>"  size="6" value="<%= tkm.getTongtien() %>" style="text-align:right" readonly></TD>
	                         <TD align="center"><input type="text" name="trakmChietkhau" id="trakmChietkhau<%= pos %>"  size="6" value="<%= tkm.getChietkhau() %>" style="text-align:right" readonly></TD>
	                        <TD align="center">
	                        	<select name="trakmPheptoan">
	                            <% if(tkm.getPheptoan().equals("1")){ %>
	                            	<option value="1" selected="selected"><%=ChuyenNgu.get("And",nnId) %></option>
	                                <option value="2"><%=ChuyenNgu.get("Or",nnId) %></option>  
	                                <option value="" ></option>   
	                            <%} else { if(tkm.getPheptoan().equals("2")){ %>
	                            	<option value="1"><%=ChuyenNgu.get("And",nnId) %></option>
	                                <option value="2" selected="selected"><%=ChuyenNgu.get("Or",nnId) %></option>  
	                                <option value="" ></option>  
	                            <% } else { %>
	                            	<option value="" selected> </option>
	                            	<option value="1"><%=ChuyenNgu.get("And",nnId) %></option>
	                                <option value="2"><%=ChuyenNgu.get("Or",nnId) %></option>  
	                             <%} } %>   
	                            </select>
	                            <input type="hidden" name="trakmThutu" size="6" value="<%= pos %>" style="text-align:right">
	                        </TD>
	                    </TR>
		                   
                    	<% pos++; } } %>
                   	<% for(int j = pos; j < 10; j++){ %>
                   		<TR class='tbdarkrow'>
	                        <TD align="center">
	                        	<input type="text" id='trakmId<%= j %>' name="trakmId" size="10" value="" onkeyup="ajax_showOptions(this,'trakhuyenmai',event)" AUTOCOMPLETE="off" style="width: 70%">
		                        
		                        <a class="trakhuyenmai<%= j %>" href="#" onclick ="AjaxTraKM(<%=j%>)">
		                        		<img style="top: -4px;" src="../images/vitriluu.png" title="Tạo mới trả khuyến mãi"></a>
				                <div style='display:none'>
			                        <div id='trakhuyenmai<%= j %>' style='padding:0px 5px; background:#fff;'>
			                        	<h4 align="left"><%=ChuyenNgu.get("Tạo mới trả khuyến mãi",nnId) %></h4>
										<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
			                            	<tr>
			                                	<td width="40%" valign="top" align="left"><%=ChuyenNgu.get("Diễn giải",nnId) %></td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="trakhuyenmai<%= j %>.diengiai" id="trakhuyenmai<%= j %>.diengiai" value="" />
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Loại trả",nnId) %></td>
			                                    <td valign="top" align="left">
			                                    	<select name = "trakhuyenmai<%= j %>.loaitra" id = "trakhuyenmai<%= j %>.loaitra" >
			                                    		<option value="3"><%=ChuyenNgu.get("Trả sản phẩm",nnId) %></option>
			                                    		<option value="1"><%=ChuyenNgu.get("Trả tiền",nnId) %></option>
			                                    		<option value="2"><%=ChuyenNgu.get("Trả chiết khấu",nnId) %></option>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Hình thức",nnId) %></td>
			                                    <td valign="top" align="left">
			                                    	<select name="trakhuyenmai<%= j %>.hinhthuc" id = "trakhuyenmai<%= j %>.hinhthuc">
			                                    		<option value="2"><%=ChuyenNgu.get("Bất kỳ trong",nnId) %></option>
			                                    		<option value="1"><%=ChuyenNgu.get("Bắt buộc nhập số lượng",nnId) %></option>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Tổng lượng / Tổng tiền",nnId) %></td>
			                                    <td valign="top" align="left">
			                                    	<input type="text" name="trakhuyenmai<%= j %>.sotong" id="trakhuyenmai<%= j %>.sotong" value="" style="text-align: right;"/>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %></td>
			                                    <td valign="top" align="left">		                                    	
			                                    	<select name="trakhuyenmai<%= j %>.nhomsanpham" id="trakhuyenmai<%= j %>.nhomsanpham" onChange = "ajaxOption2(this.id, this.value, <%= j %>)">
			                                    		<option value=""> </option>
				                                    		<% if(nhomspRs != null)
				                                    		{ 
				                                    			nhomspRs.beforeFirst();
				                                    			while(nhomspRs.next()){ %>
				                                    				<option value="<%= nhomspRs.getString("nspId") %>"><%= nhomspRs.getString("nspTen") %></option>
				                                    		<%} } %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr style="display: none">
			                                	<td valign="top" align="left" colspan="2">
			                                		<input type="checkbox" name="trakhuyenmai<%= j %>.tinhtheothung" id="trakhuyenmai<%= j %>.tinhtheothung"  value='1' > <span style="font-style: italic;">Số lượng tính theo Kg</span> 
			                                	</td>
			                                </tr>
			                                <tr>
			                                	<td colspan="2">
			                                		<table align="left" cellpadding="2px" cellspacing="2px">
				                                		<tr>
				                                			<th width="100px" align="center"><%=ChuyenNgu.get("Mã sản phẩm",nnId) %></th>
				                                			<th width="250px" align="left"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %></th>
				                                			<th width="60px" align="left"><%=ChuyenNgu.get("Số lượng",nnId) %></th>
				                                		</tr>
				                                	</table>
				                                	<div id="trakhuyenmai<%= j %>.tbsanpham" style="width: 100%; max-height: 150px; overflow: auto">
				                                	<table align="left" cellpadding="2px" cellspacing="2px">
				                                	<% for(int k=0; k < 50; k++){ %>
				                                		<tr>
				                                			<td width="100px" align="center">
				                                				<input type="text" value="" style="width: 100px" name="trakhuyenmai<%= j %>.masanpham" 
				                                						onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
				                                			</td>
				                                			<td width="250px" align="left">
				                                				<input type="text" value="" name="trakhuyenmai<%= j %>.tensanpham" style="width: 250px" readonly="readonly">
				                                			</td>
				                                			<td width="60px" align="center">
				                                				<input type="text" value="" name="trakhuyenmai<%= j %>.soluong" style="width: 60px; text-align: right;">
				                                			</td>
				                                		</tr>
				                                	<%} %>
			                                		</table>
			                                		</div>
			                                	</td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left" colspan="2">
			        								<a class="button" href="javascript:submitform();">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Nhập lại",nnId) %>  </a>
			        								&nbsp;&nbsp;&nbsp;
			        								<a class="button" href="javascript:submitform3(<%= j %>);">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Lưu trả khuyến mãi",nnId) %>  </a>
			                                	</td>
			                                </tr>
			                            </table>
									</div>
				                </div>
		                        
	                        </TD>
	                        <TD align="left"><input type="text" name="trakmDiengiai" id="trakmDiengiai<%= j %>" size="80" value="" readonly></TD>
	                        <TD align="center"><input type="text" name="trakmTongluong" id="trakmTongluong<%= j %>" size="6" value="" style="text-align:right" readonly></TD>							           
	                        <TD align="center"><input type="text" name="trakmTongtien" id="trakmTongtien<%= j %>"  size="6" value="" style="text-align:right" readonly></TD>
	                        <TD align="center"><input type="text" name="trakmChietkhau" id="trakmChietkhau<%= j %>"  size="6" value="" style="text-align:right" readonly></TD>
	                        <TD align="center">
	                        	<select name="trakmPheptoan">
	                            	<option value="" selected></option>
	                            	<option value="1"><%=ChuyenNgu.get("And",nnId) %></option>
	                                <option value="2"><%=ChuyenNgu.get("Or",nnId) %></option>     
	                            </select>
	                            <input type="hidden" name="trakmThutu" size="6" value="<%= j %>" style="text-align:right">
	                        </TD>
	                    </TR>
                   	<%} %>
                  <TR>
                      <TD align="center" colspan="11" class="tbfooter">&nbsp;</TD>
                  </TR>
              </TABLE>   
            </div> 
            <%} %>
            <h1 style="font-size:1.8em"><a href="#"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></a></h1>
            <div style="height:auto">
            <!-- <TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px"> -->
            <table class="chitieutable">
                <TR class="plainlabel" valign="bottom">
                <th colspan="3">
                   
                   <fieldset style="width: 30%; float: left;"> 
			       <legend><%=ChuyenNgu.get("Kênh bán hàng",nnId) %> &nbsp;</legend> 
			       <select name="kbhId" >
			       <!-- <option value="">Chọn hết</option> -->
                        <%
                        kbhRS.beforeFirst();
                        if(kbhRS != null) {
                         while(kbhRS.next()) 
                         {
                           if(ctkmBean.getKbhId().indexOf(kbhRS.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= kbhRS.getString("pk_seq") %>" selected style="padding: 2px" ><%= kbhRS.getString("ten") %></option>
                         <%}else { %>
                         	<option value="<%= kbhRS.getString("pk_seq") %>" style="padding: 2px"><%= kbhRS.getString("ten") %></option>
                         <%} }}%>        	
                    </select>
                    </fieldset>
                    
			       <fieldset style="width: 30%; float: left;">
			       <legend>Vùng &nbsp;</legend> 
			       <select name="vung" multiple="multiple">
			       <option value=""><%=ChuyenNgu.get("Chọn hết",nnId) %></option>
                        <% if(vungs != null) {
                         while(vungs.next()) 
                         {
                           if(ctkmBean.getVungId().indexOf(vungs.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= vungs.getString("pk_seq") %>" selected style="padding: 2px" ><%= vungs.getString("ten") %></option>
                         <%}else { %>
                         	<option value="<%=vungs.getString("pk_seq") %>" style="padding: 2px"><%= vungs.getString("ten") %></option>
                         <%} }}%>        	
                    </select>
                    </fieldset>
                    
                    <fieldset style="width: 30%; float: left;"> 
					<legend><%=ChuyenNgu.get("Khu vực",nnId) %>&nbsp;</legend>
					<select name="khuvuc" multiple="multiple">
					<option value=""><%=ChuyenNgu.get("Chọn hết",nnId) %></option>
			            <% if(khuvucs != null) {
			            	while(khuvucs.next())
	                          {
	                            if(ctkmBean.getKhuvucId().indexOf(khuvucs.getString("pk_seq")) >= 0 )
	                            { %>
	                              <option value="<%=khuvucs.getString("pk_seq") %>" selected style="padding: 2px"><%=khuvucs.getString("ten") %></option> 
	                          <%}else { %>
	                          	<option value="<%=khuvucs.getString("pk_seq") %>" style="padding: 2px"><%=khuvucs.getString("ten") %></option>
	                          <%}}}%>
                    </select>
                    </fieldset>
			   </th>
				</TR>
                <tr class="plainlabel" style="padding: 5px">
                	<th colspan="3">
                		<a class="button" href="javascript:seach();">
        					<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Hiển thị nhà phân phối theo điều kiện",nnId) %></a>
                	</th>
                </tr>
                    <TR class="tbheader">
                        <TH align="center" width="10%"><%=ChuyenNgu.get("Mã",nnId) %></TH>
                        <TH align="left" width="50%"> <%=ChuyenNgu.get("Tên",nnId) %> </TH>
                        <TH align="center" width="10%"> <%=ChuyenNgu.get("Chọn tất cả",nnId) %> <input type ="checkbox" name ="all" onclick ="All()"></TH>
                    </TR>
					<%
					
					int k=0;
                    if(DsnppIds != null)
                    {
                    	while(DsnppIds.next())
                    	{
                    		if(k % 2 == 0){
                    			%>
                    			<TR class='tbdarkrow'>
	                    	<%}else{ %> 
	                    		 <TR class='tblightrow'>
	                    	<% } %>
	                    	<TD align="center"><%= DsnppIds.getString("nppMa") %></TD>
		                    <TD align="left"><%=DsnppIds.getString("ten") %></TD>
		                    <% if(nppIds.contains(DsnppIds.getString("pk_seq"))) {%>
		                    	<TD align="center"><input type ="checkbox" name ="npp" value ="<%=DsnppIds.getString("pk_seq")%>" checked="checked"></TD>
		                    <%} else {%>
		                      	<TD align="center"><input type ="checkbox" name ="npp" value ="<%=DsnppIds.getString("pk_seq")%>"></TD>
		                  	<%} k++; } DsnppIds.close(); }
					
					if(Dsnpp != null){
					k=0;
					try{
					while(Dsnpp.next()) {
					if(k%2==0){
					%>
	                   	<TR class='tbdarkrow'>
	               <%}else{ %>
	                    <TR class='tblightrow'>
	               <%} %>
                        <TD align="center"><%= Dsnpp.getString("nppMa") %></TD>
	                    <TD align="left"><%=Dsnpp.getString("ten") %></TD>
	                    <% if(nppIds.contains(Dsnpp.getString("pk_seq"))) {%>
	                    	<TD align="center"><input type ="checkbox" name ="npp" value ="<%=Dsnpp.getString("pk_seq")%>" checked="checked"></TD>
	                    <%} else {%>
	                      	<TD align="center"><input type ="checkbox" name ="npp" value ="<%=Dsnpp.getString("pk_seq")%>"></TD>
	                  	<%} %>
                   </TR>
	                <% k++;} Dsnpp.close(); }catch(Exception ex){} }%>
                    <TR>
                        <TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
                    </TR>
                    </TABLE>
                    </div>
                    
            </div>  
      </fieldset>
  </div>    
</div>
</form>
<script type="text/javascript">
	replaces();
</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>