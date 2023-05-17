import { useState } from "react";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";

function ChatInput({
  onSendMessage,
  gameSeq,
  chatType,
  teamChatShow,
  topicNumber,
}) {
  const [inputValue, setInputValue] = useState("");

  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (inputValue.trim() !== "") {
      var curMessage = {
        gameSeq: gameSeq,
        memberSeq: 1, //추후 수정 필요
        memberTeamSeq: 1, //추후 수정 필요
        nickname: "닉넴", //추후 수정 필요
        message: inputValue,
        time: "",
        topicNumber: topicNumber,
      };
      onSendMessage(curMessage);
      setInputValue("");
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <InputText
          id="chat-input"
          value={inputValue}
          onChange={handleInputChange}
          placeholder="메시지 입력하기..."
          disabled={!teamChatShow && chatType === "마이팀 채팅"}
        />
        <Button
          id="chat-button"
          type="submit"
          label="전송"
          disabled={!teamChatShow && chatType === "마이팀 채팅"}
        ></Button>
      </form>
    </div>
  );
}

export default ChatInput;
