import React, { useEffect, useState } from "react";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import "./styles/Profile.css";
import Modal from "./Modal";
import PointModal from "./PointModal";

const Profile = () => {
  const [memberTeamSeq, setMemberTeamSeq] = useState(1);
  const [nickname, setNickname] = useState("야구조아");
  const [infos, setInfos] = useState([]);
  const [imageUrl, setImageUrl] = useState("");

  var data = {
    "my-team": "SSG",
    "my-point": 200,
    "card-quantity": 9,
    "win-rate": "73.2%",
  };

  useEffect(() => {
    setInfos([data]);
    setImageUrl(process.env.PUBLIC_URL + "/team/" + memberTeamSeq + ".svg");
  }, []);

  console.log("imageUrl: " + imageUrl);

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
          <div className="nickname">{nickname}</div>
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

      {/* Modal Test */}
      <Modal />
      <PointModal />

    </div>
  );
};

export default Profile;
