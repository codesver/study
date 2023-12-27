import React, { useContext, useRef, useState } from "react";
import { TodoDispatchContext } from "../App";

const Editor: React.FC = () => {
  const [text, setText] = useState("");
  const idRef = useRef(0);
  const setTodos = useContext(TodoDispatchContext);

  const onChangeText = (newText: string) => {
    setText(newText);
  };

  return (
    <div className="Editor">
      <input type="text" value={text} onChange={(e) => onChangeText(e.target.value)} />
      <button
        onClick={() =>
          setTodos?.create({
            id: idRef.current++,
            content: text,
          })
        }
      >
        Add
      </button>
    </div>
  );
};

export default React.memo(Editor);
