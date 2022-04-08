function addRow(stt, name, values)
	{
		tableName = document.getElementById(name);
		
		var tr = document.createElement("TR");
		var tenFields = document.createElement("TD");
		var checkAdd = document.createElement("TD");
		
		tenFields.setAttribute("class","plainlabelNew");
		tenFields.innerHTML = values;
				
		var checkbox = document.createElement("input");
		checkbox.setAttribute("type", "checkbox");
		checkbox.name = "fieldsAn";
		checkbox.value = values;
		
		checkAdd.align = "center";
		checkAdd.appendChild(checkbox);
		
		tr.appendChild(tenFields);
		tr.appendChild(checkAdd);
		
		if(stt % 2 != 0)
			tr.setAttribute("class","tblightrow");
		else
			tr.setAttribute("class","tbdarkrow");

		tableName.appendChild(tr);
	}
	
	function addRow2(stt, name, values) //them vao fields an
	{
		tableName = document.getElementById(name);
		
		var tr = document.createElement("TR");
		var tenFields = document.createElement("TD");
		var checkAdd = document.createElement("TD");
		
		tenFields.setAttribute("class","plainlabelNew");
		tenFields.innerHTML = values;
				
		var checkbox = document.createElement("input");
		checkbox.setAttribute("type", "checkbox");
		checkbox.name = "fieldsHien";
		checkbox.value = values;
		
		checkAdd.align = "center";
		checkAdd.appendChild(checkbox);
		
		tr.appendChild(tenFields);
		tr.appendChild(checkAdd);
		
		if(stt % 2 != 0)
			tr.setAttribute("class","tblightrow");
		else
			tr.setAttribute("class","tbdarkrow");

		tableName.appendChild(tr);
	}
	
	function ChuyenSangTrai()
	{	
		var fieldsHien = document.getElementsByName("fieldsHien");
		//var tbh_khName = document.getElementsByName("tbh_khName");
		
		for(var i = 0; i < fieldsHien.length; i++)
		{
			if(fieldsHien.item(i).checked)
			{
				//alert(fieldsHien.item(i).value);
				addRow(fieldsHien.length, 'AllFields', fieldsHien.item(i).value);
				document.getElementById('tbShowFields').deleteRow(fieldsHien.item(i).parentNode.parentNode.rowIndex);
			}
		}
		setTimeout(ChuyenSangTrai(), 10);
	}
	
	function ChuyenSangPhai()
	{	
		var fieldsAn = document.getElementsByName("fieldsAn");
		//var tbh_khName = document.getElementsByName("tbh_khName");
		
		for(var i = 0; i < fieldsAn.length; i++)
		{
			if(fieldsAn.item(i).checked)
			{
				//alert(fieldsHien.item(i).value);
				addRow2(fieldsAn.length, 'ShowFields', fieldsAn.item(i).value);
				document.getElementById('tbAllFields').deleteRow(fieldsAn.item(i).parentNode.parentNode.rowIndex);
			}
		}
		setTimeout(ChuyenSangPhai(), 10);
	}	