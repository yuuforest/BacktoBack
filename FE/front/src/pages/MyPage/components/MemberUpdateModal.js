import React, { useEffect, useState } from "react";
import { nickname, teamSeq } from "components/State/UserState";
import { Dialog } from "primereact/dialog";
import { useRecoilValue } from "recoil";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { Dropdown } from "primereact/dropdown";

const MemberUpdateModal = ({ showModal, closeModal }) => {
  const curNickname = useRecoilValue(nickname);
  const memberTeamSeq = useRecoilValue(teamSeq);
  const [inputId, setInputId] = useState("");
  const [selectedTeam, setSelectedTeam] = useState(null);

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

  useEffect(() => {
    setInputId(curNickname);
    const currentTeam = teams.find((team) => team.code === memberTeamSeq);
    setSelectedTeam(currentTeam);
  }, [curNickname, memberTeamSeq]);

  return (
    <div>
      <Dialog visible={showModal} onHide={closeModal} modal>
        <div className="flex align-items-center justify-content-center">
          <div className="surface-card">
            <div className="form-content">
              <div className="form-item">
                <InputText
                  id="id"
                  type="text"
                  placeholder=""
                  className="w-7 mb-3"
                  value={inputId}
                  // onChange={handleInputId}
                />
                <Button
                  label="중복확인"
                  size="small"
                  className="m-3"
                  // onClick={onClickExistId}
                ></Button>
                {/* <Button label="중복확인" icon="pi pi-check" size="small" className="m-3"/> */}
              </div>
              <div className="form-item">
                <Dropdown
                  value={selectedTeam}
                  // onChange={handleSelectedTeam}
                  options={teams}
                  optionLabel="name"
                  placeholder=""
                  className="w-12 mb-3"
                />
              </div>
              <div className="form-item">
                <Button
                  label="수정"
                  icon="pi pi-user"
                  className="w-12 mt-5"
                  // onClick={onClickSignup}
                />
              </div>
            </div>
          </div>
        </div>
      </Dialog>
    </div>
  );
};

export default MemberUpdateModal;
