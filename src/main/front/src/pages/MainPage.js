import React from "react";
import { useNavigate } from "react-router";
import Swal from "sweetalert2";
import "../css/Login.css";
import "../css/Board.css";
import { Eye, FileEarmarkPlus, SuitHeart } from "react-bootstrap-icons";

function MainPage() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const addPost = () => {
    console.log("== click ==\n" + token);
    if (token == null) {
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: "로그인 후 이용해주세요.",
        showCancelButton: true,
      }).then((result) => {
        if (result.isConfirmed) {
          navigate("/login");
        } else {
          return;
        }
      });
      return;
    } else {
      navigate("/addPost");
    }
  };
  return (
    <>
      <div className="container .container-fluid ">
        <div className="row">
          <div className="card justify-content-around col-4 col-md-3 mb-3">
            <div style={{ textAlign: "center" }}>
              <FileEarmarkPlus
                size="45"
                style={{ cursor: "pointer" }}
                onClick={()=>addPost()}
              />
            </div>
          </div>
          <div className="col-4 col-md-3 mb-3">
            <div className="card">
              <img
                src="http://localhost/images/elice.png"
                className="card-img-top"
                alt="..."
              />
              <div className="card-body p-0 text-end">
                <SuitHeart size={24} style={{ cursor: "pointer" }} />{" "}
                <Eye className="m-2" size={24} style={{ cursor: "pointer" }} />
              </div>
            </div>
          </div>
          <div className="post col-4 col-md-3 m-2">
            <h1> 안녕하세요.</h1>
            <button onClick={() => addPost()}>test</button>
          </div>
        </div>
      </div>
    </>
  );
}

export default MainPage;
