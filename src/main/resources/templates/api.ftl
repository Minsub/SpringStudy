<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
    <title>Test Page</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
    <h2>Inner Page</h2>
    <form action="/post/all">
        <input type="submit" value="submit" />
    </form>
    <div id="inner">
      <p>TEST PAGE</p>
    </div>
    <p>END</p>

    <script type="text/javascript">
      console.log("sub page!!");
    </script>
</body>
</html>