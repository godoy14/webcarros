import { ReactNode, createContext, useState, useEffect } from 'react';

import { onAuthStateChanged } from 'firebase/auth';
import { auth } from '../services/firebaseConnection';

interface IAuthProviderProps {
    children: ReactNode
}

interface IUserProps {
    uid: string;
    name: string | null;
    email: string | null;
}

type AuthContextData = {
    signed : boolean;
    loadingAuth : boolean;
    handleInfoUser : ({ name, email, uid } : IUserProps) => void;
    user : IUserProps | null;
}

export const AuthContext = createContext({} as AuthContextData);

function AuthProvider ({children} : IAuthProviderProps) {

    const [user, setUser] = useState<IUserProps | null>(null);
    const [loadingAuth, setLoadingAuth] = useState(true);

    useEffect(() => {
        const unsub = onAuthStateChanged(auth, (user) => {
            if(user) {
                setUser({
                    uid: user.uid,
                    name: user?.displayName,
                    email: user?.email
                })

                setLoadingAuth(false);
            } else {
                setUser(null);
                setLoadingAuth(false);
            }
        })

        return () => {
            unsub();
        }
    }, []);

    function handleInfoUser({ name, email, uid } : IUserProps){
        setUser({
            name, email, uid
        })
    }

    return(
        <AuthContext.Provider
            value={{
                signed : !!user,
                loadingAuth,
                handleInfoUser,
                user
            }}
        >
            {children}
        </AuthContext.Provider>
    )

}

export default AuthProvider;
