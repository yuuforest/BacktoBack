import Logo from "../../images/logo.svg";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { Link } from "react-router-dom";

const Login = () => {
  return (
    <div>
      <div className="logo">
        <Link to="/">
          <img alt="logo" src={Logo} style={{ height: "3rem" }}></img>
        </Link>
      </div>
      <div className="flex align-items-center justify-content-center">
        <div className="surface-card p-4 shadow-2 border-round lg:w-4">
          <div className="form-content">
            <div className="form-item">
              <InputText
                id="id"
                type="text"
                placeholder="아이디"
                className="w-12 mb-3"
              />
            </div>
            <div className="form-item">
              <InputText
                id="password"
                type="password"
                placeholder="비밀번호"
                className="w-12 mb-3"
              />
            </div>
            <div className="form-item flex align-items-center justify-content-center mt-3">
              <Button
                label="회원가입"
                icon="pi pi-user"
                className="w-5 mt-5 mr-2"
              />
              <Button label="로그인" icon="pi pi-check" className="w-5 mt-5" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
