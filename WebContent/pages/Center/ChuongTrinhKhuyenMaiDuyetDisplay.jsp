<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.ctkhuyenmai.*" %>
<%@ page  import = "geso.dms.center.beans.ctkhuyenmai.imp.*" %>
<%@ page  import = "geso.dms.center.beans.dieukienkhuyenmai.ISanpham" %>
<%@ page  import = "geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% ICtkhuyenmai ctkmBean = (ICtkhuyenmai)session.getAttribute("ctkmBean"); %>
<% List<IDieukienkm> dkkmList = ctkmBean.getDkkmList(); %>
<% List<ITrakm> trakmList = ctkmBean.getTrakmList(); %>
<% ResultSet nhomspRs = ctkmBean.getNhomspRs(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% ResultSet khoIds = (ResultSet)ctkmBean.getkhoIds();%>
<% ResultSet Dsnpp = (ResultSet)ctkmBean.getDsnpp();%>
<% ResultSet DsnppIds = (ResultSet)ctkmBean.getDsnppSelected(); %>
<% Hashtable<Integer, String> nppIds = (Hashtable<Integer, String>)ctkmBean.getnpp(); %>
<% ResultSet Nhomkhnpp = (ResultSet)ctkmBean.getNhomkhnpp();%>
<% ResultSet vungs = (ResultSet)ctkmBean.getVungs();%>
<% ResultSet khuvucs = (ResultSet)ctkmBean.getKhuvuc();%>
<% ResultSet kbhRS = (ResultSet)ctkmBean.getKbhRs(); %>
<% ResultSet loaikhRS = (ResultSet)ctkmBean.getLoaikhRs(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>DDT - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
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
            
            $(".dieukienkhuyenmai5").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai0"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
           
            $(".dieukienkhuyenmai6").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai1"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".dieukienkhuyenmai7").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai2"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".dieukienkhuyenmai8").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai3"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                return false;
            });
            
            $(".dieukienkhuyenmai9").colorbox({width:"46%", inline:true, href:"#dieukienkhuyenmai4"});
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
		
		function saveform()
		{

			
			
			
			
		
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
			
		    
		}
		
		function checkDieukienkm()
		{
			var dkkmIds = document.getElementsByName("dkkmId");
			var dkkmPheptoan = document.getElementsByName("dkkmPheptoan");
			
			//dieu kien dau tien ko can nhap
			for(j = 0; j < dkkmIds.length; j++)
			{
				if(dkkmIds.item(j).value != "")
				{
					if(j > 0 && dkkmPheptoan.item(j).value == "")
						return false;
				}
			}
			return true;
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
		
		function checkTrakm()
		{
			var trakmIds = document.getElementsByName("trakmId");
			for(k =0; k < trakmIds.length; k++)
			{
				if(trakmIds.item(k) != "")
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
			

		}
		
		 function Show()
		 {
		 	var element = document.getElementById('ngansachct');
		 	
		 	var apphanbo = document.getElementById('apphanbo');
		 	if(apphanbo.checked)
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
				alert('B???n ph???i nh???p di???n gi???i cho ??i???u ki???n khuy???n m??i');
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
						alert('B???n ph???i nh???p t???ng l?????ng cho ??i???u ki???n khuy???n m??i');
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
						alert('B???n ph???i nh???p t???ng ti???n cho ??i???u ki???n khuy???n m??i');
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
						alert('B???n ph???i nh???p s??? l?????ng cho s???n ph???m ' + masanpham.item(i).value);
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
				alert('B???n ph???i ch???n s???n ph???m cho ??i???u ki???n khuy???n m??i');
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
					  alert('Kh??ng th??? t???o m???i ??i???u ki???n khuy???n m??i, vui l??ng ki???m tra l???i c??c th??ng tin. \n' + xmlhttp.responseText);
				  }
				  else
				  {
					  alert('T???o m???i ??i???u ki???n khuy???n m??i th??nh c??ng.');
					  
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
				alert('B???n ph???i nh???p di???n gi???i cho tr??? khuy???n m??i');
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
					alert('B???n ph???i nh???p t???ng ti???n cho tr??? khuy???n m??i');
					return;
				}
			}
			else 
			{
				if(loaitra == 2 ) //chiet khau
				{
					if(hinhthuc == 2 && sotong == '')
					{
						alert('B???n ph???i nh???p ph???n tr??m chi???t kh???u cho tr??? khuy???n m??i');
						return;
					}
					else
					{
						if(sotong != '')
						{
							if(parseInt(sotong) > 100)
							{
								alert('Ph???n tr??m chi???t kh???u cho tr??? khuy???n m??i kh??ng ???????c v?????t qu?? 100%');
								return;
							}
						}
					}
				}
				else //san pham
				{
					if(hinhthuc == 2 && sotong == '')
					{
						alert('B???n ph???i nh???p t???ng l?????ng cho tr??? khuy???n m??i');
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
							alert('B???n ph???i nh???p s??? l?????ng cho s???n ph???m ' + masanpham.item(i).value);
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
					alert('B???n ph???i ch???n s???n ph???m cho tr??? khuy???n m??i');
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
					  alert('Kh??ng th??? t???o m???i tr??? khuy???n m??i, vui l??ng th??? l???i sau.');
				  }
				  else
				  {
					  alert('T???o m???i tr??? khuy???n m??i th??nh c??ng.');
					  
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

<form name="ctkmForm" method="post" action="../../CtkhuyenmaiDuyetUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%= ctkmBean.getId() %>'>
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
        	Qu???n l?? khuy???n m??i > Ch????ng tr??nh khuy???n m??i > C???p nh???t
        </div>
        <div align="right" style="padding:5px;" class="tbnavigation">
        	Ch??o m???ng b???n <%= userTen %> &nbsp; &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
    
    </div>
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> B??o l???i nh???p li???u</legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= ctkmBean.getMessage() %></textarea>
		         <% ctkmBean.setMessage(""); %>
    	</fieldset>
  	</div>
    <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
    <fieldset>
    	<legend class="legendtitle">Ch????ng tr??nh khuy???n m???i</legend>
        <div style="width:100%; float:none" align="left">
               <TABLE width="100%" cellpadding="4" cellspacing="0">								
                    <TR>
                        <TD width="130px" class="plainlabel">Scheme </TD>
                        <TD width="250px" class="plainlabel"><input type="text" name="scheme" id="scheme" size="30" value="<%= ctkmBean.getScheme() %>"></TD>
                    
                        <TD width="130px" class="plainlabel" valign="top">Di???n gi???i </TD>
                        <TD class="plainlabel" valign="top">
                        	<input type="text" name="diengiai" id="diengiai" style="width:400px" value="<%= ctkmBean.getDiengiai() %>" >
                        </TD>
                    </TR>               
                    <TR>
                          <TD class="plainlabel" >Lo???i ch????ng tr??nh </TD>
                            <% if(!ctkmBean.getType().equals("4")){ %>
                        	<TD class="plainlabel" >
                        <%} else {%>
                       	 <TD class="plainlabel" colspan = "1" >
                        <%} %>
                        
                            <select name="loaiCt" id="loaiCt" onchange="tichluy()">
                            <% if(ctkmBean.getType().equals("1")){ %>
                                <option value="1" selected>B??nh th?????ng</option>
                                <option value="2">On top</option>
                                <option value="3">T??ch l??y</option>
                                <option value="4">?????c bi???t</option>
                                <option value="">&nbsp;</option>
                            <%} else if(ctkmBean.getType().equals("2")){ %>
                            	<option value="2" selected>On top</option>
                            	<option value="1">B??nh th?????ng</option> 
                            	 <option value="3">T??ch l??y</option>   
                            	   <option value="4">?????c bi???t</option>                          
                                <option value="">&nbsp;</option>
                            <%}  else  if(ctkmBean.getType().equals("3")) { %>
                            	<option value="" >&nbsp;</option>
                            	<option value="1">B??nh th?????ng</option> 
                            	<option value="2">On top</option>   
                            	   <option value="4">?????c bi???t</option>
                            	 <option value="3" selected>T??ch l??y</option>   
                            	  <%}  else  if(ctkmBean.getType().equals("4")) { %>
                            	<option value="" >&nbsp;</option>
                            	<option value="1">B??nh th?????ng</option> 
                            	<option value="2">On top</option>   
                            	   <option value="4" selected >?????c bi???t</option>
                            	 <option value="3" >T??ch l??y</option>                                                                                                         
                            <% }else{ %>
                            	<option value="" selected>&nbsp;</option>
                            	<option value="1">B??nh th?????ng</option> 
                            	<option value="2">On top</option>   
                            	 <option value="3">T??ch l??y</option>
                            	    <option value="4">?????c bi???t</option>           
                            <%} %>
                            </select>
                        </TD>
                        
                       <%--   <TD width="130px" class="plainlabel" valign="top">M?? IO (SAP) </TD>
                        <TD class="plainlabel" valign="top" colspan="2">
                        	<input type="text" name="io" id="id" style="width:400px" value="<%= ctkmBean.getIo() %>" >
                        </TD> --%>
                             <% if(1==1){ %>
                       <TD width="130px" class="plainlabel" valign="top">Scheme ERP </TD>
                        <TD class="plainlabel" valign="top" colspan="2">
                        	<input type="text" maxlength="" name="schemeErp" id="schemeErp" style="width:400px" value="<%= ctkmBean.getSchemeErp() %>" >
                        </TD> 
                        <%} %>
                    </TR>
                       <% if(!ctkmBean.getType().equals("4")){ %>
                    <tr>
                    	<td class="plainlabel">M???c ph??n b???</td>
                    	<TD class="plainlabel" colspan="5" >
                    		<select name="mucphanbo"  >
                            	<% if(ctkmBean.getMucphanbo().equals("0")) { %>
                            		<option value="0" selected="selected" >Nh?? ph??n ph???i</option>
                            	<% } else { %>
                            		<option value="0" >Nh?? ph??n ph???i</option>
                            	<% } %>
                            	<% if(ctkmBean.getMucphanbo().equals("2")) { %>
                            		<option value="2" selected="selected" >Kh??ch h??ng</option>
                            	<% } else { %>
                            		<option value="2" >Kh??ch h??ng</option>
                            	<% } %>
                            	
                            </select>
                        </TD>
                    </tr>
                          <%} %>
                     <%-- <% if(ctkmBean.getkhoId().equals("100001")) {%>
                     <TR>
	               <TD class="plainlabel" >Th???i gian NPP ?????t h??ng : &nbsp;&nbsp;&nbsp;&nbsp;T??? ng??y</TD>
	                        <TD class="plainlabel" colspan="1" >
	                            <input type="text" size="10" class="days" 
	                                   id="tungay_dathang" name="tungay_dathang" value="<%= ctkmBean.getTuNgay_DatHang() %>" maxlength="10" /> </TD>
	                                   <TD  class="plainlabel"   > 
	                              ?????n ng??y   &nbsp;&nbsp;</TD>
	                             <TD  class="plainlabel" colspan="1">  <input type="text" size="10" class="days" 
	                                    id="denngay_dathang" name="denngay_dathang" value="<%= ctkmBean.getDenNgay_DatHang() %>" maxlength="10" />
	                         
	                        </TD>
                       	</TR>
                    <%} %> --%>
                    
                    
                     <% if(ctkmBean.getType().equals("3")) {%>
					  <TR>
	                        <TD class="plainlabel" >Ng??y ????ng k??: &nbsp;&nbsp;&nbsp;&nbsp;T??? ng??y</TD>
	                        <TD class="plainlabel" colspan="1" >
	                            <input type="text" size="10" class="days" 
	                                   id="tungay" name="tungay" value="<%= ctkmBean.getTungay() %>" maxlength="10" />
	                          
	                             </TD>
	                               <TD class="plainlabel" >  ?????n ng??y  </TD> 
	                               <TD  colspan="1" class="plainlabel"> <input type="text" size="10" class="days" 
	                                    id="denngay" name="denngay" value="<%= ctkmBean.getDenngay() %>" maxlength="10" /> </TD>
	                         
	                      
                       	</TR>
	                    <TR>
	                        <TD class="plainlabel" >Ng??y t??nh doanh s???:&nbsp;&nbsp;&nbsp;&nbsp;T??? ng??y</TD>
	                        <TD class="plainlabel">
	                            <input type="text" size="10" class="days" 
	                                   id="ngayds" name="ngayds" value="<%= ctkmBean.getngayds() %>" maxlength="10" />   </TD>
                             				<TD class="plainlabel" > ?????n ng??y   </TD> 
                             <TD class="plainlabel" > 
                             <input type="text" size="10" class="days" id="ngayktds" name="ngayktds" value="<%= ctkmBean.getngayktds() %>" maxlength="10" />
	                         
	                        </TD>
	                    
	                       
                       	</TR>
                       	<TR>
                       	 <TD class="plainlabel" >Ph???n tr??m t???i ??a</TD>
	                        <TD class="plainlabel" colspan="5">
	                            <input type="text" style="text-align: right;"
	                                   id="phantramtoida" name="phantramtoida" value="<%= ctkmBean.getPTToida() %>" maxlength="10" /> %
	                        </TD>
                       	</TR>
                   
                   <% } else {%>                
                    <TR>
                        <TD class="plainlabel" >T??? ng??y </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days" 
                                   id="tungay" name="tungay" value="<%= ctkmBean.getTungay() %>" maxlength="10" />
                        </TD>
                    
                        <TD class="plainlabel" >?????n ng??y </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days"
                                    id="denngay" name="denngay" value="<%= ctkmBean.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <%} %>
                    
                    <TR>
                        <TD class="plainlabel" >Kho </TD>
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
                        <TD class="plainlabel" >Nh??m kh??ch h??ng </TD>
                        <TD class="plainlabel">
                            <select name="nhomkhnpp">
                            <option value=""> </option>
                            <% while(Nhomkhnpp.next()) 
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
                       <% if(!ctkmBean.getType().equals("4")){ %>
                     <TR>
                    	<TD class="plainlabel" >Lo???i kh??ch h??ng </TD>
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
		                 <TD class="plainlabel" >??p d???ng cho</TD>
                         <TD class="plainlabel" >
                            <select name="apdungchoId" id="apdungchoId"  >
                            	<% if( ctkmBean.getApdungcho().equals("0") ) { %>
					        		<option value="0" selected="selected" >Sell In & Sell Out</option>
					        	<% } else { %>
					        		<option value="0">Sell In & Sell Out</option>
					        	<% } %>
		                        <% if( ctkmBean.getApdungcho().equals("1") ) { %>
					        		<option value="1" selected="selected" >Sell In</option>
					        	<% } else { %>
					        		<option value="1">Sell In</option>
					        	<% } %>
		                        <% if( ctkmBean.getApdungcho().equals("2") ) { %>
					        		<option value="2" selected="selected" >Sell Out</option>
					        	<% } else { %>
					        		<option value="2">Sell Out</option>
					        	<% } %>
		                    </select>
		                 </TD>
		                 <%-- <TD class="plainlabel" >K??nh b??n h??ng </TD>
                         <TD class="plainlabel" width="230px" >
                            <select name="kenhbanhangId" id="kenhbanhangId" onchange="submitform();"   >
					        	<option value=""></option>
		                        <% if(kbhRS != null) {
		                         while(kbhRS.next()) 
		                         {
		                           if(ctkmBean.getKbhId().equals(kbhRS.getString("pk_seq"))){ %>
		                             <option value="<%= kbhRS.getString("pk_seq") %>" selected style="padding: 2px" ><%= kbhRS.getString("ten") %></option>
		                         <%}else { %>
		                         	<option value="<%= kbhRS.getString("pk_seq") %>" style="padding: 2px"><%= kbhRS.getString("ten") %></option>
		                         <%} }}%>        	
		                    </select>
		                 </TD> --%>
		                <%-- <% if(ctkmBean.getKbhIds().equals("100021")){ %> 
		                <TD class="plainlabel" >Ng??n s??ch k??? ho???ch </TD>
                        <TD class="plainlabel">
                        	<input type="text" name="ngansachkehoach" id="ngansachkehoach" size="30" style="text-align:right" value="0" onkeypress="return keypress(event);">
                        </TD>
                        <% } else { %>
                        <TD class="plainlabel" ></TD>
                        <TD class="plainlabel"></TD>
                        <% } %> --%>
                    </TR>
                     <TR>
                    <TD class="plainlabel" >K??nh b??n h??ng </TD>
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
                        <TD class="plainlabel" >Lo???i ng??n s??ch </TD>
                        <TD class="plainlabel" colspan="3" >
                            <% if(ctkmBean.getLoaiNganSach().equals("1")){ %>
                            	<input type="radio" name="loains" value = "1" checked = "checked" id="apphanbo" >??p ph??n b??? &nbsp;&nbsp;
                            	<input type="radio" name="loains" value = "0"  id="khonggh" >Kh??ng gi???i h???n ng??n s??ch
                            <%}else{ %>
                            	<input type="radio" name="loains" value = "1"  id="apphanbo" >??p ph??n b??? &nbsp;&nbsp;
                            	<input type="radio" name="loains" value= "0" checked="checked" id="khonggh" >Kh??ng gi???i h???n ng??n s??ch
                            <%} %>
                            
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <% if(ctkmBean.getNppTuchay().equals("1")) { %>
                            	<input type="checkbox" name="nppTuchay" value="1" checked="checked" > NPP t??? ch???y
                            <% } else { %>
                            	<input type="checkbox" name="nppTuchay" value="1" > NPP t??? ch???y
                            <% } %>
                        </TD>
                    </TR>
                    <TR id="ngansachct" style="display: none">
                        <TD class="plainlabel" >Ng??n s??ch </TD>
                        <TD class="plainlabel"><input type="text" name="ngansach" id="ngansach" size="30" style="text-align:right" value="0" onkeypress="return keypress(event);"></TD>
                    </TR>
                    
                    <TR>
                       <TD class="plainlabel"></TD>
                        <TD class="plainlabel">
                        	<%-- <% if (ctkmBean.getApdungchoDHLe().equals("1")){ %> 
                        		<input type="checkbox" name="ApDUNGCHODHLE" value="1" checked="checked"><i> ??p d???ng cho ????n h??ng l???</i>
                        	<% } else { %>
                        		<input type="checkbox" name="ApDUNGCHODHLE" value="1" ><i> ??p d???ng cho ????n h??ng l???</i>
                        	 <% }  %>  &nbsp;&nbsp;
                        	  --%>
                        	 <% if (ctkmBean.getPhanbotheoDH().equals("1")){ %> 
                        		<input type="checkbox" name="PHANBOTHEODH" value="1" checked="checked"><i> Ph??n b??? ng??n s??ch theo s??? l?????ng </i>
                        	<% } else { %>
                        		<input type="checkbox" name="PHANBOTHEODH" value="1" ><i> Ph??n b??? ng??n s??ch theo s??? l?????ng</i>
                        	 <% }  %>
                        </TD>
                        <TD class="plainlabel" colspan="3" >
                    </TR>
                          <%} %>
                </TABLE>       
         </div>
        
      <div id="accordion" style="width:100%; height:auto; float:none; font-size:1.0em" align="left">
          <% if(!ctkmBean.getType().equals("4")){ %>                            
       <h1 style="font-size:1.8em"><a href="#">Khai b??o ??i???u ki???n khuy???n m??i</a></h1>
			<div style="height:auto">
                <TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                        <TH align="center" width="10%"> M??</TH>
                        <TH align="left" width="50%"> Di???n gi???i </TH>
                        <TH align="center" width="10%"> T???ng l?????ng</TH>
                        <TH align="center" width="10%"> T???ng ti???n</TH>
                        <TH align="center" width="10%"> Ph??p to??n</TH>
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
	                        		onkeyup="ajax_showOptions(this,'dieukien',event)" AUTOCOMPLETE="off" style="width: 80%" >
		                        
		                        <a class="dieukienkhuyenmai<%= i %>" href="#">
		                        		<img style="top: -4px;" src="../images/vitriluu.png" title="T???o m???i ??i???u ki???n"></a>
				                <div style='display:none'>
			                        <div id='dieukienkhuyenmai<%= i %>' style='padding:0px 5px; background:#fff;'>
			                        	<h4 align="left">T???o m???i ??i???u ki???n khuy???n m??i</h4>
										<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
			                            	<tr>
			                                	<td width="40%" valign="top" align="left">Di???n gi???i</td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="dieukienkhuyenmai<%= i %>.diengiai" id="dieukienkhuyenmai<%= i %>.diengiai" value="<%= dkkmDetai.getDiengiai() %>" />
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">Lo???i ??i???u ki???n</td>
			                                    <td valign="top" align="left">
			                                    	<select name="dieukienkhuyenmai<%= i %>.loaidieukien" id = "dieukienkhuyenmai<%= i %>.loaidieukien">
			                                    		<% if(dkkmDetai.getLoaidieukien().equals("1")){ %>
				                                    		<option value="2">B???t k??? trong</option>
				                                    		<option value="1" selected="selected">B???t bu???c nh???p s??? l?????ng</option>
			                                    		<%} else { %>
			                                    			<option value="2" selected="selected">B???t k??? trong</option>
				                                    		<option value="1">B???t bu???c nh???p s??? l?????ng</option>
			                                    		<%} %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">H??nh th???c</td>
			                                    <td valign="top" align="left">
			                                    	<select name = "dieukienkhuyenmai<%= i %>.hinhthuc" id = "dieukienkhuyenmai<%= i %>.hinhthuc" >
			                                    	<% if(dkkmDetai.getHinhthuc().equals("2")){ %>
			                                    		<option value="1">Nh???p t???ng l?????ng</option>
			                                    		<option value="2" selected="selected">Nh???p t???ng ti???n</option>
			                                    	<%} else { %>
			                                    		<option value="1" selected="selected">Nh???p t???ng l?????ng</option>
			                                    		<option value="2">Nh???p t???ng ti???n</option>
			                                    	<%} %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">T???ng l?????ng / T???ng ti???n</td>
			                                    <td valign="top" align="left">
			                                    	<input type="text" name="dieukienkhuyenmai<%= i %>.sotong" id="dieukienkhuyenmai<%= i %>.sotong" 
			                                    			value="<%= dkkmDetai.getSotong() %>" style="text-align: right;"/>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">Nh??m s???n ph???m</td>
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
			                                		<input type="checkbox" name="dieukienkhuyenmai<%= i %>.tinhtheothung" id="dieukienkhuyenmai<%= i %>.tinhtheothung" value='1' checked="checked" > <span style="font-style: italic;">S??? l?????ng t??nh theo Kg</span> 
			                                	<%} else { %>
			                                		<input type="checkbox" name="dieukienkhuyenmai<%= i %>.tinhtheothung" id="dieukienkhuyenmai<%= i %>.tinhtheothung" value='1' > <span style="font-style: italic;">S??? l?????ng t??nh theo Kg</span>
			                                	<%} %>
			                                	</td>
			                                </tr>
			                                <tr>
			                                	<td colspan="2">
			                                		<table align="left" cellpadding="2px" cellspacing="2px">
				                                		<tr>
				                                			<th width="100px" align="center">M?? s???n ph???m</th>
				                                			<th width="250px" align="left">T??n s???n ph???m</th>
				                                			<th width="60px" align="left">S??? l?????ng</th>
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
			        								<img style="top: -4px;" src="../images/button.png" alt=""> Nh???p l???i  </a>
			        								&nbsp;&nbsp;&nbsp;
			        								<a class="button" href="javascript:submitform2(<%= i %>);">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> L??u ??i???u ki???n  </a>
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
	                            	<option value="1" selected="selected">And</option>
	                                <option value="2">Or</option>  
	                                <option value=""></option>   
	                            <%} else { if(dkkm.getPheptoan().trim().equals("2")){ %>
	                            	<option value="1">And</option>
	                                <option value="2" selected="selected">Or</option>  
	                                <option value=""></option>  
	                            <% } else { %>
	                            	<option value="" selected> </option> 
	                            	<option value="1">And</option>
	                                <option value="2">Or</option>  
	                             <%} } %>
	                            </select>
	                            <input type="hidden" name="dkkmThutu" size="6" value="<%= i %>" style="text-align:right">
	                        </TD>
	                    </TR>
		                   
                    	<% i++; } } %>
                    	<% for(int j = i; j < 5; j++){ %>
                    		<TR class='tbdarkrow'>
		                        <TD align="center">
		                        	<input type="text" id='dkkmId<%= j %>' name="dkkmId" size="10" value="" onkeyup="ajax_showOptions(this,'dieukien',event)" 
		                        		 AUTOCOMPLETE="off" style="width: 80%">
			                        
			                        <a class="dieukienkhuyenmai<%= j %>" href="#">
			                        		<img style="top: -4px;" src="../images/vitriluu.png" title="T???o m???i ??i???u ki???n"></a>
					                <div style='display:none'>
				                        <div id='dieukienkhuyenmai<%= j %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">T???o m???i ??i???u ki???n khuy???n m??i</h4>
											<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
				                            	<tr>
				                                	<td width="40%" valign="top" align="left">Di???n gi???i</td>
				                                    <td valign="top" align="left">
					                                    <input type="text" name="dieukienkhuyenmai<%= j %>.diengiai" id="dieukienkhuyenmai<%= j %>.diengiai" value="" />
				                                    </td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left">Lo???i ??i???u ki???n</td>
				                                    <td valign="top" align="left">
				                                    	<select name="dieukienkhuyenmai<%= j %>.loaidieukien" id = "dieukienkhuyenmai<%= j %>.loaidieukien">
				                                    		<option value="2">B???t k??? trong</option>
				                                    		<option value="1">B???t bu???c nh???p s??? l?????ng</option>
				                                    	</select>
				                                    </td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left">H??nh th???c</td>
				                                    <td valign="top" align="left">
				                                    	<select name = "dieukienkhuyenmai<%= j %>.hinhthuc" id = "dieukienkhuyenmai<%= j %>.hinhthuc" >
				                                    		<option value="1">Nh???p t???ng l?????ng</option>
				                                    		<option value="2">Nh???p t???ng ti???n</option>
				                                    	</select>
				                                    </td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left">T???ng l?????ng / T???ng ti???n</td>
				                                    <td valign="top" align="left">
				                                    	<input type="text" name="dieukienkhuyenmai<%= j %>.sotong" id="dieukienkhuyenmai<%= j %>.sotong" value="" style="text-align: right;"/>
				                                    </td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left">Nh??m s???n ph???m</td>
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
				                                		<input type="checkbox" name="dieukienkhuyenmai<%= j %>.tinhtheothung" id="dieukienkhuyenmai<%= j %>.tinhtheothung" value='1' > <span style="font-style: italic;">S??? l?????ng t??nh theo Kg</span>
				                                	</td>
				                                </tr>
				                                <tr>
				                                	<td colspan="2">
				                                		<table align="left" cellpadding="2px" cellspacing="2px">
					                                		<tr>
					                                			<th width="100px" align="center">M?? s???n ph???m</th>
					                                			<th width="250px" align="left">T??n s???n ph???m</th>
					                                			<th width="60px" align="left">S??? l?????ng</th>
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
				        								<img style="top: -4px;" src="../images/button.png" alt=""> Nh???p l???i  </a>
				        								&nbsp;&nbsp;&nbsp;
				        								<a class="button" href="javascript:submitform2(<%= j %>);">
				        								<img style="top: -4px;" src="../images/button.png" alt=""> L??u ??i???u ki???n  </a>
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
		                            	<option value="1">And</option>
		                                <option value="2">Or</option>     
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
			 <h1 style="font-size:1.8em"><a href="#">Tr??? khuy???n m??i</a></h1> 
             <div style="height:auto">
              <TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
              
                  <TR class="tbheader">
                      <TH align="center" width="10%"> M?? </TH>
                      <TH align="left" width="40%" > Di???n gi???i </TH>
                      <TH align="center" width="10%" > T???ng l?????ng</TH>
                      <TH align="center" width="10%"> T???ng ti???n</TH>
                      <TH align="center" width="10%"> Chi???t kh???u</TH>
                      <TH align="center" width="10%" > Ph??p to??n</TH>
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
	                        				onkeyup="ajax_showOptions(this,'trakhuyenmai',event)" AUTOCOMPLETE="off" style="width: 80%">
		                        
		                        <a class="trakhuyenmai<%= pos %>" href="#">
		                        		<img style="top: -4px;" src="../images/vitriluu.png" title="T???o m???i tr??? khuy???n m??i"></a>
				                <div style='display:none'>
			                        <div id='trakhuyenmai<%= pos %>' style='padding:0px 5px; background:#fff;'>
			                        	<h4 align="left">T???o m???i tr??? khuy???n m??i</h4>
										<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
			                            	<tr>
			                                	<td width="40%" valign="top" align="left">Di???n gi???i</td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="trakhuyenmai<%= pos %>.diengiai" id="trakhuyenmai<%= pos %>.diengiai" value="<%= tkmDetai.getDiengiai() %>" />
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">Lo???i tr???</td>
			                                    <td valign="top" align="left">
			                                    	<select name="trakhuyenmai<%= pos %>.loaitra" id = "trakhuyenmai<%= pos %>.loaitra">
			                                    		<% if(tkmDetai.getLoaitra().equals("1")){ %>
			                                    			<option value="1" selected="selected">Tr??? ti???n</option>
			                                    			<option value="3">Tr??? s???n ph???m</option>
			                                    			<option value="2">Tr??? chi???t kh???u</option>
			                                    		<%} else { if (tkmDetai.getLoaitra().equals("2")){  %>
			                                    			<option value="2" selected="selected">Tr??? chi???t kh???u</option>
			                                    			<option value="3">Tr??? s???n ph???m</option>
			                                    			<option value="1">Tr??? ti???n</option>
			                                    		<%}else { %>
			                                    			<option value="3" selected="selected">Tr??? s???n ph???m</option>
			                                    			<option value="1">Tr??? ti???n</option>
			                                    			<option value="2">Tr??? chi???t kh???u</option>
			                                    		 <%} } %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">H??nh th???c</td>
			                                    <td valign="top" align="left">
			                                    	<select name = "trakhuyenmai<%= pos %>.hinhthuc" id = "trakhuyenmai<%= pos %>.hinhthuc" >
			                                    	<% if(tkmDetai.getHinhthuc().equals("1")){ %>
			                                    		<option value="2">B???t k??? trong</option>
			                                    		<option value="1" selected="selected" >B???t bu???c nh???p s??? l?????ng</option>
			                                    	<%} else { %>
			                                    		<option value="2" selected="selected">B???t k??? trong</option>
			                                    		<option value="1">B???t bu???c nh???p s??? l?????ng</option>
			                                    	<%} %>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">T???ng l?????ng / T???ng ti???n</td>
			                                    <td valign="top" align="left">
			                                    	<input type="text" name="trakhuyenmai<%= pos %>.sotong" id="trakhuyenmai<%= pos %>.sotong" 
			                                    			value="<%= tkmDetai.getSotong() %>" style="text-align: right;"/>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">Nh??m s???n ph???m</td>
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
			                                		<input type="checkbox" name="trakhuyenmai<%= pos %>.tinhtheothung" id="trakhuyenmai<%= pos %>.tinhtheothung"  value='1' checked="checked" > <span style="font-style: italic;">S??? l?????ng t??nh theo Kg</span>
			                                	<%} else { %>
			                                		<input type="checkbox" name="trakhuyenmai<%= pos %>.tinhtheothung" id="trakhuyenmai<%= pos %>.tinhtheothung"  value='1' > <span style="font-style: italic;">S??? l?????ng t??nh theo Kg</span>
			                                	<%} %>
			                                	</td>
			                                </tr>
			                                <tr>
			                                	<td colspan="2">
			                                		<table align="left" cellpadding="2px" cellspacing="2px">
				                                		<tr>
				                                			<th width="100px" align="center">M?? s???n ph???m</th>
				                                			<th width="250px" align="left">T??n s???n ph???m</th>
				                                			<th width="60px" align="left">S??? l?????ng</th>
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
			        								<img style="top: -4px;" src="../images/button.png" alt=""> Nh???p l???i  </a>
			        								&nbsp;&nbsp;&nbsp;
			        								<a class="button" href="javascript:submitform3(<%= pos %>);">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> L??u tr??? khuy???n m??i  </a>
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
	                            	<option value="1" selected="selected">And</option>
	                                <option value="2">Or</option>  
	                                <option value="" ></option>   
	                            <%} else { if(tkm.getPheptoan().equals("2")){ %>
	                            	<option value="1">And</option>
	                                <option value="2" selected="selected">Or</option>  
	                                <option value="" ></option>  
	                            <% } else { %>
	                            	<option value="" selected> </option>
	                            	<option value="1">And</option>
	                                <option value="2">Or</option>  
	                             <%} } %>   
	                            </select>
	                            <input type="hidden" name="trakmThutu" size="6" value="<%= pos %>" style="text-align:right">
	                        </TD>
	                    </TR>
		                   
                    	<% pos++; } } %>
                   	<% for(int j = pos; j < 10; j++){ %>
                   		<TR class='tbdarkrow'>
	                        <TD align="center">
	                        	<input type="text" id='trakmId<%= j %>' name="trakmId" size="10" value="" onkeyup="ajax_showOptions(this,'trakhuyenmai',event)" AUTOCOMPLETE="off" style="width: 80%">
		                        
		                        <a class="trakhuyenmai<%= j %>" href="#">
		                        		<img style="top: -4px;" src="../images/vitriluu.png" title="T???o m???i tr??? khuy???n m??i"></a>
				                <div style='display:none'>
			                        <div id='trakhuyenmai<%= j %>' style='padding:0px 5px; background:#fff;'>
			                        	<h4 align="left">T???o m???i tr??? khuy???n m??i</h4>
										<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
			                            	<tr>
			                                	<td width="40%" valign="top" align="left">Di???n gi???i</td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="trakhuyenmai<%= j %>.diengiai" id="trakhuyenmai<%= j %>.diengiai" value="" />
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">Lo???i tr???</td>
			                                    <td valign="top" align="left">
			                                    	<select name = "trakhuyenmai<%= j %>.loaitra" id = "trakhuyenmai<%= j %>.loaitra" >
			                                    		<option value="3">Tr??? s???n ph???m</option>
			                                    		<option value="1">Tr??? ti???n</option>
			                                    		<option value="2">Tr??? chi???t kh???u</option>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">H??nh th???c</td>
			                                    <td valign="top" align="left">
			                                    	<select name="trakhuyenmai<%= j %>.hinhthuc" id = "trakhuyenmai<%= j %>.hinhthuc">
			                                    		<option value="2">B???t k??? trong</option>
			                                    		<option value="1">B???t bu???c nh???p s??? l?????ng</option>
			                                    	</select>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">T???ng l?????ng / T???ng ti???n</td>
			                                    <td valign="top" align="left">
			                                    	<input type="text" name="trakhuyenmai<%= j %>.sotong" id="trakhuyenmai<%= j %>.sotong" value="" style="text-align: right;"/>
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left">Nh??m s???n ph???m</td>
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
			                                		<input type="checkbox" name="trakhuyenmai<%= j %>.tinhtheothung" id="trakhuyenmai<%= j %>.tinhtheothung"  value='1' > <span style="font-style: italic;">S??? l?????ng t??nh theo Kg</span> 
			                                	</td>
			                                </tr>
			                                <tr>
			                                	<td colspan="2">
			                                		<table align="left" cellpadding="2px" cellspacing="2px">
				                                		<tr>
				                                			<th width="100px" align="center">M?? s???n ph???m</th>
				                                			<th width="250px" align="left">T??n s???n ph???m</th>
				                                			<th width="60px" align="left">S??? l?????ng</th>
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
			        								<img style="top: -4px;" src="../images/button.png" alt=""> Nh???p l???i  </a>
			        								&nbsp;&nbsp;&nbsp;
			        								<a class="button" href="javascript:submitform3(<%= j %>);">
			        								<img style="top: -4px;" src="../images/button.png" alt=""> L??u tr??? khuy???n m??i  </a>
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
	                            	<option value="1">And</option>
	                                <option value="2">Or</option>     
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
            <h1 style="font-size:1.8em"><a href="#">Nh?? ph??n ph???i</a></h1>
            <div style="height:auto">
            <TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px">
                <TR class="plainlabel" valign="bottom">
                <th colspan="3">
                   
                   <fieldset style="width: 30%; float: left;"> 
			       <legend>K??nh b??n h??ng &nbsp;</legend> 
			       <select name="kbhId" >
			       <!-- <option value="">Ch???n h???t</option> -->
                        <%kbhRS.beforeFirst(); 
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
			       <legend>V??ng &nbsp;</legend> 
			       <select name="vung" multiple="multiple">
			       <option value="">Ch???n h???t</option>
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
					<legend>Khu v???c&nbsp;</legend>
					<select name="khuvuc" multiple="multiple">
					<option value="">Ch???n h???t</option>
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
        					<img style="top: -4px;" src="../images/button.png" alt=""> Hi???n th??? Npp theo ??i???u ki???n</a>
                	</th>
                </tr>
                    <TR class="tbheader">
                        <TH align="center" width="10%">M??</TH>
                        <TH align="left" width="50%"> T??n </TH>
                        <TH align="center" width="10%"> Ch???n t???t c??? <input type ="checkbox" name ="all" onclick ="All()"></TH>
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
