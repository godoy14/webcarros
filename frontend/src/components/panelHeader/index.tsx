import { useContext } from 'react';

import { Link } from 'react-router-dom';

import { AuthContext } from '../../contexts/authContext';

export function DashboardHeader() {

    const { handleSignOut } = useContext(AuthContext);

    async function handleLogOut() {
        await handleSignOut();
    }

    return (
        <div
            className='w-full items-center flex h-10 bg-red-500 rounded-lg text-white font-medium gap-4 px-4 mb-4'
        >
            <Link to="/dashboard">
                Dashboard
            </Link>
            <Link to="/dashboard/new">
                Cadastrar carro
            </Link>

            <button 
                className='ml-auto'
                onClick={handleLogOut}
            >
                Sair da conta
            </button>
        </div>
    )
}