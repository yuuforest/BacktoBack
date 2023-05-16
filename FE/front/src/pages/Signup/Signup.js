import Logo from "../../images/logo.svg";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import React, { useState } from "react";
import { Dropdown } from "primereact/dropdown";
import "./Signup.css";
import { Link } from "react-router-dom";

const SignUp = () => {
  const [selectedTeam, setSelectedTeam] = useState(null);
  const teams = [
    { name: "롯데 자이언츠", code: "1" },
    { name: "KIA 타이거즈", code: "2" },
    { name: "한화 이글스", code: "3" },
    { name: "두산 베어스", code: "4" },
    { name: "SSG 랜더스", code: "5" },
    { name: "삼성 라이온즈", code: "6" },
    { name: "LG 트윈스", code: "7" },
    { name: "NC 다이노스", code: "8" },
    { name: "키움 히어로즈", code: "9" },
    { name: "kt wiz", code: "10" },
  ];
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
              />
              <Button label="중복확인" size="small" className="m-3"></Button>
              {/* <Button label="중복확인" icon="pi pi-check" size="small" className="m-3"/> */}
            </div>
            <div className="form-item">
              <InputText
                id="password"
                type="password"
                placeholder="비밀번호"
                className="w-12 mb-3"
              />
            </div>
            <div className="form-item">
              <InputText
                id="nickname"
                type="text"
                placeholder="닉네임"
                className="w-8 mb-3"
              />
              <Button label="중복확인" size="small" className="m-3"></Button>
              {/* <Button label="중복확인" icon="pi pi-check" size="small" className="m-3"/> */}
            </div>
            <div className="form-item">
              <Dropdown
                value={selectedTeam}
                onChange={(e) => setSelectedTeam(e.value)}
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
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
