import Betting from "../Betting";
import Cheer from "../Cheer";
import Chat from "../Chat";
import Video from "components/Video";
import "./GameRoom.css";
import { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";

const GameRoom = () => {
  const {gameid} =  useParams();

  const [gameSeq, setGameSeq] = useState(gameid);
  const [homeSeq, setHomeSeq] = useState(1);
  const [awaySeq, setAwaySeq] = useState(2);
  const [homeName, setHomeName] = useState(null);
  const [awayName, setAwayName] = useState(null);
  const [gameState, setGameState] = useState(null);
  const [topicNumber, setTopicNumber] = useState(0);

  const getGameInfo = async () => {
    try {
      // 게임 정보 받아오기
      await axios
        .get("http://k8a708.p.ssafy.io/api/business/games/" + gameSeq + "/info")
        .then((response) => {
          console.log(response.data);
          setHomeSeq(response.data.homeSeq);
          setAwaySeq(response.data.awaySeq);
          setHomeName(response.data.homeName);
          setAwayName(response.data.awayName);
          setGameState(response.data.gameActiveType);
          setTopicNumber(response.data.topicNumber);
        });
      console.log("GameRoom Info Success..............");
    } catch (error) {
      console.log("Game Info Error....");
    }
  };

  useEffect(() => {
    console.log("GameRoom render..............");
    getGameInfo();
  }, []);

  return (
    <div class="grid gameroom-container m-3">
      <div class="col-7 left">
        <div
          className="streaming"
          style={{ height: "530px", border: "1px solid black" }}
          //style해제하고 스트리밍 컴포넌트 넣으면 됩니다.
        >
          <Video {...{ gameSeq }} />
        </div>
        <div className="cheer">
          <Cheer
            {...{ gameSeq: gameSeq, homeSeq: homeSeq, awaySeq: awaySeq }}
          />
        </div>
        <div>
          <h2>{gameState}</h2>
        </div>
        <div className="betting">
          <Betting
            {...{
              gameSeq: gameSeq,
              homeSeq: homeSeq,
              awaySeq: awaySeq,
              homeName: homeName,
              awayName: awayName,
              gameActive: gameState,
            }}
          />
        </div>
      </div>
      <div class="col-5 right">
        <div className="chat">
          <Chat
            {...{
              gameSeq: gameSeq,
              homeSeq: homeSeq,
              awaySeq: awaySeq,
              topicNumber: topicNumber,
            }}
          />
        </div>
      </div>
    </div>
  );
};

export default GameRoom;
