<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "java.util.Hashtable" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.bandott.IBandott" %>
<%@ page  import = "geso.dms.center.beans.bandott.imp.Bandott" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Traphaco/index.jsp");
	}else{ %>

<% IBandott bdBean = (IBandott)session.getAttribute("obj"); %>
<% ResultSet vungRs = bdBean.getVungRs(); %>
<% ResultSet DvkdRs = bdBean.getDvkdRs();%>
<% ResultSet kvRs = bdBean.getKvRs(); %>
<% ResultSet nppRs = bdBean.getNppRs(); %>
<% ResultSet ddkd = bdBean.getDdkdRs(); %>
<% ResultSet tbh = bdBean.getTbhRs(); %>
<% ResultSet ttRs = bdBean.getTtRs(); %>
<% ResultSet khSelected = bdBean.getKhDaViengThamRs(); %>
<% 
	ResultSet khList = bdBean.getKhChuaViengThamRs();
	String[] latconditon = bdBean.getLatcondition();
	String[] longconditon = bdBean.getLongconditon();
	
	NumberFormat formater = new DecimalFormat("#,###,###");
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("BandoTTSvl","",nnId);	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>VietNhat - Project</TITLE>
	
	<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?key=AIzaSyDUs-Mowbr3M3jRoVxy30B9uBAlEFgbKMQ"></script>
    <script type="text/javascript">

    
    var khSi = '<%=bdBean.getKhachHangSi().trim().replaceAll("'","") %>';
    
    var map = null;
    var geocoder = null;
    var center = null;
    
	var khs = new Array();
	var khsSelected = new Array();
	var khLatLons = [];
	var khMarkers = [];
	var vtLatLons = [];
	var vtMarkers = [];
	
	var RANDOM_NUMER = 10; 

    function initialize() 
    {
    	 if (true)
         {
       	  
       	  
       	   map = new google.maps.Map(
     		        document.getElementById('map_canvas'), {
     		          zoom: 13
     		      });
        //   map = new GMap2(document.getElementById("map_canvas")); 
           geocoder = new  google.maps.Geocoder();
           
           <% if(bdBean.getTrungtam().length() > 0 ){ System.out.println("1.Center la: " + bdBean.getTrungtam());  %>
           	center = new google.maps.LatLng(<%= bdBean.getTrungtam() %>)
           <% } else { %>        	
           	//center = new GLatLng(10.798804,106.679732);
           	center = new google.maps.LatLng(10.79912,106.680259);
           <%} %>
        
        //map.addControl(new GNavLabelControl());
        
        //map.setCenter(new GLatLng(10.822031,106.630293), 14);
        map.setCenter(center, 14);
        var ko_doanh_soimg="../images/ko_doanh_so.png";
		var khSiIcon = "../images/thang_truoc_co.png";
		var khSiIcon2 = "../images/khSiCDS.png";
		var blueIcon	="../images/maps_human.png";
 		var blueIcon2img="../images/blank.png";
 		 var yellowIcon="../images/vtYellow.png";
	
		  
		

		//Lấy data khách hàng
		<%
		if(khList != null)
		{
			int count = 0;
			int img_stt = 1;
			
			while(khList.next())
			{
		%>
		
		var __khSi = '<%= khList.getString("khachhangsi") == null ? "0" : khList.getString("khachhangsi").trim().replaceAll("'","") %>';
		
	    khs.push({
	    	ten: '<%= khList.getString("khTen") == null ? " " : khList.getString("khTen").trim().replaceAll("'","") %>', 
	    	sdt: '<%= khList.getString("dienthoai") == null ? " " : khList.getString("dienthoai").trim().replaceAll("'","") %>', 
	    	lat: parseFloat('<%= khList.getString("lat") == null  ? "0" : khList.getString("lat").trim().replaceAll("'","") %>'), 
	    	lon: parseFloat('<%= khList.getString("long") == null  ? "0" : khList.getString("long").trim().replaceAll("'","") %>'),
	    	dsThangTruoc: '<%= khList.getString("doanhsoThangtruoc") == null ? "0" : formater.format(khList.getDouble("doanhsoThangtruoc")) %>',
	    	dsTrongThang: '<%= khList.getString("doanhsoTrongthang") == null ? "0" : formater.format(khList.getDouble("doanhsoTrongthang")) %>',
	    	si: khSi == "1" && __khSi == '1' ? '1' : '0',
	    	diachi: '<%= khList.getString("diachi") == null ? " " : khList.getString("diachi").trim().replaceAll("'","") %>',
	    	ngayMHDauTien: '<%= khList.getString("ngayMHDauTien") == null ? " " : khList.getString("ngayMHDauTien").trim().replaceAll("'","") %>',
	    	ngayMHCuoicung: '<%= khList.getString("ngayMHCuoicung") == null ? " " : khList.getString("ngayMHCuoicung").trim().replaceAll("'","") %>',
	    	dhGANNHAT: '<%= khList.getString("dhGANNHAT") == null ? " " : formater.format(khList.getDouble("dhGANNHAT")) %>',
	    	vtGANNHAT: '<%= khList.getString("vtGANNHAT") == null ? " " : khList.getString("vtGANNHAT").trim().replaceAll("'","") %>',
	    	tbDONHANG: '<%= khList.getString("tbDONHANG") == null ? " " : formater.format(khList.getDouble("tbDONHANG")) %>',
	    	dophuSP: '<%= khList.getString("dophuSP") == null ? " " : formater.format(khList.getDouble("dophuSP")) %>',
	    	tonkhoSP: '<%= khList.getString("tonkhoSP") == null ? " " : formater.format(khList.getDouble("tonkhoSP")) %>',
	    	img_url: '<%= getServletContext().getInitParameter("urlAnhPDA") +"AnhDaiDien/"  + khList.getString("anhcuahang")%>'
	    });
		          
			<% count++;  img_stt++;
				if(img_stt > 11)
					img_stt = 1;
			}
		}
		%>
		
		//Lấy data viếng thăm
		
		<%
		if(khSelected != null)
		{
			khSelected.beforeFirst();
			int pos = 0;
			int img_stt = 1;
			
			while(khSelected.next())
			{
		%>
		khsSelected.push({
	    	ten: '<%= khSelected.getString("khTen") == null ? " " : khSelected.getString("khTen").trim().replaceAll("'", "") %>',
	    	ddkd: '<%= khSelected.getString("ddkdTen") == null ? " " : khSelected.getString("ddkdTen").trim().replaceAll("'", "") %>',
	    	sdt: '<%= khSelected.getString("dienthoai") == null ? " " : khSelected.getString("dienthoai").trim().replaceAll("'", "") %>',
	    	thoidiem: '<%= khSelected.getString("thoidiem") == null ? " " : khSelected.getString("thoidiem").trim().replaceAll("'","") %>', 
	    	lat: parseFloat('<%= khSelected.getString("lat") == null  ? "0" : khSelected.getString("lat").trim().replaceAll("'","") %>'), 
	    	lon: parseFloat('<%= khSelected.getString("long") == null  ? "0" : khSelected.getString("long").trim().replaceAll("'","") %>'),
	    	dsThangTruoc: '<%= khSelected.getString("doanhsoThangtruoc") == null ? "0" : formater.format(khSelected.getDouble("doanhsoThangtruoc")) %>',
	    	dsTrongThang: '<%= khSelected.getString("doanhsoTrongthang") == null ? "0" : formater.format(khSelected.getDouble("doanhsoTrongthang")) %>',
	    	diachi: '<%= khSelected.getString("diachi") == null ? " " : khSelected.getString("diachi").trim().replaceAll("'","") %>',
		    ngayMHDauTien: '<%= khSelected.getString("ngayMHDauTien") == null ? " " : khSelected.getString("ngayMHDauTien").trim().replaceAll("'","") %>',
		    ngayMHCuoicung: '<%= khSelected.getString("ngayMHCuoicung") == null ? " " : khSelected.getString("ngayMHCuoicung").trim().replaceAll("'","") %>',
		    dhGANNHAT: '<%= khSelected.getString("dhGANNHAT") == null ? " " : formater.format(khSelected.getDouble("dhGANNHAT")) %>',
		    vtGANNHAT: '<%= khSelected.getString("vtGANNHAT") == null ? " " : khSelected.getString("vtGANNHAT").trim().replaceAll("'","") %>',
		    tbDONHANG: '<%= khSelected.getString("tbDONHANG") == null ? " " : formater.format(khSelected.getDouble("tbDONHANG")) %>',
		    dophuSP: '<%= khSelected.getString("dophuSP") == null ? " " : formater.format(khSelected.getDouble("dophuSP")) %>',
		    tonkhoSP: '<%= khSelected.getString("tonkhoSP") == null ? " " : formater.format(khSelected.getDouble("tonkhoSP")) %>',
		    img_url: '<%= getServletContext().getInitParameter("urlAnhPDA") +"AnhDaiDien/"  +  khSelected.getString("anhcuahang")%>',
		    ds: '<%= khSelected.getString("ds") == null ? "0" : formater.format(khSelected.getDouble("ds")) %>'
	    });
		
		<% 		pos++; img_stt++;
				if(img_stt > 11)
					img_stt = 1;
			}
		} 
		%>
		
		
		//Xử lý
		var z;
		
		var khLats = [];
		var khLons = [];
		
		//Khách hàng
		for(z = 0; z < khs.length; z++) {
			var kh = khs[z];
			
			//Chỉnh các tọa độ trùng
			var latIndex = khLats.indexOf(kh.lat);
			var lonIndex = khLons.indexOf(kh.lon);
			if( latIndex >= 0 && lonIndex >= 0 && latIndex === lonIndex) {
				var ranLat = (Math.random()*RANDOM_NUMER-RANDOM_NUMER/2)/100000;
				var ranLon = (Math.random()*RANDOM_NUMER-RANDOM_NUMER/2)/100000;
				 //console.log("["+z+"] lat = " + ranLat + "lon = " + ranLon);
				kh.lat += ranLat;
				kh.lon += ranLon;
			}
			khLats.push(kh.lat);
			khLons.push(kh.lon);
			
			var latlon = new  google.maps.LatLng(kh.lat, kh.lon);
			var marker;
			if(parseFloat(kh.dsTrongThang) > 0) {
				// marker = new google.maps.Marker(latlon, kh.si == '1' ? {icon: khSiIcon2} : markerOptions);
				marker = new google.maps.Marker({ position: latlon,			     
					      icon: kh.si == '1' ?  khSiIcon2 : blueIcon });
			} else {
				//marker = new google.maps.Marker(latlon, kh.si == '1' ? {icon: khSiIcon} : marker_ko_ds);
				marker = new google.maps.Marker({ position: latlon,
				    
				      icon: kh.si == '1' ? khSiIcon : ko_doanh_soimg });
				
				
			}
			const m=marker;
			marker.addListener("click", (
				function(kh, latlon) { 
					return function () 
					{
						//var info = "<b>Khách hàng: </b>"+kh.ten + (kh.si == '1' ? " (KH Sỉ) " : "") + "<br /><b>Số điện thoại: </b>"+kh.sdt+"<br /><b>Doanh số TB 3 tháng: </b>"+kh.dsThangTruoc+"<br /><b>Doanh số tháng này: </b>" + kh.dsTrongThang;
						
						var info = 	"<table style='width: 650px' cellpadding='2' cellspacing='1' > " +
							"	<tr> " +
							"		<td style='width: 140px;font-weight:400;' > " +
							"			<b>Họ tên: </b> " +
							"		</td> " +
							"		<td style='width: 150px;font-weight:400;' > " +
							"			 " + kh.ten + (kh.si == '1' ? " (KH Sỉ) " : "") +
							"		</td> " +
							"		<td style='width: 110px;font-weight:400;' > " +
							"			<b>Địa chỉ: </b> " +
							"		</td> " +
							"		<td style='font-weight:400;'> " +
							"		 " + kh.diachi +
							"		</td> " +
							"	</tr> " +
							"	<tr> " +
							"		<td style='font-weight:400;'> " +
							"			<b>Điện thoại: </b>  " +
							"		</td> " +
							"		<td > " +
							"			 " + kh.sdt +
							"		</td> " +
							"		<td style='font-weight:400;'> " +
							"			<b>Tồn kho: </b> " +
							"		</td> " +
							"		<td > " +
							"			" + kh.tonkhoSP + " (KG) " +
							"		</td> " +
							"	</tr> " +
							"	<tr> " +
							"		<td style='font-weight:400;'> " +
							"			<b>Doanh số TB 3 tháng: </b> " +
							"		</td> " +
							"		<td > " +
							"			 " + kh.dsThangTruoc +
							"		</td> " +
							"		<td style='font-weight:400;'> " +
							"			<b>DS từ đầu tháng: </b>  " +
							"		</td> " +
							"		<td > " +
							"			 " + kh.dsTrongThang +
							"		</td> " +
							"	</tr> " +
							"	<tr> " +
							"		<td style='font-weight:400;'> " +
							"			<b>Ngày MH đầu tiên: </b> " +
							"		</td> " +
							"		<td > " +
							"			 " + kh.ngayMHDauTien +
							"		</td> " +
							"		<td style='font-weight:400;' rowspan='6' valign='top' > " +
							"			<b>Hình ảnh: </b>	" +		
							"		</td> " +
							"		<td rowspan='6' valign='top' > " +
							"			<img src = '" + kh.img_url + "' style='max-height: 150px; max-width: 250px' />	 " +
							"		</td> " +
							"	</tr> " +
							"	<tr> " +
							"		<td style='font-weight:400;'> " +
							"			<b>Ngày MH cuối cùng: </b> " +
							"		</td> " +
							"		<td > " +
							"			 " + kh.ngayMHCuoicung +
							"		</td> " +
							"	</tr> " +
							"	<tr> " +
							"		<td style='font-weight:400;'> " +
							"			<b>TB đơn hàng 3 tháng: </b> " +
							"		</td> " +
							"		<td > " +
							"			 " + kh.tbDONHANG +
							"		</td> " +
							"	</tr> " +
							"	<tr> " +
							"		<td style='font-weight:400;'> " +
							"			<b>Đơn hàng gần nhất: </b> " +
							"		</td> " +
							"		<td > " +
							"			 " + kh.dhGANNHAT +
							"		</td> " +
							"	</tr> " +
							"	<tr> " +
							"		<td style='font-weight:400;'> " +
							"			<b>Lần VT gần nhất: </b> " +
							"		</td> " +
							"		<td > " +
							"			 " + kh.vtGANNHAT +
							"		</td> " +
							"	</tr> " +
							"	<tr> " +
							"		<td style='font-weight:400;' > " +
							"			<b>Độ phủ sản phẩm: </b> " + 
							"		</td> " +
							"		<td > " +
							"			 " + kh.dophuSP +
							"		</td> " +
							"	</tr> " +
						"</table> ";
						
						   var infowindow = new google.maps.InfoWindow({
							    content: info,
							  });
						   infowindow.open(map,m);
					};
				}
			)(kh, latlon));
			marker.setMap(map);
			
		}
		
		khLats = [];
		khLons = [];
		
		//Viếng thăm
		for(z = 0; z < khsSelected.length; z++) {
			var kh = khsSelected[z];
			
			//Chỉnh các tọa độ trùng
			var latIndex = khLats.indexOf(kh.lat);
			var lonIndex = khLons.indexOf(kh.lon);
			if( latIndex >= 0 && lonIndex >= 0 && latIndex === lonIndex) {
				var ranLat = (Math.random()*RANDOM_NUMER-RANDOM_NUMER/2)/100000;
				var ranLon = (Math.random()*RANDOM_NUMER-RANDOM_NUMER/2)/100000;
				//console.log("["+z+"] lat = " + ranLat + "lon = " + ranLon);
				kh.lat += ranLat;
				kh.lon += ranLon;
			}
			khLats.push(kh.lat);
			khLons.push(kh.lon);
			
			var latlon = new  google.maps.GLatLng(kh.lat, kh.lon);      
	        var marker;// = new google.maps.Marker(latlon, markerOptions2);

			var marker;
			var _title = kh.ddkd + " " + (z+1);
			if( parseFloat(kh.ds) > 0) {
				//marker = new google.maps.Marker(latlon, { icon:blueIcon2, title: _title });
				marker = new google.maps.Marker({ position: latlon,
					title: _title ,
					icon: blueIcon2img });
				
			} else {
				//marker = new google.maps.Marker(latlon, { icon: yellowIcon, title: _title });
				marker = new google.maps.Marker({ position: latlon,
					title: _title ,
					icon:yellowIcon });
			}
	        const m2=marker;
			marker.addListener( "click", (
				function(kh, latlon) { 
					return function () 
					{
						//var noidung = "<b>Thời điểm: </b>"+kh.thoidiem+"<br /><b>Số điện thoại: </b>"+kh.sdt+"<br /><b>Tên nhân viên: </b>"+kh.ddkd+"<br /><b>Khách hàng: </b>"+kh.ten;
						
						var noidung = 	"<table style='width: 650px' cellpadding='2' cellspacing='1' > " +
									"	<tr> " +
									"		<td style='width: 140px;font-weight:400;' > " +
									"			<b>Họ tên: </b> " +
									"		</td> " +
									"		<td style='width: 150px;font-weight:400;' > " +
									"			 " + kh.ten +
									"		</td> " +
									"		<td style='width: 110px;font-weight:400;' > " +
									"			<b>Địa chỉ: </b> " +
									"		</td> " +
									"		<td style='font-weight:400;'> " +
									"		 " + kh.diachi +
									"		</td> " +
									"	</tr> " +
									"	<tr> " +
									"		<td style='font-weight:400;'> " +
									"			<b>Điện thoại: </b>  " +
									"		</td> " +
									"		<td > " +
									"			 " + kh.sdt +
									"		</td> " +
									"		<td style='font-weight:400;'> " +
									"			<b>Tồn kho: </b> " +
									"		</td> " +
									"		<td > " +
									"			" + kh.tonkhoSP + " (KG) " +
									"		</td> " +
									"	</tr> " +
									"	<tr> " +
									"		<td style='font-weight:400;'> " +
									"			<b>Doanh số TB 3 tháng: </b> " +
									"		</td> " +
									"		<td > " +
									"			 " + kh.dsThangTruoc +
									"		</td> " +
									"		<td style='font-weight:400;'> " +
									"			<b>DS từ đầu tháng: </b>  " +
									"		</td> " +
									"		<td > " +
									"			 " + kh.dsTrongThang +
									"		</td> " +
									"	</tr> " +
									"	<tr> " +
									"		<td style='font-weight:400;'> " +
									"			<b>Ngày MH đầu tiên: </b> " +
									"		</td> " +
									"		<td > " +
									"			 " + kh.ngayMHDauTien +
									"		</td> " +
									"		<td style='font-weight:400;' rowspan='6' valign='top' > " +
									"			<b>Hình ảnh: </b>	" +		
									"		</td> " +
									"		<td rowspan='6' valign='top' > " +
									"			<img src = '" + kh.img_url + "' style='max-height: 150px; max-width: 250px' />	 " +
									"		</td> " +
									"	</tr> " +
									"	<tr> " +
									"		<td style='font-weight:400;'> " +
									"			<b>Ngày MH cuối cùng: </b> " +
									"		</td> " +
									"		<td > " +
									"			 " + kh.ngayMHCuoicung +
									"		</td> " +
									"	</tr> " +
									"	<tr> " +
									"		<td style='font-weight:400;'> " +
									"			<b>TB đơn hàng 3 tháng: </b> " +
									"		</td> " +
									"		<td > " +
									"			 " + kh.tbDONHANG +
									"		</td> " +
									"	</tr> " +
									"	<tr> " +
									"		<td style='font-weight:400;'> " +
									"			<b>Đơn hàng gần nhất: </b> " +
									"		</td> " +
									"		<td > " +
									"			 " + kh.dhGANNHAT +
									"		</td> " +
									"	</tr> " +
									"	<tr> " +
									"		<td style='font-weight:400;'> " +
									"			<b>Lần VT gần nhất: </b> " +
									"		</td> " +
									"		<td > " +
									"			 " + kh.vtGANNHAT +
									"		</td> " +
									"	</tr> " +
									"	<tr> " +
									"		<td style='font-weight:400;'> " +
									"			<b>Thời điểm VT: </b> " +
									"		</td> " +
									"		<td > " +
									"			 " + kh.thoidiem +
									"		</td> " +
									"	</tr> " +
									"	<tr> " +
									"		<td style='font-weight:400;' > " +
									"			<b>Độ phủ sản phẩm: </b> " + 
									"		</td> " +
									"		<td > " +
									"			 " + kh.dophuSP +
									"		</td> " +
									"	</tr> " +
								"</table> ";
										
						 var infowindow = new google.maps.InfoWindow({
							    content: noidung,
							  });
						   infowindow.open(map,m2);
					};
				}
				)(kh, latlon));
			marker.setMap(map);
			
	        //Vẽ tên ĐDKD
			var canvas = document.createElement("canvas");
	        canvas.width = 130;
	        canvas.height = 20;
			var ctx = canvas.getContext("2d");
			ctx.font = "bold 10px Arial";
			ctx.textAlign = "center";
			ctx.strokeStyle = "white";
			ctx.strokeText(_title, 65, 12);
			ctx.fillText(_title, 65, 12);
			var url = canvas.toDataURL();
			 //	var textIcon = new GIcon(G_DEFAULT_ICON);
			//textIcon.image = url;
			/*	textIcon.iconSize = new GSize(130,20);
			textIcon.iconAnchor = new GPoint(60, 60);
			textIcon.shadowSize = new GSize(0,0); */
			//console.log(url);
			marker = new google.maps.Marker({ position: latlon,
				title: _title ,
				icon:yellowIcon });
			
			marker = new google.maps.Marker(latlon, {icon: "https://maps.gstatic.com/mapfiles/ms2/micons/blue-dot.png"});

			marker.setMap(map);
			
		}
		
      }
      
      
	      <% if( latconditon != null ) 
	      {
	    	  for(int i = 0; i < latconditon.length - 1; i++ ) 
	    	  {
	    		  String lat_long = latconditon[i] + ", " + longconditon[i];
	    		  String lat_long2 = latconditon[i + 1] + ", " + longconditon[i + 1];
	    		  %>
	    		  
	    		  var  flightPlanCoordinates = [ new google.maps.LatLng(<%=lat_long%> ), new google.maps.LatLng(<%=lat_long2%>  ) ];
		    	   var flightPath = new google.maps.Polyline({
			   		    path: flightPlanCoordinates,
			   		    geodesic: true,
			   		    strokeColor: "#FF0000",
			   		    strokeOpacity: 1.0,
			   		    strokeWeight: 2,
			   		 
			            icons: [{
			                icon: {path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW},
			                offset: '100%'
			            }]
			   		  });
			   	  flightPath.setMap(map);      
	    		  
	    	  <% } %>
	      /* var polyline = new GPolyline([
	                                    new GLatLng(10.8169844, 106.6872128),
	                                    new GLatLng(10.8165455, 106.6876032)
	                                  ], "#66FF33", 5);
	                                  map.addOverlay(polyline); */
	     <% } %> 
      
    }
    
    function showAddress(address) 
    {
    	 geocoder.getLatLng(
    	    address,
    	    function(point) 
    	    {
    	      if (!point) 
    	      {
    	        alert("Không thể tìn thấy địa chỉ:" + address);
    	      } 
    	      else 
    	      {
	    	        map.setCenter(point, 14);
	    	        var marker = new google.maps.Marker(point);
	    	        map.addOverlay(marker);
	
	    	        // As this is user-generated content, we display it as
	    	        // text rather than HTML to reduce XSS vulnerabilities.
	    	        marker.openInfoWindow(document.createTextNode("Địa chỉ: " + address));
	    	        document.getElementById("trungtam").value = point.lat() + "," + point.lng();
    	      }
    	    }
    	  );
    }
    
    </script>
    
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">

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
  	
  	<script type="text/javascript">
	  	function submitform()
		{  
		    document.forms["bdForm"].submit();
		}
	  	
	  	function search()
	  	{
	  		
	  		document.forms['bdForm'].action.value= 'viewBd';
	  		document.forms['bdForm'].submit();
	  		
	  	}

	  	
  	</script>
  	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
	
<script>
$(document).ready(function()
{
	$(".select2").select2();
	
});
</script>

<style type="text/css">
	input
	{
		padding: 2px 0px;
	}
	
</style>
  	
  	

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>

<body  >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="bdForm" method="post" action="../../BandoTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="trungtam" id="trungtam" value=''>

<div id="main" style="width:99.5%; padding-left:2px">

	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:40%; padding:5px; float:left" class="tbnavigation">
        	<%=url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	<%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%= userTen %> &nbsp;&nbsp;
        </div>
    </div>
    
    <div style="width:100%;" align="left">
    <fieldset>
    	<legend class="legendtitle"><%=ChuyenNgu.get("Thông tin lộ trình bán hàng",nnId) %></legend>
        <TABLE width="100%" cellpadding="4" cellspacing="0" style="background-color: #C5E8CD;">	  
        <TR>
         <TD class="plainlabel" ><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %> </TD>
                <TD class="plainlabel" >
                    <select name="dvkdId"  onChange = "submitform();" id="dvkdId"  style="width:200px" class="select2" > 
                            <option value="" selected></option>
                            <% if(DvkdRs != null){
								  try{ while(DvkdRs.next()){ 
					    			if(DvkdRs.getString("pk_seq").equals(bdBean.getDvkdId())){ %>
					      				<option value='<%= DvkdRs.getString("pk_seq")%>' selected><%= DvkdRs.getString("donvikinhdoanh") %></option>
					      			<%}else{ %>
					     				<option value='<%= DvkdRs.getString("pk_seq")%>'><%= DvkdRs.getString("donvikinhdoanh") %></option>
					     	<%}} DvkdRs.close(); }catch(java.sql.SQLException e){} }%>
                        </select>    
                </TD>
        </TR> 
            <TR>
                <TD class="plainlabel" width="150px" ><%=ChuyenNgu.get("Ngày",nnId) %> </TD>
                <TD class="plainlabel" width="200px" >
                    <input type="text" size="11" class="days" 
                          id="ngay" name="ngay" value="<%= bdBean.getdate() %>" maxlength="10" readonly onchange="submitform();"  />
                </TD>
               
               
                
               <%if(!bdBean.getDvkdId().equals("100069")) {%>
                 <TD class="plainlabel" ><%=ChuyenNgu.get("Miền",nnId) %> </TD>
                <TD class="plainlabel" >
                    <select name="vung"  onChange = "submitform();" id="vungId"  style="width:200px" class="select2" > 
                            <option value="" selected></option>
                            <% if(vungRs != null){
								  try{ while(vungRs.next()){ 
					    			if(vungRs.getString("pk_seq").equals(bdBean.getVungId())){ %>
					      				<option value='<%= vungRs.getString("pk_seq")%>' selected><%= vungRs.getString("ten") %></option>
					      			<%}else{ %>
					     				<option value='<%= vungRs.getString("pk_seq")%>'><%= vungRs.getString("ten") %></option>
					     	<%}} vungRs.close(); }catch(java.sql.SQLException e){} }%>
                        </select>    
                </TD>
               <%} %>
				  
                <TD class="plainlabel" width="120px"></TD>
                <TD class="plainlabel"  >
                   <%-- <% if(bdBean.getKhachHangSi().equals("1")) {%>						
				  		<input name="khachhangsi" type="checkbox" value="1" checked  onchange="submitform();">
				  	<%}else{ %>
				  		<input name="khachhangsi" type="checkbox" value="1"  onchange="submitform();">
				  	<%} %>
				  	Khách hàng sỉ --%>
                </TD>
                
            </TR>
            <%if(!bdBean.getDvkdId().equals("100069")) {%>
            <TR>
               
               <%--  <TD class="plainlabel" >Vùng </TD>
                <TD class="plainlabel">
                    <select name="khuvuc"  onChange = "submitform();" id="khuvucId"  style="width:200px" class="select2" >
                            <option value="" selected></option>
                            <% if(kvRs != null){
								  try{ while(kvRs.next()){ 
					    			if(kvRs.getString("pk_seq").equals(bdBean.getkvId())){ %>
					      				<option value='<%= kvRs.getString("pk_seq")%>' selected><%= kvRs.getString("ten") %></option>
					      			<%}else{ %>
					     				<option value='<%= kvRs.getString("pk_seq")%>'><%= kvRs.getString("ten") %></option>
					     	<%}} kvRs.close(); }catch(java.sql.SQLException e){} }%>
                        </select>    
                </TD> --%>
                
                 <TD class="plainlabel" ><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
                <TD class="plainlabel">
                    <select name="npp"  onChange = "submitform();" id="nppId"  style="width:200px" class="select2" >
                            <option value="" selected></option>
                            <% if(nppRs != null){
								  try{ while(nppRs.next()){ 
					    			if(nppRs.getString("pk_seq").equals(bdBean.getNppId())){ %>
					      				<option value='<%= nppRs.getString("pk_seq")%>' selected><%= nppRs.getString("ten") %></option>
					      			<%}else{ %>
					     				<option value='<%= nppRs.getString("pk_seq")%>'><%= nppRs.getString("ten") %></option>
					     	<%}} nppRs.close(); }catch(java.sql.SQLException e){} }%>
                        </select>    
                </TD>
                 <TD class="plainlabel" ><%=ChuyenNgu.get("Tỉnh thành",nnId) %> </TD>
                <TD class="plainlabel">
                    <select name="ttId"  onChange = "submitform();" id="ttId"  style="width:200px" class="select2" >
                            <option value="" selected></option>
                            <% if(ttRs != null){
								  try{ while(ttRs.next()){ 
					    			if(ttRs.getString("pk_seq").equals(bdBean.getTtId() )){ %>
					      				<option value='<%= ttRs.getString("pk_seq")%>' selected><%= ttRs.getString("ten") %></option>
					      			<%}else{ %>
					     				<option value='<%= ttRs.getString("pk_seq")%>'><%= ttRs.getString("ten") %></option>
					     	<%}} ttRs.close(); }catch(java.sql.SQLException e){} }%>
                        </select>    
                </TD>
            
            </TR>
            <TR>
                
            <%}%>   
                
	
				
                
				
            </TR>
            <TR >
              <TD class="plainlabel"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></TD>
                <TD class="plainlabel">
                    <select name="ddkd"  onChange = "submitform();" id="ddkdId"  style="width:200px" class="select2" >
                            <option value="" selected></option>
                            <% if(ddkd != null){
								  try{ while(ddkd.next()){ 
					    			if(ddkd.getString("ddkdId").equals(bdBean.getDdkdId())){ %>
					      				<option value='<%= ddkd.getString("ddkdId")%>' selected><%= ddkd.getString("ddkdTen") %></option>
					      			<%}else{ %>
					     				<option value='<%= ddkd.getString("ddkdId")%>'><%= ddkd.getString("ddkdTen") %></option>
					     	<%}} ddkd.close(); }catch(java.sql.SQLException e){} }%>
                        </select>    
                </TD>
                  <%if(!bdBean.getDvkdId().equals("100069")) {%>
                	<TD class="plainlabel" ><%=ChuyenNgu.get("Tuyến bán hàng",nnId) %> </TD>
                	<%} else {%>
                		<TD class="plainlabel" ><%=ChuyenNgu.get("Kế hoạch bán hàng",nnId) %> </TD>
                	  <%}%>
                <TD class="plainlabel">
                    <select name="tbh" id="tbh" onChange = "submitform();" id="tbhId"  style="width:200px" class="select2" >
                          <option value="" selected></option>
                          <% if(tbh != null){
						  try{ while(tbh.next()){ 
			    			if(tbh.getString("tbhId").equals(bdBean.getTbhId())){ %>
			      				<option value='<%= tbh.getString("tbhId")%>' selected><%= tbh.getString("tbhTen") %></option>
			      			<%}else{ %>
			     				<option value='<%= tbh.getString("tbhId")%>'><%= tbh.getString("tbhTen") %></option>
			     	<%}} tbh.close(); }catch(java.sql.SQLException e){} }%>
                      </select>   
                      
                      
                      
         
			                 
            </TR>

			
           
            <TR>
            	<td class="plainlabel"><%=ChuyenNgu.get("Giải thích",nnId) %> </td>
			  <TD class="plainlabel" colspan="6"  >
                     <img src="../images/ko_doanh_so.png" width="30" height="30" alt=""><%=ChuyenNgu.get("KH",nnId) %>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
                    <img src="../images/maps_human.png" width="30" height="30" alt=""><%=ChuyenNgu.get("KH có doanh số",nnId) %>  &nbsp;&nbsp;&nbsp;&nbsp;
				
			
                	<img src="../images/vtYellow.png" alt="" width="16"><%=ChuyenNgu.get("Viếng thăm",nnId) %>  &nbsp;&nbsp;&nbsp;&nbsp;
		        
              
          		<img src="../images/blank.png" alt="" width="16"><%=ChuyenNgu.get("Viếng thăm có doanh số",nnId) %>  &nbsp;&nbsp;&nbsp;&nbsp;
           
				 
                	<% if(bdBean.getKhachHangSi().equals("1")) { %>
                	 
					<img src="../images/thang_truoc_co.png" width="30" height="30" alt=""><%=ChuyenNgu.get("KH sỉ",nnId) %>  &nbsp;&nbsp;&nbsp;&nbsp;
					
					 
                    <img src="../images/khSiCDS.png" width="30" height="30" alt=""><%=ChuyenNgu.get("KH sỉ có doanh số",nnId) %>   &nbsp;&nbsp;&nbsp;&nbsp;
					<% } %>
               </TD>
            </TR>
			 <tr>
			<TD class="plainlabel" ><a class="button2" href="javascript:search();"> 
				
				 <img style="margin-bottom: -10px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Hiển thị",nnId) %>   </a>
						  </TD>
			
			</tr>
            
            
        </TABLE>
         
     	<hr>
    
	<div id="map_canvas" style="width: 100%; height: 650px; position:static; margin-left:auto; margin-right:auto">
    </div>
    
    </fieldset></div>
 </div>
 
 </form>
</body> 
</html>
<script>
google.maps.event.addDomListener(window, 'load', initialize);
</script>

<% 

if(khSelected != null)
	khSelected.close();
if(khList != null)
	khList.close();
if(nppRs != null)
	nppRs.close();
if(ddkd != null)
	ddkd.close();
if(tbh != null)
	tbh.close();
if(vungRs != null)
	vungRs.close();
if(kvRs != null)
	kvRs.close();

bdBean.DBclose(); 

%>

<%}%>
