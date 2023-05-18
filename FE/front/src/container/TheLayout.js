import React from "react";
import TheFooter from "./TheFooter";
import TheHeader from "./TheHeader";
import TheMain from "./TheMain";
import Main from "pages/Main";
import { useLocation } from "react-router-dom";

const TheLayout = () => {
  const location = useLocation();

  return (
    <div className="body-wrapper d-flex flex-column h-100 position-relative">
      <div className="body-content">
        <TheHeader />
        <TheMain />
      </div>
      {location.pathname === "/" && <Main />}
      <TheFooter />
    </div>
  );
};

export default TheLayout;
