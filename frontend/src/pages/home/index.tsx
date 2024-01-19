import { Container } from "../../components/container";

import { Link } from 'react-router-dom';

import { useState, useEffect } from 'react';

import axios from "../../api/axios";

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
    nome: string;
    codigo: string;
    url: string;
}

const BASE_URL = "http://localhost:8080";

export function Home() {

    const [cars, setCars] = useState<ICarProps[]>([]);
    const [loadImages, setLoadImages] = useState<string[]>([]);

    const [input, setInput] = useState("");

    useEffect(() => {
        

        loadCars();

    }, []);

    async function loadCars() {

        const CARS_URL = "/carros";

        await axios.get(CARS_URL, {
            params: {
                status : 'A_VENDA'
            }
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

        // const carsRef = collection(db, "cars");
        // const queryRef = query(carsRef, orderBy("created", "desc"));

        // getDocs(queryRef)
        //  .then((snapshot) => {
        //     let listCars = [] as ICarProps[];

        //     snapshot.forEach(doc => {
        //         listCars.push({
        //             id: doc.id,
        //             uid: doc.data().uid,
        //             name: doc.data().name,
        //             year: doc.data().year,
        //             price: doc.data().price,
        //             city: doc.data().city,
        //             km: doc.data().km,
        //             images: doc.data().images
        //         })
        //     })

        //     setCars(listCars);
        // })

    }

    function handleImageLoad(id : string) {
        setLoadImages((prevImageLoaded) => [...prevImageLoaded, id]);
    }

    async function handleSearchCar() {
        if(input === '' && input.length >= 2) {
            loadCars();
            return;
        }
        
        setCars([]);
        setLoadImages([]);

        // const q = query(collection (db, "cars"),
        // where("name", ">=", input.toUpperCase()),
        // where("name", "<=", input.toUpperCase() + "\uf8ff")
        // )

        // const querySnapshot = await getDocs(q);

        const PESQUISA_CARROS_URL = "/carros/pesquisa";

        await axios.get(PESQUISA_CARROS_URL, {
            params: {
                status : 'A_VENDA',
                nome : input
            }
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

    return(
        <Container>
            <section 
                className="bg-white p-4 rounded-lg
                    w-full max-w-3xl mx-auto
                    flex justify-center items-center gap-2"
            >
                <input
                    className="w-full border-2 rounded-lg h-9
                        px-3 outline-none"
                    type="text"
                    placeholder="Digite o nome do carro..."
                    onChange={ (e) => setInput(e.target.value) }
                />
                <button
                    className="bg-red-500 h-9 px-8 rounded-lg
                        text-white font-medium text-lg"
                    onClick={handleSearchCar}
                >
                    Buscar
                </button>
            </section>
            <h1
                className="font-bold text-center mt-6 text-2xl
                    mb-4"
            >
                Carros novos e usados em todo Brasil
            </h1>

            <main
                className="grid grid-cols-1 gap-6 
                    md:grid-cols-2 lg:grid-cols-3"
            >
                {cars.map(car => (
                    <Link
                        key={car.id} 
                        to={`/car/${car.id}`}
                    >
                        <section
                            className="w-full bg-white rounded-lg"
                        >
                            <div 
                                className="w-full h-72 rounded-lg bg-slate-400"
                                style={{ display : loadImages.includes(car.id) ? "none" : "block"}}
                            >
                            </div>
                            <img
                                className="w-full rounded-lg mb-2 max-h-72
                                    hover:scale-105 transition-all"
                                src={BASE_URL + car.fotos[0].url}
                                alt="Carro"
                                onLoad={() => handleImageLoad(car.id)}
                                style={{ display : loadImages.includes(car.id) ? "block" : "none" }}
                            />
                            <p
                                className="font-bold mt-1 mb-2 px-2"
                            >
                                {car.nome}
                            </p>

                            <div
                                className="flex flex-col px-2"
                            >
                                <span
                                    className="text-zinc-700 mb-6"
                                >
                                    Ano {car.ano} | {car.km} KM
                                </span>
                                <strong
                                    className="text-black font-medium text-xl"
                                >
                                    R$ {car.preco}
                                </strong>
                            </div>

                            <div className="w-full h-px bg-slate-200 my-2"></div>
                            
                            <div
                                className="px-2 pb-2"
                            >
                                <span
                                    className="text-zinc-700"
                                >
                                    {car.cidade}
                                </span>
                            </div>
                        </section>
                    </Link>
                ))}
            </main>
        </Container>
    )
}
