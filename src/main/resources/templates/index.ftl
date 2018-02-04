<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
    <title>Test Page</title>
    <script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7d5c93ed984fc4c8d72349b10f2daeca"></script>
</head>
<body>
    <h2>devTools</h2>
    <h3>Test</h3>
    <h3>Add Line!!</h3>
    <h3>Add Line 2</h3>
    <a id="kakao-login-btn"></a>
    <a href="http://developers.kakao.com/logout"></a>
    <br><br>
    <input type="text" oninput="suggest2(this)"/> <input type="button" value="검색" onClick="call()" />
    
    <div id="map" style="width:500px;height:400px;"></div>
    
    <p>END</p>

    <script type="text/javascript">

       // 사용할 앱의 JavaScript 키를 설정해 주세요.
       Kakao.init('7d5c93ed984fc4c8d72349b10f2daeca');
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



        var container = document.getElementById('map');
        var options = {
            center: new daum.maps.LatLng(33.450701, 126.570667),
            level: 3
        };

        var map = new daum.maps.Map(container, options);

        // 마우스 휠과 모바일 터치를 이용한 지도 확대, 축소를 막는다
        map.setZoomable(false);

        // 지도 타입 변경 컨트롤을 생성한다
        var mapTypeControl = new daum.maps.MapTypeControl();

        // 지도의 상단 우측에 지도 타입 변경 컨트롤을 추가한다
        map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);

        // 지도에 확대 축소 컨트롤을 생성한다
        var zoomControl = new daum.maps.ZoomControl();

        // 지도의 우측에 확대 축소 컨트롤을 추가한다
        map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);

        // 장소 검색 객체를 생성합니다
        //var ps = new daum.maps.services.Places();

        // 키워드로 장소를 검색합니다
        //ps.keywordSearch('h스퀘어', placesSearchCB);

        // 키워드 검색 완료 시 호출되는 콜백함수 입니다
        function placesSearchCB (data, status, pagination) {
            if (status === daum.maps.services.Status.OK) {

                // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
                // LatLngBounds 객체에 좌표를 추가합니다
                var bounds = new daum.maps.LatLngBounds();

                for (var i=0; i<data.length; i++) {
                    displayMarker(data[i]);
                    bounds.extend(new daum.maps.LatLng(data[i].y, data[i].x));
                }

                // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
                map.setBounds(bounds);
            }
        }

        // 지도에 마커를 표시하는 함수입니다
        function displayMarker(place) {

            // 마커를 생성하고 지도에 표시합니다
            var marker = new daum.maps.Marker({
                map: map,
                position: new daum.maps.LatLng(place.y, place.x)
            });

            // 마커에 클릭이벤트를 등록합니다
            daum.maps.event.addListener(marker, 'click', function() {
                // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
                infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
                infowindow.open(map, marker);
            });
        }
   
        
        function suggest2(e) {
            var value = encodeURIComponent(e.value);
//            var url = 'http://apihub.daum.net/local-suggest/v2/top_suggest.json?q='+value+'&appkey=aa52c36db4a468eff71df54deb2aa302';
//            var url = 'http://apihub.daum.net/local-suggest/v2/top_suggest.json?q='+value+'&appkey=f12f233215abce1a637fa26816e3c195';
//            var url = 'http://apihub.daum.net/local/v2/search/kakao/keyword.json?query=%EC%B9%B4%EC%B9%B4%EC%98%A4%ED%94%84%EB%A0%8C%EC%A6%88&x=127.0628310224993&y=37.514322572335935&radius=20000&appkey=f12f233215abce1a637fa26816e3c195';
            var url = 'https://dapi.kakao.com/v2/local/search/address.json?query=%EC%B9%B4%EC%B9%B4%EC%98%A4';
            $.ajax({
                url: url,
                headers: {
                    "Authorization": "KakaoAK 7d5c93ed984fc4c8d72349b10f2daeca",
                    "Referer": "http://jiminsub.iptime.org:8080/page/index",
                    "Origin": "http://jiminsub.iptime.org:8080"
                },
                success: function( result ) {
                    console.log(result);
                }
            });    
        }

        function call() {
             $.ajax({
                url: "/api/search?query=seoul",
                success: function( result ) {
                    console.log(result);
                }
            });

        }
        
    </script>
</body>
</html>