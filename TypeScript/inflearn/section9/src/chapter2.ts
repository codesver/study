// Decentralized Conditional Type
type StringNumberSwitch<T> = T extends number ? string : number;

let a: StringNumberSwitch<number>;
let b: StringNumberSwitch<string>;

// Union type을 전달할 경우 T에 number | string 이 전달되는 것이 아니라 각 type의 결과를 union으로 묶이게 된다.
let c: StringNumberSwitch<number | string>; // number → string | string → number = string | number

let d: StringNumberSwitch<boolean | number | string>;
// boolean → number | number → string | string → number = number | string

// Example
type Exclude<T, U> = T extends U ? never : T;
type A = Exclude<number | string | boolean, string>;
// number → number | string → never | boolean → boolean = number | boolean
// never 타입은 공집합이기 때문에 union 되었을 때 사라진다.

type Extract<T, U> = T extends U ? T : never;
type B = Extract<number | string | boolean, string>;
// number → never | string → string | boolean → never = string

type C<T> = [T] extends [number] ? string : number;
