// Enum Type (Only TS)
enum Role {
  ADMIN, // 0
  USER = 1,
  GUEST, // 2
}

enum Language {
  korean = "ko",
  english = "en",
}

type User = {
  name: string;
  role: Role;
  language?: Language;
}

const user1: User = {
  name: "codes",
  role: Role.ADMIN,
  language: Language.korean,
};

const user2: User = {
  name: "codesver",
  role: Role.USER,
};

const user3: User = {
  name: "jaewon",
  role: Role.GUEST,
};
