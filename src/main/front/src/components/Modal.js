import "../css/Modal.css";
import React, { useState } from "react";
import { XCircle } from "react-bootstrap-icons";

function Modal(props) {
  const [modal, setModal] = useState(false);

  function closeModal() {
    props.closeModal();
  }

  return (
    <div className="modal-bg" onClick={closeModal}>
      <div className="modal-body" onClick={(e) => e.stopPropagation()}>
        <button id="btn-close" onClick={closeModal}>
          <XCircle size={30} />
        </button>
        {props.children}
      </div>
    </div>
  );
}

export default Modal;
