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
p { width: 90vw; }
}

@media only screen and (min-width: 720px)  {
p { width: 60vw; }
}

@media only screen and (min-width: 1400px)  {
p { width: 60vw; }
}

@media only screen and (min-width: 2000px)  {
p { width: 60vw; }
}

@font-face {
font-family: libre-bold;
src: url("../../static/fonts/in_use/LibreBaskerville-Bold.ttf");
}

@font-face {
font-family: libre-regular;
src: url("../../static/fonts/in_use/LibreBaskerville-Regular.ttf");
}

@font-face {
font-family: libre-italic;
src: url("../../static/fonts/in_use/LibreBaskerville-Italic.ttf");
}


@font-face {
font-family: poppins-semibold;
src: url("../../static/fonts/in_use/Poppins-SemiBold.ttf");
}

body {
margin: 24px;;
background-color: rgb(250,250,250); /* subtle white */
font-size: 16px;
font-family: poppins-regular, sans-serif;
color: rgb(0,0,0);
}

h1 {
font-size: 24px;
font-family: libre-bold;
margin-bottom: 24px;
}

p {
font-size: 16px;
font-family: libre-regular;
margin-bottom: 20px;
}

label {
font-family: libre-italic;
}

a {
display: inline-block;
padding: 4px 8px;
color: rgb(255,255,255);
background-color: rgb(0,49,82); /* prussian color */
text-decoration: none;
font-family: poppins-semibold;
font-size: 16px;
}




</style>
</head>

<body>
	<h1>Error ${param.err}</h1>
	<p>Well that's a bummer ...</p>
	<a href="index.jsp">Take me to home page</a>
	<label>&nbsp;&nbsp;Or&nbsp;&nbsp;</label>
	<a href="index.jsp">Tell me a cool joke</a>
</body>

</html>
