import React, { useRef, useEffect } from "react";
import { Card } from "primereact/card";

function ChatList({ messages, chatType, teamChatShow }) {
  const messagesEndRef = useRef(null);

  useEffect(() => {
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollTop = messagesEndRef.current.scrollHeight;
    }
  }, [messages]);

  return (
    <div>
      <Card className="chat-list-box">
        <div className="scrollable-content" ref={messagesEndRef}>
          {chatType === "전체 채팅" || teamChatShow ? (
            messages.map((message, index) => (
              <div className="chat-list-content" key={index}>
                <p className="chat-nickname">{message.nickname}</p>
                <p className="chat-message">{message.message}</p>
              </div>
            ))
          ) : (
            <div className="not-my-team">
              <h4>마이팀 경기가 아닙니다.</h4>
            </div>
          )}
        </div>
      </Card>
    </div>
  );
}

export default ChatList;
