import React from "react";
import { Eye, SuitHeart } from "react-bootstrap-icons";

function BoardItem({ props }) {
  let loading = false;
  if (loading) {
    return <div>loading...</div>;
  } else {
    <>
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
    </>;
  }
}

export default BoardItem;
