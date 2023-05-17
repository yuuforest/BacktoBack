import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  user: {
    memberId: "",
    nickname: "",
    teamSeq: "",
    teamName: "",
    accessToken: "",
    refreshToken: "",
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

export const selectMemberId = (state) => state.login.user.memberId;
export const selectNickName = (state) => state.login.user.nickname;
export const selectTeamSeq = (state) => state.login.user.teamSeq;
export const selectTeamName = (state) => state.login.user.teamName;
export const selectAccessToken = (state) => state.login.user.accessToken;
export const selectRefreshToken = (state) => state.login.user.refreshToken;
export const selectIsLogin = (state) => state.login.user.isLogin;

export default login.reducer;
