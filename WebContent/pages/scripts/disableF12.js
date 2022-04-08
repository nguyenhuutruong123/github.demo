var isChrome = !!window.chrome && !!window.chrome.webstore;
var isFirefox = typeof InstallTrigger !== 'undefined';
var dev_mode = false;
if (isChrome || isFirefox) {
	
	if (isChrome) {
		
		if (!dev_mode) {
			//Redirect if detected DevTool
			$.getScript('../scripts/checkingChromeDevTool.js', function() {				
				//Base
				jdetects.create(function(status) {
					console.log('status 1: '+status);
					if (status == 'on') {
						console.clear();
						re_confirm();
					}
				});				
				//Once onChange
				jdetects.create({
					once: true,
					onchange: function(status) {
						console.log('status 2: '+status);
						if (status == 'on') {
							console.clear();
							re_confirm();
						}
					}});
			});
		}
		
		//Block shortcut open DevTool with length <= 2 for Chrome
		obj = 
		{
			single:'123', //F12
			ctrl_combine:'85' //ctrl+U
		};
		document.onkeydown = function(e) {		
			var check = true;
			var isCtrl = false;		
			if (e.ctrlKey) 
				isCtrl = true;		
			check = disable_combination(obj, e.keyCode, isCtrl);
			console.log('check is: '+check+'--e: '+e.keyCode+'--e.ctrlKey: '+e.ctrlKey);
			return check;
		};
		
		//Block shortcut open DevTool with length >= 3 for Chrome
		//Dynamic maps can not handle because Keydown event is always fired when any key is pressed down
		var map1 = {16:false, 67:false}; //ctrl+shift+c
		var map2 = {16:false, 73:false}; //ctrl+shift+I
		$(document).keydown(function(e) {
			if (e.keyCode in map1) {
				map1[e.keyCode] = true;
		        if (e.ctrlKey && map1[16] && map1[67]) {
		        	//console.log('gotcha 1');
		            return false;
		        }
		    }
			if (e.keyCode in map2) {
				map2[e.keyCode] = true;
		        if (e.ctrlKey && map2[16] && map2[73]) {
		        	//console.log('gotcha 2');
		            return false;
		        }
		    }
		})
		.keyup(function(e) {
		    if (e.keyCode in map1) {
		    	map1[e.keyCode] = false;
		    }
		    if (e.keyCode in map2) {
		    	map2[e.keyCode] = false;
		    }
		});
	}
	
	if (isFirefox) {
		obj = 
		{
			single:'123', 
			ctrl_combine:'75'
		};
	}
	
	//Disable right mouse clicking
	disable_right_mouse();
}
else {
	re_confirm();
}

function disable_right_mouse() {
	
	//Disable right mouse clicking
	var message="NoRightClicking"; 
	function defeatIE() {
		if (document.all) {(message);
		return false;}} 
	
	function defeatNS(e) {
		if (document.layers||(document.getElementById&&!document.all)) { 
			if (e.which==2||e.which==3) {(message);return false;}}} 
			
	if (document.layers) {
		document.captureEvents(Event.MOUSEDOWN);
		document.onmousedown=defeatNS;
	} 
	else {
		document.onmouseup=defeatNS;document.oncontextmenu=defeatIE;
	} 
	
	document.oncontextmenu=new Function("return false");
}

function disable_combination(obj, e, isCtrl) {

	var check = true;
	var single = obj["single"]; //Single key: F12
	var ctrl_combine = obj["ctrl_combine"]; //Ctrl + any key
	
	function check_combination(e,single,ctrl_combine) {
		var pos1 = single.indexOf(e+'');
		var pos2 = ctrl_combine.indexOf(e+'');
		console.log('pos1: '+pos1+'--pos2: '+pos2+'--e.ctrlKey: '+isCtrl);
		
		if (pos1 >= 0 || (isCtrl && pos2 >=0))
			return false; 	
		else
			return true;
	}
	
	check = check_combination(e,single,ctrl_combine);
	return check;
}

function re_confirm() {
	//window.top.location = "/ErrorSvl";
	//if (window.top != window.self) { window.top.location="/TNI/ErrorSvl"; }
	console.log('qwe');
}

function disableF12_check_file_upload(file, arrExtention ) {
	
	var ext = file.value.match(/\.([^\.]+)$/)[1];   
    for( var i = 0; i < arrExtention.length; i++ )
    {
    	if( ext == arrExtention[i] )
    	{
    		
             return;
    	}
    }
    alert('Đinh dạng file không phù hợp');
    file.value='';
}

