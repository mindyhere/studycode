import axios from "axios";
import Swal from "sweetalert2";

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
    Swal.fire({
      icon: "success",
      text: "로그인 되었습니다.",
      showConfirmButton: false,
      timer: 2500,
    });
    navigate("/main");
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

const getUserEmail = async (name, phone) => {
  await axios
    .get(`${API_URL}/find/${name}/${phone}`)
    .then((response) => {
      console.log("=== 결과 ===\n" + response.data);
      if (response.data == "fail")
        throw new Error("일치하는 회원 정보가 없습니다.");

      Swal.fire({
        icon: "success",
        html: `<strong>${name}</strong> 님의 이메일은 <strong>${response.data}</strong> 입니다.`,
        confirmButtonText: "OK",
      }).then((result) => {
        if (result.isConfirmed) {
          return;
        }
      });
    })
    .catch((error) => {
      console.error("=== 발송 실패 ===\n", error);

      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: error,
      });
    });
};

const setTemporalPasswd = async (email, form) => {
  try {
    const response = await axios
      .post(`${API_URL}/find/${email}`, form, {
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
        },
      })
      .then(() => {
        Swal.fire({
          icon: "info",
          html: `등록된 이메일로 임시 비밀번호를 발송했습니다.<br/>로그인 후 비밀번호를 변경해주시기 바랍니다.`,
          confirmButtonText: "OK",
        }).then((result) => {
          if (result.isConfirmed) {
            return;
          }
        });
      });
  } catch (error) {
    console.error("=== 요청 실패 ===\n", error);
    Swal.fire({
      icon: "warning",
      title: "잠깐!",
      html: `사용자를 찾을 수 없습니다.<br/>입력하신 내용을 확인해주시기 바랍니다.`,
      confirmButtonText: "OK",
    });
  }
};

const setTemporalCode = async (email) => {
  await axios
    .get(`${API_URL}/check/${email}`)
    .then((response) => {
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
      console.error("=== 발송 실패 ===\n", error);

      Swal.fire({
        icon: "warning",
        title: "잠깐!",
        html: error.response.data,
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
          timer: 1500,
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
      timer: 2000,
    });
  }
};

export default {
  setAuthUser,
  getUserEmail,
  setTemporalPasswd,
  setTemporalCode,
  createUser,
};
