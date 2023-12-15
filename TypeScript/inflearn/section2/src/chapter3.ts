// 구조적 타입 시스템 property based system
let user: {
  id?: number; // optional property
  name: string;
} = {
  id: 94101969,
  name: "codesver",
};

user = {
  name: "codes",
};

interface Dog {
  name: string;
  color: string;
}

let dog: Dog = {
  name: "돌돌이",
  color: "brown",
};

let config: {
  readonly apiKey: string;
} = {
  apiKey: "key",
};

const object: {
  optionalProperty?: string;
  property: string;
  readonly readonlyProperty: string;
} = {
  property: "property",
  readonlyProperty: "readonly",
};