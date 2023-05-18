import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";
import styles from "./Game.module.css";
import gameImage from "../../images/PhotocarHome/game__img.jpg";

function Game(props) {
  return (
    <Link to={`/photocard/detail/${props.gameid}`} className={styles.game}>
      <img src={gameImage} alt={props.gameid} className={styles.game__img} />
      <div>
        <div className={styles.game__title}>
          <span style={{ fontSize: "30px", fontWeight: "bold" }}>
            {props.homeTeamName}
          </span>
          <span style={{ fontSize: "15px" }}>VS</span>
          <span style={{ fontSize: "30px", fontWeight: "bold" }}>
            {props.awayTeamName}
          </span>
        </div>
      </div>
    </Link>
  );
}

Game.propTypes = {
  id: PropTypes.number.isRequired,
  coverImg: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  summary: PropTypes.string.isRequired,
  genres: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default Game;
