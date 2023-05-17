import GameRoom from "./pages/GameRoom";
import Login from "./pages/Login";
import MyPage from "./pages/MyPage";
import Profile from "./pages/MyPage/components/Profile";
import PhotocardDetail from "./pages/Photocard/PhotocardDetail";
import PhotocardHome from "./pages/Photocard/PhotocardHome";
import GameList from "./container/GameList";
import SignUp from "./pages/Signup";

const routes = [
  {
    path: "/live",
    exact: true,
    component: GameList,
  },
  {
    path: "/mypage",
    exact: true,
    component: MyPage,
  },
  {
    path: "/photocard/home",
    exact: true,
    component: PhotocardHome,
  },
  {
    path: "/photocard/detail/:gameid",
    exact: true,
    component: PhotocardDetail,
  },
  {
    path: "/live/:gameid",
    exact: true,
    component: GameRoom,
  },
];

export default routes;

// const defaultRedirect = "/banner";
// export { defaultRedirect };
