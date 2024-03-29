
import { useEffect, useContext } from 'react';
import { AuthContext } from '../../contexts/authContext';

import { Link, useNavigate } from 'react-router-dom';
import logoImg from '../../assets/logo.svg';

import { Container } from '../../components/container';
import { Input } from '../../components/input';

import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';

import toast from 'react-hot-toast';

import axios from '../../api/axios';

const schema = z.object({
    name: z.string().nonempty("O campo nome é obrigatório"),
    email: z.string().email("Insira um email válido").nonempty("O campo email é obrigatório."),
    password: z.string().min(6, "A senha deve ter pelo menos 6 caracteres").nonempty("O campo senha é obrigatório.")
})

type FormData = z.infer<typeof schema>;

export function Register() {

    const navigate = useNavigate();

    const { handleInfoUser, handleSignOut } = useContext(AuthContext);

    const { register, handleSubmit, formState: { errors }} = useForm<FormData>({
        resolver: zodResolver(schema),
        mode: "onChange"
    });

    async function onSubmit(data : FormData) {

        const REGISTER_URL = '/usuarios/cadastro';

        await axios.post(REGISTER_URL, {
            "nome": data.name,
            "email": data.email,
            "senha": data.password,
            "cargo": "USER"
        }).then((user) => {
                handleInfoUser({
                    uid: user?.data?.codigo,
                    name: user?.data?.nome,
                    email: user?.data?.email,
                    token: user?.data?.token
                })
                console.log("cadastrado com sucesso");
                toast.success("Registrado com sucesso!");
                navigate("/dashboard", { replace : true });
            })
            .catch((error) => {
                toast.error("Erro ao cadastrar!");
                console.log("erro ao cadastrar");
                console.log(error);
            })
    }

    useEffect(() => {
        function handleLogOut(){
            handleSignOut();
        };

        handleLogOut();
    }, []);

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
                            type="text"
                            placeholder="Nome..."
                            name="name"
                            error={errors.name?.message}
                            register={register}
                        />
                    </div>

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
                        Cadastrar
                    </button>
                </form>

                <Link to="/login">
                    Já possui uma conta? Faça o login!
                </Link>
            </div>
        </Container>

    )
}
