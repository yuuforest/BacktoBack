import React, { useEffect, useState } from "react";
import axios from "axios";

import styles from "./GameList.module.css";

import MainGames from "../component/gamelist/MainGames";
import stupid from "../component/images/PhotocardDetail/stupid.gif";

function GameList() {
  const [loading, setLoading] = useState(true);
  const [games, setGames] = useState();
  const getGames = async () => {
    try {
      // 경기 정보 받아오기
      const response = await axios.post(
        "http://localhost:8084/photocard/getGames"
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

export default GameList;
