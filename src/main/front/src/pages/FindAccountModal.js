import React, { useRef, useState } from "react";
import Swal from "sweetalert2";
import Modal from "react-bootstrap/Modal";
import { PersonVcard } from "react-bootstrap-icons";

import UserService from "../services/UserService";
import "../css/Login.css";

function FindAccountModal(props) {
  const email = useRef();
  const userid = useRef();
  const [phone, setPhoneNum] = useState("");
  const phoneNum = useRef();

  const handlePhoneNum = (val) => {
    const phoneRegex = /^[0-9\b -]{0,13}$/;

    if (phoneRegex.test(val)) {
      setPhoneNum(
        val.replace(/-/g, "").replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3"),
      );
    }
  };

  const handleReset = () => {
    if (props.opt == "passwd") userid.current.value = "";
    setPhoneNum("");
    email.current.value = "";
  };

  const checkRequired = (opt) => {
    const emailRegex =
      /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
    // 아이디(이메일) 유효성 검증
    if (!emailRegex.test(email.current.value)) {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "이메일을 확인해주세요.",
        showConfirmButton: false,
        timer: 2500,
      });
      return;
    }

    if (opt == "passwd" && userid.current.value == "") {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "아이디를 입력해주세요.",
        showConfirmButton: false,
        timer: 2500,
      });
      return;
    }

    if (phoneNum.current.value == "" || phoneNum.current.value.length < 13) {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "전화번호를 확인해주세요.",
        showConfirmButton: false,
        timer: 2500,
      });
      return;
    }
    findAccount(props.opt);
  };

  function findAccount(opt) {
    switch (opt) {
      case "userid":
        UserService.getUserid(email.current.value, phoneNum.current.value);
        break;

      case "passwd":
        const form = new FormData();
        form.append("email", email.current.value);
        form.append("userid", userid.current.value);
        form.append("phone", phoneNum.current.value);
        UserService.setTemporalPasswd(userid.current.value, form);
        break;
    }
  }

  if (props.opt == "userid") {
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
          <Modal.Header
            closeButton
            onClick={() => {
              console.log("닫기");
              handleReset();
            }}
          >
            <Modal.Title>
              <PersonVcard size={40} />
              &nbsp;&nbsp;아이디 찾기
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className="form-field">
              <div className="container-fluid">
                <div className="row">
                  <table className="col-md">
                    <tbody>
                    <tr>
                      <td>이메일</td>
                      <td>
                        <input
                            className="input"
                            type="text"
                            ref={email}
                            placeholder="이메일을 입력해주세요"
                            align="center"
                            style={{width: "90%"}}
                        />
                      </td>
                    </tr>
                    <tr>
                      <td>전화번호</td>
                      <td colSpan={2}>
                        <input
                            className="input"
                            type="text"
                            maxLength={13}
                            onChange={(e) => {
                              handlePhoneNum(e.target.value);
                            }}
                            value={phone}
                            ref={phoneNum}
                            placeholder="숫자만 입력해주세요"
                            align="center"
                            style={{width: "93%"}}
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
                    checkRequired(props.opt);
                  }}
              >
                Confirm
              </button>
            </div>
          </Modal.Body>
        </Modal>
      </>
    );
  } else {
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
          <Modal.Header
            closeButton
            onClick={() => {
              console.log("닫기");
              handleReset();
            }}
          >
            <Modal.Title>
              <PersonVcard size={40} />
              &nbsp;&nbsp;비밀번호 찾기
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className="form-field">
              <div className="container-fluid">
                <div className="row">
                  <table className="col-md">
                    <tbody>
                      <tr>
                        <td>아이디</td>
                        <td>
                          <input
                            className="input"
                            type="text"
                            ref={userid}
                            placeholder="아이디를 입력해주세요"
                            align="center"
                            style={{ width: "93%" }}
                          />
                        </td>
                      </tr>
                      <tr>
                        <td>이메일</td>
                        <td>
                          <input
                              className="input"
                              type="text"
                              ref={email}
                              placeholder="이메일을 입력해주세요"
                              align="center"
                              style={{ width: "90%" }}
                          />
                        </td>
                      </tr>
                      <tr>
                        <td>전화번호</td>
                        <td>
                          <input
                            className="input"
                            type="text"
                            maxLength={13}
                            onChange={(e) => {
                              handlePhoneNum(e.target.value);
                            }}
                            value={phone}
                            ref={phoneNum}
                            placeholder="숫자만 입력해주세요"
                            align="center"
                            style={{ width: "93%" }}
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
                  checkRequired(props.opt);
                }}
              >
                Confirm
              </button>
            </div>
          </Modal.Body>
        </Modal>
      </>
    );
  }
}

export default FindAccountModal;
