/* eslint-disable react/prop-types */
/* eslint-disable react/destructuring-assignment */
import React, { Component } from "react";
// import Sidebar from "./components/Sidebar";
import CouponSidebar from "../../CouponSidebar";
import MyCouponUsageListView from "./components/MyCouponUsageListView";

class CouponUsage extends Component {
  render() {
    return (
      <div className="CouponUsage">
        <CouponSidebar />
        <div className="mx-auto col-6">
          <h4 className="mb-3">쿠폰 관리 - 쿠폰 등록</h4>

          <div>
            <MyCouponUsageListView />
          </div>
        </div>
      </div>
    );
  }
}

export default CouponUsage;
