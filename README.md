# **야구 생중계를 다채롭고 즐겁게, BacktoBack**

프로젝트 기간 : 2023.04.10 ~ 2023.05.19 (6주)

<br>

<!-- <img src=".\document\images\로고.png" width="100" height="100"> -->
<img src=".\Document\images\로고.png">

<br>

야구 경기를 더욱 다채롭고 즐겁게 만드는, **BackToBack** 입니다! <br>
중계 시작 전, 승리 팀을 예상해서 포인트로 베팅하고 배당금을 획득하세요! <br>
중계 방송 중에는, 전체 채팅과 팀 채팅을 통해서 다른 시청자들과 소통하고,  <br>
상대 팀보다 더 많이 클릭수를 올려 원하는 팀을 응원할 수 있답니다! <br>
중계 방송이 끝나면, 오늘 경기 하이라이트 영상을 통해 생성한 포토카드를 놓치지 말고 구매하세요! 

<br>

## 💌 서비스 소개

**KBO 시장 주 고객인 MZ 세대를 타겟팅**
- KBO 리그 실시간 스트리밍 중계 (저작권 문제로 영상으로 대체하여 구현)
- 베팅 : 서비스 자체 포인트 제도로 건전한 확률 게임 도입
- 하이라이트 포토카드 : 실시간으로 생성되는 하이라이트 짤을 포인트로 랜덤 구매하며 경기 종료 후에도 지속적인 흥미 유발

**재미 요소**
- 일반 채팅 & 팀 채팅 : 모든 시청자와 소통할 수 있는 채팅과 더불어 본인의 응원팀 시청자와 소통할 수 있는 팀 채팅 존재
- 응원 : 경기를 시청하며 팬들 간 경쟁적 요소 도입


<br><br>

## 🚴 팀원소개

<br>

<img src=".\Document\images\팀원소개.PNG" height="400" width="700">


<br><br>

## 🔧 기술스택
<img src=".\Document\images\기술스택2.png">

<br><br>

## 🔌 서비스 아키텍처
<img src=".\Document\images\시스템아키텍처.png">

<br>

## ☁️ 데이터베이스 구조 (ERD)
<img src=".\Document\images\erd.png">

<br>

## 🌈 주요 기능

#### **⚾️ 경기 목록 및 중계 화면** <br>
오늘 경기 목록에서 중계 화면으로 이동할 수 있습니다. <br>
(kurento Media Server를 사용하여 비디오 스트리밍 환경 구축)
<img src=".\Document\images\경기목록.png" height="450" width="600">
<img src=".\Document\images\실시간전체.png" height="450" width="600">

#### **⚾️ 베팅** <br>
경기 시작 전에 예상한 승리팀에 원하는 포인트 만큼 베팅하고 <br>
경기가 시작하면 각 팀의 베팅률과 예상 배당금을 확인할 수 있습니다. <br>
경기가 끝나면 경기 결과에 따라 예상 배당금이 지급됩니다.  <br>
(예상 승리팀에 포인트를 베팅하고, 경기 결과에 따라 배당금을 지급받는 Restful API 개발)
<img src=".\Document\images\베팅.gif"  height="400" width="700">
<img src=".\Document\images\배당금.gif"  height="400" width="700">


#### **⚾️ 응원** <br>
경기 시작 전, 후에 상관없이 원하는 팀의 클릭수를 올려 응원할 수 있습니다. <br>
(Websocket을 사용하여 원하는 팀을 클릭하여 응원하는 서비스 구현)
<img src=".\Document\images\응원.gif"  height="400" width="700"> 


#### **⚾️ 채팅** <br>
회원가입 시 선택한 My 야구 팀이 동일한 회원들만 참여 가능한 팀 채팅과 <br>
팀 관계없이 모두 참여 가능한 전체 채팅을 할 수 있습니다. <br> 
(STOMP(Websocket), Kafka를 사용하여 전체 채팅 / 마이팀 채팅 구현)
<img src=".\Document\images\마이팀채팅.gif"  height="400" width="700">
<img src=".\Document\images\전체채팅.gif"  height="400" width="700">


#### **⚾️ 포토카드** <br>
경기 중, 채팅 로그를 통해 채팅이 일시적으로 급증하는 것을 감지한 후 해당 장면에 대한 포토카드를 생성합니다. <br>
(채팅 데이터 추이를 분석하여 하이라이트 시간대 추출 → 비디오를 .GIF로 변환) <br>
경기가 끝나면 랜덤으로 사용자들이 포토카드를 구매할 수 있습니다. <br> 
(유저의 포인트를 차감하여 카드를 구매하는 Restful API 개발)
<img src=".\Document\images\포토카드구매.gif"  height="400" width="700">

#### **⚾️ 마이페이지** <br>
회원의 기본 정보와 구매한 포토카드 목록을 확인할 수 있습니다. <br>
또, 포인트 사용 내역을 리스트로 확인할 수 있습니다. 
<img src=".\Document\images\마이페이지.png" height="450" width="600">
<img src=".\Document\images\포인트로그.PNG" height="450" width="600">

<br>

## 🎥 UCC
👉 [보러가기](https://github.com/yuuforest/BacktoBack/tree/develop/Document/ucc/BacktoBack.avi)

<br/>
