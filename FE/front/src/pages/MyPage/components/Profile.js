import React, { useEffect, useState } from "react";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import "./styles/Profile.css";
import Modal from "../../Photocard/PhotoCardDetailModal";
import PointModal from "./PointModal";
import { useRecoilValue } from "recoil";
import {
  bettingTotal,
  bettingWin,
  nickname,
  point,
  teamName,
  teamSeq,
} from "components/State/UserState";

const Profile = ({ cards }) => {
  //redux 데이터
  const memberTeamSeq = useRecoilValue(teamSeq);
  const curNickname = useRecoilValue(nickname);
  const curTeamName = useRecoilValue(teamName);
  const curPoint = useRecoilValue(point);
  const curBettingWin = parseFloat(useRecoilValue(bettingWin));
  const curBettingTotal = parseFloat(useRecoilValue(bettingTotal));

  const winRate =
    !isNaN(curBettingWin) && !isNaN(curBettingTotal) && curBettingWin !== 0
      ? (curBettingTotal / curBettingWin) * 100
      : 0;
  const [infos, setInfos] = useState([]);
  const [imageUrl, setImageUrl] = useState("");

  useEffect(() => {
    setInfos([
      {
        "my-team": curTeamName,
        "my-point": curPoint,
        "card-quantity": cards.length,
        "win-rate": winRate + "%",
      },
    ]);
    setImageUrl(process.env.PUBLIC_URL + "/team/" + memberTeamSeq + ".svg");
  }, [cards]);

  return (
    <div className="grid profile-container mt-3 mb-3">
      <div className="col-5 profile-left grid">
        <div className="my-team-img col-5">
          <img
            alt="teamImage"
            src={imageUrl}
            style={{ width: "100%", height: "auto" }}
          ></img>
        </div>
        <div className="col-6">
          <div className="nickname">{curNickname}</div>
          <i className="pi pi-cog" style={{ fontSize: "2rem" }}></i>
        </div>
      </div>
      <div className="col-7 profile-right">
        <DataTable value={infos}>
          <Column field="my-team" header="My 팀"></Column>
          <Column field="my-point" header="포인트"></Column>
          <Column field="card-quantity" header="보유 카드"></Column>
          <Column field="win-rate" header="베팅 승률"></Column>
        </DataTable>
      </div>

      <PointModal />
    </div>
  );
};

export default Profile;
