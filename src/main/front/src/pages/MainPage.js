import React, {useRef, useState} from "react";
import {useNavigate} from "react-router";
import Swal from "sweetalert2";
import "../css/Login.css";

function MainPage() {
    const navigate = useNavigate()
    return (
        <>
            <h1> 안녕하세요.</h1>
            <button onClick={() => navigate("/addPost")}>test</button>
        </>
    );
}

export default MainPage;
