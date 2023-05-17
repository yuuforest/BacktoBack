import React, { useEffect, useState } from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import routes, { defaultRedirect } from "../routes";
import axios from "axios";

import TheHeader from "./TheHeader";
import TheFooter from "./TheFooter";
import MainGames from "../pages/GameList/MainGames";
import styles from "../container/styles/GameList.module.css";
import stupid from "../images/stupid.gif";

function TheMain() {
  const [loading, setLoading] = useState(true);
  const [games, setGames] = useState();
  const getGames = async () => {
    try {
      // 경기 정보 받아오기
      const response = await axios.post(
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
            <h1>오늘의 경기</h1>
            <h3>오늘의 승리팀을 맞춰 포인트를 획득하세요</h3>
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

export default TheMain;
