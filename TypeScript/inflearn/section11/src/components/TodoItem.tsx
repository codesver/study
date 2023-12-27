import React, { useContext } from "react";
import { Todo } from "../types";
import { TodoDispatchContext } from "../App";

interface TodoItemProps extends Todo {}

const TodoItem: React.FC<TodoItemProps> = ({ id, content }) => {
  const setTodos = useContext(TodoDispatchContext);

  return (
    <div className="TodoItem">
      <li key={id}>{`${id} : ${content}`}</li>
      <button onClick={() => setTodos?.delete(id)}>Delete</button>
    </div>
  );
};

export default React.memo(TodoItem);
