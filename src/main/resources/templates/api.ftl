<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
    <title>Test Page</title>
    <script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
    <h2>devTools</h2>
    <h2>NEW VERSION</h2>
    <h2>NEW VERSION</h2>
    <h2>NEW VERSION</h2>
    <h3>Test</h3>
    <a id="kakao-login-btn"></a>
    <a href="http://developers.kakao.com/logout"></a>
    <br><br>
    <input type="text"/>
     <input type="button" value="검색" onClick="call()" />
    

    
    <p>END</p>

    <script type="text/javascript">

       // 사용할 앱의 JavaScript 키를 설정해 주세요.
       Kakao.init('5e4d6077b3f89b6ed48397b0c0bce7eb');
       // 카카오 로그인 버튼을 생성합니다.
       Kakao.Auth.createLoginButton({
         container: '#kakao-login-btn',
         success: function(authObj) {
           alert(JSON.stringify(authObj));
         },
         fail: function(err) {
            alert(JSON.stringify(err));
         }
       });

       function call() {
            var url = 'https://dapi.kakao.com/v2/local/search/keyword.json?query=%EC%B9%B4%EC%B9%B4%EC%98%A4';
            $.ajax({
                url: url,
                headers: {
                    "Authorization": "KakaoAK 5e4d6077b3f89b6ed48397b0c0bce7eb",
                    "Referer": "http://jiminsub.iptime.org:8080/page/index",
                    "Origin": "http://jiminsub.iptime.org:8080"
                },
                success: function( result ) {
                    console.log(result);
                }
            });    
        }
    </script>
</body>
</html>