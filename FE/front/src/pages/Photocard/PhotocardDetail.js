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

  // User - memberSeq 임시 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!!!!!
  const [memberSeq, setMemberSeq] = useState(1);

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
  const [photocards, setPhotocards] = useState([]);

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
        "http://k8a708.p.ssafy.io/api/point/photocard/getPoint/" + memberSeq
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
        "http://k8a708.p.ssafy.io/api/point/photocard/updatePoint/" + memberSeq
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
<<<<<<< HEAD
        "http://k8a708.p.ssafy.io/api/point/photocard/updatePhotocard/" +
          photocardSeq
=======
        "http://k8a708.p.ssafy.io/api/point/photocard/updatePhotocard/" + photocardSeq
>>>>>>> 3715b2a593957be0694bd3570392115355336e56
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
  const buyPhotocard = async () => {
    try {
      updatePoint();
      const photocardSeq = Math.floor(Math.random() * HL.length);
      updatePhotocard(photocardSeq);
      updateMyPhotocard(memberSeq, photocardSeq);
      getHL();
    } catch (error) {
      console.log("HL 구매 불가");
    }
  };

  const homeImgPath =
    process.env.PUBLIC_URL + "/component/images/team/" + homeSeq + ".svg";
  const awayImgPath =
    process.env.PUBLIC_URL + "/component/images/team/" + awaySeq + ".svg";

  // HL 포토카드 받기
  // const getPhotocards = async () => {
  //   const json = await (
  //     await fetch(
  //       `https://yts.mx/api/v2/list_movies.json?minimum_rating=9.8&sort_by=year`
  //     )
  //   ).json();
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

<<<<<<< HEAD
  // useEffect(() => {
  //   getHL();
  // }, [HL]);

=======
>>>>>>> ffc2c00a7f7d199d0a3d6630ada5df625980a4fb
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
                {photocards.slice(0, 15).map((HL) => (
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
                  onClick={buyPhotocard(gameSeq)}
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
