class List<T> {
  constructor(private list: T[]) {}

  push(data: T) {
    this.list.push(data);
  }

  pop(): T {
    return this.list.pop();
  }

  print() {
    console.log(this.list);
  }
}
