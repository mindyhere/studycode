import React, { useRef, useState, useEffect } from "react";
import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/i18n/ko-kr";
import "@toast-ui/editor/dist/toastui-editor.css";
import colorSyntax from "@toast-ui/editor-plugin-color-syntax";
import "tui-color-picker/dist/tui-color-picker.css";
import "@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css";
import "@toast-ui/editor/dist/toastui-editor-viewer.css";

function CommonEditor(props) {
  const onChange = () => {
    const data = props.editorRef.current.getInstance().getHTML();
    console.log(data);
  };
  return (
    <Editor
      initialValue="*내용을 입력해주세요."
      previewStyle="vertical"
      height="600px"
      initialEditType="wysiwyg"
      useCommandShortcut={false}
      hideModeSwitch={true}
      plugins={[colorSyntax]}
      language="ko-KR"
      ref={props.editorRef}
    />
  );
}

export default CommonEditor;
