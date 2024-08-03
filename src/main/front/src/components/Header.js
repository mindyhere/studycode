import React, { useEffect, useState, useRef } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate, useLocation } from "react-router-dom";
import Swal from "sweetalert2";

function Header() {
  const navigate = useNavigate();
  const accessToken = localStorage.getItem("token");
  const location = useLocation();

  useEffect(() => {
    return () => clearTimeout(timerRef.current);
  }, []);

  if (location.pathname === "/") return null; // 로그인페이지에서 헤더 제거

  if (accessToken != null) {
    // 로그인 성공 시
    const userIdx = userInfo.h_idx;
    timeoutAlert("host");

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
              <li className="nav-item rounded">
                <a
                  className="nav-link active"
                  onClick={() => navigate(`/api/host/account/${userIdx}`)}
                >
                  계정
                </a>
              </li>
              <li className="nav-item rounded">
                <a
                  className="nav-link active"
                  onClick={() => navigate(`/component/Message`)}
                >
                  메시지
                </a>
              </li>
              <li className="nav-item rounded">
                <a
                  className="nav-link active"
                  onClick={() => navigate(`/host/hotel/MyhotelList`)}
                >
                  호텔
                </a>
              </li>
              <li className="nav-item rounded">
                <a
                  className="nav-link active"
                  onClick={() => navigate(`/api/order/manage/list/${userIdx}`)}
                >
                  예약관리
                </a>
              </li>
              <li
                className="nav-item rounded"
                style={{ display: "inline-block" }}
              >
                <a
                  className="nav-link active"
                  onClick={() => navigate("/component/Notice")}
                >
                  공지사항
                </a>
              </li>
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
                        sessionStorage.clear();
                        removeCookies("host");
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
