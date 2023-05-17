import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

import styles from "./MainGames.module.css";

function MainGames(props) {
  const homeImgPath =
    process.env.PUBLIC_URL +
    "/component/images/team/" +
    props.homeTeamSeq +
    ".svg";
  const awayImgPath =
    process.env.PUBLIC_URL +
    "/component/images/team/" +
    props.awayTeamSeq +
    ".svg";

  return (
    <Link to={`/live/${props.gameid}`} className={styles.game}>
      <div className={styles.container}>
        <div className={styles.team__title}>
          <div className={styles.background_lt}>
            <img
              src={homeImgPath}
              alt="Team Image"
              className={styles.team__img}
            />
          </div>
          <div className={styles.foreground}>
            <h1 className={styles.h1over_lt}>{props.homeTeamName}</h1>
          </div>
        </div>

        <div className={styles.game__detail}>
          <div className={styles.vs__container}>
            <h2>VS</h2>
          </div>
          <h3>
            {props.time[0]} 년 {props.time[1]} 월 {props.time[2]} 일
          </h3>

          <h3>{props.place}</h3>
        </div>

        <div className={styles.team__title}>
          <div className={styles.background_rt}>
            <img
              src={awayImgPath}
              alt="Team Image"
              className={styles.team__img}
            />
          </div>
          <div className={styles.foreground}>
            <h1 className={styles.h1over_rt}>{props.awayTeamName}</h1>
          </div>
        </div>
      </div>
    </Link>
  );
}

MainGames.propTypes = {
  id: PropTypes.number.isRequired,
  coverImg: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  summary: PropTypes.string.isRequired,
  genres: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default MainGames;
