import GameRoom from "./pages/GameRoom";
import Login from "./pages/Login";
import SignUp from "./pages/Signup";

const routes = [
  {
    path: "/live",
    exact: true,
    component: GameRoom,
  },
];

export default routes;

// const defaultRedirect = "/banner";
// export { defaultRedirect };
