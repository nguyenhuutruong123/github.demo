<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "java.util.Hashtable" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.bandott.IBandott" %>
<%@ page  import = "geso.dms.center.beans.bandott.imp.Bandott" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SalesUp/index.jsp");
	}else{ %>

<% IBandott bdBean = (IBandott)session.getAttribute("obj"); %>
<% ResultSet vungRs = bdBean.getVungRs(); %>
<% ResultSet nppRs = bdBean.getNppRs(); %>
<% ResultSet ddkd = bdBean.getDdkdRs(); %>
<% ResultSet nhanvien = bdBean.getNvRs(); %>
<% ResultSet kvRs = bdBean.getKvRs(); %>

<% 
	ResultSet khList = bdBean.getKhChuaViengThamRs(); // ben nay la lo trinh cua dai dien kinh danh
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
	<TITLE>OPV - Project</TITLE>
	<style type="text/css">
  .style1 {background-color:#ffffff;font-weight:bold;border:4px #006699 solid; font-size:15px; font-style: bold; }
    .labels {
        color: blue;
        background-color: white;
        font-family: "Lucida Grande", "Arial", sans-serif;
        font-size: 10px;
        font-weight: bold;
        text-align: center;
        width: 20px;
        border: 2px solid black;
        white-space: nowrap;
      }
    </style>
	<script src="//maps.googleapis.com/maps/api/js?key=AIzaSyDUs-Mowbr3M3jRoVxy30B9uBAlEFgbKMQ" type="text/javascript"></script>
    <script src="http://cdn.sobekrepository.org/includes/gmaps-markerwithlabel/1.9.1/gmaps-markerwithlabel-1.9.1.js" type="text/javascript"></script>
    <script type="text/javascript">
   
    var map = null;
    var geocoder = null;
    var center = null;
    
	var khs = new Array();
	var duongdiList = new Array();
	
	var khLatLons = [];
	var khMarkers = [];
	
	var RANDOM_NUMER = 10; 

    function initialize() 
    {
    	if (true)
        {
      	  map = new google.maps.Map(
      		        document.getElementById('map_canvas'), {
      		          zoom: 13
      		      });
          //geocoder = new GClientGeocoder();
          geocoder = new  google.maps.Geocoder();
          center = new  google.maps.LatLng(10.79912,106.680259);
          map.setCenter(center, 14);
         // map.setUIToDefault();
          
          // Create our "tiny" marker icon
         
          var blueIcon_image = "../images/maps_human.png";
 
  		var ko_doanh_so_image = "../images/ko_doanh_so.png";
 
  		var khSiIcon_image = "../images/thang_truoc_co.png";
  
  		var khSiIcon2_image = "../images/khSiCDS.png";
 
  	    var  blueIcon2_image = "../images/blank.png";
 
  	    var  yellowIcon_image = "../images/greenflag.png";
   		
		
		//Lấy data khách hàng
		<%
		if(khList != null)
		{
			int count = 0;
			int img_stt = 1;
			
			while(khList.next())
			{
				String _lat =khList.getString("lat") == null  ? "0" : khList.getString("lat").trim().replaceAll("'","").replaceAll("\n","").replaceAll("\t","");
				String _lon =  khList.getString("long") == null  ? "0" : khList.getString("long").trim().replaceAll("'","").replaceAll("\n","").replaceAll("\t","");
 	
		%>
				
	    khs.push({
	    	stt: parseFloat('<%= khList.getString("stt") == null  ? "1" : khList.getString("stt").trim().replaceAll("'","") %>'),
	    	isVT: parseFloat('<%= khList.getString("isVT") == null  ? "0" : khList.getString("isVT").trim().replaceAll("'","") %>'),
	    	thongtinkh: '<%= khList.getString("thongtinkh") == null ? " " : khList.getString("thongtinkh").trim().replaceAll("'","") %>', 	    	 
	    	lat: parseFloat('<%= khList.getString("lat") == null  ? "0" : khList.getString("lat").trim().replaceAll("'","") %>'), 
	    	lon: parseFloat('<%= khList.getString("long") == null  ? "0" : khList.getString("long").trim().replaceAll("'","") %>'),
	    	khoangcach: parseFloat('<%= khList.getString("khoangcach") == null  ? "0" : khList.getString("khoangcach").trim().replaceAll("'","") %>'),
	   		thoidiem: '<%= khList.getString("thoidiem") == null ? " " : khList.getString("thoidiem").trim().replaceAll("'","") %>'
	   <%--  	img_url: '../upload/<%= khList.getString("anhcuahang")%>' --%>
	    });
		          
			<% count++;  img_stt++;
				if(img_stt > 11)
					img_stt = 1;
			}
		}
		%>
		

			if(khs.length <=0)
			{
				<% if(bdBean.getAction().trim().length() >1 ){ %>
					alert('Hien tai chua ghi nhan duoc toa do');
				<%}%>
				
				return;
			}
		
		//Xử lý
		var z;
		
		var khLats = [];
		var khLons = [];
		
		
		center = new google.maps.LatLng(khs[0].lat,khs[0].lon);
        map.setCenter(center, 14);
		//Khách hàng
		var run = 0;
		for(z = 0; z < khs.length; z++) 
		{
			var kh = khs[z];
			if( 1==1 )
			{	run = run + 1;
				//Chỉnh các tọa độ trùng
				var latIndex = khLats.indexOf(kh.lat);
				var lonIndex = khLons.indexOf(kh.lon);
				if( latIndex >= 0 && lonIndex >= 0 && latIndex === lonIndex) {
					var ranLat = (Math.random()*RANDOM_NUMER-RANDOM_NUMER/2)/100000;
					var ranLon = (Math.random()*RANDOM_NUMER-RANDOM_NUMER/2)/100000;
					kh.lat += ranLat;
					kh.lon += ranLon;
				}
				khLats.push(kh.lat);
				khLons.push(kh.lon);
				
				var latlon = new google.maps.LatLng(kh.lat, kh.lon);
				var latlon1 = new google.maps.LatLng(kh.lat+0.00001, kh.lon);
				var marker;
				  if(z == 0|| z == khs.length - 1) {
				
					
					marker=	new MarkerWithLabel({
					      position: latlon,
					      draggable: true,
					      raiseOnDrag: true,
					      map: map,
					      labelContent: run+"",
					      labelAnchor: new google.maps.Point(22, 0),
					      labelClass: "labels", // the CSS class for the label
					      icon: blueIcon2_image
					    });
					
				} else  if( kh.isVT >0) {
					//marker = new GMarker(latlon,   marker_ko_ds);
					
					marker=	new MarkerWithLabel({
					      position: latlon,
					      draggable: true,
					      raiseOnDrag: true,
					      map: map,
					      labelContent: run+"",
					      labelAnchor: new google.maps.Point(22, 0),
					      labelClass: "labels", // the CSS class for the label
					      icon: ko_doanh_so_image
					    });
				}else
					{
						//marker = new GMarker(latlon,   markerOptions3);
					marker=	new MarkerWithLabel({
					      position: latlon,
					      draggable: true,
					      raiseOnDrag: true,
					      map: map,
					      labelContent: run+"",
					      labelAnchor: new google.maps.Point(22, 0),
					      labelClass: "labels", // the CSS class for the label
					      icon: yellowIcon_image
					    });
					
						
					}  
				  
				 	
					
				const m=marker;
				 
				
 				marker.addListener( "click", (
					function(kh, latlon) { 
						return function () 
						{
							//var info = "<b>Khách hàng: </b>"+kh.ten + (kh.si == '1' ? " (KH Sỉ) " : "") + "<br /><b>Số điện thoại: </b>"+kh.sdt+"<br /><b>Doanh số TB 3 tháng: </b>"+kh.dsThangTruoc+"<br /><b>Doanh số tháng này: </b>" + kh.dsTrongThang;
							
							var info = 	"<table style='width: 400px' cellpadding='2' cellspacing='1' > " +
								(kh.isVT>0?
										"	<tr> " +
										"		<td style='width: 100px;font-weight:100;' > " +
										(kh.isVT >0 ? "			<b>Khach hang: </b> ":"<b>__</b>") +
										"		</td> " +
										"		<td style='width: 300px;font-weight:300;' > " +
										(kh.isVT >0 ? kh.thongtinkh:"")  +
										"		</td> " +
										"	</tr> " 
										:""
								)+
								"	<tr> " +
								"		<td style='width: 100px;font-weight:100;' > " +
								"			<b>Khoảng cách: </b> " +
								"		</td> " +
								"		<td style='width: 300px;font-weight:300;' > " +
								(Math.round(kh.khoangcach)/1000)+" km " +
								"		</td> " +
								"	</tr> "  +
								"	<tr> " +
								"		<td style='width: 100px;font-weight:100;' > " +
								"			<b>Thời điểm: </b> " +
								"		</td> " +
								"		<td style='width: 300px;font-weight:300;' > " +
								kh.thoidiem +
								"		</td> " +
								"	</tr> "  +
							
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
		}
  			
		for(z = 0; z < khs.length-1; z++) 
		{
			var kh = khs[z];
			var kh1 = khs[z + 1];
			 const  flightPlanCoordinates = [ new google.maps.LatLng( kh.lat,kh.lon ), new google.maps.LatLng( kh1.lat,kh1.lon ) ];
		   	  
		   	  const flightPath = new google.maps.Polyline({
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
		}
		
		/* var flightPlanCoordinates = [
		                             new google.maps.LatLng(khs[0].lat, khs[0].lon),
		                             new google.maps.LatLng(khs[1].lat, khs[1].lon),
		                             new google.maps.LatLng(-18.142599, 178.431),
		                             new google.maps.LatLng(-27.46758, 153.027892)
		                           ];
		                           var flightPath = new google.maps.Polyline({
		                             path: flightPlanCoordinates,
		                             geodesic: true,
		                             strokeColor: '#FF0000',
		                             strokeOpacity: 1.0,
		                             strokeWeight: 2
		                           });

		                           flightPath.setMap(map); */

		
		
     <%--  <% if( latconditon != null ) 
      {
    	  for(int i = 0; i < latconditon.length - 1; i++ ) 
    	  {
    		  String lat_long = latconditon[i] + ", " + longconditon[i];
    		  String lat_long2 = latconditon[i + 1] + ", " + longconditon[i + 1];
    		  %>
    		  
    		  var polyline = new GPolyline([
    	                                    new GLatLng(<%= lat_long %>),
    	                                    new GLatLng(<%= lat_long2 %>)
    	                                  ], "#FF0000", 5);
    	                                  map.addOverlay(polyline);
    		  
    	  <% } %>
      /* var polyline = new GPolyline([
                                    new GLatLng(10.8169844, 106.6872128),
                                    new GLatLng(10.8165455, 106.6876032)
                                  ], "#66FF33", 5);
                                  map.addOverlay(polyline); */
     <% } %>  --%>
      }
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
	    	        var marker = new GMarker(point);
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
	  	function searchTT()
	  	{
	  		
	  		document.forms['bdForm'].action.value= 'viewBdTT';
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
<input type="hidden" name="view" value='lotrinhOnline'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="trungtam" id="trungtam" value=''>

<div id="main" style="width:99.5%; padding-left:2px">

	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:40%; padding:5px; float:left" class="tbnavigation">
        	<%=" "+url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	<%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%= userTen %> &nbsp;&nbsp;
        </div>
    </div>
    
    <div style="width:100%;" align="left">
   	<FIELDSET>
		<LEGEND class="legendtitle"><%=ChuyenNgu.get("Thông báo",nnId) %></LEGEND>
		<textarea name="dataerror"
			style="width: 100%; color: #F00; font-weight: bold"
			readonly="readonly" style="width: 100%" rows="1"><%= bdBean.getMsg() %></textarea>
	</FIELDSET>
    <fieldset>
    	<legend class="legendtitle"><%=ChuyenNgu.get("Thông tin lộ trình online",nnId) %></legend>
        <TABLE width="100%" cellpadding="4" cellspacing="0" style="background-color: #C5E8CD;">	   
            <TR>
                <TD class="plainlabel" width="150px" ><%=ChuyenNgu.get("Ngày",nnId) %> </TD>
                <TD class="plainlabel" width="200px" colspan="5">
                    <input type="text" size="11" class="days" 
                          id="ngay" name="ngay" value="<%= bdBean.getdate() %>" maxlength="10" readonly onchange="submitform();"  />
                </TD>
            </TR>
            <TR>
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
               <TD class="plainlabel" ><%=ChuyenNgu.get("Khu vực",nnId) %> </TD>
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
                </TD>
                
            
			</TR>
			<TR>
			
                <TD class="plainlabel" ><%=ChuyenNgu.get("Chi nhánh/Nhà phân phối",nnId) %> </TD>
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
                	<TD class="plainlabel" ><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %> </TD>
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
            	
               
            
                 
              
            </TR>
            <TR>
                
               
                <td class="plainlabel"><%=ChuyenNgu.get("Tổng số NVBH",nnId) %> </td>
	
				 <td class="plainlabel"><%=bdBean.getSoNVBH() %> </td>
				 
				  <td class="plainlabel"><%=ChuyenNgu.get("NVBH đang online",nnId) %> </td>
	
				 <td class="plainlabel"><%=bdBean.getSoNVBHOnline() %> </td>
                
				
            </TR>
            <TR >                                                                          
         	<td class="plainlabel"><%=ChuyenNgu.get("Giải thích",nnId) %> </td>
			  <TD class="plainlabel" colspan="6"  >
                     <img src="../images/ko_doanh_so.png" width="30" height="30" alt=""><%=ChuyenNgu.get("Viếng thăm Khách Hàng",nnId) %>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
                   <!--  <img src="../images/maps_human.png" width="30" height="30" alt=""> KH có doanh số &nbsp;&nbsp;&nbsp;&nbsp; -->
				
			
                	<img src="../images/greenflag.png" alt="" width="16"><%=ChuyenNgu.get("Vị trí đã đi qua",nnId) %>  &nbsp;&nbsp;&nbsp;&nbsp;
		        
              
          		<img src="../images/blank.png" alt="" width="16"><%=ChuyenNgu.get("Bắt đầu/Hiện tại",nnId) %>  &nbsp;&nbsp;&nbsp;&nbsp;
           
               </TD>
			                 
            </TR>

			
            <tr>
			<TD class="plainlabel" ><a class="button2" href="javascript:search();"> 
				
				 <img style="margin-bottom: -10px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Lộ trình NVBH",nnId) %>   </a>
						  </TD>
			
			<!-- <TD class="plainlabel" ><a class="button2" href="javascript:searchTT();"> 
				
				 <img style="margin-bottom: -10px;" src="../images/button.png" alt=""> Lộ trình PTT   </a>
						  </TD> -->
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
if(khList != null)
	khList.close();
if(nppRs != null)
	nppRs.close();
if(ddkd != null)
	ddkd.close();
if(vungRs != null)
	vungRs.close();
bdBean.DBclose(); 

%>

<%}%>
