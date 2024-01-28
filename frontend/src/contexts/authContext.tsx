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

    useEffect(() => {
        setLoadingAuth(false);
    }, []);

    function handleInfoUser({ name, email, uid, token } : IUserProps){
        setUser({
            uid: uid,
            name: name,
            email: email,
            token: token
        })
    }

    function handleSignOut() {
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
