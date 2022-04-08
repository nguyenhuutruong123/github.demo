<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.ctkhuyenmai.*" %>
<%@ page  import = "geso.dms.distributor.beans.ctkhuyenmai.imp.*" %>
<%@ page  import = "geso.dms.distributor.beans.dieukienkhuyenmai.IDieukienkhuyenmai" %>
<%@ page  import = "geso.dms.distributor.beans.dieukienkhuyenmai.imp.*" %>
<%@ page  import = "geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai" %>
<%@ page  import = "geso.dms.distributor.beans.trakhuyenmai.imp.*" %>
<%@ page  import = "geso.dms.center.beans.dondathang.imp.*" %>
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

<% XLkhuyenmaiTT xlkm = (XLkhuyenmaiTT)session.getAttribute("xlkm"); %>
<% List<ICtkhuyenmai> ctkmAvaiable = xlkm.getCtkmList(); %>
<% List<ICtkhuyenmai> listCTKM = xlkm.getCtkmResual(); %>
<% Hashtable<String, Integer> sanpham_soluong = xlkm.getHashA(); %>

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
			var Show_All = document.getElementById("Show_All");
  			//alert(ShowAll.checked);
  			if(Show_All.checked)
  			{
  				alert('Có chương trình dùng chung sản phẩm trong điều kiện. Vui lòng điều chỉnh lại.');
  				return;
  			}
			
 			<% if(xlkm.getDieuchinh() == false){%>
	  			 if(checkSoluong() == false)
	  			 {
	  				 alert('Vui lòng điều chỉnh lại số lượng sản phẩm khuyến mãi');
	  				 return;
	  			 }
	  		<%} %>	

	  		var schemeList = document.getElementsByName("schemeList");
	  		var schemeOR = document.getElementsByName("schemeOR");
	  		
	  		for(pos = 0; pos < schemeList.length; pos++)
	  		{
	  			var trakmId = document.getElementsByName(schemeList.item(pos).value + ".trakmId");
	  			var trakmType = document.getElementsByName(schemeList.item(pos).value + ".trakmType");
	  			var trakmHinhthuc = document.getElementsByName(schemeList.item(pos).value + ".trakmHinhThuc");
	  			var trakmPrimary = document.getElementsByName(schemeList.item(pos).value + ".trakmPrimary");
	  			var tongsoxuat = document.getElementById(schemeList.item(pos).value + ".tongsoxuat");
	  			
	  			if(schemeOR.item(pos).value == "false")
	  			{
		  			for(j = 0; j < trakmId.length; j++)
		  			{
		  				if(trakmType.item(j).value == "3" && trakmHinhthuc.item(j).value == "2")
		  				{
		  					if(trakmPrimary.item(j).value == 0 && parseInt(tongsoxuat.value) >= 1 )  //tra sanpham (0),   tra tien (1)
		  					{
		  						var elementName = schemeList.item(pos).value + "." + trakmId.item(j).value + ".spSelected";
						  		var spList = document.getElementById(elementName);
					 			
				 				if(spList.value == "")
				 				{
				 					alert("Vui lòng chọn sản phẩm trả khuyến mãi cho các xuất khuyến mãi được chọn..");
				 					return;
				 				}
		  					}
		  				}
		 			}
	  			}
	  			else
	  			{
	  				var trakmSelected = document.getElementsByName(schemeList.item(pos).value + ".chon");
	  				
	  				var selected = '';
	  				if(trakmSelected.length <= 1)
	  					selected = trakmSelected.item(0).value;
	  				
	  				for(jj = 0; jj < trakmSelected.length; jj++)
	  				{
	  					if(trakmSelected.item(jj).checked)
	  					{
	  						selected = trakmSelected.item(jj).value;
	  					}
	  				}
	  				
	  				if(selected == '')
	  				{
	  					alert("Vui lòng chọn trả khuyến mãi cho chương trình khuyến mãi được chọn..");
	 					return;
	  				}
	  				
	  				for(j = 0; j < trakmId.length; j++)
		  			{
		  				if(trakmType.item(j).value == "3" && trakmHinhthuc.item(j).value == "2")
		  				{
		  					if(trakmId.item(j).value == selected)
		  					{
		  						if(trakmPrimary.item(j).value == 0 && parseInt(tongsoxuat.value) >= 1)  //tra sanpham (0),   tra tien (1)
			  					{
			  						var elementName = schemeList.item(pos).value + "." + trakmId.item(j).value + ".spSelected";
							  		var spList = document.getElementById(elementName);
						 			
					 				if(spList.value == "")
					 				{
					 					alert("Vui lòng chọn sản phẩm trả khuyến mãi cho các xuất khuyến mãi được chọn..");
					 					return;
					 				}
			  					}
		  					}
		  				}
		 			}
	  			}
	  		}
			
 			 //check ngan sach
 			 if(checkNganSach() == false)
 			 {
 				alert('Xin lỗi, ngân sách trả khuyến mại không thể vượt quá ngân sách chương trình ...\n(Vui lòng điều chỉnh lại số lượng các sản phẩm sau khuyến mãi)');
				return;
 			 }
 			 
 			 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
 			 document.getElementById("btnSave30").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
 		  	 document.forms['kmForm'].action.value='save';
 		     document.forms['kmForm'].submit();
 		     
 		 }
 		 
 		 function checkNganSach()
 		 {
 			var ttTrakm = document.getElementsByName('ttTrakm');
			var nganSachCtkm = document.getElementsByName('nganSachCtkm');
			for(i = 0; i < ttTrakm.length; i++)
			{	
				if( (parseFloat(ttTrakm.item(i).value) > parseFloat(nganSachCtkm.item(i).value)) && (parseFloat(nganSachCtkm.item(i).value != -1)) )
				{
					return false;
				}			
			}
			return true;
 		 }
  		 
  		function DieuChinh(idRow)
		{
			var content = document.getElementById('content');
			var row = content.getElementsByTagName('tr');
			for(i = 1; i < row.length; i++)
			{
				if(row.item(i).id == idRow)
				{							
					var columns = row.item(i).getElementsByTagName('td');	
					var rowSpan = 1;
					
					if(columns.length == 11)
						rowSpan = columns.item(1).getAttribute('rowspan');
					else
					{
						if(columns.length == 6)
							rowSpan = columns.item(0).getAttribute('rowspan');
					}					
					var sd = parseInt(i) + parseInt(rowSpan); //luu y khi cong gia tri kieu number trong javascript					
					var soxuat = 0;				
					var id_soxuat = idRow.substring(0, idRow.lastIndexOf('.'));
					if(parseInt(document.getElementById(id_soxuat +'.soxuat').value) >= 0)
						soxuat = parseInt(document.getElementById(id_soxuat + '.soxuat').value);
			
					// var old = document.getElementById(id_soxuat + '.hidden');						
					for(j = i; j < sd; j++)
					{
						var column = row.item(j).getElementsByTagName('td');
						var pos = 1;
						if(column.length == 11)
							pos = 3;
						else
						{
							if(column.length == 6)
								pos = 2;
						}
						var inp = column.item(pos).getElementsByTagName('input');						
						if((parseInt(inp.item(1).value)  * soxuat) > parseInt(column.item(pos + 1).innerHTML))
						{
							alert('Số xuất bạn nhập không hơp lệ,vui lòng nhập số xuát nhỏ hơn');
							document.getElementById(id_soxuat + '.soxuat').value = "";
							return;
						}						
					}
					
					//dieu chinh lai tong soxuat					
					var id_tsx = id_soxuat.substring(0, id_soxuat.lastIndexOf('.'));
					var tsx = document.getElementById(id_tsx + '.tongsoxuat');	
					
					var start = document.getElementById(id_tsx + '.pos').value;
					start = parseInt(start) + 1; //dong 0, header
					var rowspan = document.getElementById(id_tsx + '.rowspan').value;
					
					//tinh lai tong so xuat khuyen mai
					var tong_soxuat = 0;
					for(h = 0; h < parseInt(rowspan); h++)
					{
						var index = parseInt(start) + parseInt(h);
						var cols = row.item(index).getElementsByTagName('td');
						
						if(cols.length == 11)
						{
							var inp_soxuat = cols.item(6).getElementsByTagName('input');
							tong_soxuat = parseInt(inp_soxuat.item(0).value);
						}
						else
						{
							if(cols.length == 6)
							{
								var inp_soxuat = cols.item(5).getElementsByTagName('input');
								var inp_dong = cols.item(0).getElementsByTagName('input');
								var pt = parseInt(inp_dong.item(1).value);
								if(pt == 1) //phep AND
									tong_soxuat = parseInt(tong_soxuat) > parseInt(inp_soxuat.item(0).value) ? parseInt(inp_soxuat.item(0).value) : parseInt(tong_soxuat);
								else
									tong_soxuat = parseInt(tong_soxuat) + parseInt(inp_soxuat.item(0).value);
							}
						}
					}
					tsx.value = parseInt(tong_soxuat) >= 0 ? parseInt(tong_soxuat) : 0;	
					
					//thoa dk slg sp < tong slg moi tien hanh dieu chinh lai slg su dung
					var rowCount = parseInt(start) + parseInt(rowspan);
					for( j = i; j < sd; j++)
					{
						var column = row.item(j).getElementsByTagName('td');
						var pos = 1;
						if(column.length == 11)
							pos = 3;
						else
						{
							if(column.length == 6)
								pos = 2;								
						}
						input = column.item(pos).getElementsByTagName('input');
						input.item(0).value = parseInt(soxuat) * parseInt(input.item(1).value);
						column.item(pos+2).innerHTML = parseInt(column.item(pos+1).innerHTML) - parseInt(input.item(0).value);

						var inps = column.item(pos - 1).getElementsByTagName('input');				
						for( h = j+1; h < rowCount; h++)
						{
							var cols = row.item(h).getElementsByTagName('td');
							var k = 0; 
							if(cols.length == 11)
								k = 2;
							else
							{
								if(cols.length == 6)
									k = 1;
							}
							var inp = cols.item(k).getElementsByTagName('input');	
							if(inp.item(0).value == inps.item(0).value)
							{
								var tem = cols.item(k+1).getElementsByTagName('input');
								cols.item(k+2).innerHTML = columns.item(pos+2).innerHTML;
								cols.item(k+3).innerHTML = parseInt(cols.item(k+2).innerHTML) - tem.item(0).value;
								if(parseInt(cols.item(k+3).innerHTML) < 0)
								{
									cols.item(k+3).innerHTML = cols.item(k+2).innerHTML;
									tem.item(0).value = 0;
								}
								break;
							}
						}							
					}
															
					//dieu chinh trakhuyenmai
					if(document.getElementById(id_tsx + '.trakhuyenmai.type') != null)
					{
						var type = document.getElementById(id_tsx + '.trakhuyenmai.type').value;
						var giatri = document.getElementById(id_tsx + '.trakhuyenmai.giatri').value;
						var trakm = document.getElementById(id_tsx + '.trakhuyenmai');
						var tonggiatridh = document.getElementById(id_tsx + '.tonggiatridh');
						if(parseInt(type) == 1)
							trakm.value = parseFloat(giatri) * parseInt(tsx.value);
						else
						{
							if(parseInt(type) == 2)
								trakm.value = (parseFloat(giatri)/100) * parseInt(tonggiatridh.value);
							else
								trakm.value = parseFloat(giatri) * parseInt(tsx.value);
						}
					}
				}
			}
		}			
  		
  		function DieuChinh2(idRow) //truong hop tong tien
		{
			var content = document.getElementById('content');
			var row = content.getElementsByTagName('tr');
			for(i = 1; i < row.length; i++)
			{
				if(row.item(i).id == idRow)
				{							
					var columns = row.item(i).getElementsByTagName('td');	
					var pos = 1; //luu vitri cua columns dang nhan nut
					if(columns.length == 11)
						pos = 3;
					else
					{
						if(columns.length == 6)
							pos = 2;
					}					
					var slg_sudung = 0;
					if(parseInt(document.getElementById(idRow + '.sudung').value) >= 0)
						slg_sudung = document.getElementById(idRow + '.sudung').value;
					
					var old_slg = document.getElementById(idRow + '.hidden');				
					var sum = columns.item(pos+1).innerHTML;
	
					if( parseInt(slg_sudung) > parseInt(sum) )
					{
						alert('Số lượng sử dụng phải nhỏ hơn tổng số lượng hiện có');
						document.getElementById(idRow + '.sudung').value = old_slg.value;
						return;
					}
					var dongia = document.getElementById(idRow + '.dongia').value;
					
					var id_tgt = idRow.substring(0, idRow.lastIndexOf('.'));
					var tongtien = document.getElementById(id_tgt + '.tongtien').value;
					var old_tongGiatri = document.getElementById(id_tgt + '.tonggiatri');
					var soxuat = document.getElementById(id_tgt + '.soxuat');				
					old_tongGiatri.value = parseInt(old_tongGiatri.value) + ((slg_sudung - parseInt(old_slg.value)) * parseInt(dongia));
					
					var type = document.getElementById(id_tgt + '.type').value;
					if(parseInt(type) == 1) //lay min (type = 1, dkkm bat buoc phai co day du cac sp)
					{
						var so_xuat = parseInt(parseInt(old_tongGiatri.value) / parseInt(tongtien));						
						soxuat.value = parseInt(slg_sudung) > parseInt(so_xuat) ? parseInt(so_xuat) : parseInt(slg_sudung);						
					}
					else
						soxuat.value = parseInt(parseInt(old_tongGiatri.value) / parseInt(tongtien));
					
					old_slg.value = slg_sudung;
					
					//dieu chinh lai tong soxuat					
					var id_tsx = id_tgt.substring(0, id_tgt.lastIndexOf('.'));
					var tsx = document.getElementById(id_tsx + '.tongsoxuat');	
					
					var start = document.getElementById(id_tsx + '.pos').value;
					start = parseInt(start) + 1; //dong 0, header
					var rowspan = document.getElementById(id_tsx + '.rowspan').value;
					
					//tinh lai tong so xuat khuyen mai
					var tong_soxuat = 0;
					for(h = 0; h < parseInt(rowspan); h++)
					{
						var index = parseInt(start) + parseInt(h);
						var cols = row.item(index).getElementsByTagName('td');
						
						if(cols.length == 11)
						{
							var inp_soxuat = cols.item(6).getElementsByTagName('input');
							tong_soxuat = parseInt(inp_soxuat.item(0).value);
						}
						else
						{
							if(cols.length == 6)
							{
								var inp_soxuat = cols.item(5).getElementsByTagName('input');
								var inp_dong = cols.item(0).getElementsByTagName('input');
								var pt = parseInt(inp_dong.item(1).value);
								if(pt == 1) //phep AND
									tong_soxuat = parseInt(tong_soxuat) > parseInt(inp_soxuat.item(0).value) ? parseInt(inp_soxuat.item(0).value) : parseInt(tong_soxuat);
								else
									tong_soxuat = parseInt(tong_soxuat) + parseInt(inp_soxuat.item(0).value);
							}
						}
					}
					tsx.value = parseInt(tong_soxuat);
					
					//dieu chinh slg con lai
					columns.item(pos+2).innerHTML = parseInt(columns.item(pos+1).innerHTML) - parseInt(slg_sudung); 
					
					//slg con lai cua cac san pham ben duoi
					var sd = parseInt(rowspan) + parseInt(start);
					var inps = columns.item(pos - 1).getElementsByTagName('input');
					for(j = i+1; j < sd; j++)
					{
						var cols = row.item(j).getElementsByTagName('td');
						var k = 0; 
						if(cols.length == 11)
							k = 2;
						else
						{
							if(cols.length == 6)
								k = 1;
						}
						var inp = cols.item(k).getElementsByTagName('input');						
						if(inp.item(0).value == inps.item(0).value)
						{
							var tem = cols.item(k+1).getElementsByTagName('input');
							cols.item(k+2).innerHTML = columns.item(pos+2).innerHTML;
							cols.item(k+3).innerHTML = parseInt(cols.item(k+2).innerHTML) - tem.item(0).value;
							if(parseInt(cols.item(k+3).innerHTML) < 0)
							{
								cols.item(k+3).innerHTML = cols.item(k+2).innerHTML;
								tem.item(0).value = 0;
							}
							break;
						}					
					}
					
					//dieu chinh trakhuyenmai
					if(document.getElementById(id_tsx + '.trakhuyenmai.type') != null)
					{
						var type = document.getElementById(id_tsx + '.trakhuyenmai.type').value;
						var giatri = document.getElementById(id_tsx + '.trakhuyenmai.giatri').value;
						var trakm = document.getElementById(id_tsx + '.trakhuyenmai');
						var tonggiatridh = document.getElementById(id_tsx + '.tonggiatridh');
						if(parseInt(type) == 1)
							trakm.value = parseFloat(giatri) * parseInt(tsx.value);
						else
						{
							if(parseInt(type) == 2)
								trakm.value = (parseFloat(giatri)/100) * parseInt(tonggiatridh.value);
							else
								trakm.value = parseFloat(giatri) * parseInt(tsx.value);
						}
					}
				}
			}			
		}
  		
  		function DieuChinh3(idRow) //truong hop tong luong
		{
			var content = document.getElementById('content');
			var row = content.getElementsByTagName('tr');
			for(i = 1; i < row.length; i++)
			{
				if(row.item(i).id == idRow)
				{							
					var columns = row.item(i).getElementsByTagName('td');	
					var pos = 1; //luu vitri cua columns dang nhan nut
					if(columns.length >= 11)
						pos = 3;
					else
					{
						if(columns.length == 6)
							pos = 2;
					}
					
					var slg_sudung = 0;
					var idSlg_sudung = document.getElementById(idRow + '.sudung');
					if(idSlg_sudung != null)
					{
						if(parseInt(idSlg_sudung.value) >= 0)
							slg_sudung = document.getElementById(idRow + '.sudung').value;
					}
					//alert(slg_sudung);

					var old_slg = document.getElementById(idRow + '.hidden');				
					var sum = columns.item(pos+1).innerHTML;
	
					if( parseInt(slg_sudung) > parseInt(sum) )
					{
						alert('Số lượng sử dụng phải nhỏ hơn tổng số lượng hiện có ');
						document.getElementById(idRow + '.sudung').value = old_slg.value;
						return;
					}
					
					var id_tongluong = idRow.substring(0, idRow.lastIndexOf('.'));
					var tongluong = document.getElementById(id_tongluong + '.tongluong').value;
					var old_tongSoluong = document.getElementById(id_tongluong + '.tongsoluong');
					var soxuat = document.getElementById(id_tongluong + '.soxuat');
										
					old_tongSoluong.value = parseInt(old_tongSoluong.value) + (slg_sudung - parseInt(old_slg.value));
														
					var type = document.getElementById(id_tongluong + '.type').value;
					if(parseInt(type) == 1)
					{
						var so_xuat = parseInt(parseInt(old_tongSoluong.value) / parseInt(tongluong));						
						soxuat.value = parseInt(slg_sudung) > parseInt(so_xuat) ? parseInt(so_xuat) : parseInt(slg_sudung);	
					}
					else
						soxuat.value = parseInt(parseInt(old_tongSoluong.value) / parseInt(tongluong));
					
					old_slg.value = slg_sudung;
					
					//dieu chinh lai tong soxuat					
					var id_tsx = id_tongluong.substring(0, id_tongluong.lastIndexOf('.'));
					var tsx = document.getElementById(id_tsx + '.tongsoxuat');	
					
					var start = document.getElementById(id_tsx + '.pos').value;
					start = parseInt(start) + 1; //dong 0, header
					var rowspan = document.getElementById(id_tsx + '.rowspan').value;
								
					//tinh lai tong so xuat khuyen mai
					var tong_soxuat = 0;
					for(h = 0; h < parseInt(rowspan); h++)
					{
						var index = parseInt(start) + parseInt(h);
						var cols = row.item(index).getElementsByTagName('td');
						
						if(cols.length >= 11)
						{
							var inp_soxuat = cols.item(6).getElementsByTagName('input');
							tong_soxuat = parseInt(inp_soxuat.item(0).value);
						}
						else
						{
							if(cols.length == 6)
							{
								var inp_soxuat = cols.item(5).getElementsByTagName('input');
								var inp_dong = cols.item(0).getElementsByTagName('input');
								var pt = parseInt(inp_dong.item(1).value);
								if(pt == 1) //phep AND
									tong_soxuat = parseInt(tong_soxuat) > parseInt(inp_soxuat.item(0).value) ? parseInt(inp_soxuat.item(0).value) : parseInt(tong_soxuat);
								else
									tong_soxuat = parseInt(tong_soxuat) + parseInt(inp_soxuat.item(0).value);
							}
						}
					}
					tsx.value = parseInt(tong_soxuat);
									
					//dieu chinh slg con lai
					columns.item(pos+2).innerHTML = parseInt(columns.item(pos+1).innerHTML) - parseInt(slg_sudung); 
					
					//slg con lai cua cac san pham ben duoi
					var sd = parseInt(rowspan) + parseInt(start);
					var inps = columns.item(pos - 1).getElementsByTagName('input');
					for(j = i+1; j < sd; j++)
					{
						var cols = row.item(j).getElementsByTagName('td');
						var k = 0; 
						if(cols.length >= 11)
							k = 2;
						else
						{
							if(cols.length == 6)
								k = 1;
						}
						var inp = cols.item(k).getElementsByTagName('input');					
						if(inp.item(0).value == inps.item(0).value)
						{
							alert('abbc');
							var tem = cols.item(k+1).getElementsByTagName('input');
							cols.item(k+2).innerHTML = columns.item(pos+2).innerHTML;
							cols.item(k+3).innerHTML = parseInt(cols.item(k+2).innerHTML) - tem.item(0).value;
							if(parseInt(cols.item(k+3).innerHTML) < 0)
							{
								cols.item(k+3).innerHTML = cols.item(k+2).innerHTML;
								tem.item(0).value = 0;
							}
							break;
						}					
					}
					/*
					//dieu chinh trakhuyenmai
					if(document.getElementById(id_tsx + '.trakhuyenmai.type') != null)
					{
						var type = document.getElementById(id_tsx + '.trakhuyenmai.type').value;
						var giatri = document.getElementById(id_tsx + '.trakhuyenmai.giatri').value;
						var trakm = document.getElementById(id_tsx + '.trakhuyenmai');
						var tonggiatridh = document.getElementById(id_tsx + '.tonggiatridh');
						if(parseInt(type) == 1)
							trakm.value = parseFloat(giatri) * parseInt(tsx.value);
						else
						{
							if(parseInt(type) == 2)
								trakm.value = (parseFloat(giatri)/100) * parseInt(tonggiatridh.value);
							else
								trakm.value = parseFloat(giatri) * parseInt(tsx.value);
						}
					}
					*/
				}
			}
		}
  		
  		function DieuChinhTraKM(optionId)
  		{
  			var id = document.getElementById(optionId);
  			
			for(i=0; i < id.length; i++)
			{
				if(id.options[i].selected)
				{
					var sanphamId = document.getElementById(optionId + '.Id');
					sanphamId.value = id.options[i].value;
				}
			}
  		}
  		
  		function TinhTongSanPham(masp)
		{
			var sl = 0;
			var content = document.getElementById('content');
			var row = content.getElementsByTagName('tr');
			for(i = 1; i < (parseInt(row.length)-1); i++)
			{
				var columns = row.item(i).getElementsByTagName('td');				
				var pos = 0;
				if(columns.length == 11)
					pos = 2;
				else
				{
					if(columns.length == 6)
						pos = 1;
				}
				
				//alert(columns.item(pos).getElementsByTagName('input').item(1).value);
				
				if(parseInt(columns.item(pos).getElementsByTagName('input').length) > 0)
				{
					var inp_sp = columns.item(pos).getElementsByTagName('input');
					
					var loaict = columns.item(pos).getElementsByTagName('input').item(1).value;
					
					if(inp_sp.item(0).value == masp)
					{				
						var inp_slg = columns.item(pos + 1).getElementsByTagName('input');
						
						if(loaict != 2)
							sl =  parseInt(sl) + parseInt(inp_slg.item(0).value);
					}
				}
			}
			return sl;
		}
  		
  		function checkSoluong()
  		{
  			var content = document.getElementById('content');
			var row = content.getElementsByTagName('tr');
  			var spList = document.getElementsByName('sanphamList');
  			
  			var flag = true;
  			for( k = 0; k < spList.length; k++)
  			{
  				var sp = spList.item(k).value;
  				var name = sp.substring(0, sp.indexOf('--'));
  				var soluong = sp.substr(sp.indexOf('--') + 2);
  				
  				if(TinhTongSanPham(name) > parseInt(soluong))
  				{
  					changeBackground('#CFF', name);
  					flag = false;
  				}	
  				else
  					changeBackground('#E6E6E6', name);
  			}
  			return flag;
  		}
  		
  		function changeBackground(color, name)
  		{
  			var content = document.getElementById('content');
			var row = content.getElementsByTagName('tr');
  			for(l = 1; l < (parseInt(row.length)-1); l++)
			{
				var columns = row.item(l).getElementsByTagName('td');
				var pos = 0;
				if(columns.length == 11)
					pos = 2;
				else
				{
					if(columns.length == 6)
						pos = 1;
				}
				if(columns.item(pos).getElementsByTagName('input').length > 0)
				{
					var input = columns.item(pos).getElementsByTagName('input');
					if(input.item(0).value == name)
					{
 						columns.item(pos).style.backgroundColor = color;
 						columns.item(pos + 1).style.backgroundColor = color;
					}	
				}
			}
  		}
  
  		function checkSpIds() //kiem tra ma san pham tra km da duoc chon so luong hay chua
		{
			var sp = document.getElementsByName('spSelected');
			var trakmType = document.getElementsByName('traKmType');
			for(i = 0; i < sp.length; i++)
			{	
				if(trakmType.item(i).value == "3-2") //tra km: type = 3; hinhthuc=2
				{
					if(sp.item(i).value == "")
						return false;
				}			
			}
			return true;
		}
  		
  		function Auto(checkId)
  		{
  			var chkId = document.getElementById(checkId);
  			var idTlg = checkId.substring(0, checkId.lastIndexOf("."));
  			
  			var k = document.getElementById(idTlg + ".tongluong").value;  			
  			var slg = document.getElementsByName(idTlg + ".soluong");
  			var masp = document.getElementsByName(idTlg + ".maspTraKm");
  			var dgiaSpTraKm = document.getElementsByName(idTlg + ".dongiaSpTraKm");
  			var tonkhoSpTraKm = document.getElementsByName(idTlg + ".tonkhoSpTraKm");
  			
  			var idTkm = idTlg.substring(0, idTlg.indexOf("."));
  			
  			spIds = idTlg.substring(0, idTlg.indexOf("."));
  			var sanphamId = document.getElementById(idTlg + '.spSelected');
  				
  			var tongsotien = 0;
  			sanphamId.value = ""; //khoi tao lai
  			if(chkId.checked)
			{
  				var pos = 0;
  				var max = -1;
  				for(i = 0; i < slg.length; i++)
  				{
  					slg.item(i).value = "";
  					slg.item(i).setAttribute("readonly", "readonly");
  					
  					if( parseFloat(tonkhoSpTraKm.item(i).value ) >  max )
  					{
  						max = tonkhoSpTraKm.item(i).value;
  						pos = i;
  					}
  				}
  				slg.item(pos).value = k;
  				
  				sanphamId.value = masp.item(pos).value + '-' + k;	//luu lai sp chon, soluong tuong ung
  				
  				//tinh tong gia tri
  				tongsotien = parseFloat(tongsotien) + parseInt(k) * parseFloat(dgiaSpTraKm.item(pos).value);
  				
  				//Luu gia tri trakm tung dong
  				document.getElementById(idTlg + ".tonggiatriTKM").value = Math.round(tongsotien);
  				
  				//var tongFix = document.getElementById(idTkm + ".trakhuyenmaiFix").value;
  				//tongsotien = parseFloat(tongsotien); //+ parseFloat(tongFix);
  				//document.getElementById(idTkm + ".tonggiatriTKM").value = Math.round(tongsotien);
  				
  				CapNhatTienKM(idTkm);
			}
  			else
			{
  				for(i = 0; i < slg.length; i++)
  				{
  					slg.item(i).value = "";
  					slg.item(i).removeAttribute("readonly");
  				}
  				sanphamId.value = "";
  				
  				//Luu gia tri trakm tung dong
  				document.getElementById(idTlg + ".tonggiatriTKM").value = "";
  				
  				//var tongFix = document.getElementById(idTkm + ".trakhuyenmaiFix").value;
  				//document.getElementById(idTkm + ".trakhuyenmai").value = tongFix;
  				
  				CapNhatTienKM(idTkm);
			}
  		}
  		
  		function checkSlgTraKm(name)
  		{
  			var slg = document.getElementsByName(name);
  			var idTlg = name.substring(0, name.lastIndexOf("."));
  			
  			var k = document.getElementById(idTlg + ".tongluong").value;
  			var masp = document.getElementsByName(idTlg + ".maspTraKm");
  			var dgiaSpTraKm = document.getElementsByName(idTlg + ".dongiaSpTraKm");
  			
  			var idTkm = idTlg.substring(0, idTlg.indexOf("."));
  			
  			spIds = idTlg.substring(0, idTlg.indexOf("."));
  			var sanphamId = document.getElementById(idTlg + '.spSelected');
  			
  			var tongsotien = 0;
  			var sum = 0;
  			//alert(slg.length + ' --- ' + k);
  			sanphamId.value = "";
  			for(i = 0; i < slg.length; i++)
  			{
  				if(slg.item(i).value != '')
  				{
  					sum = parseInt(sum) + parseInt(slg.item(i).value);
  					tongsotien = parseFloat(tongsotien) + parseInt(slg.item(i).value) * parseFloat(dgiaSpTraKm.item(i).value);
  				}
  				if(parseInt(sum) > parseInt(k))
  				{
  					tongsotien = parseFloat(tongsotien) - parseInt(slg.item(i).value) * parseFloat(dgiaSpTraKm.item(i).value);
  					slg.item(i).value = "";
  					document.getElementById(idTlg + ".tonggiatriTKM").value = Math.round(tongsotien);
  					alert('Số lượng bạn nhập vào,lớn hơn tổng số lượng trả khuyến mãi (' + k + '), Vui lòng nhập lại ...');
  					return;
  				}
  				if(slg.item(i).value != "")
  					sanphamId.value = sanphamId.value + masp.item(i).value + '-' + slg.item(i).value + ";";
  			}
			
  			//var tongFix = document.getElementById(idTkm + ".trakhuyenmaiFix").value;
  			//tongsotien = parseFloat(tongsotien) + parseFloat(tongFix);
  			
  			//document.getElementById(idTkm + ".trakhuyenmai").value = Math.round(tongsotien);
  			document.getElementById(idTlg + ".tonggiatriTKM").value = Math.round(tongsotien);
  			
  			CapNhatTienKM(idTkm);
  			
  			if(sanphamId.value != "")
  				sanphamId.value = sanphamId.value.substring(0, sanphamId.value.length - 1); //cat dau ; cuoi cung
  		}
  		
  		function CapNhatTienKM(idTkm)
  		{
  			var schemeList = document.getElementsByName("schemeList");
  			var tongthanhtien = 0;
	  		for(pos = 0; pos < schemeList.length; pos++)
	  		{
	  			if(schemeList.item(pos).value == idTkm)
	  			{
	  				var trakmId = document.getElementsByName(schemeList.item(pos).value + ".trakmId");
		  			
		  			for(j = 0; j < trakmId.length; j++)
		  			{
		  				var idGiatriTKM = schemeList.item(pos).value + "." + trakmId.item(j).value + ".tonggiatriTKM";

		  				var tonggiatriTKM = document.getElementById(idGiatriTKM).value;
		  				if(tonggiatriTKM != "")
		  				{
		  					tongthanhtien = parseFloat(tongthanhtien) + parseFloat(tonggiatriTKM);
		  				}
		  			}
	  			}	
	  		}
	  		//alert('tong thanh tien ' + tongthanhtien);
	  		document.getElementById(idTkm + ".trakhuyenmai").value = tongthanhtien;
  		}
  		
  		function thongbao()
  		{
  			var tt = document.forms['kmForm'].msg.value;
  			if(tt.length>0)
  		    {
  				alert(tt);
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


<form name="kmForm" method="post" action="../../KhuyenmaiTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%=xlkm.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<INPUT type="hidden" name="dhId" value='<%=xlkm.getIdDonhang() %>'>
<INPUT type="hidden" name="ngaygiaodich" value='<%=xlkm.getNgaygiaodich() %>'>
<INPUT type="hidden" name="tonggiatri" value='<%=xlkm.getTonggiatriDh() %>'>
<input type="hidden" name="khachhang" value='<%=xlkm.getKhachhang() %>'>
<input type="hidden" name="hinhthuc" value='<%= 0 %>'>
<input type="hidden" name="loaidonhang" value='<%= xlkm.getLoaiDonHang() %>'>

<input type="hidden" name="msg" value='<%= xlkm.getMsg() %>'>
<script type="text/javascript">
  thongbao();
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr height="25px">
		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tồn kho TT > Đơn đặt hàng > Áp khuyễn mãi > Điều chỉnh</TD>
		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= userId %> &nbsp;</TD>
	</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<TR class="tbdarkrow">
		<TD align="left" width="30"><A href = "../../ErpDondathangUpdateSvl?userId=<%= userId %>&id=<%= xlkm.getIdDonhang() %>"><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
	    <TD align="left" width="30">
	    <div id="btnSave30">
	    <A href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A>
	    </div>
	    </TD>
	    <TD></TD>
	</TR>
</table>
 <fieldset  >
 	<legend class="legendtitle">Di chuyển mã chương trình khuyễn mãi (CTKM) bạn muốn ưu tiên</legend>
 	<table class="plainlabel" width="100%" border="0" cellspacing="1" cellpadding="2">
 	<tr height="8px"><td></td></tr>
 	<tr><td>
    <ul id="sortable" style="cursor:pointer; width:60%">    
    <%
    	int m = 0;
    	while(m < listCTKM.size())
    	{
    		Ctkhuyenmai ctkm = (Ctkhuyenmai)listCTKM.get(m);
    		String diengiai = ctkm.getDiengiai(); 		
    %>
		<li onMouseOver="this.style.color='#F00'" onMouseOut="this.style.color='#000'"><%= ctkm.getscheme() + " - " + diengiai %> 
			<input type="hidden" name="Scheme" value="<%= ctkm.getscheme() %>"></li>
    <% m++; } %>     	
    </ul></td></tr>
    <% if(xlkm.getDieuchinh() == false && xlkm.getDungDieuKien() == true){ %>
    	<tr><td><input type="checkbox" id="Show_All" name="ShowAll" checked="checked" value='1' onchange="submitform()"><i> Hiện tất cả các xuất khuyến mãi ( tối đa ) có thể đạt được của đơn hàng </i></td></tr>
    <%}else{ %>
    	<tr><td><input type="checkbox" id="Show_All" name="ShowAll"  value='0' onchange="submitform()"><i> Hiện tất cả các xuất khuyến mãi ( tối đa ) có thể đạt được của đơn hàng </i></td></tr>
    <%} %>
    </table>
</fieldset>

<fieldset>
	<legend><span class="legendtitle"> &nbsp;Danh sách chương trình khuyến mãi ( CTKM ) thỏa điều kiện </span>&nbsp;&nbsp;&nbsp;
         <a class="button" href="javascript:submitform()">
              Hiển thị danh sách <img style="top: -4px;" src="../images/New.png" alt=""></img></a>
    </legend>
    <table class="tabledetail" id="content" width="100%" border="0" cellspacing="1" cellpadding="2">
    	<tr class="tbheader">
        	<th width="10%"  align="center">Mã CTKM</th>
            <th width="7%" align="center" style="display: none" >Điều kiện</th>
            <th width="19%" align="center">Sản phẩm mua</th>
            <th width="7%" align="center">Sử dụng</th>
            <th width="7%" align="center">Tổng lượng</th>
            <th width="6%" align="center">Còn lại</th>
            <th width="5%" align="center" style="display: none">Số suất / DKKM</th>
            <th width="5%" align="center" >Số suất</th>
            <th width="19%" align="center">Trả khuyến mãi</th>
            <th width="8%" align="center">Tổng giá trị</th>
            <th width="8%" align="center">Ngân sách</th>
        </tr>
             <% //in table ketqua.....
        
        int pos = 0;
        for(int i = 0; i < listCTKM.size(); i++)
		{
			Ctkhuyenmai ctkm = (Ctkhuyenmai)listCTKM.get(i);
			
			int rowSpan = xlkm.getRowspan(ctkm);
			
			long tongtienTraKM = 0;
			
			for(int count = 0; count < ctkm.getTrakhuyenmai().size(); count++)
			{
				Trakhuyenmai trakmTien = (Trakhuyenmai)ctkm.getTrakhuyenmai().get(count);
				//System.out.print("\nTra Khuyen Mai Value: " + Float.toString(trakmTien.getTongGtriKm()) + "\n" );
				
				if(trakmTien.getType() == 1)
					tongtienTraKM += Math.round(ctkm.getSoxuatKM() * trakmTien.getTongtien());
				else
				{
					if(trakmTien.getType() == 2) //trachietkhau
					{
						tongtienTraKM += Math.round(ctkm.getTongTienTheoDKKM() * (trakmTien.getChietkhau() / 100));
					}
					else
					{
						if(ctkm.getKhoId().equals("100001") && trakmTien.getHinhthucPrimary().equals("1")) //primary tra tien
						{
							tongtienTraKM += Math.round(ctkm.getSoxuatKM() * trakmTien.getTongGtriKm());
						}
						else
						{
							if(trakmTien.getHinhthuc() == 1 )  //tra sp co dinh thi tinh dc tong gia tri, khong thi phai doi nguoi dung chon SP
								tongtienTraKM += Math.round(ctkm.getSoxuatKM() * trakmTien.getTongGtriKm());
						}
					}
				}
			}
			System.out.println("8899.Tong tien tra km: " + tongtienTraKM);
	
			if(tongtienTraKM > (ctkm.getNgansach() - ctkm.getDasudung()))
			{
			   tongtienTraKM = Math.round(ctkm.getNgansach() - ctkm.getDasudung());	  
			}
			 
			List<IDieukienkhuyenmai> dkkmList = ctkm.getDkkhuyenmai();
			for(int j = 0; j < dkkmList.size(); j++)
			{
				Dieukienkhuyenmai dkkm = (Dieukienkhuyenmai)dkkmList.get(j);
				float tonggiatri = xlkm.getTonggiatri(dkkm);
				int tongsoluong = xlkm.getTongsoluong(dkkm);
				
				List<ISanpham> spList = dkkm.getSanphamList();			
				if(spList.size() > 0)
				{	
					for(int k = 0; k < spList.size(); k++)
					{
						ISanpham sp = (ISanpham)spList.get(k);
						
						int slcan = 0;
						if(dkkm.getTongtien() <= 0 && dkkm.getTongluong() <= 0)
							slcan = sp.getSoluongcan() / dkkm.getSoxuatKM();
						String tensp = sp.getTensp();
						
						if(k == 0)
						{
							if(j == 0){%> 
								<tr class="tbdarkrow" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>">
								
					        	<td rowspan="<%= rowSpan %>" align="center">
					        		<%= ctkm.getscheme() %>
					        		<input type="hidden" id="<%= ctkm.getId() + ".pos" %>" value="<%= pos %>" />
					        		<input type="hidden" id="<%= ctkm.getId() + ".rowspan" %>" value="<%= rowSpan %>" />
					        		<input type="hidden" name="schemeList" value="<%= ctkm.getId() %>" />
					        		<input type="hidden" name="schemeDiengiai" value="<%= ctkm.getDiengiai() %>" />
					        		<input type="hidden" name="schemeOR" value="<%= ctkm.getTra_OR() %>" />
					        		<input type="hidden" name="schemePhanBo" value="<%= ctkm.getPhanbotheoDH() %>" />
					        	</td>	
					        					        						        	
					            <td rowspan="<%= spList.size() %>" align="center" style="display: none" >
					            	<div class="addspeech" title="<%= dkkm.getDiengiai() %>" ><%= dkkm.getId() %>
					            	<% if(dkkm.getType() == 1){ %>
					            		<sup style="color:#F00; font-size:0.8em">*</sup> <%} %>
					            	 </div>
					            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + ".type" %>" value="<%= dkkm.getType() %>" />
					            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + ".pheptoan" %>" value="<%= dkkm.getPheptoan() %>" />
					            </td> 
					            					                      
					            <td align="left">					    
					            	<%if(sp.getTensp().length() > 25){ %>
					            		<div class="addspeech" title="<%= sp.getTensp() %>"><%= tensp %>
					            	<% } else{%>
					            		<span><%= tensp %></span>
					            	<%} %>
					            	</div>
					            	<input type="hidden" value="<%= sp.getMasp() %>" />
					            	<input type="hidden" value="<%= ctkm.getLoaict() %>" />
					            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".dongia" %>" value="<%= sp.getDongia() %>" />
					            </td>
					                        					           				
           						<% if((dkkm.getTongtien()) > 0 || (dkkm.getTongluong() > 0)){ %>
       								<td align="center">
       									<%if(dkkm.getTongtien() > 0){ %>
       										<input type="text" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh2('<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>')" readonly="readonly"/>
       									<%}else{ %>
       										<input type="text" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh3('<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>')" readonly="readonly" />
       									<%} %>
       									<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".hidden" %>" value="<%= sp.getSoluongcan() %>" />
       								</td>
	       						<%}else{ %>
	       							<td align="center">
	       								<input type="text" size="5" disabled="disabled" value="<%= sp.getSoluongcan() %>" />
	       								<input type="hidden" value="<%= slcan %>" />
	       							</td>
	       						<%}%>
	       						           						          						
            					<td align="center"><%= sp.getSoluongAvaiable() + sp.getSoluongcan() %></td>
            					<td align="center"><%= sp.getSoluongAvaiable() %></td>
            				           								            				         					          					            
					       <% if(dkkm.getTongtien() > 0 || dkkm.getTongluong() > 0){ %>
					            	<td rowspan="<%= spList.size() %>" align="center" style="display: none">
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".soxuat" %>" type="text" size="5" disabled="disabled" value="<%= dkkm.getSoxuatKM() %>" />
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".hidden" %>" type="hidden" size="5" value="<%= dkkm.getSoxuatKM() %>"/>
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".tongtien" %>" type="hidden" value="<%= dkkm.getTongtien() %>"/>
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".tonggiatri" %>" type="hidden" value="<%= tonggiatri %>"/>
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".tongluong" %>" type="hidden" value="<%= dkkm.getTongluong() %>"/>
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".tongsoluong" %>" type="hidden" value="<%= tongsoluong %>"/>
					            	</td>
           						<%}else{ %>
           							<td rowspan="<%= spList.size() %>" align="center" style="display: none">
           								<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".soxuat" %>" type="text" size="5" value="<%= dkkm.getSoxuatKM() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh('<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>')" readonly="readonly" />
           								<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".hidden" %>" type="hidden" value="<%= dkkm.getSoxuatKM() %>"/>
           							</td>          							
           						<%}%>
           						
           						<td rowspan="<%= rowSpan%>" align="center" >
           							<input id="<%= ctkm.getId() + ".tongsoxuat"%>" type="text" size="5" value="<%= ctkm.getSoxuatKM() %>" name="soxuatKM" readonly/>
           						</td>
           						
           						
           						<td rowspan="<%= rowSpan%>" align="center" style="padding:0px">
           						<% 
           						for(int count = 0; count < ctkm.getTrakhuyenmai().size(); count++)
           						{
           							Trakhuyenmai trakm2 = (Trakhuyenmai)ctkm.getTrakhuyenmai().get(count); 
           							
           							System.out.println("7777.So xuat la: " + ctkm.getSoxuatKM() + " -- Hinh thuc: " + trakm2.getHinhthucPrimary() );
           							boolean flag = ( ( ctkm.getKhoId().equals("100001") && trakm2.getHinhthucPrimary().equals("1") ) 
           									|| ( (ctkm.getSoxuatKM() < 1) && trakm2.getHinhthucPrimary().equals("0") ) );
       
           							System.out.println("Flag la: " + flag);
           							
           							if( flag )
           							{ %>
           								Trả tiền
	     								<input type="hidden" name="<%= ctkm.getId() + ".trakmId" %>" value="<%= trakm2.getId() %>" />
	     								<input type="hidden" name="<%= ctkm.getId() + ".trakmType" %>" value="<%= trakm2.getType() %>" />
	     								<input type="hidden" name="<%= ctkm.getId() + ".trakmHinhThuc" %>" value="<%= trakm2.getHinhthuc() %>" />
	     								<input type="hidden" name="<%= ctkm.getId() + ".trakmPrimary" %>" value="<%= trakm2.getHinhthucPrimary() %>" />
	     								<input type="hidden" name="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" id="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" value="<%= Math.round(ctkm.getSoxuatKM() * trakm2.getTongGtriKm()) <= 0 ? Math.round(tongtienTraKM) : Math.round(ctkm.getSoxuatKM() * trakm2.getTongGtriKm()) %>" />
           							<% } else {
	           							if(trakm2.getType() == 3 && trakm2.getHinhthuc() == 2)
	           							{ 
	           								long tongluongNHAN = Math.round( ( trakm2.getTongluong( xlkm.getIdDonhang(), ctkm.getId(), ctkm.getSoxuatKM() ) ) - 0.5 );
	           								
	           								Hashtable<String, Integer> spSl = trakm2.getSanpham_Soluong(xlkm.getIdDonhang(), ctkm.getId(), ctkm.getSoxuatKM(), "2");
		       								Hashtable<String, Float> sp_dgia = trakm2.getsanpham_dongia();
		       								Hashtable<String, String> sp_tensp = trakm2.getsanpham_tensp();
		       								
		       								Enumeration<String> keys =  spSl.keys();
	       								%>	           								
	           								<fieldset>
	           								<legend>
	           								<% if(ctkm.getTra_OR()){ %>
	           									<input class="addspeech" type="radio" name="<%= ctkm.getId() + ".chon" %>" value="<%= trakm2.getId() %>" title="<%= "Chọn trả khuyến mại: " + trakm2.getId() + " - " + trakm2.getDiengiai()%>">
	           									<b>Tổng lượng KM <%= tongluongNHAN %></b>
	           								<%} else { %>
	           									<a class="addspeech" href="" title="<%= "Trả khuyến mại trong nhóm: " + trakm2.getId() + " - " + trakm2.getDiengiai()%>" onclick="return false;">
	           										<b>Tổng lượng KM <%= tongluongNHAN %></b></a>
	           								<%} %>
	           								</legend>
	           								
	           								<% while(keys.hasMoreElements())
	           								{
	           									String key = keys.nextElement(); 
	           									String spTen = sp_tensp.get(key);
	           									
	           									//System.out.println("__San pham ten la: " + spTen);
	           									//System.out.println("__Vi tri tim thay: " + spTen.indexOf("i: "));
	           									
	           									String tonhienTai = spTen.substring(spTen.indexOf("i: ") + 3, spTen.length() - 1);
	           									
	           									System.out.println("Ton hien tai: " + tonhienTai);
	           									
	           									%>			
	   											<p><span onMouseover="ddrivetip('<%= sp_tensp.get(key) + " - Giá " + sp_dgia.get(key) %>', 300)"; onMouseout="hideddrivetip()" style="font-size:10pt;"><%= key %></span> &nbsp;
	   											<input type="text" size="7" value="" style="text-align:right; width: 50px" name="<%= ctkm.getId() + "." + trakm2.getId() + ".soluong" %>" onkeyup="checkSlgTraKm('<%= ctkm.getId() + "." + trakm2.getId() + ".soluong" %>')"></p>
	   											<input type="hidden" value="<%= sp_dgia.get(key) %>" name="<%= ctkm.getId() + "." + trakm2.getId() + ".dongiaSpTraKm" %>">
	   											<input type="hidden" value="<%= tonhienTai %>" name="<%= ctkm.getId() + "." + trakm2.getId() + ".tonkhoSpTraKm" %>">
	   											<input type="hidden" value="<%= key %>" name="<%= ctkm.getId() + "." + trakm2.getId() + ".maspTraKm" %>">
		           							<%}%>
	           									<input type="checkbox" id="<%= ctkm.getId() + "." + trakm2.getId() + ".auto"%>" onchange="Auto('<%= ctkm.getId() + "." + trakm2.getId() + ".auto" %>')"><i style="font-size:0.7em"> Tự động chọn</i>
	           									<input type="hidden" id="<%= ctkm.getId() + "." + trakm2.getId() + ".tongluong" %>" value="<%= tongluongNHAN %>">
	           												           								     								
	           								</fieldset>
	           								<input id="<%= ctkm.getId() + ".trakhuyenmai.tongluong" %>" type="hidden" value="<%= tongluongNHAN %>"/>
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmId" %>" value="<%= trakm2.getId() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmType" %>" value="<%= trakm2.getType() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmHinhThuc" %>" value="<%= trakm2.getHinhthuc() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmPrimary" %>" value="0" />
	           								
	           								<input type="hidden" name="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" id="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" value="" />
	           								<input type="hidden" name="<%= ctkm.getId() + "." + trakm2.getId() + ".spSelected" %>" value="" id="<%= ctkm.getId() + "." + trakm2.getId() + ".spSelected" %>"/>
	           								
	           							<% }
		           						else
		           						{ %>
		           							<div style="width:100%; height:5px"></div>
		           							
         									<% if(trakm2.getType() == 1){ %>
          											<% if(!ctkm.getTra_OR()){ %>
		           									<span style="font-weight: bold;" > Tổng tiền KM <%= trakm2.getTongtien() %> </span>
		           									<%} else { %>
		           									<input class="addspeech" type="radio" name="<%= ctkm.getId() + ".chon" %>" value="<%= trakm2.getId() %>" 
          														title="<%= trakm2.getDiengiai()%>" > 
          												<span style="font-weight: bold;" > Tổng tiền KM <%= trakm2.getTongtien() %> </span>
		           									<%} %>
	           								<%} else { if(trakm2.getType() == 2){%>
	           									<% if(!ctkm.getTra_OR()){ %>
	           										<span style="font-weight: bold;" > Tổng chiết khấu KM <%= trakm2.getChietkhau() %> </span>
	           									<%} else { %>
	           										<input class="addspeech" type="radio" name="<%= ctkm.getId() + ".chon" %>" value="<%= trakm2.getId() %>" 
          														title="<%= trakm2.getDiengiai()%>" > 
          												<span style="font-weight: bold;" > Tổng tiền KM <%= trakm2.getTongtien() %> </span>
	           									<% } %>
	           								<%}else{ if(trakm2.getHinhthuc() != 2){ %>
		           								<%
		           									Hashtable<String, Integer> spSlFix = trakm2.getSanpham_Soluong( xlkm.getIdDonhang(), ctkm.getId(), ctkm.getSoxuatKM(), "1" );
		           									Hashtable<String, String> sp_tenspFix = trakm2.getsanpham_tensp();
		           									Enumeration<String> keysFix =  spSlFix.keys(); %>
		           									<fieldset>
			           								<legend>
			           								<% if(ctkm.getTra_OR()){ %>
			           									<input class="addspeech" type="radio" name="<%= ctkm.getId() + ".chon" %>" value="<%= trakm2.getId() %>" title="<%= "Chọn trả khuyến mại: " + trakm2.getId() + " - " + trakm2.getDiengiai()%>">
			           									<b> Trả sản phẩm cố định </b>
			           								<%} else { %>
			           									<a class="addspeech" href="" title="<%= trakm2.getDiengiai()%>" onclick="return false;">
			           									<b> Trả sản phẩm cố định </b></a>
			           								<%} %>
			           								</legend>
			           								
			           								<% while(keysFix.hasMoreElements())
			           								{
			           									String key = keysFix.nextElement();%>
			           									<p>			
			   											<span onMouseover="ddrivetip('<%= sp_tenspFix.get(key) %>', 300)"; onMouseout="hideddrivetip()" style="color: black; font-weight: normal;" >
			   												<%= key %></span> &nbsp;
			   											<input type="text" size="7" value="<%= Math.round(spSlFix.get(key) * ctkm.getSoxuatKM()) %>" 
			   											style="text-align:right; width: 50px" readonly="readonly" >
			   											</p>
				           							<%}%>        								     								
			           								</fieldset>	
	           								<%}}} %>
	           			
	           								
	           								<div style="width:100%; height:5px"></div>
	           								<input id="<%= ctkm.getId() + ".trakhuyenmai.type" %>" type="hidden" value="<%= trakm2.getType() %>"/>
	           								<% if(trakm2.getType() == 1){ %>
	           									<input id="<%= ctkm.getId() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getTongtien() %>"/>
	           								<%}else{ if(trakm2.getType() == 2){%>
	           									<input id="<%= ctkm.getId() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getChietkhau() %>"/>
	           								<%}else{ if(trakm2.getHinhthuc() != 2){ %>
		           								<input id="<%= ctkm.getId() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getTongGtriKm() %>"/>
	           								<%}}} %>
	           								
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmId" %>" value="<%= trakm2.getId() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmType" %>" value="<%= trakm2.getType() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmHinhThuc" %>" value="<%= trakm2.getHinhthuc() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmPrimary" %>" value="0" />
	           								<input type="hidden" name="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" id="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" value="<%= Math.round(tongtienTraKM) %>" />
	           								
           								<%}  }  } %>
           						</td>
           						
           						
					            <td rowspan="<%= rowSpan%>" align="center">
					        
					            	<input type="text" id="<%= ctkm.getId() + ".trakhuyenmai" %>" size="12" value="<%= Math.round(tongtienTraKM) %>" readonly name="ttTrakm" />
					            	<input type="hidden" id="<%= ctkm.getId() + ".trakhuyenmaiFix" %>" size="12" value="<%= Math.round(tongtienTraKM) %>" />
					            	<input type="hidden" id="<%= ctkm.getId() + ".tonggiatridh" %>"  value="<%= xlkm.getTonggiatriDh() %>" />
					            	
					            </td>
					             <td rowspan="<%= rowSpan%>" align="center">
					            	<%= Math.round(ctkm.getNgansach() - ctkm.getDasudung()) %>
					            	<input type="hidden" name = "nganSachCtkm" value="<%= Math.round(ctkm.getNgansach() - ctkm.getDasudung()) %>" />
					            </td>
					            <% pos++; %>
						        </tr>
						        
							<% } else { %>
								<tr class="tbdarkrow" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>">	
															
            					<td rowspan="<%= spList.size() %>" align="center" style="display: none" >
					            	<a class="addspeech" href="" title="<%= dkkm.getDiengiai() %>" onclick="return false;">
					            	<%= dkkm.getId() %></a>
					            	<% if(dkkm.getType() == 1){ %>
					            		<sup style="color:#F00; font-size:0.8em">*</sup> <%} %>
					            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + ".type" %>" value="<%= dkkm.getType() %>" />
					            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + ".pheptoan" %>" value="<%= dkkm.getPheptoan() %>" />
					            </td>					         
					            <td align="left">
					            	<span><%= tensp %></span>
					            	<input type="hidden" value="<%= sp.getMasp() %>" />
					            	<input type="hidden" value="<%= ctkm.getLoaict() %>" />
					            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".dongia" %>" value="<%= sp.getDongia() %>" />
					            </td>
					            
           						<% if( (dkkm.getTongtien()) > 0 || (dkkm.getTongluong() > 0)){ %>
       								<td align="center">
       									<%if(dkkm.getTongtien() > 0){ %>
       										<input type="text" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh2('<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
       									<%}else{ %>
       										<input type="text" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh3('<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
       									<%} %>
       									<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".hidden" %>" value="<%= sp.getSoluongcan() %>" />
       								</td>
	       						<%}else{ %>
	       							<td align="center">
	       								<input type="text" size="5" disabled="disabled" value="<%= sp.getSoluongcan() %>" />
	       								<input type="hidden" value="<%= slcan %>" />
	       							</td>
	       						<%}%>
            					<td align="center"><%= sp.getSoluongAvaiable() + sp.getSoluongcan() %></td>           					
            					<td align="center"><%= sp.getSoluongAvaiable() %></td>
            					           								           					            
					            <% if(dkkm.getTongtien() > 0 || dkkm.getTongluong() > 0){ %>
					            	<td rowspan="<%= spList.size() %>" align="center" style="display: none">
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".soxuat" %>" type="text" size="5" disabled="disabled" value="<%= dkkm.getSoxuatKM() %>" />
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".hidden" %>" type="hidden" size="5" value="<%= dkkm.getSoxuatKM() %>"/>
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".tongtien" %>" type="hidden" value="<%= dkkm.getTongtien() %>"/>
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".tonggiatri" %>" type="hidden" value="<%= tonggiatri %>"/>
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".tongluong" %>" type="hidden" value="<%= dkkm.getTongluong() %>"/>
					            		<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".tongsoluong" %>" type="hidden" value="<%= tongsoluong %>"/>
					            	</td>
           						<%}else{ %>
           							<td rowspan="<%= spList.size() %>" align="center" style="display: none">
           								<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".soxuat" %>" type="text" size="5" value="<%= dkkm.getSoxuatKM() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh('<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
           								<input id="<%= ctkm.getId() + "." + dkkm.getId() + ".hidden" %>" type="hidden" size="5" value="<%= dkkm.getSoxuatKM() %>"/>
           							</td>          							
           						<%}%>						
           						<% pos++; %>			           
					       		</tr>
					<% } } else { %>
							<tr class="tbdarkrow" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>">							
            				<td align="left">
				            	<span><%= tensp %></span>
				            	<input type="hidden" value="<%= sp.getMasp() %>" />
				            	<input type="hidden" value="<%= ctkm.getLoaict() %>" />
				            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".dongia" %>" value="<%= sp.getDongia() %>" />
					        </td>
					            
            				<% if( (dkkm.getTongtien()) > 0 || (dkkm.getTongluong() > 0)){ %>
   								<td align="center">
  									<%if(dkkm.getTongtien() > 0){ %>
  										<input type="text" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh2('<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
  									<%}else{ %>
  										<input type="text" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh3('<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
  									<%} %>
  									<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + "." + sp.getMasp() + ".hidden" %>" value="<%= sp.getSoluongcan() %>" />
  								</td>
       						<%} else { %>
       							<td align="center">
       								<input type="text" size="5" disabled="disabled" value="<%= sp.getSoluongcan() %>" />
       								<input type="hidden" value="<%= slcan %>" />
       							</td>
       						<%}%>   				
          					<td align="center"><%= sp.getSoluongAvaiable() + sp.getSoluongcan() %></td>         				
           					<td align="center"><%= sp.getSoluongAvaiable() %></td>   
           					<% pos++; %>        				
        					</tr>
					<% } } } else {  //truong hop dkkm nay ko sanpham nao thoa man
						
				//	ISanpham sp = (ISanpham) new Sanpham();
					if(j == 0){ System.out.println("666.Dieu kien nay khong co san pham nao....."); %>
						<tr class="tbdarkrow">
			        	<td rowspan="<%= rowSpan %>" align="center">
			        		<%= ctkm.getscheme() %>
			        		<input type="hidden" id="<%= ctkm.getId() + ".pos" %>" value="<%= pos %>" />
			        		<input type="hidden" id="<%= ctkm.getId() + ".rowspan" %>" value="<%= rowSpan %>" />
			        		<input type="hidden" name="schemeList" value="<%= ctkm.getId() %>" />
			        		<input type="hidden" name="schemeDiengiai" value="<%= ctkm.getDiengiai() %>" />
			        		<input type="hidden" name="schemeOR" value="<%= ctkm.getTra_OR() %>" />
			        		<input type="hidden" name="schemePhanBo" value="<%= ctkm.getPhanbotheoDH() %>" />
					    </td>			        				        	
			            <td align="center" style="display: none">
			            	<a class="addspeech" href="" title="<%= dkkm.getDiengiai() %>" onclick="return false;">
			            	<%= dkkm.getId() %></a>
			            	<% if(dkkm.getType() == 1){ %>
					            <sup style="color:#F00; font-size:0.8em">*</sup> <%} %>
			            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + ".type" %>" value="<%= dkkm.getType() %>" />
			            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + ".pheptoan" %>" value="<%= dkkm.getPheptoan() %>" />			  
					    </td>   			                   		     
			            <td align="center">&nbsp;</td>
          				<td ></td>
          				<td >&nbsp;</td>
          				<td align="center">&nbsp;</td>				            
			            <td rowspan="<%= spList.size() %>" align="center" style="display: none">0</td>			            
			            
			            <td rowspan="<%= rowSpan%>" align="center">
			            	<input type="text" id="<%= ctkm.getId() + ".tongsoxuat"%>" size="5" value="<%= ctkm.getSoxuatKM() %>" name="soxuatKM" readonly/>
			            </td>
			         
			        	
			        	<td rowspan="<%= rowSpan%>" align="center" style="padding:0px">
           						<% 
           						for(int count = 0; count < ctkm.getTrakhuyenmai().size(); count++)
           						{
           							Trakhuyenmai trakm2 = (Trakhuyenmai)ctkm.getTrakhuyenmai().get(count); 
           							
           							//System.out.println("7777.So xuat KM la: " + ctkm.getSoxuatKM() + "  -- Hinh thuc la: " + trakm2.getHinhthucPrimary());
           							if( ( ctkm.getKhoId().equals("100001") && trakm2.getHinhthucPrimary().equals("1") ) 
           									|| ( (ctkm.getSoxuatKM() < 1) && trakm2.getHinhthucPrimary().equals("0") ) )
           							{ %>
           								Trả tiền
	     								<input type="hidden" name="<%= ctkm.getId() + ".trakmId" %>" value="<%= trakm2.getId() %>" />
	     								<input type="hidden" name="<%= ctkm.getId() + ".trakmType" %>" value="<%= trakm2.getType() %>" />
	     								<input type="hidden" name="<%= ctkm.getId() + ".trakmHinhThuc" %>" value="<%= trakm2.getHinhthuc() %>" />
	     								<input type="hidden" name="<%= ctkm.getId() + ".trakmPrimary" %>" value="<%= trakm2.getHinhthucPrimary() %>" />
	     								<input type="hidden" name="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" id="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" value="<%= Math.round(ctkm.getSoxuatKM() * trakm2.getTongGtriKm()) <= 0 ? Math.round(tongtienTraKM) : Math.round(ctkm.getSoxuatKM() * trakm2.getTongGtriKm()) %>" />
           							<% } else {
	           							if(trakm2.getType() == 3 && trakm2.getHinhthuc() == 2)
	           							{ 
											long tongluongNHAN = Math.round( ( trakm2.getTongluong( xlkm.getIdDonhang(), ctkm.getId(), ctkm.getSoxuatKM() ) ) - 0.5 );
	           								
		      								Hashtable<String, Integer> spSl = trakm2.getSanpham_Soluong( xlkm.getIdDonhang(), ctkm.getId(), ctkm.getSoxuatKM(), "2");
		       								Hashtable<String, Float> sp_dgia = trakm2.getsanpham_dongia();
		       								Hashtable<String, String> sp_tensp = trakm2.getsanpham_tensp();
		       								
		       								Enumeration<String> keys =  spSl.keys();
	       								%>	           								
	           								<fieldset>
	           								<legend>
		           								<% if(ctkm.getTra_OR()){ %>
		           									<input class="addspeech" type="radio" name="<%= ctkm.getId() + ".chon" %>" value="<%= trakm2.getId() %>" title="<%= "Chọn trả khuyến mại: " + trakm2.getId() + " - " + trakm2.getDiengiai()%>">
		           									<b>Tổng lượng KM <%= tongluongNHAN %></b>
		           								<%} else { %>
		           									<a class="addspeech" href="" title="<%= "Trả khuyến mại trong nhóm: " + trakm2.getId() + " - " + trakm2.getDiengiai()%>" onclick="return false;">
		           										<b>Tổng lượng KM <%= tongluongNHAN %></b></a>
		           								<%} %>
	           								</legend>
	           								
	           								<% while(keys.hasMoreElements())
	           								{
	           									String key = keys.nextElement(); 
	           									String spTen = sp_tensp.get(key);
	           									String tonhienTai = spTen.substring(spTen.indexOf("i: ") + 3, spTen.length() - 1);
	           								%>			
	   											<p><span onMouseover="ddrivetip('<%= sp_tensp.get(key) + " - Giá " + sp_dgia.get(key) %>', 300)"; onMouseout="hideddrivetip()" style="font-size:10pt;"><%= key %></span> &nbsp;
	   											<input type="text" size="7" value="" style="text-align:right; width: 50px" name="<%= ctkm.getId() + "." + trakm2.getId() + ".soluong" %>" onkeyup="checkSlgTraKm('<%= ctkm.getId() + "." + trakm2.getId() + ".soluong" %>')"></p>
	   											<input type="hidden" value="<%= sp_dgia.get(key) %>" name="<%= ctkm.getId() + "." + trakm2.getId() + ".dongiaSpTraKm" %>">
	   											<input type="hidden" value="<%= tonhienTai %>" name="<%= ctkm.getId() + "." + trakm2.getId() + ".tonkhoSpTraKm" %>">
	   											<input type="hidden" value="<%= key %>" name="<%= ctkm.getId() + "." + trakm2.getId() + ".maspTraKm" %>">
		           							<%}%>
	           									<input type="checkbox" id="<%= ctkm.getId() + "." + trakm2.getId() + ".auto"%>" onchange="Auto('<%= ctkm.getId() + "." + trakm2.getId() + ".auto" %>')"><i style="font-size:0.7em"> Tự động chọn</i>
	           									<input type="hidden" id="<%= ctkm.getId() + "." + trakm2.getId() + ".tongluong" %>" value="<%= tongluongNHAN %>">
	           												           								     								
	           								</fieldset>
	           								<input id="<%= ctkm.getId() + ".trakhuyenmai.tongluong" %>" type="hidden" value="<%= tongluongNHAN %>"/>
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmId" %>" value="<%= trakm2.getId() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmType" %>" value="<%= trakm2.getType() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmHinhThuc" %>" value="<%= trakm2.getHinhthuc() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmPrimary" %>" value="0" />
	           								
	           								<input type="hidden" name="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" id="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" value="" />
	           								<input type="hidden" name="<%= ctkm.getId() + "." + trakm2.getId() + ".spSelected" %>" value="" id="<%= ctkm.getId() + "." + trakm2.getId() + ".spSelected" %>"/>
	           								
	           							<% }
		           						else
		           						{ %>
		           						
		           							<div style="width:100%; height:5px"></div>
		           							
         									<% if(trakm2.getType() == 1){ %>
          											<% if(!ctkm.getTra_OR()){ %>
		           									<span style="font-weight: bold;" > Tổng tiền KM <%= trakm2.getTongtien() %> </span>
		           									<%} else { %>
		           									<input class="addspeech" type="radio" name="<%= ctkm.getId() + ".chon" %>" value="<%= trakm2.getId() %>" 
          														title="<%= trakm2.getDiengiai()%>" > 
          												<span style="font-weight: bold;" > Tổng tiền KM <%= trakm2.getTongtien() %> </span>
		           									<%} %>
	           								<%} else { if(trakm2.getType() == 2){%>
	           									<% if(!ctkm.getTra_OR()){ %>
	           										<span style="font-weight: bold;" > Tổng chiết khấu KM <%= trakm2.getChietkhau() %> </span>
	           									<%} else { %>
	           										<input class="addspeech" type="radio" name="<%= ctkm.getId() + ".chon" %>" value="<%= trakm2.getId() %>" 
          														title="<%= trakm2.getDiengiai()%>" > 
          												<span style="font-weight: bold;" > Tổng tiền KM <%= trakm2.getTongtien() %> </span>
	           									<% } %>
	           								<%}else{ if(trakm2.getHinhthuc() != 2){ %>
		           								<%
		           								Hashtable<String, Integer> spSlFix = trakm2.getSanpham_Soluong(xlkm.getIdDonhang(), ctkm.getId(), ctkm.getSoxuatKM(), "1");
	           									Hashtable<String, String> sp_tenspFix = trakm2.getsanpham_tensp();
	           									Enumeration<String> keysFix =  spSlFix.keys(); %>
		           									<fieldset>
			           								<legend>
			           								<% if(ctkm.getTra_OR()){ %>
			           									<input class="addspeech" type="radio" name="<%= ctkm.getId() + ".chon" %>" value="<%= trakm2.getId() %>" title="<%= "Chọn trả khuyến mại: " + trakm2.getId() + " - " + trakm2.getDiengiai()%>">
			           									<b> Trả sản phẩm cố định </b>
			           								<%} else { %>
			           									<a class="addspeech" href="" title="<%= trakm2.getDiengiai()%>" onclick="return false;">
			           									<b> Trả sản phẩm cố định </b></a>
			           								<%} %>
			           								</legend>
			           								
			           								<% while(keysFix.hasMoreElements())
			           								{
			           									String key = keysFix.nextElement();%>
			           									<p>			
			   											<span onMouseover="ddrivetip('<%= sp_tenspFix.get(key) %>', 300)"; onMouseout="hideddrivetip()" style="color: black; font-weight: normal;" >
			   												<%= key %></span> &nbsp;
			   											<input type="text" size="7" value="<%= Math.round(spSlFix.get(key) * ctkm.getSoxuatKM()) %>" 
			   											style="text-align:right; width: 50px" readonly="readonly" >
			   											</p>
				           							<%}%>        								     								
			           								</fieldset>	
	           								<%}}} %>
	           								
	           								
	           								<div style="width:100%; height:5px"></div>
	           								<input id="<%= ctkm.getId() + ".trakhuyenmai.type" %>" type="hidden" value="<%= trakm2.getType() %>"/>
	           								<% if(trakm2.getType() == 1){ %>
	           									<input id="<%= ctkm.getId() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getTongtien() %>"/>
	           								<%}else{ if(trakm2.getType() == 2){%>
	           									<input id="<%= ctkm.getId() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getChietkhau() %>"/>
	           								<%}else{ if(trakm2.getHinhthuc() != 2){ %>
		           								<input id="<%= ctkm.getId() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getTongGtriKm() %>"/>
	           								<%}}} %>
	           								
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmId" %>" value="<%= trakm2.getId() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmType" %>" value="<%= trakm2.getType() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmHinhThuc" %>" value="<%= trakm2.getHinhthuc() %>" />
	           								<input type="hidden" name="<%= ctkm.getId() + ".trakmPrimary" %>" value="0" />
	           								<input type="hidden" name="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" id="<%= ctkm.getId() + "." + trakm2.getId() + ".tonggiatriTKM" %>" value="<%= Math.round(tongtienTraKM) %>" />
	           								
           								<%}  }  } %>
           						</td>
			        	
			         
			            <td rowspan="<%= rowSpan%>" align="center">
			            	
			            	<input type="text" id="<%= ctkm.getId() + ".trakhuyenmai" %>" size="12" value="<%= Math.round(tongtienTraKM) %>" readonly name="ttTrakm" />
			            	<input type="hidden" id="<%= ctkm.getId() + ".trakhuyenmaiFix" %>" size="12" value="<%= Math.round(tongtienTraKM) %>" />
			            	<input type="hidden" id="<%= ctkm.getId() + ".tonggiatridh" %>"  value="<%= xlkm.getTonggiatriDh() %>" />
			         
			            </td>
					    <td rowspan="<%= rowSpan%>" align="center">
					    	<%= Math.round(ctkm.getNgansach() - ctkm.getDasudung()) %>
					    	<input type="hidden" name = "nganSachCtkm" value="<%= Math.round(ctkm.getNgansach() - ctkm.getDasudung()) %>" />
					    </td>
					    <% pos++; %>
				        </tr>
					<%}else{ System.out.println("888.Dieu kien nay khong co san pham nao....."); %>
						<tr class="tbdarkrow">
			            <td align="center" style="display: none">
			            	<a class="addspeech" href="" title="<%= dkkm.getDiengiai() %>" onclick="return false;">
			            	<%= dkkm.getId() %> </a>
			            	<% if(dkkm.getType() == 1){ %>
					            <sup style="color:#F00; font-size:0.8em">*</sup> <%} %>
			            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + ".type" %>" value="<%= dkkm.getType() %>" />
			            	<input type="hidden" id="<%= ctkm.getId() + "." + dkkm.getId() + ".pheptoan" %>" value="<%= dkkm.getPheptoan() %>" />
					    </td>
			            <td align="center">&nbsp;</td>
          				<td >&nbsp;</td>
          				<td >&nbsp;</td>
          				<td align="center">&nbsp;</td>		            
			            <td rowspan="<%= spList.size() %>" align="center" style="display: none">0</td>
			            <% pos++; %>
				        </tr>
					<% } } } } %>
        <TR>
			<TD align="center" colspan="11" class="tbfooter">&nbsp; &nbsp;</TD>
		</TR>
		<tr>
			<td colspan="11" align="left">
			
			<div id="btnSave">
			<a class="button" href="javascript:saveform()">
              Lưu lại <img style="top: -4px;" src="../images/New.png" alt=""></img></a>
              </div>
            </td>
		</tr>
    </table>
    
    <%
    	Enumeration<String> keys_sp =  sanpham_soluong.keys();
    	while(keys_sp.hasMoreElements()){
    		String key = keys_sp.nextElement();
    %>
    	<input type="hidden" name="sanphamList" value="<%= key + "--" + Integer.toString(sanpham_soluong.get(key)) %>" >
    <%}%>
    
    <%
    	String[] masp = xlkm.getMasp();
    	String[] dongia = xlkm.getDongia();
    	String[] soluong = xlkm.getSoluong();
    	String[] quycach = xlkm.getQuycah();
    	int n = 0;
    	while(n < masp.length)
    	{
    		if(masp[n] != ""){
    %>
    	<input type="hidden" name="spMa" value="<%= masp[n] %>" >
    	<input type="hidden" name="spDongia" value="<%= dongia[n] %>" >
    	<input type="hidden" name="spSoluong" value="<%= soluong[n] %>" >
    	<input type="hidden" name="spQuycach" value="<%= quycach[n] %>" >
    <%} n++;} %>
</fieldset>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<% 	
if(xlkm != null){
	xlkm.DBclose();
	xlkm = null;
	util=null;
	ctkmAvaiable=null;
	listCTKM=null;
	sanpham_soluong=null;
	
	
	session.setAttribute("xlkm",null);
	
}
%>
<%}%>