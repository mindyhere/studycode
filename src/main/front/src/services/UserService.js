import axios from "axios";
import Swal from "sweetalert2";
import { setAuthorizationToken } from "../util/Authorization";

const API_URL = "http://localhost/api/auth";

const setAuthUser = async (credentials, navigate) => {
  console.log(credentials);

  try {
    const response = await axios.post(`${API_URL}/signIn`, credentials, {
      headers: {
        "Content-Type": "application/json;charset=UTF-8",
      },
    });

    localStorage.setItem("token", response.data);
    setAuthorizationToken(response.data);
    Swal.fire({
      icon: "success",
      text: "로그인 되었습니다.",
      showConfirmButton: false,
      timer: 2500,
    });
    navigate("/");
  } catch (error) {
    console.error("=== 로그인 실패 ===\n", error);
    Swal.fire({
      icon: "warning",
      title: "잠깐!",
      html: "아이디/비밀번호를 확인해주세요.",
      showConfirmButton: false,
      timer: 2500,
    });
  }
};

const checkRegisteredId = async (id) => {
  const response = await axios.get(`${API_URL}/check/id/${id}`);
  return response;
};

const getUserid = async (email, phone) => {
  await axios
    .get(`${API_URL}/find/id/${email}/${phone}`)
    .then((response) => {
      if (response.data == "")
        throw new Error("일치하는 회원 정보가 없습니다.");

      Swal.fire({
        icon: "success",
        html: `<strong>${response.data.name}</strong> 님의 아이디는 <strong>${response.data.userid}</strong> 입니다.`,
        confirmButtonText: "OK",
      }).then((result) => {
        if (result.isConfirmed) {
          return;
        }
      });
    })
    .catch((error) => {
      console.error("=== 요청 실패 ===\n", error);
      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: `사용자를 찾을 수 없습니다.<br/>입력하신 내용을 확인해주시기 바랍니다.`,
        confirmButtonText: "OK",
      });
    });
};

const setTemporalPasswd = async (userid, form) => {
  try {
    const response = await axios
      .post(`${API_URL}/find/pwd/${userid}`, form, {
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
        },
      })
      .then((response) => {
        if (response.data !== "not exist") {
          Swal.fire({
            icon: "info",
            html: `등록된 이메일로 임시 비밀번호를 발송했습니다.<br/>로그인 후 비밀번호를 변경해주시기 바랍니다.`,
            showConfirmButton: false,
            timer: 2500,
          }).then((result) => {
            if (result.isConfirmed) {
              return;
            }
          });
        } else {
          console.log("=== res ===\n", response.data);

          Swal.fire({
            icon: "warning",
            title: "잠깐!",
            html: `사용자를 찾을 수 없습니다.<br/>입력하신 내용을 확인해주시기 바랍니다.`,
            confirmButtonText: "OK",
          });
        }
      });
  } catch (error) {
    console.error("=== 발송 실패 ===\n", error);

    Swal.fire({
      icon: "error",
      title: "잠깐!",
      text: error,
      showConfirmButton: false,
      timer: 2500,
    });
  }
};

const setTemporalCode = async (email) => {
  await axios
    .get(`${API_URL}/check/email/${email}`)
    .then((response) => {
      if (response.data == "exits")
        throw new Error(
          "기존에 등록된 이메일입니다.",
        );
      console.log("=== 결과 ===\n" + response.data);
      localStorage.setItem("tempCode", response.data);

      Swal.fire({
        icon: "info",
        html: "인증코드를 발송했습니다.<br/>이메일 확인 후 코드를 입력해주시기 바랍니다.",
        showConfirmButton: false,
        timer: 2500,
      });
    })
    .catch((error) => {
      console.error("=== err ===\n", error);

      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: error,
        showConfirmButton: false,
        timer: 2500,
      });
    });
};

const createUser = async (form) => {
  console.log("== 호출확인 ==\n" + form.stringify);
  try {
    const response = await axios
      .post(`${API_URL}/signUp`, form, {
        headers: {
          "content-type": "multipart/form-data",
        },
      })
      .then(() => {
        Swal.fire({
          icon: "success",
          text: "회원가입이 완료되었습니다.",
          showConfirmButton: false,
          timer: 2500,
        });
        localStorage.removeItem("tempCode");
        window.location.reload();
      });
  } catch (error) {
    console.error("=== 로그인 실패 ===\n", error);
    Swal.fire({
      icon: "error",
      title: "잠깐!",
      html: "처리 중 문제가 발생했습니다.<br>잠시 후 다시 시도해주시기 바랍니다.",
      showConfirmButton: false,
      timer: 2500,
    });
  }
};

export default {
  setAuthUser,
  checkRegisteredId,
  getUserid,
  setTemporalPasswd,
  setTemporalCode,
  createUser,
};
