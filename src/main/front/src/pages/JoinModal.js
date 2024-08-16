import React, { useRef, useState } from "react";
import Swal from "sweetalert2";
import Modal from "react-bootstrap/Modal";
import { PersonVcard } from "react-bootstrap-icons";

import UserService from "../services/UserService";
import "../css/Login.css";

function JoinModal(props) {
  const [email, setEmail] = useState("");
  const emailRef = useRef();
  const confirmCode = useRef();
  const passwd = useRef();
  const pwCheck = useRef();
  const name = useRef();
  const [phone, setPhoneNum] = useState("");
  const phoneNum = useRef();
  const profile = useRef();
  const [active, setActive] = useState(false);

  const handleRegex = (val, type) => {
    const phoneRegex = /^[0-9\b -]{0,13}$/;
    const emailRegex =
      /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
    switch (type) {
      case "email":
        emailRegex.test(val) ? setActive(true) : setActive(false);
        break;
      case "phone":
        if (phoneRegex.test(val)) {
          setPhoneNum(
            val.replace(/-/g, "").replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3"),
          );
        }
        break;
    }
  };

  const checkRequired = () => {
    // 아이디(이메일) 유효성 검증
    if (!active) {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "이메일을 확인해주세요.",
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    } else {
      let tempCode = localStorage.getItem("tempCode");
      if (confirmCode.current.value != tempCode) {
        Swal.fire({
          icon: "warning",
          title: "잠깐!",
          html: "이메일 인증코드를 확인해주세요.",
          showConfirmButton: false,
          timer: 2000,
        });
        return;
      }
    }

    // 비밀번호 입력 확인
    if (passwd.current.value == "") {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "비밀번호를 입력해주세요.",
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    } else {
      if (passwd.current.value !== pwCheck.current.value) {
        Swal.fire({
          icon: "warning",
          title: "잠깐!",
          html: "비밀번호가 일치하지 않습니다.",
          showConfirmButton: false,
          timer: 2000,
        });
        return;
      }
    }

    if (name.current.value == "") {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "이름을 입력해주세요.",
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    if (phoneNum.current.value == "" || phoneNum.current.value.length < 13) {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "전화번호를 확인해주세요.",
        confirmButtonText: "OK",
      });
      return;
    }
    signUp();
  };

  function signUp() {
    const form = new FormData();
    form.append("email", emailRef.current.value);
    form.append("passwd", passwd.current.value);
    form.append("name", name.current.value);
    form.append("phone", phoneNum.current.value);
    if (profile.current.files.length > 0) {
      form.append("photo", profile.current.files[0]);
    }
    console.log("== 호출확인111 ==\n" + form);
    UserService.createUser(form);
  }

  return (
    <>
      <Modal
        show={props.show}
        onHide={props.onHide}
        backdrop={"static"}
        scrollable={true}
        keyboard={false}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>
            <PersonVcard size={40} />
            &nbsp;&nbsp;Sign Up
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className="form-field">
            <div className="container-fluid">
              <div className="row">
                <table className="col-md">
                  <tbody>
                    <tr>
                      <td rowSpan={2}>*이메일</td>
                      <td>
                        <input
                          className="input"
                          type="email"
                          value={email}
                          id={emailRef}
                          ref={emailRef}
                          onChange={(e) => {
                            setEmail(e.target.value);
                            handleRegex(e.target.value, "email");
                          }}
                          placeholder="이메일을 입력해주세요"
                          align="center"
                          style={{ width: "90%" }}
                        />
                      </td>
                      <td rowSpan={2}>
                        <button
                          type="button"
                          className={"btn-main" + (active ? "" : " disabled")}
                          style={{
                            width: "80px",
                            lineHeight: "13px",
                            fontSize: "12px",
                          }}
                          disabled={active ? false : true}
                          onClick={() => {
                            console.log("active 클릭");
                            UserService.setTemporalCode(emailRef.current.value);
                          }}
                        >
                          &nbsp;코드발송&nbsp;
                        </button>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <input
                          className="input"
                          type="text"
                          ref={confirmCode}
                          placeholder="이메일 인증코드를 확인해주세요."
                          align="center"
                          style={{ width: "90%" }}
                        />
                      </td>
                    </tr>

                    <tr>
                      <td rowSpan={2}>*비밀번호</td>
                      <td colSpan={2}>
                        <input
                          className="input"
                          type="password"
                          ref={passwd}
                          placeholder="비밀번호를 입력해주세요"
                          align="center"
                          style={{ width: "93%" }}
                        />
                      </td>
                    </tr>
                    <tr>
                      <td colSpan={2}>
                        <input
                          className="input"
                          type="password"
                          ref={pwCheck}
                          placeholder="다시 한번 입력해주세요"
                          align=""
                          style={{ width: "93%" }}
                        />
                      </td>
                    </tr>

                    <tr>
                      <td>*이름</td>
                      <td colSpan={2}>
                        <input
                          className="input"
                          type="text"
                          ref={name}
                          placeholder="이름을 입력해주세요"
                          align="center"
                          style={{ width: "93%" }}
                        />
                      </td>
                    </tr>

                    <tr>
                      <td>*전화번호</td>
                      <td colSpan={2}>
                        <input
                          className="input"
                          type="text"
                          maxLength={13}
                          onChange={(e) => {
                            handleRegex(e.target.value, "phone");
                          }}
                          value={phone}
                          ref={phoneNum}
                          placeholder="숫자만 입력해주세요"
                          align="center"
                          style={{ width: "93%" }}
                        />
                      </td>
                    </tr>

                    <tr>
                      <td>&nbsp;&nbsp;프로필</td>
                      <td colSpan={2}>
                        <input
                          className="form-control"
                          type="file"
                          ref={profile}
                          style={{ width: "95%" }}
                        />
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <div className="col m-2 pt-3" align="right">
            <button
              className={"btn-main"}
              onClick={() => {
                checkRequired();
              }}
            >
              Sign Up
            </button>
          </div>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default JoinModal;
