// Void
function func1(): string {
  return "hello";
}

function funcVoid(): void {
  // nothing to return
}

// Never
function func3(): never {
  while (true) {
    // Never returns
  }
}

function func4(): never {
  throw new Error();
}
