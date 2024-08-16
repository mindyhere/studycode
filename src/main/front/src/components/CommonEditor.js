import React, { useRef, useState,useEffect } from "react";
import { Editor } from "@toast-ui/react-editor";
import Viewer from "@toast-ui/editor/dist/toastui-editor-viewer";
import "@toast-ui/editor/dist/i18n/ko-kr";
import "@toast-ui/editor/dist/toastui-editor.css";
import colorSyntax from "@toast-ui/editor-plugin-color-syntax";
import "tui-color-picker/dist/tui-color-picker.css";
import "@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css";
import "@toast-ui/editor/dist/toastui-editor-viewer.css";

function CommonEditor(props) {
  // useEffect(() => {
  //   let item = localStorage.getItem(CONTENT_KEY);
  //
  //   const viewer = new Viewer({
  //     el: document.querySelector(".toast-editor-viewer"),
  //     viewer: true,
  //     height: "400px",
  //     usageStatistics: false, // 통계 수집 거부
  //     plugins: [tableMergedCell],
  //   });
  //
  //   viewer.setMarkdown = item;
  //
  //   if (item) {
  //     props.editorRef.current.getInstance().setMarkdown(item);
  //     setInitContents(item);
  //   }
  // }, []);

  const onChange = () => {
    const data = props.editorRef.current.getInstance().getHTML();
    console.log(data);
  };
  return (
    <>
      <div className="toast-editor-viewer"></div>
      <Editor
        initialValue="*내용을 입력해주세요."
        previewStyle="vertical"
        height="600px"
        initialEditType="wysiwyg"
        useCommandShortcut={false}
        hideModeSwitch={true}
        plugins={[colorSyntax]}
        viewer={true}
        language="ko-KR"
        ref={props.editorRef}
      />
    </>
  );
}

export default CommonEditor;
