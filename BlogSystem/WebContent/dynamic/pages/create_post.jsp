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
	</c:when>
	<c:otherwise>
	function submitForm(){ 
		var f = document.getElementById("post");
	
		var tit = document.getElementById("title");
		var d   = document.getElementById("date");
	
		if(d.value && tit.value)
			f.submit();
		}
	</c:otherwise>
</c:choose>

function goBack() {
window.history.back();	
}

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
			<input type="text" placeholder="Title goes here ... " required="true" name="title" class="full-width" id="title" value="${requestScope.editablePost.title }"/>
			<div id="post-info"><input type="date" required="true" name="date" id="date"  value="${requestScope.editablePost.date }"/><label> | </label><input type="text" required="true" name="auth" value="${sessionScope.user.fname}&nbsp;${sessionScope.user.lname}"/></div>
			<textarea name="body" placeholder="Type here ...">${requestScope.editablePost.body }</textarea>
			<input type="hidden" name="mode" value="create" id="mode"/>
			<c:if test="${not empty requestScope.editablePost }">
				<input type="hidden" name="pid" value="${requestScope.editablePost.postId }" />
			</c:if>		
		</form>

	</div>
</body>

</html>
