import React, { useState, useRef, useEffect, useCallback } from "react";
// import { useParams } from "react-router-dom";
import SockJsClient from "react-stomp";
// import axiosInstance from "../../remote/client";
import ChatInput from "./components/ChatInput";
import ChatList from "./components/ChatList";
import { SelectButton } from "primereact/selectbutton";
import "./styles/Chat.css";
// import {selectMemberId, selectNickName,selectTeamSeq} from 'store/reducers/loginReducer'

function Chat(props) {
  const [allMessages, setAllMessages] = useState([]); //전체 채팅 메시지들을 담을 배열
  const [teamMessages, setTeamMessages] = useState([]);
  const [sendTopic, setSendTopic] = useState("");
  const [receiveTopic, setReceiveTopic] = useState("");
  const [homeSeq, setHomeSeq] = useState(null);
  const [awaySeq, setAwaySeq] = useState(null);
  // const [memberTeamSeq, setMemberTeamSeq] = useState(null);
  const [teamChatShow, setTeamChatShow] = useState(false);
  const clientRef = useRef(null);
  // const wsConnectionUrl = "http://localhost:8000/api/chat/ws-chat";
  const wsConnectionUrl = "http://k8a708.p.ssafy.io/api/chat/ws-chat";

  // const { gameSeq } = useParams();
  const gameSeq = 1; //추후수정필요

  const [topicNumber, setTopicNumber] = useState(1); //이거 받아오는 api 개발해야함.

  const options = ["전체 채팅", "마이팀 채팅"];
  const [chatType, setChatType] = useState(options[0]);

  const memberTeamSeq = 1; //추후 수정 필요

  //불러오는 법
  // const memberSeq = useSelector(selectMemberId);
  // const nickname = useSelector(selectNickName);
  // const memberTeamSeq = useSelector(selectTeamSeq);

  //axios 통신 : axiosInstance.get() 형태로 변경해야함.
  // const fetchTeams = async () => {
  //   try {
  //     const response = await axiosInstance.get(
  //       `/api/chat/teams?gameSeq=${gameSeq}`
  //     );
  //     setHomeSeq(response.data.homeSeq);
  //     setAwaySeq(response.data.awaySeq);
  //   } catch (error) {
  //     console.error("Error fetching game team data", error);
  //   }
  // };

  //홈 팀 시퀀스 넘버와 원정팀 시퀀스 넘버를 가져온다.
  useEffect(() => {
    //fetchTeams();
    // setMemberTeamSeq(props.memberTeamSeq);
    setHomeSeq(props.homeSeq);
    setAwaySeq(props.awaySeq);
    setTopicNumber(props.topicNumber);
  }, []);

  //myteam에 따른 teamChat on/off
  useEffect(() => {
    if (homeSeq && awaySeq) {
      if (memberTeamSeq !== homeSeq && memberTeamSeq !== awaySeq) {
        setTeamChatShow(false);
      } else {
        setTeamChatShow(true);
      }
    }
  }, [homeSeq, awaySeq]);

  //채팅 타입에 따른 토픽 설정
  useEffect(() => {
    if (chatType === "전체 채팅") {
      setSendTopic("/api/chat/kafka/chat.message.all." + topicNumber);
      setReceiveTopic("/topic/chat.message.all." + topicNumber);
    } else if (chatType === "마이팀 채팅") {
      setSendTopic("/api/chat/kafka/chat.message.team." + memberTeamSeq);
      setReceiveTopic("/topic/chat.message.team." + memberTeamSeq);
    }
  }, [chatType]);

  //채팅방 내 메시지 전달받음 : 메시지 리스트에 메시지 추가
  const onMessageReceive = useCallback(
    (message) => {
      if (chatType === "전체 채팅") {
        setAllMessages((prevMessages) => [...prevMessages, message]);
      } else if (chatType === "마이팀 채팅") {
        setTeamMessages((prevMessages) => [...prevMessages, message]);
      }
    },
    [chatType]
  );

  //채팅 메시지 전송
  const sendMessage = (msg) => {
    if (clientRef.current) {
      clientRef.current.sendMessage(sendTopic, JSON.stringify(msg));
    }
  };

  console.log("gameSeq: " + gameSeq);
  console.log("homeSeq: " + homeSeq);
  console.log("awaySeq: " + awaySeq);
  console.log("topicNumber: " + topicNumber);

  return (
    <div>
      <div className="chat-box">
        <div className="chat-header">
          <SelectButton
            className="type-select-button"
            value={chatType}
            onChange={(e) => setChatType(e.value)}
            options={options}
          />
        </div>
        <ChatList
          messages={chatType === "전체 채팅" ? allMessages : teamMessages}
          chatType={chatType}
          teamChatShow={teamChatShow}
        />
        <ChatInput
          onSendMessage={sendMessage}
          gameSeq={gameSeq}
          chatType={chatType}
          teamChatShow={teamChatShow}
          topicNumber={topicNumber}
        />
      </div>
      <SockJsClient
        url={wsConnectionUrl}
        topics={[receiveTopic]}
        onConnect={() => console.log("Connected")}
        onDisconnect={() => console.log("Disconnected")}
        onMessage={onMessageReceive}
        ref={clientRef}
      />
    </div>
  );
}

export default Chat;
