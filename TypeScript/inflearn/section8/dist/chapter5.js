// Enum Type (Only TS)
var Role;
(function (Role) {
    Role[Role["ADMIN"] = 0] = "ADMIN";
    Role[Role["USER"] = 1] = "USER";
    Role[Role["GUEST"] = 2] = "GUEST";
})(Role || (Role = {}));
var Language;
(function (Language) {
    Language["korean"] = "ko";
    Language["english"] = "en";
})(Language || (Language = {}));
const user1 = {
    name: "codes",
    role: Role.ADMIN,
    language: Language.korean,
};
const user2 = {
    name: "codesver",
    role: Role.USER,
};
const user3 = {
    name: "jaewon",
    role: Role.GUEST,
};
export {};
