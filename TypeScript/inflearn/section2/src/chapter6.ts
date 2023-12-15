// Any : When don't know the type
let anyVariable: any = 10;
anyVariable = "10";
let num: number = anyVariable;

// Unknown
let unknownVariable: unknown = 10;
unknownVariable = "10";
// let num: number = unknownVariable;

if (typeof unknownVariable === "number") {
  let num: number = unknownVariable;
}
