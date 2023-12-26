// Inference (추론)
type FuncA = () => string;

type FuncB = () => number;

type ReturnType<T> = T extends () => infer R ? R : never; // infer R 은 true가 되는 타입을 추론한다.

type A = ReturnType<FuncA>;

type B = ReturnType<FuncB>;

type C = ReturnType<number>;

// Example
type PromiseUnpack<T> = T extends Promise<infer R> ? R : never;

type PromiseA = PromiseUnpack<Promise<number>>;

type PromiseB = PromiseUnpack<Promise<string>>;
