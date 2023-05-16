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
        <h2 className={styles.game__title}>
          {props.homeTeamName} VS {props.awayTeamName}
        </h2>
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
