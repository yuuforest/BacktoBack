import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

export const memberSeq = atom({
  key: "memberSeq",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

export const memberId = atom({
  key: "memberId",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

export const nickname = atom({
  key: "nickname",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

export const bettingTotal = atom({
  key: "bettingTotal",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

export const bettingWin = atom({
  key: "bettingWin",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

export const point = atom({
  key: "point",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

export const isLogin = atom({
  key: "isLogin",
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const teamName = atom({
  key: "teamName",
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const teamSeq = atom({
  key: "teamSeq",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

// memberSeq: res.data.memberSeq,
//             memberId: res.data.memberId,
//             nickname: res.data.nickname,
//             bettingTotal: res.data.betting_total,
//             bettingWin: res.data.betting_win,
//             point: res.data.point,
//             teamName: res.data.teamName,
//             isLogin: true,
