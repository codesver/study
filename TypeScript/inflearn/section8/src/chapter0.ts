// Indexed Acces Type
type PostList = {
  title: string;
  content: string;
  author: {
    id: number;
    name: string;
    age: number;
  };
}[];

function printAuthorInfo(author: PostList[number]["author"]) {
  console.log(`${author.name}-${author.id}`);
}

const post: PostList[number] = {
  title: "TITLE",
  content: "CONTENT",
  author: {
    id: 1,
    name: "codes",
    age: 25,
  },
};

printAuthorInfo(post.author);
