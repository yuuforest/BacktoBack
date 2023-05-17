import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Photocard from "./Photocard";
import styles from "./PhotocardDetail.module.css";
import axios from "axios";

import { Button } from "primereact/button";
import { Carousel } from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css";

// Image 받아오기
import stupid from "../../images/stupid.gif";

function PhotocardDetail() {
  const { gameid } = useParams();

  const [gameSeq, setGameSeq] = useState();
  const [place, setPlace] = useState();
  const [time, setTime] = useState();

  const [awayTN, setAwayTN] = useState();
  const [homeTN, setHomeTN] = useState();

  const [awaySeq, setAwaySeq] = useState();
  const [homeSeq, setHomeSeq] = useState();

  const [winTN, setWinTN] = useState();
  const [loseTN, setLoseTN] = useState();

  const [loading, setLoading] = useState(true);
  const [photocards, setPhotocards] = useState([]);

  const [HL, setHL] = useState();

  const [point, setPoint] = useState();

  // 해당 경기 받기
  const getGame = async () => {
    try {
      const response = await axios.post(
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
      const response = await axios.post(
        "http://k8a708.p.ssafy.io/api/point/photocard/getPoint"
      );
      setPoint(response.data);
    } catch (error) {
      console.log("Point 조회 불가");
    }
  };

  // 포인트 차감
  const updatePoint = async () => {
    try {
      const response = await axios.post(
        "http://k8a708.p.ssafy.io/api/point/photocard/updatePoint"
      );
      getPoint();
    } catch (error) {
      console.log("Point 조회 불가");
    }
  };

  // HL 받아오기
  const getHL = async () => {
    try {
      const response = await axios.get(
        "http://k8a708.p.ssafy.io/api/point/photocard/getHL/" + gameSeq
      );
      setHL(response.data);
      console.log(response.data);
    } catch (error) {
      console.log(gameSeq);
      console.log("HL 조회 불가");
    }
  };

  // 포토카드 구매 -> 가격 차감
  const buyPhotocard = async (teamSeq) => {
    try {
    } catch (error) {}
  };

  const homeImgPath =
    process.env.PUBLIC_URL + "/component/images/team/" + homeSeq + ".svg";
  const awayImgPath =
    process.env.PUBLIC_URL + "/component/images/team/" + awaySeq + ".svg";

  // HL 포토카드 받기
  const getPhotocards = async () => {
    const json = await (
      await fetch(
        `https://yts.mx/api/v2/list_movies.json?minimum_rating=9.8&sort_by=year`
      )
    ).json();
    setPhotocards(json.data.movies);
    setLoading(false);
  };

  // useEffect(() => {
  //   getHL();
  // }, []);

  // 초기화 시작
  useEffect(() => {
    getGame();
    getPhotocards();
    getPoint();
  }, []);

  return (
    <div>
      {loading ? (
        <div className={styles.loader}>
          <img src={stupid} alt="loading" />
        </div>
      ) : (
        <>
          <div className={styles.container}>
            <div className={styles.team__title}>
              <div>
                <img
                  src={homeImgPath}
                  alt="Team Image"
                  className={styles.team__img}
                />
              </div>
              <div>
                <h1>{homeTN}</h1>
              </div>
            </div>
            <div className={styles.game__detail}>
              <h1>오늘의 경기</h1>
              <h3>VS</h3>
              <h3>
                {time[0]} 년 {time[1]} 월 {time[2]} 일
              </h3>

              <h3>{place}</h3>
            </div>
            <div className={styles.team__title}>
              <div>
                <img
                  src={awayImgPath}
                  alt="Team Image"
                  className={styles.team__img}
                />
              </div>
              <div>
                <h1>{awayTN}</h1>
              </div>
            </div>
          </div>
          <div className={styles.carousel__container}>
            <div>
              <Carousel
                showArrows={true}
                showThumbs={true}
                infiniteLoop={true}
                autoPlay={true}
                interval={3000}
              >
                {photocards.slice(0, 15).map((photocard) => (
                  <Photocard />
                ))}
              </Carousel>
            </div>
            <div>
              <div>
                <h3>{point}</h3>
              </div>
              <div>
                <Button
                  label="100p에 구매하기"
                  icon="pi pi-check"
                  // visible={errorCheck}
                  onClick={updatePoint}
                  className="point-check"
                />
              </div>
              <div>
                <h1>{HL}</h1>
              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
}
export default PhotocardDetail;
