import "primereact/resources/themes/lara-light-indigo/theme.css";
import "primereact/resources/primereact.min.css";
import "./Cheer.css";

import React, { useEffect, useState, useRef } from "react";
import SockJsClient from "react-stomp";
import axios from "axios";

import { Button } from "primereact/button";
import { Avatar } from "primereact/avatar";

function Cheer(props) {
  // gameSeq: 1, homeSeq: 3, awaySeq: 2

  // ============================================================================================================================

  const connectionUrl = 'http://k8a708.p.ssafy.io/api/cheer';

  const $websocket = useRef(null);

  const [homeCount, setHomeCount] = useState(0);
  const [awayCount, setAwayCount] = useState(0);

  // axios =======================================================================================================================

  const getCheerInfo = async () => {
    try {
      // 각 팀의 현재 응원수 조회
      const response = await axios.get(connectionUrl + '/game/' + props.gameSeq);
      setHomeCount(response.data.homeCount);
      setAwayCount(response.data.awayCount);
    } catch (error) {
      console.log("getCheerInfo Error");
      // console.log(error.response.data.messsage);
    }
  };

  // useEffect ===================================================================================================================

  useEffect(() => {
    console.log("always rendered......");
    getCheerInfo();
  }, []);

  // event =======================================================================================================================

  const handleHomeCheer = () => {
    // console.log("handleHomeCheer......................");
    $websocket.current.sendMessage(
      "/game/" + props.gameSeq + "/team/" + props.homeSeq
    );
  };

  const handleAwayCheer = () => {
    // console.log("handleAwayCheer.......................");
    $websocket.current.sendMessage(
      "/game/" + props.gameSeq + "/team/" + props.awaySeq
    );
  };

  // =============================================================================================================================

  return (
    <div>
      <SockJsClient
        url="http://k8a708.p.ssafy.io/api/cheer/ws"
        topics={["/topics/game/" + props.gameSeq]}
        onMessage={(response) => {
          // console.log ("I can WebSocket! Yeah!");
          setHomeCount(response.homeCount);
          setAwayCount(response.awayCount);
        }}
        ref={$websocket}
      />

      <div className="total-div m-1">
        <Button
          className="custom-home-btn"
          label={homeCount}
          outlined={false}
          onClick={handleHomeCheer}
          raised="true"
          style={{
            backgroundImage: `url(${require("../../images/home/home" +
              props.homeSeq +
              ".png")})`,
          }}
        />
        <Avatar
          className="custom-vs"
          label="VS"
          size="large"
          shape="circle"
          raised
        />
        <Button
          className="custom-away-btn"
          label={awayCount}
          outlined={false}
          onClick={handleAwayCheer}
          raised="true"
          style={{
            backgroundImage: `url(${require("../../images/away/away" +
              props.awaySeq +
              ".png")})`,
          }}
        />
      </div>
    </div>
  );
}

export default Cheer;
