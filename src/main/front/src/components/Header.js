import React, { useEffect, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import { MarkerTip } from "react-bootstrap-icons";

function Header() {
  const navigate = useNavigate();
  const location = useLocation();
  const timerRef = useRef(null);
  const token = localStorage.getItem("token");

  const timeoutAlert = () => {
    timerRef.current = setTimeout(
      () => {
        Swal.fire({
          icon: "warning",
          title: "잠깐!",
          html: "로그인 유효시간이 만료되어 자동 로그아웃되었습니다.</br>재로그인 후 이용해 주세요.",
          showConfirmButton: false,
          timer: 3000,
        }).then(() => {
          localStorage.removeItem("token");
          window.location.href = "/";
        });
      },
      1000 * 60 * 60,
    );
  };

  const signOut = () => {
    Swal.fire({
      icon: "question",
      title: "잠깐!",
      html: "로그아웃 하시겠습니까?",
      showCancelButton: true,
      confirmButtonText: "YES",
      cancelButtonText: "NO",
    }).then((result) => {
      if (result.isConfirmed) {
        localStorage.removeItem("token");
        navigate("/");
      }
    });
  };

  useEffect(() => {
    return () => clearTimeout(timerRef.current);
  }, []);

  if (location.pathname === "/") return null; // 로그인페이지에서 헤더 제거

  if (token == null) {
    Swal.fire({
      icon: "error",
      title: "잠깐!",
      html: "잘못된 접근입니다.<br/>로그인 후 이용해주세요.",
      showConfirmButton: false,
      timer: 2000,
    }).then(() => {
      navigate("/");
    });
  } else {
    // 로그인 성공 시
    timeoutAlert();
    return (
      <nav className="navbar navbar-expand-lg bg-body-tertiary">
        <div className="container-fluid">
          <a className="navbar-brand" href="/main">
            <MarkerTip />
            &nbsp;Studycode
          </a>

          {/* 호스트로그인 후 상단 */}
          <div id="navbarNavAltMarkup">
            <div className="navbar-nav">
              <a className="nav-link" href="/main">
                둘러보기
              </a>
              <a className="nav-link" href="#">
                내계정
              </a>
              <a className="nav-link" href="#">
                자료실
              </a>
              <a className="nav-link" style={{cursor:"pointer"}} onClick={() => signOut()}>
                로그아웃
              </a>
            </div>
          </div>
        </div>
      </nav>
    );
  }
}

export default Header;
