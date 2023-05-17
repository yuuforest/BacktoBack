import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";
import styles from "./Photocard.module.css";
import gameImage from "../../images/PhotocarHome/game__img.jpg";

function Photocard(props) {
  return (
    <div className={styles.photocard}>
      <img src={gameImage} alt={"HL"} className={styles.game__img} />
    </div>
  );
}

Photocard.propTypes = {
  id: PropTypes.number.isRequired,
  coverImg: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  summary: PropTypes.string.isRequired,
  genres: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default Photocard;
