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

    localStorage.setItem("accessToken", response.data);
    Swal.fire({
      icon: "success",
      text: "로그인 되었습니다.",
      showConfirmButton: false,
      timer: 1500,
    });
    navigate("/main");
  } catch (error) {
    console.error("=== 로그인 실패 ===\n", error);
    Swal.fire({
      icon: "warning",
      title: "잠깐!",
      html: "아이디/비밀번호를 확인해주세요.",
      showConfirmButton: false,
      timer: 1500,
    });
  }
};

const getUserEmail = async (id) => {
  Swal.fire({ text: "find email" });
  // const response = await axios.get(`${API_URL}/find/${id}`);
};

const setTemporalPasswd = async (id) => {
  Swal.fire({ text: "reset pwd" });
  // axios.post(API_URL, study);
};

const createUser = (userDto) => axios.put(`${API_URL}/signUp`, userDto);

export default {
  setAuthUser,
  getUserEmail,
  setTemporalPasswd,
  createUser,
};