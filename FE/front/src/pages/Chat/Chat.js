import React, { useState, useRef, useEffect, useCallback } from "react";
import SockJsClient from "react-stomp";
import ChatInput from "./components/ChatInput";
import ChatList from "./components/ChatList";
import { SelectButton } from "primereact/selectbutton";
import "./styles/Chat.css";
import { memberSeq, nickname, teamSeq } from "components/State/UserState";
import { useRecoilValue } from "recoil";

function Chat(props) {
  const [allMessages, setAllMessages] = useState([]); //전체 채팅 메시지들을 담을 배열
  const [teamMessages, setTeamMessages] = useState([]);
  const [sendTopic, setSendTopic] = useState("");
  const [receiveTopic, setReceiveTopic] = useState("");

  const [teamChatShow, setTeamChatShow] = useState(false);
  const clientRef = useRef(null);
  const wsConnectionUrl = "http://k8a708.p.ssafy.io/api/chat/ws-chat";

  const options = ["전체 채팅", "마이팀 채팅"];
  const [chatType, setChatType] = useState(options[0]);

  //redux 데이터
  const curMemberSeq = useRecoilValue(memberSeq);
  const curNickname = useRecoilValue(nickname);
  const memberTeamSeq = useRecoilValue(teamSeq);

  console.log("################ REDUX DATA ###############");
  console.log("memberSeq: ", curMemberSeq);
  console.log("nickname: ", curNickname);
  console.log("memberTeamSeq: ", memberTeamSeq);
  console.log("############################################");

  //myteam에 따른 teamChat on/off
  useEffect(() => {
    if (props.homeSeq && props.awaySeq) {
      if (memberTeamSeq !== props.homeSeq && memberTeamSeq !== props.awaySeq) {
        setTeamChatShow(false);
      } else {
        setTeamChatShow(true);
      }
    }
  }, [props.homeSeq, props.awaySeq]);

  //채팅 타입에 따른 토픽 설정
  useEffect(() => {
    console.log("**********Topic Changed***********");
    if (chatType === "전체 채팅") {
      setSendTopic("/api/chat/kafka/chat.message.all." + props.topicNumber);
      setReceiveTopic("/topic/chat.message.all." + props.topicNumber);
      console.log("sendTopic: ", sendTopic);
      console.log("receiveTopic: ", receiveTopic);
    } else if (chatType === "마이팀 채팅") {
      setSendTopic("/api/chat/kafka/chat.message.team." + memberTeamSeq);
      setReceiveTopic("/topic/chat.message.team." + memberTeamSeq);
      console.log("sendTopic: ", sendTopic);
      console.log("receiveTopic: ", receiveTopic);
    }
  }, [chatType, props.topicNumber]);

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
          memberSeq={curMemberSeq}
          nickname={curNickname}
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
