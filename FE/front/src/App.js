import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import TheLayout from "./container";
import Login from "./pages/Login";
import SignUp from "./pages/Signup";
import GameList from "./container/GameList";
import PhotocardHome from "./pages/Photocard/PhotocardHome";
import PhotocardDetail from "./pages/Photocard/PhotocardDetail";
import User from "components/User";
import Video from "components/Video";
import { RecoilRoot } from "recoil";

function App() {
  // <div className="App">
  //   <header className="App-header">
  //     <img src={logo} className="App-logo" alt="logo" />
  //     <p>
  //       Edit <code>src/App.js</code> and save to reload.
  //     </p>
  //     <a
  //       className="App-link"
  //       href="https://reactjs.org"
  //       target="_blank"
  //       rel="noopener noreferrer"
  //     >
  //       Learn React
  //     </a>
  //   </header>
  // </div>
  return (
    <>
      <RecoilRoot>
        <Routes>
          <Route path="*" element={<TheLayout />} />
          <Route path="*" element={<Navigate to="/" />} />
          <Route path="/login" element={<Login />} />
          <Route path="/join" element={<SignUp />} />
        </Routes>
      </RecoilRoot>
    </>
  );
}

export default App;
