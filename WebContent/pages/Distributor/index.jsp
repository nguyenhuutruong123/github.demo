<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="../css/autocomplete.css" />
	<script type="text/javascript" src="../scripts/jquery-1.4.2.min.js"></script>
	<script src="../scripts/jquery.autocomplete.js"></script>
	<script language="javascript">
		function replace()
		{
			//var country = document.getElementById("country0").value;		
			var arr = new Array(); //truong hop nho ten ----> code
			var ids = new Array(); //truong hop nho Code ----> ten
			var pos = new Array();
			var pos_ids = new Array();
			for(var j = 0; j < 4; j++)
			{
				var id = "country" + j;
				var code = "code" + j;
				
				arr[j] = document.getElementById(id).value;
				ids[j] = document.getElementById(code).value;
				
				pos[j] = parseInt(arr[j].indexOf("-"));
				pos_ids[j] = parseInt(ids[j].indexOf("-"));
				
				if(pos[j] >= 0)
				{
					document.getElementById(id).value = arr[j].substring(0, pos[j]);
					document.getElementById(code).value = arr[j].substring(pos[j] + 1, arr[j].lenght);
				}
				
				if(pos_ids[j] >= 0)
				{
					document.getElementById(code).value = ids[j].substring(0, pos_ids[j]);
					document.getElementById(id).value = ids[j].substring(pos_ids[j] + 1, ids[j].lenght);
				}
			}	
			setTimeout(replace, 10);
		}
		function Test()
		{
			var arr = new Array();
			for(var j=0; j < 4; j++)
			{
				var id = "country" + j;
				arr[j] = document.getElementById(id).value;
				alert(arr[j]);
			}		
		}
	</script>	

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body onload="replace()">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->


	<div style="width: 90%; margin:auto">
		<b>Country 1</b>   : <input type="text" id="country0" name="country" />
		&nbsp; &nbsp; &nbsp; <b>Code 1</b>   : <input type="text" id="code0" name="country" class="input_text" />
		<br /><br />
		<b>Country 2</b>   : <input type="text" id="country1" name="country" class="input_text" />
		&nbsp; &nbsp; &nbsp; <b>Code 2</b>   : <input type="text" id="code1" name="country" class="input_text" />
		<br /><br />
		<b>Country 3</b>   : <input type="text" id="country2" name="country" class="input_text" />
		&nbsp; &nbsp; &nbsp; <b>Code 2</b>   : <input type="text" id="code2" name="country" class="input_text" />
		<br /><br />
		<b>Country 4</b>   : <input type="text" id="country3" name="country"/>
		&nbsp; &nbsp; &nbsp; <b>Code 3</b>   : <input type="text" id="code3" name="country" class="input_text" />
		<br /><br />
	</div>

</body>
<script>
	jQuery(function()
	{
		var countries = new Array( 
			 	"Afghanistan",
				"Albania",
				"Algeria",
				"Andorra",
				"Angola",
				"Antigua and Barbuda",
				"Argentina",
				"Armenia",
				"Australia",
				"Austria",
				"Azerbaijan",
				"Bahamas",
				"Bahrain",
				"Bangladesh",
				"Barbados",
				"Belarus",
				"Belgium",
				"Belize",
				"Benin",
				"Bhutan",
				"Bolivia",
				"Bosnia and Herzegovina",
				"Botswana",
				"Brazil",
				"Brunei",
				"Bulgaria",
				"Burkina Faso",
				"Burundi",
				"Cambodia",
				"Cameroon",
				"Canada",
				"Cape Verde",
				"Central African Republic",
				"Chad",
				"Chile",
				"China",
				"Colombi",
				"Comoros",
				"Congo (Brazzaville)",
				"Congo",
				"Costa Rica",
				"Cote d'Ivoire",
				"Croatia",
				"Cuba",
				"Cyprus",
				"Czech Republic",
				"Denmark",
				"Djibouti",
				"Dominica",
				"Dominican Republic",
				"East Timor (Timor Timur)",
				"Ecuador",
				"Egypt",
				"El Salvador",
				"Equatorial Guinea",
				"Eritrea",
				"Estonia",
				"Ethiopia",
				"Fiji",
				"Finland",
				"France",
				"Gabon",
				"Gambia, The",
				"Georgia",
				"Germany",
				"Ghana",
				"Greece",
				"Grenada",
				"Guatemala",
				"Guinea",
				"Guinea-Bissau",
				"Guyana",
				"Haiti",
				"Honduras",
				"Hungary",
				"Iceland",
				"India",
				"Indonesia",
				"Iran",
				"Iraq",
				"Ireland",
				"Israel",
				"Italy",
				"Jamaica",
				"Japan",
				"Jordan",
				"Kazakhstan",
				"Kenya",
				"Kiribati",
				"Korea, North",
				"Korea, South",
				"Kuwait",
				"Kyrgyzstan",
				"Laos",
				"Latvia",
				"Lebanon",
				"Lesotho",
				"Liberia",
				"Libya",
				"Liechtenstein",
				"Lithuania",
				"Luxembourg",
				"Macedonia",
				"Madagascar",
				"Malawi",
				"Malaysia",
				"Maldives",
				"Mali",
				"Malta",
				"Marshall Islands",
				"Mauritania",
				"Mauritius",
				"Mexico",
				"Micronesia",
				"Moldova",
				"Monaco",
				"Mongolia",
				"Morocco",
				"Mozambique",
				"Myanmar",
				"Namibia",
				"Nauru",
				"Nepal",
				"Netherlands",
				"New Zealand",
				"Nicaragua",
				"Niger",
				"Nigeria",
				"Norway",
				"Oman",
				"Pakistan",
				"Palau",
				"Panama",
				"Papua New Guinea",
				"Paraguay",
				"Peru",
				"Philippines",
				"Poland",
				"Portugal",
				"Qatar",
				"Romania",
				"Russia",
				"Rwanda",
				"Saint Kitts and Nevis",
				"Saint Lucia",
				"Saint Vincent",
				"Samoa",
				"San Marino",
				"Sao Tome and Principe",
				"Saudi Arabia",
				"Senegal",
				"Serbia and Montenegro",
				"Seychelles",
				"Sierra Leone",
				"Singapore",
				"Slovakia",
				"Slovenia",
				"Solomon Islands",
				"Somalia",
				"South Africa",
				"Spain",
				"Sri Lanka",
				"Sudan",
				"Suriname",
				"Swaziland",
				"Sweden",
				"Switzerland",
				"Syria",
				"Taiwan",
				"Tajikistan",
				"Tanzania",
				"Thailand",
				"Togo",
				"Tonga",
				"Trinidad and Tobago",
				"Tunisia",
				"Turkey",
				"Turkmenistan",
				"Tuvalu",
				"Uganda",
				"Ukraine",
				"United Arab Emirates",
				"United Kingdom",
				"United States",
				"Uruguay",
				"Uzbekistan",
				"Vanuatu",
				"Vatican City",
				"Venezuela",
				"Vietnam",
				"Yemen",
				"Zambia",
				"Zimbabwe"
	);


		for(var j=0; j < 4; j++)
		{
//			$("#country" + j).autocomplete("list.jsp");
			$("#country" + j).autocomplete(countries);
			$("#code" + j).autocomplete("listId.jsp");
		}
	});
	
</script>
</html>
