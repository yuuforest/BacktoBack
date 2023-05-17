import React from "react";
// import { Redirect, Route, Switch } from "react-router-dom";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import routes, { defaultRedirect } from "../routes";

const TheMain = () => (
  <main className="flex-shrink-0">
    <div className="container mt-4">
      <Routes>
        {routes.map((r) => (
          <Route path={r.path} element={<r.component />} key={r.path} />
        ))}
      </Routes>
    </div>
  </main>
);

export default TheMain;
