<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type="text/css">

<%@ include file="../includes/header_css.css" %>

#container {
margin: 16px auto;
box-sizing: border-box;
}

form .full-width {
display: inline-block;
width: 100%;
}

form #title {
background-color: rgba(0,0,0,0);
font-size: 20px;
font-family: libre-bold;
text-align: justify;
outline: none;
border: none;
}

form #post-info {
margin: 4px 0 8px 0;
font-size: 14px;
}

form #post-info input {
font-family: poppins-regular;
font-size: 14px;
background-color: rgba(0,0,0,0);
outline: none;
border: none;
}

form textarea {
font-size: 16px;
font-family: poppins-regular;
background-color: rgba(0,0,0,0);
outline: none;
border: none;
resize: none;
width: 100%;
height: 512px;
}

li {
cursor: pointer;
color: rgb(255,255,255);
}



</style>

<script type="text/javascript">



<c:choose>
	<c:when test="${not empty requestScope.editablePost}">
		function updatePost() {
			var f = document.getElementById("post");
			
			var m = document.getElementById("mode");
			m.setAttribute("value", "update");
			
			var tit = document.getElementById("title");
			var d   = document.getElementById("date");
		
			if(d.value && tit.value)
				f.submit();
			
		}
		function deletePost() {
			var f = document.getElementById("post");
			var m = document.getElementById("mode");
			m.setAttribute("value", "delete");
			f.submit();
		}
		function setDate() {
			document.getElementById("odate").innerHTML = "${requestScope.editablePost.odate}";
			document.getElementById("date").setAttribute("value", "${requestScope.editablePost.date}");
		}
		
	</c:when>
	<c:otherwise>
	function submitForm(){ 
		var f = document.getElementById("post");
	
		var tit = document.getElementById("title");
		var d   = document.getElementById("date");
	
		if(d.value && tit.value)
			f.submit();
	}
	
	function getOrdinalSuffixOf(i) {
		
	    var j = i % 10;
	    var k = i % 100;
	    
	    if (j == 1 && k != 11) {
	        return i + "st";
	    }
	    if (j == 2 && k != 12) {
	        return i + "nd";
	    }
	    if (j == 3 && k != 13) {
	        return i + "rd";
	    }
	    
	    return i + "th";
	}

	function getOdate(d) {
		
		var yy = d.substring(0, d.indexOf('-'));
		var mm = d.substring(d.indexOf('-') + 1, d.lastIndexOf('-'));
		var dd = d.substring(d.lastIndexOf('-') + 1);
		
		var months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
		
		var rd = getOrdinalSuffixOf(parseInt(dd)) + " " + months[parseInt(mm) - 1] + ", " + yy;

		return rd;
		
	}
	
	function setDate() {
		
		var d = new Date();
		
		var yy = d.getFullYear();
		var mm = d.getMonth() + 1;
		var dd = d.getDate();
		
		var nd = yy + "-" + mm + "-" + dd;
		
		document.getElementById("odate").innerHTML = getOdate(nd);
		document.getElementById("date").setAttribute("value", nd);
		
	}

	</c:otherwise>
</c:choose>

function goBack() {
window.history.back();	
}

window.onload = function() { setDate(); }


</script>
</head>

<body>

	<br />
	<br />

	<ul id="global-nav-bar">
		<li onclick="goBack()">Back</li>
		<c:choose>
			<c:when test="${not empty requestScope.editablePost}">
				<li onclick="updatePost()">Update</li>
				<li onclick="deletePost()">Delete</li>		
			</c:when>
			<c:otherwise>
				<li onclick="submitForm()">Publish</li>
			</c:otherwise>
		</c:choose>
		
	</ul>
	
	<div id="container">
	
		<form method="POST" action="create_post" id="post">
			<input type="text" placeholder="Title goes here ... [REQUIRED] " required="true" name="title" class="full-width" id="title" value="${requestScope.editablePost.title }"/>
			<div id="post-info"><span id="odate"></span><label> | </label><input type="text" required="true" name="auth" value="${sessionScope.user.fname}&nbsp;${sessionScope.user.lname}"/></div>
			<textarea name="body" placeholder="Type here ...">${requestScope.editablePost.body }</textarea>
			<input type="hidden" name="mode" value="create" id="mode"/>
			<input type="hidden" required="true" name="date" id="date"  value=""/>
			<c:if test="${not empty requestScope.editablePost }">
				<input type="hidden" name="pid" value="${requestScope.editablePost.postId }" />
			</c:if>		
		</form>

	</div>
</body>

</html>
