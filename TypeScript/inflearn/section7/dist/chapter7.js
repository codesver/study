// Void
function func1() {
    return "hello";
}
function funcVoid() {
    // nothing to return
}
// Never
function func3() {
    while (true) {
        // Never returns
    }
}
function func4() {
    throw new Error();
}
export {};
