import Betting from "../Betting";
import Cheer from "../Cheer";
import Chat from "../Chat";
import "./GameRoom.css";

const GameRoom = () => {
  return (
    <div class="grid gameroom-container m-3">
      <div class="col-7 left">
        <div
          className="streaming"
          style={{ height: "530px", border: "1px solid black" }}
          //style해제하고 스트리밍 컴포넌트 넣으면 됩니다.
        ></div>
        <div className="cheer">
          <Cheer {...{ gameSeq: 4, homeSeq: 3, awaySeq: 2 }} />
        </div>
        <div className="betting">
          <Betting
            {...{
              gameSeq: 4,
              homeSeq: 3,
              awaySeq: 2,
              homeName: "LG",
              awayName: "키움",
              gameActive: true,
            }}
          />
        </div>
      </div>
      <div class="col-5 right">
        <div className="chat">
          <Chat />
          {/* <Chat {...{
            homeSeq: 1,
            awaySeq: 2,
            memberTeamSeq: 1,
            topicNumber: 1,
          }}/> */}
        </div>
      </div>
    </div>
  );
};

export default GameRoom;
