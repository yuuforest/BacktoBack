import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  user: {
    memberSeq: "",
    memberId: "",
    nickname: "",
    bettingTotal: "",
    bettingWin: "",
    point: "",
    teamSeq: "",
    teamName: "",
    isLogin: false,
  },
};

const login = createSlice({
  name: "login",
  initialState,
  reducers: {
    setUser(state, action) {
      console.log(action.payload);
      state.user = {
        ...state.user,
        ...action.payload,
      };
    },
  },
});

export const { setUser } = login.actions;

export const selectMemberSeq = (state) => state.login.user.memberSeq;
export const selectMemberId = (state) => state.login.user.memberId;
export const selectBettingTotal = (state) => state.login.user.bettingTotal;
export const selectBettingWin = (state) => state.login.user.bettingWin;
export const selectPoint = (state) => state.login.user.point;
export const selectNickName = (state) => state.login.user.nickname;
export const selectTeamName = (state) => state.login.user.teamName;
export const selectTeamSeq = (state) => state.login.user.teamSeq;
export const selectIsLogin = (state) => state.login.user.isLogin;

export default login.reducer;
