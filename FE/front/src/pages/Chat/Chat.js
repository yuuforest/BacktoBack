import React, { useState, useRef, useEffect, useCallback } from "react";
import { useSelector } from "react-redux";
import SockJsClient from "react-stomp";
import ChatInput from "./components/ChatInput";
import ChatList from "./components/ChatList";
import { SelectButton } from "primereact/selectbutton";
import "./styles/Chat.css";
import {
  selectMemberId,
  selectNickName,
  selectTeamSeq,
} from "store/reducers/loginReducer";

function Chat(props) {
  const [allMessages, setAllMessages] = useState([]); //전체 채팅 메시지들을 담을 배열
  const [teamMessages, setTeamMessages] = useState([]);
  const [sendTopic, setSendTopic] = useState("");
  const [receiveTopic, setReceiveTopic] = useState("");

  //props 데이터
  // const [gameSeq, setGameSeq] = useState(null);
  // const [homeSeq, setHomeSeq] = useState(null);
  // const [awaySeq, setAwaySeq] = useState(null);
  // const [topicNumber, setTopicNumber] = useState(null);

  const [teamChatShow, setTeamChatShow] = useState(false);
  const clientRef = useRef(null);
  const wsConnectionUrl = "http://k8a708.p.ssafy.io/api/chat/ws-chat";

  const options = ["전체 채팅", "마이팀 채팅"];
  const [chatType, setChatType] = useState(options[0]);

  //redux 데이터
  const memberSeq = useSelector(selectMemberId);
  const nickname = useSelector(selectNickName);
  const memberTeamSeq = useSelector(selectTeamSeq);

  //홈 팀 시퀀스 넘버와 원정팀 시퀀스 넘버를 가져온다.
  // useEffect(() => {
  //   setGameSeq(props.gameSeq);
  //   setHomeSeq(props.homeSeq);
  //   setAwaySeq(props.awaySeq);
  //   setTopicNumber(props.topicNumber);
  // }, []);

  //myteam에 따른 teamChat on/off
  useEffect(() => {
    if (props.homeSeq && props.awaySeq) {
      if (memberTeamSeq !== props.homeSeq && memberTeamSeq !== props.awaySeq) {
        setTeamChatShow(false);
      } else {
        setTeamChatShow(true);
      }
    }
  }, []);

  //채팅 타입에 따른 토픽 설정
  useEffect(() => {
    if (chatType === "전체 채팅") {
      setSendTopic("/api/chat/kafka/chat.message.all." + props.topicNumber);
      setReceiveTopic("/topic/chat.message.all." + props.topicNumber);
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

  console.log("gameSeq: " + props.gameSeq);
  console.log("homeSeq: " + props.homeSeq);
  console.log("awaySeq: " + props.awaySeq);
  console.log("topicNumber: " + props.topicNumber);

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
          gameSeq={props.gameSeq}
          chatType={chatType}
          teamChatShow={teamChatShow}
          topicNumber={props.topicNumber}
          memberSeq={memberSeq}
          nickname={nickname}
          memberTeamSeq={memberTeamSeq}
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
