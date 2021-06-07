//package com.cryptsproject.projecthorenews.data
//
//class Post {
//    constructor(readonly title: String, readonly author: String) {}
//
////    toString(): string {
////        return this.title + ', by ' + this.author;
////    }
//}
//
//const postConverter = {
//    toFirestore(post: Post): firebase.firestore.DocumentData {
//        return {title: post.title, author: post.author};
//    },
//    fromFirestore(
//        snapshot: firebase.firestore.QueryDocumentSnapshot,
//    options: firebase.firestore.SnapshotOptions
//    ): Post {
//        const data = snapshot.data(options)!;
//        return new Post(data.title, data.author);
//    }
//};
//
//const postSnap = await firebase.firestore()
//.collection('posts')
//.withConverter(postConverter)
//.doc().get();
//const post = postSnap.data();
//if (post !== undefined) {
//    post.title; // string
//    post.toString(); // Should be defined
//    post.someNonExistentProperty; // TS error
//}