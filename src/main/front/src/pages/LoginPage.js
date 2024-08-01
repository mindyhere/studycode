import React, { useRef } from "react";
import { useNavigate } from "react-router";
import Swal from "sweetalert2";
import "../css/Login.css";

function LoginPage() {
  const navigate = useNavigate();
  const [join, setJoin] = useState(false);
  const [modal, setModal] = useState(false);
  const eamil = useRef();
  const passwd = useRef();

  return (
    <>
      <div className="main" align="center">
        <div className="organize-form form-area-signin">
          <h2 align="center">Hello</h2>
          <br />
          <div className="form-field">
            <input
              className="input"
              type="text"
              ref={eamil}
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
                if (email.current.value === "") {
                  Swal.fire({
                    icon: "warning",
                    title: "잠깐!",
                    html: "이메일을 입력해주세요.",
                    confirmButtonText: "OK",
                    confirmButtonColor: "#41774d86",
                  });
                  email.current.focus();
                  return;
                }
                if (passwd.current.value === "") {
                  Swal.fire({
                    icon: "warning",
                    title: "잠깐!",
                    html: "비밀번호를 입력해주세요.",
                    confirmButtonText: "OK",
                    confirmButtonColor: "#41774d86",
                  });
                  passwd.current.focus();
                  return;
                }
                const form = new FormData();
                form.append("email", email.current.value);
                form.append("passwd", passwd.current.value);
                fetch("http://localhost/api/auth/signIn/", {
                  method: "post",
                  body: form,
                })
                  .then((response) => response.json())
                  .then((data) => {
                    console.log("==> data: " + data);
                    if (data.message == "success") {
                      console.log("==> 로그인 성공: " + data.data);
                      navigate("/");
                    } else {
                      console.log("==> 로그인 실패: " + data.data);
                      Swal.fire({
                        icon: "warning",
                        title: "잠깐!",
                        html: "아이디/비밀번호를 확인해주세요.",
                        confirmButtonText: "OK",
                      });
                    }
                  });
              }}
              className="btn-sign"
            >
              Sign In
            </button>
          </div>
        </div>
      </div>
    </>
  );
}

export default LoginPage;
