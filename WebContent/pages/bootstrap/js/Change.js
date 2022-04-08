!function()
{
	var t,
	e=document.getElementById("darkSwitch"),
	table = document.getElementById("table_1");
	if(e)
	{
		t=null!==localStorage.getItem("darkSwitch")
		&&"dark"===localStorage.getItem("darkSwitch"),
		(e.checked=t)?document.body.setAttribute("data-theme","dark"):document.body.removeAttribute("data-theme"),
		 e.addEventListener("change",
		 function(t)
		 {
			 e.checked?(document.body.setAttribute("data-theme","dark"), 
			 localStorage.setItem("darkSwitch","dark")):
			 (document.body.removeAttribute("data-theme"),localStorage.removeItem("darkSwitch"));
			 
			 e.checked?(table.setAttribute("class", "table-dark"),
			 localStorage.setItem("darkSwitch","dark")):
			(table.removeAttribute("class", "table-dark"),localStorage.removeItem("darkSwitch"));
			 
		 } )
/*		  table.classList.add('table-dark'));
*/	 }
}();
