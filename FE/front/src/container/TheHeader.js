import React from "react";
import "primeicons/primeicons.css";
import "./styles/TheHeader.css";
import Logo from "../images/logo.svg";
import axios from "axios";

import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import { isLogin, nickname, point } from "components/State/UserState";
import { useRecoilState, useRecoilValue } from "recoil";
import { removeCookie } from "pages/Login/cookies";

const TheHeader = () => {
  const [isLoggedIn, setIsLoggedIn] = useRecoilState(isLogin);
  const nicknameValue = useRecoilValue(nickname);
  const pointValue = useRecoilValue(point);

  const onClickLogout = () => {
    setIsLoggedIn(false);
    const accessToken = localStorage.getItem("accessToken");
    console.log(`Bearer ${accessToken}`);
    removeCookie("refreshToken");
    localStorage.removeItem("accessToken");
    // axios
    //   // .post("http://k8a708.p.ssafy.io/api/auth/login", JSON.stringify(data), {
    //   .post("http://localhost:8000/api/auth/logout", {
    //     headers: {
    //       "content-type": "application/json",
    //       Authorization: `Bearer ${accessToken}`,
    //     },
    //   })
    //   .then((res) => {
    //     if (!res.data) {
    //     } else {
    //       removeCookie("refreshToken");
    //       localStorage.removeItem("accessToken");

    //       document.location.href = "/";
    //     }
    //   })
    //   .catch((err) => {
    //     console.log(err);
    //   });
  };

  console.log(isLoggedIn);
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
        {isLoggedIn ? (
          <div className="yes-login">
            <div className="profile">
              <div className="pi pi-user" style={{ fontSize: "1rem" }}></div>
              <div>{nicknameValue}</div>
              <div className="point">{pointValue}p</div>
            </div>
            <Link className="nav-btn" to="/logout" onClick={onClickLogout}>
              로그아웃
            </Link>
          </div>
        ) : (
          <div className="no-login">
            <Link className="nav-btn" to="/login">
              로그인
            </Link>
            <Link className="nav-btn" to="/join">
              회원가입
            </Link>
          </div>
        )}
        {/* <div className="yes-login">
          <div className="profile">
            <div className="pi pi-user" style={{ fontSize: "1rem" }}></div>
            <div>닉네임</div>
            <div className="point">100p</div>
          </div>
          <Link className="nav-btn" to="/logout" onClick={onClickLogout}>
            로그아웃
          </Link>
        </div> */}
      </div>
    </div>
  );
};

export default TheHeader;
