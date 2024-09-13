import React, { useEffect, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import { MarkerTip } from "react-bootstrap-icons";
import "../css/Header.css";

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

  if (location.pathname === "/login") return null; // 로그인페이지에서 헤더 제거

  if (token == null) {
    return (
      <>
        <div className="container mt-3 d-flex flex-wrap justify-content-center">
          <h1>
            <a
              href="/"
              className="d-flex align-items-center mb-3 mb-lg-0 me-lg-auto"
            >
              <MarkerTip />
              &nbsp;Studycode
            </a>
          </h1>
        </div>
        <nav className="py-2 bg-body-tertiary border-bottom">
          <div className="container d-flex flex-wrap justify-content-center">
            <ul className="nav">
              <li className="nav-item mx-2">
                <a href="/" className="nav-link link-body-emphasis px-2">
                  둘러보기
                </a>
              </li>
              <li className="nav-item mx-2">
                <a href="#" className="nav-link link-body-emphasis px-2">
                  자료나눔
                </a>
              </li>
              <li className="nav-item mx-2">
                <a href="/login" className="nav-link link-body-emphasis px-2">
                  로그인/회원가입
                </a>
              </li>
            </ul>
          </div>
        </nav>
      </>
    );
  } else {
    // 로그인 성공 시
    timeoutAlert();
    return (
      <>
        <div className="container mt-3 d-flex flex-wrap justify-content-center">
          <h1>
            <a
              href="/"
              className="d-flex align-items-center mb-3 mb-lg-0 me-lg-auto"
            >
              <MarkerTip />
              &nbsp;Studycode
            </a>
          </h1>
        </div>
        <nav className="py-2 bg-body-tertiary border-bottom">
          <div className="container d-flex flex-wrap justify-content-center">
            <ul className="nav">
              <li className="nav-item mx-2">
                <a href="/" className="nav-link link-body-emphasis px-2">
                  둘러보기
                </a>
              </li>
              <li className="nav-item mx-2">
                <a href="#" className="nav-link link-body-emphasis px-2">
                  자료나눔
                </a>
              </li>
              <li className="nav-item mx-2">
                <a href="#" className="nav-link link-body-emphasis px-2">
                  내계정
                </a>
              </li>
              <li className="nav-item mx-2">
                <a
                  className="nav-link link-body-emphasis px-2"
                  onClick={() => signOut()}
                >
                  로그아웃
                </a>
              </li>
            </ul>
          </div>
        </nav>
      </>
    );
  }
}

export default Header;
