import { useEffect } from "react";
import { useRef, useState } from "react";
import { useParams,useNavigate  } from "react-router-dom";

import kurentoUtils from "kurento-utils";
import { v4 as uuidv4 } from "uuid";
import * as StompJs from "@stomp/stompjs";
import * as SockJS from "sockjs-client";

let webRtcPeer;

const MatchDetail = ({ gameSeq }) => {
  const videoRef = useRef(null);
  const client = useRef({});
  
  // const {gameId} = useParams();
  const [readyToStart, setReadyToStart] = useState(false);
  const [readyToVideo, setReadyToVideo] = useState(false);
  const [userId, setUserId] = useState(uuidv4());
  const navigate = useNavigate();


  useEffect(() => {
    connect();
    return () => disconnect();
  }, []);

  const connect = () => {
    client.current = new StompJs.Client({
      // brokerURL: "http://k8a708.p.ssafy.io/api/media/video", // 웹소켓 서버로 직접 접속
      webSocketFactory: () => new SockJS("http://k8a708.p.ssafy.io/api/media/video"), // proxy를 통한 접속
      connectHeaders: {
        gameId: gameSeq,
        userId: userId
      },
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        console.log("on Connect 합니다")
        subscribe();
      },
      onStompError: (frame) => {
        console.error(frame);
      },
    });

    client.current.activate();
  };

  const disconnect = () => {
    client.current.deactivate();
  };

  const subscribe = () => {
    // enterRoom();
    client.current.subscribe(`/user/${userId}/sub/${gameSeq}`, function (message) {
        var parsedMessage = JSON.parse(message.body);
        switch (parsedMessage.id) {
          case "startResponse":
            startResponse(parsedMessage);
            break;
          case "error":
            onError("Error message from server: " + parsedMessage.message);
            break;
          case "playEnd":
            alert("경기 종료")
            navigate('/live');
            break;
          case "videoInfo":
            startStream();
            setReadyToVideo(true);
            break;
          case "iceCandidate":
            webRtcPeer.addIceCandidate(
              parsedMessage.candidate,
              function (error) {
                if (error)
                  return console.error("Error adding candidate: " + error);
              }
            );
            break;
          case "seek":
            console.log(parsedMessage.message);
            break;
          case "position":
            document.getElementById("videoPosition").value =
              parsedMessage.position;
            break;
          default:
            onError("Unrecognized message", parsedMessage);
        }
      });
    
      start();
      setReadyToStart(true);
  };

  // useEffect(() => {
  //   sockJs = new SockJS("http://k8a708.p.ssafy.io/api/media/video");
  //   stomp = Stomp.over(sockJs);
  //   enterRoom();
  //   return () => {
  //     stomp.disconnect();
  //   };
  // }, []);

  // useEffect(() => {
  //   if (readyToStart === true) {
  //     console.log("start");
  //     start();
  //   }
  // }, [readyToStart]);

  useEffect(() => {
    if (readyToVideo === true) {
      console.log("start Stream");
      // startStream();
    }
  }, [readyToVideo]);

  // const enterRoom = () => {
  //   console.log("enterRoom!!!!!");
  //   const headers = { gameId: gameSeq, userId: userId };
  //   //2. connection이 맺어지면 실행
  //   stomp.connect(headers, function () {
      // stomp.subscribe(`/user/${userId}/sub/${gameSeq}`, function (message) {
      //   var parsedMessage = JSON.parse(message.body);
      //   switch (parsedMessage.id) {
      //     case "startResponse":
      //       startResponse(parsedMessage);
      //       break;
      //     case "error":
      //       onError("Error message from server: " + parsedMessage.message);
      //       break;
      //     case "playEnd":
      //       alert("경기 종료")
      //       navigate('/live');
      //       break;
      //     case "videoInfo":
      //       startStream();
      //       setReadyToVideo(true);
      //       break;
      //     case "iceCandidate":
      //       webRtcPeer.addIceCandidate(
      //         parsedMessage.candidate,
      //         function (error) {
      //           if (error)
      //             return console.error("Error adding candidate: " + error);
      //         }
      //       );
      //       break;
      //     case "seek":
      //       console.log(parsedMessage.message);
      //       break;
      //     case "position":
      //       document.getElementById("videoPosition").value =
      //         parsedMessage.position;
      //       break;
      //     default:
      //       onError("Unrecognized message", parsedMessage);
      //   }
      // });
      // start();
      // setReadyToStart(true);
  //   });
  // };

  const start = () => {
    // Video and audio by default
    var userMediaConstraints = {
      audio: true,
      video: true,
    };

  //   console.log("videoRef current" + videoRef.current);
    var options = {
      video: videoRef.current,
      mediaConstraints: userMediaConstraints,
      onicecandidate: onIceCandidate,
    };

  //   console.info("User media constraints" + userMediaConstraints);

    webRtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(
      options,
      function (error) {
        if (error) return console.error(error);
       
        console.log(onOffer);
        webRtcPeer.generateOffer(onOffer);
      }
    );
  };

  const onOffer = (error, offerSdp) => {
    if (error) return console.error("Error generating the offer");
    console.info("Invoking SDP offer callback function " + "location.host");

    var message = {
      id: "start",
      gameId: gameSeq,
      userId: userId,
      sdpOffer: offerSdp,
      // videourl: document.getElementById("videourl").value,
    };
    sendMessage(message);
  };

  const onError = (error) => {
    console.error(error);
  };

  const onIceCandidate = (candidate) => {
    console.log("Local candidate" + JSON.stringify(candidate));

    var message = {
      id: "onIceCandidate",
      gameId: gameSeq,
      userId: userId,
      candidate: candidate,
    };
    sendMessage(message);
  };

  const startResponse = (message) => {
    webRtcPeer.processAnswer(message.sdpAnswer, function (error) {
      if (error) return console.error(error);
    });
  };

  const startStream = () => {
    videoRef.current.srcObject = webRtcPeer.getRemoteStream();
  };

  const sendMessage = function (message) {
    var jsonMessage = JSON.stringify(message);
    client.current.send(`/pub/video/${message.id}`, {}, jsonMessage);
  };

  return (
    <>
      <video
        style={{
          width: "100%",
          height: "550px",
        }}
        poster={require("images/Video/beforelive.png")}
        ref={videoRef}
        autoPlay
      />
    </>
  );
};

export default MatchDetail;
