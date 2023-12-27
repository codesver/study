import Editor from "./components/Editor";
import TodoList from "./components/TodoList";

import { Todo } from "./types";

import "./App.css";
import { useMethods } from "react-use";
import React from "react";

const createMethods = (todos: Todo[]) => {
  return {
    create: (todo: Todo) => {
      return [...todos, todo];
    },
    delete: (id: number) => {
      return todos.filter((todo) => todo.id !== id);
    },
  };
};

type WrappedMethods<M> = {
  [P in keyof M]: (...payload: any) => void;
};

export const TodoStateContext = React.createContext<Todo[] | null>(null);
export const TodoDispatchContext = React.createContext<WrappedMethods<{
  create: unknown;
  delete: unknown;
}> | null>(null);

function App() {
  const [todos, setTodos] = useMethods(createMethods, [] as Todo[]);

  return (
    <div className="App">
      <h1>Todo</h1>
      <TodoStateContext.Provider value={todos}>
        <TodoDispatchContext.Provider value={setTodos}>
          <Editor />
          <TodoList />
        </TodoDispatchContext.Provider>
      </TodoStateContext.Provider>
    </div>
  );
}

export default App;
