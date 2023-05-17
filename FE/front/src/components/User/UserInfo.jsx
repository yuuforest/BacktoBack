// import React from "react";
// import { useDispatch, useSelector } from "react-redux";
// import {
//   selectMemberId,
//   selectNickName,
//   selectTeamSeq,
//   selectTeamName,
//   selectAccessToken,
//   selectRefreshToken,
//   selectIsLogin,
// } from "store/reducers/loginReducer";
// import { setUser } from "store/reducers/loginReducer";
// const UserInfo = () => {
//   //불러오는 법
//   const memberId = useSelector(selectMemberId);
//   const nickname = useSelector(selectNickName);
//   const teamSeq = useSelector(selectTeamSeq);
//   const teamName = useSelector(selectTeamName);
//   const accessToken = useSelector(selectAccessToken);
//   const refreshToken = useSelector(selectRefreshToken);
//   const isLogin = useSelector(selectIsLogin);

//   const dispatch = useDispatch();

//   //저장하는 법
//   const changeUser = () => {
//     //1. await axios 호출
//     //2. 유저 정보 아래 함수로 저장
//     dispatch(setUser({ memberId: "환규", nickname: "hwan" }));
//   };

//   return (
//     <>
//       <button onClick={changeUser}>유저 변경</button>
//       멤버아이디:{memberId} <br />
//       닉네임: {nickname}
//     </>
//   );
// };

// export default UserInfo;
