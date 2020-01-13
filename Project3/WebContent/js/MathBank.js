function loadProblemsAtPage(n) 
{
	var pagenumber = parseInt(document.getElementById("currentpage").value);
	var categoryid = parseInt(document.getElementById("currentcategory").value);
	
	if(n == -1) 
	{
		pagenumber--;
	} 
	else 
	{
		pagenumber++;
	}
	
	if(categoryid <= 0)
	{
		document.location.href = "list_math?page=" + pagenumber;
	}
	else
	{
		document.location.href = "list_math?page=" + pagenumber + "&filter=" + categoryid;
	}
}

function goToProblemsAtPage() 
{
    var pagenumber = document.getElementById("problempage").value;
    var categoryid = parseInt(document.getElementById("currentcategory").value);
	
    if(pagenumber.length == 0) 
    {
    	pagenumber = document.getElementById("problempage2").value;
    	
    	if(pagenumber.length == 0)
    	{
    		pagenumber = 1;
    	}
	}
    
	if(categoryid <= 0)
	{
		document.location.href = "list_math?page=" + pagenumber;
	}
	else
	{
		document.location.href = "list_math?page=" + pagenumber + "&filter=" + categoryid;
	}
}
