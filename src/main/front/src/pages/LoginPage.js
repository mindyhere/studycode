import React, { useRef, useState } from "react";
import { useNavigate } from "react-router";
import Swal from "sweetalert2";
import "../css/Login.css";
import JoinModal from "./JoinModal";
import FindAccountModal from "./FindAccountModal";

import UserService from "../services/UserService";

function LoginPage() {
  const navigate = useNavigate();
  const email = useRef();
  const passwd = useRef();
  const [join, setJoin] = useState(false);
  const [findAcc, setFindAcc] = useState(false);
  const [option, setOption] = useState("");

  function signIn(email, passwd) {
    // console.log(email.value + "\n" + passwd.value);
    if (email.value === "") {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "이메일을 입력해주세요.",
        showConfirmButton: false,
        timer: 1500,
        didClose: () => {
          email.value = "";
          email.focus();
        },
      });
      return;
    }

    if (passwd.value === "") {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "비밀번호를 입력해주세요.",
        showConfirmButton: false,
        timer: 1500,
        didClose: () => {
          passwd.value = "";
          passwd.focus();
        },
      });
      return;
    }

    const credentials = {
      email: email.value,
      passwd: passwd.value,
    };
    UserService.setAuthUser(credentials, navigate);
  }

  return (
    <>
      <div className="main" align="center">
        <div className="organize-form form-area-signin">
          <h2 align="center">Studycode</h2>
          <br />
          <div className="form-field" style={{ width: "60%" }}>
            <input
              className="input"
              type="text"
              ref={email}
              placeholder="이메일을 입력해주세요"
              align="center"
            />
          </div>
          <br />
          <div className="form-field" style={{ width: "60%" }}>
            <input
              className="input"
              type="password"
              ref={passwd}
              placeholder="비밀번호를 입력해주세요"
              align="center"
            />
          </div>
          <br />
          <div colSpan="2" align="center">
            <button
              onClick={() => {
                signIn(email.current, passwd.current);
              }}
              className="btn-main"
            >
              Sign In
            </button>
          </div>
          <br />
          <p>
            <a
              onClick={() => {
                setOption("email");
                setFindAcc(true);
              }}
            >
              아이디 찾기&nbsp;&nbsp;
            </a>
            |
            <a
              onClick={() => {
                setOption("passwd");
                setFindAcc(true);
              }}
            >
              &nbsp; 비밀번호 찾기&nbsp;&nbsp;
            </a>
            |
            <a
              onClick={() => {
                setJoin(true);
              }}
            >
              &nbsp; 회원가입
            </a>
          </p>
        </div>
      </div>
      <JoinModal show={join} onHide={() => setJoin(false)} />
      <FindAccountModal
        opt={option}
        show={findAcc}
        onHide={() => {
          setFindAcc(false);
        }}
      />
    </>
  );
}

export default LoginPage;
