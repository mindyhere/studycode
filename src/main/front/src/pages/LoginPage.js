import React, { useRef, useState } from "react";
import { useNavigate } from "react-router";
import Swal from "sweetalert2";
import JoinModal from "./JoinModal";
import FindAccountModal from "./FindAccountModal";
import UserService from "../services/UserService";

import "../css/Login.css";

function LoginPage() {
  const navigate = useNavigate();
  const userid = useRef();
  const passwd = useRef();
  const [join, setJoin] = useState(false);
  const [findAcc, setFindAcc] = useState(false);
  const [option, setOption] = useState("");

  function signIn(userid, passwd) {
    // console.log(email.value + "\n" + passwd.value);
    if (userid.value === "") {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "야이디를 입력해주세요.",
        showConfirmButton: false,
        timer: 1500,
        didClose: () => {
          userid.value = "";
          userid.focus();
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
      userid: userid.value,
      passwd: passwd.value,
    };
    UserService.setAuthUser(credentials, navigate);
  }

  return (
    <>
      <div className="main" align="center">
        <div className="organize-form form-area-signin">
          <h2 align="center" onClick={() => navigate("/")} style={{cursor:"pointer"}}>Studycode</h2>
          <br />
          <div className="form-field" style={{ width: "60%" }}>
            <input
              className="input"
              type="text"
              ref={userid}
              placeholder="아이디를 입력해주세요"
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
                signIn(userid.current, passwd.current);
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
                setOption("userid");
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
