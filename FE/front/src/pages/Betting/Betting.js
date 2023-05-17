import "primereact/resources/themes/lara-light-indigo/theme.css";
import "primereact/resources/primereact.min.css";
import "./Betting.css";

import React, { useEffect, useState } from "react";
import axios from "axios";

import { InputNumber } from "primereact/inputnumber";
import { Button } from "primereact/button";
import { ToggleButton } from "primereact/togglebutton";
import { Message } from "primereact/message";
import { Card } from "primereact/card";

function Betting(props) {
  // gameSeq: 1, homeSeq: 1, awaySeq: 2, homeName: "SSG", awayName: "LG", gameActive: false

  // =============================================================================================================================

  /* axios url */
  const connectionUrl = 'http://k8a708.p.ssafy.io/api/point/betting';

  /*임시 */
  const [member, setMember] = useState(2);

  /* ToggleButton */
  const [home_checked, setHomeChecked] = useState(false);
  const [away_checked, setAwayChecked] = useState(false);
  const [homeLabel, setHomeLabel] = useState(false);
  const [awayLabel, setAwayLabel] = useState(false);

  const [check, setCheck] = useState(true);
  const [errorCheck, setErrorCheck] = useState(true);

  /* 베팅 여부 */
  const [isBetting, setIsBetting] = useState(false); // 베팅 정보 여부

  /* 베팅 Request 정보 */
  // const [bettingTeamSeq, setBettingTeamSeq] = useState(null);     // 베팅한 팀 Sequence ID
  const [bettingPoint, setBettingPoint] = useState(null); // 베팅 포인트

  const [bettingTeamName, setBettingTeamName] = useState(null); // 베팅 팀 이름

  /* 베팅 Response 정보 */
  const [divdends, setDividends] = useState(null); // 예상 배당금
  const [homePercent, setHomePercent] = useState(null); // Home팀 베팅률
  const [awayPercent, setAwayPercent] = useState(null); // Away팀 베팅률

  /* 베팅 메세지 */
  const [message, setMessage] = useState(null); // 예상 배당금 or 오류
  const [bettingMessage, setBettingMessage] = useState(null); // 베팅 정보

  // =============================================================================================================================

  const getBettingInfo = async () => {
    try {
      // 베팅 정보 받아오기
      const response = await axios.get(
        connectionUrl + '/member/' + member + '/info',
        {
          params: {
            gameID: props.gameSeq,
          },
        }
      );
      setIsBetting(true);
      // setBettingTeamSeq(response.data.teamSeq);
      setBettingPoint(response.data.bettingPoint);
      if (response.data.teamSeq === props.homeSeq) {
        setHomeChecked(true);
        setBettingTeamName(props.homeName);
      } else if (response.data.teamSeq === props.awaySeq) {
        setAwayChecked(true);
        setBettingTeamName(props.awayName);
      }
      // console.log("베팅 기록 있음");
    } catch (error) {
      // console.log("베팅 기록 없음 : Do Nothing..............");
    }
  };

  const doBetting = async (teamSeq) => {
    try {
      // 베팅 진행
      await axios.post(connectionUrl + '/member/' + member, {
        gameSeq: props.gameSeq,
        teamSeq: teamSeq,
        bettingPoint: bettingPoint,
      });
      setIsBetting(true);
      // setBettingTeamSeq(teamSeq);
      setBettingPoint(bettingPoint);
      if (teamSeq === props.homeSeq) {
        setHomeChecked(true);
        setBettingTeamName(props.homeName);
      } else if (teamSeq === props.awaySeq) {
        setAwayChecked(true);
        setBettingTeamName(props.awayName);
      }
      // console.log("베팅 완료");
    } catch (error) {
      // console.log("베팅 실패");
      setMessage(error.response.data.message);
      handleErrorMessage();
    }
  };

  const getBettingResult = async () => {
    try {
      const response = await axios.get(connectionUrl + '/member/' + member, {
        params: {
          gameID: props.gameSeq,
        },
      });
      setHomePercent(response.data.homePercent);
      setAwayPercent(response.data.awayPercent);
      setDividends(response.data.divdends);
      // console.log("베팅 결과 전달 완료");
    } catch (error) {
      setMessage(error.response.data.message);
      handleErrorMessage();
    }
  };

  // =============================================================================================================================

  useEffect(() => {
    // 항상 실행
    // console.log('always rendered......');
    getBettingInfo();
  }, []);

  useEffect(() => {
    setHomeLabel(props.homeName);
    setAwayLabel(props.awayName);
  }, [props.awayName, props.homeName]);

  useEffect(() => {
    setBettingMessage(
      bettingTeamName + "팀에 " + bettingPoint + "P 베팅하셨습니다."
    );
  }, [bettingTeamName, bettingPoint]);

  useEffect(() => {
    setMessage(
      bettingTeamName + " 승리 시 예상 배당금 : " + divdends + " P 획득"
    );
    if (homePercent !== null && awayPercent !== null) {
      setHomeLabel(props.homeName + " " + homePercent + "%");
      setAwayLabel(props.awayName + " " + awayPercent + "%");
    }
  }, [divdends]);

  useEffect(() => {
    setTimeout(() => {
      setErrorCheck(true);
    }, 3000);
  }, [errorCheck]);

  // =============================================================================================================================

  /* Event */

  const handleHomeToggle = (e) => {
    if (!e.value) {
      setHomeChecked(false);
      setAwayChecked(false);
    } else {
      setHomeChecked(true);
      setAwayChecked(false);
    }
  };

  const handleAwayToggle = (e) => {
    if (!e.value) {
      setAwayChecked(false);
      setHomeChecked(false);
    } else {
      setAwayChecked(true);
      setHomeChecked(false);
    }
  };

  const handleDoBetting = () => {
    // console.log("베팅했음");
    if (bettingPoint === null || bettingPoint <= 0 || bettingPoint > 10000) {
      setMessage("베팅할 포인트는 1 이상 10,000 이하여야 합니다. ");
      handleErrorMessage();
    } else {
      if (home_checked) {
        doBetting(props.homeSeq);
      } else if (away_checked) {
        doBetting(props.awaySeq);
      } else {
        setMessage("팀을 선택해주세요! ");
        handleErrorMessage();
        // console.log("둘중 하나는 베팅하자");
      }
    }
  };

  const handleGetDividends = () => {
    // console.log("handleGetDividends....");
    setCheck(false);
    getBettingResult();
  };

  const handleErrorMessage = () => {
    setErrorCheck(false);
  };

  // =============================================================================================================================

  return (
    <div className="container-left">
      <div className="container-bottom">
        <p className="title"> 오늘의 승리 팀을 맞춰라!</p>
      </div>

      <div className="container-bottom">
        {!props.gameActive && !isBetting && (
          <div className="container-bottom-sm">
            <Card
              className="betting-card"
              header="⚠한번 등록한 베팅은 수정도, 취소도 불가능합니다. 신중히 선택해주세요."
            />
          </div>
        )}
        <ToggleButton
          onLabel={homeLabel}
          offLabel={homeLabel}
          checked={home_checked}
          onChange={handleHomeToggle}
          className="toggleLeft"
        />
        <ToggleButton
          onLabel={awayLabel}
          offLabel={awayLabel}
          checked={away_checked}
          onChange={handleAwayToggle}
          className="toggleRight"
        />
      </div>

      <div>
        {
          /* 경기 시작 전 & 베팅 안함 : 베팅 입력 창 */
          !props.gameActive && !isBetting && (
            <div>
              {errorCheck && (
                <div>
                  <InputNumber
                    value={bettingPoint}
                    onValueChange={(e) => setBettingPoint(e.value)}
                    placeholder="베팅할 포인트를 입력하세요. (최대 10,000P)"
                    className="point-input"
                  />
                  <Button
                    label="베팅"
                    icon="pi pi-check"
                    onClick={handleDoBetting}
                    className="betting-btn"
                  />
                </div>
              )}
              {!errorCheck && (
                <Message
                  className="betting-message"
                  severity="error"
                  text={message}
                />
              )}
            </div>
          )
        }
        {
          /* 경기 시작 전 & 베팅 함 : 배당 확인 버튼만 있고 실행은 안됨 */
          !props.gameActive && isBetting && (
            <div>
              <div className="container-bottom-sm">
                <Card className="betting-card" header={bettingMessage} />
              </div>
              <Button
                label="예상 배당금 확인하기"
                icon="pi pi-check"
                visible={errorCheck}
                onClick={handleErrorMessage}
                className="point-check"
              />
              {!errorCheck && (
                <Message
                  className="betting-message"
                  severity="warn"
                  text="경기 시작 전에는 배당금 확인이 불가능합니다."
                />
              )}
            </div>
          )
        }
        {
          /* 경기 시작 후 & 베팅 함 : 입력창 없음 & 베팅 기록 : 우선 버튼을 띄우자 */
          props.gameActive && isBetting && (
            <div>
              <div className="container-bottom-sm">
                <Card className="betting-card" header={bettingMessage} />
              </div>
              <Button
                label="예상 배당금 확인하기"
                icon="pi pi-check"
                visible={check}
                onClick={handleGetDividends}
                className="point-check"
              />
              {!check && <Card className="betting-card" header={message} />}
            </div>
          )
        }
        {
          /* 경기 시작 후 & 베팅 안함 : 입력창도 없고 베팅 기록도 없음 */
          props.gameActive && !isBetting && (
            <Message
              className="betting-message"
              severity="warn"
              text="베팅 기록이 없습니다."
            />
          )
        }
      </div>
    </div>
  );
}

export default Betting;
