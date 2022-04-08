function Next(formName, act)
{
	document.forms[formName].action.value= act;
	document.forms[formName].submit();
}

function Prev(formName, act)
{
	document.forms[formName].action.value= act;
	document.forms[formName].submit();
}

function XemTrang(formName ,page, act)
{

	document.forms[formName].action.value= act;
	
	document.forms[formName].currentPage.value= page;
	document.forms[formName].submit();
}

function View(formName ,page, act)
{

	document.forms[formName].action.value= act;
	document.forms[formName].crrApprSplitting.value= document.forms[formName].nxtApprSplitting.value;
	document.forms[formName].nxtApprSplitting.value= page;
	document.forms[formName].submit();
}