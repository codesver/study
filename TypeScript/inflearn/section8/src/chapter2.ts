// keyof operator → 어떤 객체 타입의 모든 key를 union 타입으로 추출

interface Person {
  name: string;
  age: number;
}

function getPropertyKey(person: Person, key: keyof typeof person) {
  return person[key];
}

const person: Person = {
  name: "codes",
  age: 25,
};

getPropertyKey(person, "name");
