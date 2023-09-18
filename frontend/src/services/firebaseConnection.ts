
import { initializeApp } from "firebase/app";
import { getAuth } from 'firebase/auth';
import { getFirestore } from 'firebase/firestore';
import { getStorage } from 'firebase/storage';

const firebaseConfig = {
  apiKey: "AIzaSyCBakzBNHQPFmZ6dE_YtI7V3mtmpvSqbsw",
  authDomain: "webcarros-e3c79.firebaseapp.com",
  projectId: "webcarros-e3c79",
  storageBucket: "webcarros-e3c79.appspot.com",
  messagingSenderId: "338283515810",
  appId: "1:338283515810:web:f435ca362401c8878fecfb"
};

const app = initializeApp(firebaseConfig);

const db = getFirestore(app);
const auth = getAuth(app);
const storage = getStorage(app);

export { db, auth, storage };
