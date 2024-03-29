
import { useEffect, useContext } from 'react';

import { Link, useNavigate } from 'react-router-dom';
import logoImg from '../../assets/logo.svg';

import { Container } from '../../components/container';
import { Input } from '../../components/input';

import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';

import toast from 'react-hot-toast';

import axios from '../../api/axios';

import { AuthContext } from '../../contexts/authContext';

const schema = z.object({
    email: z.string().email("Insira um email válido").nonempty("O campo email é obrigatório."),
    password: z.string().nonempty("O campo senha é obrigatório.")
})

type FormData = z.infer<typeof schema>;

export function Login() {

    const { handleInfoUser, handleSignOut } = useContext(AuthContext);

    const navigate = useNavigate();

    const { register, handleSubmit, formState: { errors }} = useForm<FormData>({
        resolver: zodResolver(schema),
        mode: "onChange"
    });

    useEffect(() => {
        function handleLogOut(){
            handleSignOut();
        };

        handleLogOut();
    }, []);

    async function onSubmit(data : FormData) {

        const LOGIN_URL = '/usuarios/login';

        await axios.post(LOGIN_URL, {
            "login": data.email,
            "senha": data.password
        }).then((user) => {
                handleInfoUser({
                    uid: user?.data?.codigo,
                    name: user?.data?.nome,
                    email: user?.data?.email,
                    token: user?.data?.token
                })
                console.log("Logado com sucesso")
                toast.success("Logado com sucesso!")
                navigate("/dashboard", { replace : true })
            })
            .catch((error) => {
                toast.error("Error ao fazer o login.")
                console.log("Erro ao logar");
                console.log(error);
            })
    }

    return(
        <Container>
            <div
                className='w-full min-h-screen flex justify-center items-center flex-col gap-4'
            >
                <Link
                    to="/"
                    className='mb-6 max-w-sm w-full'
                >
                    <img
                        src={logoImg}
                        alt="Logo Webcarros"
                        className='w-full'
                    />
                </Link>

                <form
                    className='bg-white max-w-xl w-full rounded-lg p-4'
                    onSubmit={handleSubmit(onSubmit)}
                >
                    <div className='mb-3'>
                        <Input
                            type="email"
                            placeholder="Email..."
                            name="email"
                            error={errors.email?.message}
                            register={register}
                        />
                    </div>

                    <div className='mb-3'>
                        <Input
                            type="password"
                            placeholder="Senha..."
                            name="password"
                            error={errors.password?.message}
                            register={register}
                        />
                    </div>

                    <button
                        type='submit'
                        className='bg-zinc-900 w-full rounded-md text-white h-10 font-medium'
                    >
                        Acessar
                    </button>
                </form>

                <Link to="/register">
                    Ainda não possui uma conta? Cadastre-se!
                </Link>
            </div>
        </Container>

    )
}
