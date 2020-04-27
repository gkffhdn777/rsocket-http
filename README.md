RSocket and HTTP
===================
RSocket 의 4가지 모델을 구현한다.
HTTP 또한 동일 하게 RSocket 모델을 따라 구현해보도록 한다.

4가지 모델
-------------------
1. Fire and forget
2. Request/Response
3. Request/Stream
3. Channel

서버 호출 관계
-------------------

#RSocket 
client -> server -> otherserver

#HTTP
restclient -> rest

주의 사항
-------------------
RSocket client 생성시 캐시 해야 한다. 
- 이유 : 캐시 하지 않으면 채널 커넥션이 계속 생성됨