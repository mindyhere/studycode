import React, { useRef, useState } from "react";
import { useNavigate } from "react-router";
import Modal from "react-bootstrap/Modal";
import { Button } from "react-bootstrap";

function JoinModal(props) {
  const [email, setEmail] = useState("");
  const userEmail = useRef();
  const pwd = useRef();
  const pwdChk = useRef();
  const name = useRef();
  const [phoneNum, setPhoneNum] = useState("");
  const phone = useRef();
  const profile = useRef();
  const [chkdId, setChkdId] = useState("");
  const emailChk = useRef();
  const [check, setCheck] = useState(false);

  const emailRegEx =
    /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;

  const idCheck = (e) => {
    setChkdId(e);
  };

  const handleChange = (val, opt) => {
    const phoneRegEx = /^[0-9\b -]{0,13}$/;
    const businessRegEx = /^[0-9\b -]{0,12}$/;
    switch (opt) {
      case "phone":
        if (phoneRegEx.test(val)) {
          setPhoneNum(
            val.replace(/-/g, "").replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3"),
          );
        }
        break;
    }
  };

  const emailCheck = (e) => {
    // 형식에 맞을 경우, true 리턴
    emailRegEx.test(e.target.value) ? setCheck(true) : setCheck(false);
  };

  return (
    <>
      <Modal
        show={props.show}
        onHide={props.onHide}
        backdrop={"static"}
        size="lg"
        scrollable={true}
        keyboard={false}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>Sign Up</Modal.Title>
        </Modal.Header>
        <Modal.Body>여기에 폼작성</Modal.Body>
        <Modal.Footer>
          <Button
            className={"btn-main"}
            onClick={() => {
              window.location.reload();
            }}
          >
            Sign Up
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default JoinModal;
