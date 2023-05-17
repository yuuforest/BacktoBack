import React, { useEffect, useState } from "react";
import axios from "axios";

import Game from "./Game";
import styles from "./PhotocardHome.module.css";

import stupid from "../../images/stupid.gif";
import TheFooter from "../../container/TheFooter";
import TheHeader from "../../container/TheHeader";

function PhotocardHome() {
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
            <h1>오늘의 포토카드</h1>
            <h3>오늘 딱 하루만 구매할 수 있는 포토카드 놓치지 마세요!</h3>
          </div>
          <div className={styles.game__container}>
            <div className={styles.games}>
              {games.map((game) => (
                <Game
                  key={game.gameSeq}
                  gameid={game.gameSeq}
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
export default PhotocardHome;
