var functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendPushToGroups = functions.database.ref('/groups').onWrite(event => {
	let groupData = event.data.val(); 
	const payload = {
    	notification: {
            title: 'Push Notif',
            body: 'Push notification via cloud function',
            sound: "default"
        }
    };
 
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24 //24 hours
    };
    return admin.messaging().sendToTopic("groups", payload, options);
});

function loadUsers() {
    let dbRef = admin.database().ref('/users');
    let defer = new Promise((resolve, reject) => {
        dbRef.once('value', (snap) => {
            let data = snap.val();
            let users = [];
            for (var property in data) {
                users.push(data[property]);
            }
            resolve(users);
        }, (err) => {
            reject(err);
        });
    });
    return defer;
}