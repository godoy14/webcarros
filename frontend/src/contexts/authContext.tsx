import { ReactNode, createContext, useState, useEffect } from 'react';

interface IAuthProviderProps {
    children: ReactNode
}

interface IUserProps {
    uid: string;
    name: string | null;
    email: string | null;
    token: string | null;
}

type AuthContextData = {
    signed : boolean;
    loadingAuth : boolean;
    handleInfoUser : ({ name, email, uid, token } : IUserProps) => void;
    handleSignOut : () => void;
    user : IUserProps | null;
}

export const AuthContext = createContext({} as AuthContextData);

function AuthProvider ({children} : IAuthProviderProps) {

    const [user, setUser] = useState<IUserProps | null>(null);
    const [loadingAuth, setLoadingAuth] = useState(true);

    // useEffect(() => {
    //     const unsub = onAuthStateChanged(auth, (user) => {
    //         if(user) {
    //             setUser({
    //                 uid: user.uid,
    //                 name: user?.displayName,
    //                 email: user?.email,
    //                 token: user?.to
    //             })

    //             setLoadingAuth(false);
    //         } else {
    //             setUser(null);
    //             setLoadingAuth(false);
    //         }
    //     })

    //     return () => {
    //         unsub();
    //     }
    // }, []);

    function handleInfoUser({ name, email, uid, token } : IUserProps){
        setUser({
            name, email, uid, token
        })
        setLoadingAuth(false);
    }

    function handleSignOut() {
        console.log(user);
        setUser(null);
    }

    return(
        <AuthContext.Provider
            value={{
                signed : !!user,
                loadingAuth,
                handleInfoUser,
                handleSignOut,
                user
            }}
        >
            {children}
        </AuthContext.Provider>
    )

}

export default AuthProvider;
