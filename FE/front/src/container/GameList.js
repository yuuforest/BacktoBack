import React, { useEffect, useState } from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import routes, { defaultRedirect } from "../routes";
import axios from "axios";

import MainGames from "../pages/GameList/MainGames";
import styles from "../container/styles/GameList.module.css";
import stupid from "../images/stupid.gif";

function GameList() {
  const [loading, setLoading] = useState(true);
  const [games, setGames] = useState();
  const getGames = async () => {
    try {
      // 경기 정보 받아오기
      const response = await axios.get(
        "http://k8a708.p.ssafy.io/api/point/photocard/getGames"
      );
      setGames(response.data);
      setLoading(false);
    } catch (error) {
      console.log("Game 없음");
    }
  };
  useEffect(() => {
    getGames();
  }, []);

  console.log(games);
  return (
    <div>
      {loading ? (
        <div>
          <div className={styles.loader}>
            <img src={stupid} alt="loading" />
          </div>
        </div>
      ) : (
        <>
          <div className={styles.title__container}>
            <div style={{ fontSize: "40px", fontWeight: "900" }}>
              🎊 오늘의 경기 🎊
            </div>
            <div style={{ fontSize: "20px", fontWeight: "700" }}>
              승리팀을 맞춰 <span style={{ color: "tomato" }}>포인트</span>를
              획득하세요!
            </div>
          </div>
          <div className={styles.game__container}>
            <div>
              {games.map((game) => (
                <MainGames
                  key={game.gameSeq}
                  gameid={game.gameSeq}
                  place={game.gamePlace}
                  time={game.gameDateTime}
                  homeTeamSeq={game.homeTeam.teamSeq}
                  homeTeamName={game.homeTeam.teamName}
                  awayTeamSeq={game.awayTeam.teamSeq}
                  awayTeamName={game.awayTeam.teamName}
                />
              ))}
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default GameList;
