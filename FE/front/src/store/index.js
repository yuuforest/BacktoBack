import { configureStore } from "@reduxjs/toolkit";
import { combineReducers } from "redux";
import LoginReducer from "./reducers/loginReducer";

const rootReducer = combineReducers({
  login: LoginReducer,
});

export const store = configureStore({
  reducer: rootReducer,
});
