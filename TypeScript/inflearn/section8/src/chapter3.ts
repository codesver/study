// Mapped Type (only type not interface)
interface User {
  id: number;
  name: string;
  age: number;
}

type PartialUser = {
  [key in keyof User]?: User[key];
};

type BooleanUser = {
  [key in keyof User]?: boolean;
};

type ReadOnlyUser = {
  readonly [key in keyof User]: User[key];
};

function fetchUser(): ReadOnlyUser {
  return {
    id: 1,
    name: "codes",
    age: 25,
  };
}

function updateUser(user: PartialUser) {}

updateUser({
  id: 1,
  name: "codes",
  age: 26,
});
