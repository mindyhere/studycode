import React, { useRef, useState } from "react";
import { useNavigate } from "react-router";
import Swal from "sweetalert2";
import "../css/Login.css";
import { formToJSON } from "axios";

function LoginPage_bk() {
  const navigate = useNavigate();
  const [join, setJoin] = useState(false);
  const [modal, setModal] = useState(false);
  const email = useRef();
  const passwd = useRef();

  function signIn(email, passwd) {
    console.log(email.value + "\n" + passwd.value);
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

    fetch("http://localhost/api/auth/signIn", {
      method: "post",
      headers: {
        "content-type": "application/json;charset=UTF-8",
      },
      body: JSON.stringify(credentials),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("false: " + response.status);
        }
        localStorage.setItem("accessToken", response);
        Swal.fire({
          icon: "success",
          text: "로그인 되었습니다.",
          showConfirmButton: false,
          timer: 1500,
        });
        navigate("/main");
      })
      .catch((error) => {
        console.log("=== 로그인 실패 ===\n" + error);
        Swal.fire({
          icon: "warning",
          title: "잠깐!",
          html: "아이디/비밀번호를 확인해주세요.",
          showConfirmButton: false,
          timer: 1500,
        });
      });
  }

  function findEmail() {
    Swal.fire({ text: "find email" });
  }

  function setTemporalPasswd() {
    Swal.fire({ text: "reset pwd" });
  }

  return (
    <>
      <div className="main" align="center">
        <div className="organize-form form-area-signin">
          <h2 align="center">Studycode</h2>
          <br />
          <div className="form-field">
            <input
              className="input"
              type="text"
              ref={email}
              placeholder="이메일을 입력해주세요"
              align="center"
            />
          </div>
          <br />
          <div className="form-field">
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
              type="submit"
              onClick={() => {
                signIn(email.current, passwd.current);
              }}
              className="btn-main"
            >
              Sign In
            </button>
            <button type="submit" onClick={() => {}} className="btn-main">
              Sign Up
            </button>
          </div>
          <br />
          <p>
            <a
              onClick={() => {
                findEmail()
              }}
            >
              아이디 &nbsp;
            </a>
            |
            <a
              onClick={() => {
                setTemporalPasswd()
              }}
            >
              &nbsp; 비밀번호 찾기
            </a>
          </p>
        </div>
      </div>
    </>
  );
}

export default LoginPage;
