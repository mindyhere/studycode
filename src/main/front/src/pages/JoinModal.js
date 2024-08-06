import React, { useRef, useState } from "react";
import "../css/Login.css";
import Modal from "react-bootstrap/Modal";
import { PersonVcard } from "react-bootstrap-icons";

import AuthService from "../services/AuthService";

function JoinModal(props) {
  const [email, setEmail] = useState("");
  const emailRef = useRef();
  const [confirmCode, setConfirmCode] = useState("");
  const confirmCodeRef = useRef();
  const passwd = useRef();
  const pwCheck = useRef();
  const name = useRef();
  const [phone, setPhoneNum] = useState("");
  const phoneNum = useRef();
  const profile = useRef();
  const [chkdId, setChkdId] = useState("");
  const emailChk = useRef();
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
                  <tr>
                    <td rowSpan={2}>*이메일</td>
                    <td>
                      :
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
                        value={confirmCode}
                        className={
                          "btn-check " + (active ? "active" : "disabled")
                        }
                        disabled={active ? false : true}
                      >
                        &nbsp;코드발송&nbsp;
                      </button>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      :
                      <input
                        className="input"
                        type="text"
                        ref={confirmCodeRef}
                        placeholder="이메일 인증코드를 확인해주세요."
                        align="center"
                        style={{ width: "90%" }}
                      />
                    </td>
                  </tr>
                  <br />
                  <tr>
                    <td rowSpan={2}>*비밀번호</td>
                    <td colSpan={2}>
                      :
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
                      :
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
                  <br />
                  <tr>
                    <td>&nbsp;&nbsp;이름</td>
                    <td colSpan={2}>
                      :
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
                  <br />
                  <tr>
                    <td>*전화번호</td>
                    <td colSpan={2}>
                      :
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
                  <br />
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
                </table>
              </div>
            </div>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <button
            className={"btn-main"}
            onClick={() => {
              window.location.reload();
            }}
          >
            Sign Up
          </button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default JoinModal;
