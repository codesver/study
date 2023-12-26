// Any : When don't know the type
let anyVariable = 10;
anyVariable = "10";
let num = anyVariable;
// Unknown
let unknownVariable = 10;
unknownVariable = "10";
// let num: number = unknownVariable;
if (typeof unknownVariable === "number") {
    let num = unknownVariable;
}
export {};
