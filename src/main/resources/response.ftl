<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Result</title>
</head>
<body>
<h1>Result</h1>
<ul>
  <#list urls as item>
      <li><a href="${item}">${item}</a></li>
  </#list>
</ul>
<form>
<button formaction="/">Return to search</button>
</form>
</body>
</html>