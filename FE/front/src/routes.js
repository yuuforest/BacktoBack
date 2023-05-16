import GameRoom from "./pages/GameRoom";
import Login from "./pages/Login";
import MyPage from "./pages/MyPage";
import Profile from "./pages/MyPage/components/Profile";
import SignUp from "./pages/Signup";

const routes = [
  {
    path: "/live",
    exact: true,
    component: GameRoom,
  },
  {
    path: "/mypage",
    exact: true,
    component: MyPage,
  },
];

export default routes;

// const defaultRedirect = "/banner";
// export { defaultRedirect };
