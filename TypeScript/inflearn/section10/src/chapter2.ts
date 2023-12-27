interface User {
  id: number;
  name: string;
  age: number;
  readonly gender: "XX" | "XY";
}

// PickT, K extends T> → 객체 타입으로부터 특정 프로퍼티만 골라내는 타입
type Pick<T, K extends keyof T> = {
  [key in K]: T[key];
};

const pickUser: Pick<User, "id" | "name"> = {
  id: 1,
  name: "codes",
};

// Omit<T, K extends T> → 객체 타입으로부터 특정 프로퍼티를 배제하는 타입
type Omit<T, K extends keyof T> = Pick<T, Exclude<keyof T, K>>;

const omitUser: Omit<User, "age" | "gender"> = {
  id: 1,
  name: "codes",
};

// Record<K, V> → 동일한 패턴을 가지는 프로퍼티를 정의
type Record<K extends keyof any, V> = {
  [key in K]: V
}

type Thumbnail = Record<"large" | "medium" | "small" | "watch", { url: string; size: number }>;
