// Promise
// resolve(result value)
// reject(reason)
const promise = new Promise<number>((resolve, reject) => {
  setTimeout(() => {
    resolve(20);
    reject("REASON");
  }, 3000);
});

// response를 자동으로 추론하지 않는다. Generic 사용 new Promise<number>
promise
  .then((response) => {
    console.log(response * 10);
  })
  .catch((err) => {
    if (typeof err === "string") {
      console.log(err);
    }
  });

// Promise를 반환하는 함수의 타입을 정의
interface Post {
  id: number;
  title: string;
  content: string;
}

// function에 type 지정 (추천)
function fetchPost(): Promise<Post> {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve({
        id: 1,
        title: "게시글 제목",
        content: "게시글 컨텐츠",
      });
    }, 3000);
  });
}

const postRequest = fetchPost();

postRequest.then((post) => {
  post.id;
});
