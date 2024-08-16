import React, { useRef, useState } from "react";
import CommonEditor from "../components/CommonEditor";

function AddPostPage() {
  const title = useRef();
  const topic = useRef();
  const editorRef = useRef();

  const onChange = () => {
    const data = editorRef.current.getInstance().getHTML();
    console.log(data);
  };

  return (
    <>
      <div className="container form-field">
        <div className="container-fluid">
          <div className="row">
            <table className="col-md m-3">
              <tbody>
                <tr>
                  <td className="p-2">*제목</td>
                  <td colSpan={2}>
                    <input
                      className="input"
                      type="text"
                      ref={title}
                      align="center"
                      style={{ width: "90%" }}
                    />
                  </td>
                </tr>
                <tr>
                  <td className="p-2">*주제</td>
                  <td colSpan={2}>
                    <input
                      className="input"
                      type="text"
                      ref={topic}
                      align="center"
                      style={{ width: "90%" }}
                    />
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <CommonEditor ref={editorRef} />
      </div>
    </>
  );
}

export default AddPostPage;
