// [ Array ]
let numArr: number[] = [1, 2, 3];
let strArr: string[] = ["hello", "typescript"];
let booleanArr: Array<boolean> = [true, false];

// Multi Array
let multiArr: (number | string)[] = [1, "hello"];

// Multi Dimension Array
let doubleArr: (number | string)[][] = [[1, 2, 3], [2, 3], ["string"]];

// [ Tuple ]

// Fixed Length & Type (Only for typescript)
let tupleA: [number, number] = [1, 2];
let tupleB: [number, string, boolean] = [1, "hello", true];

tupleA.push(1);
tupleA.pop();

const users: [string, number][] = [
  ["usera", 1],
  ["userb", 2],
  ["userc", 3],
  // [5, "userd"],
];
