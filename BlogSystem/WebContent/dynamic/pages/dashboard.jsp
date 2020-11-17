<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type="text/css">

/* width of containers and nav bar is 640px from 0 to 720px */
/* width of containers and nav bar is 720px for 720px to 1400px; */
/* width will be 960px from 1400px to 2000px */
/* width will be 1400px from and beyond 2000px */
/* 10px gap should be there between section and aside */

@media only screen and (min-width: 0px)  {
#banner { width: 640px; }
#global-nav-bar { width: 640px; }
#container { width: 640px; }
}

@media only screen and (min-width: 720px)  {
#banner { width: 720px; }
#global-nav-bar { width: 720px; }
#container { width: 720px; }
}

@media only screen and (min-width: 1400px)  {
#banner { width: 960px; }
#global-nav-bar { width: 960px; }
#container { width: 960px; }
}

@media only screen and (min-width: 2000px)  {
#banner { width: 1400px; }
#global-nav-bar { width: 1400px; }
#container { width: 1400px; }
}

@font-face {
font-family: libre-bold;
src: url("static/fonts/in_use/LibreBaskerville-Bold.ttf");
}

@font-face {
font-family: libre-regular;
src: url("static/fonts/in_use/LibreBaskerville-Regular.ttf");
}

@font-face {
font-family: poppins-regular;
src: url("static/fonts/in_use/Poppins-Regular.ttf");
}

@font-face {
font-family: poppins-light;
src: url("static/fonts/in_use/Poppins-Light.ttf");
}

@font-face {
font-family: poppins-semibold;
src: url("static/fonts/in_use/Poppins-SemiBold.ttf");
}

body {
margin: 0;
background-color: rgb(250,250,250); /* subtle white */
font-size: 16px;
font-family: poppins-regular, sans-serif;
color: rgb(0,0,0);
}

h1,h2,h3,h4,h5, p {
margin: 0;
}

#global-nav-bar {
display:block;
box-sizing: border-box;
background-color: rgb(51,51,51); /* apple black */
font-size: 0;
margin: 16px auto;
padding: 0;
}

#global-nav-bar li {
display: inline-block;
font-size: 16px;
padding: 8px 16px;
font-family: poppins-semibold;
color: rgb(255,255,255);
cursor: pointer;
}

#global-nav-bar li a {
color: rgb(255,255,255);
text-decoration: none;
}

#container {
margin: 0 auto;
box-sizing: border-box;
}

#container section {
width: 100%;
}

.post {
width: 100%;
background-color: white;
box-sizing: border-box;
padding: 8px;
margin-bottom: 8px;
box-shadow: 0 2px 4px rgb(135,135,135);
}

.post-info {
margin: 4px 0 8px 0; 
font-family: poppins-light;
font-size: 14px;
}

.post h1 {
font-size: 20px;
font-family: libre-bold;
text-align: justify;
margin-bottom: 8px;
}

.post p {
font-family: poppins-regular;
text-align: justify;
}

.read-more-wrapper {
text-align: right;
width: 100%;
margin-top: 8px;
}

.read-more {
display: inline-block;
padding: 4px 8px;
color: rgb(255,255,255);
background-color: rgb(0,49,82); /* prussian color */
text-decoration: none;
font-family: poppins-semibold;
font-size: 16px;
}

#prev-post-title {
font-size: 20px;
margin-bottom: 16px;
}

#settings-container {
width: 100vw;
height: 100vh;
display: none;
background-color: rgba(0,0,0,0.4);
position: fixed;
top: 0;
left: 0;
}

#settings-container form {
width: 400px;
background-color: rgb(255,255,255);
margin: 64px auto;
box-shadow: 0 8px 16px rgb(100,100,100);
}

#settings-container h1 {
color: rgb(255,255,255);
background-color: rgb(51,51,51);
font-size: 20px;
padding: 8px;
}

#settings-container .full-width {
display: block;
width: 100%;
padding: 4px 8px;
margin-bottom: 12px;
font-size: 16px;
font-family: poppins-regular;
}

#settings-container button {
color: rgb(255,255,255);
text-transform: uppercase;
background-color: rgb(0,49,82);
outline: none;
border: none;
padding: 8px;
font-weight: bold;
cursor: pointer;
display: inline-block;
font-size: 16px;
font-family: poppins-regular;
margin-bottom: 16px;
}

#settings-container label {
font-size: 14px;
}


#settings-container form div {
width: 100%;
padding: 0 8px;
margin: 16px 0;
box-sizing: border-box;
}


</style>

<script type="text/javascript">

function toggleView(id) {
var e = document.getElementById(id);
if(e.style.display === "block")
e.style.display = "none";
else
e.style.display = "block";
}


</script>

</head>

<body>
	
	<ul id="global-nav-bar">
		<li>${sessionScope.user.fname}&nbsp;${sessionScope.user.lname}</li>
		<li>|</li>
		<li><a href="create_post?mode=create">Create Post</a></li>
		<li onclick="toggleView('settings-container')">User Settings</li>
		<li><a href="logout">Log Out</a></li>
	</ul>
	
	<div id="container">
		<section>
			
			<c:choose>
				<c:when test = "${not empty requestScope.posts}">
					<h1 id="prev-post-title">Your previous posts</h1>
					<c:forEach var="post" items="${requestScope.posts}">					
						<div class="post">
							<h1>${post.title}</h1>
							<div class="post-info"><span>${post.date}</span><label> | </label><span class="author">${post.fname}&nbsp;${post.lname}</span></div>
							<p>${post.summary}</p>
							<div class="read-more-wrapper">
								<a href="create_post?mode=edit&postId=${post.postId}" class="read-more" target="_blank">Edit</a>
							</div>
						</div>
					</c:forEach>
				</c:when>		
				<c:otherwise>
					<h1 id="prev-post-title">No previous posts</h1>
					No posts yet, try creating one with the "Create Post" button on the black bar above ^
				</c:otherwise>
			</c:choose>		
		</section>
	</div>
	
	<div id="settings-container">
		<form method="POST" action="dashboard">
			<h1>User Settings</h1>
			<div>
				<label>First Name</label>
				<input type="text" placeholder="First Name" required="true" name="first_name" value = "${sessionScope.user.fname}"class="full-width"/>
				<label>Last Name</label>
				<input type="text" placeholder="Last Name" name="last_name" value = "${sessionScope.user.lname}" class="full-width" />
				<label>Email</label>
				<input type="email" placeholder="Email" required="true" value = "${sessionScope.user.email}" class="full-width" name="email"/>
				<label>Password</label>
				<input type="text" placeholder="Password" required="true"  value = "${sessionScope.user.passwd}"class="full-width" name="password" />
				<button type="submit">APPLY</button>
				<button type="button" onclick="toggleView('settings-container')">Close</button>
			</div>
		</form>
	</div>
	
</body>

</html>
