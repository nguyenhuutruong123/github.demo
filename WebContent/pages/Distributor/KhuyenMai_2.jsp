<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.ctkhuyenmai.*" %>
<%@ page  import = "geso.dms.distributor.beans.ctkhuyenmai.imp.*" %>
<%@ page  import = "geso.dms.distributor.beans.dieukienkhuyenmai.IDieukienkhuyenmai" %>
<%@ page  import = "geso.dms.distributor.beans.dieukienkhuyenmai.imp.*" %>
<%@ page  import = "geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai" %>
<%@ page  import = "geso.dms.distributor.beans.trakhuyenmai.imp.*" %>
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

<% XLkhuyenmai xlkm = (XLkhuyenmai)session.getAttribute("xlkm"); %>
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
  		function keypress(e) //H??m d??ng d? ngan ngu?i d??ng nh?p c??c k?? t? kh??c k?? t? s? v??o TextBox
  		{    
  			var keypressed = null;
  			if (window.event)
  				keypressed = window.event.keyCode; //IE
  			else
  				keypressed = e.which; //NON-IE, Standard
  			
  			if (keypressed < 48 || keypressed > 57)
  			{ 
  				if (keypressed == 8 || keypressed == 127)
  				{//Ph??m Delete v?? Ph??m Back
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
 			<% if(xlkm.getDieuchinh() == false){%>
	  			 if(checkSoluong() == false)
	  			 {
	  				 alert('Vui l??ng ??i???u ch???nh l???i s??? l?????ng s???n ph???m ho???c s??? xu???t h?????ng khuy???n m??i');
	  				 return;
	  			 }
	  		<%} %>	
	  		
	  		spList = document.getElementsByName("spSelectList");
 			for(k = 0; k < spList.length; k++)
 			{
 				if(spList.item(k).value == "")
 				{
 					alert("Vui l??ng ch???n s???n ph???m tr??? khuy???n m??i cho c??c xu???t khuy???n m??i ???????c ch???n..");
 					return;
 				}
 			 }

 			 if(checkSpIds() == false)
 			 {
 				 alert('Xin l???i,b???n ph???i nh???p s??? l?????ng c???a s???n ph???m nh???n ???????c sau tr??? khuy???n m??i ...\n(Vui l??ng ki???m tra l???i s??? l?????ng c??c s???n ph???m sau khuy???n m??i)');
				 return;
 			 }
 			 
 			 //check ngan sach
 			 if(checkNganSach() == false)
 			 {
 				alert('Xin l???i, ng??n s??ch tr??? khuy???n m???i kh??ng th??? v?????t qu?? ng??n s??ch ch????ng tr??nh ...\n(Vui l??ng ??i???u ch???nh l???i s??? l?????ng c??c s???n ph???m sau khuy???n m??i)');
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
							alert('S??? xu???t b???n nh???p kh??ng h??p l???,vui l??ng nh???p s??? xu??t nh??? h??n');
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
						alert('S??? l?????ng s??? d???ng ph???i nh??? h??n t???ng s??? l?????ng hi???n c??');
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
						alert('S??? l?????ng s??? d???ng ph???i nh??? h??n t???ng s??? l?????ng hi???n c?? ');
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
					//alert(id.options[i].value);	
					var sanphamId = document.getElementById(optionId + '.Id');
					//var id_tgt = optionId.substring(0, optionId.indexOf('.'));
					//var tongluong = document.getElementById(id_tgt + '.trakhuyenmai.tongluong');
					//var soxuat = document.getElementById(id_tgt + '.tongsoxuat');
					//var tonggiatriTrakm = document.getElementById(id_tgt + '.trakhuyenmai');
								
					//tonggiatriTrakm.value = parseInt(soxuat.value) * parseFloat(id.options[i].value) * parseInt(tongluong.value);
					//sanphamId.value = id.options[i].innerHTML;
					sanphamId.value = id.options[i].value;
				}
			}
			//checkNganSach();
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
				if(parseInt(columns.item(pos).getElementsByTagName('input').length) > 0)
				{
					var inp_sp = columns.item(pos).getElementsByTagName('input');					
					if(inp_sp.item(0).value == masp)
					{				
						var inp_slg = columns.item(pos + 1).getElementsByTagName('input');
						sl =  parseInt(sl) + parseInt(inp_slg.item(0).value);
					}
				}
			}
			//alert('So luong la: ' + sl);
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
				//alert(columns.item(pos).innerHTML);
				if(columns.item(pos).getElementsByTagName('input').length > 0)
				{
					var input = columns.item(pos).getElementsByTagName('input');
					if(input.item(0).value == name)
					{
 						columns.item(pos).style.backgroundColor = color;
 						columns.item(pos + 1).style.backgroundColor = color;
 						//columns.item(pos + 2).style.backgroundColor = "#CFF";
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
  			
  			var idTkm = idTlg.substring(0, idTlg.indexOf("."));
  			
  			spIds = idTlg.substring(0, idTlg.indexOf("."));
  			var sanphamId = document.getElementById(spIds + '.trakhuyenmai.sanpham.Id');
  			
  			var tongsotien = 0;
  			sanphamId.value = ""; //khoi tao lai
  			if(chkId.checked)
			{
  				for(i = 0; i < slg.length; i++)
  				{
  					slg.item(i).value = "";
  					slg.item(i).setAttribute("readonly", "readonly");
  				}
  				slg.item(0).value = k;
  				sanphamId.value = masp.item(0).value + '-' + k;	//luu lai sp chon, soluong tuong ung
  				//alert(sanphamId.value);
  				
  				//tinh tong gia tri
  				tongsotien = parseFloat(tongsotien) + parseInt(k) * parseFloat(dgiaSpTraKm.item(0).value);
  				document.getElementById(idTkm + ".trakhuyenmai").value = Math.round(tongsotien);
			}
  			else
			{
  				for(i = 0; i < slg.length; i++)
  				{
  					slg.item(i).value = "";
  					slg.item(i).removeAttribute("readonly");
  				}
  				sanphamId.value = "";
  				document.getElementById(idTkm + ".trakhuyenmai").value = "";
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
  			var sanphamId = document.getElementById(spIds + '.trakhuyenmai.sanpham.Id');
  			
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
  					document.getElementById(idTkm + ".trakhuyenmai").value = Math.round(tongsotien);
  					alert('S??? l?????ng b???n nh???p v??o,l???n h??n t???ng s??? l?????ng tr??? khuy???n m??i (' + k + '), Vui l??ng nh???p l???i ...');
  					return;
  				}
  				if(slg.item(i).value != "")
  					sanphamId.value = sanphamId.value + masp.item(i).value + '-' + slg.item(i).value + ";";
  			}

  			document.getElementById(idTkm + ".trakhuyenmai").value = Math.round(tongsotien);
  			if(sanphamId.value != "")
  				sanphamId.value = sanphamId.value.substring(0, sanphamId.value.length - 1); //cat dau ; cuoi cung
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
  <!-- <link rel="stylesheet" type="text/css" href="../css/cool_DHTML_tooltip.css" /> -->

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

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>

<body style="margin-left:3px">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->


<form name="kmForm" method="post" action="../../KhuyenmaiSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%=xlkm.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<INPUT type="hidden" name="dhId" value='<%=xlkm.getIdDonhang() %>'>
<INPUT type="hidden" name="ngaygiaodich" value='<%=xlkm.getNgaygiaodich() %>'>
<INPUT type="hidden" name="tonggiatri" value='<%=xlkm.getTonggiatriDh() %>'>
<input type="hidden" name="khachhang" value='<%=xlkm.getKhachhang() %>'>

<input type="hidden" name="msg" value='<%=xlkm.getMsg() %>'>
<script type="text/javascript">
  thongbao();
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr height="25px">
		<TD align="left" colspan="2" class="tbnavigation">&nbsp;????n h??ng > ??p khuy???n m??i > ??i???u ch???nh</TD>
		<TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n    <%= xlkm.getNppTen() %> &nbsp;</TD>
	</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<TR class="tbdarkrow">
		<TD align="left" width="30"><A href = "../../DonhangUpdateSvl?userId=<%=userId%>&update=<%= xlkm.getIdDonhang() %>"><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
	    <TD align="left" width="30">
	    <div id="btnSave30">
	    <A href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A>
	    </div>
	    </TD>
	    <TD></TD>
	</TR>
</table>
 <fieldset  >
 	<legend class="legendtitle">Di chuy???n m?? ch????ng tr??nh khuy???n m??i(CTKM) b???n mu???n ??u ti??n</legend>
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
    	<tr><td><input type="checkbox" name="ShowAll" checked="checked" value='1' onchange="submitform()">Hi???n t???t c??? c??c xu???t khuy???n m??i ( t???i ??a ) c?? th??? ?????t ???????c c???a ????n h??ng cua don hang</td></tr>
    <%}else{ %>
    	<tr><td><input type="checkbox" name="ShowAll"  value='0' onchange="submitform()">Hi???n t???t c??? c??c xu???t khuy???n m??i  ( t???i ??a ) c?? th??? ?????t ???????c c???a ????n h??ng</td></tr>
    <%} %>
    </table>
</fieldset>

<fieldset>
	<legend><span class="legendtitle"> Danh s??ch h????ng tr??nh khuy???n m??i (CTKM) th???a ??i???u ki???n </span>&nbsp;&nbsp;&nbsp;
         <a class="button" href="javascript:submitform()">
              Hi???n th??? danh s??ch <img style="top: -4px;" src="../images/New.png" alt=""></img></a>
    </legend>
    <table class="tabledetail" id="content" width="100%" border="0" cellspacing="1" cellpadding="2">
    	<tr class="tbheader">
        	<th width="10%"  align="center">M?? CTKM</th>
            <th width="7%" align="center" style="display: none">??i???u ki???n</th>
            <th width="17%" align="center">S???n ph???m</th>
            <th width="7%" align="center">S??? d???ng</th>
            <th width="10%" align="center">T???ng S??? l?????ng</th>
            <th width="7%" align="center">C??n l???i</th>
            <th width="5%" align="center">S??? xu???t</th>
            <th width="7%" align="center" style="display: none">S??? xu???t/M?? CTKM</th>
            <th width="15%" align="center">Tr??? khuy???n m??i</th>
            <th width="8%" align="center">T???ng gi?? tr???</th>
            <th width="8%" align="center">Ng??n s??ch</th>
        </tr>
             <% //in table ketqua.....
        
        int pos = 0;
        for(int i = 0; i < listCTKM.size(); i++)
		{
			Ctkhuyenmai ctkm = (Ctkhuyenmai)listCTKM.get(i);
			
			int rowSpan = xlkm.getRowspan(ctkm);
			
			Trakhuyenmai trakm = (Trakhuyenmai)ctkm.getTrakhuyenmai().get(0);
			float tongtienTraKM = 0;
			
			for(int count = 0; count < ctkm.getTrakhuyenmai().size(); count++)
			{
				Trakhuyenmai trakmTien = (Trakhuyenmai)ctkm.getTrakhuyenmai().get(count);
				
				if(trakmTien.getType() == 1)
					tongtienTraKM = ctkm.getSoxuatKM() * trakmTien.getTongtien();
				else
				{
					if(trakmTien.getType() == 2) //trachietkhau
					{
						//tongtienTraKM = xlkm.getTonggiatriDh() * (trakm.getChietkhau() / 100);
						tongtienTraKM = ctkm.getTongTienTheoDKKM() * (trakmTien.getChietkhau() / 100);
					}
					else
					{
						tongtienTraKM = ctkm.getSoxuatKM() * trakmTien.getTongGtriKm();	
						//System.out.print("\nTra Khuyen Mai Value: " + Float.toString(trakm.getTongGtriKm()) + "\n" );
					}
				}
			}
			
		//float tt = ctkm.getNgansach() - ctkm.getDasudung();
		//tong tien khuyen mai vuot qua ngan sach phai dieu chinh lai
			//System.out.println("tong luong can lay" +  trakm.getTongluong() +" tong khuyen mai :"+ tongtienTraKM +"ngsn sach "+Float.toString(ctkm.getNgansach() - ctkm.getDasudung()));
			
			if(tongtienTraKM > (ctkm.getNgansach() - ctkm.getDasudung()))
			{
			   tongtienTraKM = ctkm.getNgansach() - ctkm.getDasudung();	  
			}
			
			List<IDieukienkhuyenmai> dkkmList = ctkm.getDkkhuyenmai();
			for(int j = 0; j < dkkmList.size(); j++)
			{
				Dieukienkhuyenmai dkkm = (Dieukienkhuyenmai)dkkmList.get(j);
				float tonggiatri = xlkm.getTonggiatri(dkkm);
				int tongsoluong = xlkm.getTongsoluong(dkkm);
				
				List<ISanpham> spList = dkkm.getSanphamList();			
				if(spList.size() > 0){	
					for(int k = 0; k < spList.size(); k++)
					{
						Sanpham sp = (Sanpham)spList.get(k);
						
						int slcan = 0;
						if(dkkm.getTongtien() <= 0 && dkkm.getTongluong() <= 0)
							slcan = sp.getSoluongcan() / dkkm.getSoxuatKM();
						String tensp = sp.getTensp();
						//System.out.println("soluong can:"+ slcan);
						if(tensp.length() > 25)
							tensp = tensp.substring(0, 25) + "...";
						
						if(k == 0)
						{
							if(j == 0){%> 
								<tr class="tbdarkrow" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>">
								
					        	<td rowspan="<%= rowSpan %>" align="center">
					        		<%= ctkm.getscheme() %>
					        		<input type="hidden" id="<%= ctkm.getscheme() + ".pos" %>" value="<%= pos %>" />
					        		<input type="hidden" id="<%= ctkm.getscheme() + ".rowspan" %>" value="<%= rowSpan %>" />
					        		<input type="hidden" name="schemeList" value="<%= ctkm.getId() %>" />
					        		<input type="hidden" name="schemeDiengiai" value="<%= ctkm.getDiengiai() %>" />
					        	</td>					        						        	
					            <td rowspan="<%= spList.size() %>" align="center" style="display: none;">
					            	<div class="addspeech" title="<%= dkkm.getDiengiai() %>" ><%= dkkm.getId() %>
					            	<% if(dkkm.getType() == 1){ %>
					            		<sup style="color:#F00; font-size:0.8em">*</sup> <%} %>
					            	 </div>
					            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".type" %>" value="<%= dkkm.getType() %>" />
					            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".pheptoan" %>" value="<%= dkkm.getPheptoan() %>" />
					            </td> 
					            					                      
					            <td align="left">					    
					            	<%if(sp.getTensp().length() > 25){ %>
					            		<div class="addspeech" title="<%= sp.getTensp() %>"><%= tensp %>
					            	<% } else{%>
					            		<span><%= tensp %></span>
					            	<%} %>
					            	</div>
					            	<input type="hidden" value="<%= sp.getMasp() %>" />
					            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".dongia" %>" value="<%= sp.getDongia() %>" />
					            </td>
					                        					           				
           						<% if((dkkm.getTongtien()) > 0 || (dkkm.getTongluong() > 0)){ %>
       								<td align="center">
       									<%if(dkkm.getTongtien() > 0){ %>
       										<input type="text" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh2('<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
       									<%}else{ %>
       										<input type="text" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh3('<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
       									<%} %>
       									<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".hidden" %>" value="<%= sp.getSoluongcan() %>" />
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
					            	<td rowspan="<%= spList.size() %>" align="center">
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".soxuat" %>" type="text" size="5" disabled="disabled" value="<%= dkkm.getSoxuatKM() %>" />
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".hidden" %>" type="hidden" size="5" value="<%= dkkm.getSoxuatKM() %>"/>
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".tongtien" %>" type="hidden" value="<%= dkkm.getTongtien() %>"/>
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".tonggiatri" %>" type="hidden" value="<%= tonggiatri %>"/>
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".tongluong" %>" type="hidden" value="<%= dkkm.getTongluong() %>"/>
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".tongsoluong" %>" type="hidden" value="<%= tongsoluong %>"/>
					            	</td>
           						<%}else{ %>
           							<td rowspan="<%= spList.size() %>" align="center">
           								<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".soxuat" %>" type="text" size="5" value="<%= dkkm.getSoxuatKM() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh('<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
           								<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".hidden" %>" type="hidden" value="<%= dkkm.getSoxuatKM() %>"/>
           							</td>          							
           						<%}%>
           						
           						<td rowspan="<%= rowSpan%>" align="center" style="display: none">
           							<input id="<%= ctkm.getscheme() + ".tongsoxuat"%>" type="text" size="5" value="<%= ctkm.getSoxuatKM() %>" name="soxuatKM" readonly/>
           						</td>
           						
           						<td rowspan="<%= rowSpan%>" align="center" style="padding:0px">
           						<% 
           						for(int count = 0; count < ctkm.getTrakhuyenmai().size(); count++)
           						{
           							Trakhuyenmai trakm2 = (Trakhuyenmai)ctkm.getTrakhuyenmai().get(count); 
           							if(trakm2.getType() == 3 && trakm2.getHinhthuc() == 2)
           							{ 
	      								Hashtable<String, Integer> spSl = trakm2.getSanpham_Soluong();
	       								Hashtable<String, Float> sp_dgia = trakm2.getsanpham_dongia();
	       								Hashtable<String, String> sp_tensp = trakm2.getsanpham_tensp();
	       								
	       								Enumeration<String> keys =  spSl.keys(); 
       							%>	           								
           								<fieldset>
           								<legend><a class="addspeech" href="" title="<%= trakm2.getDiengiai()%>" onclick="return false;"><%= trakm2.getId()%> <b> - T???ng l?????ng KM <%= trakm2.getTongluong() * ctkm.getSoxuatKM() %></b></a></legend>
           								
           								<% while(keys.hasMoreElements())
           								{
           									String key = keys.nextElement();%>			
   											<p><span onMouseover="ddrivetip('<%= sp_tensp.get(key) + " - Gi?? " + sp_dgia.get(key) %>', 300)"; onMouseout="hideddrivetip()" style="font-size:10pt;"><%= key %></span> &nbsp;
   											<input type="text" size="7" value="" style="text-align:right; width: 50px" name="<%= ctkm.getscheme() + "." + trakm2.getId() + ".soluong" %>" onkeyup="checkSlgTraKm('<%= ctkm.getscheme() + "." + trakm2.getId() + ".soluong" %>')"></p>
   											<input type="hidden" value="<%= sp_dgia.get(key) %>" name="<%= ctkm.getscheme() + "." + trakm2.getId() + ".dongiaSpTraKm" %>">
   											<input type="hidden" value="<%= key %>" name="<%= ctkm.getscheme() + "." + trakm2.getId() + ".maspTraKm" %>">
	           							<%}%>
           									<input type="checkbox" id="<%= ctkm.getscheme() + "." + trakm2.getId() + ".auto"%>" onchange="Auto('<%= ctkm.getscheme() + "." + trakm2.getId() + ".auto" %>')"><i style="font-size:0.7em"> T??? ?????ng ch???n</i>
           									<input type="hidden" id="<%= ctkm.getscheme() + "." + trakm2.getId() + ".tongluong" %>" value="<%= trakm2.getTongluong() * ctkm.getSoxuatKM() %>">			           								     								
           								</fieldset>
           								<input id="<%= ctkm.getscheme() + ".trakhuyenmai.tongluong" %>" type="hidden" value="<%= trakm2.getTongluong() %>"/>
           								<input type="hidden" name="trakmId" value="<%= trakm2.getId() %>" />
           								<input type="hidden" name="trakmType" value="<%= trakm2.getType() %>" />
           								<input type="hidden" name="trakmHinhThuc" value="<%= trakm2.getHinhthuc() %>" />
           							<% }
	           						else
	           						{ %>
	           							<div style="width:100%; height:5px"></div>
           								<a class="addspeech" href="" title="<%= trakm2.getDiengiai() %>" onclick="return false;"><%= trakm2.getId() %></a>
           								<div style="width:100%; height:5px"></div>
           								<input id="<%= ctkm.getscheme() + ".trakhuyenmai.type" %>" type="hidden" value="<%= trakm2.getType() %>"/>
           								<% if(trakm2.getType() == 1){ %>
           									<input id="<%= ctkm.getscheme() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getTongtien() %>"/>
           								<%}else{ if(trakm2.getType() == 2){%>
           									<input id="<%= ctkm.getscheme() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getChietkhau() %>"/>
           								<%}else{ if(trakm2.getHinhthuc() != 2){ %>
	           								<input id="<%= ctkm.getscheme() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getTongGtriKm() %>"/>
           								<%}}} %>
           								<input type="hidden" name="trakmId" value="<%= trakm2.getId() %>" />
           								<input type="hidden" name="trakmType" value="<%= trakm2.getType() %>" />
           								<input type="hidden" name="trakmHinhThuc" value="<%= trakm2.getHinhthuc() %>" />
           						<%} } %>
           						</td>
           						
					            <td rowspan="<%= rowSpan%>" align="center">
					            
					               	<% if(trakm.getType() == 3 && trakm.getHinhthuc() == 2) {%>
					               		<input type="text" id="<%= ctkm.getscheme() + ".trakhuyenmai" %>" size="12" value="" readonly name="ttTrakm"/>
					            	<%}else{ %> 
					            		<input type="text" id="<%= ctkm.getscheme() + ".trakhuyenmai" %>" size="12" value="<%= Math.round(tongtienTraKM) %>" readonly name="ttTrakm" />
					            	<%}%>
					            	
					            	<input type="hidden" id="<%= ctkm.getscheme() + ".tonggiatridh" %>"  value="<%= xlkm.getTonggiatriDh() %>" />
					            	<input type="hidden" name="spSelected" value="" id="<%= ctkm.getscheme() + ".trakhuyenmai.sanpham.Id" %>"/>
					            	<input type="hidden" name="traKmType" value="<%= trakm.getType() + "-" + trakm.getHinhthuc() %>"/>
					            </td>
					             <td rowspan="<%= rowSpan%>" align="center">
					            	<%= Math.round(ctkm.getNgansach() - ctkm.getDasudung()) %>
					            	<input type="hidden" name = "nganSachCtkm" value="<%= Math.round(ctkm.getNgansach() - ctkm.getDasudung()) %>" />
					            </td>
					            <% pos++; %>
						        </tr>
						        
							<% } else { %>
								<tr class="tbdarkrow" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>">	
															
            					<td rowspan="<%= spList.size() %>" align="center" style="display: none">
					            	<a class="addspeech" href="" title="<%= dkkm.getDiengiai() %>" onclick="return false;">
					            	<%= dkkm.getId() %></a>
					            	<% if(dkkm.getType() == 1){ %>
					            		<sup style="color:#F00; font-size:0.8em">*</sup> <%} %>
					            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".type" %>" value="<%= dkkm.getType() %>" />
					            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".pheptoan" %>" value="<%= dkkm.getPheptoan() %>" />
					            </td>					         
					            <td align="left">
					            	<%if(sp.getTensp().length() > 25){ %>
					            		<a class="addspeech" href="" title="<%= sp.getTensp() %>" onclick="return false;"><%= tensp %></a>
					            	<% } else{%>
					            		<span><%= tensp %></span>
					            	<%} %> 
					            	<input type="hidden" value="<%= sp.getMasp() %>" />
					            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".dongia" %>" value="<%= sp.getDongia() %>" />
					            </td>
					            
           						<% if( (dkkm.getTongtien()) > 0 || (dkkm.getTongluong() > 0)){ %>
       								<td align="center">
       									<%if(dkkm.getTongtien() > 0){ %>
       										<input type="text" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh2('<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
       									<%}else{ %>
       										<input type="text" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh3('<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
       									<%} %>
       									<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".hidden" %>" value="<%= sp.getSoluongcan() %>" />
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
					            	<td rowspan="<%= spList.size() %>" align="center">
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".soxuat" %>" type="text" size="5" disabled="disabled" value="<%= dkkm.getSoxuatKM() %>" />
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".hidden" %>" type="hidden" size="5" value="<%= dkkm.getSoxuatKM() %>"/>
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".tongtien" %>" type="hidden" value="<%= dkkm.getTongtien() %>"/>
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".tonggiatri" %>" type="hidden" value="<%= tonggiatri %>"/>
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".tongluong" %>" type="hidden" value="<%= dkkm.getTongluong() %>"/>
					            		<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".tongsoluong" %>" type="hidden" value="<%= tongsoluong %>"/>
					            	</td>
           						<%}else{ %>
           							<td rowspan="<%= spList.size() %>" align="center">
           								<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".soxuat" %>" type="text" size="5" value="<%= dkkm.getSoxuatKM() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh('<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
           								<input id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".hidden" %>" type="hidden" size="5" value="<%= dkkm.getSoxuatKM() %>"/>
           							</td>          							
           						<%}%>						
           						<% pos++; %>			           
					       		</tr>
					<% } } else { %>
							<tr class="tbdarkrow" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>">							
            				<td align="left">
				            	<%if(sp.getTensp().length() > 25){ %>
				            		<a class="addspeech" href="" title="<%= sp.getTensp() %>" onclick="return false;"><%= tensp %></a>
				            	<% } else{%>
				            		<span><%= tensp %></span>
				            	<%} %>
				            	<input type="hidden" value="<%= sp.getMasp() %>" />
				            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".dongia" %>" value="<%= sp.getDongia() %>" />
					        </td>
					            
            				<% if( (dkkm.getTongtien()) > 0 || (dkkm.getTongluong() > 0)){ %>
   								<td align="center">
  									<%if(dkkm.getTongtien() > 0){ %>
  										<input type="text" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh2('<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
  									<%}else{ %>
  										<input type="text" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".sudung" %>" size="5" value="<%= sp.getSoluongcan() %>" onkeypress="return keypress(event);" onkeyup="DieuChinh3('<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() %>')"/>
  									<%} %>
  									<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + "." + sp.getMasp() + ".hidden" %>" value="<%= sp.getSoluongcan() %>" />
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
					Sanpham sp = new Sanpham();
					if(j == 0){ %>
						<tr class="tbdarkrow">
			        	<td rowspan="<%= rowSpan %>" align="center">
			        		<%= ctkm.getscheme() %>
			        		<input type="hidden" id="<%= ctkm.getscheme() + ".pos" %>" value="<%= pos %>" />
			        		<input type="hidden" id="<%= ctkm.getscheme() + ".rowspan" %>" value="<%= rowSpan %>" />
			        		<input type="hidden" name="schemeList" value="<%= ctkm.getId() %>" />
			        		<input type="hidden" name="schemeDiengiai" value="<%= ctkm.getDiengiai() %>" />
					    </td>			        				        	
			            <td align="center" style="display: none">
			            	<a class="addspeech" href="" title="<%= dkkm.getDiengiai() %>" onclick="return false;">
			            	<%= dkkm.getId() %></a>
			            	<% if(dkkm.getType() == 1){ %>
					            <sup style="color:#F00; font-size:0.8em">*</sup> <%} %>
			            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".type" %>" value="<%= dkkm.getType() %>" />
			            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".pheptoan" %>" value="<%= dkkm.getPheptoan() %>" />			  
					    </td>   			                   		     
			            <td align="center">&nbsp;</td>
          				<td ></td>
          				<td >&nbsp;</td>
          				<td align="center">&nbsp;</td>				            
			            <td rowspan="<%= spList.size() %>" align="center">0</td>			            
			            <td rowspan="<%= rowSpan%>" align="center">
			            	<input type="text" id="<%= ctkm.getscheme() + ".tongsoxuat"%>" size="5" value="<%= ctkm.getSoxuatKM() %>" name="soxuatKM" readonly/>
			            </td>
			         
			         	<td rowspan="<%= rowSpan%>" align="center" style="padding:0px">
           						<% 
           						for(int count = 0; count < ctkm.getTrakhuyenmai().size(); count++)
           						{
           							Trakhuyenmai trakm2 = (Trakhuyenmai)ctkm.getTrakhuyenmai().get(count); 
           							if(trakm2.getType() == 3 && trakm2.getHinhthuc() == 2)
           							{ 
	      								Hashtable<String, Integer> spSl = trakm2.getSanpham_Soluong();
	       								Hashtable<String, Float> sp_dgia = trakm2.getsanpham_dongia();
	       								Hashtable<String, String> sp_tensp = trakm2.getsanpham_tensp();
	       								
	       								Enumeration<String> keys =  spSl.keys(); 
       							%>	           								
           								<fieldset>
           								<legend><a class="addspeech" href="" title="<%= trakm2.getDiengiai()%>" onclick="return false;"><%= trakm2.getId()%> <b> - T???ng l?????ng KM <%= trakm2.getTongluong() * ctkm.getSoxuatKM() %></b></a></legend>
           								
           								<% while(keys.hasMoreElements())
           								{
           									String key = keys.nextElement();%>			
   											<p><span onMouseover="ddrivetip('<%= sp_tensp.get(key) + " - Gi?? " + sp_dgia.get(key) %>', 300)"; onMouseout="hideddrivetip()" style="font-size:10pt;"><%= key %></span> &nbsp;
   											<input type="text" size="7" value="" style="text-align:right; width: 50px" name="<%= ctkm.getscheme() + "." + trakm2.getId() + ".soluong" %>" onkeyup="checkSlgTraKm('<%= ctkm.getscheme() + "." + trakm2.getId() + ".soluong" %>')"></p>
   											<input type="hidden" value="<%= sp_dgia.get(key) %>" name="<%= ctkm.getscheme() + "." + trakm2.getId() + ".dongiaSpTraKm" %>">
   											<input type="hidden" value="<%= key %>" name="<%= ctkm.getscheme() + "." + trakm2.getId() + ".maspTraKm" %>">
	           							<%}%>
           									<input type="checkbox" id="<%= ctkm.getscheme() + "." + trakm2.getId() + ".auto"%>" onchange="Auto('<%= ctkm.getscheme() + "." + trakm2.getId() + ".auto" %>')"><i style="font-size:0.7em"> T??? ?????ng ch???n</i>
           									<input type="hidden" id="<%= ctkm.getscheme() + "." + trakm2.getId() + ".tongluong" %>" value="<%= trakm2.getTongluong() * ctkm.getSoxuatKM() %>">			           								     								
           								</fieldset>
           								<input id="<%= ctkm.getscheme() + ".trakhuyenmai.tongluong" %>" type="hidden" value="<%= trakm2.getTongluong() %>"/>
           								<input type="hidden" name="trakmId" value="<%= trakm2.getId() %>" />
           								<input type="hidden" name="trakmType" value="<%= trakm2.getType() %>" />
           								<input type="hidden" name="trakmHinhThuc" value="<%= trakm2.getHinhthuc() %>" />
           							<% }
	           						else
	           						{ %>
	           							<div style="width:100%; height:5px"></div>
           								<a class="addspeech" href="" title="<%= trakm2.getDiengiai() %>" onclick="return false;"><%= trakm2.getId() %></a>
           								<div style="width:100%; height:5px"></div>
           								<input id="<%= ctkm.getscheme() + ".trakhuyenmai.type" %>" type="hidden" value="<%= trakm2.getType() %>"/>
           								<% if(trakm2.getType() == 1){ %>
           									<input id="<%= ctkm.getscheme() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getTongtien() %>"/>
           								<%}else{ if(trakm2.getType() == 2){%>
           									<input id="<%= ctkm.getscheme() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getChietkhau() %>"/>
           								<%}else{ if(trakm2.getHinhthuc() != 2){ %>
	           								<input id="<%= ctkm.getscheme() + ".trakhuyenmai.giatri" %>" type="hidden" value="<%= trakm2.getTongGtriKm() %>"/>
           								<%}}} %>
           								<input type="hidden" name="trakmId" value="<%= trakm2.getId() %>" />
           								<input type="hidden" name="trakmType" value="<%= trakm2.getType() %>" />
           								<input type="hidden" name="trakmHinhThuc" value="<%= trakm2.getHinhthuc() %>" />
           						<%} } %>
           						</td>
			         
			            <td rowspan="<%= rowSpan%>" align="center">
			            	<input type="text" id="<%= ctkm.getscheme() + ".trakhuyenmai" %>" size="12" value="<%= Math.round(tongtienTraKM) %>" readonly name="ttTrakm"/>
			            	<input  type="hidden" id="<%= ctkm.getscheme() + ".tonggiatridh" %>"  value="<%= xlkm.getTonggiatriDh() %>" />
			            	<input type="hidden" name="spSelected" value="" id="<%= ctkm.getscheme() + ".trakhuyenmai.sanpham.Id" %>" />
			            	<input type="hidden" name="traKmType" value="<%= trakm.getType() + "-" + trakm.getHinhthuc() %>"/>
			            </td>
					    <td rowspan="<%= rowSpan%>" align="center"><%= Math.round(ctkm.getNgansach() - ctkm.getDasudung()) %></td>
					    <% pos++; %>
				        </tr>
					<%}else{ %>
						<tr class="tbdarkrow">
			            <td align="center">
			            	<a class="addspeech" href="" title="<%= dkkm.getDiengiai() %>" onclick="return false;">
			            	<%= dkkm.getId() %> </a>
			            	<% if(dkkm.getType() == 1){ %>
					            <sup style="color:#F00; font-size:0.8em">*</sup> <%} %>
			            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".type" %>" value="<%= dkkm.getType() %>" />
			            	<input type="hidden" id="<%= ctkm.getscheme() + "." + dkkm.getId() + ".pheptoan" %>" value="<%= dkkm.getPheptoan() %>" />
					    </td>
			            <td align="center">&nbsp;</td>
          				<td >&nbsp;</td>
          				<td >&nbsp;</td>
          				<td align="center">&nbsp;</td>		            
			            <td rowspan="<%= spList.size() %>" align="center">0</td>
			            <% pos++; %>
				        </tr>
					<%}}}}%>
        <TR>
			<TD align="center" colspan="11" class="tbfooter">&nbsp; &nbsp;</TD>
		</TR>
		<tr>
			<td colspan="11" align="left">
			
			<div id="btnSave">
			<a class="button" href="javascript:saveform()">
              L??u l???i <img style="top: -4px;" src="../images/New.png" alt=""></img></a>
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
    	int n = 0;
    	while(n < masp.length)
    	{
    		if(masp[n] != ""){
    %>
    	<input type="hidden" name="spMa" value="<%= masp[n] %>" >
    	<input type="hidden" name="spDongia" value="<%= dongia[n] %>" >
    	<input type="hidden" name="spSoluong" value="<%= soluong[n] %>" >
    <%} n++;} %>
</fieldset>
</form>
</body>
</html>
<% 	

if(xlkm != null){
	xlkm.DBclose();
	xlkm = null;
}

%>
<%}%>