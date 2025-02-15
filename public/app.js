document.addEventListener('DOMContentLoaded', event => {
    const app = firebase.app();
    console.log("Firebase app initialized:", app);

    const db = firebase.firestore();

    const myPost = db.collection('posts').doc('firstpost');

    myPost.onSnapshot(doc => {
        if (doc.exists) {
            const data = doc.data();
            console.log("Document data:", data);
            const postContainer = document.getElementById('post-container');
            postContainer.innerHTML = `${data.title}<br>${data.createdAt}<br>`;
        } else {
            console.log("No such document!");
        }
    }, error => {
        console.error("Error fetching document:", error);
    });
});

function googleLogin() {
    const provider = new firebase.auth.GoogleAuthProvider();

    firebase.auth().signInWithPopup(provider)
        .then(result => {
            const user = result.user;
            const userContainer = document.getElementById('user-container');
            userContainer.innerHTML = `Hello ${user.displayName}`;
            console.log("User signed in:", user);
        })
        .catch(error => {
            console.error("Error during sign-in:", error);
        });
}