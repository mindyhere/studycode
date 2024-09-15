import React, { useRef, useState } from "react";
import { Images } from "react-bootstrap-icons";
import { useNavigate } from "react-router";

function AddPostPage() {
  const navigate = useNavigate();
  const title = useRef();
  const name = useRef();
  const topic = useRef();

  const [preview, setPreview] = useState([]);

  // 이미지 상대경로 저장
  const addImage = (e) => {
    const arrfiles = e.target.files;
    let arrFileURLs = [...preview];

    for (let i = 0; i < arrfiles.length; i++) {
      const currentFileURL = URL.createObjectURL(arrfiles[i]);
      arrFileURLs.push(currentFileURL);
    }

    if (arrFileURLs.length > 5) {
      arrFileURLs = arrFileURLs.slice(0, 5);
    }

    setPreview(arrFileURLs);
  };

  return (
    <>
      <div className="container form-field">
        <div className="container-fluid">
          <div className="row">
            <table className="tbl">
              <tbody>
                <tr>
                  <td className="col-6" rowSpan={4}>
                    <div>
                      <label
                        onChange={addImage}
                        htmlFor="input-file"
                        className="btn-main"
                        style={{
                          display: "inline-block",
                          width: "120px",
                          height: "50px",
                        }}
                      >
                        <Images size={25} /> Add File
                        <input
                          type="file"
                          id="input-file"
                          multiple="multiple"
                          accept="image/*"
                        />
                      </label>
                    </div>
                    <div style={{ display: "block", float: "left" }}>
                      {preview &&
                        preview.map((x, i) => {
                          return (
                            <img
                              key={i}
                              src={x}
                              width="150px"
                              height="150px"
                              style={{ margin: "1%", borderRdius: "7px" }}
                            />
                          );
                        })}
                    </div>
                  </td>
                  <th className="col-2" colSpan={1}>
                    Writer
                  </th>
                  <td className="col-4" colSpan={2}>
                    <input
                      readOnly
                      // defaultValue={userid}
                    />
                  </td>
                </tr>
                <tr>
                  <th colSpan={1}>Date</th>
                  <td colSpan={2}>
                    <input
                      readOnly
                      // defaultValue={userid}
                    />
                  </td>
                </tr>
                <tr>
                  <th colSpan={1}>Content</th>
                  <td colSpan={2}>
                    <textarea></textarea>
                  </td>
                </tr>
                <tr>
                  <th colSpan={1}>Tags</th>
                  <td colSpan={2}>
                    <textarea></textarea>
                    <textarea></textarea>
                    <textarea></textarea>
                    <textarea></textarea>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <div className="row justify-content-center">
            <button className="btn-main mx-3" style={{ width: "200px" }}>
              저장
            </button>
            <button
              className="btn-main"
              style={{ backgroundColor: "#cccccc", width: "200px" }}
              onClick={() => navigate("/")}
            >
              취소
            </button>
          </div>
        </div>
      </div>
    </>
  );
}

export default AddPostPage;
