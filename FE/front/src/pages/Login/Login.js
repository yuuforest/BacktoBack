import Logo from "../../images/logo.svg";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { Link } from "react-router-dom";
import React, { useState } from "react";
import axios from "axios";
import { setCookie } from "./cookies.js";
import { setUser } from "store/reducers/loginReducer";
import { useDispatch } from "react-redux";
import { atom } from "recoil";
import { useSetRecoilState } from "recoil";
import {
  teamName,
  memberId,
  memberSeq,
  point,
  bettingTotal,
  bettingWin,
  isLogin,
  nickname,
  teamSeq,
} from "components/State/UserState";
const Login = () => {
  const [inputId, setInputId] = useState("");
  const [inputPw, setInputPw] = useState("");
  const dispatch = useDispatch();
  const selectTeamName = useSetRecoilState(teamName);
  const selectMemberId = useSetRecoilState(memberId);
  const selectMemberSeq = useSetRecoilState(memberSeq);
  const selectPoint = useSetRecoilState(point);
  const selectbettingTotal = useSetRecoilState(bettingTotal);
  const selectbettingWin = useSetRecoilState(bettingWin);
  const selectIsLogin = useSetRecoilState(isLogin);
  const selectNickname = useSetRecoilState(nickname);
  const selectTeamSeq = useSetRecoilState(teamSeq);

  const handleInputId = (e) => {
    setInputId(e.target.value);
  };

  const handleInputPw = (e) => {
    setInputPw(e.target.value);
  };

  let data = { memberId: inputId, memberPassword: inputPw };

  const selectMember = (memberSeq) => {
    axios
      .get(`http://k8a708.p.ssafy.io/api/member/member/${memberSeq}`)
      // .get(`http://localhost:8000/api/member/member/${memberSeq}`)
      .then((res) => {
        console.log(res.data);
        // dispatch(
        //   setUser({
        //     memberSeq: res.data.memberSeq,
        //     memberId: res.data.memberId,
        //     nickname: res.data.nickname,
        //     bettingTotal: res.data.betting_total,
        //     bettingWin: res.data.betting_win,
        //     point: res.data.point,
        //     teamName: res.data.teamName,
        //     isLogin: true,
        //   })
        // );
        selectTeamName(res.data.teamName);
        selectMemberId(res.data.memberId);
        selectMemberSeq(res.data.memberSeq);
        selectNickname(res.data.nickname);
        selectbettingTotal(res.data.betting_total);
        selectbettingWin(res.data.betting_win);
        selectPoint(res.data.point);
        selectTeamSeq(res.data.teamSeq);
      });
  };

  const onClickLogin = () => {
    axios
      .post("http://k8a708.p.ssafy.io/api/auth/login", JSON.stringify(data), {
      // .post("http://localhost:8000/api/auth/login", JSON.stringify(data), {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        if (!res.data) {
        } else {
          selectIsLogin(true);

          const refreshToken = res.data.refreshToken;
          const memberSeq = res.data.memberSeq;
          {
            selectMember(memberSeq);
          }
          setCookie("refreshToken", `${refreshToken}`);
          localStorage.setItem("accessToken", res.data.accessToken);
          console.log(localStorage.getItem("accessToken"));
          document.location.href = "/";
        }
      })
      .catch((err) => {
        alert("아이디 또는 비밀번호 오류");

        console.log(err);
      });
  };

  return (
    <div>
      <div className="logo">
        <Link to="/">
          <img alt="logo" src={Logo} style={{ height: "3rem" }}></img>
        </Link>
      </div>
      <div className="flex align-items-center justify-content-center">
        <div className="surface-card p-4 shadow-2 border-round lg:w-4">
          <div className="form-content">
            <div className="form-item">
              <InputText
                id="id"
                type="text"
                placeholder="아이디"
                className="w-12 mb-3"
                value={inputId}
                onChange={handleInputId}
              />
            </div>
            <div className="form-item">
              <InputText
                id="password"
                type="password"
                placeholder="비밀번호"
                className="w-12 mb-3"
                value={inputPw}
                onChange={handleInputPw}
              />
            </div>
            <div className="form-item flex align-items-center justify-content-center mt-3">
              <Link to="/join" className="w-5 mt-5 mr-2">
              <Button
                label="회원가입"
                icon="pi pi-user"
              />
              </Link>
              <Button
                label="로그인"
                icon="pi pi-check"
                className="w-5 mt-5"
                onClick={onClickLogin}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
