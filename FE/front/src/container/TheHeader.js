import React from "react";
import "primeicons/primeicons.css";
import "./styles/TheHeader.css";
import Logo from "../images/logo.svg";

import { BrowserRouter as Router, Route, Link } from "react-router-dom";

const TheHeader = () => {
  return (
    <div className="header">
      <Link to="/">
        <img alt="logo" src={Logo} style={{ height: "3rem" }}></img>
      </Link>
      <Link className="menu-item-active" to="/live">
        라이브시청
      </Link>
      <Link className="menu-item" to="/photocard/home">
        포토카드
      </Link>
      <div>
        <div className="no-login">
          <Link className="nav-btn" to="/login">
            로그인
          </Link>
          <Link className="nav-btn" to="/join">
            회원가입
          </Link>
        </div>
        {/* <div className="yes-login">
          <div className="profile">
            <div className="pi pi-user" style={{ fontSize: "1rem" }}></div>
            <div>닉네임</div>
            <div className="point">100p</div>
          </div>
          <Link className="nav-btn" to="/logout">
            로그아웃
          </Link>
        </div> */}
      </div>
    </div>
  );
};

export default TheHeader;
