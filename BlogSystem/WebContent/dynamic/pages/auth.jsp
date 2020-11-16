<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style tpye="text/css">

body {
background-color: rgb(250,250,250);
margin: 0;
font-family: sans-serif;
}

form {
width: 400px;
background-color: rgb(255,255,255);
padding: 0 0 12px 0;
box-sizing: border-box;
box-shadow: 0 4px 16px rgb(135,135,135);
margin: 0 auto;
}

form h1 {
padding: 8px;
background-color: rgb(51,51,51);
color: rgb(255,255,255);
font-size: 20px;
margin-bottom: 16px;
}

.name, .full-width {
font-size: 16px;
}

form .name  {
 display: inline-block;
 padding: 4px 8px;
 width: 46%;
 margin-bottom: 12px;
}

form .full-width {
display: block;
width: 94%;
margin-left: 3%;
padding: 4px 8px;
margin-bottom: 12px;
}

input[type="submit"] {
color: rgb(255,255,255);
text-transform: uppercase;
background-color: rgb(0,49,82);
outline: none;
border: none;
padding: 8px;
font-weight: bold;
cursor: pointer;
}

.prompt {
display: inline-block;
width: 96%;
margin: 0 3% 12px 3%;
font-size: 16px;
}

.prompt span {
color: rgb(0,49,82);
font-weight: bold;
cursor: pointer;
}

#message {
width: 400px;
padding: 8px;
box-sizing: border-box;
color: rgb(255,255,255);
font-weight: bold;
margin: 32px auto;
}

</style>

<script type="text/javascript">

var signUpHeight = 0;
var signInHeight = 0;

function hide(id) {
document.getElementById(id).style.display = "none";
}

function show(id) {
document.getElementById(id).style.display = "block";
}

function toggleDisplay(code) {
document.getElementById("message").style.display = "none";
if(code == 0) {
// hide sign-up, show sign-in
hide("sign-up-form");
show("sign-in-form");
} else {
hide("sign-in-form");
show("sign-up-form");
}
}

function centerVertical(id) {
var e = document.getElementById(id);
var h;

// height of elements with display: none is 0 so below code fixes it,
// this witchery is because I used display to toggle visibility should
// have used visible which preserves the dimensions, display chnages it
if(e.style.display === "none")
{
if(id == "sign-up-form")
{
h = signUpHeight;
}
else
{
h = signInHeight;
}
}
else
{
h = e.offsetHeight;
}

var wh = window.innerHeight;
e.style.marginTop = (wh - h)/2 + "px";
}


window.onload = function () {
signUpHeight = document.getElementById("sign-up-form").offsetHeight;
signInHeight = document.getElementById("sign-in-form").offsetHeight;
centerVertical("sign-up-form");
centerVertical("sign-in-form");

var msgCode = ${requestScope.code};

if(msgCode == -1) {
	hide("sign-in-form");
	document.getElementById("message").style.display = "none";
}
else if(msgCode == 0) {
	hide("sign-up-form")
	document.getElementById("message").style.backgroundColor = "rgb(0,255,0)";
	document.getElementById("message").innerHTML = "Signed up succ ! Now sign in";
}
else if(msgCode == 1) {
	hide("sign-in-form")
	document.getElementById("message").style.backgroundColor = "rgb(255,0,0)";
	document.getElementById("message").innerHTML = "Sign up failed";	
}
else if(msgCode == 3) {
	hide("sign-up-form")
	document.getElementById("message").style.backgroundColor = "rgb(255,0,0)";
	document.getElementById("message").innerHTML = "Sign in fail";	
} else if(msgCode == 2){
	hide("sign-up-form")
	document.getElementById("message").style.backgroundColor = "rgb(0,255,0)";
	document.getElementById("message").innerHTML = "Sign in succ";		
}



}

window.onresize = function () {
centerVertical("sign-up-form");
centerVertical("sign-in-form");
}

</script>
</head>

<body>

<form method="POST" id="sign-up-form" action="auth" >
<h1>Create account</h1>
<input type="text" placeholder="Last Name" class="name" name="last_name" style="float: right; margin-right: 3%;" />
<input type="text" placeholder="First Name" required="true" class="name" name="first_name" style="margin-left: 3%;" />
<input type="email" placeholder="Email" required="true" class="full-width" name="email"/>
<input type="password" placeholder="Password" required="true" class="full-width" name="password" />
<label class="prompt">Already have an account ? <span onclick="toggleDisplay(0)">Sign In</span></label>
<input type="hidden" value="sign_up" name="auth_type" />
<input type="submit" value="Create account" class="full-width" />
</form>

<form method="POST" id="sign-in-form"  action="auth">
<h1>Log In</h1>
<input type="email" placeholder="Email" required="true" class="full-width" name="email"/>
<input type="password" placeholder="Password" required="true" class="full-width" name="password" />
<label class="prompt">Don't have an account ? <span onclick="toggleDisplay(1)">Sign Up</span></label>
<input type="hidden" value="sign_in" name="auth_type" />
<input type="submit" value="Continue" class="full-width" />
</form>

<p id="message"></p>

</body>
</html>