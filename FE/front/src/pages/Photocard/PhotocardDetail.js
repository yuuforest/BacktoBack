import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Photocard from "./Photocard";
import styles from "./PhotocardDetail.module.css";
import axios from "axios";

import { useRecoilValue } from "recoil";
import { memberSeq } from "../../components/State/UserState";
import { Button } from "primereact/button";
import { Avatar } from "primereact/avatar";
import "react-responsive-carousel/lib/styles/carousel.min.css";
import PhotoCardCarousel from "./PhotoCardCarousel";

// Image 받아오기
import stupid from "../../images/stupid.gif";

function PhotocardDetail() {
  const { gameid } = useParams();

  // User - memberSeq 임시 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!!!!!
  const memSeq = useRecoilValue(memberSeq);

  const [gameSeq, setGameSeq] = useState(null);
  const [place, setPlace] = useState();
  const [time, setTime] = useState();

  const [awayTN, setAwayTN] = useState();
  const [homeTN, setHomeTN] = useState();

  const [awaySeq, setAwaySeq] = useState();
  const [homeSeq, setHomeSeq] = useState();

  const [winTN, setWinTN] = useState();
  const [loseTN, setLoseTN] = useState();

  const [loading, setLoading] = useState(true);
  // const [photocards, setPhotocards] = useState([]);

  const [HL, setHL] = useState();

  const [point, setPoint] = useState();

  // 해당 경기 받기
  const getGame = async () => {
    try {
      const response = await axios.get(
        "http://k8a708.p.ssafy.io/api/point/photocard/getGames"
      );
      setGameSeq(response.data[gameid % 5].gameSeq);
      setPlace(response.data[gameid % 5].gamePlace);
      setTime(response.data[gameid % 5].gameDateTime);
      setAwayTN(response.data[gameid % 5].awayTeam.teamName);
      setHomeTN(response.data[gameid % 5].homeTeam.teamName);
      setWinTN(response.data[gameid % 5].winTeam.teamName);
      setLoseTN(response.data[gameid % 5].loseTeam.teamName);
      setAwaySeq(response.data[gameid % 5].awayTeam.teamSeq);
      setHomeSeq(response.data[gameid % 5].homeTeam.teamSeq);
      console.log(response.data[gameid % 5]);
    } catch (error) {
      console.log("Game 없음");
    }
  };

  // 포인트 받기
  const getPoint = async () => {
    try {
      const response = await axios.get(
        "http://k8a708.p.ssafy.io/api/point/photocard/getPoint/" + memSeq
      );
      setPoint(response.data);
    } catch (error) {
      console.log("Point 조회 불가");
    }
  };

  // 포인트 차감
  // const updatePoint = async () => {
  //   try {
  //     const response = await axios.post(
  //       "http://k8a708.p.ssafy.io/api/point/photocard/updatePoint/" + memberSeq
  //     );
  //     getPoint();
  //   } catch (error) {
  //     console.log("Point 조회 불가");
  //   }
  // };

  // HL 받아오기
  const getHL = async () => {
    try {
      const response = await axios.get(
        "http://k8a708.p.ssafy.io/api/point/photocard/getHL/" + gameSeq
      );
      setHL(response.data);
      console.log(response.data);
      setLoading(false);
    } catch (error) {
      console.log();
      console.log("HL 조회 불가");
    }
  };

  // HL 수량 차감
  const updatePhotocard = async (photocardSeq) => {
    try {
      const response = await axios.post(
        "http://k8a708.p.ssafy.io/api/point/photocard/updatePhotocard/" +
          photocardSeq
      );
    } catch (error) {
      console.log("HL 조회 불가");
    }
  };

  // 유저-HL 등록
  const updateMyPhotocard = async (memberSeq, photocardSeq) => {
    try {
      const response = await axios.post(
        "http://k8a708.p.ssafy.io/api/point/photocard/updateMyPhotocard/" +
          memberSeq +
          "/" +
          photocardSeq
      );
    } catch (error) {
      console.log("HL 조회 불가");
    }
  };

  // 포토카드 구매 | 가격 차감 > HL 랜덤 > HL 수량 차감 > 유저-HL 등록
  // const buyPhotocard = async () => {
  //   try {
  //     updatePoint();
  //     const photocardSeq = Math.floor(Math.random() * HL.length);
  //     updatePhotocard(photocardSeq);
  //     updateMyPhotocard(memberSeq, photocardSeq);
  //     // getHL();
  //   } catch (error) {
  //     console.log("HL 구매 불가");
  //   }
  // };

  const homeImgPath = process.env.PUBLIC_URL + "/team/" + homeSeq + ".svg";
  const awayImgPath = process.env.PUBLIC_URL + "/team/" + awaySeq + ".svg";

  // HL 포토카드 받기
  // const getPhotocards = async () => {
  //   const json = await (
  //     await fetch(
  //       `https://yts.mx/api/v2/list_movies.json?minimum_rating=9.8&sort_by=year`
  //     )
  //   ).json();npm
  //   setPhotocards(json.data.movies);
  //   setLoading(false);
  // };

  // useEffect(() => {
  //   getHL();
  // }, []);

  // 초기화 시작
  useEffect(() => {
    getGame();
    getPoint();
  }, []);

  useEffect(() => {
    if (gameSeq !== null) {
      getHL();
    }
  }, [gameSeq]);

  // useEffect(() => {
  //   getHL();
  // }, [HL]);

  return (
    <div>
      {loading ? (
        <div className={styles.loader}>
          <img src={stupid} alt="loading" />
        </div>
      ) : (
        <>
          {/* <div className={styles.container}>
            <div className={styles.team__title}>
              <div><img src={homeImgPath} alt= "Home Image" className={styles.team__img}/></div>
              <div><h1>{homeTN}</h1></div>
            </div>
            <div className={styles.game__detail}>
              <Avatar className="custom-vs" label="VS" size="large" shape="circle" raised/>
              <p className="photocard_game_info">{time[0]} 년 {time[1]} 월 {time[2]} 일</p>
              <p className="photocard_game_info">{place}</p>
            </div>
            <div className={styles.team__title}>
              <div><img src={awayImgPath} alt="Team Image" className={styles.team__img} /></div>
              <div><h1>{awayTN}</h1></div>
            </div>
          </div> */}

          <div className="grid game-card mt-2">
            <div className="flex justify-content-center col-5 home-info">
              <img
                className="team-image"
                src={homeImgPath}
                alt="Home Image"
              ></img>
              <div className="team-name">{homeTN}</div>
            </div>
            <div className="col-2 game-info">
              <div className="game-time">
                {time[0]} 년 {time[1]} 월 {time[2]} 일
              </div>
              <div className="game-place">{place}</div>
            </div>
            <div className="flex justify-content-center col-5 away-info">
              <img
                className="team-image"
                src={awayImgPath}
                alt="Away Image"
              ></img>
              <div className="team-name">{awayTN}</div>
            </div>
          </div>
          <div>
            <PhotoCardCarousel {...{ photocards: HL }} />
          </div>
          <div className="buy-button">
            <Button label="100p에 구매하기" className="point-check" />
          </div>
        </>
      )}
    </div>
  );
}
export default PhotocardDetail;
