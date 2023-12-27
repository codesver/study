import React, { useContext } from "react";

import TodoItem from "./TodoItem";
import { TodoStateContext } from "../App";

const TodoList: React.FC = () => {
  const todos = useContext(TodoStateContext);

  return (
    <div className="TodoList">
      <ul>
        {todos?.map((todo) => (
          <TodoItem id={todo.id} content={todo.content} />
        ))}
      </ul>
    </div>
  );
};

export default React.memo(TodoList);
