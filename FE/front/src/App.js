import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import TheLayout from "./container";
import Login from "./pages/Login";
import SignUp from "./pages/Signup";
import PhotocardHome from "./pages/Photocard/PhotocardHome";
import PhotocardDetail from "./pages/Photocard/PhotocardDetail";

function App() {
  return (
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

    <Routes>
      <Route path="*" element={<TheLayout />} />
      <Route path="*" element={<Navigate to="/" />} />
      <Route path="/login" element={<Login />} />
      <Route path="/join" element={<SignUp />} />
      <Route path="/photocard/home" element={<PhotocardHome />} />
      <Route path="/photocard/detail/:gameid" element={<PhotocardDetail />} />
    </Routes>
  );
}

export default App;
