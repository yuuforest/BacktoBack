import Logo from "../../images/logo.svg";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import React, { useState } from "react";
import { Dropdown } from "primereact/dropdown";
import "./Signup.css";
import { Link } from "react-router-dom";
import axios from "axios";
import Modal from "../../components/Modal";

const SignUp = () => {
  const [selectedTeam, setSelectedTeam] = useState("");
  const [inputId, setInputId] = useState("");
  const [inputPw, setInputPw] = useState("");
  const [inputNickname, setInputNickname] = useState("");
  const [modalOpen, setModalOpen] = useState(false);
  const [checkId, setCheckId] = useState(false);
  const [checkNickname, setCheckNickname] = useState(false);

  const teams = [
    { name: "SSG 랜더스", code: "1" },
    { name: "키움 히어로즈", code: "2" },
    { name: "LG 트윈스", code: "3" },
    { name: "KT 위즈", code: "4" },
    { name: "기아 타이거즈", code: "5" },
    { name: "NC 다이노스", code: "6" },
    { name: "삼성 라이온즈", code: "7" },
    { name: "롯데 자이언트", code: "8" },
    { name: "한화 이글스", code: "9" },
    { name: "두산 베어스", code: "10" },
  ];

  const handleInputId = (e) => {
    setInputId(e.target.value);
  };

  const handleInputPw = (e) => {
    setInputPw(e.target.value);
  };

  const handleInputNickname = (e) => {
    setInputNickname(e.target.value);
  };

  const handleSelectedTeam = (e) => {
    setSelectedTeam(e.target.value);
  };
  const closeModal = () => {
    setModalOpen(false);
  };

  const onClickSignup = () => {
    let data = {
      memberId: inputId,
      memberPassword: inputPw,
      nickname: inputNickname,
      team: {
        teamSeq: selectedTeam.code,
        teamName: selectedTeam.name,
      },
    };

    if (checkId && checkNickname && selectedTeam !== "") {
      axios
        .post(
          "http://k8a708.p.ssafy.io/api/member/signup",
          JSON.stringify(data),
          {
            headers: {
              "Content-Type": "application/json",
            },
            data: {},
          }
        )
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    } else alert("아이디와 닉네임 중복확인, 팀 선택을 해주세요");
  };

  const onClickExistId = () => {
    setModalOpen(true);
    axios
      .get(`http://k8a708.p.ssafy.io/api/member/existId/${inputId}`)
      .then((res) => {
        setCheckId(true);

        alert("사용 가능한 ID입니다.");

        console.log(res.data);
      })
      .catch((err) => {
        alert("이미 사용중인  ID입니다.");

        <Modal open={modalOpen} close={closeModal} header="Modal heading">
          모달모달
        </Modal>;
        console.log(err);
      });
  };

  const onClickExistNickname = () => {
    setModalOpen(true);
    axios
      .get(`http://k8a708.p.ssafy.io/api/member/existNickname/${inputNickname}`)
      .then((res) => {
        alert("사용 가능한 닉네임 입니다.");
        setCheckNickname(true);

        console.log(res.data);
      })
      .catch((err) => {
        alert("이미 사용중인 닉네임 입니다.");

        <Modal open={modalOpen} close={closeModal} header="Modal heading">
          모달모달
        </Modal>;
        console.log(err);
      });
  };
  return (
    <div className="sign-in-container">
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
                className="w-8 mb-3"
                value={inputId}
                onChange={handleInputId}
              />
              <Button
                label="중복확인"
                size="small"
                className="m-3"
                onClick={onClickExistId}
              ></Button>
              {/* <Button label="중복확인" icon="pi pi-check" size="small" className="m-3"/> */}
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
            <div className="form-item">
              <InputText
                id="nickname"
                type="text"
                placeholder="닉네임"
                className="w-8 mb-3"
                value={inputNickname}
                onChange={handleInputNickname}
              />
              <Button
                label="중복확인"
                size="small"
                className="m-3"
                onClick={onClickExistNickname}
              ></Button>
              {/* <Button label="중복확인" icon="pi pi-check" size="small" className="m-3"/> */}
            </div>
            <div className="form-item">
              <Dropdown
                value={selectedTeam}
                onChange={handleSelectedTeam}
                options={teams}
                optionLabel="name"
                placeholder="My팀을 선택해주세요."
                className="w-12 mb-3"
              />
            </div>
            <div className="form-item">
              <Button
                label="회원가입"
                icon="pi pi-user"
                className="w-12 mt-5"
                onClick={onClickSignup}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
