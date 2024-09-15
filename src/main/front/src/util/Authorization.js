import axios from "axios";

// axios 요청 시 header에 token을 보내기 위함
export const setAuthorizationToken = (token) => {
  {
    if (token) {
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    } else {
      delete axios.defaults.headers.common["Authorization"];
    }
  }
};

