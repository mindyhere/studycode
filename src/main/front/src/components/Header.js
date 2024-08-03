import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useLocation, useNavigate } from "react-router-dom";
import Swal from "sweetalert2";

function Header() {
  const navigate = useNavigate();
  const accessToken = localStorage.getItem("accessToken");
  const location = useLocation();

  // useEffect(() => {
  //   return () => clearTimeout(timerRef.current);
  // }, []);

  if (location.pathname === "/") return null; // 로그인페이지에서 헤더 제거

  if (accessToken == null) {
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
    //   // 로그인 성공 시

    return (
      <nav className="navbar navbar-expand-lg">
        <div className="container-fluid">
          <a className="navbar-brand" href="/">
            <img
              src="/img/sybnb.png"
              href="/"
              width="170px"
              height="65px"
              style={{ padding: "0.5rem" }}
            ></img>
          </a>

          {/* 호스트로그인 후 상단 */}
          <div align="right">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item rounded">HOME</li>
              <li className="nav-item rounded">계정</li>
              <li className="nav-item rounded">스터디룸</li>
              <li className="nav-item rounded">자료실</li>
              <li className="nav-item rounded">
                <a
                  className="nav-link active"
                  onClick={() => {
                    Swal.fire({
                      icon: "question",
                      title: "잠깐!",
                      html: "로그아웃 하시겠습니까?",
                      showCancelButton: true,
                      confirmButtonText: "YES",
                      cancelButtonText: "NO",
                    }).then((result) => {
                      if (result.isConfirmed) {
                        localStorage.clear();
                        navigate("/");
                      }
                    });
                  }}
                >
                  로그아웃
                </a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    );
  }
}

export default Header;
