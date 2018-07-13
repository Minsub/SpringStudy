<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
    <title>Test Page</title>
    <script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
    <h2>Kakao SDK</h2>
    <a id="kakao-login-btn"></a>
    <div id="kakao-pf-btn"></div>
    <a href="javascript:void addPlusFriend()">
        <img src="https://developers.kakao.com/assets/img/about/logos/plusfriend/friendadd_small_yellow_rect.png"/>
    </a>
    <div id="kakao-chat-btn"></div>
    <br><br>
    <h3>STORY SHARE TEST</h3>
    Kakao.Story.createShareButton: <a id="kakaostory-share-button"></a><br/>
    Kakao.Story.share: <button onclick="share()">SHARE</button><br/>
    Kakao.Story.open: <button onclick="open()">OPEN</button><br/>

    <h3>LOCAL KEYWORD SEARCH TEST</h3>
    <input type="text"/>
    <input type="button" value="검색" onClick="call()" />

    <p>END</p>

    <script type="text/javascript">
       //<![CDATA[

//       Kakao.init('5e4d6077b3f89b6ed48397b0c0bce7eb');

       Kakao.init('4470e27b99b19e287d3601d5cc5b7f90');
//       Kakao.init('4e27b99b19e287d3601d5cc5b7f90');

       // 카카오 로그인 버튼을 생성합니다.
       Kakao.Auth.createLoginButton({
           container: '#kakao-login-btn',
           success: function(authObj) {
               // 로그인 성공시, API를 호출합니다.
               Kakao.API.request({
                   url: '/v1/user/me',
                   success: function(res) {
                       alert(JSON.stringify(res));
                   },
                   fail: function(error) {
                       alert(JSON.stringify(error));
                   }
               });
           },
           fail: function(err) {
               alert(JSON.stringify(err));
           }
       });

       Kakao.PlusFriend.createAddFriendButton({
           container: '#kakao-pf-btn',
           plusFriendId: '_xjXcmM'
       });

       function addPlusFriend() {
           Kakao.PlusFriend.addFriend({
               plusFriendId: '_xjXcmM' // 플러스친구 홈 URL에 명시된 id로 설정합니다.
           });
       }

       Kakao.PlusFriend.createChatButton({
           container: '#kakao-chat-btn',
           plusFriendId: '_xcLqmC'
       });

       Kakao.Story.createShareButton({
           container: '#kakaostory-share-button',
           url: 'https://daum.net',
           text: '테스트 share :)'
       });

       function open() {
           Kakao.Story.open({
               url: 'http://localhost:8080',
               text: '공유할 텍스트입니다 테스트',
           });
       }

       function share() {
           Kakao.Story.share({
               url: 'https://developers.kakao.com',
               text: '카카오 개발자 사이트로 놀러오세요! #개발자 #카카오 :)'
           });
       }



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
    //]]>
    </script>
</body>
</html>