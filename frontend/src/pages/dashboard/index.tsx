
import { useState, useEffect, useContext } from 'react';

import { Container } from "../../components/container";
import { DashboardHeader } from "../../components/panelHeader";

import axios from '../../api/axios';

import { FiTrash2 } from 'react-icons/fi';

import { AuthContext } from '../../contexts/authContext';

interface ICarProps {
    id: string;
    nome: string;
    ano: string;
    codigo: string;
    preco: string | number;
    cidade: string;
    km: string;
    fotos: ICarImageProps[];
}

interface ICarImageProps {
    name: string;
    uid: string;
    url: string;
}

const BASE_URL = "http://localhost:8080";

export function Dashboard() {

    const { user } = useContext(AuthContext);
    const [cars, setCars] = useState<ICarProps[]>([]);

    useEffect(() => {
        
        async function loadCars() {
            if(!user?.uid && !user?.token) {
                return;
            }

            const CARS_URL = `/carros/usuario/${user.uid}`;

            await axios.get(CARS_URL, {
                params: {
                    status : 'A_VENDA'
                },
                headers: {'Authorization': `Bearer ${user.token}`}
            }).then(snapshot => {
    
                const r = snapshot.data.map((item : ICarProps) => {
                    return {
                        id: item.id,
                        codigo: item.codigo,
                        nome: item.nome,
                        ano: item.ano,
                        preco: item.preco,
                        cidade: item.cidade,
                        km: item.km,
                        fotos: item.fotos
                    }
                })
    
                setCars(r);
            })
        }

        loadCars();

    }, [user]);

    async function handleDeleteCar( car : ICarProps) {
        const itemCar = car;

        const CARS_URL = '/carros';

        await axios.delete(CARS_URL, {
            params: {
                codigoCarro : car.codigo,
                usuario: user?.uid
            },
            headers: {'Authorization': `Bearer ${user?.token}`}
        }).then(() => {
            setCars(cars.filter(car => car.id !== itemCar.id));
        })
    }


    return(
        <Container>
            <DashboardHeader />
            
            <main
                className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3"
            >
                {cars.map( car => (
                    <section
                        className="w-full bg-white rounded-lg relative"
                    >
                        <button
                            onClick={ () => handleDeleteCar(car) }
                            className="absolute bg-white w-14 h-14 rounded-full flex items-center justify-center right-2 top-2 drop-shadow"
                        >
                            <FiTrash2 size={25} color="#000" />
                        </button>
                        <img 
                            className="w-full rounded-lg mb-2 max-h-72"
                            src={BASE_URL + car.fotos[0].url}
                            alt="Foto do carro"
                        />
                        <p className="font-bold mt-1 px-2 mb-2">{car.nome}</p>

                        <div className="flex flex-col px-2">
                            <span className="text-zinc-700">
                                Ano {car.ano} | {car.km} KM
                            </span>
                            <strong className="text-black font-bold mb-4">
                                R$ {car.preco}
                            </strong>
                        </div>

                        <div className="w-full h-px bg-slate-300 my-2"></div>
                        <div className="px-2 pb-2">
                            <span className="text-black">
                                {car.cidade}
                            </span>
                        </div>
                        

                    </section>
                ))}

            </main>

        </Container>
    )
}
